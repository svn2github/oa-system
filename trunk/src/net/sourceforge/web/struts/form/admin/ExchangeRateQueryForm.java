/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.web.struts.form.admin;


import net.sourceforge.model.admin.query.ExchangeRateQueryOrder;
import net.sourceforge.web.struts.form.BaseSessionQueryForm;

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
