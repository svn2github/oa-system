/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.business.expense;

import java.io.Serializable;

/**
 * A class that represents a row in the 'exp_cell_det_hist' table.
 * 
 * @author nicebean
 * @version 1.0 (2005-11-18)
 */
public class ExpenseHistoryCell extends AbstractExpenseHistoryCell implements Serializable {
    /**
     * Simple constructor of ExpenseHistoryCell instances.
     */
    public ExpenseHistoryCell() {
    }

    /**
     * Constructor of ExpenseHistoryCell instances given a simple primary key.
     * 
     * @param id
     */
    public ExpenseHistoryCell(Integer id) {
        super(id);
    }

    /* Add customized code below */

}
