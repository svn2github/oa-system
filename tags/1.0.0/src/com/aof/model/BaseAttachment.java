/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model;

import java.io.Serializable;
import java.util.Date;

/**
 * A class that represents a row in the attachment like table.
 * @author nicebean
 * @version 1.0 (2005-11-15)
 */
public abstract class BaseAttachment implements Serializable {
    /**
     * The cached hash code value for this instance. Settting to 0 triggers
     * re-calculation.
     */
    private int hashValue = 0;

    private Integer id;

    private String fileName;

    private String description;

    private java.util.Date uploadDate;

    private int fileSize;

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * Simple constructor of BaseAttachment instances.
     */
    public BaseAttachment() {
    }

    /**
     * Constructor of BaseAttachment instances given a simple primary key.
     * 
     * @param id
     */
    public BaseAttachment(Integer id) {
        this.setId(id);
    }

    /**
     * @return Returns the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     *            The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return Returns the fileName.
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName
     *            The fileName to set.
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return Returns the id.
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     *            The id to set.
     */
    protected void setId(Integer id) {
        this.hashValue = 0;
        this.id = id;
    }

    /**
     * @return Returns the uploadDate.
     */
    public Date getUploadDate() {
        return uploadDate;
    }

    /**
     * @param uploadDate
     *            The uploadDate to set.
     */
    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
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