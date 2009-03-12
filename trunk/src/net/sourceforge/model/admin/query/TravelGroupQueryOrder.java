package net.sourceforge.model.admin.query;

import org.apache.commons.lang.enums.Enum;

public class TravelGroupQueryOrder extends Enum{

	/*id*/
	public static final TravelGroupQueryOrder ID = new TravelGroupQueryOrder("id");

	/*property*/
	public static final TravelGroupQueryOrder NAME = new TravelGroupQueryOrder("name");
	public static final TravelGroupQueryOrder ENABLED = new TravelGroupQueryOrder("enabled");
    
	protected TravelGroupQueryOrder(String value) {
		super(value);
	}
	
	public static TravelGroupQueryOrder getEnum(String value) {
        return (TravelGroupQueryOrder) getEnum(TravelGroupQueryOrder.class, value);
    }

}
