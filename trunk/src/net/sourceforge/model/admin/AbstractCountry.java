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
 * A class that represents a row in the country table.
 * 
 * @author nicebean
 * @version 1.0 (2005-11-15)
 */
public abstract class AbstractCountry implements Serializable {

    /**
     * The cached hash code value for this instance. Settting to 0 triggers
     * re-calculation.
     */
    private int hashValue = 0;

    /** The composite primary key value. */
    private java.lang.Integer id;

    /** The value of the simple shortName property. */
    private java.lang.String shortName;

    /** The value of the simple engName property. */
    private java.lang.String engName;

    /** The value of the simple chnName property. */
    private java.lang.String chnName;

    /** The value of the simple enabled property. */
    private EnabledDisabled enabled;

    private Site site;

    /**
     * Simple constructor of AbstractCountry instances.
     */
    public AbstractCountry() {
    }

    /**
     * Constructor of AbstractCountry instances given a simple primary key.
     * 
     * @param countryId
     */
    public AbstractCountry(java.lang.Integer countryId) {
        this.setId(countryId);
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
     * @param countryId
     */
    public void setId(java.lang.Integer id) {
        this.hashValue = 0;
        this.id = id;
    }

    /**
     * Return the value of the short_name column.
     * 
     * @return java.lang.String
     */
    public java.lang.String getShortName() {
        return this.shortName;
    }

    /**
     * Set the value of the short_name column.
     * 
     * @param shortName
     */
    public void setShortName(java.lang.String shortName) {
        this.shortName = shortName;
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
        if (!(rhs instanceof Country))
            return false;
        Country that = (Country) rhs;
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
