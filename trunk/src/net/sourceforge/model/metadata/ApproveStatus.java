/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.metadata;

public class ApproveStatus extends MetadataDetailEnum {
    
    public static final ApproveStatus NOT_YOUR_TURN = new ApproveStatus(0, "Not your turn", MetadataType.APPROVE_STATUS);
    public static final ApproveStatus WAITING_FOR_APPROVE = new ApproveStatus(1, "Waiting for Approve", MetadataType.APPROVE_STATUS);
    public static final ApproveStatus APPROVED = new ApproveStatus(2, "Approved", MetadataType.APPROVE_STATUS);
    public static final ApproveStatus REJECTED = new ApproveStatus(3, "Rejected", MetadataType.APPROVE_STATUS);

    public ApproveStatus() {
    	
    }
    
    private ApproveStatus(int metadataId, String defaultLabel, MetadataType type) {
        super(metadataId, defaultLabel, type);
    }

}
