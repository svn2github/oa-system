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
public abstract class AbstractEmailBatch implements Serializable {
    /**
     * The cached hash code value for this instance. Settting to 0 triggers
     * re-calculation.
     */
    private int hashValue = 0;

    /** The composite primary key value. */
    private java.lang.Integer id;

    private User mailToUser;
    
    private Site site;
    
    private String templateName;
    
    private String refNo;
    
    private YesNo isSend;

    /**
     * Simple constructor of AbstractEmail instances.
     */
    public AbstractEmailBatch() {
    }

    /**
     * Constructor of AbstractEmail instances given a simple primary key.
     * 
     * @param emailId
     */
    public AbstractEmailBatch(java.lang.Integer id) {
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
     * @return Returns the mailToUser.
     */
    public User getMailToUser() {
        return mailToUser;
    }

    /**
     * @param mailToUser The mailToUser to set.
     */
    public void setMailToUser(User mailToUser) {
        this.mailToUser = mailToUser;
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

    /**
     * @return Returns the templateName.
     */
    public String getTemplateName() {
        return templateName;
    }

    /**
     * @param templateName The templateName to set.
     */
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    /**
     * @return Returns the isSend.
     */
    public YesNo getIsSend() {
        return isSend;
    }

    /**
     * @param isSend The isSend to set.
     */
    public void setIsSend(YesNo isSend) {
        this.isSend = isSend;
    }

    /**
     * @return Returns the refNo.
     */
    public String getRefNo() {
        return refNo;
    }

    /**
     * @param refNo The refNo to set.
     */
    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }
    
    
}
