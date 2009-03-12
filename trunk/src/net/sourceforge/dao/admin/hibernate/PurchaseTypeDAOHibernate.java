package net.sourceforge.dao.admin.hibernate;

import java.sql.SQLException;
import java.util.List;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;

import org.springframework.orm.hibernate.HibernateCallback;

import net.sourceforge.dao.BaseDAOHibernate;
import net.sourceforge.dao.admin.PurchaseTypeDAO;
import net.sourceforge.model.admin.PurchaseType;
import net.sourceforge.model.admin.Site;
import net.sourceforge.model.metadata.EnabledDisabled;
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
