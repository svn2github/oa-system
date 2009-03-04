/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.dao.admin;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.aof.model.admin.Currency;
import com.aof.model.admin.GlobalMailReminder;
import com.aof.model.admin.PurchaseSubCategory;
import com.aof.model.admin.Site;
import com.aof.model.admin.Supplier;
import com.aof.model.admin.query.SupplierQueryOrder;

/**
 * 定义操作Supplier的接口
 * 
 * @author ych
 * @version 1.0 (Nov 13, 2005)
 */
public interface SupplierDAO {

    /**
     * 从数据库取得指定id的Supplier
     * 
     * @param id
     *            Supplier的id
     * @return 返回指定的Supplier
     */
    public Supplier getSupplier(Integer id);

    /**
     * 返回符合查询条件的Supplier对象个数
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自SupplierQueryCondition类的预定义常量
     * @return 符合查询条件的Supplier对象个数
     */
    public int getSupplierListCount(Map conditions);

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
     */
    public List getSupplierList(Map conditions, int pageNo, int pageSize, SupplierQueryOrder order, boolean descend);

    /**
     * 插入指定的Supplier对象到数据库
     * 
     * @param supplier
     *            要保存的Supplier对象
     * @return 保存后的Supplier对象
     */
    public Supplier insertSupplier(Supplier supplier);

    /**
     * 更新指定的Supplier对象到数据库
     * 
     * @param supplier
     *            要更新的Supplier对象
     * @return 更新后的Supplier对象
     */
    public Supplier updateSupplier(Supplier supplier);

    /**
     * 返回新增Supplier时所要的code
     * 
     * @return 新增Supplier时所要的code
     */
    public String getLastSupplierCode();

    /**
     * 检查给定的code是否已经使用
     * 
     * @param code
     *            Supplier的Code
     * @param site
     *            Supplier的Site
     * @return true已经使用，false还未使用
     */
    public boolean isCodeUsed(String code, Site site);

    /**
     * 为指定的PurchaseSubCategory返回适合于采购的Supplier列表，列表中的Supplier对象的items集合已经填充了合适的SupplierItem对象
     * 适合于采购的是指: 1、Supplier和SupplierItem的状态都为enabled;
     * 2、SupplierItem的purchaseSubCategory属性为指定的PurchaseSubCategory;
     * 3、SupplierItem的currency必须在site的汇率表中维护有相应的汇率
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
    public List getSuitableSupplierItemListForPurchase(Supplier supplier, PurchaseSubCategory psc, Currency currency);

    /**
     * 获得指定site的需要email提醒的尚未response的supplier列表
     * @param site
     * @param time
     * @param gmr
     * @return
     */
    public List getSupplierMaintainerNotResponsedList(Site site, Date now, GlobalMailReminder gmr);
    
}
