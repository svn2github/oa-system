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
import net.sourceforge.dao.admin.MenuDAO;
import net.sourceforge.model.admin.Menu;
import net.sourceforge.model.admin.query.MenuQueryOrder;

/**
 * MenuDAOµÄHibernateÊµÏÖ
 * @author nicebean
 * @version 1.0 (2005-11-11)
 */
public class MenuDAOHibernate extends BaseDAOHibernate implements MenuDAO {
    private Log log = LogFactory.getLog(MenuDAOHibernate.class);

    /* (non-Javadoc)
     * @see net.sourceforge.dao.admin.MenuDAO#getMenu(java.lang.Integer)
     */
    public Menu getMenu(Integer id) {
        if (id == null) {
            if (log.isDebugEnabled()) log.debug("try to get Menu with null id");
            return null;
        }
        return (Menu) getHibernateTemplate().get(Menu.class, id);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.dao.admin.MenuDAO#saveMenu(net.sourceforge.model.admin.Menu)
     */
    public Menu saveMenu(final Menu menu) {
        getHibernateTemplate().saveOrUpdate(menu);
        return menu;
    }

    /* (non-Javadoc)
     * @see net.sourceforge.dao.admin.MenuDAO#removeMenu(java.lang.Integer)
     */
    public void removeMenu(Integer id) {
        Menu menu = getMenu(id);
        getHibernateTemplate().delete(menu);
    }

    private final static String SQL_COUNT = "select count(*) from Menu m";
    
    private final static String SQL = "from Menu m";
    
    private final static Object[][] QUERY_CONDITIONS = {
    };
    
    private final static Object[][] QUERY_ORDERS = {
    };
    
    /* (non-Javadoc)
     * @see net.sourceforge.dao.admin.MenuDAO#getMenuListCount(java.util.Map)
     */
    public int getMenuListCount(final Map conditions) {
        return getListCount(conditions, SQL_COUNT, QUERY_CONDITIONS);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.dao.admin.MenuDAO#getMenuList(java.util.Map, int, int, net.sourceforge.model.admin.query.MenuQueryOrder, boolean)
     */
    public List getMenuList(final Map conditions, final int pageNo, final int pageSize, final MenuQueryOrder order, final boolean descend) {
        return getList(conditions, pageNo, pageSize, order, descend, SQL, QUERY_CONDITIONS, QUERY_ORDERS);
    }


}
