/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.service.business.pr;

import java.util.List;
import java.util.Map;

import com.aof.model.admin.User;
import com.aof.model.business.pr.CapexRequest;
import com.aof.model.business.pr.CapexRequestApproveRequest;
import com.aof.model.business.pr.query.CapexRequestApproveRequestQueryOrder;

/**
 * 定义操作CapexRequestApproveRequest的接口
 *
 * @author nicebean
 * @version 1.0 (2005-12-8)
 */
public interface CapexRequestApproveRequestManager {

    /**
     * 从数据库取得指定approveRequestId和approver的CapexRequestApproveRequest
     * @param approveRequestId CapexRequestApproveRequest的approveRequestId
     * @param approver CapexRequestApproveRequest的approver
     * @return 返回指定的CapexRequestApproveRequest
     */
    public CapexRequestApproveRequest getCapexRequestApproveRequest(String approveRequestId, User approver);
    
    /**
     * 从数据库取得指定approveRequestId的CapexRequestApproveRequest列表
     * @param approveRequestId CapexRequestApproveRequest的approveRequestId
     * @return 返回指定的CapexRequestApproveRequest列表
     */
    public List getCapexRequestApproveRequestListByApproveRequestId(String approveRequestId);

    /**
     * 插入指定的CapexRequestApproveRequest对象到数据库
     * 
     * @param capexRequestApproveRequest
     *            要保存的CapexRequestApproveRequest对象
     * @return 保存后的CapexRequestApproveRequest对象
     */
    public CapexRequestApproveRequest insertCapexRequestApproveRequest(CapexRequestApproveRequest capexRequestApproveRequest);
    
    /**
     * 更新指定的CapexRequestApproveRequest对象到数据库
     * 
     * @param capexRequestApproveRequest
     *            要更新的CapexRequestApproveRequest对象
     * @return 更新后的CapexRequestApproveRequest对象
     */
    public CapexRequestApproveRequest updateCapexRequestApproveRequest(CapexRequestApproveRequest capexRequestApproveRequest);

    /**
     * 返回符合查询条件的CapexRequestApproveRequest对象个数
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自CapexRequestApproveRequestQueryCondition类的预定义常量
     * @return 符合查询条件的CapexRequestApproveRequest对象个数
     */
    public int getCapexRequestApproveRequestListCount(Map condtions);

    /**
     * 返回符合查询条件的CapexRequestApproveRequest对象列表
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自CapexRequestApproveRequestQueryCondition类的预定义常量
     * @param pageNo
     *            第几页，以pageSize为页的大小，pageSize为-1时忽略该参数
     * @param pageSize
     *            页的大小，-1表示不分页
     * @param order
     *            排序条件，null表示不排序
     * @param descend
     *            false表示升序，true表示降序
     * @return 符合查询条件的CapexRequestApproveRequest对象列表
     */
    public List getCapexRequestApproveRequestList(Map condtions, int pageNo, int pageSize, CapexRequestApproveRequestQueryOrder order, boolean descend);
    
    /**
     * 通过指定的CapexRequestApproveRequest
     * @param capexRequestApproveRequest 审批的CapexRequestApproveRequest
     */
    public void approveCapexRequestApproveRequest(CapexRequestApproveRequest capexRequestApproveRequest);
    
    /**
     * 拒绝指定的CapexRequestApproveRequest
     * @param capexRequestApproveRequest 审批的CapexRequestApproveRequest
     */
    public void rejectCapexRequestApproveRequest(CapexRequestApproveRequest capexRequestApproveRequest);

    /**
     * 通过指定的CapexRequestApproveRequest并保存CapexRequest
     * @param capexRequestApproveRequest     审批的CapexRequestApproveRequest
     * @param capexRequest                   要保存的CapexRequest
     * @param capexRequestItemList
     */
    public void approveCapexRequestApproveRequestAndUpdateCapexRequest(CapexRequestApproveRequest capexRequestApproveRequest, CapexRequest capexRequest, List capexRequestItemList);
    
    
}
