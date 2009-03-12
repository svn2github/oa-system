package net.sourceforge.model.business.rule.query;

import org.apache.commons.lang.enums.Enum;

public class ApproverDelegateQueryCondition extends Enum{

	/*id*/
	public static final ApproverDelegateQueryCondition ID_EQ =
		 new ApproverDelegateQueryCondition("id_eq");

	/*keyPropertyList*/

	/*kmtoIdList*/

	/*mtoIdList*/
	public static final ApproverDelegateQueryCondition ORIGINALAPPROVER_ID_EQ =
		 new ApproverDelegateQueryCondition("originalApprover_id_eq");
	public static final ApproverDelegateQueryCondition DELEGATEAPPROVER_ID_EQ =
		 new ApproverDelegateQueryCondition("delegateApprover_id_eq");

	/*property*/
	public static final ApproverDelegateQueryCondition TYPE_EQ =
		 new ApproverDelegateQueryCondition("type_eq");
	public static final ApproverDelegateQueryCondition FROMDATE_EQ =
		 new ApproverDelegateQueryCondition("fromDate_eq");
	public static final ApproverDelegateQueryCondition TODATE_EQ =
		 new ApproverDelegateQueryCondition("toDate_eq");

    public static final ApproverDelegateQueryCondition FROMDATE_GE = 
        new ApproverDelegateQueryCondition("FROMDATE_GE");

    public static final ApproverDelegateQueryCondition FROMDATE_LT = 
        new ApproverDelegateQueryCondition("FROMDATE_LT");

    public static final ApproverDelegateQueryCondition TODATE_GE = 
        new ApproverDelegateQueryCondition("TODATE_GE");

    public static final ApproverDelegateQueryCondition TODATE_LT =
        new ApproverDelegateQueryCondition("TODATE_LT");

	protected ApproverDelegateQueryCondition(String value) {
		super(value);
	}
	
	public static ApproverDelegateQueryCondition getEnum(String value) {
        return (ApproverDelegateQueryCondition) getEnum(ApproverDelegateQueryCondition.class, value);
    }

}
