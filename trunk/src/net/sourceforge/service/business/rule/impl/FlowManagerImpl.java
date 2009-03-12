/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.service.business.rule.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sourceforge.dao.business.rule.FlowDAO;
import net.sourceforge.model.admin.Site;
import net.sourceforge.model.admin.User;
import net.sourceforge.model.business.Approvable;
import net.sourceforge.model.business.BaseApproveRequest;
import net.sourceforge.model.business.BasePurchaser;
import net.sourceforge.model.business.Controllable;
import net.sourceforge.model.business.Notifiable;
import net.sourceforge.model.business.Purchasable;
import net.sourceforge.model.business.rule.Flow;
import net.sourceforge.model.business.rule.FlowRule;
import net.sourceforge.model.business.rule.Rule;
import net.sourceforge.model.business.rule.RuleConsequence;
import net.sourceforge.model.business.rule.query.FlowQueryCondition;
import net.sourceforge.model.business.rule.query.FlowQueryOrder;
import net.sourceforge.model.metadata.ApproveStatus;
import net.sourceforge.model.metadata.ConsequenceType;
import net.sourceforge.model.metadata.EnabledDisabled;
import net.sourceforge.model.metadata.RuleType;
import net.sourceforge.ruleengine.EngineFlow;
import net.sourceforge.ruleengine.EngineRule;
import net.sourceforge.ruleengine.EngineWorkspace;
import net.sourceforge.service.BaseManager;
import net.sourceforge.service.admin.EmailManager;
import net.sourceforge.service.admin.SiteManager;
import net.sourceforge.service.admin.UserManager;
import net.sourceforge.service.business.rule.ExecuteFlowEmptyResultException;
import net.sourceforge.service.business.rule.FlowManager;
import net.sourceforge.service.business.rule.NoAvailableFlowToExecuteException;
import net.sourceforge.service.business.rule.RuleManager;
import com.shcnc.hibernate.PersistentEnum;

/**
 * FlowManager的实现
 * 
 * @author nicebean
 * @version 1.0 (2005-11-10)
 */
public class FlowManagerImpl extends BaseManager implements FlowManager {
    private Log log = LogFactory.getLog(FlowManagerImpl.class);

    private FlowDAO dao;

    private SiteManager siteManager;

    private UserManager userManager;

    private RuleManager ruleManager;
    
    private EmailManager emailManager;

    private EngineWorkspace workspace;

    /**
     * 设置FlowDAO给dao属性
     * 
     * @param dao
     *            FlowDAO对象
     */
    public void setFlowDAO(FlowDAO dao) {
        this.dao = dao;
    }

    /**
     * 设置SiteManager给siteManager属性
     * 
     * @param siteManager
     *            SiteManager对象
     */
    public void setSiteManager(SiteManager siteManager) {
        this.siteManager = siteManager;
    }

    /**
     * 设置UserManager给userManager属性
     * 
     * @param userManager
     *            UserManager对象
     */
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    /**
     * 设置RuleManager给ruleManager属性
     * 
     * @param ruleManager
     *            RuleManager对象
     */
    public void setRuleManager(RuleManager ruleManager) {
        this.ruleManager = ruleManager;
    }

    public void setEmailManager(EmailManager emailManager) {
        this.emailManager = emailManager;
    }

    /**
     * 设置EngineWorkspace给workspace属性
     * 
     * @param workspace
     *            EngineWorkspace对象
     */
    public void setWorkspace(EngineWorkspace workspace) {
        this.workspace = workspace;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sourceforge.service.business.rule.FlowManager#getFlow(java.lang.Integer)
     */
    public Flow getFlow(Integer id) {
        return getFlow(id, false);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sourceforge.service.business.rule.FlowManager#getFlow(java.lang.Integer,
     *      boolean)
     */
    public Flow getFlow(Integer id, boolean loadLazyProperties) {
        return dao.getFlow(id, loadLazyProperties);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sourceforge.service.business.rule.FlowManager#saveFlow(net.sourceforge.model.business.rule.Flow)
     */
    public Flow[] saveFlow(Flow f) {
        return saveFlow(f, null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sourceforge.service.business.rule.FlowManager#saveFlow(net.sourceforge.model.business.rule.Flow,
     *      java.util.Set)
     */
    public Flow[] saveFlow(Flow f, Set flowRules) {
        Flow[] result = null;
        boolean needUpdateWorkspace = false;

        Flow f2 = getSiteEnabledFlow(f.getSite().getId(), f.getType());
        if (f.equals(f2)) {
            f2.setDescription(f.getDescription());
            f2.setEnabled(f.getEnabled());
            f = f2;
            needUpdateWorkspace = true;
        } else {
            if (EnabledDisabled.ENABLED.equals(f.getEnabled())) {
                if (f2 != null) {
                    result = new Flow[2];
                    f2.setEnabled(EnabledDisabled.DISABLED);
                    result[1] = dao.saveFlow(f2);
                }
                needUpdateWorkspace = true;
            }
        }

        if (result == null)
            result = new Flow[1];
        result[0] = dao.saveFlow(f);
        if (flowRules != null) {
            for (Iterator itor = dao.getRulesForFlow(f.getId()).iterator(); itor.hasNext();) {
                dao.removeFlowRule((FlowRule) itor.next());
            }
            for (Iterator itor = flowRules.iterator(); itor.hasNext();) {
                dao.saveFlowRule((FlowRule) itor.next());
            }
        }
        if (needUpdateWorkspace) {
            constructEngineFlow(f.getSite().getId(), f.getType());
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sourceforge.service.business.rule.FlowManager#removeFlow(java.lang.Integer)
     */
    public void removeFlow(Integer id) {
        Flow f = getFlow(id, true);
        for (Iterator itor = f.getRules().iterator(); itor.hasNext();) {
            dao.removeFlowRule((FlowRule) itor.next());
        }
        dao.removeFlow(id);
        if (EnabledDisabled.ENABLED.equals(f.getEnabled())) {
            Site s = f.getSite();
            RuleType rt = f.getType();
            workspace.removeFlow(s.getId() + rt.getPrefixUrl());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sourceforge.service.business.rule.FlowManager#getFlowListCount(java.util.Map)
     */
    public int getFlowListCount(Map conditions) {
        return dao.getFlowListCount(conditions);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sourceforge.service.business.rule.FlowManager#getFlowList(java.util.Map,
     *      int, int, net.sourceforge.model.business.rule.query.FlowQueryOrder, boolean)
     */
    public List getFlowList(Map conditions, int pageNo, int pageSize, FlowQueryOrder order, boolean descend) {
        return dao.getFlowList(conditions, pageNo, pageSize, order, descend);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sourceforge.service.business.rule.FlowManager#getSiteEnabledFlow(java.lang.Integer,
     *      net.sourceforge.model.metadata.RuleType)
     */
    public Flow getSiteEnabledFlow(Integer siteId, RuleType type) {
        Map conditions = new HashMap();
        conditions.put(FlowQueryCondition.ENABLED_EQ, EnabledDisabled.ENABLED.getEnumCode());
        conditions.put(FlowQueryCondition.SITE_ID_EQ, siteId);
        conditions.put(FlowQueryCondition.TYPE_EQ, type.getEnumCode());
        List l = getFlowList(conditions, 0, 1, null, false);
        if (l.size() > 0)
            return (Flow) l.get(0);
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sourceforge.service.business.rule.FlowManager#validateRules(java.util.Collection)
     */
    public int validateRules(Collection flowRules) {
        List rules = new ArrayList();
        for (Iterator itor = flowRules.iterator(); itor.hasNext();) {
            FlowRule fr = (FlowRule) itor.next();
            rules.add(createEngineRuleForFlowRule(fr));
        }
        return EngineFlow.validateRules(rules);
    }

    /**
     * 初始化Rule Engine 工作区
     * 
     * @throws Exception
     */
    public void initWorkspace() {
        log.info("Initialize rule engine workspace");
        List ruleTypeList = PersistentEnum.getEnumList(RuleType.class);
        for (Iterator itor = siteManager.getAllEnabledSiteList().iterator(); itor.hasNext();) {
            Site s = (Site) itor.next();
            Integer siteId = s.getId();
            for (Iterator itorRuleType = ruleTypeList.iterator(); itorRuleType.hasNext();) {
                RuleType rt = (RuleType) itorRuleType.next();

                log.info("Initialize flow type '" + rt.getEngShortDescription() + "' for site '" + s.getName() + "' to workspace.");
                constructEngineFlow(siteId, rt);
            }
        }
    }

    public void constructEngineFlow(Integer siteId, RuleType rt) {
        String flowName = siteId + rt.getPrefixUrl();
        Collection flowRules = null;
        if (ConsequenceType.NOTIFIER.equals(rt.getConsequenceType())) {
            flowRules = new ArrayList();
            List ruleList = ruleManager.getEnabledRuleListForRuleType(siteId, rt);
            int i = 2;
            for (Iterator itorRule = ruleList.iterator(); itorRule.hasNext(); i++) {
                Rule r = (Rule) itorRule.next();
                FlowRule fr = new FlowRule();
                fr.setRule(r);
                if (itorRule.hasNext()) {
                    fr.setNextSeqWhenFail(i);
                    fr.setNextSeqWhenPass(i);
                } else {
                    fr.setNextSeqWhenFail(0);
                    fr.setNextSeqWhenPass(0);
                }
                flowRules.add(fr);
            }
        } else {
            Flow f = getSiteEnabledFlow(siteId, rt);
            if (f != null) {
                flowRules = dao.getRulesForFlow(f.getId());
            }
        }

        if (flowRules == null || flowRules.isEmpty()) {
            workspace.removeFlow(flowName);
            return;
        }
        EngineFlow ef = workspace.getFlow(flowName);
        if (ef == null) {
            ef = workspace.createFlow(flowName);
        }
        ef.lockForUpdate();
        try {
            ef.clearRules();
            for (Iterator itor = flowRules.iterator(); itor.hasNext();) {
                ef.addRule(createEngineRuleForFlowRule((FlowRule) itor.next()));
            }
        } finally {
            ef.releaseLock();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sourceforge.service.business.rule.FlowManager#createEngineRuleForFlowRule(net.sourceforge.model.business.rule.FlowRule)
     */
    public EngineRule createEngineRuleForFlowRule(FlowRule fr) {
        Integer ruleId = fr.getRule().getId();
        EngineRule er = new EngineRule(ruleId);
        ruleManager.updateEngineRuleForRule(ruleId, er);
        er.setNextSeqPass(fr.getNextSeqWhenPass() - 1);
        er.setNextSeqFail(fr.getNextSeqWhenFail() - 1);
        return er;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sourceforge.service.business.rule.FlowManager#executeApproveFlow(net.sourceforge.model.business.Approvable)
     */
    public List executeApproveFlow(Approvable target) throws ExecuteFlowEmptyResultException, NoAvailableFlowToExecuteException {
        if (target == null) {
            throw new RuntimeException("Cannot execute flow on null target");
        }

        EngineFlow ef = workspace.getFlow(target.getApproveFlowName());
        if (ef == null) {
            throw new NoAvailableFlowToExecuteException();
        }
        List l = ef.execute(target);
        int s = l.size();
        if (s == 0) {
            throw new ExecuteFlowEmptyResultException();
        }
        List results = new ArrayList();
        Set users = new HashSet();
        User requestor = target.getRequestor();
        /*
         * 重复的approver保留后一个 
         */
        /*
        for (int i = s; i > 0; i--) {
            RuleConsequence rc = (RuleConsequence) l.get(i - 1);
            User approver = rc.getUser();
            User superior = rc.getSuperior();
            if (superior != null && approver.equals(requestor)) {
                approver = superior;
            }
            if (!users.contains(approver)) {
                BaseApproveRequest bar = target.createNewApproveRequestObj();
                bar.setApprover(approver);
                bar.setCanModify(rc.getCanModify());
                results.add(0, bar);
                users.add(approver);
            }
        }
        */
        /*
         * 重复的approver保留前一个 
         */
        for (int i = 0; i < s; i++) {
            RuleConsequence rc = (RuleConsequence) l.get(i);
            User approver = rc.getUser();
            User superior = rc.getSuperior();
            if (superior != null && approver.equals(requestor)) {
                approver = superior;
            }
            if (!users.contains(approver)) {
                BaseApproveRequest bar = target.createNewApproveRequestObj();
                bar.setApprover(approver);
                bar.setCanModify(rc.getCanModify());
                results.add(bar);
                users.add(approver);
            }
        }

        s = results.size();
        for (int i = 0; i < s; i++) {
            BaseApproveRequest bar = (BaseApproveRequest) results.get(i);
            if (i == 0) {
                bar.setStatus(ApproveStatus.WAITING_FOR_APPROVE);
                bar.setYourTurnDate(new Date());
            } else {
                bar.setStatus(ApproveStatus.NOT_YOUR_TURN);
            }
            bar.setSeq(i + 1);
        }
        return results;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sourceforge.service.business.rule.FlowManager#executePurchaseFlow(net.sourceforge.model.business.Purchasable)
     */
    public List executePurchaseFlow(Purchasable target) throws ExecuteFlowEmptyResultException, NoAvailableFlowToExecuteException {
        if (target == null) {
            throw new RuntimeException("Cannot execute flow on null target");
        }

        EngineFlow ef = workspace.getFlow(target.getPurchaseFlowName());
        if (ef == null)
            throw new NoAvailableFlowToExecuteException();
        List l = ef.execute(target);
        int s = l.size();
        if (s == 0) {
            throw new ExecuteFlowEmptyResultException();
        }
        List results = new ArrayList();
        Set users = new HashSet();
        for (Iterator itor = l.iterator(); itor.hasNext();) {
            RuleConsequence rc = (RuleConsequence) itor.next();
            User purchaser = rc.getUser();
            if (!users.contains(purchaser)) {
                BasePurchaser bp = target.createNewPurchaserObj();
                bp.setPurchaser(purchaser);
                results.add(bp);
                users.add(purchaser);
            }
        }
        return results;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sourceforge.service.business.rule.FlowManager#executeControlFlow(net.sourceforge.model.business.Controllable)
     */
    public boolean executeControlFlow(Controllable target) throws NoAvailableFlowToExecuteException {
        if (target == null) {
            throw new RuntimeException("Cannot execute flow on null target");
        }

        EngineFlow ef = workspace.getFlow(target.getControlFlowName());
        if (ef == null) {
            throw new NoAvailableFlowToExecuteException();
        }
        List l = ef.execute(target);
        int s = l.size();
        if (s == 0) {
            throw new RuntimeException("Execute control flow return empty result list, this is a coding error.");
        }
        return ((Boolean) l.get(s - 1)).booleanValue();
    }

    public void executeNotifyFlow(Notifiable target) {
        if (target == null) {
            throw new RuntimeException("Cannot execute flow on null target");
        }

        EngineFlow ef = workspace.getFlow(target.getNotifyFlowName());
        if (ef == null) {
            return;
        }
        Set users = new HashSet();
        for (Iterator itor = ef.execute(target).iterator(); itor.hasNext();) {
            RuleConsequence rc = (RuleConsequence) itor.next();
            User notifier = rc.getUser();
            if (!users.contains(notifier)) {
                /*
                 * 重新load User, 个人信息可能已经修改
                 */
                notifier = userManager.getUser(notifier.getId());
                if (EnabledDisabled.ENABLED.equals(notifier.getEnabled())) {
                    Map context = target.getNotifyEmailContext();
                    context.put("user", notifier);
                    context.put("role", EmailManager.EMAIL_ROLE_NOTIFIER);
                    emailManager.insertEmail(target.getLogSite(), notifier.getEmail(), target.getNotifyEmailTemplateName(), context);
                }

                users.add(notifier);
            }
        }
    }
    
    public void saveFlowRules(Set flowRuleSet) {
        if (flowRuleSet != null && flowRuleSet.size() > 0) {
            Flow savedFlow = null;
            Iterator iterator = flowRuleSet.iterator();
            while (iterator.hasNext()) {
                FlowRule flowRule = (FlowRule)iterator.next();
                if (savedFlow == null) {
                    saveFlow(flowRule.getFlow());
                    savedFlow = flowRule.getFlow();
                } else {
                    flowRule.setFlow(savedFlow);
                }
                
                ruleManager.saveRuleAll(flowRule.getRule());
                dao.saveFlowRule(flowRule);
            }            
        }
    }

}
