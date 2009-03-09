/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.web.struts.action.business.po;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aof.model.admin.Function;
import com.aof.model.admin.Site;
import com.aof.model.business.po.PurchaseOrder;
import com.aof.model.metadata.PurchaseOrderStatus;
import com.aof.service.admin.EmailManager;
import com.aof.service.admin.FunctionManager;
import com.aof.service.admin.UserManager;
import com.aof.service.business.po.PurchaseOrderManager;
import com.aof.web.struts.action.ServiceLocator;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.form.BeanForm;


/**
 * action class for assign PurchaseOrder
 * @author nicebean
 * @version 1.0 (Dec 26, 2005)
 */
public class PurchaseOrderAssignAction extends BasePurchaseOrderAction {
    
    /**
     * action method for assign purchaseOrder
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward assign(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PurchaseOrder po = this.getPurchaseOrderFromRequest(request);
        this.checkAssignable(po, request);
        request.setAttribute("x_po", po);

        this.putPurchaseOrderDetailsForView(po, request);
        this.putPurchaserListToRequest(po.getSite(), request);

        if (!isBack(request)) {
            BeanForm purchaseOrderAssignForm = (BeanForm) this.getForm("/assignPurchaseOrder_result", request);
            purchaseOrderAssignForm.populateToForm(po);
        }

        this.setEditing(false, request);
        
        return mapping.findForward("page");
    }
    
    /**
     * action method for assign purchaseOrder
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward assignResult(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PurchaseOrder po = this.getPurchaseOrderFromRequest(request);
        this.checkAssignable(po, request);

        BeanForm beanForm = (BeanForm) form;
        beanForm.setBeanLoader(ServiceLocator.getBeanLoader(request));
        beanForm.populateToBean(po, request);

        /**
         * 不必检查设置的Purchaser是否真有Purchaser权限，只需要检查当前用户是否有权assign
         */
        /*
        UserManager um = ServiceLocator.getUserManager(request);
        if (!um.hasSitePower(pr.getDepartment().getSite(), pr.getPurchaser(), this.getPurchaseFunction(request)))
            throw new ActionException("purchaseRequest.assign.purchaserNoPower");
        */
        
        PurchaseOrderManager prm = ServiceLocator.getPurchaseOrderManager(request);
        prm.updatePurchaseOrder(po);
        
        EmailManager em=ServiceLocator.getEmailManager(request);
        Map context=new HashMap();
        context.put("x_po",po);
        context.put("role", EmailManager.EMAIL_ROLE_PURCHASER);
        em.insertEmail(po.getSite(),po.getPurchaser().getEmail(),"POAssignee.vm",context);

        this.postGlobalMessage("purchaseOrder.assign.success", request.getSession());
        return new ActionForward("assignPurchaseOrder.do?id=" + po.getId(), true);

    }

    private void checkAssignable(PurchaseOrder po, HttpServletRequest request) {
        PurchaseOrderStatus status = po.getStatus();
        if (!PurchaseOrderStatus.APPROVED.equals(status) && !PurchaseOrderStatus.CONFIRMED.equals(status)) {
            throw new ActionException("purchaseOrder.assign.statusMustBeApprovedOrConfirmed");
        }
        this.checkAssignPower(po, request);
    }

    private void checkAssignPower(PurchaseOrder po,HttpServletRequest request) {
        this.checkSite(po.getSite(), request);
    }

    private Function getPurchaseFunction(HttpServletRequest request) {
        FunctionManager fm = ServiceLocator.getFunctionManager(request);
        Function f = fm.getFunction("PurchaseOrderPurchase"); 
        if (f == null) throw new ActionException("function.typeNotFound", "PurchaseOrderPurchase");
        return f;
    }

    private void putPurchaserListToRequest(Site site, HttpServletRequest request) {
        UserManager um = ServiceLocator.getUserManager(request);
        request.setAttribute("x_purchaserList", um.getEnabledUserList(getPurchaseFunction(request), site));
    }


}