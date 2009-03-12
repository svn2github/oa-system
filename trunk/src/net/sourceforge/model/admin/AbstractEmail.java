/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.admin;

import java.io.Serializable;

import net.sourceforge.model.metadata.YesNo;

/**
 * A class that represents a row in the t_email table.
 * 
 * @author nicebean
 * @version 1.0 (2005-11-15)
 */
public abstract class AbstractEmail implements Serializable {
    /**
     * The cached hash code value for this instance. Settting to 0 triggers
     * re-calculation.
     */
    private int hashValue = 0;

    /** The composite primary key value. */
    private java.lang.Integer id;

    /** The value of the simple mailfrom property. */
    private java.lang.String mailFrom;

    /** The value of the simple mailto property. */
    private java.lang.String mailTo;

    /** The value of the simple subject property. */
    private java.lang.String subject;

    private java.util.Date createTime;

    private java.util.Date sentTime;

    private java.lang.Integer failCount;
    
    private Site site;

    /** The value of the simple waitToSend property. */
    private YesNo waitToSend;

    /**
     * Simple constructor of AbstractEmail instances.
     */
    public AbstractEmail() {
    }

    /**
     * Constructor of AbstractEmail instances given a simple primary key.
     * 
     * @param emailId
     */
    public AbstractEmail(java.lang.Integer id) {
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
     * Return the value of the mailFrom column.
     * 
     * @return java.lang.String
     */
    public java.lang.String getMailFrom() {
        return this.mailFrom;
    }

    /**
     * Set the value of the mailFrom column.
     * 
     * @param mailfrom
     */
    public void setMailFrom(java.lang.String mailFrom) {
        this.mailFrom = mailFrom;
    }

    /**
     * Return the value of the mailTo column.
     * 
     * @return java.lang.String
     */
    public java.lang.String getMailTo() {
        return this.mailTo;
    }

    /**
     * Set the value of the mailTo column.
     * 
     * @param mailto
     */
    public void setMailTo(java.lang.String mailTo) {
        this.mailTo = mailTo;
    }

    /**
     * Return the value of the subject column.
     * 
     * @return java.lang.String
     */
    public java.lang.String getSubject() {
        return this.subject;
    }

    /**
     * Set the value of the subject column.
     * 
     * @param subject
     */
    public void setSubject(java.lang.String subject) {
        this.subject = subject;
    }

    /**
     * @return Returns the waitToSend.
     */
    public YesNo getWaitToSend() {
        return waitToSend;
    }

    /**
     * @param waitToSend
     *            The waitToSend to set.
     */
    public void setWaitToSend(YesNo waitToSend) {
        this.waitToSend = waitToSend;
    }

    /**
     * @return Returns the createTime.
     */
    public java.util.Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     *            The createTime to set.
     */
    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return Returns the failCount.
     */
    public java.lang.Integer getFailCount() {
        return failCount;
    }

    /**
     * @param failCount
     *            The failCount to set.
     */
    public void setFailCount(java.lang.Integer failCount) {
        this.failCount = failCount;
    }

    /**
     * @return Returns the sentTime.
     */
    public java.util.Date getSentTime() {
        return sentTime;
    }

    /**
     * @param sentTime
     *            The sentTime to set.
     */
    public void setSentTime(java.util.Date sentTime) {
        this.sentTime = sentTime;
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
        Email that = (Email) rhs;
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

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }
}
