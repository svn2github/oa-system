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
import com.aof.dao.admin.SupplierHistoryDAO;
import com.aof.model.admin.Supplier;
import com.aof.model.admin.SupplierHistory;
import com.aof.model.admin.query.SupplierHistoryQueryCondition;
import com.aof.model.admin.query.SupplierHistoryQueryOrder;

/**
 * SupplierHistoryDAOµÄHibernateÊµÏÖ 
 * @author ych
 * @version 1.0 (Nov 13, 2005)
 */
public class SupplierHistoryDAOHibernate extends BaseDAOHibernate implements SupplierHistoryDAO {
    private Log log = LogFactory.getLog(SupplierHistoryDAOHibernate.class);


    public SupplierHistory getSupplierHistory(Integer id){
        if (id == null) {
            if (log.isDebugEnabled()) log.debug("try to get SupplierHistory with null id");
            return null;
        }
        return (SupplierHistory) getHibernateTemplate().get(SupplierHistory.class, id);
    }

    public SupplierHistory updateSupplierHistory(SupplierHistory supplierHistory) {
        Integer id = supplierHistory.getId();
        if (id == null) {
            throw new RuntimeException("cannot save a supplierHistory with null id");
        }
        SupplierHistory oldSupplierHistory = getSupplierHistory(id);
        if (oldSupplierHistory != null) {
        	try{
                PropertyUtils.copyProperties(oldSupplierHistory,supplierHistory);
        	}
        	catch(Exception e)
        	{
                throw new RuntimeException("copy error£º"+e);
        	}
        	getHibernateTemplate().update(oldSupplierHistory);	
        	return oldSupplierHistory;
        }
        else
        {
        	throw new RuntimeException("supplierHistory not found");
        }
    }

	public SupplierHistory insertSupplierHistory(SupplierHistory supplierHistory) {
       	getHibernateTemplate().save(supplierHistory);
       	return supplierHistory;
    }
	
	public void copySupplier(Supplier supplier)  {
		Integer id = supplier.getId();
        if (id == null) {
            throw new RuntimeException("cannot save a supplierHistory with null id");
        }
        SupplierHistory oldSupplierHistory = getSupplierHistory(id);
        if (oldSupplierHistory != null) {
        	oldSupplierHistory.copySupplier(supplier);
        	getHibernateTemplate().update(oldSupplierHistory);	
        } else {
        	SupplierHistory newSupplierHistory=new SupplierHistory();
        	newSupplierHistory.copySupplier(supplier);
        	newSupplierHistory.setSupplier(supplier);
        	getHibernateTemplate().save(newSupplierHistory);
        }
	}

    private final static String SQL_COUNT = "select count(*) from SupplierHistory supplierHistory";

    private final static String SQL = "from SupplierHistory supplierHistory";

    private final static Object[][] QUERY_CONDITIONS = {
    	/*id*/    
		{ SupplierHistoryQueryCondition.ID_EQ, "supplierHistory.id = ?", null },
    };
    
    private final static Object[][] QUERY_ORDERS = {
		/*id*/
        { SupplierHistoryQueryOrder.ID, "supplierHistory.id" },
    };
    
    public int getSupplierHistoryListCount(final Map conditions) {
        return getListCount(conditions, SQL_COUNT, QUERY_CONDITIONS);
    }

    public List getSupplierHistoryList(final Map conditions, final int pageNo, final int pageSize, final SupplierHistoryQueryOrder order, final boolean descend) {
        return getList(conditions, pageNo, pageSize, order, descend, SQL, QUERY_CONDITIONS, QUERY_ORDERS);
    }

}
