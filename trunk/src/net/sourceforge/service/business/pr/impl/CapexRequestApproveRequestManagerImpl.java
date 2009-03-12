/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.service.business.pr.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sourceforge.dao.business.pr.CapexDAO;
import net.sourceforge.dao.business.pr.CapexRequestApproveRequestDAO;
import net.sourceforge.model.admin.User;
import net.sourceforge.model.business.pr.CapexRequest;
import net.sourceforge.model.business.pr.CapexRequestApproveRequest;
import net.sourceforge.model.business.pr.query.CapexRequestApproveRequestQueryOrder;
import net.sourceforge.model.metadata.ApproveStatus;
import net.sourceforge.model.metadata.ApproverDelegateType;
import net.sourceforge.service.BaseManager;
import net.sourceforge.service.admin.SystemLogManager;
import net.sourceforge.service.business.ApproveRelativeEmailManager;
import net.sourceforge.service.business.pr.CapexManager;
import net.sourceforge.service.business.pr.CapexRequestApproveRequestManager;

/**
 * CapexRequestApproveRequestManagerµÄÊµÏÖ

 * @author nicebean
 * @version 1.0 (2005-12-8)
 */
public class CapexRequestApproveRequestManagerImpl extends BaseManager implements CapexRequestApproveRequestManager {

    private CapexRequestApproveRequestDAO dao;

    private CapexDAO capexDao;
    
    private CapexManager capexManager;
    
    private SystemLogManager systemLogManager;
    
    private ApproveRelativeEmailManager approveRelativeEmailManager;

    public void setCapexManager(CapexManager capexManager) {
        this.capexManager = capexManager;
    }

    public void setCapexRequestApproveRequestDAO(CapexRequestApproveRequestDAO dao) {
        this.dao = dao;
    }

    public void setCapexDAO(CapexDAO capexDao) {
        this.capexDao = capexDao;
    }

    public void setSystemLogManager(SystemLogManager systemLogManager) {
        this.systemLogManager = systemLogManager;
    }

    public void setApproveRelativeEmailManager(ApproveRelativeEmailManager approveRelativeEmailManager) {
        this.approveRelativeEmailManager = approveRelativeEmailManager;
    }

    public List getCapexRequestApproveRequestListByApproveRequestId(String requestId) {
        return dao.getCapexRequestApproveRequestListByApproveRequestId(requestId);
    }

    public CapexRequestApproveRequest getCapexRequestApproveRequest(String id, User approver) {
        return dao.getCapexRequestApproveRequest(id, approver);
    }

    public CapexRequestApproveRequest updateCapexRequestApproveRequest(CapexRequestApproveRequest capexRequestApproveRequest) {
        return dao.updateCapexRequestApproveRequest(capexRequestApproveRequest);
    }

    public CapexRequestApproveRequest insertCapexRequestApproveRequest(CapexRequestApproveRequest capexRequestApproveRequest) {
        return dao.insertCapexRequestApproveRequest(capexRequestApproveRequest);
    }

    public int getCapexRequestApproveRequestListCount(Map conditions) {
        return dao.getCapexRequestApproveRequestListCount(conditions);
    }

    public List getCapexRequestApproveRequestList(Map conditions, int pageNo, int pageSize, CapexRequestApproveRequestQueryOrder order, boolean descend) {
        return dao.getCapexRequestApproveRequestList(conditions, pageNo, pageSize, order, descend);
    }

    public void approveCapexRequestApproveRequest(CapexRequestApproveRequest capexRequestApproveRequest) {
        capexRequestApproveRequest.setStatus(ApproveStatus.APPROVED);
        capexRequestApproveRequest.setApproveDate(new Date());
        dao.updateCapexRequestApproveRequest(capexRequestApproveRequest);

        CapexRequest capexRequest = capexDao.getCapexRequestByApproveRequestId(capexRequestApproveRequest.getApproveRequestId());

        //add by Jackey 2007-10-15, for not sending batch mail when approver approved capexRequest.
        approveRelativeEmailManager.UnsendApprovedEmailBatch(capexRequest);    
        
        CapexRequestApproveRequest nextApprove = getNextCapexRequestApproveRequest(capexRequestApproveRequest);
        if (nextApprove != null) {
            nextApprove.setStatus(ApproveStatus.WAITING_FOR_APPROVE);
            nextApprove.setYourTurnDate(new Date());
            nextApprove = dao.updateCapexRequestApproveRequest(nextApprove);
            approveRelativeEmailManager.sendApprovalEmail(capexRequest, ApproverDelegateType.NONBUDGET_CAPEX_APPROVER, nextApprove);
        } else {
            if (capexRequest != null) {
                capexManager.approveCapexRequest(capexRequest);
            }
        }

        systemLogManager.generateLog(null, capexRequest, CapexRequest.LOG_ACTION_APPROVE, capexRequestApproveRequest.getActualApprover());
    }

    public void rejectCapexRequestApproveRequest(CapexRequestApproveRequest capexRequestApproveRequest) {
        capexRequestApproveRequest.setStatus(ApproveStatus.REJECTED);
        capexRequestApproveRequest.setApproveDate(new Date());
        dao.updateCapexRequestApproveRequest(capexRequestApproveRequest);
        CapexRequest capexRequest = capexDao.getCapexRequestByApproveRequestId(capexRequestApproveRequest.getApproveRequestId());
        
        //add by Jackey 2007-10-15, for not sending batch mail when approver rejected capexRequest.
        approveRelativeEmailManager.UnsendApprovedEmailBatch(capexRequest);
        
        if (capexRequest != null) {
            capexManager.rejectCapexRequest(capexRequest);
            approveRelativeEmailManager.sendRejectedEmail(capexRequest, capexRequestApproveRequest);
        }

        systemLogManager.generateLog(null, capexRequest, CapexRequest.LOG_ACTION_REJECT, capexRequestApproveRequest.getActualApprover());

    }

    private CapexRequestApproveRequest getNextCapexRequestApproveRequest(CapexRequestApproveRequest capexRequestApproveRequest) {
        List approveList = getCapexRequestApproveRequestListByApproveRequestId(capexRequestApproveRequest.getApproveRequestId());
        Iterator itor = approveList.iterator();
        while (itor.hasNext()) {
            CapexRequestApproveRequest approve = (CapexRequestApproveRequest) itor.next();
            if (approve.getApprover().equals(capexRequestApproveRequest.getApprover())) {
                if (itor.hasNext()) {
                    return (CapexRequestApproveRequest) itor.next();
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    public void approveCapexRequestApproveRequestAndUpdateCapexRequest(CapexRequestApproveRequest capexRequestApproveRequest, CapexRequest capexRequest, List capexRequestItemList) {
        capexManager.saveCapexRequest(capexRequest, capexRequestItemList);
        approveCapexRequestApproveRequest(capexRequestApproveRequest);
    }

}
