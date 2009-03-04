package com.aof.model.business.po.query;

import org.apache.commons.lang.enums.Enum;

public class PurchaseOrderItemQueryCondition extends Enum{

    public static final PurchaseOrderItemQueryCondition ID_EQ = new PurchaseOrderItemQueryCondition("id_eq");
    public static final PurchaseOrderItemQueryCondition PR_ID_LIKE = new PurchaseOrderItemQueryCondition("pr_id_like");
    public static final PurchaseOrderItemQueryCondition PO_STATUS_EQ = new PurchaseOrderItemQueryCondition("po_status_eq");
    public static final PurchaseOrderItemQueryCondition PO_STATUS_IN2 = new PurchaseOrderItemQueryCondition("po_status_in2");
    public static final PurchaseOrderItemQueryCondition ITEMSPEC_LIKE = new PurchaseOrderItemQueryCondition("itemSpec_like");
    public static final PurchaseOrderItemQueryCondition NOT_RECEIVE_ALL = new PurchaseOrderItemQueryCondition("not_receive_all");
    public static final PurchaseOrderItemQueryCondition PR_REQUESTOR_OR_PO_INSPECTOR_EQ = new PurchaseOrderItemQueryCondition("pr_requestor_or_po_inspector_eq");

	protected PurchaseOrderItemQueryCondition(String value) {
		super(value);
	}
	
	public static PurchaseOrderItemQueryCondition getEnum(String value) {
        return (PurchaseOrderItemQueryCondition) getEnum(PurchaseOrderItemQueryCondition.class, value);
    }

}
