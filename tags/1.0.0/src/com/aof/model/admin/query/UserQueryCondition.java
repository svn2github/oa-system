/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.admin.query;

import org.apache.commons.lang.enums.Enum;

public class UserQueryCondition extends Enum{
    public static final UserQueryCondition LOGINNAME_EQ = new UserQueryCondition("loginName_eq");
	public static final UserQueryCondition LOGINNAME_LIKE = new UserQueryCondition("loginName_like");
    public static final UserQueryCondition NAME_LIKE = new UserQueryCondition("name_like");
    public static final UserQueryCondition SITE_ID_EQ = new UserQueryCondition("site_eq");
    public static final UserQueryCondition PRIMARY_OR_SITE_ID_EQ = new UserQueryCondition("primary_or_site_eq");
    public static final UserQueryCondition DEPARTMENT_ID_EQ = new UserQueryCondition("department_eq");
    public static final UserQueryCondition ENABLED_EQ = new UserQueryCondition("enabled_eq");
    public static final UserQueryCondition FUNCTION_ID_EQ = new UserQueryCondition("function_id_eq");;
    public static final UserQueryCondition ROLE_ID_EQ = new UserQueryCondition("role_id_eq");;
	
	protected UserQueryCondition(String value) {
		super(value);
	}
	
	public static UserQueryCondition getEnum(String value) {
        return (UserQueryCondition) getEnum(UserQueryCondition.class, value);
    }

}
