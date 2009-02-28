/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.dao.business.po.hibernate;

import java.util.List;
import java.util.Map;

import net.sf.hibernate.Hibernate;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.aof.dao.business.hibernate.BaseApproveRequestDAOHibernate;
import com.aof.dao.business.po.PurchaseOrderApproveRequestDAO;
import com.aof.dao.convert.LikeConvert;
import com.aof.model.admin.User;
import com.aof.model.business.po.PurchaseOrder;
import com.aof.model.business.po.PurchaseOrderApproveRequest;
import com.aof.model.business.po.query.PurchaseOrderApproveRequestQueryCondition;
import com.aof.model.business.po.query.PurchaseOrderApproveRequestQueryOrder;
import com.aof.model.business.query.BaseApproveQueryCondition;
import com.aof.model.metadata.ApproverDelegateType;

/**
 * PurchaseOrderApproveRequestµÄHibernateÊµÏÖ
 * 
 * @author nicebean
 * @version 1.0 (2005-12-15)
 */
public class PurchaseOrderApproveRequestDAOHibernate extends BaseApproveRequestDAOHibernate implements PurchaseOrderApproveRequestDAO {
    private Log log = LogFactory.getLog(PurchaseOrderApproveRequestDAOHibernate.class);

    public List getBaseApproveRequestListByApproveRequestId(String approveRequestId) {
        return this.getPurchaseOrderApproveRequestListByApproveRequestId(approveRequestId);
    }

    public List getPurchaseOrderApproveRequestListByApproveRequestId(String approveRequestId) {
        return getHibernateTemplate().find("from PurchaseOrderApproveRequest prar where prar.approveRequestId=? order by prar.seq", approveRequestId,
                Hibernate.STRING);
    }

    public PurchaseOrderApproveRequest getPurchaseOrderApproveRequest(String requestId, User approver) {
        if (requestId == null) {
            if (log.isDebugEnabled())
                log.debug("try to get PurchaseOrderApproveRequest with null requestId");
            return null;
        }
        if (approver == null) {
            if (log.isDebugEnabled())
                log.debug("try to get PurchaseOrderApproveRequest with null approver");
            return null;
        }
        return (PurchaseOrderApproveRequest) getHibernateTemplate().get(PurchaseOrderApproveRequest.class, new PurchaseOrderApproveRequest(requestId, approver));
    }

    public PurchaseOrderApproveRequest updatePurchaseOrderApproveRequest(PurchaseOrderApproveRequest purchaseOrderApproveRequest) {
        PurchaseOrderApproveRequest oldPurchaseOrderApproveRequest = getPurchaseOrderApproveRequest(purchaseOrderApproveRequest.getApproveRequestId(),
                purchaseOrderApproveRequest.getApprover());
        if (oldPurchaseOrderApproveRequest != null) {
            try {
                PropertyUtils.copyProperties(oldPurchaseOrderApproveRequest, purchaseOrderApproveRequest);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            getHibernateTemplate().update(oldPurchaseOrderApproveRequest);
            return oldPurchaseOrderApproveRequest;
        } else {
            throw new RuntimeException("purchaseOrderApproveRequest not found");
        }
    }

    public PurchaseOrderApproveRequest insertPurchaseOrderApproveRequest(PurchaseOrderApproveRequest purchaseOrderApproveRequest) {
        getHibernateTemplate().save(purchaseOrderApproveRequest);
        return purchaseOrderApproveRequest;
    }

    private final static String SQL_COUNT = "select count(*) from PurchaseOrderApproveRequest prar ,PurchaseOrder purchaseOrder where prar.approveRequestId=purchaseOrder.approveRequestId";

    private final static String SQL = "from PurchaseOrderApproveRequest prar , PurchaseOrder purchaseOrder where prar.approveRequestId=purchaseOrder.approveRequestId";

    private final static Object[][] QUERY_CONDITIONS = {
            { BaseApproveQueryCondition.APPROVER_ID_EQ, "prar.approver.id=? or" + 
                " prar.approver.id in (select ad.originalApprover.id from ApproverDelegate ad where ad.type=" + 
                ApproverDelegateType.PURCHASE_ORDER_APPROVER.getEnumCode().toString() +
                " and ad.delegateApprover.id=? " + 
                " and ((ad.fromDate is null or ad.fromDate<?) and (ad.toDate is null or ad.toDate>=?)))", null },
            { BaseApproveQueryCondition.SITE_ID_EQ, "purchaseOrder.site.id = ?", null },
            { BaseApproveQueryCondition.STATUS_EQ, "prar.status=?", null },
            { BaseApproveQueryCondition.YOUR_TURN_DATE_LE, "prar.yourTurnDate<=?", null },
            { BaseApproveQueryCondition.LAST_EMAIL_DATE_LE, "(prar.lastEmailDate is null or prar.lastEmailDate<=?)", null},
            { BaseApproveQueryCondition.SENT_EMAIL_TIMES_LT, "prar.sentEmailTimes<?", null},
            { PurchaseOrderApproveRequestQueryCondition.CODE_LIKE, "purchaseOrder.id like ?", new LikeConvert() },
            { PurchaseOrderApproveRequestQueryCondition.TITLE_LIKE, "purchaseOrder.title like  ?", new LikeConvert() },
            { PurchaseOrderApproveRequestQueryCondition.STATUS_NEQ, "prar.status<>?", null },
            { PurchaseOrderApproveRequestQueryCondition.SUBMIT_DATE_BT, "purchaseOrder.requestDate between ? and ?", null }, 
            { PurchaseOrderApproveRequestQueryCondition.PUCHASE_ORDER_SUPPLIER_NAME_LIKE, "purchaseOrder.supplier.name like ?", new LikeConvert() }, 
            { PurchaseOrderApproveRequestQueryCondition.PUCHASE_ORDER_AMOUNT_GE, "purchaseOrder.amount >= ?", null},
            { PurchaseOrderApproveRequestQueryCondition.PUCHASE_ORDER_AMOUNT_LE, "purchaseOrder.amount <= ?", null},
    };

    private final static Object[][] QUERY_ORDERS = {
            { PurchaseOrderApproveRequestQueryOrder.PURCHASEORDER_ID, "purchaseOrder.id" },
            { PurchaseOrderApproveRequestQueryOrder.SEQ, "prar.seq" }, 
            { PurchaseOrderApproveRequestQueryOrder.STATUS, "prar.status" },
            { PurchaseOrderApproveRequestQueryOrder.APPROVEDATE, "prar.approveDate" }, 
            { PurchaseOrderApproveRequestQueryOrder.COMMENT, "prar.comment" },
            { PurchaseOrderApproveRequestQueryOrder.CANMODIFY, "prar.canModify" }, 
    };

    public int getPurchaseOrderApproveRequestListCount(final Map conditions) {
        return getListCount(conditions, SQL_COUNT, QUERY_CONDITIONS);
    }

    public List getPurchaseOrderApproveRequestList(final Map conditions, final int pageNo, final int pageSize, final PurchaseOrderApproveRequestQueryOrder order,
            final boolean descend) {
        return getList(conditions, pageNo, pageSize, order, descend, SQL, QUERY_CONDITIONS, QUERY_ORDERS);
    }

    public List getBaseApproveRequestList(final Map conditions) {
        return getList(conditions, 0, -1, null, false, SQL, QUERY_CONDITIONS, null);
    }

    public void deletePurchaseOrderApproveRequestByPurchaseOrder(PurchaseOrder purchaseOrder) {
        getHibernateTemplate().delete("from PurchaseOrderApproveRequest prar where prar.approveRequestId=?", purchaseOrder.getApproveRequestId(),
                Hibernate.STRING);
    }

    public int getNextApproveRequestSeq(String approveRequestId) {
        getHibernateTemplate().find("select max(prar.seq) from PurchaseOrderApproveRequest prar where prar.approveRequestId");
        return 0;
    }

}
