/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.business.pr;

import java.io.Serializable;

import com.aof.model.business.BasePurchaser;

/**
 * 该类代表pr_authorized_purchaser表中的一行
 * 
 * @author nicebean
 * @version 1.0 (2005-12-01)
 */
public abstract class AbstractPurchaseRequestPurchaser extends BasePurchaser implements Serializable {
    /** persistent field */
    private PurchaseRequest purchaseRequest;

    /** default constructor */
    public AbstractPurchaseRequestPurchaser() {
    }

    /** minimal constructor */
    public AbstractPurchaseRequestPurchaser(Integer id) {
        setId(id);
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
        if (!(rhs instanceof PurchaseRequestPurchaser))
            return false;
        PurchaseRequestPurchaser that = (PurchaseRequestPurchaser) rhs;
        if (this.getId() != null)
            return this.getId().equals(that.getId());
        return that.getId() == null;
    }

}
