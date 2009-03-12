/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.metadata;

public class ExportFileStatus extends MetadataDetailEnum {
    public static final ExportFileStatus CREATE = new ExportFileStatus(1, "Create", MetadataType.EXPORT_FILE_STATUS);
    public static final ExportFileStatus READ = new ExportFileStatus(2, "Read", MetadataType.EXPORT_FILE_STATUS);
    public static final ExportFileStatus CLOSE = new ExportFileStatus(3, "Close", MetadataType.EXPORT_FILE_STATUS);
    public static final ExportFileStatus ERROR = new ExportFileStatus(4, "Error", MetadataType.EXPORT_FILE_STATUS);
    
    public ExportFileStatus() {
    }

    private ExportFileStatus(int metadataId, String defaultLabel, MetadataType type) {
        super(metadataId, defaultLabel, type);
    }

}
