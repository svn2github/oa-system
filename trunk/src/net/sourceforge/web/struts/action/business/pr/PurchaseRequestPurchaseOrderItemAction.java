/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.web.struts.action.business.pr;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import net.sourceforge.model.admin.PurchaseSubCategory;
import net.sourceforge.model.admin.Site;
import net.sourceforge.model.business.po.PurchaseOrderItem;
import net.sourceforge.model.business.po.PurchaseOrderItemAttachment;
import net.sourceforge.model.business.po.PurchaseOrderItemRecharge;
import net.sourceforge.model.business.pr.PurchaseRequest;
import net.sourceforge.model.metadata.YesNo;
import net.sourceforge.service.admin.ExchangeRateManager;
import net.sourceforge.service.admin.ProjectCodeManager;
import net.sourceforge.service.admin.PurchaseTypeManager;
import net.sourceforge.service.admin.SupplierManager;
import net.sourceforge.service.business.po.PurchaseOrderManager;
import net.sourceforge.web.struts.action.ServiceLocator;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.action.ActionUtils;
import com.shcnc.struts.form.BeanForm;

/**
 * Action类，处理在采购purchaseRequest上的PurchaseOrderItem
 * 
 * @author shi1
 * @version 1.0 (2005-12-4)
 */
public class PurchaseRequestPurchaseOrderItemAction extends BasePurchaseRequestPurchaseAction  {
    
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
    
    private PurchaseOrderItem getPurchaseOrderItemFromRequest(HttpServletRequest request) {
        Integer id = ActionUtils.parseInt(request.getParameter("id"));
        if (id == null) throw new ActionException("purchaseOrderItem.idNotSet");
        PurchaseOrderManager pm =ServiceLocator.getPurchaseOrderManager(request);
        PurchaseOrderItem poi=pm.getPurchaseOrderItemWithDetails(id);
        if(poi==null)
            throw new ActionException("purchaseOrderItem.notFound",id);
        return poi;
    }

    private static final String ATTRIBUTE_EXCHANGELIST="x_exchangeRateList";
    
    private List getAndPutExchangeRateListToRequest(Site site,HttpServletRequest request)
    {
        ExchangeRateManager erm = ServiceLocator.getExchangeRateManager(request);
        List exchangeRateList = erm.getEnabledExchangeRateListBySiteIncludeBaseCurrency(site);
        request.setAttribute(ATTRIBUTE_EXCHANGELIST, exchangeRateList);
        return exchangeRateList;
    }
    
    /*
    private void putSupplierListToRequest(Site site,HttpServletRequest request)
    {
        SupplierManager sm = ServiceLocator.getSupplierManager(request);
        List supplierList = sm.getEnabledNonAirTicketSuppliersForSiteAndIncludeGlobal(site);
        request.setAttribute("x_supplierList", supplierList);
    }
    */

    private void putSupplierListToRequest(Site site, PurchaseSubCategory psc, List exchangeRateList,HttpServletRequest request)
    {
        SupplierManager sm = ServiceLocator.getSupplierManager(request);
        List supplierList = sm.getSuitableSupplierListForPurchase(site, psc, exchangeRateList);
        request.setAttribute("x_supplierList", supplierList);
    }
    
    private void putProjectCodeToRequest(Site site, HttpServletRequest request) 
    {
        ProjectCodeManager pcm = ServiceLocator.getProjectCodeManager(request);
        List projectCodeList = pcm.getEnabledProjectCodeListBySite(site);
        request.setAttribute("x_projectCodeList", projectCodeList);
    }

    protected String getPurchaseRequsetId(HttpServletRequest request)
    {
        return request.getParameter("purchaseRequestItem_purchaseRequest_id");
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
        PurchaseRequest pr=poi.getPurchaseRequestItem().getPurchaseRequest();
        
        request.setAttribute("x_poi",poi);
        
        BeanForm itemForm=(BeanForm)this.getForm("/updatePurchaseOrderItem_purchaseRequest",request);
        
        if(!this.isBack(request))
        {
            itemForm.populateToForm(poi);
        }
        
        //recharge
        this.setRechargeInfoToRequest(poi,poi.getRecharges(),
                pr.getDepartment().getSite(),itemForm,request);
        request.setAttribute("X_FORMNAME", "purchaseOrderItemForm");

        //combo list
        Site site = pr.getDepartment().getSite();
        List exchangeList=this.getAndPutExchangeRateListToRequest(site,request);
        //this.putSupplierListToRequest(pr.getDepartment().getSite(), request);
        this.putSupplierListToRequest(site, pr.getPurchaseSubCategory(),exchangeList,request);
        this.putProjectCodeToRequest(site, request);
        this.putPurchaseTypeListToRequest(site, request);
        
        //make itemAttacmentRow.jsp edit version
        this.setEditing(true,request);
        
        this.setSpliting(false,request);
        
        return mapping.findForward("page");
    }
    
    private void putPurchaseTypeListToRequest(Site site,HttpServletRequest request)
    {
        PurchaseTypeManager pm=ServiceLocator.getPurchaseTypeManager(request);
        request.setAttribute("x_purchaseTypeList",pm.getEnabledPurchaseTypeList(site));
    }
    
    private PurchaseOrderItem getPurchaseOrderItemFromSession(HttpServletRequest request) {
        Integer id = ActionUtils.parseInt(request.getParameter("id"));
        return this.getPurchaseOrderItemFromSession(id,request);
    }

    private void setSpliting(boolean b,HttpServletRequest request)
    {
        request.setAttribute("x_split",new Boolean(b));
    }
    
    

    private void processSupplier(PurchaseOrderItem poi)
    {
        if (poi.getSupplier() == null) {
            poi.setItem(null);
        } else {
            poi.setSupplierName(null);
        }
    }
    
    /**
     * action method for creating PurchaseRequestItem
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward split(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PurchaseOrderItem oldPoi=this.getPurchaseOrderItemFromSession(request);
        request.setAttribute("x_oldPoi",oldPoi);
        PurchaseRequest pr=oldPoi.getPurchaseRequestItem().getPurchaseRequest();
        //this.checkPurchaseRequestEditPower(pr,request);
        
        BeanForm itemForm=(BeanForm)this.getForm("/splitPurchaseOrderItem_result",request);
        PurchaseOrderItem poi = new PurchaseOrderItem(oldPoi.getId());
        
        if(!this.isBack(request))
        {
            PropertyUtils.copyProperties(poi,oldPoi);
            
            poi.setAttachments(Collections.EMPTY_LIST);
            poi.setQuantity(0);
            poi.setIsRecharge(YesNo.NO);
            poi.setRecharges(Collections.EMPTY_LIST);
            
            itemForm.populateToForm(poi);
        }
        
        //recharge 
        this.setRechargeInfoToRequest(poi,new ArrayList(),pr.getDepartment().getSite(),itemForm,request);
        request.setAttribute("X_FORMNAME", "purchaseOrderItemForm");
        
        //combo list
        Site site = pr.getDepartment().getSite();
        List exchangeList=this.getAndPutExchangeRateListToRequest(site,request);
        //this.putSupplierListToRequest(pr.getDepartment().getSite(),request);
        this.putSupplierListToRequest(site, pr.getPurchaseSubCategory(),exchangeList,request);
        this.putProjectCodeToRequest(site, request);
        this.putPurchaseTypeListToRequest(site,request);
        
        //itemList.jsp, itemRow.jsp, attachmentList.jsp, attachmentRow is edit version
        this.setEditing(true,request);
        
        this.setSpliting(true,request);
        
        return mapping.findForward("page");
    } 
    
    public ActionForward splitResult(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PurchaseOrderItem oldPoi=this.getPurchaseOrderItemFromSession(request);
        
        //this.checkPurchaseRequestEditPower(pr,request);
        
        BeanForm purchaseRequestItemForm = (BeanForm) form;
        purchaseRequestItemForm.setBeanLoader(ServiceLocator.getBeanLoader(request));
        
        PurchaseOrderItem poi = new PurchaseOrderItem(this.getNewPurchaseOrderItemId(request));
        if(purchaseRequestItemForm.getString("isRecharge").equals(YesNo.YES.getEnumCode().toString()))
            purchaseRequestItemForm.populateToBean(poi,request,new String[]{"buyForDepartment.id","buyForUser.id"});
        else
            purchaseRequestItemForm.populateToBean(poi,request);
        poi.setPurchaseRequestItem(oldPoi.getPurchaseRequestItem());
        poi.setReturnedQuantity(new Integer(0));
        poi.setCancelledQuantity(new Integer(0));
        poi.setReceivedQuantity(new Integer(0));
        
        if(poi.getQuantity()>=oldPoi.getQuantity())
            throw new ActionException("purchaseRequest.purchase.purchaseOrderItem.split.newQuantityMustLessThanOld");
        
        this.processSupplier(poi);
        
        poi.setRecharges(this.getRechargeInfoFromRequest(poi,  request));
        poi.setAttachments(this.getPurchaseOrderItemAttachmentListFromRequest(request));
        
        updateItemQuantity(oldPoi,oldPoi.getQuantity()-poi.getQuantity());
        this.insertPurchaseOrderItem(poi,request);
        
        request.setAttribute("X_OBJECT", poi);
        request.setAttribute("X_ROWPAGE", "purchaseRequestPurchase/itemRow.jsp");

        //make itemRow.jsp edit version
        this.setEditing(true,request);

        return mapping.findForward("success");
    }
    
    private void updateItemQuantity(PurchaseOrderItem poi,int newQuantity)
    {
        int oldQuantity=poi.getQuantity();
        poi.setQuantity(newQuantity);
        for (Iterator iter = poi.getRecharges().iterator(); iter.hasNext();) {
            PurchaseOrderItemRecharge poir = (PurchaseOrderItemRecharge) iter.next();
            BigDecimal oldAmount=poir.getAmount();
            double amount=oldAmount.doubleValue();
            amount=amount/oldQuantity*newQuantity;
            poir.setAmount(new BigDecimal(amount).setScale(oldAmount.scale(),BigDecimal.ROUND_HALF_UP));
        }
    }
    

    private void insertPurchaseOrderItem(PurchaseOrderItem poi, HttpServletRequest request) {
        this.getPurchaseOrderItemListFromSession(request).add(poi);
    }
    
    protected List getPurchaseOrderItemAttachmentListFromRequest(HttpServletRequest request) {
        String[] ids = this.getParameterValues("item_attachment_id", request);
        List attachmentList = new ArrayList();
        PurchaseOrderManager prm = ServiceLocator.getPurchaseOrderManager(request);
        for (int i = 0; i < ids.length; i++) {
            Integer id = ActionUtils.parseInt(ids[i]);
            PurchaseOrderItemAttachment pria = prm.getPurchaseOrderItemAttachment(id);
            attachmentList.add(pria);
        }
        return attachmentList;
    }

    protected Integer getNewPurchaseOrderItemId(HttpServletRequest request) {
        List itemList=this.getPurchaseOrderItemListFromSession(request);
        int min=0;
        for (Iterator iter = itemList.iterator(); iter.hasNext();) {
            PurchaseOrderItem poi = (PurchaseOrderItem) iter.next();
            min=Math.min(min,poi.getId().intValue());
        }
        return new Integer(min-1);
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
        this.processSupplier(poi);
        
        poi.setRecharges(this.getRechargeInfoFromRequest(poi,  request));
        poi.setAttachments(this.getPurchaseOrderItemAttachmentListFromRequest(request));
        
        request.setAttribute("X_OBJECT", poi);
        request.setAttribute("X_ROWPAGE", "purchaseRequestPurchase/itemRow.jsp");

        //make itemRow.jsp edit version
        this.setEditing(true,request);

        return mapping.findForward("success");
    }

    /* (non-Javadoc)
     * @see net.sourceforge.web.struts.action.business.RechargeAction#selectRechargeCustomer(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public ActionForward selectRechargeCustomer(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return super.selectRechargeCustomer(mapping, form, request, response);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.web.struts.action.business.RechargeAction#selectRechargeEntity(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public ActionForward selectRechargeEntity(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return super.selectRechargeEntity(mapping, form, request, response);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.web.struts.action.business.RechargeAction#selectRechargePerson(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public ActionForward selectRechargePerson(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return super.selectRechargePerson(mapping, form, request, response);
    }


}