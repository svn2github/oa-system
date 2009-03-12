/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.dao.business.pr;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;


import net.sourceforge.model.admin.SiteMailReminder;
import net.sourceforge.model.business.pr.PurchaseRequest;
import net.sourceforge.model.business.pr.PurchaseRequestApproveRequest;
import net.sourceforge.model.business.pr.PurchaseRequestAttachment;
import net.sourceforge.model.business.pr.PurchaseRequestHistory;
import net.sourceforge.model.business.pr.PurchaseRequestHistoryItem;
import net.sourceforge.model.business.pr.PurchaseRequestItem;
import net.sourceforge.model.business.pr.PurchaseRequestItemAttachment;
import net.sourceforge.model.business.pr.PurchaseRequestItemRecharge;
import net.sourceforge.model.business.pr.PurchaseRequestPurchaser;
import net.sourceforge.model.business.pr.query.PurchaseRequestQueryOrder;

/**
 * DAO interface for domain model purchase request
 * 
 * @author shilei
 * @version 1.0 (Dec 7, 2005)
 */
public interface PurchaseRequestDAO {

    /**
     * get purchaseRequest by id
     * 
     * @param id
     *            id of urchaseRequest
     * @return
     */
    public PurchaseRequest getPurchaseRequest(String id);

    /**
     * get purchaseRequest List Count according to conditions
     * 
     * @param conditions
     *            query conditions
     * @return count of purchaseRequest
     */
    public int getPurchaseRequestListCount(Map conditions);

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
    public List getPurchaseRequestList(Map conditions, int pageNo, int pageSize, PurchaseRequestQueryOrder order, boolean descend);

    /**
     * insert purchaseRequest
     * 
     * @param purchaseRequest
     * @return
     */
    public PurchaseRequest insertPurchaseRequest(PurchaseRequest purchaseRequest);

    /**
     * update purchaseRequest
     * 
     * @param purchaseRequest
     * @return
     */
    public PurchaseRequest updatePurchaseRequest(PurchaseRequest purchaseRequest);

    /**
     * get Max PurchaseRequest Id which begins with prefix
     * 
     * @param prefix
     * @return Max PurchaseRequest Id
     */
    public String getMaxPurchaseRequestIdBeginWith(final String prefix);

    /**
     * get purchase request item list of pr
     * 
     * @param pr
     * @return
     */
    public List getPurchaseRequestItemList(PurchaseRequest pr);
    
    /**
     * get purchase request item list of pr with item.attachments and item.recharges
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
     * delete PurchaseRequest
     * 
     * @param pr
     */
    public void deletePurchaseRequest(PurchaseRequest pr);

    /**
     * delete delete PurchaseRequest Items of pr
     * 
     * @param pr
     */
    public void deletePurchaseRequestItems(PurchaseRequest pr);

    /**
     * insert PurchaseRequest Item
     * 
     * @param pri
     */
    public void insertPurchaseRequestItem(PurchaseRequestItem pri);

    /**
     * insert Purchase Request Item Recharge
     * 
     * @param prir
     */
    public void insertPurchaseRequestItemRecharge(PurchaseRequestItemRecharge prir);

    /**
     * delete histories of purchaseRequest
     * 
     * @param pr
     */
    public void deletePurchaseRequestHistories(PurchaseRequest pr);

    /**
     * update purchaseRequestItem
     * 
     * @param pri
     */
    public void updatePurchaseRequestItem(PurchaseRequestItem pri);

    /**
     * delete recharges of pri
     * 
     * @param pri
     */
    public void deletePurchaseRequestItemRecharges(PurchaseRequestItem pri);

    /**
     * delete attachments of pri
     * 
     * @param pri
     */
    public void deletePurchaseRequestItemAttachments(PurchaseRequestItem pri);

    /**
     * get item id list of pr
     * 
     * @param pr
     * @return
     */
    public List getPurchaseRequestItemIdList(PurchaseRequest pr);

    /**
     * delete purchaseRequestItem by id
     * 
     * @param itemId
     */
    public void deletePurchaseRequestItem(Integer itemId);

    /**
     * update purchaseRequestItemAttachment
     * 
     * @param pria
     */
    public void updatePurchaseRequestItemAttachment(PurchaseRequestItemAttachment pria);

    /**
     * update purchaseRequestAttachment
     * 
     * @param pria
     */
    public void updatePurchaseRequestAttachment(PurchaseRequestAttachment pria);

    /**
     * get PurchaseRequestItemAttachment by id
     * 
     * @param id
     * @return
     */
    public PurchaseRequestItemAttachment getPurchaseRequestItemAttachment(Integer id);

    /**
     * get PurchaseRequestAttachment by id
     * 
     * @param id
     * @return
     */
    public PurchaseRequestAttachment getPurchaseRequestAttachment(Integer id);

    /**
     * get content of PurchaseRequestItemAttachment
     * 
     * @param id
     * @return
     */
    public InputStream getPurchaseRequestItemAttachmentContent(Integer id);

    /**
     * get content of PurchaseRequestAttachment
     * 
     * @param id
     * @return
     */
    public InputStream getPurchaseRequestAttachmentContent(Integer id);

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
     * @param pria
     * @param inputStream
     * @return
     */
    public PurchaseRequestAttachment insertPurchaseRequestAttachment(PurchaseRequestAttachment pra, InputStream inputStream);

    /**
     * get attachment id list for purchaseRequestItem pri
     * 
     * @param pri
     * @return
     */
    public List getPurchaseRequestItemAttachmentIdList(PurchaseRequestItem pri);

    /**
     * delete PurchaseRequestItemAttachment by id
     * 
     * @param itemAttachmentId
     */
    public void deletePurchaseRequestItemAttachment(Integer itemAttachmentId);

    /**
     * get attachment id list for purchaseRequest pr
     * 
     * @param pr
     * @return
     */
    public List getPurchaseRequestAttachmentIdList(PurchaseRequest pr);

    /**
     * delete PurchaseRequestAttachment by id
     * 
     * @param id
     */
    public void deletePurchaseRequestAttachment(Integer id);

    /**
     * insert PurchaseRequestApproveRequest
     * 
     * @param prar
     */
    public void insertPurchaseRequestApproveRequest(PurchaseRequestApproveRequest prar);

    /**
     * get PurchaseRequest by approveRequestId
     * 
     * @param approveRequestId
     * @return
     */
    public PurchaseRequest getPurchaseRequestByApproveRequestId(String approveRequestId);

    /**
     * get PurchaseRequestItem by id
     * 
     * @param id
     * @return
     */
    public PurchaseRequestItem getPurchaseRequestItem(Integer id);
    
    /**
     * get PurchaseRequestItem by id with recharges, attachments
     * @param id
     * @return
     */
    public PurchaseRequestItem getPurchaseRequestItemWithDetails(Integer id);    

    /**
     * save PurchaseRequestHistory
     * 
     * @param history
     */
    public void savePurchaseRequestHistory(PurchaseRequestHistory history);

    /**
     * save PurchaseRequestHistoryItem
     * 
     * @param historyItem
     */
    public void savePurchaseRequestHistoryItem(PurchaseRequestHistoryItem historyItem);

    /**
     * get purchase order item list of pr
     * 
     * @param pr
     * @return
     */
    public List getPurchaseOrderItemList(PurchaseRequest pr);

    /**
     * get purchase order item id list of pr
     * 
     * @param pr
     * @return
     */
    public List getPurchaseOrderItemIdList(PurchaseRequest pr);

    /**
     * save PurchaseRequestPurchaser
     * 
     * @param purchaser
     */
    public void savePurchaseRequestPurchaser(PurchaseRequestPurchaser purchaser);

    /**
     * get purchase order item list of pr with attachments and recharges
     * @param pr
     * @return
     */
    public List getPurchaseOrderItemListWithDetails(PurchaseRequest pr);
    
    /**
     * get PurchaseRequestItemAttachments of pri
     * @param pri
     * @return
     */
    public List getPurchaseRequestItemAttachments(PurchaseRequestItem pri) ;
    
    
    /**
     * get PurchaseRequestItemRecharges of pri
     * @param pri
     * @return
     */
    public List getPurchaseRequestItemRecharges(PurchaseRequestItem pri);

    /**
     * is PurchaseRequestItem InUse
     * @param pri
     * @return
     */
    public boolean isPurchaseRequestItemInUse(PurchaseRequestItem pri);

    /**
     * whether purchaseRequest has POItem
     * @param pr
     * @return
     */
    public boolean purchaseRequestHasPOItem(PurchaseRequest pr);
    
    /**
     * delete PurchaseRequestApproveRequest of pr
     * @param pr
     */
    public void deletePurchaseRequestApproveRequest(PurchaseRequest pr);

    /**
     * delete PurchaseRequestPurchase of pr
     * @param pr
     */
    public void deletePurchaseRequestPurchaserByPurchaseRequest(PurchaseRequest pr);

    public List getPurchaserNotRespondedPurchaseRequestList(Date time,SiteMailReminder smr);

    public List getPurchaseRequestPurchaserList(PurchaseRequest pr);



}
