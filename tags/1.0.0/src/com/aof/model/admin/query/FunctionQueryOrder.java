/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.admin.query;

import org.apache.commons.lang.enums.Enum;

public class FunctionQueryOrder extends Enum{

	public static final FunctionQueryOrder ID = new FunctionQueryOrder("id");
    public static final FunctionQueryOrder NAME = new FunctionQueryOrder("name");
    public static final FunctionQueryOrder DESCRIPTION = new FunctionQueryOrder("description");
    
	
	protected FunctionQueryOrder(String value) {
		super(value);
	}
	
	public static FunctionQueryOrder getEnum(String value) {
        return (FunctionQueryOrder) getEnum(FunctionQueryOrder.class, value);
    }

}
