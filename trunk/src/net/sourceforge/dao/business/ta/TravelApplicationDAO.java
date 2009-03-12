/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.dao.business.ta;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sourceforge.model.admin.SiteMailReminder;
import net.sourceforge.model.business.ta.AirTicket;
import net.sourceforge.model.business.ta.TravelApplication;
import net.sourceforge.model.business.ta.TravelApplicationApproveRequest;
import net.sourceforge.model.business.ta.TravelApplicationHistory;
import net.sourceforge.model.business.ta.query.TravelApplicationQueryOrder;

/**
 * dao interface for Travel Application
 * 
 * @author shilei
 * @version 1.0 (Nov 13, 2005)
 */
public interface TravelApplicationDAO {

    /**
     * get Travel Application by id
     * 
     * @param id
     *            id of Travel Application
     * @return Travel Application identified by id
     */
    public TravelApplication getTravelApplication(String id);

    /**
     * get Travel Application List Count by conditions
     * 
     * @param conditions
     *            查询条件
     * @return Count of Travel Application List
     */
    public int getTravelApplicationListCount(Map conditions);

    /**
     * get Travel Application List by conditions
     * 
     * @param conditions
     *            search condition
     * @param pageNo
     *            start page no(0 based), ignored if is -1
     * @param pageSize
     *            page size, ignored if is -1
     * @param order
     *            search order
     * @param descend
     *            asc or desc
     * @return Travel Application list
     */
    public List getTravelApplicationList(Map conditions, int pageNo, int pageSize, TravelApplicationQueryOrder order, boolean descend);

    /**
     * insert Application updated to database
     * 
     * @param travelApplication
     *            the travel Application inserted
     * @return the travel Application inserted
     */
    public TravelApplication insertTravelApplication(TravelApplication travelApplication);

    /**
     * update Application updated to database
     * 
     * @param travelApplication
     *            the travel Application updated
     * @return the travel Application updated
     */
    public TravelApplication updateTravelApplication(TravelApplication travelApplication);

    /**
     * @return Last Code of Travel Application
     */
    public String getLastTravelApplicationCode();
    
    /**
     * 返回指定approveRequestId的TravelApplication
     * @param approveRequestId TravelApplication的approveRequestId
     * @return 指定approveRequestId的TravelApplication
     */
    public TravelApplication getTravelApplicationByApproveRequestId(String approveRequestId);

    /**
     * save Travel Application Approve Requst
     * @param taar
     */
    public void saveTravelApplicationApproveRequst(TravelApplicationApproveRequest taar);

    /**
     * delete Travel Application
     * @param ta
     */
    public void deleteTravelApplication(TravelApplication ta);
    
    /**
     * save Travel Application History
     * @param taHistory
     */
    public void saveTravelApplicationHistory(TravelApplicationHistory taHistory);

    /**
     * get Max TravelApplication Id Begin With
     * @param prefix
     * @return
     */
    public String getMaxTravelApplicationIdBeginWith(String prefix);

    /**
     * delete ApproveRequest of ta
     * @param ta
     */
    public void deleteTravelApplicationApproveRequest(TravelApplication ta);

    public List getReceptionNotRespondedTravelApplication(Date now, SiteMailReminder smr);

    public List getAirTicketListWithDetails(TravelApplication ta);
    
    public List getAirTicketList(TravelApplication ta);

    public AirTicket getAirTicketWithDetails(Integer id);

    public List getAirTicketIdList(TravelApplication ta);

    public List getBookedAirTicketIdList(TravelApplication ta);

    public boolean hasBookedAirTicket(TravelApplication ta);

    public boolean hasReceivedAirTicket(TravelApplication ta);

    public void lockTravelApplication(TravelApplication ta);
}
