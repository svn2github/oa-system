/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.web.struts.form.admin;

import com.aof.model.admin.query.WebMonitorQueryOrder;
import com.aof.web.struts.form.BaseSessionQueryForm;


/**
 * ²éÑ¯WebMonitorµÄForm
 * @author ych
 * @version 1.0 (Nov 14, 2005)
 */
public class WebMonitorQueryForm extends BaseSessionQueryForm {
	private String siteId;

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


    public WebMonitorQueryForm(){
        this.setOrder(WebMonitorQueryOrder.SITE.getName());
        this.setDescend(false);
    }	
	
}
