/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.dao.business.ta.hibernate;

import java.util.List;
import java.util.Map;

import net.sf.hibernate.Hibernate;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sourceforge.dao.business.hibernate.BaseApproveRequestDAOHibernate;
import net.sourceforge.dao.business.ta.TravelApplicationApproveRequestDAO;
import net.sourceforge.dao.convert.LikeConvert;
import net.sourceforge.model.admin.User;
import net.sourceforge.model.business.query.BaseApproveQueryCondition;
import net.sourceforge.model.business.ta.TravelApplicationApproveRequest;
import net.sourceforge.model.business.ta.query.TravelApplicationApproveRequestQueryCondition;
import net.sourceforge.model.business.ta.query.TravelApplicationApproveRequestQueryOrder;
import net.sourceforge.model.metadata.ApproverDelegateType;

/**
 * TravelApplicationApproveRequestµÄHibernateÊµÏÖ 
 * @author ych
 * @version 1.0 (Nov 18, 2005)
 */
public class TravelApplicationApproveRequestDAOHibernate extends BaseApproveRequestDAOHibernate implements TravelApplicationApproveRequestDAO {
    private Log log = LogFactory.getLog(TravelApplicationApproveRequestDAOHibernate.class);

    public List getBaseApproveRequestListByApproveRequestId(String approveRequestId) {
        return this.getTravelApplicationApproveRequestListByApproveRequestId(approveRequestId);
    }

    public List getTravelApplicationApproveRequestListByApproveRequestId(String approveRequestId) {
        return getHibernateTemplate().find("from TravelApplicationApproveRequest taa where taa.approveRequestId=? order by taa.seq",
                approveRequestId , Hibernate.STRING);
    }
    
    public TravelApplicationApproveRequest getTravelApplicationApproveRequest(String requestId, User approver) {
        if (requestId == null) {
            if (log.isDebugEnabled())
                log.debug("try to get TravelApplicationApproveRequest with null requestId");
            return null;
        }
        if (approver == null) {
            if (log.isDebugEnabled())
                log.debug("try to get TravelApplicationApproveRequest with null approver");
            return null;
        }
        return (TravelApplicationApproveRequest) getHibernateTemplate().get(TravelApplicationApproveRequest.class,
                new TravelApplicationApproveRequest(requestId, approver));
    }

    public TravelApplicationApproveRequest updateTravelApplicationApproveRequest(TravelApplicationApproveRequest travelApplicationApproveRequest) {
        TravelApplicationApproveRequest oldTravelApplicationApproveRequest = getTravelApplicationApproveRequest(travelApplicationApproveRequest.getApproveRequestId(),
                travelApplicationApproveRequest.getApprover());
        if (oldTravelApplicationApproveRequest != null) {
            try {
                PropertyUtils.copyProperties(oldTravelApplicationApproveRequest, travelApplicationApproveRequest);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            getHibernateTemplate().update(oldTravelApplicationApproveRequest);
            return oldTravelApplicationApproveRequest;
        } else {
            throw new RuntimeException("travelApplicationApproveRequest not found");
        }
    }

    public TravelApplicationApproveRequest insertTravelApplicationApproveRequest(TravelApplicationApproveRequest travelApplicationApproveRequest) {
        getHibernateTemplate().save(travelApplicationApproveRequest);
        return travelApplicationApproveRequest;
    }

    private final static String SQL_COUNT = "select count(*) from TravelApplicationApproveRequest taa ,TravelApplication ta where taa.approveRequestId=ta.approveRequestId";

    private final static String SQL = "from TravelApplicationApproveRequest taa , TravelApplication ta where taa.approveRequestId=ta.approveRequestId";

    private final static Object[][] QUERY_CONDITIONS = {
        { BaseApproveQueryCondition.APPROVER_ID_EQ , "taa.approver.id=? or " + 
            " taa.approver.id in (select ad.originalApprover.id from ApproverDelegate ad where ad.type="+
            ApproverDelegateType.TRAVEL_APPLICATION_APPROVER.getEnumCode().toString()+
            " and ad.delegateApprover.id=? " +
            " and ((ad.fromDate is null or ad.fromDate<?) and (ad.toDate is null or ad.toDate>=?)))", null },
        { BaseApproveQueryCondition.SITE_ID_EQ, "ta.department.site.id = ?", null },
        { BaseApproveQueryCondition.STATUS_EQ, "taa.status=?", null},
        { BaseApproveQueryCondition.YOUR_TURN_DATE_LE, "taa.yourTurnDate<=?", null},
        { BaseApproveQueryCondition.LAST_EMAIL_DATE_LE, "(taa.lastEmailDate is null or taa.lastEmailDate<=?)", null},
        { BaseApproveQueryCondition.SENT_EMAIL_TIMES_LT, "taa.sentEmailTimes<?", null},
        { TravelApplicationApproveRequestQueryCondition.CODE_LIKE, "ta.id like ?", new LikeConvert() },
        { TravelApplicationApproveRequestQueryCondition.TITLE_LIKE, "ta.title like  ?", new LikeConvert() },
        { TravelApplicationApproveRequestQueryCondition.STATUS_NEQ, "taa.status<>?", null},
        { TravelApplicationApproveRequestQueryCondition.SUBMIT_DATE_BT, "ta.requestDate between ? and ?", null},
        { TravelApplicationApproveRequestQueryCondition.URGENT_EQ, "ta.urgent = ?", null },
        { TravelApplicationApproveRequestQueryCondition.REQUESTOR_LK, "ta.requestor.name like ?",  new LikeConvert()},
        };

    private final static Object[][] QUERY_ORDERS = {
        { TravelApplicationApproveRequestQueryOrder.TA_CODE, "ta.id" }, 
        { TravelApplicationApproveRequestQueryOrder.SEQ, "taa.seq" },
        { TravelApplicationApproveRequestQueryOrder.STATUS, "taa.status" }, 
        { TravelApplicationApproveRequestQueryOrder.APPROVEDATE, "taa.approveDate" },
        { TravelApplicationApproveRequestQueryOrder.COMMENT, "taa.comment" }, 
        { TravelApplicationApproveRequestQueryOrder.CANMODIFY, "taa.canModify" }, 
        };

    public int getTravelApplicationApproveRequestListCount(final Map conditions) {
        return getListCount(conditions, SQL_COUNT, QUERY_CONDITIONS);
    }

    public List getTravelApplicationApproveRequestList(final Map conditions, final int pageNo, final int pageSize,
            final TravelApplicationApproveRequestQueryOrder order, final boolean descend) {
        return getList(conditions, pageNo, pageSize, order, descend, SQL, QUERY_CONDITIONS, QUERY_ORDERS);
    }

    public List getBaseApproveRequestList(final Map conditions) {
        return getList(conditions, 0, -1, null, false, SQL, QUERY_CONDITIONS, null);
    }

}
