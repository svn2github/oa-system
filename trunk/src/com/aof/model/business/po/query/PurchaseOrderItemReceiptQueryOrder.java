package com.aof.model.business.po.query;

import org.apache.commons.lang.enums.Enum;

public class PurchaseOrderItemReceiptQueryOrder extends Enum{

	/*id*/
	public static final PurchaseOrderItemReceiptQueryOrder ID = new PurchaseOrderItemReceiptQueryOrder("id");

	/*property*/
	public static final PurchaseOrderItemReceiptQueryOrder RECEIVEDATE1 = new PurchaseOrderItemReceiptQueryOrder("receiveDate1");
	public static final PurchaseOrderItemReceiptQueryOrder RECEIVEQTY1 = new PurchaseOrderItemReceiptQueryOrder("receiveQty1");
	public static final PurchaseOrderItemReceiptQueryOrder RECEIVEDATE2 = new PurchaseOrderItemReceiptQueryOrder("receiveDate2");
	public static final PurchaseOrderItemReceiptQueryOrder RECEIVEQTY2 = new PurchaseOrderItemReceiptQueryOrder("receiveQty2");
	public static final PurchaseOrderItemReceiptQueryOrder EXPORTSTATUS = new PurchaseOrderItemReceiptQueryOrder("exportStatus");
    
	protected PurchaseOrderItemReceiptQueryOrder(String value) {
		super(value);
	}
	
	public static PurchaseOrderItemReceiptQueryOrder getEnum(String value) {
        return (PurchaseOrderItemReceiptQueryOrder) getEnum(PurchaseOrderItemReceiptQueryOrder.class, value);
    }

}
