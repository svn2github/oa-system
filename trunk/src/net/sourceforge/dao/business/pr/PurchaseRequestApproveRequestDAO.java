/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.dao.business.pr;

import java.util.List;
import java.util.Map;

import net.sourceforge.dao.business.BaseApproveRequestDAO;
import net.sourceforge.model.admin.User;
import net.sourceforge.model.business.pr.PurchaseRequest;
import net.sourceforge.model.business.pr.PurchaseRequestApproveRequest;
import net.sourceforge.model.business.pr.query.PurchaseRequestApproveRequestQueryOrder;

/**
 * 定义操作PurchaseRequestApproveRequest的接口

 * @author nicebean
 * @version 1.0 (2005-12-15)
 */
public interface PurchaseRequestApproveRequestDAO extends BaseApproveRequestDAO {

    /**
     * 从数据库取得指定approveRequestId和approver的PurchaseRequestApproveRequest
     * @param approveRequestId PurchaseRequestApproveRequest的approveRequestId
     * @param approver PurchaseRequestApproveRequest的approver
     * @return 返回指定的PurchaseRequestApproveRequest
     */
    public PurchaseRequestApproveRequest getPurchaseRequestApproveRequest(String approveRequestId, User approver);    
	
    /**
     * 从数据库取得指定approveRequestId的PurchaseRequestApproveRequest列表
     * @param approveRequestId PurchaseRequestApproveRequest的approveRequestId
     * @return 返回指定的PurchaseRequestApproveRequest列表
     */
    public List getPurchaseRequestApproveRequestListByApproveRequestId(String approveRequestId); 
    
    /**
     * 返回符合查询条件的PurchaseRequestApproveRequest对象个数
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自PurchaseRequestApproveRequestQueryCondition类的预定义常量
     * @return 符合查询条件的PurchaseRequestApproveRequest对象个数
     */
	public int getPurchaseRequestApproveRequestListCount(Map conditions);
	
    /**
     * 返回符合查询条件的PurchaseRequestApproveRequest对象列表
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自PurchaseRequestApproveRequestQueryCondition类的预定义常量
     * @param pageNo
     *            第几页，以pageSize为页的大小，pageSize为-1时忽略该参数
     * @param pageSize
     *            页的大小，-1表示不分页
     * @param order
     *            排序条件，null表示不排序
     * @param descend
     *            false表示升序，true表示降序
     * @return 符合查询条件的PurchaseRequestApproveRequest对象列表
     */
    public List getPurchaseRequestApproveRequestList(Map conditions, int pageNo, int pageSize, PurchaseRequestApproveRequestQueryOrder order, boolean descend);

    /**
     * 插入指定的PurchaseRequestApproveRequest对象到数据库
     * 
     * @param purchaseRequestApproveRequest
     *            要保存的PurchaseRequestApproveRequest对象
     * @return 保存后的PurchaseRequestApproveRequest对象
     */
    public PurchaseRequestApproveRequest insertPurchaseRequestApproveRequest(PurchaseRequestApproveRequest purchaseRequestApproveRequest);
    
    /**
     * 更新指定的PurchaseRequestApproveRequest对象到数据库
     * 
     * @param purchaseRequestApproveRequest
     *            要更新的PurchaseRequestApproveRequest对象
     * @return 更新后的PurchaseRequestApproveRequest对象
     */
    public PurchaseRequestApproveRequest updatePurchaseRequestApproveRequest(PurchaseRequestApproveRequest purchaseRequestApproveRequest);

    /**
     * 
     * @param purchaseRequest
     */
    public void deletePurchaseRequestApproveRequestByPurchaseRequest(PurchaseRequest purchaseRequest);
    
}
