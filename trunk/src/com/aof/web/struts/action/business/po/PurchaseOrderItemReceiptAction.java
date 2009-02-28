package com.aof.web.struts.action.business.po;



import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aof.model.admin.User;
import com.aof.model.business.po.PurchaseOrderItem;
import com.aof.model.business.po.PurchaseOrderItemReceipt;
import com.aof.model.business.po.query.PurchaseOrderItemReceiptQueryCondition;
import com.aof.model.business.po.query.PurchaseOrderItemReceiptQueryOrder;
import com.aof.model.metadata.PurchaseOrderStatus;
import com.aof.service.admin.EmailManager;
import com.aof.service.business.po.PurchaseOrderItemReceiptManager;
import com.aof.web.struts.action.BaseAction;
import com.aof.web.struts.action.ServiceLocator;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.action.BackToInputActionException;
import com.shcnc.struts.form.BeanForm;

/**
 * action class for domain model PurchaseOrderItemReceipt
 * 
 * @author shilei
 * @version 1.0 (Dec 27, 2005)
 */
public class PurchaseOrderItemReceiptAction extends BaseAction {
    /**
     * action method for listing PurchaseOrderItemReceipt
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PurchaseOrderItem poi = this.getPurchaseOrderItemFromRequest(request);
        this.checkViewPower(poi, request);
        request.setAttribute("x_poi", poi);

        PurchaseOrderItemReceiptManager fm = ServiceLocator.getPurchaseOrderItemReceiptManager(request);
        Map conditions = new HashMap();
        conditions.put(PurchaseOrderItemReceiptQueryCondition.PURCHASEORDERITEM_ID_EQ, poi.getId());
        List result = fm.getPurchaseOrderItemReceiptList(conditions, 0, -1, PurchaseOrderItemReceiptQueryOrder.RECEIVEDATE1, true);

        request.setAttribute("X_RESULTLIST", result);
        return mapping.findForward("page");
    }

    private void checkOwner(PurchaseOrderItem poi, HttpServletRequest request) {
        if (!this.isRequestor(poi,request) && !this.isInspector(poi,request))
            throw new ActionException("purchaseOrderItemReceipt.notOwner");
    }

    private void checkEditPower(PurchaseOrderItemReceipt poir, HttpServletRequest request) {
        this.checkEditPower(poir.getPurchaseOrderItem(), request);
        if (poir.isFinished())
            throw new ActionException("purchaseOrderItemReceipt.edit.finished");
    }
    
    private void checkDeletePower(PurchaseOrderItemReceipt poir, HttpServletRequest request) {
        this.checkViewPower(poir.getPurchaseOrderItem(), request);
        if (poir.isFinished())
            throw new ActionException("purchaseOrderItemReceipt.delete.finished");
    }
    

    private void checkEditPower(PurchaseOrderItem poi, HttpServletRequest request) {
        this.checkOwner(poi, request);
        if (!poi.getPurchaseOrder().getStatus().equals(PurchaseOrderStatus.CONFIRMED))
            throw new ActionException("purchaseOrderItemReceipt.edit.purchaseOrder.mustConfirmed");
        if (poi.isReceived())
            throw new ActionException("purchaseOrderItemReceipt.edit.purchaseOrderItem.alreadyReceived");
    }

    private void checkViewPower(PurchaseOrderItem poi, HttpServletRequest request) {
        this.checkOwner(poi, request);
        if (!poi.getPurchaseOrder().getStatus().equals(PurchaseOrderStatus.CONFIRMED)
                && !poi.getPurchaseOrder().getStatus().equals(PurchaseOrderStatus.RECEIVED))
            throw new ActionException("purchaseOrderItemReceipt.view.status.error");
    }

    private PurchaseOrderItem getPurchaseOrderItemFromRequest(HttpServletRequest request) throws Exception {
        return (PurchaseOrderItem) this.getObjectFromRequestAndCheckExists("purchaseOrderItem_id", PurchaseOrderItem.class, "id", request);
    }

    private PurchaseOrderItemReceipt getPurchaseOrderItemReceiptFromRequest(HttpServletRequest request) throws Exception {
        return (PurchaseOrderItemReceipt) this.getObjectFromRequestAndCheckExists("id", PurchaseOrderItemReceipt.class, "id", request);
    }

    private void putReceiverNoToRequest(PurchaseOrderItem poi, HttpServletRequest request) {
        if(isRequestor(poi,request))
            request.setAttribute("x_receiverNo", "1");
        else if(isInspector(poi,request))
            request.setAttribute("x_receiverNo", "2");
    }
    
    /**
     * action method for deleting PurchaseOrderItemReceipt
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PurchaseOrderItemReceipt poir = getPurchaseOrderItemReceiptFromRequest(request);
        this.checkDeletePower(poir, request);
        PurchaseOrderItemReceiptManager pm = ServiceLocator.getPurchaseOrderItemReceiptManager(request);
        pm.deletePurchaseOrderItemReceipt(poir,this.getCurrentUser(request));
        return mapping.findForward("success");
    }

    /**
     * action method for editing PurchaseOrderItemReceipt
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PurchaseOrderItemReceipt poir = getPurchaseOrderItemReceiptFromRequest(request);
        this.checkEditPower(poir, request);
        this.putReceiverNoToRequest(poir.getPurchaseOrderItem(), request);
        this.putMaxQtyToRequest(poir.getPurchaseOrderItem(),poir,request);
        
        if (!isBack(request)) {
            BeanForm purchaseOrderItemReceiptForm = (BeanForm) getForm("/updatePurchaseOrderItemReceipt", request);
            purchaseOrderItemReceiptForm.populateToForm(poir);
        }

        this.setAdding(false,request);
        
        if(this.isRequestor(poir.getPurchaseOrderItem(),request))
        {
            if(poir.getReceiveDate1()!=null)
                request.setAttribute("x_receiveDate",poir.getReceiveDate1());
            else
                request.setAttribute("x_receiveDate",new Date());
        }
        else
        {
            if(poir.getReceiveDate2()!=null)
                request.setAttribute("x_receiveDate",poir.getReceiveDate2());
            else
                request.setAttribute("x_receiveDate",new Date());
        }
            
        
        return mapping.findForward("page");
    }
    
    private void putMaxQtyToRequest(PurchaseOrderItem poi,PurchaseOrderItemReceipt poir,HttpServletRequest request) throws Exception
    {
        PurchaseOrderItemReceiptManager pm = ServiceLocator.getPurchaseOrderItemReceiptManager(request);
        int receiveQty=pm.getRecevieSum(poi,this.getCurrentUser(request));
        int maxQty=poi.getQuantity()-receiveQty;
        if(poir!=null)
        {
            User currentUser=this.getCurrentUser(request);
            if(poir.getReceiver1().equals(currentUser) && poir.getReceiveQty1()!=null)
            {
                maxQty+=poir.getReceiveQty1().intValue();
            }
            else if(poir.getReceiver2().equals(currentUser) && poir.getReceiveQty2()!=null)
            {
                maxQty+=poir.getReceiveQty2().intValue();    
            }
        }
        if(maxQty<=0) throw new ActionException("purchaseOrderItemReceipt.edit.purchaseOrderItem.alreadyReceived");
        request.setAttribute("x_maxQty",new Integer(maxQty));
    }

    /**
     * action method for creating PurchaseOrderItemReceipt
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward newObject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PurchaseOrderItem poi = this.getPurchaseOrderItemFromRequest(request);
        this.checkEditPower(poi, request);
        this.putReceiverNoToRequest(poi, request);
        this.putMaxQtyToRequest(poi,null,request);

        if (!isBack(request)) {
            PurchaseOrderItemReceipt purchaseOrderItemReceipt = new PurchaseOrderItemReceipt();
            purchaseOrderItemReceipt.setPurchaseOrderItem(poi);
            BeanForm purchaseOrderItemReceiptForm = (BeanForm) getForm("/insertPurchaseOrderItemReceipt", request);
            purchaseOrderItemReceiptForm.populateToForm(purchaseOrderItemReceipt);
        }

        this.setAdding(true,request);
        request.setAttribute("x_receiveDate",new Date());
        return mapping.findForward("page");
    }
    
    private void setAdding(boolean b,HttpServletRequest request)
    {
        request.setAttribute("x_add",new Boolean(b));
    }
    
    private boolean isRequestor(PurchaseOrderItem poi,HttpServletRequest request)
    {
        if(poi.getRequestor().equals(this.getCurrentUser(request)))
            return true;
        return false;
    }
    
    
    
    private boolean isInspector(PurchaseOrderItem poi,HttpServletRequest request)
    {
        if(poi.getInspector().equals(this.getCurrentUser(request)))
            return true;
        return false;
    }
    

    /**
     * action method for updating PurchaseOrderItemReceipt
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PurchaseOrderItemReceipt poir = this.getPurchaseOrderItemReceiptFromRequest(request);
        this.checkEditPower(poir, request);
        
        BeanForm purchaseOrderItemReceiptForm = (BeanForm) form;
        purchaseOrderItemReceiptForm.populateToBean(poir, request);
        
        PurchaseOrderItemReceiptManager pm = ServiceLocator.getPurchaseOrderItemReceiptManager(request);
        
        if(!pm.checkQty(poir))
            throw new BackToInputActionException("purchaseOrderItemReceipt.qtyExceeds"); 
        
        PurchaseOrderItemReceipt oldPoir = this.getPurchaseOrderItemReceiptFromRequest(request);
        request.setAttribute("X_OBJECT", pm.updatePurchaseOrderItemReceipt(oldPoir,poir,this.getCurrentUser(request)));
        request.setAttribute("X_ROWPAGE", "purchaseOrderItemReceipt/row.jsp");

        return mapping.findForward("success");
    }
    

    /**
     * action method for inserting PurchaseOrderItemReceipt
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward insert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PurchaseOrderItem poi = this.getPurchaseOrderItemFromRequest(request);
        this.checkEditPower(poi, request);

        BeanForm purchaseOrderItemReceiptForm = (BeanForm) form;
        PurchaseOrderItemReceipt poir = new PurchaseOrderItemReceipt();
        purchaseOrderItemReceiptForm.populate(poir, BeanForm.TO_BEAN);
        poir.setPurchaseOrderItem(poi);
        
        PurchaseOrderItemReceiptManager pm = ServiceLocator.getPurchaseOrderItemReceiptManager(request);        
        if(!pm.checkQty(poir))
            throw new BackToInputActionException("purchaseOrderItemReceipt.qtyExceeds"); 

        poir=pm.insertPurchaseOrderItemReceipt(poir,this.getCurrentUser(request));
        request.setAttribute("X_OBJECT", poir);
        request.setAttribute("X_ROWPAGE", "purchaseOrderItemReceipt/row.jsp");

        EmailManager em=ServiceLocator.getEmailManager(request);
        
        Map context=new HashMap();
        User emailToUser=null;
        if(this.getCurrentUser(request).equals(poir.getReceiver1()))
        {
            emailToUser=poir.getReceiver2();
            context.put("x_receiveQty",poir.getReceiveQty1());
            context.put("x_receiveDate",poir.getReceiveDate1());
        }
        else
        {
            emailToUser=poir.getReceiver1();
            context.put("x_receiveQty",poir.getReceiveQty2());
            context.put("x_receiveDate",poir.getReceiveDate2());
            
        }
        context.put("x_emailToUser",emailToUser);
        context.put("x_receiver",this.getCurrentUser(request));
        context.put("x_poir",poir);
        context.put("role", EmailManager.EMAIL_ROLE_RECEIVER);
        em.insertEmail(poir.getLogSite(),emailToUser.getEmail(),"POItemReceive.vm",context);
        
        return mapping.findForward("success");
    }

}