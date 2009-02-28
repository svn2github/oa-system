/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.dao.admin;

import java.util.List;
import java.util.Map;

import com.aof.model.admin.PurchaseSubCategory;
import com.aof.model.admin.Supplier;
import com.aof.model.admin.SupplierItem;
import com.aof.model.admin.query.SupplierItemQueryOrder;
/**
 * 定义操作SupplierItem的接口
 * @author ych
 * @version 1.0 (Nov 13, 2005)
 */
public interface SupplierItemDAO{
    
    /**
     * 从数据库取得指定id的SupplierItem
     * 
     * @param id
     *            SupplierItem的id
     * @return 返回指定的SupplierItem
     */
	public SupplierItem getSupplierItem(Integer id);
	
    /**
     * 返回符合查询条件的SupplierItem对象个数
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自SupplierItemQueryCondition类的预定义常量
     * @return 符合查询条件的SupplierItem对象个数
     */
	public int getSupplierItemListCount(Map conditions);
	
    /**
     * 返回符合查询条件的SupplierItem对象列表
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自SupplierItemQueryCondition类的预定义常量
     * @param pageNo
     *            第几页，以pageSize为页的大小，pageSize为-1时忽略该参数
     * @param pageSize
     *            页的大小，-1表示不分页
     * @param order
     *            排序条件，null表示不排序
     * @param descend
     *            false表示升序，true表示降序
     * @return 符合查询条件的SupplierItem对象列表
     */
    public List getSupplierItemList(Map conditions, int pageNo, int pageSize, SupplierItemQueryOrder order, boolean descend);

    /**
     * 插入指定的SupplierItem对象到数据库
     * 
     * @param supplierItem
     *            要保存的SupplierItem对象
     * @return 保存后的SupplierItem对象
     */
    public SupplierItem insertSupplierItem(SupplierItem supplierItem);
    
    /**
     * 更新指定的SupplierItem对象到数据库
     * 
     * @param supplierItem
     *            要更新的SupplierItem对象
     * @return 更新后的SupplierItem对象
     */
    public SupplierItem updateSupplierItem(SupplierItem supplierItem);
    
    /**
     * 返回指定supplier的所有SupplierItem列表
     * @param supplier 所要返回SupplierItem的supplier 
     * @return 指定supplier的所有SupplierItem列表
     */
    public List getSupplierItemListBySupplier(Supplier supplier);

    /**
     * 从数据库移除指定id的SupplierItem对象
     * @param id 所要移除的SupplierItem的id
     */
    public void removeSupplierItem(Integer id) ;

    /**
     * 返回指定supplier的SupplierItem列表,其中这些SupplierItem的PurchaseCategory不是Global级的造成不能提升该Supplier
     * 
     * @param supplier
     *            所要返回SupplierItem的supplier
     * @return 指定supplier的所有SupplierItem列表
     */
    public List getSupplierItemListConflictWithPromoteBySupplier(Supplier supplier);

    /**
     * 
     * @param supplierId
     * @param purchaseSubCategoryId
     * @param destPurchaseSubCategory
     */
    public void changePurchaseSubCategoryBySupplierAndPurchaseSubCategory(Integer supplierId, Integer purchaseSubCategoryId, PurchaseSubCategory destPurchaseSubCategory);

}
