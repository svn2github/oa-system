/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.model.admin.query;

import org.apache.commons.lang.enums.Enum;

public class PriceQueryOrder extends Enum{

	/*id*/
	public static final PriceQueryOrder ID = new PriceQueryOrder("id");

	/*property*/
	public static final PriceQueryOrder ROOM = new PriceQueryOrder("room");
	public static final PriceQueryOrder PRICE = new PriceQueryOrder("price");
	public static final PriceQueryOrder DISCOUNT = new PriceQueryOrder("discount");
	public static final PriceQueryOrder DESCRIPTION = new PriceQueryOrder("description");
	public static final PriceQueryOrder ENABLED = new PriceQueryOrder("enabled");
    
	protected PriceQueryOrder(String value) {
		super(value);
	}
	
	public static PriceQueryOrder getEnum(String value) {
        return (PriceQueryOrder) getEnum(PriceQueryOrder.class, value);
    }

}
