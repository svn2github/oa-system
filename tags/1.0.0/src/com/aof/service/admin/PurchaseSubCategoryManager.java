/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.service.admin;

import java.util.List;
import java.util.Map;

import com.aof.model.admin.PurchaseCategory;
import com.aof.model.admin.PurchaseSubCategory;
import com.aof.model.admin.query.PurchaseSubCategoryQueryOrder;

/**
 * 定义操作PurchaseSubCategory的接口
 * 
 * @author ych
 * @version 1.0 (Nov 13, 2005)
 */
public interface PurchaseSubCategoryManager {

    /**
     * 从数据库取得指定id的PurchaseSubCategory
     * 
     * @param id
     *            PurchaseSubCategory的id
     * @return 返回指定的PurchaseSubCategory
     * 
     */
    public PurchaseSubCategory getPurchaseSubCategory(Integer id);

    /**
     * 插入指定的PurchaseSubCategory对象到数据库
     * 
     * @param purchaseSubCategory
     *            要保存的PurchaseSubCategory对象
     * @return 保存后的PurchaseSubCategory对象
     * 
     */
    public PurchaseSubCategory insertPurchaseSubCategory(PurchaseSubCategory purchaseSubCategory);

    /**
     * 更新指定的PurchaseSubCategory对象到数据库
     * 
     * @param purchaseSubCategory
     *            要更新的PurchaseSubCategory对象
     * @return 更新后的PurchaseSubCategory对象
     * 
     */
    public PurchaseSubCategory updatePurchaseSubCategory(PurchaseSubCategory purchaseSubCategory);

    /**
     * 返回符合查询条件的PurchaseSubCategory对象个数
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自PurchaseSubCategoryQueryCondition类的预定义常量
     * @return 符合查询条件的PurchaseSubCategory对象个数
     * 
     */
    public int getPurchaseSubCategoryListCount(Map condtions);

    /**
     * 返回符合查询条件的PurchaseSubCategory对象列表
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自PurchaseSubCategoryQueryCondition类的预定义常量
     * @param pageNo
     *            第几页，以pageSize为页的大小，pageSize为-1时忽略该参数
     * @param pageSize
     *            页的大小，-1表示不分页
     * @param order
     *            排序条件，null表示不排序
     * @param descend
     *            false表示升序，true表示降序
     * @return 符合查询条件的PurchaseSubCategory对象列表
     * 
     */
    public List getPurchaseSubCategoryList(Map condtions, int pageNo, int pageSize, PurchaseSubCategoryQueryOrder order, boolean descend);

    /**
     * 返回指定PurchaseCategory的所有enabled的PurchaseSubCategory对象列表
     * 
     * @param category
     *            指定的PurchaseCategory
     * @return 所有enabled的PurchaseSubCategory对象列表
     */
    public List getEnabledPurchaseSubCategoryOfPurchaseCategory(PurchaseCategory category);
}
