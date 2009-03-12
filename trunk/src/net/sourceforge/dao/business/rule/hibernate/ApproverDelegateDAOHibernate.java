/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.dao.business.rule.hibernate;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.type.Type;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sourceforge.dao.BaseDAOHibernate;
import net.sourceforge.dao.business.rule.ApproverDelegateDAO;
import net.sourceforge.model.business.rule.ApproverDelegate;
import net.sourceforge.model.business.rule.query.ApproverDelegateQueryCondition;
import net.sourceforge.model.business.rule.query.ApproverDelegateQueryOrder;
import net.sourceforge.model.metadata.ApproverDelegateType;

/**
 * dao hibernate implement for Approver Delegate
 * 
 * @author sl
 * @version 1.0 (Nov 13, 2005)
 */
public class ApproverDelegateDAOHibernate extends BaseDAOHibernate implements ApproverDelegateDAO {
    private Log log = LogFactory.getLog(ApproverDelegateDAOHibernate.class);

    /* (non-Javadoc)
     * @see net.sourceforge.dao.business.rule.ApproverDelegateDAO#getApproverDelegate(java.lang.Integer)
     */
    public ApproverDelegate getApproverDelegate(Integer id) {
        if (id == null) {
            if (log.isDebugEnabled())
                log.debug("try to get ApproverDelegate with null id");
            return null;
        }
        return (ApproverDelegate) getHibernateTemplate().get(ApproverDelegate.class, id);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.dao.business.rule.ApproverDelegateDAO#updateApproverDelegate(net.sourceforge.model.business.rule.ApproverDelegate)
     */
    public ApproverDelegate updateApproverDelegate(ApproverDelegate approverDelegate) {
        Integer id = approverDelegate.getId();
        if (id == null) {
            throw new RuntimeException("cannot save a approverDelegate with null id");
        }
        ApproverDelegate oldApproverDelegate = getApproverDelegate(id);
        if (oldApproverDelegate != null) {
            try {
                PropertyUtils.copyProperties(oldApproverDelegate, approverDelegate);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            getHibernateTemplate().update(oldApproverDelegate);
            return oldApproverDelegate;
        } else {
            throw new RuntimeException("approverDelegate not found");
        }
    }

    /* (non-Javadoc)
     * @see net.sourceforge.dao.business.rule.ApproverDelegateDAO#insertApproverDelegate(net.sourceforge.model.business.rule.ApproverDelegate)
     */
    public ApproverDelegate insertApproverDelegate(ApproverDelegate approverDelegate) {
        getHibernateTemplate().save(approverDelegate);
        return approverDelegate;
    }

    private final static String SQL_COUNT = "select count(*) from ApproverDelegate approverDelegate";

    private final static String SQL = "from ApproverDelegate approverDelegate";

    private final static Object[][] QUERY_CONDITIONS = {

    { ApproverDelegateQueryCondition.ID_EQ, "approverDelegate.id = ?", null },
    { ApproverDelegateQueryCondition.ORIGINALAPPROVER_ID_EQ, "approverDelegate.originalApprover.id = ?", null },
    { ApproverDelegateQueryCondition.DELEGATEAPPROVER_ID_EQ, "approverDelegate.delegateApprover.id = ?", null },
    { ApproverDelegateQueryCondition.TYPE_EQ, "approverDelegate.type = ?", null },
    { ApproverDelegateQueryCondition.FROMDATE_EQ, "approverDelegate.fromDate = ?", null },
    { ApproverDelegateQueryCondition.TODATE_EQ, "approverDelegate.toDate = ?", null },
    { ApproverDelegateQueryCondition.FROMDATE_GE, "approverDelegate.fromDate >= ?", null },
    { ApproverDelegateQueryCondition.FROMDATE_LT, "approverDelegate.fromDate < ?", null },
    { ApproverDelegateQueryCondition.TODATE_GE, "approverDelegate.toDate >= ?", null },
    { ApproverDelegateQueryCondition.TODATE_LT, "approverDelegate.toDate < ?", null },
    };
    
    
    

    private final static Object[][] QUERY_ORDERS = {
        { ApproverDelegateQueryOrder.ID, "approverDelegate.id" },
        { ApproverDelegateQueryOrder.TYPE, "approverDelegate.type" },
        { ApproverDelegateQueryOrder.FROMDATE, "approverDelegate.fromDate" },
        { ApproverDelegateQueryOrder.TODATE, "approverDelegate.toDate" },
        { ApproverDelegateQueryOrder.DELEGATEAPPROVER_NAME, "approverDelegate.delegateApprover.name" },
    };

    /* (non-Javadoc)
     * @see net.sourceforge.dao.business.rule.ApproverDelegateDAO#getApproverDelegateListCount(java.util.Map)
     */
    public int getApproverDelegateListCount(final Map conditions) {
        return getListCount(conditions, SQL_COUNT, QUERY_CONDITIONS);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.dao.business.rule.ApproverDelegateDAO#getApproverDelegateList(java.util.Map, int, int, net.sourceforge.model.business.rule.query.ApproverDelegateQueryOrder, boolean)
     */
    public List getApproverDelegateList(final Map conditions, final int pageNo, final int pageSize, final ApproverDelegateQueryOrder order,
            final boolean descend) {
        return getList(conditions, pageNo, pageSize, order, descend, SQL, QUERY_CONDITIONS, QUERY_ORDERS);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.dao.business.rule.ApproverDelegateDAO#isDelegateApprover(net.sourceforge.model.metadata.ApproverDelegateType, java.lang.Integer, java.lang.Integer)
     */
    public boolean isDelegateApprover(ApproverDelegateType approverDelegateType, Integer originalApproverId, Integer delegateApproverId) {
        long today = this.getTodayTimeMillis();
        List result = getHibernateTemplate().find(
                "from ApproverDelegate ad where ad.type=? and ad.originalApprover.id=? and ad.delegateApprover.id=? "
                        + " and ((ad.fromDate is null or ad.fromDate<?) and (ad.toDate is null or ad.toDate>=?))",
                new Object[] { approverDelegateType.getEnumCode(), originalApproverId,
                        delegateApproverId, new Date(today + 86400000), new Date(today) },
                new Type[] { Hibernate.INTEGER, Hibernate.INTEGER, Hibernate.INTEGER, Hibernate.DATE, Hibernate.DATE });
        return (result.size()>0);
    }
}
