/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.web.struts.form.business.ta;

import com.aof.model.business.ta.AirTicket;
import com.aof.model.business.ta.TravelApplication;


public class TravelApplicationPurchaseForm {

    
    
    private AirTicket airTicket;
    
    private TravelApplication travelApplication;

    public AirTicket getAirTicket() {
        return airTicket;
    }

    public void setAirTicket(AirTicket airTicket) {
        this.airTicket = airTicket;
    }

    public TravelApplication getTravelApplication() {
        return travelApplication;
    }

    public void setTravelApplication(TravelApplication travelApplication) {
        this.travelApplication = travelApplication;
    }


    
    

}
