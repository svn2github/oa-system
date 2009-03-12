/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.admin;

import java.io.Serializable;

import net.sourceforge.model.metadata.EnabledDisabled;

/**
 * A class that represents a row in the currency table.
 * 
 * @author nicebean
 * @version 1.0 (2005-11-15)
 */
public abstract class AbstractCurrency implements Serializable {
    /**
     * The cached hash code value for this instance. Settting to 0 triggers
     * re-calculation.
     */
    private int hashValue = 0;

    /** The composite primary key value. */
    private java.lang.String code;

    /** The value of the simple currName property. */
    private java.lang.String name;

    /** The value of the simple enabled property. */
    private EnabledDisabled enabled;

    /**
     * Simple constructor of AbstractCurrency instances.
     */
    public AbstractCurrency() {
    }

    /**
     * Constructor of AbstractCurrency instances given a simple primary key.
     * 
     * @param code
     */
    public AbstractCurrency(java.lang.String code) {
        this.setCode(code);
    }

    /**
     * Return the simple primary key value that identifies this object.
     * 
     * @return java.lang.String
     */
    public java.lang.String getCode() {
        return code;
    }

    /**
     * Set the simple primary key value that identifies this object.
     * 
     * @param currCode
     */
    public void setCode(java.lang.String code) {
        this.hashValue = 0;
        this.code = code;
    }

    public java.lang.String getName() {
        return name;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }

    /**
     * Return the value of the enabled column.
     * 
     * @return java.lang.Integer
     */
    public EnabledDisabled getEnabled() {
        return this.enabled;
    }

    /**
     * Set the value of the enabled column.
     * 
     * @param enabled
     */
    public void setEnabled(EnabledDisabled enabled) {
        this.enabled = enabled;
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
        if (!(rhs instanceof Currency))
            return false;
        Currency that = (Currency) rhs;
        if (this.getCode() != null)
            return this.getCode().equals(that.getCode());
        return that.getCode() == null;
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
            int currCodeValue = this.getCode() == null ? 0 : this.getCode().hashCode();
            result = result * 37 + currCodeValue;
            this.hashValue = result;
        }
        return this.hashValue;
    }
}
