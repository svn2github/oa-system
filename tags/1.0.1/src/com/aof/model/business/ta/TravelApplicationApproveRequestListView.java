/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.business.ta;


/**
 * 该类是TravelApplication的Approve查询显示列表对象
 * @author ych
 * @version 1.0 (Nov 16, 2005)
 */
public class TravelApplicationApproveRequestListView {
    
    private TravelApplication travelApplication;
    
    private TravelApplicationApproveRequest travelApplicationApproveRequest;
    

    
    /**
     * @return Returns the travelApplicationApproveRequest.
     */
    public TravelApplicationApproveRequest getTravelApplicationApproveRequest() {
        return travelApplicationApproveRequest;
    }

    /**
     * @param travelApplicationApproveRequest The travelApplicationApproveRequest to set.
     */
    public void setTravelApplicationApproveRequest(TravelApplicationApproveRequest travelApplicationApproveRequest) {
        this.travelApplicationApproveRequest = travelApplicationApproveRequest;
    }


    /**
     * @return Returns the travelApplication.
     */
    public TravelApplication getTravelApplication() {
        return travelApplication;
    }

    /**
     * @param travelApplication The travelApplication to set.
     */
    public void setTravelApplication(TravelApplication travelApplication) {
        this.travelApplication = travelApplication;
    }
    
    
}
