/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.web.struts.action.business.po;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aof.model.business.po.PurchaseOrder;
import com.aof.model.business.po.PurchaseOrderItem;
import com.aof.model.metadata.PurchaseOrderStatus;
import com.aof.web.struts.action.ActionUtils;
import com.aof.web.struts.action.ServiceLocator;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.action.BackToInputActionException;
import com.shcnc.struts.form.BeanForm;


/**
 * action class for cancel PurchaseOrderItem
 * @author shilei
 * @version 1.0 (Dec 23, 2005)
 */
public class PurchaseOrderCancelAction extends BasePurchaseOrderAction {
    
    /**
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PurchaseOrder po = this.getPurchaseOrderFromRequest(request);
        this.checkPurchaseOrderCancelPower(po, request);
        request.setAttribute("x_po", po);

        this.putPurchaseOrderDetailsForView(po, request);
        this.setEditing(false, request);
        return mapping.findForward("page");
    }
    
    public ActionForward cancel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PurchaseOrderItem poi = this.getPurchaseOrderItemFromRequest(request);
        this.checkPurchaseOrderCancelPower(poi.getPurchaseOrder(), request);
        request.setAttribute("x_poi", poi);
        
        if (!isBack(request)) {
            BeanForm beanForm = (BeanForm) this.getForm("/cancelPurchaseOrderItemQuantityResult", request);
            beanForm.populateToForm(poi);
        }
        
        return mapping.findForward("page");

    }

    public ActionForward cancelResult(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PurchaseOrderItem poi = this.getPurchaseOrderItemFromRequest(request);
        this.checkPurchaseOrderCancelPower(poi.getPurchaseOrder(), request);
        int cancelQuantity = ActionUtils.parseInt(request.getParameter("cancelQuantity")).intValue();
        int maxCancelQuantity = poi.getQuantity() - poi.getReceivedQuantity().intValue() - poi.getReturnedQuantity().intValue() - poi.getCancelledQuantity().intValue();
        if (cancelQuantity > maxCancelQuantity) {
            throw new BackToInputActionException("purchaseOrder.cancel.cancelToMuch", new Integer(maxCancelQuantity));
        }
        
        ServiceLocator.getPurchaseOrderManager(request).cancelPurchaseOrderItemQuantity(poi, cancelQuantity);
        request.setAttribute("X_OBJECT", poi);
        request.setAttribute("X_ROWPAGE", "purchaseOrderCancel/itemRow.jsp");
        
        return mapping.findForward("success");

    }

    private void checkPurchaseOrderCancelPower(PurchaseOrder po, HttpServletRequest request) {
        this.checkSite(po.getSite(),request);
        this.checkConfirmed(po);
    }

    private void checkConfirmed(PurchaseOrder po) {
        if(!po.getStatus().equals(PurchaseOrderStatus.CONFIRMED))
            throw new ActionException("purchaseOrder.cancel.mustBeConfirmed");
    }


}