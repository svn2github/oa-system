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

import net.sourceforge.dao.admin.PurchaseSubCategoryDAO;
import net.sourceforge.model.admin.PurchaseCategory;
import net.sourceforge.model.admin.PurchaseSubCategory;
import net.sourceforge.model.admin.query.PurchaseSubCategoryQueryCondition;
import net.sourceforge.model.admin.query.PurchaseSubCategoryQueryOrder;
import net.sourceforge.model.metadata.EnabledDisabled;
import net.sourceforge.service.BaseManager;
import net.sourceforge.service.admin.PurchaseSubCategoryManager;

/**
 * PurchaseSubCategoryManagerµÄÊµÏÖ
 * @author ych
 * @version 1.0 (Nov 17, 2005)
 */
public class PurchaseSubCategoryManagerImpl extends BaseManager implements PurchaseSubCategoryManager {

    private PurchaseSubCategoryDAO dao;

    /**
     * @param dao
     */
    public void setPurchaseSubCategoryDAO(PurchaseSubCategoryDAO dao) {
        this.dao = dao;
    }
    
     /* (non-Javadoc)
     * @see net.sourceforge.service.admin.PurchaseSubCategoryManager#getPurchaseSubCategory(java.lang.Integer)
     */
    public PurchaseSubCategory getPurchaseSubCategory(Integer id)  {
        return dao.getPurchaseSubCategory(id);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.PurchaseSubCategoryManager#updatePurchaseSubCategory(net.sourceforge.model.admin.PurchaseSubCategory)
     */
    public PurchaseSubCategory updatePurchaseSubCategory(PurchaseSubCategory function)  {
        return dao.updatePurchaseSubCategory(function);
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.PurchaseSubCategoryManager#insertPurchaseSubCategory(net.sourceforge.model.admin.PurchaseSubCategory)
     */
    public PurchaseSubCategory insertPurchaseSubCategory(PurchaseSubCategory function)  {
        return dao.insertPurchaseSubCategory(function);
    }
    

    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.PurchaseSubCategoryManager#getPurchaseSubCategoryListCount(java.util.Map)
     */
    public int getPurchaseSubCategoryListCount(Map conditions)  {
        return dao.getPurchaseSubCategoryListCount(conditions);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.PurchaseSubCategoryManager#getPurchaseSubCategoryList(java.util.Map, int, int, net.sourceforge.model.admin.query.PurchaseSubCategoryQueryOrder, boolean)
     */
    public List getPurchaseSubCategoryList(Map conditions, int pageNo, int pageSize, PurchaseSubCategoryQueryOrder order, boolean descend)  {
        return dao.getPurchaseSubCategoryList(conditions, pageNo, pageSize, order, descend);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.PurchaseSubCategoryManager#getEnabledPurchaseSubCategoryOfPurchaseCategory(net.sourceforge.model.admin.PurchaseCategory)
     */
    public List getEnabledPurchaseSubCategoryOfPurchaseCategory(PurchaseCategory category) {
        Map conds = new HashMap();
        conds.put(PurchaseSubCategoryQueryCondition.ENABLED_EQ, EnabledDisabled.ENABLED.getEnumCode());
        conds.put(PurchaseSubCategoryQueryCondition.PURCHASECATEGORY_ID_EQ, category.getId());
        return getPurchaseSubCategoryList(conds, 0, -1, PurchaseSubCategoryQueryOrder.DESCRIPTION, false);
    }

}
