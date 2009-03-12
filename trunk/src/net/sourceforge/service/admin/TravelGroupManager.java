package net.sourceforge.service.admin;

import java.util.List;
import java.util.Map;

import net.sourceforge.model.admin.ExpenseSubCategory;
import net.sourceforge.model.admin.TravelGroup;
import net.sourceforge.model.admin.TravelGroupDetail;
import net.sourceforge.model.admin.query.TravelGroupQueryOrder;

/**
 * service manager for domain model travelGroup
 * 
 * @author shilei
 * @version 1.0 (Nov 15, 2005)
 */
public interface TravelGroupManager {

    /**
     * 从数据库取得指定id的Travel Group
     * 
     * @param id
     *            Travel Group的id
     * @return 返回指定的Travel Group
     */
    public TravelGroup getTravelGroup(Integer id);

    /**
     * insert Travel Group to database
     * 
     * @param travel
     *            Group the Travel Group inserted
     * @return the Travel Group inserted
     */
    public TravelGroup insertTravelGroup(TravelGroup travelGroup);

    /**
     * update Travel Group to datebase
     * 
     * @param travelGroup
     *            the Travel Group updated
     * @return the Travel Group updated
     */
    public TravelGroup updateTravelGroup(TravelGroup travelGroup);

    /**
     * get Travel Group List Count according to conditions
     * 
     * @param conditions
     *            search condition
     * @return list count
     */
    public int getTravelGroupListCount(Map condtions);

    /**
     * get Travel Group List according to conditions
     * 
     * @param conditions
     *            search condition
     * @param pageNo
     *            start page no(0 based), ignored if -1
     * @param pageSize
     *            page size, ignored if -1
     * @param order
     *            search order
     * @param descend
     *            asc or desc
     * @return Travel Group list
     */
    public List getTravelGroupList(Map condtions, int pageNo, int pageSize, TravelGroupQueryOrder order, boolean descend);

    /**
     * insert Travel Group to database
     * 
     * @param travel
     *            Group the Travel Group inserted
     * @return the Travel Group inserted
     */
    public void insertTravelGroup(TravelGroup travelGroup, List detailList);

    /**
     * get travelGroupDetail list of desinated travelGroup
     * 
     * @param travelGroup
     * @return list of travelGroupDetail
     */
    public List getDetailOf(TravelGroup travelGroup);

    /**
     * update Travel Group to datebase
     * 
     * @param travelGroup
     *            the Travel Group updated
     * @return the Travel Group updated
     */
    public void updateTravelGroup(TravelGroup travelGroup, List detailList);

    /**
     * get All Enabled TravelGroup List of desinated Site
     * 
     * @param siteId
     *            id of site
     * @return list of TravelGroup
     */
    public List getAllEnabledTravelGroupListBySiteId(Integer siteId);

    /**
     * get All Enabled TravelGroup List of desinated Site including the
     * desinated TravelGroup
     * 
     * @param siteId
     *            id of site
     * 
     * @param travelGroupId
     *            id of TravelGroup
     * @return list of TravelGroup
     */
    public List getAllEnabledTravelGroupListBySiteIdAndIncludeThis(Integer siteId, Integer travelGroupId);

    /**
     * the the TravelGroupDetail of TravelGroup whose type is hotel
     * 
     * @param travelGroup
     * @return TravelGroupDetail whose type is hotel
     */
    public TravelGroupDetail getHotelTravelGroupDetail(TravelGroup travelGroup);
    
    /**
     * get TravelGroupDetail of desinated TravelGroup and ExpenseSubCategory
     * @param travelGroup
     * @param expenseSubCategory
     * @return
     */
    public TravelGroupDetail getTravelGroupDetail(TravelGroup travelGroup,ExpenseSubCategory expenseSubCategory);
}
