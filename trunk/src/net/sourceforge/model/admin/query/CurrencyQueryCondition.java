/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.admin.query;

import org.apache.commons.lang.enums.Enum;

/**
 * Currency查询条件的枚举类
 * @author ych
 * @version 1.0 (Nov 13, 2005)
 */
public class CurrencyQueryCondition extends Enum{
    public static final CurrencyQueryCondition CODE_LIKE = new CurrencyQueryCondition("code_like");
    public static final CurrencyQueryCondition NAME_LIKE = new CurrencyQueryCondition("name_like");
    
    public static final CurrencyQueryCondition STATUS_EQ = new CurrencyQueryCondition("status_eq");
	
	protected CurrencyQueryCondition(String value) {
		super(value);
	}
	
	public static CurrencyQueryCondition getEnum(String value) {
        return (CurrencyQueryCondition) getEnum(CurrencyQueryCondition.class, value);
    }

}
