/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.metadata;

public class ExpenseType extends MetadataDetailEnum {
    public final static ExpenseType TRAVEL = new ExpenseType(1, "Travel", MetadataType.EXPENSE_TYPE);
    public final static ExpenseType OTHER = new ExpenseType(2, "Other", MetadataType.EXPENSE_TYPE);
    
    public ExpenseType() {
    }

    private ExpenseType(int metadataId, String defaultLabel, MetadataType type) {
        super(metadataId, defaultLabel, type);
    }

}
