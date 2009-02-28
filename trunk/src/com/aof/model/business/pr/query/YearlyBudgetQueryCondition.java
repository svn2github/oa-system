/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.model.business.pr.query;

import org.apache.commons.lang.enums.Enum;

/**
 * query condition for domain model YearlyBudget
 * @author shilei
 * @version 1.0 (Dec 1, 2005)
 */
public class YearlyBudgetQueryCondition extends Enum{


	public static final YearlyBudgetQueryCondition ID_EQ =
		 new YearlyBudgetQueryCondition("id_eq");
    
    public static final YearlyBudgetQueryCondition HAS_DEPARTMENT =
         new YearlyBudgetQueryCondition("has_department");
    
	public static final YearlyBudgetQueryCondition SITE_ID_EQ =
		 new YearlyBudgetQueryCondition("site_id_eq");
	public static final YearlyBudgetQueryCondition PURCHASECATEGORY_ID_EQ =
		 new YearlyBudgetQueryCondition("purchaseCategory_id_eq");
	public static final YearlyBudgetQueryCondition PURCHASESUBCATEGORY_ID_EQ =
		 new YearlyBudgetQueryCondition("purchaseSubCategory_id_eq");
	public static final YearlyBudgetQueryCondition CREATOR_ID_EQ =
		 new YearlyBudgetQueryCondition("creator_id_eq");
	public static final YearlyBudgetQueryCondition MODIFIER_ID_EQ =
		 new YearlyBudgetQueryCondition("modifier_id_eq");


	public static final YearlyBudgetQueryCondition CODE_EQ =
		 new YearlyBudgetQueryCondition("code_eq");
	public static final YearlyBudgetQueryCondition NAME_LIKE =
		 new YearlyBudgetQueryCondition("name_like");
	public static final YearlyBudgetQueryCondition TYPE_EQ =
		 new YearlyBudgetQueryCondition("type_eq");
	public static final YearlyBudgetQueryCondition YEAR_EQ =
		 new YearlyBudgetQueryCondition("year_eq");
	public static final YearlyBudgetQueryCondition AMOUNT_EQ =
		 new YearlyBudgetQueryCondition("amount_eq");
	public static final YearlyBudgetQueryCondition ACTUALAMOUNT_EQ =
		 new YearlyBudgetQueryCondition("actualAmount_eq");
	public static final YearlyBudgetQueryCondition STATUS_EQ =
		 new YearlyBudgetQueryCondition("status_eq");
	public static final YearlyBudgetQueryCondition ISFREEZE_EQ =
		 new YearlyBudgetQueryCondition("isFreeze_eq");
	public static final YearlyBudgetQueryCondition CREATEDATE_EQ =
		 new YearlyBudgetQueryCondition("createDate_eq");
	public static final YearlyBudgetQueryCondition MODIFYDATE_EQ =
		 new YearlyBudgetQueryCondition("modifyDate_eq");

    public static final YearlyBudgetQueryCondition AMOUNT_GE = 
         new YearlyBudgetQueryCondition("amount_ge");

    public static final YearlyBudgetQueryCondition AMOUNT_LE = 
        new YearlyBudgetQueryCondition("amount_le");
    
    public static final YearlyBudgetQueryCondition HAS_PURCHASESUBCATEGORY = 
        new YearlyBudgetQueryCondition("has_purchaseSubCategory");


	protected YearlyBudgetQueryCondition(String value) {
		super(value);
	}
	
	public static YearlyBudgetQueryCondition getEnum(String value) {
        return (YearlyBudgetQueryCondition) getEnum(YearlyBudgetQueryCondition.class, value);
    }

}
