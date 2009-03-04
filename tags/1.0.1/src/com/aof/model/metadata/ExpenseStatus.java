/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.metadata;

public class ExpenseStatus extends MetadataDetailEnum {
    
    public static final ExpenseStatus DRAFT = new ExpenseStatus(1, "Draft", MetadataType.EXPENSE_STATUS);
    public static final ExpenseStatus PENDING = new ExpenseStatus(2, "Pending", MetadataType.EXPENSE_STATUS);
    public static final ExpenseStatus APPROVED = new ExpenseStatus(3, "Approved", MetadataType.EXPENSE_STATUS);
    public static final ExpenseStatus REJECTED = new ExpenseStatus(4, "Rejected", MetadataType.EXPENSE_STATUS);
    public static final ExpenseStatus CONFIRMED = new ExpenseStatus(5, "Confirmed", MetadataType.EXPENSE_STATUS);
    public static final ExpenseStatus CLAIMED = new ExpenseStatus(6, "Claimed", MetadataType.EXPENSE_STATUS);

    public ExpenseStatus() {
    	
    }
    
    private ExpenseStatus(int metadataId, String defaultLabel, MetadataType type) {
        super(metadataId, defaultLabel, type);
    }

}
