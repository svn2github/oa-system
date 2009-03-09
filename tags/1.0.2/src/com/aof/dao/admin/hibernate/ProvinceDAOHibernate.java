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
import com.aof.dao.admin.ProvinceDAO;
import com.aof.dao.convert.LikeConvert;
import com.aof.model.admin.Country;
import com.aof.model.admin.Province;
import com.aof.model.admin.query.ProvinceQueryCondition;
import com.aof.model.admin.query.ProvinceQueryOrder;
import com.aof.model.metadata.EnabledDisabled;

/**
 * hibernate implement for ProvinceDAO
 * 
 * @author shilei
 * @version 1.0 (Nov 15, 2005)
 */
public class ProvinceDAOHibernate extends BaseDAOHibernate implements ProvinceDAO {
    private Log log = LogFactory.getLog(ProvinceDAOHibernate.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.dao.admin.ProvinceDAO#getProvince(java.lang.Integer)
     */
    public Province getProvince(Integer id) {
        if (id == null) {
            if (log.isDebugEnabled())
                log.debug("try to get Province with null id");
            return null;
        }
        return (Province) getHibernateTemplate().get(Province.class, id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.dao.admin.ProvinceDAO#updateProvince(com.aof.model.admin.Province)
     */
    public Province updateProvince(Province province) {
        Integer id = province.getId();
        if (id == null) {
            throw new RuntimeException("cannot save a province with null id");
        }
        Province oldProvince = getProvince(id);
        if (oldProvince != null) {
            try {
                PropertyUtils.copyProperties(oldProvince, province);
            } catch (Exception e) {
                throw new RuntimeException("copy error£º" + e);
            }
            getHibernateTemplate().update(oldProvince);
            return oldProvince;
        } else {
            throw new RuntimeException("province not found");
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.dao.admin.ProvinceDAO#insertProvince(com.aof.model.admin.Province)
     */
    public Province insertProvince(Province province) {
        getHibernateTemplate().save(province);
        return province;
    }

    private final static String SQL_COUNT = "select count(*) from Province province";

    private final static String SQL = "from Province province";

    private final static Object[][] QUERY_CONDITIONS = {
    /* id */
    { ProvinceQueryCondition.ID_EQ, "province.id = ?", null },

    /* keyPropertyList */

    /* kmtoIdList */

    /* mtoIdList */
    { ProvinceQueryCondition.COUNTRY_ID_EQ, "province.country.id = ?", null },

    /* property */
    { ProvinceQueryCondition.ENGNAME_LIKE, "province.engName like ?", new LikeConvert() },
            { ProvinceQueryCondition.CHNNAME_LIKE, "province.chnName like ?", new LikeConvert() },
            { ProvinceQueryCondition.ENABLED_EQ, "province.enabled = ?", null }, };

    private final static Object[][] QUERY_ORDERS = {
    /* id */
    { ProvinceQueryOrder.ID, "province.id" },

    /* property */
    { ProvinceQueryOrder.ENGNAME, "province.engName" }, { ProvinceQueryOrder.CHNNAME, "province.chnName" }, { ProvinceQueryOrder.ENABLED, "province.enabled" }, };

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.dao.admin.ProvinceDAO#getProvinceListCount(java.util.Map)
     */
    public int getProvinceListCount(final Map conditions) {
        return getListCount(conditions, SQL_COUNT, QUERY_CONDITIONS);
    }

    public List getProvinceList(final Map conditions, final int pageNo, final int pageSize, final ProvinceQueryOrder order, final boolean descend) {
        return getList(conditions, pageNo, pageSize, order, descend, SQL, QUERY_CONDITIONS, QUERY_ORDERS);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.dao.admin.ProvinceDAO#getEnabledProvinceList()
     */
    public List getEnabledProvinceList() {
        Map conds = new HashMap();
        conds.put(ProvinceQueryCondition.ENABLED_EQ, EnabledDisabled.ENABLED.getEnumCode());
        return getProvinceList(conds, 0, -1, ProvinceQueryOrder.ENGNAME, false);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.dao.admin.ProvinceDAO#getEnabledProvinceList(com.aof.model.admin.Country)
     */
    public List getEnabledProvinceList(Country c) {
        Map conds = new HashMap();
        conds.put(ProvinceQueryCondition.ENABLED_EQ, EnabledDisabled.ENABLED.getEnumCode());
        conds.put(ProvinceQueryCondition.COUNTRY_ID_EQ, c.getId());
        return getProvinceList(conds, 0, -1, ProvinceQueryOrder.ENGNAME, false);
    }

    public void deleteProvince(Province p) {
        this.getHibernateTemplate().delete(p);

    }

    public Province getProvinceByChnName(Country country, String chnName) {
        List list = this.getHibernateTemplate().find("from Province p where p.country.id=? and p.chnName=?", new Object[] { country.getId(), chnName });
        if (list.isEmpty())
            return null;
        if (list.size() == 1)
            return (Province) list.get(0);
        throw new RuntimeException("province.chnName repeat");
    }

    public Province getProvinceByEngName(Country country, String engName) {
        List list = this.getHibernateTemplate().find("from Province p where p.country.id=? and p.engName=?", new Object[] { country.getId(), engName });
        if (list.isEmpty())
            return null;
        if (list.size() == 1)
            return (Province) list.get(0);
        throw new RuntimeException("province.engName repeat");
    }

}
