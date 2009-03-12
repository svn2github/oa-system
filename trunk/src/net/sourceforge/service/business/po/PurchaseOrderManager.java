package net.sourceforge.service.business.po;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sourceforge.model.admin.User;
import net.sourceforge.model.business.po.PaymentScheduleDetail;
import net.sourceforge.model.business.po.PurchaseOrder;
import net.sourceforge.model.business.po.PurchaseOrderAttachment;
import net.sourceforge.model.business.po.PurchaseOrderItem;
import net.sourceforge.model.business.po.PurchaseOrderItemAttachment;
import net.sourceforge.model.business.po.query.PurchaseOrderItemQueryOrder;
import net.sourceforge.model.business.po.query.PurchaseOrderQueryOrder;

/**
 * service manager interface for domain model purchaseOrder
 * 
 * @author shilei
 * @version 1.0 (Dec 20, 2005)
 */
public interface PurchaseOrderManager {

    /**
     * @param id
     * @return
     */
    public PurchaseOrder getPurchaseOrder(String id);

    public PurchaseOrder insertPurchaseOrder(PurchaseOrder purchaseOrder);

    public PurchaseOrder updatePurchaseOrder(PurchaseOrder purchaseOrder);

    public int getPurchaseOrderListCount(Map condtions);

    public List getPurchaseOrderList(Map condtions, int pageNo, int pageSize, PurchaseOrderQueryOrder order, boolean descend);

    /**
     * get purchaseItemAttachment by id
     * 
     * @param id
     * @return
     */
    public PurchaseOrderItemAttachment getPurchaseOrderItemAttachment(Integer id);

    /**
     * get purchaseRequestItemAttachment file content by id
     * 
     * @param id
     * @return
     */
    public InputStream getPurchaseRequestItemAttachmentContent(Integer id);

    /**
     * insert purchase order item attachment
     * 
     * @param poia
     * @param inputStream
     * @return
     */
    public PurchaseOrderItemAttachment insertPurchaseOrderItemAttachment(PurchaseOrderItemAttachment poia, InputStream inputStream);

    /**
     * get purchase order item by id
     * 
     * @param id
     * @return
     */
    public PurchaseOrderItem getPurchaseOrderItem(Integer id);

    /**
     * get purchase order item by id with details
     * 
     * @param id
     * @return
     */
    public PurchaseOrderItem getPurchaseOrderItemWithDetails(Integer id);

    /**
     * get PurchaseOrderItemAttachments of poi
     * 
     * @param poi
     * @return
     */
    public Collection getPurchaseOrderItemAttachments(PurchaseOrderItem poi);

    /**
     * get PurchaseOrderItemRecharges of poi
     * 
     * @param poi
     * @return
     */
    public Collection getPurchaseOrderItemRecharges(PurchaseOrderItem poi);

    /**
     * get purchaseOrder item list of po with recharges, attachments
     * 
     * @param po
     * @return
     */
    public List getPurchaseOrderItemListWithDetails(PurchaseOrder po);

    /**
     * get purchaseOrderAttachment list of po
     * 
     * @param po
     * @return
     */
    public List getPurchaseOrderAttachmentList(PurchaseOrder po);

    /**
     * get purchaseOrderItem list of po
     * 
     * @param po
     * @return
     */
    public List getPurchaseOrderItemList(PurchaseOrder po);

    /**
     * get PurchaseOrderAttachment by id
     * 
     * @param id
     * @return
     */
    public PurchaseOrderAttachment getPurchaseOrderAttachment(Integer id);

    /**
     * get PaymentScheduleDetail List of po
     * 
     * @param po
     * @return
     */
    public List getPaymentScheduleDetailList(PurchaseOrder po);

    /**
     * get PaymentScheduleDetail by id
     * 
     * @param id
     * @return
     */
    public PaymentScheduleDetail getPaymentScheduleDetail(Integer id);

    /**
     * consolidate PurchaseOrders
     * 
     * @param po_ids
     */
    public void consolidatePurchaseOrder(String[] po_ids,User user);

    /**
     * update purchaseOrders' erpNo
     * 
     * @param poList
     * @param newErpNo
     */
    public void updateErpNo(List poList, String[] newErpNo);

    /**
     * update updatePurchase Order
     * 
     * @param po
     * @param itemList
     * @param attachmentList
     * @param paymentScheduleDetailList
     */
    public void updatePurchaseOrder(PurchaseOrder purchaseOrder,List oldItemList, List itemList, List attachmentList, List paymentScheduleDetailList);

    /**
     * update updatePurchase Order
     * 
     * @param po
     * @param itemList
     * @param attachmentList
     * @param paymentScheduleDetailList
     * @param poarList
     * @param isDraft
     */
    public void updatePurchaseOrder(PurchaseOrder oldPO,PurchaseOrder po,List oldItemList, List itemList, List attachmentList, List paymentScheduleDetailList, List poarList, boolean isDraft,User user);

    /**
     * insert PurchaseOrderAttachment
     * 
     * @param pra
     * @param inputStream
     * @return
     */
    public PurchaseOrderAttachment insertPurchaseOrderAttachment(PurchaseOrderAttachment pra, InputStream inputStream);

    /**
     * get PurchaseOrderAttachment Content by id
     * 
     * @param id
     * @return
     */
    public InputStream getPurchaseOrderAttachmentContent(Integer id);

    /**
     * get PurchaseOrderApproveRequestList of po
     * 
     * @param po
     * @return
     */
    public List getPurchaseOrderApproveRequestList(PurchaseOrder po);

    /**
     * cancel purchase order
     * 
     * @param id
     */
    public void cancelPurchaseOrder(String id);

    /**
     * update purchase order item's recharge info. must put recharge info into
     * purchase order item's recharges property
     * 
     * @param poi
     */
    public void updatePurchaseOrderItemRecharges(PurchaseOrderItem poi);

    /**
     * approve Purchase Order
     * 
     * @param purchaseOrder
     */
    public void approvePurchaseOrder(PurchaseOrder purchaseOrder,User user);

    /**
     * reject Purchase Order
     * 
     * @param purchaseOrder
     */
    public void rejectPurchaseOrder(PurchaseOrder purchaseOrder,User user,String comment);

    /**
     * get PurchaseOrder by approveRequestId
     * 
     * @param approveRequestId
     * @return
     */
    public PurchaseOrder getPurchaseOrderByApproveRequestId(String approveRequestId);

    /**
     * reject PurhcaseOrder when finalConfirm
     * @param id
     */
    public void rejectPurhcaseOrderWhenFinalConfirm(String id,User user);
    public void rejectPurhcaseOrderWhenFinalConfirm(String id,User user, String comment);

    /**
     * confirm Purchase Order
     * @param po
     * @param itemList
     */
    public void confirmPurchaseOrder(PurchaseOrder po, List itemList,User user);


    /**
     * cancel purchase order item with specified quantity
     * @param poi
     * @param cancelQuantity
     */
    public void cancelPurchaseOrderItemQuantity(PurchaseOrderItem poi, int cancelQuantity);

    /**
     * get Requestors Of Purchase Order
     * @param po
     * @return
     */
    public Set getRequestorsOfPurchaseOrder(PurchaseOrder po);

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
     * withdraw PurchaseOrder
     * @param po
     */
    public void withdrawPurchaseOrder(PurchaseOrder po,User user);

    public void updatePurchaseOrderInspector(PurchaseOrder oldPo, User inspector,User user);

}
