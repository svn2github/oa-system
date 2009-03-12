package net.sourceforge.model.admin.query;

import org.apache.commons.lang.enums.Enum;

public class HotelQueryCondition extends Enum{

	/*id*/
	public static final HotelQueryCondition ID_EQ =
		 new HotelQueryCondition("id_eq");

	/*keyPropertyList*/

	/*kmtoIdList*/

	/*mtoIdList*/
	public static final HotelQueryCondition SITE_ID_EQ =
		 new HotelQueryCondition("site_id_eq");
	public static final HotelQueryCondition CURRENCY_CODE_EQ =
		 new HotelQueryCondition("currency_code_eq");
	public static final HotelQueryCondition CITY_ID_EQ =
		 new HotelQueryCondition("city_id_eq");
    public static final HotelQueryCondition PROVINCE_ID_EQ =
         new HotelQueryCondition("province_id_eq");

    public static final HotelQueryCondition COUNTRY_ID_EQ =
         new HotelQueryCondition("country_id_eq");


	/*property*/
	public static final HotelQueryCondition NAME_LIKE =
		 new HotelQueryCondition("name_like");
	public static final HotelQueryCondition ADDRESS_LIKE =
		 new HotelQueryCondition("address_like");
	public static final HotelQueryCondition DESCRIPTION_LIKE =
		 new HotelQueryCondition("description_like");
	public static final HotelQueryCondition TELEPHONE_LIKE =
		 new HotelQueryCondition("telephone_like");
	public static final HotelQueryCondition FAX_LIKE =
		 new HotelQueryCondition("fax_like");
	public static final HotelQueryCondition LEVEL_EQ =
		 new HotelQueryCondition("level_eq");
	public static final HotelQueryCondition CONTRACTSTARTDATE_EQ =
		 new HotelQueryCondition("contractStartDate_eq");
	public static final HotelQueryCondition CONTRACTEXPIREDATE_EQ =
		 new HotelQueryCondition("contractExpireDate_eq");
	public static final HotelQueryCondition DRAFT_EQ =
		 new HotelQueryCondition("draft_eq");
	public static final HotelQueryCondition ENABLED_EQ =
		 new HotelQueryCondition("enabled_eq");
	
	public static final HotelQueryCondition PROMOTESTATUS_NOTEQ =
		 new HotelQueryCondition("promoteStatus_notEq");
    
    public static final HotelQueryCondition PROMOTESTATUS_EQ =
         new HotelQueryCondition("promoteStatus_eq");

	protected HotelQueryCondition(String value) {
		super(value);
	}
	
	public static HotelQueryCondition getEnum(String value) {
        return (HotelQueryCondition) getEnum(HotelQueryCondition.class, value);
    }

}
