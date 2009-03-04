/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.metadata;

public class RechargeType extends MetadataDetailEnum {
    public final static RechargeType CUSTOMER = new RechargeType(1, "Customer", MetadataType.RECHARGE_TYPE);
    public final static RechargeType ENTITY = new RechargeType(2, "Entity", MetadataType.RECHARGE_TYPE);
    public final static RechargeType PERSON = new RechargeType(3, "Person", MetadataType.RECHARGE_TYPE);
    
    public RechargeType() {
    	
    }
    
    private RechargeType(int metadataId, String defaultLabel, MetadataType type) {
        super(metadataId, defaultLabel, type);
    }

}
