/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.business.expense;

import java.io.Serializable;
import java.math.BigDecimal;

import com.aof.model.admin.ExpenseSubCategory;

/**
 * 该类代表exp_cell_det_hist表中的一行
 * 
 * @author nicebean
 * @version 1.0 (2005-11-18)
 */
public abstract class AbstractExpenseHistoryCell implements Serializable {
    /**
     * The cached hash code value for this instance. Settting to 0 triggers
     * re-calculation.
     */
    private int hashValue = 0;

    /** identifier field */
    private Integer id;

    /** persistent field */
    private BigDecimal amount;

    /** persistent field */
    private ExpenseHistoryRow row;

    /** persistent field */
    private ExpenseSubCategory expenseSubCategory;

    /** nullable persistent field */
    private String description;

    /** default constructor */
    public AbstractExpenseHistoryCell() {
    }

    /** minimal constructor */
    public AbstractExpenseHistoryCell(Integer id) {
        setId(id);
    }

    /**
     * @return Returns the amount.
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount
     *            The amount to set.
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * @return Returns the expenseSubCategory.
     */
    public ExpenseSubCategory getExpenseSubCategory() {
        return expenseSubCategory;
    }

    /**
     * @param expenseSubCategory
     *            The expenseSubCategory to set.
     */
    public void setExpenseSubCategory(ExpenseSubCategory expenseSubCategory) {
        this.expenseSubCategory = expenseSubCategory;
    }

    /**
     * @return Returns the id.
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     *            The id to set.
     */
    protected void setId(Integer id) {
        this.hashValue = 0;
        this.id = id;
    }

    /**
     * @return Returns the row.
     */
    public ExpenseHistoryRow getRow() {
        return row;
    }

    /**
     * @param row
     *            The row to set.
     */
    public void setRow(ExpenseHistoryRow row) {
        this.row = row;
    }

    /**
     * @return Returns the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     *            The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
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
        if (!(rhs instanceof ExpenseHistoryCell))
            return false;
        ExpenseHistoryCell that = (ExpenseHistoryCell) rhs;
        if (this.getId() != null)
            return this.getId().equals(that.getId());
        return that.getId() == null;
    }

    /**
     * Implementation of the hashCode method conforming to the Bloch pattern
     * with the exception of array properties (these are very unlikely primary
     * key types).
     * 
     * @return int
     */
    public int hashCode() {
        if (this.hashValue == 0) {
            int result = 17;
            int idValue = this.getId() == null ? 0 : this.getId().hashCode();
            result = result * 37 + idValue;
            this.hashValue = result;
        }
        return this.hashValue;
    }

}
