/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.web.struts.action.business.expense;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.aof.model.admin.ExpenseSubCategory;
import com.aof.model.admin.ProjectCode;
import com.aof.model.admin.Site;
import com.aof.model.admin.TravelGroup;
import com.aof.model.admin.TravelGroupDetail;
import com.aof.model.admin.UserSite;
import com.aof.model.business.BaseApproveRequest;
import com.aof.model.business.expense.Expense;
import com.aof.model.business.expense.ExpenseCell;
import com.aof.model.business.expense.ExpenseClaim;
import com.aof.model.business.expense.ExpenseRow;
import com.aof.model.business.expense.query.ExpenseAttachmentQueryCondition;
import com.aof.model.business.expense.query.ExpenseAttachmentQueryOrder;
import com.aof.model.metadata.ApproveStatus;
import com.aof.model.metadata.ExpenseStatus;
import com.aof.service.admin.ExchangeRateManager;
import com.aof.service.admin.ExpenseSubCategoryManager;
import com.aof.service.admin.ProjectCodeManager;
import com.aof.service.admin.TravelGroupManager;
import com.aof.service.admin.UserManager;
import com.aof.service.business.expense.ExpenseApproveRequestManager;
import com.aof.service.business.expense.ExpenseAttachmentManager;
import com.aof.service.business.expense.ExpenseManager;
import com.aof.web.struts.action.ActionUtils;
import com.aof.web.struts.action.ServiceLocator;
import com.aof.web.struts.action.business.RechargeAction;
import com.shcnc.hibernate.PersistentEnum;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.form.BeanForm;
/**
 * Expense的Action基类,ExpenseAction和ExpenseApproveRequestAction从该类继承
 * 
 * @author ych
 * @version 1.0 (Nov 19, 2005)
 */
public class BaseExpenseAction extends RechargeAction {
    
    protected void putExpenseRechargeListToRequest(Expense expense, HttpServletRequest request, BeanForm expenseForm,boolean forView) {
        ExpenseManager  em = ServiceLocator.getExpenseManager(request);
        List rechargeList=em.getExpenseRechargeListByExpense(expense);
        if (!forView) {
            setRechargeInfoToRequest(expense, rechargeList, expense.getDepartment().getSite(), expenseForm, request);
            request.setAttribute("X_FORMNAME", "expenseForm");
        } else {
            setRechargeInfoToRequestForView(expense,rechargeList,request);
        }
    }

    protected void putExpenseRowCellListToRequest(Expense expense, HttpServletRequest request, boolean putSumRow,boolean putLimitRow) {
        ExpenseSubCategoryManager escm = ServiceLocator.getExpenseSubCategoryManager(request);
        List enabledExpenseSubCategoryList=escm.getEnabledChildrenOfExpenseCategory(expense.getExpenseCategory().getId());
        ExpenseManager  em = ServiceLocator.getExpenseManager(request);
        List expenseRowList=em.getExpenseRowCellList(expense,enabledExpenseSubCategoryList);
        request.setAttribute("x_expenseRowList",expenseRowList);
        if (putSumRow) {
            putExpenseSumRowToRequest(enabledExpenseSubCategoryList.size(),expenseRowList,request);
        }
        if (putLimitRow) {
            putExpenseLimitRowToRequest(expense,enabledExpenseSubCategoryList,request);
        }
        if (expense.getStatus().equals(ExpenseStatus.CLAIMED) || expense.getStatus().equals(ExpenseStatus.CONFIRMED)) {
            List claimList=em.getExpenseClaimList(expense);
            putExpenseClaimRowToRequest(enabledExpenseSubCategoryList,claimList,request);
        }
    }
    
    protected void putExpenseClaimRowToRequest(List enabledExpenseSubCategoryList,List claimList, HttpServletRequest request) {
        ExpenseRow claimRow=new ExpenseRow();
        List claimCellList=new ArrayList();
        claimRow.setExpenseCellList(claimCellList);
        for (Iterator itor = enabledExpenseSubCategoryList.iterator(); itor.hasNext();) {
            ExpenseSubCategory expenseSubCategory = (ExpenseSubCategory) itor.next();
            ExpenseCell claimCell=new ExpenseCell();
            claimCell.setAmount(new BigDecimal(0d));
            claimCellList.add(claimCell);
            for (Iterator itorClaim = claimList.iterator(); itorClaim.hasNext();) {
                ExpenseClaim claim = (ExpenseClaim) itorClaim.next();
                if (claim.getExpenseSubCategory().equals(expenseSubCategory)) {
                    claimCell.setAmount(claim.getAmount());
                    claimCell.setDescription(claim.getDescription());
                }
            }
        }    
        request.setAttribute("x_expenseClaimRow",claimRow);
    }

    protected void putExpenseLimitRowToRequest(Expense expense,List enabledExpenseSubCategoryList, HttpServletRequest request) {
        if (expense.getTravelApplication()==null) return;
        UserManager um=ServiceLocator.getUserManager(request);
        UserSite userSite=um.getUserSite(expense.getRequestor().getId(),expense.getDepartment().getSite().getId());
        if (userSite==null) return;
        TravelGroup travelGroup=userSite.getTravelGroup();
        boolean isAbroad=!expense.getDepartment().getSite().getCity().getProvince().getCountry().equals(expense.getTravelApplication().getToCity().getProvince().getCountry());
        ExpenseRow limitRow=new ExpenseRow();
        List limitCellList=new ArrayList();
        limitRow.setExpenseCellList(limitCellList);
        TravelGroupManager tm=ServiceLocator.getTravelGroupManager(request);
        List groupList=tm.getDetailOf(travelGroup);
        for (Iterator itor = enabledExpenseSubCategoryList.iterator(); itor.hasNext();) {
            ExpenseSubCategory expenseSubCategory = (ExpenseSubCategory) itor.next();
            ExpenseCell limitCell=new ExpenseCell();
            limitCell.setAmount(new BigDecimal(0d));
            limitCellList.add(limitCell);
            for (int index = 0; index < groupList.size(); index++) {
                TravelGroupDetail travelGroupDetail=(TravelGroupDetail)groupList.get(index);
                if (travelGroupDetail.getExpenseSubCategory().equals(expenseSubCategory) ) {
                    limitCell.setAmount(isAbroad?travelGroupDetail.getAbroadAmountLimit():travelGroupDetail.getAmountLimit());
                    groupList.remove(index);
                    break;
                }
            }
        }
        request.setAttribute("x_expenseLimitRow",limitRow);
    }

    protected void putExpenseSumRowToRequest(int sumCellCount, List expenseRowList ,HttpServletRequest request) {
        ExpenseRow sumRow=new ExpenseRow();
        List sumCellList=new ArrayList();
        sumRow.setExpenseCellList(sumCellList);
        for (int index=0;index<sumCellCount;index++) {
            ExpenseCell sumCell=new ExpenseCell();
            sumCell.setAmount(new BigDecimal(0d));
            sumCellList.add(sumCell);
        }
        for (Iterator itor = expenseRowList.iterator(); itor.hasNext();) {
            ExpenseRow row = (ExpenseRow) itor.next();
            List cellList = row.getExpenseCellList();
            for (int index=0;index<sumCellCount;index++) {
                ExpenseCell sumCell = (ExpenseCell) sumCellList.get(index);
                ExpenseCell cell = (ExpenseCell) cellList.get(index);
                BigDecimal sum = sumCell.getAmount().add(cell.getAmount());
                sumCell.setAmount(sum);
                String description = cell.getDescription();
                if (!ActionUtils.isEmptyString(description)) {
                    String sumDescription = sumCell.getDescription(); 
                    StringBuffer buffer = new StringBuffer();
                    if (sumDescription != null) {
                        buffer.append(sumDescription);
                        buffer.append(' ');
                    }
                    buffer.append(ActionUtils.getDisplayDateFromDate(row.getDate()));
                    buffer.append(' ');
                    buffer.append(description);
                    sumCell.setDescription(buffer.toString());
                }
            }
        }
        request.setAttribute("x_expenseSumRow",sumRow);
    }

    protected void putApproveListToRequest(Expense expense, HttpServletRequest request) {
        ExpenseApproveRequestManager em = ServiceLocator.getExpenseApproveRequestManager(request);
        List approveList = em.getExpenseApproveRequestListByApproveRequestId(expense.getApproveRequestId());
        request.setAttribute("X_APPROVELIST", approveList);
        if (approveList.size() > 0) {
            BaseApproveRequest approveRequest = (BaseApproveRequest)approveList.get(0);
            request.setAttribute("x_canWithDraw", Boolean.valueOf(approveRequest.getStatus().equals(ApproveStatus.WAITING_FOR_APPROVE)&& expense.getStatus().equals((ExpenseStatus.PENDING))));
        }
    }

    protected void putExpenseAttachmentToRequest(Expense expense, HttpServletRequest request) throws Exception {
        ExpenseAttachmentManager eam = ServiceLocator.getExpenseAttachmentManager(request);
        Map conditions = new HashMap();
        conditions.put(ExpenseAttachmentQueryCondition.EXPENSE_ID_EQ, expense.getId());
        List result = eam.getExpenseAttachmentList(conditions, 0, -1, ExpenseAttachmentQueryOrder.ID, false);
        request.setAttribute("x_attachmentList", result);
    }
    
    protected void putExpenseSubCategoryListToRequest(Expense expense,HttpServletRequest request) {
        ExpenseSubCategoryManager em = ServiceLocator.getExpenseSubCategoryManager(request);
        request.setAttribute("x_subCategoryList", em.getEnabledChildrenOfExpenseCategory(expense.getExpenseCategory().getId()));
    }

    protected void putExpenseCurrencyListToRequest(Expense expense,HttpServletRequest request) {
        ExchangeRateManager em=ServiceLocator.getExchangeRateManager(request);
        List currencyList=em.getEnabledExchangeRateListBySiteIncludeBaseCurrency(expense.getDepartment().getSite());
        request.setAttribute("x_expenseCurrencyList",currencyList);
    }

    protected void putProjectCodeToRequest(Site site, HttpServletRequest request) 
    {
        ProjectCodeManager pcm = ServiceLocator.getProjectCodeManager(request);
        List projectCodeList = pcm.getEnabledProjectCodeListBySite(site);
        request.setAttribute("x_projectCodeList", projectCodeList);
    }
    
    protected void putEnumListToRequest(HttpServletRequest request) {
        request.setAttribute("x_expenseStatusList", PersistentEnum.getEnumList(ExpenseStatus.class));
    }

    protected Expense getExpenseFromRequest(HttpServletRequest request) throws Exception {
        String id = request.getParameter("id");
        ExpenseManager expenseManager = ServiceLocator.getExpenseManager(request);
        Expense expense = expenseManager.getExpense(id);
        if (expense == null)
            throw new ActionException("expense.notFound", id);
        return expense;
    }

    protected List getExpenseRowCellFromRequest(Expense expense, HttpServletRequest request) {
        List rowList=new ArrayList();
        ExpenseSubCategoryManager em=ServiceLocator.getExpenseSubCategoryManager(request);
        List expenseSubCategoryList=em.getEnabledChildrenOfExpenseCategory(expense.getExpenseCategory().getId());
        String date[]=request.getParameterValues("expense_date");
        double totalAmount=0;
        if (date!=null) {
            String projectCode[]=request.getParameterValues("projectCode");
            for (int index=0;index<date.length;index++) {
                ExpenseRow row=new ExpenseRow();
                row.setExpense(expense);
                row.setDate(ActionUtils.getDateFromDisplayDate(date[index]));
                //row.setDescription(description[index]);
                List cellList=new ArrayList();
                Iterator itor=expenseSubCategoryList.iterator();
                while (itor.hasNext()) {
                    ExpenseSubCategory  expenseSubCategory= (ExpenseSubCategory) itor.next();
                    String amount[]=request.getParameterValues("expense_"+expenseSubCategory.getId());
                    String description[]=request.getParameterValues("description_"+expenseSubCategory.getId());                    
                    if (amount!=null) {
                        ExpenseCell cell=new ExpenseCell();
                        cell.setExpenseSubCategory(expenseSubCategory);
                        cell.setAmount(getAsBigDecimal(amount[index]));
                        cell.setDescription(description[index]);                      
                        cell.setRow(row);
                        cellList.add(cell);
                        totalAmount+=cell.getAmount().doubleValue();
                    }
                }
                if (projectCode[index] != null && projectCode[index].length() > 0) {
                    Integer projectId = new Integer(projectCode[index]);                
                    row.setProjectCode(new ProjectCode(projectId));
                }
                row.setExpenseCellList(cellList);
                rowList.add(row);
            }
        }        
        expense.setAmount((new BigDecimal(totalAmount)).setScale(2, BigDecimal.ROUND_HALF_UP));
        return rowList;
    }
    
    protected BigDecimal getAsBigDecimal(String s) {
        if (s == null)
            return new BigDecimal(0d);
        try {
            return new BigDecimal(s);
        } catch (Throwable e) {
            return new BigDecimal(0d);
        }
    }
    

}
