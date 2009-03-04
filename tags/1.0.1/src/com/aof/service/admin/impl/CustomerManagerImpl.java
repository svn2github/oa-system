/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.service.admin.impl;

import java.util.List;
import java.util.Map;

import com.aof.dao.admin.CustomerDAO;
import com.aof.model.admin.Customer;
import com.aof.model.admin.query.CustomerQueryOrder;
import com.aof.service.BaseManager;
import com.aof.service.admin.CustomerManager;

/**
 * CustomerManager的实现
 * 
 * @author nicebean
 * @version 1.0 (2005-11-23)
 */
public class CustomerManagerImpl extends BaseManager implements CustomerManager {

    private CustomerDAO dao;

    /**
     * 设置CustomerDAO给dao属性
     * 
     * @param dao
     *            CustomerDAO对象
     */
    public void setCustomerDAO(CustomerDAO dao) {
        this.dao = dao;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin.CustomerManager#getCustomer(java.lang.Integer)
     */
    public Customer getCustomer(String code)  {
        return dao.getCustomer(code);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin.CustomerManager#getCustomerListCount(java.util.Map)
     */
    public int getCustomerListCount(Map conditions)  {
        return dao.getCustomerListCount(conditions);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin.CustomerManager#getCustomerList(java.util.Map, int,
     *      int, com.aof.model.admin.query.CustomerQueryOrder, boolean)
     */
    public List getCustomerList(Map conditions, int pageNo, int pageSize, CustomerQueryOrder order, boolean descend)  {
        return dao.getCustomerList(conditions, pageNo, pageSize, order, descend);
    }

}
