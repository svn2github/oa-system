package net.sourceforge.model.business.ta.query;

import org.apache.commons.lang.enums.Enum;

public class AirTicketQueryOrder extends Enum {

    public static final AirTicketQueryOrder ID = new AirTicketQueryOrder("id");
    
    public static final AirTicketQueryOrder SUPPLIER = new AirTicketQueryOrder("supplier.id");
    public static final AirTicketQueryOrder DATE = new AirTicketQueryOrder("leaveDepartTime");

    protected AirTicketQueryOrder(String value) {
        super(value);
    }

    public static AirTicketQueryOrder getEnum(String value) {
        return (AirTicketQueryOrder) getEnum(AirTicketQueryOrder.class, value);
    }

}
