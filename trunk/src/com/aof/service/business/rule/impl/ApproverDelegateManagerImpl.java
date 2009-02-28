/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.service.business.rule.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aof.dao.business.rule.ApproverDelegateDAO;
import com.aof.model.admin.Function;
import com.aof.model.admin.query.UserQueryCondition;
import com.aof.model.admin.query.UserQueryOrder;
import com.aof.model.business.rule.ApproverDelegate;
import com.aof.model.business.rule.query.ApproverDelegateQueryOrder;
import com.aof.model.business.rule.query.ApproverQueryCondition;
import com.aof.model.metadata.ApproverDelegateType;
import com.aof.model.metadata.EnabledDisabled;
import com.aof.service.BaseManager;
import com.aof.service.admin.FunctionManager;
import com.aof.service.admin.UserManager;
import com.aof.service.business.rule.ApproverDelegateManager;

public class ApproverDelegateManagerImpl extends BaseManager implements ApproverDelegateManager {
	private FunctionManager functionManager;
	private UserManager userManager;
	
	
	
	
    private ApproverDelegateDAO dao;

    /**
     * @param dao
     */
    public void setApproverDelegateDAO(ApproverDelegateDAO dao) {
        this.dao = dao;
    }
    
     /* (non-Javadoc)
     * @see com.aof.service.business.rule.ApproverDelegateManager#getApproverDelegate(java.lang.Integer)
     */
    public ApproverDelegate getApproverDelegate(Integer id) throws Exception {
        return dao.getApproverDelegate(id);
    }

    /* (non-Javadoc)
     * @see com.aof.service.business.rule.ApproverDelegateManager#updateApproverDelegate(com.aof.model.business.rule.ApproverDelegate)
     */
    public ApproverDelegate updateApproverDelegate(ApproverDelegate function) throws Exception {
        return dao.updateApproverDelegate(function);
    }
    
    /* (non-Javadoc)
     * @see com.aof.service.business.rule.ApproverDelegateManager#insertApproverDelegate(com.aof.model.business.rule.ApproverDelegate)
     */
    public ApproverDelegate insertApproverDelegate(ApproverDelegate function) throws Exception {
        return dao.insertApproverDelegate(function);
    }
    

    /* (non-Javadoc)
     * @see com.aof.service.business.rule.ApproverDelegateManager#getApproverDelegateListCount(java.util.Map)
     */
    public int getApproverDelegateListCount(Map conditions) throws Exception {
        return dao.getApproverDelegateListCount(conditions);
    }

    /* (non-Javadoc)
     * @see com.aof.service.business.rule.ApproverDelegateManager#getApproverDelegateList(java.util.Map, int, int, com.aof.model.business.rule.query.ApproverDelegateQueryOrder, boolean)
     */
    public List getApproverDelegateList(Map conditions, int pageNo, int pageSize, ApproverDelegateQueryOrder order, boolean descend) throws Exception {
        return dao.getApproverDelegateList(conditions, pageNo, pageSize, order, descend);
    }

	/* (non-Javadoc)
	 * @see com.aof.service.business.rule.ApproverDelegateManager#getApproverList(com.aof.model.business.rule.query.ApproverQueryCondition, int, int, com.aof.model.business.rule.query.ApproverDelegateQueryOrder, boolean)
	 */
	public List getApproverList(ApproverQueryCondition cond, int pageNo, int pageSize, ApproverDelegateQueryOrder order, boolean descend) throws Exception {
		
		return userManager.getUserList(getApproveConds(cond),pageNo,pageSize,UserQueryOrder.NAME,false);
	}
	
	private Map getApproveConds(ApproverQueryCondition cond) throws Exception
	{
		Map conds=new HashMap();
		Function function=functionManager.getFunction(cond.getRuleType().getPrefixUrl());
		conds.put(UserQueryCondition.DEPARTMENT_ID_EQ,cond.getDepartmentId());
		conds.put(UserQueryCondition.SITE_ID_EQ,cond.getSiteId());
		conds.put(UserQueryCondition.ENABLED_EQ,EnabledDisabled.ENABLED.getEnumCode());
		conds.put(UserQueryCondition.FUNCTION_ID_EQ,function.getId());
		return conds;
	}

	/* (non-Javadoc)
	 * @see com.aof.service.business.rule.ApproverDelegateManager#getApproverListCount(com.aof.model.business.rule.query.ApproverQueryCondition)
	 */
	public int getApproverListCount(ApproverQueryCondition cond) throws Exception {
		return userManager.getUserListCount(getApproveConds(cond));
	}

	/**
	 * @param functionManager
	 */
	public void setFunctionManager(FunctionManager functionManager) {
		this.functionManager = functionManager;
	}

	/**
	 * @param userManager
	 */
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	/* (non-Javadoc)
	 * @see com.aof.service.business.rule.ApproverDelegateManager#isDelegateApprover(com.aof.model.metadata.ApproverDelegateType, java.lang.Integer, java.lang.Integer)
	 */
	public boolean isDelegateApprover(ApproverDelegateType approverDelegateType, Integer originalApproverId, Integer delegateApproverId) {
	    return dao.isDelegateApprover(approverDelegateType,originalApproverId,delegateApproverId);
    }



}
