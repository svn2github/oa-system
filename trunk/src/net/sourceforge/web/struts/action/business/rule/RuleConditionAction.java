/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.web.struts.action.business.rule;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import net.sourceforge.model.admin.Site;
import net.sourceforge.model.business.rule.Rule;
import net.sourceforge.model.metadata.RuleType;
import net.sourceforge.service.admin.SiteManager;
import net.sourceforge.service.business.rule.RuleManager;
import net.sourceforge.web.struts.action.ServiceLocator;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.action.ActionUtils;
import com.shcnc.struts.form.BeanForm;

/**
 * 操作RuleCondition的Action
 * @author nicebean
 * @version 1.0 (2005-11-14)
 */
public class RuleConditionAction extends BaseRuleConditionAction {
    /**
     * 查询Capex Approval Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward listCapexApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return list(mapping, request, response, RuleType.CAPEX_APPROVAL_RULES);
    }
    
    /**
     * 新增Capex Approval Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newCapexApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return newObject(mapping, request, RuleType.CAPEX_APPROVAL_RULES, null);
    }
    
    /**
     * 新增Capex Approval Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newCapexApprovalWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Site site = prepareWebDragAndDraw(request);
        return newObject(mapping, request, RuleType.CAPEX_APPROVAL_RULES, site);
    }

    /**
     * 插入新增的Capex Approval Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insertCapexApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return insert(mapping, (BeanForm)form, request, RuleType.CAPEX_APPROVAL_RULES, null);
    }
    
    /**
     * 插入新增的Capex Approval Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insertCapexApprovalWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Site site = prepareWebDragAndDraw(request);
        return insert(mapping, (BeanForm)form, request, RuleType.CAPEX_APPROVAL_RULES, site);
    }

    /**
     * 修改Capex Approval Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editCapexApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return edit(mapping, request, RuleType.CAPEX_APPROVAL_RULES, null);
    }
    
    /**
     * 修改Capex Approval Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editCapexApprovalWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Site site = prepareWebDragAndDraw(request);
        return edit(mapping, request, RuleType.CAPEX_APPROVAL_RULES, site);
    }

    /**
     * 保存修改的Capex Approval Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward updateCapexApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return update(mapping, (BeanForm)form, request, RuleType.CAPEX_APPROVAL_RULES, null);
    }
    
    /**
     * 保存修改的Capex Approval Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward updateCapexApprovalWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Site site = prepareWebDragAndDraw(request);
        return update(mapping, (BeanForm)form, request, RuleType.CAPEX_APPROVAL_RULES, site);
    }

    /**
     * 删除Capex Approval Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward deleteCapexApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return delete(mapping, request, RuleType.CAPEX_APPROVAL_RULES, null);
    }

    /**
     * 查询PR Approval Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward listPRApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return list(mapping, request, response, RuleType.PR_APPROVAL_RULES);
    }
    
    /**
     * 新增PR Approval Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newPRApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return newObject(mapping, request, RuleType.PR_APPROVAL_RULES, null);
    }
    
    /**
     * 新增PR Approval Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newPRApprovalWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Site site = prepareWebDragAndDraw(request);
        return newObject(mapping, request, RuleType.PR_APPROVAL_RULES, site);
    }

    /**
     * 插入新增的PR Approval Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insertPRApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return insert(mapping, (BeanForm)form, request, RuleType.PR_APPROVAL_RULES, null);
    }
    
    /**
     * 插入新增的PR Approval Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insertPRApprovalWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Site site = prepareWebDragAndDraw(request);
        return insert(mapping, (BeanForm)form, request, RuleType.PR_APPROVAL_RULES, site);
    }

    /**
     * 修改PR Approval Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editPRApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return edit(mapping, request, RuleType.PR_APPROVAL_RULES, null);
    }
    
    /**
     * 修改PR Approval Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editPRApprovalWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Site site = prepareWebDragAndDraw(request);
        return edit(mapping, request, RuleType.PR_APPROVAL_RULES, site);
    }

    /**
     * 保存修改的PR Approval Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward updatePRApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return update(mapping, (BeanForm)form, request, RuleType.PR_APPROVAL_RULES, null);
    }
    
    /**
     * 保存修改的PR Approval Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward updatePRApprovalWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Site site = prepareWebDragAndDraw(request);
        return update(mapping, (BeanForm)form, request, RuleType.PR_APPROVAL_RULES, site);
    }

    /**
     * 删除PR Approval Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward deletePRApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return delete(mapping, request, RuleType.PR_APPROVAL_RULES, null);
    }

    /**
     * 查询Purchasing Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward listPurchasing(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return list(mapping, request, response, RuleType.PURCHASING_RULES);
    }
    
    /**
     * 新增Purchasing Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newPurchasing(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return newObject(mapping, request, RuleType.PURCHASING_RULES, null);
    }
    
    /**
     * 新增Purchasing Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newPurchasingWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Site site = prepareWebDragAndDraw(request);
        return newObject(mapping, request, RuleType.PURCHASING_RULES, site);
    }

    /**
     * 插入新增的Purchasing Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insertPurchasing(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return insert(mapping, (BeanForm)form, request, RuleType.PURCHASING_RULES, null);
    }
    
    /**
     * 插入新增的Purchasing Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insertPurchasingWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Site site = prepareWebDragAndDraw(request);
        return insert(mapping, (BeanForm)form, request, RuleType.PURCHASING_RULES, site);
    }

    /**
     * 修改Purchasing Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editPurchasing(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return edit(mapping, request, RuleType.PURCHASING_RULES, null);
    }
    
    /**
     * 修改Purchasing Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editPurchasingWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Site site = prepareWebDragAndDraw(request);
        return edit(mapping, request, RuleType.PURCHASING_RULES, site);
    }

    /**
     * 保存修改的Purchasing Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward updatePurchasing(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return update(mapping, (BeanForm)form, request, RuleType.PURCHASING_RULES, null);
    }
    
    /**
     * 保存修改的Purchasing Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward updatePurchasingWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Site site = prepareWebDragAndDraw(request);
        return update(mapping, (BeanForm)form, request, RuleType.PURCHASING_RULES, site);
    }

    /**
     * 删除Purchasing Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward deletePurchasing(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return delete(mapping, request, RuleType.PURCHASING_RULES, null);
    }

    /**
     * 查询Purchasing Price Control Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward listPurchasingPriceControl(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return list(mapping, request, response, RuleType.PURCHASING_PRICE_CONTROL_RULES);
    }
    
    /**
     * 新增Purchasing Price Control Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newPurchasingPriceControl(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return newObject(mapping, request, RuleType.PURCHASING_PRICE_CONTROL_RULES, null);
    }
    
    /**
     * 新增Purchasing Price Control Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newPurchasingPriceControlWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Site site = prepareWebDragAndDraw(request);
        return newObject(mapping, request, RuleType.PURCHASING_PRICE_CONTROL_RULES, site);
    }

    /**
     * 插入新增的Purchasing Price Control Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insertPurchasingPriceControl(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return insert(mapping, (BeanForm)form, request, RuleType.PURCHASING_PRICE_CONTROL_RULES, null);
    }
    
    /**
     * 插入新增的Purchasing Price Control Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insertPurchasingPriceControlWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Site site = prepareWebDragAndDraw(request);
        return insert(mapping, (BeanForm)form, request, RuleType.PURCHASING_PRICE_CONTROL_RULES, site);
    }

    /**
     * 修改Purchasing Price Control Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editPurchasingPriceControl(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return edit(mapping, request, RuleType.PURCHASING_PRICE_CONTROL_RULES, null);
    }
    
    /**
     * 修改Purchasing Price Control Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editPurchasingPriceControlWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Site site = prepareWebDragAndDraw(request);
        return edit(mapping, request, RuleType.PURCHASING_PRICE_CONTROL_RULES, site);
    }

    /**
     * 保存修改的Purchasing Price Control Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward updatePurchasingPriceControl(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return update(mapping, (BeanForm)form, request, RuleType.PURCHASING_PRICE_CONTROL_RULES, null);
    }
    
    /**
     * 保存修改的Purchasing Price Control Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward updatePurchasingPriceControlWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Site site = prepareWebDragAndDraw(request);
        return update(mapping, (BeanForm)form, request, RuleType.PURCHASING_PRICE_CONTROL_RULES, site);
    }

    /**
     * 删除Purchasing Price Control Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward deletePurchasingPriceControl(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return delete(mapping, request, RuleType.PURCHASING_PRICE_CONTROL_RULES, null);
    }

    /**
     * 查询PO Approval Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward listPOApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return list(mapping, request, response, RuleType.PO_APPROVAL_RULES);
    }
    
    /**
     * 新增PO Approval Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newPOApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return newObject(mapping, request, RuleType.PO_APPROVAL_RULES, null);
    }
    
    /**
     * 新增PO Approval Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newPOApprovalWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Site site = prepareWebDragAndDraw(request);
        return newObject(mapping, request, RuleType.PO_APPROVAL_RULES, site);
    }

    /**
     * 插入新增的PO Approval Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insertPOApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return insert(mapping, (BeanForm)form, request, RuleType.PO_APPROVAL_RULES, null);
    }
    
    /**
     * 插入新增的PO Approval Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insertPOApprovalWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Site site = prepareWebDragAndDraw(request);
        return insert(mapping, (BeanForm)form, request, RuleType.PO_APPROVAL_RULES, site);
    }

    /**
     * 修改PO Approval Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editPOApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return edit(mapping, request, RuleType.PO_APPROVAL_RULES, null);
    }
    
    /**
     * 修改PO Approval Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editPOApprovalWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Site site = prepareWebDragAndDraw(request);
        return edit(mapping, request, RuleType.PO_APPROVAL_RULES, site);
    }

    /**
     * 保存修改的PO Approval Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward updatePOApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return update(mapping, (BeanForm)form, request, RuleType.PO_APPROVAL_RULES, null);
    }
    
    /**
     * 保存修改的PO Approval Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward updatePOApprovalWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Site site = prepareWebDragAndDraw(request);
        return update(mapping, (BeanForm)form, request, RuleType.PO_APPROVAL_RULES, site);
    }

    /**
     * 删除PO Approval Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward deletePOApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return delete(mapping, request, RuleType.PO_APPROVAL_RULES, null);
    }

    /**
     * 查询Expense Approval Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward listExpenseApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return list(mapping, request, response, RuleType.EXPENSE_APPROVAL_RULES);
    }
    
    /**
     * 新增Expense Approval Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newExpenseApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return newObject(mapping, request, RuleType.EXPENSE_APPROVAL_RULES, null);
    }
    
    /**
     * 新增Expense Approval Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newExpenseApprovalWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Site site = prepareWebDragAndDraw(request);
        return newObject(mapping, request, RuleType.EXPENSE_APPROVAL_RULES, site);
    }
    
    /**
     * 插入新增的Expense Approval Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insertExpenseApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return insert(mapping, (BeanForm)form, request, RuleType.EXPENSE_APPROVAL_RULES, null);
    }
    
    /**
     * 插入新增的Expense Approval Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insertExpenseApprovalWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Site site = prepareWebDragAndDraw(request);
        return insert(mapping, (BeanForm)form, request, RuleType.EXPENSE_APPROVAL_RULES, site);
    }

    /**
     * 修改Expense Approval Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editExpenseApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return edit(mapping, request, RuleType.EXPENSE_APPROVAL_RULES, null);
    }
    
    /**
     * 修改Expense Approval Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editExpenseApprovalWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Site site = prepareWebDragAndDraw(request);
        return edit(mapping, request, RuleType.EXPENSE_APPROVAL_RULES, site);
    }       

    /**
     * 保存修改的Expense Approval Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward updateExpenseApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return update(mapping, (BeanForm)form, request, RuleType.EXPENSE_APPROVAL_RULES, null);
    }
    
    /**
     * 保存修改的Expense Approval Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward updateExpenseApprovalWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Site site = prepareWebDragAndDraw(request);
        return update(mapping, (BeanForm)form, request, RuleType.EXPENSE_APPROVAL_RULES, site);
    }

    /**
     * 删除Expense Approval Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward deleteExpenseApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return delete(mapping, request, RuleType.EXPENSE_APPROVAL_RULES, null);
    }

    /**
     * 查询Expense Claim Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward listExpenseClaim(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return list(mapping, request, response, RuleType.EXPENSE_CLAIM_RULES);
    }
    
    /**
     * 新增Expense Claim Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newExpenseClaim(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return newObject(mapping, request, RuleType.EXPENSE_CLAIM_RULES, null);
    }
    
    /**
     * 新增Expense Claim Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newExpenseClaimWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Site site = prepareWebDragAndDraw(request);
        return newObject(mapping, request, RuleType.EXPENSE_CLAIM_RULES, site);
    }

    /**
     * 插入新增的Expense Claim Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insertExpenseClaim(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return insert(mapping, (BeanForm)form, request, RuleType.EXPENSE_CLAIM_RULES, null);
    }
    
    /**
     * 插入新增的Expense Claim Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insertExpenseClaimWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Site site = prepareWebDragAndDraw(request);
        return insert(mapping, (BeanForm)form, request, RuleType.EXPENSE_CLAIM_RULES, site);
    }

    /**
     * 修改Expense Claim Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editExpenseClaim(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return edit(mapping, request, RuleType.EXPENSE_CLAIM_RULES, null);
    }
    
    /**
     * 修改Expense Claim Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editExpenseClaimWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Site site = prepareWebDragAndDraw(request);
        return edit(mapping, request, RuleType.EXPENSE_CLAIM_RULES, site);
    }

    /**
     * 保存修改的Expense Claim Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward updateExpenseClaim(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return update(mapping, (BeanForm)form, request, RuleType.EXPENSE_CLAIM_RULES, null);
    }
    
    /**
     * 保存修改的Expense Claim Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward updateExpenseClaimWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Site site = prepareWebDragAndDraw(request);
        return update(mapping, (BeanForm)form, request, RuleType.EXPENSE_CLAIM_RULES, site);
    }

    /**
     * 删除Expense Claim Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward deleteExpenseClaim(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return delete(mapping, request, RuleType.EXPENSE_CLAIM_RULES, null);
    }

    /**
     * 查询Travel Approval Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward listTravelApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return list(mapping, request, response, RuleType.TRAVEL_APPROVAL_RULES);
    }
    
    /**
     * 新增Travel Approval Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newTravelApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return newObject(mapping, request, RuleType.TRAVEL_APPROVAL_RULES, null);
    }
    
    /**
     * 新增Travel Approval Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newTravelApprovalWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Site site = prepareWebDragAndDraw(request);
        return newObject(mapping, request, RuleType.TRAVEL_APPROVAL_RULES, site);
    }

    /**
     * 插入新增的Travel Approval Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insertTravelApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return insert(mapping, (BeanForm)form, request, RuleType.TRAVEL_APPROVAL_RULES, null);
    }
    
    /**
     * 插入新增的Travel Approval Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insertTravelApprovalWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Site site = prepareWebDragAndDraw(request);
        return insert(mapping, (BeanForm)form, request, RuleType.TRAVEL_APPROVAL_RULES, site);
    }

    /**
     * 修改Travel Approval Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editTravelApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return edit(mapping, request, RuleType.TRAVEL_APPROVAL_RULES, null);
    }
    
    /**
     * 修改Travel Approval Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editTravelApprovalWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Site site = prepareWebDragAndDraw(request);
        return edit(mapping, request, RuleType.TRAVEL_APPROVAL_RULES, site);
    }

    /**
     * 保存修改的Travel Approval Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward updateTravelApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return update(mapping, (BeanForm)form, request, RuleType.TRAVEL_APPROVAL_RULES, null);
    }
    
    /**
     * 保存修改的Travel Approval Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward updateTravelApprovalWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Site site = prepareWebDragAndDraw(request);
        return update(mapping, (BeanForm)form, request, RuleType.TRAVEL_APPROVAL_RULES, site);
    }

    /**
     * 删除Travel Approval Rule Condition
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward deleteTravelApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return delete(mapping, request, RuleType.TRAVEL_APPROVAL_RULES, null);
    }
    
    private Site prepareWebDragAndDraw(HttpServletRequest request) {
        SiteManager sm = ServiceLocator.getSiteManager(request);
        Integer siteId = ActionUtils.parseInt(request.getParameter("site_id"));
        Site site = sm.getSite(siteId);
        if (site == null) {
            throw new ActionException("site.notFound", siteId);
        }
        request.setAttribute(FlowAction.WEB_DRAG_AND_DRAW, FlowAction.WEB_DRAG_AND_DRAW);
        
        return site;
    }

}
