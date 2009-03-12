/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.metadata;

import java.util.HashSet;
import java.util.Set;

public class RuleType extends MetadataDetailEnum {
    public final static RuleType CAPEX_APPROVAL_RULES =  new RuleType(1, "Capex Approval Rules", MetadataType.RULE_TYPE);
    public final static RuleType PR_APPROVAL_RULES =  new RuleType(2, "PR Approval Rules", MetadataType.RULE_TYPE);
    public final static RuleType PURCHASING_RULES =  new RuleType(3, "Purchasing Rules", MetadataType.RULE_TYPE);
    public final static RuleType PURCHASING_PRICE_CONTROL_RULES =  new RuleType(4, "Purchasing Price Control Rules", MetadataType.RULE_TYPE);
    public final static RuleType PO_APPROVAL_RULES =  new RuleType(5, "PO Approval Rules", MetadataType.RULE_TYPE);
    public final static RuleType EXPENSE_APPROVAL_RULES =  new RuleType(6, "Expense Approval Rules", MetadataType.RULE_TYPE);
    public final static RuleType EXPENSE_CLAIM_RULES =  new RuleType(7, "Expense Claim Rules", MetadataType.RULE_TYPE);
    public final static RuleType TRAVEL_APPROVAL_RULES =  new RuleType(8, "Travel Approval Rules", MetadataType.RULE_TYPE);
    
    public final static RuleType YEARLY_BUDGET_FILTERS =  new RuleType(10, "Yearly Budget Filters", MetadataType.RULE_TYPE);
    public final static RuleType CAPEX_FILTERS =  new RuleType(11, "Capex Filters", MetadataType.RULE_TYPE);
    public final static RuleType PURCHASE_REQUEST_FILTERS =  new RuleType(12, "Purchase Request Filters", MetadataType.RULE_TYPE);
    public final static RuleType PURCHASE_ORDER_FILTERS =  new RuleType(13, "Purchase Order Filters", MetadataType.RULE_TYPE);
    public final static RuleType EXPENSE_FILTERS =  new RuleType(14, "Expense Filters", MetadataType.RULE_TYPE);
    public final static RuleType TRAVEL_APPLICATION_FILTERS =  new RuleType(15, "Travel Application Filters", MetadataType.RULE_TYPE);

    static {
        Set s = new HashSet();
        s.add(ConditionType.DEPARTMENT);
        s.add(ConditionType.PURCHASE_CATEGORY);
        //s.add(ConditionType.PURCHASE_SUBCATEGORY);
        s.add(ConditionType.CAPEX_REQUEST_AMOUNT);
        s.add(ConditionType.OVER_BUDGET);
        s.add(ConditionType.OVER_BUDGET_PERCENTAGE);
        s.add(ConditionType.WITH_BUDGET);
        CAPEX_APPROVAL_RULES.supportedConditions = s;
        CAPEX_APPROVAL_RULES.consequenceType = ConsequenceType.APPROVER;
        CAPEX_APPROVAL_RULES.prefixUrl = "CapexApproval";
        
        s = new HashSet();
        s.add(ConditionType.DEPARTMENT);
        s.add(ConditionType.PURCHASE_CATEGORY);
        //s.add(ConditionType.PURCHASE_SUBCATEGORY);
        s.add(ConditionType.PR_REQUEST_AMOUNT);
        s.add(ConditionType.OVER_BUDGET);
        s.add(ConditionType.OVER_BUDGET_PERCENTAGE);
        s.add(ConditionType.WITH_BUDGET);
        s.add(ConditionType.PR_REQUEST_ITEM_MAX_PRICE);
        PR_APPROVAL_RULES.supportedConditions = s;
        PR_APPROVAL_RULES.consequenceType = ConsequenceType.APPROVER;
        PR_APPROVAL_RULES.prefixUrl = "PRApproval";

        s = new HashSet();
        s.add(ConditionType.DEPARTMENT);
        s.add(ConditionType.PURCHASE_CATEGORY);
        //s.add(ConditionType.PURCHASE_SUBCATEGORY);
        PURCHASING_RULES.supportedConditions = s;
        PURCHASING_RULES.consequenceType = ConsequenceType.PURCHASER;
        PURCHASING_RULES.prefixUrl = "Purchasing";

        s = new HashSet();
        s.add(ConditionType.DEPARTMENT);
        s.add(ConditionType.PURCHASE_CATEGORY);
        //s.add(ConditionType.PURCHASE_SUBCATEGORY);
        s.add(ConditionType.DIFFERENCE_AMOUNT_OF_PR_PURCHASE_AND_APPROVED);
        s.add(ConditionType.DIFFERENCE_AMOUNT_PERCENTAGE_OF_PR_PURCHASE_AND_APPROVED);
        PURCHASING_PRICE_CONTROL_RULES.supportedConditions = s;
        PURCHASING_PRICE_CONTROL_RULES.consequenceType = ConsequenceType.ACCEPTABLE;
        PURCHASING_PRICE_CONTROL_RULES.prefixUrl = "PurchasingPriceControl";

        s = new HashSet();
        s.add(ConditionType.DEPARTMENT);
        s.add(ConditionType.PURCHASE_CATEGORY);
        //s.add(ConditionType.PURCHASE_SUBCATEGORY);
        s.add(ConditionType.PO_PURCHASE_AMOUNT);
        PO_APPROVAL_RULES.supportedConditions = s;
        PO_APPROVAL_RULES.consequenceType = ConsequenceType.APPROVER;
        PO_APPROVAL_RULES.prefixUrl = "POApproval";

        s = new HashSet();
        s.add(ConditionType.DEPARTMENT);
        s.add(ConditionType.EXPENSE_CATEGORY);
        s.add(ConditionType.EXPENSE_ENTERED_AMOUNT);
        s.add(ConditionType.OVER_BUDGET);
        s.add(ConditionType.OVER_BUDGET_PERCENTAGE);
        s.add(ConditionType.WITH_BUDGET);
        EXPENSE_APPROVAL_RULES.supportedConditions = s;
        EXPENSE_APPROVAL_RULES.consequenceType = ConsequenceType.APPROVER;
        EXPENSE_APPROVAL_RULES.prefixUrl = "ExpenseApproval";

        s = new HashSet();
        s.add(ConditionType.DEPARTMENT);
        s.add(ConditionType.EXPENSE_CATEGORY);
        //s.add(ConditionType.EXPENSE_SUBCATEGORY);
        s.add(ConditionType.DIFFERENCE_AMOUNT_OF_EXPENSE_CLAIMED_AND_ENTERED);
        s.add(ConditionType.DIFFERENCE_AMOUNT_PERCENTAGE_OF_EXPENSE_CLAIMED_AND_ENTERED);
        s.add(ConditionType.OVER_BUDGET);
        s.add(ConditionType.OVER_BUDGET_PERCENTAGE);
        s.add(ConditionType.WITH_BUDGET);        
        EXPENSE_CLAIM_RULES.supportedConditions = s;
        EXPENSE_CLAIM_RULES.consequenceType = ConsequenceType.ACCEPTABLE;
        EXPENSE_CLAIM_RULES.prefixUrl = "ExpenseClaim";

        s = new HashSet();
        s.add(ConditionType.DEPARTMENT);
        s.add(ConditionType.TRAVEL_MODE);
        s.add(ConditionType.TRAVEL_FROM);
        s.add(ConditionType.TRAVEL_TO);
        s.add(ConditionType.TRAVEL_FEE);
        //s.add(ConditionType.TRAVEL_AMOUNT);
        TRAVEL_APPROVAL_RULES.supportedConditions = s;
        TRAVEL_APPROVAL_RULES.consequenceType = ConsequenceType.APPROVER;
        TRAVEL_APPROVAL_RULES.prefixUrl = "TravelApproval";
        
        s = new HashSet();
        s.add(ConditionType.DEPARTMENT);
        s.add(ConditionType.PURCHASE_CATEGORY);
        s.add(ConditionType.BUDGET_TYPE);
        s.add(ConditionType.YEARLY_BUDGET_AMOUNT);
        s.add(ConditionType.REMAIN_AMOUNT);
        YEARLY_BUDGET_FILTERS.supportedConditions = s;
        YEARLY_BUDGET_FILTERS.consequenceType = ConsequenceType.NOTIFIER;
        YEARLY_BUDGET_FILTERS.prefixUrl = "YearlyBudgetFilter";

        s = new HashSet();
        s.add(ConditionType.DEPARTMENT);
        s.add(ConditionType.PURCHASE_CATEGORY);
        s.add(ConditionType.CAPEX_REQUEST_AMOUNT);
        s.add(ConditionType.REMAIN_AMOUNT);
        CAPEX_FILTERS.supportedConditions = s;
        CAPEX_FILTERS.consequenceType = ConsequenceType.NOTIFIER;
        CAPEX_FILTERS.prefixUrl = "CapexFilter";

        s = new HashSet();
        s.add(ConditionType.DEPARTMENT);
        s.add(ConditionType.PURCHASE_CATEGORY);
        s.add(ConditionType.PR_REQUEST_AMOUNT);
        PURCHASE_REQUEST_FILTERS.supportedConditions = s;
        PURCHASE_REQUEST_FILTERS.consequenceType = ConsequenceType.NOTIFIER;
        PURCHASE_REQUEST_FILTERS.prefixUrl = "PurchaseRequestFilter";

        s = new HashSet();
        s.add(ConditionType.DEPARTMENT);
        s.add(ConditionType.PURCHASE_CATEGORY);
        s.add(ConditionType.PO_PURCHASE_AMOUNT);
        PURCHASE_ORDER_FILTERS.supportedConditions = s;
        PURCHASE_ORDER_FILTERS.consequenceType = ConsequenceType.NOTIFIER;
        PURCHASE_ORDER_FILTERS.prefixUrl = "PurchaseOrderFilter";

        s = new HashSet();
        s.add(ConditionType.DEPARTMENT);
        s.add(ConditionType.EXPENSE_CATEGORY);
        s.add(ConditionType.EXPENSE_ENTERED_AMOUNT);
        EXPENSE_FILTERS.supportedConditions = s;
        EXPENSE_FILTERS.consequenceType = ConsequenceType.NOTIFIER;
        EXPENSE_FILTERS.prefixUrl = "ExpenseFilter";
    
        s = new HashSet();
        s.add(ConditionType.DEPARTMENT);
        s.add(ConditionType.TRAVEL_MODE);
        s.add(ConditionType.TRAVEL_FROM);
        s.add(ConditionType.TRAVEL_TO);
        TRAVEL_APPLICATION_FILTERS.supportedConditions = s;
        TRAVEL_APPLICATION_FILTERS.consequenceType = ConsequenceType.NOTIFIER;
        TRAVEL_APPLICATION_FILTERS.prefixUrl = "TravelApplicationFilter";
    }
    
    private Set supportedConditions;
    private ConsequenceType consequenceType;
    private String prefixUrl;
    
    public RuleType() {
    }

    private RuleType(int metadataId, String defaultLabel, MetadataType type) {
        super(metadataId, defaultLabel, type);
    }
    
    /**
     * @return Returns the supportedConditions.
     */
    public Set getSupportedConditions() {
        return supportedConditions;
    }

    /**
     * @return Returns the consequenceType.
     */
    public ConsequenceType getConsequenceType() {
        return consequenceType;
    }

    /**
     * @return Returns the prefixUrl.
     */
    public String getPrefixUrl() {
        return prefixUrl;
    }


}
