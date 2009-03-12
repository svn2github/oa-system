package net.sourceforge.model.admin.query;

import org.apache.commons.lang.enums.Enum;

public class HotelQueryOrder extends Enum{

	/*id*/
	public static final HotelQueryOrder ID = new HotelQueryOrder("id");

	/*property*/
	public static final HotelQueryOrder NAME = new HotelQueryOrder("name");
	public static final HotelQueryOrder ADDRESS = new HotelQueryOrder("address");
	public static final HotelQueryOrder DESCRIPTION = new HotelQueryOrder("description");
	public static final HotelQueryOrder TELEPHONE = new HotelQueryOrder("telephone");
	public static final HotelQueryOrder FAX = new HotelQueryOrder("fax");
	public static final HotelQueryOrder LEVEL = new HotelQueryOrder("level");
	public static final HotelQueryOrder CONTRACTSTARTDATE = new HotelQueryOrder("contractStartDate");
	public static final HotelQueryOrder CONTRACTEXPIREDATE = new HotelQueryOrder("contractExpireDate");
	public static final HotelQueryOrder DRAFT = new HotelQueryOrder("draft");
	public static final HotelQueryOrder ENABLED = new HotelQueryOrder("enabled");

    public static final HotelQueryOrder COUNTRY_ENG = new HotelQueryOrder("country_eng");
    public static final HotelQueryOrder CITY_ENG = new HotelQueryOrder("city_eng");
    public static final HotelQueryOrder PROVINCE_ENG = new HotelQueryOrder("province_eng");
    
    public static final HotelQueryOrder COUNTRY_CHN = new HotelQueryOrder("country_chn");
    public static final HotelQueryOrder CITY_CHN = new HotelQueryOrder("city_chn");
    public static final HotelQueryOrder PROVINCE_CHN = new HotelQueryOrder("province_chn");
    
    public static final HotelQueryOrder SITE = new HotelQueryOrder("site");
    public static final HotelQueryOrder PROMOTESTATUS = new HotelQueryOrder("promoteStatus");
    
	protected HotelQueryOrder(String value) {
		super(value);
	}
	
	public static HotelQueryOrder getEnum(String value) {
        return (HotelQueryOrder) getEnum(HotelQueryOrder.class, value);
    }

}
