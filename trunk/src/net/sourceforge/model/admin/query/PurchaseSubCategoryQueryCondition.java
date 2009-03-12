/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.model.admin.query;

import org.apache.commons.lang.enums.Enum;

/**
 * PurchaseSubCategory查询条件的枚举类
 * @author ych
 * @version 1.0 (Nov 13, 2005)
 */
public class PurchaseSubCategoryQueryCondition extends Enum{

	public static final PurchaseSubCategoryQueryCondition ID_EQ =
		 new PurchaseSubCategoryQueryCondition("id_eq");

	public static final PurchaseSubCategoryQueryCondition PURCHASECATEGORY_ID_EQ =
		 new PurchaseSubCategoryQueryCondition("purchaseCategory_id_eq");
    public static final PurchaseSubCategoryQueryCondition PURCHASECATEGORY_SITE_ID_EQ =
         new PurchaseSubCategoryQueryCondition("purchaseCategory_site_id_eq");
    public static final PurchaseSubCategoryQueryCondition PURCHASECATEGORY_SITE_ID_EQ_OR_NULL =
        new PurchaseSubCategoryQueryCondition("purchaseCategory_site_id_eq_or_null");
	public static final PurchaseSubCategoryQueryCondition DEFAULTSUPPLIER_ID_EQ =
		 new PurchaseSubCategoryQueryCondition("defaultSupplier_id_eq");

	public static final PurchaseSubCategoryQueryCondition DESCRIPTION_LIKE =
		 new PurchaseSubCategoryQueryCondition("description_like");
	public static final PurchaseSubCategoryQueryCondition ENABLED_EQ =
		 new PurchaseSubCategoryQueryCondition("enabled_eq");

	protected PurchaseSubCategoryQueryCondition(String value) {
		super(value);
	}
	
	public static PurchaseSubCategoryQueryCondition getEnum(String value) {
        return (PurchaseSubCategoryQueryCondition) getEnum(PurchaseSubCategoryQueryCondition.class, value);
    }

}
