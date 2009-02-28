/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.metadata;

public class BudgetType extends MetadataDetailEnum {
    public final static BudgetType CAPEX = new BudgetType(1, "Capex", MetadataType.BUDGET_TYPE);
    public final static BudgetType OTHER = new BudgetType(2, "Other", MetadataType.BUDGET_TYPE);
    
    public BudgetType() {
    }

    private BudgetType(int metadataId, String defaultLabel, MetadataType type) {
        super(metadataId, defaultLabel, type);
    }

}
