/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.dao.business.po;

import java.util.List;
import java.util.Map;

import com.aof.dao.business.BaseApproveRequestDAO;
import com.aof.model.admin.User;
import com.aof.model.business.po.PurchaseOrder;
import com.aof.model.business.po.PurchaseOrderApproveRequest;
import com.aof.model.business.po.query.PurchaseOrderApproveRequestQueryOrder;

/**
 * 定义操作PurchaseOrderApproveRequest的接口

 * @author nicebean
 * @version 1.0 (2005-12-22)
 */
public interface PurchaseOrderApproveRequestDAO extends BaseApproveRequestDAO {

    /**
     * 从数据库取得指定approveRequestId和approver的PurchaseOrderApproveRequest
     * @param approveRequestId PurchaseOrderApproveRequest的approveRequestId
     * @param approver PurchaseOrderApproveRequest的approver
     * @return 返回指定的PurchaseOrderApproveRequest
     */
    public PurchaseOrderApproveRequest getPurchaseOrderApproveRequest(String approveRequestId, User approver);    
	
    /**
     * 从数据库取得指定approveRequestId的PurchaseOrderApproveRequest列表
     * @param approveRequestId PurchaseOrderApproveRequest的approveRequestId
     * @return 返回指定的PurchaseOrderApproveRequest列表
     */
    public List getPurchaseOrderApproveRequestListByApproveRequestId(String approveRequestId); 
    
    /**
     * 返回符合查询条件的PurchaseOrderApproveRequest对象个数
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自PurchaseOrderApproveRequestQueryCondition类的预定义常量
     * @return 符合查询条件的PurchaseOrderApproveRequest对象个数
     */
	public int getPurchaseOrderApproveRequestListCount(Map conditions);
	
    /**
     * 返回符合查询条件的PurchaseOrderApproveRequest对象列表
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自PurchaseOrderApproveRequestQueryCondition类的预定义常量
     * @param pageNo
     *            第几页，以pageSize为页的大小，pageSize为-1时忽略该参数
     * @param pageSize
     *            页的大小，-1表示不分页
     * @param order
     *            排序条件，null表示不排序
     * @param descend
     *            false表示升序，true表示降序
     * @return 符合查询条件的PurchaseOrderApproveRequest对象列表
     */
    public List getPurchaseOrderApproveRequestList(Map conditions, int pageNo, int pageSize, PurchaseOrderApproveRequestQueryOrder order, boolean descend);

    /**
     * 插入指定的PurchaseOrderApproveRequest对象到数据库
     * 
     * @param purchaseOrderApproveRequest
     *            要保存的PurchaseOrderApproveRequest对象
     * @return 保存后的PurchaseOrderApproveRequest对象
     */
    public PurchaseOrderApproveRequest insertPurchaseOrderApproveRequest(PurchaseOrderApproveRequest purchaseOrderApproveRequest);
    
    /**
     * 更新指定的PurchaseOrderApproveRequest对象到数据库
     * 
     * @param purchaseOrderApproveRequest
     *            要更新的PurchaseOrderApproveRequest对象
     * @return 更新后的PurchaseOrderApproveRequest对象
     */
    public PurchaseOrderApproveRequest updatePurchaseOrderApproveRequest(PurchaseOrderApproveRequest purchaseOrderApproveRequest);

    /**
     * delete PurchaseOrderApproveRequest By PurchaseOrder
     * @param purchaseOrder
     */
    public void deletePurchaseOrderApproveRequestByPurchaseOrder(PurchaseOrder purchaseOrder);
    
}
