/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.dao.business.ta;

import java.util.List;
import java.util.Map;

import net.sourceforge.model.admin.Site;
import net.sourceforge.model.business.ta.AirTicket;
import net.sourceforge.model.business.ta.AirTicketRecharge;
import net.sourceforge.model.business.ta.TravelApplication;
import net.sourceforge.model.business.ta.query.AirTicketQueryOrder;

/**
 * DAO interface for domain model AirTicket
 * 
 * @author shi1
 * @version 1.0 (Nov 25, 2005)
 */
public interface AirTicketDAO {

    /**
     * 根据id取得机票
     * @param id
     * @return
     */
    public AirTicket getAirTicket(Integer id);

    /**
     * 取得对机票的查询结果数量
     * @param condtions
     * @return
     */
    public int getAirTicketListCount(Map conditions);

    /**
     * 根据条件查询机票
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
    public List getAirTicketList(Map conditions, int pageNo, int pageSize, AirTicketQueryOrder order, boolean descend);

    /**
     * 查询已经产生PO_ITEM的机票
     * @param condtions
     *      查询条件
     * @return 查询结果
     */
    public List getReceivedAirTicketList(Map conditions);

    /**
     * 新建机票
     * @param airTicket
     * @return
     */
    public AirTicket insertAirTicket(AirTicket airTicket);

    /**
     * 更新机票
     * @param airTicket
     * @return
     */
    public AirTicket updateAirTicket(AirTicket airTicket);

    /**
     * 删除机票
     * @param at
     */
    public void deleteAirTicket(AirTicket at);

    /**
     * 新增Air Ticket Recharge
     * @param atr
     */
    public void insertAirTicketRecharge(AirTicketRecharge atr);

    /**
     * 取得指定AirTicket对象的AirTicketRecharge列表
     * 
     * @param at
     *            AirTicket对象
     * @return 和AirTicket对象相关的AirTicketRecharge对象列表
     */
    public List getAirTicketRechargeList(AirTicket at);

    /**
     * 查询Site和机票相关又没有建立PO的po_item id
     * 
     * @param s
     *            Site
     * @return 查询结果
     */
    public List getSiteReceivedAirTicketPoItemIdList(Site s);

    /**
     * get ta's air ticket
     * @param travelApplication
     * @return
     */
    public AirTicket getAirTicketByTravelApplication(TravelApplication travelApplication);

    public void deleteAirTicket(Integer id);

}
