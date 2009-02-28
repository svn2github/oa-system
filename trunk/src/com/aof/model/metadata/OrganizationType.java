/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.metadata;

public class OrganizationType extends MetadataDetailEnum {
    public final static OrganizationType REGIONAL =  new OrganizationType(1, "Regional", MetadataType.ORGANIZATION_TYPE);
    public final static OrganizationType SITE =  new OrganizationType(2, "Site", MetadataType.ORGANIZATION_TYPE);
    public final static OrganizationType DEPARTMENT =  new OrganizationType(3, "Department", MetadataType.ORGANIZATION_TYPE);
    
    public OrganizationType() {
    }

    private OrganizationType(int metadataId, String defaultLabel, MetadataType type) {
        super(metadataId, defaultLabel, type);
    }

}
