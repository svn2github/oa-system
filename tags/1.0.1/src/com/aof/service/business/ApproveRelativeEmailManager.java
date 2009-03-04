/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.service.business;

import com.aof.model.admin.EmailBatch;
import com.aof.model.business.Approvable;
import com.aof.model.business.BaseApproveRequest;
import com.aof.model.metadata.ApproverDelegateType;


public interface ApproveRelativeEmailManager {
    public void sendApprovalEmail(Approvable target, ApproverDelegateType type, BaseApproveRequest approveRequest);

    public void sendApprovedEmail(Approvable target);

    public void sendRejectedEmail(Approvable target, BaseApproveRequest approveRequest);
    
    public void sendRejectedEmail(Approvable target, String rejecterName, String comment);

    public void deleteWithdrawEmail(Approvable target);
    
    public void UnsendApprovedEmailBatch(Approvable target);
}
