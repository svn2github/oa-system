/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.business;

import java.util.Map;

import com.aof.model.admin.Site;
import com.aof.model.admin.User;

/**
 * 支持审批的对象应该实现该接口
 * 
 * @author nicebean
 * @version 1.0 (2005-11-25)
 */
public interface Approvable {

    /**
     * 创建新的ApproveRequest对象。比如，对Expense，应创建ExpenseApproveRequest对象
     * 
     * @return BaseApproveRequest对象对应子类实例
     */
    public BaseApproveRequest createNewApproveRequestObj();
    
    /**
     * 返回对象的申请人
     * @return User对象
     */
    public User getRequestor();
    
    /**
     * 返回用于审批该对象的Flow的名称
     * @return Flow的名称
     */
    public String getApproveFlowName();

    public String getApprovalBatchEmailTemplateName();
    
    public String getApprovalNotifyEmailTemplateName();

    public Map getApprovalNotifyEmailContext();

    public String getApprovedNotifyEmailTemplateName();

    public Map getApprovedNotifyEmailContext();

    public String getRejectedNotifyEmailTemplateName();

    public Map getRejectedNotifyEmailContext();
    
    public Site getLogSite();
    
    public String getApproveRequestId();
    
    public String getRefNo();
    
    public User getCreator();

}
