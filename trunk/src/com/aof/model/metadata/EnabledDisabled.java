/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.metadata;

public class EnabledDisabled extends MetadataDetailEnum {
    
    public static final EnabledDisabled ENABLED = new EnabledDisabled(0, "Enabled", MetadataType.ENABLED_DISABLED);
    public static final EnabledDisabled DISABLED = new EnabledDisabled(1, "Disabled", MetadataType.ENABLED_DISABLED);

    public EnabledDisabled() {
    }

    private EnabledDisabled(int metadataId, String defaultLabel, MetadataType type) {
        super(metadataId, defaultLabel, type);
    }

}
