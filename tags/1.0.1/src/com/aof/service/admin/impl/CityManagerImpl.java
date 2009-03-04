/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.service.admin.impl;

import java.util.List;
import java.util.Map;

import com.aof.dao.admin.CityDAO;
import com.aof.model.admin.City;
import com.aof.model.admin.Province;
import com.aof.model.admin.query.CityQueryOrder;
import com.aof.service.BaseManager;
import com.aof.service.admin.CityManager;

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
     * @see com.aof.service.admin.CityManager#getCity(java.lang.Integer)
     */
    public City getCity(Integer id)  {
        return dao.getCity(id);
    }

    /* (non-Javadoc)
     * @see com.aof.service.admin.CityManager#updateCity(com.aof.model.admin.City)
     */
    public City updateCity(City function)  {
        return dao.updateCity(function);
    }
    
    /* (non-Javadoc)
     * @see com.aof.service.admin.CityManager#insertCity(com.aof.model.admin.City)
     */
    public City insertCity(City function)  {
        return dao.insertCity(function);
    }
    

    /* (non-Javadoc)
     * @see com.aof.service.admin.CityManager#getCityListCount(java.util.Map)
     */
    public int getCityListCount(Map conditions)  {
        return dao.getCityListCount(conditions);
    }

    /* (non-Javadoc)
     * @see com.aof.service.admin.CityManager#getCityList(java.util.Map, int, int, com.aof.model.admin.query.CityQueryOrder, boolean)
     */
    public List getCityList(Map conditions, int pageNo, int pageSize, CityQueryOrder order, boolean descend)  {
        return dao.getCityList(conditions, pageNo, pageSize, order, descend);
    }

	/* (non-Javadoc)
	 * @see com.aof.service.admin.CityManager#promoteCity(com.aof.model.admin.City)
	 */
	public void promoteCity(City city)  {
		city.setSite(null);
		updateCity(city);
		
	}

	/* (non-Javadoc)
	 * @see com.aof.service.admin.CityManager#getEnabledCityList(com.aof.model.admin.Province)
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
