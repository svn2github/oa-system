/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.dao.business.po;

import java.io.InputStream;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.aof.model.admin.SiteMailReminder;
import com.aof.model.business.po.PaymentScheduleDetail;
import com.aof.model.business.po.PurchaseOrder;
import com.aof.model.business.po.PurchaseOrderApproveRequest;
import com.aof.model.business.po.PurchaseOrderAttachment;
import com.aof.model.business.po.PurchaseOrderHistory;
import com.aof.model.business.po.PurchaseOrderHistoryItem;
import com.aof.model.business.po.PurchaseOrderItem;
import com.aof.model.business.po.PurchaseOrderItemAttachment;
import com.aof.model.business.po.PurchaseOrderItemRecharge;
import com.aof.model.business.po.query.PurchaseOrderItemQueryOrder;
import com.aof.model.business.po.query.PurchaseOrderQueryOrder;
import com.aof.model.business.pr.PurchaseRequest;

/**
 * dao interface for domain model purchase
 * 
 * @author shi1
 * @version 1.0 (Nov 30, 2005)
 */
public interface PurchaseOrderDAO {
    
    /**
     * get PurchaseOrder List by conditions
     * 
     * @param conditions
     *            search condition
     * @param pageNo
     *            start page no(0 based), ignored if is -1
     * @param pageSize
     *            page size, ignored if is -1
     * @param order
     *            search order
     * @param descend
     *            asc or desc
     * @return Yearly Budget list
     */
    public List getPurchaseOrderList(Map conditions, int pageNo, int pageSize, PurchaseOrderQueryOrder order, boolean descend);

    /**
     * insert purchaseOrder
     * @param purchaseOrder
     * @return
     */
    public PurchaseOrder insertPurchaseOrder(PurchaseOrder purchaseOrder);
    
    /**
     * update purchaseOrder
     * @param purchaseOrder
     * @return
     */
    public PurchaseOrder updatePurchaseOrder(PurchaseOrder purchaseOrder);
    
    /**
     * get purchase order list count accroding to conditions
     * @param conditions
     * @return
     */
    public int getPurchaseOrderListCount(Map conditions);
    
    /**
     * get purchaseOrder by id
     * @param id
     * @return
     */
    public PurchaseOrder getPurchaseOrder(String id);
    /**
     * inser PurchaseOrderItem
     * 
     * @param item
     * @return
     */
    public PurchaseOrderItem insertPurchaseOrderItem(PurchaseOrderItem item);

    /**
     * update PurchaseOrderItem
     * 
     * @param item
     * @return
     */
    public PurchaseOrderItem updatePurchaseOrderItem(PurchaseOrderItem item);

    /**
     * get PurchaseOrderItem by id
     * 
     * @param id
     * @return
     */
    public PurchaseOrderItem getPurchaseOrderItem(Integer id);

    /**
     * delete PurchaseOrderItem
     * 
     * @param item
     * @return
     */
    public void deletePurchaseOrderItem(PurchaseOrderItem item);

    /**
     * 获取和指定PurchaseRequest关联的PurchaseOrderItem列表
     * 
     * @param pr
     *            指定的PurchaseRequest
     * @return PurchaseOrderItem列表
     */
    public List getPurchaseOrderItemListByPurchaseRequest(PurchaseRequest pr);

    /**
     * 删除和指定PurchaseRequest关联的PurchaseOrderItem对象，这些PurchaseOrderItem必须没有和PurchaseOrder关联
     * 
     * @param purchaseRequest
     *            指定的PurchaseRequest
     */
    public void deleteIsolationPurchaseOrderItemByPurchaseRequest(PurchaseRequest purchaseRequest);

    /**
     * 获取和指定PurchaseRequest关联的PurchaseOrderItemAttachment对象，这些PurchaseOrderItemAttchment对象所属的PurchaseOrderItem必须没有和PurchaseOrder关联
     * 
     * @param purchaseRequest
     *            指定的PurchaseRequest
     */
    public List getIsolationPurchaseOrderItemAttachmentByPurchaseRequest(PurchaseRequest purchaseRequest);
    
    /**
     * 获取和指定PurchaseRequest关联的PurchaseOrderItemRecharge对象，这些PurchaseOrderItemRecharge对象所属的PurchaseOrderItem必须没有和PurchaseOrder关联
     * 
     * @param purchaseRequest
     *            指定的PurchaseRequest
     */
    public List getIsolationPurchaseOrderItemRechargeByPurchaseRequest(PurchaseRequest purchaseRequest);

    /**
     * 获取和指定PurchaseRequest关联的PurchaseOrderItem对象，这些PurchaseOrderItem对象所属的PurchaseOrderItem必须没有和PurchaseOrder关联
     * 
     * @param purchaseRequest
     *            指定的PurchaseRequest
     */
    public List getIsolationPurchaseOrderItemByPurchaseRequest(PurchaseRequest purchaseRequest);

    /**
     * get purchaseItemAttachment by id
     * @param id
     * @return
     */
    public PurchaseOrderItemAttachment getPurchaseOrderItemAttachment(Integer id);

    /**
     * get purchaseRequestItemAttachment file content by id
     * @param id
     * @return
     */
    public InputStream getPurchaseRequestItemAttachmentContent(Integer id);

    /**
     * insert PurchaseOrderItemAttachment
     * @param poia
     * @param inputStream
     * @return
     */
    public PurchaseOrderItemAttachment insertPurchaseOrderItemAttachment(PurchaseOrderItemAttachment poia, InputStream inputStream);

    /**
     * get PurchaseOrderItemAttachments of poi 
     * @param poi
     * @return
     */
    public Collection getPurchaseOrderItemAttachments(PurchaseOrderItem poi);

    /**
     * get PurchaseOrderItemRecharges of poi 
     * @param poi
     * @return
     */
    public Collection getPurchaseOrderItemRecharges(PurchaseOrderItem poi);

    /**
     * delete recharges of poi
     * @param poi
     */
    public void deletePurchaseOrderItemRecharges(PurchaseOrderItem poi);

    /**
     * insert purchaseOrderItemRecharge
     * @param poir
     */
    public void insertPurchaseOrderItemRecharge(PurchaseOrderItemRecharge poir);

    
    /**
     * update PurchaseOrderItemAttachment
     * @param poia
     */
    public void updatePurchaseOrderItemAttachment(PurchaseOrderItemAttachment poia);

    /**
     * deletePurchaseOrderItemAttachment by itemAttachmentId
     * @param itemAttachmentId
     */
    public void deletePurchaseOrderItemAttachment(Integer itemAttachmentId);

    /**
     * delete PurchaseOrderItemAttachment by purchaseOrderItem
     * @param purchaseOrderItem
     */
    public void deletePurchaseOrderItemAttachments(PurchaseOrderItem purchaseOrderItem);

    public String getMaxPurchaseOrderIdBeginWith(String prefix);

    /**
     * get PurchaseOrderItemAttachment Id List of poi
     * @param poi
     * @return
     */
    public List getPurchaseOrderItemAttachmentIdList(PurchaseOrderItem poi);

    /**
     * get PurchaseOrderItemList of po With recharges, attachments
     * @param po
     * @return
     */
    public List getPurchaseOrderItemListWithDetails(PurchaseOrder po);

    /**
     * get PurchaseOrderAttachment List of po
     * @param po
     * @return
     */
    public List getPurchaseOrderAttachmentList(PurchaseOrder po);

    /**
     * get PurchaseOrderItem List of po
     * @param po
     * @return
     */
    public List getPurchaseOrderItemList(PurchaseOrder po);

    /**
     * get PurchaseOrderAttachment by id
     * @param id
     * @return
     */
    public PurchaseOrderAttachment getPurchaseOrderAttachment(Integer id);
    
    /**
     * get PurchaseOrderItem Id List of po
     * @param po
     * @return
     */
    public List getPurchaseOrderItemIdList(PurchaseOrder po);
    
    /**
     * get PurchaseOrderAttachment Id List of po;
     * @param po
     * @return
     */
    public List getPurchaseOrderAttachmentIdList(PurchaseOrder po);

    /**
     * get PaymentScheduleDetail List of po
     * @param po
     * @return
     */
    public List getPaymentScheduleDetailList(PurchaseOrder po);

    /**
     * get PaymentScheduleDetail by id
     * @param id
     * @return
     */
    public PaymentScheduleDetail getPaymentScheduleDetail(Integer id);

    /**
     * get PaymentScheduleDetail Id List of po
     * @param po
     * @return
     */
    public List getPaymentScheduleDetailIdList(PurchaseOrder po);

    /**
     * delete PaymentScheduleDetails of po
     * @param po
     */
    public void deletePaymentScheduleDetails(PurchaseOrder po);

    /**
     * update PurchaseOrderAttachment of poa
     * @param poa
     */
    public void updatePurchaseOrderAttachment(PurchaseOrderAttachment poa);

    /**
     * delete PurchaseOrder
     * @param po
     */
    public void deletePurchaseOrder(PurchaseOrder po);
    
    /**
     * delete PurchaseOrderHisotries of po
     * @param po
     */
    public void deletePurchaseOrderHisotries(PurchaseOrder po);

    /**
     * delete PurchaseOrderItem by itemId
     * @param itemId
     */
    public void deletePurchaseOrderItem(Integer itemId);

    /**
     * delete PurchaseOrderAttachment by attachmentId
     * @param attachmentId
     */
    public void deletePurchaseOrderAttachment(Integer attachmentId);

    /**
     * insert PaymentScheduleDetail
     * @param psd
     */
    public void insertPaymentScheduleDetail(PaymentScheduleDetail psd);

    /**
     * update PaymentScheduleDetail
     * @param psd
     */
    public void updatePaymentScheduleDetail(PaymentScheduleDetail psd);

    /**
     * delete PaymentScheduleDetail
     * @param psdId
     */
    public void deletePaymentScheduleDetail(Integer psdId);

    /**
     * insert PurchaseOrderAttachment
     * @param pra
     * @param inputStream
     * @return
     */
    public PurchaseOrderAttachment insertPurchaseOrderAttachment(PurchaseOrderAttachment pra, InputStream inputStream);

    /**
     * get PurchaseOrderAttachment Content by id
     * @param id
     * @return
     */
    public InputStream getPurchaseOrderAttachmentContent(Integer id);

    /**
     * insert PurchaseOrderApproveRequest
     * @param poar
     */
    public void insertPurchaseOrderApproveRequest(PurchaseOrderApproveRequest poar);

    /**
     * get PurchaseOrderApproveRequest List of po 
     * @param po
     * @return
     */
    public List getPurchaseOrderApproveRequestList(PurchaseOrder po);

    /**
     * delete PurchaseOrderAttachments of po
     * @param po
     */
    public void deletePurchaseOrderAttachments(PurchaseOrder po);

    /**
     * get PurchaseOrder by approveRequestId
     * 
     * @param approveRequestId
     * @return
     */
    public PurchaseOrder getPurchaseOrderByApproveRequestId(String approveRequestId);

    /**
     * insert or update purchase order history 
     * @param history
     */
    public void savePurchaseOrderHistory(PurchaseOrderHistory history);

    /**
     * insert or update purchase order history item
     * @param historyItem
     */
    public void savePurchaseOrderHistoryItem(PurchaseOrderHistoryItem historyItem);

    /**
     * get Requestor List Of PurchaseOrder
     * @param po
     * @return
     */
    public List getRequestorListOfPurchaseOrder(PurchaseOrder po);
    
    /**
     * get PurchaseOrderItem List by conditions
     * 
     * @param conditions
     *            search condition
     * @param pageNo
     *            start page no(0 based), ignored if is -1
     * @param pageSize
     *            page size, ignored if is -1
     * @param order
     *            search order
     * @param descend
     *            asc or desc
     * @return Yearly Budget list
     */
    public List getPurchaseOrderItemList(Map conditions, int pageNo, int pageSize, PurchaseOrderItemQueryOrder order, boolean descend);

    /**
     * get purchase order item list count accroding to conditions
     * @param conditions
     * @return
     */
    public int getPurchaseOrderItemListCount(Map conditions);

    /**
     * delete approver request of po
     * @param po
     */
    public void deletePurchaseOrderApproveRequest(PurchaseOrder po);

    /**
     * get finance not responded PurchaseOrder list
     * @param now
     * @param smr
     * @return
     */
    public List getFinanceNotRespondedPurchaseOrderList(Date now, SiteMailReminder smr);
    
}   
