/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.web.struts.action.business.po;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aof.model.admin.Site;
import com.aof.model.business.po.PurchaseOrderItem;
import com.aof.model.business.po.query.PurchaseOrderItemQueryCondition;
import com.aof.model.business.po.query.PurchaseOrderItemQueryOrder;
import com.aof.model.business.pr.PurchaseRequest;
import com.aof.model.metadata.PurchaseOrderStatus;
import com.aof.model.metadata.YesNo;
import com.aof.service.admin.PurchaseTypeManager;
import com.aof.service.admin.SupplierManager;
import com.aof.service.business.po.PurchaseOrderManager;
import com.aof.web.struts.action.ActionUtils;
import com.aof.web.struts.action.ServiceLocator;
import com.aof.web.struts.form.business.po.PurchaseOrderItemQueryForm;
import com.shcnc.struts.form.BeanForm;

/**
 * Action类，处理PurchaseOrderItem
 * 
 * @author shi1
 * @version 1.0 (2005-12-4)
 */
public class PurchaseOrderItemAction extends BasePurchaseOrderAction  {
    protected String getPurchaseOrderId(HttpServletRequest request) {
        return request.getParameter("purchaseOrder_id");
    }
    
    /**
     * view PurchaseOrderItem detail
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PurchaseOrderItem poi=this.getPurchaseOrderItemFromRequest(request);
        request.setAttribute("x_poi",poi);
        
        //recharge
        this.setRechargeInfoToRequestForView(poi,poi.getRecharges(),request);
        
        return mapping.findForward("page");
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
        PurchaseRequest pr=poi.getPurchaseRequestItem().getPurchaseRequest();
        
        request.setAttribute("x_poi",poi);
        
        BeanForm itemForm=(BeanForm)this.getForm("/updatePurchaseOrderItem",request);
        
        if(!this.isBack(request))
        {
            itemForm.populateToForm(poi);
        }
        
        //recharge
        this.setRechargeInfoToRequest(poi,poi.getRecharges(),
                pr.getDepartment().getSite(),itemForm,request);
        request.setAttribute("X_FORMNAME", "purchaseOrderItemForm");

        //combo list
        SupplierManager sm=ServiceLocator.getSupplierManager(request);
        request.setAttribute("x_supplierItemList",
                sm.getSuitableSupplierItemListForPurchase(
                        poi.getSupplier(),poi.getPurchaseOrder().getSubCategory(),poi.getExchangeRate().getCurrency()));
        this.putPurchaseTypeListToRequest(pr.getDepartment().getSite(),request);
        this.putProjectCodeToRequest(pr.getDepartment().getSite(), request);
        //make itemAttacmentRow.jsp edit version
        this.setEditing(true,request);
        
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
        request.setAttribute("X_ROWPAGE", "purchaseOrder/itemRow.jsp");

        //make itemRow.jsp edit version
        this.setEditing(true,request);

        return mapping.findForward("success");
    }
    
    /**
     * action method for canceling PurchaseOrderItem
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward cancel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PurchaseOrderItem poi=this.getPurchaseOrderItemFromSession(request);
        poi.setPurchaseOrder(null);
        this.getPurchaseOrderItemListFromSession(request).remove(poi);
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

    public ActionForward listReceipt(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PurchaseOrderManager fm =  ServiceLocator.getPurchaseOrderManager(request);
        PurchaseOrderItemQueryForm queryForm = (PurchaseOrderItemQueryForm) form;
        
        if(StringUtils.isEmpty(queryForm.getOrder()))
        {
            //init queryForm
            queryForm.setOrder(PurchaseOrderItemQueryOrder.ITEMSPEC.getName());
            queryForm.setDescend(false);
        }
        else if(PurchaseOrderItemQueryOrder.getEnum(queryForm.getOrder())==null)
            throw new RuntimeException("order not found!");
        
        Map conditions = constructQueryMap(queryForm);
        if (queryForm.isIncludeReceivedItem()) {
            conditions.put(PurchaseOrderItemQueryCondition.PO_STATUS_IN2, new Object[] { PurchaseOrderStatus.CONFIRMED.getEnumCode(), PurchaseOrderStatus.RECEIVED.getEnumCode() } );
        } else {
            conditions.put(PurchaseOrderItemQueryCondition.PO_STATUS_EQ, PurchaseOrderStatus.CONFIRMED.getEnumCode());
        }
        Integer userId = this.getCurrentUser(request).getId();
        conditions.put(PurchaseOrderItemQueryCondition.PR_REQUESTOR_OR_PO_INSPECTOR_EQ, new Object[] { userId, userId } );

        if (queryForm.isFirstInit()) {
            queryForm.init(fm.getPurchaseOrderItemListCount(conditions));
        } else {
            queryForm.init();
        }
        
        List result = fm.getPurchaseOrderItemList(
                conditions, 
                queryForm.getPageNoAsInt(), 
                queryForm.getPageSizeAsInt(),
                PurchaseOrderItemQueryOrder.getEnum(queryForm.getOrder()),
                queryForm.isDescend()
        );

        request.setAttribute("X_RESULTLIST", result);
        
        return mapping.findForward("page");
    }
    
    private Map constructQueryMap(PurchaseOrderItemQueryForm queryForm) {
        Map conditions = new HashMap();

        Integer id = ActionUtils.parseInt(queryForm.getId());
        if(id != null) {
            conditions.put(PurchaseOrderItemQueryCondition.ID_EQ, id);
        }       

        String itemSpec = queryForm.getItemSpec();
        if(itemSpec != null) {
            itemSpec = itemSpec.trim();
            if (itemSpec.length()!=0) {
                conditions.put(PurchaseOrderItemQueryCondition.ITEMSPEC_LIKE, itemSpec);
            }
        }       

        String purchaseRequestId = queryForm.getPurchaseRequest_id();
        if(purchaseRequestId != null) {
            purchaseRequestId = purchaseRequestId.trim();
            if (purchaseRequestId.length()!=0) {
                conditions.put(PurchaseOrderItemQueryCondition.PR_ID_LIKE, purchaseRequestId);
            }
        }       

        if (!queryForm.isIncludeReceivedItem()) {
            conditions.put(PurchaseOrderItemQueryCondition.NOT_RECEIVE_ALL, null);
        }
        return conditions;
    }


}