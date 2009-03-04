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
 * A class that represents a row in the department table.
 * 
 * @author nicebean
 * @version 1.0 (2005-11-15)
 */
public abstract class AbstractDepartment implements Serializable {
    /**
     * The cached hash code value for this instance. Settting to 0 triggers
     * re-calculation.
     */
    private int hashValue = 0;

    /** The composite primary key value. */
    private java.lang.Integer id;

    /** The value of the tDepartment association. */
    private Department parentDepartment;

    private Site site;

    private String name;

    private User manager;

    /** The value of the simple enabled property. */
    private EnabledDisabled enabled;

    /**
     * Simple constructor of AbstractDepartment instances.
     */
    public AbstractDepartment() {
    }

    /**
     * Constructor of AbstractDepartment instances given a simple primary key.
     * 
     * @param id
     */
    public AbstractDepartment(java.lang.Integer id) {
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
     * @return Returns the manager.
     */
    public User getManager() {
        return manager;
    }

    /**
     * @param manager
     *            The manager to set.
     */
    public void setManager(User manager) {
        this.manager = manager;
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
     * @return Returns the parentDepartment.
     */
    public Department getParentDepartment() {
        return parentDepartment;
    }

    /**
     * @param parentDepartment
     *            The parentDepartment to set.
     */
    public void setParentDepartment(Department parentDepartment) {
        this.parentDepartment = parentDepartment;
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
        if (!(rhs instanceof Department))
            return false;
        Department that = (Department) rhs;
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
