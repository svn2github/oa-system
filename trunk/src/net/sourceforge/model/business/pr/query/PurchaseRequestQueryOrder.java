package net.sourceforge.model.business.pr.query;

import org.apache.commons.lang.enums.Enum;

public class PurchaseRequestQueryOrder extends Enum{

	/*id*/
	public static final PurchaseRequestQueryOrder ID = new PurchaseRequestQueryOrder("id");

	/*property*/
	public static final PurchaseRequestQueryOrder TITLE = new PurchaseRequestQueryOrder("title");
	public static final PurchaseRequestQueryOrder DESCRIPTION = new PurchaseRequestQueryOrder("description");
	public static final PurchaseRequestQueryOrder STATUS = new PurchaseRequestQueryOrder("status");
	public static final PurchaseRequestQueryOrder REQUESTDATE = new PurchaseRequestQueryOrder("requestDate");
	public static final PurchaseRequestQueryOrder CREATEDATE = new PurchaseRequestQueryOrder("createDate");
	public static final PurchaseRequestQueryOrder APPROVEREQUESTID = new PurchaseRequestQueryOrder("approveRequestId");
    public static final PurchaseRequestQueryOrder REQUESTOR_NAME = new PurchaseRequestQueryOrder("requestor_name");
    public static final PurchaseRequestQueryOrder AMOUNT = new PurchaseRequestQueryOrder("amount");
    public static final PurchaseRequestQueryOrder PURCHASER_NAME = new PurchaseRequestQueryOrder("purchaser_name");
    public static final PurchaseRequestQueryOrder PURCHASER_CATEGORY = new PurchaseRequestQueryOrder("purchaser_category");
    
	protected PurchaseRequestQueryOrder(String value) {
		super(value);
	}
	
	public static PurchaseRequestQueryOrder getEnum(String value) {
        return (PurchaseRequestQueryOrder) getEnum(PurchaseRequestQueryOrder.class, value);
    }

}
