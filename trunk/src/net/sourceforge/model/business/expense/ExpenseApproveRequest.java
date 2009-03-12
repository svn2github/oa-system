/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.business.expense;

import java.io.Serializable;

import net.sourceforge.model.admin.User;

/**
 * A class that represents a row in the 'expense_approve' table.
 * 
 * @author nicebean
 * @version 1.0 (2005-11-18)
 */
public class ExpenseApproveRequest extends AbstractExpenseApproveRequest implements Serializable {
    /**
     * Simple constructor of ExpenseApproveRequest instances.
     */
    public ExpenseApproveRequest() {
    }

    /**
     * 给定主键的构造函数
     * 
     * @param id
     * @param approver
     */
    public ExpenseApproveRequest(String id, User approver) {
        super(id, approver);
    }

    /* Add customized code below */

}
