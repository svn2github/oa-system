/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.business.po;

/**
 * 该类是PurchaseOrder的Approve查询显示列表对象
 * 
 * @author nicebean
 * @version 1.0 (2005-12-22)
 */
public class PurchaseOrderApproveRequestListView {

    private PurchaseOrder purchaseOrder;

    private PurchaseOrderApproveRequest purchaseOrderApproveRequest;

    /**
     * @return Returns the purchaseOrderApproveRequest.
     */
    public PurchaseOrderApproveRequest getPurchaseOrderApproveRequest() {
        return purchaseOrderApproveRequest;
    }

    /**
     * @param purchaseOrderApproveRequest
     *            The purchaseOrderApproveRequest to set.
     */
    public void setPurchaseOrderApproveRequest(PurchaseOrderApproveRequest purchaseOrderApproveRequest) {
        this.purchaseOrderApproveRequest = purchaseOrderApproveRequest;
    }

    /**
     * @return Returns the purchaseOrder.
     */
    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    /**
     * @param purchaseOrder
     *            The purchaseOrder to set.
     */
    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

}
