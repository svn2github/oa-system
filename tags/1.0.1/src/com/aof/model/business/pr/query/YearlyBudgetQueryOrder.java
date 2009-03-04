/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.model.business.pr.query;

import org.apache.commons.lang.enums.Enum;

/**
 * query order for domain model YearlyBudget 
 * @author shilei
 * @version 1.0 (Dec 1, 2005)
 */
public class YearlyBudgetQueryOrder extends Enum{

	/*id*/
	public static final YearlyBudgetQueryOrder ID = new YearlyBudgetQueryOrder("id");

	/*property*/
	public static final YearlyBudgetQueryOrder CODE = new YearlyBudgetQueryOrder("code");
	public static final YearlyBudgetQueryOrder NAME = new YearlyBudgetQueryOrder("name");
	public static final YearlyBudgetQueryOrder TYPE = new YearlyBudgetQueryOrder("type");
	public static final YearlyBudgetQueryOrder YEAR = new YearlyBudgetQueryOrder("year");
	public static final YearlyBudgetQueryOrder AMOUNT = new YearlyBudgetQueryOrder("amount");
	public static final YearlyBudgetQueryOrder ACTUALAMOUNT = new YearlyBudgetQueryOrder("actualAmount");
	public static final YearlyBudgetQueryOrder STATUS = new YearlyBudgetQueryOrder("status");
	public static final YearlyBudgetQueryOrder ISFREEZE = new YearlyBudgetQueryOrder("isFreeze");
	public static final YearlyBudgetQueryOrder CREATEDATE = new YearlyBudgetQueryOrder("createDate");
	public static final YearlyBudgetQueryOrder MODIFYDATE = new YearlyBudgetQueryOrder("modifyDate");

    public static final YearlyBudgetQueryOrder REMAINAMOUNT = new YearlyBudgetQueryOrder("remainAmount");
    
    public static final YearlyBudgetQueryOrder SITE = new YearlyBudgetQueryOrder("site");
    
	protected YearlyBudgetQueryOrder(String value) {
		super(value);
	}
	
	public static YearlyBudgetQueryOrder getEnum(String value) {
        return (YearlyBudgetQueryOrder) getEnum(YearlyBudgetQueryOrder.class, value);
    }

}
