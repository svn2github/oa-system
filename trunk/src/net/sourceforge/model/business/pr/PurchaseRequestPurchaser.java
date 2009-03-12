/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.business.pr;

import java.io.Serializable;

/**
 * A class that represents a row in the 'pr_authorized_purchaser' table.
 * 
 * @author nicebean
 * @version 1.0 (2005-12-01)
 */
public class PurchaseRequestPurchaser extends AbstractPurchaseRequestPurchaser implements Serializable {
    /**
     * Simple constructor of PurchaseRequestPurchaser instances.
     */
    public PurchaseRequestPurchaser() {
    }

    /**
     * Constructor of PurchaseRequestPurchaser instances given a simple primary key.
     * 
     * @param id
     */
    public PurchaseRequestPurchaser(Integer id) {
        super(id);
    }
    
    /* Add customized code below */

}
