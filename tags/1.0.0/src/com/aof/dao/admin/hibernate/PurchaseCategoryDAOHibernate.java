/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.dao.admin.hibernate;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.aof.dao.BaseDAOHibernate;
import com.aof.dao.admin.PurchaseCategoryDAO;
import com.aof.dao.convert.LikeConvert;
import org.apache.commons.beanutils.PropertyUtils;

import com.aof.model.admin.PurchaseCategory;
import com.aof.model.admin.query.PurchaseCategoryQueryCondition;
import com.aof.model.admin.query.PurchaseCategoryQueryOrder;

/**
 * PurchaseCategoryDAOµÄHibernateÊµÏÖ 
 * @author ych
 * @version 1.0 (Nov 13, 2005)
 */
public class PurchaseCategoryDAOHibernate extends BaseDAOHibernate implements PurchaseCategoryDAO {
    private Log log = LogFactory.getLog(PurchaseCategoryDAOHibernate.class);


    /* (non-Javadoc)
     * @see com.aof.dao.admin.PurchaseCategoryDAO#getPurchaseCategory(java.lang.Integer)
     */
    public PurchaseCategory getPurchaseCategory(Integer id){
        if (id == null) {
            if (log.isDebugEnabled()) log.debug("try to get PurchaseCategory with null id");
            return null;
        }
        return (PurchaseCategory) getHibernateTemplate().get(PurchaseCategory.class, id);
    }

    /* (non-Javadoc)
     * @see com.aof.dao.admin.PurchaseCategoryDAO#updatePurchaseCategory(com.aof.model.admin.PurchaseCategory)
     */
    public PurchaseCategory updatePurchaseCategory(PurchaseCategory purchaseCategory) {
        Integer id = purchaseCategory.getId();
        if (id == null) {
            throw new RuntimeException("cannot save a purchaseCategory with null id");
        }
        PurchaseCategory oldPurchaseCategory = getPurchaseCategory(id);
        if (oldPurchaseCategory != null) {
        	try{
                PropertyUtils.copyProperties(oldPurchaseCategory,purchaseCategory);
        	}
        	catch(Exception e)
        	{
                throw new RuntimeException("copy error£º"+e);
        	}
        	getHibernateTemplate().update(oldPurchaseCategory);	
        	return oldPurchaseCategory;
        }
        else
        {
        	throw new RuntimeException("purchaseCategory not found");
        }
    }

	/* (non-Javadoc)
	 * @see com.aof.dao.admin.PurchaseCategoryDAO#insertPurchaseCategory(com.aof.model.admin.PurchaseCategory)
	 */
	public PurchaseCategory insertPurchaseCategory(PurchaseCategory purchaseCategory) {
       	getHibernateTemplate().save(purchaseCategory);
       	return purchaseCategory;
    }

    private final static String SQL_COUNT = "select count(*) from PurchaseCategory purchaseCategory";

    private final static String SQL = "from PurchaseCategory purchaseCategory";

    private final static Object[][] QUERY_CONDITIONS = {
		{ PurchaseCategoryQueryCondition.ID_EQ, "purchaseCategory.id = ?", null },
        { PurchaseCategoryQueryCondition.GLOBAL, "purchaseCategory.site.id is null", null },
		{ PurchaseCategoryQueryCondition.SITE_ID_EQ, "purchaseCategory.site.id = ?", null },
        { PurchaseCategoryQueryCondition.GLOBAL_OR_SITE_ID_EQ, "purchaseCategory.site.id is null or purchaseCategory.site.id = ?", null },
		{ PurchaseCategoryQueryCondition.DESCRIPTION_LIKE, "purchaseCategory.description like ?", new LikeConvert() },
		{ PurchaseCategoryQueryCondition.ENABLED_EQ, "purchaseCategory.enabled = ?", null },
    };
    
    private final static Object[][] QUERY_ORDERS = {
		/*id*/
        { PurchaseCategoryQueryOrder.ID, "purchaseCategory.id" },

		/*property*/
        { PurchaseCategoryQueryOrder.DESCRIPTION, "purchaseCategory.description" },
        { PurchaseCategoryQueryOrder.ENABLED, "purchaseCategory.enabled" },
    };
    
    /* (non-Javadoc)
     * @see com.aof.dao.admin.PurchaseCategoryDAO#getPurchaseCategoryListCount(java.util.Map)
     */
    public int getPurchaseCategoryListCount(final Map conditions) {
        return getListCount(conditions, SQL_COUNT, QUERY_CONDITIONS);
    }

    /* (non-Javadoc)
     * @see com.aof.dao.admin.PurchaseCategoryDAO#getPurchaseCategoryList(java.util.Map, int, int, com.aof.model.admin.query.PurchaseCategoryQueryOrder, boolean)
     */
    public List getPurchaseCategoryList(final Map conditions, final int pageNo, final int pageSize, final PurchaseCategoryQueryOrder order, final boolean descend) {
        return getList(conditions, pageNo, pageSize, order, descend, SQL, QUERY_CONDITIONS, QUERY_ORDERS);
    }

}
