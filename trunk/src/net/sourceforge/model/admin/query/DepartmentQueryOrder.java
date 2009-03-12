/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.admin.query;

import org.apache.commons.lang.enums.Enum;

public class DepartmentQueryOrder extends Enum{
    
    public final static DepartmentQueryOrder NAME = new DepartmentQueryOrder("name");

	protected DepartmentQueryOrder(String value) {
		super(value);
	}
	
	public static DepartmentQueryOrder getEnum(String value) {
        return (DepartmentQueryOrder) getEnum(DepartmentQueryOrder.class, value);
    }

}
