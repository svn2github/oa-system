/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.business.expense;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A class that represents a row in the 'expense_recharge' table.
 * 
 * @author nicebean
 * @version 1.0 (2005-11-18)
 */
public class ExpenseRecharge extends AbstractExpenseRecharge implements Serializable {
    /**
     * Simple constructor of ExpenseRecharge instances.
     */
    public ExpenseRecharge() {
    }

    /**
     * Constructor of ExpenseRecharge instances given a simple primary key.
     * 
     * @param id
     */
    public ExpenseRecharge(Integer id) {
        super(id);
    }

    /* Add customized code below */

    private BigDecimal outputedAmount = new BigDecimal(0);
    private BigDecimal outputedEnterAmount = new BigDecimal(0);

    public BigDecimal getOutputedAmount() {
        return outputedAmount;
    }

    public void setOutputedAmount(BigDecimal outputedAmount) {
        this.outputedAmount = outputedAmount;
    }

    public BigDecimal getOutputedEnterAmount() {
        return outputedEnterAmount;
    }

    public void setOutputedEnterAmount(BigDecimal outputedEnterAmount) {
        this.outputedEnterAmount = outputedEnterAmount;
    }
    
    
}
