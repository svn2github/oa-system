/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.business.pr;

import java.io.Serializable;

import net.sourceforge.model.admin.User;
import net.sourceforge.model.business.BaseApproveRequest;

/**
 * 该类代表pr_approver表中的一行
 * 
 * @author nicebean
 * @version 1.0 (2005-12-01)
 */
public abstract class AbstractPurchaseRequestApproveRequest extends BaseApproveRequest implements Serializable {

    /** default constructor */
    public AbstractPurchaseRequestApproveRequest() {
    }

    /** minimal constructor */
    public AbstractPurchaseRequestApproveRequest(String approveRequestId, User approver) {
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
        if (!(rhs instanceof PurchaseRequestApproveRequest))
            return false;
        PurchaseRequestApproveRequest that = (PurchaseRequestApproveRequest) rhs;
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
