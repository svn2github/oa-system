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
import com.aof.model.admin.UserDepartment;
import com.aof.model.admin.query.UserDepartmentQueryOrder;

/**
 * 定义操作UserDepartment的接口
 * 
 * @author nicebean
 * @version 1.0 (2005-11-12)
 */
public interface UserDepartmentDAO extends DAO {

    /**
     * 从数据库获取指定的UserDepartment对象
     * 
     * @param userId
     *            要获取的UserDepartment对象的user属性的id
     * @param departmentId
     *            要获取的UserDepartment对象的department属性的id
     * @return 返回指定的UserDepartment对象
     */
    public UserDepartment getUserDepartment(Integer userId, Integer departmentId);

    /**
     * 新增UserDepartment对象到数据库
     * 
     * @param ud
     *            要新增的UserDepartment对象
     * @return 保存后的UserDepartment对象
     */
    public UserDepartment saveUserDepartment(UserDepartment ud);

    /**
     * 更新UserDepartment对象到数据库
     * 
     * @param ud
     *            要更新的UserDepartment对象
     * @return 保存后的UserDepartment对象
     */
    public UserDepartment updateUserDepartment(UserDepartment ud);

    /**
     * 从数据库删除指定的UserDepartment对象
     * 
     * @param ud
     *            要删除的UserDepartment对象
     */
    public void removeUserDepartment(UserDepartment ud);

    /**
     * 返回符合查询条件的UserDepartment对象个数
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自UserDepartmentQueryCondition类的预定义常量
     * @return 符合查询条件的UserDepartment对象个数
     */
    public int getUserDepartmentListCount(Map conditions);

    /**
     * 返回符合查询条件的UserDepartment对象列表
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自UserDepartmentQueryCondition类的预定义常量
     * @param pageNo
     *            第几页，以pageSize为页的大小，pageSize为-1时忽略该参数
     * @param pageSize
     *            页的大小，-1表示不分页
     * @param order
     *            排序条件，null表示不排序
     * @param descend
     *            false表示升序，true表示降序
     * @return 符合查询条件的UserDepartment对象列表
     */
    public List getUserDepartmentList(Map conditions, int pageNo, int pageSize, UserDepartmentQueryOrder order, boolean descend);

}
