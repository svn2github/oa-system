package net.sourceforge.model.business.ta.query;

import org.apache.commons.lang.enums.Enum;

public class TravelApplicationQueryOrder extends Enum{

	/*id*/
	public static final TravelApplicationQueryOrder ID = new TravelApplicationQueryOrder("id");

	/*property*/
	public static final TravelApplicationQueryOrder TITLE = new TravelApplicationQueryOrder("title");
	public static final TravelApplicationQueryOrder DESCRIPTION = new TravelApplicationQueryOrder("description");
	public static final TravelApplicationQueryOrder STATUS = new TravelApplicationQueryOrder("status");
    public static final TravelApplicationQueryOrder URGENT = new TravelApplicationQueryOrder("urgent");
    public static final TravelApplicationQueryOrder BOOKSTATUS = new TravelApplicationQueryOrder("bookStatus");
	public static final TravelApplicationQueryOrder HOTELNAME = new TravelApplicationQueryOrder("hotelName");
	public static final TravelApplicationQueryOrder ROOMDESCRIPTION = new TravelApplicationQueryOrder("roomDescription");
	public static final TravelApplicationQueryOrder CHECKINDATE = new TravelApplicationQueryOrder("checkInDate");
	public static final TravelApplicationQueryOrder CHECKOUTDATE = new TravelApplicationQueryOrder("checkOutDate");
	public static final TravelApplicationQueryOrder TRAVELLINGMODE = new TravelApplicationQueryOrder("travellingMode");
	public static final TravelApplicationQueryOrder SINGLEORRETURN = new TravelApplicationQueryOrder("singleOrReturn");
	public static final TravelApplicationQueryOrder FROMDATE = new TravelApplicationQueryOrder("fromDate");
	public static final TravelApplicationQueryOrder FROMTIME = new TravelApplicationQueryOrder("fromTime");
	public static final TravelApplicationQueryOrder TODATE = new TravelApplicationQueryOrder("toDate");
	public static final TravelApplicationQueryOrder TOTIME = new TravelApplicationQueryOrder("toTime");
	public static final TravelApplicationQueryOrder REQUESTDATE = new TravelApplicationQueryOrder("requestDate");
	public static final TravelApplicationQueryOrder APPROVEREQUESTID = new TravelApplicationQueryOrder("approveRequestId");
    public static final TravelApplicationQueryOrder CREATOR = new TravelApplicationQueryOrder("creator");
    public static final TravelApplicationQueryOrder REQUESTOR = new TravelApplicationQueryOrder("requestor");
    public static final TravelApplicationQueryOrder BOOKER = new TravelApplicationQueryOrder("booker");

    public static final TravelApplicationQueryOrder DEPARTMENT = new TravelApplicationQueryOrder("department");
    public static final TravelApplicationQueryOrder TOCITY_ENG = new TravelApplicationQueryOrder("toCity_eng");
    public static final TravelApplicationQueryOrder TOCITY_CHN = new TravelApplicationQueryOrder("toCity_chn");
    
	protected TravelApplicationQueryOrder(String value) {
		super(value);
	}
	
	public static TravelApplicationQueryOrder getEnum(String value) {
        return (TravelApplicationQueryOrder) getEnum(TravelApplicationQueryOrder.class, value);
    }

}
