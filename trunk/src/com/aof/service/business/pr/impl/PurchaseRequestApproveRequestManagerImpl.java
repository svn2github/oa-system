/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.service.business.pr.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.aof.dao.business.pr.PurchaseRequestApproveRequestDAO;
import com.aof.dao.business.pr.PurchaseRequestDAO;
import com.aof.model.admin.User;
import com.aof.model.business.pr.PurchaseRequest;
import com.aof.model.business.pr.PurchaseRequestApproveRequest;
import com.aof.model.business.pr.query.PurchaseRequestApproveRequestQueryOrder;
import com.aof.model.metadata.ApproveStatus;
import com.aof.model.metadata.ApproverDelegateType;
import com.aof.service.BaseManager;
import com.aof.service.admin.SystemLogManager;
import com.aof.service.business.ApproveRelativeEmailManager;
import com.aof.service.business.pr.PurchaseRequestApproveRequestManager;
import com.aof.service.business.pr.PurchaseRequestManager;

/**
 * PurchaseRequestApproveRequestManagerµÄÊµÏÖ
 * 
 * @author nicebean
 * @version 1.0 (2005-12-15)
 */
public class PurchaseRequestApproveRequestManagerImpl extends BaseManager implements PurchaseRequestApproveRequestManager {

    private PurchaseRequestApproveRequestDAO dao;

    private PurchaseRequestDAO purchaseRequestDao;

    private PurchaseRequestManager purchaseRequestManager;
    
    private SystemLogManager systemLogManager;
    
    private ApproveRelativeEmailManager approveRelativeEmailManager;

    public void setSystemLogManager(SystemLogManager systemLogManager) {
        this.systemLogManager = systemLogManager;
    }

    public void setPurchaseRequestManager(PurchaseRequestManager purchaseRequestManager) {
        this.purchaseRequestManager = purchaseRequestManager;
    }

    public void setPurchaseRequestApproveRequestDAO(PurchaseRequestApproveRequestDAO dao) {
        this.dao = dao;
    }

    public void setPurchaseRequestDAO(PurchaseRequestDAO purchaseRequestDao) {
        this.purchaseRequestDao = purchaseRequestDao;
    }

    public void setApproveRelativeEmailManager(ApproveRelativeEmailManager approveRelativeEmailManager) {
        this.approveRelativeEmailManager = approveRelativeEmailManager;
    }

    public List getPurchaseRequestApproveRequestListByApproveRequestId(String requestId) {
        return dao.getPurchaseRequestApproveRequestListByApproveRequestId(requestId);
    }

    public PurchaseRequestApproveRequest getPurchaseRequestApproveRequest(String id, User approver) {
        return dao.getPurchaseRequestApproveRequest(id, approver);
    }

    public PurchaseRequestApproveRequest updatePurchaseRequestApproveRequest(PurchaseRequestApproveRequest purchaseRequestApproveRequest) {
        return dao.updatePurchaseRequestApproveRequest(purchaseRequestApproveRequest);
    }

    public PurchaseRequestApproveRequest insertPurchaseRequestApproveRequest(PurchaseRequestApproveRequest purchaseRequestApproveRequest) {
        return dao.insertPurchaseRequestApproveRequest(purchaseRequestApproveRequest);
    }

    public int getPurchaseRequestApproveRequestListCount(Map conditions) {
        return dao.getPurchaseRequestApproveRequestListCount(conditions);
    }

    public List getPurchaseRequestApproveRequestList(Map conditions, int pageNo, int pageSize, PurchaseRequestApproveRequestQueryOrder order, boolean descend) {
        return dao.getPurchaseRequestApproveRequestList(conditions, pageNo, pageSize, order, descend);
    }

    public void approvePurchaseRequestApproveRequest(PurchaseRequestApproveRequest purchaseRequestApproveRequest,User user) {
        purchaseRequestApproveRequest.setStatus(ApproveStatus.APPROVED);
        purchaseRequestApproveRequest.setApproveDate(new Date());
        dao.updatePurchaseRequestApproveRequest(purchaseRequestApproveRequest);
        PurchaseRequestApproveRequest nextApprove = getNextPurchaseRequestApproveRequest(purchaseRequestApproveRequest);
        PurchaseRequest purchaseRequest = purchaseRequestDao.getPurchaseRequestByApproveRequestId(purchaseRequestApproveRequest.getApproveRequestId());
        
        //add by Jackey 2007-10-15, for not sending batch mail when approver approved purchaseRequest.
        approveRelativeEmailManager.UnsendApprovedEmailBatch(purchaseRequest);                   
        
        if (nextApprove != null) {
            nextApprove.setStatus(ApproveStatus.WAITING_FOR_APPROVE);
            nextApprove.setYourTurnDate(new Date());
            nextApprove = dao.updatePurchaseRequestApproveRequest(nextApprove);
            approveRelativeEmailManager.sendApprovalEmail(purchaseRequest, ApproverDelegateType.PURCHASE_REQUEST_APPROVER, nextApprove);
        } else {
            if (purchaseRequest != null) {
                purchaseRequestManager.approvePurchaseRequest(purchaseRequest,user);
            }
        }
        
        systemLogManager.generateLog(null,purchaseRequest,PurchaseRequest.LOG_ACTION_APPROVE,user);
    }

    public void rejectPurchaseRequestApproveRequest(PurchaseRequestApproveRequest purchaseRequestApproveRequest,User user) {
        purchaseRequestApproveRequest.setStatus(ApproveStatus.REJECTED);
        purchaseRequestApproveRequest.setApproveDate(new Date());
        dao.updatePurchaseRequestApproveRequest(purchaseRequestApproveRequest);
        PurchaseRequest purchaseRequest = purchaseRequestDao.getPurchaseRequestByApproveRequestId(purchaseRequestApproveRequest.getApproveRequestId());
        
        //add by Jackey 2007-10-15, for not sending batch mail when approver rejected purchaseRequest.
        approveRelativeEmailManager.UnsendApprovedEmailBatch(purchaseRequest);
        
        if (purchaseRequest != null) {
            purchaseRequestManager.rejectPurchaseRequest(purchaseRequest, user, purchaseRequestApproveRequest.getComment());
        }
    }

    private PurchaseRequestApproveRequest getNextPurchaseRequestApproveRequest(PurchaseRequestApproveRequest purchaseRequestApproveRequest) {
        List approveList = getPurchaseRequestApproveRequestListByApproveRequestId(purchaseRequestApproveRequest.getApproveRequestId());
        Iterator itor = approveList.iterator();
        while (itor.hasNext()) {
            PurchaseRequestApproveRequest approve = (PurchaseRequestApproveRequest) itor.next();
            if (approve.getApprover().equals(purchaseRequestApproveRequest.getApprover())) {
                if (itor.hasNext()) {
                    return (PurchaseRequestApproveRequest) itor.next();
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    public void approvePurchaseRequestApproveRequestAndUpdatePurchaseRequest(PurchaseRequestApproveRequest purchaseRequestApproveRequest,
            PurchaseRequest purchaseRequest, List itemList, List attachmentList,User user) {
        purchaseRequestManager.updatePurchaseRequest(purchaseRequest, itemList, attachmentList, false);
        approvePurchaseRequestApproveRequest(purchaseRequestApproveRequest,user);
    }
}
