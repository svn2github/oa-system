/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.dao.admin;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sourceforge.model.admin.GlobalMailReminder;
import net.sourceforge.model.admin.Hotel;
import net.sourceforge.model.admin.Site;
import net.sourceforge.model.admin.query.HotelQueryOrder;

/**
 * dao interface for domain model Hotel
 * 
 * @author shilei
 * @version 1.0 (Nov 14, 2005)
 */
public interface HotelDAO {

    /**
     * 从数据库取得指定id的Hotel
     * 
     * @param id
     *            Hotel的id
     * @return 返回指定的Hotel
     */
    public Hotel getHotel(Integer id);

    /**
     * get Hotel List Count according to conditions
     * 
     * @param conditions
     *            search condition
     * @return list count
     */
    public int getHotelListCount(Map conditions);

    /**
     * get Hotel List according to conditions
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
     * @return Hotel list
     */
    public List getHotelList(Map conditions, int pageNo, int pageSize, HotelQueryOrder order, boolean descend);

    /**
     * insert Hotel to database
     * 
     * @param hotel
     *            the Hotel inserted
     * @return the Hotel inserted
     */
    public Hotel insertHotel(Hotel hotel);

    /**
     * update Hotel to datebase
     * 
     * @param hotel
     *            the Hotel updated
     * @return the Hotel updated
     */
    public Hotel updateHotel(Hotel hotel);
    
    /**
     * 获得指定site的需要email提醒的尚未response的hotel列表
     * @param site
     * @param time
     * @param gmr
     * @return
     */
    public List getHotelMaintainerNotResponsedList(Site site, final Date time, final GlobalMailReminder gmr);

}
