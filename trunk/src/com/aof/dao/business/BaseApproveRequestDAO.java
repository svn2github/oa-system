/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.dao.business;

import java.util.List;
import java.util.Map;

import com.aof.model.business.BaseApproveRequest;

public interface BaseApproveRequestDAO {
    public List getBaseApproveRequestList(Map conditions);
    
    public List getBaseApproveRequestListByApproveRequestId(String approveRequestId);
    
    public void updateBaseApproveRequest(BaseApproveRequest bar);

    public void insertBaseApproveRequest(BaseApproveRequest bar);
}
