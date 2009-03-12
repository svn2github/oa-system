/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.admin.query;

import org.apache.commons.lang.enums.Enum;

public class MenuQueryCondition extends Enum{
	
	protected MenuQueryCondition(String value) {
		super(value);
	}
	
	public static MenuQueryCondition getEnum(String value) {
        return (MenuQueryCondition) getEnum(MenuQueryCondition.class, value);
    }

}
