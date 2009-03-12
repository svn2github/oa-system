/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.dao;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate.HibernateCallback;
import org.springframework.orm.hibernate.support.HibernateDaoSupport;

import net.sourceforge.dao.convert.QueryParameterConvert;
import net.sourceforge.dao.convert.QuerySQLConvert;
import com.shcnc.hibernate.CompositeQuery;
import com.shcnc.hibernate.QueryCondition;

/**
 * DAOµÄHibernateÊµÏÖ
 * @author nicebean
 * @version 1.0 (2005-11-11)
 */
public class BaseDAOHibernate extends HibernateDaoSupport implements DAO {
    private Log log = LogFactory.getLog(BaseDAOHibernate.class);

    /**
     * @see net.sourceforge.dao.DAO#refreshObject(java.lang.Object)
     */
    public Object refreshObject(Object o) {
        if (o == null) {
            log.info("Try to refresh a null object");
        }
        getHibernateTemplate().refresh(o);
        return o;
    }
    
    /**
     * @see net.sourceforge.dao.DAO#saveObject(java.lang.Object)
     */
    public void saveObject(Object o) {
        getHibernateTemplate().save(o);
    }

    /**
     * @see net.sourceforge.dao.DAO#updateObject(java.lang.Object)
     */
    public void updateObject(Object o) {
        getHibernateTemplate().update(o);
    }

    /**
     * @see net.sourceforge.dao.DAO#removeObject(java.lang.Object)
     */
    public void removeObject(Object o) {
        getHibernateTemplate().delete(o);
    }

    protected int getListCount(final Map conditions, final String sql, final Object[][] queryConditions) {
        Integer result = (Integer) getHibernateTemplate().execute( new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                CompositeQuery query = new CompositeQuery(sql, "", getSession());
                if (conditions != null) appendConditions(query, conditions, queryConditions);
                List result = query.list();
                if (!result.isEmpty()) {
                    Integer count = (Integer)result.get(0);
                    if (count != null) return count;
                }
                return null;
            }
            
        });
        if (result == null) return 0;
        return result.intValue();
    }

    protected List getList(final Map conditions, final int pageNo, final int pageSize, final Object order, final boolean descend, final String sql, final Object[][] queryConditions, final Object[][] queryOrders) {
        return (List) getHibernateTemplate().execute( new HibernateCallback() {
            
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                CompositeQuery query = new CompositeQuery(sql, getOrder(order, queryOrders, descend), session);
                if (conditions != null) appendConditions(query, conditions, queryConditions);
                if (pageSize == -1) return query.list();
                return query.list(pageNo * pageSize, pageSize);
            }
            
        });
    }
    
    protected static String getOrder(Object order, Object[][] orders, boolean descend) {
        if (order != null) {
            for (int i = 0; i < orders.length; i++) {
                if (orders[i][0].equals(order)) {
                    if (descend) {
                        return orders[i][1].toString() + " desc";
                    }
                    return orders[i][1].toString();
                }
            }
        }
        return "";
    }
    
    protected static void appendConditions(CompositeQuery query, Map condition, Object[][] conditions) {
        for (int i = 0; i < conditions.length; i++) {
            if (condition.containsKey(conditions[i][0])) {
                Object value = condition.get(conditions[i][0]);
                if (conditions[i][2] instanceof QueryParameterConvert) {
                    makeSimpleCondition(query, conditions[i][1].toString(), value, (QueryParameterConvert)conditions[i][2]);
                } else if (conditions[i][2] instanceof QuerySQLConvert) {
                    makeSimpleCondition(query, conditions[i][1].toString(), value, (QuerySQLConvert)conditions[i][2]);
                } else {
                    makeSimpleCondition(query, conditions[i][1].toString(), value);
                }                
            }
        }
    }
    
    protected static void makeSimpleCondition(final CompositeQuery query, final String sql, final Object parameter) {
        //QueryCondition qc=query.createQueryCondition(sql);   
        //if (parameter != null) {
            //qc.appendParameter(parameter);    
        //}
        QueryCondition qc=query.createQueryCondition(sql);
        //Object finalParameter = converter == null ? parameter : converter.convert(parameter);
        if (parameter != null) {
            if (parameter instanceof Object[]) {
                Object[] parameters = (Object[]) parameter;
                for (int i = 0; i < parameters.length; i++) {
                    qc.appendParameter(parameters[i]);
                }
            } else {
                qc.appendParameter(parameter);
            }
        }
    }
    
    protected static void makeSimpleCondition(final CompositeQuery query, final String sql, final Object parameter, final QueryParameterConvert converter) {
        QueryCondition qc=query.createQueryCondition(sql);
        Object finalParameter = converter == null ? parameter : converter.convert(parameter);
        if (finalParameter != null) {
            if (finalParameter instanceof Object[]) {
                Object[] parameters = (Object[]) finalParameter;
                for (int i = 0; i < parameters.length; i++) {
                    qc.appendParameter(parameters[i]);
                }
            } else {
                qc.appendParameter(finalParameter);
            }
        }
    }
    
    protected static void makeSimpleCondition(final CompositeQuery query, String sql, final Object parameter, final QuerySQLConvert converter) {        
        StringBuffer sb = new StringBuffer(sql);
        Object finalParameter = converter == null ? parameter : converter.convert(sb, parameter);
        QueryCondition qc=query.createQueryCondition(sb.toString());
        if (finalParameter != null) {
            if (finalParameter instanceof Object[]) {
                Object[] parameters = (Object[]) finalParameter;
                for (int i = 0; i < parameters.length; i++) {
                    qc.appendParameter(parameters[i]);
                }
            } else {
                qc.appendParameter(finalParameter);
            }
        }
    }

    protected static InputStream preRead(InputStream is) throws IOException {
        if (is instanceof ByteArrayInputStream) {
            return is;
        }
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        while (true) {
            int s = is.read(buffer);
            if (s > 0) {
                os.write(buffer, 0, s);
            } else {
                break;
            }
        }
        is.close();
        return new ByteArrayInputStream(os.toByteArray());
    }
    
    protected long getTodayTimeMillis() {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.clear();
        c1.set(Calendar.YEAR, c2.get(Calendar.YEAR));
        c1.set(Calendar.MONTH, c2.get(Calendar.MONTH));
        c1.set(Calendar.DATE, c2.get(Calendar.DATE));
        return c1.getTimeInMillis();
    }

}
