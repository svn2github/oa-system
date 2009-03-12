/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.dao.admin.hibernate;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sourceforge.dao.BaseDAOHibernate;
import net.sourceforge.dao.admin.PurchaseSubCategoryDAO;
import net.sourceforge.dao.convert.LikeConvert;
import org.apache.commons.beanutils.PropertyUtils;

import net.sourceforge.model.admin.PurchaseSubCategory;
import net.sourceforge.model.admin.query.PurchaseSubCategoryQueryCondition;
import net.sourceforge.model.admin.query.PurchaseSubCategoryQueryOrder;

/**
 * PurchaseSubCategoryDAOµÄHibernateÊµÏÖ 
 * @author ych
 * @version 1.0 (Nov 13, 2005)
 */
public class PurchaseSubCategoryDAOHibernate extends BaseDAOHibernate implements PurchaseSubCategoryDAO {
    private Log log = LogFactory.getLog(PurchaseSubCategoryDAOHibernate.class);


    /* (non-Javadoc)
     * @see net.sourceforge.dao.admin.PurchaseSubCategoryDAO#getPurchaseSubCategory(java.lang.Integer)
     */
    public PurchaseSubCategory getPurchaseSubCategory(Integer id){
        if (id == null) {
            if (log.isDebugEnabled()) log.debug("try to get PurchaseSubCategory with null id");
            return null;
        }
        return (PurchaseSubCategory) getHibernateTemplate().get(PurchaseSubCategory.class, id);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.dao.admin.PurchaseSubCategoryDAO#updatePurchaseSubCategory(net.sourceforge.model.admin.PurchaseSubCategory)
     */
    public PurchaseSubCategory updatePurchaseSubCategory(PurchaseSubCategory purchaseSubCategory) {
        Integer id = purchaseSubCategory.getId();
        if (id == null) {
            throw new RuntimeException("cannot save a purchaseSubCategory with null id");
        }
        PurchaseSubCategory oldPurchaseSubCategory = getPurchaseSubCategory(id);
        if (oldPurchaseSubCategory != null) {
        	try{
                PropertyUtils.copyProperties(oldPurchaseSubCategory,purchaseSubCategory);
        	}
        	catch(Exception e)
        	{
                throw new RuntimeException("copy error£º"+e);
        	}
        	getHibernateTemplate().update(oldPurchaseSubCategory);	
        	return oldPurchaseSubCategory;
        }
        else
        {
        	throw new RuntimeException("purchaseSubCategory not found");
        }
    }

	/* (non-Javadoc)
	 * @see net.sourceforge.dao.admin.PurchaseSubCategoryDAO#insertPurchaseSubCategory(net.sourceforge.model.admin.PurchaseSubCategory)
	 */
	public PurchaseSubCategory insertPurchaseSubCategory(PurchaseSubCategory purchaseSubCategory) {
       	getHibernateTemplate().save(purchaseSubCategory);
       	return purchaseSubCategory;
    }

    private final static String SQL_COUNT = "select count(*) from PurchaseSubCategory purchaseSubCategory";

    private final static String SQL = "from PurchaseSubCategory purchaseSubCategory";

    private final static Object[][] QUERY_CONDITIONS = {
    	/*id*/    
		{ PurchaseSubCategoryQueryCondition.ID_EQ, "purchaseSubCategory.id = ?", null },

		/*keyPropertyList*/

		/*kmtoIdList*/

		/*mtoIdList*/
        { PurchaseSubCategoryQueryCondition.PURCHASECATEGORY_SITE_ID_EQ, "purchaseSubCategory.purchaseCategory.site.id = ?", null },
        { PurchaseSubCategoryQueryCondition.PURCHASECATEGORY_SITE_ID_EQ_OR_NULL, "purchaseSubCategory.purchaseCategory.site.id = ? or purchaseSubCategory.purchaseCategory.site.id is null", null },
		{ PurchaseSubCategoryQueryCondition.PURCHASECATEGORY_ID_EQ, "purchaseSubCategory.purchaseCategory.id = ?", null },
		{ PurchaseSubCategoryQueryCondition.DEFAULTSUPPLIER_ID_EQ, "purchaseSubCategory.defaultSupplier.id = ?", null },

		/*property*/
		{ PurchaseSubCategoryQueryCondition.DESCRIPTION_LIKE, "purchaseSubCategory.description like ?", new LikeConvert() },
		{ PurchaseSubCategoryQueryCondition.ENABLED_EQ, "purchaseSubCategory.enabled = ?", null },
    };
    
    private final static Object[][] QUERY_ORDERS = {
		/*id*/
        { PurchaseSubCategoryQueryOrder.ID, "purchaseSubCategory.id" },

		/*property*/
        { PurchaseSubCategoryQueryOrder.SUPPLIER,"purchaseSubCategory.defaultSupplier.name"},
        { PurchaseSubCategoryQueryOrder.DESCRIPTION, "purchaseSubCategory.description" },
        { PurchaseSubCategoryQueryOrder.ENABLED, "purchaseSubCategory.enabled" },
    };
    
    /* (non-Javadoc)
     * @see net.sourceforge.dao.admin.PurchaseSubCategoryDAO#getPurchaseSubCategoryListCount(java.util.Map)
     */
    public int getPurchaseSubCategoryListCount(final Map conditions) {
        return getListCount(conditions, SQL_COUNT, QUERY_CONDITIONS);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.dao.admin.PurchaseSubCategoryDAO#getPurchaseSubCategoryList(java.util.Map, int, int, net.sourceforge.model.admin.query.PurchaseSubCategoryQueryOrder, boolean)
     */
    public List getPurchaseSubCategoryList(final Map conditions, final int pageNo, final int pageSize, final PurchaseSubCategoryQueryOrder order, final boolean descend) {
        return getList(conditions, pageNo, pageSize, order, descend, SQL, QUERY_CONDITIONS, QUERY_ORDERS);
    }

}
