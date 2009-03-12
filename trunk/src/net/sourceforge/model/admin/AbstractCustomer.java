/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.admin;

import java.io.Serializable;

import net.sourceforge.model.metadata.CustomerType;
import net.sourceforge.model.metadata.EnabledDisabled;

/**
 * A class that represents a row in the customer table.
 * 
 * @author nicebean
 * @version 1.0 (2005-11-15)
 */
public abstract class AbstractCustomer implements Serializable {
    /**
     * The cached hash code value for this instance. Settting to 0 triggers
     * re-calculation.
     */
    private int hashValue = 0;

    /** The composite primary key value. */
    private java.lang.String code;

    /** The value of the tSite association. */
    private Site site;

    /** The value of the simple description property. */
    private java.lang.String description;

    /** The value of the simple custType property. */
    private CustomerType type;

    /** The value of the simple custType property. */
    private EnabledDisabled enabled;

    /**
     * Simple constructor of AbstractCustomer instances.
     */
    public AbstractCustomer() {
    }

    /**
     * Constructor of AbstractCustomer instances given a simple primary key.
     * 
     * @param code
     */
    public AbstractCustomer(java.lang.String code) {
        this.setCode(code);
    }

    /**
     * Return the value of the description column.
     * 
     * @return java.lang.String
     */
    public java.lang.String getDescription() {
        return this.description;
    }

    /**
     * Set the value of the description column.
     * 
     * @param description
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }

    /**
     * @return Returns the code.
     */
    public java.lang.String getCode() {
        return code;
    }

    /**
     * @param code
     *            The code to set.
     */
    public void setCode(java.lang.String code) {
        this.code = code;
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
    public CustomerType getType() {
        return type;
    }

    /**
     * @param type
     *            The type to set.
     */
    public void setType(CustomerType type) {
        this.type = type;
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
        if (!(rhs instanceof Customer))
            return false;
        Customer that = (Customer) rhs;
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
            int custCodeValue = this.getCode() == null ? 0 : this.getCode().hashCode();
            result = result * 37 + custCodeValue;
            this.hashValue = result;
        }
        return this.hashValue;
    }
}
