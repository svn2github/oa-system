/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.admin.query;

import org.apache.commons.lang.enums.Enum;

public class UserDepartmentQueryCondition extends Enum{
    public static final UserDepartmentQueryCondition USER_ID_EQ = new UserDepartmentQueryCondition("user_id_eq");
    public static final UserDepartmentQueryCondition SITE_ID_EQ = new UserDepartmentQueryCondition("site_id_eq");
	
	protected UserDepartmentQueryCondition(String value) {
		super(value);
	}
	
	public static UserDepartmentQueryCondition getEnum(String value) {
        return (UserDepartmentQueryCondition) getEnum(UserDepartmentQueryCondition.class, value);
    }

}
