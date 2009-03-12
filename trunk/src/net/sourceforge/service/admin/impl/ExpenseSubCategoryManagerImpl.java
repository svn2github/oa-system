/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.service.admin.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sourceforge.dao.admin.ExpenseSubCategoryDAO;
import net.sourceforge.model.admin.ExpenseSubCategory;
import net.sourceforge.model.admin.query.ExpenseSubCategoryQueryCondition;
import net.sourceforge.model.admin.query.ExpenseSubCategoryQueryOrder;
import net.sourceforge.model.metadata.EnabledDisabled;
import net.sourceforge.service.BaseManager;
import net.sourceforge.service.admin.ExpenseSubCategoryManager;

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
     * @see net.sourceforge.service.admin.ExpenseSubCategoryManager#getExpenseSubCategory(java.lang.Integer)
     */
    public ExpenseSubCategory getExpenseSubCategory(Integer id)  {
        return dao.getExpenseSubCategory(id);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.ExpenseSubCategoryManager#updateExpenseSubCategory(net.sourceforge.model.admin.ExpenseSubCategory)
     */
    public ExpenseSubCategory updateExpenseSubCategory(ExpenseSubCategory function)  {
        return dao.updateExpenseSubCategory(function);
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.ExpenseSubCategoryManager#insertExpenseSubCategory(net.sourceforge.model.admin.ExpenseSubCategory)
     */
    public ExpenseSubCategory insertExpenseSubCategory(ExpenseSubCategory function)  {
        return dao.insertExpenseSubCategory(function);
    }
    

    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.ExpenseSubCategoryManager#getExpenseSubCategoryListCount(java.util.Map)
     */
    public int getExpenseSubCategoryListCount(Map conditions)  {
        return dao.getExpenseSubCategoryListCount(conditions);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.ExpenseSubCategoryManager#getExpenseSubCategoryList(java.util.Map, int, int, net.sourceforge.model.admin.query.ExpenseSubCategoryQueryOrder, boolean)
     */
    public List getExpenseSubCategoryList(Map conditions, int pageNo, int pageSize, ExpenseSubCategoryQueryOrder order, boolean descend)  {
        return dao.getExpenseSubCategoryList(conditions, pageNo, pageSize, order, descend);
    }

	/* (non-Javadoc)
	 * @see net.sourceforge.service.admin.ExpenseSubCategoryManager#getChildrenOfExpenseCategory(java.lang.Integer)
	 */
	public List getChildrenOfExpenseCategory(Integer expenseCategory_id)  {
		Map conditions =new HashMap();
		conditions.put(ExpenseSubCategoryQueryCondition.EXPENSECATEGORY_ID_EQ,expenseCategory_id);
		return this.getExpenseSubCategoryList(conditions,0,-1,ExpenseSubCategoryQueryOrder.ID,false);
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.service.admin.ExpenseSubCategoryManager#getEnabledChildrenOfExpenseCategory(java.lang.Integer)
	 */
	public List getEnabledChildrenOfExpenseCategory(Integer expenseCategory_id)  {
		Map conditions =new HashMap();
		conditions.put(ExpenseSubCategoryQueryCondition.EXPENSECATEGORY_ID_EQ,expenseCategory_id);
		conditions.put(ExpenseSubCategoryQueryCondition.ENABLED_EQ,EnabledDisabled.ENABLED.getEnumCode());
		return this.getExpenseSubCategoryList(conditions,0,-1,ExpenseSubCategoryQueryOrder.ID,false);
	}

}
