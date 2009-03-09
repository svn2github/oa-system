/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.metadata;

public class PurchaseRequestStatus extends MetadataDetailEnum {
    
    public static final PurchaseRequestStatus DRAFT = new PurchaseRequestStatus(1, "Draft", MetadataType.PURCHASE_REQUEST_STATUS);
    public static final PurchaseRequestStatus PENDING = new PurchaseRequestStatus(2, "Pending", MetadataType.PURCHASE_REQUEST_STATUS);
    public static final PurchaseRequestStatus APPROVED = new PurchaseRequestStatus(3, "Approved", MetadataType.PURCHASE_REQUEST_STATUS);
    public static final PurchaseRequestStatus REJECTED = new PurchaseRequestStatus(4, "Rejected", MetadataType.PURCHASE_REQUEST_STATUS);
    public static final PurchaseRequestStatus BOOKED = new PurchaseRequestStatus(5, "Booked", MetadataType.PURCHASE_REQUEST_STATUS);
    public static final PurchaseRequestStatus CLOSED = new PurchaseRequestStatus(6, "Closed", MetadataType.PURCHASE_REQUEST_STATUS);

    public PurchaseRequestStatus() {
    	
    }
    
    private PurchaseRequestStatus(int metadataId, String defaultLabel, MetadataType type) {
        super(metadataId, defaultLabel, type);
    }

}
