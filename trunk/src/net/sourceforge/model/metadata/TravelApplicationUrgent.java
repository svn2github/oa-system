/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.metadata;

public class TravelApplicationUrgent extends MetadataDetailEnum {
    
    public static final TravelApplicationUrgent NORMAL = new TravelApplicationUrgent(1, "Normal", MetadataType.TRAVEL_APPLICATION_URGENT);
    public static final TravelApplicationUrgent URGENT = new TravelApplicationUrgent(2, "Urgent", MetadataType.TRAVEL_APPLICATION_URGENT);
    
    public TravelApplicationUrgent() {
    }
    
    private TravelApplicationUrgent(int metadataId, String defaultLabel, MetadataType type) {
        super(metadataId, defaultLabel, type);
    }

}
