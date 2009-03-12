/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.metadata;

public class ExportFileType extends MetadataDetailEnum {
    public static final ExportFileType TEXT = new ExportFileType(1, "Text", MetadataType.EXPORT_FILE_TYPE);
    public static final ExportFileType XML = new ExportFileType(2, "Xml", MetadataType.EXPORT_FILE_TYPE);
    
    public ExportFileType() {
    }

    private ExportFileType(int metadataId, String defaultLabel, MetadataType type) {
        super(metadataId, defaultLabel, type);
    }

}
