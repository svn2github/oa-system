/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.service.business.po.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sourceforge.dao.business.po.PurchaseOrderDAO;
import net.sourceforge.dao.business.po.PurchaseOrderItemReceiptDAO;
import net.sourceforge.model.admin.User;
import net.sourceforge.model.business.po.PurchaseOrder;
import net.sourceforge.model.business.po.PurchaseOrderItem;
import net.sourceforge.model.business.po.PurchaseOrderItemReceipt;
import net.sourceforge.model.business.po.query.PurchaseOrderItemReceiptQueryCondition;
import net.sourceforge.model.business.po.query.PurchaseOrderItemReceiptQueryOrder;
import net.sourceforge.model.metadata.ExportStatus;
import net.sourceforge.model.metadata.PurchaseOrderStatus;
import net.sourceforge.service.BaseManager;
import net.sourceforge.service.admin.EmailManager;
import net.sourceforge.service.admin.SystemLogManager;
import net.sourceforge.service.business.po.PurchaseOrderItemReceiptManager;

/**
 * service manager implement for domain model PurchaseOrderItemReceipt
 * 
 * @author shilei
 * @version 1.0 (Dec 27, 2005)
 */
public class PurchaseOrderItemReceiptManagerImpl extends BaseManager implements PurchaseOrderItemReceiptManager {
    private PurchaseOrderItemReceiptDAO dao;

    private PurchaseOrderDAO purchaseOrderDAO;
    
    private SystemLogManager systemLogManager;
    
    private EmailManager emailManager;

    public void setEmailManager(EmailManager emailManager) {
        this.emailManager = emailManager;
    }

    public void setSystemLogManager(SystemLogManager systemLogManager) {
        this.systemLogManager = systemLogManager;
    }

    public void setPurchaseOrderItemReceiptDAO(PurchaseOrderItemReceiptDAO dao) {
        this.dao = dao;
    }

    public void setPurchaseOrderDAO(PurchaseOrderDAO purchaseOrderDAO) {
        this.purchaseOrderDAO = purchaseOrderDAO;
    }

    public PurchaseOrderItemReceipt getPurchaseOrderItemReceipt(Integer id) throws Exception {
        return dao.getPurchaseOrderItemReceipt(id);
    }

    private void setDate(PurchaseOrderItemReceipt poir) {
        if (poir.getReceiveQty1() != null && poir.getReceiveDate1() == null)
            poir.setReceiveDate1(new Date());

        if (poir.getReceiveQty2() != null && poir.getReceiveDate2() == null)
            poir.setReceiveDate2(new Date());
    }

    public PurchaseOrderItemReceipt updatePurchaseOrderItemReceipt(PurchaseOrderItemReceipt oldPoir,PurchaseOrderItemReceipt poir,User user) throws Exception {
        if (!this.checkQty(poir))
            throw new RuntimeException("qty exceeds");

        this.setDate(poir);

        if (poir.getExportStatus().equals(ExportStatus.EXPORTED))
            poir.setExportStatus(ExportStatus.NEEDREEXPORT);

        dao.updatePurchaseOrderItemReceipt(poir);

        if (poir.isFinished()) {
            PurchaseOrderItem poi = poir.getPurchaseOrderItem();
            int qty = poir.getReceiveQty1().intValue();
            poi.receive(qty);
            purchaseOrderDAO.updatePurchaseOrderItem(poi);

            if (poi.isReceived()) {
                setPurchaseOrderReceivedIfAllPoItemReceived(poi.getPurchaseOrder());
                Map context=new HashMap();
                context.put("x_poir",poir);
                //PurchaseOrder po = poi.getPurchaseOrder();
                //User emailUser = po.getConfirmer();
                //context.put("x_user", emailUser);
                //emailManager.insertEmail(poi.getPurchaseOrder().getSite(),emailUser.getEmail(), "POItemReceiveConfirm.vm",context);
                //emailUser = po.getPurchaser();
                //context.put("x_user", emailUser);
                //emailManager.insertEmail(poi.getPurchaseOrder().getSite(),emailUser.getEmail(), "POItemReceiveConfirm.vm",context);
                User emailUser = poi.getPurchaseRequestItem().getPurchaseRequest().getRequestor();
                context.put("x_user", emailUser);
                context.put("role", EmailManager.EMAIL_ROLE_REQUESTOR);
                emailManager.insertEmail(poi.getPurchaseOrder().getSite(),emailUser.getEmail(), "POItemReceiveConfirm.vm",context);
            }
        }
        
        systemLogManager.generateLog(oldPoir,poir,PurchaseOrderItemReceipt.LOG_ACTION_RECEIPT,user);
        return poir;
    }

    private void setPurchaseOrderReceivedIfAllPoItemReceived(PurchaseOrder po) {
        List poiList = purchaseOrderDAO.getPurchaseOrderItemList(po);
        boolean allReceived = true;
        for (Iterator iter = poiList.iterator(); iter.hasNext();) {
            PurchaseOrderItem poi = (PurchaseOrderItem) iter.next();
            if (!poi.isReceived()) {
                allReceived = false;
                break;
            }
        }
        if (allReceived) {
            po=purchaseOrderDAO.getPurchaseOrder(po.getId());
            po.setStatus(PurchaseOrderStatus.RECEIVED);
            purchaseOrderDAO.updatePurchaseOrder(po);
        }
    }

    private int intValue(Integer v) {
        if (v == null)
            return 0;
        return v.intValue();
    }

    public boolean checkQty(PurchaseOrderItemReceipt poir) {
        PurchaseOrderItem poi = poir.getPurchaseOrderItem();

        int qty1 = Math.abs(intValue(poir.getReceiveQty1()));
        int qty2 = Math.abs(intValue(poir.getReceiveQty2()));

        if (Math.max(qty1, qty2) + poi.getProcessedQty() > poi.getQuantity())
            return false;
        return true;
    }

    public PurchaseOrderItemReceipt insertPurchaseOrderItemReceipt(PurchaseOrderItemReceipt poir,User user) throws Exception {
        if (!this.checkQty(poir))
            throw new RuntimeException("qty exceeds");
        poir.setReceiver1(poir.getPurchaseOrderItem().getRequestor());
        poir.setReceiver2(poir.getPurchaseOrderItem().getInspector());
        this.setDate(poir);
        poir.setExportStatus(ExportStatus.UNEXPORTED);
        
        systemLogManager.generateLog(null,poir,PurchaseOrderItemReceipt.LOG_ACTION_RECEIPT,user);
        return dao.insertPurchaseOrderItemReceipt(poir);
    }

    public int getPurchaseOrderItemReceiptListCount(Map conditions) throws Exception {
        return dao.getPurchaseOrderItemReceiptListCount(conditions);
    }

    public List getPurchaseOrderItemReceiptList(Map conditions, int pageNo, int pageSize, PurchaseOrderItemReceiptQueryOrder order, boolean descend)
            throws Exception {
        return dao.getPurchaseOrderItemReceiptList(conditions, pageNo, pageSize, order, descend);
    }

    public void deletePurchaseOrderItemReceipt(PurchaseOrderItemReceipt poir,User user) {
        dao.deletePurchaseOrderItemReceipt(poir);
        systemLogManager.generateLog(null,poir,PurchaseOrderItemReceipt.LOG_ACTION_DELETERECEIPT,user);
    }

    public int getRecevieSum(PurchaseOrderItem poi, User currentUser) throws Exception {
        
        Map conds=new HashMap();
        conds.put(PurchaseOrderItemReceiptQueryCondition.PURCHASEORDERITEM_ID_EQ,poi.getId());
        List l=this.getPurchaseOrderItemReceiptList(conds,0,-1,null,false);
        int sum=0;
        for (Iterator iter = l.iterator(); iter.hasNext();) {
            PurchaseOrderItemReceipt poir = (PurchaseOrderItemReceipt) iter.next();
            if(poir.getReceiver1().equals(currentUser) && poir.getReceiveQty1()!=null)
            {
                sum+=poir.getReceiveQty1().intValue();
            }
            else if(poir.getReceiver2().equals(currentUser) && poir.getReceiveQty2()!=null)
            {
                sum+=poir.getReceiveQty2().intValue();    
            }
        }
        return sum;
    }

}
