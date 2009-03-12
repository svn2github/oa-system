/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.business.po;

import java.io.Serializable;

/**
 * A class that represents a row in the 'po_item_attach' table (exclude
 * file_content column).
 * 
 * @author nicebean
 * @version 1.0 (2005-12-14)
 */
public class PurchaseOrderItemAttachment extends AbstractPurchaseOrderItemAttachment implements Serializable {
    /**
     * Simple constructor of PurchaseRequestItemAttachment instances.
     */
    public PurchaseOrderItemAttachment() {
    }

    /**
     * Constructor of PurchaseRequestItemAttachment instances given a simple primary key.
     * 
     * @param id
     */
    public PurchaseOrderItemAttachment(Integer id) {
        super(id);
    }

    /* Add customized code below */

}
