/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.web.struts.action.business.rule;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aof.model.metadata.RuleType;
import com.aof.web.struts.form.business.rule.RuleQueryForm;
import com.shcnc.struts.form.BeanForm;

/**
 * 操作Rule的Action
 * @author nicebean
 * @version 1.0 (2005-11-14)
 */
public class RuleAction extends BaseRuleAction {
    /**
     * 查询Capex Approval Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward listCapexApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return list(mapping, (RuleQueryForm) form, request, response, RuleType.CAPEX_APPROVAL_RULES);
    }

    /**
     * 新增Capex Approval Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newCapexApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return newObject(mapping, request, RuleType.CAPEX_APPROVAL_RULES);
    }

    /**
     * 插入新增的Capex Approval Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insertCapexApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return insert(mapping, (BeanForm) form, request, RuleType.CAPEX_APPROVAL_RULES);
    }

    /**
     * 修改Capex Approval Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editCapexApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return edit(mapping, request, RuleType.CAPEX_APPROVAL_RULES);
    }
    
    /**
     * 修改Capex Approval Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editCapexApprovalWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return newObjectWebDragAndDraw(mapping, request, RuleType.CAPEX_APPROVAL_RULES);
    }

    /**
     * 保存修改的Capex Approval Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward updateCapexApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return update(mapping, (BeanForm) form, request, RuleType.CAPEX_APPROVAL_RULES);
    }

    /**
     * 删除Capex Approval Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward deleteCapexApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return delete(mapping, request, RuleType.CAPEX_APPROVAL_RULES);
    }

    /**
     * 查询PR Approval Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward listPRApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return list(mapping, (RuleQueryForm) form, request, response, RuleType.PR_APPROVAL_RULES);
    }

    /**
     * 新增PR Approval Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newPRApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return newObject(mapping, request, RuleType.PR_APPROVAL_RULES);
    }

    /**
     * 插入新增的PR Approval Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insertPRApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return insert(mapping, (BeanForm) form, request, RuleType.PR_APPROVAL_RULES);
    }

    /**
     * 修改PR Approval Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editPRApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return edit(mapping, request, RuleType.PR_APPROVAL_RULES);
    }
    
    /**
     * 修改PR Approval Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editPRApprovalWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return newObjectWebDragAndDraw(mapping, request, RuleType.PR_APPROVAL_RULES);
    }

    /**
     * 保存修改的PR Approval Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward updatePRApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return update(mapping, (BeanForm) form, request, RuleType.PR_APPROVAL_RULES);
    }

    /**
     * 删除PR Approval Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward deletePRApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return delete(mapping, request, RuleType.PR_APPROVAL_RULES);
    }

    /**
     * 查询Purchasing Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward listPurchasing(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return list(mapping, (RuleQueryForm) form, request, response, RuleType.PURCHASING_RULES);
    }

    /**
     * 新增Purchasing Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newPurchasing(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return newObject(mapping, request, RuleType.PURCHASING_RULES);
    }

    /**
     * 插入新增的Purchasing Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insertPurchasing(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return insert(mapping, (BeanForm) form, request, RuleType.PURCHASING_RULES);
    }

    /**
     * 修改Purchasing Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editPurchasing(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return edit(mapping, request, RuleType.PURCHASING_RULES);
    }
    
    /**
     * 修改Purchasing Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editPurchasingWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return newObjectWebDragAndDraw(mapping, request, RuleType.PURCHASING_RULES);
    }

    /**
     * 保存修改的Purchasing Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward updatePurchasing(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return update(mapping, (BeanForm) form, request, RuleType.PURCHASING_RULES);
    }

    /**
     * 删除Purchasing Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward deletePurchasing(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return delete(mapping, request, RuleType.PURCHASING_RULES);
    }

    /**
     * 查询Purchasing Price Control Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward listPurchasingPriceControl(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return list(mapping, (RuleQueryForm) form, request, response, RuleType.PURCHASING_PRICE_CONTROL_RULES);
    }

    /**
     * 新增Purchasing Price Control Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newPurchasingPriceControl(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return newObject(mapping, request, RuleType.PURCHASING_PRICE_CONTROL_RULES);
    }

    /**
     * 插入新增的Purchasing Price Control Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insertPurchasingPriceControl(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return insert(mapping, (BeanForm) form, request, RuleType.PURCHASING_PRICE_CONTROL_RULES);
    }

    /**
     * 修改Purchasing Price Control Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editPurchasingPriceControl(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return edit(mapping, request, RuleType.PURCHASING_PRICE_CONTROL_RULES);
    }
    
    /**
     * 修改Purchasing Price Control Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editPurchasingPriceControlWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return newObjectWebDragAndDraw(mapping, request, RuleType.PURCHASING_PRICE_CONTROL_RULES);
    }

    /**
     * 保存修改的Purchasing Price Control Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward updatePurchasingPriceControl(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return update(mapping, (BeanForm) form, request, RuleType.PURCHASING_PRICE_CONTROL_RULES);
    }

    /**
     * 删除Purchasing Price Control Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward deletePurchasingPriceControl(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return delete(mapping, request, RuleType.PURCHASING_PRICE_CONTROL_RULES);
    }

    /**
     * 查询PO Approval Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward listPOApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return list(mapping, (RuleQueryForm) form, request, response, RuleType.PO_APPROVAL_RULES);
    }

    /**
     * 新增PO Approval Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newPOApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return newObject(mapping, request, RuleType.PO_APPROVAL_RULES);
    }

    /**
     * 插入新增的PO Approval Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insertPOApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return insert(mapping, (BeanForm) form, request, RuleType.PO_APPROVAL_RULES);
    }

    /**
     * 修改PO Approval Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editPOApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return edit(mapping, request, RuleType.PO_APPROVAL_RULES);
    }
    
    /**
     * 修改PO Approval Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editPOApprovalWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return newObjectWebDragAndDraw(mapping, request, RuleType.PO_APPROVAL_RULES);
    }

    /**
     * 保存修改的PO Approval Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward updatePOApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return update(mapping, (BeanForm) form, request, RuleType.PO_APPROVAL_RULES);
    }

    /**
     * 删除PO Approval Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward deletePOApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return delete(mapping, request, RuleType.PO_APPROVAL_RULES);
    }

    /**
     * 查询Expense Approval Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward listExpenseApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return list(mapping, (RuleQueryForm) form, request, response, RuleType.EXPENSE_APPROVAL_RULES);
    }

    /**
     * 新增Expense Approval Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newExpenseApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return newObject(mapping, request, RuleType.EXPENSE_APPROVAL_RULES);
    }

    /**
     * 插入新增的Expense Approval Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insertExpenseApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return insert(mapping, (BeanForm) form, request, RuleType.EXPENSE_APPROVAL_RULES);
    }

    /**
     * 修改Expense Approval Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editExpenseApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return edit(mapping, request, RuleType.EXPENSE_APPROVAL_RULES);
    }
    
    /**
     * 修改Expense Approval Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editExpenseApprovalWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return newObjectWebDragAndDraw(mapping, request, RuleType.EXPENSE_APPROVAL_RULES);
        //return edit(mapping, request, RuleType.EXPENSE_APPROVAL_RULES);
    }

    /**
     * 保存修改的Expense Approval Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward updateExpenseApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return update(mapping, (BeanForm) form, request, RuleType.EXPENSE_APPROVAL_RULES);
    }

    /**
     * 删除Expense Approval Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward deleteExpenseApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return delete(mapping, request, RuleType.EXPENSE_APPROVAL_RULES);
    }

    /**
     * 查询Expense Claim Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward listExpenseClaim(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return list(mapping, (RuleQueryForm) form, request, response, RuleType.EXPENSE_CLAIM_RULES);
    }

    /**
     * 新增Expense Claim Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newExpenseClaim(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return newObject(mapping, request, RuleType.EXPENSE_CLAIM_RULES);
    }

    /**
     * 插入新增的Expense Claim Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insertExpenseClaim(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return insert(mapping, (BeanForm) form, request, RuleType.EXPENSE_CLAIM_RULES);
    }

    /**
     * 修改Expense Claim Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editExpenseClaim(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return edit(mapping, request, RuleType.EXPENSE_CLAIM_RULES);
    }
    
    /**
     * 修改Expense Claim Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editExpenseClaimWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return newObjectWebDragAndDraw(mapping, request, RuleType.EXPENSE_CLAIM_RULES);
    }

    /**
     * 保存修改的Expense Claim Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward updateExpenseClaim(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return update(mapping, (BeanForm) form, request, RuleType.EXPENSE_CLAIM_RULES);
    }

    /**
     * 删除Expense Claim Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward deleteExpenseClaim(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return delete(mapping, request, RuleType.EXPENSE_CLAIM_RULES);
    }

    /**
     * 查询Travel Approval Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward listTravelApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return list(mapping, (RuleQueryForm) form, request, response, RuleType.TRAVEL_APPROVAL_RULES);
    }

    /**
     * 新增Travel Approval Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newTravelApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return newObject(mapping, request, RuleType.TRAVEL_APPROVAL_RULES);
    }

    /**
     * 插入新增的Travel Approval Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insertTravelApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return insert(mapping, (BeanForm) form, request, RuleType.TRAVEL_APPROVAL_RULES);
    }

    /**
     * 修改Travel Approval Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editTravelApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return edit(mapping, request, RuleType.TRAVEL_APPROVAL_RULES);
    }
    
    /**
     * 修改Travel Approval Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editTravelApprovalWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return newObjectWebDragAndDraw(mapping, request, RuleType.TRAVEL_APPROVAL_RULES);
    }

    /**
     * 保存修改的Travel Approval Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward updateTravelApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return update(mapping, (BeanForm) form, request, RuleType.TRAVEL_APPROVAL_RULES);
    }

    /**
     * 删除Travel Approval Rule
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward deleteTravelApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return delete(mapping, request, RuleType.TRAVEL_APPROVAL_RULES);
    }

}
