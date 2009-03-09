/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.metadata;

public class ContractStatus extends MetadataDetailEnum {
    
    public static final ContractStatus NOT_ACTIVED = new ContractStatus(1, "Not Actived", MetadataType.CONTRACT_STATUS);
    public static final ContractStatus ACTIVED = new ContractStatus(2, "Actived", MetadataType.CONTRACT_STATUS);
    public static final ContractStatus EXPIRED = new ContractStatus(3, "Expired", MetadataType.CONTRACT_STATUS);

    public ContractStatus() {
    	
    }
    
    private ContractStatus(int metadataId, String defaultLabel, MetadataType type) {
        super(metadataId, defaultLabel, type);
    }

}
