/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.business.expense;

import java.io.Serializable;
import java.math.BigDecimal;

import net.sourceforge.model.admin.ExpenseSubCategory;
import net.sourceforge.model.admin.ProjectCode;

/**
 * 该类代表expense_claim表中的一行
 * 
 * @author nicebean
 * @version 1.0 (2005-11-18)
 */
public abstract class AbstractExpenseClaim implements Serializable {
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
    private String description;

    /** persistent field */
    private Expense expense;

    /** persistent field */
    private ExpenseSubCategory expenseSubCategory;
    
    private ProjectCode projectCode;

    /** default constructor */
    public AbstractExpenseClaim() {
    }

    /** minimal constructor */
    public AbstractExpenseClaim(Integer id) {
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
        hashValue = 0;
        this.id = id;
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
        if (!(rhs instanceof ExpenseClaim))
            return false;
        ExpenseClaim that = (ExpenseClaim) rhs;
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

    public ProjectCode getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(ProjectCode projectCode) {
        this.projectCode = projectCode;
    }

}
