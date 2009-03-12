/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.web.struts.form.admin;

import net.sourceforge.model.admin.query.WebMonitorQueryOrder;
import net.sourceforge.web.struts.form.BaseSessionQueryForm;


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
