/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.dao.admin.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sourceforge.dao.BaseDAOHibernate;
import net.sourceforge.dao.admin.CityDAO;
import net.sourceforge.dao.convert.LikeConvert;
import net.sourceforge.model.admin.City;
import net.sourceforge.model.admin.Province;
import net.sourceforge.model.admin.query.CityQueryCondition;
import net.sourceforge.model.admin.query.CityQueryOrder;
import net.sourceforge.model.metadata.EnabledDisabled;

/**
 * hibernate implement for CityDAO
 * 
 * @author shilei
 * @version 1.0 (Nov 15, 2005)
 */
public class CityDAOHibernate extends BaseDAOHibernate implements CityDAO {
    private Log log = LogFactory.getLog(CityDAOHibernate.class);

    /*
     * (non-Javadoc)
     * 
     * @see net.sourceforge.dao.admin.CityDAO#getCity(java.lang.Integer)
     */
    public City getCity(Integer id) {
        if (id == null) {
            if (log.isDebugEnabled())
                log.debug("try to get City with null id");
            return null;
        }
        return (City) getHibernateTemplate().get(City.class, id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sourceforge.dao.admin.CityDAO#updateCity(net.sourceforge.model.admin.City)
     */
    public City updateCity(City city) {
        Integer id = city.getId();
        if (id == null) {
            throw new RuntimeException("cannot save a city with null id");
        }
        City oldCity = getCity(id);
        if (oldCity != null) {
            try {
                PropertyUtils.copyProperties(oldCity, city);
            } catch (Exception e) {
                throw new RuntimeException("copy error£º" + e);
            }
            getHibernateTemplate().update(oldCity);
            return oldCity;
        } else {
            throw new RuntimeException("city not found");
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sourceforge.dao.admin.CityDAO#insertCity(net.sourceforge.model.admin.City)
     */
    public City insertCity(City city) {
        getHibernateTemplate().save(city);
        return city;
    }

    private final static String SQL_COUNT = "select count(*) from City city";

    private final static String SQL = "from City city";

    private final static Object[][] QUERY_CONDITIONS = { { CityQueryCondition.ID_EQ, "city.id = ?", null },
            { CityQueryCondition.PROVINCE_ID_EQ, "city.province.id = ?", null }, { CityQueryCondition.ENGNAME_LIKE, "city.engName like ?", new LikeConvert() },
            { CityQueryCondition.CHNNAME_LIKE, "city.chnName like ?", new LikeConvert() }, { CityQueryCondition.ENABLED_EQ, "city.enabled = ?", null },

    };

    private final static Object[][] QUERY_ORDERS = { { CityQueryOrder.ID, "city.id" }, { CityQueryOrder.ENGNAME, "city.engName" },
            { CityQueryOrder.CHNNAME, "city.chnName" }, { CityQueryOrder.ENABLED, "city.enabled" }, };

    /*
     * (non-Javadoc)
     * 
     * @see net.sourceforge.dao.admin.CityDAO#getCityListCount(java.util.Map)
     */
    public int getCityListCount(final Map conditions) {
        return getListCount(conditions, SQL_COUNT, QUERY_CONDITIONS);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sourceforge.dao.admin.CityDAO#getCityList(java.util.Map, int, int,
     *      net.sourceforge.model.admin.query.CityQueryOrder, boolean)
     */
    public List getCityList(final Map conditions, final int pageNo, final int pageSize, final CityQueryOrder order, final boolean descend) {
        return getList(conditions, pageNo, pageSize, order, descend, SQL, QUERY_CONDITIONS, QUERY_ORDERS);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sourceforge.dao.admin.CityDAO#getEnabledCityList()
     */
    public List getEnabledCityList() {
        Map conds = new HashMap();
        conds.put(CityQueryCondition.ENABLED_EQ, EnabledDisabled.ENABLED.getEnumCode());
        return getCityList(conds, 0, -1, CityQueryOrder.ENGNAME, false);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sourceforge.dao.admin.CityDAO#getEnabledCityList(net.sourceforge.model.admin.Province)
     */
    public List getEnabledCityList(Province p) {
        Map conds = new HashMap();
        conds.put(CityQueryCondition.ENABLED_EQ, EnabledDisabled.ENABLED.getEnumCode());
        conds.put(CityQueryCondition.PROVINCE_ID_EQ, p.getId());
        return getCityList(conds, 0, -1, CityQueryOrder.ENGNAME, false);
    }

    public void deleteCity(City city) {
        this.getHibernateTemplate().delete(city);
    }

    public City getCityByChnName(Province p, String chnName) {
        List list = this.getHibernateTemplate().find("from City city where city.province.id=? and city.chnName=?", new Object[] { p.getId(), chnName });
        if (list.isEmpty())
            return null;
        if (list.size() == 1)
            return (City) list.get(0);
        throw new RuntimeException("city.chnName repeat");
    }

    public City getCityByEngName(Province p, String engName) {
        List list = this.getHibernateTemplate().find("from City city where city.province.id=? and city.engName=?", new Object[] { p.getId(), engName });
        if (list.isEmpty())
            return null;
        if (list.size() == 1)
            return (City) list.get(0);
        throw new RuntimeException("city.engName repeat");
    }

}
