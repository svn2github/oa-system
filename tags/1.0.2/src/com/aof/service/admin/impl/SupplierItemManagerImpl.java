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

import com.aof.dao.admin.SupplierItemDAO;
import com.aof.model.admin.PurchaseSubCategory;
import com.aof.model.admin.Supplier;
import com.aof.model.admin.SupplierItem;
import com.aof.model.admin.query.SupplierItemQueryCondition;
import com.aof.model.admin.query.SupplierItemQueryOrder;
import com.aof.service.BaseManager;
import com.aof.service.admin.SupplierItemManager;

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
     * @see com.aof.service.admin.SupplierItemManager#getSupplierItemListConflictWithPromoteBySupplier(com.aof.model.admin.Supplier)
     */
    public List getSupplierItemListConflictWithPromoteBySupplierGroupByPurchaseSubCategory(Supplier supplier) {
        List itemList=dao.getSupplierItemListConflictWithPromoteBySupplier(supplier);
        return this.getSupplierItemListGroupByPurchaseSubCategory(itemList);
    }

    /* (non-Javadoc)
     * @see com.aof.service.admin.SupplierItemManager#changePurchaseSubCategoryBySupplierAndPurchaseSubCategory(java.lang.Integer, java.lang.Integer, com.aof.model.admin.PurchaseSubCategory)
     */
    public void changePurchaseSubCategoryBySupplierAndPurchaseSubCategory(Integer supplierId, Integer purchaseSubCategoryId, PurchaseSubCategory destPurchaseSubCategory) {
        dao.changePurchaseSubCategoryBySupplierAndPurchaseSubCategory(supplierId,purchaseSubCategoryId,destPurchaseSubCategory);
    }
    
    

}
