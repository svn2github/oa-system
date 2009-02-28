/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.model.admin.query;

import org.apache.commons.lang.enums.Enum;

/**
 * PurchaseCategory查询条件的枚举类
 * @author ych
 * @version 1.0 (Nov 13, 2005)
 */
public class PurchaseCategoryQueryCondition extends Enum{

	public static final PurchaseCategoryQueryCondition ID_EQ =
		 new PurchaseCategoryQueryCondition("id_eq");

    public static final PurchaseCategoryQueryCondition GLOBAL =
         new PurchaseCategoryQueryCondition("global");
    
    public static final PurchaseCategoryQueryCondition SITE_ID_EQ =
		 new PurchaseCategoryQueryCondition("site_id_eq");

    public static final PurchaseCategoryQueryCondition GLOBAL_OR_SITE_ID_EQ =
         new PurchaseCategoryQueryCondition("global_or_site_id_eq");
    
	public static final PurchaseCategoryQueryCondition DESCRIPTION_LIKE =
		 new PurchaseCategoryQueryCondition("description_like");
	public static final PurchaseCategoryQueryCondition ENABLED_EQ =
		 new PurchaseCategoryQueryCondition("enabled_eq");
    
	protected PurchaseCategoryQueryCondition(String value) {
		super(value);
	}
	
	public static PurchaseCategoryQueryCondition getEnum(String value) {
        return (PurchaseCategoryQueryCondition) getEnum(PurchaseCategoryQueryCondition.class, value);
    }

}
