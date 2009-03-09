/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.web.struts.form.admin;



import com.aof.model.admin.query.SystemLogQueryOrder;
import com.aof.web.struts.form.BaseSessionQueryForm;


/**
 * @author ych
 * @version 1.0 (Feb 20, 2006)
 */
public class SystemLogQueryForm extends BaseSessionQueryForm {

    private String siteId;
    
    private String userId;
    
    private String userName;
    
    private String targetObject;
    
    private String actionDateFrom;
    
    private String actionDateTo;
    
    
    
    
    

    /**
     * @return Returns the actionDateFrom.
     */
    public String getActionDateFrom() {
        return actionDateFrom;
    }




    /**
     * @param actionDateFrom The actionDateFrom to set.
     */
    public void setActionDateFrom(String actionDateFrom) {
        this.actionDateFrom = actionDateFrom;
    }




    /**
     * @return Returns the actionDateTo.
     */
    public String getActionDateTo() {
        return actionDateTo;
    }




    /**
     * @param actionDateTo The actionDateTo to set.
     */
    public void setActionDateTo(String actionDateTo) {
        this.actionDateTo = actionDateTo;
    }




    /**
     * @return Returns the siteId.
     */
    public String getSiteId() {
        return siteId;
    }




    /**
     * @param siteId The siteId to set.
     */
    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }


    
    

    /**
     * @return Returns the targetObject.
     */
    public String getTargetObject() {
        return targetObject;
    }




    /**
     * @param targetObject The targetObject to set.
     */
    public void setTargetObject(String targetObject) {
        this.targetObject = targetObject;
    }




    /**
     * @return Returns the userId.
     */
    public String getUserId() {
        return userId;
    }




    /**
     * @param userId The userId to set.
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }




    /**
     * @return Returns the userName.
     */
    public String getUserName() {
        return userName;
    }




    /**
     * @param userName The userName to set.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    
    public SystemLogQueryForm()
    {
        this.setOrder(SystemLogQueryOrder.SITE_NAME.getName());
        this.setDescend(false);
    }
}
