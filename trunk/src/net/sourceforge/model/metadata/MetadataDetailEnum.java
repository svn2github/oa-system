/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.metadata;

import com.shcnc.hibernate.PersistentIntegerEnum;

public class MetadataDetailEnum extends PersistentIntegerEnum {
    private MetadataType type;
    protected String engDescription;
    protected String chnDescription;
    protected String engShortDescription;
    protected String chnShortDescription;
    protected String color;

    public static final int DUMMY_KEY = -1;
    public static final MetadataDetailEnum DUMMY = new MetadataDetailEnum(DUMMY_KEY, null, null);
    
    protected MetadataDetailEnum() {
    }
    
    protected MetadataDetailEnum(int metadataId, String defaultLabel, MetadataType type) {
        super(defaultLabel, metadataId);
        this.type = type;
    }

    public MetadataType getType() {
        return type;
    }
    
    public String getChnDescription() {
        return chnDescription;
    }

    public void setChnDescription(String chnDescription) {
        this.chnDescription = chnDescription;
    }

    public String getChnShortDescription() {
        return chnShortDescription;
    }

    public void setChnShortDescription(String chnShortDescription) {
        this.chnShortDescription = chnShortDescription;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getEngDescription() {
        return engDescription;
    }

    public void setEngDescription(String engDescription) {
        this.engDescription = engDescription;
    }

    public String getEngShortDescription() {
        return engShortDescription;
    }

    public void setEngShortDescription(String engShortDescription) {
        this.engShortDescription = engShortDescription;
    }
    
}
