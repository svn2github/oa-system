/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.metadata;

public class HotelPromoteStatus extends MetadataDetailEnum {
    
    public static final HotelPromoteStatus GLOBAL = new HotelPromoteStatus(1, "Global", MetadataType.HOTEL_PROMOTE_STATUS);
    public static final HotelPromoteStatus SITE = new HotelPromoteStatus(2, "Site", MetadataType.HOTEL_PROMOTE_STATUS);
    public static final HotelPromoteStatus REQUEST = new HotelPromoteStatus(3, "Promote Requested", MetadataType.HOTEL_PROMOTE_STATUS);

    public HotelPromoteStatus() {
    }

    private HotelPromoteStatus(int metadataId, String defaultLabel, MetadataType type) {
        super(metadataId, defaultLabel, type);
    }

}
