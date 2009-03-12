/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.business.po;

import java.io.Serializable;

import net.sourceforge.model.BaseAttachment;

/**
 * 该类代表po_item_attach表中的一行(不包括file_content列)
 * 
 * @author nicebean
 * @version 1.0 (2005-12-14)
 */
public abstract class AbstractPurchaseOrderItemAttachment extends BaseAttachment implements Serializable {

    /** persistent field */
    private PurchaseOrderItem purchaseOrderItem;

    /** default constructor */
    public AbstractPurchaseOrderItemAttachment() {
    }

    /** minimal constructor */
    public AbstractPurchaseOrderItemAttachment(Integer id) {
        setId(id);
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
        if (!(rhs instanceof PurchaseOrderItemAttachment))
            return false;
        PurchaseOrderItemAttachment that = (PurchaseOrderItemAttachment) rhs;
        if (this.getId() != null)
            return this.getId().equals(that.getId());
        return that.getId() == null;
    }

}
