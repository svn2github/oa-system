/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.service.admin;

import java.util.List;
import java.util.Map;

import com.aof.model.admin.Country;
import com.aof.model.admin.Province;
import com.aof.model.admin.query.ProvinceQueryOrder;

/**
 * service manager for domain model Province
 * 
 * @author shilei
 * @version 1.0 (Nov 15, 2005)
 */
public interface ProvinceManager {

    /**
     * 从数据库取得指定id的Province
     * 
     * @param id
     *            Province的id
     * @return 返回指定的Province
     */
    public Province getProvince(Integer id);

    /**
     * insert Province to database
     * 
     * @param province
     *            the Province inserted
     * @return the Province inserted
     */
    public Province insertProvince(Province province);

    /**
     * update Province to datebase
     * 
     * @param province
     *            the Province updated
     * @return the Province updated
     */
    public Province updateProvince(Province province);

    /**
     * get Province List Count according to conditions
     * 
     * @param conditions
     *            search condition
     * @return list count
     */
    public int getProvinceListCount(Map condtions);

    /**
     * get Province List according to conditions
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
     * @return Province list
     */
    public List getProvinceList(Map condtions, int pageNo, int pageSize, ProvinceQueryOrder order, boolean descend);

    /**
     * promote the province to global level
     * 
     * @param province
     */
    public void promoteProvince(Province province);

    /**
     * get Enabled Province List of Country p
     * 
     * @param c
     *            Country
     * @return Enabled Province List of Country p
     */
    public List getEnabledProvinceOfCountry(Country c);

    /**
     * delete province
     * @param p
     * @return
     */
    public boolean deleteProvince(Province p);
    
    /**
     * get province by chn name
     * @param country
     * @param chnName
     * @return
     */
    public Province getProvinceByChnName(Country country,String chnName);
    
    /**
     * get province by engName
     * @param country
     * @param chnName
     * @return
     */
    public Province getProvinceByEngName(Country country,String engName);
}
