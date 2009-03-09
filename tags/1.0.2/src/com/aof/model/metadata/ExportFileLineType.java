/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.metadata;

public class ExportFileLineType extends MetadataDetailEnum {
    public static final ExportFileLineType MASTER_DATA = new ExportFileLineType(1, "Master Data", MetadataType.EXPORT_FILE_LINE_TYPE);
    public static final ExportFileLineType DETAIL_DATA = new ExportFileLineType(2, "Detail Data", MetadataType.EXPORT_FILE_LINE_TYPE);
    public static final ExportFileLineType RECHARGE_DATA = new ExportFileLineType(3, "Recharge Data", MetadataType.EXPORT_FILE_LINE_TYPE);
    
    public ExportFileLineType() {
    }

    private ExportFileLineType(int metadataId, String defaultLabel, MetadataType type) {
        super(metadataId, defaultLabel, type);
    }

}
