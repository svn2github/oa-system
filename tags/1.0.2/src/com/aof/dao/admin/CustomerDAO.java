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
import com.aof.model.admin.Customer;
import com.aof.model.admin.query.CustomerQueryOrder;

/**
 * 定义操作Customer的接口
 * 
 * @author nicebean
 * @version 1.0 (2005-11-23)
 */
public interface CustomerDAO extends DAO {

    /**
     * 从数据库取得指定id的Customer对象
     * 
     * @param code
     *            Customer对象的code
     * @return 返回指定的Customer对象
     */
    public Customer getCustomer(String code);

    /**
     * 返回符合查询条件的Customer对象个数
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自CustomerQueryCondition类的预定义常量
     * @return 符合查询条件的Customer对象个数
     */
    public int getCustomerListCount(Map conditions);

    /**
     * 返回符合查询条件的Customer对象列表
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自CustomerQueryCondition类的预定义常量
     * @param pageNo
     *            第几页，以pageSize为页的大小，pageSize为-1时忽略该参数
     * @param pageSize
     *            页的大小，-1表示不分页
     * @param order
     *            排序条件，null表示不排序
     * @param descend
     *            false表示升序，true表示降序
     * @return 符合查询条件的Customer对象列表
     */
    public List getCustomerList(Map conditions, int pageNo, int pageSize, CustomerQueryOrder order, boolean descend);

}
