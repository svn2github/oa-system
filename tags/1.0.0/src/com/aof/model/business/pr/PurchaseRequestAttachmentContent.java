/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.business.pr;

import java.io.Serializable;

/**
 * A class that represents a row in the 'pr_attach' table (only
 * file_content column).
 * 
 * @author nicebean
 * @version 1.0 (2005-12-01)
 */
public class PurchaseRequestAttachmentContent extends AbstractPurchaseRequestAttachmentContent implements Serializable {
    /**
     * Simple constructor of PurchaseRequestAttachmentContent instances.
     */
    public PurchaseRequestAttachmentContent() {
    }

    /**
     * Constructor of PurchaseRequestAttachmentContent instances given a simple primary key.
     * 
     * @param id
     */
    public PurchaseRequestAttachmentContent(Integer id) {
        super(id);
    }

    /* Add customized code below */

}
