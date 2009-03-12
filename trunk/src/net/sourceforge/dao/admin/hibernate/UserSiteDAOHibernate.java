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
import org.springframework.orm.hibernate.HibernateTemplate;

import net.sourceforge.dao.BaseDAOHibernate;
import net.sourceforge.dao.admin.UserSiteDAO;
import net.sourceforge.model.admin.Site;
import net.sourceforge.model.admin.User;
import net.sourceforge.model.admin.UserSite;
import net.sourceforge.model.admin.query.UserSiteQueryCondition;
import net.sourceforge.model.admin.query.UserSiteQueryOrder;

/**
 * UserSiteDAOµÄHibernateÊµÏÖ
 * @author nicebean
 * @version 1.0 (2005-11-12)
 */
public class UserSiteDAOHibernate extends BaseDAOHibernate implements UserSiteDAO {
	private Log log = LogFactory.getLog(UserSiteDAOHibernate.class);

	/* (non-Javadoc)
	 * @see net.sourceforge.dao.admin.UserSiteDAO#getUserSite(java.lang.Integer, java.lang.Integer)
	 */
	public UserSite getUserSite(Integer userId, Integer siteId) {
		if (userId == null) {
			if (log.isDebugEnabled())
				log.debug("try to get UserSite with null user id");
			return null;
		}
        if (siteId == null) {
            if (log.isDebugEnabled())
                log.debug("try to get UserSite with null site id");
            return null;
        }
        HibernateTemplate template = getHibernateTemplate();
        User u = (User) template.get(User.class, userId);
        if (u == null) return null;
        Site s = (Site) template.get(Site.class, siteId);
        if (s == null) return null;
        return (UserSite) getHibernateTemplate().get(
                UserSite.class, new UserSite(u, s));
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.dao.admin.UserSiteDAO#saveUserSite(net.sourceforge.model.admin.UserSite)
	 */
	public UserSite saveUserSite(final UserSite us) {
        HibernateTemplate template = getHibernateTemplate();
        template.save(us);
        template.flush();
        template.evict(us);
        return getUserSite(us.getUser().getId(), us.getSite().getId());
	}

    /* (non-Javadoc)
     * @see net.sourceforge.dao.admin.UserSiteDAO#updateUserSite(net.sourceforge.model.admin.UserSite)
     */
    public UserSite updateUserSite(UserSite us) {
        HibernateTemplate template = getHibernateTemplate();
        template.update(us);
        template.flush();
        template.evict(us);
        return getUserSite(us.getUser().getId(), us.getSite().getId());
    }

    /* (non-Javadoc)
     * @see net.sourceforge.dao.admin.UserSiteDAO#removeUserSite(net.sourceforge.model.admin.UserSite)
     */
    public void removeUserSite(UserSite us) {
        getHibernateTemplate().delete(us);
    }

	private final static String SQL_COUNT = "select count(*) from UserSite us";

	private final static String SQL = "from UserSite us";


	private final static Object[][] QUERY_CONDITIONS = {
			{ UserSiteQueryCondition.USER_ID_EQ, "us.user.id = ?", null }, };

	private final static Object[][] QUERY_ORDERS = {
			{ UserSiteQueryOrder.SITE_NAME, "us.site.name" }, };

	/* (non-Javadoc)
	 * @see net.sourceforge.dao.admin.UserSiteDAO#getUserSiteListCount(java.util.Map)
	 */
	public int getUserSiteListCount(final Map conditions) {
		return getListCount(conditions, SQL_COUNT, QUERY_CONDITIONS);
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.dao.admin.UserSiteDAO#getUserSiteList(java.util.Map, int, int, net.sourceforge.model.admin.query.UserSiteQueryOrder, boolean)
	 */
	public List getUserSiteList(final Map conditions, final int pageNo, final int pageSize, final UserSiteQueryOrder order, final boolean descend) {
		return getList(conditions, pageNo, pageSize, order, descend, SQL, QUERY_CONDITIONS, QUERY_ORDERS);
	}

}
