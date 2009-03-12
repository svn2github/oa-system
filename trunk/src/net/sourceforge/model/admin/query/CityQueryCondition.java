package net.sourceforge.model.admin.query;

import org.apache.commons.lang.enums.Enum;

public class CityQueryCondition extends Enum{

	/*id*/
	public static final CityQueryCondition ID_EQ =
		 new CityQueryCondition("id_eq");

	/*keyPropertyList*/

	/*kmtoIdList*/

	/*mtoIdList*/
	public static final CityQueryCondition PROVINCE_ID_EQ =
		 new CityQueryCondition("province_id_eq");

	/*property*/
	public static final CityQueryCondition ENGNAME_LIKE =
		 new CityQueryCondition("engName_like");
	public static final CityQueryCondition CHNNAME_LIKE =
		 new CityQueryCondition("chnName_like");
	public static final CityQueryCondition ENABLED_EQ =
		 new CityQueryCondition("enabled_eq");

	protected CityQueryCondition(String value) {
		super(value);
	}
	
	public static CityQueryCondition getEnum(String value) {
        return (CityQueryCondition) getEnum(CityQueryCondition.class, value);
    }

}
