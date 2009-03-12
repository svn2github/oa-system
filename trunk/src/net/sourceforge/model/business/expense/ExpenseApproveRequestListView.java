/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.business.expense;


/**
 * 该类是Expense的Approve查询显示列表对象
 * @author ych
 * @version 1.0 (Nov 16, 2005)
 */
public class ExpenseApproveRequestListView {
    
    private Expense expense;
    
    private ExpenseApproveRequest expenseApproveRequest;
    

    
    /**
     * @return Returns the expenseApproveRequest.
     */
    public ExpenseApproveRequest getExpenseApproveRequest() {
        return expenseApproveRequest;
    }

    /**
     * @param expenseApproveRequest The expenseApproveRequest to set.
     */
    public void setExpenseApproveRequest(ExpenseApproveRequest expenseApproveRequest) {
        this.expenseApproveRequest = expenseApproveRequest;
    }


    /**
     * @return Returns the expense.
     */
    public Expense getExpense() {
        return expense;
    }

    /**
     * @param expense The expense to set.
     */
    public void setExpense(Expense expense) {
        this.expense = expense;
    }
    
    
}
