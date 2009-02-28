/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.admin.query;

import org.apache.commons.lang.enums.Enum;

public class CustomerQueryCondition extends Enum{
    public static final CustomerQueryCondition CODE_EQ = new CustomerQueryCondition("code_eq");
	public static final CustomerQueryCondition CODE_LIKE = new CustomerQueryCondition("code_like");
    public static final CustomerQueryCondition DESCRIPTION_LIKE = new CustomerQueryCondition("description_like");
    public static final CustomerQueryCondition SITE_ID_EQ = new CustomerQueryCondition("site_id_eq");
    public static final CustomerQueryCondition TYPE_EQ = new CustomerQueryCondition("type_eq");
    public static final CustomerQueryCondition ENABLED_EQ = new CustomerQueryCondition("enabled_eq");
	
	protected CustomerQueryCondition(String value) {
		super(value);
	}
	
	public static CustomerQueryCondition getEnum(String value) {
        return (CustomerQueryCondition) getEnum(CustomerQueryCondition.class, value);
    }

}
