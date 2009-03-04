/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.service.admin;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.aof.model.admin.Department;
import com.aof.model.admin.Function;
import com.aof.model.admin.Site;
import com.aof.model.admin.User;
import com.aof.model.admin.UserDepartment;
import com.aof.model.admin.UserRole;
import com.aof.model.admin.UserSite;
import com.aof.model.admin.query.UserQueryOrder;

/**
 * 定义操作User的接口
 * 
 * @author nicebean
 * @version 1.0 (2005-11-14)
 */
public interface UserManager {

    /**
     * 从数据库取得指定id的User对象
     * 
     * @param id
     *            User对象的id
     * @return 返回指定的User对象
     * 
     */
    public User getUser(Integer id) ;

    /**
     * 保存User对象到数据库
     * 
     * @param user
     *            要保存的User对象
     * @return 保存后的User对象
     * 
     */
    public User saveUser(User user) ;

    /**
     * 从数据库删除指定id的User对象
     * 
     * @param id
     *            User对象的id
     * 
     */
    public void removeUser(Integer id) ;

    /**
     * 查询指定loginName的User对象
     * 
     * @param loginName
     *            User对象的loginName
     * @return 如果存在，返回相应的User对象，否则返回null
     * 
     */
    public User getUserByLoginName(String loginName) ;

    /**
     * 返回符合查询条件的User对象个数
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自UserQueryCondition类的预定义常量
     * @return 符合查询条件的User对象个数
     * 
     */
    public int getUserListCount(Map conditions) ;

    /**
     * 返回符合查询条件的User对象列表
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自UserQueryCondition类的预定义常量
     * @param pageNo
     *            第几页，以pageSize为页的大小，pageSize为-1时忽略该参数
     * @param pageSize
     *            页的大小，-1表示不分页
     * @param order
     *            排序条件，null表示不排序
     * @param descend
     *            false表示升序，true表示降序
     * @return 符合查询条件的User对象列表
     * 
     */
    public List getUserList(Map conditions, int pageNo, int pageSize, UserQueryOrder order, boolean descend) ;

    /**
     * 返回指定User对象的UserSite列表
     * 
     * @param user
     *            User对象
     * @return 包含UserSite对象的列表，其中每个UserSite对象的user属性为指定的User对象
     * 
     */
    public List getUserSiteListByUser(User user) ;

    /**
     * 返回指定User对象的UserDepartment列表
     * 
     * @param user
     *            User对象
     * @return 包含UserDepartment对象的列表，其中每个UserDepartment对象的user属性为指定的User对象
     * 
     */
    public List getUserDepartmentListByUser(User user) ;

    /**
     * 返回指定User对象在指定Site的UserDepartment列表
     * 
     * @param user
     *            User对象
     * @param site
     *            Site对象
     * @return 包含UserDepartment对象的列表，其中每个UserDepartment对象的user属性为指定的User对象，department属性的site属性为指定的Site
     * 
     */
    public List getUserDepartmentListByUserAndSite(User user, Site site) ;

    /**
     * 从数据库获取指定的UserSite对象
     * 
     * @param userId
     *            要获取的UserSite对象的user属性的id
     * @param siteId
     *            要获取的UserSite对象的site属性的id
     * @return 返回指定的UserSite对象
     * 
     */
    public UserSite getUserSite(Integer userId, Integer siteId) ;

    /**
     * 新增UserSite对象到数据库
     * 
     * @param us
     *            要新增的UserSite对象
     * @return 保存后的UserSite对象
     * 
     */
    public UserSite saveUserSite(UserSite us) ;

    /**
     * 更新UserSite对象到数据库
     * 
     * @param us
     *            要更新的UserSite对象
     * @return 保存后的UserSite对象
     * 
     */
    public UserSite updateUserSite(UserSite us) ;

    /**
     * 从数据库删除指定的UserSite对象
     * 
     * @param us
     *            要删除的UserSite对象
     * 
     */
    public void removeUserSite(UserSite us) ;

    /**
     * 从数据库获取指定的UserDepartment对象
     * 
     * @param userId
     *            要获取的UserDepartment对象的user属性的id
     * @param departmentId
     *            要获取的UserDepartment对象的department属性的id
     * @return 返回指定的UserDepartment对象
     * 
     */
    public UserDepartment getUserDepartment(Integer userId, Integer departmentId) ;

    /**
     * 新增UserDepartment对象到数据库
     * 
     * @param ud
     *            要新增的UserDepartment对象
     * @return 保存后的UserDepartment对象
     * 
     */
    public UserDepartment saveUserDepartment(UserDepartment ud) ;

    /**
     * 更新UserDepartment对象到数据库
     * 
     * @param ud
     *            要更新的UserDepartment对象
     * @return 保存后的UserDepartment对象
     * 
     */
    public UserDepartment updateUserDepartment(UserDepartment ud) ;

    /**
     * 从数据库删除指定的UserDepartment对象
     * 
     * @param ud
     *            要删除的UserDepartment对象
     * 
     */
    public void removeUserDepartment(UserDepartment ud) ;

    /**
     * 从数据库取得UserRole对象列表，其中每个UserRole对象的user属性等于指定的User对象
     * 
     * @param u
     *            指定的User对象
     * @return 返回UserRole对象列表
     * 
     */
    public List getUserRoleListByUser(User user) ;

    /**
     * 从数据库取得UserRole对象列表，其中每个UserRole对象的user属性等于指定的User对象，且role属性包含指定的Function对象
     * 
     * @param user
     *            指定的User对象
     * @param function
     *            指定的Function对象
     * @return 返回UserRole对象列表
     * 
     */
    public List getUserRoleListByUserAndFunction(User user, Function function) ;

    /**
     * 从数据库取得指定id的UserRole对象
     * 
     * @param id
     *            UserRole对象的id
     * @return 返回指定的UserRole对象
     * 
     */
    public UserRole getUserRole(Integer id) ;

    /**
     * 保存指定的UserRole对象到数据库
     * 
     * @param ur
     *            要保存的UserRole对象
     * @return 保存后的UserRole对象
     * 
     */
    public UserRole saveUserRole(UserRole ur) ;

    /**
     * 从数据库删除指定的UserRole对象
     * 
     * @param ur
     *            要删除的UserRole对象
     * 
     */
    public void removeUserRole(UserRole ur) ;

    /**
     * 取得指定User被授权使用指定Function的所有Site列表，只有被授予了Site级权限的Site才会出现在此列表中。
     * 
     * @param user
     *            指定的User对象
     * @param function
     *            指定的Function对象
     * @return Site对象的列表
     * 
     */
    public List getGrantedSiteList(User user, Function function) ;
    
    
    
    /**
     * 取得用指定Function的所有授权User列表，只有被授予了Global级权限的User才会出现在此列表中。
     * 
     * @param function
     *            指定的Function对象
     * @return User对象的列表
     */
    public List getEnabledUserList(Function function);

    /**
     * 查询指定User对指定的Site和Function是否有Site级权限。如果有表示该User在此Function内可访问的Department不受限制
     * 
     * @param site
     *            指定的Site对象
     * @param user
     *            指定的User对象
     * @param function
     *            指定的Function对象
     * @return true表示有Site级权限，false表示没有
     * 
     */
    public boolean hasSitePower(Site site, User user, Function function) ;

    /**
     * 查询指定User对指定的Department和Function是否有权限。
     * 
     * @param department
     *            指定的Department对象
     * @param user
     *            指定的User对象
     * @param function
     *            指定的Function对象
     * @return true表示有权限，false表示没有
     * 
     */
    public boolean hasDepartmentPower(Department department, User user, Function function) ;

    /**
     * 取得指定User被授权使用指定Function的所有Site列表，列表中的每个Site对象的departments集合中已经填充了被授权的Department对象。
     * 注：为了显示需要，和被授权Department对象有关的Parent
     * Department也会在集合中，而不管是否有被授权。因此，该集合中的Department对象实际是否有被授权，应通过其granted属性获得，true表示有授权，false表示没有。
     * 
     * @param user
     *            指定的User对象
     * @param function
     *            指定的Function对象
     * @return Site对象的列表
     * 
     */
    public List getGrantedSiteDeparmentList(User currentUser, Function function) ;

    /**
     * 查询指定的User对指定的Function是否有Global级权限。如果有表示该User在此Function内可访问的Site和Department不受限制
     * 
     * @param user
     *            指定的User对象
     * @param function
     *            指定的Function对象
     * @return true表示有权限，false表示没有
     * 
     */
    public boolean hasGlobalPower(User user, Function function) ;

    /**
     * 取得指定User可以使用的Menu对象集合
     * 
     * @param user
     *            指定的User对象
     * @return Menu对象的集合
     * 
     */
    public Set getGrantedMenuSet(User user) ;

    /**
     * 返回指定User对象的UserSite列表，每个UserSite对象的site属性的状态要求是可用，且departments集合由getEnabledUserDepartmentList(User
     * user, Site site)的结果填充
     * 
     * User对象
     * 
     * @return 包含UserSite对象的列表，其中每个UserSite对象的user属性为指定的User对象，且site属性的状态为可用
     * 
     */
    public List getEnabledSiteDepartmentListOfUser(User user) ;

    /**
     * 返回指定User对象的UserSite列表，每个UserSite对象的site属性的状态要求是可用
     * 
     * @param user
     *            User对象
     * @return 包含UserSite对象的列表，其中每个UserSite对象的user属性为指定的User对象，且site属性的状态为可用
     * 
     */
    public List getEnabledUserSiteList(User user) ;

    /**
     * 返回指定User对象的UserSite列表，每个UserSite对象的site属性的状态要求是可用,并且每个UserSite对象填充了可用的UserDepartment列表,UserSite的Site对象填充了Enable的ExpenseCategory
     * 
     * @param user
     *            User对象
     * @return 包含UserSite对象的列表，其中每个UserSite对象的user属性为指定的User对象，site属性的状态为可用，且填充了可用的UserDepartment列表,UserSite的Site对象填充了Enable的ExpenseCategory
     * 
     */
    public List getEnabledUserSiteListWithDepartmentsAndExpenseCategory(User user);
    
    /**
     * 返回指定User对象在指定Site的UserDepartment列表，每个UserDepartment对象的department属性的状态要求是可用
     * 
     * @param user
     *            User对象
     * @param site
     *            Site对象
     * @return 包含UserDepartment对象的列表，其中每个UserDepartment对象的user属性为指定的User对象，department属性的site属性为指定的Site，且状态为可用
     * 
     */
    public List getEnabledUserDepartmentList(User user, Site site) ;

    /**
     * 取得指定User被授权使用指定Function的所有Site列表。
     * 
     * @param user
     *            指定的User对象
     * @param function
     *            指定的Function对象
     * @return Site对象的列表
     * 
     */
    public List getSiteOfGrantedSiteDeparmentList(User user, Function function) ;

    /**
     * 取得指定User在指定Site被授权使用指定Function的所有Department列表。
     * 注：为了显示需要，和被授权Department对象有关的Parent
     * Department也会在列表中，而不管是否有被授权。因此，该列表中的Department对象实际是否有被授权，应通过其granted属性获得，true表示有授权，false表示没有。
     * 
     * @param user
     *            指定的User对象
     * @param site
     *            指定的Site对象
     * @param function
     *            指定的Function对象
     * @return Site对象的列表
     * 
     */
    public List getGrantedDepartmentListOfSite(User user, Site site, Function function) ;

    /**
     * 获取指定Department中所有状态为可用的User的列表
     * 
     * @param department
     *            Department对象
     * @return User对象的列表
     * 
     */
    public List getEnabledUserListOfDepartment(Department department) ;

    /**
     * 查询指定operator对指定的user和Function是否有权限。
     * @param user 
     * @param operator
     * @param function
     * @return true表示有权限，false表示没有
     */
    public boolean hasUserPower(User user, User currentUser, Function function) ;

    /**
     * 取得对指定function,site有权限的有效用户列表
     * @param f
     * @param site
     * @return
     */
    public List getEnabledUserList(Function f, Site site);
    public List getEnabledUserList(Function f, Department dep);

    /**
     * get Enabled SiteDepartment Tree Of User
     * @param currentUser
     * @return
     */
    public List getEnabledSiteDepartmentTreeOfUser(User currentUser);

    /**
     * get Enabled Site List Of User
     * @param currentUser
     * @return
     */
    public List getEnabledSiteListOfUser(User currentUser);

    

    public void fillUserRole(List userList);

    public List getUserRoleList(Map conditions, UserQueryOrder order, boolean isDesc);
}
