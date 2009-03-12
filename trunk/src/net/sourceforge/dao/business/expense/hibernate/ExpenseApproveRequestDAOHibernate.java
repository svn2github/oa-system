/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.dao.business.expense.hibernate;

import java.util.List;
import java.util.Map;

import net.sf.hibernate.Hibernate;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sourceforge.dao.business.expense.ExpenseApproveRequestDAO;
import net.sourceforge.dao.business.hibernate.BaseApproveRequestDAOHibernate;
import net.sourceforge.dao.convert.LikeConvert;
import net.sourceforge.model.admin.User;
import net.sourceforge.model.business.expense.Expense;
import net.sourceforge.model.business.expense.ExpenseApproveRequest;
import net.sourceforge.model.business.expense.query.ExpenseApproveRequestQueryCondition;
import net.sourceforge.model.business.expense.query.ExpenseApproveRequestQueryOrder;
import net.sourceforge.model.business.query.BaseApproveQueryCondition;
import net.sourceforge.model.metadata.ApproverDelegateType;

/**
 * ExpenseApproveRequestµÄHibernateÊµÏÖ 
 * @author ych
 * @version 1.0 (Nov 18, 2005)
 */
public class ExpenseApproveRequestDAOHibernate extends BaseApproveRequestDAOHibernate implements ExpenseApproveRequestDAO {
    private Log log = LogFactory.getLog(ExpenseApproveRequestDAOHibernate.class);

    public List getBaseApproveRequestListByApproveRequestId(String approveRequestId) {
        return this.getExpenseApproveRequestListByApproveRequestId(approveRequestId);
    }

    public List getExpenseApproveRequestListByApproveRequestId(String approveRequestId) {
        return getHibernateTemplate().find("from ExpenseApproveRequest ear where ear.approveRequestId=? order by ear.seq",
                approveRequestId , Hibernate.STRING);
    }
    
    public ExpenseApproveRequest getExpenseApproveRequest(String requestId, User approver) {
        if (requestId == null) {
            if (log.isDebugEnabled())
                log.debug("try to get ExpenseApproveRequest with null requestId");
            return null;
        }
        if (approver == null) {
            if (log.isDebugEnabled())
                log.debug("try to get ExpenseApproveRequest with null approver");
            return null;
        }
        return (ExpenseApproveRequest) getHibernateTemplate().get(ExpenseApproveRequest.class,
                new ExpenseApproveRequest(requestId, approver));
    }

    public ExpenseApproveRequest updateExpenseApproveRequest(ExpenseApproveRequest expenseApproveRequest) {
        ExpenseApproveRequest oldExpenseApproveRequest = getExpenseApproveRequest(expenseApproveRequest.getApproveRequestId(),
                expenseApproveRequest.getApprover());
        if (oldExpenseApproveRequest != null) {
            try {
                PropertyUtils.copyProperties(oldExpenseApproveRequest, expenseApproveRequest);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            getHibernateTemplate().update(oldExpenseApproveRequest);
            return oldExpenseApproveRequest;
        } else {
            throw new RuntimeException("expenseApproveRequest not found");
        }
    }

    public ExpenseApproveRequest insertExpenseApproveRequest(ExpenseApproveRequest expenseApproveRequest) {
        getHibernateTemplate().save(expenseApproveRequest);
        return expenseApproveRequest;
    }

    private final static String SQL_COUNT = "select count(*) from ExpenseApproveRequest ear ,Expense expense where ear.approveRequestId=expense.approveRequestId";

    private final static String SQL = "from ExpenseApproveRequest ear , Expense expense where ear.approveRequestId=expense.approveRequestId";

    private final static Object[][] QUERY_CONDITIONS = {
        { BaseApproveQueryCondition.APPROVER_ID_EQ , "ear.approver.id=? or " + 
            " ear.approver.id in (select ad.originalApprover.id from ApproverDelegate ad where ad.type="+
            ApproverDelegateType.EXPENSE_APPROVER.getEnumCode().toString()+
            " and ad.delegateApprover.id=? " +
            " and ((ad.fromDate is null or ad.fromDate<?) and (ad.toDate is null or ad.toDate>=?)))", null },
        { BaseApproveQueryCondition.SITE_ID_EQ, "expense.department.site.id = ?", null},
        { BaseApproveQueryCondition.STATUS_EQ, "ear.status=?", null},
        { BaseApproveQueryCondition.YOUR_TURN_DATE_LE, "ear.yourTurnDate<=?", null},
        { BaseApproveQueryCondition.LAST_EMAIL_DATE_LE, "(ear.lastEmailDate is null or ear.lastEmailDate<=?)", null},
        { BaseApproveQueryCondition.SENT_EMAIL_TIMES_LT, "ear.sentEmailTimes<?", null},
        { ExpenseApproveRequestQueryCondition.CODE_LIKE, "expense.id like ?", new LikeConvert() },
        { ExpenseApproveRequestQueryCondition.TITLE_LIKE, "expense.title like  ?", new LikeConvert() },
        { ExpenseApproveRequestQueryCondition.STATUS_NEQ, "ear.status<>?", null},
        { ExpenseApproveRequestQueryCondition.SUBMIT_DATE_BT, "expense.requestDate between ? and ?", null},
        { ExpenseApproveRequestQueryCondition.EXPENSE_AMOUNT_GE, "expense.amount >= ?", null},
        { ExpenseApproveRequestQueryCondition.EXPENSE_AMOUNT_LE, "expense.amount <= ?", null},
        };

    private final static Object[][] QUERY_ORDERS = {
        { ExpenseApproveRequestQueryOrder.EXPENSE_CODE, "expense.id" }, 
        { ExpenseApproveRequestQueryOrder.EXPENSE_DEPARTMENT, "expense.department.name" }, 
        { ExpenseApproveRequestQueryOrder.SEQ, "ear.seq" },
        { ExpenseApproveRequestQueryOrder.STATUS, "ear.status" }, 
        { ExpenseApproveRequestQueryOrder.APPROVEDATE, "ear.approveDate" },
        { ExpenseApproveRequestQueryOrder.COMMENT, "ear.comment" }, 
        { ExpenseApproveRequestQueryOrder.CANMODIFY, "ear.canModify" }, 
        };

    public int getExpenseApproveRequestListCount(final Map conditions) {
        return getListCount(conditions, SQL_COUNT, QUERY_CONDITIONS);
    }

    public List getExpenseApproveRequestList(final Map conditions, final int pageNo, final int pageSize,
            final ExpenseApproveRequestQueryOrder order, final boolean descend) {
        return getList(conditions, pageNo, pageSize, order, descend, SQL, QUERY_CONDITIONS, QUERY_ORDERS);
    }

    public List getBaseApproveRequestList(final Map conditions) {
        return getList(conditions, 0, -1, null, false, SQL, QUERY_CONDITIONS, null);
    }

    public void deleteExpenseApproveRequestByExpense(Expense expense) {
        getHibernateTemplate().delete("from ExpenseApproveRequest ear where ear.approveRequestId=?",expense.getApproveRequestId(),Hibernate.STRING);
    }

}
