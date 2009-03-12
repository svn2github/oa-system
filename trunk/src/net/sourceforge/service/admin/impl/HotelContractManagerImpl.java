/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.service.admin.impl;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sourceforge.dao.admin.HotelContractDAO;
import net.sourceforge.model.admin.HotelContract;
import net.sourceforge.model.admin.query.HotelContractQueryOrder;
import net.sourceforge.service.BaseManager;
import net.sourceforge.service.admin.HotelContractManager;

/**
 * implement for HotelContractManager
 * @author shilei
 * @version 1.0 (Nov 15, 2005)
 */
public class HotelContractManagerImpl extends BaseManager implements HotelContractManager {
    //private Log log = LogFactory.getLog(HotelContractManagerImpl.class);

    private HotelContractDAO dao;

    /**
     * @param dao
     */
    public void setHotelContractDAO(HotelContractDAO dao) {
        this.dao = dao;
    }
    
     /* (non-Javadoc)
     * @see net.sourceforge.service.admin.HotelContractManager#getHotelContract(java.lang.Integer)
     */
    public HotelContract getHotelContract(Integer id)  {
        return dao.getHotelContract(id);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.HotelContractManager#updateHotelContract(net.sourceforge.model.admin.HotelContract)
     */
    public HotelContract updateHotelContract(HotelContract hotelContract)  {
        return dao.updateHotelContract(hotelContract);
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.HotelContractManager#insertHotelContract(net.sourceforge.model.admin.HotelContract)
     */
    public HotelContract insertHotelContract(HotelContract hotelContract)  {
        return dao.insertHotelContract(hotelContract);
    }
    

    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.HotelContractManager#getHotelContractListCount(java.util.Map)
     */
    public int getHotelContractListCount(Map conditions)  {
        return dao.getHotelContractListCount(conditions);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.HotelContractManager#getHotelContractList(java.util.Map, int, int, net.sourceforge.model.admin.query.HotelContractQueryOrder, boolean)
     */
    public List getHotelContractList(Map conditions, int pageNo, int pageSize, HotelContractQueryOrder order, boolean descend)  {
        return dao.getHotelContractList(conditions, pageNo, pageSize, order, descend);
    }

	/* (non-Javadoc)
	 * @see net.sourceforge.service.admin.HotelContractManager#insertHotelContract(net.sourceforge.model.admin.HotelContract, java.io.InputStream)
	 */
	public HotelContract insertHotelContract(HotelContract hotelContract, InputStream inputStream) {
		hotelContract.setUploadDate(new Date());
		HotelContract hc = dao.insertHotelContract(hotelContract);
        dao.saveHotelContractContent(hc.getId(), inputStream);
        return hc;
	}
    
    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.HotelContractManager#getHotelContractContent(java.lang.Integer)
     */
    public InputStream getHotelContractContent(Integer id) {
        return dao.getHotelContractContent(id);
    }

}
