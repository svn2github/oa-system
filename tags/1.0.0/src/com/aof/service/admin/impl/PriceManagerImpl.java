/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.service.admin.impl;

import java.util.List;
import java.util.Map;

import com.aof.dao.admin.PriceDAO;
import com.aof.model.admin.Price;
import com.aof.model.admin.query.PriceQueryOrder;
import com.aof.service.BaseManager;
import com.aof.service.admin.PriceManager;

/**
 * implement for PriceManager
 * @author shilei
 * @version 1.0 (Nov 15, 2005)
 */
public class PriceManagerImpl extends BaseManager implements PriceManager {
    

    private PriceDAO dao;

    /**
     * @param dao
     */
    public void setPriceDAO(PriceDAO dao) {
        this.dao = dao;
    }
    
     /* (non-Javadoc)
     * @see com.aof.service.admin.PriceManager#getPrice(java.lang.Integer)
     */
    public Price getPrice(Integer id)  {
        return dao.getPrice(id);
    }

    /* (non-Javadoc)
     * @see com.aof.service.admin.PriceManager#updatePrice(com.aof.model.admin.Price)
     */
    public Price updatePrice(Price function)  {
        return dao.updatePrice(function);
    }
    
    /* (non-Javadoc)
     * @see com.aof.service.admin.PriceManager#insertPrice(com.aof.model.admin.Price)
     */
    public Price insertPrice(Price function)  {
        return dao.insertPrice(function);
    }
    

    /* (non-Javadoc)
     * @see com.aof.service.admin.PriceManager#getPriceListCount(java.util.Map)
     */
    public int getPriceListCount(Map conditions)  {
        return dao.getPriceListCount(conditions);
    }

    /* (non-Javadoc)
     * @see com.aof.service.admin.PriceManager#getPriceList(java.util.Map, int, int, com.aof.model.admin.query.PriceQueryOrder, boolean)
     */
    public List getPriceList(Map conditions, int pageNo, int pageSize, PriceQueryOrder order, boolean descend)  {
        return dao.getPriceList(conditions, pageNo, pageSize, order, descend);
    }

}
