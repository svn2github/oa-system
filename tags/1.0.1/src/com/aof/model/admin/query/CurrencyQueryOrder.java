/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.admin.query;

import org.apache.commons.lang.enums.Enum;

/**
 * Currency查询顺序的枚举类
 * @author ych
 * @version 1.0 (Nov 13, 2005)
 */
public class CurrencyQueryOrder extends Enum{

	
    public static final CurrencyQueryOrder CODE = new CurrencyQueryOrder("code");
    public static final CurrencyQueryOrder STATUS = new CurrencyQueryOrder("enabled");
	
	protected CurrencyQueryOrder(String value) {
		super(value);
	}
	
	public static CurrencyQueryOrder getEnum(String value) {
        return (CurrencyQueryOrder) getEnum(CurrencyQueryOrder.class, value);
    }

}
