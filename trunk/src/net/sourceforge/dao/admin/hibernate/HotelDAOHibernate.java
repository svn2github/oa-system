/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.dao.admin.hibernate;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.hibernate.Hibernate;

import net.sf.hibernate.type.Type;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sourceforge.dao.BaseDAOHibernate;
import net.sourceforge.dao.admin.HotelDAO;
import net.sourceforge.dao.convert.LikeConvert;
import net.sourceforge.model.admin.GlobalMailReminder;
import net.sourceforge.model.admin.Hotel;
import net.sourceforge.model.admin.Site;
import net.sourceforge.model.admin.query.HotelQueryCondition;
import net.sourceforge.model.admin.query.HotelQueryOrder;
import net.sourceforge.model.metadata.EnabledDisabled;
import net.sourceforge.model.metadata.HotelPromoteStatus;

/**
 * hibernate implement for HotelDAO
 * @author shilei
 * @version 1.0 (Nov 15, 2005)
 */
public class HotelDAOHibernate extends BaseDAOHibernate implements HotelDAO {
    private Log log = LogFactory.getLog(HotelDAOHibernate.class);


    /* (non-Javadoc)
     * @see net.sourceforge.dao.admin.HotelDAO#getHotel(java.lang.Integer)
     */
    public Hotel getHotel(Integer id){
        if (id == null) {
            if (log.isDebugEnabled()) log.debug("try to get Hotel with null id");
            return null;
        }
        return (Hotel) getHibernateTemplate().get(Hotel.class, id);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.dao.admin.HotelDAO#updateHotel(net.sourceforge.model.admin.Hotel)
     */
    public Hotel updateHotel(final Hotel hotel) {
        Integer id = hotel.getId();
        if (id == null) {
            throw new RuntimeException("cannot save a hotel with null id");
        }
        Hotel oldHotel = getHotel(id);
        if (oldHotel != null) {
        	try{
                PropertyUtils.copyProperties(oldHotel,hotel);
        	}
        	catch(Exception e)
        	{
                throw new RuntimeException("copy error£º"+e);
        	}
        	getHibernateTemplate().update(oldHotel);
        	
        	
        	return oldHotel;
        }
        else
        {
        	throw new RuntimeException("hotel not found");
        }
        
        
    }

	/* (non-Javadoc)
	 * @see net.sourceforge.dao.admin.HotelDAO#insertHotel(net.sourceforge.model.admin.Hotel)
	 */
	public Hotel insertHotel(Hotel hotel) {
       	getHibernateTemplate().save(hotel);
       	return hotel;
    }

    private final static String SQL_COUNT = "select count(*) from Hotel hotel left join hotel.site join hotel.city join hotel.city.province join hotel.city.province.country";

    private final static String SQL = "select hotel from Hotel hotel left join hotel.site join hotel.city join hotel.city.province join hotel.city.province.country";

    private final static Object[][] QUERY_CONDITIONS = {
    	/*id*/    
		{ HotelQueryCondition.ID_EQ, "hotel.id = ?", null },

		/*keyPropertyList*/

		/*kmtoIdList*/

		/*mtoIdList*/
		{ HotelQueryCondition.SITE_ID_EQ, "hotel.site.id = ?", null },
		{ HotelQueryCondition.CURRENCY_CODE_EQ, "hotel.currency.code = ?", null },
		{ HotelQueryCondition.CITY_ID_EQ, "hotel.city.id = ?", null },
        { HotelQueryCondition.PROVINCE_ID_EQ, "hotel.city.province.id = ?", null },
        { HotelQueryCondition.COUNTRY_ID_EQ, "hotel.city.province.country.id = ?", null },

		/*property*/
		{ HotelQueryCondition.NAME_LIKE, "hotel.name like ?", new LikeConvert() },
		{ HotelQueryCondition.ADDRESS_LIKE, "hotel.address like ?", new LikeConvert() },
		{ HotelQueryCondition.DESCRIPTION_LIKE, "hotel.description like ?", new LikeConvert() },
		{ HotelQueryCondition.TELEPHONE_LIKE, "hotel.telephone like ?", new LikeConvert() },
		{ HotelQueryCondition.FAX_LIKE, "hotel.fax like ?", new LikeConvert() },
		{ HotelQueryCondition.LEVEL_EQ, "hotel.level = ?", null },
		{ HotelQueryCondition.CONTRACTSTARTDATE_EQ, "hotel.contractStartDate = ?", null },
		{ HotelQueryCondition.CONTRACTEXPIREDATE_EQ, "hotel.contractExpireDate = ?", null },
		{ HotelQueryCondition.DRAFT_EQ, "hotel.draft = ?", null },
		{ HotelQueryCondition.ENABLED_EQ, "hotel.enabled = ?", null },
		{ HotelQueryCondition.PROMOTESTATUS_NOTEQ, "hotel.promoteStatus <> ?", null },
        { HotelQueryCondition.PROMOTESTATUS_EQ, "hotel.promoteStatus = ?", null },
    };
    
    private final static Object[][] QUERY_ORDERS = {
		/*id*/
        { HotelQueryOrder.ID, "hotel.id" },

		/*property*/
        { HotelQueryOrder.NAME, "hotel.name" },
        { HotelQueryOrder.ADDRESS, "hotel.address" },
        { HotelQueryOrder.DESCRIPTION, "hotel.description" },
        { HotelQueryOrder.TELEPHONE, "hotel.telephone" },
        { HotelQueryOrder.FAX, "hotel.fax" },
        { HotelQueryOrder.LEVEL, "hotel.level" },
        { HotelQueryOrder.CONTRACTSTARTDATE, "hotel.contractStartDate" },
        { HotelQueryOrder.CONTRACTEXPIREDATE, "hotel.contractExpireDate" },
        { HotelQueryOrder.DRAFT, "hotel.draft" },
        { HotelQueryOrder.ENABLED, "hotel.enabled" },
        { HotelQueryOrder.COUNTRY_ENG, "hotel.city.province.country.engName" },
        { HotelQueryOrder.COUNTRY_CHN, "hotel.city.province.country.chnName" },
        { HotelQueryOrder.CITY_ENG, "hotel.city.engName" },
        { HotelQueryOrder.CITY_CHN, "hotel.city.chnName" },
        { HotelQueryOrder.PROVINCE_ENG, "hotel.city.province.engName" },
        { HotelQueryOrder.PROVINCE_CHN, "hotel.city.province.chnName" },
        { HotelQueryOrder.SITE, "hotel.site.name" },
        { HotelQueryOrder.PROMOTESTATUS, "hotel.promoteStatus" },
        
    };
    
    /* (non-Javadoc)
     * @see net.sourceforge.dao.admin.HotelDAO#getHotelListCount(java.util.Map)
     */
    public int getHotelListCount(final Map conditions) {
        return getListCount(conditions, SQL_COUNT, QUERY_CONDITIONS);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.dao.admin.HotelDAO#getHotelList(java.util.Map, int, int, net.sourceforge.model.admin.query.HotelQueryOrder, boolean)
     */
    public List getHotelList(final Map conditions, final int pageNo, final int pageSize, final HotelQueryOrder order, final boolean descend) {
        return getList(conditions, pageNo, pageSize, order, descend, SQL, QUERY_CONDITIONS, QUERY_ORDERS);
    }
    
    public List getHotelMaintainerNotResponsedList(final Site site, final Date time, final GlobalMailReminder gmr) {
        return getHibernateTemplate().find("from Hotel h where h.site.id=? and h.promoteStatus=? and h.promoteDate<=? " +
                "and h.emailTimes<? and (h.emailDate is null or h.emailDate<=?) and h.enabled=?",
                new Object[] {site.getId(),HotelPromoteStatus.REQUEST.getEnumCode(),
                    gmr.getResponseDate(time),new Integer(gmr.getMaxTime()),gmr.getEmailDate(time),
                    EnabledDisabled.ENABLED.getEnumCode()},
                new Type[] { Hibernate.INTEGER , Hibernate.INTEGER, Hibernate.TIMESTAMP , Hibernate.INTEGER ,Hibernate.TIMESTAMP, Hibernate.INTEGER });
    }

}

