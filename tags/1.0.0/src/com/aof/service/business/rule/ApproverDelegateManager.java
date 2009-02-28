/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.service.business.rule;

import java.util.List;
import java.util.Map;

import com.aof.model.business.rule.ApproverDelegate;
import com.aof.model.business.rule.query.ApproverDelegateQueryOrder;
import com.aof.model.business.rule.query.ApproverQueryCondition;
import com.aof.model.metadata.ApproverDelegateType;

public interface ApproverDelegateManager {

    /**
     * get Approver Delegate by id
     * @param id
     *      approver id
     * @return Approver Delegate
     * @throws Exception
     */
    public ApproverDelegate getApproverDelegate(Integer id) throws Exception;

    /**
     * insert Approver Delegate to database
     * @param approverDelegate 
     *      the Approver Delegate to be inserted
     * @return Approver Delegate inserted
     * @throws Exception
     */
    public ApproverDelegate insertApproverDelegate(ApproverDelegate approverDelegate) throws Exception;
    
    /**
     * update Approver Delegate to database
     * @param approverDelegate
     *      the Approver Delegate to be updated
     * @return Approver Delegate updated
     * @throws Exception
     */
    public ApproverDelegate updateApproverDelegate(ApproverDelegate approverDelegate) throws Exception;

    /**
     * get Approver Delegate List Count
     * @param condtions
     *      search conditions
     * @return count of Approver Delegate
     * @throws Exception
     */
    public int getApproverDelegateListCount(Map condtions) throws Exception;

    /**
     * get Approver Delegate List
     * @param condtions 
     *      search conditions
     * @param pageNo
     *      start page no, if pageNo is -1, ignored
     * @param pageSize
     *      page size, if pageSize is -1, ignored 
     * @param order
     *      sort order, if order is null, ignored
     * @param descend
     *      ascend or descend
     * @return list of Approver Delegate
     * @throws Exception
     */
    public List getApproverDelegateList(Map condtions, int pageNo, int pageSize, ApproverDelegateQueryOrder order, boolean descend) throws Exception;

	/**
     * get Approver List
     * @param cond
     *      search conditions
     * @param pageNo
     *      start page no, if pageNo is -1, ignored
     * @param pageSize
     *      page size, if pageSize is -1, ignored
     * @param order
     *      sort order, if order is null, ignored
     * @param descend
     *      ascend or descend
     * @return List of Approver
     * @throws Exception
	 */
    public List getApproverList(ApproverQueryCondition cond, int page, int pageSize, ApproverDelegateQueryOrder order, boolean descend)throws Exception;

    /**
     * get Approver List Count
     * @param cond
     *      search conditions
     * @return Approver List Count
     * @throws Exception
     */
	public int getApproverListCount(ApproverQueryCondition cond)throws Exception;
    
    /**
     * 判断是否是委托审批人
     * @param approverDelegateType 审批类型
     * @param originalApproverId 原审批人
     * @param delegateApproverId 委托审批人
     * @return 是返回true,否则返回false
     */
    public boolean isDelegateApprover(ApproverDelegateType approverDelegateType, Integer originalApproverId, Integer delegateApproverId);
    
}
