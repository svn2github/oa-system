/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.admin.query;

import org.apache.commons.lang.enums.Enum;

public class RoleQueryOrder extends Enum {

	public static final RoleQueryOrder ID = new RoleQueryOrder("id");
    public static final RoleQueryOrder NAME = new RoleQueryOrder("name");
    public static final RoleQueryOrder TYPE = new RoleQueryOrder("type");
    
	
	protected RoleQueryOrder(String value) {
		super(value);
	}
	
	public static RoleQueryOrder getEnum(String value) {
        return (RoleQueryOrder) getEnum(RoleQueryOrder.class, value);
    }

}
