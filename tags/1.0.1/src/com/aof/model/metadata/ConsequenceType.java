/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.metadata;

public class ConsequenceType extends MetadataDetailEnum {
    
    public static final ConsequenceType APPROVER = new ConsequenceType(1, "Approver", MetadataType.CONSEQUENCE_TYPE);
    public static final ConsequenceType PURCHASER = new ConsequenceType(2, "Purchaser", MetadataType.CONSEQUENCE_TYPE);
    public static final ConsequenceType ACCEPTABLE = new ConsequenceType(3, "Accpetable", MetadataType.CONSEQUENCE_TYPE);
    public static final ConsequenceType NOTIFIER = new ConsequenceType(4, "Notification", MetadataType.CONSEQUENCE_TYPE);
    
    static {
        APPROVER.prefixUrl = "Approver";
        PURCHASER.prefixUrl = "Purchaser";
        ACCEPTABLE.prefixUrl = "Acceptable";
        NOTIFIER.prefixUrl = "Notifier";
    }

    public ConsequenceType() {
    	
    }
    
    private ConsequenceType(int metadataId, String defaultLabel, MetadataType type) {
        super(metadataId, defaultLabel, type);
    }

    private String prefixUrl;

    /**
     * @return Returns the prefixUrl.
     */
    public String getPrefixUrl() {
        return prefixUrl;
    }

}
