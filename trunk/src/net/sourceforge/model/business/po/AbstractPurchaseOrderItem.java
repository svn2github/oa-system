/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.business.po;

import java.io.Serializable;

import net.sourceforge.model.business.pr.PurchaseRequestItem;

/**
 * 该类代表po_item表的一行记录
 */
public abstract class AbstractPurchaseOrderItem extends BasePurchaseOrderItem implements Serializable {
    private PurchaseOrder purchaseOrder;

    private Integer receivedQuantity;

    private Integer returnedQuantity;

    private Integer cancelledQuantity;

    private String supplierName;

    private PurchaseRequestItem purchaseRequestItem;
    
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 缺省构造函数
     */
    public AbstractPurchaseOrderItem() {
    }

    /**
     * 给定主键的构造函数
     * 
     * @param id
     */
    public AbstractPurchaseOrderItem(Integer id) {
        this.setId(id);
    }

    /**
     * @return Returns the cancelledQuantity.
     */
    public Integer getCancelledQuantity() {
        return cancelledQuantity;
    }

    /**
     * @param cancelledQuantity
     *            The cancelledQuantity to set.
     */
    public void setCancelledQuantity(Integer cancelledQuantity) {
        this.cancelledQuantity = cancelledQuantity;
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

    /**
     * @return Returns the receivedQuantity.
     */
    public Integer getReceivedQuantity() {
        return receivedQuantity;
    }

    /**
     * @param receivedQuantity
     *            The receivedQuantity to set.
     */
    public void setReceivedQuantity(Integer receivedQuantity) {
        this.receivedQuantity = receivedQuantity;
    }

    /**
     * @return Returns the returnedQuantity.
     */
    public Integer getReturnedQuantity() {
        return returnedQuantity;
    }

    /**
     * @param returnedQuantity
     *            The returnedQuantity to set.
     */
    public void setReturnedQuantity(Integer returnedQuantity) {
        this.returnedQuantity = returnedQuantity;
    }

    /**
     * @return Returns the purchaseRequestItem.
     */
    public PurchaseRequestItem getPurchaseRequestItem() {
        return purchaseRequestItem;
    }

    /**
     * @param purchaseRequestItem
     *            The purchaseRequestItem to set.
     */
    public void setPurchaseRequestItem(PurchaseRequestItem purchaseRequestItem) {
        this.purchaseRequestItem = purchaseRequestItem;
    }

    /**
     * @return Returns the supplierName.
     */
    public String getSupplierName() {
        return supplierName;
    }

    /**
     * @param supplierName
     *            The supplierName to set.
     */
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    /**
     * Implementation of the equals comparison on the basis of equality of the
     * primary key values.
     * 
     * @param rhs
     * @return boolean
     */
    public boolean equals(Object rhs) {
        if (rhs == null)
            return false;
        if (this == rhs)
            return true;
        if (!(rhs instanceof PurchaseOrderItem))
            return false;
        PurchaseOrderItem that = (PurchaseOrderItem) rhs;
        if (this.getId() != null)
            return this.getId().equals(that.getId());
        return that.getId() == null;
    }

}
