/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.model.admin.query;

import org.apache.commons.lang.enums.Enum;

/**
 * PurchaseCategory查询顺序的枚举类
 * @author ych
 * @version 1.0 (Nov 13, 2005)
 */
public class PurchaseCategoryQueryOrder extends Enum{


	public static final PurchaseCategoryQueryOrder ID = new PurchaseCategoryQueryOrder("id");
	public static final PurchaseCategoryQueryOrder DESCRIPTION = new PurchaseCategoryQueryOrder("description");
	public static final PurchaseCategoryQueryOrder ENABLED = new PurchaseCategoryQueryOrder("enabled");
    
	protected PurchaseCategoryQueryOrder(String value) {
		super(value);
	}
	
	public static PurchaseCategoryQueryOrder getEnum(String value) {
        return (PurchaseCategoryQueryOrder) getEnum(PurchaseCategoryQueryOrder.class, value);
    }

}
