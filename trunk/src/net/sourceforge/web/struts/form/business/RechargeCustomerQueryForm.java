/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.web.struts.form.business;

import net.sourceforge.model.admin.query.CustomerQueryOrder;
import net.sourceforge.web.struts.form.BaseSessionQueryForm;


/**
 * ²éÑ¯Recharge UserµÄForm
 * 
 * @author nicebean
 * @version 1.0 (2005-11-15)
 */
public class RechargeCustomerQueryForm extends BaseSessionQueryForm {
    private String siteId;

    private String code;

    private String description;

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
     * @return Returns the code.
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     *            The code to set.
     */
    public void setCode(String code) {
        this.code = code;
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

    public RechargeCustomerQueryForm() {
        this.setOrder(CustomerQueryOrder.CODE.getName());
        this.setDescend(false);
    }

}
