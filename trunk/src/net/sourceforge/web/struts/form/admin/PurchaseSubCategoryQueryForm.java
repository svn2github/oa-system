/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.web.struts.form.admin;

import net.sourceforge.model.admin.query.PurchaseSubCategoryQueryOrder;
import net.sourceforge.web.struts.form.BaseSessionQueryForm;

/**
 * ²éÑ¯PurchaseSubCategoryµÄForm
 * @author ych
 * @version 1.0 (Nov 14, 2005)
 */
public class PurchaseSubCategoryQueryForm extends BaseSessionQueryForm {

	private String purchaseCategory_id;
	private String defaultSupplier_id;
	private String description;
	
	
	/**
     * @return Returns the defaultSupplier_id.
     */
    public String getDefaultSupplier_id() {
        return defaultSupplier_id;
    }
    /**
     * @param defaultSupplier_id The defaultSupplier_id to set.
     */
    public void setDefaultSupplier_id(String defaultSupplier_id) {
        this.defaultSupplier_id = defaultSupplier_id;
    }
    /**
     * @return Returns the purchaseCategory_id.
     */
    public String getPurchaseCategory_id() {
        return purchaseCategory_id;
    }
    /**
     * @param purchaseCategory_id The purchaseCategory_id to set.
     */
    public void setPurchaseCategory_id(String purchaseCategory_id) {
        this.purchaseCategory_id = purchaseCategory_id;
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
      
    public PurchaseSubCategoryQueryForm()
    {
        this.setOrder(PurchaseSubCategoryQueryOrder.DESCRIPTION.getName());
        this.setDescend(false);
    }
}
