/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.business.po;

import java.io.Serializable;

/**
 * A class that represents a row in the 'po_attach' table (exclude
 * file_content column).
 * 
 * @author nicebean
 * @version 1.0 (2005-12-14)
 */
public class PurchaseOrderAttachment extends AbstractPurchaseOrderAttachment implements Serializable {
    /**
     * Simple constructor of PurchaseOrderAttachment instances.
     */
    public PurchaseOrderAttachment() {
    }

    /**
     * Constructor of PurchaseOrderAttachment instances given a simple primary key.
     * 
     * @param id
     */
    public PurchaseOrderAttachment(Integer id) {
        super(id);
    }

    /* Add customized code below */

}
