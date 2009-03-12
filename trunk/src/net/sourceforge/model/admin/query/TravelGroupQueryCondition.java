package net.sourceforge.model.admin.query;

import org.apache.commons.lang.enums.Enum;

public class TravelGroupQueryCondition extends Enum{

	/*id*/
	public static final TravelGroupQueryCondition ID_EQ =
		 new TravelGroupQueryCondition("id_eq");

	/*keyPropertyList*/

	/*kmtoIdList*/

	/*mtoIdList*/
	public static final TravelGroupQueryCondition SITE_ID_EQ =
		 new TravelGroupQueryCondition("site_id_eq");

	/*property*/
	public static final TravelGroupQueryCondition NAME_LIKE =
		 new TravelGroupQueryCondition("name_like");
	public static final TravelGroupQueryCondition ENABLED_EQ =
		 new TravelGroupQueryCondition("enabled_eq");

	protected TravelGroupQueryCondition(String value) {
		super(value);
	}
	
	public static TravelGroupQueryCondition getEnum(String value) {
        return (TravelGroupQueryCondition) getEnum(TravelGroupQueryCondition.class, value);
    }

}
