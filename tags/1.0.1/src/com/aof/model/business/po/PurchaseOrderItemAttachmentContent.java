/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.business.po;

import java.io.Serializable;

/**
 * A class that represents a row in the 'po_item_attach' table (only
 * file_content column).
 * 
 * @author nicebean
 * @version 1.0 (2005-12-14)
 */
public class PurchaseOrderItemAttachmentContent extends AbstractPurchaseOrderItemAttachmentContent implements Serializable {
    /**
     * Simple constructor of PurchaseRequestItemAttachmentContent instances.
     */
    public PurchaseOrderItemAttachmentContent() {
    }

    /**
     * Constructor of PurchaseRequestItemAttachmentContent instances given a simple primary key.
     * 
     * @param id
     */
    public PurchaseOrderItemAttachmentContent(Integer id) {
        super(id);
    }

    /* Add customized code below */

}
