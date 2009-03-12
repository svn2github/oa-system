/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.admin.query;

import org.apache.commons.lang.enums.Enum;

/**
 * SupplierItem查询条件的枚举类
 * @author ych
 * @version 1.0 (Nov 13, 2005)
 */
public class SupplierItemQueryCondition extends Enum{


	public static final SupplierItemQueryCondition ID_EQ =
		 new SupplierItemQueryCondition("id_eq");
	public static final SupplierItemQueryCondition CURRENCY_CODE_EQ =
		 new SupplierItemQueryCondition("currency_code_eq");
    public static final SupplierItemQueryCondition PURCHASECATEGORY_ID_EQ =
         new SupplierItemQueryCondition("purchaseCategory_id_eq");
	public static final SupplierItemQueryCondition PURCHASESUBCATEGORY_ID_EQ =
		 new SupplierItemQueryCondition("purchaseSubCategory_id_eq");
	public static final SupplierItemQueryCondition SUPPLIER_ID_EQ =
		 new SupplierItemQueryCondition("supplier_id_eq");

	public static final SupplierItemQueryCondition SEPC_LIKE =
		 new SupplierItemQueryCondition("sepc_like");
	public static final SupplierItemQueryCondition UNITPRICE_EQ =
		 new SupplierItemQueryCondition("unitPrice_eq");
	public static final SupplierItemQueryCondition ENABLED_EQ =
		 new SupplierItemQueryCondition("enabled_eq");
	public static final SupplierItemQueryCondition ERPNO_LIKE =
		 new SupplierItemQueryCondition("erpNo_like");

	protected SupplierItemQueryCondition(String value) {
		super(value);
	}
	
	public static SupplierItemQueryCondition getEnum(String value) {
        return (SupplierItemQueryCondition) getEnum(SupplierItemQueryCondition.class, value);
    }

}
