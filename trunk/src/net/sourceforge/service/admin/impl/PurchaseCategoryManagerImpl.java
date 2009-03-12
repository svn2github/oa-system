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

import net.sourceforge.dao.admin.PurchaseCategoryDAO;
import net.sourceforge.dao.admin.PurchaseSubCategoryDAO;
import net.sourceforge.model.admin.PurchaseCategory;
import net.sourceforge.model.admin.PurchaseSubCategory;
import net.sourceforge.model.admin.Site;
import net.sourceforge.model.admin.query.PurchaseCategoryQueryCondition;
import net.sourceforge.model.admin.query.PurchaseCategoryQueryOrder;
import net.sourceforge.model.admin.query.PurchaseSubCategoryQueryCondition;
import net.sourceforge.model.admin.query.PurchaseSubCategoryQueryOrder;
import net.sourceforge.model.metadata.EnabledDisabled;
import net.sourceforge.service.BaseManager;
import net.sourceforge.service.admin.PurchaseCategoryManager;

/**
 * PurchaseCategoryManagerµÄÊµÏÖ
 * @author ych
 * @version 1.0 (Nov 17, 2005)
 */
public class PurchaseCategoryManagerImpl extends BaseManager implements PurchaseCategoryManager {

    private PurchaseCategoryDAO dao;

    private PurchaseSubCategoryDAO purchaseSubCategoryDAO;

    /**
     * @param dao
     */
    public void setPurchaseCategoryDAO(PurchaseCategoryDAO dao) {
        this.dao = dao;
    }
    
    /**
     * @param purchaseSubCategoryDAO The purchaseSubCategoryDAO to set.
     */
    public void setPurchaseSubCategoryDAO(PurchaseSubCategoryDAO purchaseSubCategoryDAO) {
        this.purchaseSubCategoryDAO = purchaseSubCategoryDAO;
    }

    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.PurchaseCategoryManager#getPurchaseCategory(java.lang.Integer)
     */
    public PurchaseCategory getPurchaseCategory(Integer id)  {
        return dao.getPurchaseCategory(id);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.PurchaseCategoryManager#updatePurchaseCategory(net.sourceforge.model.admin.PurchaseCategory)
     */
    public PurchaseCategory updatePurchaseCategory(PurchaseCategory function)  {
        return dao.updatePurchaseCategory(function);
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.PurchaseCategoryManager#insertPurchaseCategory(net.sourceforge.model.admin.PurchaseCategory)
     */
    public PurchaseCategory insertPurchaseCategory(PurchaseCategory function)  {
        return dao.insertPurchaseCategory(function);
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.PurchaseCategoryManager#getPurchaseCategoryListCount(java.util.Map)
     */
    public int getPurchaseCategoryListCount(Map conditions)  {
        return dao.getPurchaseCategoryListCount(conditions);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.PurchaseCategoryManager#getPurchaseCategoryList(java.util.Map, int, int, net.sourceforge.model.admin.query.PurchaseCategoryQueryOrder, boolean)
     */
    public List getPurchaseCategoryList(Map conditions, int pageNo, int pageSize, PurchaseCategoryQueryOrder order, boolean descend)  {
        return dao.getPurchaseCategoryList(conditions, pageNo, pageSize, order, descend);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.PurchaseCategoryManager#getEnabledPurchaseCategoryOfSite(net.sourceforge.model.admin.Site)
     */
    public List getEnabledPurchaseCategoryOfSite(Site site) {
        Map conds = new HashMap();
        conds.put(PurchaseCategoryQueryCondition.ENABLED_EQ, EnabledDisabled.ENABLED.getEnumCode());
        conds.put(PurchaseCategoryQueryCondition.SITE_ID_EQ, site.getId());
        return getPurchaseCategoryList(conds, 0, -1, PurchaseCategoryQueryOrder.DESCRIPTION, false);
    }
    
    public List getEnabledPurchaseCategoryOfSiteIncludeGlobal(Site site) {
        Map conds = new HashMap();
        conds.put(PurchaseCategoryQueryCondition.ENABLED_EQ, EnabledDisabled.ENABLED.getEnumCode());
        conds.put(PurchaseCategoryQueryCondition.GLOBAL_OR_SITE_ID_EQ, site.getId());
        return getPurchaseCategoryList(conds, 0, -1, PurchaseCategoryQueryOrder.DESCRIPTION, false);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.PurchaseCategoryManager#getEnabledPurchaseCategorySubCategoryOfSite(net.sourceforge.model.admin.Site)
     */
    public List getEnabledPurchaseCategorySubCategoryOfSite(Site site)  {
        List purchaseCategoryList = getEnabledPurchaseCategoryOfSite(site);
        for (Iterator itor = purchaseCategoryList.iterator(); itor.hasNext();) {
            ((PurchaseCategory) itor.next()).setEnabledPurchaseSubCategoryList(new ArrayList());
        }
        Map conds = new HashMap();
        conds.put(PurchaseSubCategoryQueryCondition.ENABLED_EQ, EnabledDisabled.ENABLED.getEnumCode());
        conds.put(PurchaseSubCategoryQueryCondition.PURCHASECATEGORY_SITE_ID_EQ, site.getId());
        for (Iterator itor = purchaseSubCategoryDAO.getPurchaseSubCategoryList(conds, 0, -1, PurchaseSubCategoryQueryOrder.DESCRIPTION, false).iterator(); itor.hasNext(); ) {
            PurchaseSubCategory psc = (PurchaseSubCategory) itor.next();
            psc.getPurchaseCategory().addEnabledPurchaseSubCategory(psc);
        }
        return purchaseCategoryList;
    }
    
    public List getEnabledPurchaseCategorySubCategoryOfSiteIncludeGlobal(Site site)  {
        List purchaseCategoryList = getEnabledPurchaseCategoryOfSiteIncludeGlobal(site);
        for (Iterator itor = purchaseCategoryList.iterator(); itor.hasNext();) {
            ((PurchaseCategory) itor.next()).setEnabledPurchaseSubCategoryList(new ArrayList());
        }
        Map conds = new HashMap();
        conds.put(PurchaseSubCategoryQueryCondition.ENABLED_EQ, EnabledDisabled.ENABLED.getEnumCode());
        conds.put(PurchaseSubCategoryQueryCondition.PURCHASECATEGORY_SITE_ID_EQ_OR_NULL, site.getId());
        for (Iterator itor = purchaseSubCategoryDAO.getPurchaseSubCategoryList(conds, 0, -1, PurchaseSubCategoryQueryOrder.DESCRIPTION, false).iterator(); itor.hasNext(); ) {
            PurchaseSubCategory psc = (PurchaseSubCategory) itor.next();
            psc.getPurchaseCategory().addEnabledPurchaseSubCategory(psc);
        }
        return purchaseCategoryList;
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.PurchaseCategoryManager#getEnabledPurchaseCategorySubCategory()
     */
    public List getEnabledPurchaseCategorySubCategoryOfGlobal()  {
        Map conds = new HashMap();
        conds.put(PurchaseCategoryQueryCondition.ENABLED_EQ, EnabledDisabled.ENABLED.getEnumCode());
        conds.put(PurchaseCategoryQueryCondition.GLOBAL, null);
        List purchaseCategoryList = getPurchaseCategoryList(conds, 0, -1, PurchaseCategoryQueryOrder.DESCRIPTION, false);
        for (Iterator itor = purchaseCategoryList.iterator(); itor.hasNext();) {
            ((PurchaseCategory) itor.next()).setEnabledPurchaseSubCategoryList(new ArrayList());
        }
        conds.clear();
        conds.put(PurchaseSubCategoryQueryCondition.ENABLED_EQ, EnabledDisabled.ENABLED.getEnumCode());
        for (Iterator itor = purchaseSubCategoryDAO.getPurchaseSubCategoryList(conds, 0, -1, PurchaseSubCategoryQueryOrder.DESCRIPTION, false).iterator(); itor.hasNext(); ) {
            PurchaseSubCategory psc = (PurchaseSubCategory) itor.next();
            psc.getPurchaseCategory().addEnabledPurchaseSubCategory(psc);
        }
        return purchaseCategoryList;
    }

    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.PurchaseCategoryManager#fillEnabledPurchaseCategorySubCategoryForSiteList(java.util.List)
     */
    public void fillEnabledPurchaseCategorySubCategoryForSiteList(List siteList) {
        for (Iterator itor = siteList.iterator(); itor.hasNext();) {
            Site s = (Site) itor.next();
            s.setEnabledPurchaseCategoryList(getEnabledPurchaseCategorySubCategoryOfSiteIncludeGlobal(s));            
        }
    }

}
