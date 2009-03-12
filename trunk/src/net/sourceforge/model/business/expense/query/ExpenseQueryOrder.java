/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.model.business.expense.query;

import org.apache.commons.lang.enums.Enum;

/**
 * Expense查询顺序的枚举类
 * @author ych
 * @version 1.0 (Nov 19, 2005)
 */
public class ExpenseQueryOrder extends Enum{

	public static final ExpenseQueryOrder ID = new ExpenseQueryOrder("id");
	public static final ExpenseQueryOrder TITLE = new ExpenseQueryOrder("title");
	public static final ExpenseQueryOrder DESCRIPTION = new ExpenseQueryOrder("description");
	public static final ExpenseQueryOrder STATUS = new ExpenseQueryOrder("status");
	public static final ExpenseQueryOrder AMOUNT = new ExpenseQueryOrder("amount");
	public static final ExpenseQueryOrder EXCHANGERATE = new ExpenseQueryOrder("exchangeRate");
	public static final ExpenseQueryOrder REQUESTDATE = new ExpenseQueryOrder("requestDate");
	public static final ExpenseQueryOrder CREATEDATE = new ExpenseQueryOrder("createDate");
	public static final ExpenseQueryOrder ISRECHARGE = new ExpenseQueryOrder("isRecharge");
	public static final ExpenseQueryOrder APPROVEREQUESTID = new ExpenseQueryOrder("approveRequestId");
	public static final ExpenseQueryOrder EXPORTSTATUS = new ExpenseQueryOrder("exportStatus");
    public static final ExpenseQueryOrder CATEGORY = new ExpenseQueryOrder("category");
    public static final ExpenseQueryOrder DEPARTMENT = new ExpenseQueryOrder("department");
    public static final ExpenseQueryOrder CONFIRMDATE = new ExpenseQueryOrder("confirmDate");;
    
	protected ExpenseQueryOrder(String value) {
		super(value);
	}
	
	public static ExpenseQueryOrder getEnum(String value) {
        return (ExpenseQueryOrder) getEnum(ExpenseQueryOrder.class, value);
    }

}
