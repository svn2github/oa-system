/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.service.admin;

import java.util.List;
import java.util.Map;

import com.aof.model.admin.City;
import com.aof.model.admin.Province;
import com.aof.model.admin.query.CityQueryOrder;

/**
 * service manager interface for domain model city
 * 
 * @author shilei
 * @version 1.0 (Nov 15, 2005)
 */
public interface CityManager {

    /**
     * 从数据库取得指定id的City
     * 
     * @param id
     *            city的id
     * @return 返回指定的City
     */
    public City getCity(Integer id);

    /**
     * insert city to database
     * 
     * @param city
     *            the city inserted
     * @return the city inserted
     */
    public City insertCity(City city);

    /**
     * update city to datebase
     * 
     * @param city
     *            the city updated
     * @return the city updated
     */
    public City updateCity(City city);

    /**
     * get City List Count according to conditions
     * 
     * @param conditions
     *            search condition
     * @return list count
     */
    public int getCityListCount(Map condtions);

    /**
     * get City List according to conditions
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
     * @return city list
     */
    public List getCityList(Map condtions, int pageNo, int pageSize, CityQueryOrder order, boolean descend);

    /**
     * promoteCity to global level
     * 
     * @param city
     *            the city to promote
     */
    public void promoteCity(City city);

    /**
     * get Enabled City List of Province p
     * 
     * @param p
     *            Province
     * @return Enabled City List
     */
    public List getEnabledCityList(Province p);

    /**
     * delete city
     * @param city
     * @return
     */
    public boolean deleteCity(City city);

    /**
     * get city by it's chnName
     * @param p
     * @param chnName
     * @return
     */
    public City getCityByChnName(Province p,String chnName);

    /**
     * get city by it's engName
     * @param p
     * @param engName
     * @return
     */
    public City getCityByEngName(Province p,String engName);
}
