/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.business.pr;

import java.io.Serializable;

import net.sourceforge.model.business.BaseRecharge;

/**
 * 该类代表pr_item_recharge表中的一行
 * 
 * @author nicebean
 * @version 1.0 (2005-12-01)
 */
public abstract class AbstractPurchaseRequestItemRecharge extends BaseRecharge implements Serializable {
    /** persistent field */
    private PurchaseRequestItem purchaseRequestItem;

    /** default constructor */
    public AbstractPurchaseRequestItemRecharge() {
    }

    /** minimal constructor */
    public AbstractPurchaseRequestItemRecharge(Integer id) {
        super(id);
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
        if (!(rhs instanceof PurchaseRequestItemRecharge))
            return false;
        PurchaseRequestItemRecharge that = (PurchaseRequestItemRecharge) rhs;
        if (this.getId() != null)
            return this.getId().equals(that.getId());
        return that.getId() == null;
    }

}
