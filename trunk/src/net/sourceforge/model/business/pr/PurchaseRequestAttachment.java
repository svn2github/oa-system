/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.business.pr;

import java.io.Serializable;

/**
 * A class that represents a row in the 'pr_attach' table (exclude
 * file_content column).
 * 
 * @author nicebean
 * @version 1.0 (2005-12-01)
 */
public class PurchaseRequestAttachment extends AbstractPurchaseRequestAttachment implements Serializable {
    /**
     * Simple constructor of PurchaseRequestAttachment instances.
     */
    public PurchaseRequestAttachment() {
    }

    /**
     * Constructor of PurchaseRequestAttachment instances given a simple primary key.
     * 
     * @param id
     */
    public PurchaseRequestAttachment(Integer id) {
        super(id);
    }

    /* Add customized code below */

}
