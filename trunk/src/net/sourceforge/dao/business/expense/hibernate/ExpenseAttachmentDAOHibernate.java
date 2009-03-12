/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.dao.business.expense.hibernate;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate.HibernateCallback;

import net.sourceforge.dao.BaseDAOHibernate;
import net.sourceforge.dao.business.expense.ExpenseAttachmentDAO;
import net.sourceforge.dao.convert.LikeConvert;
import net.sourceforge.model.business.expense.Expense;
import net.sourceforge.model.business.expense.ExpenseAttachment;
import net.sourceforge.model.business.expense.ExpenseAttachmentContent;
import net.sourceforge.model.business.expense.query.ExpenseAttachmentQueryCondition;
import net.sourceforge.model.business.expense.query.ExpenseAttachmentQueryOrder;
/**
 * ExpenseAttachmentDAOµÄHibernateÊµÏÖ 
 * @author ych
 * @version 1.0 (Nov 23, 2005)
 */
public class ExpenseAttachmentDAOHibernate extends BaseDAOHibernate implements ExpenseAttachmentDAO {
    private Log log = LogFactory.getLog(ExpenseAttachmentDAOHibernate.class);


    public ExpenseAttachment getExpenseAttachment(Integer id){
        if (id == null) {
            if (log.isDebugEnabled()) log.debug("try to get ExpenseAttachment with null id");
            return null;
        }
        return (ExpenseAttachment) getHibernateTemplate().get(ExpenseAttachment.class, id);
    }

    public void deleteExpenseAttachment(Integer id) {
        ExpenseAttachment expenseAttachment = getExpenseAttachment(id);
        getHibernateTemplate().delete(expenseAttachment);
    }
    
    public ExpenseAttachment updateExpenseAttachment(ExpenseAttachment expenseAttachment) {
        Integer id = expenseAttachment.getId();
        if (id == null) {
            throw new RuntimeException("cannot save a expenseAttachment with null id");
        }
        ExpenseAttachment oldExpenseAttachment = getExpenseAttachment(id);
        if (oldExpenseAttachment != null) {
        	try{
                PropertyUtils.copyProperties(oldExpenseAttachment,expenseAttachment);
        	}
        	catch(Exception e)
        	{
                throw new RuntimeException(e);
        	}
        	getHibernateTemplate().update(oldExpenseAttachment);	
        	return oldExpenseAttachment;
        }
        else
        {
        	throw new RuntimeException("expenseAttachment not found");
        }
    }

	public ExpenseAttachment insertExpenseAttachment(ExpenseAttachment expenseAttachment) {
       	getHibernateTemplate().save(expenseAttachment);
       	return expenseAttachment;
    }

    private final static String SQL_COUNT = "select count(*) from ExpenseAttachment expenseAttachment";

    private final static String SQL = "from ExpenseAttachment expenseAttachment";

    private final static Object[][] QUERY_CONDITIONS = {
    	/*id*/    
		{ ExpenseAttachmentQueryCondition.ID_EQ, "expenseAttachment.id = ?", null },
      
		{ ExpenseAttachmentQueryCondition.EXPENSE_ID_EQ, "expenseAttachment.expense.id = ?", null },

		/*property*/
		{ ExpenseAttachmentQueryCondition.FILESIZE_EQ, "expenseAttachment.fileSize = ?", null },
		{ ExpenseAttachmentQueryCondition.FILENAME_LIKE, "expenseAttachment.fileName like ?", new LikeConvert() },
		{ ExpenseAttachmentQueryCondition.DESCRIPTION_LIKE, "expenseAttachment.description like ?", new LikeConvert() },
		{ ExpenseAttachmentQueryCondition.UPLOADDATE_EQ, "expenseAttachment.uploadDate = ?", null },
    };
    
    private final static Object[][] QUERY_ORDERS = {
		/*id*/
        { ExpenseAttachmentQueryOrder.ID, "expenseAttachment.id" },

		/*property*/
        { ExpenseAttachmentQueryOrder.FILESIZE, "expenseAttachment.fileSize" },
        { ExpenseAttachmentQueryOrder.FILENAME, "expenseAttachment.fileName" },
        { ExpenseAttachmentQueryOrder.DESCRIPTION, "expenseAttachment.description" },
        { ExpenseAttachmentQueryOrder.UPLOADDATE, "expenseAttachment.uploadDate" },
    };
    
    public int getExpenseAttachmentListCount(final Map conditions) {
        return getListCount(conditions, SQL_COUNT, QUERY_CONDITIONS);
    }

    public List getExpenseAttachmentList(final Map conditions, final int pageNo, final int pageSize, final ExpenseAttachmentQueryOrder order, final boolean descend) {
        return getList(conditions, pageNo, pageSize, order, descend, SQL, QUERY_CONDITIONS, QUERY_ORDERS);
    }

    public void saveExpenseAttachmentContent(final Integer id, final InputStream inputStream) {
        getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                ExpenseAttachmentContent scc = (ExpenseAttachmentContent) session.get(ExpenseAttachmentContent.class, id);
                if (scc == null) return null;
                try {
                    Blob content = Hibernate.createBlob(inputStream);
                    scc.setFileContent(content);
                    session.update(scc);
                    session.flush();
                } catch (IOException e) {
                    throw new HibernateException(e);
                }
                return null;
            }

        });
    }

    public InputStream getExpenseAttachmentContent(final Integer id) {
        return (InputStream) getHibernateTemplate().execute( new HibernateCallback() {
            
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                ExpenseAttachmentContent scc = (ExpenseAttachmentContent) session.get(ExpenseAttachmentContent.class, id);
                if (scc == null) return null;
                Blob fileContent = scc.getFileContent();
                if (fileContent == null) return null;
                try {
                    return preRead(fileContent.getBinaryStream());
                } catch (IOException e) {
                    throw new HibernateException(e);
                }
            }
            
        });
    }

    
    public void deleteExpenseAttachmentByExpense(Expense expense) {
        getHibernateTemplate().delete("from ExpenseAttachment ea where ea.expense.id=?",expense.getId(),Hibernate.STRING);
    }

    
}
