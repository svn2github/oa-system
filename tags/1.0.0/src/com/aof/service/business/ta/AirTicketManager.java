/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.service.business.ta;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.aof.model.admin.Site;
import com.aof.model.admin.User;
import com.aof.model.business.ta.AirTicket;
import com.aof.model.business.ta.TravelApplication;
import com.aof.model.business.ta.query.AirTicketQueryOrder;

/**
 * service interface for domain model air ticket
 * 
 * @author shi1
 * @version 1.0 (Nov 25, 2005)
 */
public interface AirTicketManager {

    /**
     * 根据id得到机票
     * 
     * @param id
     * @return
     */
    public AirTicket getAirTicket(Integer id);

    /**
     * 新建机票
     * 
     * @param airTicket
     * @return
     */
    public AirTicket insertAirTicket(AirTicket airTicket);

    /**
     * 更新机票
     * 
     * @param airTicket
     * @return
     */
    public AirTicket updateAirTicket(AirTicket airTicket);
    
    public void deleteAirTicket(Integer id, String taId);

    /**
     * 取得对机票的查询结果数量
     * 
     * @param condtions
     * @return
     */
    public int getAirTicketListCount(Map condtions);

    /**
     * 根据条件查询机票
     * 
     * @param condtions
     *            查询条件
     * @param pageNo
     *            页编号(从0开始,-1则忽略)
     * @param pageSize
     *            每页条目数,-1则忽略
     * @param order
     *            排序条件
     * @param descend
     *            是否降序
     * @return 查询结果
     */
    public List getAirTicketList(Map condtions, int pageNo, int pageSize, AirTicketQueryOrder order, boolean descend);

    /**
     * 取得出差申请的机票
     * 
     * @param ta
     * @return
     */
    public AirTicket getTravelApplicationAirTicket(TravelApplication ta);

    /**
     * 取得指定AirTicket对象的AirTicketRecharge列表
     * 
     * @param at
     *            AirTicket对象
     * @return 和AirTicket对象相关的AirTicketRecharge对象列表
     */
    public List getAirTicketRechargeList(AirTicket at);

    /**
     * 查询已经产生PO_ITEM的机票
     * 
     * @param condtions
     *            查询条件
     * @return 查询结果
     */
    public List getReceivedAirTicketList(Map conditions);

    /**
     * 查询Site和机票相关又没有建立PO的po_item id
     * 
     * @param s
     *            Site
     * @return 查询结果
     */
    public List getSiteReceivedAirTicketPoItemIdList(Site s);

    /**
     * 从提供的po_item id
     * list创建一个或者多个PO，相同supplier和exchangeRate的po_item将创建在一个PO中，所有这些PO属于指定的site。
     * 调用者需自行保证这些id指向的po_item由received air ticket产生，并且属于指定的site
     * 函数仅仅会检查这些po_item还没有和已有的PO有关系。
     * 
     * @param s
     *            site
     * @param currentyUser
     *            creator of created po
     * @param ids
     *            collection contains po_item id
     */
    public void createMergePO(Site s, User currenyUser, Collection ids);

}
