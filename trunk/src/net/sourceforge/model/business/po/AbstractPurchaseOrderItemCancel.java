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
 * 该类代表po_item_cancel表的一行记录
 */
public abstract class AbstractPurchaseOrderItemCancel implements Serializable {
    /**
     * The cached hash code value for this instance. Settting to 0 triggers re-calculation.
     */
    private int hashValue = 0;

    private Integer id;
    private User cancelUser;
    private Date cancelDate;
    private int cancelQty;
    private ExportStatus exportStatus;

    private PurchaseOrderItem purchaseOrderItem;

    /**
     * 缺省构造函数
     */
    public AbstractPurchaseOrderItemCancel() {
    }

    /**
     * 给定主键的构造函数
     * 
     * @param id
     */
    public AbstractPurchaseOrderItemCancel(Integer id) {
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
     * @return Returns the cancelDate.
     */
    public Date getCancelDate() {
        return cancelDate;
    }

    /**
     * @param cancelDate The cancelDate to set.
     */
    public void setCancelDate(Date cancelDate) {
        this.cancelDate = cancelDate;
    }

    /**
     * @return Returns the cancelQty.
     */
    public int getCancelQty() {
        return cancelQty;
    }

    /**
     * @param cancelQty The cancelQty to set.
     */
    public void setCancelQty(int cancelQty) {
        this.cancelQty = cancelQty;
    }

    /**
     * @return Returns the cancelUser.
     */
    public User getCancelUser() {
        return cancelUser;
    }

    /**
     * @param cancelUser The cancelUser to set.
     */
    public void setCancelUser(User cancelUser) {
        this.cancelUser = cancelUser;
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
        if (!(rhs instanceof PurchaseOrderItemCancel))
            return false;
        PurchaseOrderItemCancel that = (PurchaseOrderItemCancel) rhs;
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
