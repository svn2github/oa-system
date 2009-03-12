/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.service.admin.impl;

import java.util.List;
import java.util.Map;

import net.sourceforge.dao.admin.PriceDAO;
import net.sourceforge.model.admin.Price;
import net.sourceforge.model.admin.query.PriceQueryOrder;
import net.sourceforge.service.BaseManager;
import net.sourceforge.service.admin.PriceManager;

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
     * @see net.sourceforge.service.admin.PriceManager#getPrice(java.lang.Integer)
     */
    public Price getPrice(Integer id)  {
        return dao.getPrice(id);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.PriceManager#updatePrice(net.sourceforge.model.admin.Price)
     */
    public Price updatePrice(Price function)  {
        return dao.updatePrice(function);
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.PriceManager#insertPrice(net.sourceforge.model.admin.Price)
     */
    public Price insertPrice(Price function)  {
        return dao.insertPrice(function);
    }
    

    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.PriceManager#getPriceListCount(java.util.Map)
     */
    public int getPriceListCount(Map conditions)  {
        return dao.getPriceListCount(conditions);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.PriceManager#getPriceList(java.util.Map, int, int, net.sourceforge.model.admin.query.PriceQueryOrder, boolean)
     */
    public List getPriceList(Map conditions, int pageNo, int pageSize, PriceQueryOrder order, boolean descend)  {
        return dao.getPriceList(conditions, pageNo, pageSize, order, descend);
    }

}
