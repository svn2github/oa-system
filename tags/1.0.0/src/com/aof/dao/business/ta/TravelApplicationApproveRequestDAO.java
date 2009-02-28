/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.dao.business.ta;

import java.util.List;
import java.util.Map;

import com.aof.dao.business.BaseApproveRequestDAO;
import com.aof.model.admin.User;
import com.aof.model.business.ta.TravelApplicationApproveRequest;
import com.aof.model.business.ta.query.TravelApplicationApproveRequestQueryOrder;

/**
 * 定义操作TravelApplicationApproveRequest的接口
 * @author ych
 * @version 1.0 (Nov 18, 2005)
 */
public interface TravelApplicationApproveRequestDAO extends BaseApproveRequestDAO {

    /**
     * 从数据库取得指定approveRequestId和approver的TravelApplicationApproveRequest
     * @param approveRequestId TravelApplicationApproveRequest的approveRequestId
     * @param approver TravelApplicationApproveRequest的approver
     * @return 返回指定的TravelApplicationApproveRequest
     */
    public TravelApplicationApproveRequest getTravelApplicationApproveRequest(String approveRequestId, User approver);    
	
    /**
     * 从数据库取得指定approveRequestId的TravelApplicationApproveRequest列表
     * @param approveRequestId TravelApplicationApproveRequest的approveRequestId
     * @return 返回指定的TravelApplicationApproveRequest列表
     */
    public List getTravelApplicationApproveRequestListByApproveRequestId(String approveRequestId); 
    
    /**
     * 返回符合查询条件的TravelApplicationApproveRequest对象个数
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自TravelApplicationApproveRequestQueryCondition类的预定义常量
     * @return 符合查询条件的TravelApplicationApproveRequest对象个数
     */
	public int getTravelApplicationApproveRequestListCount(Map conditions);
	
    /**
     * 返回符合查询条件的TravelApplicationApproveRequest对象列表
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自TravelApplicationApproveRequestQueryCondition类的预定义常量
     * @param pageNo
     *            第几页，以pageSize为页的大小，pageSize为-1时忽略该参数
     * @param pageSize
     *            页的大小，-1表示不分页
     * @param order
     *            排序条件，null表示不排序
     * @param descend
     *            false表示升序，true表示降序
     * @return 符合查询条件的TravelApplicationApproveRequest对象列表
     */
    public List getTravelApplicationApproveRequestList(Map conditions, int pageNo, int pageSize, TravelApplicationApproveRequestQueryOrder order, boolean descend);

    /**
     * 插入指定的TravelApplicationApproveRequest对象到数据库
     * 
     * @param travelApplicationApproveRequest
     *            要保存的TravelApplicationApproveRequest对象
     * @return 保存后的TravelApplicationApproveRequest对象
     */
    public TravelApplicationApproveRequest insertTravelApplicationApproveRequest(TravelApplicationApproveRequest travelApplicationApproveRequest);
    
    /**
     * 更新指定的TravelApplicationApproveRequest对象到数据库
     * 
     * @param travelApplicationApproveRequest
     *            要更新的TravelApplicationApproveRequest对象
     * @return 更新后的TravelApplicationApproveRequest对象
     */
    public TravelApplicationApproveRequest updateTravelApplicationApproveRequest(TravelApplicationApproveRequest travelApplicationApproveRequest);

}
