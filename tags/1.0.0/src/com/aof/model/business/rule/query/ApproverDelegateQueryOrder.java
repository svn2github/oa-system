package com.aof.model.business.rule.query;

import org.apache.commons.lang.enums.Enum;

public class ApproverDelegateQueryOrder extends Enum{

	/*id*/
	public static final ApproverDelegateQueryOrder ID = new ApproverDelegateQueryOrder("id");

	/*property*/
	public static final ApproverDelegateQueryOrder TYPE = new ApproverDelegateQueryOrder("type");
	public static final ApproverDelegateQueryOrder FROMDATE = new ApproverDelegateQueryOrder("fromDate");
	public static final ApproverDelegateQueryOrder TODATE = new ApproverDelegateQueryOrder("toDate");

    public static final ApproverDelegateQueryOrder DELEGATEAPPROVER_NAME = new ApproverDelegateQueryOrder("delegateApprover.name");;
    
	protected ApproverDelegateQueryOrder(String value) {
		super(value);
	}
	
	public static ApproverDelegateQueryOrder getEnum(String value) {
        return (ApproverDelegateQueryOrder) getEnum(ApproverDelegateQueryOrder.class, value);
    }

}
