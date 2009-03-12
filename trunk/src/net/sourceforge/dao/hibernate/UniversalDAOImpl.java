package net.sourceforge.dao.hibernate;

import java.io.Serializable;
import java.sql.SQLException;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.SessionFactory;
import net.sf.hibernate.metadata.ClassMetadata;

import org.springframework.orm.hibernate.HibernateCallback;

import net.sourceforge.dao.BaseDAOHibernate;
import net.sourceforge.dao.UniversalDAO;

public class UniversalDAOImpl extends BaseDAOHibernate implements UniversalDAO {

    public Object load(Class clazz, Serializable idValue) {
        return getHibernateTemplate().get(clazz, idValue);
    }

    public void refresh(final Object object) {
        getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                session.refresh(object);
                if (!session.contains(object)) {
                    Class clazz = object.getClass();
                    SessionFactory sf = session.getSessionFactory();
                    ClassMetadata cm = sf.getClassMetadata(clazz);
                    Serializable id = null;
                    if (cm.hasIdentifierProperty()) {
                        id = cm.getIdentifier(object);
                    } else {
                        id = (Serializable) object;
                    }
                    Object objInSession = session.get(clazz, id);
                    session.flush();
                    session.evict(objInSession);
                    session.load(object, id);
                }
                return object;
            }
            
        });
    }
}
