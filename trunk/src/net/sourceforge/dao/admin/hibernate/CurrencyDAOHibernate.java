/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.dao.admin.hibernate;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sourceforge.dao.BaseDAOHibernate;
import net.sourceforge.dao.admin.CurrencyDAO;
import net.sourceforge.dao.convert.LikeConvert;
import net.sourceforge.model.admin.Currency;
import net.sourceforge.model.admin.query.CurrencyQueryCondition;
import net.sourceforge.model.admin.query.CurrencyQueryOrder;

/**
 * CurrencyDAOµÄHibernateÊµÏÖ 
 * @author ych
 * @version 1.0 (Nov 13, 2005)
 */
public class CurrencyDAOHibernate extends BaseDAOHibernate implements
		CurrencyDAO {
	
	private Log log = LogFactory.getLog(CurrencyDAOHibernate.class);
	
	/* (non-Javadoc)
	 * @see net.sourceforge.dao.admin.CurrencyDAO#getCurrency(java.lang.String)
	 */
	public Currency getCurrency(String code) {
		if (code == null) {
            if (log.isDebugEnabled()) log.debug("try to get Currency with null code");
            return null;
        }
        return (Currency) getHibernateTemplate().get(Currency.class, code);
	}
	
    
    private final static String SQL_COUNT = "select count(*) from Currency c";
    
    private final static String SQL = "from Currency c";
    
    private final static Object[][] QUERY_CONDITIONS = {
    	{CurrencyQueryCondition.CODE_LIKE, "c.code like ?", new LikeConvert()},
        {CurrencyQueryCondition.NAME_LIKE, "c.name like ?", new LikeConvert()},
    	{CurrencyQueryCondition.STATUS_EQ, "c.enabled=?",null},
    };
    
    private final static Object[][] QUERY_ORDERS = {
        { CurrencyQueryOrder.CODE, "c.code" },
        { CurrencyQueryOrder.STATUS, "c.enabled" },
    };
    
	/* (non-Javadoc)
	 * @see net.sourceforge.dao.admin.CurrencyDAO#getCurrencyList(java.util.Map, int, int, net.sourceforge.model.admin.query.CurrencyQueryOrder, boolean)
	 */
	public List getCurrencyList(Map conditions, int pageNo, int pageSize, CurrencyQueryOrder order, boolean descend) {
		return getList(conditions, pageNo, pageSize, order, descend, SQL, QUERY_CONDITIONS, QUERY_ORDERS);
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.dao.admin.CurrencyDAO#getCurrencyListCount(java.util.Map)
	 */
	public int getCurrencyListCount(Map conditions) {
		return getListCount(conditions, SQL_COUNT, QUERY_CONDITIONS);
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.dao.admin.CurrencyDAO#insertCurrency(net.sourceforge.model.admin.Currency)
	 */
	public Currency insertCurrency(Currency currency) {
		getHibernateTemplate().save(currency);
		return currency;
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.dao.admin.CurrencyDAO#updateCurrency(net.sourceforge.model.admin.Currency)
	 */
	public Currency updateCurrency(Currency currency) {
        String code=currency.getCode();
        if (code == null) {
            throw new RuntimeException("cannot save a currency with null code");
        }
        Currency oldCurrency=getCurrency(code);
        if (oldCurrency != null) {
        	try{
                PropertyUtils.copyProperties(oldCurrency,currency);
        	}
        	catch(Exception e)
        	{
                throw new RuntimeException("copy error£º"+e);
        	}
        	getHibernateTemplate().update(oldCurrency);	
        	return oldCurrency;
        }
        else
        {
        	throw new RuntimeException("currency not found");
        }
	}
}
