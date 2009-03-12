/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.model.admin.query;

import org.apache.commons.lang.enums.Enum;

public class PriceQueryCondition extends Enum{

	/*id*/
	public static final PriceQueryCondition ID_EQ =
		 new PriceQueryCondition("id_eq");

	/*keyPropertyList*/

	/*kmtoIdList*/

	/*mtoIdList*/
	public static final PriceQueryCondition HOTEL_ID_EQ =
		 new PriceQueryCondition("hotel_id_eq");

	/*property*/
	public static final PriceQueryCondition ROOM_LIKE =
		 new PriceQueryCondition("room_like");
	public static final PriceQueryCondition PRICE_EQ =
		 new PriceQueryCondition("price_eq");
	public static final PriceQueryCondition DISCOUNT_EQ =
		 new PriceQueryCondition("discount_eq");
	public static final PriceQueryCondition DESCRIPTION_LIKE =
		 new PriceQueryCondition("description_like");
	public static final PriceQueryCondition ENABLED_EQ =
		 new PriceQueryCondition("enabled_eq");

	public static final PriceQueryCondition HOTEL_CITY_ID_EQ = 
		 new PriceQueryCondition("hotel_city_id_eq");

	protected PriceQueryCondition(String value) {
		super(value);
	}
	
	public static PriceQueryCondition getEnum(String value) {
        return (PriceQueryCondition) getEnum(PriceQueryCondition.class, value);
    }

}
