package com.aof.model.business.po.query;

import org.apache.commons.lang.enums.Enum;

public class PurchaseOrderItemReceiptQueryCondition extends Enum{

	/*id*/
	public static final PurchaseOrderItemReceiptQueryCondition ID_EQ =
		 new PurchaseOrderItemReceiptQueryCondition("id_eq");

	/*keyPropertyList*/

	/*kmtoIdList*/

	/*mtoIdList*/
	public static final PurchaseOrderItemReceiptQueryCondition PURCHASEORDERITEM_ID_EQ =
		 new PurchaseOrderItemReceiptQueryCondition("purchaseOrderItem_id_eq");
	public static final PurchaseOrderItemReceiptQueryCondition RECEIVER1_ID_EQ =
		 new PurchaseOrderItemReceiptQueryCondition("receiver1_id_eq");
	public static final PurchaseOrderItemReceiptQueryCondition RECEIVER2_ID_EQ =
		 new PurchaseOrderItemReceiptQueryCondition("receiver2_id_eq");

	/*property*/
	public static final PurchaseOrderItemReceiptQueryCondition RECEIVEDATE1_EQ =
		 new PurchaseOrderItemReceiptQueryCondition("receiveDate1_eq");
	public static final PurchaseOrderItemReceiptQueryCondition RECEIVEQTY1_EQ =
		 new PurchaseOrderItemReceiptQueryCondition("receiveQty1_eq");
	public static final PurchaseOrderItemReceiptQueryCondition RECEIVEDATE2_EQ =
		 new PurchaseOrderItemReceiptQueryCondition("receiveDate2_eq");
	public static final PurchaseOrderItemReceiptQueryCondition RECEIVEQTY2_EQ =
		 new PurchaseOrderItemReceiptQueryCondition("receiveQty2_eq");
	public static final PurchaseOrderItemReceiptQueryCondition EXPORTSTATUS_EQ =
		 new PurchaseOrderItemReceiptQueryCondition("exportStatus_eq");

	protected PurchaseOrderItemReceiptQueryCondition(String value) {
		super(value);
	}
	
	public static PurchaseOrderItemReceiptQueryCondition getEnum(String value) {
        return (PurchaseOrderItemReceiptQueryCondition) getEnum(PurchaseOrderItemReceiptQueryCondition.class, value);
    }

}
