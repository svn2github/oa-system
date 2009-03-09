/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.web.struts.form.admin;


import com.aof.model.admin.query.PurchaseCategoryQueryOrder;
import com.aof.model.metadata.EnabledDisabled;
import com.aof.web.struts.form.BaseSessionQueryForm;


/**
 * ²éÑ¯PurchaseCategoryµÄForm
 * @author ych
 * @version 1.0 (Nov 14, 2005)
 */
public class PurchaseCategoryQueryForm extends BaseSessionQueryForm {

	private String id;
	private String siteId;
	private String description;
    private String status;
    
    
    
    
    /**
     * @return Returns the id.
     */
    public String getId() {
        return id;
    }
    /**
     * @param id The id to set.
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * @return Returns the status.
     */
    public String getStatus() {
        return status;
    }
    /**
     * @param status The status to set.
     */
    public void setStatus(String status) {
        this.status = status;
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
     * @return Returns the description.
     */
    public String getDescription() {
        return description;
    }
    /**
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    public PurchaseCategoryQueryForm()
    {
        this.setStatus(EnabledDisabled.ENABLED.getEnumCode().toString());
        this.setOrder(PurchaseCategoryQueryOrder.DESCRIPTION.getName());
        this.setDescend(false);
    }
}
