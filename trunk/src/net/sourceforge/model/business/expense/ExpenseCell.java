/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.business.expense;

import java.io.Serializable;

/**
 * A class that represents a row in the 'expense_cell_det' table.
 * 
 * @author nicebean
 * @version 1.0 (2005-11-18)
 */
public class ExpenseCell extends AbstractExpenseCell implements Serializable {
    /**
     * Simple constructor of ExpenseCell instances.
     */
    public ExpenseCell() {
    }

    /**
     * Constructor of ExpenseCell instances given a simple primary key.
     * 
     * @param id
     */
    public ExpenseCell(Integer id) {
        super(id);
    }

    /* Add customized code below */

}
