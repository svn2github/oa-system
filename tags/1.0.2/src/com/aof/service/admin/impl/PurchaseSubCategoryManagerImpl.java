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

import com.aof.dao.admin.PurchaseSubCategoryDAO;
import com.aof.model.admin.PurchaseCategory;
import com.aof.model.admin.PurchaseSubCategory;
import com.aof.model.admin.query.PurchaseSubCategoryQueryCondition;
import com.aof.model.admin.query.PurchaseSubCategoryQueryOrder;
import com.aof.model.metadata.EnabledDisabled;
import com.aof.service.BaseManager;
import com.aof.service.admin.PurchaseSubCategoryManager;

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
     * @see com.aof.service.admin.PurchaseSubCategoryManager#getPurchaseSubCategory(java.lang.Integer)
     */
    public PurchaseSubCategory getPurchaseSubCategory(Integer id)  {
        return dao.getPurchaseSubCategory(id);
    }

    /* (non-Javadoc)
     * @see com.aof.service.admin.PurchaseSubCategoryManager#updatePurchaseSubCategory(com.aof.model.admin.PurchaseSubCategory)
     */
    public PurchaseSubCategory updatePurchaseSubCategory(PurchaseSubCategory function)  {
        return dao.updatePurchaseSubCategory(function);
    }
    
    /* (non-Javadoc)
     * @see com.aof.service.admin.PurchaseSubCategoryManager#insertPurchaseSubCategory(com.aof.model.admin.PurchaseSubCategory)
     */
    public PurchaseSubCategory insertPurchaseSubCategory(PurchaseSubCategory function)  {
        return dao.insertPurchaseSubCategory(function);
    }
    

    /* (non-Javadoc)
     * @see com.aof.service.admin.PurchaseSubCategoryManager#getPurchaseSubCategoryListCount(java.util.Map)
     */
    public int getPurchaseSubCategoryListCount(Map conditions)  {
        return dao.getPurchaseSubCategoryListCount(conditions);
    }

    /* (non-Javadoc)
     * @see com.aof.service.admin.PurchaseSubCategoryManager#getPurchaseSubCategoryList(java.util.Map, int, int, com.aof.model.admin.query.PurchaseSubCategoryQueryOrder, boolean)
     */
    public List getPurchaseSubCategoryList(Map conditions, int pageNo, int pageSize, PurchaseSubCategoryQueryOrder order, boolean descend)  {
        return dao.getPurchaseSubCategoryList(conditions, pageNo, pageSize, order, descend);
    }

    /* (non-Javadoc)
     * @see com.aof.service.admin.PurchaseSubCategoryManager#getEnabledPurchaseSubCategoryOfPurchaseCategory(com.aof.model.admin.PurchaseCategory)
     */
    public List getEnabledPurchaseSubCategoryOfPurchaseCategory(PurchaseCategory category) {
        Map conds = new HashMap();
        conds.put(PurchaseSubCategoryQueryCondition.ENABLED_EQ, EnabledDisabled.ENABLED.getEnumCode());
        conds.put(PurchaseSubCategoryQueryCondition.PURCHASECATEGORY_ID_EQ, category.getId());
        return getPurchaseSubCategoryList(conds, 0, -1, PurchaseSubCategoryQueryOrder.DESCRIPTION, false);
    }

}
