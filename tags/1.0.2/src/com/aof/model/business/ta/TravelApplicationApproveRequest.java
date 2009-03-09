/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.business.ta;

import java.io.Serializable;

import com.aof.model.admin.User;

/**
 * 该类代表t_ta_approve表的一行记录
 */
public class TravelApplicationApproveRequest extends AbstractTravelApplicationApproveRequest implements Serializable {

    /**
     * 缺省构造函数
     */
    public TravelApplicationApproveRequest() {
    }

    /**
     * 给定主键的构造函数
     * 
     * @param id
     * @param approver
     */
    public TravelApplicationApproveRequest(String id, User approver) {
        super(id, approver);
    }

    /* Add customized code below */

}
