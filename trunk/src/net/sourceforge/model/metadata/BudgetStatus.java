/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.metadata;

public class BudgetStatus extends MetadataDetailEnum {
    public final static BudgetStatus OPEN = new BudgetStatus(1, "Open", MetadataType.BUDGET_STATUS);
    public final static BudgetStatus CLOSE = new BudgetStatus(2, "Close", MetadataType.BUDGET_STATUS);
    
    public BudgetStatus() {
    }
    
    private BudgetStatus(int metadataId, String defaultLabel, MetadataType type) {
        super(metadataId, defaultLabel, type);
    }

}
