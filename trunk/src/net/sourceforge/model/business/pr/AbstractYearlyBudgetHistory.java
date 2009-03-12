/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.business.pr;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import net.sourceforge.model.admin.User;

/**
 * 该类代表yearly_budget_history表中的一行
 * 
 * @author nicebean
 * @version 1.0 (2005-11-19)
 */
public abstract class AbstractYearlyBudgetHistory implements Serializable {
    /**
     * The cached hash code value for this instance. Settting to 0 triggers
     * re-calculation.
     */
    private int hashValue = 0;

    /** identifier field */
    private Integer id;

    /** persistent field */
    private int version;

    /** persistent field */
    private String name;

    /** persistent field */
    private BigDecimal amount;

    /** persistent field */
    private Date createDate;

    /** persistent field */
    private YearlyBudget yearlyBudget;

    /** persistent field */
    private User creator;

    /** default constructor */
    public AbstractYearlyBudgetHistory() {
    }

    /** minimal constructor */
    public AbstractYearlyBudgetHistory(Integer id) {
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
     * @return Returns the createDate.
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate
     *            The createDate to set.
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return Returns the creator.
     */
    public User getCreator() {
        return creator;
    }

    /**
     * @param creator
     *            The creator to set.
     */
    public void setCreator(User creator) {
        this.creator = creator;
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
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Returns the version.
     */
    public int getVersion() {
        return version;
    }

    /**
     * @param version
     *            The version to set.
     */
    public void setVersion(int version) {
        this.version = version;
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
        if (!(rhs instanceof YearlyBudgetHistory))
            return false;
        YearlyBudgetHistory that = (YearlyBudgetHistory) rhs;
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
    
    private Set departments;

    public Set getDepartments() {
        return departments;
    }

    public void setDepartments(Set departments) {
        this.departments = departments;
    }

}
