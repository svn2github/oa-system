/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.dao.admin;

import java.util.List;
import java.util.Map;

import net.sourceforge.dao.DAO;
import net.sourceforge.model.admin.Department;
import net.sourceforge.model.admin.Function;
import net.sourceforge.model.admin.Site;
import net.sourceforge.model.admin.User;
import net.sourceforge.model.admin.UserRole;
import net.sourceforge.model.admin.query.UserQueryOrder;

/**
 * 定义操作User的接口
 * 
 * @author nicebean
 * @version 1.0 (2005-11-12)
 */
public interface UserDAO extends DAO {

    /**
     * 从数据库取得指定id的User对象
     * 
     * @param id
     *            User对象的id
     * @return 返回指定的User对象
     */
    public User getUser(Integer id);

    /**
     * 保存User对象到数据库
     * 
     * @param user
     *            要保存的User对象
     * @return 保存后的User对象
     */
    public User saveUser(User user);

    /**
     * 从数据库删除指定id的User对象
     * 
     * @param id
     *            User对象的id
     */
    public void removeUser(Integer id);

    /**
     * 返回符合查询条件的User对象个数
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自UserQueryCondition类的预定义常量
     * @return 符合查询条件的User对象个数
     */
    public int getUserListCount(Map conditions);

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
     */
    public List getUserList(Map conditions, int pageNo, int pageSize, UserQueryOrder order, boolean descend);

    /**
     * 从数据库取得UserRole对象列表，其中每个UserRole对象的user属性等于指定的User对象
     * 
     * @param u
     *            指定的User对象
     * @return 返回UserRole对象列表
     */
    public List getUserRoleListByUser(User u);

    /**
     * 从数据库取得UserRole对象列表，其中每个UserRole对象的user属性等于指定的User对象，且role属性包含指定的Function对象
     * 
     * @param user
     *            指定的User对象
     * @param function
     *            指定的Function对象
     * @return 返回UserRole对象列表
     */
    public List getUserRoleListByUserAndFunction(User user, Function function);

    /**
     * 从数据库取得指定id的UserRole对象
     * 
     * @param id
     *            UserRole对象的id
     * @return 返回指定的UserRole对象
     */
    public UserRole getUserRole(Integer id);

    /**
     * 保存指定的UserRole对象到数据库
     * 
     * @param ur
     *            要保存的UserRole对象
     * @return 保存后的UserRole对象
     */
    public UserRole saveUserRole(UserRole ur);

    /**
     * 从数据库删除指定的UserRole对象
     * 
     * @param ur
     *            要删除的UserRole对象
     */
    public void removeUserRole(UserRole ur);

    /**
     * 取得指定User被授权使用指定Function的所有Site列表，只有被授予了Site级权限的Site才会出现在此列表中。
     * 
     * @param user
     *            指定的User对象
     * @param function
     *            指定的Function对象
     * @return Site对象的列表
     */
    public List getGrantedSiteList(User user, Function function);
    
    
    
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
     */
    public boolean hasSitePower(Site site, User user, Function function);

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
     */
    public List getGrantedSiteDeparmentList(User user, Function function);

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
     */
    public boolean hasDepartmentPower(Department department, User user, Function function);

    /**
     * 查询指定的User对指定的Function是否有Global级权限。如果有表示该User在此Function内可访问的Site和Department不受限制
     * 
     * @param user
     *            指定的User对象
     * @param function
     *            指定的Function对象
     * @return true表示有权限，false表示没有
     */
    public boolean hasGlobalPower(User user, Function function);

    /**
     * 取得指定User可以使用的Menu对象列表
     * 
     * @param user
     *            指定的User对象
     * @return Menu对象的列表
     */
    public List getGrantedMenuList(User user);

    /**
     * 取得指定User被授权使用指定Function的所有Site列表。
     * 
     * @param user
     *            指定的User对象
     * @param function
     *            指定的Function对象
     * @return Site对象的列表
     */
    public List getSiteOfGrantedSiteDeparmentList(User user, Function function);

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
     */
    public List getGrantedDeparmentListOfSite(User user, Site site, Function function);

    /**
     * 查询指定operator对指定的user和Function是否有权限。
     * @param user 
     * @param operator
     * @param function
     * @return true表示有权限，false表示没有
     */
    public boolean hasUserPower(User user, User operator, Function function);

    /**
     * 取得指定授权Site的用指定Function的所有授权User列表。
     * 
     * @param grantedSite
     *            指定的Site对象
     * @param function
     *            指定的Function对象
     * @return User对象的列表
     */
    public List getEnabledUserList(Function function, Site grantedSite);
    public List getEnabledUserList(Function function, Department grantedDep);
    
    /**
     * 取得用指定Function的所有授权User列表，只有被授予了Global级权限的User才会出现在此列表中。
     * 
     * @param function
     *            指定的Function对象
     * @return User对象的列表
     */
    public List getEnabledUserList(Function function);



}
