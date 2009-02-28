package com.aof.model.business.pr.query;

import org.apache.commons.lang.enums.Enum;

public class PurchaseRequestQueryCondition extends Enum{

	/*id*/
	public static final PurchaseRequestQueryCondition ID_EQ =
		 new PurchaseRequestQueryCondition("id_eq");

	/*keyPropertyList*/

	/*kmtoIdList*/

	/*mtoIdList*/
	public static final PurchaseRequestQueryCondition PURCHASESUBCATEGORY_ID_EQ =
		 new PurchaseRequestQueryCondition("purchaseSubCategory_id_eq");
	public static final PurchaseRequestQueryCondition DEPARTMENT_ID_EQ =
		 new PurchaseRequestQueryCondition("department_id_eq");
    public static final PurchaseRequestQueryCondition DEPARTMENT_ID_IN =
         new PurchaseRequestQueryCondition("department_id_in");
	public static final PurchaseRequestQueryCondition CAPEX_ID_EQ =
		 new PurchaseRequestQueryCondition("capex_id_eq");
	public static final PurchaseRequestQueryCondition YEARLYBUDGET_ID_EQ =
		 new PurchaseRequestQueryCondition("yearlyBudget_id_eq");
	public static final PurchaseRequestQueryCondition CURRENCY_CODE_EQ =
		 new PurchaseRequestQueryCondition("currency_code_eq");
	public static final PurchaseRequestQueryCondition REQUESTOR_ID_EQ =
		 new PurchaseRequestQueryCondition("requestor_id_eq");
	public static final PurchaseRequestQueryCondition CREATOR_ID_EQ =
		 new PurchaseRequestQueryCondition("creator_id_eq");
	public static final PurchaseRequestQueryCondition PURCHASER_ID_EQ =
		 new PurchaseRequestQueryCondition("purchaser_id_eq");

	/*property*/
	public static final PurchaseRequestQueryCondition TITLE_LIKE =
		 new PurchaseRequestQueryCondition("title_like");
	public static final PurchaseRequestQueryCondition DESCRIPTION_LIKE =
		 new PurchaseRequestQueryCondition("description_like");
	public static final PurchaseRequestQueryCondition STATUS_EQ =
		 new PurchaseRequestQueryCondition("status_eq");
	public static final PurchaseRequestQueryCondition REQUESTDATE_EQ =
		 new PurchaseRequestQueryCondition("requestDate_eq");
	public static final PurchaseRequestQueryCondition CREATEDATE_EQ =
		 new PurchaseRequestQueryCondition("createDate_eq");
	public static final PurchaseRequestQueryCondition APPROVEREQUESTID_EQ =
		 new PurchaseRequestQueryCondition("approveRequestId_eq");

    public static final PurchaseRequestQueryCondition REQUESTOR_ID_NOT_EQ = 
        new PurchaseRequestQueryCondition("requestor_id_not_eq");

    public static final PurchaseRequestQueryCondition REQUESTOR_NAME_LIKE =
        new PurchaseRequestQueryCondition("requestor_name_like");

    public static final PurchaseRequestQueryCondition CAN_PURCHASED_BY =
        new PurchaseRequestQueryCondition("can_purchased_by");

    public static final PurchaseRequestQueryCondition STATUS_IN_2 = 
        new PurchaseRequestQueryCondition("STATUS_IN_2");

    public static final PurchaseRequestQueryCondition SITE_ID_EQ = 
        new PurchaseRequestQueryCondition("site_eq");

    public static final PurchaseRequestQueryCondition ID_LIKE = 
        new PurchaseRequestQueryCondition("id_like");

    public static final PurchaseRequestQueryCondition AMOUNT_GE = 
        new PurchaseRequestQueryCondition("AMOUNT_GE");

    public static final PurchaseRequestQueryCondition AMOUNT_LE = 
        new PurchaseRequestQueryCondition("AMOUNT_LE");

    public static final PurchaseRequestQueryCondition PURCHASECATEGORY_ID_EQ = 
        new PurchaseRequestQueryCondition("PURCHASECATEGORY_ID_EQ");

    public static final PurchaseRequestQueryCondition CREATEDATE_GE = 
        new PurchaseRequestQueryCondition("CREATEDATE_GE");
    
    public static final PurchaseRequestQueryCondition CREATEDATE_LE = 
        new PurchaseRequestQueryCondition("CREATEDATE_LE");
    
    public static final PurchaseRequestQueryCondition CREATEDATE_LT = 
        new PurchaseRequestQueryCondition("CREATEDATE_LT");

    public static final PurchaseRequestQueryCondition ISDELEGATE_EQ = 
        new PurchaseRequestQueryCondition("ISDELEGATE_EQ");
    
    public static final PurchaseRequestQueryCondition APPROVEDATE_GE = 
        new PurchaseRequestQueryCondition("APPROVEDATE_GE");
    
    public static final PurchaseRequestQueryCondition APPROVEDATE_LE = 
        new PurchaseRequestQueryCondition("APPROVEDATE_LE");
    
    

	protected PurchaseRequestQueryCondition(String value) {
		super(value);
	}
	
	public static PurchaseRequestQueryCondition getEnum(String value) {
        return (PurchaseRequestQueryCondition) getEnum(PurchaseRequestQueryCondition.class, value);
    }

}
