/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.admin.query;

import org.apache.commons.lang.enums.Enum;

public class SiteQueryCondition extends Enum{
    
    public static final SiteQueryCondition NAME_LIKE = new SiteQueryCondition("name_like");
    public static final SiteQueryCondition ENABLED_EQ = new SiteQueryCondition("enabled_eq");
	
	protected SiteQueryCondition(String value) {
		super(value);
	}
	
	public static SiteQueryCondition getEnum(String value) {
        return (SiteQueryCondition) getEnum(SiteQueryCondition.class, value);
    }

}
