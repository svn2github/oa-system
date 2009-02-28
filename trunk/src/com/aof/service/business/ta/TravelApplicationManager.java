/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.service.business.ta;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.aof.model.admin.User;
import com.aof.model.business.ta.AirTicket;
import com.aof.model.business.ta.TravelApplication;
import com.aof.model.business.ta.query.TravelApplicationQueryOrder;

/**
 * service manager for domain model travel application
 * @author shi1
 * @version 1.0 (Nov 23, 2005)
 */
public interface TravelApplicationManager {

    /**
     * get Travel Application by id
     * @param id
     *      id of travel application
     * @return TravelApplication object
     */
    public TravelApplication getTravelApplication(String id) ;

    /**
     * insert new Travel Application to db
     * @param travelApplication
     *      the Travel Application inserted
     * @param approveRequestList
     *      approve Request List
     * @return the Travel Application inserted
     */
    public TravelApplication insertTravelApplication(TravelApplication travelApplication,List approveRequestList,boolean isDraft,User user) ;
    
    /**
     * 更新出差申请
     * @param travelApplication
     * @return
     */
    public TravelApplication updateTravelApplication(TravelApplication travelApplication) ;

    /**
     * 更新出差申请
     * @param travelApplication
     * @param approveRequestList
     * @param isDraft
     * @return
     */
    public TravelApplication updateTravelApplication(TravelApplication travelApplication,List approveRequestList,boolean isDraft,User user) ;

    /**
     * 取得对出差申请的查询结果数量
     * @param condtions
     * @return
     */
    public int getTravelApplicationListCount(Map condtions) ;

    /**
     * 根据条件查询出差申请
     * @param condtions
     *      查询条件
     * @param pageNo
     *      页编号(从0开始,-1则忽略)
     * @param pageSize
     *      每页条目数,-1则忽略
     * @param order
     *      排序条件
     * @param descend
     *      是否降序
     * @return 查询结果
     */
    public List getTravelApplicationList(Map condtions, int pageNo, int pageSize, TravelApplicationQueryOrder order, boolean descend) ;
    
    /**
     * 返回指定approveRequestId的TravelApplication
     * @param approveRequestId TravelApplication的approveRequestId
     * @return 指定approveRequestId的TravelApplication
     */
    public TravelApplication getTravelApplicationByApproveRequestId(String approveRequestId);

    /**
     * 出差申请相应的采购
     * @param ta
     *      出差申请
     * @param at
     *      机票
     * @param airTicketRechargeList
     *      机票Recharge List
     * @param booker
     *      预定人
     * @param confirmed
     */
    public void book(TravelApplication ta, List atList,User user) ;
    
    

    /**
     * 取消机票
     * @param airTicketId
     * @param price
     */
    public AirTicket cancelAirTicket(Integer airTicketId,BigDecimal price,User user) ;
    
    /**
     * 删除机票
     * @param airTicketId
     */
    public void deleteAirTicket(Integer airTicketId);
    
    /**
     * 确认机票
     * @param atId
     */
    public AirTicket confirmAirTicket(Integer atId) ;

    /**
     * 删除出差申请
     * @param ta
     */
    public void deleteTravelApplication(TravelApplication ta,User user);

    /**
     * 拒绝出差申请
     * @param travelApplication
     */
    public void rejectTravelApplication(TravelApplication travelApplication, User user, String comment);

    public void assignBooker(String taId, User booker);
    
    public void withdrawTravelApplication(TravelApplication ta,User user);

    /**
     * 审批通过出差申请
     * @param travelApplication
     */
    public void approveTravelApplication(TravelApplication travelApplication,User user);

    public List getAirTicketListWithDetails(TravelApplication ta);

    public List getAirTicketList(TravelApplication ta);

    public AirTicket getAirTicketWithDetails(Integer id);

    public TravelApplication updateAirTickets(String taId,List airTicketList,User user);

    public TravelApplication updateAirTickets(String taId,List airTicketList,boolean setIsOnTravel,User user);
    
}
