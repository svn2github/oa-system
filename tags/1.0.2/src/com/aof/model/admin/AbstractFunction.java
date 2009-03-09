/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.admin;

import java.io.Serializable;

/**
 * A class that represents a row in the function table.
 * 
 * @author nicebean
 * @version 1.0 (2005-11-15)
 */
public abstract class AbstractFunction implements Serializable {
    /**
     * The cached hash code value for this instance. Settting to 0 triggers
     * re-calculation.
     */
    private int hashValue = 0;

    /** The composite primary key value. */
    private java.lang.Integer id;

    /** The value of the simple name property. */
    private java.lang.String name;

    /** The value of the simple description property. */
    private java.lang.String description;

    /**
     * Simple constructor of AbstractFunction instances.
     */
    public AbstractFunction() {
    }

    /**
     * Constructor of AbstractFunction instances given a simple primary key.
     * 
     * @param id
     */
    public AbstractFunction(java.lang.Integer id) {
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
     * @param id
     */
    public void setId(java.lang.Integer id) {
        this.hashValue = 0;
        this.id = id;
    }

    /**
     * @return Returns the name.
     */
    public java.lang.String getName() {
        return name;
    }

    /**
     * @param name
     *            The name to set.
     */
    public void setName(java.lang.String name) {
        this.name = name;
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
        if (!(rhs instanceof Function))
            return false;
        Function that = (Function) rhs;
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
