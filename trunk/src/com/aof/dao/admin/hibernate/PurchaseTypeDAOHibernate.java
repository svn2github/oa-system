package com.aof.dao.admin.hibernate;

import java.sql.SQLException;
import java.util.List;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;

import org.springframework.orm.hibernate.HibernateCallback;

import com.aof.dao.BaseDAOHibernate;
import com.aof.dao.admin.PurchaseTypeDAO;
import com.aof.model.admin.PurchaseType;
import com.aof.model.admin.Site;
import com.aof.model.metadata.EnabledDisabled;
import com.shcnc.hibernate.CompositeQuery;

public class PurchaseTypeDAOHibernate extends BaseDAOHibernate implements PurchaseTypeDAO {

    public PurchaseType getPurchaseType(String code) {

        return (PurchaseType) this.getHibernateTemplate().get(PurchaseType.class, code);
    }

    public List getEnabledPurchaseTypeList(final Site site) {
        return (List) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                String sql = "from PurchaseType pt";
                CompositeQuery query = new CompositeQuery(sql, "pt.description", session);
                query.createQueryCondition("pt.enabled=?").appendParameter(EnabledDisabled.ENABLED.getEnumCode());
                query.createQueryCondition("pt.site.id=?").appendParameter(site.getId());
                return query.list();
            }
        });

    }

}
