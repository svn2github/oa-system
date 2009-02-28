/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.admin;

import java.io.Serializable;

import com.aof.model.metadata.EnabledDisabled;

/**
 * A class that represents a row in the city table.
 * 
 * @author nicebean
 * @version 1.0 (2005-11-15)
 */
public abstract class AbstractCity implements Serializable {
    /**
     * The cached hash code value for this instance. Settting to 0 triggers
     * re-calculation.
     */
    private int hashValue = 0;

    /** The composite primary key value. */
    private java.lang.Integer id;

    /** The value of the province association. */
    private Province province;

    /** The value of the simple engName property. */
    private java.lang.String engName;

    /** The value of the simple chnName property. */
    private java.lang.String chnName;

    /** The value of the simple enabled property. */
    private EnabledDisabled enabled;

    private Site site;

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
     * @return Returns the province.
     */
    public Province getProvince() {
        return province;
    }

    /**
     * @param province
     *            The province to set.
     */
    public void setProvince(Province province) {
        this.province = province;
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
     * Simple constructor of AbstractTCity instances.
     */
    public AbstractCity() {
    }

    /**
     * Constructor of AbstractTCity instances given a simple primary key.
     * 
     * @param id
     */
    public AbstractCity(java.lang.Integer id) {
        this.setId(id);
    }

    /**
     * Return the simple primary key value that identifies this object.
     * 
     * @return java.lang.Short
     */
    public java.lang.Integer getId() {
        return id;
    }

    /**
     * Set the simple primary key value that identifies this object.
     * 
     * @param id
     */
    public void setId(java.lang.Integer id) {
        this.hashValue = 0;
        this.id = id;
    }

    /**
     * Return the value of the eng_name column.
     * 
     * @return java.lang.String
     */
    public java.lang.String getEngName() {
        return this.engName;
    }

    /**
     * Set the value of the eng_name column.
     * 
     * @param engName
     */
    public void setEngName(java.lang.String engName) {
        this.engName = engName;
    }

    /**
     * Return the value of the chn_name column.
     * 
     * @return java.lang.String
     */
    public java.lang.String getChnName() {
        return this.chnName;
    }

    /**
     * Set the value of the chn_name column.
     * 
     * @param chnName
     */
    public void setChnName(java.lang.String chnName) {
        this.chnName = chnName;
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
        if (!(rhs instanceof City))
            return false;
        City that = (City) rhs;
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
            int cityIdValue = this.getId() == null ? 0 : this.getId().hashCode();
            result = result * 37 + cityIdValue;
            this.hashValue = result;
        }
        return this.hashValue;
    }

}
