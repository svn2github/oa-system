/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.service.business;

import net.sourceforge.model.admin.EmailBatch;
import net.sourceforge.model.business.Approvable;
import net.sourceforge.model.business.BaseApproveRequest;
import net.sourceforge.model.metadata.ApproverDelegateType;


public interface ApproveRelativeEmailManager {
    public void sendApprovalEmail(Approvable target, ApproverDelegateType type, BaseApproveRequest approveRequest);

    public void sendApprovedEmail(Approvable target);

    public void sendRejectedEmail(Approvable target, BaseApproveRequest approveRequest);
    
    public void sendRejectedEmail(Approvable target, String rejecterName, String comment);

    public void deleteWithdrawEmail(Approvable target);
    
    public void UnsendApprovedEmailBatch(Approvable target);
}
