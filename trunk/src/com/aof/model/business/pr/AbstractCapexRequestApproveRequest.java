/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.business.pr;

import java.io.Serializable;

import com.aof.model.admin.User;
import com.aof.model.business.BaseApproveRequest;

/**
 * 该类代表capex_request_approver表中的一行
 * 
 * @author nicebean
 * @version 1.0 (2005-11-19)
 */
public abstract class AbstractCapexRequestApproveRequest extends BaseApproveRequest implements Serializable {

    /** default constructor */
    public AbstractCapexRequestApproveRequest() {
    }

    /** minimal constructor */
    public AbstractCapexRequestApproveRequest(String approveRequestId, User approver) {
        super(approveRequestId, approver);
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
        if (!(rhs instanceof CapexRequestApproveRequest))
            return false;
        CapexRequestApproveRequest that = (CapexRequestApproveRequest) rhs;
        if (this.getApproveRequestId() != null) {
            if (!this.getApproveRequestId().equals(that.getApproveRequestId()))
                return false;
        } else {
            if (that.getApproveRequestId() != null)
                return false;
        }
        if (this.getApprover() != null) {
            if (!this.getApprover().equals(that.getApprover()))
                return false;
        } else {
            if (that.getApprover() != null)
                return false;
        }
        return true;
    }

}
