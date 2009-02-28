/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.business.pr;

import java.io.Serializable;

/**
 * A class that represents a row in the 'pr_item_history' table.
 * 
 * @author nicebean
 * @version 1.0 (2005-12-01)
 */
public class PurchaseRequestHistoryItem extends AbstractPurchaseRequestHistoryItem implements Serializable {
    /**
     * Simple constructor of PurchaseRequestHistoryItem instances.
     */
    public PurchaseRequestHistoryItem() {
    }

    /**
     * Constructor of PurchaseRequestHistoryItem instances given a simple primary key.
     * 
     * @param id
     */
    public PurchaseRequestHistoryItem(Integer id) {
        super(id);
    }
    
    /* Add customized code below */

}
