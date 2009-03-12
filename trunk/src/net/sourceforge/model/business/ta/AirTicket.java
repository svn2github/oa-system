/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.business.ta;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.sourceforge.model.Loggable;
import net.sourceforge.model.admin.Site;
import net.sourceforge.model.business.BaseRecharge;
import net.sourceforge.model.business.Rechargeable;

/**
 * A class that represents a row in the 'air_ticket' table. This class may be
 * customized as it is never re-generated after being created.
 */
public class AirTicket extends AbstractAirTicket implements Serializable, Rechargeable,Loggable {
    /**
     * Simple constructor of AirTicket instances.
     */
    public AirTicket() {
    }

    /**
     * Constructor of AirTicket instances given a simple primary key.
     * 
     * @param id
     */
    public AirTicket(Integer id) {
        super(id);
    }

    /* Add customized code below */

    /* (non-Javadoc)
     * @see net.sourceforge.model.business.Rechargeable#createNewRechargeObj()
     */
    public BaseRecharge createNewRechargeObj() {
        AirTicketRecharge atr = new AirTicketRecharge();
        atr.setAirTicket(this);
        return atr;
    }

    public Site getLogSite() {
        return this.getTravelApplication().getDepartment().getSite();
    }

    public String getLogTargetName() {
        return LOG_TARGET_NAME;
    }

    public String getLogTargetId() {
        return this.getTravelApplication().getId();
    }

    public String[][] getLogFieldInfo(String action) {
        return (String[][]) actionFieldInfo.get(action);
    }
    
    private static final String LOG_TARGET_NAME="TA";
    public static final String LOG_ACTION_CONFIRM = "Confirm";
    public static final String LOG_ACTION_CANCELAIRTICKET = "Cancel Air Ticket";    
    public static final String LOG_ACTION_APPROVE = "Approve";
    public static final String LOG_ACTION_BOOK = "Book";
    
    private static final Map actionFieldInfo = new HashMap();
    static {
        actionFieldInfo.put(LOG_ACTION_BOOK, 
                new String[][] { 
                    { "Hotel", "travelApplication.nameOfHotel", null },
                    { "Room", "travelApplication.nameOfRoom", null },
                    { "Check In Date", "travelApplication.checkInDate", null },
                    { "Check Out Date", "travelApplication.checkOutDate", null },
                    { "Travel Mode", "travelApplication.travellingMode", "label" },
                    { "Air Ticket Supplier", "supplier","name"  },
                    { "Air Ticket Flight No", "flightNo",null  },
                    { "Air Ticket Price", "price",null  },
                    { "Air Ticket Currency", "exchangeRate.currency","code"},
        });
        
        actionFieldInfo.put(LOG_ACTION_CONFIRM, 
                new String[][] { 
                    { "Hotel", "travelApplication.nameOfHotel", null },
                    { "Room", "travelApplication.nameOfRoom", null },
                    { "Check In Date", "travelApplication.checkInDate", null },
                    { "Check Out Date", "travelApplication.checkOutDate", null },
                    { "Travel Mode", "travelApplication.travellingMode", "label" },
                    { "Air Ticket Supplier", "supplier","name"  },
                    { "Air Ticket Flight No", "flightNo",null  },
                    { "Air Ticket Price", "price",null  },
                    { "Air Ticket Currency", "exchangeRate.currency","code"},
        });
        
        actionFieldInfo.put(LOG_ACTION_APPROVE, 
                new String[][] { 
                    { "Amount", "price",null  },
        });
        
        actionFieldInfo.put(LOG_ACTION_CANCELAIRTICKET, 
                new String[][] { 
                    { "Flight No", "flightNo",null  },
        });
    }
    
   
    private Collection recharges;

    public Collection getRecharges() {
        return recharges;
    }

    public void setRecharges(Collection recharges) {
        this.recharges = recharges;
    }

}
