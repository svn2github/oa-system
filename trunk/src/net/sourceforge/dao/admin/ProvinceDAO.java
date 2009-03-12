/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.dao.admin;

import java.util.List;
import java.util.Map;

import net.sourceforge.model.admin.Country;
import net.sourceforge.model.admin.Province;
import net.sourceforge.model.admin.query.ProvinceQueryOrder;

/**
 * dao interface for domain model province
 * @author shilei
 * @version 1.0 (Nov 14, 2005)
 */
public interface ProvinceDAO {

    /**
     * 从数据库取得指定id的Province
     * 
     * @param id
     *            Province的id
     * @return 返回指定的Province
     */
    public Province getProvince(Integer id);

    /**
     * get Province List Count according to conditions
     * 
     * @param conditions
     *            search condition
     * @return list count
     */
    public int getProvinceListCount(Map conditions);

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
    public List getProvinceList(Map conditions, int pageNo, int pageSize, ProvinceQueryOrder order, boolean descend);

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
     * get Enabled Province List
     * 
     * @return Enabled Province List
     */
    public List getEnabledProvinceList();

    /**
     * get Enabled Province List of Country p
     * 
     * @param c
     *            Country
     * @return Enabled Province List of Country p
     */
    public List getEnabledProvinceList(Country c);

    /**
     * delete province
     * @param p
     */
    public void deleteProvince(Province p);

    /**
     * get Province by chnName 
     * @param country
     * @param chnName
     * @return
     */
    public Province getProvinceByChnName(Country country, String chnName);

    /**
     * get Province by engName 
     * @param country
     * @param engName
     * @return
     */
    public Province getProvinceByEngName(Country country, String engName);

}
