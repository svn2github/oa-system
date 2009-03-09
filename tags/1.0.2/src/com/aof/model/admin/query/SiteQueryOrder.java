/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.admin.query;

import org.apache.commons.lang.enums.Enum;

public class SiteQueryOrder extends Enum{

    public static final SiteQueryOrder NAME = new SiteQueryOrder("name");
    public static final SiteQueryOrder ENABLED = new SiteQueryOrder("enabled");

    protected SiteQueryOrder(String value) {
		super(value);
	}
	
	public static SiteQueryOrder getEnum(String value) {
        return (SiteQueryOrder) getEnum(SiteQueryOrder.class, value);
    }

}
