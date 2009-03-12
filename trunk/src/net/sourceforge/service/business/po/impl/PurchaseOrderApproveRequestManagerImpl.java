/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.service.business.po.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sourceforge.dao.business.po.PurchaseOrderApproveRequestDAO;
import net.sourceforge.dao.business.po.PurchaseOrderDAO;
import net.sourceforge.model.admin.User;
import net.sourceforge.model.business.po.PurchaseOrder;
import net.sourceforge.model.business.po.PurchaseOrderApproveRequest;
import net.sourceforge.model.business.po.query.PurchaseOrderApproveRequestQueryOrder;
import net.sourceforge.model.metadata.ApproveStatus;
import net.sourceforge.model.metadata.ApproverDelegateType;
import net.sourceforge.service.BaseManager;
import net.sourceforge.service.admin.SystemLogManager;
import net.sourceforge.service.business.ApproveRelativeEmailManager;
import net.sourceforge.service.business.po.PurchaseOrderApproveRequestManager;
import net.sourceforge.service.business.po.PurchaseOrderManager;

/**
 * PurchaseOrderApproveRequestManagerµÄÊµÏÖ
 * 
 * @author nicebean
 * @version 1.0 (2005-12-22)
 */
public class PurchaseOrderApproveRequestManagerImpl extends BaseManager implements PurchaseOrderApproveRequestManager {

    private PurchaseOrderApproveRequestDAO dao;

    private PurchaseOrderDAO purchaseOrderDao;

    private PurchaseOrderManager purchaseOrderManager;
    private SystemLogManager systemLogManager; 
    private ApproveRelativeEmailManager approveRelativeEmailManager;

    public void setApproveRelativeEmailManager(ApproveRelativeEmailManager approveRelativeEmailManager) {
        this.approveRelativeEmailManager = approveRelativeEmailManager;
    }

    public void setSystemLogManager(SystemLogManager systemLogManager) {
        this.systemLogManager = systemLogManager;
    }

    public void setPurchaseOrderManager(PurchaseOrderManager purchaseOrderManager) {
        this.purchaseOrderManager = purchaseOrderManager;
    }

    public void setPurchaseOrderApproveRequestDAO(PurchaseOrderApproveRequestDAO dao) {
        this.dao = dao;
    }

    public void setPurchaseOrderDAO(PurchaseOrderDAO purchaseOrderDao) {
        this.purchaseOrderDao = purchaseOrderDao;
    }

    public List getPurchaseOrderApproveRequestListByApproveRequestId(String requestId) {
        return dao.getPurchaseOrderApproveRequestListByApproveRequestId(requestId);
    }

    public PurchaseOrderApproveRequest getPurchaseOrderApproveRequest(String id, User approver) {
        return dao.getPurchaseOrderApproveRequest(id, approver);
    }

    public PurchaseOrderApproveRequest updatePurchaseOrderApproveRequest(PurchaseOrderApproveRequest purchaseOrderApproveRequest) {
        return dao.updatePurchaseOrderApproveRequest(purchaseOrderApproveRequest);
    }

    public PurchaseOrderApproveRequest insertPurchaseOrderApproveRequest(PurchaseOrderApproveRequest purchaseOrderApproveRequest) {
        return dao.insertPurchaseOrderApproveRequest(purchaseOrderApproveRequest);
    }

    public int getPurchaseOrderApproveRequestListCount(Map conditions) {
        return dao.getPurchaseOrderApproveRequestListCount(conditions);
    }

    public List getPurchaseOrderApproveRequestList(Map conditions, int pageNo, int pageSize, PurchaseOrderApproveRequestQueryOrder order, boolean descend) {
        return dao.getPurchaseOrderApproveRequestList(conditions, pageNo, pageSize, order, descend);
    }

    public void approvePurchaseOrderApproveRequest(PurchaseOrderApproveRequest purchaseOrderApproveRequest,User user) {
        purchaseOrderApproveRequest.setStatus(ApproveStatus.APPROVED);
        purchaseOrderApproveRequest.setApproveDate(new Date());
        dao.updatePurchaseOrderApproveRequest(purchaseOrderApproveRequest);
        PurchaseOrderApproveRequest nextApprove = getNextPurchaseOrderApproveRequest(purchaseOrderApproveRequest);
        PurchaseOrder purchaseOrder = purchaseOrderDao.getPurchaseOrderByApproveRequestId(purchaseOrderApproveRequest.getApproveRequestId());
                
        //add by Jackey 2007-10-15, for not sending batch mail when approver approved purchaseOrder.
        approveRelativeEmailManager.UnsendApprovedEmailBatch(purchaseOrder);    
        
        if (nextApprove != null) {
            nextApprove.setStatus(ApproveStatus.WAITING_FOR_APPROVE);
            nextApprove.setYourTurnDate(new Date());
            nextApprove = dao.updatePurchaseOrderApproveRequest(nextApprove);
            approveRelativeEmailManager.sendApprovalEmail(purchaseOrder, ApproverDelegateType.PURCHASE_ORDER_APPROVER, purchaseOrderApproveRequest);
        } else {
            
            if (purchaseOrder != null) {
                purchaseOrderManager.approvePurchaseOrder(purchaseOrder,user);
            }
        }
        
        systemLogManager.generateLog(null,purchaseOrder,PurchaseOrder.LOG_ACTION_APPROVE,user);
    }

    public void rejectPurchaseOrderApproveRequest(PurchaseOrderApproveRequest purchaseOrderApproveRequest,User user) {
        purchaseOrderApproveRequest.setStatus(ApproveStatus.REJECTED);
        purchaseOrderApproveRequest.setApproveDate(new Date());
        dao.updatePurchaseOrderApproveRequest(purchaseOrderApproveRequest);
        PurchaseOrder purchaseOrder = purchaseOrderDao.getPurchaseOrderByApproveRequestId(purchaseOrderApproveRequest.getApproveRequestId());
        
        //add by Jackey 2007-10-15, for not sending batch mail when approver rejected purchaseOrder.
        approveRelativeEmailManager.UnsendApprovedEmailBatch(purchaseOrder);
        
        if (purchaseOrder != null) {
            purchaseOrderManager.rejectPurchaseOrder(purchaseOrder,user, purchaseOrderApproveRequest.getComment());
        }
    }

    private PurchaseOrderApproveRequest getNextPurchaseOrderApproveRequest(PurchaseOrderApproveRequest purchaseOrderApproveRequest) {
        List approveList = getPurchaseOrderApproveRequestListByApproveRequestId(purchaseOrderApproveRequest.getApproveRequestId());
        Iterator itor = approveList.iterator();
        while (itor.hasNext()) {
            PurchaseOrderApproveRequest approve = (PurchaseOrderApproveRequest) itor.next();
            if (approve.getApprover().equals(purchaseOrderApproveRequest.getApprover())) {
                if (itor.hasNext()) {
                    return (PurchaseOrderApproveRequest) itor.next();
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    public void approvePurchaseOrderApproveRequestAndUpdatePurchaseOrder(PurchaseOrderApproveRequest purchaseOrderApproveRequest,
            PurchaseOrder purchaseOrder,List oldItemList, List itemList, List attachmentList, List paymentScheduleDetailList,User user) {
        // TODO save whole purchase order
        purchaseOrderManager.updatePurchaseOrder(purchaseOrder,oldItemList, itemList, attachmentList, paymentScheduleDetailList);
        approvePurchaseOrderApproveRequest(purchaseOrderApproveRequest,user);
    }
}
