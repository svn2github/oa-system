/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.dao.business.pr.hibernate;

import java.sql.SQLException;
import java.util.List;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;

import org.springframework.orm.hibernate.HibernateCallback;

import net.sourceforge.dao.BaseDAOHibernate;
import net.sourceforge.dao.business.pr.YearlyBudgetHistoryDAO;
import net.sourceforge.model.business.pr.YearlyBudget;
import com.shcnc.hibernate.CompositeQuery;

/**
 * hibernate implement of YearlyBudgetHistoryDAO
 * 
 * @author shilei
 * @version 1.0 (Nov 29, 2005)
 */
public class YearlyBudgetHistoryDAOHibernate extends BaseDAOHibernate implements YearlyBudgetHistoryDAO {
    public List listHistory(final YearlyBudget yb) {
        return (List) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                String sql="from YearlyBudgetHistory ybh";
                CompositeQuery query = new CompositeQuery(sql,"ybh.version", session);
                query.createQueryCondition("ybh.yearlyBudget.id=?").appendParameter(yb.getId());
                return query.list();
            }
        });
    }
}
