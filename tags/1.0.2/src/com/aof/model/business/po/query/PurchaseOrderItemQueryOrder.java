package com.aof.model.business.po.query;

import org.apache.commons.lang.enums.Enum;

public class PurchaseOrderItemQueryOrder extends Enum{

    public static final PurchaseOrderItemQueryOrder ID = new PurchaseOrderItemQueryOrder("id");
	public static final PurchaseOrderItemQueryOrder ITEMSPEC = new PurchaseOrderItemQueryOrder("itemSpec");
    public static final PurchaseOrderItemQueryOrder PR_ID = new PurchaseOrderItemQueryOrder("purchaseRequest_id");
    
	protected PurchaseOrderItemQueryOrder(String value) {
		super(value);
	}
	
	public static PurchaseOrderItemQueryOrder getEnum(String value) {
        return (PurchaseOrderItemQueryOrder) getEnum(PurchaseOrderItemQueryOrder.class, value);
    }

}
