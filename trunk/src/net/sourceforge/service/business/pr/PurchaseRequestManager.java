/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.service.business.pr;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import net.sourceforge.model.admin.User;
import net.sourceforge.model.business.pr.PurchaseRequest;
import net.sourceforge.model.business.pr.PurchaseRequestAttachment;
import net.sourceforge.model.business.pr.PurchaseRequestItem;
import net.sourceforge.model.business.pr.PurchaseRequestItemAttachment;
import net.sourceforge.model.business.pr.query.PurchaseRequestQueryOrder;

/**
 * manager interface for domain model
 * 
 * @author shilei
 * @version 1.0 (Dec 7, 2005)
 */
public interface PurchaseRequestManager {

    /**
     * get purchaseRequest by id
     * 
     * @param id
     *            id of urchaseRequest
     * @return
     */
    public PurchaseRequest getPurchaseRequest(String id);

    /**
     * insert purchaseRequest
     * 
     * @param purchaseRequest
     * @return
     */
    public PurchaseRequest insertPurchaseRequest(PurchaseRequest purchaseRequest, User creator, User requestor);

    /**
     * update purchaseRequest
     * 
     * @param purchaseRequest
     * @param itemList
     * @param attachmentList
     * @param firstSubmit
     * @return
     */
    public PurchaseRequest updatePurchaseRequest(PurchaseRequest purchaseRequest, List itemList, List attachmentList, boolean firstSubmit);

    /**
     * update purchaseRequest
     * 
     * @param purchaseRequest
     * @param itemList
     * @param attachmentList
     * @param purchaseRequestApproveRequestList
     * @param isDraft
     * @return
     */
    public PurchaseRequest updatePurchaseRequest(PurchaseRequest purchaseRequest, List itemList, List attachmentList, //List purchaseRequestApproveRequestList,
            boolean isDraft,User user);

    /**
     * get purchaseRequest List Count according to conditions
     * 
     * @param conditions
     *            query conditions
     * @return count of purchaseRequest
     */
    public int getPurchaseRequestListCount(Map condtions);

    /**
     * get purchaseRequest List by conditions
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
     * @return purchaseRequest list
     */
    public List getPurchaseRequestList(Map condtions, int pageNo, int pageSize, PurchaseRequestQueryOrder order, boolean descend);

    /**
     * get purchaseRequest item list of pr
     * 
     * @param pr
     * @return
     */
    public List getPurchaseRequestItemList(PurchaseRequest pr);

    /**
     * get purchaseRequest item list of pr with item.recharges and attachments
     * 
     * @param pr
     * @return
     */
    public List getPurchaseRequestItemListWithDetails(PurchaseRequest pr);

    /**
     * get purchaseRequest attachment list of pr
     * 
     * @param pr
     * @return
     */
    public List getPurchaseRequestAttachmentList(PurchaseRequest pr);

    /**
     * get purchase request approve request list of pr
     * 
     * @param pr
     * @return
     */
    public List getPurchaseRequestApproveRequestList(PurchaseRequest pr);

    /**
     * get purchase request item attachment by id
     * 
     * @param id
     * @return
     */
    public PurchaseRequestItemAttachment getPurchaseRequestItemAttachment(Integer id);

    /**
     * get content of PurchaseRequestItemAttachment
     * 
     * @param id
     * @return
     */
    public InputStream getPurchaseRequestItemAttachmentContent(Integer id);

    /**
     * insert PurchaseRequestItemAttachment
     * 
     * @param pria
     * @param inputStream
     * @return
     */
    public PurchaseRequestItemAttachment insertPurchaseRequestItemAttachment(PurchaseRequestItemAttachment pria, InputStream inputStream);

    /**
     * insert PurchaseRequestAttachment
     * 
     * @param pra
     * @param inputStream
     * @return
     */
    public PurchaseRequestAttachment insertPurchaseRequestAttachment(PurchaseRequestAttachment pra, InputStream inputStream);

    /**
     * get content of PurchaseRequestAttachment
     * 
     * @param id
     * @return
     */
    public InputStream getPurchaseRequestAttachmentContent(Integer id);

    /**
     * get PurchaseRequestAttachment according to id
     * 
     * @param id
     * @return
     */
    public PurchaseRequestAttachment getPurchaseRequestAttachment(Integer id);

    /**
     * delete purchaseRequest
     * 
     * @param pr
     */
    public void deletePurchaseRequest(PurchaseRequest pr,User user);

    /**
     * reject purchaseRequest
     * 
     * @param purchaseRequest
     */
    public void rejectPurchaseRequest(PurchaseRequest purchaseRequest,User user, String comment);

    /**
     * approve purchaseRequest
     * 
     * @param purchaseRequest
     */
    public void approvePurchaseRequest(PurchaseRequest purchaseRequest,User user);

    /**
     * get purchaseRequest by approveRequestId
     * 
     * @param approveRequestId
     * @return
     */
    public PurchaseRequest getPurchaseRequestByApproveRequestId(String approveRequestId);

    /**
     * get purchaseRequestItem by id
     * 
     * @param id
     * @return
     */
    public PurchaseRequestItem getPurchaseRequestItem(Integer id);

    /**
     * get purchaseRequestItem by id with recharges,attachments
     * 
     * @param id
     * @return
     */
    public PurchaseRequestItem getPurchaseRequestItemWithDetails(Integer id);

    /**
     * get purchaseOrderItem List of pr
     * 
     * @param pr
     * @return
     */
    public List getPurchaseOrderItemList(PurchaseRequest pr);

    /**
     * get purchaseOrderItem List of pr with purchaseOrderItem.recharges,
     * purchaseOrderItem.attachments
     * 
     * @param pr
     * @return
     */
    public List getPurchaseOrderItemListWithDetails(PurchaseRequest pr);

    /**
     * 采购purchaseRequest时保存PurchaseOrderItemList
     * 
     * @param pr
     *            purchaseRequset
     * @param purchaseOrderItemList
     *            list of purchaseRequestOrderItem
     * @param purchaseUser
     *            purchase User
     */
    public void savePurchaseOrderItemsOfPurchaseRequest(PurchaseRequest pr,List oldPoItemList, List purchaseOrderItemList, User purchaseUser);

    /**
     * 采购purchaseRequest时保存PurchaseOrderItemList
     * 并且把选择的purchaseOrderItems新建purchaseOrder
     * 
     * @param pr
     * @param purchaseOrderItemList
     *            要保存的purchaseOrderItems
     * @param selectedPurchaseOrderItemList
     *            要新建purchaseOrderItem的purchaseOrderItems
     * @param purchaseUser
     *            purchase User
     */
    public void savePurchaseOrderItemsOfPurchaseRequestAndCreatePO(PurchaseRequest pr,List oldPoItemList, List purchaseOrderItemList, List selectedPurchaseOrderItemList,
            User purchaseUser);

    /**
     * update PurchaseRequest
     * 
     * @param pr
     * @return
     */
    public PurchaseRequest updatePurchaseRequest(PurchaseRequest pr);

    /**
     * is PurchaseRequestItem InUse
     * 
     * @param pri
     * @return
     */
    public boolean isPurchaseRequestItemInUse(PurchaseRequestItem pri);

    /**
     * withdraw purchaseRequest
     * 
     * @param pr
     */
    public void withdrawPurchaseRequest(PurchaseRequest pr,User user);

    /**
     * whether purchaseRequest has POItem
     * 
     * @param pr
     * @return
     */
    public boolean purchaseRequestHasPOItem(PurchaseRequest pr);

    /**
     * get purchaseRequest using sepcified capex
     * 
     * @param id
     *            id of capex
     * @return
     */
    public List getCalculatedPurchaseRequestItemByCapexId(String id);

    /**
     * purchaser reject purchase request
     * @param pr
     * @param user
     */
    public void rejectPurchaseRequestByPurchaser(PurchaseRequest pr, User user);
    public void rejectPurchaseRequestByPurchaser(PurchaseRequest pr, User user, String comment);

    /**
     * 复制pr
     * @param copyPR 目标PR
     * @param srcPR 源PR
     * @return 返回复制的PR
     * @throws Exception 
     */
    public PurchaseRequest copyPurchaseRequest(PurchaseRequest copyPR,PurchaseRequest srcPR) throws Exception;    
}
