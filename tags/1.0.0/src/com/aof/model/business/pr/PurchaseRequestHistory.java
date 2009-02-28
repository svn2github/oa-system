/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.business.pr;

import java.io.Serializable;

/**
 * A class that represents a row in the 'pr_history' table.
 * 
 * @author nicebean
 * @version 1.0 (2005-12-01)
 */
public class PurchaseRequestHistory extends AbstractPurchaseRequestHistory implements Serializable {
    /**
     * Simple constructor of PurchaseRequestHistory instances.
     */
    public PurchaseRequestHistory() {
    }

    /**
     * Constructor of PurchaseRequestHistory instances given a simple primary key.
     * 
     * @param id
     */
    public PurchaseRequestHistory(Integer id) {
        super(id);
    }
    
    /* Add customized code below */

}
