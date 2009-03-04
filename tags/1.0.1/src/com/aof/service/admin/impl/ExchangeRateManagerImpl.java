/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.service.admin.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import com.aof.dao.admin.ExchangeRateDAO;
import com.aof.model.admin.Currency;
import com.aof.model.admin.ExchangeRate;
import com.aof.model.admin.Site;
import com.aof.model.admin.query.ExchangeRateQueryCondition;
import com.aof.model.admin.query.ExchangeRateQueryOrder;
import com.aof.model.metadata.EnabledDisabled;
import com.aof.service.BaseManager;
import com.aof.service.admin.ExchangeRateManager;

/**
 * ExchangeRateManagerµÄÊµÏÖ
 * @author ych
 * @version 1.0 (Nov 17, 2005)
 */
public class ExchangeRateManagerImpl extends BaseManager implements ExchangeRateManager {

    private ExchangeRateDAO dao;

    /**
     * @param dao
     */
    public void setExchangeRateDAO(ExchangeRateDAO dao) {
        this.dao = dao;
    }
    
    /* (non-Javadoc)
     * @see com.aof.service.admin.ExchangeRateManager#getExchangeRate(java.lang.Integer)
     */
    public ExchangeRate getExchangeRate(Integer id) {
        return dao.getExchangeRate(id);
    }

    /* (non-Javadoc)
     * @see com.aof.service.admin.ExchangeRateManager#updateExchangeRate(com.aof.model.admin.ExchangeRate)
     */
    /*
    public ExchangeRate updateExchangeRate(ExchangeRate function) {
        return dao.updateExchangeRate(function);
    }
    */
    
    /* (non-Javadoc)
     * @see com.aof.service.admin.ExchangeRateManager#insertExchangeRate(com.aof.model.admin.ExchangeRate)
     */
    /*
    public ExchangeRate insertExchangeRate(ExchangeRate function) {
        return dao.insertExchangeRate(function);
    }
    */
    
    /* (non-Javadoc)
     * @see com.aof.service.admin.ExchangeRateManager#saveExchangeRate(com.aof.model.admin.ExchangeRate)
     */
    public ExchangeRate saveExchangeRate(ExchangeRate function) {
        return dao.saveExchangeRate(function);
    }
    
    /* (non-Javadoc)
     * @see com.aof.service.admin.ExchangeRateManager#getExchangeRateListCount(java.util.Map)
     */
    public int getExchangeRateListCount(Map conditions) {
        return dao.getExchangeRateListCount(conditions);
    }

    /* (non-Javadoc)
     * @see com.aof.service.admin.ExchangeRateManager#getExchangeRateList(java.util.Map, int, int, com.aof.model.admin.query.ExchangeRateQueryOrder, boolean)
     */
    public List getExchangeRateList(Map conditions, int pageNo, int pageSize, ExchangeRateQueryOrder order, boolean descend) {
        return dao.getExchangeRateList(conditions, pageNo, pageSize, order, descend);
    }

	/* (non-Javadoc)
	 * @see com.aof.service.admin.ExchangeRateManager#getExchangeRate(com.aof.model.admin.Currency, com.aof.model.admin.Site)
	 */
	public ExchangeRate getExchangeRate(Currency currency, Site site) {
		return dao.getExchangeRate(currency, site);
	}

    /* (non-Javadoc)
     * @see com.aof.service.admin.ExchangeRateManager#getExchangeRateListBySite(com.aof.model.admin.Site)
     */
    public List getExchangeRateListBySite(Site site) {
        Map condition=new HashMap();
        condition.put(ExchangeRateQueryCondition.SITE_ID_EQ,site.getId());
        return dao.getExchangeRateList(condition,0,-1,ExchangeRateQueryOrder.CODE,false);
    }
    
    public List getEnabledExchangeRateListBySite(Site site) {
        Map condition=new HashMap();
        condition.put(ExchangeRateQueryCondition.SITE_ID_EQ,site.getId());
        condition.put(ExchangeRateQueryCondition.CURRENCY_STATUS_EQ,EnabledDisabled.ENABLED);
        return dao.getExchangeRateList(condition,0,-1,ExchangeRateQueryOrder.CODE,false);
    }
    
    public List getEnabledExchangeRateListBySiteIncludeBaseCurrency(Site site) {
        List currencyList=this.getEnabledExchangeRateListBySite(site);
        Currency baseCurrency=site.getBaseCurrency();
        boolean containBaseCurrency=false;
        Iterator itor=currencyList.iterator();
        while (itor.hasNext()) {
            ExchangeRate exchangeRate=(ExchangeRate) itor.next();
            if (exchangeRate.getCurrency().equals(baseCurrency)) {
                containBaseCurrency=true;
                break;
            }
        }
        if (!containBaseCurrency) {
            ExchangeRate baseExchangeRate=new ExchangeRate();
            baseExchangeRate.setCurrency(baseCurrency);
            baseExchangeRate.setSite(site);
            baseExchangeRate.setExchangeRate(new BigDecimal(1d));
            currencyList.add(0,baseExchangeRate);
        }
        return currencyList;
    }

    public void fillEnabledExchangeRateListBySiteListIncludeBaseCurrency(List siteList) {
        for (Iterator itor = siteList.iterator(); itor.hasNext();) {
            Site s = (Site) itor.next();
            s.setEnabledExchangeRateList(getEnabledExchangeRateListBySiteIncludeBaseCurrency(s));            
        }
    }

}
