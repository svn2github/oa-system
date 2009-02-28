/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.metadata;

public class CapexRequestStatus extends MetadataDetailEnum {
    
    public static final CapexRequestStatus DRAFT = new CapexRequestStatus(1, "Draft", MetadataType.CAPEX_REQUEST_STATUS);
    public static final CapexRequestStatus PENDING = new CapexRequestStatus(2, "Pending", MetadataType.CAPEX_REQUEST_STATUS);
    public static final CapexRequestStatus APPROVED = new CapexRequestStatus(3, "Approved", MetadataType.CAPEX_REQUEST_STATUS);
    public static final CapexRequestStatus REJECTED = new CapexRequestStatus(4, "Rejected", MetadataType.CAPEX_REQUEST_STATUS);

    public CapexRequestStatus() {
    	
    }
    
    private CapexRequestStatus(int metadataId, String defaultLabel, MetadataType type) {
        super(metadataId, defaultLabel, type);
    }

}
