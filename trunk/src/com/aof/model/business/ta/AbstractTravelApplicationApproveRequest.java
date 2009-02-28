/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.business.ta;

import java.io.Serializable;

import com.aof.model.admin.User;
import com.aof.model.business.BaseApproveRequest;

/**
 * 该类代表t_ta_approve表的一行记录
 */
public abstract class AbstractTravelApplicationApproveRequest extends BaseApproveRequest implements Serializable {

    /**
     * 缺省构造函数
     */
    public AbstractTravelApplicationApproveRequest() {
    }

    /**
     * 给定主键的构造函数
     * 
     * @param id
     * @param approver
     */
    public AbstractTravelApplicationApproveRequest(String id, User approver) {
        super(id, approver);
    }

    /**
     * Implementation of the equals comparison on the basis of equality of the
     * primary key values.
     * 
     * @param rhs
     * @return boolean
     */
    public boolean equals(Object rhs) {
        if (rhs == null) return false;
        if (this == rhs) return true;
        if (!(rhs instanceof TravelApplicationApproveRequest)) return false;
        TravelApplicationApproveRequest that = (TravelApplicationApproveRequest) rhs;
        if (this.getApproveRequestId() != null) {
            if (!this.getApproveRequestId().equals(that.getApproveRequestId())) return false;
        } else {
            if (that.getApproveRequestId() != null) return false;
        }
        if (this.getApprover() != null) {
            if (!this.getApprover().equals(that.getApprover())) return false;
        } else {
            if (that.getApprover() != null) return false;
        }
        return true;
    }

}
