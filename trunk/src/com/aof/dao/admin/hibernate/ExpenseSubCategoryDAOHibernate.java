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
import com.aof.dao.admin.ExpenseSubCategoryDAO;
import com.aof.dao.convert.LikeConvert;
import com.aof.model.admin.ExpenseSubCategory;
import com.aof.model.admin.query.ExpenseSubCategoryQueryCondition;
import com.aof.model.admin.query.ExpenseSubCategoryQueryOrder;

/**
 * hibernate implement for ExpenseSubCategoryDAO
 * @author shilei
 * @version 1.0 (Nov 15, 2005)
 */
public class ExpenseSubCategoryDAOHibernate extends BaseDAOHibernate implements ExpenseSubCategoryDAO {
    private Log log = LogFactory.getLog(ExpenseSubCategoryDAOHibernate.class);


    /* (non-Javadoc)
     * @see com.aof.dao.admin.ExpenseSubCategoryDAO#getExpenseSubCategory(java.lang.Integer)
     */
    public ExpenseSubCategory getExpenseSubCategory(Integer id){
        if (id == null) {
            if (log.isDebugEnabled()) log.debug("try to get ExpenseSubCategory with null id");
            return null;
        }
        return (ExpenseSubCategory) getHibernateTemplate().get(ExpenseSubCategory.class, id);
    }

    /* (non-Javadoc)
     * @see com.aof.dao.admin.ExpenseSubCategoryDAO#updateExpenseSubCategory(com.aof.model.admin.ExpenseSubCategory)
     */
    public ExpenseSubCategory updateExpenseSubCategory(ExpenseSubCategory expenseSubCategory) {
        Integer id = expenseSubCategory.getId();
        if (id == null) {
            throw new RuntimeException("cannot save a expenseSubCategory with null id");
        }
        ExpenseSubCategory oldExpenseSubCategory = getExpenseSubCategory(id);
        if (oldExpenseSubCategory != null) {
        	try{
                PropertyUtils.copyProperties(oldExpenseSubCategory,expenseSubCategory);
        	}
        	catch(Exception e)
        	{
                throw new RuntimeException("copy error£º"+e);
        	}
        	getHibernateTemplate().update(oldExpenseSubCategory);	
        	return oldExpenseSubCategory;
        }
        else
        {
        	throw new RuntimeException("expenseSubCategory not found");
        }
    }

	/* (non-Javadoc)
	 * @see com.aof.dao.admin.ExpenseSubCategoryDAO#insertExpenseSubCategory(com.aof.model.admin.ExpenseSubCategory)
	 */
	public ExpenseSubCategory insertExpenseSubCategory(ExpenseSubCategory expenseSubCategory) {
       	getHibernateTemplate().save(expenseSubCategory);
       	return expenseSubCategory;
    }

    private final static String SQL_COUNT = "select count(*) from ExpenseSubCategory expenseSubCategory";

    private final static String SQL = "from ExpenseSubCategory expenseSubCategory";

    private final static Object[][] QUERY_CONDITIONS = {
    	/*id*/    
		{ ExpenseSubCategoryQueryCondition.ID_EQ, "expenseSubCategory.id = ?", null },

		/*keyPropertyList*/

		/*kmtoIdList*/

		/*mtoIdList*/
		{ ExpenseSubCategoryQueryCondition.EXPENSECATEGORY_ID_EQ, "expenseSubCategory.expenseCategory.id = ?", null },
        { ExpenseSubCategoryQueryCondition.EXPENSECATEGORY_SITE_ID_EQ, "expenseSubCategory.expenseCategory.site.id = ?", null },

		/*property*/
		{ ExpenseSubCategoryQueryCondition.DESCRIPTION_LIKE, "expenseSubCategory.description like ?", new LikeConvert() },
		{ ExpenseSubCategoryQueryCondition.REFERENCEERPID_LIKE, "expenseSubCategory.referenceErpId like ?", new LikeConvert() },
		{ ExpenseSubCategoryQueryCondition.ENABLED_EQ, "expenseSubCategory.enabled = ?", null },
		{ ExpenseSubCategoryQueryCondition.ISHOTEL_EQ, "expenseSubCategory.isHotel = ?", null },
    };
    
    private final static Object[][] QUERY_ORDERS = {
		/*id*/
        { ExpenseSubCategoryQueryOrder.ID, "expenseSubCategory.id" },

		/*property*/
        { ExpenseSubCategoryQueryOrder.DESCRIPTION, "expenseSubCategory.description" },
        { ExpenseSubCategoryQueryOrder.REFERENCEERPID, "expenseSubCategory.referenceErpId" },
        { ExpenseSubCategoryQueryOrder.ENABLED, "expenseSubCategory.enabled" },
    };
    
    /* (non-Javadoc)
     * @see com.aof.dao.admin.ExpenseSubCategoryDAO#getExpenseSubCategoryListCount(java.util.Map)
     */
    public int getExpenseSubCategoryListCount(final Map conditions) {
        return getListCount(conditions, SQL_COUNT, QUERY_CONDITIONS);
    }

    /* (non-Javadoc)
     * @see com.aof.dao.admin.ExpenseSubCategoryDAO#getExpenseSubCategoryList(java.util.Map, int, int, com.aof.model.admin.query.ExpenseSubCategoryQueryOrder, boolean)
     */
    public List getExpenseSubCategoryList(final Map conditions, final int pageNo, final int pageSize, final ExpenseSubCategoryQueryOrder order, final boolean descend) {
        return getList(conditions, pageNo, pageSize, order, descend, SQL, QUERY_CONDITIONS, QUERY_ORDERS);
    }

}
