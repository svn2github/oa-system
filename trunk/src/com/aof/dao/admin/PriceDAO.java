/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.dao.admin;

import java.util.List;
import java.util.Map;

import com.aof.model.admin.Price;
import com.aof.model.admin.query.PriceQueryOrder;

/**
 * dao interface for domain model price
 * @author shilei
 * @version 1.0 (Nov 15, 2005)
 */
public interface PriceDAO {

    /**
     * 从数据库取得指定id的Price
     * 
     * @param id
     *            Price的id
     * @return 返回指定的Price
     */
    public Price getPrice(Integer id);

    /**
     * get price List Count according to conditions
     * 
     * @param conditions
     *            search condition
     * @return list count
     */
    public int getPriceListCount(Map conditions);

    /**
     * get Price List according to conditions
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
     * @return Price list
     */
    public List getPriceList(Map conditions, int pageNo, int pageSize, PriceQueryOrder order, boolean descend);

    /**
     * insert Price to database
     * 
     * @param price
     *            the Price inserted
     * @return the Price inserted
     */
    public Price insertPrice(Price price);

    /**
     * update Price to datebase
     * 
     * @param price
     *            the Price updated
     * @return the Price updated
     */
    public Price updatePrice(Price price);

}
