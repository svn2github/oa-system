/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.dao.business.po.hibernate;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sourceforge.dao.BaseDAOHibernate;
import net.sourceforge.dao.business.po.PurchaseOrderItemReceiptDAO;
import net.sourceforge.model.business.po.PurchaseOrderItemReceipt;
import net.sourceforge.model.business.po.query.PurchaseOrderItemReceiptQueryCondition;
import net.sourceforge.model.business.po.query.PurchaseOrderItemReceiptQueryOrder;

/**
 * dao implement for domain model purchaseOrderItem
 * @author shilei
 * @version 1.0 (Dec 27, 2005)
 */
public class PurchaseOrderItemReceiptDAOHibernate extends BaseDAOHibernate implements PurchaseOrderItemReceiptDAO {
    private Log log = LogFactory.getLog(PurchaseOrderItemReceiptDAOHibernate.class);


    public PurchaseOrderItemReceipt getPurchaseOrderItemReceipt(Integer id){
        if (id == null) {
            if (log.isDebugEnabled()) log.debug("try to get PurchaseOrderItemReceipt with null id");
            return null;
        }
        return (PurchaseOrderItemReceipt) getHibernateTemplate().get(PurchaseOrderItemReceipt.class, id);
    }

    public PurchaseOrderItemReceipt updatePurchaseOrderItemReceipt(PurchaseOrderItemReceipt purchaseOrderItemReceipt) {
        Integer id = purchaseOrderItemReceipt.getId();
        if (id == null) {
            throw new RuntimeException("cannot save a purchaseOrderItemReceipt with null id");
        }
        PurchaseOrderItemReceipt oldPurchaseOrderItemReceipt = getPurchaseOrderItemReceipt(id);
        if (oldPurchaseOrderItemReceipt != null) {
        	try{
        		PropertyUtils.copyProperties(oldPurchaseOrderItemReceipt,purchaseOrderItemReceipt);
        	}
        	catch(Exception e)
        	{
        		throw new RuntimeException(e);
        	}
        	getHibernateTemplate().update(oldPurchaseOrderItemReceipt);	
        	return oldPurchaseOrderItemReceipt;
        }
        else
        {
        	throw new RuntimeException("purchaseOrderItemReceipt not found");
        }
    }

	public PurchaseOrderItemReceipt insertPurchaseOrderItemReceipt(PurchaseOrderItemReceipt purchaseOrderItemReceipt) {
       	getHibernateTemplate().save(purchaseOrderItemReceipt);
       	return purchaseOrderItemReceipt;
    }

    private final static String SQL_COUNT = "select count(*) from PurchaseOrderItemReceipt purchaseOrderItemReceipt";

    private final static String SQL = "from PurchaseOrderItemReceipt purchaseOrderItemReceipt";

    private final static Object[][] QUERY_CONDITIONS = {
    	/*id*/    
		{ PurchaseOrderItemReceiptQueryCondition.ID_EQ, "purchaseOrderItemReceipt.id = ?", null },

		/*keyPropertyList*/

		/*kmtoIdList*/

		/*mtoIdList*/
		{ PurchaseOrderItemReceiptQueryCondition.PURCHASEORDERITEM_ID_EQ, "purchaseOrderItemReceipt.purchaseOrderItem.id = ?", null },
		{ PurchaseOrderItemReceiptQueryCondition.RECEIVER1_ID_EQ, "purchaseOrderItemReceipt.receiver1.id = ?", null },
		{ PurchaseOrderItemReceiptQueryCondition.RECEIVER2_ID_EQ, "purchaseOrderItemReceipt.receiver2.id = ?", null },

		/*property*/
		{ PurchaseOrderItemReceiptQueryCondition.RECEIVEDATE1_EQ, "purchaseOrderItemReceipt.receiveDate1 = ?", null },
		{ PurchaseOrderItemReceiptQueryCondition.RECEIVEQTY1_EQ, "purchaseOrderItemReceipt.receiveQty1 = ?", null },
		{ PurchaseOrderItemReceiptQueryCondition.RECEIVEDATE2_EQ, "purchaseOrderItemReceipt.receiveDate2 = ?", null },
		{ PurchaseOrderItemReceiptQueryCondition.RECEIVEQTY2_EQ, "purchaseOrderItemReceipt.receiveQty2 = ?", null },
		{ PurchaseOrderItemReceiptQueryCondition.EXPORTSTATUS_EQ, "purchaseOrderItemReceipt.exportStatus = ?", null },
    };
    
    private final static Object[][] QUERY_ORDERS = {
		/*id*/
        { PurchaseOrderItemReceiptQueryOrder.ID, "purchaseOrderItemReceipt.id" },

		/*property*/
        { PurchaseOrderItemReceiptQueryOrder.RECEIVEDATE1, "purchaseOrderItemReceipt.receiveDate1" },
        { PurchaseOrderItemReceiptQueryOrder.RECEIVEQTY1, "purchaseOrderItemReceipt.receiveQty1" },
        { PurchaseOrderItemReceiptQueryOrder.RECEIVEDATE2, "purchaseOrderItemReceipt.receiveDate2" },
        { PurchaseOrderItemReceiptQueryOrder.RECEIVEQTY2, "purchaseOrderItemReceipt.receiveQty2" },
        { PurchaseOrderItemReceiptQueryOrder.EXPORTSTATUS, "purchaseOrderItemReceipt.exportStatus" },
    };
    
    public int getPurchaseOrderItemReceiptListCount(final Map conditions) {
        return getListCount(conditions, SQL_COUNT, QUERY_CONDITIONS);
    }

    public List getPurchaseOrderItemReceiptList(final Map conditions, final int pageNo, final int pageSize, final PurchaseOrderItemReceiptQueryOrder order, final boolean descend) {
        return getList(conditions, pageNo, pageSize, order, descend, SQL, QUERY_CONDITIONS, QUERY_ORDERS);
    }

    public void deletePurchaseOrderItemReceipt(PurchaseOrderItemReceipt poir) {
        this.getHibernateTemplate().delete(poir);
        
    }


}
