/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.service.admin.impl;

import java.util.List;
import java.util.Map;

import com.aof.dao.admin.SupplierHistoryDAO;
import com.aof.model.admin.SupplierHistory;
import com.aof.model.admin.query.SupplierHistoryQueryOrder;
import com.aof.service.BaseManager;
import com.aof.service.admin.SupplierHistoryManager;

/**
 * SupplierHistoryManagerµÄÊµÏÖ
 * @author ych
 * @version 1.0 (Nov 17, 2005)
 */
public class SupplierHistoryManagerImpl extends BaseManager implements SupplierHistoryManager {

    private SupplierHistoryDAO dao;

    public void setSupplierHistoryDAO(SupplierHistoryDAO dao) {
        this.dao = dao;
    }
    
     public SupplierHistory getSupplierHistory(Integer id)  {
        return dao.getSupplierHistory(id);
    }

    public SupplierHistory updateSupplierHistory(SupplierHistory function)  {
        return dao.updateSupplierHistory(function);
    }
    
    public SupplierHistory insertSupplierHistory(SupplierHistory function)  {
        return dao.insertSupplierHistory(function);
    }
    

    public int getSupplierHistoryListCount(Map conditions)  {
        return dao.getSupplierHistoryListCount(conditions);
    }

    public List getSupplierHistoryList(Map conditions, int pageNo, int pageSize, SupplierHistoryQueryOrder order, boolean descend)  {
        return dao.getSupplierHistoryList(conditions, pageNo, pageSize, order, descend);
    }

}
