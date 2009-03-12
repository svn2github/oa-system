/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.admin.query;

import org.apache.commons.lang.enums.Enum;

/**
 * SupplierContract查询条件的枚举类
 * @author ych
 * @version 1.0 (Nov 13, 2005)
 */
public class SupplierContractQueryCondition extends Enum{

	public static final SupplierContractQueryCondition ID_EQ =
		 new SupplierContractQueryCondition("id_eq");
	public static final SupplierContractQueryCondition SUPPLIER_ID_EQ =
		 new SupplierContractQueryCondition("Supplier_id_eq");
	public static final SupplierContractQueryCondition FILESIZE_EQ =
		 new SupplierContractQueryCondition("fileSize_eq");
	public static final SupplierContractQueryCondition FILENAME_LIKE =
		 new SupplierContractQueryCondition("fileName_like");
	public static final SupplierContractQueryCondition DESCRIPTION_LIKE =
		 new SupplierContractQueryCondition("description_like");
	public static final SupplierContractQueryCondition UPLOADDATE_EQ =
		 new SupplierContractQueryCondition("uploadDate_eq");

	protected SupplierContractQueryCondition(String value) {
		super(value);
	}
	
	public static SupplierContractQueryCondition getEnum(String value) {
        return (SupplierContractQueryCondition) getEnum(SupplierContractQueryCondition.class, value);
    }

}
