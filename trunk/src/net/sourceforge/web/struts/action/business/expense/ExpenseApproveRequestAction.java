/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.web.struts.action.business.expense;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import net.sourceforge.model.admin.User;
import net.sourceforge.model.business.expense.Expense;
import net.sourceforge.model.business.expense.ExpenseApproveRequest;
import net.sourceforge.model.business.expense.ExpenseApproveRequestListView;
import net.sourceforge.model.business.expense.query.ExpenseApproveRequestQueryCondition;
import net.sourceforge.model.business.expense.query.ExpenseApproveRequestQueryOrder;
import net.sourceforge.model.business.pr.YearlyBudget;
import net.sourceforge.model.metadata.ApproveStatus;
import net.sourceforge.model.metadata.ApproverDelegateType;
import net.sourceforge.model.metadata.YesNo;
import net.sourceforge.service.admin.UserManager;
import net.sourceforge.service.business.expense.ExpenseApproveRequestManager;
import net.sourceforge.service.business.expense.ExpenseManager;
import net.sourceforge.service.business.pr.YearlyBudgetManager;
import net.sourceforge.service.business.rule.ApproverDelegateManager;
import net.sourceforge.web.struts.action.ActionUtils;
import net.sourceforge.web.struts.action.ServiceLocator;
import net.sourceforge.web.struts.form.business.expense.ExpenseApproveRequestQueryForm;
import com.shcnc.hibernate.PersistentEnum;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.form.BeanForm;

/**
 * struts action class for domain model ExpenseApproveRequest
 * 
 * @author ych
 * @version 1.0 (Nov 18, 2005)
 */
public class ExpenseApproveRequestAction extends BaseExpenseAction {

    private static final String FAKE_DATE_BEGIN = "1900/01/01";

    private static final String FAKE_DATE_TO = "2099/01/01";

    /**
     * 查询ExpenseApproveRequest
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ExpenseApproveRequestManager earm = ServiceLocator.getExpenseApproveRequestManager(request);

        ExpenseApproveRequestQueryForm queryForm = (ExpenseApproveRequestQueryForm) form;

        User loginUser = getCurrentUser(request);
        Map conditions = constructQueryMap(queryForm, loginUser);

        if (queryForm.isFirstInit()) {
            queryForm.init(earm.getExpenseApproveRequestListCount(conditions));
        } else {
            queryForm.init();
        }

        List result = earm.getExpenseApproveRequestList(conditions, queryForm.getPageNoAsInt(), queryForm.getPageSizeAsInt(), ExpenseApproveRequestQueryOrder
                .getEnum(queryForm.getOrder()), queryForm.isDescend());
        List view = getApproveListView(result);
        request.setAttribute("X_RESULTLIST", view);
        putApproveStatusListToRequest(request);
        return mapping.findForward("page");
    }

    private List getApproveListView(List queryResult) {
        List viewList = new ArrayList();
        Iterator itor = queryResult.iterator();
        while (itor.hasNext()) {
            Object[] obj = (Object[]) itor.next();
            ExpenseApproveRequestListView view = new ExpenseApproveRequestListView();
            view.setExpenseApproveRequest((ExpenseApproveRequest) obj[0]);
            view.setExpense((Expense) obj[1]);
            viewList.add(view);
        }
        return viewList;
    }

    private Map constructQueryMap(ExpenseApproveRequestQueryForm queryForm, User loginUser) {
        Map conditions = new HashMap();

        long today = this.getTodayTimeMillis();
        conditions.put(ExpenseApproveRequestQueryCondition.APPROVER_ID_EQ, 
                new Object[] { loginUser.getId(), loginUser.getId(), new Date(today + 86400000), new Date(today) });

        String code = queryForm.getCode();
        if (code != null && code.trim().length() != 0) {
            conditions.put(ExpenseApproveRequestQueryCondition.CODE_LIKE, code);
        }

        String title = queryForm.getTitle();
        if (title != null && title.trim().length() != 0) {
            conditions.put(ExpenseApproveRequestQueryCondition.TITLE_LIKE, title);
        }

        Integer approveStatus = ActionUtils.parseInt(queryForm.getApproveStatus());
        if (approveStatus != null) {
            conditions.put(ExpenseApproveRequestQueryCondition.STATUS_EQ, approveStatus);
        } else {
            conditions.put(ExpenseApproveRequestQueryCondition.STATUS_NEQ, ApproveStatus.NOT_YOUR_TURN);
        }
        
        String submitTimeFrom = queryForm.getSubmitDateFrom();
        String submitTimeTo = queryForm.getSubmitDateTo();
        if ((submitTimeFrom != null && submitTimeFrom.trim().length() != 0) || (submitTimeTo != null && submitTimeTo.trim().length() != 0)) {
            if (submitTimeFrom == null || submitTimeFrom.trim().length() == 0)
                submitTimeFrom = FAKE_DATE_BEGIN;
            if (submitTimeTo == null || submitTimeTo.trim().length() == 0)
                submitTimeTo = FAKE_DATE_TO;
            Date queryCreateTimeBegin = ActionUtils.getQueryBeginDateFromDisplayDate(submitTimeFrom);
            Date queryCreateTimeTo = ActionUtils.getQueryToDateFromDisplayDate(submitTimeTo);
            conditions.put(ExpenseApproveRequestQueryCondition.SUBMIT_DATE_BT, new Object[] { queryCreateTimeBegin, queryCreateTimeTo, });
        }
        
        BigDecimal amountFrom = ActionUtils.parseBigDecimal(queryForm.getAmountFrom());
        if (amountFrom != null) {
            conditions.put(ExpenseApproveRequestQueryCondition.EXPENSE_AMOUNT_GE, amountFrom);
        }

        BigDecimal amountTo = ActionUtils.parseBigDecimal(queryForm.getAmountTo());
        if (amountTo != null) {
            conditions.put(ExpenseApproveRequestQueryCondition.EXPENSE_AMOUNT_LE, amountTo);
        }

        return conditions;
    }

    private void putApproveStatusListToRequest(HttpServletRequest request) {
        List approveStatusList = PersistentEnum.getEnumList(ApproveStatus.class);
        approveStatusList.remove(ApproveStatus.NOT_YOUR_TURN);
        request.setAttribute("X_APPROVESTATUSLIST", approveStatusList);
    }

    /**
     * 查看ExpenseApproveRequest
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        ExpenseApproveRequest expenseApproveRequest = getExpenseApproveRequestFromRequest(request);
        Expense expense = getExpenseByApproveRequest(expenseApproveRequest, request);

        checkApprovePower(expenseApproveRequest, request);
        request.setAttribute("x_expense", expense);

        putExpenseSubCategoryListToRequest(expense, request);
        putExpenseAttachmentToRequest(expense, request);
        putApproveListToRequest(expense, request);

        request.setAttribute("X_APPROVEREQUEST", expenseApproveRequest);
        request.setAttribute("x_currentUserId", this.getCurrentUser(request).getId());
        request.setAttribute("X_APPROVEACTION", "Expense");

        if (ApproveStatus.WAITING_FOR_APPROVE.equals(expenseApproveRequest.getStatus())) {
            if (YesNo.YES.equals(expenseApproveRequest.getCanModify())) {
                BeanForm expenseForm = (BeanForm) getForm("/updateAndApproveExpenseApproveRequest", request);
                if (!isBack(request)) {
                    expenseForm.populateToForm(expense);
                }

                putExpenseCurrencyListToRequest(expense, request);
                putProjectCodeToRequest(expense.getDepartment().getSite(), request);
                putEnumListToRequest(request);
                putExpenseRowCellListToRequest(expense, request, true, true);
                putExpenseRechargeListToRequest(expense, request, expenseForm, false);
                request.setAttribute("x_postfix", "_self");
                return mapping.findForward("editPage");
            }
            request.setAttribute("X_SHOWAPPROVEBUTTON", "1");
        }

        putExpenseRowCellListToRequest(expense, request, true, true);
        putExpenseRechargeListToRequest(expense, request, null, true);
        putCanViewExpenseBudgetAmount(expense.getYearlyBudget(),request);
        
        return mapping.findForward("viewPage");
    }

    /**
     * 通过ExpenseApproveRequest
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward approve(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ExpenseApproveRequest expenseApproveRequest = getExpenseApproveRequestFromRequest(request);

        checkApprovePower(expenseApproveRequest, request);
        expenseApproveRequest.setComment(request.getParameter("comment"));
        expenseApproveRequest.setActualApprover(getCurrentUser(request));
        ExpenseApproveRequestManager earm = ServiceLocator.getExpenseApproveRequestManager(request);
        earm.approveExpenseApproveRequest(expenseApproveRequest,this.getCurrentUser(request));
        return getViewForward(expenseApproveRequest);
    }

    /**
     * 拒绝ExpenseApproveRequest
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward reject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ExpenseApproveRequest expenseApproveRequest = getExpenseApproveRequestFromRequest(request);

        checkApprovePower(expenseApproveRequest, request);
        expenseApproveRequest.setComment(request.getParameter("comment"));
        expenseApproveRequest.setActualApprover(getCurrentUser(request));
        ExpenseApproveRequestManager earm = ServiceLocator.getExpenseApproveRequestManager(request);
        earm.rejectExpenseApproveRequest(expenseApproveRequest,this.getCurrentUser(request));
        return getViewForward(expenseApproveRequest);
    }

    /**
     * 保存Expense并通过ExpenseApproveRequest
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward updateAndApprove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ExpenseApproveRequest expenseApproveRequest = getExpenseApproveRequestFromRequest(request);
        checkApprovePower(expenseApproveRequest, request);

        Expense expense = getExpenseFromRequest(request);

        BeanForm expenseForm = (BeanForm) form;
        expenseForm.setBeanLoader(ServiceLocator.getBeanLoader(request));
        expenseForm.populateToBean(expense, request);

        List expenseRowList = getExpenseRowCellFromRequest(expense, request);

        List rechargeList = getRechargeInfoFromRequest(expense, request);

        expenseApproveRequest.setComment(request.getParameter("comment"));
        expenseApproveRequest.setActualApprover(getCurrentUser(request));

        ExpenseApproveRequestManager earm = ServiceLocator.getExpenseApproveRequestManager(request);
        earm.approveExpenseApproveRequestAndUpdateExpense(expenseApproveRequest, expense, expenseRowList, rechargeList,this.getCurrentUser(request));
    
        return getViewForward(expenseApproveRequest);
    }

    private Expense getExpenseByApproveRequest(ExpenseApproveRequest expenseApproveRequest, HttpServletRequest request) {
        ExpenseManager expenseManager = ServiceLocator.getExpenseManager(request);
        Expense expense = expenseManager.getExpenseByApproveRequestId(expenseApproveRequest.getApproveRequestId());
        if (expense == null)
            throw new ActionException("expenseApproveRequest.error.expenseNotFound");
        return expense;
    }

    private ExpenseApproveRequest getExpenseApproveRequestFromRequest(HttpServletRequest request) throws Exception {
        String approveRequestId = request.getParameter("request_id");
        Integer userId = ActionUtils.parseInt(request.getParameter("approver_id"));
        UserManager um = ServiceLocator.getUserManager(request);
        User user = um.getUser(userId);
        ExpenseApproveRequestManager earm = ServiceLocator.getExpenseApproveRequestManager(request);
        ExpenseApproveRequest taaRequest = earm.getExpenseApproveRequest(approveRequestId, user);
        if (taaRequest == null)
            throw new ActionException("expenseApproveRequest.notFound", new Object[] { approveRequestId, userId });
        return taaRequest;
    }

    private void checkApprovePower(ExpenseApproveRequest expenseApproveRequest, HttpServletRequest request) {
        User loginUser = getCurrentUser(request);
        if (!expenseApproveRequest.getApprover().getId().equals(loginUser.getId())) {
            ApproverDelegateManager adm = ServiceLocator.getApproverDelegateManager(request);
            if (!adm.isDelegateApprover(ApproverDelegateType.EXPENSE_APPROVER, expenseApproveRequest.getApprover().getId(), loginUser.getId())) {
                throw new ActionException("expenseApproveRequest.error.noApprovePower");
            }
        }
    }
    
    private ActionForward getViewForward(ExpenseApproveRequest expenseApproveRequest) {
        String url = "/viewExpenseApproveRequest.do?request_id=" + expenseApproveRequest.getApproveRequestId() + "&approver_id="
        + expenseApproveRequest.getApprover().getId();
        return new ActionForward(url, true);
    }
    
    protected void putCanViewExpenseBudgetAmount(YearlyBudget yb, HttpServletRequest request) {
        if (yb != null) {
            YearlyBudgetManager ym = ServiceLocator.getYearlyBudgetManager(request);
            request.setAttribute("x_canViewExpenseBudgetAmount",new Boolean(ym.canViewExpenseBudgetAmount(yb,this.getCurrentUser(request))));
        } else {
            request.setAttribute("x_canViewExpenseBudgetAmount", false);
        }         
   }
    
}