/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model;

import java.io.Serializable;
import java.sql.Blob;

/**
 * A class that represents a row (only content field) in the attachment like table.
 * @author nicebean
 * @version 1.0 (2005-11-15)
 */
public abstract class BaseAttachmentContent implements Serializable {
    /**
     * The cached hash code value for this instance. Settting to 0 triggers
     * re-calculation.
     */
    private int hashValue = 0;

    /** The composite primary key value. */
    private Integer id;

    /** The value of the simple fileContent property. */
    private Blob fileContent;

    /**
     * Simple constructor of BaseAttachmentContent instances.
     */
    public BaseAttachmentContent() {
    }

    /**
     * Constructor of BaseAttachmentContent instances given a simple primary
     * key.
     * 
     * @param id
     */
    public BaseAttachmentContent(Integer id) {
        this.setId(id);
    }

    /**
     * Return the simple primary key value that identifies this object.
     * 
     * @return Integer
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set the simple primary key value that identifies this object.
     * 
     * @param contractId
     */
    protected void setId(Integer id) {
        this.hashValue = 0;
        this.id = id;
    }

    /**
     * @return Returns the fileContent.
     */
    public Blob getFileContent() {
        return fileContent;
    }

    /**
     * @param fileContent
     *            The fileContent to set.
     */
    public void setFileContent(Blob fileContent) {
        this.fileContent = fileContent;
    }

    /**
     * 子类应该实现自己的equals方法
     */
    public abstract boolean equals(Object rhs);

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
            int contractIdValue = this.getId() == null ? 0 : this.getId().hashCode();
            result = result * 37 + contractIdValue;
            this.hashValue = result;
        }
        return this.hashValue;
    }

}