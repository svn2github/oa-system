/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.metadata;

public class TravellingMode extends MetadataDetailEnum {
    
    public static final TravellingMode AIR = new TravellingMode(1, "Air Plane", MetadataType.TRAVELLING_MODE);
    public static final TravellingMode BUS = new TravellingMode(2, "Coach", MetadataType.TRAVELLING_MODE);
    public static final TravellingMode TRAIN = new TravellingMode(3, "Train", MetadataType.TRAVELLING_MODE);
    public static final TravellingMode SHIP = new TravellingMode(4, "Ship", MetadataType.TRAVELLING_MODE);

    public TravellingMode() {
    	
    }
    
    private TravellingMode(int metadataId, String defaultLabel, MetadataType type) {
        super(metadataId, defaultLabel, type);
    }

}
