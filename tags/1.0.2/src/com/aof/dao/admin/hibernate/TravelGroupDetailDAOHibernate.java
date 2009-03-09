/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.dao.admin.hibernate;

import java.util.List;
import java.util.Map;

import com.aof.dao.BaseDAOHibernate;
import com.aof.dao.admin.TravelGroupDetailDAO;
import com.aof.model.admin.ExpenseSubCategory;
import com.aof.model.admin.TravelGroup;
import com.aof.model.admin.TravelGroupDetail;
import com.aof.model.admin.query.TravelGroupDetailQueryCondition;
import com.aof.model.admin.query.TravelGroupDetailQueryOrder;

/**
 * hibernate implement for TravelGroupDetailDAO
 * @author shilei
 * @version 1.0 (Nov 15, 2005)
 */
public class TravelGroupDetailDAOHibernate extends BaseDAOHibernate implements TravelGroupDetailDAO {
    
	/* (non-Javadoc)
	 * @see com.aof.dao.admin.TravelGroupDetailDAO#getTravelGroupDetail(java.lang.Integer, java.lang.Integer)
	 */
	public TravelGroupDetail getTravelGroupDetail(Integer travelGroup_id,Integer expenseSubCategroy_id){
		TravelGroup tg=(TravelGroup) getHibernateTemplate().get(TravelGroup.class,travelGroup_id);
		ExpenseSubCategory esc=(ExpenseSubCategory) getHibernateTemplate().get(ExpenseSubCategory.class,expenseSubCategroy_id);
		TravelGroupDetail key=new TravelGroupDetail();
		key.setTravelGroup(tg);
		key.setExpenseSubCategory(esc);
        return (TravelGroupDetail) getHibernateTemplate().get(TravelGroupDetail.class,key );
    }
	
    /* (non-Javadoc)
     * @see com.aof.dao.admin.TravelGroupDetailDAO#updateTravelGroupDetail(com.aof.model.admin.TravelGroupDetail)
     */
    public TravelGroupDetail updateTravelGroupDetail(TravelGroupDetail travelGroupDetail) {
        TravelGroupDetail oldTravelGroupDetail = getTravelGroupDetail(travelGroupDetail.getTravelGroup().getId(),
        		travelGroupDetail.getExpenseSubCategory().getId());
        if (oldTravelGroupDetail != null) {
        	oldTravelGroupDetail.setAmountLimit(travelGroupDetail.getAmountLimit());
        	oldTravelGroupDetail.setAbroadAmountLimit(travelGroupDetail.getAbroadAmountLimit());
        	getHibernateTemplate().update(oldTravelGroupDetail);	
        	return oldTravelGroupDetail;
        }
        else
        {
        	throw new RuntimeException("travelGroupDetail not found");
        }
    }	    

	/* (non-Javadoc)
	 * @see com.aof.dao.admin.TravelGroupDetailDAO#insertTravelGroupDetail(com.aof.model.admin.TravelGroupDetail)
	 */
	public TravelGroupDetail insertTravelGroupDetail(TravelGroupDetail travelGroupDetail) {
       	getHibernateTemplate().save(travelGroupDetail);
       	return travelGroupDetail;
    }

    private final static String SQL_COUNT = "select count(*) from TravelGroupDetail travelGroupDetail";

    private final static String SQL = "from TravelGroupDetail travelGroupDetail";

    private final static Object[][] QUERY_CONDITIONS = {
    	/*id*/    

		/*keyPropertyList*/

		/*kmtoIdList*/
		{ TravelGroupDetailQueryCondition.EXPENSESUBCATEGORY_ID_EQ, "travelGroupDetail.expenseSubCategory.id = ?", null },
		{ TravelGroupDetailQueryCondition.TRAVELGROUP_ID_EQ, "travelGroupDetail.travelGroup.id = ?", null },
        { TravelGroupDetailQueryCondition.EXPENSESUBCATEGORY_ISHOTEL_EQ, "travelGroupDetail.expenseSubCategory.isHotel = ?", null },
        

		/*mtoIdList*/

		/*property*/
		{ TravelGroupDetailQueryCondition.AMOUNTLIMIT_EQ, "travelGroupDetail.amountLimit = ?", null },
    };
    
    private final static Object[][] QUERY_ORDERS = {
		/*id*/

		/*property*/
        { TravelGroupDetailQueryOrder.AMOUNTLIMIT, "travelGroupDetail.amountLimit" },
    };
    
    /* (non-Javadoc)
     * @see com.aof.dao.admin.TravelGroupDetailDAO#getTravelGroupDetailListCount(java.util.Map)
     */
    public int getTravelGroupDetailListCount(final Map conditions) {
        return getListCount(conditions, SQL_COUNT, QUERY_CONDITIONS);
    }

    /* (non-Javadoc)
     * @see com.aof.dao.admin.TravelGroupDetailDAO#getTravelGroupDetailList(java.util.Map, int, int, com.aof.model.admin.query.TravelGroupDetailQueryOrder, boolean)
     */
    public List getTravelGroupDetailList(final Map conditions, final int pageNo, final int pageSize, final TravelGroupDetailQueryOrder order, final boolean descend) {
        return getList(conditions, pageNo, pageSize, order, descend, SQL, QUERY_CONDITIONS, QUERY_ORDERS);
    }

	/* (non-Javadoc)
	 * @see com.aof.dao.admin.TravelGroupDetailDAO#insertOrUpdateTravelGroupDetail(com.aof.model.admin.TravelGroupDetail)
	 */
	public TravelGroupDetail insertOrUpdateTravelGroupDetail(TravelGroupDetail travelGroupDetail) {
		TravelGroupDetail oldTravelGroupDetail = getTravelGroupDetail(travelGroupDetail.getTravelGroup().getId(),
        		travelGroupDetail.getExpenseSubCategory().getId());
        if (oldTravelGroupDetail != null) {
        	oldTravelGroupDetail.setAmountLimit(travelGroupDetail.getAmountLimit());
        	oldTravelGroupDetail.setAbroadAmountLimit(travelGroupDetail.getAbroadAmountLimit());
        	getHibernateTemplate().update(oldTravelGroupDetail);	
        	return oldTravelGroupDetail;
        }
        else
        {
        	insertTravelGroupDetail(travelGroupDetail);
        	return travelGroupDetail;
        }
	}

}
