package net.sourceforge.model.business.ta.query;

import org.apache.commons.lang.enums.Enum;

public class AirTicketQueryCondition extends Enum{


    public static final AirTicketQueryCondition TRAVELAPPLICATION_ID_EQ = new AirTicketQueryCondition("travelApplication_id_eq");
    public static final AirTicketQueryCondition STATUS_EQ = new AirTicketQueryCondition("status_eq");
    public static final AirTicketQueryCondition STATUS_NOTEQ = new AirTicketQueryCondition("status_noteq");
    public static final AirTicketQueryCondition TA_SITE_ID_EQ = new AirTicketQueryCondition("ta_site_id_eq");
    public static final AirTicketQueryCondition TA_DEPERTMENT_ID_EQ = new AirTicketQueryCondition("ta_department_id_eq");
    public static final AirTicketQueryCondition SUPPLIER_NAME_LIKE = new AirTicketQueryCondition("supplier_name_like");
    public static final AirTicketQueryCondition TA_REQUESTOR_NAME_LIKE = new AirTicketQueryCondition("ta_requestor_name_like");
    public static final AirTicketQueryCondition TA_IS_ON_TRAVEL_EQ = new AirTicketQueryCondition("ta_isOnTravel_eq");
    public static final AirTicketQueryCondition DEPART_TIME_LT = new AirTicketQueryCondition("departTime_lt");
    public static final AirTicketQueryCondition DEPART_TIME_GT = new AirTicketQueryCondition("departTime_gt");    

	protected AirTicketQueryCondition(String value) {
		super(value);
	}
	
	public static AirTicketQueryCondition getEnum(String value) {
        return (AirTicketQueryCondition) getEnum(AirTicketQueryCondition.class, value);
    }

}
