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
import com.aof.model.admin.Role;
import com.aof.model.admin.RoleFunction;
import com.aof.model.admin.query.RoleQueryOrder;

/**
 * 定义操作Role的接口
 * 
 * @author nicebean
 * @version 1.0 (2005-11-11)
 */
public interface RoleDAO extends DAO {

    /**
     * 从数据库取得指定id的Role对象
     * 
     * @param id
     *            Role对象的id
     * @return 返回指定的Role对象
     */
    public Role getRole(Integer id);

    /**
     * 保存Role对象到数据库
     * 
     * @param role
     *            要保存的Role对象
     * @return 返回保存后的Role对象
     */
    public Role saveRole(Role role);

    /**
     * 从数据库删除指定id的Role对象
     * 
     * @param id
     *            Role对象的id
     */
    public void removeRole(Integer id);

    /**
     * 返回符合查询条件的Role对象个数
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自RoleQueryCondition类的预定义常量
     * @return 符合查询条件的Role对象个数
     */
    public int getRoleListCount(Map conditions);

    /**
     * 返回符合查询条件的Role对象列表
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自RoleQueryCondition类的预定义常量
     * @param pageNo
     *            第几页，以pageSize为页的大小，pageSize为-1时忽略该参数
     * @param pageSize
     *            页的大小，-1表示不分页
     * @param order
     *            排序条件，null表示不排序
     * @param descend
     *            false表示升序，true表示降序
     * @return 符合查询条件的Role对象列表
     */
    public List getRoleList(Map conditions, int pageNo, int pageSize, RoleQueryOrder order, boolean descend);

    /**
     * 取得分配给指定Role对象的Function对象列表
     * 
     * @param role
     *            指定的Role对象
     * 
     * @return 返回分配给该Role对象的Function对象列表
     */
    public List getFunctionListByRole(Role role);

    /**
     * 保存RoleFunction对象到数据库
     * 
     * @param roleFunction
     *            要保存的RoleFunction对象
     * @return 返回保存到数据库的RoleFunction对象
     */
    public RoleFunction saveRoleFunction(RoleFunction roleFunction);

    /**
     * 从数据库删除指定的RoleFunction对象
     * 
     * @param roleFunction
     *            要删除的RoleFunction对象
     */
    public void removeRoleFunction(RoleFunction roleFunction);
}
