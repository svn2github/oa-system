package com.aof.model.admin.query;

import org.apache.commons.lang.enums.Enum;

public class ExpenseCategoryQueryCondition extends Enum{

	/*id*/
	public static final ExpenseCategoryQueryCondition ID_EQ =
		 new ExpenseCategoryQueryCondition("id_eq");

	/*keyPropertyList*/

	/*kmtoIdList*/

	/*mtoIdList*/
	public static final ExpenseCategoryQueryCondition SITE_ID_EQ =
		 new ExpenseCategoryQueryCondition("site_id_eq");

	/*property*/
	public static final ExpenseCategoryQueryCondition DESCRIPTION_LIKE =
		 new ExpenseCategoryQueryCondition("description_like");
	public static final ExpenseCategoryQueryCondition TYPE_EQ =
		 new ExpenseCategoryQueryCondition("type_eq");
    public static final ExpenseCategoryQueryCondition TYPE_NEQ =
         new ExpenseCategoryQueryCondition("type_neq");
	public static final ExpenseCategoryQueryCondition REFERENCEERPID_LIKE =
		 new ExpenseCategoryQueryCondition("referenceErpId_like");
	public static final ExpenseCategoryQueryCondition ENABLED_EQ =
		 new ExpenseCategoryQueryCondition("enabled_eq");

	protected ExpenseCategoryQueryCondition(String value) {
		super(value);
	}
	
	public static ExpenseCategoryQueryCondition getEnum(String value) {
        return (ExpenseCategoryQueryCondition) getEnum(ExpenseCategoryQueryCondition.class, value);
    }

}
