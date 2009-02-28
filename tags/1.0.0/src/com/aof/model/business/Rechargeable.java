/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.business;

import com.aof.model.metadata.YesNo;

/**
 * 支持Recharge的对象应该实现该接口
 * 
 * @author nicebean
 * @version 1.0 (2005-11-24)
 */
public interface Rechargeable {

    /**
     * 创建新的Recharge对象。比如，对Expense，应创建ExpenseRecharge对象，并调用setExpense
     * 
     * @return BaseRecharge对象对应子类实例
     */
    public BaseRecharge createNewRechargeObj();
    
    /**
     * 返回对象是否有recharge
     * @return YesNo.YES: 是; YesNo.NO: 否
     */
    public YesNo getIsRecharge();
    
    /**
     * 设置对象是否有recharge
     * @param canRecharge YesNo对象
     */
    public void setIsRecharge(YesNo isRecharge);
}
