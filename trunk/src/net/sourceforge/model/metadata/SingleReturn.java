/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.metadata;

public class SingleReturn extends MetadataDetailEnum {
    
    public static final SingleReturn SINGLE = new SingleReturn(1, "Single", MetadataType.SINGLE_RETURN);
    public static final SingleReturn RETURN = new SingleReturn(2, "Return", MetadataType.SINGLE_RETURN);

    public SingleReturn() {
    	
    }
    
    private SingleReturn(int metadataId, String defaultLabel, MetadataType type) {
        super(metadataId, defaultLabel, type);
    }

}
