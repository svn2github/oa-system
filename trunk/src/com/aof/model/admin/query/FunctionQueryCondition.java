/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.admin.query;

import org.apache.commons.lang.enums.Enum;

public class FunctionQueryCondition extends Enum{

	public static final FunctionQueryCondition ID_EQ = new FunctionQueryCondition("id_eq");
    public static final FunctionQueryCondition NAME_LIKE = new FunctionQueryCondition("name_like");
    
	
	protected FunctionQueryCondition(String value) {
		super(value);
	}
	
	public static FunctionQueryCondition getEnum(String value) {
        return (FunctionQueryCondition) getEnum(FunctionQueryCondition.class, value);
    }

}
