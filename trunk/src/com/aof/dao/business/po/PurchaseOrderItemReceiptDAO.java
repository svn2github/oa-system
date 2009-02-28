/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.dao.business.po;

import java.util.List;
import java.util.Map;

import com.aof.model.business.po.PurchaseOrderItemReceipt;
import com.aof.model.business.po.query.PurchaseOrderItemReceiptQueryOrder;

/**
 * dao interface for domain model PurchaseOrderItemReceipt
 * @author shilei
 * @version 1.0 (Dec 27, 2005)
 */
public interface PurchaseOrderItemReceiptDAO {

    /**
     * get PurchaseOrder Item Receipt by id
     * @param id
     * @return
     */
    public PurchaseOrderItemReceipt getPurchaseOrderItemReceipt(Integer id);

    /**
     * get PurchaseOrder Item Receipt List Count
     * @param conditions
     * @return
     */
    public int getPurchaseOrderItemReceiptListCount(Map conditions);

    /**
     * get Purchase Order Item Receipt List
     * @param conditions
     * @param pageNo
     * @param pageSize
     * @param order
     * @param descend
     * @return
     */
    public List getPurchaseOrderItemReceiptList(Map conditions, int pageNo, int pageSize, PurchaseOrderItemReceiptQueryOrder order, boolean descend);

    /**
     * insert PurchaseOrderItemReceipt
     * @param purchaseOrderItemReceipt
     * @return
     */
    public PurchaseOrderItemReceipt insertPurchaseOrderItemReceipt(PurchaseOrderItemReceipt purchaseOrderItemReceipt);

    /**
     * update PurchaseOrderItemReceipt
     * @param purchaseOrderItemReceipt
     * @return
     */
    public PurchaseOrderItemReceipt updatePurchaseOrderItemReceipt(PurchaseOrderItemReceipt purchaseOrderItemReceipt);

    /**
     * delete PurchaseOrderItemReceipt
     * @param poir
     * @return
     */
    public void deletePurchaseOrderItemReceipt(PurchaseOrderItemReceipt poir);


}
