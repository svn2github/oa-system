/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.dao.admin.hibernate;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate.HibernateTemplate;

import com.aof.dao.BaseDAOHibernate;
import com.aof.dao.admin.UserDepartmentDAO;
import com.aof.model.admin.Department;
import com.aof.model.admin.User;
import com.aof.model.admin.UserDepartment;
import com.aof.model.admin.query.UserDepartmentQueryCondition;
import com.aof.model.admin.query.UserDepartmentQueryOrder;

/**
 * UserDepartmentDAOµÄHibernateÊµÏÖ
 * @author nicebean
 * @version 1.0 (2005-11-12)
 */
public class UserDepartmentDAOHibernate extends BaseDAOHibernate implements UserDepartmentDAO {
	private Log log = LogFactory.getLog(UserDepartmentDAOHibernate.class);

    /* (non-Javadoc)
     * @see com.aof.dao.admin.UserDepartmentDAO#getUserDepartment(java.lang.Integer, java.lang.Integer)
     */
    public UserDepartment getUserDepartment(Integer userId, Integer departmentId) {
        if (userId == null) {
            if (log.isDebugEnabled())
                log.debug("try to get UserDepartment with null user id");
            return null;
        }
        if (departmentId == null) {
            if (log.isDebugEnabled())
                log.debug("try to get UserDepartment with null department id");
            return null;
        }
        HibernateTemplate template = getHibernateTemplate();
        User u = (User) template.get(User.class, userId);
        if (u == null)
            return null;
        Department d = (Department) template
                .get(Department.class, departmentId);
        if (d == null)
            return null;
        return (UserDepartment) getHibernateTemplate().get(
                UserDepartment.class, new UserDepartment(u, d));
    }

    /* (non-Javadoc)
     * @see com.aof.dao.admin.UserDepartmentDAO#saveUserDepartment(com.aof.model.admin.UserDepartment)
     */
    public UserDepartment saveUserDepartment(UserDepartment ud) {
        HibernateTemplate template = getHibernateTemplate();
        template.save(ud);
        template.flush();
        template.evict(ud);
        return getUserDepartment(ud.getUser().getId(), ud.getDepartment().getId());
    }

    /* (non-Javadoc)
     * @see com.aof.dao.admin.UserDepartmentDAO#updateUserDepartment(com.aof.model.admin.UserDepartment)
     */
    public UserDepartment updateUserDepartment(UserDepartment ud) {
        HibernateTemplate template = getHibernateTemplate();
        template.update(ud);
        template.flush();
        template.evict(ud);
        return getUserDepartment(ud.getUser().getId(), ud.getDepartment().getId());
    }

    /* (non-Javadoc)
     * @see com.aof.dao.admin.UserDepartmentDAO#removeUserDepartment(com.aof.model.admin.UserDepartment)
     */
    public void removeUserDepartment(UserDepartment ud) {
        getHibernateTemplate().delete(ud);
    }

	private final static String SQL_COUNT = "select count(*) from UserDepartment ud";

	private final static String SQL = "from UserDepartment ud";

	private final static Object[][] QUERY_CONDITIONS = {
			{ UserDepartmentQueryCondition.USER_ID_EQ, "ud.user.id = ?", null },
			{ UserDepartmentQueryCondition.SITE_ID_EQ, "ud.department.site.id = ?", null }, };

	private final static Object[][] QUERY_ORDERS = {
            { UserDepartmentQueryOrder.SITE_NAME, "ud.department.site.name" }, 
			{ UserDepartmentQueryOrder.DEPARTMENT_NAME, "ud.department.name" }, };

	/* (non-Javadoc)
	 * @see com.aof.dao.admin.UserDepartmentDAO#getUserDepartmentListCount(java.util.Map)
	 */
	public int getUserDepartmentListCount(final Map conditions) {
		return getListCount(conditions, SQL_COUNT, QUERY_CONDITIONS);
	}

	/* (non-Javadoc)
	 * @see com.aof.dao.admin.UserDepartmentDAO#getUserDepartmentList(java.util.Map, int, int, com.aof.model.admin.query.UserDepartmentQueryOrder, boolean)
	 */
	public List getUserDepartmentList(final Map conditions, final int pageNo, final int pageSize, final UserDepartmentQueryOrder order, final boolean descend) {
		return getList(conditions, pageNo, pageSize, order, descend, SQL,
				QUERY_CONDITIONS, QUERY_ORDERS);
	}

}
