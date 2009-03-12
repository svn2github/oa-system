/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.service.admin;

import java.util.List;
import java.util.Map;

import net.sourceforge.model.admin.Country;
import net.sourceforge.model.admin.query.CountryQueryOrder;

/**
 * service manager interface for domain model Country
 * 
 * @author shilei
 * @version 1.0 (Nov 15, 2005)
 */
public interface CountryManager {

    /**
     * 从数据库取得指定id的Country
     * 
     * @param id
     *            Country的id
     * @return 返回指定的Country
     */
    public Country getCountry(Integer id);

    public Country insertCountry(Country country);

    public Country updateCountry(Country country);

    /**
     * get Country List Count according to conditions
     * 
     * @param conditions
     *            search condition
     * @return list count
     */
    public int getCountryListCount(Map condtions);

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
    public List getCountryList(Map condtions, int pageNo, int pageSize, CountryQueryOrder order, boolean descend);

    /**
     * get all Enabled country provicne city List
     * 在country的EnabledProvinceList属性中填充有效的省 在province的EnabledCityList属性中填充有效的城市
     * 
     * @return Enabled country List
     */
    public List listEnabledCountryProvinceCity();

    /**
     * get Enabled country and city List 在country的EnabledCityList属性中填充有效的城市
     * 
     * @return Enabled country List
     */
    public List listEnabledCountryCity();

    /**
     * promote country to global level
     * 
     * @param country
     *            the country to promote
     */
    public void promoteCountry(Country country);

    /**
     * get Enabled country List
     * 
     * @return Enabled country List
     */
    public List getEnabledCountryList();

    /**
     * delete country
     * @param country
     * @return
     */
    public boolean deleteCountry(Country country);
    
    /**
     * get country by it's chnName
     * @param chnName
     * @return
     */
    public Country getCountryByChnName(String chnName);
    
    /**
     * get country by it's engName
     * @param engName
     * @return
     */
    public Country getCountryByEngName(String engName);
    
    /**
     * get country by it's shortName
     * @param engName
     * @return
     */
    public Country getCountryByShortName(String shortName);
}
