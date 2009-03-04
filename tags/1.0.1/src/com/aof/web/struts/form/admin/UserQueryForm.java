/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.web.struts.form.admin;


import com.aof.model.admin.query.UserQueryOrder;
import com.aof.model.metadata.EnabledDisabled;
import com.aof.web.struts.form.BaseSessionQueryForm;

/**
 * ²éÑ¯UserµÄForm
 * 
 * @author nicebean
 * @version 1.0 (2005-11-15)
 */
public class UserQueryForm extends BaseSessionQueryForm {
    private String siteId;

    private String departmentId;

    private String loginName;

    private String name;

    private String enabled;
    
    private String roleId;

    
    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    /**
     * @return Returns the enabled.
     */
    public String getEnabled() {
        return enabled;
    }

    /**
     * @param enabled The enabled to set.
     */
    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    /**
     * @return Returns the departmentId.
     */
    public String getDepartmentId() {
        return departmentId;
    }

    /**
     * @param departmentId
     *            The departmentId to set.
     */
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    
    /**
     * @return Returns the loginName.
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * @param loginName
     *            The loginName to set.
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
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
     * @return Returns the siteId.
     */
    public String getSiteId() {
        return siteId;
    }

    /**
     * @param siteId
     *            The siteId to set.
     */
    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }


    public UserQueryForm()
    {
        this.setOrder(UserQueryOrder.LOGINNAME.getName());
        this.setDescend(false);
        this.setEnabled(EnabledDisabled.ENABLED.getEnumCode().toString());
    }

}
