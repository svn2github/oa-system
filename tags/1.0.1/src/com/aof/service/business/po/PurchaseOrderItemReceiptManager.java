/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.service.business.po;

import java.util.List;
import java.util.Map;

import com.aof.model.admin.User;
import com.aof.model.business.po.PurchaseOrderItem;
import com.aof.model.business.po.PurchaseOrderItemReceipt;
import com.aof.model.business.po.query.PurchaseOrderItemReceiptQueryOrder;

/**
 * service manager for domain model PurchaseOrderItemReceipt
 * @author shilei
 * @version 1.0 (Dec 27, 2005)
 */
public interface PurchaseOrderItemReceiptManager {

    /**
     * get PurchaseOrder Item Receipt by id
     * @param id
     * @return
     */
    public PurchaseOrderItemReceipt getPurchaseOrderItemReceipt(Integer id) throws Exception;

    /**
     * insert PurchaseOrderItemReceipt
     * @param purchaseOrderItemReceipt
     * @return
     */
    public PurchaseOrderItemReceipt insertPurchaseOrderItemReceipt(PurchaseOrderItemReceipt purchaseOrderItemReceipt,User user) throws Exception;
    
    /**
     * update PurchaseOrderItemReceipt
     * @param purchaseOrderItemReceipt
     * @return
     * @throws Exception
     */
    public PurchaseOrderItemReceipt updatePurchaseOrderItemReceipt(PurchaseOrderItemReceipt oldPoir,PurchaseOrderItemReceipt purchaseOrderItemReceipt,User user) throws Exception;

    /**
     * get PurchaseOrderItemReceipt List Count
     * @param condtions
     * @return
     * @throws Exception
     */
    public int getPurchaseOrderItemReceiptListCount(Map condtions) throws Exception;

    /**
     * get PurchaseOrder Item Receipt List
     * @param condtions
     * @return
     * @throws Exception
     */
    public List getPurchaseOrderItemReceiptList(Map condtions, int pageNo, int pageSize, PurchaseOrderItemReceiptQueryOrder order, boolean descend) throws Exception;
    
    /**
     * check Qty of PurchaseOrderItemReceipt
     * @param poir
     * @return
     */
    public boolean checkQty(PurchaseOrderItemReceipt poir);

    /**
     * delete PurchaseOrderItemReceipt
     * @param poir
     */
    public void deletePurchaseOrderItemReceipt(PurchaseOrderItemReceipt poir,User user);

    public int getRecevieSum(PurchaseOrderItem poi, User currentUser) throws Exception ;
}
