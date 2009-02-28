/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.business.pr;

import java.io.Serializable;

/**
 * A class that represents a row in the 'pr_item_attach' table (exclude
 * file_content column).
 * 
 * @author nicebean
 * @version 1.0 (2005-12-01)
 */
public class PurchaseRequestItemAttachment extends AbstractPurchaseRequestItemAttachment implements Serializable {
    /**
     * Simple constructor of PurchaseRequestItemAttachment instances.
     */
    public PurchaseRequestItemAttachment() {
    }

    /**
     * Constructor of PurchaseRequestItemAttachment instances given a simple primary key.
     * 
     * @param id
     */
    public PurchaseRequestItemAttachment(Integer id) {
        super(id);
    }

    /* Add customized code below */

}
