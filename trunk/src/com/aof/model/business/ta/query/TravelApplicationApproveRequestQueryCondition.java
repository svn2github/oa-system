/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.model.business.ta.query;

import com.aof.model.business.query.BaseApproveQueryCondition;

/**
 * TravelApplicationApproveRequest查询条件的枚举类
 * 
 * @author ych
 * @version 1.0 (Nov 18, 2005)
 */
public class TravelApplicationApproveRequestQueryCondition extends BaseApproveQueryCondition {

    public static final TravelApplicationApproveRequestQueryCondition CODE_LIKE = new TravelApplicationApproveRequestQueryCondition("code_like");

    public static final TravelApplicationApproveRequestQueryCondition TITLE_LIKE = new TravelApplicationApproveRequestQueryCondition("title_like");

    //public static final TravelApplicationApproveRequestQueryCondition STATUS_EQ = new TravelApplicationApproveRequestQueryCondition("status_eq");

    public static final TravelApplicationApproveRequestQueryCondition STATUS_NEQ = new TravelApplicationApproveRequestQueryCondition("status_neq");

    public static final TravelApplicationApproveRequestQueryCondition SUBMIT_DATE_BT = new TravelApplicationApproveRequestQueryCondition("submit_date_bt");
    
    public static final TravelApplicationApproveRequestQueryCondition URGENT_EQ = new TravelApplicationApproveRequestQueryCondition("urgent_eq");

    public static final TravelApplicationApproveRequestQueryCondition REQUESTOR_LK = new TravelApplicationApproveRequestQueryCondition("requestor_lk");
    

    protected TravelApplicationApproveRequestQueryCondition(String value) {
        super(value);
    }

    public static TravelApplicationApproveRequestQueryCondition getEnum(String value) {
        return (TravelApplicationApproveRequestQueryCondition) getEnum(TravelApplicationApproveRequestQueryCondition.class, value);
    }

}
