/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.admin.query;

import org.apache.commons.lang.enums.Enum;

public class RoleQueryCondition extends Enum{
    public static final RoleQueryCondition ID_EQ = new RoleQueryCondition("id_eq");
    public static final RoleQueryCondition NAME_LIKE = new RoleQueryCondition("name_like");
    public static final RoleQueryCondition TYPE_EQ = new RoleQueryCondition("type_eq");
	
	protected RoleQueryCondition(String value) {
		super(value);
	}
	
	public static RoleQueryCondition getEnum(String value) {
        return (RoleQueryCondition) getEnum(RoleQueryCondition.class, value);
    }

}
