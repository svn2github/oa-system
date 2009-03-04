/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.admin.query;

import org.apache.commons.lang.enums.Enum;

public class MenuQueryOrder extends Enum{

	protected MenuQueryOrder(String value) {
		super(value);
	}
	
	public static MenuQueryOrder getEnum(String value) {
        return (MenuQueryOrder) getEnum(MenuQueryOrder.class, value);
    }

}
