/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.model.business.expense.query;

import org.apache.commons.lang.enums.Enum;

/**
 * ExpenseAttachment查询顺序的枚举类
 * @author ych
 * @version 1.0 (Nov 23, 2005)
 */
public class ExpenseAttachmentQueryOrder extends Enum{

	/*id*/
	public static final ExpenseAttachmentQueryOrder ID = new ExpenseAttachmentQueryOrder("id");

	/*property*/
	public static final ExpenseAttachmentQueryOrder FILESIZE = new ExpenseAttachmentQueryOrder("fileSize");
	public static final ExpenseAttachmentQueryOrder FILENAME = new ExpenseAttachmentQueryOrder("fileName");
	public static final ExpenseAttachmentQueryOrder DESCRIPTION = new ExpenseAttachmentQueryOrder("description");
	public static final ExpenseAttachmentQueryOrder UPLOADDATE = new ExpenseAttachmentQueryOrder("uploadDate");
    
	protected ExpenseAttachmentQueryOrder(String value) {
		super(value);
	}
	
	public static ExpenseAttachmentQueryOrder getEnum(String value) {
        return (ExpenseAttachmentQueryOrder) getEnum(ExpenseAttachmentQueryOrder.class, value);
    }

}
