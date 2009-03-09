/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.admin.query;

import org.apache.commons.lang.enums.Enum;

/**
 * Supplier查询条件的枚举类
 * @author ych
 * @version 1.0 (Nov 13, 2005)
 */
public class SupplierQueryCondition extends Enum{

	public static final SupplierQueryCondition ID_EQ =
		 new SupplierQueryCondition("id_eq");
    public static final SupplierQueryCondition CODE_LIKE =
         new SupplierQueryCondition("CODE_like");
	public static final SupplierQueryCondition GLOBAL_OR_SITE_ID_EQ =
		 new SupplierQueryCondition("global_or_site_id_eq");
	public static final SupplierQueryCondition SITE_ID_EQ =
		 new SupplierQueryCondition("site_id_eq");
	public static final SupplierQueryCondition COUNTRY_ID_EQ =
		 new SupplierQueryCondition("country_id_eq");
	public static final SupplierQueryCondition CITY_ID_EQ =
		 new SupplierQueryCondition("city_id_eq");
	public static final SupplierQueryCondition NAME_LIKE =
		 new SupplierQueryCondition("name_like");
	public static final SupplierQueryCondition ENABLED_EQ =
		 new SupplierQueryCondition("enabled_eq");
	public static final SupplierQueryCondition DRAFT_EQ =
		 new SupplierQueryCondition("draft_eq");
	public static final SupplierQueryCondition CONFIRM_EQ =
		 new SupplierQueryCondition("confirm_eq");
	public static final SupplierQueryCondition PROMOTE_STATUS_EQ =
		 new SupplierQueryCondition("promote_status_eq");
	public static final SupplierQueryCondition PROMOTE_STATUS_NEQ =
		 new SupplierQueryCondition("promote_status_neq");
	public static final SupplierQueryCondition CONFIRM_TYPE_EQ =
		 new SupplierQueryCondition("confirm_type_eq");
	public static final SupplierQueryCondition CONTRACT_NOT_ACTIVE =
		 new SupplierQueryCondition("contract_not_active");
	public static final SupplierQueryCondition CONTRACT_ACTIVE =
		 new SupplierQueryCondition("contract_active");
	public static final SupplierQueryCondition CONTRACT_EXPIRED =
		 new SupplierQueryCondition("contract_expired");
    public static final SupplierQueryCondition FOR_SITE_ID_EQ = new SupplierQueryCondition("for_site_id_eq");
    
    public static final SupplierQueryCondition IS_AIRTICKET = new SupplierQueryCondition("is_airticket");
	
	protected SupplierQueryCondition(String value) {
		super(value);
	}
	
	public static SupplierQueryCondition getEnum(String value) {
        return (SupplierQueryCondition) getEnum(SupplierQueryCondition.class, value);
    }

}
