package com.aof.service.business.pr;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.aof.model.admin.Department;
import com.aof.model.admin.PurchaseSubCategory;
import com.aof.model.admin.User;
import com.aof.model.business.pr.Capex;
import com.aof.model.business.pr.CapexRequest;
import com.aof.model.business.pr.CapexRequestAttachment;
import com.aof.model.business.pr.query.CapexRequestQueryOrder;

public interface CapexManager {

    public CapexRequest getCapexRequest(Integer id);

    public CapexRequest saveCapexRequest(CapexRequest capexRequest);
    
    public CapexRequest saveCapexRequestAndDepartments(CapexRequest cr, List departmentList);

    public void saveCapexRequest(CapexRequest cr, List itemList);

    public void updateCapexRequest(CapexRequest capexRequest, List capexRequestItemList, List approveRequestList, boolean draft);

    public int getCapexRequestListCount(Map condtions);

    public List getCapexRequestList(Map condtions, int pageNo, int pageSize, CapexRequestQueryOrder order, boolean descend);

    public List getCapexDepartmentListForCapex(Capex c);

    public List getOldApprovedCapexRequestListForCapexRequest(CapexRequest cr);

    public List getCapexRequestItemListForCapexRequest(CapexRequest cr);

    public List getCapexRequestAttachmentListForCapexRequest(CapexRequest cr);

    public List getCapexRequestApproveRequestListForCapexRequest(CapexRequest cr);

    public CapexRequestAttachment getCapexRequestAttachment(Integer id);

    public InputStream getCapexRequestAttachmentContent(Integer id);

    public CapexRequestAttachment saveCapexRequestAttachment(CapexRequestAttachment capexRequestAttachment, InputStream inputStream);

    public void deleteCapexRequestAttachment(CapexRequestAttachment capexRequestAttachment);

    public CapexRequest getCapexRequestByApproveRequestId(String approveRequestId);

    public void approveCapexRequest(CapexRequest capexRequest);

    public void rejectCapexRequest(CapexRequest capexRequest);

    public void deleteCapexRequest(CapexRequest capexRequest);

    public CapexRequest createAddtionalCapexRequest(CapexRequest capexRequest);

    public Capex getCapex(String capex_id);

    public List getRequestApprovedCapexList(final PurchaseSubCategory psc, final Department d);

    public Department[] getDepartments(Capex cp);

    public void withdrawCapexRequest(CapexRequest capexRequest);

    public void setExtraInformationToCapexForExecuteFlow(Capex c);

    public void updateAndNotifyCapex(Capex capex, boolean amountChanged);
    
    public boolean canViewCapexAmount(Capex capex,User user);

}
