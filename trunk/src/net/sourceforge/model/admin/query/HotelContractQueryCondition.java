package net.sourceforge.model.admin.query;

import org.apache.commons.lang.enums.Enum;

public class HotelContractQueryCondition extends Enum{

	/*id*/
	public static final HotelContractQueryCondition ID_EQ =
		 new HotelContractQueryCondition("id_eq");

	/*keyPropertyList*/

	/*kmtoIdList*/

	/*mtoIdList*/
	public static final HotelContractQueryCondition HOTEL_ID_EQ =
		 new HotelContractQueryCondition("hotel_id_eq");

	/*property*/
	public static final HotelContractQueryCondition FILENAME_LIKE =
		 new HotelContractQueryCondition("fileName_like");
	public static final HotelContractQueryCondition DESCRIPTION_LIKE =
		 new HotelContractQueryCondition("description_like");
	public static final HotelContractQueryCondition FILECONTENT_EQ =
		 new HotelContractQueryCondition("fileContent_eq");
	public static final HotelContractQueryCondition UPLOADDATE_EQ =
		 new HotelContractQueryCondition("uploadDate_eq");

	protected HotelContractQueryCondition(String value) {
		super(value);
	}
	
	public static HotelContractQueryCondition getEnum(String value) {
        return (HotelContractQueryCondition) getEnum(HotelContractQueryCondition.class, value);
    }

}
