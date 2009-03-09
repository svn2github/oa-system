/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.business;


/**
 * 支持采购的对象应该实现该接口
 * 
 * @author nicebean
 * @version 1.0 (2005-12-16)
 */
public interface Purchasable {

    /**
     * 创建新的Purchaser对象。比如，对PurchaseRequest，应创建PurchaseRequestPurchaser对象
     * 
     * @return BasePurchaser对象对应子类实例
     */
    public BasePurchaser createNewPurchaserObj();
    
    /**
     * 返回用于采购该对象的Flow的名称
     * @return Flow的名称
     */
    public String getPurchaseFlowName();
    
}
