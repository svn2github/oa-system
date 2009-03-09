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
import com.aof.model.admin.Menu;
import com.aof.model.admin.query.MenuQueryOrder;

/**
 * 定义操作Menu的接口
 * 
 * @author nicebean
 * @version 1.0 (2005-11-11)
 */
public interface MenuDAO extends DAO {

    /**
     * 从数据库取得指定id的Menu对象
     * 
     * @param id
     *            Menu对象的id
     * @return 返回指定的Menu对象
     */
    public Menu getMenu(Integer id);

    /**
     * 保存Menu对象到数据库
     * 
     * @param menu
     *            要保存的Menu对象
     * @return 返回保存后的Menu对象
     */
    public Menu saveMenu(Menu menu);

    /**
     * 从数据库删除指定id的Menu对象
     * 
     * @param id
     *            Menu对象的id
     */
    public void removeMenu(Integer id);

    /**
     * 返回符合查询条件的Menu对象个数
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自MenuQueryCondition类的预定义常量
     * @return 符合查询条件的Menu对象个数
     */
    public int getMenuListCount(Map conditions);

    /**
     * 返回符合查询条件的Menu对象列表
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自MenuQueryCondition类的预定义常量
     * @param pageNo
     *            第几页，以pageSize为页的大小，pageSize为-1时忽略该参数
     * @param pageSize
     *            页的大小，-1表示不分页
     * @param order
     *            排序条件，null表示不排序
     * @param descend
     *            false表示升序，true表示降序
     * @return 符合查询条件的Menu对象列表
     */
    public List getMenuList(Map conditions, int pageNo, int pageSize, MenuQueryOrder order, boolean descend);

}
