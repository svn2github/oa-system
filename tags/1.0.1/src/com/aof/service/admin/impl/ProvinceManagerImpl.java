/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.service.admin.impl;

import java.util.List;
import java.util.Map;

import com.aof.dao.admin.ProvinceDAO;
import com.aof.model.admin.Country;
import com.aof.model.admin.Province;
import com.aof.model.admin.query.ProvinceQueryOrder;
import com.aof.service.BaseManager;
import com.aof.service.admin.ProvinceManager;

/**
 * implement for ProvinceManager
 * @author shilei
 * @version 1.0 (Nov 15, 2005)
 */
public class ProvinceManagerImpl extends BaseManager implements ProvinceManager {
    //private Log log = LogFactory.getLog(ProvinceManagerImpl.class);

    private ProvinceDAO dao;

    /**
     * @param dao
     */
    public void setProvinceDAO(ProvinceDAO dao) {
        this.dao = dao;
    }
    
     /* (non-Javadoc)
     * @see com.aof.service.admin.ProvinceManager#getProvince(java.lang.Integer)
     */
    public Province getProvince(Integer id)  {
        return dao.getProvince(id);
    }

    /* (non-Javadoc)
     * @see com.aof.service.admin.ProvinceManager#updateProvince(com.aof.model.admin.Province)
     */
    public Province updateProvince(Province function)  {
        return dao.updateProvince(function);
    }
    
    /* (non-Javadoc)
     * @see com.aof.service.admin.ProvinceManager#insertProvince(com.aof.model.admin.Province)
     */
    public Province insertProvince(Province function)  {
        return dao.insertProvince(function);
    }
    

    /* (non-Javadoc)
     * @see com.aof.service.admin.ProvinceManager#getProvinceListCount(java.util.Map)
     */
    public int getProvinceListCount(Map conditions)  {
        return dao.getProvinceListCount(conditions);
    }

    /* (non-Javadoc)
     * @see com.aof.service.admin.ProvinceManager#getProvinceList(java.util.Map, int, int, com.aof.model.admin.query.ProvinceQueryOrder, boolean)
     */
    public List getProvinceList(Map conditions, int pageNo, int pageSize, ProvinceQueryOrder order, boolean descend)  {
        return dao.getProvinceList(conditions, pageNo, pageSize, order, descend);
    }

	/* (non-Javadoc)
	 * @see com.aof.service.admin.ProvinceManager#promoteProvince(com.aof.model.admin.Province)
	 */
	public void promoteProvince(Province province) {
		province.setSite(null);
		this.updateProvince(province);
		
	}

	/* (non-Javadoc)
	 * @see com.aof.service.admin.ProvinceManager#getEnabledProvinceOfCountry(com.aof.model.admin.Country)
	 */
	public List getEnabledProvinceOfCountry(Country c) {
		return dao.getEnabledProvinceList(c);
	}

    public boolean deleteProvince(Province p) {
        try{
            dao.deleteProvince(p);
            return true;
        }
        catch(Throwable t)
        {
            return false;
        }
    }

    public Province getProvinceByChnName(Country country, String chnName) {
        return dao.getProvinceByChnName(country,chnName);
    }

    public Province getProvinceByEngName(Country country, String engName) {
        return dao.getProvinceByEngName(country,engName);
    }

}
