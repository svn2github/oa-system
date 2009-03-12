/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.admin.query;

import org.apache.commons.lang.enums.Enum;

public class UserSiteQueryCondition extends Enum{
    public static final UserSiteQueryCondition USER_ID_EQ = new UserSiteQueryCondition("user_id_eq");
	
	protected UserSiteQueryCondition(String value) {
		super(value);
	}
	
	public static UserSiteQueryCondition getEnum(String value) {
        return (UserSiteQueryCondition) getEnum(UserSiteQueryCondition.class, value);
    }

}
