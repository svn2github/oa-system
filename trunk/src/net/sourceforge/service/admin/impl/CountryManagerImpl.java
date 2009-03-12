/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.service.admin.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sourceforge.dao.admin.CityDAO;
import net.sourceforge.dao.admin.CountryDAO;
import net.sourceforge.dao.admin.ProvinceDAO;
import net.sourceforge.model.admin.City;
import net.sourceforge.model.admin.Country;
import net.sourceforge.model.admin.Province;
import net.sourceforge.model.admin.query.CountryQueryOrder;
import net.sourceforge.service.BaseManager;
import net.sourceforge.service.admin.CountryManager;

/**
 * implement for CountryManager
 * @author shilei
 * @version 1.0 (Nov 15, 2005)
 */
public class CountryManagerImpl extends BaseManager implements CountryManager {
    //private Log log = LogFactory.getLog(CountryManagerImpl.class);

    private CountryDAO dao;
    private ProvinceDAO provinceDAO;
    private CityDAO cityDAO;

    /**
     * @param dao
     */
    public void setCountryDAO(CountryDAO dao) {
        this.dao = dao;
    }
    
     /* (non-Javadoc)
     * @see net.sourceforge.service.admin.CountryManager#getCountry(java.lang.Integer)
     */
    public Country getCountry(Integer id)  {
        return dao.getCountry(id);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.CountryManager#updateCountry(net.sourceforge.model.admin.Country)
     */
    public Country updateCountry(Country function)  {
        return dao.updateCountry(function);
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.CountryManager#insertCountry(net.sourceforge.model.admin.Country)
     */
    public Country insertCountry(Country function)  {
        return dao.insertCountry(function);
    }
    

    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.CountryManager#getCountryListCount(java.util.Map)
     */
    public int getCountryListCount(Map conditions)  {
        return dao.getCountryListCount(conditions);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.CountryManager#getCountryList(java.util.Map, int, int, net.sourceforge.model.admin.query.CountryQueryOrder, boolean)
     */
    public List getCountryList(Map conditions, int pageNo, int pageSize, CountryQueryOrder order, boolean descend){
        return dao.getCountryList(conditions, pageNo, pageSize, order, descend);
    }

	/**
	 * @param cityDAO
	 */
	public void setCityDAO(CityDAO cityDAO) {
		this.cityDAO = cityDAO;
	}

	/**
	 * @param provinceDAO
	 */
	public void setProvinceDAO(ProvinceDAO provinceDAO) {
		this.provinceDAO = provinceDAO;
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.service.admin.CountryManager#listEnabledCountryProvinceCity()
	 */
	public List listEnabledCountryProvinceCity() {
		List countryList=dao.listEnabledCountry();
		List cityList=cityDAO.getEnabledCityList();
		List provinceList=provinceDAO.getEnabledProvinceList();
		
		for (Iterator iter = countryList.iterator(); iter.hasNext();) {
			Country c = (Country) iter.next();
			c.setEnabledProvinceList(new ArrayList());
		}
		
		for (Iterator iter = provinceList.iterator(); iter.hasNext();) {
			Province p = (Province) iter.next();
			p.setEnabledCityList(new ArrayList());
		}
		
		for (Iterator iter = provinceList.iterator(); iter.hasNext();) {
			Province p = (Province) iter.next();
			if(p.getCountry().getEnabledProvinceList()!=null)
				p.getCountry().getEnabledProvinceList().add(p);
		}
		
		for (Iterator iter = cityList.iterator(); iter.hasNext();) {
			City  c = (City) iter.next();
			if(c.getProvince().getEnabledCityList()!=null)
				c.getProvince().getEnabledCityList().add(c);
		}
		
		return countryList;
	}
	
	/* (non-Javadoc)
	 * @see net.sourceforge.service.admin.CountryManager#listEnabledCountryCity()
	 */
	public List listEnabledCountryCity() {
		List countryList=dao.listEnabledCountry();
		List cityList=cityDAO.getEnabledCityList();
		for (Iterator iter = countryList.iterator(); iter.hasNext();) {
			Country c = (Country) iter.next();
			c.setEnabledCityList(new ArrayList());
		}
		
		for (Iterator iter = cityList.iterator(); iter.hasNext();) {
			City  c = (City) iter.next();
			c.getProvince().getCountry().getEnabledCityList().add(c);
		}
		
		return countryList;
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.service.admin.CountryManager#promoteCountry(net.sourceforge.model.admin.Country)
	 */
	public void promoteCountry(Country country){
		country.setSite(null);
		this.updateCountry(country);
		
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.service.admin.CountryManager#getEnabledCountryList()
	 */
	public List getEnabledCountryList() {
		return dao.listEnabledCountry();
	}

    public boolean deleteCountry(Country country) {
        dao.deleteCountry(country);
        return true;
    }

    public Country getCountryByChnName(String chnName) {
        return dao.getCountryByChnName(chnName);
    }

    public Country getCountryByEngName(String engName) {
        return dao.getCountryByEngName(engName);
    }

    public Country getCountryByShortName(String shortName) {
        return dao.getCountryByShortName(shortName);
    }

}
