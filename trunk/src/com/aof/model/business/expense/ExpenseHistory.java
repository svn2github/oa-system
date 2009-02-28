/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.business.expense;

import java.io.Serializable;

/**
 * A class that represents a row in the 'expense_history' table.
 * 
 * @author nicebean
 * @version 1.0 (2005-11-18)
 */
public class ExpenseHistory extends AbstractExpenseHistory implements Serializable {
    /**
     * Simple constructor of ExpenseHistory instances.
     */
    public ExpenseHistory() {
    }

    /**
     * Constructor of ExpenseHistory instances given a simple primary key.
     * 
     * @param id
     */
    public ExpenseHistory(Integer id) {
        super(id);
    }

    /* Add customized code below */

}
