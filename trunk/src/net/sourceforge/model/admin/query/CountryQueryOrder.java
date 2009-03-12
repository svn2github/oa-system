package net.sourceforge.model.admin.query;

import org.apache.commons.lang.enums.Enum;

/**
 * @author ych
 * @version 1.0 (Nov 13, 2005)
 */
public class CountryQueryOrder extends Enum{

	/*id*/
	public static final CountryQueryOrder ID = new CountryQueryOrder("id");

	/*property*/
	public static final CountryQueryOrder SHORTNAME = new CountryQueryOrder("shortName");
	public static final CountryQueryOrder ENGNAME = new CountryQueryOrder("engName");
	public static final CountryQueryOrder CHNNAME = new CountryQueryOrder("chnName");
	public static final CountryQueryOrder ENABLED = new CountryQueryOrder("enabled");
    
	protected CountryQueryOrder(String value) {
		super(value);
	}
	
	public static CountryQueryOrder getEnum(String value) {
        return (CountryQueryOrder) getEnum(CountryQueryOrder.class, value);
    }

}
