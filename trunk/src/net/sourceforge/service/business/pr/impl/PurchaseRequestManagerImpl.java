/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.service.business.pr.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sourceforge.dao.business.po.PurchaseOrderDAO;
import net.sourceforge.dao.business.pr.PurchaseRequestApproveRequestDAO;
import net.sourceforge.dao.business.pr.PurchaseRequestDAO;
import net.sourceforge.model.admin.ExchangeRate;
import net.sourceforge.model.admin.Site;
import net.sourceforge.model.admin.Supplier;
import net.sourceforge.model.admin.User;
import net.sourceforge.model.business.BaseApproveRequest;
import net.sourceforge.model.business.BaseRecharge;
import net.sourceforge.model.business.po.PurchaseOrder;
import net.sourceforge.model.business.po.PurchaseOrderItem;
import net.sourceforge.model.business.po.PurchaseOrderItemAttachment;
import net.sourceforge.model.business.po.PurchaseOrderItemRecharge;
import net.sourceforge.model.business.pr.Capex;
import net.sourceforge.model.business.pr.PurchaseRequest;
import net.sourceforge.model.business.pr.PurchaseRequestApproveRequest;
import net.sourceforge.model.business.pr.PurchaseRequestAttachment;
import net.sourceforge.model.business.pr.PurchaseRequestHistory;
import net.sourceforge.model.business.pr.PurchaseRequestHistoryItem;
import net.sourceforge.model.business.pr.PurchaseRequestItem;
import net.sourceforge.model.business.pr.PurchaseRequestItemAttachment;
import net.sourceforge.model.business.pr.PurchaseRequestItemRecharge;
import net.sourceforge.model.business.pr.PurchaseRequestPurchaser;
import net.sourceforge.model.business.pr.YearlyBudget;
import net.sourceforge.model.business.pr.query.PurchaseRequestQueryCondition;
import net.sourceforge.model.business.pr.query.PurchaseRequestQueryOrder;
import net.sourceforge.model.metadata.ApproverDelegateType;
import net.sourceforge.model.metadata.ExportStatus;
import net.sourceforge.model.metadata.PurchaseOrderStatus;
import net.sourceforge.model.metadata.PurchaseRequestStatus;
import net.sourceforge.model.metadata.RuleType;
import net.sourceforge.service.BaseManager;
import net.sourceforge.service.admin.EmailManager;
import net.sourceforge.service.admin.SystemLogManager;
import net.sourceforge.service.business.ApproveRelativeEmailManager;
import net.sourceforge.service.business.pr.CapexManager;
import net.sourceforge.service.business.pr.PurchaseRequestManager;
import net.sourceforge.service.business.pr.YearlyBudgetManager;
import net.sourceforge.service.business.rule.ExecuteFlowEmptyResultException;
import net.sourceforge.service.business.rule.FlowManager;
import net.sourceforge.service.business.rule.NoAvailableFlowToExecuteException;
import net.sourceforge.web.struts.action.ActionUtils;

import com.shcnc.struts.action.BackToInputActionException;
import com.shcnc.utils.UUID;

/**
 * manager implement for domain model PurchaseRequest
 * 
 * @author shilei
 * @version 1.0 (Dec 7, 2005)
 */
public class PurchaseRequestManagerImpl extends BaseManager implements PurchaseRequestManager {
    private PurchaseRequestDAO dao;

    private PurchaseOrderDAO purchaseOrderDAO;

    private YearlyBudgetManager yearlyBudgetManager;

    private CapexManager capexManager;

    private FlowManager flowManager;
    
    private SystemLogManager systemLogManager;
    
    private ApproveRelativeEmailManager approveRelativeEmailManager;
    
    private PurchaseRequestApproveRequestDAO purchaseRequestApproveRequestDAO;
    
    private EmailManager emailManager;
    
    public void setEmailManager(EmailManager emailManager) {
        this.emailManager = emailManager;
    }

    public void setSystemLogManager(SystemLogManager systemLogManager) {
        this.systemLogManager = systemLogManager;
    }

    public void setApproveRelativeEmailManager(ApproveRelativeEmailManager approveRelativeEmailManager) {
        this.approveRelativeEmailManager = approveRelativeEmailManager;
    }

    private String getNewPurchaseOrderCode(Site site, Date date) {
        StringBuffer sb = new StringBuffer("PO");
        String siteId = site.getId().toString();
        for (int i = 0; i < 3 - siteId.length(); i++)
            sb.append('0');
        sb.append(siteId);
        sb.append(StringUtils.right(ActionUtils.get8CharsFromDate(date), 6));
        String prefix = sb.toString();
        String maxId = purchaseOrderDAO.getMaxPurchaseOrderIdBeginWith(prefix);

        int serialNo = 1;
        if (maxId != null) {
            Integer maxSN = ActionUtils.parseInt(StringUtils.right(maxId, 5));
            if (maxSN == null)
                throw new RuntimeException("max serial no. is not digit");
            serialNo = maxSN.intValue() + 1;
        }
        String sn = String.valueOf(serialNo);
        for (int i = 0; i < 5 - sn.length(); i++)
            sb.append('0');
        sb.append(sn);
        return sb.toString();
    }

    private String getNewPurchaseRequestCode(Site site, Date date) {
        StringBuffer sb = new StringBuffer("PR");
        String siteId = site.getId().toString();
        for (int i = 0; i < 3 - siteId.length(); i++)
            sb.append('0');
        sb.append(siteId);
        sb.append(StringUtils.right(ActionUtils.get8CharsFromDate(date), 6));
        String prefix = sb.toString();
        String maxId = dao.getMaxPurchaseRequestIdBeginWith(prefix);

        int serialNo = 1;
        if (maxId != null) {
            Integer maxSN = ActionUtils.parseInt(StringUtils.right(maxId, 5));
            if (maxSN == null)
                throw new RuntimeException("max serial no. is not digit");
            serialNo = maxSN.intValue() + 1;
        }
        String sn = String.valueOf(serialNo);
        for (int i = 0; i < 5 - sn.length(); i++)
            sb.append('0');
        sb.append(sn);
        return sb.toString();
    }

    public void setPurchaseRequestDAO(PurchaseRequestDAO dao) {
        this.dao = dao;
    }

    /**
     * @param purchaseOrderDAO
     *            The purchaseOrderDAO to set.
     */
    public void setPurchaseOrderDAO(PurchaseOrderDAO purchaseOrderDAO) {
        this.purchaseOrderDAO = purchaseOrderDAO;
    }

    public void setPurchaseRequestApproveRequestDAO(PurchaseRequestApproveRequestDAO purchaseRequestApproveRequestDAO) {
        this.purchaseRequestApproveRequestDAO = purchaseRequestApproveRequestDAO;
    }

    /**
     * @param flowManager
     *            The flowManager to set.
     */
    public void setFlowManager(FlowManager flowManager) {
        this.flowManager = flowManager;
    }

    public PurchaseRequest getPurchaseRequest(String id) {
        return dao.getPurchaseRequest(id);
    }

    public PurchaseRequest updatePurchaseRequest(final PurchaseRequest pr, final List itemList, final List attachmentList, boolean firstSubmit) {
        dao.updatePurchaseRequest(pr);
        BigDecimal oldAmount = firstSubmit ? new BigDecimal(0d) : pr.getAmount();
        List oldItemIdList = dao.getPurchaseRequestItemIdList(pr);
        BigDecimal amount = new BigDecimal(0d);
        for (Iterator iter = itemList.iterator(); iter.hasNext();) {
            PurchaseRequestItem pri = (PurchaseRequestItem) iter.next();
            BigDecimal baseCurrencyAmount = pri.getBaseCurrencyAmount();
            if (firstSubmit && pri.getPurchaseOrderItem() != null) {
                oldAmount = oldAmount.add(baseCurrencyAmount);
            }
            amount = amount.add(baseCurrencyAmount);
            pri.setPurchaseRequest(pr);
            if (pri.getId().intValue() < 0) {// insert
                pri.clearId();
                dao.insertPurchaseRequestItem(pri);
                this.insertPurchaseRequesetItemRecharges(pri);
            } else {// update
                dao.updatePurchaseRequestItem(pri);
                oldItemIdList.remove(pri.getId());
                this.updatePurchaseRequesetItemRecharges(pri);
            }
            this.updatePurchaseRequesetItemAttachments(pri);
        }

        pr.setAmount(amount);
        pr.setMaxItemPrice(getMaxPriceOfPrItems(itemList));
        dao.updatePurchaseRequest(pr);
        
        if (!PurchaseRequestStatus.DRAFT.equals(pr.getStatus())) {
            this.updateBudgetAmount(pr,oldAmount,amount);
        }

        for (Iterator iter = oldItemIdList.iterator(); iter.hasNext();) {
            Integer itemId = (Integer) iter.next();
            dao.deletePurchaseRequestItem(itemId);
        }

        updatePurchaseRequesetAttachments(pr, attachmentList);
        return pr;
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

    private void updatePurchaseRequesetItemRecharges(PurchaseRequestItem pri) {
        dao.deletePurchaseRequestItemRecharges(pri);
        this.insertPurchaseRequesetItemRecharges(pri);
    }

    private void insertPurchaseRequesetItemRecharges(PurchaseRequestItem pri) {
        for (Iterator iterator = pri.getRecharges().iterator(); iterator.hasNext();) {
            PurchaseRequestItemRecharge prir = (PurchaseRequestItemRecharge) iterator.next();
            dao.insertPurchaseRequestItemRecharge(prir);
        }
    }

    private void updatePurchaseRequesetItemAttachments(PurchaseRequestItem pri) {
        List oldItemAttachmentIdList = dao.getPurchaseRequestItemAttachmentIdList(pri);
        for (Iterator iterator = pri.getAttachments().iterator(); iterator.hasNext();) {
            PurchaseRequestItemAttachment pria = (PurchaseRequestItemAttachment) iterator.next();
            pria.setPurchaseRequestItem(pri);
            dao.updatePurchaseRequestItemAttachment(pria);
            oldItemAttachmentIdList.remove(pria.getId());
        }
        for (Iterator iterator = oldItemAttachmentIdList.iterator(); iterator.hasNext();) {
            Integer itemAttachmentId = (Integer) iterator.next();
            dao.deletePurchaseRequestItemAttachment(itemAttachmentId);
        }
    }

    private void updatePurchaseRequesetAttachments(PurchaseRequest pr, List attachmentList) {
        List oldAttachmentIdList = dao.getPurchaseRequestAttachmentIdList(pr);

        for (Iterator iterator = attachmentList.iterator(); iterator.hasNext();) {
            PurchaseRequestAttachment pra = (PurchaseRequestAttachment) iterator.next();
            pra.setPurchaseRequest(pr);
            dao.updatePurchaseRequestAttachment(pra);
            oldAttachmentIdList.remove(pra.getId());
        }

        for (Iterator iterator = oldAttachmentIdList.iterator(); iterator.hasNext();) {
            Integer attachmentId = (Integer) iterator.next();
            dao.deletePurchaseRequestAttachment(attachmentId);
        }

    }

    public PurchaseRequest updatePurchaseRequest(final PurchaseRequest prData, final List itemList, final List attachmentList,
            //final List purchaseRequestApproveRequestList, 
            final boolean isDraft,User user) {
        List purchaseRequestApproveRequestList = null;
        PurchaseRequest pr = this.getPurchaseRequest(prData.getId());

        if (!pr.isEditable())
            throw new RuntimeException("not editable");

        pr.setTitle(prData.getTitle());
        pr.setDescription(prData.getDescription());

        if (isDraft) {
            pr.setStatus(PurchaseRequestStatus.DRAFT);
            pr.setApproveRequestId(null);
            pr= updatePurchaseRequest(pr, itemList, attachmentList, false);
        } else {
            pr.setRequestDate(new Date());
            pr.setStatus(PurchaseRequestStatus.PENDING);
            String approveRequestId = UUID.getUUID();
            pr.setApproveRequestId(approveRequestId);
            
            pr= updatePurchaseRequest(pr, itemList, attachmentList, true);
            
            try {
                purchaseRequestApproveRequestList = flowManager.executeApproveFlow(pr);
                
            } catch (ExecuteFlowEmptyResultException e) {
                throw new BackToInputActionException("flow.execute.notApproverFound");
            } catch (NoAvailableFlowToExecuteException e) {
                throw new BackToInputActionException("flow.execute.notFlowFound");
            }
            
            for (Iterator iter = purchaseRequestApproveRequestList.iterator(); iter.hasNext();) {
                PurchaseRequestApproveRequest prar = (PurchaseRequestApproveRequest) iter.next();
                prar.setApproveRequestId(approveRequestId);
                dao.insertPurchaseRequestApproveRequest(prar);
            }
        }
        
        if(!isDraft) {
            systemLogManager.generateLog(null,pr,PurchaseRequest.LOG_ACTION_SUBMIT,user);
            approveRelativeEmailManager.sendApprovalEmail(pr, ApproverDelegateType.PURCHASE_REQUEST_APPROVER, (BaseApproveRequest)purchaseRequestApproveRequestList.get(0));
        }
        
        return pr;
    }

    public PurchaseRequest insertPurchaseRequest(PurchaseRequest pr, User creator, User requestor) {
        pr.setCreateDate(new Date());
        pr.setCreator(creator);

        pr.setRequestDate(null);
        
        if (pr.getRequestor() == null) {
            pr.setRequestor(requestor);
        }

        pr.setAmount(new BigDecimal(0d));

        pr.setStatus(PurchaseRequestStatus.DRAFT);

        pr.setId(this.getNewPurchaseRequestCode(pr.getDepartment().getSite(), pr.getCreateDate()));

        pr= dao.insertPurchaseRequest(pr);
        
        return pr;
    }

    public int getPurchaseRequestListCount(Map conditions) {
        return dao.getPurchaseRequestListCount(conditions);
    }

    public List getPurchaseRequestList(Map conditions, int pageNo, int pageSize, PurchaseRequestQueryOrder order, boolean descend) {
        return dao.getPurchaseRequestList(conditions, pageNo, pageSize, order, descend);
    }

    public List getPurchaseRequestItemList(PurchaseRequest pr) {
        return dao.getPurchaseRequestItemList(pr);
    }

    public List getPurchaseRequestItemListWithDetails(PurchaseRequest pr) {
        return dao.getPurchaseRequestItemListWithDetails(pr);
    }

    public List getPurchaseRequestAttachmentList(PurchaseRequest pr) {
        return dao.getPurchaseRequestAttachmentList(pr);
    }

    public List getPurchaseRequestApproveRequestList(PurchaseRequest pr) {
        return dao.getPurchaseRequestApproveRequestList(pr);
    }

    public PurchaseRequestItemAttachment getPurchaseRequestItemAttachment(Integer id) {
        return dao.getPurchaseRequestItemAttachment(id);
    }

    public InputStream getPurchaseRequestItemAttachmentContent(Integer id) {
        return dao.getPurchaseRequestItemAttachmentContent(id);
    }

    public PurchaseRequestItemAttachment insertPurchaseRequestItemAttachment(PurchaseRequestItemAttachment pria, InputStream inputStream) {
        return dao.insertPurchaseRequestItemAttachment(pria, inputStream);
    }

    public PurchaseRequestAttachment insertPurchaseRequestAttachment(PurchaseRequestAttachment pra, InputStream inputStream) {
        return dao.insertPurchaseRequestAttachment(pra, inputStream);
    }

    public InputStream getPurchaseRequestAttachmentContent(Integer id) {
        return dao.getPurchaseRequestAttachmentContent(id);
    }

    public PurchaseRequestAttachment getPurchaseRequestAttachment(Integer id) {
        return dao.getPurchaseRequestAttachment(id);
    }

    public void deletePurchaseRequest(PurchaseRequest pr,User user) {
        if (this.purchaseRequestHasPOItem(pr))
            throw new RuntimeException("in use");
        
        systemLogManager.generateLog(null,pr,PurchaseRequest.LOG_ACTION_DELETE,user);
        dao.deletePurchaseRequest(pr);
    }

    public void approvePurchaseRequest(PurchaseRequest pr, User user) {
        Log log = LogFactory.getLog(PurchaseRequestManagerImpl.class);

        PurchaseRequestStatus status = pr.getStatus();
        if (!PurchaseRequestStatus.PENDING.equals(status)) {
            /*
             * 只有PENDING状态的PurchaseRequest才可以通过审批
             */
            throw new RuntimeException("try to approve a purchase request with status " + status.getEngShortDescription());
        }
        /*
         * 执行Purchase Flow，为PurchaseRequest确定purchaser
         */
        try {
            List purchaserList = flowManager.executePurchaseFlow(pr);
            for (Iterator itor = purchaserList.iterator(); itor.hasNext();) {
                PurchaseRequestPurchaser purchaser = (PurchaseRequestPurchaser) itor.next();
                dao.savePurchaseRequestPurchaser(purchaser);
                
                //send email to notify purchaser               
                Map context = new HashMap();
                context.put("x_pr", pr);
                context.put("purchaser_name", purchaser.getPurchaser().getName());
                context.put("role", EmailManager.EMAIL_ROLE_PURCHASER);
                emailManager.insertEmail(pr.getDepartment().getSite(), purchaser.getPurchaser().getEmail(), "PRAssignee.vm", context);
            }
        } catch (ExecuteFlowEmptyResultException e) {
            log.info("No purchaser deal with purchase request '" + pr.getId() + "', it was rejected.");
            String comment = "No purchaser deal with this purchase request";
            insertRejectApproveRequest(purchaseRequestApproveRequestDAO, pr, null, comment);
            rejectPurchaseRequest(pr, null, comment);
            return;
        } catch (NoAvailableFlowToExecuteException e) {
            log.info("No purchase flow available, purchase request '" + pr.getId() + "' was rejected.");
            String comment = "No purchase flow available";
            insertRejectApproveRequest(purchaseRequestApproveRequestDAO, pr, null, comment);
            rejectPurchaseRequest(pr, null, comment);
            return;
        }
        /*
         * 通过该PurchaseRequest
         */
        pr.setStatus(PurchaseRequestStatus.APPROVED);
        pr.setApproveDate(new Date());;
        
        
        dao.updatePurchaseRequest(pr);
        flowManager.executeNotifyFlow(pr);

        /*
         * 为每个pr_item创建相应的po_item.
         * PurchaseRequest可能被Purchaser退回，因此可能po_item已经创建过
         * 对已经创建过po_item的pr_item，不再创建此po_item
         */
        List itemList = dao.getPurchaseRequestItemList(pr);
        for (Iterator itor = itemList.iterator(); itor.hasNext();) {
            PurchaseRequestItem item = (PurchaseRequestItem) itor.next();
            PurchaseOrderItem poItem = item.getPurchaseOrderItem();
            if (poItem == null) {
                poItem = new PurchaseOrderItem();
                poItem.setPurchaseRequestItem(item);
                poItem.setQuantity(item.getQuantity());
                poItem.setBuyForDepartment(item.getBuyForDepartment());
                poItem.setBuyForUser(item.getBuyForUser());
                poItem.setDueDate(item.getDueDate());
                poItem.setExchangeRate(item.getExchangeRate());
                poItem.setIsRecharge(item.getIsRecharge());
                poItem.setItem(item.getSupplierItem());
                poItem.setItemSpec(item.getSupplierItemSepc());
                poItem.setSupplier(item.getSupplier());
                poItem.setSupplierName(item.getSupplierName());
                poItem.setUnitPrice(item.getUnitPrice());
                poItem.setCancelledQuantity(new Integer(0));
                poItem.setReceivedQuantity(new Integer(0));
                poItem.setReturnedQuantity(new Integer(0));
                poItem.setProjectCode(item.getProjectCode());
                purchaseOrderDAO.insertPurchaseOrderItem(poItem);
                for (Iterator itor2 = dao.getPurchaseRequestItemRecharges(item).iterator(); itor2.hasNext();) {
                    BaseRecharge poir = poItem.createNewRechargeObj();
                    poir.copyFrom((BaseRecharge) itor2.next());
                    purchaseOrderDAO.insertPurchaseOrderItemRecharge((PurchaseOrderItemRecharge) poir);
                }
                item.setPurchaseOrderItem(poItem);
                dao.updatePurchaseRequestItem(item);
            }
        }
        approveRelativeEmailManager.sendApprovedEmail(pr);
    }

    public void rejectPurchaseRequestByPurchaser(PurchaseRequest pr, User user) {
        rejectPurchaseRequestByPurchaser(pr, user, null);
    }
    
    public void rejectPurchaseRequestByPurchaser(PurchaseRequest pr, User user, String comment) {
        if (comment == null) {
            comment = "rejected by purchaser";
        }
        insertRejectApproveRequest(purchaseRequestApproveRequestDAO, pr, user, comment);
        rejectPurchaseRequest(pr, user, comment);
    }
    
    public void rejectPurchaseRequest(PurchaseRequest pr, User user, String comment) {
        pr = this.getPurchaseRequest(pr.getId());
        PurchaseRequestStatus status = pr.getStatus();
        List prItems = this.getPurchaseRequestItemList(pr);
        BigDecimal changedBudget = new BigDecimal(0d);
        if (PurchaseRequestStatus.APPROVED.equals(status)) {
            // Purchaser拒绝已经审批通过的PurchaseRequest，需要删除还未和po关联的po_item
            for (Iterator itor = prItems.iterator(); itor.hasNext(); ) {
                PurchaseRequestItem prItem = (PurchaseRequestItem) itor.next();
                PurchaseOrderItem poItem = prItem.getPurchaseOrderItem();
                if (poItem == null) {
                    changedBudget = changedBudget.add(prItem.getBaseCurrencyAmount());
                } else if (poItem.getPurchaseOrder() == null) {
                    changedBudget = changedBudget.add(poItem.getBaseCurrencyAmount());
                    purchaseOrderDAO.deletePurchaseOrderItemAttachments(poItem);
                    purchaseOrderDAO.deletePurchaseOrderItemRecharges(poItem);
                    purchaseOrderDAO.deletePurchaseOrderItem(poItem);
                    prItem.setPurchaseOrderItem(null);
                    dao.updatePurchaseRequestItem(prItem);
                }
            }
            // Purchaser拒绝已经审批通过的PurchaseRequest，需要删除purchaser
            dao.deletePurchaseRequestPurchaserByPurchaseRequest(pr);
        } else if (PurchaseRequestStatus.PENDING.equals(status)) {
            for (Iterator itor = prItems.iterator(); itor.hasNext(); ) {
                PurchaseRequestItem prItem = (PurchaseRequestItem) itor.next();
                PurchaseOrderItem poItem = prItem.getPurchaseOrderItem();
                if (poItem == null) {
                    changedBudget = changedBudget.add(prItem.getBaseCurrencyAmount());
                }
            }
        } else {
            // 只有PENDING和APPROVED状态的PurchaseRequest才可以拒绝
            throw new RuntimeException("try to reject a purchase request with status " + status.getEngShortDescription());
        }

        if (changedBudget.compareTo(new BigDecimal(0d)) != 0) {
            Capex c = pr.getCapex();
            if (c != null ) {
                c.setActualAmount(c.getActualAmount().subtract(changedBudget));
                capexManager.updateAndNotifyCapex(c, true);
            } else {
                YearlyBudget yb = pr.getYearlyBudget();
                if (yb != null) {
                    yb.setActualAmount(yb.getActualAmount().subtract(changedBudget));
                    yearlyBudgetManager.updateAndNotifyYearlyBudget(yb, true);
                }
            }
        }

        pr.setStatus(PurchaseRequestStatus.REJECTED);
        dao.updatePurchaseRequest(pr);
        // 创建history
        PurchaseRequestHistory history = new PurchaseRequestHistory();
        history.setPurchaseRequest(pr);
        history.setApproveRequestId(pr.getApproveRequestId());
        history.setStatus(pr.getStatus());
        dao.savePurchaseRequestHistory(history);
        List itemList = dao.getPurchaseRequestItemList(pr);
        for (Iterator itor = itemList.iterator(); itor.hasNext();) {
            PurchaseRequestItem item = (PurchaseRequestItem) itor.next();
            PurchaseRequestHistoryItem historyItem = new PurchaseRequestHistoryItem();
            historyItem.setPurchaseRequestHistory(history);
            historyItem.setBuyForDepartment(item.getBuyForDepartment());
            historyItem.setBuyForUser(item.getBuyForUser());
            historyItem.setDueDate(item.getDueDate());
            historyItem.setExchangeRate(item.getExchangeRate());
            historyItem.setExchangeRateValue(item.getExchangeRateValue());
            historyItem.setIsRecharge(item.getIsRecharge());
            historyItem.setQuantity(item.getQuantity());
            historyItem.setSupplier(item.getSupplier());
            historyItem.setSupplierItem(item.getSupplierItem());
            historyItem.setSupplierItemSepc(item.getSupplierItemSepc());
            historyItem.setSupplierName(item.getSupplierName());
            historyItem.setUnitPrice(item.getUnitPrice());
            dao.savePurchaseRequestHistoryItem(historyItem);
        }
        systemLogManager.generateLog(null,pr,PurchaseRequest.LOG_ACTION_REJECT,user);
        approveRelativeEmailManager.sendRejectedEmail(pr, user == null ? RuleType.PURCHASING_RULES.getEngDescription() : user.getName(), comment);
    }

    public PurchaseRequest getPurchaseRequestByApproveRequestId(String approveRequestId) {
        return dao.getPurchaseRequestByApproveRequestId(approveRequestId);
    }

    public PurchaseRequestItem getPurchaseRequestItem(Integer id) {
        return dao.getPurchaseRequestItem(id);
    }

    public PurchaseRequestItem getPurchaseRequestItemWithDetails(Integer id) {
        return dao.getPurchaseRequestItemWithDetails(id);
    }

    public List getPurchaseOrderItemList(PurchaseRequest pr) {
        return dao.getPurchaseOrderItemList(pr);
    }

    public List getPurchaseOrderItemListWithDetails(PurchaseRequest pr) {
        return dao.getPurchaseOrderItemListWithDetails(pr);
    }

    public void savePurchaseOrderItemsOfPurchaseRequest(PurchaseRequest pr,List oldPoItemList, List purchaseOrderItemList, User purchaseUser) {
        pr.setPurchaser(purchaseUser);
        dao.updatePurchaseRequest(pr);
        Map priPoisMap=new HashMap();
        for (Iterator iter = purchaseOrderItemList.iterator(); iter.hasNext();) {
            PurchaseOrderItem poi = (PurchaseOrderItem) iter.next();
            if (poi.getPurchaseOrder() != null)
                continue;// 已经创建purchaseOrder的poitem不能再被修改
            PurchaseRequestItem pri=poi.getPurchaseRequestItem();
            if(priPoisMap.get(pri)==null)
                priPoisMap.put(pri,new ArrayList());
            List poiList=(List) priPoisMap.get(pri);
            poiList.add(poi);
        }
        
        //split pri
        for (Iterator iter = priPoisMap.keySet().iterator(); iter.hasNext();) {
            PurchaseRequestItem pri=(PurchaseRequestItem) iter.next();
            dao.updatePurchaseRequestItem(pri);
            List poiList=(List) priPoisMap.get(pri);
            if(poiList.size()>1)
            {
                split(pri,poiList);
            }
        }
        
        for (Iterator iter = purchaseOrderItemList.iterator(); iter.hasNext();) {
            PurchaseOrderItem poi = (PurchaseOrderItem) iter.next();
            if (poi.getPurchaseOrder() != null)
                continue;// 已经创建purchaseOrder的poitem不能再被修改
            if (poi.getId().intValue() < 0)// split
            {
                poi.clearId();
                purchaseOrderDAO.insertPurchaseOrderItem(poi);
                this.updateBudgetAmount(pr,new BigDecimal(0d),poi.getBaseCurrencyAmount());    
                this.insertPurchaseOrderItemRecharges(poi);
            } else// update
            {
                purchaseOrderDAO.updatePurchaseOrderItem(poi);
                this.updateBudgetAmount(pr,getOldAmount(poi,oldPoItemList),poi.getBaseCurrencyAmount());
                this.updatePurchaseOrderItemRecharges(poi);
            }
            
            poi.getPurchaseRequestItem().setPurchaseOrderItem(poi);
            dao.updatePurchaseRequestItem(poi.getPurchaseRequestItem());
            
            this.updatePurchaseOrderItemAttachments(poi);
        }
        setPurchaseRequestStatusBookedIfAllHasPO(pr, purchaseOrderItemList);
    }
    
    private BigDecimal getOldAmount(PurchaseOrderItem poi, List oldPoItemList) {
        for (Iterator iter = oldPoItemList.iterator(); iter.hasNext();) {
            PurchaseOrderItem aPoi = (PurchaseOrderItem) iter.next();
            if(aPoi.equals(poi)) return aPoi.getBaseCurrencyAmount();
        }
        throw new RuntimeException("old poi not found");
    }

    private void split(PurchaseRequestItem oldPri,List poiList)
    {
        if(poiList.size()<=1) throw new RuntimeException("poiList size must > 0");
        
        Collection oldRechares=dao.getPurchaseRequestItemRecharges(oldPri);
        dao.deletePurchaseRequestItemRecharges(oldPri);
        
        int oldQuantity=oldPri.getQuantity();
        
        int i=0;
        for (Iterator iter = poiList.iterator(); iter.hasNext();i++) {
            PurchaseOrderItem poi = (PurchaseOrderItem) iter.next();
            PurchaseRequestItem newPri=null; 
            if(i==0)
            {
                newPri=oldPri;
                newPri.setQuantity(poi.getQuantity());
                dao.updatePurchaseRequestItem(newPri);
            }
            else
            {
                newPri=new PurchaseRequestItem();
                try {
                    PropertyUtils.copyProperties(newPri,oldPri);
                }
                catch(Exception e)
                {
                    throw new RuntimeException("copy error："+e);
                }
                newPri.setQuantity(poi.getQuantity());
                dao.insertPurchaseRequestItem(newPri);
            }
            poi.setPurchaseRequestItem(newPri);
            insertPurchaseRequestItemRecharges(newPri,oldRechares,oldQuantity);
        }
    }
    
    private void insertPurchaseRequestItemRecharges(PurchaseRequestItem newPri, Collection oldRechares, int oldQuantity) {
        for (Iterator iter = oldRechares.iterator(); iter.hasNext();) {
            PurchaseRequestItemRecharge oldPrir = (PurchaseRequestItemRecharge) iter.next();
            
            PurchaseRequestItemRecharge newPrir=new PurchaseRequestItemRecharge();
            try {
                PropertyUtils.copyProperties(newPrir,oldPrir);
            }
            catch(Exception e)
            {
                throw new RuntimeException("copy error："+e);
            }
            newPrir.setId(null);
            newPrir.setPurchaseRequestItem(newPri);
            BigDecimal oldAmount=newPrir.getAmount();
            double amount=oldAmount.doubleValue();
            amount = amount / oldQuantity * newPri.getQuantity();
            newPrir.setAmount(new BigDecimal(amount).setScale(oldAmount.scale(),BigDecimal.ROUND_HALF_UP));
            dao.insertPurchaseRequestItemRecharge(newPrir);
        }
    }


    private void updatePurchaseOrderItemAttachments(PurchaseOrderItem poi) {
        List oldItemAttachmentIdList = purchaseOrderDAO.getPurchaseOrderItemAttachmentIdList(poi);
        for (Iterator iterPoir = poi.getAttachments().iterator(); iterPoir.hasNext();) {
            PurchaseOrderItemAttachment poia = (PurchaseOrderItemAttachment) iterPoir.next();
            poia.setPurchaseOrderItem(poi);
            purchaseOrderDAO.updatePurchaseOrderItemAttachment(poia);
            oldItemAttachmentIdList.remove(poia.getId());
        }
        for (Iterator iterator = oldItemAttachmentIdList.iterator(); iterator.hasNext();) {
            Integer itemAttachmentId = (Integer) iterator.next();
            purchaseOrderDAO.deletePurchaseOrderItemAttachment(itemAttachmentId);
        }
    }

    private void updatePurchaseOrderItemRecharges(PurchaseOrderItem poi) {
        purchaseOrderDAO.deletePurchaseOrderItemRecharges(poi);
        this.insertPurchaseOrderItemRecharges(poi);
    }

    private void insertPurchaseOrderItemRecharges(PurchaseOrderItem poi) {
        for (Iterator iterator = poi.getRecharges().iterator(); iterator.hasNext();) {
            PurchaseOrderItemRecharge poir = (PurchaseOrderItemRecharge) iterator.next();
            purchaseOrderDAO.insertPurchaseOrderItemRecharge(poir);
        }

    }

    public void savePurchaseOrderItemsOfPurchaseRequestAndCreatePO(PurchaseRequest pr,List oldPoItemList, List purchaseOrderItemList, List selectedPurchaseOrderItemList,
            User purchaseUser) {
        this.savePurchaseOrderItemsOfPurchaseRequest(pr,oldPoItemList, purchaseOrderItemList, purchaseUser);
        if (selectedPurchaseOrderItemList != null && !selectedPurchaseOrderItemList.isEmpty()) {
            PurchaseOrderItem firstPoi = (PurchaseOrderItem) selectedPurchaseOrderItemList.get(0);

            ExchangeRate er = firstPoi.getExchangeRate();

            Supplier supplier = firstPoi.getSupplier();
            if (supplier == null)
                throw new RuntimeException("supplier can't be null");

            BigDecimal amount = new BigDecimal(0d);
            for (Iterator iter = selectedPurchaseOrderItemList.iterator(); iter.hasNext();) {
                PurchaseOrderItem poi = (PurchaseOrderItem) iter.next();

                if (poi.getPurchaseOrder() != null)
                    throw new RuntimeException("already savedPo");
                if (!er.equals(poi.getExchangeRate()))
                    throw new RuntimeException("different exchangeRate");
                if (!supplier.equals(poi.getSupplier()))
                    throw new RuntimeException("different supplier");

                amount = amount.add(poi.getAmount());
            }

            PurchaseOrder po = new PurchaseOrder();
            po.setAmount(amount);
            po.setBaseCurrency(pr.getDepartment().getSite().getBaseCurrency());
            po.setCreateDate(new Date());
            po.setCreateUser(purchaseUser);
            po.setExchangeRate(er);
            po.setExchangeRateValue(er.getExchangeRate());
            po.setExportStatus(ExportStatus.UNEXPORTED);
            po.setId(this.getNewPurchaseOrderCode(pr.getDepartment().getSite(), po.getCreateDate()));
            po.setPurchaser(purchaseUser);
            po.setSite(pr.getDepartment().getSite());
            po.setStatus(PurchaseOrderStatus.DRAFT);
            po.setSubCategory(pr.getPurchaseSubCategory());
            po.setSupplier(supplier);
            Iterator itor = selectedPurchaseOrderItemList.iterator();
            if (itor.hasNext()) {
                po.setTitle(pr.getTitle());
                po.setDescription(pr.getDescription());
            } else {
                po.setTitle("");
                po.setDescription("");
            }

            purchaseOrderDAO.insertPurchaseOrder(po);

            for (Iterator iter = selectedPurchaseOrderItemList.iterator(); iter.hasNext();) {
                PurchaseOrderItem poi = (PurchaseOrderItem) iter.next();
                poi.setPurchaseOrder(po);
                purchaseOrderDAO.updatePurchaseOrderItem(poi);
            }
            systemLogManager.generateLog(null,po,PurchaseOrder.LOG_ACTION_CREATE,purchaseUser);
        }
        setPurchaseRequestStatusBookedIfAllHasPO(pr, purchaseOrderItemList);
    }

    private void setPurchaseRequestStatusBookedIfAllHasPO(PurchaseRequest pr, List purchaseOrderItemList) {
        boolean allPO = true;
        for (Iterator iter = purchaseOrderItemList.iterator(); iter.hasNext();) {
            PurchaseOrderItem poi = (PurchaseOrderItem) iter.next();
            if (poi.getPurchaseOrder() == null) {
                allPO = false;
                break;
            }
        }
        if (allPO) {
            pr.setStatus(PurchaseRequestStatus.BOOKED);
            dao.updatePurchaseRequest(pr);
        }
    }

    public PurchaseRequest updatePurchaseRequest(PurchaseRequest pr) {
        return dao.updatePurchaseRequest(pr);
    }

    public boolean isPurchaseRequestItemInUse(PurchaseRequestItem pri) {
        return dao.isPurchaseRequestItemInUse(pri);

    }

    public void withdrawPurchaseRequest(PurchaseRequest pr,User user) {
        if (!pr.getStatus().equals(PurchaseRequestStatus.PENDING))
            throw new RuntimeException("status error");

        pr.setStatus(PurchaseRequestStatus.DRAFT);
        pr.setApproveRequestId(null);
        dao.updatePurchaseRequest(pr);
        
        this.updateBudgetAmount(pr, pr.getAmount(), new BigDecimal(0));
        
        dao.deletePurchaseRequestApproveRequest(pr);
        approveRelativeEmailManager.deleteWithdrawEmail(pr);
        systemLogManager.generateLog(null,pr,PurchaseRequest.LOG_ACTION_WITHDRAW,user);
    }

    public boolean purchaseRequestHasPOItem(PurchaseRequest pr) {
        return dao.purchaseRequestHasPOItem(pr);
    }

    public void setCapexManager(CapexManager capexManager) {
        this.capexManager = capexManager;
    }

    public void setYearlyBudgetManager(YearlyBudgetManager yearlyBudgetManager) {
        this.yearlyBudgetManager = yearlyBudgetManager;
    }

    public List getCalculatedPurchaseRequestItemByCapexId(String id) {
        List result = new ArrayList();
        Map conditions = new HashMap();
        conditions.put(PurchaseRequestQueryCondition.CAPEX_ID_EQ, id);
        List prList = dao.getPurchaseRequestList(conditions, -1, -1, PurchaseRequestQueryOrder.ID, false);
        for (Iterator itor = prList.iterator(); itor.hasNext();) {
            PurchaseRequest pr = (PurchaseRequest)itor.next();
            boolean shouldCalculated = !(PurchaseRequestStatus.DRAFT.equals(pr.getStatus()) || PurchaseRequestStatus.REJECTED.equals(pr.getStatus()));
            List prItems = dao.getPurchaseRequestItemList(pr);
            if (shouldCalculated) {
                result.addAll(prItems);
            } else {
                for (Iterator itor2 = prItems.iterator(); itor2.hasNext();) {
                    PurchaseRequestItem prItem = (PurchaseRequestItem) itor2.next();
                    if (prItem.getPurchaseOrderItem() != null) {
                        result.add(prItem);
                    }
                }
            }
        }
        return result;
    }
    
    /**
     * 复制pr
     * @param copyPR 目标PR
     * @param srcPR 源PR
     * @return 返回复制的PR
     * @throws Exception 
     */
    public PurchaseRequest copyPurchaseRequest(PurchaseRequest copyPR,PurchaseRequest srcPR) throws Exception {
        copyPR.setAmount(srcPR.getAmount());
        copyPR.setApproveDate(null);
        copyPR.setApproveRequestId(null);
        copyPR.setCapex(srcPR.getCapex());
        copyPR.setCreateDate(new Date());        
        copyPR.setCurrency(srcPR.getCurrency());
        copyPR.setDepartment(srcPR.getDepartment());
        copyPR.setDescription(srcPR.getDescription());
        copyPR.setEmailDate(null);
        copyPR.setEmailTimes(0);
        copyPR.setIsDelegate(srcPR.getIsDelegate());
        copyPR.setMaxItemPrice(srcPR.getMaxItemPrice());
        copyPR.setPurchaseAmount(srcPR.getPurchaseAmount());
        copyPR.setPurchaser(null);
        copyPR.setPurchaseSubCategory(srcPR.getPurchaseSubCategory());        
        copyPR.setRequestDate(new Date());   
        copyPR.setRequestor(srcPR.getRequestor());
        copyPR.setStatus(PurchaseRequestStatus.DRAFT);
        copyPR.setTitle(srcPR.getTitle());
        copyPR.setYearlyBudget(srcPR.getYearlyBudget());
        copyPR.setId(getNewPurchaseRequestCode(copyPR.getDepartment().getSite(), new Date()));
        
        copyPR = dao.insertPurchaseRequest(copyPR);
        
        //update budget actual amount
        //updateBudgetAmount(copyPR, new BigDecimal(0d), copyPR.getAmount());
        
        List itemList = getPurchaseRequestItemListWithDetails(srcPR);
        //copy item infomation
        if (itemList != null) {
            for (Iterator iterator = itemList.iterator(); iterator.hasNext(); ) {
                PurchaseRequestItem srcItem = (PurchaseRequestItem)iterator.next();
                PurchaseRequestItem copyItem = new PurchaseRequestItem();
                
                copyItem.setBuyForDepartment(srcItem.getBuyForDepartment());
                copyItem.setBuyForUser(srcItem.getBuyForUser());
                copyItem.setDueDate(srcItem.getDueDate());
                copyItem.setExchangeRate(srcItem.getExchangeRate());
                copyItem.setExchangeRateValue(srcItem.getExchangeRateValue());
                copyItem.setIsRecharge(srcItem.getIsRecharge());
                copyItem.setPurchaseOrderItem(null);
                copyItem.setPurchaseRequest(copyPR);
                copyItem.setQuantity(srcItem.getQuantity());
                copyItem.setSupplier(srcItem.getSupplier());
                copyItem.setSupplierItem(srcItem.getSupplierItem());
                copyItem.setSupplierItemSepc(srcItem.getSupplierItemSepc());
                copyItem.setSupplierName(srcItem.getSupplierName());
                copyItem.setUnitPrice(srcItem.getUnitPrice());
                
                dao.insertPurchaseRequestItem(copyItem);
                
                //copy item attachment
                if (srcItem.getAttachments() != null && srcItem.getAttachments().size() > 0) {
                    for (Iterator attIterator = srcItem.getAttachments().iterator(); attIterator.hasNext(); ) { 
                        PurchaseRequestItemAttachment srcItemAttachment = (PurchaseRequestItemAttachment)attIterator.next();
                        PurchaseRequestItemAttachment copyItemAttachment = new PurchaseRequestItemAttachment();
                        
                        copyItemAttachment.setDescription(srcItemAttachment.getDescription());
                        copyItemAttachment.setFileName(srcItemAttachment.getFileName());
                        copyItemAttachment.setFileSize(srcItemAttachment.getFileSize());
                        copyItemAttachment.setPurchaseRequestItem(copyItem);
                        copyItemAttachment.setUploadDate(new Date());
                        dao.insertPurchaseRequestItemAttachment(
                                copyItemAttachment, dao.getPurchaseRequestItemAttachmentContent(
                                        srcItemAttachment.getId()));                                       
                    }
                }
                
                //copy recharge information
                if (srcItem.getRecharges() != null && srcItem.getRecharges().size() > 0) {
                    for (Iterator rechargeIterator = srcItem.getRecharges().iterator(); rechargeIterator.hasNext(); ) {
                        PurchaseRequestItemRecharge srcItemRecharge = (PurchaseRequestItemRecharge)rechargeIterator.next();
                        PurchaseRequestItemRecharge copyItemRecharge = new PurchaseRequestItemRecharge();
                        
                        copyItemRecharge.setAmount(srcItemRecharge.getAmount());
                        copyItemRecharge.setCustomer(srcItemRecharge.getCustomer());
                        copyItemRecharge.setPerson(srcItemRecharge.getPerson());
                        copyItemRecharge.setPersonDepartment(srcItemRecharge.getPersonDepartment());
                        copyItemRecharge.setPurchaseRequestItem(copyItem);
                        copyItemRecharge.setTotalAmount(copyItem.getBaseCurrencyAmount());
                        
                        dao.insertPurchaseRequestItemRecharge(copyItemRecharge);
                    }
                }
            }                                
        }
        
        List attachList = getPurchaseRequestAttachmentList(srcPR);
        //copy pr attahment
        if (attachList != null) {
            for (Iterator attachIterator = attachList.iterator(); attachIterator.hasNext(); ) {
                PurchaseRequestAttachment srcAttach = (PurchaseRequestAttachment)attachIterator.next();
                PurchaseRequestAttachment copyAttach = new PurchaseRequestAttachment();
                
                copyAttach.setDescription(srcAttach.getDescription());
                copyAttach.setFileName(srcAttach.getFileName());
                copyAttach.setFileSize(srcAttach.getFileSize());
                copyAttach.setPurchaseRequest(copyPR);
                copyAttach.setUploadDate(new Date());
                
                dao.insertPurchaseRequestAttachment(
                        copyAttach, dao.getPurchaseRequestAttachmentContent(srcAttach.getId()));
            }   
        }
        return copyPR;
    }
    
    private BigDecimal getMaxPriceOfPrItems(List itemList) {
        BigDecimal maxPrice = null;
        for (Iterator iter = itemList.iterator(); iter.hasNext();) {
            PurchaseRequestItem pri = (PurchaseRequestItem) iter.next();
            BigDecimal price = pri.getBaseCurrencyPrice();
            if (maxPrice == null || maxPrice.compareTo(price) < 0) {
                maxPrice = price;
            }
        }
        return maxPrice;
    }
}
