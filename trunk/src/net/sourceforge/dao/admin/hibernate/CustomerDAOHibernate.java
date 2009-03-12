/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.dao.admin.hibernate;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sourceforge.dao.BaseDAOHibernate;
import net.sourceforge.dao.admin.CustomerDAO;
import net.sourceforge.dao.convert.LikeConvert;
import net.sourceforge.model.admin.Customer;
import net.sourceforge.model.admin.query.CustomerQueryCondition;
import net.sourceforge.model.admin.query.CustomerQueryOrder;

/**
 * CustomerDAOµÄHibernateÊµÏÖ
 * 
 * @author nicebean
 * @version 1.0 (2005-11-23)
 */
public class CustomerDAOHibernate extends BaseDAOHibernate implements CustomerDAO {
    private Log log = LogFactory.getLog(CustomerDAOHibernate.class);

    public Customer getCustomer(String code) {
        if (code == null) {
            if (log.isDebugEnabled())
                log.debug("try to get Customer with null code");
            return null;
        }
        return (Customer) getHibernateTemplate().get(Customer.class, code);
    }

    private final static String SQL_COUNT = "select count(*) from Customer c";

    private final static String SQL = "from Customer c";

    private final static Object[][] QUERY_CONDITIONS = {
        { CustomerQueryCondition.CODE_EQ, "c.code = ?", null },
        { CustomerQueryCondition.CODE_LIKE, "c.code like ?", new LikeConvert() },
        { CustomerQueryCondition.DESCRIPTION_LIKE, "c.description like ?", new LikeConvert() },
        { CustomerQueryCondition.SITE_ID_EQ, "c.site.id = ?", null },
        { CustomerQueryCondition.TYPE_EQ, "c.type = ?", null }, 
        { CustomerQueryCondition.ENABLED_EQ, "c.enabled = ?", null }, 
    };

    private final static Object[][] QUERY_ORDERS = { 
            { CustomerQueryOrder.CODE, "c.code" }, 
            { CustomerQueryOrder.DESCRIPTION, "c.description" }, };

    public int getCustomerListCount(final Map conditions) {
        return getListCount(conditions, SQL_COUNT, QUERY_CONDITIONS);
    }

    public List getCustomerList(final Map conditions, final int pageNo, final int pageSize, final CustomerQueryOrder order, final boolean descend) {
        return getList(conditions, pageNo, pageSize, order, descend, SQL, QUERY_CONDITIONS, QUERY_ORDERS);
    }

}
