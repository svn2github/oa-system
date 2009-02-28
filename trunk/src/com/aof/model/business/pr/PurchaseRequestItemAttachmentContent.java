/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.business.pr;

import java.io.Serializable;

/**
 * A class that represents a row in the 'pr_item_attach' table (only
 * file_content column).
 * 
 * @author nicebean
 * @version 1.0 (2005-12-01)
 */
public class PurchaseRequestItemAttachmentContent extends AbstractPurchaseRequestItemAttachmentContent implements Serializable {
    /**
     * Simple constructor of PurchaseRequestItemAttachmentContent instances.
     */
    public PurchaseRequestItemAttachmentContent() {
    }

    /**
     * Constructor of PurchaseRequestItemAttachmentContent instances given a simple primary key.
     * 
     * @param id
     */
    public PurchaseRequestItemAttachmentContent(Integer id) {
        super(id);
    }

    /* Add customized code below */

}
