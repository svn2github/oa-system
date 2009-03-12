/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.business.expense;

import java.io.Serializable;

/**
 * A class that represents a row in the 'expense_attach' table (exclude
 * file_content column).
 * 
 * @author nicebean
 * @version 1.0 (2005-11-18)
 */
public class ExpenseAttachment extends AbstractExpenseAttachment implements Serializable {
    /**
     * Simple constructor of ExpenseAttachment instances.
     */
    public ExpenseAttachment() {
    }

    /**
     * Constructor of ExpenseAttachment instances given a simple primary key.
     * 
     * @param id
     */
    public ExpenseAttachment(Integer id) {
        super(id);
    }

    /* Add customized code below */

}
