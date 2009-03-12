/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.admin.query;

import org.apache.commons.lang.enums.Enum;

/**
 * PurchaseSubCategory查询顺序的枚举类
 * @author ych
 * @version 1.0 (Nov 13, 2005)
 */
public class PurchaseSubCategoryQueryOrder extends Enum{

	public static final PurchaseSubCategoryQueryOrder ID = new PurchaseSubCategoryQueryOrder("id");

	public static final PurchaseSubCategoryQueryOrder DESCRIPTION = new PurchaseSubCategoryQueryOrder("description");
	public static final PurchaseSubCategoryQueryOrder SUPPLIER = new PurchaseSubCategoryQueryOrder("supplier");
	public static final PurchaseSubCategoryQueryOrder ENABLED = new PurchaseSubCategoryQueryOrder("enabled");
    
	protected PurchaseSubCategoryQueryOrder(String value) {
		super(value);
	}
	
	public static PurchaseSubCategoryQueryOrder getEnum(String value) {
        return (PurchaseSubCategoryQueryOrder) getEnum(PurchaseSubCategoryQueryOrder.class, value);
    }

}
