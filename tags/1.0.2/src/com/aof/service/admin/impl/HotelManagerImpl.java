/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.service.admin.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.aof.dao.admin.ExchangeRateDAO;
import com.aof.dao.admin.HotelDAO;
import com.aof.dao.admin.PriceDAO;
import com.aof.model.admin.City;
import com.aof.model.admin.Currency;
import com.aof.model.admin.ExchangeRate;
import com.aof.model.admin.Hotel;
import com.aof.model.admin.Price;
import com.aof.model.admin.Site;
import com.aof.model.admin.TravelGroupDetail;
import com.aof.model.admin.query.HotelQueryCondition;
import com.aof.model.admin.query.HotelQueryOrder;
import com.aof.model.admin.query.PriceQueryCondition;
import com.aof.model.admin.query.PriceQueryOrder;
import com.aof.model.metadata.EnabledDisabled;
import com.aof.model.metadata.HotelPromoteStatus;
import com.aof.service.BaseManager;
import com.aof.service.admin.HotelManager;

/**
 * implement for HotelManager
 * @author shilei
 * @version 1.0 (Nov 15, 2005)
 */
public class HotelManagerImpl extends BaseManager implements HotelManager {
	// private Log log = LogFactory.getLog(HotelManagerImpl.class);

	private HotelDAO dao;

	private PriceDAO priceDao;

	private ExchangeRateDAO exchangeRateDao;

	/**
	 * @param dao
	 */
	public void setHotelDAO(HotelDAO dao) {
		this.dao = dao;
	}

	/* (non-Javadoc)
	 * @see com.aof.service.admin.HotelManager#getHotel(java.lang.Integer)
	 */
	public Hotel getHotel(Integer id) {
		return dao.getHotel(id);
	}

	/* (non-Javadoc)
	 * @see com.aof.service.admin.HotelManager#updateHotel(com.aof.model.admin.Hotel)
	 */
	public Hotel updateHotel(Hotel hotel) {
		return dao.updateHotel(hotel);
	}

	/* (non-Javadoc)
	 * @see com.aof.service.admin.HotelManager#insertHotel(com.aof.model.admin.Hotel)
	 */
	public Hotel insertHotel(Hotel hotel) {
		if (hotel.getSite() == null)
			hotel.setPromoteStatus(HotelPromoteStatus.GLOBAL);
		else
			hotel.setPromoteStatus(HotelPromoteStatus.SITE);
		return dao.insertHotel(hotel);
	}

	/* (non-Javadoc)
	 * @see com.aof.service.admin.HotelManager#getHotelListCount(java.util.Map)
	 */
	public int getHotelListCount(Map conditions) {
		return dao.getHotelListCount(conditions);
	}

	/* (non-Javadoc)
	 * @see com.aof.service.admin.HotelManager#getHotelList(java.util.Map, int, int, com.aof.model.admin.query.HotelQueryOrder, boolean)
	 */
	public List getHotelList(Map conditions, int pageNo, int pageSize,
			HotelQueryOrder order, boolean descend) {
		return dao.getHotelList(conditions, pageNo, pageSize, order, descend);
	}

	/* (non-Javadoc)
	 * @see com.aof.service.admin.HotelManager#reponsePromote(java.lang.Integer)
	 */
	public Hotel reponsePromote(Integer id) {
		Hotel h = this.getHotel(id);
		if (!h.getPromoteStatus().equals(HotelPromoteStatus.REQUEST)) {
			throw new RuntimeException("Promote Status error");
		}
        //h.setSite(null);
		h.setPromoteStatus(HotelPromoteStatus.GLOBAL);
        h.setPromoteDate(new Date());
		this.updateHotel(h);
		return h;
	}


	/* (non-Javadoc)
	 * @see com.aof.service.admin.HotelManager#requestPromote(java.lang.Integer, java.lang.String)
	 */
	public Hotel requestPromote(Integer id, String promoteMessage) {
		Hotel h = this.getHotel(id);

		if (!h.getPromoteStatus().equals(HotelPromoteStatus.SITE)) {
			throw new RuntimeException("Promote Status error");
		}
		h.setPromoteMessage(promoteMessage);
        h.setPromoteDate(new Date());
		h.setPromoteStatus(HotelPromoteStatus.REQUEST);
		this.updateHotel(h);
		return h;
	}

	/* (non-Javadoc)
	 * @see com.aof.service.admin.HotelManager#getEnabledHotelRoomList(com.aof.model.admin.City, boolean, com.aof.model.admin.TravelGroupDetail)
	 */
	public List getEnabledHotelRoomList(City city, boolean isLocal,
			TravelGroupDetail tgd) {
		List hotelList = this.getEnabledHotelList(city);
		List roomList = this.getEnabledRoomList(city, isLocal, tgd);
		Set hasRoomHotelSet = new HashSet();
		for (Iterator iter = roomList.iterator(); iter.hasNext();) {
			Price room = (Price) iter.next();
			if (room.getHotel().getRooms() == null) {
				room.getHotel().setRooms(new ArrayList());
			}
			room.getHotel().getRooms().add(room);
			hasRoomHotelSet.add(room.getHotel());
		}
		hotelList.retainAll(hasRoomHotelSet);
		return hotelList;
	}

	private List getEnabledRoomList(City city, boolean isLocal,
			TravelGroupDetail tgd) {

		Site site = null;
		BigDecimal limit = null;
		if (tgd != null) {
			if (isLocal) {

				limit = tgd.getAmountLimit();
			} else {
				limit = tgd.getAbroadAmountLimit();
			}
		}

		boolean hasLimit = limit != null
				&& !limit.equals(BigDecimal.valueOf(0));
		if(hasLimit)
			site = tgd.getTravelGroup().getSite();

		Map conds = new HashMap();

		conds.put(PriceQueryCondition.HOTEL_CITY_ID_EQ, city.getId());
		conds.put(PriceQueryCondition.ENABLED_EQ, EnabledDisabled.ENABLED
				.getEnumCode());
		List roomList = priceDao.getPriceList(conds, 0, -1,
				PriceQueryOrder.PRICE, false);
		List retVal = new ArrayList();
		for (Iterator iter = roomList.iterator(); iter.hasNext();) {
			Price room = (Price) iter.next();
			
			if (hasLimit) {
				Currency hotelCurrency = room.getHotel().getCurrency();
				BigDecimal price = room.getPrice();
                BigDecimal discount=new BigDecimal(1d+room.getDiscount().intValue()/100d);
                price=price.multiply(discount);
				if (this.convert(price, site, hotelCurrency).compareTo(limit) < 0) {
					retVal.add(room);
				} else {
					break;
				}
			} else {
				retVal.add(room);
			}
		}
		return retVal;
	}

	private BigDecimal convert(BigDecimal price, Site site,
			Currency hotelCurrency) {
		if (site.getBaseCurrency().equals(hotelCurrency)) {
			return price;
		}
		ExchangeRate er = exchangeRateDao.getExchangeRate(hotelCurrency, site);
		if (er == null) {
			throw new RuntimeException("currency not found!");
		}
		return price.multiply(er.getExchangeRate());
	}

	private List getEnabledHotelList(City city) {
		Map conds = new HashMap();
		conds.put(HotelQueryCondition.CITY_ID_EQ, city.getId());
		conds.put(HotelQueryCondition.ENABLED_EQ, EnabledDisabled.ENABLED
				.getEnumCode());
		return dao.getHotelList(conds, 0, -1, HotelQueryOrder.LEVEL, true);
	}

	/**
	 * @param priceDao
	 */
	public void setPriceDAO(PriceDAO priceDao) {
		this.priceDao = priceDao;
	}

	/**
	 * @param eDao
	 */
	public void setExchangeRateDAO(ExchangeRateDAO eDao) {
		this.exchangeRateDao=eDao;
	}

    public List getEnabledHotelRoomList(City city) {
        List hotelList = this.getEnabledHotelList(city);
        List roomList = this.getEnabledRoomList(city);
        Set hasRoomHotelSet = new HashSet();
        for (Iterator iter = roomList.iterator(); iter.hasNext();) {
            Price room = (Price) iter.next();
            if (room.getHotel().getRooms() == null) {
                room.getHotel().setRooms(new ArrayList());
            }
            room.getHotel().getRooms().add(room);
            hasRoomHotelSet.add(room.getHotel());
        }
        hotelList.retainAll(hasRoomHotelSet);
        return hotelList;
    }

    private List getEnabledRoomList(City city) {
        Map conds = new HashMap();
        conds.put(PriceQueryCondition.HOTEL_CITY_ID_EQ, city.getId());
        conds.put(PriceQueryCondition.ENABLED_EQ, EnabledDisabled.ENABLED
                .getEnumCode());
        List roomList = priceDao.getPriceList(conds, 0, -1,
                PriceQueryOrder.PRICE, false);
        return roomList;
    }

}
