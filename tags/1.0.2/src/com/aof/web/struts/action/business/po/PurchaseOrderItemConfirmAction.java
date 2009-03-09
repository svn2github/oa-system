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

import com.aof.model.admin.Site;
import com.aof.model.business.po.PurchaseOrderItem;
import com.aof.model.metadata.YesNo;
import com.aof.service.admin.PurchaseTypeManager;
import com.aof.web.struts.action.ServiceLocator;
import com.shcnc.struts.form.BeanForm;

/**
 * Action类，处理PurchaseOrderItem
 * 
 * @author shi1
 * @version 1.0 (2005-12-4)
 */
public class PurchaseOrderItemConfirmAction extends BasePurchaseOrderAction  {
    protected String getPurchaseOrderId(HttpServletRequest request) {
        return request.getParameter("purchaseOrder_id");
    }
    
    /**
     * 修改PurchaseOrderItem
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PurchaseOrderItem poi=this.getPurchaseOrderItemFromSession(request);
        //this.checkPurchaseOrderEditPower(poi.getPurchaseOrder(),request);
        Site site=null;
        if(poi.getPurchaseOrder().isFromAirTicket())
        {
            site=poi.getPurchaseOrder().getSite();
        }
        else
        {
            site=poi.getPurchaseRequestItem().getPurchaseRequest().getDepartment().getSite() ;
        }
        //PurchaseRequest pr=poi.getPurchaseRequestItem().getPurchaseRequest();
        
        request.setAttribute("x_poi",poi);
        
        BeanForm itemForm=(BeanForm)this.getForm("/updatePurchaseOrderItem_confirm",request);
        
        if(!this.isBack(request))
        {
            itemForm.populateToForm(poi);
        }
        
        //recharge
        this.setRechargeInfoToRequest(poi,poi.getRecharges(),
                site,itemForm,request);
        request.setAttribute("X_FORMNAME", "purchaseOrderItemConfirmForm");

        //combo list
        this.putPurchaseTypeListToRequest(site,request);
        //make itemAttacmentRow.jsp edit version
        this.setEditing(false,request);
        
        return mapping.findForward("page");
    }
    
    private void putPurchaseTypeListToRequest(Site site,HttpServletRequest request)
    {
        PurchaseTypeManager pm=ServiceLocator.getPurchaseTypeManager(request);
        request.setAttribute("x_purchaseTypeList",pm.getEnabledPurchaseTypeList(site));
    }
    
    /**
     * 保存修改的PurchaseOrderItem
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        BeanForm itemForm = (BeanForm) form;
        itemForm.setBeanLoader(ServiceLocator.getBeanLoader(request));
        
        PurchaseOrderItem poi = this.getPurchaseOrderItemFromSession(request);
        
        if(itemForm.getString("isRecharge").equals(YesNo.YES.getEnumCode().toString()))
            itemForm.populateToBean(poi,request,new String[]{"buyForDepartment.id","buyForUser.id"});
        else
            itemForm.populateToBean(poi,request);

        
        poi.setRecharges(this.getRechargeInfoFromRequest(poi,  request));
        poi.setAttachments(this.getPurchaseOrderItemAttachmentListFromRequest(request));
        
        request.setAttribute("X_OBJECT", poi);
        request.setAttribute("X_ROWPAGE", "purchaseOrderConfirm/itemRow.jsp");

        //make itemRow.jsp edit version
        this.setEditing(true,request);

        return mapping.findForward("success");
    }
    
    /* (non-Javadoc)
     * @see com.aof.web.struts.action.business.RechargeAction#selectRechargeCustomer(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public ActionForward selectRechargeCustomer(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return super.selectRechargeCustomer(mapping, form, request, response);
    }

    /* (non-Javadoc)
     * @see com.aof.web.struts.action.business.RechargeAction#selectRechargeEntity(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public ActionForward selectRechargeEntity(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return super.selectRechargeEntity(mapping, form, request, response);
    }

    /* (non-Javadoc)
     * @see com.aof.web.struts.action.business.RechargeAction#selectRechargePerson(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public ActionForward selectRechargePerson(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return super.selectRechargePerson(mapping, form, request, response);
    }


}