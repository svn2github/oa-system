/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.business.pr;

import java.io.Serializable;

/**
 * A class that represents a row in the 'yearly_bgt_dep_history' table.
 * 
 * @author nicebean
 * @version 1.0 (2005-11-19)
 */
public class YearlyBudgetHistoryDepartment extends AbstractYearlyBudgetHistoryDepartment implements Serializable {
    /**
     * Simple constructor of YearlyBudgetHistoryDepartment instances.
     */
    public YearlyBudgetHistoryDepartment() {
    }

    /**
     * Constructor of YearlyBudgetHistoryDepartment instances given a simple primary key.
     * 
     * @param id
     */
    public YearlyBudgetHistoryDepartment(Integer id) {
        super(id);
    }

    /* Add customized code below */

}
