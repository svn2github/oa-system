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
import net.sourceforge.dao.business.pr.PurchaseRequestApproveRequestDAO;
import net.sourceforge.dao.convert.LikeConvert;
import net.sourceforge.model.admin.User;
import net.sourceforge.model.business.pr.PurchaseRequest;
import net.sourceforge.model.business.pr.PurchaseRequestApproveRequest;
import net.sourceforge.model.business.pr.query.PurchaseRequestApproveRequestQueryCondition;
import net.sourceforge.model.business.pr.query.PurchaseRequestApproveRequestQueryOrder;
import net.sourceforge.model.business.query.BaseApproveQueryCondition;
import net.sourceforge.model.metadata.ApproverDelegateType;

/**
 * PurchaseRequestApproveRequestµÄHibernateÊµÏÖ
 * 
 * @author nicebean
 * @version 1.0 (2005-12-15)
 */
public class PurchaseRequestApproveRequestDAOHibernate extends BaseApproveRequestDAOHibernate implements PurchaseRequestApproveRequestDAO {
    private Log log = LogFactory.getLog(PurchaseRequestApproveRequestDAOHibernate.class);

    public List getBaseApproveRequestListByApproveRequestId(String approveRequestId) {
        return this.getPurchaseRequestApproveRequestListByApproveRequestId(approveRequestId);
    }

    public List getPurchaseRequestApproveRequestListByApproveRequestId(String approveRequestId) {
        return getHibernateTemplate().find("from PurchaseRequestApproveRequest prar where prar.approveRequestId=? order by prar.seq", approveRequestId,
                Hibernate.STRING);
    }

    public PurchaseRequestApproveRequest getPurchaseRequestApproveRequest(String requestId, User approver) {
        if (requestId == null) {
            if (log.isDebugEnabled())
                log.debug("try to get PurchaseRequestApproveRequest with null requestId");
            return null;
        }
        if (approver == null) {
            if (log.isDebugEnabled())
                log.debug("try to get PurchaseRequestApproveRequest with null approver");
            return null;
        }
        return (PurchaseRequestApproveRequest) getHibernateTemplate().get(PurchaseRequestApproveRequest.class, new PurchaseRequestApproveRequest(requestId, approver));
    }

    public PurchaseRequestApproveRequest updatePurchaseRequestApproveRequest(PurchaseRequestApproveRequest purchaseRequestApproveRequest) {
        PurchaseRequestApproveRequest oldPurchaseRequestApproveRequest = getPurchaseRequestApproveRequest(purchaseRequestApproveRequest.getApproveRequestId(),
                purchaseRequestApproveRequest.getApprover());
        if (oldPurchaseRequestApproveRequest != null) {
            try {
                PropertyUtils.copyProperties(oldPurchaseRequestApproveRequest, purchaseRequestApproveRequest);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            getHibernateTemplate().update(oldPurchaseRequestApproveRequest);
            return oldPurchaseRequestApproveRequest;
        } else {
            throw new RuntimeException("purchaseRequestApproveRequest not found");
        }
    }

    public PurchaseRequestApproveRequest insertPurchaseRequestApproveRequest(PurchaseRequestApproveRequest purchaseRequestApproveRequest) {
        getHibernateTemplate().save(purchaseRequestApproveRequest);
        return purchaseRequestApproveRequest;
    }

    private final static String SQL_COUNT = "select count(*) from PurchaseRequestApproveRequest prar ,PurchaseRequest purchaseRequest where prar.approveRequestId=purchaseRequest.approveRequestId";

    private final static String SQL = "from PurchaseRequestApproveRequest prar , PurchaseRequest purchaseRequest where prar.approveRequestId=purchaseRequest.approveRequestId";

    private final static Object[][] QUERY_CONDITIONS = {
            { BaseApproveQueryCondition.APPROVER_ID_EQ, "prar.approver.id=? or" + 
                " prar.approver.id in (select ad.originalApprover.id from ApproverDelegate ad where ad.type=" + 
                ApproverDelegateType.PURCHASE_REQUEST_APPROVER.getEnumCode().toString() +
                " and ad.delegateApprover.id=? " + 
                " and ((ad.fromDate is null or ad.fromDate<?) and (ad.toDate is null or ad.toDate>=?)))", null },
            { BaseApproveQueryCondition.SITE_ID_EQ, "purchaseRequest.department.site.id = ?", null },
            { BaseApproveQueryCondition.STATUS_EQ, "prar.status=?", null },
            { BaseApproveQueryCondition.YOUR_TURN_DATE_LE, "prar.yourTurnDate<=?", null },
            { BaseApproveQueryCondition.LAST_EMAIL_DATE_LE, "(prar.lastEmailDate is null or prar.lastEmailDate<=?)", null},
            { BaseApproveQueryCondition.SENT_EMAIL_TIMES_LT, "prar.sentEmailTimes<?", null},
            { PurchaseRequestApproveRequestQueryCondition.CODE_LIKE, "purchaseRequest.id like ?", new LikeConvert() },
            { PurchaseRequestApproveRequestQueryCondition.TITLE_LIKE, "purchaseRequest.title like  ?", new LikeConvert() },
            { PurchaseRequestApproveRequestQueryCondition.STATUS_NEQ, "prar.status<>?", null },
            { PurchaseRequestApproveRequestQueryCondition.SUBMIT_DATE_BT, "purchaseRequest.requestDate between ? and ?", null }, 
    };

    private final static Object[][] QUERY_ORDERS = {
            { PurchaseRequestApproveRequestQueryOrder.PURCHASEREQUEST_ID, "purchaseRequest.id" },
            { PurchaseRequestApproveRequestQueryOrder.SEQ, "prar.seq" }, 
            { PurchaseRequestApproveRequestQueryOrder.STATUS, "prar.status" },
            { PurchaseRequestApproveRequestQueryOrder.APPROVEDATE, "prar.approveDate" }, 
            { PurchaseRequestApproveRequestQueryOrder.COMMENT, "prar.comment" },
            { PurchaseRequestApproveRequestQueryOrder.CANMODIFY, "prar.canModify" }, 
    };

    public int getPurchaseRequestApproveRequestListCount(final Map conditions) {
        return getListCount(conditions, SQL_COUNT, QUERY_CONDITIONS);
    }

    public List getPurchaseRequestApproveRequestList(final Map conditions, final int pageNo, final int pageSize, final PurchaseRequestApproveRequestQueryOrder order,
            final boolean descend) {
        return getList(conditions, pageNo, pageSize, order, descend, SQL, QUERY_CONDITIONS, QUERY_ORDERS);
    }

    public List getBaseApproveRequestList(final Map conditions) {
        return getList(conditions, 0, -1, null, false, SQL, QUERY_CONDITIONS, null);
    }

    public void deletePurchaseRequestApproveRequestByPurchaseRequest(PurchaseRequest purchaseRequest) {
        getHibernateTemplate().delete("from PurchaseRequestApproveRequest prar where prar.approveRequestId=?", purchaseRequest.getApproveRequestId(),
                Hibernate.STRING);
    }

}
