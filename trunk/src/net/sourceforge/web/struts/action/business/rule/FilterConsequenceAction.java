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

import net.sourceforge.model.metadata.RuleType;
import net.sourceforge.web.struts.form.admin.UserQueryForm;
import com.shcnc.struts.form.BeanForm;

/**
 * 操作FilterConsequence的Action
 * @author nicebean
 * @version 1.0 (2005-11-14)
 */
public class FilterConsequenceAction extends BaseRuleConsequenceAction {
    /**
     * 新增Yearly Budget Filter Consequence
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newYearlyBudget(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return newObject(mapping, request, RuleType.YEARLY_BUDGET_FILTERS, null);
    }

    /**
     * 插入新增的Yearly Budget Filter Consequence
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insertYearlyBudget(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return insert(mapping, (BeanForm)form, request, RuleType.YEARLY_BUDGET_FILTERS, null);
    }

    /**
     * 修改Yearly Budget Filter Consequence
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editYearlyBudget(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return edit(mapping, request, RuleType.YEARLY_BUDGET_FILTERS, null);
    }

    /**
     * 保存修改的Yearly Budget Filter Consequence
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward updateYearlyBudget(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return update(mapping, (BeanForm)form, request, RuleType.YEARLY_BUDGET_FILTERS, null);
    }

    /**
     * 删除Yearly Budget Filter Consequence
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward deleteYearlyBudget(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return delete(mapping, request, RuleType.YEARLY_BUDGET_FILTERS, null);
    }

    /**
     * 选择Yearly Budget Filter Consequence User
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward selectYearlyBudgetUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return selectUser(mapping, (UserQueryForm) form, request, RuleType.YEARLY_BUDGET_FILTERS);
    }

    /**
     * 新增Capex Filter Consequence
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newCapex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return newObject(mapping, request, RuleType.CAPEX_FILTERS, null);
    }

    /**
     * 插入新增的Capex Filter Consequence
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insertCapex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return insert(mapping, (BeanForm)form, request, RuleType.CAPEX_FILTERS, null);
    }

    /**
     * 修改Capex Filter Consequence
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editCapex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return edit(mapping, request, RuleType.CAPEX_FILTERS, null);
    }

    /**
     * 保存修改的Capex Filter Consequence
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward updateCapex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return update(mapping, (BeanForm)form, request, RuleType.CAPEX_FILTERS, null);
    }

    /**
     * 删除Capex Filter Consequence
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward deleteCapex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return delete(mapping, request, RuleType.CAPEX_FILTERS, null);
    }

    /**
     * 选择Capex Filter Consequence User
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward selectCapexUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return selectUser(mapping, (UserQueryForm) form, request, RuleType.CAPEX_FILTERS);
    }

    /**
     * 新增Purchase Request Filter Consequence
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newPurchaseRequest(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return newObject(mapping, request, RuleType.PURCHASE_REQUEST_FILTERS, null);
    }

    /**
     * 插入新增的Purchase Request Filter Consequence
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insertPurchaseRequest(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return insert(mapping, (BeanForm)form, request, RuleType.PURCHASE_REQUEST_FILTERS, null);
    }

    /**
     * 修改Purchase Request Filter Consequence
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editPurchaseRequest(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return edit(mapping, request, RuleType.PURCHASE_REQUEST_FILTERS, null);
    }

    /**
     * 保存修改的Purchase Request Filter Consequence
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward updatePurchaseRequest(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return update(mapping, (BeanForm)form, request, RuleType.PURCHASE_REQUEST_FILTERS, null);
    }

    /**
     * 删除Purchase Request Filter Consequence
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward deletePurchaseRequest(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return delete(mapping, request, RuleType.PURCHASE_REQUEST_FILTERS, null);
    }

    /**
     * 选择Purchase Request Filter Consequence User
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward selectPurchaseRequestUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return selectUser(mapping, (UserQueryForm) form, request, RuleType.PURCHASE_REQUEST_FILTERS);
    }

    /**
     * 新增Purchase Order Filter Consequence
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newPurchaseOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return newObject(mapping, request, RuleType.PURCHASE_ORDER_FILTERS, null);
    }

    /**
     * 插入新增的Purchase Order Filter Consequence
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insertPurchaseOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return insert(mapping, (BeanForm)form, request, RuleType.PURCHASE_ORDER_FILTERS, null);
    }

    /**
     * 修改Purchase Order Filter Consequence
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editPurchaseOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return edit(mapping, request, RuleType.PURCHASE_ORDER_FILTERS, null);
    }

    /**
     * 保存修改的Purchase Order Filter Consequence
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward updatePurchaseOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return update(mapping, (BeanForm)form, request, RuleType.PURCHASE_ORDER_FILTERS, null);
    }

    /**
     * 删除Purchase Order Filter Consequence
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward deletePurchaseOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return delete(mapping, request, RuleType.PURCHASE_ORDER_FILTERS, null);
    }

    /**
     * 选择Purchase Order Filter Consequence User
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward selectPurchaseOrderUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return selectUser(mapping, (UserQueryForm) form, request, RuleType.PURCHASE_ORDER_FILTERS);
    }

    /**
     * 新增Expense Filter Consequence
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newExpense(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return newObject(mapping, request, RuleType.EXPENSE_FILTERS, null);
    }

    /**
     * 插入新增的Expense Filter Consequence
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insertExpense(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return insert(mapping, (BeanForm)form, request, RuleType.EXPENSE_FILTERS, null);
    }

    /**
     * 修改Expense Filter Consequence
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editExpense(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return edit(mapping, request, RuleType.EXPENSE_FILTERS, null);
    }

    /**
     * 保存修改的Expense Filter Consequence
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward updateExpense(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return update(mapping, (BeanForm)form, request, RuleType.EXPENSE_FILTERS, null);
    }

    /**
     * 删除Expense Filter Consequence
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward deleteExpense(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return delete(mapping, request, RuleType.EXPENSE_FILTERS, null);
    }

    /**
     * 选择Expense Filter Consequence User
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward selectExpenseUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return selectUser(mapping, (UserQueryForm) form, request, RuleType.EXPENSE_FILTERS);
    }

    /**
     * 新增Travel Application Filter Consequence
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newTravelApplication(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return newObject(mapping, request, RuleType.TRAVEL_APPLICATION_FILTERS, null);
    }

    /**
     * 插入新增的Travel Application Filter Consequence
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insertTravelApplication(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return insert(mapping, (BeanForm)form, request, RuleType.TRAVEL_APPLICATION_FILTERS, null);
    }

    /**
     * 修改Travel Application Filter Consequence
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editTravelApplication(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return edit(mapping, request, RuleType.TRAVEL_APPLICATION_FILTERS, null);
    }

    /**
     * 保存修改的Travel Application Filter Consequence
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward updateTravelApplication(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return update(mapping, (BeanForm)form, request, RuleType.TRAVEL_APPLICATION_FILTERS, null);
    }

    /**
     * 删除Travel Application Filter Consequence
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward deleteTravelApplication(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return delete(mapping, request, RuleType.TRAVEL_APPLICATION_FILTERS, null);
    }

    /**
     * 选择Travel Application Filter Consequence User
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward selectTravelApplicationUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return selectUser(mapping, (UserQueryForm) form, request, RuleType.TRAVEL_APPLICATION_FILTERS);
    }

}
