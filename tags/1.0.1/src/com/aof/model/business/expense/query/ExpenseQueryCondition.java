/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.model.business.expense.query;

import org.apache.commons.lang.enums.Enum;

/**
 * Expense查询条件的枚举类
 * 
 * @author ych
 * @version 1.0 (Nov 19, 2005)
 */
public class ExpenseQueryCondition extends Enum {

    public static final ExpenseQueryCondition ID_LIKE = new ExpenseQueryCondition("id_like");

    public static final ExpenseQueryCondition TRAVELAPPLICATION_ID_EQ = new ExpenseQueryCondition("travelApplication_id_eq");

    public static final ExpenseQueryCondition DEPARTMENT_ID_EQ = new ExpenseQueryCondition("department_id_eq");
    
    public static final ExpenseQueryCondition DEPARTMENT_ID_IN = new ExpenseQueryCondition("department_id_in");

    public static final ExpenseQueryCondition EXPENSECATEGORY_ID_EQ = new ExpenseQueryCondition("expenseCategory_id_eq");

    public static final ExpenseQueryCondition EXPENSECURRENCY_CODE_EQ = new ExpenseQueryCondition("expenseCurrency_code_eq");

    public static final ExpenseQueryCondition BASECURRENCY_CODE_EQ = new ExpenseQueryCondition("baseCurrency_code_eq");

    public static final ExpenseQueryCondition REQUESTOR_ID_EQ = new ExpenseQueryCondition("requestor_id_eq");
    
    public static final ExpenseQueryCondition REQUESTOR_ID_NEQ = new ExpenseQueryCondition("requestor_id_neq");

    public static final ExpenseQueryCondition CREATOR_ID_EQ = new ExpenseQueryCondition("creator_id_eq");

    public static final ExpenseQueryCondition TITLE_LIKE = new ExpenseQueryCondition("title_like");

    public static final ExpenseQueryCondition DESCRIPTION_LIKE = new ExpenseQueryCondition("description_like");

    public static final ExpenseQueryCondition STATUS_EQ = new ExpenseQueryCondition("status_eq");

    public static final ExpenseQueryCondition AMOUNT_EQ = new ExpenseQueryCondition("amount_eq");
    
    public static final ExpenseQueryCondition AMOUNT_GT = new ExpenseQueryCondition("amount_gt");
    
    public static final ExpenseQueryCondition AMOUNT_LT = new ExpenseQueryCondition("amount_lt");

    public static final ExpenseQueryCondition EXCHANGERATE_EQ = new ExpenseQueryCondition("exchangeRate_eq");

    public static final ExpenseQueryCondition REQUESTDATE_EQ = new ExpenseQueryCondition("requestDate_eq");
    
    public static final ExpenseQueryCondition REQUESTDATE_GT = new ExpenseQueryCondition("requestDate_gt");
    
    public static final ExpenseQueryCondition REQUESTDATE_LT = new ExpenseQueryCondition("requestDate_lt");
    
    public static final ExpenseQueryCondition CREATEDATE_GE = new ExpenseQueryCondition("createDate_gt");
    
    public static final ExpenseQueryCondition CREATEDATE_LE = new ExpenseQueryCondition("createDate_lt");

    public static final ExpenseQueryCondition CRATEDATE_EQ = new ExpenseQueryCondition("crateDate_eq");

    public static final ExpenseQueryCondition ISRECHARGE_EQ = new ExpenseQueryCondition("isRecharge_eq");

    public static final ExpenseQueryCondition APPROVEREQUESTID_EQ = new ExpenseQueryCondition("approveRequestId_eq");

    public static final ExpenseQueryCondition EXPORTSTATUS_EQ = new ExpenseQueryCondition("exportStatus_eq");

    public static final ExpenseQueryCondition STATUS_EQ2 = new ExpenseQueryCondition("status_eq2");

    public static final ExpenseQueryCondition SITE_ID_EQ = new ExpenseQueryCondition("site_id_eq");
    
    public static final ExpenseQueryCondition ID_BEGINWITH = new ExpenseQueryCondition("id_begin_with");
    
    public static final ExpenseQueryCondition REQUESTOR_LK = new ExpenseQueryCondition("requestor_like");
    
    public static final ExpenseQueryCondition APPROVEDATE_GE = new ExpenseQueryCondition("approveDate_ge");
    
    public static final ExpenseQueryCondition APPROVEDATE_LE = new ExpenseQueryCondition("approveDate_le");

    public static final ExpenseQueryCondition STATUS_EQ3 = new ExpenseQueryCondition("status_eq3");
    

    protected ExpenseQueryCondition(String value) {
        super(value);
    }

    public static ExpenseQueryCondition getEnum(String value) {
        return (ExpenseQueryCondition) getEnum(ExpenseQueryCondition.class, value);
    }

}
