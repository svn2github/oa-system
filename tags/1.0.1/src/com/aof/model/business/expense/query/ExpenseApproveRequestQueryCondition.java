/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.model.business.expense.query;

import com.aof.model.business.query.BaseApproveQueryCondition;

/**
 * ExpenseApproveRequest查询条件的枚举类
 * 
 * @author ych
 * @version 1.0 (Nov 18, 2005)
 */
public class ExpenseApproveRequestQueryCondition extends BaseApproveQueryCondition {

    public static final ExpenseApproveRequestQueryCondition CODE_LIKE = new ExpenseApproveRequestQueryCondition("code_like");

    public static final ExpenseApproveRequestQueryCondition TITLE_LIKE = new ExpenseApproveRequestQueryCondition("title_like");

    //public static final ExpenseApproveRequestQueryCondition STATUS_EQ = new ExpenseApproveRequestQueryCondition("status_eq");

    public static final ExpenseApproveRequestQueryCondition STATUS_NEQ = new ExpenseApproveRequestQueryCondition("status_neq");

    public static final ExpenseApproveRequestQueryCondition SUBMIT_DATE_BT = new ExpenseApproveRequestQueryCondition("submit_date_bt");

    public static final ExpenseApproveRequestQueryCondition EXPENSE_AMOUNT_GE = new ExpenseApproveRequestQueryCondition("expense_amount_ge");;

    public static final ExpenseApproveRequestQueryCondition EXPENSE_AMOUNT_LE = new ExpenseApproveRequestQueryCondition("expense_amount_le");;

    protected ExpenseApproveRequestQueryCondition(String value) {
        super(value);
    }

    public static ExpenseApproveRequestQueryCondition getEnum(String value) {
        return (ExpenseApproveRequestQueryCondition) getEnum(ExpenseApproveRequestQueryCondition.class, value);
    }

}
