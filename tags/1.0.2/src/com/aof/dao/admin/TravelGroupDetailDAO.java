/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.dao.admin;

import java.util.List;
import java.util.Map;

import com.aof.model.admin.TravelGroupDetail;
import com.aof.model.admin.query.TravelGroupDetailQueryOrder;

/**
 * dao interface for domain model Travel Group Detail
 * 
 * @author shilei
 * @version 1.0 (Nov 15, 2005)
 */
public interface TravelGroupDetailDAO {

    /**
     * 从数据库取得指定id的Travel Group Detail
     * 
     * @param id
     *            Travel Group Detail的id
     * @return 返回指定的Travel Group Detail
     */
    public TravelGroupDetail getTravelGroupDetail(Integer travelGroup_id, Integer expenseSubCategroy_id);

    /**
     * get Travel Group Detail List Count according to conditions
     * 
     * @param conditions
     *            search condition
     * @return list count
     */
    public int getTravelGroupDetailListCount(Map conditions);

    /**
     * get Travel Group Detail List according to conditions
     * 
     * @param conditions
     *            search condition
     * @param pageNo
     *            start page no(0 based), ignored if -1
     * @param pageSize
     *            page size, ignored if -1
     * @param order
     *            search order
     * @param descend
     *            asc or desc
     * @return Travel Group Detail list
     */
    public List getTravelGroupDetailList(Map conditions, int pageNo, int pageSize, TravelGroupDetailQueryOrder order, boolean descend);

    /**
     * insert Travel Group Detail to database
     * 
     * @param travelGroupDetail
     *            Group the Travel Group Detail inserted
     * @return the Travel Group Detail inserted
     */
    public TravelGroupDetail insertTravelGroupDetail(TravelGroupDetail travelGroupDetail);

    /**
     * update Travel Group Detail to datebase
     * 
     * @param travelGroupDetail
     *            the Travel Group Detail updated
     * @return the Travel Group Detail updated
     */
    public TravelGroupDetail updateTravelGroupDetail(TravelGroupDetail travelGroupDetail);

    /**
     * insert or update Travel Group Detail to datebase
     * 
     * @param travelGroupDetail
     *            the Travel Group Detail saved
     * @return the Travel Group Detail saved
     */
    public TravelGroupDetail insertOrUpdateTravelGroupDetail(TravelGroupDetail travelGroupDetail);

}
