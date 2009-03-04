/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.admin.query;

import org.apache.commons.lang.enums.Enum;

/**
 * SupplierHistory查询顺序的枚举类
 * @author ych
 * @version 1.0 (Nov 13, 2005)
 */
public class SupplierHistoryQueryOrder extends Enum{

	public static final SupplierHistoryQueryOrder ID = new SupplierHistoryQueryOrder("id");

	protected SupplierHistoryQueryOrder(String value) {
		super(value);
	}
	
	public static SupplierHistoryQueryOrder getEnum(String value) {
        return (SupplierHistoryQueryOrder) getEnum(SupplierHistoryQueryOrder.class, value);
    }

}
