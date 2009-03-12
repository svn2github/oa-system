/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.model.business.pr.query;

import org.apache.commons.lang.enums.Enum;

/**
 * query order for domain model YearlyBudgetDepartment
 * @author shilei
 * @version 1.0 (Dec 1, 2005)
 */
public class YearlyBudgetDepartmentQueryOrder extends Enum{

	/*id*/
	public static final YearlyBudgetDepartmentQueryOrder ID = new YearlyBudgetDepartmentQueryOrder("id");

	/*property*/
    
	protected YearlyBudgetDepartmentQueryOrder(String value) {
		super(value);
	}
	
	public static YearlyBudgetDepartmentQueryOrder getEnum(String value) {
        return (YearlyBudgetDepartmentQueryOrder) getEnum(YearlyBudgetDepartmentQueryOrder.class, value);
    }

}
