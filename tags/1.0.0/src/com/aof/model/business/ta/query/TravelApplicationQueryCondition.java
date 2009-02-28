package com.aof.model.business.ta.query;

import org.apache.commons.lang.enums.Enum;

public class TravelApplicationQueryCondition extends Enum{

    public static final TravelApplicationQueryCondition STATUS_EQ_OR_URGENT_EQ=
        new TravelApplicationQueryCondition("STATUS_EQ_OR_URGENT_EQ");
	/*id*/
	public static final TravelApplicationQueryCondition ID_EQ =
		 new TravelApplicationQueryCondition("id_eq");
    public static final TravelApplicationQueryCondition ID_LIKE =
         new TravelApplicationQueryCondition("id_like");

	/*keyPropertyList*/

	/*kmtoIdList*/

	/*mtoIdList*/
	public static final TravelApplicationQueryCondition DEPARTMENT_ID_EQ =
		 new TravelApplicationQueryCondition("department_id_eq");
    public static final TravelApplicationQueryCondition DEPARTMENT_ID_IN =
         new TravelApplicationQueryCondition("department_id_in");
    
    public static final TravelApplicationQueryCondition SITE_ID_EQ =
         new TravelApplicationQueryCondition("site_id_eq");
    
	public static final TravelApplicationQueryCondition FROMCITY_ID_EQ =
		 new TravelApplicationQueryCondition("fromCity_id_eq");
	public static final TravelApplicationQueryCondition TOCITY_ID_EQ =
		 new TravelApplicationQueryCondition("toCity_id_eq");
	public static final TravelApplicationQueryCondition HOTEL_ID_EQ =
		 new TravelApplicationQueryCondition("hotel_id_eq");
	public static final TravelApplicationQueryCondition PRICE_ID_EQ =
		 new TravelApplicationQueryCondition("price_id_eq");
	public static final TravelApplicationQueryCondition REQUESTOR_ID_EQ =
		 new TravelApplicationQueryCondition("requestor_id_eq");
	public static final TravelApplicationQueryCondition CREATOR_ID_EQ =
		 new TravelApplicationQueryCondition("creator_id_eq");
	public static final TravelApplicationQueryCondition BOOKER_ID_EQ =
		 new TravelApplicationQueryCondition("booker_id_eq");

	/*property*/
	public static final TravelApplicationQueryCondition TITLE_LIKE =
		 new TravelApplicationQueryCondition("title_like");
	public static final TravelApplicationQueryCondition DESCRIPTION_LIKE =
		 new TravelApplicationQueryCondition("description_like");
	public static final TravelApplicationQueryCondition STATUS_EQ =
		 new TravelApplicationQueryCondition("status_eq");
	public static final TravelApplicationQueryCondition HOTELNAME_LIKE =
		 new TravelApplicationQueryCondition("hotelName_like");
	public static final TravelApplicationQueryCondition ROOMDESCRIPTION_LIKE =
		 new TravelApplicationQueryCondition("roomDescription_like");
	public static final TravelApplicationQueryCondition CHECKINDATE_EQ =
		 new TravelApplicationQueryCondition("checkInDate_eq");
	public static final TravelApplicationQueryCondition CHECKOUTDATE_EQ =
		 new TravelApplicationQueryCondition("checkOutDate_eq");
	public static final TravelApplicationQueryCondition TRAVELLINGMODE_EQ =
		 new TravelApplicationQueryCondition("travellingMode_eq");
	public static final TravelApplicationQueryCondition SINGLEORRETURN_EQ =
		 new TravelApplicationQueryCondition("singleOrReturn_eq");
	public static final TravelApplicationQueryCondition FROMDATE_EQ =
		 new TravelApplicationQueryCondition("fromDate_eq");
	public static final TravelApplicationQueryCondition FROMTIME_EQ =
		 new TravelApplicationQueryCondition("fromTime_eq");
	public static final TravelApplicationQueryCondition TODATE_EQ =
		 new TravelApplicationQueryCondition("toDate_eq");
	public static final TravelApplicationQueryCondition TOTIME_EQ =
		 new TravelApplicationQueryCondition("toTime_eq");
    public static final TravelApplicationQueryCondition FROMDATE_TODATE_LT =
         new TravelApplicationQueryCondition("fromDate_toDate_lt");
    public static final TravelApplicationQueryCondition FROMDATE_TODATE_GT =
        new TravelApplicationQueryCondition("fromDate_toDate_gt");
	public static final TravelApplicationQueryCondition REQUESTDATE_EQ =
		 new TravelApplicationQueryCondition("requestDate_eq");
	public static final TravelApplicationQueryCondition APPROVEREQUESTID_EQ =
		 new TravelApplicationQueryCondition("approveRequestId_eq");
    public static final TravelApplicationQueryCondition BOOKSTATUS_EQ = 
        new TravelApplicationQueryCondition("bookStatus_eq");
    public static final TravelApplicationQueryCondition BOOKER_NULL_OR_ID_EQ = 
        new TravelApplicationQueryCondition("booker_null_or_id_eq");
    public static final TravelApplicationQueryCondition URGENT_EQ = 
        new TravelApplicationQueryCondition("urgent_eq");
    public static final TravelApplicationQueryCondition BOOKSTATUS_NOT_EQ = 
        new TravelApplicationQueryCondition("bookstatus_not_eq");
    
    /*other*/
    public static final TravelApplicationQueryCondition FROMDATE_GE = 
        new TravelApplicationQueryCondition("fromDate_ge");
    public static final TravelApplicationQueryCondition FROMDATE_LT = 
        new TravelApplicationQueryCondition("fromDate_lt");
    public static final TravelApplicationQueryCondition TODATE_GE = 
        new TravelApplicationQueryCondition("toDate_ge");
    public static final TravelApplicationQueryCondition TODATE_LT = 
        new TravelApplicationQueryCondition("toDate_lt");
    public static final TravelApplicationQueryCondition TOCOUNTRY_ID_EQ = 
        new TravelApplicationQueryCondition("toCountry_id_eq");
    public static final TravelApplicationQueryCondition TOPROVINCE_ID_EQ = 
        new TravelApplicationQueryCondition("toProvince_id_eq");
    public static final TravelApplicationQueryCondition REQUESTOR_NAME_LIKE = 
        new TravelApplicationQueryCondition("requestor_name_like");
    public static final TravelApplicationQueryCondition ID_BEGINWITH = 
        new TravelApplicationQueryCondition("id_beginwith");
    public static final TravelApplicationQueryCondition REQUESTOR_ID_NOT_EQ = 
        new TravelApplicationQueryCondition("requestor_id_not_eq");
    public static final TravelApplicationQueryCondition REQUESTDATE_GE = 
        new TravelApplicationQueryCondition("REQUESTDATE_GE");
    public static final TravelApplicationQueryCondition REQUESTDATE_LT = 
        new TravelApplicationQueryCondition("REQUESTDATE_LT");
    public static final TravelApplicationQueryCondition CREATEDATE_GE = 
        new TravelApplicationQueryCondition("CREATEDATE_GE");
    public static final TravelApplicationQueryCondition CREATEDATE_LE = 
        new TravelApplicationQueryCondition("CREATEDATE_LE");
    public static final TravelApplicationQueryCondition CREATEDATE_LT = 
        new TravelApplicationQueryCondition("CREATEDATE_LT");
    public static final TravelApplicationQueryCondition APPROVEDATE_GE = 
        new TravelApplicationQueryCondition("APPROVEDATE_GE");
    public static final TravelApplicationQueryCondition APPROVEDATE_LE = 
        new TravelApplicationQueryCondition("APPROVEDATE_LE");
    
    
    


	protected TravelApplicationQueryCondition(String value) {
		super(value);
	}
	
	public static TravelApplicationQueryCondition getEnum(String value) {
        return (TravelApplicationQueryCondition) getEnum(TravelApplicationQueryCondition.class, value);
    }

}
