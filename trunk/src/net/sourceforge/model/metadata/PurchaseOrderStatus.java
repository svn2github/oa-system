/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.metadata;

public class PurchaseOrderStatus extends MetadataDetailEnum {
    
    public static final PurchaseOrderStatus DRAFT = new PurchaseOrderStatus(1, "Draft", MetadataType.PURCHASE_ORDER_STATUS);
    public static final PurchaseOrderStatus PENDING = new PurchaseOrderStatus(2, "Pending", MetadataType.PURCHASE_ORDER_STATUS);
    public static final PurchaseOrderStatus APPROVED = new PurchaseOrderStatus(3, "Approved", MetadataType.PURCHASE_ORDER_STATUS);
    public static final PurchaseOrderStatus REJECTED = new PurchaseOrderStatus(4, "Rejected", MetadataType.PURCHASE_ORDER_STATUS);
    public static final PurchaseOrderStatus CONFIRMED = new PurchaseOrderStatus(5, "Confirmed", MetadataType.PURCHASE_ORDER_STATUS);
    public static final PurchaseOrderStatus RECEIVED = new PurchaseOrderStatus(6, "Received", MetadataType.PURCHASE_ORDER_STATUS);

    public PurchaseOrderStatus() {
    	
    }
    
    private PurchaseOrderStatus(int metadataId, String defaultLabel, MetadataType type) {
        super(metadataId, defaultLabel, type);
    }

}
