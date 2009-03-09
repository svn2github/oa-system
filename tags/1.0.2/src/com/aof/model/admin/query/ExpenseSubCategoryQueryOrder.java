package com.aof.model.admin.query;

import org.apache.commons.lang.enums.Enum;

public class ExpenseSubCategoryQueryOrder extends Enum{

	/*id*/
	public static final ExpenseSubCategoryQueryOrder ID = new ExpenseSubCategoryQueryOrder("id");

	/*property*/
	public static final ExpenseSubCategoryQueryOrder DESCRIPTION = new ExpenseSubCategoryQueryOrder("description");
	public static final ExpenseSubCategoryQueryOrder REFERENCEERPID = new ExpenseSubCategoryQueryOrder("referenceErpId");
	public static final ExpenseSubCategoryQueryOrder ENABLED = new ExpenseSubCategoryQueryOrder("enabled");
    
	protected ExpenseSubCategoryQueryOrder(String value) {
		super(value);
	}
	
	public static ExpenseSubCategoryQueryOrder getEnum(String value) {
        return (ExpenseSubCategoryQueryOrder) getEnum(ExpenseSubCategoryQueryOrder.class, value);
    }

}
