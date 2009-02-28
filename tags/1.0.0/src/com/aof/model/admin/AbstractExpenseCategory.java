/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.admin;

import java.io.Serializable;

import com.aof.model.metadata.EnabledDisabled;
import com.aof.model.metadata.ExpenseType;

/**
 * A class that represents a row in the expense_ctgy table.
 * 
 * @author nicebean
 * @version 1.0 (2005-11-15)
 */
public abstract class AbstractExpenseCategory implements Serializable {
    /**
     * The cached hash code value for this instance. Settting to 0 triggers
     * re-calculation.
     */
    private int hashValue = 0;

    /** The composite primary key value. */
    private java.lang.Integer id;

    /** The value of the tSite association. */
    private Site site;

    /** The value of the simple expCateDesc property. */
    private java.lang.String description;

    /** The value of the simple expCateType property. */
    private ExpenseType type;

    /** The value of the simple refErpId property. */
    private java.lang.String referenceErpId;

    /** The value of the simple enabled property. */
    private EnabledDisabled enabled;

    /**
     * Simple constructor of AbstractExpenseCategory instances.
     */
    public AbstractExpenseCategory() {
    }

    /**
     * Constructor of AbstractExpenseCategory instances given a simple primary
     * key.
     * 
     * @param id
     */
    public AbstractExpenseCategory(java.lang.Integer id) {
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
     * @param expCateId
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
     * @return Returns the site.
     */
    public Site getSite() {
        return site;
    }

    /**
     * @param site
     *            The site to set.
     */
    public void setSite(Site site) {
        this.site = site;
    }

    /**
     * @return Returns the type.
     */
    public ExpenseType getType() {
        return type;
    }

    /**
     * @param type
     *            The type to set.
     */
    public void setType(ExpenseType type) {
        this.type = type;
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
        if (!(rhs instanceof ExpenseCategory))
            return false;
        ExpenseCategory that = (ExpenseCategory) rhs;
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
