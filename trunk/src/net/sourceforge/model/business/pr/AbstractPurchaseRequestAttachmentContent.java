/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.business.pr;

import java.io.Serializable;

import net.sourceforge.model.BaseAttachmentContent;

/**
 * 该类代表pr_attach表中的一行(仅file_content列)
 * 
 * @author nicebean
 * @version 1.0 (2005-12-01)
 */
public abstract class AbstractPurchaseRequestAttachmentContent extends BaseAttachmentContent implements Serializable {

    /** default constructor */
    public AbstractPurchaseRequestAttachmentContent() {
    }

    /** minimal constructor */
    public AbstractPurchaseRequestAttachmentContent(Integer id) {
        super(id);
    }

    /**
     * Implementation of the equals comparison on the basis of equality of the
     * primary key values.
     * 
     * @param rhs
     * @return boolean
     */
    public boolean equals(Object rhs) {
        if (rhs == null)
            return false;
        if (this == rhs)
            return true;
        if (!(rhs instanceof PurchaseRequestAttachmentContent))
            return false;
        PurchaseRequestAttachmentContent that = (PurchaseRequestAttachmentContent) rhs;
        if (this.getId() != null)
            return this.getId().equals(that.getId());
        return that.getId() == null;
    }

}
