/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.metadata;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ApproverDelegateType extends MetadataDetailEnum {
    
    public static final ApproverDelegateType NONBUDGET_CAPEX_APPROVER = new ApproverDelegateType(1, "Non-Budget Capex Approver", MetadataType.APPROVER_DELEGATE_TYPE);
    public static final ApproverDelegateType PURCHASE_REQUEST_APPROVER = new ApproverDelegateType(2, "Purchase Request Approver", MetadataType.APPROVER_DELEGATE_TYPE);
    public static final ApproverDelegateType PURCHASE_ORDER_APPROVER = new ApproverDelegateType(3, "Purchase Order Approver", MetadataType.APPROVER_DELEGATE_TYPE);
    public static final ApproverDelegateType EXPENSE_APPROVER = new ApproverDelegateType(4, "Expense Approver", MetadataType.APPROVER_DELEGATE_TYPE);
    public static final ApproverDelegateType TRAVEL_APPLICATION_APPROVER = new ApproverDelegateType(5, "Travel Application Approver", MetadataType.APPROVER_DELEGATE_TYPE);

    public ApproverDelegateType() {
    	
    }
    
    private ApproverDelegateType(int metadataId, String defaultLabel, MetadataType type) {
        super(metadataId, defaultLabel, type);
    }
    
    public static ApproverDelegateType getApproverDelegateTypeByRuleType(RuleType ruleType)
    {
    	return (ApproverDelegateType) ruleToAdMap.get(ruleType);
    }
    
    public static RuleType getRuleTypeByApproverDelegateType(ApproverDelegateType type)
    {
    	return (RuleType) adToRuleMap.get(type);
    }
    
    private static Map ruleToAdMap=new HashMap();
    private static Map adToRuleMap=new HashMap();
    
    static{
    	ruleToAdMap.put(RuleType.CAPEX_APPROVAL_RULES,NONBUDGET_CAPEX_APPROVER);
    	ruleToAdMap.put(RuleType.EXPENSE_APPROVAL_RULES,EXPENSE_APPROVER);
    	ruleToAdMap.put(RuleType.TRAVEL_APPROVAL_RULES,TRAVEL_APPLICATION_APPROVER);
    	ruleToAdMap.put(RuleType.PO_APPROVAL_RULES,PURCHASE_ORDER_APPROVER);
    	ruleToAdMap.put(RuleType.PR_APPROVAL_RULES,PURCHASE_REQUEST_APPROVER);
    	
    	for (Iterator iter = ruleToAdMap.keySet().iterator(); iter.hasNext();) {
			Object key = iter.next();
			adToRuleMap.put(ruleToAdMap.get(key),key);
		}
    	
    }

}


