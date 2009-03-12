/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.service.admin.impl;

import java.util.List;
import java.util.Map;

import net.sourceforge.dao.admin.ProvinceDAO;
import net.sourceforge.model.admin.Country;
import net.sourceforge.model.admin.Province;
import net.sourceforge.model.admin.query.ProvinceQueryOrder;
import net.sourceforge.service.BaseManager;
import net.sourceforge.service.admin.ProvinceManager;

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
     * @see net.sourceforge.service.admin.ProvinceManager#getProvince(java.lang.Integer)
     */
    public Province getProvince(Integer id)  {
        return dao.getProvince(id);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.ProvinceManager#updateProvince(net.sourceforge.model.admin.Province)
     */
    public Province updateProvince(Province function)  {
        return dao.updateProvince(function);
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.ProvinceManager#insertProvince(net.sourceforge.model.admin.Province)
     */
    public Province insertProvince(Province function)  {
        return dao.insertProvince(function);
    }
    

    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.ProvinceManager#getProvinceListCount(java.util.Map)
     */
    public int getProvinceListCount(Map conditions)  {
        return dao.getProvinceListCount(conditions);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.ProvinceManager#getProvinceList(java.util.Map, int, int, net.sourceforge.model.admin.query.ProvinceQueryOrder, boolean)
     */
    public List getProvinceList(Map conditions, int pageNo, int pageSize, ProvinceQueryOrder order, boolean descend)  {
        return dao.getProvinceList(conditions, pageNo, pageSize, order, descend);
    }

	/* (non-Javadoc)
	 * @see net.sourceforge.service.admin.ProvinceManager#promoteProvince(net.sourceforge.model.admin.Province)
	 */
	public void promoteProvince(Province province) {
		province.setSite(null);
		this.updateProvince(province);
		
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.service.admin.ProvinceManager#getEnabledProvinceOfCountry(net.sourceforge.model.admin.Country)
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
