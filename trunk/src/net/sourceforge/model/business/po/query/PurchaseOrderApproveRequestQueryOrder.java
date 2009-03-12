/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.model.business.po.query;

import org.apache.commons.lang.enums.Enum;

/**
 * PurchaseOrderApproveRequest查询顺序的枚举类
 * 
 * @author nicebean
 * @version 1.0 (2005-12-15)
 */
public class PurchaseOrderApproveRequestQueryOrder extends Enum {

    public static final PurchaseOrderApproveRequestQueryOrder SEQ = new PurchaseOrderApproveRequestQueryOrder("seq");

    public static final PurchaseOrderApproveRequestQueryOrder STATUS = new PurchaseOrderApproveRequestQueryOrder("status");

    public static final PurchaseOrderApproveRequestQueryOrder APPROVEDATE = new PurchaseOrderApproveRequestQueryOrder("approveDate");

    public static final PurchaseOrderApproveRequestQueryOrder COMMENT = new PurchaseOrderApproveRequestQueryOrder("comment");

    public static final PurchaseOrderApproveRequestQueryOrder CANMODIFY = new PurchaseOrderApproveRequestQueryOrder("canModify");

    public static final PurchaseOrderApproveRequestQueryOrder PURCHASEORDER_ID = new PurchaseOrderApproveRequestQueryOrder("purchaseOrder_id");

    protected PurchaseOrderApproveRequestQueryOrder(String value) {
        super(value);
    }

    public static PurchaseOrderApproveRequestQueryOrder getEnum(String value) {
        return (PurchaseOrderApproveRequestQueryOrder) getEnum(PurchaseOrderApproveRequestQueryOrder.class, value);
    }

}
