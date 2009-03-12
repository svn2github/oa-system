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
 * 该类代表pr_item_attach表中的一行(不包括file_content列)
 * 
 * @author nicebean
 * @version 1.0 (2005-12-01)
 */
public abstract class AbstractPurchaseRequestItemAttachment extends BaseAttachment implements Serializable {

    /** persistent field */
    private PurchaseRequestItem purchaseRequestItem;

    /** default constructor */
    public AbstractPurchaseRequestItemAttachment() {
    }

    /** minimal constructor */
    public AbstractPurchaseRequestItemAttachment(Integer id) {
        setId(id);
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
        if (!(rhs instanceof PurchaseRequestItemAttachment))
            return false;
        PurchaseRequestItemAttachment that = (PurchaseRequestItemAttachment) rhs;
        if (this.getId() != null)
            return this.getId().equals(that.getId());
        return that.getId() == null;
    }

}
