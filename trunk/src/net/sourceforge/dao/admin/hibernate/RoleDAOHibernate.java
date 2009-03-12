/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.dao.admin.hibernate;

import java.util.List;
import java.util.Map;

import net.sf.hibernate.Hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate.HibernateTemplate;

import net.sourceforge.dao.BaseDAOHibernate;
import net.sourceforge.dao.admin.RoleDAO;
import net.sourceforge.dao.convert.LikeConvert;
import net.sourceforge.model.admin.Role;
import net.sourceforge.model.admin.RoleFunction;
import net.sourceforge.model.admin.query.RoleQueryCondition;
import net.sourceforge.model.admin.query.RoleQueryOrder;

/**
 * RoleDAOµÄHibernateÊµÏÖ
 * @author nicebean
 * @version 1.0 (2005-11-11)
 */
public class RoleDAOHibernate extends BaseDAOHibernate implements RoleDAO {
    private Log log = LogFactory.getLog(RoleDAOHibernate.class);

    /* (non-Javadoc)
     * @see net.sourceforge.dao.admin.RoleDAO#getRole(java.lang.Integer)
     */
    public Role getRole(Integer id) {
        if (id == null) {
            if (log.isDebugEnabled()) log.debug("try to get Role with null id");
            return null;
        }
        return (Role) getHibernateTemplate().get(Role.class, id);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.dao.admin.RoleDAO#saveRole(net.sourceforge.model.admin.Role)
     */
    public Role saveRole(final Role role) {
        getHibernateTemplate().saveOrUpdate(role);
        return role;
    }

    /* (non-Javadoc)
     * @see net.sourceforge.dao.admin.RoleDAO#removeRole(java.lang.Integer)
     */
    public void removeRole(Integer id) {
        Role role = getRole(id);
        getHibernateTemplate().delete(role);
    }

    private final static String SQL_COUNT = "select count(*) from Role r";
    
    private final static String SQL = "from Role r";
    
    private final static Object[][] QUERY_CONDITIONS = {
        { RoleQueryCondition.ID_EQ, "r.id = ?", null },
        { RoleQueryCondition.NAME_LIKE, "r.name like ?", new LikeConvert() },
        { RoleQueryCondition.TYPE_EQ, "r.type = ?", null },
    };
    
    private final static Object[][] QUERY_ORDERS = {
        { RoleQueryOrder.ID, "r.id" },
        { RoleQueryOrder.NAME, "r.name" },
        { RoleQueryOrder.TYPE, "r.type" },
    };
    
    /* (non-Javadoc)
     * @see net.sourceforge.dao.admin.RoleDAO#getRoleListCount(java.util.Map)
     */
    public int getRoleListCount(final Map conditions) {
        return getListCount(conditions, SQL_COUNT, QUERY_CONDITIONS);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.dao.admin.RoleDAO#getRoleList(java.util.Map, int, int, net.sourceforge.model.admin.query.RoleQueryOrder, boolean)
     */
    public List getRoleList(final Map conditions, final int pageNo, final int pageSize, final RoleQueryOrder order, final boolean descend) {
        return getList(conditions, pageNo, pageSize, order, descend, SQL, QUERY_CONDITIONS, QUERY_ORDERS);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.dao.admin.RoleDAO#getFunctionListByRole(net.sourceforge.model.admin.Role)
     */
    public List getFunctionListByRole(Role role) {
        if (role == null) return null;
        Integer roleId = role.getId();
        if (roleId == null) return null;
        return getHibernateTemplate().find("select rf.function from RoleFunction rf where rf.role.id = ? order by rf.function.name", roleId, Hibernate.INTEGER);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.dao.admin.RoleDAO#saveRoleFunction(net.sourceforge.model.admin.RoleFunction)
     */
    public RoleFunction saveRoleFunction(RoleFunction roleFunction) {
        HibernateTemplate template = getHibernateTemplate();
        template.save(roleFunction);
        return roleFunction;
    }

    /* (non-Javadoc)
     * @see net.sourceforge.dao.admin.RoleDAO#removeRoleFunction(net.sourceforge.model.admin.RoleFunction)
     */
    public void removeRoleFunction(RoleFunction roleFunction) {
        getHibernateTemplate().delete(roleFunction);
    }

}
