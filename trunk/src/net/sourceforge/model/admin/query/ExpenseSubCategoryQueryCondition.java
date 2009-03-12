package net.sourceforge.model.admin.query;

import org.apache.commons.lang.enums.Enum;

public class ExpenseSubCategoryQueryCondition extends Enum{

	/*id*/
	public static final ExpenseSubCategoryQueryCondition ID_EQ =
		 new ExpenseSubCategoryQueryCondition("id_eq");

	/*keyPropertyList*/

	/*kmtoIdList*/

	/*mtoIdList*/
	public static final ExpenseSubCategoryQueryCondition EXPENSECATEGORY_ID_EQ =
		 new ExpenseSubCategoryQueryCondition("expenseCategory_id_eq");

    public static final ExpenseSubCategoryQueryCondition EXPENSECATEGORY_SITE_ID_EQ =
         new ExpenseSubCategoryQueryCondition("expenseCategory_site_id_eq");

    /*property*/
	public static final ExpenseSubCategoryQueryCondition DESCRIPTION_LIKE =
		 new ExpenseSubCategoryQueryCondition("description_like");
	public static final ExpenseSubCategoryQueryCondition REFERENCEERPID_LIKE =
		 new ExpenseSubCategoryQueryCondition("referenceErpId_like");
	public static final ExpenseSubCategoryQueryCondition ENABLED_EQ =
		 new ExpenseSubCategoryQueryCondition("enabled_eq");
	public static final ExpenseSubCategoryQueryCondition ISHOTEL_EQ =
		 new ExpenseSubCategoryQueryCondition("isHotel_eq");

	protected ExpenseSubCategoryQueryCondition(String value) {
		super(value);
	}
	
	public static ExpenseSubCategoryQueryCondition getEnum(String value) {
        return (ExpenseSubCategoryQueryCondition) getEnum(ExpenseSubCategoryQueryCondition.class, value);
    }

}
