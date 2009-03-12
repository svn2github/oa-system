/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.metadata;

public class TravelApplicationBookStatus extends MetadataDetailEnum {
    
    public static final TravelApplicationBookStatus NA = new TravelApplicationBookStatus(1, "----", MetadataType.TRAVEL_APPLICATION_BOOK_STATUS);
    public static final TravelApplicationBookStatus BOOKED = new TravelApplicationBookStatus(2, "Booked", MetadataType.TRAVEL_APPLICATION_BOOK_STATUS);
    public static final TravelApplicationBookStatus RECEIVED = new TravelApplicationBookStatus(3, "Received", MetadataType.TRAVEL_APPLICATION_BOOK_STATUS);

    public TravelApplicationBookStatus() {
    	
    }
    
    private TravelApplicationBookStatus(int metadataId, String defaultLabel, MetadataType type) {
        super(metadataId, defaultLabel, type);
    }

}
