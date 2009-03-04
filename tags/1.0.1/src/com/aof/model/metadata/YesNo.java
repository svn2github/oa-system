/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.metadata;

public class YesNo extends MetadataDetailEnum {
    
    public static final YesNo YES = new YesNo(0, "Yes", MetadataType.YES_NO);
    public static final YesNo NO = new YesNo(1, "No", MetadataType.YES_NO);

    public YesNo() {
    	
    }
    
    private YesNo(int metadataId, String defaultLabel, MetadataType type) {
        super(metadataId, defaultLabel, type);
    }
    
    public boolean equalsYesNo(YesNo yn)
    {
        return this.equals(yn);
    }

}
