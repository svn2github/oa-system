/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.dao.admin.hibernate;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate.HibernateCallback;

import net.sourceforge.dao.BaseDAOHibernate;
import net.sourceforge.dao.admin.SiteDAO;
import net.sourceforge.dao.convert.LikeConvert;
import net.sourceforge.model.admin.Site;
import net.sourceforge.model.admin.SiteLogo;
import net.sourceforge.model.admin.query.SiteQueryCondition;
import net.sourceforge.model.admin.query.SiteQueryOrder;
import net.sourceforge.model.metadata.EnabledDisabled;

/**
 * SiteDAOµÄHibernateÊµÏÖ
 * @author nicebean
 * @version 1.0 (2005-11-11)
 */
public class SiteDAOHibernate extends BaseDAOHibernate implements SiteDAO {
    private Log log = LogFactory.getLog(SiteDAOHibernate.class);

    /* (non-Javadoc)
     * @see net.sourceforge.dao.admin.SiteDAO#getSite(java.lang.Integer)
     */
    public Site getSite(Integer id) {
        if (id == null) {
            if (log.isDebugEnabled()) log.debug("try to get Site with null id");
            return null;
        }
        return (Site) getHibernateTemplate().get(Site.class, id);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.dao.admin.SiteDAO#saveSite(net.sourceforge.model.admin.Site)
     */
    public Site saveSite(final Site site) {
        getHibernateTemplate().saveOrUpdate(site);
        return site;
    }

    /* (non-Javadoc)
     * @see net.sourceforge.dao.admin.SiteDAO#removeSite(java.lang.Integer)
     */
    public void removeSite(Integer id) {
        Site site = getSite(id);
        getHibernateTemplate().delete(site);
    }

    private final static String SQL_COUNT = "select count(*) from Site s";
    
    private final static String SQL = "from Site s";
    
    private final static Object[][] QUERY_CONDITIONS = {
        { SiteQueryCondition.NAME_LIKE, "s.name like ?", new LikeConvert() },
        { SiteQueryCondition.ENABLED_EQ, "s.enabled = ?", null },
    };
    
    private final static Object[][] QUERY_ORDERS = {
        { SiteQueryOrder.NAME, "s.name" },
        { SiteQueryOrder.ENABLED, "s.enabled" },
    };

    /* (non-Javadoc)
     * @see net.sourceforge.dao.admin.SiteDAO#getSiteListCount(java.util.Map)
     */
    public int getSiteListCount(Map conditions) {
        return getListCount(conditions, SQL_COUNT, QUERY_CONDITIONS);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.dao.admin.SiteDAO#getSiteList(java.util.Map, int, int, net.sourceforge.model.admin.query.SiteQueryOrder, boolean)
     */
    public List getSiteList(Map conditions, int pageNo, int pageSize, SiteQueryOrder order, boolean descend) {
        return getList(conditions, pageNo, pageSize, order, descend, SQL, QUERY_CONDITIONS, QUERY_ORDERS);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.dao.admin.SiteDAO#isSiteHasLogo(java.lang.Integer)
     */
    public boolean isSiteHasLogo(Integer id) {
        return getHibernateTemplate().iterate("select sl.id from SiteLogo sl where sl.id = ? and not sl.logo is null", id, Hibernate.INTEGER).hasNext();
    }

    /* (non-Javadoc)
     * @see net.sourceforge.dao.admin.SiteDAO#getSiteLogo(java.lang.Integer)
     */
    public InputStream getSiteLogo(final Integer id) {
        return (InputStream) getHibernateTemplate().execute( new HibernateCallback() {
            
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                SiteLogo sl = (SiteLogo) session.get(SiteLogo.class, id);
                if (sl == null) return null;
                Blob logo = sl.getLogo();
                if (logo == null) return null;
                try {
                    return preRead(logo.getBinaryStream());
                } catch (IOException e) {
                    throw new HibernateException(e);
                }
            }
            
        });
    }

    /* (non-Javadoc)
     * @see net.sourceforge.dao.admin.SiteDAO#saveSiteLogo(java.lang.Integer, java.io.InputStream)
     */
    public void saveSiteLogo(final Integer id, final InputStream in) {
        getHibernateTemplate().execute( new HibernateCallback() {
            
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                SiteLogo sl = (SiteLogo) session.get(SiteLogo.class, id);
                if (sl == null) return null;
                try {
                    Blob logo = Hibernate.createBlob(in);
                    sl.setLogo(logo);
                    session.update(sl);
                } catch (IOException e) {
                    throw new HibernateException(e);
                }
                return null;
            }
            
        });
    }

    public List getEnabledSiteList() {
        Map conds=new HashMap();
        conds.put(SiteQueryCondition.ENABLED_EQ,EnabledDisabled.ENABLED.getEnumCode());
        return this.getSiteList(conds,0,-1,null,false);
    }

}
