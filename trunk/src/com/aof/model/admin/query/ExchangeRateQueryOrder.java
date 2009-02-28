/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.admin.query;

import org.apache.commons.lang.enums.Enum;

/**
 * ExchangeRate查询顺序的枚举类
 * @author ych
 * @version 1.0 (Nov 13, 2005)
 */
public class ExchangeRateQueryOrder extends Enum{

	public static final ExchangeRateQueryOrder SITE_NAME = new ExchangeRateQueryOrder("siteName");
	public static final ExchangeRateQueryOrder CODE = new ExchangeRateQueryOrder("code");
	public static final ExchangeRateQueryOrder EXCHANGERATE = new ExchangeRateQueryOrder("exchangeRate");
    
	
	protected ExchangeRateQueryOrder(String value) {
		super(value);
	}
	
	public static ExchangeRateQueryOrder getEnum(String value) {
        return (ExchangeRateQueryOrder) getEnum(ExchangeRateQueryOrder.class, value);
    }

}
