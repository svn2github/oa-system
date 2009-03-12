/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.model.business.expense.query;

import org.apache.commons.lang.enums.Enum;

/**
 * ExpenseAttachment查询条件的枚举类
 * @author ych
 * @version 1.0 (Nov 23, 2005)
 */
public class ExpenseAttachmentQueryCondition extends Enum{

	/*id*/
	public static final ExpenseAttachmentQueryCondition ID_EQ =
		 new ExpenseAttachmentQueryCondition("id_eq");

	/*keyPropertyList*/

	/*kmtoIdList*/

	/*mtoIdList*/
	public static final ExpenseAttachmentQueryCondition EXPENSE_ID_EQ =
		 new ExpenseAttachmentQueryCondition("expense_id_eq");

	/*property*/
	public static final ExpenseAttachmentQueryCondition FILESIZE_EQ =
		 new ExpenseAttachmentQueryCondition("fileSize_eq");
	public static final ExpenseAttachmentQueryCondition FILENAME_LIKE =
		 new ExpenseAttachmentQueryCondition("fileName_like");
	public static final ExpenseAttachmentQueryCondition DESCRIPTION_LIKE =
		 new ExpenseAttachmentQueryCondition("description_like");
	public static final ExpenseAttachmentQueryCondition UPLOADDATE_EQ =
		 new ExpenseAttachmentQueryCondition("uploadDate_eq");

	protected ExpenseAttachmentQueryCondition(String value) {
		super(value);
	}
	
	public static ExpenseAttachmentQueryCondition getEnum(String value) {
        return (ExpenseAttachmentQueryCondition) getEnum(ExpenseAttachmentQueryCondition.class, value);
    }

}
