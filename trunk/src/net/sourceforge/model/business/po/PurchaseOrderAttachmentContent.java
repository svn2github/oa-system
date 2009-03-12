/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.business.po;

import java.io.Serializable;

/**
 * A class that represents a row in the 'po_attach' table (only
 * file_content column).
 * 
 * @author nicebean
 * @version 1.0 (2005-12-14)
 */
public class PurchaseOrderAttachmentContent extends AbstractPurchaseOrderAttachmentContent implements Serializable {
    /**
     * Simple constructor of PurchaseOrderAttachmentContent instances.
     */
    public PurchaseOrderAttachmentContent() {
    }

    /**
     * Constructor of PurchaseOrderAttachmentContent instances given a simple primary key.
     * 
     * @param id
     */
    public PurchaseOrderAttachmentContent(Integer id) {
        super(id);
    }

    /* Add customized code below */

}
