/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.business.pr;

import java.io.Serializable;

import net.sourceforge.model.admin.Department;

/**
 * 该类代表yearly_budget_department表中的一行
 * 
 * @author nicebean
 * @version 1.0 (2005-11-19)
 */
public abstract class AbstractYearlyBudgetDepartment implements Serializable {
    /**
     * The cached hash code value for this instance. Settting to 0 triggers
     * re-calculation.
     */
    private int hashValue = 0;

    /** identifier field */
    private Integer id;

    /** persistent field */
    private YearlyBudget yearlyBudget;

    /** persistent field */
    private Department department;

    /** default constructor */
    public AbstractYearlyBudgetDepartment() {
    }

    /** minimal constructor */
    public AbstractYearlyBudgetDepartment(Integer id) {
        setId(id);
    }

    /**
     * @return Returns the department.
     */
    public Department getDepartment() {
        return department;
    }

    /**
     * @param department
     *            The department to set.
     */
    public void setDepartment(Department department) {
        this.department = department;
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
     * @return Returns the yearlyBudget.
     */
    public YearlyBudget getYearlyBudget() {
        return yearlyBudget;
    }

    /**
     * @param yearlyBudget
     *            The yearlyBudget to set.
     */
    public void setYearlyBudget(YearlyBudget yearlyBudget) {
        this.yearlyBudget = yearlyBudget;
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
        if (!(rhs instanceof YearlyBudgetDepartment))
            return false;
        YearlyBudgetDepartment that = (YearlyBudgetDepartment) rhs;
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
