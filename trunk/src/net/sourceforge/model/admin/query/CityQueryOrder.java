package net.sourceforge.model.admin.query;

import org.apache.commons.lang.enums.Enum;

public class CityQueryOrder extends Enum{

	/*id*/
	public static final CityQueryOrder ID = new CityQueryOrder("id");

	/*property*/
	public static final CityQueryOrder ENGNAME = new CityQueryOrder("engName");
	public static final CityQueryOrder CHNNAME = new CityQueryOrder("chnName");
	public static final CityQueryOrder ENABLED = new CityQueryOrder("enabled");
    
	protected CityQueryOrder(String value) {
		super(value);
	}
	
	public static CityQueryOrder getEnum(String value) {
        return (CityQueryOrder) getEnum(CityQueryOrder.class, value);
    }

}
