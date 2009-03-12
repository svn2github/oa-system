/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.service.admin;

import java.util.List;
import java.util.Map;

import net.sourceforge.model.admin.City;
import net.sourceforge.model.admin.Hotel;
import net.sourceforge.model.admin.TravelGroupDetail;
import net.sourceforge.model.admin.query.HotelQueryOrder;

/**
 * service manager interface for domain model Hotel
 * 
 * @author shilei
 * @version 1.0 (Nov 15, 2005)
 */
public interface HotelManager {

    /**
     * 从数据库取得指定id的Hotel
     * 
     * @param id
     *            Hotel的id
     * @return 返回指定的Hotel
     */
    public Hotel getHotel(Integer id);

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
     * get Hotel List Count according to conditions
     * 
     * @param conditions
     *            search condition
     * @return list count
     */
    public int getHotelListCount(Map condtions);

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
    public List getHotelList(Map condtions, int pageNo, int pageSize, HotelQueryOrder order, boolean descend);

    /**
     * response to promote request
     * 
     * @param id
     *            id of hotel
     * @return the hotel responsed
     */
    public Hotel reponsePromote(Integer id);


    /**
     * send request to promote Hotel
     * 
     * @param id
     *            id of hotel
     * @param promoteMessage
     *            promote message
     * @return the hotel responsed
     */
    public Hotel requestPromote(Integer id, String promoteMessage);

    /**
     * get Enabled Hotel Room List of city
     * 返回有效的hotel的list,在hotel的rooms属性里填充有效的room List
     * 
     * @param city
     * @param isLocal
     *            whether to be local
     * @param tgd
     *            TravelGroupDetail
     * @return list of hotel
     */
    public List getEnabledHotelRoomList(City city, boolean isLocal, TravelGroupDetail tgd);

    /**
     * 返回有效的hotel的list,在hotel的rooms属性里填充有效的room List
     * @param city
     * @return
     */
    public List getEnabledHotelRoomList(City city);
}
