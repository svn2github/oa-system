/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.business.pr;

import java.io.Serializable;

import com.aof.model.admin.User;

/**
 * A class that represents a row in the 'pr_approver' table.
 * 
 * @author nicebean
 * @version 1.0 (2005-12-01)
 */
public class PurchaseRequestApproveRequest extends AbstractPurchaseRequestApproveRequest implements Serializable {
    /**
     * Simple constructor of PurchaseRequestApproveRequest instances.
     */
    public PurchaseRequestApproveRequest() {
    }

    /**
     * Constructor of PurchaseRequestApproveRequest instances given a simple primary key.
     * 
     * @param id
     * @param approver
     */
    public PurchaseRequestApproveRequest(String id, User approver) {
        super(id, approver);
    }

    /* Add customized code below */

}
