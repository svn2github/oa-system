/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.service.admin.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sourceforge.dao.admin.ExpenseCategoryDAO;
import net.sourceforge.dao.admin.ExpenseSubCategoryDAO;
import net.sourceforge.model.admin.ExpenseCategory;
import net.sourceforge.model.admin.ExpenseSubCategory;
import net.sourceforge.model.admin.Site;
import net.sourceforge.model.admin.query.ExpenseCategoryQueryCondition;
import net.sourceforge.model.admin.query.ExpenseCategoryQueryOrder;
import net.sourceforge.model.admin.query.ExpenseSubCategoryQueryCondition;
import net.sourceforge.model.admin.query.ExpenseSubCategoryQueryOrder;
import net.sourceforge.model.metadata.EnabledDisabled;
import net.sourceforge.model.metadata.ExpenseType;
import net.sourceforge.service.BaseManager;
import net.sourceforge.service.admin.ExpenseCategoryManager;

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
     * @see net.sourceforge.service.admin.ExpenseCategoryManager#getExpenseCategory(java.lang.Integer)
     */
    public ExpenseCategory getExpenseCategory(Integer id)  {
        return dao.getExpenseCategory(id);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.ExpenseCategoryManager#updateExpenseCategory(net.sourceforge.model.admin.ExpenseCategory)
     */
    public ExpenseCategory updateExpenseCategory(ExpenseCategory function)  {
        return dao.updateExpenseCategory(function);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.ExpenseCategoryManager#insertExpenseCategory(net.sourceforge.model.admin.ExpenseCategory)
     */
    public ExpenseCategory insertExpenseCategory(ExpenseCategory function)  {
        return dao.insertExpenseCategory(function);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.ExpenseCategoryManager#getExpenseCategoryListCount(java.util.Map)
     */
    public int getExpenseCategoryListCount(Map conditions)  {
        return dao.getExpenseCategoryListCount(conditions);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.ExpenseCategoryManager#getExpenseCategoryList(java.util.Map, int, int, net.sourceforge.model.admin.query.ExpenseCategoryQueryOrder, boolean)
     */
    public List getExpenseCategoryList(Map conditions, int pageNo, int pageSize, ExpenseCategoryQueryOrder order, boolean descend)  {
        return dao.getExpenseCategoryList(conditions, pageNo, pageSize, order, descend);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.ExpenseCategoryManager#getEnabledTravelExpenseCategoryOfSite(int)
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
     * @see net.sourceforge.service.admin.ExpenseCategoryManager#getEnabledExpenseCategorySubCategoryOfSite(net.sourceforge.model.admin.Site)
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
     * @see net.sourceforge.service.admin.ExpenseCategoryManager#getEnabledExpenseCategoryOfSite(net.sourceforge.model.admin.Site)
     */
    public List getEnabledExpenseCategoryOfSite(Site site) {
        Map conds = new HashMap();
        conds.put(ExpenseCategoryQueryCondition.ENABLED_EQ, EnabledDisabled.ENABLED.getEnumCode());
        conds.put(ExpenseCategoryQueryCondition.SITE_ID_EQ, site.getId());
        return getExpenseCategoryList(conds, 0, -1, ExpenseCategoryQueryOrder.DESCRIPTION, false);
    }

}
