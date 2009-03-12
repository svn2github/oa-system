/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.dao.business.expense.hibernate;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.type.Type;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate.HibernateCallback;

import net.sourceforge.dao.BaseDAOHibernate;
import net.sourceforge.dao.business.expense.ExpenseDAO;
import net.sourceforge.dao.convert.LikeConvert;
import net.sourceforge.dao.convert.QueryParameterConvert;
import net.sourceforge.dao.convert.QuerySQLConvert;
import net.sourceforge.model.admin.ExpenseSubCategory;
import net.sourceforge.model.admin.SiteMailReminder;
import net.sourceforge.model.business.expense.Expense;
import net.sourceforge.model.business.expense.ExpenseApproveRequest;
import net.sourceforge.model.business.expense.ExpenseCell;
import net.sourceforge.model.business.expense.ExpenseClaim;
import net.sourceforge.model.business.expense.ExpenseHistory;
import net.sourceforge.model.business.expense.ExpenseHistoryCell;
import net.sourceforge.model.business.expense.ExpenseHistoryRow;
import net.sourceforge.model.business.expense.ExpenseRecharge;
import net.sourceforge.model.business.expense.ExpenseRow;
import net.sourceforge.model.business.expense.query.ExpenseQueryCondition;
import net.sourceforge.model.business.expense.query.ExpenseQueryOrder;
import net.sourceforge.model.metadata.ExpenseStatus;
import net.sourceforge.web.struts.action.ActionUtils;

/**
 * ExpenseDAOµÄHibernateÊµÏÖ
 * 
 * @author ych
 * @version 1.0 (Nov 19, 2005)
 */
public class ExpenseDAOHibernate extends BaseDAOHibernate implements ExpenseDAO {
    private Log log = LogFactory.getLog(ExpenseDAOHibernate.class);

    public Expense getExpense(String id) {
        if (id == null) {
            if (log.isDebugEnabled())
                log.debug("try to get Expense with null id");
            return null;
        }
        return (Expense) getHibernateTemplate().get(Expense.class, id);
    }

    public Expense updateExpense(Expense expense) {
        String id = expense.getId();
        if (id == null) {
            throw new RuntimeException("cannot save a expense with null id");
        }
        Expense oldExpense = getExpense(id);
        if (oldExpense != null) {
            try {
                PropertyUtils.copyProperties(oldExpense, expense);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            getHibernateTemplate().update(oldExpense);
            return oldExpense;
        } else {
            throw new RuntimeException("expense not found");
        }
    }

    public Expense insertExpense(Expense expense) {
        getHibernateTemplate().save(expense);
        return expense;
    }

    private final static String SQL_COUNT = "select count(*) from Expense expense";

    private final static String SQL = "from Expense expense";

    private final static Object[][] QUERY_CONDITIONS = { { ExpenseQueryCondition.ID_LIKE, "expense.id like ?", new LikeConvert() },
        { ExpenseQueryCondition.STATUS_EQ2, "expense.status in (?,?)", null },
        { ExpenseQueryCondition.STATUS_EQ3, "expense.status in (?,?,?)", null },
        { ExpenseQueryCondition.ID_BEGINWITH, "expense.id like ?", new QueryParameterConvert(){
            public Object convert(Object src) {
                return src+"%";
            }} },
        { ExpenseQueryCondition.SITE_ID_EQ, "expense.department.site.id = ?", null },
        { ExpenseQueryCondition.TRAVELAPPLICATION_ID_EQ, "expense.travelApplication.id = ?", null },
        { ExpenseQueryCondition.DEPARTMENT_ID_EQ, "expense.department.id = ?", null },
        { ExpenseQueryCondition.DEPARTMENT_ID_IN, "expense.department.id in ", new QuerySQLConvert() {
                public Object convert(StringBuffer sql, Object parameter) { 
                    if (parameter != null && parameter instanceof Object[]) {
                        Object[] finalParameter = (Object[])parameter;
                        if (finalParameter.length > 0) {
                            sql.append("(?");
                            for (int i=1; i < finalParameter.length;i++) {
                                sql.append(",?");
                            }
                            sql.append(")");
                        } else {
                            return finalParameter;
                        }
                    } else {
                        return parameter;
                    }
                    return parameter;
                }
            }},
        { ExpenseQueryCondition.EXPENSECATEGORY_ID_EQ, "expense.expenseCategory.id = ?", null },
        { ExpenseQueryCondition.EXPENSECURRENCY_CODE_EQ, "expense.expenseCurrency.code = ?", null },
        { ExpenseQueryCondition.BASECURRENCY_CODE_EQ, "expense.baseCurrency.code = ?", null },
        { ExpenseQueryCondition.REQUESTOR_ID_EQ, "expense.requestor.id = ?", null },
        { ExpenseQueryCondition.REQUESTOR_ID_NEQ, "expense.requestor.id <> ?", null },
        { ExpenseQueryCondition.CREATOR_ID_EQ, "expense.creator.id = ?", null },
        { ExpenseQueryCondition.TITLE_LIKE, "expense.title like ?", new LikeConvert() },
        { ExpenseQueryCondition.DESCRIPTION_LIKE, "expense.description like ?", new LikeConvert() },
        { ExpenseQueryCondition.STATUS_EQ, "expense.status = ?", null }, { ExpenseQueryCondition.AMOUNT_EQ, "expense.amount = ?", null },
        { ExpenseQueryCondition.EXCHANGERATE_EQ, "expense.exchangeRate = ?", null },
        { ExpenseQueryCondition.REQUESTDATE_EQ, "expense.requestDate = ?", null }, { ExpenseQueryCondition.CRATEDATE_EQ, "expense.crateDate = ?", null },
        { ExpenseQueryCondition.ISRECHARGE_EQ, "expense.isRecharge = ?", null },
        { ExpenseQueryCondition.APPROVEREQUESTID_EQ, "expense.approveRequestId = ?", null },
        { ExpenseQueryCondition.EXPORTSTATUS_EQ, "expense.exportStatus = ?", null }, 
        { ExpenseQueryCondition.AMOUNT_GT, "expense.amount >= ?", null },
        { ExpenseQueryCondition.AMOUNT_LT, "expense.amount <= ?", null },
        { ExpenseQueryCondition.REQUESTDATE_GT, "expense.requestDate >= ?", null },
        { ExpenseQueryCondition.REQUESTDATE_LT, "expense.requestDate <= ?", null },
        { ExpenseQueryCondition.CREATEDATE_GE, "expense.createDate >= ?", null },
        { ExpenseQueryCondition.CREATEDATE_LE, "expense.createDate <= ?", null },
        { ExpenseQueryCondition.APPROVEDATE_GE, "expense.approveDate >= ?", null },
        { ExpenseQueryCondition.APPROVEDATE_LE, "expense.approveDate <= ?", null },
        { ExpenseQueryCondition.REQUESTOR_LK , "expense.requestor.name like ?",  new LikeConvert() },
    };

    private final static Object[][] QUERY_ORDERS = { { ExpenseQueryOrder.ID, "expense.id" },
            { ExpenseQueryOrder.CATEGORY, "expense.expenseCategory.description" }, { ExpenseQueryOrder.DEPARTMENT, "expense.department.name" },
            { ExpenseQueryOrder.TITLE, "expense.title" }, { ExpenseQueryOrder.DESCRIPTION, "expense.description" },
            { ExpenseQueryOrder.STATUS, "expense.status" }, { ExpenseQueryOrder.AMOUNT, "expense.amount" },
            { ExpenseQueryOrder.EXCHANGERATE, "expense.exchangeRate" }, { ExpenseQueryOrder.REQUESTDATE, "expense.requestDate" },
            { ExpenseQueryOrder.CREATEDATE, "expense.createDate" }, { ExpenseQueryOrder.ISRECHARGE, "expense.isRecharge" },
            { ExpenseQueryOrder.APPROVEREQUESTID, "expense.approveRequestId" }, { ExpenseQueryOrder.EXPORTSTATUS, "expense.exportStatus" }, 
            { ExpenseQueryOrder.CONFIRMDATE, "expense.confirmDate" }, };

    public int getExpenseListCount(final Map conditions) {
        return getListCount(conditions, SQL_COUNT, QUERY_CONDITIONS);
    }

    public List getExpenseList(final Map conditions, final int pageNo, final int pageSize, final ExpenseQueryOrder order, final boolean descend) {
        return getList(conditions, pageNo, pageSize, order, descend, SQL, QUERY_CONDITIONS, QUERY_ORDERS);
    }

    public String getLastExpenseCode() {
        String result = (String) getHibernateTemplate().find("select max(expense.id) from Expense expense where expense.id like 'EX%'").get(0);
        return (result == null ? "EX000000" : result + "000000").substring(0, 8);
    }

    public Expense getExpenseByApproveRequestId(String approveRequestId) {
        Map condition = new HashMap();
        condition.put(ExpenseQueryCondition.APPROVEREQUESTID_EQ, approveRequestId);
        List result = getExpenseList(condition, 0, -1, null, false);
        if (result.size() > 0)
            return (Expense) result.get(0);
        else
            return null;
    }

    public void saveExpenseApproveRequst(ExpenseApproveRequest ear) {
        this.getHibernateTemplate().save(ear);
    }
    
    public List getExpenseClaimList(Expense expense) {
        return getHibernateTemplate().find("from ExpenseClaim ec where ec.expense.id=?",expense.getId(),Hibernate.STRING);
    }
    
    public void insertExpenseClaim(ExpenseClaim ec) {
        this.getHibernateTemplate().save(ec);
    }
    
    public void deleteExpenseClaimListByExpense(Expense expense) {
        this.getHibernateTemplate().delete("from ExpenseClaim ec where ec.expense.id=?",expense.getId(),Hibernate.STRING);
        this.getHibernateTemplate().flush();
    }
    
    public void saveExpenseRecharge(ExpenseRecharge expenseRecharge) {
        this.getHibernateTemplate().save(expenseRecharge);
    }
    
    public void deleteExpenseRechargeByExpense(Expense expense) {
        this.getHibernateTemplate().delete("from ExpenseRecharge er where er.expense.id=?",expense.getId(),Hibernate.STRING);
        this.getHibernateTemplate().flush();
    }
    
    public List getExpenseRechargeListByExpense(Expense expense) {
        return getHibernateTemplate().find("from ExpenseRecharge er where er.expense.id=? order by er.id",expense.getId(),Hibernate.STRING);
    }
    
    public void saveExpenseRow(ExpenseRow expenseRow) {
        this.getHibernateTemplate().save(expenseRow);
    }

    public void deleteExpenseRowByExpense(Expense expense) {
        this.getHibernateTemplate().delete("from ExpenseRow er where er.expense.id=?",expense.getId(),Hibernate.STRING);
        this.getHibernateTemplate().flush();
    }
    
    public List getExpenseRowListByExpense(Expense expense) {
        return getHibernateTemplate().find("from ExpenseRow er where er.expense.id=? order by er.date",expense.getId(),Hibernate.STRING);
    }
    
    public void saveExpenseCell(ExpenseCell expenseCell) {
        this.getHibernateTemplate().save(expenseCell);
    }
    
    public void deleteExpenseCellByExpense(Expense expense) {
        this.getHibernateTemplate().delete("from ExpenseCell ec where ec.row.expense.id=?",expense.getId(),Hibernate.STRING);
        this.getHibernateTemplate().flush();
    }
    
    public List getExpenseCellListByExpense(Expense expense) {
        return getHibernateTemplate().find("from ExpenseCell ec where ec.row.expense.id=? order by ec.row.date,ec.expenseSubCategory.id",expense.getId(),Hibernate.STRING);
    }
    
    public void deleteExpense(String expenseId) {
        Expense expense=getExpense(expenseId);
        this.getHibernateTemplate().delete(expense);
    }

    public void saveExpenseHistory(ExpenseHistory expenseHistory) {
        this.getHibernateTemplate().save(expenseHistory);
    }
    
    public void deleteExpenseHistory(ExpenseHistory expenseHistory) {
        this.getHibernateTemplate().delete(expenseHistory);
        this.getHibernateTemplate().flush();
    }   
    
    public void deleteExpenseHistoryListByExpense(Expense expense) {
        getHibernateTemplate().delete("from ExpenseHistory eh where eh.expense.id=?",expense.getId(),Hibernate.STRING);
        this.getHibernateTemplate().flush();
    }

    public void saveExpenseHistoryRow(ExpenseHistoryRow expenseHistoryRow) {
        this.getHibernateTemplate().save(expenseHistoryRow);
    }

    public void deleteExpenseHistoryRow(ExpenseHistoryRow expenseHistoryRow) {
        this.getHibernateTemplate().delete(expenseHistoryRow);
        this.getHibernateTemplate().flush();
        
    }
    
    public void deleteExpenseHistoryRowListByExpense(Expense expense) {
        getHibernateTemplate().delete("from ExpenseHistoryRow ehr where ehr.expenseHistory.expense.id=?",expense.getId(),Hibernate.STRING);
        this.getHibernateTemplate().flush();
    }

    public void saveExpenseHistoryCell(ExpenseHistoryCell expenseHistoryCell) {
        this.getHibernateTemplate().save(expenseHistoryCell);
    }

    public void deleteExpenseHistoryCellListByExpense(Expense expense) {
        getHibernateTemplate().delete("from ExpenseHistoryCell ehc where ehc.row.expenseHistory.expense.id=?",expense.getId(),Hibernate.STRING);
    }
    
    public void deleteExpenseHistoryCell(ExpenseHistoryCell expenseHistoryCell) {
        this.getHibernateTemplate().delete(expenseHistoryCell);
        this.getHibernateTemplate().flush();
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.dao.business.expense.ExpenseDAO#getMaxExpenseIdBeginWith(java.lang.String)
     */
    public String getMaxExpenseIdBeginWith(String prefix) {
        String sql="select max(expense.id) from Expense expense";
        Map conds=new HashMap();
        conds.put(ExpenseQueryCondition.ID_BEGINWITH,prefix);
        List l=getList(conds, 0, -1, null, false, sql, QUERY_CONDITIONS, QUERY_ORDERS);
        if(l.isEmpty()) return null;
        else return (String) l.get(0);
    }

    public String getColumnDescription(Expense ep, ExpenseSubCategory esc) {
        String sql="select ec.description,ec.row.date from ExpenseCell ec where ec.row.expense.id=? and ec.expenseSubCategory.id=? order by ec.row.date";
        List l=this.getHibernateTemplate().find(sql,
                new Object[]{ep.getId(),esc.getId()},new Type[]{Hibernate.STRING,Hibernate.INTEGER});
        StringBuffer sb=new StringBuffer();
        for (Iterator iter = l.iterator(); iter.hasNext();) {
            Object[] row = (Object[]) iter.next();
            String desc=(String) row[0];
            Date d=(Date) row[1];
            sb.append(ActionUtils.getDisplayDateFromDate(d));
            sb.append(' ');
            sb.append(desc);
            sb.append(' ');
        }
        return sb.toString();
    }
    
    
    public List getFinanceNotRespondedExpenseList(final Date time, final SiteMailReminder smr) {
        return (List) getHibernateTemplate().execute( new HibernateCallback() {
            
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query q=session.createQuery(
                        "from Expense e where e.status=? "+
                        "and e.approveDate<=? and e.emailTimes<? "+
                        "and (e.emailDate is null or e.emailDate<=?)"+
                        "and e.department.site.id=?");
                q.setParameter(0,ExpenseStatus.APPROVED);
                q.setTimestamp(1,smr.getApproveDate(time));
                q.setInteger(2,smr.getMaxTime());
                q.setTimestamp(3,smr.getEmailDate(time));
                q.setParameter(4,smr.getSite().getId());
                return q.list();
            }
        });
    }
    
    public List getExpenseCategoriesAndUserageAmountBySiteId(Integer siteId)
    {
        String sql="select ex.expenseCategory, count(ex.expenseCategory) "
                 + "from Expense as ex "
                 + "where ex.department.site.id = ? "
                 + "group by ex.expenseCategory "
                 + "order by count(ex.expenseCategory) ";
        
        List l=this.getHibernateTemplate().find(sql,
                new Object[]{siteId},new Type[]{Hibernate.INTEGER});
        
        return l;
    }
}
