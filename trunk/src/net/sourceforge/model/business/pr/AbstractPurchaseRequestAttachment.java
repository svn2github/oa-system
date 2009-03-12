/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.business.pr;

import java.io.Serializable;

import net.sourceforge.model.BaseAttachment;

/**
 * 该类代表pr_attach表中的一行(不包括file_content列)
 * 
 * @author nicebean
 * @version 1.0 (2005-12-01)
 */
public abstract class AbstractPurchaseRequestAttachment extends BaseAttachment implements Serializable {

    /** persistent field */
    private PurchaseRequest purchaseRequest;

    /** default constructor */
    public AbstractPurchaseRequestAttachment() {
    }

    /** minimal constructor */
    public AbstractPurchaseRequestAttachment(Integer id) {
        setId(id);
    }

    /**
     * @return Returns the purchaseRequest.
     */
    public PurchaseRequest getPurchaseRequest() {
        return purchaseRequest;
    }

    /**
     * @param purchaseRequest The purchaseRequest to set.
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
        if (!(rhs instanceof PurchaseRequestAttachment))
            return false;
        PurchaseRequestAttachment that = (PurchaseRequestAttachment) rhs;
        if (this.getId() != null)
            return this.getId().equals(that.getId());
        return that.getId() == null;
    }
    
}
