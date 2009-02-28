/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.model.business.pr.query;

import org.apache.commons.lang.enums.Enum;

/**
 * Query Condition for domain model YearlyBudgetDepartment
 * @author shi1
 * @version 1.0 (Nov 30, 2005)
 */
public class YearlyBudgetDepartmentQueryCondition extends Enum{

	public static final YearlyBudgetDepartmentQueryCondition ID_EQ =
		 new YearlyBudgetDepartmentQueryCondition("id_eq");

	public static final YearlyBudgetDepartmentQueryCondition YEARLYBUDGET_ID_EQ =
		 new YearlyBudgetDepartmentQueryCondition("yearlyBudget_id_eq");
    
	public static final YearlyBudgetDepartmentQueryCondition DEPARTMENT_ID_EQ =
		 new YearlyBudgetDepartmentQueryCondition("department_id_eq");

    public static final YearlyBudgetDepartmentQueryCondition YEARLYBUDGET_SITE_ID_EQ = 
        new YearlyBudgetDepartmentQueryCondition("yb_site_id_eq");
    
    public static final YearlyBudgetDepartmentQueryCondition YEARLYBUDGET_YEAR_EQ = 
        new YearlyBudgetDepartmentQueryCondition("yb_year_eq");
    
    public static final YearlyBudgetDepartmentQueryCondition YEARLYBUDGET_STATUS_EQ = 
        new YearlyBudgetDepartmentQueryCondition("yb_status_eq");
    
    public static final YearlyBudgetDepartmentQueryCondition YEARLYBUDGET_CODE_EQ = 
        new YearlyBudgetDepartmentQueryCondition("yb_code_eq");
    
    public static final YearlyBudgetDepartmentQueryCondition YEARLYBUDGET_NAME_LIKE = 
        new YearlyBudgetDepartmentQueryCondition("yb_name_like");
    
    public static final YearlyBudgetDepartmentQueryCondition YEARLYBUDGET_AMOUNT_LE = 
        new YearlyBudgetDepartmentQueryCondition("yb_amount_le");
    
    public static final YearlyBudgetDepartmentQueryCondition YEARLYBUDGET_AMOUNT_GE = 
        new YearlyBudgetDepartmentQueryCondition("yb_amount_ge");
    
    public static final YearlyBudgetDepartmentQueryCondition YEARLYBUDGET_TYPE_EQ = 
        new YearlyBudgetDepartmentQueryCondition("yb_type_eq");

    public static final YearlyBudgetDepartmentQueryCondition YEARLYBUDGET_HAS_DEPARTMENT =
        new YearlyBudgetDepartmentQueryCondition("yb_has_department");

    public static final YearlyBudgetDepartmentQueryCondition YEARLYBUDGET_ISFREEZE_EQ = 
        new YearlyBudgetDepartmentQueryCondition("yb_isFreeze_eq");
    
	protected YearlyBudgetDepartmentQueryCondition(String value) {
		super(value);
	}
	
	public static YearlyBudgetDepartmentQueryCondition getEnum(String value) {
        return (YearlyBudgetDepartmentQueryCondition) getEnum(YearlyBudgetDepartmentQueryCondition.class, value);
    }

}
