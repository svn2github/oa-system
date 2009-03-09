/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.business.expense;

import java.io.Serializable;

/**
 * A class that represents a row in the 'exp_row_det_hist' table.
 * 
 * @author nicebean
 * @version 1.0 (2005-11-18)
 */
public class ExpenseHistoryRow extends AbstractExpenseHistoryRow implements Serializable {
    /**
     * Simple constructor of ExpenseHistoryRow instances.
     */
    public ExpenseHistoryRow() {
    }

    /**
     * Constructor of ExpenseHistoryRow instances given a simple primary key.
     * 
     * @param id
     */
    public ExpenseHistoryRow(Integer id) {
        super(id);
    }

    /* Add customized code below */

}
