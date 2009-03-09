/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.service.admin.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.aof.dao.admin.TravelGroupDAO;
import com.aof.dao.admin.TravelGroupDetailDAO;
import com.aof.model.admin.ExpenseSubCategory;
import com.aof.model.admin.TravelGroup;
import com.aof.model.admin.TravelGroupDetail;
import com.aof.model.admin.query.TravelGroupDetailQueryCondition;
import com.aof.model.admin.query.TravelGroupQueryCondition;
import com.aof.model.admin.query.TravelGroupQueryOrder;
import com.aof.model.metadata.EnabledDisabled;
import com.aof.model.metadata.YesNo;
import com.aof.service.BaseManager;
import com.aof.service.admin.TravelGroupManager;

/**
 * implement for TravelGroupManager
 * @author shilei
 * @version 1.0 (Nov 15, 2005)
 */
public class TravelGroupManagerImpl extends BaseManager implements TravelGroupManager {
    private TravelGroupDAO dao;
    private TravelGroupDetailDAO travelGroupDetailDAO;
    
    /**
     * @return
     */
    public TravelGroupDetailDAO getTravelGroupDetailDAO() {
		return travelGroupDetailDAO;
	}

	/**
	 * @param travelGroupDetailDAO
	 */
	public void setTravelGroupDetailDAO(TravelGroupDetailDAO travelGroupDetailDAO) {
		this.travelGroupDetailDAO = travelGroupDetailDAO;
	}

	/**
	 * @param dao
	 */
	public void setTravelGroupDAO(TravelGroupDAO dao) {
        this.dao = dao;
    }
    
     /* (non-Javadoc)
     * @see com.aof.service.admin.TravelGroupManager#getTravelGroup(java.lang.Integer)
     */
    public TravelGroup getTravelGroup(Integer id)  {
        return dao.getTravelGroup(id);
    }

    /* (non-Javadoc)
     * @see com.aof.service.admin.TravelGroupManager#updateTravelGroup(com.aof.model.admin.TravelGroup)
     */
    public TravelGroup updateTravelGroup(TravelGroup function)  {
        return dao.updateTravelGroup(function);
    }
    
    /* (non-Javadoc)
     * @see com.aof.service.admin.TravelGroupManager#insertTravelGroup(com.aof.model.admin.TravelGroup)
     */
    public TravelGroup insertTravelGroup(TravelGroup function)  {
        return dao.insertTravelGroup(function);
    }
    

    /* (non-Javadoc)
     * @see com.aof.service.admin.TravelGroupManager#getTravelGroupListCount(java.util.Map)
     */
    public int getTravelGroupListCount(Map conditions)  {
        return dao.getTravelGroupListCount(conditions);
    }

    /* (non-Javadoc)
     * @see com.aof.service.admin.TravelGroupManager#getTravelGroupList(java.util.Map, int, int, com.aof.model.admin.query.TravelGroupQueryOrder, boolean)
     */
    public List getTravelGroupList(Map conditions, int pageNo, int pageSize, TravelGroupQueryOrder order, boolean descend)  {
        return dao.getTravelGroupList(conditions, pageNo, pageSize, order, descend);
    }

	/* (non-Javadoc)
	 * @see com.aof.service.admin.TravelGroupManager#insertTravelGroup(com.aof.model.admin.TravelGroup, java.util.List)
	 */
	public void insertTravelGroup(TravelGroup travelGroup, List detailList)  {
		insertTravelGroup(travelGroup);
		for (Iterator iter = detailList.iterator(); iter.hasNext();) {
			TravelGroupDetail tgd = (TravelGroupDetail) iter.next();
			travelGroupDetailDAO.insertTravelGroupDetail(tgd);
		}
	}

	/* (non-Javadoc)
	 * @see com.aof.service.admin.TravelGroupManager#getDetailOf(com.aof.model.admin.TravelGroup)
	 */
	public List getDetailOf(TravelGroup travelGroup)  {
		Map conds=new HashMap();
		conds.put(TravelGroupDetailQueryCondition.TRAVELGROUP_ID_EQ,travelGroup.getId());
		return travelGroupDetailDAO.getTravelGroupDetailList(conds,0,-1,null,false);
	}

	/* (non-Javadoc)
	 * @see com.aof.service.admin.TravelGroupManager#updateTravelGroup(com.aof.model.admin.TravelGroup, java.util.List)
	 */
	public void updateTravelGroup(TravelGroup travelGroup, List detailList)  {
		updateTravelGroup(travelGroup);
		for (Iterator iter = detailList.iterator(); iter.hasNext();) {
			TravelGroupDetail tgd = (TravelGroupDetail) iter.next();
			travelGroupDetailDAO.insertOrUpdateTravelGroupDetail(tgd);
		}
		
	}

    /* (non-Javadoc)
     * @see com.aof.service.admin.TravelGroupManager#getAllEnabledTravelGroupListBySiteId(java.lang.Integer)
     */
    public List getAllEnabledTravelGroupListBySiteId(Integer siteId) {
        Map conditions = new HashMap();
        conditions.put(TravelGroupQueryCondition.ENABLED_EQ, EnabledDisabled.ENABLED.getEnumCode());
        conditions.put(TravelGroupQueryCondition.SITE_ID_EQ, siteId);
        return dao.getTravelGroupList(conditions, 0, -1, TravelGroupQueryOrder.NAME, false);
    }

    /* (non-Javadoc)
     * @see com.aof.service.admin.TravelGroupManager#getAllEnabledTravelGroupListBySiteIdAndIncludeThis(java.lang.Integer, java.lang.Integer)
     */
    public List getAllEnabledTravelGroupListBySiteIdAndIncludeThis(Integer siteId, Integer id) {
        TravelGroup tg = dao.getTravelGroup(id);
        List l = getAllEnabledTravelGroupListBySiteId(siteId);
        if (tg == null) return l;
        if (l.contains(tg)) return l;
        for (int i = 0; i < l.size(); i++) {
            TravelGroup tg2 = (TravelGroup)l.get(i);
            if (tg2.getName().compareTo(tg.getName()) > 0) {
                l.add(i, tg);
                return l;
            }
        }
        l.add(tg);
        return l;
    }

	/* (non-Javadoc)
	 * @see com.aof.service.admin.TravelGroupManager#getHotelTravelGroupDetail(com.aof.model.admin.TravelGroup)
	 */
	public TravelGroupDetail getHotelTravelGroupDetail(TravelGroup tg) {
        Map conds=new HashMap();
        conds.put(TravelGroupDetailQueryCondition.TRAVELGROUP_ID_EQ,tg.getId());
        conds.put(TravelGroupDetailQueryCondition.EXPENSESUBCATEGORY_ISHOTEL_EQ,YesNo.YES.getEnumCode());
        List l=travelGroupDetailDAO.getTravelGroupDetailList(conds,0,-1,null,false);
        if(l.isEmpty())
            return null;
        return (TravelGroupDetail) l.get(0);
	}

    /* (non-Javadoc)
     * @see com.aof.service.admin.TravelGroupManager#getTravelGroupDetail(com.aof.model.admin.TravelGroup, com.aof.model.admin.ExpenseSubCategory)
     */
    public TravelGroupDetail getTravelGroupDetail(TravelGroup travelGroup, ExpenseSubCategory expenseSubCategory) {
        return travelGroupDetailDAO.getTravelGroupDetail(travelGroup.getId(),expenseSubCategory.getId());
    }

    

}
