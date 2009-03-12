/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.model.business.expense.query;

import org.apache.commons.lang.enums.Enum;

/**
 * ExpenseApproveRequest查询顺序的枚举类
 * @author ych
 * @version 1.0 (Nov 18, 2005)
 */
public class ExpenseApproveRequestQueryOrder extends Enum{
    
	public static final ExpenseApproveRequestQueryOrder SEQ = new ExpenseApproveRequestQueryOrder("seq");
	public static final ExpenseApproveRequestQueryOrder STATUS = new ExpenseApproveRequestQueryOrder("status");
	public static final ExpenseApproveRequestQueryOrder APPROVEDATE = new ExpenseApproveRequestQueryOrder("approveDate");
	public static final ExpenseApproveRequestQueryOrder COMMENT = new ExpenseApproveRequestQueryOrder("comment");
	public static final ExpenseApproveRequestQueryOrder CANMODIFY = new ExpenseApproveRequestQueryOrder("canModify");
    public static final ExpenseApproveRequestQueryOrder EXPENSE_CODE = new ExpenseApproveRequestQueryOrder("expense_code");
    public static final ExpenseApproveRequestQueryOrder EXPENSE_DEPARTMENT = new ExpenseApproveRequestQueryOrder("expense_department");
    
	protected ExpenseApproveRequestQueryOrder(String value) {
		super(value);
	}
	
	public static ExpenseApproveRequestQueryOrder getEnum(String value) {
        return (ExpenseApproveRequestQueryOrder) getEnum(ExpenseApproveRequestQueryOrder.class, value);
    }

}
