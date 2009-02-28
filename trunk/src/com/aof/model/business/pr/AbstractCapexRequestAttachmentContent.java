/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.business.pr;

import java.io.Serializable;

import com.aof.model.BaseAttachmentContent;

/**
 * 该类代表capex_request_attach表中的一行(仅file_content列)
 * 
 * @author nicebean
 * @version 1.0 (2005-11-19)
 */
public abstract class AbstractCapexRequestAttachmentContent extends BaseAttachmentContent implements Serializable {

    /** default constructor */
    public AbstractCapexRequestAttachmentContent() {
    }

    /** minimal constructor */
    public AbstractCapexRequestAttachmentContent(Integer id) {
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
        if (!(rhs instanceof CapexRequestAttachmentContent))
            return false;
        CapexRequestAttachmentContent that = (CapexRequestAttachmentContent) rhs;
        if (this.getId() != null)
            return this.getId().equals(that.getId());
        return that.getId() == null;
    }

}
