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
 * A class that represents a row in the 'capex_request_approver' table.
 * 
 * @author nicebean
 * @version 1.0 (2005-11-19)
 */
public class CapexRequestApproveRequest extends AbstractCapexRequestApproveRequest implements Serializable {
    /**
     * Simple constructor of CapexRequestApproveRequest instances.
     */
    public CapexRequestApproveRequest() {
    }

    /**
     * Constructor of CapexRequestApproveRequest instances given a simple primary key.
     * 
     * @param id
     * @param approver
     */
    public CapexRequestApproveRequest(String id, User approver) {
        super(id, approver);
    }

    /* Add customized code below */

}
