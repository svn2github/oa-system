/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.admin;

import java.io.Serializable;

import net.sourceforge.model.metadata.EnabledDisabled;
import net.sourceforge.model.metadata.YesNo;

/**
 * A class that represents a row in the expense_subctgy table.
 * 
 * @author nicebean
 * @version 1.0 (2005-11-15)
 */
public abstract class AbstractExpenseSubCategory implements Serializable {
    /**
     * The cached hash code value for this instance. Settting to 0 triggers
     * re-calculation.
     */
    private int hashValue = 0;

    /** The composite primary key value. */
    private java.lang.Integer id;

    /** The value of the expenseCategory association. */
    private ExpenseCategory expenseCategory;

    /** The value of the simple description property. */
    private java.lang.String description;

    /** The value of the simple referenceErpId property. */
    private java.lang.String referenceErpId;

    /** The value of the simple enabled property. */
    private EnabledDisabled enabled;

    private YesNo isHotel;

    /**
     * Simple constructor of AbstractExpenseSubCategory instances.
     */
    public AbstractExpenseSubCategory() {
    }

    /**
     * Constructor of AbstractExpenseSubCategory instances given a simple
     * primary key.
     * 
     * @param id
     */
    public AbstractExpenseSubCategory(java.lang.Integer id) {
        this.setId(id);
    }

    /**
     * Return the simple primary key value that identifies this object.
     * 
     * @return java.lang.Integer
     */
    public java.lang.Integer getId() {
        return id;
    }

    /**
     * Set the simple primary key value that identifies this object.
     * 
     * @param expSubcateId
     */
    public void setId(java.lang.Integer id) {
        this.hashValue = 0;
        this.id = id;
    }

    /**
     * @return Returns the description.
     */
    public java.lang.String getDescription() {
        return description;
    }

    /**
     * @param description
     *            The description to set.
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }

    /**
     * @return Returns the enabled.
     */
    public EnabledDisabled getEnabled() {
        return enabled;
    }

    /**
     * @param enabled
     *            The enabled to set.
     */
    public void setEnabled(EnabledDisabled enabled) {
        this.enabled = enabled;
    }

    /**
     * @return Returns the expenseCategory.
     */
    public ExpenseCategory getExpenseCategory() {
        return expenseCategory;
    }

    /**
     * @param expenseCategory
     *            The expenseCategory to set.
     */
    public void setExpenseCategory(ExpenseCategory expenseCategory) {
        this.expenseCategory = expenseCategory;
    }

    /**
     * @return Returns the referenceErpId.
     */
    public java.lang.String getReferenceErpId() {
        return referenceErpId;
    }

    /**
     * @param referenceErpId
     *            The referenceErpId to set.
     */
    public void setReferenceErpId(java.lang.String referenceErpId) {
        this.referenceErpId = referenceErpId;
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
        if (!(rhs instanceof ExpenseSubCategory))
            return false;
        ExpenseSubCategory that = (ExpenseSubCategory) rhs;
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

    public YesNo getIsHotel() {
        return isHotel;
    }

    public void setIsHotel(YesNo isHotel) {
        this.isHotel = isHotel;
    }
}
