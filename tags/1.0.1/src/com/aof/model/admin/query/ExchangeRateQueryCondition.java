/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.admin.query;

import org.apache.commons.lang.enums.Enum;

/**
 * ExchangeRate查询条件的枚举类
 * @author ych
 * @version 1.0 (Nov 13, 2005)
 */
public class ExchangeRateQueryCondition extends Enum{

	public static final ExchangeRateQueryCondition CURRENCY_CODE_LIKE = new ExchangeRateQueryCondition("currency_code_like");
    public static final ExchangeRateQueryCondition CURRENCY_CODE_EQ = new ExchangeRateQueryCondition("currency_code_eq");
	public static final ExchangeRateQueryCondition SITE_ID_EQ = new ExchangeRateQueryCondition("site_id_eq");
    public static final ExchangeRateQueryCondition CURRENCY_STATUS_EQ = new ExchangeRateQueryCondition("currency_status_eq");
	
	protected ExchangeRateQueryCondition(String value) {
		super(value);
	}
	
	public static ExchangeRateQueryCondition getEnum(String value) {
        return (ExchangeRateQueryCondition) getEnum(ExchangeRateQueryCondition.class, value);
    }

}
