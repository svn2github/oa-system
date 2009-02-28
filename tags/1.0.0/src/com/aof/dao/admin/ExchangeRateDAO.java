/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.dao.admin;

import java.util.List;
import java.util.Map;

import com.aof.model.admin.Currency;
import com.aof.model.admin.ExchangeRate;
import com.aof.model.admin.Site;
import com.aof.model.admin.query.ExchangeRateQueryOrder;

/**
 * 定义操作ExchangeRate的接口
 * @author ych
 * @version 1.0 (Nov 13, 2005)
 */
public interface ExchangeRateDAO {

    /**
     * 从数据库取得指定id的ExchangeRate
     * 
     * @param id
     *            ExchangeRate的id
     * @return 返回指定的ExchangeRate
     */
    public ExchangeRate getExchangeRate(Integer id);

    /**
     * 从数据库取得指定currency和site的ExchangeRate
     * 
     * @param currency
     *            ExchangeRate的currency
     * @param site
     *            ExchangeRate的site
     * 
     * @return 返回指定的ExchangeRate
     */
    public ExchangeRate getExchangeRate(Currency currency,Site site);

    /**
     * 返回符合查询条件的ExchangeRate对象个数
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自ExchangeRateQueryCondition类的预定义常量
     * @return 符合查询条件的Currency对象个数
     */
    public int getExchangeRateListCount(Map conditions);

    /**
     * 返回符合查询条件的ExchangeRate对象列表
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自ExchangeRateQueryCondition类的预定义常量
     * @param pageNo
     *            第几页，以pageSize为页的大小，pageSize为-1时忽略该参数
     * @param pageSize
     *            页的大小，-1表示不分页
     * @param order
     *            排序条件，null表示不排序
     * @param descend
     *            false表示升序，true表示降序
     * @return 符合查询条件的ExchangeRate对象列表
     */
    public List getExchangeRateList(Map conditions, int pageNo, int pageSize, ExchangeRateQueryOrder order, boolean descend);

    /**
     * 插入指定的ExchangeRate对象到数据库
     * 
     * @param exchangeRate
     *            要保存的ExchangeRate对象
     * @return 保存后的ExchangeRate对象
     */
    //public ExchangeRate insertExchangeRate(ExchangeRate exchangeRate);

    /**
     * 更新指定的ExchangeRate对象到数据库
     * 
     * @param exchangeRate
     *            要更新的ExchangeRate对象
     * @return 更新后的ExchangeRate对象
     */
    //public ExchangeRate updateExchangeRate(ExchangeRate exchangeRate);

    /**
     * 保存指定的ExchangeRate对象到数据库
     * 
     * @param exchangeRate
     *            要更新的ExchangeRate对象
     * @return 更新后的ExchangeRate对象
     */
    public ExchangeRate saveExchangeRate(ExchangeRate exchangeRate);
}
