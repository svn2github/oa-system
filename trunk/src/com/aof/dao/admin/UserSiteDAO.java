/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.dao.admin;

import java.util.List;
import java.util.Map;

import com.aof.dao.DAO;
import com.aof.model.admin.UserSite;
import com.aof.model.admin.query.UserSiteQueryOrder;

/**
 * 定义UserSite应该提供的方法
 * @author nicebean
 * @version 1.0 (2005-11-12)
 */
public interface UserSiteDAO extends DAO {

    /**
     * 从数据库获取指定的UserSite对象
     * 
     * @param userId
     *            要获取的UserSite对象的user属性的id
     * @param siteId
     *            要获取的UserSite对象的site属性的id
     * @return 返回指定的UserSite对象
     */
    public UserSite getUserSite(Integer userId, Integer siteId);

    /**
     * 新增UserSite对象到数据库
     * 
     * @param us
     *            要新增的UserSite对象
     * @return 保存后的UserSite对象
     */
    public UserSite saveUserSite(UserSite us);
    
    /**
     * 更新UserSite对象到数据库
     * 
     * @param us
     *            要更新的UserSite对象
     * @return 保存后的UserSite对象
     */
    public UserSite updateUserSite(UserSite us);
    
    /**
     * 从数据库删除指定的UserSite对象
     * 
     * @param us
     *            要删除的UserSite对象
     */
    public void removeUserSite(UserSite us);

    /**
     * 返回符合查询条件的UserSite对象个数
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自UserSiteQueryCondition类的预定义常量
     * @return 符合查询条件的UserSite对象个数
     */
    public int getUserSiteListCount(Map conditions);
    
    /**
     * 返回符合查询条件的UserSite对象列表
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自UserSiteQueryCondition类的预定义常量
     * @param pageNo
     *            第几页，以pageSize为页的大小，pageSize为-1时忽略该参数
     * @param pageSize
     *            页的大小，-1表示不分页
     * @param order
     *            排序条件，null表示不排序
     * @param descend
     *            false表示升序，true表示降序
     * @return 符合查询条件的UserSite对象列表
     */
    public List getUserSiteList(Map conditions, int pageNo, int pageSize, UserSiteQueryOrder order, boolean descend);
    
}
