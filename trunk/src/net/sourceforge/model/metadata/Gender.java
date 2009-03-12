/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.metadata;

public class Gender extends MetadataDetailEnum {
    
    public static final Gender MALE = new Gender(0, "Male", MetadataType.GENDER);
    public static final Gender FEMALE = new Gender(1, "Female", MetadataType.GENDER);

    public Gender() {
    }

    private Gender(int metadataId, String defaultLabel, MetadataType type) {
        super(metadataId, defaultLabel, type);
    }

}
