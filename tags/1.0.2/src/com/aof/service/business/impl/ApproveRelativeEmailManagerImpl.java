/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.service.business.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.aof.dao.business.rule.ApproverDelegateDAO;
import com.aof.model.admin.EmailBatch;
import com.aof.model.admin.User;
import com.aof.model.business.Approvable;
import com.aof.model.business.BaseApproveRequest;
import com.aof.model.business.rule.ApproverDelegate;
import com.aof.model.business.rule.query.ApproverDelegateQueryCondition;
import com.aof.model.metadata.ApproverDelegateType;
import com.aof.service.BaseManager;
import com.aof.service.admin.EmailManager;
import com.aof.service.business.ApproveRelativeEmailManager;

public class ApproveRelativeEmailManagerImpl extends BaseManager implements ApproveRelativeEmailManager {
    private EmailManager emailManager;
    private ApproverDelegateDAO approverDelegateDAO;
    
    public void setEmailManager(EmailManager emailManager) {
        this.emailManager = emailManager;
    }

    public void setApproverDelegateDAO(ApproverDelegateDAO approverDelegateDAO) {
        this.approverDelegateDAO = approverDelegateDAO;
    }

    public void sendApprovalEmail(Approvable target, ApproverDelegateType type, BaseApproveRequest approveRequest) {
        User approver = approveRequest.getApprover();
        sendApprovalEmail(target, approver);
        Map conditions = new HashMap();
        conditions.put(ApproverDelegateQueryCondition.ORIGINALAPPROVER_ID_EQ, approver.getId());
        conditions.put(ApproverDelegateQueryCondition.TYPE_EQ, type);
        long now = this.getTodayTimeMillis();
        conditions.put(ApproverDelegateQueryCondition.FROMDATE_LT, new Date(now + 86400000));
        conditions.put(ApproverDelegateQueryCondition.TODATE_GE, new Date(now));
        for (Iterator itor = approverDelegateDAO.getApproverDelegateList(conditions, 0, -1, null, false).iterator(); itor.hasNext(); ) {
            ApproverDelegate delegate = (ApproverDelegate) itor.next();
            sendApprovalEmail(target, delegate.getDelegateApprover());
        }
    }
    
    private void sendApprovalEmail(Approvable target, User approver) {
        Map context = target.getApprovalNotifyEmailContext();
        context.put("user", approver);
        context.put("role", EmailManager.EMAIL_ROLE_APPROVER);
        emailManager.insertEmailBatch(target.getLogSite(), approver.getEmail(), target.getApprovalNotifyEmailTemplateName(), target.getRefNo(), context, target.getApprovalBatchEmailTemplateName());
    }
    
    public void sendApprovedEmail(Approvable target) {        
        User requestor = target.getRequestor();
        Map context = target.getApprovalNotifyEmailContext();
        context.put("user", requestor);
        context.put("role", EmailManager.EMAIL_ROLE_REQUESTOR);
        emailManager.insertEmail(target.getLogSite(), requestor.getEmail(), target.getApprovedNotifyEmailTemplateName(), context);
        
        User creator = target.getCreator();        
        if (creator != null && !requestor.equals(creator)) {
            Map context2 = target.getApprovalNotifyEmailContext();
            context2.put("user", creator);
            context2.put("role", EmailManager.EMAIL_ROLE_CREATOR);
            emailManager.insertEmail(target.getLogSite(), creator.getEmail(), target.getApprovedNotifyEmailTemplateName(), context2);
        }
    }

    public void sendRejectedEmail(Approvable target, BaseApproveRequest approveRequest) {
        User rejecter = approveRequest.getApprover();
        User requestor = target.getRequestor();
        Map context = target.getRejectedNotifyEmailContext();
        context.put("user", requestor);
        context.put("rejecterName", rejecter.getName());
        context.put("comment", approveRequest.getComment());
        context.put("role", EmailManager.EMAIL_ROLE_REQUESTOR);
        emailManager.insertEmail(target.getLogSite(), requestor.getEmail(), target.getRejectedNotifyEmailTemplateName(), context);
        
        User creator = target.getCreator();        
        if (creator != null && !requestor.equals(creator)) {
            Map context2 = target.getRejectedNotifyEmailContext();
            context2.put("user", creator);
            context2.put("rejecterName", rejecter.getName());
            context2.put("comment", approveRequest.getComment());
            context2.put("role", EmailManager.EMAIL_ROLE_CREATOR);
            emailManager.insertEmail(target.getLogSite(), creator.getEmail(), target.getRejectedNotifyEmailTemplateName(), context2);
        }
    }

    public void sendRejectedEmail(Approvable target, String rejecterName, String comment) {
        Map context = target.getRejectedNotifyEmailContext();
        User requestor = target.getRequestor();
        context.put("user", requestor);
        context.put("rejecterName", rejecterName);
        context.put("comment", comment);
        context.put("role", EmailManager.EMAIL_ROLE_REQUESTOR);
        emailManager.insertEmail(target.getLogSite(), requestor.getEmail(), target.getRejectedNotifyEmailTemplateName(), context);
        
        User creator = target.getCreator();        
        if (creator != null && !requestor.equals(creator)) {
            Map context2 = target.getRejectedNotifyEmailContext();
            context2.put("user", creator);
            context2.put("rejecterName", rejecterName);
            context2.put("comment", comment);
            context2.put("role", EmailManager.EMAIL_ROLE_CREATOR);
            emailManager.insertEmail(target.getLogSite(), creator.getEmail(), target.getRejectedNotifyEmailTemplateName(), context2);
        }
    }
    
    public void deleteWithdrawEmail(Approvable target) {
        String refNo = target.getRefNo();
        emailManager.deleteEmailBatch(refNo);
    }
    
    public void UnsendApprovedEmailBatch(Approvable target)
    {
        String refNo = target.getRefNo();
        EmailBatch emailBatch = emailManager.findNotSendEmailBatchByRefNo(refNo);
        
        if (emailBatch != null) {
            emailBatch.setIsSend(com.aof.model.metadata.YesNo.YES);
            
            emailManager.updateEmailBatch(emailBatch);
        }        
    }
}
