/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.metadata;

public class RoleType extends MetadataDetailEnum {
    
    public static final RoleType GLOBAL_ADMIN = new RoleType(1, "Global Level", MetadataType.ROLE_TYPE);
    public static final RoleType SITE_ADMIN = new RoleType(2, "Site Level", MetadataType.ROLE_TYPE);
    public static final RoleType ENDUSER = new RoleType(3, "Department Level", MetadataType.ROLE_TYPE);
    public static final RoleType COMMON_GLOBAL_LEVEL = new RoleType(4, "Common Global Level", MetadataType.ROLE_TYPE);

    public RoleType() {
    	
    }
    
    private RoleType(int metadataId, String defaultLabel, MetadataType type) {
        super(metadataId, defaultLabel, type);
    }

}
