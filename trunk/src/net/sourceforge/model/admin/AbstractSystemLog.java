/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.admin;

import java.io.Serializable;
import java.util.Date;

/**
 * A class that represents a row in the city table.
 * 
 * @author nicebean
 * @version 1.0 (2005-11-15)
 */
public abstract class AbstractSystemLog implements Serializable {
    /**
     * The cached hash code value for this instance. Settting to 0 triggers
     * re-calculation.
     */
    private int hashValue = 0;

    /** The simple primary key value. */
    private Integer id;

    /** The value of the site association. */
    private Site site;

    /** The value of the simple target property. */
    private String target;

    /** The value of the simple targetId property. */
    private String targetId;

    /** The value of the simple action property. */
    private String action;

    /** The value of the simple content property. */
    private String content;

    /** The value of the simple content property. */
    private Date actionTime;

    /** The value of the site association. */
    private User user;

    /**
     * Simple constructor of AbstractSystemLog instances.
     */
    public AbstractSystemLog() {
    }

    /**
     * Constructor of AbstractSystemLog instances given a simple primary key.
     * 
     * @param id
     */
    public AbstractSystemLog(Integer id) {
        this.setId(id);
    }

    /**
     * @return Returns the action.
     */
    public String getAction() {
        return action;
    }

    /**
     * @param action
     *            The action to set.
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * @return Returns the user.
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user
     *            The user to set.
     */
    public void setUser(User user) {
        this.user = user;
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
     * @param id
     */
    protected void setId(Integer id) {
        this.hashValue = 0;
        this.id = id;
    }

    /**
     * Return the value of the object column.
     * 
     * @return String
     */
    public String getTarget() {
        return this.target;
    }

    /**
     * Set the value of the object column.
     * 
     * @param target
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * Return the value of the obj_id column.
     * 
     * @return String
     */
    public String getTargetId() {
        return this.targetId;
    }

    /**
     * Set the value of the obj_id column.
     * 
     * @param targetId
     */
    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    /**
     * Return the value of the key_field column.
     * 
     * @return String
     */
    public String getContent() {
        return this.content;
    }

    /**
     * Set the value of the key_field column.
     * 
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Return the value of the action_time column.
     * 
     * @return Date
     */
    public Date getActionTime() {
        return this.actionTime;
    }

    /**
     * Set the value of the action_time column.
     * 
     * @param actionTime
     */
    public void setActionTime(Date actionTime) {
        this.actionTime = actionTime;
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
        SystemLog that = (SystemLog) rhs;
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
