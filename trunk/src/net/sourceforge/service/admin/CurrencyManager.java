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
import net.sourceforge.model.admin.query.CurrencyQueryOrder;

/**
 * 定义操作Currency的接口
 * @author ych
 * @version 1.0 (Nov 13, 2005)
 */
public interface CurrencyManager {
	
    /**
     * 从数据库取得指定code的Currency
     * 
     * @param code
     *            Currency的code
     * @return 返回指定的Currency
     * 
     */
	public Currency getCurrency(String code) ;
    
    /**
     * 返回符合查询条件的Currency对象个数
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自CurrencyQueryCondition类的预定义常量
     * @return 符合查询条件的Currency对象个数
     * 
     */
	public int getCurrencyListCount(Map conditions) ;

    /**
     * 返回符合查询条件的Currency对象列表
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自CurrencyQueryCondition类的预定义常量
     * @param pageNo
     *            第几页，以pageSize为页的大小，pageSize为-1时忽略该参数
     * @param pageSize
     *            页的大小，-1表示不分页
     * @param order
     *            排序条件，null表示不排序
     * @param descend
     *            false表示升序，true表示降序
     * @return 符合查询条件的Currency对象列表
     * 
     */
    public List getCurrencyList(Map conditions, int pageNo, int pageSize, CurrencyQueryOrder order, boolean descend) ;
    
    /**
     * 插入指定的Currency对象到数据库
     * 
     * @param currency
     *            要保存的Currency对象
     * @return 保存后的Currency对象
     * 
     */
    public Currency insertCurrency(Currency currency) ;
    
    /**
     * 更新指定的Currency对象到数据库
     * 
     * @param currency
     *            要更新的Currency对象
     * @return 更新后的Currency对象
     * 
     */
    public Currency updateCurrency(Currency currency) ;
    
    /**
     * 返回所有enabled的Currency列表
     * @return 所有enabled的Currency列表
     * 
     */
    public List getAllEnabledCurrencyList() ;

    /**
     * 返回所有enabled的Currency列表包括指定code的Currency
     * @param code Currency的code
     * @return 所有enabled的Currency列表包括指定code的Currency
     * 
     */
    public List getAllEnabledCurrencyListAndIncludeThis(String code) ;

}
