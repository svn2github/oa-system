/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.admin.query;

import org.apache.commons.lang.enums.Enum;

public class CustomerQueryOrder extends Enum{

	public static final CustomerQueryOrder CODE = new CustomerQueryOrder("code");
    public static final CustomerQueryOrder DESCRIPTION = new CustomerQueryOrder("description");
    
    
	
	protected CustomerQueryOrder(String value) {
		super(value);
	}
	
	public static CustomerQueryOrder getEnum(String value) {
        return (CustomerQueryOrder) getEnum(CustomerQueryOrder.class, value);
    }

}
