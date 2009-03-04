/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.model.admin.query;

import org.apache.commons.lang.enums.Enum;

public class ProvinceQueryOrder extends Enum{

	/*id*/
	public static final ProvinceQueryOrder ID = new ProvinceQueryOrder("id");

	/*property*/
	public static final ProvinceQueryOrder ENGNAME = new ProvinceQueryOrder("engName");
	public static final ProvinceQueryOrder CHNNAME = new ProvinceQueryOrder("chnName");
	public static final ProvinceQueryOrder ENABLED = new ProvinceQueryOrder("enabled");
    
	protected ProvinceQueryOrder(String value) {
		super(value);
	}
	
	public static ProvinceQueryOrder getEnum(String value) {
        return (ProvinceQueryOrder) getEnum(ProvinceQueryOrder.class, value);
    }

}
