/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.web.struts.form.admin;


import com.aof.model.admin.query.ExchangeRateQueryOrder;
import com.aof.web.struts.form.BaseSessionQueryForm;

/**
 * ²éÑ¯ExchangeRateµÄForm
 * @author ych
 * @version 1.0 (Nov 14, 2005)
 */
public class ExchangeRateQueryForm extends BaseSessionQueryForm {

	private String currencyCode;
	
	private String siteId;

	

    
    /**
     * @return Returns the currencyCode.
     */
    public String getCurrencyCode() {
        return currencyCode;
    }




    /**
     * @param currencyCode The currencyCode to set.
     */
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
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

    
    public ExchangeRateQueryForm()
    {
        this.setOrder(ExchangeRateQueryOrder.CODE.getName());
        this.setDescend(false);
    }

	    
}
