/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.model.business.pr.query;

import org.apache.commons.lang.enums.Enum;

/**
 * PurchaseRequestApproveRequest查询顺序的枚举类
 * 
 * @author nicebean
 * @version 1.0 (2005-12-15)
 */
public class PurchaseRequestApproveRequestQueryOrder extends Enum {

    public static final PurchaseRequestApproveRequestQueryOrder SEQ = new PurchaseRequestApproveRequestQueryOrder("seq");

    public static final PurchaseRequestApproveRequestQueryOrder STATUS = new PurchaseRequestApproveRequestQueryOrder("status");

    public static final PurchaseRequestApproveRequestQueryOrder APPROVEDATE = new PurchaseRequestApproveRequestQueryOrder("approveDate");

    public static final PurchaseRequestApproveRequestQueryOrder COMMENT = new PurchaseRequestApproveRequestQueryOrder("comment");

    public static final PurchaseRequestApproveRequestQueryOrder CANMODIFY = new PurchaseRequestApproveRequestQueryOrder("canModify");

    public static final PurchaseRequestApproveRequestQueryOrder PURCHASEREQUEST_ID = new PurchaseRequestApproveRequestQueryOrder("purchaseRequest_id");

    protected PurchaseRequestApproveRequestQueryOrder(String value) {
        super(value);
    }

    public static PurchaseRequestApproveRequestQueryOrder getEnum(String value) {
        return (PurchaseRequestApproveRequestQueryOrder) getEnum(PurchaseRequestApproveRequestQueryOrder.class, value);
    }

}
