package com.aof.dao.business.pr;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.aof.model.admin.Department;
import com.aof.model.admin.PurchaseSubCategory;
import com.aof.model.business.pr.Capex;
import com.aof.model.business.pr.CapexDepartment;
import com.aof.model.business.pr.CapexRequest;
import com.aof.model.business.pr.CapexRequestApproveRequest;
import com.aof.model.business.pr.CapexRequestAttachment;
import com.aof.model.business.pr.CapexRequestHistory;
import com.aof.model.business.pr.CapexRequestHistoryItem;
import com.aof.model.business.pr.CapexRequestItem;
import com.aof.model.business.pr.query.CapexRequestQueryOrder;

public interface CapexDAO {

    public Capex getCapex(String id);

    public Capex insertCapex(Capex capex);

    public Capex updateCapex(Capex capex);

    public CapexRequest getCapexRequest(Integer id);

    public int getCapexRequestListCount(Map conditions);

    public List getCapexRequestList(Map conditions, int pageNo, int pageSize, CapexRequestQueryOrder order, boolean descend);

    public CapexRequest saveCapexRequest(CapexRequest capexRequest);
    
    public String getLastCapexNo(String prefix);

    public List getCapexDepartmentListForCapex(Capex capex);
    
    public CapexDepartment saveCapexDepartment(CapexDepartment capexDepartment);

    public List getCapexRequestItemListForCapex(CapexRequest cr);

    public List getCapexRequestAttachmentListForCapexRequest(CapexRequest cr);

    public List getCapexRequestApproveRequestListForCapexRequest(CapexRequest cr);

    public CapexRequestAttachment getCapexRequestAttachment(Integer id);

    public InputStream getCapexRequestAttachmentContent(Integer id);

    public CapexRequestAttachment saveCapexRequestAttachment(CapexRequestAttachment capexRequestAttachment, InputStream inputStream);

    public void deleteCapexRequestAttachment(CapexRequestAttachment capexRequestAttachment);

    public CapexRequestItem saveCapexRequestItem(CapexRequestItem item);
    
    public void deleteCapexRequestItem(CapexRequestItem item);

    public CapexRequestApproveRequest saveCapexRequestApproveRequest(CapexRequestApproveRequest request);

    public CapexRequest getCapexRequestByApproveRequestId(String approveRequestId);

    /**
     * 根据psc和department选择已经有申请被同意的capex
     * @param psc
     * @param department
     * @return
     */
    public List getRequestApprovedCapexList(final PurchaseSubCategory psc,final Department department);

    public void saveCapexRequestHistory(CapexRequestHistory history);

    public void saveCapexRequestHistoryItem(CapexRequestHistoryItem historyItem);

    public void deleteCapexRequest(CapexRequest capexRequest);

    public Department[] getDepartments(Capex cp);

    public void withdrawCapexRequest(CapexRequest capexRequest);

}
