/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.service.business.expense.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.aof.dao.business.expense.ExpenseApproveRequestDAO;
import com.aof.model.admin.User;
import com.aof.model.business.expense.Expense;
import com.aof.model.business.expense.ExpenseApproveRequest;
import com.aof.model.business.expense.query.ExpenseApproveRequestQueryOrder;
import com.aof.model.metadata.ApproveStatus;
import com.aof.model.metadata.ApproverDelegateType;
import com.aof.service.BaseManager;
import com.aof.service.admin.SystemLogManager;
import com.aof.service.business.ApproveRelativeEmailManager;
import com.aof.service.business.expense.ExpenseApproveRequestManager;
import com.aof.service.business.expense.ExpenseManager;

/**
 * ExpenseApproveRequestManagerµÄÊµÏÖ
 * 
 * @author ych
 * @version 1.0 (Nov 17, 2005)
 */
public class ExpenseApproveRequestManagerImpl extends BaseManager implements ExpenseApproveRequestManager {

    private ExpenseApproveRequestDAO dao;

    private ExpenseManager expenseManager;
    
    private SystemLogManager systemLogManager;
    
    private ApproveRelativeEmailManager approveRelativeEmailManager;   

    public void setExpenseManager(ExpenseManager expenseManager) {
        this.expenseManager = expenseManager;
    }
    
    public void setSystemLogManager(SystemLogManager systemLogManager) {
        this.systemLogManager = systemLogManager;
    }

    public void setExpenseApproveRequestDAO(ExpenseApproveRequestDAO dao) {
        this.dao = dao;
    }

    public void setApproveRelativeEmailManager(ApproveRelativeEmailManager approveRelativeEmailManager) {
        this.approveRelativeEmailManager = approveRelativeEmailManager;
    }

    public List getExpenseApproveRequestListByApproveRequestId(String requestId) {
        return dao.getExpenseApproveRequestListByApproveRequestId(requestId);
    }

    public ExpenseApproveRequest getExpenseApproveRequest(String id, User approver) {
        return dao.getExpenseApproveRequest(id, approver);
    }

    public ExpenseApproveRequest updateExpenseApproveRequest(ExpenseApproveRequest expenseApproveRequest) {
        return dao.updateExpenseApproveRequest(expenseApproveRequest);
    }

    public ExpenseApproveRequest insertExpenseApproveRequest(ExpenseApproveRequest expenseApproveRequest) {
        return dao.insertExpenseApproveRequest(expenseApproveRequest);
    }

    public int getExpenseApproveRequestListCount(Map conditions) {
        return dao.getExpenseApproveRequestListCount(conditions);
    }

    public List getExpenseApproveRequestList(Map conditions, int pageNo, int pageSize, ExpenseApproveRequestQueryOrder order, boolean descend) {
        return dao.getExpenseApproveRequestList(conditions, pageNo, pageSize, order, descend);
    }

    public void approveExpenseApproveRequest(ExpenseApproveRequest expenseApproveRequest,User currentUser) {
        expenseApproveRequest.setStatus(ApproveStatus.APPROVED);
        expenseApproveRequest.setApproveDate(new Date());
        dao.updateExpenseApproveRequest(expenseApproveRequest);
        ExpenseApproveRequest nextApprove = getNextExpenseApproveRequest(expenseApproveRequest);
        Expense expense = expenseManager.getExpenseByApproveRequestId(expenseApproveRequest.getApproveRequestId());
        
        //add by Jackey 2007-10-15, for not sending batch mail when approver approved expense.
        approveRelativeEmailManager.UnsendApprovedEmailBatch(expense);        
        
        if (nextApprove != null) {
            nextApprove.setStatus(ApproveStatus.WAITING_FOR_APPROVE);
            nextApprove.setYourTurnDate(new Date());
            nextApprove = dao.updateExpenseApproveRequest(nextApprove);
            approveRelativeEmailManager.sendApprovalEmail(expense, ApproverDelegateType.EXPENSE_APPROVER, nextApprove);
        } else {
            if (expense != null) {
                expenseManager.approveExpense(expense);                
            }
        }
        systemLogManager.generateLog(null, expense, Expense.LOG_ACTION_APPROVE, currentUser);
    }

    public void rejectExpenseApproveRequest(ExpenseApproveRequest expenseApproveRequest,User currentUser) {
        expenseApproveRequest.setStatus(ApproveStatus.REJECTED);
        expenseApproveRequest.setApproveDate(new Date());
        dao.updateExpenseApproveRequest(expenseApproveRequest);
        Expense expense = expenseManager.getExpenseByApproveRequestId(expenseApproveRequest.getApproveRequestId());
        
        //add by Jackey 2007-10-15, for not sending batch mail when approver rejected expense.
        approveRelativeEmailManager.UnsendApprovedEmailBatch(expense);
        
        if (expense != null) {
            expenseManager.rejectExpense(expense, currentUser, expenseApproveRequest.getComment());
        }
    }

    private ExpenseApproveRequest getNextExpenseApproveRequest(ExpenseApproveRequest expenseApproveRequest) {
        List approveList = getExpenseApproveRequestListByApproveRequestId(expenseApproveRequest.getApproveRequestId());
        Iterator itor = approveList.iterator();
        while (itor.hasNext()) {
            ExpenseApproveRequest approve = (ExpenseApproveRequest) itor.next();
            if (approve.getApprover().equals(expenseApproveRequest.getApprover())) {
                if (itor.hasNext()) {
                    return (ExpenseApproveRequest) itor.next();
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    public void approveExpenseApproveRequestAndUpdateExpense(ExpenseApproveRequest expenseApproveRequest, Expense expense, List expenseRowList, List rechargeList,User currentUser) {
        expenseManager.updateExpense(expense, expenseRowList, rechargeList);
        approveExpenseApproveRequest(expenseApproveRequest,currentUser);
    }


}
