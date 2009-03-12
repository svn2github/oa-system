/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.dao.business.pr.hibernate;

import java.util.List;
import java.util.Map;

import net.sf.hibernate.Hibernate;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sourceforge.dao.business.hibernate.BaseApproveRequestDAOHibernate;
import net.sourceforge.dao.business.pr.CapexRequestApproveRequestDAO;
import net.sourceforge.dao.convert.LikeConvert;
import net.sourceforge.model.admin.User;
import net.sourceforge.model.business.pr.CapexRequest;
import net.sourceforge.model.business.pr.CapexRequestApproveRequest;
import net.sourceforge.model.business.pr.query.CapexRequestApproveRequestQueryCondition;
import net.sourceforge.model.business.pr.query.CapexRequestApproveRequestQueryOrder;
import net.sourceforge.model.business.query.BaseApproveQueryCondition;
import net.sourceforge.model.metadata.ApproverDelegateType;

/**
 * CapexRequestApproveRequestµÄHibernateÊµÏÖ
 * 
 * @author nicebean
 * @version 1.0 (2005-12-8)
 */
public class CapexRequestApproveRequestDAOHibernate extends BaseApproveRequestDAOHibernate implements CapexRequestApproveRequestDAO {
    private Log log = LogFactory.getLog(CapexRequestApproveRequestDAOHibernate.class);

    public List getBaseApproveRequestListByApproveRequestId(String approveRequestId) {
        return this.getCapexRequestApproveRequestListByApproveRequestId(approveRequestId);
    }

    public List getCapexRequestApproveRequestListByApproveRequestId(String approveRequestId) {
        return getHibernateTemplate().find("from CapexRequestApproveRequest crar where crar.approveRequestId=? order by crar.seq", approveRequestId,
                Hibernate.STRING);
    }

    public CapexRequestApproveRequest getCapexRequestApproveRequest(String requestId, User approver) {
        if (requestId == null) {
            if (log.isDebugEnabled())
                log.debug("try to get CapexRequestApproveRequest with null requestId");
            return null;
        }
        if (approver == null) {
            if (log.isDebugEnabled())
                log.debug("try to get CapexRequestApproveRequest with null approver");
            return null;
        }
        return (CapexRequestApproveRequest) getHibernateTemplate().get(CapexRequestApproveRequest.class, new CapexRequestApproveRequest(requestId, approver));
    }

    public CapexRequestApproveRequest updateCapexRequestApproveRequest(CapexRequestApproveRequest capexRequestApproveRequest) {
        CapexRequestApproveRequest oldCapexRequestApproveRequest = getCapexRequestApproveRequest(capexRequestApproveRequest.getApproveRequestId(),
                capexRequestApproveRequest.getApprover());
        if (oldCapexRequestApproveRequest != null) {
            try {
                PropertyUtils.copyProperties(oldCapexRequestApproveRequest, capexRequestApproveRequest);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            getHibernateTemplate().update(oldCapexRequestApproveRequest);
            return oldCapexRequestApproveRequest;
        } else {
            throw new RuntimeException("capexRequestApproveRequest not found");
        }
    }

    public CapexRequestApproveRequest insertCapexRequestApproveRequest(CapexRequestApproveRequest capexRequestApproveRequest) {
        getHibernateTemplate().save(capexRequestApproveRequest);
        return capexRequestApproveRequest;
    }

    private final static String SQL_COUNT = "select count(*) from CapexRequestApproveRequest crar ,CapexRequest capexRequest where crar.approveRequestId=capexRequest.approveRequestId";

    private final static String SQL = "from CapexRequestApproveRequest crar , CapexRequest capexRequest where crar.approveRequestId=capexRequest.approveRequestId";

    private final static Object[][] QUERY_CONDITIONS = {
            { BaseApproveQueryCondition.APPROVER_ID_EQ, "crar.approver.id=? or" + 
                " crar.approver.id in (select ad.originalApprover.id from ApproverDelegate ad where ad.type=" + 
                ApproverDelegateType.NONBUDGET_CAPEX_APPROVER.getEnumCode().toString() +
                " and ad.delegateApprover.id=? " + 
                " and ((ad.fromDate is null or ad.fromDate<?) and (ad.toDate is null or ad.toDate>=?)))", null },
            { BaseApproveQueryCondition.SITE_ID_EQ, "capexRequest.capex.site.id = ?", null },
            { BaseApproveQueryCondition.STATUS_EQ, "crar.status=?", null },
            { BaseApproveQueryCondition.YOUR_TURN_DATE_LE, "crar.yourTurnDate<=?", null },
            { BaseApproveQueryCondition.LAST_EMAIL_DATE_LE, "(crar.lastEmailDate is null or crar.lastEmailDate<=?)", null},
            { BaseApproveQueryCondition.SENT_EMAIL_TIMES_LT, "crar.sentEmailTimes<?", null},
            { CapexRequestApproveRequestQueryCondition.CODE_LIKE, "capexRequest.capex.id like ?", new LikeConvert() },
            { CapexRequestApproveRequestQueryCondition.TITLE_LIKE, "capexRequest.title like  ?", new LikeConvert() },
            { CapexRequestApproveRequestQueryCondition.STATUS_NEQ, "crar.status<>?", null },
            { CapexRequestApproveRequestQueryCondition.SUBMIT_DATE_BT, "capexRequest.requestDate between ? and ?", null }, 
    };

    private final static Object[][] QUERY_ORDERS = {
            { CapexRequestApproveRequestQueryOrder.CAPEXREQUEST_CAPEX_ID, "capexRequest.capex.id" },
            { CapexRequestApproveRequestQueryOrder.SEQ, "crar.seq" }, 
            { CapexRequestApproveRequestQueryOrder.STATUS, "crar.status" },
            { CapexRequestApproveRequestQueryOrder.APPROVEDATE, "crar.approveDate" }, 
            { CapexRequestApproveRequestQueryOrder.COMMENT, "crar.comment" },
            { CapexRequestApproveRequestQueryOrder.CANMODIFY, "crar.canModify" }, 
    };

    public int getCapexRequestApproveRequestListCount(final Map conditions) {
        return getListCount(conditions, SQL_COUNT, QUERY_CONDITIONS);
    }

    public List getCapexRequestApproveRequestList(final Map conditions, final int pageNo, final int pageSize, final CapexRequestApproveRequestQueryOrder order,
            final boolean descend) {
        Object[] obj = (Object[])conditions.get(BaseApproveQueryCondition.APPROVER_ID_EQ);
        System.out.println(obj[0]);
        System.out.println(obj[1]);
        System.out.println(obj[2]);
        System.out.println(obj[3]);
        return getList(conditions, pageNo, pageSize, order, descend, SQL, QUERY_CONDITIONS, QUERY_ORDERS);
    }

    public List getBaseApproveRequestList(final Map conditions) {
        return getList(conditions, 0, -1, null, false, SQL, QUERY_CONDITIONS, null);
    }

    public void deleteCapexRequestApproveRequestByCapexRequest(CapexRequest capexRequest) {
        getHibernateTemplate().delete("from CapexRequestApproveRequest crar where crar.approveRequestId=?", capexRequest.getApproveRequestId(),
                Hibernate.STRING);
    }        
}
