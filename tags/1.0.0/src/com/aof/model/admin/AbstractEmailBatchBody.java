/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.admin;

import java.io.Serializable;
import java.sql.Clob;

/**
 * A class that represents a row in the t_email table.
 * 
 * @author nicebean
 * @version 1.0 (2005-11-15)
 */
public abstract class AbstractEmailBatchBody implements Serializable {
    /**
     * The cached hash code value for this instance. Settting to 0 triggers
     * re-calculation.
     */
    private int hashValue = 0;

    /** The composite primary key value. */
    private java.lang.Integer id;

    /** The value of the simple body property. */
    private Clob body;

    /**
     * Simple constructor of AbstractEmailBody instances.
     */
    public AbstractEmailBatchBody() {
    }

    /**
     * Constructor of AbstractEmailBody instances given a simple primary key.
     * 
     * @param emailId
     */
    public AbstractEmailBatchBody(java.lang.Integer id) {
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
     * @param emailId
     */
    public void setId(java.lang.Integer id) {
        this.hashValue = 0;
        this.id = id;
    }

    /**
     * Return the value of the body column.
     * 
     * @return Clob
     */
    public Clob getBody() {
        return this.body;
    }

    /**
     * Set the value of the body column.
     * 
     * @param body
     */
    public void setBody(Clob body) {
        this.body = body;
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
        if (!(rhs instanceof Email))
            return false;
        EmailBody that = (EmailBody) rhs;
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
            int emailIdValue = this.getId() == null ? 0 : this.getId().hashCode();
            result = result * 37 + emailIdValue;
            this.hashValue = result;
        }
        return this.hashValue;
    }

}
