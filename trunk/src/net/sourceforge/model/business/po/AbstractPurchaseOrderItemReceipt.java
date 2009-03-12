/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.business.po;

import java.io.Serializable;
import java.util.Date;

import net.sourceforge.model.admin.User;
import net.sourceforge.model.metadata.ExportStatus;

/**
 * 该类代表po_item_receipt表的一行记录
 */
public abstract class AbstractPurchaseOrderItemReceipt implements Serializable {
    /**
     * The cached hash code value for this instance. Settting to 0 triggers re-calculation.
     */
    private int hashValue = 0;

    private Integer id;
    private User receiver1;
    private Date receiveDate1;
    private Integer receiveQty1;
    private User receiver2;
    private Date receiveDate2;
    private Integer receiveQty2;
    private ExportStatus exportStatus;

    private PurchaseOrderItem purchaseOrderItem;

    /**
     * 缺省构造函数
     */
    public AbstractPurchaseOrderItemReceipt() {
    }

    /**
     * 给定主键的构造函数
     * 
     * @param id
     */
    public AbstractPurchaseOrderItemReceipt(Integer id) {
        this.setId(id);
    }

    /**
     * @return Returns the exportStatus.
     */
    public ExportStatus getExportStatus() {
        return exportStatus;
    }

    /**
     * @param exportStatus The exportStatus to set.
     */
    public void setExportStatus(ExportStatus exportStatus) {
        this.exportStatus = exportStatus;
    }

    /**
     * @return Returns the id.
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id to set.
     */
    protected void setId(Integer id) {
        this.hashValue = 0;
        this.id = id;
    }

    /**
     * @return Returns the receiveDate1.
     */
    public Date getReceiveDate1() {
        return receiveDate1;
    }

    /**
     * @param receiveDate1 The receiveDate1 to set.
     */
    public void setReceiveDate1(Date receiveDate1) {
        this.receiveDate1 = receiveDate1;
    }

    /**
     * @return Returns the receiveDate2.
     */
    public Date getReceiveDate2() {
        return receiveDate2;
    }

    /**
     * @param receiveDate2 The receiveDate2 to set.
     */
    public void setReceiveDate2(Date receiveDate2) {
        this.receiveDate2 = receiveDate2;
    }

    /**
     * @return Returns the receiveQty1.
     */
    public Integer getReceiveQty1() {
        return receiveQty1;
    }

    /**
     * @param receiveQty1 The receiveQty1 to set.
     */
    public void setReceiveQty1(Integer receiveQty1) {
        this.receiveQty1 = receiveQty1;
    }

    /**
     * @return Returns the receiveQty2.
     */
    public Integer getReceiveQty2() {
        return receiveQty2;
    }

    /**
     * @param receiveQty2 The receiveQty2 to set.
     */
    public void setReceiveQty2(Integer receiveQty2) {
        this.receiveQty2 = receiveQty2;
    }

    /**
     * @return Returns the receiver1.
     */
    public User getReceiver1() {
        return receiver1;
    }

    /**
     * @param receiver1 The receiver1 to set.
     */
    public void setReceiver1(User receiver1) {
        this.receiver1 = receiver1;
    }

    /**
     * @return Returns the receiver2.
     */
    public User getReceiver2() {
        return receiver2;
    }

    /**
     * @param receiver2 The receiver2 to set.
     */
    public void setReceiver2(User receiver2) {
        this.receiver2 = receiver2;
    }

    /**
     * @return Returns the purchaseOrderItem.
     */
    public PurchaseOrderItem getPurchaseOrderItem() {
        return purchaseOrderItem;
    }

    /**
     * @param purchaseOrderItem
     *            The purchaseOrderItem to set.
     */
    public void setPurchaseOrderItem(PurchaseOrderItem purchaseOrderItem) {
        this.purchaseOrderItem = purchaseOrderItem;
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
        if (!(rhs instanceof PurchaseOrderItemReceipt))
            return false;
        PurchaseOrderItemReceipt that = (PurchaseOrderItemReceipt) rhs;
        if (this.getId() != null)
            return this.getId().equals(that.getId());
        return that.getId() == null;
    }

    /**
     * Implementation of the hashCode method conforming to the Bloch pattern
     * with the exception of array properties (these are very unlikely primary
     * key types).
     * 
     * @return int
     */
    public int hashCode() {
        if (this.hashValue == 0) {
            int result = 17;
            int itemIdValue = this.getId() == null ? 0 : this.getId().hashCode();
            result = result * 37 + itemIdValue;
            this.hashValue = result;
        }
        return this.hashValue;
    }

}
