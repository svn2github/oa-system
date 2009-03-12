/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.service.admin;

import java.util.List;
import java.util.Map;

import net.sourceforge.model.admin.Currency;
import net.sourceforge.model.admin.PurchaseSubCategory;
import net.sourceforge.model.admin.Site;
import net.sourceforge.model.admin.Supplier;
import net.sourceforge.model.admin.query.SupplierQueryOrder;

/**
 * 定义操作Supplier的接口
 * 
 * @author ych
 * @version 1.0 (Nov 13, 2005)
 */
public interface SupplierManager {

    /**
     * 从数据库取得指定id的Supplier
     * 
     * @param id
     *            Supplier的id
     * @return 返回指定的Supplier
     * 
     */
    public Supplier getSupplier(Integer id);

    /**
     * 插入指定的Supplier对象到数据库
     * 
     * @param supplier
     *            要保存的Supplier对象
     * @return 保存后的Supplier对象
     * 
     */
    public Supplier insertSupplier(Supplier supplier);

    /**
     * 更新指定的Supplier对象到数据库
     * 
     * @param supplier
     *            要更新的Supplier对象
     * @return 更新后的Supplier对象
     * 
     */
    public Supplier updateSupplier(Supplier supplier);

    /**
     * 返回符合查询条件的Supplier对象个数
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自SupplierQueryCondition类的预定义常量
     * @return 符合查询条件的Supplier对象个数
     * 
     */
    public int getSupplierListCount(Map condtions);

    /**
     * 返回符合查询条件的Supplier对象列表
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自SupplierQueryCondition类的预定义常量
     * @param pageNo
     *            第几页，以pageSize为页的大小，pageSize为-1时忽略该参数
     * @param pageSize
     *            页的大小，-1表示不分页
     * @param order
     *            排序条件，null表示不排序
     * @param descend
     *            false表示升序，true表示降序
     * @return 符合查询条件的Supplier对象列表
     * 
     */
    public List getSupplierList(Map condtions, int pageNo, int pageSize, SupplierQueryOrder order, boolean descend);

    /**
     * 确认指定的Supplier
     * 
     * @param supplier
     *            所要确认的Supplier
     * 
     */
    public void confirmSupplier(Supplier supplier);

    /**
     * 取消确认指定的Supplier
     * 
     * @param supplier
     *            所要取消确认的Supplier
     * 
     */
    public void cancelSupplier(Supplier supplier);

    /**
     * 对指定id的Supplier发出提升请求
     * 
     * @param id
     *            所要发出提升请求的Supplier的id
     * @param promoteMessage
     *            提升请求附言
     * 
     */
    public Supplier requestPromote(Integer id, String promoteMessage);

    /**
     * 响应指定id的Supplier提升请求
     * 
     * @param id
     *            响应Supplier的id
     * 
     */
    public Supplier responsePromote(Integer id);

    /**
     * 提升指定的Supplier
     * 
     * @param supplier
     *            所要提升的Supplier
     * 
     */
    public void promoteCreate(Supplier supplier);

    /**
     * 取消提升指定的Supplier的操作
     * 
     * @param supplier
     *            所要取消提升的Supplier
     * 
     */
    public void promoteDelete(Supplier supplier);

    /**
     * 得到某site的所有供应商
     * 
     * @param site
     * @return
     */
    public List getEnabledAirTicketSuppliersForSiteAndIncludeGlobal(Site site);
    
    public List getEnabledNonAirTicketSuppliersForSiteAndIncludeGlobal(Site site);
    
    /**
     * 返回是否数据库中已有指定Code和Site的数据
     * 
     * @param code
     *            Supplier的Code
     * @param site
     *            Supplier的Site
     * @return
     */
    public boolean isCodeUsed(String code, Site site);

    /**
     * 为指定的PurchaseSubCategory返回适合于采购的Supplier列表，列表中的Supplier对象的items集合已经填充了合适的SupplierItem对象
     * 适合于采购的是指: 1、Supplier和SupplierItem的状态都为enabled;
     * 2、SupplierItem的purchaseSubCategory属性为指定的PurchaseSubCategory;
     * 3、SupplierItem的currency必须在指定的exchangeRateList中有
     * 
     * @param site
     *            指定的Site对象
     * @param psc
     *            指定的PurchaseSubCategory对象
     * @param exchangeRateList
     *            指定的ExchangeRate对象的列表
     * @return Supplier列表
     */
    public List getSuitableSupplierListForPurchase(Site site, PurchaseSubCategory psc, List exchangeRateList);
    
    
    /**
     * 为指定的Supplier,PurchaseSubCategory返回适合于采购的SupplierItem列表
     * 适合于采购的是指: 1、SupplierItem的状态都为enabled;
     * 2、SupplierItem的purchaseSubCategory属性为指定的PurchaseSubCategory;
     * 3、SupplierItem的currency必须是currency
     * 
     * @param supplier
     * @param psc
     * @param currency
     * @return
     */
    public List getSuitableSupplierItemListForPurchase(Supplier supplier,PurchaseSubCategory psc,Currency currency);

}
