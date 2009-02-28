/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.business.expense;

import java.io.Serializable;
import java.util.Date;

import com.aof.model.admin.ProjectCode;

/**
 * 该类代表exp_row_det_hist表中的一行
 * 
 * @author nicebean
 * @version 1.0 (2005-11-18)
 */
public abstract class AbstractExpenseHistoryRow implements Serializable {
    /**
     * The cached hash code value for this instance. Settting to 0 triggers
     * re-calculation.
     */
    private int hashValue = 0;

    /** identifier field */
    private Integer id;

    /** persistent field */
    private Date date;

    /** persistent field */
    private ExpenseHistory expenseHistory;
    
    private ProjectCode projectCode;

    public ProjectCode getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(ProjectCode projectCode) {
        this.projectCode = projectCode;
    }

    /** default constructor */
    public AbstractExpenseHistoryRow() {
    }

    /** minimal constructor */
    public AbstractExpenseHistoryRow(Integer id) {
        setId(id);
    }

    /**
     * @return Returns the date.
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date
     *            The date to set.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return Returns the expenseHistory.
     */
    public ExpenseHistory getExpenseHistory() {
        return expenseHistory;
    }

    /**
     * @param expenseHistory
     *            The expenseHistory to set.
     */
    public void setExpenseHistory(ExpenseHistory expenseHistory) {
        this.expenseHistory = expenseHistory;
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
        if (!(rhs instanceof ExpenseHistoryRow))
            return false;
        ExpenseHistoryRow that = (ExpenseHistoryRow) rhs;
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
