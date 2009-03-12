/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.metadata;

public class FlightClass extends MetadataDetailEnum {
    
    public static final FlightClass FIRST = new FlightClass(1, "First", MetadataType.FLIGHT_CLASS);
    public static final FlightClass BUSINESS = new FlightClass(2, "Business", MetadataType.FLIGHT_CLASS);
    public static final FlightClass ECONOMY = new FlightClass(3, "Economy", MetadataType.FLIGHT_CLASS);

    public FlightClass() {
    	
    }
    
    private FlightClass(int metadataId, String defaultLabel, MetadataType type) {
        super(metadataId, defaultLabel, type);
    }

}
