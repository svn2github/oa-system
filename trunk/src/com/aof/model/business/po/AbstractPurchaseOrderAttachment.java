/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.business.po;

import java.io.Serializable;

import com.aof.model.BaseAttachment;

/**
 * 该类代表po_attach表中的一行(不包括file_content列)
 * 
 * @author nicebean
 * @version 1.0 (2005-12-14)
 */
public abstract class AbstractPurchaseOrderAttachment extends BaseAttachment implements Serializable {

    /** persistent field */
    private PurchaseOrder purchaseOrder;

    /** default constructor */
    public AbstractPurchaseOrderAttachment() {
    }

    /** minimal constructor */
    public AbstractPurchaseOrderAttachment(Integer id) {
        setId(id);
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
        if (!(rhs instanceof PurchaseOrderAttachment))
            return false;
        PurchaseOrderAttachment that = (PurchaseOrderAttachment) rhs;
        if (this.getId() != null)
            return this.getId().equals(that.getId());
        return that.getId() == null;
    }

}
