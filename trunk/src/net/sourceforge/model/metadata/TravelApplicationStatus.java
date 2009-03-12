/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.metadata;

public class TravelApplicationStatus extends MetadataDetailEnum {
    
    public static final TravelApplicationStatus DRAFT = new TravelApplicationStatus(1, "Draft", MetadataType.TRAVEL_APPLICATION_STATUS);
    public static final TravelApplicationStatus PENDING = new TravelApplicationStatus(2, "Pending", MetadataType.TRAVEL_APPLICATION_STATUS);
    public static final TravelApplicationStatus APPROVED = new TravelApplicationStatus(3, "Approved", MetadataType.TRAVEL_APPLICATION_STATUS);
    public static final TravelApplicationStatus REJECTED = new TravelApplicationStatus(4, "Rejected", MetadataType.TRAVEL_APPLICATION_STATUS);
    //public static final TravelApplicationStatus BOOKED = new TravelApplicationStatus(5, "Booked", MetadataType.TRAVEL_APPLICATION_STATUS);
    //public static final TravelApplicationStatus RECEIVED = new TravelApplicationStatus(6, "Received", MetadataType.TRAVEL_APPLICATION_STATUS);

    public TravelApplicationStatus() {
    	
    }
    
    private TravelApplicationStatus(int metadataId, String defaultLabel, MetadataType type) {
        super(metadataId, defaultLabel, type);
    }

}
