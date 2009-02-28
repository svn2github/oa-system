/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.dao.admin.hibernate;

import java.util.List;
import java.util.Map;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.type.Type;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate.HibernateTemplate;

import com.aof.dao.BaseDAOHibernate;
import com.aof.dao.admin.ExchangeRateDAO;
import com.aof.dao.convert.LikeConvert;
import com.aof.model.admin.Currency;
import com.aof.model.admin.ExchangeRate;
import com.aof.model.admin.Site;
import com.aof.model.admin.query.ExchangeRateQueryCondition;
import com.aof.model.admin.query.ExchangeRateQueryOrder;

/**
 * ExchangeRateDAOµÄHibernateÊµÏÖ 
 * @author ych
 * @version 1.0 (Nov 13, 2005)
 */
public class ExchangeRateDAOHibernate extends BaseDAOHibernate implements ExchangeRateDAO {

	private Log log = LogFactory.getLog(CurrencyDAOHibernate.class);

    /* (non-Javadoc)
     * @see com.aof.dao.admin.ExchangeRateDAO#getExchangeRate(java.lang.Integer)
     */
    public ExchangeRate getExchangeRate(Integer id) {
        if (id == null) {
            if (log.isDebugEnabled()) log.debug("try to get ExchangeRate with null id");
            return null;
        }
        return (ExchangeRate) getHibernateTemplate().get(ExchangeRate.class, id);
    }
    
    public ExchangeRate getExchangeRate(Currency currency,Site site) {
        List l = getHibernateTemplate().find("from ExchangeRate er where er.currency.code = ? and er.site.id = ?",
                new Object[] { currency.getCode(), site.getId() },
                new Type[] { Hibernate.STRING, Hibernate.INTEGER } );
        if (l.isEmpty()) return null;
        return (ExchangeRate) l.get(0);
    }
    
	private final static String SQL_COUNT = "select count(*) from ExchangeRate e";
    
    private final static String SQL = "from ExchangeRate e";
    
    private final static Object[][] QUERY_CONDITIONS = {
    	{ExchangeRateQueryCondition.CURRENCY_CODE_LIKE, "e.currency.code like ?", new LikeConvert()},
        {ExchangeRateQueryCondition.CURRENCY_CODE_EQ, "e.currency.code = ?", null},
    	{ExchangeRateQueryCondition.SITE_ID_EQ, "e.site.id=?",null},
        {ExchangeRateQueryCondition.CURRENCY_STATUS_EQ, "e.currency.enabled=?",null},
    };
    
    private final static Object[][] QUERY_ORDERS = {
    	{ ExchangeRateQueryOrder.SITE_NAME, "e.site.name" },
        { ExchangeRateQueryOrder.CODE, "e.currency.code" },
        { ExchangeRateQueryOrder.EXCHANGERATE, "e.exchangeRate" },
    };

	/* (non-Javadoc)
	 * @see com.aof.dao.admin.ExchangeRateDAO#getExchangeRateListCount(java.util.Map)
	 */
	public int getExchangeRateListCount(Map conditions) {
		return getListCount(conditions, SQL_COUNT, QUERY_CONDITIONS);
	}

	/* (non-Javadoc)
	 * @see com.aof.dao.admin.ExchangeRateDAO#getExchangeRateList(java.util.Map, int, int, com.aof.model.admin.query.ExchangeRateQueryOrder, boolean)
	 */
	public List getExchangeRateList(Map conditions, int pageNo, int pageSize, ExchangeRateQueryOrder order, boolean descend) {
		return getList(conditions, pageNo, pageSize, order, descend, SQL, QUERY_CONDITIONS, QUERY_ORDERS);
	}

	/* (non-Javadoc)
	 * @see com.aof.dao.admin.ExchangeRateDAO#insertExchangeRate(com.aof.model.admin.ExchangeRate)
	 */
    /*
	public ExchangeRate insertExchangeRate(ExchangeRate exchangeRate) {
        getHibernateTemplate().save(exchangeRate);
        getHibernateTemplate().flush();
        getHibernateTemplate().refresh(exchangeRate);
        return  exchangeRate;
	}
    */

    public ExchangeRate saveExchangeRate(ExchangeRate exchangeRate) {
        HibernateTemplate template = getHibernateTemplate();
        template.saveOrUpdate(exchangeRate);
        template.flush();
        template.refresh(exchangeRate);
        return exchangeRate;
    }

    /* (non-Javadoc)
	 * @see com.aof.dao.admin.ExchangeRateDAO#updateExchangeRate(com.aof.model.admin.ExchangeRate)
	 */
    /*
	public ExchangeRate updateExchangeRate(ExchangeRate exchangeRate) {
        Site site=exchangeRate.getSite();
        if (site==null) {
        	throw new RuntimeException("cannot save a ExchangeRate with null site");
        }
        Currency currency=exchangeRate.getCurrency();
        if (currency==null) {
        	throw new RuntimeException("cannot save a ExchangeRate with null currency");
        }
        ExchangeRate oldExchangeRate=getExchangeRate(currency,site);
        if (oldExchangeRate!=null) {
        	try{
        		PropertyUtils.copyProperties(oldExchangeRate,exchangeRate);
        	}
        	catch(Exception e)
        	{
        		throw new RuntimeException("copy error£º"+e);
        	}
        	getHibernateTemplate().update(oldExchangeRate);	
        	return oldExchangeRate;
        }
        else
        {
        	throw new RuntimeException("exchangeRate not found");
        }
	}
    */
	
}
