/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.web.struts.action.business.rule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import net.sourceforge.model.admin.Function;
import net.sourceforge.model.admin.Site;
import net.sourceforge.model.admin.query.UserQueryCondition;
import net.sourceforge.model.admin.query.UserQueryOrder;
import net.sourceforge.model.business.rule.Rule;
import net.sourceforge.model.business.rule.RuleConsequence;
import net.sourceforge.model.metadata.ConditionType;
import net.sourceforge.model.metadata.ConsequenceType;
import net.sourceforge.model.metadata.EnabledDisabled;
import net.sourceforge.model.metadata.RuleType;
import net.sourceforge.model.metadata.YesNo;
import net.sourceforge.service.admin.UserManager;
import net.sourceforge.service.business.rule.RuleManager;
import net.sourceforge.web.struts.action.BaseAction;
import net.sourceforge.web.struts.action.ServiceLocator;
import net.sourceforge.web.struts.form.admin.UserQueryForm;
import com.shcnc.hibernate.PersistentEnum;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.action.ActionUtils;
import com.shcnc.struts.action.BackToInputActionException;
import com.shcnc.struts.form.BeanForm;

/**
 * 操作RuleConsequence和FilterConsequence的共用Action
 * @author nicebean
 * @version 1.0 (2005-11-14)
 */
public class BaseRuleConsequenceAction extends BaseAction {
    protected ActionForward newObject(ActionMapping mapping, HttpServletRequest request, RuleType type, Site site) throws Exception {
        request.setAttribute("X_RULETYPE", type);
        ConsequenceType ct = type.getConsequenceType();
        if (ConsequenceType.ACCEPTABLE.equals(ct)) throw new ActionException("ruleConsequence.OperationNotSupported", type.getPrefixUrl());

        RuleConsequence rc = prepareAndCheckRuleConsequence(type, request, site);
        BeanForm ruleConsequenceForm = (BeanForm) getForm("/insert" + type.getPrefixUrl() + "RuleConsequence", request);
        if (!isBack(request)) {
            ruleConsequenceForm.populateToForm(rc);
        }

        prepareData(rc, request, type);
        request.setAttribute("X_RULECONSEQUENCE", rc);
        return mapping.findForward("pageRuleConsequenceNew" + type.getConsequenceType().getPrefixUrl());
    }

    protected ActionForward insert(ActionMapping mapping, BeanForm ruleConsequenceForm, HttpServletRequest request, RuleType type, Site site) throws Exception {
        request.setAttribute("X_RULETYPE", type);
        ConsequenceType ct = type.getConsequenceType();
        if (ConsequenceType.ACCEPTABLE.equals(ct)) throw new ActionException("ruleConsequence.OperationNotSupported", type.getPrefixUrl());

        RuleConsequence rc = getRuleConsequence(request);
        if (rc != null) throw new BackToInputActionException("ruleConsequence.user.duplicate");
        if (ConsequenceType.PURCHASER.equals(ct) || ConsequenceType.NOTIFIER.equals(ct)) {
            ruleConsequenceForm.set("seq", "0");
            ruleConsequenceForm.set("canModify", YesNo.NO.getEnumCode().toString());
        }
        rc = prepareAndCheckRuleConsequence(type, request, site);
        updateAndCheckRuleConsequence(rc, request, ruleConsequenceForm, type);
        RuleManager rm = ServiceLocator.getRuleManager(request);
        
        if (rc.getRule() != null) {
            rc = rm.saveRuleConsequence(rc);
        } else {
            UserManager um = ServiceLocator.getUserManager(request);
            rc.setUser(um.getUser(rc.getUser().getId()));
            if (rc.getSuperior() != null) {
                rc.setSuperior(um.getUser(rc.getSuperior().getId()));
            }
        }
        
        if (ConsequenceType.APPROVER.equals(ct) && rc.getRule() != null) {
            Rule r = rm.getRule(rc.getRule().getId(), true);
            request.setAttribute("X_OBJECTS", r.getConsequences());
            request.setAttribute("X_ROWPAGE", "ruleConsequence/" + lowerFirstChar(ct.getPrefixUrl()) + "Rows.jsp");           
        } else {
            request.setAttribute("X_OBJECT", rc);
            request.setAttribute("X_ROWPAGE", "ruleConsequence/" + lowerFirstChar(ct.getPrefixUrl()) + "Row.jsp");
        }
        return mapping.findForward("successRuleConsequenceNew");
    }
    
    protected ActionForward edit(ActionMapping mapping, HttpServletRequest request, RuleType type, Site site) throws Exception {
        request.setAttribute("X_RULETYPE", type);
        ConsequenceType ct = type.getConsequenceType();
        if (ConsequenceType.ACCEPTABLE.equals(ct) || ConsequenceType.PURCHASER.equals(ct) || ConsequenceType.NOTIFIER.equals(ct)) throw new ActionException("ruleConsequence.OperationNotSupported", type.getPrefixUrl());

        RuleConsequence rc = getAndCheckRuleConsequence(type, request, site);
        
        BeanForm ruleConditionForm = (BeanForm) getForm("/update" + type.getPrefixUrl() + "RuleConsequence", request);
        if (!isBack(request)) {
            ruleConditionForm.populateToForm(rc);
        }

        prepareData(rc, request, type);
        request.setAttribute("X_RULECONSEQUENCE", rc);
        return mapping.findForward("pageRuleConsequenceEdit" + type.getConsequenceType().getPrefixUrl());
    }

    protected ActionForward update(ActionMapping mapping, BeanForm ruleConsequenceForm, HttpServletRequest request, RuleType type, Site site) throws Exception {
        ConsequenceType ct = type.getConsequenceType();
        if (ConsequenceType.ACCEPTABLE.equals(ct) || ConsequenceType.PURCHASER.equals(ct) || ConsequenceType.NOTIFIER.equals(ct)) throw new ActionException("ruleConsequence.OperationNotSupported", type.getPrefixUrl());

        RuleConsequence rc = getAndCheckRuleConsequence(type, request, site);
        updateAndCheckRuleConsequence(rc, request, ruleConsequenceForm, type);
        RuleManager rm = ServiceLocator.getRuleManager(request);

        if (rc.getRule() != null) {
            rc = rm.updateRuleConsequence(rc);
        } else {
            UserManager um = ServiceLocator.getUserManager(request);
            rc.setUser(um.getUser(rc.getUser().getId()));
            if (rc.getSuperior() != null) {
                rc.setSuperior(um.getUser(rc.getSuperior().getId()));
            }
        }
        
        if (ConsequenceType.APPROVER.equals(ct) && rc.getRule() != null) {
            Rule r = rm.getRule(rc.getRule().getId(), true);
            request.setAttribute("X_OBJECTS", r.getConsequences());
            request.setAttribute("X_ROWPAGE", "ruleConsequence/" + lowerFirstChar(ct.getPrefixUrl()) + "Rows.jsp");
        } else {
            request.setAttribute("X_OBJECT", rc);
            request.setAttribute("X_ROWPAGE", "ruleConsequence/" + lowerFirstChar(ct.getPrefixUrl()) + "Row.jsp");
        }
        return mapping.findForward("successRuleConsequenceEdit");
    }
    
    protected ActionForward delete(ActionMapping mapping, HttpServletRequest request, RuleType type, Site site) throws Exception {
        ConsequenceType ct = type.getConsequenceType();
        if (ConsequenceType.ACCEPTABLE.equals(ct)) throw new ActionException("ruleConsequence.OperationNotSupported", type.getPrefixUrl());

        RuleConsequence rc = getAndCheckRuleConsequence(type, request, site);
        RuleManager rm = ServiceLocator.getRuleManager(request);
        
        rm.removeRuleConsequence(rc);
        if (ConsequenceType.APPROVER.equals(ct)) {
            Rule r = rm.getRule(rc.getRule().getId(), true);
            request.setAttribute("X_OBJECTS", r.getConsequences());
            request.setAttribute("X_ROWPAGE", "ruleConsequence/" + lowerFirstChar(ct.getPrefixUrl()) + "Rows.jsp");
        } else {
            request.setAttribute("X_OBJECT", rc);
        }
        return mapping.findForward("successRuleConsequenceDelete" + ct.getPrefixUrl());
    }
    
    protected ActionForward selectUser(ActionMapping mapping, UserQueryForm queryForm, HttpServletRequest request, RuleType type) throws Exception {
        ConsequenceType ct = type.getConsequenceType();
        if (ConsequenceType.ACCEPTABLE.equals(ct)) throw new ActionException("ruleConsequence.OperationNotSupported", type.getPrefixUrl());
        Function f = null;
        if (!ConsequenceType.NOTIFIER.equals(ct)) {
            f = ServiceLocator.getFunctionManager(request).getFunction(type.getPrefixUrl());
            if (f == null) throw new ActionException("ruleConsequence.functionNotFound", type.getPrefixUrl());
        }
        
        /**
         * 检查要查询的Site是否被授权，并取得授权Site列表
         */
        List grantedSiteList = getGrantedSiteDeparmentList(request);
        request.setAttribute("X_SITELIST", grantedSiteList);
        
        if (hasGlobalPower(request)) {
            request.setAttribute("X_GLOBAL", Boolean.TRUE);
        } else {
            /**
             * Site级查询初始应取授权Site列表第一项
             */
            Integer siteId = ActionUtils.parseInt(queryForm.getSiteId());
            if (siteId == null) {
                siteId = ((Site) grantedSiteList.get(0)).getId();
                queryForm.setSiteId(siteId.toString());
            } else {
                checkSite(siteId, request);
            }
            request.setAttribute("X_GLOBAL", Boolean.FALSE);
        }
        
        UserManager um =  ServiceLocator.getUserManager(request);
        
        Map conditions = constructQueryMap(queryForm);
        conditions.put(UserQueryCondition.ENABLED_EQ, EnabledDisabled.ENABLED);
        
        if (f != null) {
            conditions.put(UserQueryCondition.FUNCTION_ID_EQ, f.getId());
        }
        
        queryForm.setPageSize("10");
        if (queryForm.isFirstInit()) {
            queryForm.init(um.getUserListCount(conditions));
        } else {
            queryForm.init();
        }

        request.setAttribute("X_RULETYPE", type);
        request.setAttribute("X_RESULTLIST", um.getUserList(conditions, queryForm.getPageNoAsInt(), queryForm.getPageSizeAsInt(), UserQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend()));
        return mapping.findForward("pageRuleConsequenceSelectUser");
    }

    private Map constructQueryMap(UserQueryForm queryForm) {
        Map conditions = new HashMap();

        String loginName = queryForm.getLoginName();
        if (loginName != null) {
            loginName = loginName.trim();
            if (loginName.length() == 0) loginName = null;
        }
        if (loginName != null) conditions.put(UserQueryCondition.LOGINNAME_LIKE, loginName);

        String name = queryForm.getName();
        if (name != null) {
            name = name.trim();
            if (name.length() == 0) name = null;
        }
        if (name != null) conditions.put(UserQueryCondition.NAME_LIKE, name);
        
        Integer siteId = ActionUtils.parseInt(queryForm.getSiteId());
        Integer departmentId = ActionUtils.parseInt(queryForm.getDepartmentId());
        if (departmentId != null) {
            conditions.put(UserQueryCondition.DEPARTMENT_ID_EQ, departmentId);
        } else if (siteId != null) {
            conditions.put(UserQueryCondition.SITE_ID_EQ, siteId);
        }
        
        return conditions;
    }

    private String lowerFirstChar(String value) {
        return Character.toLowerCase(value.charAt(0)) + value.substring(1);
    }
    
    private RuleConsequence getRuleConsequence(HttpServletRequest request) throws Exception {
        Integer ruleId = ActionUtils.parseInt(request.getParameter("rule_id"));
        Integer userId = ActionUtils.parseInt(request.getParameter("user_id"));
        if (ruleId != null) {
            RuleManager rm = ServiceLocator.getRuleManager(request);
            return rm.getRuleConsequence(ruleId, userId);
        } else {
           return null;
        }
        
    }
    
    private RuleConsequence prepareAndCheckRuleConsequence(RuleType type, HttpServletRequest request, Site site) throws Exception {
        RuleManager rm = ServiceLocator.getRuleManager(request);
        Integer ruleId = ActionUtils.parseInt(request.getParameter("rule_id"));
        if (ruleId != null) {
            Rule r = rm.getRule(ruleId);
            if (r == null || !type.equals(r.getType())) throw new ActionException("rule." + type.getPrefixUrl() + ".notFound", ruleId);
            checkSite(r.getSite(), request);
            RuleConsequence rc =  new RuleConsequence();
            rc.setRule(r);
            return rc;
        } else {
            checkSite(site, request);
            RuleConsequence rc =  new RuleConsequence();
            return rc;
        }
    }
    
    private RuleConsequence getAndCheckRuleConsequence(RuleType type, HttpServletRequest request, Site site) throws Exception {
        Integer ruleId = ActionUtils.parseInt(request.getParameter("rule_id"));
        Integer userId = ActionUtils.parseInt(request.getParameter("user_id"));
        if (ruleId != null) {
            RuleManager rm = ServiceLocator.getRuleManager(request);
            RuleConsequence rc = rm.getRuleConsequence(ruleId, userId);
            if (rc == null) throw new ActionException("ruleConsequence.notFound", new Object[] { ruleId, userId } );
            Rule r = rc.getRule();
            if (r ==null || !type.equals(r.getType())) throw new ActionException("rule." + type.getPrefixUrl() + ".notFound", ruleId);
            checkSite(r.getSite(), request);
            return rc;
        } else {
            Integer superiorId = ActionUtils.parseInt(request.getParameter("superiorId"));
            Integer canModify = ActionUtils.parseInt(request.getParameter("canModify"));
            Integer seq = ActionUtils.parseInt(request.getParameter("seq"));
            YesNo yn = (YesNo) PersistentEnum.fromEnumCode(YesNo.class, canModify);
            RuleConsequence rc = new RuleConsequence();
            UserManager um = ServiceLocator.getUserManager(request);
            rc.setUser(um.getUser(userId));
            if (superiorId != null) {
                rc.setSuperior(um.getUser(superiorId));
            }
            rc.setCanModify(yn);
            rc.setSeq(seq);
            checkSite(site, request);
            return rc;
        }
    }
    
    private void updateAndCheckRuleConsequence(RuleConsequence rc, HttpServletRequest request, BeanForm ruleConsequenceForm, RuleType rt) {
        ruleConsequenceForm.populateToBean(rc);
    }
    
    private void prepareData(RuleConsequence rc, HttpServletRequest request, RuleType type) throws Exception {
        if (rc.getRule() != null) {
            Rule r = rc.getRule();
            ConsequenceType ct = r.getType().getConsequenceType();
            if (ConsequenceType.APPROVER.equals(ct)) {
                request.setAttribute("X_YESNOLIST", PersistentEnum.getEnumList(YesNo.class));
                request.setAttribute("X_MAXSEQ", ServiceLocator.getRuleManager(request).getMaxConsequenceSeqNoForRuleId(r.getId()));
            }
        } else {
            ConsequenceType ct = type.getConsequenceType();
            Integer maxConsequenceSeqNo = ActionUtils.parseInt(request.getParameter("MaxConsequenceSeqNo"));
            if (ConsequenceType.APPROVER.equals(ct)) {
                request.setAttribute("X_YESNOLIST", PersistentEnum.getEnumList(YesNo.class));
                request.setAttribute("X_MAXSEQ", maxConsequenceSeqNo);
            }
        }
    }
}
