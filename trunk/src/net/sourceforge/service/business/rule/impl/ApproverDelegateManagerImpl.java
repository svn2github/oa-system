/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.service.business.rule.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sourceforge.dao.business.rule.ApproverDelegateDAO;
import net.sourceforge.model.admin.Function;
import net.sourceforge.model.admin.query.UserQueryCondition;
import net.sourceforge.model.admin.query.UserQueryOrder;
import net.sourceforge.model.business.rule.ApproverDelegate;
import net.sourceforge.model.business.rule.query.ApproverDelegateQueryOrder;
import net.sourceforge.model.business.rule.query.ApproverQueryCondition;
import net.sourceforge.model.metadata.ApproverDelegateType;
import net.sourceforge.model.metadata.EnabledDisabled;
import net.sourceforge.service.BaseManager;
import net.sourceforge.service.admin.FunctionManager;
import net.sourceforge.service.admin.UserManager;
import net.sourceforge.service.business.rule.ApproverDelegateManager;

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
     * @see net.sourceforge.service.business.rule.ApproverDelegateManager#getApproverDelegate(java.lang.Integer)
     */
    public ApproverDelegate getApproverDelegate(Integer id) throws Exception {
        return dao.getApproverDelegate(id);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.service.business.rule.ApproverDelegateManager#updateApproverDelegate(net.sourceforge.model.business.rule.ApproverDelegate)
     */
    public ApproverDelegate updateApproverDelegate(ApproverDelegate function) throws Exception {
        return dao.updateApproverDelegate(function);
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.service.business.rule.ApproverDelegateManager#insertApproverDelegate(net.sourceforge.model.business.rule.ApproverDelegate)
     */
    public ApproverDelegate insertApproverDelegate(ApproverDelegate function) throws Exception {
        return dao.insertApproverDelegate(function);
    }
    

    /* (non-Javadoc)
     * @see net.sourceforge.service.business.rule.ApproverDelegateManager#getApproverDelegateListCount(java.util.Map)
     */
    public int getApproverDelegateListCount(Map conditions) throws Exception {
        return dao.getApproverDelegateListCount(conditions);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.service.business.rule.ApproverDelegateManager#getApproverDelegateList(java.util.Map, int, int, net.sourceforge.model.business.rule.query.ApproverDelegateQueryOrder, boolean)
     */
    public List getApproverDelegateList(Map conditions, int pageNo, int pageSize, ApproverDelegateQueryOrder order, boolean descend) throws Exception {
        return dao.getApproverDelegateList(conditions, pageNo, pageSize, order, descend);
    }

	/* (non-Javadoc)
	 * @see net.sourceforge.service.business.rule.ApproverDelegateManager#getApproverList(net.sourceforge.model.business.rule.query.ApproverQueryCondition, int, int, net.sourceforge.model.business.rule.query.ApproverDelegateQueryOrder, boolean)
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
	 * @see net.sourceforge.service.business.rule.ApproverDelegateManager#getApproverListCount(net.sourceforge.model.business.rule.query.ApproverQueryCondition)
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
	 * @see net.sourceforge.service.business.rule.ApproverDelegateManager#isDelegateApprover(net.sourceforge.model.metadata.ApproverDelegateType, java.lang.Integer, java.lang.Integer)
	 */
	public boolean isDelegateApprover(ApproverDelegateType approverDelegateType, Integer originalApproverId, Integer delegateApproverId) {
	    return dao.isDelegateApprover(approverDelegateType,originalApproverId,delegateApproverId);
    }



}
