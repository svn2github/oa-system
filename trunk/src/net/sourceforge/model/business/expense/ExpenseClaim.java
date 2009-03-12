/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.business.expense;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A class that represents a row in the 'expense_claim' table.
 * 
 * @author nicebean
 * @version 1.0 (2005-11-18)
 */
public class ExpenseClaim extends AbstractExpenseClaim implements Serializable {
    /**
     * Simple constructor of ExpenseClaim instances.
     */
    public ExpenseClaim() {
    }

    /**
     * Constructor of ExpenseClaim instances given a simple primary key.
     * 
     * @param id
     */
    public ExpenseClaim(Integer id) {
        super(id);
    }

    /* Add customized code below */

    private BigDecimal enterAmount;

    /**
     * @return Returns the enterAmount.
     */
    public BigDecimal getEnterAmount() {
        return enterAmount;
    }

    /**
     * @param enterAmount The enterAmount to set.
     */
    public void setEnterAmount(BigDecimal enterAmount) {
        this.enterAmount = enterAmount;
    }
    
    
}
