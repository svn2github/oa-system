/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.service.admin.impl;

import java.util.List;
import java.util.Map;

import net.sourceforge.dao.admin.CustomerDAO;
import net.sourceforge.model.admin.Customer;
import net.sourceforge.model.admin.query.CustomerQueryOrder;
import net.sourceforge.service.BaseManager;
import net.sourceforge.service.admin.CustomerManager;

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
     * @see net.sourceforge.service.admin.CustomerManager#getCustomer(java.lang.Integer)
     */
    public Customer getCustomer(String code)  {
        return dao.getCustomer(code);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sourceforge.service.admin.CustomerManager#getCustomerListCount(java.util.Map)
     */
    public int getCustomerListCount(Map conditions)  {
        return dao.getCustomerListCount(conditions);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sourceforge.service.admin.CustomerManager#getCustomerList(java.util.Map, int,
     *      int, net.sourceforge.model.admin.query.CustomerQueryOrder, boolean)
     */
    public List getCustomerList(Map conditions, int pageNo, int pageSize, CustomerQueryOrder order, boolean descend)  {
        return dao.getCustomerList(conditions, pageNo, pageSize, order, descend);
    }

}
