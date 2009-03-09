/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.dao.business.hibernate;

import com.aof.dao.BaseDAOHibernate;
import com.aof.dao.business.BaseApproveRequestDAO;
import com.aof.model.business.BaseApproveRequest;

/**
 * BaseApproveRequestµÄHibernateÊµÏÖ
 * 
 * @author nicebean
 * @version 1.0 (2006-5-15)
 */
public abstract class BaseApproveRequestDAOHibernate extends BaseDAOHibernate implements BaseApproveRequestDAO {

    public void updateBaseApproveRequest(BaseApproveRequest bar) {
        getHibernateTemplate().update(bar);
    }

    public void insertBaseApproveRequest(BaseApproveRequest bar) {
        getHibernateTemplate().save(bar);
    }

}
