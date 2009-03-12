/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.dao.business.hibernate;

import net.sourceforge.dao.BaseDAOHibernate;
import net.sourceforge.dao.business.BaseApproveRequestDAO;
import net.sourceforge.model.business.BaseApproveRequest;

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
