/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.dao.business.ta.hibernate;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.type.Type;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate.HibernateCallback;

import net.sourceforge.dao.BaseDAOHibernate;
import net.sourceforge.dao.business.ta.AirTicketDAO;
import net.sourceforge.dao.convert.LikeConvert;
import net.sourceforge.model.admin.Site;
import net.sourceforge.model.business.ta.AirTicket;
import net.sourceforge.model.business.ta.AirTicketRecharge;
import net.sourceforge.model.business.ta.TravelApplication;
import net.sourceforge.model.business.ta.query.AirTicketQueryCondition;
import net.sourceforge.model.business.ta.query.AirTicketQueryOrder;
import net.sourceforge.model.metadata.AirTicketStatus;
import com.shcnc.hibernate.CompositeQuery;

/**
 * hibernate implement for AirTicketDAO
 * @author shi1
 * @version 1.0 (Nov 25, 2005)
 */
public class AirTicketDAOHibernate extends BaseDAOHibernate implements AirTicketDAO {
    private Log log = LogFactory.getLog(AirTicketDAOHibernate.class);

    public AirTicket getAirTicket(Integer id) {
        if (id == null) {
            if (log.isDebugEnabled())
                log.debug("try to get AirTicket with null id");
            return null;
        }
        return (AirTicket) getHibernateTemplate().get(AirTicket.class, id);
    }

    public AirTicket updateAirTicket(AirTicket airTicket) {
        Integer id = airTicket.getId();
        if (id == null) {
            throw new RuntimeException("cannot save a airTicket with null id");
        }
        AirTicket oldAirTicket = getAirTicket(id);
        if (oldAirTicket != null) {
            try {
                PropertyUtils.copyProperties(oldAirTicket, airTicket);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            getHibernateTemplate().update(oldAirTicket);
            return oldAirTicket;
        } else {
            throw new RuntimeException("airTicket not found");
        }
    }

    public AirTicket insertAirTicket(AirTicket airTicket) {
        getHibernateTemplate().save(airTicket);
        return airTicket;
    }

    private final static String SQL_COUNT = "select count(*) from AirTicket airTicket";

    private final static String SQL = "from AirTicket airTicket";

    private final static Object[][] QUERY_CONDITIONS = {
        { AirTicketQueryCondition.TRAVELAPPLICATION_ID_EQ, "airTicket.travelApplication.id = ?", null },
        { AirTicketQueryCondition.STATUS_NOTEQ, "airTicket.status <> ?", null },
        { AirTicketQueryCondition.STATUS_EQ, "airTicket.status = ?", null },
        { AirTicketQueryCondition.TA_SITE_ID_EQ, "airTicket.travelApplication.department.site.id = ?", null },
        { AirTicketQueryCondition.SUPPLIER_NAME_LIKE, "airTicket.supplier.name like ?", new LikeConvert() },
        { AirTicketQueryCondition.TA_DEPERTMENT_ID_EQ, "airTicket.travelApplication.department.id = ?", null},
        { AirTicketQueryCondition.TA_REQUESTOR_NAME_LIKE, "airTicket.travelApplication.requestor.name like ?", new LikeConvert()},
        { AirTicketQueryCondition.DEPART_TIME_LT, "airTicket.departTime >= ?", null},
        { AirTicketQueryCondition.DEPART_TIME_GT, "airTicket.departTime <= ?", null},
        { AirTicketQueryCondition.TA_IS_ON_TRAVEL_EQ, "airTicket.boardingPassRecevied = ?", null},
    };

    private final static Object[][] QUERY_ORDERS = {
        { AirTicketQueryOrder.ID, "airTicket.id" },
        { AirTicketQueryOrder.SUPPLIER, "airTicket.supplier.id" },
    };

    public int getAirTicketListCount(final Map conditions) {
        return getListCount(conditions, SQL_COUNT, QUERY_CONDITIONS);
    }

    public List getAirTicketList(final Map conditions, final int pageNo, final int pageSize, final AirTicketQueryOrder order, final boolean descend) {       
        return getList(conditions, pageNo, pageSize, order, descend, SQL, QUERY_CONDITIONS, QUERY_ORDERS);
    }

    public List getReceivedAirTicketList(final Map conditions) {
        
        return getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                CompositeQuery query = new CompositeQuery("select airTicket from AirTicket airTicket join airTicket.poItem poItem left join airTicket.cancelPoItem cancelPoItem", "airTicket.supplier.id, airTicket.exchangeRate.id", session);
                appendConditions(query, conditions, QUERY_CONDITIONS);
                query.createQueryCondition("not poItem.id is null");
                query.createQueryCondition("poItem.purchaseOrder.id is null or cancelPoItem.purchaseOrder.id is null");
                return query.list();
            }

        });
    }

    public List getSiteReceivedAirTicketPoItemIdList(final Site s) {
        return getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                CompositeQuery query = new CompositeQuery("select poItem.id from AirTicket airTicket join airTicket.poItem poItem", "", session);
                query.createQueryCondition("airTicket.travelApplication.department.site.id = ?").appendParameter(s.getId());
                query.createQueryCondition("poItem.purchaseOrder.id is null");
                List l1 = query.list();
                query = new CompositeQuery("select poItem.id from AirTicket airTicket join airTicket.cancelPoItem poItem", "", session);
                query.createQueryCondition("airTicket.travelApplication.department.site.id = ?").appendParameter(s.getId());
                query.createQueryCondition("poItem.purchaseOrder.id is null");
                List l2 = query.list();
                l1.addAll(l2);
                return l1;
            }

        });
    }

    public void deleteAirTicket(final AirTicket at) {
        final Integer atId = at.getId();
        getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                String sql = "from AirTicketRecharge atr where atr.airTicket.id=?";
                session.delete(sql, atId, Hibernate.INTEGER);
                session.delete(at);
                return null;
            }

        });

    }

    public void insertAirTicketRecharge(AirTicketRecharge atr) {
        this.getHibernateTemplate().save(atr);

    }

    public List getAirTicketRechargeList(AirTicket at) {
        return getHibernateTemplate().find("from AirTicketRecharge atr where atr.airTicket.id = ?", at.getId(), Hibernate.INTEGER);
    }

    public AirTicket getAirTicketByTravelApplication(TravelApplication travelApplication) {
        String sql="from AirTicket art where art.travelApplication.id=? and art.status<>? "; 
        
        List l= this.getHibernateTemplate().find(sql,new Object[]{travelApplication.getId(),AirTicketStatus.CANCELLED.getEnumCode()},
                new Type[]{Hibernate.STRING,Hibernate.INTEGER});
        if(l.size()==1) return (AirTicket) l.get(0);
        return null;
    }

    public void deleteAirTicket(Integer id) {
        AirTicket at=this.getAirTicket(id);
        this.deleteAirTicket(at);
        
    }

}
