/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.dao.admin.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.aof.dao.BaseDAOHibernate;
import com.aof.dao.admin.CountryDAO;
import com.aof.dao.convert.LikeConvert;
import com.aof.model.admin.Country;
import com.aof.model.admin.query.CountryQueryCondition;
import com.aof.model.admin.query.CountryQueryOrder;
import com.aof.model.metadata.YesNo;

/**
 * hibernate implement for CountryDAO
 * 
 * @author shilei
 * @version 1.0 (Nov 15, 2005)
 */
public class CountryDAOHibernate extends BaseDAOHibernate implements CountryDAO {
    private Log log = LogFactory.getLog(CountryDAOHibernate.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.dao.admin.CountryDAO#getCountry(java.lang.Integer)
     */
    public Country getCountry(Integer id) {
        if (id == null) {
            if (log.isDebugEnabled())
                log.debug("try to get Country with null id");
            return null;
        }
        return (Country) getHibernateTemplate().get(Country.class, id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.dao.admin.CountryDAO#updateCountry(com.aof.model.admin.Country)
     */
    public Country updateCountry(Country country) {
        Integer id = country.getId();
        if (id == null) {
            throw new RuntimeException("cannot save a country with null id");
        }
        Country oldCountry = getCountry(id);
        if (oldCountry != null) {
            try {
                PropertyUtils.copyProperties(oldCountry, country);
            } catch (Exception e) {
                throw new RuntimeException("copy error£º" + e);
            }
            getHibernateTemplate().update(oldCountry);
            return oldCountry;
        } else {
            throw new RuntimeException("country not found");
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.dao.admin.CountryDAO#insertCountry(com.aof.model.admin.Country)
     */
    public Country insertCountry(Country country) {
        getHibernateTemplate().save(country);
        return country;
    }

    private final static String SQL_COUNT = "select count(*) from Country country";

    private final static String SQL = "from Country country";

    private final static Object[][] QUERY_CONDITIONS = {
    /* id */
    { CountryQueryCondition.ID_EQ, "country.id = ?", null },

    /* keyPropertyList */

    /* kmtoIdList */

    /* mtoIdList */

    /* property */
    { CountryQueryCondition.SHORTNAME_LIKE, "country.shortName like ?", new LikeConvert() },
            { CountryQueryCondition.ENGNAME_LIKE, "country.engName like ?", new LikeConvert() },
            { CountryQueryCondition.CHNNAME_LIKE, "country.chnName like ?", new LikeConvert() },
            { CountryQueryCondition.ENABLED_EQ, "country.enabled = ?", null }, };

    private final static Object[][] QUERY_ORDERS = {
    /* id */
    { CountryQueryOrder.ID, "country.id" },

    /* property */
    { CountryQueryOrder.SHORTNAME, "country.shortName" }, { CountryQueryOrder.ENGNAME, "country.engName" }, { CountryQueryOrder.CHNNAME, "country.chnName" },
            { CountryQueryOrder.ENABLED, "country.enabled" }, };

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.dao.admin.CountryDAO#getCountryListCount(java.util.Map)
     */
    public int getCountryListCount(final Map conditions) {
        return getListCount(conditions, SQL_COUNT, QUERY_CONDITIONS);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.dao.admin.CountryDAO#getCountryList(java.util.Map, int, int,
     *      com.aof.model.admin.query.CountryQueryOrder, boolean)
     */
    public List getCountryList(final Map conditions, final int pageNo, final int pageSize, final CountryQueryOrder order, final boolean descend) {
        return getList(conditions, pageNo, pageSize, order, descend, SQL, QUERY_CONDITIONS, QUERY_ORDERS);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.dao.admin.CountryDAO#listEnabledCountry()
     */
    public List listEnabledCountry() {
        Map conds = new HashMap();
        conds.put(CountryQueryCondition.ENABLED_EQ, YesNo.YES.getEnumCode());
        return getCountryList(conds, 0, -1, CountryQueryOrder.ENGNAME, false);

    }

    public void deleteCountry(Country country) {
        this.getHibernateTemplate().delete(country);
    }

    public Country getCountryByChnName(String chnName) {
        List list = this.getHibernateTemplate().find("from Country c where c.chnName=?", chnName);
        if (list.isEmpty())
            return null;
        if (list.size() == 1)
            return (Country) list.get(0);
        throw new RuntimeException("country.chnName repeat");
    }

    public Country getCountryByEngName(String engName) {
        List list = this.getHibernateTemplate().find("from Country c where c.engName=?", engName);
        if (list.isEmpty())
            return null;
        if (list.size() == 1)
            return (Country) list.get(0);
        throw new RuntimeException("country.engName repeat");
    }

    public Country getCountryByShortName(String sn) {
        List list = this.getHibernateTemplate().find("from Country c where c.shortName=?", sn);
        if (list.isEmpty())
            return null;
        if (list.size() == 1)
            return (Country) list.get(0);
        throw new RuntimeException("country.engName repeat");
    }

}
