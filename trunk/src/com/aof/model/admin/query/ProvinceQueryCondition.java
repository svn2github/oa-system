/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.model.admin.query;

import org.apache.commons.lang.enums.Enum;

public class ProvinceQueryCondition extends Enum{

	/*id*/
	public static final ProvinceQueryCondition ID_EQ =
		 new ProvinceQueryCondition("id_eq");

	/*keyPropertyList*/

	/*kmtoIdList*/

	/*mtoIdList*/
	public static final ProvinceQueryCondition COUNTRY_ID_EQ =
		 new ProvinceQueryCondition("country_id_eq");

	/*property*/
	public static final ProvinceQueryCondition ENGNAME_LIKE =
		 new ProvinceQueryCondition("engName_like");
	public static final ProvinceQueryCondition CHNNAME_LIKE =
		 new ProvinceQueryCondition("chnName_like");
	public static final ProvinceQueryCondition ENABLED_EQ =
		 new ProvinceQueryCondition("enabled_eq");

	protected ProvinceQueryCondition(String value) {
		super(value);
	}
	
	public static ProvinceQueryCondition getEnum(String value) {
        return (ProvinceQueryCondition) getEnum(ProvinceQueryCondition.class, value);
    }

}
