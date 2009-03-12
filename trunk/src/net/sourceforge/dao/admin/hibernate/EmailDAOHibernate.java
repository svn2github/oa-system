/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.dao.admin.hibernate;

import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;


import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate.HibernateCallback;
import org.springframework.orm.hibernate.HibernateTemplate;

import net.sourceforge.dao.BaseDAOHibernate;
import net.sourceforge.dao.admin.EmailDAO;
import net.sourceforge.dao.convert.LikeConvert;
import net.sourceforge.model.admin.Email;
import net.sourceforge.model.admin.EmailBatch;
import net.sourceforge.model.admin.EmailBatchBody;
import net.sourceforge.model.admin.EmailBody;
import net.sourceforge.model.admin.query.EmailQueryCondition;
import net.sourceforge.model.admin.query.EmailQueryOrder;
import net.sourceforge.model.metadata.YesNo;

/**
 * EmailDAOµÄHibernateÊµÏÖ 
 * @author ych
 * @version 1.0 (Nov 13, 2005)
 */
public class EmailDAOHibernate extends BaseDAOHibernate implements EmailDAO {
    private Log log = LogFactory.getLog(EmailDAOHibernate.class);


    public Email getEmail(Integer id){
        if (id == null) {
            if (log.isDebugEnabled()) log.debug("try to get Email with null id");
            return null;
        }
        return (Email) getHibernateTemplate().get(Email.class, id);
    }
    
    public EmailBatch getEmailBatch(Integer id){
        if (id == null) {
            if (log.isDebugEnabled()) log.debug("try to get EmailBatch with null id");
            return null;
        }
        return (EmailBatch) getHibernateTemplate().get(EmailBatch.class, id);
    }
    
    public String getEmailBody(final Integer id) {
        if (id == null) {
            if (log.isDebugEnabled()) log.debug("try to get EmailBody with null id");
            return null;
        }
        return (String) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                EmailBody eb = (EmailBody) session.get(EmailBody.class, id);
                if (eb == null) {
                    return null;
                }
                char[] buffer = new char[1024];
                StringBuffer result = new StringBuffer();
                Reader rd = eb.getBody().getCharacterStream();
                try {
                    while (true) {
                        int i = rd.read(buffer);
                        if (i > 0) {
                            result.append(buffer, 0, i);
                        } else {
                            break;
                        }
                    }
                    rd.close();
                } catch (IOException e) {
                    throw new HibernateException(e);
                }
                return result.toString();
            }
        });
    }
    
    public List getAllUnSendEmailBatch() {
        return getHibernateTemplate().find(
                "from EmailBatch as eb where eb.isSend = " + YesNo.NO + " order by eb.mailToUser, eb.site, eb.templateName");
    }

    public String getEmailBatchBody(final Integer id) {
        if (id == null) {
            if (log.isDebugEnabled()) log.debug("try to get EmailBatchBody with null id");
            return null;
        }
        return (String) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                EmailBatchBody eb = (EmailBatchBody) session.get(EmailBatchBody.class, id);
                if (eb == null) {
                    return null;
                }
                char[] buffer = new char[1024];
                StringBuffer result = new StringBuffer();
                Reader rd = eb.getBody().getCharacterStream();
                try {
                    while (true) {
                        int i = rd.read(buffer);
                        if (i > 0) {
                            result.append(buffer, 0, i);
                        } else {
                            break;
                        }
                    }
                    rd.close();
                } catch (IOException e) {
                    throw new HibernateException(e);
                }
                return result.toString();
            }
        });    
    }
    
    public Email updateEmail(Email email) {
        Integer id = email.getId();
        if (id == null) {
            throw new RuntimeException("cannot save a email with null id");
        }
        Email oldEmail = getEmail(id);
        if (oldEmail != null) {
            try{
                PropertyUtils.copyProperties(oldEmail,email);
            }
            catch(Exception e)
            {
                throw new RuntimeException("copy error£º"+e);
            }
            getHibernateTemplate().update(oldEmail);    
            return oldEmail;
        }
        else
        {
            throw new RuntimeException("email not found");
        }
    }
    
    public EmailBatch updateEmailBatch(EmailBatch emailBatch) {
        Integer id = emailBatch.getId();
        if (id == null) {
            throw new RuntimeException("cannot save a EmailBatch with null id");
        }
        EmailBatch oldEmailBatch = getEmailBatch(id);
        if (oldEmailBatch != null) {
            try{
                PropertyUtils.copyProperties(oldEmailBatch,emailBatch);
            }
            catch(Exception e)
            {
                throw new RuntimeException("copy error£º"+e);
            }
            getHibernateTemplate().update(oldEmailBatch);    
            return oldEmailBatch;
        }
        else
        {
            throw new RuntimeException("EmailBatch not found");
        }
    }

    public Email insertEmail(Email email) {
        HibernateTemplate template = this.getHibernateTemplate();
        email.setCreateTime(new Date());
        email.setWaitToSend(YesNo.YES);
        email.setFailCount(new Integer(0));
        template.save(email);
        return email;
    }
    
    public EmailBatch insertEmailBatch(EmailBatch emailBatch) {
        HibernateTemplate template = this.getHibernateTemplate();
        template.save(emailBatch);
        return emailBatch;
    }
        
    public void saveEmailBody(final Integer id, final String body) {
        getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                EmailBody eb = (EmailBody) session.get(EmailBody.class, id);
                if (eb == null) return null;
                Clob content = Hibernate.createClob(body);
                eb.setBody(content);
                session.update(eb);
                session.flush();
                return null;
            }

        });
    }
    
    public void saveEmailBatchBody(final Integer id, final String body) {
        getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                EmailBatchBody eb = (EmailBatchBody) session.get(EmailBatchBody.class, id);
                if (eb == null) return null;
                Clob content = Hibernate.createClob(body);
                eb.setBody(content);
                session.update(eb);
                session.flush();
                return null;
            }

        });
    }

    private final static String SQL_COUNT = "select count(*) from Email email";

    private final static String SQL = "from Email email";

    private final static Object[][] QUERY_CONDITIONS = {
        /*id*/    
        { EmailQueryCondition.ID_EQ, "email.id = ?", null },

        /*keyPropertyList*/

        /*kmtoIdList*/

        /*mtoIdList*/

        /*property*/
        { EmailQueryCondition.MAILFROM_LIKE, "email.mailFrom like ?", new LikeConvert() },
        { EmailQueryCondition.MAILTO_LIKE, "email.mailTo like ?", new LikeConvert() },
        { EmailQueryCondition.SUBJECT_LIKE, "email.subject like ?", new LikeConvert() },
        { EmailQueryCondition.BODY_LIKE, "email.body like ?", new LikeConvert() },
        { EmailQueryCondition.CREATETIME_BT, "email.createTime between ? and ?", null },
        { EmailQueryCondition.SENTTIME_BT, "email.sentTime between ? and ?", null },
        { EmailQueryCondition.FAILCOUNT_GE, "email.failCount >= ?", null },
        { EmailQueryCondition.WAITTOSEND_EQ, "email.waitToSend = ?", null },
    };
    
    private final static Object[][] QUERY_ORDERS = {
        /*id*/
        { EmailQueryOrder.ID, "email.id" },

        /*property*/
        { EmailQueryOrder.MAILFROM, "email.mailFrom" },
        { EmailQueryOrder.MAILTO, "email.mailTo" },
        { EmailQueryOrder.SUBJECT, "email.subject" },
        { EmailQueryOrder.BODY, "email.body" },
        { EmailQueryOrder.CREATETIME, "email.createTime" },
        { EmailQueryOrder.SENTTIME, "email.sentTime" },
        { EmailQueryOrder.FAILCOUNT, "email.failCount" },
        { EmailQueryOrder.WAITTOSEND, "email.waitToSend" },
    };
    
    public int getEmailListCount(final Map conditions) {
        return getListCount(conditions, SQL_COUNT, QUERY_CONDITIONS);
    }

    public List getEmailList(final Map conditions, final int pageNo, final int pageSize, final EmailQueryOrder order, final boolean descend) {
        return getList(conditions, pageNo, pageSize, order, descend, SQL, QUERY_CONDITIONS, QUERY_ORDERS);
    }
    
    public List getWaitToSendEmailList() {
        return getHibernateTemplate().find(
                "from Email email where email.waitToSend = ? order by email.id ", 
                new Integer(YesNo.YES.getEnumCode().toString()), Hibernate.INTEGER);
    }
    
    public void deleteEmailBatch(String refNo) {
        String hql = "from EmailBatch as eb where eb.refNo = '" + refNo + "' and eb.isSend = " + net.sourceforge.model.metadata.YesNo.NO;
        getHibernateTemplate().delete(hql);
    }   
    
    public EmailBatch findNotSendEmailBatchByRefNo(String refNo) {
        //following 2 lines removed by nicebean, 07/11/9, template.find() will return List object.
        //String hql = "from EmailBatch as eb where eb.refNo = '" + refNo + "' and eb.isSend = " + net.sourceforge.model.metadata.YesNo.NO;
        //return (EmailBatch)getHibernateTemplate().find(hql);
        return null;
    }   
}
