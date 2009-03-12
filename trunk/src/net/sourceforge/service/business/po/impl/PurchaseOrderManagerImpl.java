package net.sourceforge.service.business.po.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sourceforge.dao.business.po.PurchaseOrderApproveRequestDAO;
import net.sourceforge.dao.business.po.PurchaseOrderDAO;
import net.sourceforge.dao.business.po.PurchaseOrderItemReceiptDAO;
import net.sourceforge.dao.business.pr.PurchaseRequestDAO;
import net.sourceforge.model.admin.User;
import net.sourceforge.model.business.BaseApproveRequest;
import net.sourceforge.model.business.po.PaymentScheduleDetail;
import net.sourceforge.model.business.po.PurchaseOrder;
import net.sourceforge.model.business.po.PurchaseOrderApproveRequest;
import net.sourceforge.model.business.po.PurchaseOrderAttachment;
import net.sourceforge.model.business.po.PurchaseOrderHistory;
import net.sourceforge.model.business.po.PurchaseOrderHistoryItem;
import net.sourceforge.model.business.po.PurchaseOrderItem;
import net.sourceforge.model.business.po.PurchaseOrderItemAttachment;
import net.sourceforge.model.business.po.PurchaseOrderItemReceipt;
import net.sourceforge.model.business.po.PurchaseOrderItemRecharge;
import net.sourceforge.model.business.po.query.PurchaseOrderItemQueryOrder;
import net.sourceforge.model.business.po.query.PurchaseOrderQueryOrder;
import net.sourceforge.model.business.pr.Capex;
import net.sourceforge.model.business.pr.PurchaseRequest;
import net.sourceforge.model.business.pr.YearlyBudget;
import net.sourceforge.model.metadata.ApproverDelegateType;
import net.sourceforge.model.metadata.ExportStatus;
import net.sourceforge.model.metadata.PurchaseOrderStatus;
import net.sourceforge.model.metadata.PurchaseRequestStatus;
import net.sourceforge.service.BaseManager;
import net.sourceforge.service.admin.SystemLogManager;
import net.sourceforge.service.business.ApproveRelativeEmailManager;
import net.sourceforge.service.business.po.PurchaseOrderManager;
import net.sourceforge.service.business.pr.CapexManager;
import net.sourceforge.service.business.pr.YearlyBudgetManager;
import net.sourceforge.service.business.rule.FlowManager;
import com.shcnc.utils.UUID;

public class PurchaseOrderManagerImpl extends BaseManager implements PurchaseOrderManager {
    private PurchaseOrderDAO dao;

    private PurchaseRequestDAO purchaseRequestDAO;

    private FlowManager flowManager;
    
    private SystemLogManager systemLogManager;
    
    private YearlyBudgetManager yearlyBudgetManager;

    private CapexManager capexManager;
    
    private PurchaseOrderItemReceiptDAO purchaseOrderItemReceiptDAO;

    private ApproveRelativeEmailManager approveRelativeEmailManager;
    
    private PurchaseOrderApproveRequestDAO purchaseOrderApproveRequestDAO;

    public void setCapexManager(CapexManager capexManager) {
        this.capexManager = capexManager;
    }

    public void setYearlyBudgetManager(YearlyBudgetManager yearlyBudgetManager) {
        this.yearlyBudgetManager = yearlyBudgetManager;
    }

    public void setSystemLogManager(SystemLogManager systemLogManager) {
        this.systemLogManager = systemLogManager;
    }

    public void setFlowManager(FlowManager flowManager) {
        this.flowManager = flowManager;
    }

    public void setPurchaseRequestDAO(PurchaseRequestDAO purchaseRequestDAO) {
        this.purchaseRequestDAO = purchaseRequestDAO;
    }

    // private String getNewPurchaseOrderCode(Site site, Date date) {
    // StringBuffer sb = new StringBuffer("PO");
    // String siteId = site.getId().toString();
    // for (int i = 0; i < 3 - siteId.length(); i++)
    // sb.append('0');
    // sb.append(siteId);
    // sb.append(StringUtils.right(ActionUtils.get8CharsFromDate(date), 6));
    // String prefix = sb.toString();
    // String maxId = dao.getMaxPurchaseOrderIdBeginWith(prefix);
    //
    // int serialNo = 1;
    // if (maxId != null) {
    // Integer maxSN = ActionUtils.parseInt(StringUtils.right(maxId, 5));
    // if (maxSN == null)
    // throw new RuntimeException("max serial no. is not digit");
    // serialNo = maxSN.intValue() + 1;
    // }
    // String sn = String.valueOf(serialNo);
    // for (int i = 0; i < 5 - sn.length(); i++)
    // sb.append('0');
    // sb.append(sn);
    // return sb.toString();
    // }

    public void setPurchaseOrderApproveRequestDAO(PurchaseOrderApproveRequestDAO purchaseOrderApproveRequestDAO) {
        this.purchaseOrderApproveRequestDAO = purchaseOrderApproveRequestDAO;
    }

    public void setApproveRelativeEmailManager(ApproveRelativeEmailManager approveRelativeEmailManager) {
        this.approveRelativeEmailManager = approveRelativeEmailManager;
    }

    public void setPurchaseOrderDAO(PurchaseOrderDAO dao) {
        this.dao = dao;
    }

    public PurchaseOrder getPurchaseOrder(String id) {
        return dao.getPurchaseOrder(id);
    }

    public PurchaseOrder updatePurchaseOrder(PurchaseOrder function) {
        return dao.updatePurchaseOrder(function);
    }

    public PurchaseOrder insertPurchaseOrder(PurchaseOrder function) {
        return dao.insertPurchaseOrder(function);
    }

    public int getPurchaseOrderListCount(Map conditions) {
        return dao.getPurchaseOrderListCount(conditions);
    }

    public List getPurchaseOrderList(Map conditions, int pageNo, int pageSize, PurchaseOrderQueryOrder order, boolean descend) {
        return dao.getPurchaseOrderList(conditions, pageNo, pageSize, order, descend);
    }

    public PurchaseOrderItemAttachment getPurchaseOrderItemAttachment(Integer id) {
        return dao.getPurchaseOrderItemAttachment(id);
    }

    public InputStream getPurchaseRequestItemAttachmentContent(Integer id) {
        return dao.getPurchaseRequestItemAttachmentContent(id);
    }

    public PurchaseOrderItemAttachment insertPurchaseOrderItemAttachment(PurchaseOrderItemAttachment poia, InputStream inputStream) {
        return dao.insertPurchaseOrderItemAttachment(poia, inputStream);
    }

    public PurchaseOrderItem getPurchaseOrderItem(Integer id) {
        return dao.getPurchaseOrderItem(id);
    }

    public PurchaseOrderItem getPurchaseOrderItemWithDetails(Integer id) {
        PurchaseOrderItem poi = dao.getPurchaseOrderItem(id);
        poi.setAttachments(this.getPurchaseOrderItemAttachments(poi));
        poi.setRecharges(this.getPurchaseOrderItemRecharges(poi));
        return poi;
    }

    public Collection getPurchaseOrderItemRecharges(PurchaseOrderItem poi) {
        return dao.getPurchaseOrderItemRecharges(poi);
    }

    public Collection getPurchaseOrderItemAttachments(PurchaseOrderItem poi) {
        return dao.getPurchaseOrderItemAttachments(poi);
    }

    public List getPurchaseOrderItemListWithDetails(PurchaseOrder po) {
        return dao.getPurchaseOrderItemListWithDetails(po);
    }

    public List getPurchaseOrderAttachmentList(PurchaseOrder po) {
        return dao.getPurchaseOrderAttachmentList(po);
    }

    public List getPurchaseOrderItemList(PurchaseOrder po) {
        return dao.getPurchaseOrderItemList(po);
    }

    public PurchaseOrderAttachment getPurchaseOrderAttachment(Integer id) {
        return dao.getPurchaseOrderAttachment(id);
    }

    public List getPaymentScheduleDetailList(PurchaseOrder po) {
        return dao.getPaymentScheduleDetailList(po);
    }

    public PaymentScheduleDetail getPaymentScheduleDetail(Integer id) {
        return dao.getPaymentScheduleDetail(id);
    }

    public void consolidatePurchaseOrder(String[] po_ids,User user) {
        if (po_ids.length < 2)
            throw new RuntimeException("po list size < 2");
        List poList = new ArrayList();
        BigDecimal sumAmount=new BigDecimal(0d);
        for (int i = 0; i < po_ids.length; i++) {
            PurchaseOrder po = this.getPurchaseOrder(po_ids[i]);
            if (po == null)
                throw new RuntimeException("purchaseOrder notFound");
            if (!po.isEditable())
                throw new RuntimeException("purchaseOrder notEditable");
            poList.add(po);
            sumAmount=sumAmount.add(po.getAmount());
        }

        PurchaseOrder firstPO = (PurchaseOrder) poList.get(0);
        firstPO.setAmount(sumAmount);
        for (Iterator iter = poList.iterator(); iter.hasNext();) {
            PurchaseOrder po = (PurchaseOrder) iter.next();
            if (!po.isEditable())
                new RuntimeException("po not editable");
            if (!po.getSupplier().equals(firstPO.getSupplier()))
                new RuntimeException("supplier not same");
            if (!po.getExchangeRate().equals(firstPO.getExchangeRate()))
                new RuntimeException("exchangerate not same");
            if (!po.getSite().equals(firstPO.getSite()))
                new RuntimeException("site not same");
            if (!po.getSubCategory().equals(firstPO.getSubCategory()))
                new RuntimeException("subCategory not same");
        }

        for (Iterator iter = poList.iterator(); iter.hasNext();) {
            PurchaseOrder po = (PurchaseOrder) iter.next();
            dao.deletePaymentScheduleDetails(po);
        }

        poList.remove(0);

        for (Iterator iter = poList.iterator(); iter.hasNext();) {
            PurchaseOrder po = (PurchaseOrder) iter.next();
            List poItemList = dao.getPurchaseOrderItemList(po);
            for (Iterator iterator = poItemList.iterator(); iterator.hasNext();) {
                PurchaseOrderItem poi = (PurchaseOrderItem) iterator.next();
                poi.setPurchaseOrder(firstPO);
                dao.updatePurchaseOrderItem(poi);
            }

            List attachmentList = dao.getPurchaseOrderAttachmentList(po);
            for (Iterator iterator = attachmentList.iterator(); iterator.hasNext();) {
                PurchaseOrderAttachment poa = (PurchaseOrderAttachment) iterator.next();
                poa.setPurchaseOrder(firstPO);
                dao.updatePurchaseOrderAttachment(poa);
            }

            dao.deletePurchaseOrderHisotries(po);
            dao.deletePurchaseOrder(po);
        }
        firstPO.setConsolidateIds(this.getPOIds(po_ids));
        systemLogManager.generateLog(null,firstPO,PurchaseOrder.LOG_ACTION_CONSOLIDATE,user);
    }
    
    private String getPOIds(String[] po_ids)
    {
        StringBuffer sb=new StringBuffer();
        for (int i = 0; i < po_ids.length; i++) {
            sb.append(po_ids[i]);
            sb.append(',');
        }
        return sb.toString();
    }

    public void updateErpNo(List poList, String[] newErpNo) {
        if (poList.size() != newErpNo.length)
            throw new RuntimeException("length of poList not equal length of newErpNo");
        int i = 0;
        for (Iterator iter = poList.iterator(); iter.hasNext();) {
            PurchaseOrder po = (PurchaseOrder) iter.next();
            po.setErpNo(newErpNo[i++]);
            dao.updatePurchaseOrder(po);
        }
    }

    public void updatePurchaseOrder(PurchaseOrder po,List oldItemList, List itemList, List attachmentList, List paymentScheduleDetailList) {
        dao.updatePurchaseOrder(po);

        List oldItemIdList = dao.getPurchaseOrderItemIdList(po);

        BigDecimal amount = new BigDecimal(0d);

        for (Iterator iter = itemList.iterator(); iter.hasNext();) {
            PurchaseOrderItem poi = (PurchaseOrderItem) iter.next();
            amount = amount.add(poi.getAmount());

            // update purchaseOrderItem
            poi.setPurchaseOrder(po);
            dao.updatePurchaseOrderItem(poi);
            oldItemIdList.remove(poi.getId());

            updatePurchaseOrderItemRecharges(poi);
            updatePurchaseOrderItemAttachments(poi);
            
            this.updateBudgetAmount(poi.getPurchaseRequestItem().getPurchaseRequest(),
                    this.getOldAmount(poi,oldItemList),poi.getBaseCurrencyAmount());
        }

        po.setAmount(amount);
        dao.updatePurchaseOrder(po);

        for (Iterator iter = oldItemIdList.iterator(); iter.hasNext();) {
            Integer itemId = (Integer) iter.next();
            PurchaseOrderItem poi = dao.getPurchaseOrderItem(itemId);
            poi.setPurchaseOrder(null);
            dao.updatePurchaseOrderItem(poi);

            PurchaseRequest pr = poi.getPurchaseRequestItem().getPurchaseRequest();
            if (PurchaseRequestStatus.BOOKED.equals(pr.getStatus())) {
                pr.setStatus(PurchaseRequestStatus.APPROVED);
                purchaseRequestDAO.updatePurchaseRequest(pr);
            }
            
            this.updateBudgetAmount(poi.getPurchaseRequestItem().getPurchaseRequest(),
                    this.getOldAmount(poi,oldItemList),poi.getBaseCurrencyAmount());
        }

        updatePurchaseOrderAttachments(po, attachmentList);
        updatePaymentScheduleDetails(po, paymentScheduleDetailList);
        
    }
    
    
    private BigDecimal getOldAmount(PurchaseOrderItem poi, List oldPoItemList) {
        for (Iterator iter = oldPoItemList.iterator(); iter.hasNext();) {
            PurchaseOrderItem aPoi = (PurchaseOrderItem) iter.next();
            if(aPoi.equals(poi)) return aPoi.getBaseCurrencyAmount();
        }
        throw new RuntimeException("old poi not found");
    }

    private void updateBudgetAmount(PurchaseRequest pr,BigDecimal oldAmount,BigDecimal newAmount)
    {
        if (!oldAmount.equals(newAmount)) {
            if (pr.getYearlyBudget() != null) {
                YearlyBudget yb = yearlyBudgetManager.getYearlyBudget(pr.getYearlyBudget().getId());
                yb.updateActualAmount(oldAmount, newAmount);
                yearlyBudgetManager.updateAndNotifyYearlyBudget(yb, true);
            } else if (pr.getCapex() != null) {
                Capex capex = capexManager.getCapex(pr.getCapex().getId());
                capex.updateActualAmount(oldAmount, newAmount);
                capexManager.updateAndNotifyCapex(capex, true);
            }
        }
    }


    public void updatePurchaseOrder(PurchaseOrder oldPO,PurchaseOrder po,List oldItemList, List itemList, List attachmentList, List paymentScheduleDetailList, List poarList, boolean isDraft,User user) {
        if (isDraft) {
            po.setStatus(PurchaseOrderStatus.DRAFT);
            po.setApproveRequestId(null);
            
            
        } else {
            po.setStatus(PurchaseOrderStatus.PENDING);
            po.setRequestDate(new Date());
            String approveRequestId = UUID.getUUID();
            po.setApproveRequestId(approveRequestId);
            for (Iterator iter = poarList.iterator(); iter.hasNext();) {
                PurchaseOrderApproveRequest poar = (PurchaseOrderApproveRequest) iter.next();
                poar.setApproveRequestId(approveRequestId);
                dao.insertPurchaseOrderApproveRequest(poar);
            }
        }
        this.updatePurchaseOrder(po,oldItemList, itemList, attachmentList, paymentScheduleDetailList);
        
        systemLogManager.generateLog(oldPO,po,PurchaseOrder.LOG_ACTION_MODIFY,user);
        if (!isDraft) {
            systemLogManager.generateLog(null,po,PurchaseOrder.LOG_ACTION_SUBMIT,user);
            approveRelativeEmailManager.sendApprovalEmail(po, ApproverDelegateType.PURCHASE_ORDER_APPROVER, (BaseApproveRequest)poarList.get(0));
        }
    }

    private void updatePaymentScheduleDetails(PurchaseOrder po, List paymentScheduleDetailList) {
        List oldPsdIdList = dao.getPaymentScheduleDetailIdList(po);
        for (Iterator iter = paymentScheduleDetailList.iterator(); iter.hasNext();) {
            PaymentScheduleDetail psd = (PaymentScheduleDetail) iter.next();
            psd.setPurchaseOrder(po);
            if (psd.getId().intValue() < 0) {// insert
                psd.clearId();
                dao.insertPaymentScheduleDetail(psd);
            } else {// update
                dao.updatePaymentScheduleDetail(psd);
                oldPsdIdList.remove(psd.getId());
            }
        }
        for (Iterator iter = oldPsdIdList.iterator(); iter.hasNext();) {
            Integer psdId = (Integer) iter.next();
            dao.deletePaymentScheduleDetail(psdId);
        }

    }

    public void updatePurchaseOrderItemRecharges(PurchaseOrderItem poi) {
        dao.deletePurchaseOrderItemRecharges(poi);
        for (Iterator iterator = poi.getRecharges().iterator(); iterator.hasNext();) {
            PurchaseOrderItemRecharge poir = (PurchaseOrderItemRecharge) iterator.next();
            dao.insertPurchaseOrderItemRecharge(poir);
        }
    }

    private void updatePurchaseOrderItemAttachments(PurchaseOrderItem poi) {
        List oldItemAttachmentIdList = dao.getPurchaseOrderItemAttachmentIdList(poi);
        for (Iterator iterator = poi.getAttachments().iterator(); iterator.hasNext();) {
            PurchaseOrderItemAttachment poia = (PurchaseOrderItemAttachment) iterator.next();
            poia.setPurchaseOrderItem(poi);
            dao.updatePurchaseOrderItemAttachment(poia);
            oldItemAttachmentIdList.remove(poia.getId());
        }
        for (Iterator iterator = oldItemAttachmentIdList.iterator(); iterator.hasNext();) {
            Integer itemAttachmentId = (Integer) iterator.next();
            dao.deletePurchaseOrderItemAttachment(itemAttachmentId);
        }
    }

    private void updatePurchaseOrderAttachments(PurchaseOrder po, List attachmentList) {
        List oldAttachmentIdList = dao.getPurchaseOrderAttachmentIdList(po);
        for (Iterator iterator = attachmentList.iterator(); iterator.hasNext();) {
            PurchaseOrderAttachment poa = (PurchaseOrderAttachment) iterator.next();
            poa.setPurchaseOrder(po);
            dao.updatePurchaseOrderAttachment(poa);
            oldAttachmentIdList.remove(poa.getId());
        }
        for (Iterator iterator = oldAttachmentIdList.iterator(); iterator.hasNext();) {
            Integer attachmentId = (Integer) iterator.next();
            dao.deletePurchaseOrderAttachment(attachmentId);
        }
    }

    public PurchaseOrderAttachment insertPurchaseOrderAttachment(PurchaseOrderAttachment pra, InputStream inputStream) {
        return dao.insertPurchaseOrderAttachment(pra, inputStream);
    }

    public InputStream getPurchaseOrderAttachmentContent(Integer id) {
        return dao.getPurchaseOrderAttachmentContent(id);
    }

    public List getPurchaseOrderApproveRequestList(PurchaseOrder po) {
        return dao.getPurchaseOrderApproveRequestList(po);
    }

    public void cancelPurchaseOrder(String id) {
        PurchaseOrder po = this.getPurchaseOrder(id);
        if (po == null)
            throw new RuntimeException("po not found!");
        if (!po.isEditable())
            throw new RuntimeException("po not editable!");
        List poItemList = dao.getPurchaseOrderItemList(po);
        for (Iterator iter = poItemList.iterator(); iter.hasNext();) {
            PurchaseOrderItem poi = (PurchaseOrderItem) iter.next();
            poi.setPurchaseOrder(null);
            dao.updatePurchaseOrderItem(poi);

            PurchaseRequest pr = poi.getPurchaseRequestItem().getPurchaseRequest();
            if (PurchaseRequestStatus.BOOKED.equals(pr.getStatus())) {
                pr.setStatus(PurchaseRequestStatus.APPROVED);
                purchaseRequestDAO.updatePurchaseRequest(pr);
            }
        }
        dao.deletePurchaseOrderAttachments(po);
        dao.deletePaymentScheduleDetails(po);
        dao.deletePurchaseOrderHisotries(po);
        dao.deletePurchaseOrder(po);
    }

    public void approvePurchaseOrder(PurchaseOrder purchaseOrder,User user) {
        PurchaseOrderStatus status = purchaseOrder.getStatus();
        if (!PurchaseOrderStatus.PENDING.equals(status)) {
            /*
             * 只有PENDING状态的PurchaseOrder才可以通过审批
             */
            throw new RuntimeException("try to approve a purchase order with status " + status.getEngShortDescription());
        }
        /*
         * 通过该PurchaseOrder
         */
        purchaseOrder.setStatus(PurchaseOrderStatus.APPROVED);
        purchaseOrder.setApproveDate(new Date());
        dao.updatePurchaseOrder(purchaseOrder);
        approveRelativeEmailManager.sendApprovedEmail(purchaseOrder);
    }

    public void rejectPurchaseOrder(PurchaseOrder purchaseOrder,User user, String comment) {
        PurchaseOrderStatus status = purchaseOrder.getStatus();
        if (PurchaseOrderStatus.APPROVED.equals(status)) {
        } else if (!PurchaseOrderStatus.PENDING.equals(status)) {
            // 只有PENDING和APPROVED状态的PurchaseOrder才可以拒绝
            throw new RuntimeException("try to reject a purchase order with status " + status.getEngShortDescription());
        }
        purchaseOrder.setStatus(PurchaseOrderStatus.REJECTED);
        dao.updatePurchaseOrder(purchaseOrder);
        // 创建history
        PurchaseOrderHistory history = new PurchaseOrderHistory();
        history.setPurchaseOrder(purchaseOrder);
        history.setAmount(purchaseOrder.getAmount());
        history.setApproveRequestId(purchaseOrder.getApproveRequestId());
        history.setExchangeRate(purchaseOrder.getExchangeRateValue());
        history.setStatus(purchaseOrder.getStatus());
        dao.savePurchaseOrderHistory(history);
        List itemList = dao.getPurchaseOrderItemList(purchaseOrder);
        for (Iterator itor = itemList.iterator(); itor.hasNext();) {
            PurchaseOrderItem item = (PurchaseOrderItem) itor.next();
            PurchaseOrderHistoryItem historyItem = new PurchaseOrderHistoryItem();
            historyItem.setPurchaseOrderHistory(history);
            historyItem.setBuyForDepartment(item.getBuyForDepartment());
            historyItem.setBuyForUser(item.getBuyForUser());
            historyItem.setDueDate(item.getDueDate());
            historyItem.setExchangeRate(item.getExchangeRate());
            historyItem.setIsRecharge(item.getIsRecharge());
            historyItem.setItem(item.getItem());
            historyItem.setItemSpec(item.getItemSpec());
            historyItem.setPurchaseType(item.getPurchaseType());
            historyItem.setQuantity(item.getQuantity());
            historyItem.setSupplier(item.getSupplier());
            historyItem.setUnitPrice(item.getUnitPrice());
            dao.savePurchaseOrderHistoryItem(historyItem);
        }
        systemLogManager.generateLog(null,purchaseOrder,PurchaseOrder.LOG_ACTION_REJECT,user);
        approveRelativeEmailManager.sendRejectedEmail(purchaseOrder, user.getName(), comment);
    }

    public PurchaseOrder getPurchaseOrderByApproveRequestId(String approveRequestId) {
        return dao.getPurchaseOrderByApproveRequestId(approveRequestId);
    }

    public void rejectPurhcaseOrderWhenFinalConfirm(String id,User user) {
        String comment = "Rejected by final confirm";
        rejectPurhcaseOrderWhenFinalConfirm(id, user, comment);
    }
    
    public void rejectPurhcaseOrderWhenFinalConfirm(String id,User user, String comment) {
        PurchaseOrder po = this.getPurchaseOrder(id);
        if (po == null)
            throw new RuntimeException("purchaseOrder not founf");
        if (!po.getStatus().equals(PurchaseOrderStatus.APPROVED))
            throw new RuntimeException("purchaseOrder not approved");
        insertRejectApproveRequest(purchaseOrderApproveRequestDAO, po, user, comment);
        this.rejectPurchaseOrder(po,user, comment);
    }
    
    public void confirmPurchaseOrder(PurchaseOrder po, List itemList,User user) {
        if (!po.getStatus().equals(PurchaseOrderStatus.APPROVED))
            throw new RuntimeException("purchaseOrder not approved");
        
        po.setConfirmer(user);
        if (po.getSubCategory() != null) {
            po.setStatus(PurchaseOrderStatus.CONFIRMED);
        } else {
            po.setStatus(PurchaseOrderStatus.RECEIVED);
        }
        po.setConfirmDate(new Date());
        dao.updatePurchaseOrder(po);
        for (Iterator iter = itemList.iterator(); iter.hasNext();) {
            PurchaseOrderItem poi = (PurchaseOrderItem) iter.next();
            dao.updatePurchaseOrderItem(poi);
            this.updatePurchaseOrderItemRecharges(poi);
            if (po.getSubCategory() == null) {
                //insert po item receipt for air ticket
                PurchaseOrderItemReceipt purchaseOrderItemReceipt = new PurchaseOrderItemReceipt();
                purchaseOrderItemReceipt.setPurchaseOrderItem(poi);
                purchaseOrderItemReceipt.setReceiveDate1(new Date());
                purchaseOrderItemReceipt.setReceiveDate2(new Date());
                purchaseOrderItemReceipt.setReceiveQty1(new Integer(1));
                purchaseOrderItemReceipt.setReceiveQty2(new Integer(1));
                purchaseOrderItemReceipt.setReceiver1(poi.getBuyForUser());
                purchaseOrderItemReceipt.setReceiver2(po.getConfirmer());
                purchaseOrderItemReceipt.setExportStatus(ExportStatus.UNEXPORTED);
                
                purchaseOrderItemReceiptDAO.insertPurchaseOrderItemReceipt(purchaseOrderItemReceipt);
            }
        }
        if (po.getSubCategory() != null) {
            flowManager.executeNotifyFlow(po);
        }
        
        systemLogManager.generateLog(null,po,PurchaseOrder.LOG_ACTION_FINALCONFIRM,user);
    }

    public void cancelPurchaseOrderItemQuantity(PurchaseOrderItem poi, int cancelQuantity) {
        poi.setCancelledQuantity(new Integer(poi.getCancelledQuantity().intValue() + cancelQuantity));
        poi = dao.updatePurchaseOrderItem(poi);
        BigDecimal cancelAmount = poi.getUnitPrice().multiply(new BigDecimal(cancelQuantity)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        BigDecimal changedBudget = cancelAmount.multiply(poi.getExchangeRateValue()).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        PurchaseRequest pr = poi.getPurchaseRequestItem().getPurchaseRequest();
        Capex c = pr.getCapex();
        if (c != null) {
            c.setActualAmount(c.getActualAmount().subtract(changedBudget));
            capexManager.updateAndNotifyCapex(c, true);
        } else {
            YearlyBudget yb = pr.getYearlyBudget();
            if (yb != null) {
                yb.setActualAmount(yb.getActualAmount().subtract(changedBudget));
                yearlyBudgetManager.updateAndNotifyYearlyBudget(yb, true);
            }
        }
        PurchaseOrder po = poi.getPurchaseOrder();        
        if (ExportStatus.EXPORTED.equals(po.getExportStatus())) {
            po = dao.getPurchaseOrder(po.getId());
            po.setExportStatus(ExportStatus.NEEDREEXPORT);
            dao.updatePurchaseOrder(po);
        }
    }

    public Set getRequestorsOfPurchaseOrder(PurchaseOrder po) {
        Set retVal = new HashSet();
        List requestorList = dao.getRequestorListOfPurchaseOrder(po);
        for (Iterator iter = requestorList.iterator(); iter.hasNext();) {
            User requestor = (User) iter.next();
            retVal.add(requestor);
        }
        return retVal;
    }

    public List getPurchaseOrderItemList(Map conditions, int pageNo, int pageSize, PurchaseOrderItemQueryOrder order, boolean descend) {
        return dao.getPurchaseOrderItemList(conditions, pageNo, pageSize, order, descend);
    }

    public int getPurchaseOrderItemListCount(Map conditions) {
        return dao.getPurchaseOrderItemListCount(conditions);
    }

    public void withdrawPurchaseOrder(PurchaseOrder po,User user) {
        if (!po.getStatus().equals(PurchaseOrderStatus.PENDING))
            throw new RuntimeException("status error");

        po.setStatus(PurchaseOrderStatus.DRAFT);
        po.setApproveRequestId(null);
        dao.updatePurchaseOrder(po);

        dao.deletePurchaseOrderApproveRequest(po);
        approveRelativeEmailManager.deleteWithdrawEmail(po);
        systemLogManager.generateLog(null,po,PurchaseOrder.LOG_ACTION_WITHDRAW,user);
    }

    public void updatePurchaseOrderInspector(PurchaseOrder oldPo, User inspector,User user) {
        if(oldPo.getInspector().equals(inspector)) return;
        PurchaseOrder po=this.getPurchaseOrder(oldPo.getId());
        po.setInspector(inspector);
        dao.updatePurchaseOrder(po);
        
        List poirList=purchaseOrderItemReceiptDAO.getPurchaseOrderItemReceiptList(Collections.EMPTY_MAP,0,-1,null,false);
        for (Iterator iter = poirList.iterator(); iter.hasNext();) {
            PurchaseOrderItemReceipt poir = (PurchaseOrderItemReceipt) iter.next();
            poir.setReceiver2(inspector);
            purchaseOrderItemReceiptDAO.updatePurchaseOrderItemReceipt(poir);
        }
        
        systemLogManager.generateLog(oldPo,po,PurchaseOrder.LOG_CHANGE_INSPECTOR,user);
    }


    public void setPurchaseOrderItemReceiptDAO(PurchaseOrderItemReceiptDAO purchaseOrderItemReceiptDAO) {
        this.purchaseOrderItemReceiptDAO = purchaseOrderItemReceiptDAO;
    }

}
