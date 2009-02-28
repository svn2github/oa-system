/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.metadata;

public class AirTicketStatus extends MetadataDetailEnum {
    
    public static final AirTicketStatus BOOKED = new AirTicketStatus(1, "Booked", MetadataType.AIR_TICKET_STATUS);
    public static final AirTicketStatus RECEIVED = new AirTicketStatus(2, "Received", MetadataType.AIR_TICKET_STATUS);
    public static final AirTicketStatus CANCELLED = new AirTicketStatus(3, "Cancelled", MetadataType.AIR_TICKET_STATUS);

    public AirTicketStatus() {
    	
    }
    
    private AirTicketStatus(int metadataId, String defaultLabel, MetadataType type) {
        super(metadataId, defaultLabel, type);
    }

}
