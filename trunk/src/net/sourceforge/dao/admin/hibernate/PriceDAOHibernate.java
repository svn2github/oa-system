/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.dao.admin.hibernate;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sourceforge.dao.BaseDAOHibernate;
import net.sourceforge.dao.admin.PriceDAO;
import net.sourceforge.dao.convert.LikeConvert;
import org.apache.commons.beanutils.PropertyUtils;

import net.sourceforge.model.admin.Price;
import net.sourceforge.model.admin.query.PriceQueryCondition;
import net.sourceforge.model.admin.query.PriceQueryOrder;

/**
 * hibernate implement for PriceDAO
 * @author shilei
 * @version 1.0 (Nov 15, 2005)
 */
public class PriceDAOHibernate extends BaseDAOHibernate implements PriceDAO {
    private Log log = LogFactory.getLog(PriceDAOHibernate.class);


    /* (non-Javadoc)
     * @see net.sourceforge.dao.admin.PriceDAO#getPrice(java.lang.Integer)
     */
    public Price getPrice(Integer id){
        if (id == null) {
            if (log.isDebugEnabled()) log.debug("try to get Price with null id");
            return null;
        }
        return (Price) getHibernateTemplate().get(Price.class, id);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.dao.admin.PriceDAO#updatePrice(net.sourceforge.model.admin.Price)
     */
    public Price updatePrice(Price price) {
        Integer id = price.getId();
        if (id == null) {
            throw new RuntimeException("cannot save a price with null id");
        }
        Price oldPrice = getPrice(id);
        if (oldPrice != null) {
        	try{
                PropertyUtils.copyProperties(oldPrice,price);
        	}
        	catch(Exception e)
        	{
                throw new RuntimeException("copy error£º"+e);
        	}
        	getHibernateTemplate().update(oldPrice);	
        	return oldPrice;
        }
        else
        {
        	throw new RuntimeException("price not found");
        }
    }

	/* (non-Javadoc)
	 * @see net.sourceforge.dao.admin.PriceDAO#insertPrice(net.sourceforge.model.admin.Price)
	 */
	public Price insertPrice(Price price) {
       	getHibernateTemplate().save(price);
       	return price;
    }

    private final static String SQL_COUNT = "select count(*) from Price price";

    private final static String SQL = "from Price price";

    private final static Object[][] QUERY_CONDITIONS = {
    	/*id*/    
		{ PriceQueryCondition.ID_EQ, "price.id = ?", null },

		/*keyPropertyList*/

		/*kmtoIdList*/

		/*mtoIdList*/
		{ PriceQueryCondition.HOTEL_ID_EQ, "price.hotel.id = ?", null },

		/*property*/
		{ PriceQueryCondition.ROOM_LIKE, "price.room like ?", new LikeConvert() },
		{ PriceQueryCondition.PRICE_EQ, "price.price = ?", null },
		{ PriceQueryCondition.DISCOUNT_EQ, "price.discount = ?", null },
		{ PriceQueryCondition.DESCRIPTION_LIKE, "price.description like ?", new LikeConvert() },
		{ PriceQueryCondition.ENABLED_EQ, "price.enabled = ?", null },
		{ PriceQueryCondition.HOTEL_CITY_ID_EQ, "price.hotel.city.id = ?", null },
    };
    
    private final static Object[][] QUERY_ORDERS = {
		/*id*/
        { PriceQueryOrder.ID, "price.id" },

		/*property*/
        { PriceQueryOrder.ROOM, "price.room" },
        { PriceQueryOrder.PRICE, "price.price" },
        { PriceQueryOrder.DISCOUNT, "price.discount" },
        { PriceQueryOrder.DESCRIPTION, "price.description" },
        { PriceQueryOrder.ENABLED, "price.enabled" },
    };
    
    /* (non-Javadoc)
     * @see net.sourceforge.dao.admin.PriceDAO#getPriceListCount(java.util.Map)
     */
    public int getPriceListCount(final Map conditions) {
        return getListCount(conditions, SQL_COUNT, QUERY_CONDITIONS);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.dao.admin.PriceDAO#getPriceList(java.util.Map, int, int, net.sourceforge.model.admin.query.PriceQueryOrder, boolean)
     */
    public List getPriceList(final Map conditions, final int pageNo, final int pageSize, final PriceQueryOrder order, final boolean descend) {
        return getList(conditions, pageNo, pageSize, order, descend, SQL, QUERY_CONDITIONS, QUERY_ORDERS);
    }

}
