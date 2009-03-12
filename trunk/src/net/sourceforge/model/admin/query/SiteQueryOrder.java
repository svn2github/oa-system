/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.admin.query;

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
