/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.business.pr;

import java.io.Serializable;

import net.sourceforge.model.BaseAttachment;

/**
 * 该类代表capex_request_attach表中的一行(不包括file_content列)
 * 
 * @author nicebean
 * @version 1.0 (2005-11-19)
 */
public abstract class AbstractCapexRequestAttachment extends BaseAttachment implements Serializable {

    /** persistent field */
    private CapexRequest capexRequest;

    /** default constructor */
    public AbstractCapexRequestAttachment() {
    }

    /** minimal constructor */
    public AbstractCapexRequestAttachment(Integer id) {
        setId(id);
    }

    /**
     * @return Returns the capexRequest.
     */
    public CapexRequest getCapexRequest() {
        return capexRequest;
    }

    /**
     * @param capexRequest The capexRequest to set.
     */
    public void setCapexRequest(CapexRequest capexRequest) {
        this.capexRequest = capexRequest;
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
        if (!(rhs instanceof CapexRequestAttachment))
            return false;
        CapexRequestAttachment that = (CapexRequestAttachment) rhs;
        if (this.getId() != null)
            return this.getId().equals(that.getId());
        return that.getId() == null;
    }
    
}
