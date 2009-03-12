/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.admin.query;

import org.apache.commons.lang.enums.Enum;

public class UserDepartmentQueryOrder extends Enum{

    public static final UserDepartmentQueryOrder SITE_NAME = new UserDepartmentQueryOrder("site_name");
	public static final UserDepartmentQueryOrder DEPARTMENT_NAME = new UserDepartmentQueryOrder("department_name");
    
	
	protected UserDepartmentQueryOrder(String value) {
		super(value);
	}
	
	public static UserDepartmentQueryOrder getEnum(String value) {
        return (UserDepartmentQueryOrder) getEnum(UserDepartmentQueryOrder.class, value);
    }

}
