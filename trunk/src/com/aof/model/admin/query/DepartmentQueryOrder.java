/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.admin.query;

import org.apache.commons.lang.enums.Enum;

public class DepartmentQueryOrder extends Enum{
    
    public final static DepartmentQueryOrder NAME = new DepartmentQueryOrder("name");

	protected DepartmentQueryOrder(String value) {
		super(value);
	}
	
	public static DepartmentQueryOrder getEnum(String value) {
        return (DepartmentQueryOrder) getEnum(DepartmentQueryOrder.class, value);
    }

}
