/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.admin.query;

import org.apache.commons.lang.enums.Enum;

/**
 * SupplierItem查询顺序的枚举类
 * @author ych
 * @version 1.0 (Nov 7, 2005)
 */
public class SupplierItemQueryOrder extends Enum{

	public static final SupplierItemQueryOrder ID = new SupplierItemQueryOrder("id");

	public static final SupplierItemQueryOrder SEPC = new SupplierItemQueryOrder("sepc");
	public static final SupplierItemQueryOrder UNITPRICE = new SupplierItemQueryOrder("unitPrice");
	public static final SupplierItemQueryOrder ENABLED = new SupplierItemQueryOrder("enabled");
	public static final SupplierItemQueryOrder ERPNO = new SupplierItemQueryOrder("erpNo");
	public static final SupplierItemQueryOrder CATEGORY = new SupplierItemQueryOrder("category");
    public static final SupplierItemQueryOrder SUBCATEGORY = new SupplierItemQueryOrder("subCategory");
    
	protected SupplierItemQueryOrder(String value) {
		super(value);
	}
	
	public static SupplierItemQueryOrder getEnum(String value) {
        return (SupplierItemQueryOrder) getEnum(SupplierItemQueryOrder.class, value);
    }

}
