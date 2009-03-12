/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.admin.query;

import org.apache.commons.lang.enums.Enum;

public class UserQueryOrder extends Enum{

	public static final UserQueryOrder LOGINNAME = new UserQueryOrder("loginName");
    public static final UserQueryOrder NAME = new UserQueryOrder("name");
    public static final UserQueryOrder EMAIL = new UserQueryOrder("email");
    public static final UserQueryOrder TELEPHONE = new UserQueryOrder("telephone");
    
    
	
	protected UserQueryOrder(String value) {
		super(value);
	}
	
	public static UserQueryOrder getEnum(String value) {
        return (UserQueryOrder) getEnum(UserQueryOrder.class, value);
    }

}
