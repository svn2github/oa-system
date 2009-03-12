/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.dao.admin.hibernate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.hibernate.Hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sourceforge.dao.BaseDAOHibernate;
import net.sourceforge.dao.admin.SupplierItemDAO;
import net.sourceforge.dao.convert.LikeConvert;
import net.sourceforge.model.admin.PurchaseSubCategory;
import net.sourceforge.model.admin.Supplier;
import net.sourceforge.model.admin.SupplierItem;
import org.apache.commons.beanutils.PropertyUtils;

import net.sourceforge.model.admin.query.SupplierItemQueryCondition;
import net.sourceforge.model.admin.query.SupplierItemQueryOrder;

/**
 * SupplierItemDAOµÄHibernateÊµÏÖ 
 * @author ych
 * @version 1.0 (Nov 7, 2005)
 */
public class SupplierItemDAOHibernate extends BaseDAOHibernate implements SupplierItemDAO {
    private Log log = LogFactory.getLog(SupplierItemDAOHibernate.class);


    public SupplierItem getSupplierItem(Integer id){
        if (id == null) {
            if (log.isDebugEnabled()) log.debug("try to get SupplierItem with null id");
            return null;
        }
        return (SupplierItem) getHibernateTemplate().get(SupplierItem.class, id);
    }

    public SupplierItem updateSupplierItem(SupplierItem supplierItem) {
        Integer id = supplierItem.getId();
        if (id == null) {
            throw new RuntimeException("cannot save a supplierItem with null id");
        }
        SupplierItem oldSupplierItem = getSupplierItem(id);
        if (oldSupplierItem != null) {
        	try{
                PropertyUtils.copyProperties(oldSupplierItem,supplierItem);
        	}
        	catch(Exception e)
        	{
                throw new RuntimeException("copy error£º"+e);
        	}
        	getHibernateTemplate().update(oldSupplierItem);	
            getHibernateTemplate().flush();
            getHibernateTemplate().refresh(oldSupplierItem);
        	return oldSupplierItem;
        }
        else
        {
        	throw new RuntimeException("supplierItem not found");
        }
    }

	public SupplierItem insertSupplierItem(SupplierItem supplierItem) {
       	getHibernateTemplate().save(supplierItem);
        getHibernateTemplate().flush();
        getHibernateTemplate().refresh(supplierItem);
       	return supplierItem;
    }

    private final static String SQL_COUNT = "select count(*) from SupplierItem supplierItem";

    private final static String SQL = "from SupplierItem supplierItem";

    private final static Object[][] QUERY_CONDITIONS = {
		{ SupplierItemQueryCondition.ID_EQ, "supplierItem.id = ?", null },
		{ SupplierItemQueryCondition.CURRENCY_CODE_EQ, "supplierItem.currency.code = ?", null },
		{ SupplierItemQueryCondition.PURCHASESUBCATEGORY_ID_EQ, "supplierItem.purchaseSubCategory.id = ?", null },
        { SupplierItemQueryCondition.PURCHASECATEGORY_ID_EQ, "supplierItem.purchaseSubCategory.purchaseCategory.id = ?", null },
		{ SupplierItemQueryCondition.SUPPLIER_ID_EQ, "supplierItem.supplier.id = ?", null },
		{ SupplierItemQueryCondition.SEPC_LIKE, "supplierItem.sepc like ?", new LikeConvert() },
		{ SupplierItemQueryCondition.UNITPRICE_EQ, "supplierItem.unitPrice = ?", null },
		{ SupplierItemQueryCondition.ENABLED_EQ, "supplierItem.enabled = ?", null },
		{ SupplierItemQueryCondition.ERPNO_LIKE, "supplierItem.erpNo like ?", new LikeConvert() },
		
    };
    
    private final static Object[][] QUERY_ORDERS = {
        { SupplierItemQueryOrder.ID, "supplierItem.id" },
        { SupplierItemQueryOrder.SEPC, "supplierItem.sepc" },
        { SupplierItemQueryOrder.UNITPRICE, "supplierItem.unitPrice" },
        { SupplierItemQueryOrder.ENABLED, "supplierItem.enabled" },
        { SupplierItemQueryOrder.ERPNO, "supplierItem.erpNo" },
        { SupplierItemQueryOrder.CATEGORY, "supplierItem.purchaseSubCategory.purchaseCategory.description" },
        { SupplierItemQueryOrder.SUBCATEGORY, "supplierItem.purchaseSubCategory.description" },
    };
    
    public int getSupplierItemListCount(final Map conditions) {
        return getListCount(conditions, SQL_COUNT, QUERY_CONDITIONS);
    }

    public List getSupplierItemList(final Map conditions, final int pageNo, final int pageSize, final SupplierItemQueryOrder order, final boolean descend) {
        return getList(conditions, pageNo, pageSize, order, descend, SQL, QUERY_CONDITIONS, QUERY_ORDERS);
    }

	
	public List getSupplierItemListBySupplier(Supplier supplier) {
		return getHibernateTemplate().find("from SupplierItem si where si.supplier.id=? order by si.purchaseSubCategory.purchaseCategory.id,si.purchaseSubCategory.id,si.sepc",supplier.getId(),Hibernate.INTEGER);
	}


	public void removeSupplierItem(Integer id) {
		SupplierItem supplierItem=getSupplierItem(id);
		getHibernateTemplate().delete(supplierItem);
	}
	
    public List getSupplierItemListConflictWithPromoteBySupplier(Supplier supplier) {
        return getHibernateTemplate().find("from SupplierItem si where si.supplier.id=? and si.purchaseSubCategory.purchaseCategory.site.id is not null order by si.purchaseSubCategory.purchaseCategory.id,si.purchaseSubCategory.id,si.sepc",supplier.getId(),Hibernate.INTEGER);
    }

    public void changePurchaseSubCategoryBySupplierAndPurchaseSubCategory(Integer supplierId, Integer purchaseSubCategoryId, PurchaseSubCategory destPurchaseSubCategory) {
        Map conditions=new HashMap();
        conditions.put(SupplierItemQueryCondition.SUPPLIER_ID_EQ, supplierId);
        conditions.put(SupplierItemQueryCondition.PURCHASESUBCATEGORY_ID_EQ,purchaseSubCategoryId);
        List result=this.getSupplierItemList(conditions,0,-1,null,false);
        for (Iterator itor = result.iterator(); itor.hasNext();) {
            SupplierItem item = (SupplierItem) itor.next();
            item.setPurchaseSubCategory(destPurchaseSubCategory);
            getHibernateTemplate().update(item);
        }
    }

    
    
    
}
