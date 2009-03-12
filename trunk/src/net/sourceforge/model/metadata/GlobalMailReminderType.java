/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.metadata;

public class GlobalMailReminderType extends MetadataDetailEnum {
    
    public static final GlobalMailReminderType SUPPLIER_MAINTAINER_NOT_RESPOND = new GlobalMailReminderType(1, "Site supplier maintainer not respond the supplier promote call", MetadataType.GLOBAL_MAIL_REMINDER_TYPE);
    public static final GlobalMailReminderType HOTEL_MAINTAINER_NOT_RESPOND = new GlobalMailReminderType(2, "Site Hotel maintainer not respond the hotel promote call", MetadataType.GLOBAL_MAIL_REMINDER_TYPE);

    public GlobalMailReminderType() {
    	
    }
    
    private GlobalMailReminderType(int metadataId, String defaultLabel, MetadataType type) {
        super(metadataId, defaultLabel, type);
    }

}
