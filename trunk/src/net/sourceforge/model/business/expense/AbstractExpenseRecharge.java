/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.business.expense;

import java.io.Serializable;

import net.sourceforge.model.business.BaseRecharge;

/**
 * 该类代表expense_recharge表中的一行
 * 
 * @author nicebean
 * @version 1.0 (2005-11-18)
 */
public abstract class AbstractExpenseRecharge extends BaseRecharge implements Serializable {
    /** persistent field */
    private Expense expense;

    /** default constructor */
    public AbstractExpenseRecharge() {
    }

    /** minimal constructor */
    public AbstractExpenseRecharge(Integer id) {
        super(id);
    }

    /**
     * @return Returns the expense.
     */
    public Expense getExpense() {
        return expense;
    }

    /**
     * @param expense
     *            The expense to set.
     */
    public void setExpense(Expense expense) {
        this.expense = expense;
    }

    /**
     * Implementation of the equals comparison on the basis of equality of the
     * primary key values.
     * 
     * @param rhs
     * @return boolean
     */
    public boolean equals(Object rhs) {
        if (rhs == null)
            return false;
        if (this == rhs)
            return true;
        if (!(rhs instanceof ExpenseRecharge))
            return false;
        ExpenseRecharge that = (ExpenseRecharge) rhs;
        if (this.getId() != null)
            return this.getId().equals(that.getId());
        return that.getId() == null;
    }

}
