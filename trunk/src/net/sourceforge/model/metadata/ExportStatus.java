/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.metadata;

public class ExportStatus extends MetadataDetailEnum {
    public static final ExportStatus UNEXPORTED = new ExportStatus(1, "Unexported", MetadataType.EXPORT_STATUS);
    public static final ExportStatus EXPORTED = new ExportStatus(2, "Exported", MetadataType.EXPORT_STATUS);
    public static final ExportStatus NEEDREEXPORT = new ExportStatus(3, "NeedReExport", MetadataType.EXPORT_STATUS);
    
    public ExportStatus() {
    }

    private ExportStatus(int metadataId, String defaultLabel, MetadataType type) {
        super(metadataId, defaultLabel, type);
    }

}
