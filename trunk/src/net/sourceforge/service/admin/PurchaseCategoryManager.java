/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.service.admin;

import java.util.List;
import java.util.Map;

import net.sourceforge.model.admin.PurchaseCategory;
import net.sourceforge.model.admin.Site;
import net.sourceforge.model.admin.query.PurchaseCategoryQueryOrder;

/**
 * 定义操作PurchaseCategory的接口
 * 
 * @author ych
 * @version 1.0 (Nov 13, 2005)
 */
public interface PurchaseCategoryManager {

    /**
     * 从数据库取得指定id的PurchaseCategory
     * 
     * @param id
     *            PurchaseCategory的id
     * @return 返回指定的PurchaseCategory
     * 
     */
    public PurchaseCategory getPurchaseCategory(Integer id);

    /**
     * 插入指定的PurchaseCategory对象到数据库
     * 
     * @param purchaseCategory
     *            要保存的PurchaseCategory对象
     * @return 保存后的PurchaseCategory对象
     * 
     */
    public PurchaseCategory insertPurchaseCategory(PurchaseCategory purchaseCategory);

    /**
     * 更新指定的PurchaseCategory对象到数据库
     * 
     * @param purchaseCategory
     *            要更新的PurchaseCategory对象
     * @return 更新后的PurchaseCategory对象
     * 
     */
    public PurchaseCategory updatePurchaseCategory(PurchaseCategory purchaseCategory);

    /**
     * 返回符合查询条件的PurchaseCategory对象个数
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自PurchaseCategoryQueryCondition类的预定义常量
     * @return 符合查询条件的PurchaseCategory对象个数
     * 
     */
    public int getPurchaseCategoryListCount(Map condtions);

    /**
     * 返回符合查询条件的PurchaseCategory对象列表
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自PurchaseCategoryQueryCondition类的预定义常量
     * @param pageNo
     *            第几页，以pageSize为页的大小，pageSize为-1时忽略该参数
     * @param pageSize
     *            页的大小，-1表示不分页
     * @param order
     *            排序条件，null表示不排序
     * @param descend
     *            false表示升序，true表示降序
     * @return 符合查询条件的PurchaseCategory对象列表
     * 
     */
    public List getPurchaseCategoryList(Map condtions, int pageNo, int pageSize, PurchaseCategoryQueryOrder order, boolean descend);

    /**
     * 根据site取得enabled的PurchaseCategory列表
     * 
     * @param site
     *            PurchaseCategory的site
     * @return enabled的PurchaseCategory列表
     * 
     */
    public List getEnabledPurchaseCategoryOfSite(Site site);

    /**
     * 根据site取得enabled的PurchaseCategory列表包括Global级的PurchaseCategory
     * 
     * @param site
     *            PurchaseCategory的site
     * @return enabled的PurchaseCategory列表
     * 
     */
    public List getEnabledPurchaseCategoryOfSiteIncludeGlobal(Site site);
        
    /**
     * 根据site取得enabled的PurchaseCategory和PurchaseSubCategory列表
     * 
     * @param site
     *            PurchaseCategory的site
     * @return enabled的PurchaseCategory和PurchaseSubCategory列表
     * 
     */
    public List getEnabledPurchaseCategorySubCategoryOfSite(Site site);
    
    /**
     * 根据site取得enabled的PurchaseCategory和PurchaseSubCategory列表包括Global级的PurchaseCategory
     * 
     * @param site
     *            PurchaseCategory的site
     * @return enabled的PurchaseCategory和PurchaseSubCategory列表
     * 
     */
    public List getEnabledPurchaseCategorySubCategoryOfSiteIncludeGlobal(Site site);
    

    /**
     * 取得所有enabled的Global级PurchaseCategory和PurchaseSubCategory列表
     * 
     * @return 所有enabled的PurchaseCategory和PurchaseSubCategory列表
     * 
     */
    public List getEnabledPurchaseCategorySubCategoryOfGlobal();

    /**
     * 为所给的Site List中的每个Site填充enabled的PurchaseCategory和PurchaseSubCategory列表
     * 
     * @param siteList
     *            Site对象的列表
     */
    public void fillEnabledPurchaseCategorySubCategoryForSiteList(List siteList);

}
