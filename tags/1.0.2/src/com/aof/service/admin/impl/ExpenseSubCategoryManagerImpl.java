/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.service.admin.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aof.dao.admin.ExpenseSubCategoryDAO;
import com.aof.model.admin.ExpenseSubCategory;
import com.aof.model.admin.query.ExpenseSubCategoryQueryCondition;
import com.aof.model.admin.query.ExpenseSubCategoryQueryOrder;
import com.aof.model.metadata.EnabledDisabled;
import com.aof.service.BaseManager;
import com.aof.service.admin.ExpenseSubCategoryManager;

/**
 * implement for ExpenseSubCategoryManager
 * @author shilei
 * @version 1.0 (Nov 15, 2005)
 */
public class ExpenseSubCategoryManagerImpl extends BaseManager implements ExpenseSubCategoryManager {
    private ExpenseSubCategoryDAO dao;

    /**
     * @param dao
     */
    public void setExpenseSubCategoryDAO(ExpenseSubCategoryDAO dao) {
        this.dao = dao;
    }
    
     /* (non-Javadoc)
     * @see com.aof.service.admin.ExpenseSubCategoryManager#getExpenseSubCategory(java.lang.Integer)
     */
    public ExpenseSubCategory getExpenseSubCategory(Integer id)  {
        return dao.getExpenseSubCategory(id);
    }

    /* (non-Javadoc)
     * @see com.aof.service.admin.ExpenseSubCategoryManager#updateExpenseSubCategory(com.aof.model.admin.ExpenseSubCategory)
     */
    public ExpenseSubCategory updateExpenseSubCategory(ExpenseSubCategory function)  {
        return dao.updateExpenseSubCategory(function);
    }
    
    /* (non-Javadoc)
     * @see com.aof.service.admin.ExpenseSubCategoryManager#insertExpenseSubCategory(com.aof.model.admin.ExpenseSubCategory)
     */
    public ExpenseSubCategory insertExpenseSubCategory(ExpenseSubCategory function)  {
        return dao.insertExpenseSubCategory(function);
    }
    

    /* (non-Javadoc)
     * @see com.aof.service.admin.ExpenseSubCategoryManager#getExpenseSubCategoryListCount(java.util.Map)
     */
    public int getExpenseSubCategoryListCount(Map conditions)  {
        return dao.getExpenseSubCategoryListCount(conditions);
    }

    /* (non-Javadoc)
     * @see com.aof.service.admin.ExpenseSubCategoryManager#getExpenseSubCategoryList(java.util.Map, int, int, com.aof.model.admin.query.ExpenseSubCategoryQueryOrder, boolean)
     */
    public List getExpenseSubCategoryList(Map conditions, int pageNo, int pageSize, ExpenseSubCategoryQueryOrder order, boolean descend)  {
        return dao.getExpenseSubCategoryList(conditions, pageNo, pageSize, order, descend);
    }

	/* (non-Javadoc)
	 * @see com.aof.service.admin.ExpenseSubCategoryManager#getChildrenOfExpenseCategory(java.lang.Integer)
	 */
	public List getChildrenOfExpenseCategory(Integer expenseCategory_id)  {
		Map conditions =new HashMap();
		conditions.put(ExpenseSubCategoryQueryCondition.EXPENSECATEGORY_ID_EQ,expenseCategory_id);
		return this.getExpenseSubCategoryList(conditions,0,-1,ExpenseSubCategoryQueryOrder.ID,false);
	}

	/* (non-Javadoc)
	 * @see com.aof.service.admin.ExpenseSubCategoryManager#getEnabledChildrenOfExpenseCategory(java.lang.Integer)
	 */
	public List getEnabledChildrenOfExpenseCategory(Integer expenseCategory_id)  {
		Map conditions =new HashMap();
		conditions.put(ExpenseSubCategoryQueryCondition.EXPENSECATEGORY_ID_EQ,expenseCategory_id);
		conditions.put(ExpenseSubCategoryQueryCondition.ENABLED_EQ,EnabledDisabled.ENABLED.getEnumCode());
		return this.getExpenseSubCategoryList(conditions,0,-1,ExpenseSubCategoryQueryOrder.ID,false);
	}

}
