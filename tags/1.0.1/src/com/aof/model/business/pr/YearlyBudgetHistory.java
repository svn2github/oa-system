/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.business.pr;

import java.io.Serializable;

/**
 * A class that represents a row in the 'yearly_budget_history' table.
 * 
 * @author nicebean
 * @version 1.0 (2005-11-19)
 */
public class YearlyBudgetHistory extends AbstractYearlyBudgetHistory implements Serializable {
    /**
     * Simple constructor of YearlyBudgetHistory instances.
     */
    public YearlyBudgetHistory() {
    }

    /**
     * Constructor of YearlyBudgetHistory instances given a simple primary key.
     * 
     * @param id
     */
    public YearlyBudgetHistory(Integer id) {
        super(id);
    }

    /* Add customized code below */

}
