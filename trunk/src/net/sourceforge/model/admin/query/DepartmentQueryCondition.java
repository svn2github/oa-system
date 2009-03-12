/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.admin.query;

import org.apache.commons.lang.enums.Enum;

public class DepartmentQueryCondition extends Enum{
    
    public static final DepartmentQueryCondition SITE_EQ = new  DepartmentQueryCondition("site_eq");
    public static final DepartmentQueryCondition ENABLED_EQ = new  DepartmentQueryCondition("enabled_eq");
	
	protected DepartmentQueryCondition(String value) {
		super(value);
	}
	
	public static DepartmentQueryCondition getEnum(String value) {
        return (DepartmentQueryCondition) getEnum(DepartmentQueryCondition.class, value);
    }

}
