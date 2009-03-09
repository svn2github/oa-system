/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.service.admin.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.aof.dao.admin.ExpenseCategoryDAO;
import com.aof.dao.admin.ExpenseSubCategoryDAO;
import com.aof.model.admin.ExpenseCategory;
import com.aof.model.admin.ExpenseSubCategory;
import com.aof.model.admin.Site;
import com.aof.model.admin.query.ExpenseCategoryQueryCondition;
import com.aof.model.admin.query.ExpenseCategoryQueryOrder;
import com.aof.model.admin.query.ExpenseSubCategoryQueryCondition;
import com.aof.model.admin.query.ExpenseSubCategoryQueryOrder;
import com.aof.model.metadata.EnabledDisabled;
import com.aof.model.metadata.ExpenseType;
import com.aof.service.BaseManager;
import com.aof.service.admin.ExpenseCategoryManager;

/**
 * implement for ExpenseCategoryManager
 * @author shilei
 * @version 1.0 (Nov 15, 2005)
 */
public class ExpenseCategoryManagerImpl extends BaseManager implements ExpenseCategoryManager {
    private ExpenseCategoryDAO dao;
    private ExpenseSubCategoryDAO expenseSubCategoryDAO;

    /**
     * @param dao
     */
    public void setExpenseCategoryDAO(ExpenseCategoryDAO dao) {
        this.dao = dao;
    }

    /**
     * @param expenseSubCategoryDAO The expenseSubCategoryDAO to set.
     */
    public void setExpenseSubCategoryDAO(ExpenseSubCategoryDAO expenseSubCategoryDAO) {
        this.expenseSubCategoryDAO = expenseSubCategoryDAO;
    }

    /* (non-Javadoc)
     * @see com.aof.service.admin.ExpenseCategoryManager#getExpenseCategory(java.lang.Integer)
     */
    public ExpenseCategory getExpenseCategory(Integer id)  {
        return dao.getExpenseCategory(id);
    }

    /* (non-Javadoc)
     * @see com.aof.service.admin.ExpenseCategoryManager#updateExpenseCategory(com.aof.model.admin.ExpenseCategory)
     */
    public ExpenseCategory updateExpenseCategory(ExpenseCategory function)  {
        return dao.updateExpenseCategory(function);
    }

    /* (non-Javadoc)
     * @see com.aof.service.admin.ExpenseCategoryManager#insertExpenseCategory(com.aof.model.admin.ExpenseCategory)
     */
    public ExpenseCategory insertExpenseCategory(ExpenseCategory function)  {
        return dao.insertExpenseCategory(function);
    }

    /* (non-Javadoc)
     * @see com.aof.service.admin.ExpenseCategoryManager#getExpenseCategoryListCount(java.util.Map)
     */
    public int getExpenseCategoryListCount(Map conditions)  {
        return dao.getExpenseCategoryListCount(conditions);
    }

    /* (non-Javadoc)
     * @see com.aof.service.admin.ExpenseCategoryManager#getExpenseCategoryList(java.util.Map, int, int, com.aof.model.admin.query.ExpenseCategoryQueryOrder, boolean)
     */
    public List getExpenseCategoryList(Map conditions, int pageNo, int pageSize, ExpenseCategoryQueryOrder order, boolean descend)  {
        return dao.getExpenseCategoryList(conditions, pageNo, pageSize, order, descend);
    }

    /* (non-Javadoc)
     * @see com.aof.service.admin.ExpenseCategoryManager#getEnabledTravelExpenseCategoryOfSite(int)
     */
    public ExpenseCategory getEnabledTravelExpenseCategoryOfSite(int site_id)  {
        Map conds = new HashMap();
        conds.put(ExpenseCategoryQueryCondition.ENABLED_EQ, EnabledDisabled.ENABLED.getEnumCode());
        conds.put(ExpenseCategoryQueryCondition.SITE_ID_EQ, new Integer(site_id));
        conds.put(ExpenseCategoryQueryCondition.TYPE_EQ, ExpenseType.TRAVEL.getEnumCode());

        List l = this.getExpenseCategoryList(conds, 0, -1, ExpenseCategoryQueryOrder.ID, false);
        if (l.isEmpty()) {
            return null;
        } else {
            return (ExpenseCategory) l.get(0);
        }
    }
    
    public List getEnabledNotTravelExpenseCategoryListOfSite(int site_id)  {
        Map conds = new HashMap();
        conds.put(ExpenseCategoryQueryCondition.ENABLED_EQ, EnabledDisabled.ENABLED.getEnumCode());
        conds.put(ExpenseCategoryQueryCondition.SITE_ID_EQ, new Integer(site_id));
        conds.put(ExpenseCategoryQueryCondition.TYPE_NEQ, ExpenseType.TRAVEL.getEnumCode());

        return  this.getExpenseCategoryList(conds, 0, -1, ExpenseCategoryQueryOrder.ID, false);
    }

    /* (non-Javadoc)
     * @see com.aof.service.admin.ExpenseCategoryManager#getEnabledExpenseCategorySubCategoryOfSite(com.aof.model.admin.Site)
     */
    public List getEnabledExpenseCategorySubCategoryOfSite(Site site)  {
        List expenseCategoryList = getEnabledExpenseCategoryOfSite(site);
        for (Iterator itor = expenseCategoryList.iterator(); itor.hasNext();) {
            ((ExpenseCategory) itor.next()).setEnabledExpenseSubCategoryList(new ArrayList());
        }

        Map conds = new HashMap();
        conds.put(ExpenseSubCategoryQueryCondition.ENABLED_EQ, EnabledDisabled.ENABLED.getEnumCode());
        conds.put(ExpenseSubCategoryQueryCondition.EXPENSECATEGORY_SITE_ID_EQ, site.getId());
        for (Iterator itor = expenseSubCategoryDAO.getExpenseSubCategoryList(conds, 0, -1, ExpenseSubCategoryQueryOrder.DESCRIPTION, false).iterator(); itor.hasNext(); ) {
            ExpenseSubCategory esc = (ExpenseSubCategory) itor.next();
            esc.getExpenseCategory().addEnabledExpenseSubCategory(esc);
        }
        return expenseCategoryList;
    }

    /* (non-Javadoc)
     * @see com.aof.service.admin.ExpenseCategoryManager#getEnabledExpenseCategoryOfSite(com.aof.model.admin.Site)
     */
    public List getEnabledExpenseCategoryOfSite(Site site) {
        Map conds = new HashMap();
        conds.put(ExpenseCategoryQueryCondition.ENABLED_EQ, EnabledDisabled.ENABLED.getEnumCode());
        conds.put(ExpenseCategoryQueryCondition.SITE_ID_EQ, site.getId());
        return getExpenseCategoryList(conds, 0, -1, ExpenseCategoryQueryOrder.DESCRIPTION, false);
    }

}
