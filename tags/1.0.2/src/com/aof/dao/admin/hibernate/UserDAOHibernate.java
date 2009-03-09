/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.dao.admin.hibernate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.type.Type;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate.HibernateTemplate;

import com.aof.dao.BaseDAOHibernate;
import com.aof.dao.admin.UserDAO;
import com.aof.dao.convert.LikeConvert;
import com.aof.dao.convert.QueryParameterConvert;
import com.aof.model.admin.Department;
import com.aof.model.admin.Function;
import com.aof.model.admin.Site;
import com.aof.model.admin.User;
import com.aof.model.admin.UserRole;
import com.aof.model.admin.query.UserQueryCondition;
import com.aof.model.admin.query.UserQueryOrder;
import com.aof.model.metadata.EnabledDisabled;

/**
 * UserDAOµÄHibernateÊµÏÖ
 * 
 * @author nicebean
 * @version 1.0 (2005-11-12)
 */
public class UserDAOHibernate extends BaseDAOHibernate implements UserDAO {
    private Log log = LogFactory.getLog(UserDAOHibernate.class);

    /* (non-Javadoc)
     * @see com.aof.dao.admin.UserDAO#getUser(java.lang.Integer)
     */
    public User getUser(Integer id) {
        if (id == null) {
            if (log.isDebugEnabled())
                log.debug("try to get User with null id");
            return null;
        }
        return (User) getHibernateTemplate().get(User.class, id);
    }

    /* (non-Javadoc)
     * @see com.aof.dao.admin.UserDAO#saveUser(com.aof.model.admin.User)
     */
    public User saveUser(final User user) {
        getHibernateTemplate().saveOrUpdate(user);
        return user;
    }

    /* (non-Javadoc)
     * @see com.aof.dao.admin.UserDAO#removeUser(java.lang.Integer)
     */
    public void removeUser(Integer id) {
        User user = getUser(id);
        getHibernateTemplate().delete(user);
    }

    private final static String SQL_COUNT = "select count(*) from User u";

    private final static String SQL = "from User u";

    private final static Object[][] QUERY_CONDITIONS = {
            { UserQueryCondition.LOGINNAME_EQ, "u.loginName = ?", null },
            { UserQueryCondition.LOGINNAME_LIKE, "u.loginName like ?", new LikeConvert() },
            { UserQueryCondition.NAME_LIKE, "u.name like ?", new LikeConvert() },
            { UserQueryCondition.ROLE_ID_EQ, "exists (select ur.user.id from UserRole ur where ur.user.id = u.id and ur.role.id = ?)", null },
            { UserQueryCondition.SITE_ID_EQ, "exists (select us.user.id from UserSite us where us.user.id = u.id and us.site.id = ?)", null },
            { UserQueryCondition.PRIMARY_OR_SITE_ID_EQ, "(u.primarySite.id = ? or exists (select us.user.id from UserSite us where us.user.id = u.id and us.site.id = ?))", 
                new QueryParameterConvert() {

                    public Object convert(Object src) {
                        return new Object[] { src, src };
                    }
            } 
            },
            { UserQueryCondition.DEPARTMENT_ID_EQ, "exists (select ud.user.id from UserDepartment ud where ud.user.id = u.id and ud.department.id = ?)", null },
            { UserQueryCondition.ENABLED_EQ, "u.enabled = ?", null },
            { UserQueryCondition.FUNCTION_ID_EQ,
                    "exists (select ur.user.id from UserRole ur, RoleFunction rf where ur.role.id = rf.role.id and ur.user.id = u.id and rf.function.id = ?)",
                    null }, };

    private final static Object[][] QUERY_ORDERS = { { UserQueryOrder.LOGINNAME, "u.loginName" }, { UserQueryOrder.NAME, "u.name" },
            { UserQueryOrder.EMAIL, "u.email" }, { UserQueryOrder.TELEPHONE, "u.telephone" }, };

    /* (non-Javadoc)
     * @see com.aof.dao.admin.UserDAO#getUserListCount(java.util.Map)
     */
    public int getUserListCount(final Map conditions) {
        return getListCount(conditions, SQL_COUNT, QUERY_CONDITIONS);
    }

    /* (non-Javadoc)
     * @see com.aof.dao.admin.UserDAO#getUserList(java.util.Map, int, int, com.aof.model.admin.query.UserQueryOrder, boolean)
     */
    public List getUserList(final Map conditions, final int pageNo, final int pageSize, final UserQueryOrder order, final boolean descend) {
        return getList(conditions, pageNo, pageSize, order, descend, SQL, QUERY_CONDITIONS, QUERY_ORDERS);
    }

    /* (non-Javadoc)
     * @see com.aof.dao.admin.UserDAO#getUserRoleListByUser(com.aof.model.admin.User)
     */
    public List getUserRoleListByUser(User user) {
        if (user == null)
            return null;
        Integer userId = user.getId();
        if (userId == null)
            return null;
        return getHibernateTemplate().find("from UserRole ur where ur.user.id = ? order by ur.role.name", userId, Hibernate.INTEGER);
    }

    /* (non-Javadoc)
     * @see com.aof.dao.admin.UserDAO#getUserRoleListByUserAndFunction(com.aof.model.admin.User, com.aof.model.admin.Function)
     */
    public List getUserRoleListByUserAndFunction(User user, Function function) {
        if (user == null || function == null)
            return null;
        Integer userId = user.getId();
        if (userId == null)
            return null;
        Integer functionId = function.getId();
        if (functionId == null)
            return null;
        return getHibernateTemplate().find(
                "from UserRole ur where ur.user.id = ? and ur.role in (select rf.role from RoleFunction rf where rf.function.id = ?)",
                new Object[] { userId, functionId }, new Type[] { Hibernate.INTEGER, Hibernate.INTEGER });
    }

    /* (non-Javadoc)
     * @see com.aof.dao.admin.UserDAO#getUserRole(java.lang.Integer)
     */
    public UserRole getUserRole(Integer id) {
        if (id == null) {
            if (log.isDebugEnabled())
                log.debug("try to get UserRole with null id");
            return null;
        }
        return (UserRole) getHibernateTemplate().get(UserRole.class, id);
    }

    /* (non-Javadoc)
     * @see com.aof.dao.admin.UserDAO#saveUserRole(com.aof.model.admin.UserRole)
     */
    public UserRole saveUserRole(UserRole ur) {
        HibernateTemplate template = getHibernateTemplate();
        template.saveOrUpdate(ur);
        template.flush();
        template.evict(ur);
        return getUserRole(ur.getId());
    }

    /* (non-Javadoc)
     * @see com.aof.dao.admin.UserDAO#removeUserRole(com.aof.model.admin.UserRole)
     */
    public void removeUserRole(UserRole ur) {
        getHibernateTemplate().delete(ur);
    }

    /* (non-Javadoc)
     * @see com.aof.dao.admin.UserDAO#getGrantedSiteList(com.aof.model.admin.User, com.aof.model.admin.Function)
     */
    public List getGrantedSiteList(User user, Function function) {

        final String sql = "select distinct ur.grantedSite from UserRole ur " + " where (ur.user.id = ?) and (ur.grantedSite.enabled=?)"
                + " and (ur.role in (select rf.role from RoleFunction rf where rf.function.id = ?)) " + " and (ur.grantedSite is not null) "
                + " and (ur.grantedDepartment is null) order by ur.grantedSite.id ";

        return getHibernateTemplate().find(sql, new Object[] { user.getId(), EnabledDisabled.ENABLED.getEnumCode(), function.getId() },
                new Type[] { Hibernate.INTEGER, Hibernate.INTEGER, Hibernate.INTEGER });
    }

    
        
    /* (non-Javadoc)
     * @see com.aof.dao.admin.UserDAO#hasSitePower(com.aof.model.admin.Site, com.aof.model.admin.User, com.aof.model.admin.Function)
     */
    public boolean hasSitePower(Site site, User user, Function function) {
        String sql = "select ur.grantedSite.id from UserRole ur " + " where (ur.user.id = ?) "
                + " and (ur.role in (select rf.role from RoleFunction rf where rf.function.id = ?)) " + " and (ur.grantedSite is null or ur.grantedSite.id=?) "
                + " and (ur.grantedDepartment is null) ";

        return getHibernateTemplate().iterate(sql, new Object[] { user.getId(), function.getId(), site.getId() },
                new Type[] { Hibernate.INTEGER, Hibernate.INTEGER, Hibernate.INTEGER }).hasNext();

    }

    /* (non-Javadoc)
     * @see com.aof.dao.admin.UserDAO#getGrantedSiteDeparmentList(com.aof.model.admin.User, com.aof.model.admin.Function)
     */
    public List getGrantedSiteDeparmentList(User user, Function function) {
        final String sql = "select distinct ur.grantedDepartment from UserRole ur "
                + " where (ur.user.id = ?) and (ur.grantedSite.enabled=?) and (ur.grantedDepartment.enabled=?)"
                + " and (ur.role in (select rf.role from RoleFunction rf where rf.function.id = ?)) " + " and (ur.grantedSite is not null) "
                + " and (ur.grantedDepartment is not null)";

        List departList = getHibernateTemplate().find(sql,
                new Object[] { user.getId(), EnabledDisabled.ENABLED.getEnumCode(), EnabledDisabled.ENABLED.getEnumCode(), function.getId() },
                new Type[] { Hibernate.INTEGER, Hibernate.INTEGER, Hibernate.INTEGER, Hibernate.INTEGER });
        List siteList = new ArrayList();

        for (Iterator iter = departList.iterator(); iter.hasNext();) {
            Department d = (Department) iter.next();
            if (!siteList.contains(d.getSite())) {
                d.getSite().setDepartments(new ArrayList());
                siteList.add(d.getSite());
                d.setGranted(true);
            }
            d.getSite().getDepartments().add(d);
        }
        return siteList;
    }

    /* (non-Javadoc)
     * @see com.aof.dao.admin.UserDAO#hasDepartmentPower(com.aof.model.admin.Department, com.aof.model.admin.User, com.aof.model.admin.Function)
     */
    public boolean hasDepartmentPower(Department department, User user, Function function) {
        final String sql = "select ur.grantedDepartment.id from UserRole ur " + " where (ur.user.id = ?) "
                + " and (ur.role in (select rf.role from RoleFunction rf where rf.function.id = ?)) " + " and ( (ur.grantedSite is null) "
                + " or (ur.grantedSite.id=? and (ur.grantedDepartment is null or ur.grantedDepartment.id=?) ) )";

        return getHibernateTemplate().iterate(sql, new Object[] { user.getId(), function.getId(), department.getSite().getId(), department.getId() },
                new Type[] { Hibernate.INTEGER, Hibernate.INTEGER, Hibernate.INTEGER, Hibernate.INTEGER }).hasNext();
    }

    /* (non-Javadoc)
     * @see com.aof.dao.admin.UserDAO#hasGlobalPower(com.aof.model.admin.User, com.aof.model.admin.Function)
     */
    public boolean hasGlobalPower(User user, Function function) {
        String sql = "select ur.grantedSite.id from UserRole ur " + " where (ur.user.id = ?) "
                + " and (ur.role in (select rf.role from RoleFunction rf where rf.function.id = ?)) " + " and (ur.grantedSite is null ) "
                + " and (ur.grantedDepartment is null) ";

        return getHibernateTemplate().iterate(sql, new Object[] { user.getId(), function.getId() }, new Type[] { Hibernate.INTEGER, Hibernate.INTEGER, })
                .hasNext();
    }

    /* (non-Javadoc)
     * @see com.aof.dao.admin.UserDAO#getGrantedMenuList(com.aof.model.admin.User)
     */
    public List getGrantedMenuList(User user) {
        return getHibernateTemplate()
                .find(
                        "from Menu m where m.function.id in (select rf.function.id from RoleFunction rf where rf.role.id in (select ur.role.id from UserRole ur where ur.user.id = ?))",
                        user.getId(), Hibernate.INTEGER);
    }

    /* (non-Javadoc)
     * @see com.aof.dao.admin.UserDAO#getSiteOfGrantedSiteDeparmentList(com.aof.model.admin.User, com.aof.model.admin.Function)
     */
    public List getSiteOfGrantedSiteDeparmentList(User user, Function function) {
        final String sql = "select distinct ur.grantedSite from UserRole ur " + " join ur.grantedSite left join ur.grantedDepartment"
                + " where (ur.user.id = ?) and (ur.grantedSite.enabled=?) " + " and (ur.grantedDepartment is null or ur.grantedDepartment.enabled=?)"
                + " and (ur.role in (select rf.role from RoleFunction rf where rf.function.id = ?)) "
                + " and (ur.grantedSite is not null) order by ur.grantedSite.id";

        return getHibernateTemplate().find(sql,
                new Object[] { user.getId(), EnabledDisabled.ENABLED.getEnumCode(), EnabledDisabled.ENABLED.getEnumCode(), function.getId() },
                new Type[] { Hibernate.INTEGER, Hibernate.INTEGER, Hibernate.INTEGER, Hibernate.INTEGER });
    }

    /* (non-Javadoc)
     * @see com.aof.dao.admin.UserDAO#getGrantedDeparmentListOfSite(com.aof.model.admin.User, com.aof.model.admin.Site, com.aof.model.admin.Function)
     */
    public List getGrantedDeparmentListOfSite(User user, Site site, Function function) {
        final String sql = "select distinct ur.grantedDepartment from UserRole ur "
                + " where (ur.user.id = ?) and (ur.grantedSite.enabled=?) and (ur.grantedDepartment.enabled=?)"
                + " and (ur.role in (select rf.role from RoleFunction rf where rf.function.id = ?)) " + " and (ur.grantedSite.id=?) "
                + " and (ur.grantedSite is not null) " + " and (ur.grantedDepartment is not null) order by ur.grantedDepartment.id";

        return getHibernateTemplate().find(sql,
                new Object[] { user.getId(), EnabledDisabled.ENABLED.getEnumCode(), EnabledDisabled.ENABLED.getEnumCode(), function.getId(), site.getId() },
                new Type[] { Hibernate.INTEGER, Hibernate.INTEGER, Hibernate.INTEGER, Hibernate.INTEGER, Hibernate.INTEGER });

    }

    /* (non-Javadoc)
     * @see com.aof.dao.admin.UserDAO#hasUserPower(com.aof.model.admin.User, com.aof.model.admin.User, com.aof.model.admin.Function)
     */
    public boolean hasUserPower(User user, User operator, Function function) {

        if (this.hasGlobalPower(operator, function)) {
            return true;
        }

        String sql = "select ur.grantedSite.id from UserRole ur " + " where (ur.user.id = ?) "
                + " and (ur.role in (select rf.role from RoleFunction rf where rf.function.id = ?)) "
                + " and (ur.grantedSite.id in (select us.site.id from UserSite us where us.user.id=?))" + " and ur.grantedDepartment is null";

        if (getHibernateTemplate().iterate(sql, new Object[] { operator.getId(), function.getId(), user.getId() },
                new Type[] { Hibernate.INTEGER, Hibernate.INTEGER, Hibernate.INTEGER }).hasNext())
            return true;

        sql = "select ur.grantedDepartment.id from UserRole ur " + " where (ur.user.id = ?) "
                + " and (ur.role in (select rf.role from RoleFunction rf where rf.function.id = ?)) "
                + " and (ur.grantedDepartment.id in (select ud.department.id from UserDepartment ud where ud.user.id=?))";

        if (getHibernateTemplate().iterate(sql, new Object[] { operator.getId(), function.getId(), user.getId() },
                new Type[] { Hibernate.INTEGER, Hibernate.INTEGER, Hibernate.INTEGER }).hasNext())
            return true;

        return false;

    }

    public List getEnabledUserList(Function f, Site site) {
        final String sql = "select distinct ur.user from UserRole ur where "
            + " (ur.grantedSite is null or ur.grantedSite.id=?) "
            + " and (ur.role in (select rf.role from RoleFunction rf where rf.function.id = ?)) "
            + " and (ur.grantedDepartment is null) "
            + " and ur.user.enabled=? order by ur.user.name";
             
    return getHibernateTemplate().find(sql,
            new Object[] {site.getId(),f.getId(),EnabledDisabled.ENABLED.getEnumCode()  },
            new Type[] { Hibernate.INTEGER, Hibernate.INTEGER, Hibernate.INTEGER });

    }
    
    public List getEnabledUserList(Function f, Department dep) {
        final String sql = "select distinct ur.user from UserRole ur where "
            + " (ur.grantedSite is null or ur.grantedDepartment is null or ur.grantedDepartment.id=? ) "
            + " and (ur.role in (select rf.role from RoleFunction rf where rf.function.id = ?)) "            
            + " and ur.user.enabled=? order by ur.user.name";
             
    return getHibernateTemplate().find(sql,
            new Object[] {dep.getId(),f.getId(),EnabledDisabled.ENABLED.getEnumCode()  },
            new Type[] { Hibernate.INTEGER, Hibernate.INTEGER, Hibernate.INTEGER });

    }
    
    public List getEnabledUserList(Function function) {

        final String sql = "select distinct ur.user from UserRole ur " + " where "
                + " (ur.role in (select rf.role from RoleFunction rf where rf.function.id = ?)) " + " and (ur.grantedSite is null) "
                + " and (ur.grantedDepartment is null) and ur.user.enabled=? order by ur.user.id ";

        return getHibernateTemplate().find(sql, new Object[]{ function.getId(),EnabledDisabled.ENABLED.getEnumCode()}, new Type[]{Hibernate.INTEGER,Hibernate.INTEGER});
    }


}
