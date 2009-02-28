/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.service.admin.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.aof.dao.admin.UserDAO;
import com.aof.dao.admin.UserDepartmentDAO;
import com.aof.dao.admin.UserRoleDAO;
import com.aof.dao.admin.UserSiteDAO;
import com.aof.model.admin.Department;
import com.aof.model.admin.Function;
import com.aof.model.admin.Menu;
import com.aof.model.admin.Site;
import com.aof.model.admin.User;
import com.aof.model.admin.UserDepartment;
import com.aof.model.admin.UserRole;
import com.aof.model.admin.UserSite;
import com.aof.model.admin.query.UserDepartmentQueryCondition;
import com.aof.model.admin.query.UserDepartmentQueryOrder;
import com.aof.model.admin.query.UserQueryCondition;
import com.aof.model.admin.query.UserQueryOrder;
import com.aof.model.admin.query.UserSiteQueryCondition;
import com.aof.model.admin.query.UserSiteQueryOrder;
import com.aof.model.metadata.EnabledDisabled;
import com.aof.service.BaseManager;
import com.aof.service.admin.DepartmentManager;
import com.aof.service.admin.ExpenseCategoryManager;
import com.aof.service.admin.SiteManager;
import com.aof.service.admin.UserManager;

/**
 * UserManager的实现
 * 
 * @author nicebean
 * @version 1.0 (2005-11-14)
 */
public class UserManagerImpl extends BaseManager implements UserManager {

    private UserDAO dao;

    private UserSiteDAO userSiteDAO;

    private UserDepartmentDAO userDepartmentDAO;

    private DepartmentManager departmentManager;

    private SiteManager siteManager;
    
    private ExpenseCategoryManager expenseCategoryManager;
    
    private UserRoleDAO userRoleDAO;

    
    /**
     * 设置DepartmentManager给departmentManager属性
     * 
     * @param departmentManager
     *            DepartmentManager对象
     */
    public void setExpenseCategoryManager(ExpenseCategoryManager expenseCategoryManager) {
        this.expenseCategoryManager = expenseCategoryManager;
    }

    
    /**
     * 设置DepartmentManager给departmentManager属性
     * 
     * @param departmentManager
     *            DepartmentManager对象
     */
    public void setDepartmentManager(DepartmentManager departmentManager) {
        this.departmentManager = departmentManager;
    }

    /**
     * 设置UserDAO给dao属性
     * 
     * @param dao
     *            UserDAO对象
     */
    public void setUserDAO(UserDAO dao) {
        this.dao = dao;
    }

    /**
     * 设置UserDepartmentDAO给userDepartmentDAO属性
     * 
     * @param userDepartmentDAO
     *            UserDepartmentDAO对象
     */
    public void setUserDepartmentDAO(UserDepartmentDAO userDepartmentDAO) {
        this.userDepartmentDAO = userDepartmentDAO;
    }

    /**
     * 设置UserSiteDAO给userSiteDAO属性
     * 
     * @param userSiteDAO
     *            UserSiteDAO对象
     */
    public void setUserSiteDAO(UserSiteDAO userSiteDAO) {
        this.userSiteDAO = userSiteDAO;
    }

    /**
     * 设置SiteManager给siteManager属性
     * 
     * @param siteManager
     *            SiteManager对象
     */
    public void setSiteManager(SiteManager siteManager) {
        this.siteManager = siteManager;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin.UserManager#getUser(java.lang.Integer)
     */
    public User getUser(Integer id)  {
        return dao.getUser(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin.UserManager#saveUser(com.aof.model.admin.User)
     */
    public User saveUser(User user)  {
        return dao.saveUser(user);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin.UserManager#removeUser(java.lang.Integer)
     */
    public void removeUser(Integer id)  {
        dao.removeUser(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin.UserManager#getUserByLoginName(java.lang.String)
     */
    public User getUserByLoginName(String loginName)  {
        if (loginName == null)
            return null;
        Map conditions = new HashMap();
        conditions.put(UserQueryCondition.LOGINNAME_EQ, loginName);
        List l = dao.getUserList(conditions, 0, -1, null, false);
        if (l.size() == 0)
            return null;
        return (User) l.get(0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin.UserManager#getUserListCount(java.util.Map)
     */
    public int getUserListCount(Map conditions)  {
        return dao.getUserListCount(conditions);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin.UserManager#getUserList(java.util.Map, int,
     *      int, com.aof.model.admin.query.UserQueryOrder, boolean)
     */
    public List getUserList(Map conditions, int pageNo, int pageSize, UserQueryOrder order, boolean descend)  {
        return dao.getUserList(conditions, pageNo, pageSize, order, descend);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin.UserManager#getUserSiteListByUser(com.aof.model.admin.User)
     */
    public List getUserSiteListByUser(User user)  {
        Map conditions = new HashMap();
        conditions.put(UserSiteQueryCondition.USER_ID_EQ, user.getId());
        return userSiteDAO.getUserSiteList(conditions, 0, -1, UserSiteQueryOrder.SITE_NAME, false);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin.UserManager#getUserSite(java.lang.Integer,
     *      java.lang.Integer)
     */
    public UserSite getUserSite(Integer userId, Integer siteId)  {
        return userSiteDAO.getUserSite(userId, siteId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin.UserManager#saveUserSite(com.aof.model.admin.UserSite)
     */
    public UserSite saveUserSite(UserSite us)  {
        return userSiteDAO.saveUserSite(us);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin.UserManager#updateUserSite(com.aof.model.admin.UserSite)
     */
    public UserSite updateUserSite(UserSite us)  {
        return userSiteDAO.updateUserSite(us);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin.UserManager#removeUserSite(com.aof.model.admin.UserSite)
     */
    public void removeUserSite(UserSite us)  {
        for (Iterator itor = getUserDepartmentListByUserAndSite(us.getUser(), us.getSite()).iterator(); itor.hasNext();) {
            userDepartmentDAO.removeUserDepartment((UserDepartment) itor.next());
        }
        userSiteDAO.removeUserSite(us);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin.UserManager#getUserDepartmentListByUser(com.aof.model.admin.User)
     */
    public List getUserDepartmentListByUser(User user)  {
        Map conditions = new HashMap();
        conditions.put(UserDepartmentQueryCondition.USER_ID_EQ, user.getId());
        return userDepartmentDAO.getUserDepartmentList(conditions, 0, -1, UserDepartmentQueryOrder.SITE_NAME, false);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin.UserManager#getUserDepartmentListByUserAndSite(com.aof.model.admin.User,
     *      com.aof.model.admin.Site)
     */
    public List getUserDepartmentListByUserAndSite(User user, Site site)  {
        Map conditions = new HashMap();
        conditions.put(UserDepartmentQueryCondition.USER_ID_EQ, user.getId());
        conditions.put(UserDepartmentQueryCondition.SITE_ID_EQ, site.getId());
        return userDepartmentDAO.getUserDepartmentList(conditions, 0, -1, UserDepartmentQueryOrder.DEPARTMENT_NAME, false);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin.UserManager#getUserDepartment(java.lang.Integer,
     *      java.lang.Integer)
     */
    public UserDepartment getUserDepartment(Integer userId, Integer departmentId)  {
        return userDepartmentDAO.getUserDepartment(userId, departmentId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin.UserManager#saveUserDepartment(com.aof.model.admin.UserDepartment)
     */
    public UserDepartment saveUserDepartment(UserDepartment ud)  {
        return userDepartmentDAO.saveUserDepartment(ud);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin.UserManager#updateUserDepartment(com.aof.model.admin.UserDepartment)
     */
    public UserDepartment updateUserDepartment(UserDepartment ud)  {
        return userDepartmentDAO.updateUserDepartment(ud);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin.UserManager#removeUserDepartment(com.aof.model.admin.UserDepartment)
     */
    public void removeUserDepartment(UserDepartment ud)  {
        userDepartmentDAO.removeUserDepartment(ud);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin.UserManager#getUserRoleListByUser(com.aof.model.admin.User)
     */
    public List getUserRoleListByUser(User user)  {
        return dao.getUserRoleListByUser(user);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin.UserManager#getUserRoleListByUserAndFunction(com.aof.model.admin.User,
     *      com.aof.model.admin.Function)
     */
    public List getUserRoleListByUserAndFunction(User user, Function function)  {
        return dao.getUserRoleListByUserAndFunction(user, function);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin.UserManager#getUserRole(java.lang.Integer)
     */
    public UserRole getUserRole(Integer id)  {
        return dao.getUserRole(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin.UserManager#saveUserRole(com.aof.model.admin.UserRole)
     */
    public UserRole saveUserRole(UserRole ur)  {
        return dao.saveUserRole(ur);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin.UserManager#removeUserRole(com.aof.model.admin.UserRole)
     */
    public void removeUserRole(UserRole ur)  {
        dao.removeUserRole(ur);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin.UserManager#getGrantedSiteList(com.aof.model.admin.User,
     *      com.aof.model.admin.Function)
     */
    public List getGrantedSiteList(User user, Function function)  {
        if (!function.isDepartment()) {
            return dao.getGrantedSiteList(user, function);
        } else
            throw new RuntimeException("not with department level function");
    }

    

    public List getEnabledUserList(Function function) {
        return dao.getEnabledUserList(function);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin.UserManager#hasSitePower(com.aof.model.admin.Site,
     *      com.aof.model.admin.User, com.aof.model.admin.Function)
     */
    public boolean hasSitePower(Site site, User user, Function function)  {
        return dao.hasSitePower(site, user, function);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin.UserManager#hasDepartmentPower(com.aof.model.admin.Department,
     *      com.aof.model.admin.User, com.aof.model.admin.Function)
     */
    public boolean hasDepartmentPower(Department department, User user, Function function)  {
        return dao.hasDepartmentPower(department, user, function);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin.UserManager#getSiteOfGrantedSiteDeparmentList(com.aof.model.admin.User,
     *      com.aof.model.admin.Function)
     */
    public List getSiteOfGrantedSiteDeparmentList(User user, Function function)  {
        List siteList = null;
        if (hasGlobalPower(user, function)) {
            siteList = siteManager.getAllEnabledSiteList();
        } else {
            siteList = dao.getSiteOfGrantedSiteDeparmentList(user, function);

        }
        return siteList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin.UserManager#getGrantedSiteDeparmentList(com.aof.model.admin.User,
     *      com.aof.model.admin.Function)
     */
    public List getGrantedSiteDeparmentList(User user, Function function)  {
        List siteList = null;
        if (hasGlobalPower(user, function)) {
            siteList = siteManager.getAllEnabledSiteList();
            departmentManager.fillDepartment(siteList, true);
            setGranted(siteList);
        } else {
            siteList = dao.getGrantedSiteList(user, function);
            departmentManager.fillDepartment(siteList, true);
            setGranted(siteList);

            List departmentSiteList = dao.getGrantedSiteDeparmentList(user, function);
            for (Iterator iter = departmentSiteList.iterator(); iter.hasNext();) {
                Site site = (Site) iter.next();
                if (!siteList.contains(site)) {
                    siteList.add(site);
                    buildDepartmentList(site);
                }
            }
        }
        return siteList;
    }

    private void setGranted(Site site) {
        List list = new ArrayList();
        list.add(site);
        setGranted(list);
    }

    private void setGranted(List siteList) {
        for (Iterator iter = siteList.iterator(); iter.hasNext();) {
            Site site = (Site) iter.next();
            for (Iterator iterator = site.getDepartments().iterator(); iterator.hasNext();) {
                Department d = (Department) iterator.next();
                d.setGranted(true);
            }

        }

    }

    private void buildDepartmentList(Site site) {
        List dList = site.getDepartments();
        Set rootSet = new HashSet();
        Map departmentItems = new HashMap();
        for (Iterator iter = dList.iterator(); iter.hasNext();) {
            Department d = (Department) iter.next();
            d.setGranted(true);
            visiteAncestor(d, rootSet, departmentItems);
        }
        List resultList = new ArrayList();
        List rootList = new ArrayList();
        rootList.addAll(rootSet);
        buildList(resultList, rootList, departmentItems, 0);
        site.setDepartments(resultList);
    }

    private void buildList(List resultList, List rootList, Map departmentItems, int indent) {
        if (rootList == null)
            return;
        String strIndent = getIndenetString(indent);
        for (int i = 0; i < rootList.size(); i++) {
            Department d = (Department) rootList.get(i);
            d.setIndentName(strIndent + d.getName());
            resultList.add(d);
            List l = (List) departmentItems.get(d);
            buildList(resultList, l, departmentItems, indent + 1);
        }
    }

    private String getIndenetString(int indent) {
        StringBuffer space = new StringBuffer();
        while (indent-- > 0)
            space.append("　　");
        return space.toString();
    }

    private void visiteAncestor(final Department d, Set rootSet, Map departmentItems) {
        for (Department item = d;;) {
            if (item.getParentDepartment() == null) {
                rootSet.add(item);
                return;
            } else {
                Department parent = item.getParentDepartment();
                if (departmentItems.get(parent) == null) {
                    departmentItems.put(parent, new ArrayList());
                }
                List childList = (List) departmentItems.get(parent);
                childList.add(item);
                item = parent;
            }
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin.UserManager#hasGlobalPower(com.aof.model.admin.User,
     *      com.aof.model.admin.Function)
     */
    public boolean hasGlobalPower(User user, Function function)  {
        return dao.hasGlobalPower(user, function);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin.UserManager#getGrantedMenuSet(com.aof.model.admin.User)
     */
    public Set getGrantedMenuSet(User user)  {
        Set resultSet = new TreeSet(Menu.MENU_COMPARATOR);
        for (Iterator itor = dao.getGrantedMenuList(user).iterator(); itor.hasNext();) {
            Menu m = (Menu) itor.next();
            while (true) {
                Menu pm = m.getParentMenu();
                if (pm == null) {
                    resultSet.add(m);
                    break;
                } else {
                    pm.addChild(m);
                }
                m = pm;
            }
        }

        return resultSet;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin.UserManager#getEnabledSiteDepartmentListOfUser(com.aof.model.admin.User)
     */
    public List getEnabledSiteDepartmentListOfUser(User user)  {
        List userSiteList = this.getEnabledUserSiteList(user);
        List userDepartmentList = this.getUserDepartmentListByUser(user);
        List retVal = new ArrayList();
        for (Iterator iter = userSiteList.iterator(); iter.hasNext();) {
            UserSite userSite = (UserSite) iter.next();
            Site site = userSite.getSite();
            if (site.getEnabled().equals(EnabledDisabled.ENABLED)) {
                retVal.add(site);
                site.setDepartments(new ArrayList());
            }
        }
        for (Iterator iter = userDepartmentList.iterator(); iter.hasNext();) {
            UserDepartment ud = (UserDepartment) iter.next();
            Department d = ud.getDepartment();
            if (d.getEnabled().equals(EnabledDisabled.ENABLED)) {
                List dList = d.getSite().getDepartments();
                if (dList != null) {
                    dList.add(d);
                }
            }
        }
        return retVal;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin.UserManager#getEnabledUserSiteList(com.aof.model.admin.User)
     */
    public List getEnabledUserSiteList(User user)  {
        List userSiteList = this.getUserSiteListByUser(user);
        List retVal = new ArrayList();
        for (Iterator iter = userSiteList.iterator(); iter.hasNext();) {
            UserSite userSite = (UserSite) iter.next();
            Site site = userSite.getSite();
            if (site.getEnabled().equals(EnabledDisabled.ENABLED)) {
                retVal.add(userSite);
            }
        }
        return userSiteList;
    }
    

    /* (non-Javadoc)
     * @see com.aof.service.admin.UserManager#getEnabledUserSiteListWithDepartments(com.aof.model.admin.User)
     */
    public List getEnabledUserSiteListWithDepartmentsAndExpenseCategory(User user) {
        List userSiteList = this.getUserSiteListByUser(user);
        List retVal = new ArrayList();
        for (Iterator iter = userSiteList.iterator(); iter.hasNext();) {
            UserSite userSite = (UserSite) iter.next();
            Site site = userSite.getSite();
            if (site.getEnabled().equals(EnabledDisabled.ENABLED)) {
                retVal.add(userSite);
                userSite.setEnabledUserDepartmentList(this.getEnabledUserDepartmentList(user,site));
                site.setEnabledTravelExpenseCategory(expenseCategoryManager.getEnabledTravelExpenseCategoryOfSite(site.getId().intValue()));
                site.setEnabledNotTravelExpenseCategoryList(expenseCategoryManager.getEnabledNotTravelExpenseCategoryListOfSite(site.getId().intValue()));
            }
        }
        return userSiteList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin.UserManager#getEnabledUserDepartmentList(com.aof.model.admin.User,
     *      com.aof.model.admin.Site)
     */
    public List getEnabledUserDepartmentList(User user, Site site)  {
        List udList = this.getUserDepartmentListByUserAndSite(user, site);
        List retVal = new ArrayList();
        for (Iterator iter = udList.iterator(); iter.hasNext();) {
            UserDepartment ud = (UserDepartment) iter.next();
            Department d = ud.getDepartment();
            if (d.getEnabled().equals(EnabledDisabled.ENABLED)) {
                retVal.add(ud);
            }
        }
        return retVal;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin.UserManager#getGrantedDepartmentListOfSite(com.aof.model.admin.User,
     *      com.aof.model.admin.Site, com.aof.model.admin.Function)
     */
    public List getGrantedDepartmentListOfSite(User user, Site site, Function function)  {
        if (this.hasSitePower(site, user, function)) {
            departmentManager.fillDepartment(site, true);
            setGranted(site);
            return site.getDepartments();
        } else {
            List departmentList = dao.getGrantedDeparmentListOfSite(user, site, function);
            site.setDepartments(departmentList);
            this.buildDepartmentList(site);
            return site.getDepartments();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin.UserManager#getEnabledUserListOfDepartment(com.aof.model.admin.Department)
     */
    public List getEnabledUserListOfDepartment(Department department)  {
        Map conds = new HashMap();
        conds.put(UserQueryCondition.DEPARTMENT_ID_EQ, department.getId());
        conds.put(UserQueryCondition.ENABLED_EQ, EnabledDisabled.ENABLED.getEnumCode());
        return dao.getUserList(conds, 0, -1, UserQueryOrder.NAME, false);
    }

    /* (non-Javadoc)
     * @see com.aof.service.admin.UserManager#hasUserPower(com.aof.model.admin.User, com.aof.model.admin.User, com.aof.model.admin.Function)
     */
    public boolean hasUserPower(User user, User operator, Function function)  {
        return dao.hasUserPower(user,operator,function);
    }


    public List getEnabledUserList(Function f, Site site) {
       return dao.getEnabledUserList(f,site);
    }
    
    public List getEnabledUserList(Function f, Department dep) {
        return dao.getEnabledUserList(f,dep);
     }


    public List getEnabledSiteDepartmentTreeOfUser(User user) {
        List siteList = this.getEnabledSiteDepartmentListOfUser(user);
        for (Iterator iter = siteList.iterator(); iter.hasNext();) {
            Site site = (Site) iter.next();
            buildDepartmentList(site);
        }
        return siteList;
    }


    public List getEnabledSiteListOfUser(User currentUser) {
        List userSiteList=getEnabledUserSiteList(currentUser);
        List retVal=new ArrayList();
        for (Iterator iter = userSiteList.iterator(); iter.hasNext();) {
            UserSite userSite = (UserSite) iter.next();
            retVal.add(userSite.getSite());
        }
        return retVal;
    }



    public void setUserRoleDAO(UserRoleDAO userRoleDAO) {
        this.userRoleDAO = userRoleDAO;
    }


    public void fillUserRole(List userList) {
        userRoleDAO.fillUserRole(userList);
    }


    public List getUserRoleList(Map conditions, UserQueryOrder order, boolean isDesc) {
        return userRoleDAO.getUserRoleList(conditions,order,isDesc);
        
    }

}
