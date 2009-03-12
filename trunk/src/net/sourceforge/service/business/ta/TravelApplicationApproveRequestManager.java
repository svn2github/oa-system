/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.service.business.ta;

import java.util.List;
import java.util.Map;

import net.sourceforge.model.admin.User;
import net.sourceforge.model.business.ta.TravelApplication;
import net.sourceforge.model.business.ta.TravelApplicationApproveRequest;
import net.sourceforge.model.business.ta.query.TravelApplicationApproveRequestQueryOrder;

/**
 * 定义操作TravelApplicationApproveRequest的接口
 * 
 * @author ych
 * @version 1.0 (Nov 17, 2005)
 */
public interface TravelApplicationApproveRequestManager {

    /**
     * 从数据库取得指定approveRequestId和approver的TravelApplicationApproveRequest
     * 
     * @param approveRequestId
     *            TravelApplicationApproveRequest的approveRequestId
     * @param approver
     *            TravelApplicationApproveRequest的approver
     * @return 返回指定的TravelApplicationApproveRequest
     */
    public TravelApplicationApproveRequest getTravelApplicationApproveRequest(String approveRequestId, User approver);

    /**
     * 从数据库取得指定approveRequestId的TravelApplicationApproveRequest列表
     * 
     * @param approveRequestId
     *            TravelApplicationApproveRequest的approveRequestId
     * @return 返回指定的TravelApplicationApproveRequest列表
     */
    public List getTravelApplicationApproveRequestListByApproveRequestId(String approveRequestId);

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

    /**
     * 返回符合查询条件的TravelApplicationApproveRequest对象个数
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自TravelApplicationApproveRequestQueryCondition类的预定义常量
     * @return 符合查询条件的TravelApplicationApproveRequest对象个数
     */
    public int getTravelApplicationApproveRequestListCount(Map condtions);

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
    public List getTravelApplicationApproveRequestList(Map condtions, int pageNo, int pageSize, TravelApplicationApproveRequestQueryOrder order, boolean descend);

    /**
     * 通过指定的TravelApplicationApproveRequest
     * 
     * @param travelApplicationApproveRequest
     *            审批的TravelApplicationApproveRequest
     */
    public void approveTravelApplicationApproveRequest(TravelApplicationApproveRequest travelApplicationApproveRequest,User user);

    /**
     * 反对指定的TravelApplicationApproveRequest
     * 
     * @param travelApplicationApproveRequest
     *            审批的TravelApplicationApproveRequest
     */
    public void rejectTravelApplicationApproveRequest(TravelApplicationApproveRequest travelApplicationApproveRequest,User user);

    /**
     * 通过指定的TravelApplicationApproveRequest并保存TravelApplication
     * 
     * @param travelApplicationApproveRequest
     *            审批的TravelApplicationApproveRequest
     * @param travelApplication
     *            要保存的TravelApplication
     */
    public void approveTravelApplicationApproveRequestAndUpdateTravelApplication(TravelApplicationApproveRequest travelApplicationApproveRequest,
            TravelApplication travelApplication,User user);
}
