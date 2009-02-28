/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.service.admin.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aof.dao.admin.CurrencyDAO;
import com.aof.model.admin.Currency;
import com.aof.model.admin.query.CurrencyQueryCondition;
import com.aof.model.admin.query.CurrencyQueryOrder;
import com.aof.model.metadata.EnabledDisabled;
import com.aof.service.BaseManager;
import com.aof.service.admin.CurrencyManager;

/**
 * CurrencyManagerµÄÊµÏÖ
 * @author ych
 * @version 1.0 (Nov 17, 2005)
 */
public class CurrencyManagerImpl extends BaseManager implements CurrencyManager {

    private CurrencyDAO dao;
    
	/**
	 * @param dao
	 */
	public void setCurrencyDAO(CurrencyDAO dao) {
		this.dao = dao;
	}

	/* (non-Javadoc)
	 * @see com.aof.service.admin.CurrencyManager#getCurrency(java.lang.String)
	 */
	public Currency getCurrency(String code)  {
		return dao.getCurrency(code);
	}

	/* (non-Javadoc)
	 * @see com.aof.service.admin.CurrencyManager#insertCurrency(com.aof.model.admin.Currency)
	 */
	public Currency insertCurrency(Currency currency)  {
		return dao.insertCurrency(currency);
	}

	/* (non-Javadoc)
	 * @see com.aof.service.admin.CurrencyManager#updateCurrency(com.aof.model.admin.Currency)
	 */
	public Currency updateCurrency(Currency currency)  {
		return dao.updateCurrency(currency);
	}

	/* (non-Javadoc)
	 * @see com.aof.service.admin.CurrencyManager#getCurrencyListCount(java.util.Map)
	 */
	public int getCurrencyListCount(Map conditions)  {
		return dao.getCurrencyListCount(conditions);
	}

	/* (non-Javadoc)
	 * @see com.aof.service.admin.CurrencyManager#getCurrencyList(java.util.Map, int, int, com.aof.model.admin.query.CurrencyQueryOrder, boolean)
	 */
	public List getCurrencyList(Map conditions, int pageNo, int pageSize, CurrencyQueryOrder order, boolean descend)  {
		return dao.getCurrencyList(conditions,pageNo,pageSize,order,descend);
	}

	/* (non-Javadoc)
	 * @see com.aof.service.admin.CurrencyManager#getAllEnabledCurrencyList()
	 */
	public List getAllEnabledCurrencyList()  {
		Map conditions = new HashMap();
        conditions.put(CurrencyQueryCondition.STATUS_EQ, EnabledDisabled.ENABLED.getEnumCode());
        return dao.getCurrencyList(conditions, 0, -1, CurrencyQueryOrder.CODE, false);
	}

    /* (non-Javadoc)
     * @see com.aof.service.admin.CurrencyManager#getAllEnabledCurrencyListAndIncludeThis(java.lang.String)
     */
    public List getAllEnabledCurrencyListAndIncludeThis(String code)  {
        Currency c = dao.getCurrency(code);
        List l = getAllEnabledCurrencyList();
        if (c == null) return l;
        if (l.contains(c)) return l;
        for (int i = 0; i < l.size(); i++) {
            Currency c2 = (Currency)l.get(i);
            if (c2.getCode().compareTo(c.getCode()) > 0) {
                l.add(i, c);
                return l;
            }
        }
        l.add(c);
        return l;
    }
}
