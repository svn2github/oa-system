/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.web.struts.form.business;

import net.sourceforge.model.admin.query.UserQueryOrder;
import net.sourceforge.web.struts.form.BaseSessionQueryForm;


/**
 * ²éÑ¯Recharge UserµÄForm
 * 
 * @author nicebean
 * @version 1.0 (2005-11-15)
 */
public class RechargePersonQueryForm extends BaseSessionQueryForm {
    private String siteId;

    private String departmentId;

    private String loginName;

    private String name;

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

    
    public RechargePersonQueryForm()
    {
        this.setOrder(UserQueryOrder.LOGINNAME.getName());
        this.setDescend(false);
    }

}
