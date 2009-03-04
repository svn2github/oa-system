/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.business.pr;

import java.io.Serializable;

/**
 * A class that represents a row in the 'yearly_budget_department' table.
 * 
 * @author nicebean
 * @version 1.0 (2005-11-19)
 */
public class YearlyBudgetDepartment extends AbstractYearlyBudgetDepartment implements Serializable {
    /**
     * Simple constructor of YearlyBudgetDepartment instances.
     */
    public YearlyBudgetDepartment() {
    }

    /**
     * Constructor of YearlyBudgetDepartment instances given a simple primary key.
     * 
     * @param id
     */
    public YearlyBudgetDepartment(Integer id) {
        super(id);
    }

    /* Add customized code below */

}
