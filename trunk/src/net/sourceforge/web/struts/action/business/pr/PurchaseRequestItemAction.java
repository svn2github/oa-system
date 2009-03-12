/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.web.struts.action.business.pr;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import net.sourceforge.model.admin.PurchaseSubCategory;
import net.sourceforge.model.admin.Site;
import net.sourceforge.model.business.pr.PurchaseRequest;
import net.sourceforge.model.business.pr.PurchaseRequestItem;
import net.sourceforge.model.metadata.YesNo;
import net.sourceforge.service.admin.ExchangeRateManager;
import net.sourceforge.service.admin.ProjectCodeManager;
import net.sourceforge.service.admin.SupplierManager;
import net.sourceforge.service.business.pr.PurchaseRequestManager;
import net.sourceforge.web.struts.action.ServiceLocator;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.action.ActionUtils;
import com.shcnc.struts.form.BeanForm;

/**
 * PurchaseRequestItem的Action类
 * 
 * @author shi1
 * @version 1.0 (2005-12-4)
 */
public class PurchaseRequestItemAction extends BasePurchaseRequestAction  {
    
    public ActionForward viewSelf(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return this.view(mapping,form,request,response,true);
    }
    
    public ActionForward viewOther(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return this.view(mapping,form,request,response,false);
    }
    
    /**
     * view PurchaseRequestItem detail
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response,boolean isSelf) throws Exception {
        PurchaseRequestItem pri=this.getPurchaseRequestItemFromRequest(request);
        //this.checkPurchaseRequestViewPower(pri.getPurchaseRequest(),request);
        request.setAttribute("x_pri",pri);
        
        //recharge
        this.setRechargeInfoToRequestForView(pri,pri.getRecharges(),request);
        
        this.putSelfOrOtherPostfixToRequest(isSelf,request);
        return mapping.findForward("page");
    }
    
    private PurchaseRequestItem getPurchaseRequestItemFromRequest(HttpServletRequest request) {
        Integer id = ActionUtils.parseInt(request.getParameter("id"));
        if (id == null) throw new ActionException("purchaseRequestItem.idNotSet");
        PurchaseRequestManager prm =ServiceLocator.getPurchaseRequestManager(request);
        PurchaseRequestItem pri=prm.getPurchaseRequestItemWithDetails(id);
        if(pri==null)
            throw new ActionException("purchaseRequestItem.notFound",id);
        return pri;
    }

    private static final String ATTRIBUTE_EXCHANGELIST="x_exchangeRateList";
    
    private List getAndPutExchangeRateListToRequest(Site site,HttpServletRequest request)
    {
        ExchangeRateManager erm = ServiceLocator.getExchangeRateManager(request);
        List exchangeRateList = erm.getEnabledExchangeRateListBySiteIncludeBaseCurrency(site);
        request.setAttribute(ATTRIBUTE_EXCHANGELIST, exchangeRateList);
        return exchangeRateList;
    }
    
    private void putSupplierListToRequest(Site site, PurchaseSubCategory psc ,List exchangeRateList,HttpServletRequest request)
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

    public ActionForward newObjectSelf(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return this.newObject(mapping,form,request,response,true);
    }
    
    public ActionForward newObjectOther(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return this.newObject(mapping,form,request,response,false);
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
    public ActionForward newObject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response,boolean isSelf) throws Exception {
        PurchaseRequest pr = this.getPurchaseRequestFromRequest(request);
        //this.checkPurchaseRequestEditPower(pr,request);
        
        BeanForm purchaseRequestItemForm=(BeanForm)this.getForm("/insertPurchaseRequestItem_self",request);
        PurchaseRequestItem pri = new PurchaseRequestItem();
        request.setAttribute("x_pr",pr);
        
        if(!this.isBack(request))
        {
            pri.setIsRecharge(YesNo.NO);
            pri.setBuyForDepartment(pr.getDepartment());
            pri.setPurchaseRequest(pr);
            purchaseRequestItemForm.populateToForm(pri);
            pri.setBuyForUser(pr.getRequestor());
        }
        
        //recharge 
        this.setRechargeInfoToRequest(pri,new ArrayList(),pr.getDepartment().getSite(),purchaseRequestItemForm,request);
        request.setAttribute("X_FORMNAME", "purchaseRequestItemForm");
        
        //combo list
        Site site = pr.getDepartment().getSite();
        List exchangeList=this.getAndPutExchangeRateListToRequest(site, request);
        this.putSupplierListToRequest(site, pr.getPurchaseSubCategory(),exchangeList,request);
        
        //projectCode list
        this.putProjectCodeToRequest(site, request);
        
        //selfOrOther
        this.putSelfOrOtherPostfixToRequest(isSelf,request);
        //itemList.jsp, itemRow.jsp, attachmentList.jsp, attachmentRow is edit version
        this.setEditing(true,request);
        
        request.setAttribute("x_add",Boolean.TRUE);
        
        return mapping.findForward("page");
    }    
    
    protected String getPurchaseRequsetId(HttpServletRequest request)
    {
        return request.getParameter("purchaseRequest_id");
    }
    
    /**
     * action method for deleting PurchaseRequestItem
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PurchaseRequestItem pri=this.getPurchaseRequestItemFromSession(request);
        PurchaseRequestManager pm=ServiceLocator.getPurchaseRequestManager(request);
        if(pri.getId().intValue()>0 && pm.isPurchaseRequestItemInUse(pri))
        {
            throw new ActionException("purchaseRequestItem.delete.isUse");
        }
        //this.checkPurchaseRequestEditPower(pri.getPurchaseRequest(),request);
        this.getPurchaseRequestItemListFromSession(request).remove(pri);
        return mapping.findForward("success");
    }
    
    public ActionForward editSelf(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return this.edit(mapping,form,request,response,true);
    }
    
    public ActionForward editOther(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return this.edit(mapping,form,request,response,false);
    }
    
    
    /**
     * 修改PurchaseRequestItem
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response,boolean isSelf) throws Exception {
        PurchaseRequestItem pri=this.getPurchaseRequestItemFromSession(request);
        //this.checkPurchaseRequestEditPower(pri.getPurchaseRequest(),request);
        
        request.setAttribute("x_pri",pri);
        request.setAttribute("x_pr",pri.getPurchaseRequest());
        
        BeanForm purchaseRequestItemForm=(BeanForm)this.getForm("/updatePurchaseRequestItem_self",request);
        
        if(!this.isBack(request))
        {
            purchaseRequestItemForm.populateToForm(pri);
        }
        
        //recharge
        this.setRechargeInfoToRequest(pri,pri.getRecharges(),pri.getPurchaseRequest().getDepartment().getSite(),purchaseRequestItemForm,request);
        request.setAttribute("X_FORMNAME", "purchaseRequestItemForm");

        //combo list
        Site site = pri.getPurchaseRequest().getDepartment().getSite();
        List exchangeList=this.getAndPutExchangeRateListToRequest(site,request);
        this.putSupplierListToRequest(site,pri.getPurchaseRequest().getPurchaseSubCategory(),exchangeList,request);
        
        //projectCode list
        this.putProjectCodeToRequest(site, request);
        
        //selfOrOther
        this.putSelfOrOtherPostfixToRequest(isSelf,request);
        
        //make itemAttacmentRow.jsp edit version
        this.setEditing(true,request);
        
        request.setAttribute("x_add",Boolean.FALSE);
        
        return mapping.findForward("page");
    }
    
    private PurchaseRequestItem getPurchaseRequestItemFromSession(HttpServletRequest request) {
        Integer id = ActionUtils.parseInt(request.getParameter("id"));
        if (id == null) throw new ActionException("purchaseRequestItem.idNotSet");
        List itemList=this.getPurchaseRequestItemListFromSession(request);
        for (Iterator iter = itemList.iterator(); iter.hasNext();) {
            PurchaseRequestItem item = (PurchaseRequestItem) iter.next();
            if(item.getId().equals(id)) return item;
        }
        throw new ActionException("purchaseRequestItem.notFound",id);
    }
    


    private void processSupplier(PurchaseRequestItem pri)
    {
        if (pri.getSupplier() == null) {
            pri.setSupplierItem(null);
        } else {
            pri.setSupplierName(null);
        }
    }
    
    public ActionForward insertSelf(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return this.insert(mapping,form,request,response,true);
    }
    
    public ActionForward insertOther(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return this.insert(mapping,form,request,response,false);
    }
    
    
    public ActionForward insert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response,boolean isSelf) throws Exception {
        PurchaseRequest pr=this.getPurchaseRequestFromRequest(request);
        //this.checkPurchaseRequestEditPower(pr,request);
        
        BeanForm purchaseRequestItemForm = (BeanForm) form;
        purchaseRequestItemForm.setBeanLoader(ServiceLocator.getBeanLoader(request));
        
        PurchaseRequestItem pri = new PurchaseRequestItem(this.getNewPurchaseRequestItemId(request));
        pri.setPurchaseRequest(pr);
        pri.setBuyForUser(pr.getRequestor());
        purchaseRequestItemForm.populateToBean(pri,request);
        this.processSupplier(pri);
        
        isSelf=pr.getIsDelegate().equals(YesNo.NO);
        if(isSelf)
        {
            pri.setIsRecharge(YesNo.NO);
        }
        if(isSelf || pri.getIsRecharge().equals(YesNo.YES))
        {
            pri.setBuyForDepartment(pr.getDepartment());
            pri.setBuyForUser(pr.getRequestor());
        }
        
        pri.setRecharges(this.getRechargeInfoFromRequest(pri,  request));
        pri.setAttachments(this.getPurchaseRequestItemAttachmentListFromRequest(request));
        
        this.insertPurchaseRequestItem(pri,request);
        
        request.setAttribute("X_OBJECT", pri);
        request.setAttribute("X_ROWPAGE", "purchaseRequest/itemRow.jsp");

        //make itemRow.jsp edit version
        this.setEditing(true,request);

        return mapping.findForward("success");
    }
    

    private void insertPurchaseRequestItem(PurchaseRequestItem pri, HttpServletRequest request) {
        this.getPurchaseRequestItemListFromSession(request).add(pri);
    }

    private Integer getNewPurchaseRequestItemId(HttpServletRequest request) {
        List itemList=this.getPurchaseRequestItemListFromSession(request);
        int min=0;
        for (Iterator iter = itemList.iterator(); iter.hasNext();) {
            PurchaseRequestItem pri = (PurchaseRequestItem) iter.next();
            min=Math.min(min,pri.getId().intValue());
        }
        return new Integer(min-1);
    }
    
    public ActionForward updateSelf(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return this.update(mapping,form,request,response,true);
    }
    
    public ActionForward updateOther(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return this.update(mapping,form,request,response,false);
    }
    

    /**
     * 保存修改的CapexRequestItem
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response,boolean isSelf) throws Exception {
        //PurchaseRequest pr=this.getPurchaseRequestFromRequest(request);
        //this.checkPurchaseRequestEditPower(pr,request);
        
        BeanForm purchaseRequestItemForm = (BeanForm) form;
        purchaseRequestItemForm.setBeanLoader(ServiceLocator.getBeanLoader(request));
        
        PurchaseRequestItem pri = this.getPurchaseRequestItemFromSession(request);
        
        isSelf=pri.getPurchaseRequest().getIsDelegate().equals(YesNo.NO);
        if(isSelf || purchaseRequestItemForm.getString("isRecharge").equals(YesNo.YES.getEnumCode().toString()))
            purchaseRequestItemForm.populateToBean(pri,request,new String[]{"buyForDepartment.id","buyForUser.id"});
        else
            purchaseRequestItemForm.populateToBean(pri,request);
        
        if(isSelf)
        {
            pri.setIsRecharge(YesNo.NO);
        }

        this.processSupplier(pri);
        
        pri.setRecharges(this.getRechargeInfoFromRequest(pri,  request));
        pri.setAttachments(this.getPurchaseRequestItemAttachmentListFromRequest(request));
        
        request.setAttribute("X_OBJECT", pri);
        request.setAttribute("X_ROWPAGE", "purchaseRequest/itemRow.jsp");

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