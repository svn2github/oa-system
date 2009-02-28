/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.business.pr;

/**
 * 该类是PurchaseRequest的Approve查询显示列表对象
 * 
 * @author nicebean
 * @version 1.0 (2005-12-15)
 */
public class PurchaseRequestApproveRequestListView {

    private PurchaseRequest purchaseRequest;

    private PurchaseRequestApproveRequest purchaseRequestApproveRequest;

    /**
     * @return Returns the purchaseRequestApproveRequest.
     */
    public PurchaseRequestApproveRequest getPurchaseRequestApproveRequest() {
        return purchaseRequestApproveRequest;
    }

    /**
     * @param purchaseRequestApproveRequest
     *            The purchaseRequestApproveRequest to set.
     */
    public void setPurchaseRequestApproveRequest(PurchaseRequestApproveRequest purchaseRequestApproveRequest) {
        this.purchaseRequestApproveRequest = purchaseRequestApproveRequest;
    }

    /**
     * @return Returns the purchaseRequest.
     */
    public PurchaseRequest getPurchaseRequest() {
        return purchaseRequest;
    }

    /**
     * @param purchaseRequest
     *            The purchaseRequest to set.
     */
    public void setPurchaseRequest(PurchaseRequest purchaseRequest) {
        this.purchaseRequest = purchaseRequest;
    }

}
