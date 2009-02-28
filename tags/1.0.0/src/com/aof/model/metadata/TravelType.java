/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.metadata;

public class TravelType extends MetadataDetailEnum {
    
    public static final TravelType LOCAL = new TravelType(0, "Local", MetadataType.TRAVEL_TYPE);
    public static final TravelType DOMESTIC = new TravelType(1, "Domestic", MetadataType.TRAVEL_TYPE);
    public static final TravelType OVERSEA = new TravelType(2, "Oversea", MetadataType.TRAVEL_TYPE);    

    public TravelType() {
    	
    }
    
    private TravelType(int metadataId, String defaultLabel, MetadataType type) {
        super(metadataId, defaultLabel, type);
    }

}
