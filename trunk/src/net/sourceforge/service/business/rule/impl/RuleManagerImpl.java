/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.service.business.rule.impl;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import net.sourceforge.dao.business.rule.RuleDAO;
import net.sourceforge.model.business.rule.Rule;
import net.sourceforge.model.business.rule.RuleCondition;
import net.sourceforge.model.business.rule.RuleConsequence;
import net.sourceforge.model.business.rule.query.RuleQueryCondition;
import net.sourceforge.model.business.rule.query.RuleQueryOrder;
import net.sourceforge.model.metadata.BudgetType;
import net.sourceforge.model.metadata.ConditionType;
import net.sourceforge.model.metadata.ConsequenceType;
import net.sourceforge.model.metadata.EnabledDisabled;
import net.sourceforge.model.metadata.RuleType;
import net.sourceforge.model.metadata.TravelType;
import net.sourceforge.model.metadata.TravellingMode;
import net.sourceforge.model.metadata.YesNo;
import net.sourceforge.ruleengine.EngineFlow;
import net.sourceforge.ruleengine.EngineRule;
import net.sourceforge.ruleengine.EngineWorkspace;
import net.sourceforge.service.BaseManager;
import net.sourceforge.service.business.rule.FlowManager;
import net.sourceforge.service.business.rule.RuleManager;
import net.sourceforge.web.struts.action.ActionUtils;
import com.shcnc.hibernate.PersistentEnum;

/**
 * RuleManager的实现
 * 
 * @author nicebean
 * @version 1.0 (2005-11-14)
 */
public class RuleManagerImpl extends BaseManager implements RuleManager {
    private RuleDAO dao;

    private FlowManager flowManager;

    private EngineWorkspace workspace;

    /**
     * 设置RuleDAO给dao属性
     * 
     * @param dao
     *            RuleDAO对象
     */
    public void setRuleDAO(RuleDAO dao) {
        this.dao = dao;
    }

    /**
     * 设置FlowManager给flowManager属性
     * 
     * @param flowManager
     *            FlowManager对象
     */
    public void setFlowManager(FlowManager flowManager) {
        this.flowManager = flowManager;
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
     * @see net.sourceforge.service.business.rule.RuleManager#getRule(java.lang.Integer)
     */
    public Rule getRule(Integer id) {
        return getRule(id, false);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sourceforge.service.business.rule.RuleManager#getRule(java.lang.Integer,
     *      boolean)
     */
    public Rule getRule(Integer id, boolean loadLazyProperties) {
        return dao.getRule(id, loadLazyProperties);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sourceforge.service.business.rule.RuleManager#saveRule(net.sourceforge.model.business.rule.Rule)
     */
    public Rule saveRule(Rule r) {
        boolean needUpdateWorkspace = false;
        RuleType rt = r.getType(); 
        if (ConsequenceType.NOTIFIER.equals(rt.getConsequenceType())) {
            if (r.getId() == null) {
                needUpdateWorkspace = EnabledDisabled.ENABLED.equals(r.getEnabled());
            } else {
                Rule or = dao.getRule(r.getId(), false);
                needUpdateWorkspace = !r.getEnabled().equals(or.getEnabled());
                or.setDescription(r.getDescription());
                or.setEnabled(r.getEnabled());
                r = or;
            }
        }
        r = dao.saveRule(r);
        if (needUpdateWorkspace) {
            flowManager.constructEngineFlow(r.getSite().getId(), rt);
        }
        return r;
    }
    
    public void saveRules(List ruleList) {
        if (ruleList != null && ruleList.size() > 0) {
            for (int i = 0; i < ruleList.size(); i++) {
                saveRuleAll((Rule)ruleList.get(i));
            }
        }
    }
    
    public Rule saveRuleAll(Rule r) {
        Rule rule = saveRule(r);
        if (r.getConditions() != null && r.getConditions().size() > 0) {
            Set newRuleConditionSet = new HashSet();
            Iterator iterator = r.getConditions().iterator();
            while (iterator.hasNext()) {
                RuleCondition condition = (RuleCondition)iterator.next();
                condition.setRule(rule);
                RuleCondition ruleCondition = this.saveRuleCondition(condition);    
                newRuleConditionSet.add(ruleCondition);
            }
            //r.setConditions(newRuleConditionSet);
        }
        
        if (r.getConsequences() != null && r.getConsequences().size() > 0) {
            Set newRuleConsequenceSet = new HashSet();
            Iterator iterator = r.getConsequences().iterator();
            while (iterator.hasNext()) {
                RuleConsequence consequence = (RuleConsequence)iterator.next();
                consequence.setRule(rule);           
                RuleConsequence ruleConsequence = dao.saveRuleConsequence(consequence);           
                newRuleConsequenceSet.add(ruleConsequence);
            }
            //r.setConsequences(newRuleConsequenceSet);
        }
        return rule;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sourceforge.service.business.rule.RuleManager#removeRule(java.lang.Integer)
     */
    public void removeRule(Integer id) {
        Rule r = getRule(id, true);
        for (Iterator itor = r.getConditions().iterator(); itor.hasNext();) {
            dao.removeRuleCondition((RuleCondition) itor.next());
        }
        for (Iterator itor = r.getConsequences().iterator(); itor.hasNext();) {
            dao.removeRuleConsequence((RuleConsequence) itor.next());
        }
        dao.removeRule(id);
        RuleType rt = r.getType(); 
        if (ConsequenceType.NOTIFIER.equals(rt.getConsequenceType()) && EnabledDisabled.ENABLED.equals(r.getEnabled())) {
            flowManager.constructEngineFlow(r.getSite().getId(), rt);
        }
    }
    
    public void removeRules(Set ruleSet) {
        if (ruleSet != null && ruleSet.size() > 0) {
            Iterator iter = ruleSet.iterator();
            while(iter.hasNext()) {
                removeRule(((Rule)iter.next()).getId());
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sourceforge.service.business.rule.RuleManager#getRuleListCount(java.util.Map)
     */
    public int getRuleListCount(Map conditions) {
        return dao.getRuleListCount(conditions);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sourceforge.service.business.rule.RuleManager#getRuleList(java.util.Map,
     *      int, int, net.sourceforge.model.business.rule.query.RuleQueryOrder, boolean)
     */
    public List getRuleList(Map conditions, int pageNo, int pageSize, RuleQueryOrder order, boolean descend) {
        return dao.getRuleList(conditions, pageNo, pageSize, order, descend);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sourceforge.service.business.rule.RuleManager#isRuleInUse(java.lang.Integer)
     */
    public boolean isRuleInUse(Integer id) {
        return dao.isRuleInUse(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sourceforge.service.business.rule.RuleManager#getSiteEnabledRuleList(java.lang.Integer,
     *      net.sourceforge.model.metadata.RuleType)
     */
    public List getSiteEnabledRuleList(Integer siteId, RuleType type) {
        Map conditions = new HashMap();
        conditions.put(RuleQueryCondition.SITE_ID_EQ, siteId);
        conditions.put(RuleQueryCondition.TYPE_EQ, type.getEnumCode());
        conditions.put(RuleQueryCondition.ENABLED_EQ, EnabledDisabled.ENABLED.getEnumCode());
        List ruleList = getRuleList(conditions, 0, -1, RuleQueryOrder.DESCRIPTION, false);
        
//        if (isGetCondition) {
//            for (int i = 0; i < ruleList.size(); i++) {
//                Rule rule = (Rule)ruleList.get(i);
//                if (rule.getConditions() != null && rule.getConditions().size() > 0) {
//                    rule.getConditions().iterator().next();
//                }
//                if (isGetConsequence) {
//                    if (rule.getConsequences() != null && rule.getConsequences().size() > 0) {
//                        rule.getConsequences().iterator().next();
//                    }
//                }
//            }
//        }
        
        return ruleList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sourceforge.service.business.rule.RuleManager#getRuleCondition(java.lang.Integer,
     *      net.sourceforge.model.metadata.ConditionType)
     */
    public RuleCondition getRuleCondition(Integer ruleId, ConditionType ct) {
        return dao.getRuleCondition(ruleId, ct);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sourceforge.service.business.rule.RuleManager#saveRuleCondition(net.sourceforge.model.business.rule.RuleCondition)
     */
    public RuleCondition saveRuleCondition(RuleCondition rc) {
        try {
            return dao.saveRuleCondition(rc);
        } finally {
            updateEngineRuleForRule(rc.getRule().getId());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sourceforge.service.business.rule.RuleManager#updateRuleCondition(net.sourceforge.model.business.rule.RuleCondition)
     */
    public RuleCondition updateRuleCondition(RuleCondition rc) {
        try {
            return dao.updateRuleCondition(rc);
        } finally {
            updateEngineRuleForRule(rc.getRule().getId());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sourceforge.service.business.rule.RuleManager#removeRuleCondition(net.sourceforge.model.business.rule.RuleCondition)
     */
    public void removeRuleCondition(RuleCondition rc) {
        dao.removeRuleCondition(rc);
        updateEngineRuleForRule(rc.getRule().getId());
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sourceforge.service.business.rule.RuleManager#getRuleConsequence(java.lang.Integer,
     *      java.lang.Integer)
     */
    public RuleConsequence getRuleConsequence(Integer ruleId, Integer userId) {
        return dao.getRuleConsequence(ruleId, userId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sourceforge.service.business.rule.RuleManager#saveRuleConsequence(net.sourceforge.model.business.rule.RuleConsequence)
     */
    public RuleConsequence saveRuleConsequence(RuleConsequence rc) {
        try {
            if (ConsequenceType.APPROVER.equals(rc.getRule().getType().getConsequenceType())) {
                return saveApproverRuleConsequence(rc);
            }
            return dao.saveRuleConsequence(rc);
        } finally {
            updateEngineRuleForRule(rc.getRule().getId());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sourceforge.service.business.rule.RuleManager#updateRuleConsequence(net.sourceforge.model.business.rule.RuleConsequence)
     */
    public RuleConsequence updateRuleConsequence(RuleConsequence rc) {
        try {
            if (ConsequenceType.APPROVER.equals(rc.getRule().getType().getConsequenceType())) {
                return saveApproverRuleConsequence(rc);
            }
            return dao.updateRuleConsequence(rc);
        } finally {
            updateEngineRuleForRule(rc.getRule().getId());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sourceforge.service.business.rule.RuleManager#removeRuleConsequence(net.sourceforge.model.business.rule.RuleConsequence)
     */
    public void removeRuleConsequence(RuleConsequence rc) {
        try {
            if (ConsequenceType.APPROVER.equals(rc.getRule().getType().getConsequenceType())) {
                removeApproverRuleConsequence(rc);
                return;
            }
            dao.removeRuleConsequence(rc);
        } finally {
            updateEngineRuleForRule(rc.getRule().getId());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sourceforge.service.business.rule.RuleManager#getMaxConsequenceSeqNoForRuleId(java.lang.Integer)
     */
    public Integer getMaxConsequenceSeqNoForRuleId(Integer ruleId) {
        Integer seq = dao.getMaxConsequenceSeqNoForRuleId(ruleId);
        if (seq == null)
            return new Integer(0);
        return seq;
    }

    private Comparator ruleConsequenceComparator = new Comparator() {
        public int compare(Object o1, Object o2) {
            return ((RuleConsequence) o1).getSeq() - ((RuleConsequence) o2).getSeq();
        }
    };

    /**
     * 保存或更新ConsequeceType为APPROVER的Rule Consequence，
     * 并保证完成后该Rule的所有consequence的seq从1开始依次递增。
     * 
     * @param rc
     * @return RuleConsequence
     */
    private RuleConsequence saveApproverRuleConsequence(RuleConsequence rc) {
        int newSeq = rc.getSeq();
        RuleConsequence oldOne = null;
        Set smallerSet = new TreeSet(ruleConsequenceComparator);
        Set biggerSet = new TreeSet(ruleConsequenceComparator);
        for (Iterator itor = dao.getRule(rc.getRule().getId(), true).getConsequences().iterator(); itor.hasNext();) {
            RuleConsequence rc2 = (RuleConsequence) itor.next();
            int currentSeq = rc2.getSeq();
            if (rc2.equals(rc)) {
                oldOne = rc2;
                oldOne.setCanModify(rc.getCanModify());
                oldOne.setSuperior(rc.getSuperior());
            } else {
                if (currentSeq >= newSeq) {
                    biggerSet.add(rc2);
                } else {
                    smallerSet.add(rc2);
                }
            }
        }
        int nextSeq = 1;
        for (Iterator itor = smallerSet.iterator(); itor.hasNext(); nextSeq++) {
            RuleConsequence rc2 = (RuleConsequence) itor.next();
            int currentSeq = rc2.getSeq();
            if (currentSeq != nextSeq) {
                rc2.setSeq(nextSeq);
                dao.updateRuleConsequence(rc2);
            }
        }
        if (oldOne == null) {
            rc.setSeq(nextSeq++);
            oldOne = dao.saveRuleConsequence(rc);
        } else {
            oldOne.setSeq(nextSeq++);
            oldOne = dao.updateRuleConsequence(oldOne);
        }
        for (Iterator itor = biggerSet.iterator(); itor.hasNext(); nextSeq++) {
            RuleConsequence rc2 = (RuleConsequence) itor.next();
            int currentSeq = rc2.getSeq();
            if (currentSeq != nextSeq) {
                rc2.setSeq(nextSeq);
                dao.updateRuleConsequence(rc2);
            }
        }
        return oldOne;
    }

    /**
     * 删除ConsequeceType为APPROVER的Rule Consequence，
     * 并保证完成后该Rule的所有consequence的seq从1开始依次递增。
     * 
     * @param rc
     */
    private void removeApproverRuleConsequence(RuleConsequence rc) {
        dao.removeRuleConsequence(rc);
        int nextSeq = 1;
        for (Iterator itor = dao.getRule(rc.getRule().getId(), true).getConsequences().iterator(); itor.hasNext();) {
            RuleConsequence rc2 = (RuleConsequence) itor.next();
            int currentSeq = rc2.getSeq();
            if (currentSeq != nextSeq) {
                rc2.setSeq(nextSeq);
                dao.updateRuleConsequence(rc2);
            }
        }
    }

    private void updateEngineRuleForRule(Integer ruleId) {
        Rule r = dao.getRule(ruleId, false);
        List conditions = dao.getConditionsForRuleId(ruleId);
        List consequences = dao.getConsequencesForRuleId(ruleId);
        EngineFlow flow = workspace.getFlow(r.getSite().getId() + r.getType().getPrefixUrl());
        if (flow != null) {
            flow.lockForUpdate();
            try {
                Set rules = flow.getRulesByExternalId(ruleId);
                if (rules != null) {
                    for (Iterator itor = rules.iterator(); itor.hasNext();) {
                        EngineRule er = (EngineRule) itor.next();
                        updateEngineRuleForRule(conditions, consequences, er, r.getType());
                    }
                }
            } finally {
                flow.releaseLock();
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sourceforge.service.business.rule.RuleManager#updateEngineRuleForRule(java.lang.Integer,
     *      net.sourceforge.ruleengine.EngineRule)
     */
    public void updateEngineRuleForRule(Integer ruleId, EngineRule er) {
        Rule r = dao.getRule(ruleId, false);
        List conditions = dao.getConditionsForRuleId(ruleId);
        List consequences = dao.getConsequencesForRuleId(ruleId);
        updateEngineRuleForRule(conditions, consequences, er, r.getType());
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sourceforge.service.business.rule.RuleManager#getEnabledRuleListForRuleType(java.lang.Integer,
     *      net.sourceforge.model.metadata.RuleType)
     */
    public List getEnabledRuleListForRuleType(Integer siteId, RuleType rt) {
        Map conditions = new HashMap();
        conditions.put(RuleQueryCondition.SITE_ID_EQ, siteId);
        conditions.put(RuleQueryCondition.ENABLED_EQ, EnabledDisabled.ENABLED);
        conditions.put(RuleQueryCondition.TYPE_EQ, rt);
        return dao.getRuleList(conditions, 0, -1, null, false);
    }

    private void updateEngineRuleForRule(Collection conditions, Collection consequences, EngineRule er, RuleType rt) {
        er.clearConditions();
        for (Iterator itor = conditions.iterator(); itor.hasNext();) {
            RuleCondition rc = (RuleCondition) itor.next();
            int comparePassCondition = rc.getCompareType().getEngineComparePassCondition();
            ConditionType ct = rc.getType();
            String value = rc.getValue();
            String compareSource;
            Object compareTarget;
            if (
                    ConditionType.CAPEX_REQUEST_AMOUNT.equals(ct) || 
                    ConditionType.EXPENSE_ENTERED_AMOUNT.equals(ct) ||
                    ConditionType.PO_PURCHASE_AMOUNT.equals(ct) ||
                    ConditionType.PR_REQUEST_AMOUNT.equals(ct) ||
                    ConditionType.YEARLY_BUDGET_AMOUNT.equals(ct) ||
                    ConditionType.TRAVEL_FEE.equals(ct)) {
                
                compareSource = "approveAmount";
                compareTarget = new BigDecimal(value);
            } else if (ConditionType.DEPARTMENT.equals(ct)) {
                compareSource = "approveDepartment";
                compareTarget = ActionUtils.parseInt(value);
            /*
            } else if (ConditionType.DIFFERENCE_AMOUNT_OF_ACTUAL_BUDGET_AND_PR_REQUEST.equals(ct)) {
                compareSource = "differenceBetweenBudgetAmountAndAmount";
                compareTarget = new BigDecimal(value);
            */
            } else if (ConditionType.OVER_BUDGET.equals(ct)) {
                compareSource = "approveOverBudget";
                compareTarget = new BigDecimal(value);
            } else if (ConditionType.DIFFERENCE_AMOUNT_OF_EXPENSE_CLAIMED_AND_ENTERED.equals(ct)) {
                compareSource = "differenceBetweenClaimAmountAndAmount";
                compareTarget = new BigDecimal(value);
            } else if (ConditionType.DIFFERENCE_AMOUNT_OF_PR_PURCHASE_AND_APPROVED.equals(ct)) {
                compareSource = "differenceBetweenPurchaseAmountAndApprovedAmount";
                compareTarget = new BigDecimal(value);
            /*
            } else if (ConditionType.DIFFERENCE_AMOUNT_PERCENTAGE_OF_ACTUAL_BUDGET_AND_PR_REQUEST.equals(ct)) {
                compareSource = "differencePercentageBetweenBudgetAmountAndAmount";
                compareTarget = new BigDecimal(value);
            */
            } else if (ConditionType.OVER_BUDGET_PERCENTAGE.equals(ct)) {
                compareSource = "approveOverBudgetPercentage";
                compareTarget = new BigDecimal(value);
            } else if (ConditionType.DIFFERENCE_AMOUNT_PERCENTAGE_OF_EXPENSE_CLAIMED_AND_ENTERED.equals(ct)) {
                compareSource = "differencePercentageBetweenClaimAmountAndAmount";
                compareTarget = new BigDecimal(value);
            } else if (ConditionType.DIFFERENCE_AMOUNT_PERCENTAGE_OF_PR_PURCHASE_AND_APPROVED.equals(ct)) {
                compareSource = "differencePercentageBetweenPurchaseAmountAndApprovedAmount";
                compareTarget = new BigDecimal(value);
            } else if (ConditionType.EXPENSE_CATEGORY.equals(ct)) {
                compareSource = "approveExpenseCategory";
                compareTarget = ActionUtils.parseInt(value);
            } else if (ConditionType.EXPENSE_SUBCATEGORY.equals(ct)) {
                compareSource = "approveExpenseSubCategoryAmount";
                compareTarget = ActionUtils.parseInt(value);
            } else if (ConditionType.PURCHASE_CATEGORY.equals(ct)) {
                if (value.charAt(0) == 'p') {
                    compareSource = "approvePurchaseCategory";
                } else {
                    compareSource = "approvePurchaseSubCategory";
                }
                compareTarget = ActionUtils.parseInt(value.substring(1));
            } else if (ConditionType.TRAVEL_FROM.equals(ct)) {
                compareSource = "approveTravelFrom";
                compareTarget = PersistentEnum.fromEnumCode(TravelType.class, ActionUtils.parseInt(value));
            } else if (ConditionType.TRAVEL_MODE.equals(ct)) {
                compareSource = "approveTravellingMode";
                compareTarget = PersistentEnum.fromEnumCode(TravellingMode.class, ActionUtils.parseInt(value));
            } else if (ConditionType.TRAVEL_TO.equals(ct)) {
                compareSource = "approveTravelTo";
                compareTarget = PersistentEnum.fromEnumCode(TravelType.class, ActionUtils.parseInt(value));
            } else if (ConditionType.BUDGET_TYPE.equals(ct)) {
                compareSource = "notifyBudgetType";
                compareTarget = PersistentEnum.fromEnumCode(BudgetType.class, ActionUtils.parseInt(value));
            } else if (ConditionType.REMAIN_AMOUNT.equals(ct)) {
                compareSource = "notifyRemainAmount";
                compareTarget = new BigDecimal(value);
            } else if (ConditionType.WITH_BUDGET.equals(ct)) {
                compareSource = "approveWithBudget";
                compareTarget = PersistentEnum.fromEnumCode(YesNo.class, ActionUtils.parseInt(value));
            } else if (ConditionType.PR_REQUEST_ITEM_MAX_PRICE.equals(ct)) {
                compareSource = "maxItemPrice";
                compareTarget = new BigDecimal(value);
            } else {
                throw new UnsupportedOperationException("Missing support for condition type '" + ct.getEngShortDescription() + "'.");
            }
            er.addCondition(compareSource, comparePassCondition, compareTarget);
        }
        if (ConsequenceType.ACCEPTABLE.equals(rt.getConsequenceType())) {
            er.setConsequencesPass(Boolean.TRUE);
            er.setConsequencesFail(Boolean.FALSE);
        } else {
            er.setConsequencesPass(consequences);
        }
    }

}
