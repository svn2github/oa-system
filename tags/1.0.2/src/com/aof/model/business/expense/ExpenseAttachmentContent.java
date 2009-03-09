/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.business.expense;

import java.io.Serializable;

/**
 * A class that represents a row in the 'expense_attach' table (only
 * file_content column).
 * 
 * @author nicebean
 * @version 1.0 (2005-11-18)
 */
public class ExpenseAttachmentContent extends AbstractExpenseAttachmentContent implements Serializable {
    /**
     * Simple constructor of ExpenseAttachmentContent instances.
     */
    public ExpenseAttachmentContent() {
    }

    /**
     * Constructor of ExpenseAttachmentContent instances given a simple primary
     * key.
     * 
     * @param id
     */
    public ExpenseAttachmentContent(Integer id) {
        super(id);
    }

    /* Add customized code below */

}
