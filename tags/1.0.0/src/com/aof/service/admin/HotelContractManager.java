/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.service.admin;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.aof.model.admin.HotelContract;
import com.aof.model.admin.query.HotelContractQueryOrder;

/**
 * service manager interface for domain model HotelContract
 * 
 * @author shilei
 * @version 1.0 (Nov 15, 2005)
 */
public interface HotelContractManager {

    /**
     * 从数据库取得指定id的Hotel Contract
     * 
     * @param id
     *            Hotel Contract的id
     * @return 返回指定的Hotel Contract
     */
    public HotelContract getHotelContract(Integer id);

    public HotelContract insertHotelContract(HotelContract hotelContract);

    public HotelContract updateHotelContract(HotelContract hotelContract);

    /**
     * get Hotel Contract List Count according to conditions
     * 
     * @param conditions
     *            search condition
     * @return list count
     */
    public int getHotelContractListCount(Map condtions);

    /**
     * get Hotel Contract List according to conditions
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
     * @return Hotel Contract list
     */
    public List getHotelContractList(Map condtions, int pageNo, int pageSize, HotelContractQueryOrder order, boolean descend);

    /**
     * insert Hotel Contract to database
     * 
     * @param hotelContract
     *            the Hotel Contract inserted
     * @return the Hotel Contract inserted
     */
    public HotelContract insertHotelContract(HotelContract hotelContract, InputStream inputStream);

    /**
     * 取出从数据库取得指定id的Hotel Contract的内容
     * 
     * @param id
     *            id of Hotel Contract
     * @return content stream for designated hotel contract
     */
    public InputStream getHotelContractContent(Integer id);
}
