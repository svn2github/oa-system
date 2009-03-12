/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.web.struts.action.business.rule;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import net.sourceforge.model.admin.Department;
import net.sourceforge.model.admin.ExpenseCategory;
import net.sourceforge.model.admin.PurchaseCategory;
import net.sourceforge.model.admin.PurchaseSubCategory;
import net.sourceforge.model.admin.Site;
import net.sourceforge.model.business.rule.Rule;
import net.sourceforge.model.business.rule.RuleCondition;
import net.sourceforge.model.metadata.BudgetType;
import net.sourceforge.model.metadata.ConditionCompareType;
import net.sourceforge.model.metadata.ConditionType;
import net.sourceforge.model.metadata.RuleType;
import net.sourceforge.model.metadata.TravelType;
import net.sourceforge.model.metadata.TravellingMode;
import net.sourceforge.model.metadata.YesNo;
import net.sourceforge.service.business.rule.RuleManager;
import net.sourceforge.web.struts.action.BaseAction;
import net.sourceforge.web.struts.action.ServiceLocator;
import com.shcnc.hibernate.PersistentEnum;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.action.ActionUtils;
import com.shcnc.struts.form.BeanForm;

/**
 * 操作RuleCondition和FilterCondition的共用Action
 * @author nicebean
 * @version 1.0 (2005-11-14)
 */
public class BaseRuleConditionAction extends BaseAction {
    protected ActionForward list(ActionMapping mapping, HttpServletRequest request, HttpServletResponse response, RuleType type) throws Exception {
        RuleManager rm = ServiceLocator.getRuleManager(request);
        Integer id = ActionUtils.parseInt(request.getParameter("id"));
        Rule r = rm.getRule(id, true);
        if (r == null || !type.equals(r.getType())) throw new ActionException("rule." + type.getPrefixUrl() + ".notFound", id);
        checkSite(r.getSite(), request);
        request.setAttribute("X_RULE", r);
        for (Iterator itor = r.getConditions().iterator(); itor.hasNext(); ) {
            RuleCondition rc = (RuleCondition) itor.next();
            rc.setDisplayValue(getDisplayValue(rc.getType(), rc.getValue(), request));
        }
        return mapping.findForward("pageRuleConditionList");
    }

    protected ActionForward newObject(ActionMapping mapping, HttpServletRequest request, RuleType type, Site site) throws Exception {
        Integer typeEnumCode = ActionUtils.parseInt(request.getParameter("type"));
        request.setAttribute("X_RULETYPE", type);
        if (typeEnumCode == null) {
            Set usedConditions = new HashSet();
            RuleManager rm = ServiceLocator.getRuleManager(request);
            Integer ruleId = ActionUtils.parseInt(request.getParameter("rule_id"));
            if (ruleId != null) {
                Rule r = rm.getRule(ruleId, true);
                if (r == null || !type.equals(r.getType())) throw new ActionException("rule." + type.getPrefixUrl() + ".notFound", ruleId);
                checkSite(r.getSite(), request);                
                for (Iterator itor = r.getConditions().iterator(); itor.hasNext(); ) {
                    usedConditions.add(((RuleCondition)itor.next()).getType());
                }
            } else {
                checkSite(site, request);
            }
            Set availiableConditions = new HashSet();
            availiableConditions.addAll(type.getSupportedConditions());
            availiableConditions.removeAll(usedConditions);
            if (availiableConditions.isEmpty()) throw new ActionException("ruleCondition.noAvailiableCondition");           
            request.setAttribute("X_AVAILIABLECONDITIONS", availiableConditions);
            return mapping.findForward("pageRuleConditionChooseType");
        }
        
        RuleCondition rc = prepareNewRuleCondition(type, typeEnumCode, request, site);
        BeanForm ruleConditionForm = (BeanForm) getForm("/insert" + type.getPrefixUrl() + "RuleCondition", request);
        if (!isBack(request)) {
            ruleConditionForm.populateToForm(rc);
        }

        request.setAttribute("X_RULECONDITION", rc);
        prepareData(rc, ruleConditionForm, request, site);
        return mapping.findForward("pageRuleConditionNew" + rc.getType().getTypeString());
    }

    protected ActionForward insert(ActionMapping mapping, BeanForm ruleConditionForm, HttpServletRequest request, RuleType type, Site site) throws Exception {
        Integer typeEnumCode = ActionUtils.parseInt(request.getParameter("type"));
        RuleCondition rc = prepareNewRuleCondition(type, typeEnumCode, request, site);
        updateAndCheckRuleCondition(rc, request, ruleConditionForm, type);
        if (rc.getRule() != null) {
            RuleManager rm = ServiceLocator.getRuleManager(request);
            rc = rm.saveRuleCondition(rc);
        }
        rc.setDisplayValue(getDisplayValue(rc.getType(), rc.getValue(), request));
        request.setAttribute("X_OBJECT", rc);
        request.setAttribute("X_ROWPAGE", "ruleCondition/conditionRow.jsp");
        return mapping.findForward("successRuleConditionNew");
    }
    
    protected ActionForward edit(ActionMapping mapping, HttpServletRequest request, RuleType type, Site site) throws Exception {
        request.setAttribute("X_RULETYPE", type);
        RuleCondition rc = getAndCheckRuleCondition(type, request, site);        
        BeanForm ruleConditionForm = (BeanForm) getForm("/update" + type.getPrefixUrl() + "RuleCondition", request);
        if (!isBack(request)) {
            ruleConditionForm.populateToForm(rc);
        }

        request.setAttribute("X_RULECONDITION", rc);
        prepareData(rc, ruleConditionForm, request, site);
        return mapping.findForward("pageRuleConditionEdit" + rc.getType().getTypeString());
    }

    protected ActionForward update(ActionMapping mapping, BeanForm ruleConditionForm, HttpServletRequest request, RuleType type, Site site) throws Exception {
        RuleCondition rc = getAndCheckRuleCondition(type, request, site);
        updateAndCheckRuleCondition(rc, request, ruleConditionForm, type);
        if (site == null) {
            RuleManager rm = ServiceLocator.getRuleManager(request);            
            rc = rm.updateRuleCondition(rc);
        }
        rc.setDisplayValue(getDisplayValue(rc.getType(), rc.getValue(), request));
        request.setAttribute("X_OBJECT", rc);
        request.setAttribute("X_ROWPAGE", "ruleCondition/conditionRow.jsp");
        
        return mapping.findForward("successRuleConditionEdit");
    }
    
    protected ActionForward delete(ActionMapping mapping, HttpServletRequest request, RuleType type, Site site) throws Exception {
        RuleCondition rc = getAndCheckRuleCondition(type, request, site);
        RuleManager rm = ServiceLocator.getRuleManager(request);
        
        rm.removeRuleCondition(rc);
        return mapping.findForward("successRuleConditionDelete");
    }

    private String getDisplayValue(ConditionType ct, String value, HttpServletRequest request) throws Exception {
        if (value == null) return null;
        if (ConditionType.DEPARTMENT.equals(ct)) {
            Integer id = ActionUtils.parseInt(value);
            if (id == null) return null;
            Department d = ServiceLocator.getDepartmentManager(request).getDepartment(id);
            if (d == null) return null;
            return d.getName();
        }
        if (ConditionType.EXPENSE_CATEGORY.equals(ct)) {
            Integer id = ActionUtils.parseInt(value);
            if (id == null) return null;
            ExpenseCategory ec = ServiceLocator.getExpenseCategoryManager(request).getExpenseCategory(id);
            if (ec == null) return null;
            return ec.getDescription();
        }
        if (ConditionType.PURCHASE_CATEGORY.equals(ct)) {
            Integer id = ActionUtils.parseInt(value.substring(1));
            if (id == null) return null;
            if (value.charAt(0) == 'p') {
                PurchaseCategory pc = ServiceLocator.getPurchaseCategoryManager(request).getPurchaseCategory(id);
                if (pc == null) return null;
                return pc.getDescription();
            } else {
                PurchaseSubCategory psc = ServiceLocator.getPurchaseSubCategoryManager(request).getPurchaseSubCategory(id);
                if (psc == null) return null;
                return psc.getPurchaseCategory().getDescription() + " \\ " + psc.getDescription();
            }
        }
        if (ConditionType.TRAVEL_FROM.equals(ct) || ConditionType.TRAVEL_TO.equals(ct)) {
            Integer id = ActionUtils.parseInt(value);
            if (id == null) return null;
            TravelType tt = (TravelType)PersistentEnum.fromEnumCode(TravelType.class, id);
            if (tt == null) return null;
            return this.getLocaleShortDescription(tt, request);
        }
        if (ConditionType.TRAVEL_MODE.equals(ct)) {
            Integer id = ActionUtils.parseInt(value);
            if (id == null) return null;
            TravellingMode tm = (TravellingMode)PersistentEnum.fromEnumCode(TravellingMode.class, id);
            if (tm == null) return null;
            return this.getLocaleShortDescription(tm, request);
        }
        if (ConditionType.BUDGET_TYPE.equals(ct)) {
            Integer id = ActionUtils.parseInt(value);
            if (id == null) return null;
            BudgetType bt = (BudgetType)PersistentEnum.fromEnumCode(BudgetType.class, id);
            if (bt == null) return null;
            return this.getLocaleShortDescription(bt, request);
        }
        if (ConditionType.WITH_BUDGET.equals(ct)) {
            Integer id = ActionUtils.parseInt(value);
            if (id == null) return null;
            YesNo yn = (YesNo)PersistentEnum.fromEnumCode(YesNo.class, id);
            if (yn == null) return null;
            return this.getLocaleShortDescription(yn, request);
        }
        return value;
    }
    
    private RuleCondition prepareNewRuleCondition(RuleType type, Integer typeEnumCode, HttpServletRequest request, Site site) throws Exception {
        ConditionType ct = (ConditionType) PersistentEnum.fromEnumCode(ConditionType.class, typeEnumCode);
        if (ct == null) throw new ActionException("ruleCondition.type.notSupported", typeEnumCode);

        Integer ruleId = ActionUtils.parseInt(request.getParameter("rule_id"));
        if (ruleId != null) {
            RuleManager rm = ServiceLocator.getRuleManager(request);
            RuleCondition rc = rm.getRuleCondition(ruleId, ct);
            if (rc != null) throw new ActionException("ruleCondition.type.dupliate");        
            
            Rule r = rm.getRule(ruleId);
            if (r == null || !type.equals(r.getType())) throw new ActionException("rule." + type.getPrefixUrl() + ".notFound", ruleId);
            checkSite(r.getSite(), request);
    
            rc =  new RuleCondition(r, ct);
            return rc;
        } else {
            checkSite(site, request);
            RuleCondition rc = new RuleCondition();
            rc.setType(ct);
            return rc;
        }
    }
    
    private RuleCondition getAndCheckRuleCondition(RuleType type, HttpServletRequest request, Site site) throws Exception {
        
        Integer typeEnumCode = ActionUtils.parseInt(request.getParameter("type"));
        ConditionType ct = (ConditionType) PersistentEnum.fromEnumCode(ConditionType.class, typeEnumCode);
        if (ct == null) throw new ActionException("ruleCondition.type.notSupported", typeEnumCode);
        
        Integer ruleId = ActionUtils.parseInt(request.getParameter("rule_id"));
        if (ruleId != null) {
            RuleManager rm = ServiceLocator.getRuleManager(request);
            RuleCondition rc = rm.getRuleCondition(ruleId, ct);
            if (rc == null) throw new ActionException("ruleCondition.notFound", new Object[] { ruleId, typeEnumCode } );
            Rule r = rc.getRule();
            if (r ==null || !type.equals(r.getType())) throw new ActionException("rule." + type.getPrefixUrl() + ".notFound", ruleId);
            checkSite(r.getSite(), request);
            return rc;
        } else {
            Integer compareTypeEnumCode = ActionUtils.parseInt(request.getParameter("compareType"));
            ConditionCompareType cct = (ConditionCompareType) PersistentEnum.fromEnumCode(ConditionCompareType.class, compareTypeEnumCode);
            
            if (!ct.getSupportedCompareTypes().contains(cct)) {
                if (getCurrentUser(request).getLocale().equals("en")) {
                    throw new ActionException("ruleCondition.compareType.notSupportedForCondition", new Object[] { cct.getEngShortDescription(), ct.getEngShortDescription() } );
                }
                throw new ActionException("ruleCondition.compareType.notSupportedForCondition", new Object[] { cct.getChnShortDescription(), ct.getChnShortDescription() } );
            }
            
            String value = request.getParameter("value");
            
            RuleCondition rc = new RuleCondition();
            rc.setType(ct);
            rc.setCompareType(cct);
            rc.setValue(value);
            checkSite(site, request);
            return rc;
        }
    }
    
    private void updateAndCheckRuleCondition(RuleCondition rc, HttpServletRequest request, BeanForm ruleConditionForm, RuleType rt) {
        ruleConditionForm.populateToBean(rc);
        ConditionType ct = rc.getType();
        if (!rt.getSupportedConditions().contains(ct)) {
            if (getCurrentUser(request).getLocale().equals("en")) {
                throw new ActionException("ruleCondition.type.notSupportedForRule", new Object[] { ct.getEngShortDescription(), rt.getEngShortDescription() } );
            }
            throw new ActionException("ruleCondition.type.notSupportedForRule", new Object[] { ct.getChnShortDescription(), rt.getChnShortDescription() } );
        }
        if (!ct.getSupportedCompareTypes().contains(rc.getCompareType())) {
            if (getCurrentUser(request).getLocale().equals("en")) {
                throw new ActionException("ruleCondition.compareType.notSupportedForCondition", new Object[] { rc.getCompareType().getEngShortDescription(), ct.getEngShortDescription() } );
            }
            throw new ActionException("ruleCondition.compareType.notSupportedForCondition", new Object[] { rc.getCompareType().getChnShortDescription(), ct.getChnShortDescription() } );
        }
        String value = ruleConditionForm.getString("value");
        if (ConditionType.PURCHASE_CATEGORY.equals(ct)) {
            Integer intValue = ActionUtils.parseInt(value);
            if (intValue == null) {
                rc.setValue('p' + ruleConditionForm.getString("pvalue"));
            } else {
                rc.setValue('s' + value);
            }
        } else {
            rc.setValue(value);
        }
    }
    
    private void prepareData(RuleCondition rc, BeanForm ruleConditionForm, HttpServletRequest request, Site site) throws Exception {
        ConditionType ct = rc.getType();
        Set supportedCompareTypes = ct.getSupportedCompareTypes();
        if (supportedCompareTypes.size() == 1) {
            ruleConditionForm.set("compareType", ((ConditionCompareType)supportedCompareTypes.iterator().next()).getEnumCode().toString());
        } else {
            request.setAttribute("X_COMPARETYPELIST", supportedCompareTypes);
        }

        Site s = site;
        if (s == null) {
            s = rc.getRule().getSite();
        }
        if (ConditionType.DEPARTMENT.equals(ct)) {
            ServiceLocator.getDepartmentManager(request).fillDepartment(s, true);
            request.setAttribute("X_DEPARTMENTLIST", s.getDepartments());
            return;
        }
        if (ConditionType.EXPENSE_CATEGORY.equals(ct)) {
            request.setAttribute("X_EXPENSECATEGORYLIST", ServiceLocator.getExpenseCategoryManager(request).getEnabledExpenseCategoryOfSite(s));
            return;
        }
        if (ConditionType.PURCHASE_CATEGORY.equals(ct)) {
            String value = ruleConditionForm.getString("value");
            if (value != null && value.length() > 0) {
                if (value.charAt(0) == 'p') {
                    ruleConditionForm.set("pvalue", value.substring(1));
                    ruleConditionForm.set("value", null);
                } else {
                    Integer id = ActionUtils.parseInt(value.substring(1));
                    PurchaseSubCategory psc = ServiceLocator.getPurchaseSubCategoryManager(request).getPurchaseSubCategory(id);
                    if (psc != null) {
                        ruleConditionForm.set("pvalue", psc.getPurchaseCategory().getId().toString());
                        ruleConditionForm.set("value", psc.getId().toString());
                    }
                }
            }
            request.setAttribute("X_PURCHASECATEGORYLIST", ServiceLocator.getPurchaseCategoryManager(request).getEnabledPurchaseCategorySubCategoryOfSiteIncludeGlobal(s));
            return;
        }
        if (ConditionType.TRAVEL_FROM.equals(ct) || ConditionType.TRAVEL_TO.equals(ct)) {
            request.setAttribute("X_TRAVELTYPELIST", PersistentEnum.getEnumList(TravelType.class));
            return;
        }
        if (ConditionType.TRAVEL_MODE.equals(ct)) {
            request.setAttribute("X_TRAVELLINGMODELIST", PersistentEnum.getEnumList(TravellingMode.class));
            return;
        }
        if (ConditionType.BUDGET_TYPE.equals(ct)) {
            request.setAttribute("X_BUDGETTYPELIST", PersistentEnum.getEnumList(BudgetType.class));
            return;
        }
        if (ConditionType.WITH_BUDGET.equals(ct)) {
            request.setAttribute("X_YESNOLIST", PersistentEnum.getEnumList(YesNo.class));
            return;
        }
    }

}
