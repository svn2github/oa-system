/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.model.business.po.query;

import net.sourceforge.model.business.query.BaseApproveQueryCondition;

/**
 * PurchaseOrderApproveRequest查询条件的枚举类
 * 
 * @author nicebean
 * @version 1.0 (2005-12-22)
 */
public class PurchaseOrderApproveRequestQueryCondition extends BaseApproveQueryCondition {

    public static final PurchaseOrderApproveRequestQueryCondition CODE_LIKE = new PurchaseOrderApproveRequestQueryCondition("code_like");

    public static final PurchaseOrderApproveRequestQueryCondition TITLE_LIKE = new PurchaseOrderApproveRequestQueryCondition("title_like");

    //public static final PurchaseOrderApproveRequestQueryCondition STATUS_EQ = new PurchaseOrderApproveRequestQueryCondition("status_eq");

    public static final PurchaseOrderApproveRequestQueryCondition STATUS_NEQ = new PurchaseOrderApproveRequestQueryCondition("status_neq");

    public static final PurchaseOrderApproveRequestQueryCondition SUBMIT_DATE_BT = new PurchaseOrderApproveRequestQueryCondition("submit_date_bt");

    public static final PurchaseOrderApproveRequestQueryCondition PUCHASE_ORDER_AMOUNT_GE = new PurchaseOrderApproveRequestQueryCondition("amount_ge");;

    public static final PurchaseOrderApproveRequestQueryCondition PUCHASE_ORDER_AMOUNT_LE = new PurchaseOrderApproveRequestQueryCondition("amount_le");;

    public static final PurchaseOrderApproveRequestQueryCondition PUCHASE_ORDER_SUPPLIER_NAME_LIKE = new PurchaseOrderApproveRequestQueryCondition("puchase_order_supplier_name_like");

    protected PurchaseOrderApproveRequestQueryCondition(String value) {
        super(value);
    }

    public static PurchaseOrderApproveRequestQueryCondition getEnum(String value) {
        return (PurchaseOrderApproveRequestQueryCondition) getEnum(PurchaseOrderApproveRequestQueryCondition.class, value);
    }

}
