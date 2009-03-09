package com.aof.model.business.po.query;

import org.apache.commons.lang.enums.Enum;

public class PurchaseOrderQueryOrder extends Enum{

	/*id*/
	public static final PurchaseOrderQueryOrder ID = new PurchaseOrderQueryOrder("id");

	/*property*/
	public static final PurchaseOrderQueryOrder ERPNO = new PurchaseOrderQueryOrder("erpNo");
	public static final PurchaseOrderQueryOrder TITLE = new PurchaseOrderQueryOrder("title");
	public static final PurchaseOrderQueryOrder DESCRIPTION = new PurchaseOrderQueryOrder("description");
	public static final PurchaseOrderQueryOrder AMOUNT = new PurchaseOrderQueryOrder("amount");
	public static final PurchaseOrderQueryOrder EXCHANGERATE = new PurchaseOrderQueryOrder("exchangeRate");
	public static final PurchaseOrderQueryOrder STATUS = new PurchaseOrderQueryOrder("status");
	public static final PurchaseOrderQueryOrder CREATEDATE = new PurchaseOrderQueryOrder("createDate");
	public static final PurchaseOrderQueryOrder EXPORTSTATUS = new PurchaseOrderQueryOrder("exportStatus");
	public static final PurchaseOrderQueryOrder APPROVEREQUESTID = new PurchaseOrderQueryOrder("approveRequestId");
    public static final PurchaseOrderQueryOrder SUPPLIER_NAME = new PurchaseOrderQueryOrder("supplier_name");
    public static final PurchaseOrderQueryOrder CATEGORY_DESCRIPTION = new PurchaseOrderQueryOrder("category_descrption");
    public static final PurchaseOrderQueryOrder SUBCATEGORY_DESCRIPTION = new PurchaseOrderQueryOrder("subCategory_descrption");
    public static final PurchaseOrderQueryOrder CREATEUSER_NAME = new PurchaseOrderQueryOrder("createUser_name");
    public static final PurchaseOrderQueryOrder CONFIRMDATE = new PurchaseOrderQueryOrder("confirmDate");
    
	protected PurchaseOrderQueryOrder(String value) {
		super(value);
	}
	
	public static PurchaseOrderQueryOrder getEnum(String value) {
        return (PurchaseOrderQueryOrder) getEnum(PurchaseOrderQueryOrder.class, value);
    }

}
