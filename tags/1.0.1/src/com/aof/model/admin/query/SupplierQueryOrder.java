/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.admin.query;

import org.apache.commons.lang.enums.Enum;

/**
 * Supplier查询顺序的枚举类
 * @author ych
 * @version 1.0 (Nov 13, 2005)
 */
public class SupplierQueryOrder extends Enum{


	public static final SupplierQueryOrder ID = new SupplierQueryOrder("id");

    public static final SupplierQueryOrder CODE = new SupplierQueryOrder("code");
	public static final SupplierQueryOrder NAME = new SupplierQueryOrder("name");
	public static final SupplierQueryOrder CITYENGNAME = new SupplierQueryOrder("cityEngName");
	public static final SupplierQueryOrder CITYCHNNAME = new SupplierQueryOrder("cityChnName");
	public static final SupplierQueryOrder ENABLED = new SupplierQueryOrder("enabled");
	public static final SupplierQueryOrder DRAFT = new SupplierQueryOrder("draft");
    public static final SupplierQueryOrder PROMOTE_STATUS = new SupplierQueryOrder("promoteStatus");
    
	
	protected SupplierQueryOrder(String value) {
		super(value);
	}
	
	public static SupplierQueryOrder getEnum(String value) {
        return (SupplierQueryOrder) getEnum(SupplierQueryOrder.class, value);
    }

}
