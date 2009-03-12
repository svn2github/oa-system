package net.sourceforge.model.admin.query;

import org.apache.commons.lang.enums.Enum;

public class TravelGroupDetailQueryOrder extends Enum{

	/*id*/

	/*property*/
	public static final TravelGroupDetailQueryOrder AMOUNTLIMIT = new TravelGroupDetailQueryOrder("amountLimit");
	
	protected TravelGroupDetailQueryOrder(String value) {
		super(value);
	}
	
	public static TravelGroupDetailQueryOrder getEnum(String value) {
        return (TravelGroupDetailQueryOrder) getEnum(TravelGroupDetailQueryOrder.class, value);
    }

}
