/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.dao.admin.hibernate;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.aof.dao.BaseDAOHibernate;
import com.aof.dao.admin.ExpenseCategoryDAO;
import com.aof.dao.convert.LikeConvert;
import com.aof.model.admin.ExpenseCategory;
import com.aof.model.admin.query.ExpenseCategoryQueryCondition;
import com.aof.model.admin.query.ExpenseCategoryQueryOrder;

/**
 * hibernate implement for ExpenseCategoryDAO
 * 
 * @author shilei
 * @version 1.0 (Nov 15, 2005)
 */
public class ExpenseCategoryDAOHibernate extends BaseDAOHibernate implements ExpenseCategoryDAO {
    private Log log = LogFactory.getLog(ExpenseCategoryDAOHibernate.class);

    /* (non-Javadoc)
     * @see com.aof.dao.admin.ExpenseCategoryDAO#getExpenseCategory(java.lang.Integer)
     */
    public ExpenseCategory getExpenseCategory(Integer id) {
        if (id == null) {
            if (log.isDebugEnabled())
                log.debug("try to get ExpenseCategory with null id");
            return null;
        }
        return (ExpenseCategory) getHibernateTemplate().get(ExpenseCategory.class, id);
    }

    /* (non-Javadoc)
     * @see com.aof.dao.admin.ExpenseCategoryDAO#updateExpenseCategory(com.aof.model.admin.ExpenseCategory)
     */
    public ExpenseCategory updateExpenseCategory(ExpenseCategory expenseCategory) {
        Integer id = expenseCategory.getId();
        if (id == null) {
            throw new RuntimeException("cannot save a expenseCategory with null id");
        }
        ExpenseCategory oldExpenseCategory = getExpenseCategory(id);
        if (oldExpenseCategory != null) {
            try {
                PropertyUtils.copyProperties(oldExpenseCategory, expenseCategory);
            } catch (Exception e) {
                throw new RuntimeException("copy error£º"+e);
            }
            getHibernateTemplate().update(oldExpenseCategory);
            return oldExpenseCategory;
        } else {
            throw new RuntimeException("expenseCategory not found");
        }
    }

    /* (non-Javadoc)
     * @see com.aof.dao.admin.ExpenseCategoryDAO#insertExpenseCategory(com.aof.model.admin.ExpenseCategory)
     */
    public ExpenseCategory insertExpenseCategory(ExpenseCategory expenseCategory) {
        getHibernateTemplate().save(expenseCategory);
        return expenseCategory;
    }

    private final static String SQL_COUNT = "select count(*) from ExpenseCategory expenseCategory";

    private final static String SQL = "from ExpenseCategory expenseCategory";

    private final static Object[][] QUERY_CONDITIONS = {
        /* id */
        { ExpenseCategoryQueryCondition.ID_EQ, "expenseCategory.id = ?", null },
    
        /* keyPropertyList */
    
        /* kmtoIdList */
    
        /* mtoIdList */
        { ExpenseCategoryQueryCondition.SITE_ID_EQ, "expenseCategory.site.id = ?", null },
    
        /* property */
        { ExpenseCategoryQueryCondition.DESCRIPTION_LIKE, "expenseCategory.description like ?", new LikeConvert() },
        { ExpenseCategoryQueryCondition.TYPE_EQ, "expenseCategory.type = ?", null },
        { ExpenseCategoryQueryCondition.TYPE_NEQ, "expenseCategory.type <> ?", null },
        { ExpenseCategoryQueryCondition.REFERENCEERPID_LIKE, "expenseCategory.referenceErpId like ?", new LikeConvert() },
        { ExpenseCategoryQueryCondition.ENABLED_EQ, "expenseCategory.enabled = ?", null },

    };

    private final static Object[][] QUERY_ORDERS = {
    /* id */
    { ExpenseCategoryQueryOrder.ID, "expenseCategory.id" },

    /* property */
    { ExpenseCategoryQueryOrder.DESCRIPTION, "expenseCategory.description" }, { ExpenseCategoryQueryOrder.TYPE, "expenseCategory.type" },
            { ExpenseCategoryQueryOrder.REFERENCEERPID, "expenseCategory.referenceErpId" }, { ExpenseCategoryQueryOrder.ENABLED, "expenseCategory.enabled" }, };

    /* (non-Javadoc)
     * @see com.aof.dao.admin.ExpenseCategoryDAO#getExpenseCategoryListCount(java.util.Map)
     */
    public int getExpenseCategoryListCount(final Map conditions) {
        return getListCount(conditions, SQL_COUNT, QUERY_CONDITIONS);
    }

    /* (non-Javadoc)
     * @see com.aof.dao.admin.ExpenseCategoryDAO#getExpenseCategoryList(java.util.Map, int, int, com.aof.model.admin.query.ExpenseCategoryQueryOrder, boolean)
     */
    public List getExpenseCategoryList(final Map conditions, final int pageNo, final int pageSize, final ExpenseCategoryQueryOrder order, final boolean descend) {
        return getList(conditions, pageNo, pageSize, order, descend, SQL, QUERY_CONDITIONS, QUERY_ORDERS);
    }

}
