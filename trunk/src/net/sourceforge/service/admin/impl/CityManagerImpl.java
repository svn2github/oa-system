/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.service.admin.impl;

import java.util.List;
import java.util.Map;

import net.sourceforge.dao.admin.CityDAO;
import net.sourceforge.model.admin.City;
import net.sourceforge.model.admin.Province;
import net.sourceforge.model.admin.query.CityQueryOrder;
import net.sourceforge.service.BaseManager;
import net.sourceforge.service.admin.CityManager;

/**
 * implement for CityManager
 * @author shilei
 * @version 1.0 (Nov 15, 2005)
 */
public class CityManagerImpl extends BaseManager implements CityManager {
    //private Log log = LogFactory.getLog(CityManagerImpl.class);

    private CityDAO dao;

    /**
     * @param dao
     */
    public void setCityDAO(CityDAO dao) {
        this.dao = dao;
    }
    
     /* (non-Javadoc)
     * @see net.sourceforge.service.admin.CityManager#getCity(java.lang.Integer)
     */
    public City getCity(Integer id)  {
        return dao.getCity(id);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.CityManager#updateCity(net.sourceforge.model.admin.City)
     */
    public City updateCity(City function)  {
        return dao.updateCity(function);
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.CityManager#insertCity(net.sourceforge.model.admin.City)
     */
    public City insertCity(City function)  {
        return dao.insertCity(function);
    }
    

    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.CityManager#getCityListCount(java.util.Map)
     */
    public int getCityListCount(Map conditions)  {
        return dao.getCityListCount(conditions);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.CityManager#getCityList(java.util.Map, int, int, net.sourceforge.model.admin.query.CityQueryOrder, boolean)
     */
    public List getCityList(Map conditions, int pageNo, int pageSize, CityQueryOrder order, boolean descend)  {
        return dao.getCityList(conditions, pageNo, pageSize, order, descend);
    }

	/* (non-Javadoc)
	 * @see net.sourceforge.service.admin.CityManager#promoteCity(net.sourceforge.model.admin.City)
	 */
	public void promoteCity(City city)  {
		city.setSite(null);
		updateCity(city);
		
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.service.admin.CityManager#getEnabledCityList(net.sourceforge.model.admin.Province)
	 */
	public List getEnabledCityList(Province p) {
		return dao.getEnabledCityList(p);
	}

    public boolean deleteCity(City city) {
        try{
            dao.deleteCity(city);
            return true;
        }
        catch(Throwable t)
        {
            return false;
        }    }

    public City getCityByChnName(Province p,String chnName) {
        return dao.getCityByChnName(p,chnName);
    }

    public City getCityByEngName(Province p,String engName) {
        return dao.getCityByEngName(p,engName);
    }

}
