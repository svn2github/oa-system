/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.metadata;

public class CapexRequestType extends MetadataDetailEnum {
    
    public static final CapexRequestType INITIAL = new CapexRequestType(1, "Initial", MetadataType.CAPEX_REQUEST_TYPE);
    public static final CapexRequestType INCREASE = new CapexRequestType(2, "Increase", MetadataType.CAPEX_REQUEST_TYPE);

    public CapexRequestType() {
    	
    }
    
    private CapexRequestType(int metadataId, String defaultLabel, MetadataType type) {
        super(metadataId, defaultLabel, type);
    }

}
