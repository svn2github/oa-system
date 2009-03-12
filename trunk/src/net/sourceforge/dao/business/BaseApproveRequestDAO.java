/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.dao.business;

import java.util.List;
import java.util.Map;

import net.sourceforge.model.business.BaseApproveRequest;

public interface BaseApproveRequestDAO {
    public List getBaseApproveRequestList(Map conditions);
    
    public List getBaseApproveRequestListByApproveRequestId(String approveRequestId);
    
    public void updateBaseApproveRequest(BaseApproveRequest bar);

    public void insertBaseApproveRequest(BaseApproveRequest bar);
}
