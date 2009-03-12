/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.business.pr;

import java.io.Serializable;

/**
 * A class that represents a row in the 'pr_item_recharge' table.
 * 
 * @author nicebean
 * @version 1.0 (2005-12-01)
 */
public class PurchaseRequestItemRecharge extends AbstractPurchaseRequestItemRecharge implements Serializable {
    /**
     * Simple constructor of PurchaseRequestItemRecharge instances.
     */
    public PurchaseRequestItemRecharge() {
    }

    /**
     * Constructor of PurchaseRequestItemRecharge instances given a simple primary key.
     * 
     * @param id
     */
    public PurchaseRequestItemRecharge(Integer id) {
        super(id);
    }
    
    /* Add customized code below */

}
