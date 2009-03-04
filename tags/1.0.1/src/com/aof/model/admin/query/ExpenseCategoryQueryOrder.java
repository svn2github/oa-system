package com.aof.model.admin.query;

import org.apache.commons.lang.enums.Enum;

public class ExpenseCategoryQueryOrder extends Enum{

	/*id*/
	public static final ExpenseCategoryQueryOrder ID = new ExpenseCategoryQueryOrder("id");

	/*property*/
	public static final ExpenseCategoryQueryOrder DESCRIPTION = new ExpenseCategoryQueryOrder("description");
	public static final ExpenseCategoryQueryOrder TYPE = new ExpenseCategoryQueryOrder("type");
	public static final ExpenseCategoryQueryOrder REFERENCEERPID = new ExpenseCategoryQueryOrder("referenceErpId");
	public static final ExpenseCategoryQueryOrder ENABLED = new ExpenseCategoryQueryOrder("enabled");
    
	protected ExpenseCategoryQueryOrder(String value) {
		super(value);
	}
	
	public static ExpenseCategoryQueryOrder getEnum(String value) {
        return (ExpenseCategoryQueryOrder) getEnum(ExpenseCategoryQueryOrder.class, value);
    }

}
