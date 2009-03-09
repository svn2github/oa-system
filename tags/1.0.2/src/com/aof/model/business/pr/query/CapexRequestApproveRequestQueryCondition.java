/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.model.business.pr.query;

import com.aof.model.business.query.BaseApproveQueryCondition;

/**
 * CapexRequestApproveRequest查询条件的枚举类
 * 
 * @author nicebean
 * @version 1.0 (2005-12-8)
 */
public class CapexRequestApproveRequestQueryCondition extends BaseApproveQueryCondition {

    public static final CapexRequestApproveRequestQueryCondition CODE_LIKE = new CapexRequestApproveRequestQueryCondition("code_like");

    public static final CapexRequestApproveRequestQueryCondition TITLE_LIKE = new CapexRequestApproveRequestQueryCondition("title_like");

    //public static final CapexRequestApproveRequestQueryCondition STATUS_EQ = new CapexRequestApproveRequestQueryCondition("status_eq");

    public static final CapexRequestApproveRequestQueryCondition STATUS_NEQ = new CapexRequestApproveRequestQueryCondition("status_neq");

    public static final CapexRequestApproveRequestQueryCondition SUBMIT_DATE_BT = new CapexRequestApproveRequestQueryCondition("submit_date_bt");

    protected CapexRequestApproveRequestQueryCondition(String value) {
        super(value);
    }

    public static CapexRequestApproveRequestQueryCondition getEnum(String value) {
        return (CapexRequestApproveRequestQueryCondition) getEnum(CapexRequestApproveRequestQueryCondition.class, value);
    }

}
