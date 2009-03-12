/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.dao.business.ta.hibernate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.LockMode;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.type.Type;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.orm.hibernate.HibernateCallback;

import net.sourceforge.dao.BaseDAOHibernate;
import net.sourceforge.dao.business.ta.TravelApplicationDAO;
import net.sourceforge.dao.convert.LikeConvert;
import net.sourceforge.dao.convert.QueryParameterConvert;
import net.sourceforge.dao.convert.QuerySQLConvert;
import net.sourceforge.model.admin.SiteMailReminder;
import net.sourceforge.model.business.ta.AirTicket;
import net.sourceforge.model.business.ta.AirTicketRecharge;
import net.sourceforge.model.business.ta.TravelApplication;
import net.sourceforge.model.business.ta.TravelApplicationApproveRequest;
import net.sourceforge.model.business.ta.TravelApplicationHistory;
import net.sourceforge.model.business.ta.query.TravelApplicationQueryCondition;
import net.sourceforge.model.business.ta.query.TravelApplicationQueryOrder;
import net.sourceforge.model.metadata.AirTicketStatus;
import net.sourceforge.model.metadata.TravelApplicationBookStatus;
import net.sourceforge.model.metadata.TravelApplicationStatus;
import net.sourceforge.model.metadata.TravelApplicationUrgent;
import net.sourceforge.model.metadata.YesNo;

/**
 * hibernate implement for TravelApplicationDAO
 * @author shilei
 * @version 1.0 (Nov 15, 2005)
 */
public class TravelApplicationDAOHibernate extends BaseDAOHibernate implements TravelApplicationDAO {
    public TravelApplication getTravelApplication(String id){
        return (TravelApplication) getHibernateTemplate().get(TravelApplication.class, id);
    }

    public TravelApplication updateTravelApplication(TravelApplication travelApplication) {
        String id = travelApplication.getId();
        if (id == null) {
            throw new RuntimeException("cannot save a travelApplication with null id");
        }
        TravelApplication oldTravelApplication = getTravelApplication(id);
        if (oldTravelApplication != null) {
            try{
                PropertyUtils.copyProperties(oldTravelApplication,travelApplication);
            }
            catch(Exception e)
            {
                throw new RuntimeException(e);
            }
            if (oldTravelApplication.getIsOnTravel() == null) {
                oldTravelApplication.setIsOnTravel(YesNo.NO);
            }
            getHibernateTemplate().update(oldTravelApplication);    
            return oldTravelApplication;
        }
        else
        {
            throw new RuntimeException("travelApplication not found");
        }
    }

    public TravelApplication insertTravelApplication(TravelApplication travelApplication) {
        if (travelApplication.getIsOnTravel() == null) {
            travelApplication.setIsOnTravel(YesNo.NO);
        }
        getHibernateTemplate().save(travelApplication);
        return travelApplication;
    }

    private final static String SQL_COUNT = "select count(*) from TravelApplication travelApplication join travelApplication.requestor join travelApplication.toCity join travelApplication.toCity.province join travelApplication.toCity.province.country left join travelApplication.booker";

    private final static String SQL = "select travelApplication from TravelApplication travelApplication join travelApplication.requestor join travelApplication.toCity join travelApplication.toCity.province join travelApplication.toCity.province.country left join travelApplication.booker ";

    private final static Object[][] QUERY_CONDITIONS = {
        { TravelApplicationQueryCondition.FROMDATE_TODATE_LT, "(travelApplication.fromDate <= ? or travelApplication.toDate <= ?)", null },
        { TravelApplicationQueryCondition.FROMDATE_TODATE_GT, "(travelApplication.fromDate >= ? or travelApplication.toDate >= ?)", null },
        { TravelApplicationQueryCondition.BOOKER_NULL_OR_ID_EQ,
            "((travelApplication.booker is null) or (travelApplication.booker.id = ?) )", null },
        { TravelApplicationQueryCondition.STATUS_EQ_OR_URGENT_EQ,
            "((travelApplication.status =? ) or (travelApplication.urgent = ?) )", null },    

        /*id*/    
        { TravelApplicationQueryCondition.ID_EQ, "travelApplication.id = ?", null },
        { TravelApplicationQueryCondition.ID_BEGINWITH, "travelApplication.id like ?", new QueryParameterConvert(){
            public Object convert(Object src) {
                return src+"%";
            }} },

        /*keyPropertyList*/

        /*kmtoIdList*/

        /*mtoIdList*/
        { TravelApplicationQueryCondition.DEPARTMENT_ID_EQ, "travelApplication.department.id = ?", null },
        { TravelApplicationQueryCondition.DEPARTMENT_ID_IN, "travelApplication.department.id in ", new QuerySQLConvert() {
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
        { TravelApplicationQueryCondition.SITE_ID_EQ, "travelApplication.department.site.id = ?", null },
        
        { TravelApplicationQueryCondition.FROMCITY_ID_EQ, "travelApplication.fromCity.id = ?", null },
        { TravelApplicationQueryCondition.TOCITY_ID_EQ, "travelApplication.toCity.id = ?", null },
        { TravelApplicationQueryCondition.HOTEL_ID_EQ, "travelApplication.hotel.id = ?", null },
        { TravelApplicationQueryCondition.PRICE_ID_EQ, "travelApplication.price.id = ?", null },
        { TravelApplicationQueryCondition.REQUESTOR_ID_EQ, "travelApplication.requestor.id = ?", null },
        { TravelApplicationQueryCondition.REQUESTOR_ID_NOT_EQ, "travelApplication.requestor.id <> ?", null },
        { TravelApplicationQueryCondition.BOOKER_ID_EQ, "travelApplication.booker.id = ?", null },

        /*property*/
        { TravelApplicationQueryCondition.ID_LIKE, "travelApplication.id like ?", new LikeConvert() },
        { TravelApplicationQueryCondition.TITLE_LIKE, "travelApplication.title like ?", new LikeConvert() },
        { TravelApplicationQueryCondition.DESCRIPTION_LIKE, "travelApplication.description like ?", new LikeConvert() },
        { TravelApplicationQueryCondition.STATUS_EQ, "travelApplication.status = ?", null },
        { TravelApplicationQueryCondition.HOTELNAME_LIKE, "travelApplication.hotelName like ?", new LikeConvert() },
        { TravelApplicationQueryCondition.ROOMDESCRIPTION_LIKE, "travelApplication.roomDescription like ?", new LikeConvert() },
        { TravelApplicationQueryCondition.CHECKINDATE_EQ, "travelApplication.checkInDate = ?", null },
        { TravelApplicationQueryCondition.CHECKOUTDATE_EQ, "travelApplication.checkOutDate = ?", null },
        { TravelApplicationQueryCondition.TRAVELLINGMODE_EQ, "travelApplication.travellingMode = ?", null },
        { TravelApplicationQueryCondition.SINGLEORRETURN_EQ, "travelApplication.singleOrReturn = ?", null },
        { TravelApplicationQueryCondition.FROMDATE_EQ, "travelApplication.fromDate = ?", null },
        { TravelApplicationQueryCondition.FROMTIME_EQ, "travelApplication.fromTime = ?", null },
        { TravelApplicationQueryCondition.TODATE_EQ, "travelApplication.toDate = ?", null },
        { TravelApplicationQueryCondition.TOTIME_EQ, "travelApplication.toTime = ?", null },
        { TravelApplicationQueryCondition.REQUESTDATE_EQ, "travelApplication.requestDate = ?", null },
        { TravelApplicationQueryCondition.APPROVEREQUESTID_EQ, "travelApplication.approveRequestId = ?", null },
        { TravelApplicationQueryCondition.BOOKSTATUS_EQ, "travelApplication.bookStatus = ?", null },
        { TravelApplicationQueryCondition.URGENT_EQ, "travelApplication.urgent = ?", null },
        { TravelApplicationQueryCondition.BOOKSTATUS_NOT_EQ, "travelApplication.bookStatus <> ?" ,null},
        
        
        /*other*/
        { TravelApplicationQueryCondition.REQUESTDATE_GE, "travelApplication.requestDate >= ?" ,null},
        { TravelApplicationQueryCondition.REQUESTDATE_LT, "travelApplication.requestDate < ?" ,null},

        
        { TravelApplicationQueryCondition.FROMDATE_GE, "travelApplication.fromDate >= ?" ,null},
        { TravelApplicationQueryCondition.FROMDATE_LT, "travelApplication.fromDate < ?" ,null},
        { TravelApplicationQueryCondition.TODATE_GE, "travelApplication.toDate >= ?" ,null},
        { TravelApplicationQueryCondition.TODATE_LT, "travelApplication.toDate < ?" ,null},
        { TravelApplicationQueryCondition.TOCOUNTRY_ID_EQ, "travelApplication.toCity.province.country.id = ?", null },
        { TravelApplicationQueryCondition.TOPROVINCE_ID_EQ, "travelApplication.toCity.province.id = ?", null },
        { TravelApplicationQueryCondition.REQUESTOR_NAME_LIKE, "travelApplication.requestor.name like ?", new LikeConvert() },
        { TravelApplicationQueryCondition.CREATEDATE_GE, "travelApplication.createDate >= ?" ,null},
        { TravelApplicationQueryCondition.CREATEDATE_LE, "travelApplication.createDate <= ?" ,null},
        { TravelApplicationQueryCondition.CREATEDATE_LT, "travelApplication.createDate < ?" ,null},
        { TravelApplicationQueryCondition.CREATOR_ID_EQ, "travelApplication.creator.id = ?" ,null},
        { TravelApplicationQueryCondition.APPROVEDATE_GE, "travelApplication.approveDate >= ?" ,null},
        { TravelApplicationQueryCondition.APPROVEDATE_LE, "travelApplication.approveDate <= ?" ,null},
        
        
    };
    
    private final static Object[][] QUERY_ORDERS = {
        /*id*/
        { TravelApplicationQueryOrder.ID, "travelApplication.id" },

        /*property*/
        { TravelApplicationQueryOrder.TITLE, "travelApplication.title" },
        { TravelApplicationQueryOrder.DESCRIPTION, "travelApplication.description" },
        { TravelApplicationQueryOrder.STATUS, "travelApplication.status" },
        { TravelApplicationQueryOrder.HOTELNAME, "travelApplication.hotelName" },
        { TravelApplicationQueryOrder.ROOMDESCRIPTION, "travelApplication.roomDescription" },
        { TravelApplicationQueryOrder.CHECKINDATE, "travelApplication.checkInDate" },
        { TravelApplicationQueryOrder.CHECKOUTDATE, "travelApplication.checkOutDate" },
        { TravelApplicationQueryOrder.TRAVELLINGMODE, "travelApplication.travellingMode" },
        { TravelApplicationQueryOrder.SINGLEORRETURN, "travelApplication.singleOrReturn" },
        { TravelApplicationQueryOrder.FROMDATE, "travelApplication.fromDate" },
        { TravelApplicationQueryOrder.FROMTIME, "travelApplication.fromTime" },
        { TravelApplicationQueryOrder.TODATE, "travelApplication.toDate" },
        { TravelApplicationQueryOrder.TOTIME, "travelApplication.toTime" },
        { TravelApplicationQueryOrder.REQUESTDATE, "travelApplication.requestDate" },
        { TravelApplicationQueryOrder.APPROVEREQUESTID, "travelApplication.approveRequestId" },
        { TravelApplicationQueryOrder.CREATOR, "travelApplication.creator.name" },
        { TravelApplicationQueryOrder.REQUESTOR, "travelApplication.requestor.name" },
        { TravelApplicationQueryOrder.BOOKER, "travelApplication.booker.name" },
        { TravelApplicationQueryOrder.DEPARTMENT, "travelApplication.department.name" },
        { TravelApplicationQueryOrder.TOCITY_ENG, "travelApplication.toCity.engName" },
        { TravelApplicationQueryOrder.TOCITY_CHN, "travelApplication.toCity.chnName" },
        { TravelApplicationQueryOrder.BOOKSTATUS, "travelApplication.bookStatus" },
        { TravelApplicationQueryOrder.URGENT, "travelApplication.urgent" },
        
        
    };
    
    public int getTravelApplicationListCount(final Map conditions) {
        return getListCount(conditions, SQL_COUNT, QUERY_CONDITIONS);
    }

    public List getTravelApplicationList(final Map conditions, final int pageNo, final int pageSize, final TravelApplicationQueryOrder order, final boolean descend) {
        return getList(conditions, pageNo, pageSize, order, descend, SQL, QUERY_CONDITIONS, QUERY_ORDERS);
    }

    public String getLastTravelApplicationCode() {
        String result=(String)getHibernateTemplate().find(
        "select max(ta.id) from TravelApplication ta where ta.id like 'TA%'").get(0);
            return (result==null?"TA000000":result+"000000").substring(0,8);
    }

    
    public TravelApplication getTravelApplicationByApproveRequestId(String approveRequestId) {
        Map condition = new HashMap();
        condition.put(TravelApplicationQueryCondition.APPROVEREQUESTID_EQ,approveRequestId);
        List result=getTravelApplicationList(condition , 0 , -1 , null , false);
        if (result.size()>0)
            return (TravelApplication)result.get(0);
        else 
            return null;
    }

    public void saveTravelApplicationApproveRequst(TravelApplicationApproveRequest taar) {
        this.getHibernateTemplate().save(taar);
        
    }

    public void deleteTravelApplication(TravelApplication ta) {
        String sql1="from TravelApplicationHistory tah where tah.travelApplication.id=?";
        String sql2="from AirTicket art where art.travelApplication.id=?";
        this.getHibernateTemplate().delete(sql1,ta.getId(),Hibernate.STRING);
        this.getHibernateTemplate().delete(sql2,ta.getId(),Hibernate.STRING);
        ta = this.getTravelApplication(ta.getId());
        this.getHibernateTemplate().delete(ta);
        
    }

    public void saveTravelApplicationHistory(TravelApplicationHistory taHistory) {
        this.getHibernateTemplate().save(taHistory);
        
    }

    public String getMaxTravelApplicationIdBeginWith(final String prefix) {
        String sql="select max(travelApplication.id) from TravelApplication travelApplication";
        Map conds=new HashMap();
        conds.put(TravelApplicationQueryCondition.ID_BEGINWITH,prefix);
        List l=getList(conds, 0, -1, null, false, sql, QUERY_CONDITIONS, QUERY_ORDERS);
        if(l.isEmpty()) return null;
        else return (String) l.get(0);
    }

    public void deleteTravelApplicationApproveRequest(TravelApplication ta) {
        getHibernateTemplate().delete(
                "from TravelApplicationApproveRequest ear where ear.approveRequestId=?",
                ta.getApproveRequestId(),Hibernate.STRING);
    }

    public List getReceptionNotRespondedTravelApplication(final Date time, final SiteMailReminder smr) {
        return (List) getHibernateTemplate().execute( new HibernateCallback() {
            
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query q=session.createQuery(
                        "from TravelApplication ta where "+
                        "((ta.status=? and ta.urgent=?)or((ta.status=? or ta.status=?) and ta.urgent=?)) "+
                        "and (ta.bookStatus is null or ta.bookStatus =?) "+
                        "and ta.booker is null "+
                        "and ta.approveDate<=? and ta.emailTimes<? and (ta.emailDate is null or ta.emailDate<=?) "+
                        "and ta.department.site.id=?");
                int i=0;
                q.setParameter(i++,TravelApplicationStatus.APPROVED);
                q.setParameter(i++,TravelApplicationUrgent.NORMAL);
                
                q.setParameter(i++,TravelApplicationStatus.PENDING);
                q.setParameter(i++,TravelApplicationStatus.APPROVED);
                q.setParameter(i++,TravelApplicationUrgent.URGENT);
                
                q.setParameter(i++,TravelApplicationBookStatus.NA);
                
                q.setTimestamp(i++,smr.getApproveDate(time));
                q.setInteger(i++,smr.getMaxTime());
                q.setTimestamp(i++,smr.getEmailDate(time));
                q.setParameter(i++,smr.getSite().getId());
                return q.list();
            }
        });
    }

    public List getAirTicketListWithDetails(TravelApplication ta) {
        List artList = this.getAirTicketList(ta);
        for (Iterator iter = artList.iterator(); iter.hasNext();) {
            AirTicket art = (AirTicket) iter.next();
            art.setRecharges(new ArrayList());
        }

        String sql = "from AirTicketRecharge artr where artr.airTicket.travelApplication.id = ? order by artr.id";
        List airticketRechargeList = getHibernateTemplate().find(sql, ta.getId(), Hibernate.STRING);
        for (Iterator iter = airticketRechargeList.iterator(); iter.hasNext();) {
            AirTicketRecharge artr = (AirTicketRecharge) iter.next();
            AirTicket art = artr.getAirTicket();
            art.getRecharges().add(artr);
        }

        return artList;
    }

    public List getAirTicketList(TravelApplication ta) {
        return this.getHibernateTemplate().find("select from AirTicket art where art.travelApplication.id=? order by art.id",ta.getId(),Hibernate.STRING);
        
    }

    public AirTicket getAirTicketWithDetails(Integer id) {
        AirTicket at=(AirTicket) this.getHibernateTemplate().get(AirTicket.class,id);
        String sql = "from AirTicketRecharge artr where artr.airTicket.id = ? order by artr.id";
        List airticketRechargeList = getHibernateTemplate().find(sql, id, Hibernate.INTEGER);
        at.setRecharges(airticketRechargeList);
        return at;
    }

    public List getAirTicketIdList(TravelApplication ta) {
        String sql = "select air.id from AirTicket air where air.travelApplication.id = ?";
        return this.getHibernateTemplate().find(sql,ta.getId(),Hibernate.STRING);
    }

    public List getBookedAirTicketIdList(TravelApplication ta) {
        String sql = "select air.id from AirTicket air where air.travelApplication.id = ? and air.status=?";
        return this.getHibernateTemplate().find(sql,
                new Object[]{ta.getId(),AirTicketStatus.BOOKED.getEnumCode()},
                new Type[]{Hibernate.STRING,Hibernate.INTEGER});

    }

    public boolean hasBookedAirTicket(TravelApplication ta) {
        String sql = "select air.id from AirTicket air where air.travelApplication.id = ? and air.status=?";
        return this.getHibernateTemplate().iterate(sql,
                new Object[]{ta.getId(),AirTicketStatus.BOOKED.getEnumCode()},
                new Type[]{Hibernate.STRING,Hibernate.INTEGER}).hasNext();
        
    }

    public boolean hasReceivedAirTicket(TravelApplication ta) {
        String sql = "select air.id from AirTicket air where air.travelApplication.id = ? and air.status=?";
        return this.getHibernateTemplate().iterate(sql,
                new Object[]{ta.getId(),AirTicketStatus.RECEIVED.getEnumCode()},
                new Type[]{Hibernate.STRING,Hibernate.INTEGER}).hasNext();
    }

    public void lockTravelApplication(TravelApplication ta) {
        this.getHibernateTemplate().lock(ta,LockMode.UPGRADE);
        
    }

    
}

