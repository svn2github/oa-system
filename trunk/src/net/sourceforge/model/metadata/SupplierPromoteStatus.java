/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.metadata;

public class SupplierPromoteStatus extends MetadataDetailEnum {
    
    public static final SupplierPromoteStatus GLOBAL = new SupplierPromoteStatus(1, "Global", MetadataType.SUPPLIER_PROMOTE_STATUS);
    public static final SupplierPromoteStatus SITE = new SupplierPromoteStatus(2, "Site", MetadataType.SUPPLIER_PROMOTE_STATUS);
    public static final SupplierPromoteStatus REQUEST = new SupplierPromoteStatus(3, "Promote Requested", MetadataType.SUPPLIER_PROMOTE_STATUS);
    //public static final SupplierPromoteStatus RESPONSED = new SupplierPromoteStatus(4, "Promote Responsed", MetadataType.SUPPLIER_PROMOTE_STATUS);

    public SupplierPromoteStatus() {
    }

    private SupplierPromoteStatus(int metadataId, String defaultLabel, MetadataType type) {
        super(metadataId, defaultLabel, type);
    }

}
