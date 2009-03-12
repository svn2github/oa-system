/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.dao.admin;

import java.util.List;
import java.util.Map;

import net.sourceforge.model.admin.TravelGroup;
import net.sourceforge.model.admin.query.TravelGroupQueryOrder;

/**
 * dao interface for domain model Travel Group
 * 
 * @author shilei
 * @version 1.0 (Nov 15, 2005)
 */
public interface TravelGroupDAO {

    /**
     * 从数据库取得指定id的Travel Group
     * 
     * @param id
     *            Travel Group的id
     * @return 返回指定的Travel Group
     */
    public TravelGroup getTravelGroup(Integer id);

    /**
     * get Travel Group List Count according to conditions
     * 
     * @param conditions
     *            search condition
     * @return list count
     */
    public int getTravelGroupListCount(Map conditions);

    /**
     * get Travel Group List according to conditions
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
     * @return Travel Group list
     */
    public List getTravelGroupList(Map conditions, int pageNo, int pageSize, TravelGroupQueryOrder order, boolean descend);

    /**
     * insert Travel Group to database
     * 
     * @param travel
     *            Group the Travel Group inserted
     * @return the Travel Group inserted
     */
    public TravelGroup insertTravelGroup(TravelGroup travelGroup);

    /**
     * update Travel Group to datebase
     * 
     * @param travelGroup
     *            the Travel Group updated
     * @return the Travel Group updated
     */
    public TravelGroup updateTravelGroup(TravelGroup travelGroup);

}
