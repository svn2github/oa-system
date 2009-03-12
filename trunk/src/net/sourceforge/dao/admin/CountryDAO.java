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
import net.sourceforge.model.admin.query.CountryQueryOrder;

/**
 * @author sl
 * @version 1.0 (Nov 13, 2005)
 */
public interface CountryDAO {
    /**
     * 从数据库取得指定id的Country
     * 
     * @param id
     *            Country的id
     * @return 返回指定的Country
     */
    public Country getCountry(Integer id);

    /**
     * get Country List Count according to conditions
     * 
     * @param conditions
     *            search condition
     * @return list count
     */
    public int getCountryListCount(Map conditions);

    /**
     * get Country List according to conditions
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
     * @return Country list
     */
    public List getCountryList(Map conditions, int pageNo, int pageSize, CountryQueryOrder order, boolean descend);

    /**
     * insert Country to database
     * 
     * @param country
     *            Country inserted
     * @return Country inserted
     */
    public Country insertCountry(Country country);

    /**
     * update country to datebase
     * 
     * @param country
     *            country updated
     * @return country updated
     */
    public Country updateCountry(Country country);

    /**
     * get Enabled country List
     * 
     * @return Enabled country List
     */
    public List listEnabledCountry();

    /**
     * delete country
     * @param country
     */
    public void deleteCountry(Country country);

    /**
     * get country by chnName
     * @param chnName
     * @return
     */
    public Country getCountryByChnName(String chnName);

    /**
     * get country by engName
     * @param engName
     * @return
     */
    public Country getCountryByEngName(String engName);
    
    /**
     * get country by shortName
     * @param engName
     * @return
     */
    public Country getCountryByShortName(String sn);


}
