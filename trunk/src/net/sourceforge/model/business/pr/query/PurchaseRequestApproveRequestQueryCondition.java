/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.model.business.pr.query;

import net.sourceforge.model.business.query.BaseApproveQueryCondition;

/**
 * PurchaseRequestApproveRequest查询条件的枚举类
 * 
 * @author nicebean
 * @version 1.0 (2005-12-15)
 */
public class PurchaseRequestApproveRequestQueryCondition extends BaseApproveQueryCondition {

    public static final PurchaseRequestApproveRequestQueryCondition CODE_LIKE = new PurchaseRequestApproveRequestQueryCondition("code_like");

    public static final PurchaseRequestApproveRequestQueryCondition TITLE_LIKE = new PurchaseRequestApproveRequestQueryCondition("title_like");

    //public static final PurchaseRequestApproveRequestQueryCondition STATUS_EQ = new PurchaseRequestApproveRequestQueryCondition("status_eq");

    public static final PurchaseRequestApproveRequestQueryCondition STATUS_NEQ = new PurchaseRequestApproveRequestQueryCondition("status_neq");

    public static final PurchaseRequestApproveRequestQueryCondition SUBMIT_DATE_BT = new PurchaseRequestApproveRequestQueryCondition("submit_date_bt");

    protected PurchaseRequestApproveRequestQueryCondition(String value) {
        super(value);
    }

    public static PurchaseRequestApproveRequestQueryCondition getEnum(String value) {
        return (PurchaseRequestApproveRequestQueryCondition) getEnum(PurchaseRequestApproveRequestQueryCondition.class, value);
    }

}
