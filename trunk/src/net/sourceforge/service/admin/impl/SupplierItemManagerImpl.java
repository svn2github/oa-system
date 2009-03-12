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

import net.sourceforge.dao.admin.SupplierItemDAO;
import net.sourceforge.model.admin.PurchaseSubCategory;
import net.sourceforge.model.admin.Supplier;
import net.sourceforge.model.admin.SupplierItem;
import net.sourceforge.model.admin.query.SupplierItemQueryCondition;
import net.sourceforge.model.admin.query.SupplierItemQueryOrder;
import net.sourceforge.service.BaseManager;
import net.sourceforge.service.admin.SupplierItemManager;

/**
 * SupplierItemManagerµÄÊµÏÖ
 * @author ych
 * @version 1.0 (Nov 17, 2005)
 */
public class SupplierItemManagerImpl extends BaseManager implements SupplierItemManager {

    private SupplierItemDAO dao;

    public void setSupplierItemDAO(SupplierItemDAO dao) {
        this.dao = dao;
    }
    
     public SupplierItem getSupplierItem(Integer id)  {
        return dao.getSupplierItem(id);
    }

    public SupplierItem updateSupplierItem(SupplierItem function)  {
        return dao.updateSupplierItem(function);
    }
    
    public SupplierItem insertSupplierItem(SupplierItem function)  {
        return dao.insertSupplierItem(function);
    }
    

    public int getSupplierItemListCount(Map conditions)  {
        return dao.getSupplierItemListCount(conditions);
    }

    public List getSupplierItemList(Map conditions, int pageNo, int pageSize, SupplierItemQueryOrder order, boolean descend)  {
        return dao.getSupplierItemList(conditions, pageNo, pageSize, order, descend);
    }


	public List getSupplierItemListBySupplier(Supplier supplier) {
		return dao.getSupplierItemListBySupplier(supplier);
	}
    

    public List getSupplierItemListBySupplierPurchaseSubCategoryId(Integer supplierId,Integer purchaseSubCategoryId) {
        Map conditions=new HashMap();
        conditions.put(SupplierItemQueryCondition.SUPPLIER_ID_EQ, supplierId);
        conditions.put(SupplierItemQueryCondition.PURCHASESUBCATEGORY_ID_EQ,purchaseSubCategoryId);
        return dao.getSupplierItemList(conditions,0,-1,null,false);
    }
    
    public List getSupplierItemListGroupByPurchaseSubCategory(Supplier supplier) {
        List itemList=dao.getSupplierItemListBySupplier(supplier);
        return this.getSupplierItemListGroupByPurchaseSubCategory(itemList);
    }
    
    private List getSupplierItemListGroupByPurchaseSubCategory(List itemList) {
        List result=new ArrayList();
        if (itemList.size()>0) {
            Iterator itor=itemList.iterator();
            Integer lastSubCategoryId=new Integer(-1);
            List items=null;
            while (itor.hasNext()) {
                SupplierItem item=(SupplierItem)itor.next();
                if (!lastSubCategoryId.equals(item.getPurchaseSubCategory().getId())) {
                    items=new ArrayList();
                    items.add(item);
                    lastSubCategoryId=item.getPurchaseSubCategory().getId();
                    result.add(items);
                } else {
                    items.add(item);
                }
            }
        }
        return result;
    }

	
	public void removeSupplierItem(Integer id) {
		dao.removeSupplierItem(id);
	}

    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.SupplierItemManager#getSupplierItemListConflictWithPromoteBySupplier(net.sourceforge.model.admin.Supplier)
     */
    public List getSupplierItemListConflictWithPromoteBySupplierGroupByPurchaseSubCategory(Supplier supplier) {
        List itemList=dao.getSupplierItemListConflictWithPromoteBySupplier(supplier);
        return this.getSupplierItemListGroupByPurchaseSubCategory(itemList);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.SupplierItemManager#changePurchaseSubCategoryBySupplierAndPurchaseSubCategory(java.lang.Integer, java.lang.Integer, net.sourceforge.model.admin.PurchaseSubCategory)
     */
    public void changePurchaseSubCategoryBySupplierAndPurchaseSubCategory(Integer supplierId, Integer purchaseSubCategoryId, PurchaseSubCategory destPurchaseSubCategory) {
        dao.changePurchaseSubCategoryBySupplierAndPurchaseSubCategory(supplierId,purchaseSubCategoryId,destPurchaseSubCategory);
    }
    
    

}
