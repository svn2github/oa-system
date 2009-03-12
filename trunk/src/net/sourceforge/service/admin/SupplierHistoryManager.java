/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.service.admin;

import java.util.List;
import java.util.Map;

import net.sourceforge.model.admin.SupplierHistory;
import net.sourceforge.model.admin.query.SupplierHistoryQueryOrder;

/**
 * 定义操作SupplierHistory的接口
 * @author ych
 * @version 1.0 (Nov 13, 2005)
 */
public interface SupplierHistoryManager {

    /**
     * 从数据库取得指定id的SupplierHistory
     * 
     * @param id
     *            SupplierHistory的id
     * @return 返回指定的SupplierHistory
     * 
     */
    public SupplierHistory getSupplierHistory(Integer id) ;

    /**
     * 插入指定的SupplierHistory对象到数据库
     * 
     * @param supplierHistory
     *            要保存的SupplierHistory对象
     * @return 保存后的SupplierHistory对象
     * 
     */
    public SupplierHistory insertSupplierHistory(SupplierHistory supplierHistory) ;
    
    /**
     * 更新指定的SupplierHistory对象到数据库
     * 
     * @param supplierHistory
     *            要更新的SupplierHistory对象
     * @return 更新后的SupplierHistory对象
     * 
     */
    public SupplierHistory updateSupplierHistory(SupplierHistory supplierHistory) ;

    /**
     * 返回符合查询条件的SupplierHistory对象个数
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自SupplierHistoryQueryCondition类的预定义常量
     * @return 符合查询条件的SupplierHistory对象个数
     * 
     */
    public int getSupplierHistoryListCount(Map condtions) ;

    /**
     * 返回符合查询条件的SupplierHistory对象列表
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自SupplierHistoryQueryCondition类的预定义常量
     * @param pageNo
     *            第几页，以pageSize为页的大小，pageSize为-1时忽略该参数
     * @param pageSize
     *            页的大小，-1表示不分页
     * @param order
     *            排序条件，null表示不排序
     * @param descend
     *            false表示升序，true表示降序
     * @return 符合查询条件的SupplierHistory对象列表
     * 
     */
    public List getSupplierHistoryList(Map condtions, int pageNo, int pageSize, SupplierHistoryQueryOrder order, boolean descend) ;
}
