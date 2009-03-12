/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.admin.query;

import org.apache.commons.lang.enums.Enum;

/**
 * SupplierContract查询顺序的枚举类
 * @author ych
 * @version 1.0 (Nov 13, 2005)
 */
public class SupplierContractQueryOrder extends Enum{

    public static final SupplierContractQueryOrder ID = new SupplierContractQueryOrder("id");
	public static final SupplierContractQueryOrder FILESIZE = new SupplierContractQueryOrder("fileSize");
	public static final SupplierContractQueryOrder FILENAME = new SupplierContractQueryOrder("fileName");
	public static final SupplierContractQueryOrder DESCRIPTION = new SupplierContractQueryOrder("description");
	public static final SupplierContractQueryOrder UPLOADDATE = new SupplierContractQueryOrder("uploadDate");
    
	protected SupplierContractQueryOrder(String value) {
		super(value);
	}
	
	public static SupplierContractQueryOrder getEnum(String value) {
        return (SupplierContractQueryOrder) getEnum(SupplierContractQueryOrder.class, value);
    }

}
