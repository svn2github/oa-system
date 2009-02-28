/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.metadata;

public class SupplierConfirmStatus extends MetadataDetailEnum {
    
    public static final SupplierConfirmStatus NEW = new SupplierConfirmStatus(1, "New", MetadataType.SUPPLIER_CONFIRM_STATUS);
    public static final SupplierConfirmStatus MODIFY = new SupplierConfirmStatus(2, "Modify", MetadataType.SUPPLIER_CONFIRM_STATUS);

    public SupplierConfirmStatus() {
    }

    private SupplierConfirmStatus(int metadataId, String defaultLabel, MetadataType type) {
        super(metadataId, defaultLabel, type);
    }

}
