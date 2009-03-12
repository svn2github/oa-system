/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.metadata;

import java.util.HashSet;
import java.util.Set;

public class ConditionType extends MetadataDetailEnum {
    public final static ConditionType DEPARTMENT =  new ConditionType(1, "Department", MetadataType.CONDITION_TYPE);
    public final static ConditionType PURCHASE_CATEGORY =  new ConditionType(2, "Purchase Category", MetadataType.CONDITION_TYPE);
    //public final static ConditionType PURCHASE_SUBCATEGORY =  new ConditionType(3, "Purchase SubCategory", MetadataType.CONDITION_TYPE);
    public final static ConditionType CAPEX_REQUEST_AMOUNT =  new ConditionType(4, "Capex Request Amount", MetadataType.CONDITION_TYPE);
    public final static ConditionType PR_REQUEST_AMOUNT =  new ConditionType(5, "PR Request Amount", MetadataType.CONDITION_TYPE);
    //public final static ConditionType DIFFERENCE_AMOUNT_OF_ACTUAL_BUDGET_AND_PR_REQUEST =  new ConditionType(6, "Actual Budget Amount - PR Request Amount", MetadataType.CONDITION_TYPE);
    //public final static ConditionType DIFFERENCE_AMOUNT_PERCENTAGE_OF_ACTUAL_BUDGET_AND_PR_REQUEST =  new ConditionType(7, "(Actual Budget Amount - PR Request Amount) / Budget Amount %", MetadataType.CONDITION_TYPE);
    public final static ConditionType DIFFERENCE_AMOUNT_OF_PR_PURCHASE_AND_APPROVED = new ConditionType(8, "PR Purchase Amount - PR Approved Amount", MetadataType.CONDITION_TYPE);
    public final static ConditionType DIFFERENCE_AMOUNT_PERCENTAGE_OF_PR_PURCHASE_AND_APPROVED = new ConditionType(9, "(PR Purchase Amount - PR Approved Amount) / PR Approved Amount %", MetadataType.CONDITION_TYPE);
    public final static ConditionType PO_PURCHASE_AMOUNT =  new ConditionType(10, "PO Purchase Amount", MetadataType.CONDITION_TYPE);
    public final static ConditionType EXPENSE_CATEGORY =  new ConditionType(11, "Expense Category", MetadataType.CONDITION_TYPE);
    public final static ConditionType EXPENSE_SUBCATEGORY =  new ConditionType(12, "Expense SubCategory", MetadataType.CONDITION_TYPE);
    public final static ConditionType EXPENSE_ENTERED_AMOUNT =  new ConditionType(13, "Expense Entered Amount", MetadataType.CONDITION_TYPE);
    public final static ConditionType DIFFERENCE_AMOUNT_OF_EXPENSE_CLAIMED_AND_ENTERED =  new ConditionType(14, "Expense Claimed Amount - Expense Entered Amount", MetadataType.CONDITION_TYPE);
    public final static ConditionType DIFFERENCE_AMOUNT_PERCENTAGE_OF_EXPENSE_CLAIMED_AND_ENTERED =  new ConditionType(15, "(Expense Claimed Amount - Expense Entered Amount) / Expense Entered Amount %", MetadataType.CONDITION_TYPE);
    public final static ConditionType TRAVEL_FROM =  new ConditionType(16, "Travel From", MetadataType.CONDITION_TYPE);
    public final static ConditionType TRAVEL_TO =  new ConditionType(17, "Travel To", MetadataType.CONDITION_TYPE);
    public final static ConditionType TRAVEL_MODE =  new ConditionType(18, "Travel Mode", MetadataType.CONDITION_TYPE);
    public final static ConditionType OVER_BUDGET =  new ConditionType(19, "Over Budget", MetadataType.CONDITION_TYPE);
    public final static ConditionType OVER_BUDGET_PERCENTAGE =  new ConditionType(20, "Over Budget (%)", MetadataType.CONDITION_TYPE);

    public final static ConditionType BUDGET_TYPE =  new ConditionType(21, "Budget Type", MetadataType.CONDITION_TYPE);
    public final static ConditionType REMAIN_AMOUNT =  new ConditionType(22, "Remaining Amount", MetadataType.CONDITION_TYPE);
    public final static ConditionType YEARLY_BUDGET_AMOUNT =  new ConditionType(23, "Yearly Budget Amount", MetadataType.CONDITION_TYPE);

    public final static ConditionType WITH_BUDGET =  new ConditionType(24, "With Budget", MetadataType.CONDITION_TYPE);

    public final static ConditionType TRAVEL_FEE = new ConditionType(25, "Expense Fee", MetadataType.CONDITION_TYPE);
    public final static ConditionType PR_REQUEST_ITEM_MAX_PRICE =  new ConditionType(26, "PR Request Item Max Price", MetadataType.CONDITION_TYPE);

    static {
        Set s = new HashSet();
        s.add(ConditionCompareType.EQUAL);
        DEPARTMENT.supportedCompareTypes = s;
        DEPARTMENT.typeString = "Department";
        
        s = new HashSet();
        s.add(ConditionCompareType.EQUAL);
        PURCHASE_CATEGORY.supportedCompareTypes = s;
        PURCHASE_CATEGORY.typeString = "PurchaseCategory";
        
        //s = new HashSet();
        //s.add(ConditionCompareType.EQUAL);
        //PURCHASE_SUBCATEGORY.supportedCompareTypes = s;
        //PURCHASE_SUBCATEGORY.typeString = "PurchaseCategory";
        
        s = new HashSet();
        s.add(ConditionCompareType.LESS_THAN);
        s.add(ConditionCompareType.LESS_OR_EQUAL);
        s.add(ConditionCompareType.EQUAL);
        s.add(ConditionCompareType.GREATER_OR_EQUAL);
        s.add(ConditionCompareType.GREATER_THAN);
        CAPEX_REQUEST_AMOUNT.supportedCompareTypes = s;
        CAPEX_REQUEST_AMOUNT.typeString = "Number";
        
        s = new HashSet();
        s.add(ConditionCompareType.LESS_THAN);
        s.add(ConditionCompareType.LESS_OR_EQUAL);
        s.add(ConditionCompareType.EQUAL);
        s.add(ConditionCompareType.GREATER_OR_EQUAL);
        s.add(ConditionCompareType.GREATER_THAN);
        PR_REQUEST_AMOUNT.supportedCompareTypes = s;
        PR_REQUEST_AMOUNT.typeString = "Number";
        
        /*
        s = new HashSet();
        s.add(ConditionCompareType.LESS_THAN);
        s.add(ConditionCompareType.LESS_OR_EQUAL);
        s.add(ConditionCompareType.EQUAL);
        s.add(ConditionCompareType.GREATER_OR_EQUAL);
        s.add(ConditionCompareType.GREATER_THAN);
        DIFFERENCE_AMOUNT_OF_ACTUAL_BUDGET_AND_PR_REQUEST.supportedCompareTypes = s;
        DIFFERENCE_AMOUNT_OF_ACTUAL_BUDGET_AND_PR_REQUEST.typeString = "Number";
        
        s = new HashSet();
        s.add(ConditionCompareType.LESS_THAN);
        s.add(ConditionCompareType.LESS_OR_EQUAL);
        s.add(ConditionCompareType.EQUAL);
        s.add(ConditionCompareType.GREATER_OR_EQUAL);
        s.add(ConditionCompareType.GREATER_THAN);
        DIFFERENCE_AMOUNT_PERCENTAGE_OF_ACTUAL_BUDGET_AND_PR_REQUEST.supportedCompareTypes = s;
        DIFFERENCE_AMOUNT_PERCENTAGE_OF_ACTUAL_BUDGET_AND_PR_REQUEST.typeString = "Number";
        */
        
        s = new HashSet();
        s.add(ConditionCompareType.LESS_THAN);
        s.add(ConditionCompareType.LESS_OR_EQUAL);
        s.add(ConditionCompareType.EQUAL);
        s.add(ConditionCompareType.GREATER_OR_EQUAL);
        s.add(ConditionCompareType.GREATER_THAN);
        DIFFERENCE_AMOUNT_OF_PR_PURCHASE_AND_APPROVED.supportedCompareTypes = s;
        DIFFERENCE_AMOUNT_OF_PR_PURCHASE_AND_APPROVED.typeString = "Number";
        
        s = new HashSet();
        s.add(ConditionCompareType.LESS_THAN);
        s.add(ConditionCompareType.LESS_OR_EQUAL);
        s.add(ConditionCompareType.EQUAL);
        s.add(ConditionCompareType.GREATER_OR_EQUAL);
        s.add(ConditionCompareType.GREATER_THAN);
        DIFFERENCE_AMOUNT_PERCENTAGE_OF_PR_PURCHASE_AND_APPROVED.supportedCompareTypes = s;
        DIFFERENCE_AMOUNT_PERCENTAGE_OF_PR_PURCHASE_AND_APPROVED.typeString = "Number";
        
        s = new HashSet();
        s.add(ConditionCompareType.LESS_THAN);
        s.add(ConditionCompareType.LESS_OR_EQUAL);
        s.add(ConditionCompareType.EQUAL);
        s.add(ConditionCompareType.GREATER_OR_EQUAL);
        s.add(ConditionCompareType.GREATER_THAN);
        PO_PURCHASE_AMOUNT.supportedCompareTypes = s;
        PO_PURCHASE_AMOUNT.typeString = "Number";
        
        s = new HashSet();
        s.add(ConditionCompareType.EQUAL);
        EXPENSE_CATEGORY.supportedCompareTypes = s;
        EXPENSE_CATEGORY.typeString = "ExpenseCategory";
        
        s = new HashSet();
        s.add(ConditionCompareType.LESS_THAN);
        s.add(ConditionCompareType.LESS_OR_EQUAL);
        s.add(ConditionCompareType.EQUAL);
        s.add(ConditionCompareType.GREATER_OR_EQUAL);
        s.add(ConditionCompareType.GREATER_THAN);
        EXPENSE_SUBCATEGORY.supportedCompareTypes = s;
        EXPENSE_SUBCATEGORY.typeString = "Number";
        
        s = new HashSet();
        s.add(ConditionCompareType.LESS_THAN);
        s.add(ConditionCompareType.LESS_OR_EQUAL);
        s.add(ConditionCompareType.EQUAL);
        s.add(ConditionCompareType.GREATER_OR_EQUAL);
        s.add(ConditionCompareType.GREATER_THAN);
        EXPENSE_ENTERED_AMOUNT.supportedCompareTypes = s;
        EXPENSE_ENTERED_AMOUNT.typeString = "Number";
        
        s = new HashSet();
        s.add(ConditionCompareType.LESS_THAN);
        s.add(ConditionCompareType.LESS_OR_EQUAL);
        s.add(ConditionCompareType.EQUAL);
        s.add(ConditionCompareType.GREATER_OR_EQUAL);
        s.add(ConditionCompareType.GREATER_THAN);
        DIFFERENCE_AMOUNT_OF_EXPENSE_CLAIMED_AND_ENTERED.supportedCompareTypes = s;
        DIFFERENCE_AMOUNT_OF_EXPENSE_CLAIMED_AND_ENTERED.typeString = "Number";
        
        s = new HashSet();
        s.add(ConditionCompareType.LESS_THAN);
        s.add(ConditionCompareType.LESS_OR_EQUAL);
        s.add(ConditionCompareType.EQUAL);
        s.add(ConditionCompareType.GREATER_OR_EQUAL);
        s.add(ConditionCompareType.GREATER_THAN);
        DIFFERENCE_AMOUNT_PERCENTAGE_OF_EXPENSE_CLAIMED_AND_ENTERED.supportedCompareTypes = s;
        DIFFERENCE_AMOUNT_PERCENTAGE_OF_EXPENSE_CLAIMED_AND_ENTERED.typeString = "Number";
        
        s = new HashSet();
        s.add(ConditionCompareType.EQUAL);
        TRAVEL_FROM.supportedCompareTypes = s;
        TRAVEL_FROM.typeString = "TravelType";
        
        s = new HashSet();
        s.add(ConditionCompareType.EQUAL);
        TRAVEL_TO.supportedCompareTypes = s;
        TRAVEL_TO.typeString = "TravelType";
        
        s = new HashSet();
        s.add(ConditionCompareType.EQUAL);
        TRAVEL_MODE.supportedCompareTypes = s;
        TRAVEL_MODE.typeString = "TravellingMode";
        
        s = new HashSet();
        s.add(ConditionCompareType.LESS_THAN);
        s.add(ConditionCompareType.LESS_OR_EQUAL);
        s.add(ConditionCompareType.EQUAL);
        s.add(ConditionCompareType.GREATER_OR_EQUAL);
        s.add(ConditionCompareType.GREATER_THAN);
        OVER_BUDGET.supportedCompareTypes = s;
        OVER_BUDGET.typeString = "Number";

        s = new HashSet();
        s.add(ConditionCompareType.LESS_THAN);
        s.add(ConditionCompareType.LESS_OR_EQUAL);
        s.add(ConditionCompareType.EQUAL);
        s.add(ConditionCompareType.GREATER_OR_EQUAL);
        s.add(ConditionCompareType.GREATER_THAN);
        OVER_BUDGET_PERCENTAGE.supportedCompareTypes = s;
        OVER_BUDGET_PERCENTAGE.typeString = "Number";

        s = new HashSet();
        s.add(ConditionCompareType.EQUAL);
        BUDGET_TYPE.supportedCompareTypes = s;
        BUDGET_TYPE.typeString = "BudgetType";

        s = new HashSet();
        s.add(ConditionCompareType.LESS_THAN);
        s.add(ConditionCompareType.LESS_OR_EQUAL);
        s.add(ConditionCompareType.EQUAL);
        s.add(ConditionCompareType.GREATER_OR_EQUAL);
        s.add(ConditionCompareType.GREATER_THAN);
        REMAIN_AMOUNT.supportedCompareTypes = s;
        REMAIN_AMOUNT.typeString = "Number";

        s = new HashSet();
        s.add(ConditionCompareType.LESS_THAN);
        s.add(ConditionCompareType.LESS_OR_EQUAL);
        s.add(ConditionCompareType.EQUAL);
        s.add(ConditionCompareType.GREATER_OR_EQUAL);
        s.add(ConditionCompareType.GREATER_THAN);
        YEARLY_BUDGET_AMOUNT.supportedCompareTypes = s;
        YEARLY_BUDGET_AMOUNT.typeString = "Number";
        
        s = new HashSet();
        s.add(ConditionCompareType.EQUAL);
        WITH_BUDGET.supportedCompareTypes = s;
        WITH_BUDGET.typeString = "YesNo";
        
        s = new HashSet();
        s.add(ConditionCompareType.LESS_THAN);
        s.add(ConditionCompareType.LESS_OR_EQUAL);
        s.add(ConditionCompareType.EQUAL);
        s.add(ConditionCompareType.GREATER_OR_EQUAL);
        s.add(ConditionCompareType.GREATER_THAN);
        TRAVEL_FEE.supportedCompareTypes = s;
        TRAVEL_FEE.typeString = "Number";
        
        s = new HashSet();
        s.add(ConditionCompareType.LESS_THAN);
        s.add(ConditionCompareType.LESS_OR_EQUAL);
        s.add(ConditionCompareType.EQUAL);
        s.add(ConditionCompareType.GREATER_OR_EQUAL);
        s.add(ConditionCompareType.GREATER_THAN);
        PR_REQUEST_ITEM_MAX_PRICE.supportedCompareTypes = s;
        PR_REQUEST_ITEM_MAX_PRICE.typeString = "Number";
    }
    
    private Set supportedCompareTypes;
    private String typeString;
    
    public ConditionType() {
    }

    private ConditionType(int metadataId, String defaultLabel, MetadataType type) {
        super(metadataId, defaultLabel, type);
    }

    /**
     * @return Returns the supportedCompareTypes.
     */
    public Set getSupportedCompareTypes() {
        return supportedCompareTypes;
    }

    /**
     * @return Returns the typeString.
     */
    public String getTypeString() {
        return typeString;
    }

    
}
