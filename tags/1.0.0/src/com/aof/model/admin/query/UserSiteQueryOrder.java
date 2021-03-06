/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.admin.query;

import org.apache.commons.lang.enums.Enum;

public class UserSiteQueryOrder extends Enum{

	public static final UserSiteQueryOrder SITE_NAME = new UserSiteQueryOrder("site_name");
    
	
	protected UserSiteQueryOrder(String value) {
		super(value);
	}
	
	public static UserSiteQueryOrder getEnum(String value) {
        return (UserSiteQueryOrder) getEnum(UserSiteQueryOrder.class, value);
    }

}
