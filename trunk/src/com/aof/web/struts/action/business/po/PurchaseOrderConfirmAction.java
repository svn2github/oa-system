/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.web.struts.action.business.po;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.aof.model.admin.Supplier;
import com.aof.model.admin.User;
import com.aof.model.admin.query.SupplierContractQueryCondition;
import com.aof.model.admin.query.SupplierContractQueryOrder;
import com.aof.model.admin.query.UserQueryCondition;
import com.aof.model.admin.query.UserQueryOrder;
import com.aof.model.business.po.PurchaseOrder;
import com.aof.model.business.po.PurchaseOrderItem;
import com.aof.model.metadata.ContractStatus;
import com.aof.model.metadata.EnabledDisabled;
import com.aof.model.metadata.PurchaseOrderStatus;
import com.aof.model.metadata.SupplierConfirmStatus;
import com.aof.model.metadata.SupplierPromoteStatus;
import com.aof.model.metadata.YesNo;
import com.aof.service.admin.CountryManager;
import com.aof.service.admin.CurrencyManager;
import com.aof.service.admin.DepartmentManager;
import com.aof.service.admin.EmailManager;
import com.aof.service.admin.SupplierContractManager;
import com.aof.service.admin.SupplierManager;
import com.aof.service.admin.UserManager;
import com.aof.service.business.po.PurchaseOrderManager;
import com.aof.web.struts.action.ActionUtils;
import com.aof.web.struts.action.ServiceLocator;
import com.aof.web.struts.form.admin.UserQueryForm;
import com.shcnc.hibernate.PersistentEnum;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.form.BeanForm;


/**
 * action class for confirming PurchaseOrder
 * @author shilei
 * @version 1.0 (Dec 23, 2005)
 */
public class PurchaseOrderConfirmAction extends BasePurchaseOrderAction {
    protected void putPurchaseOrderDetailsToRequest(PurchaseOrder po, HttpServletRequest request) {
        PurchaseOrderManager pm = ServiceLocator.getPurchaseOrderManager(request);
        if (!isBack(request) ) {
            this.putPurchaseOrderItemListToSession(pm.getPurchaseOrderItemListWithDetails(po),request);
        }
        request.setAttribute("x_purchaseOrderItemList", this.getPurchaseOrderItemListFromSession(request));
        request.setAttribute("x_purchaseOrderAttachmentList", pm.getPurchaseOrderAttachmentList(po));
        request.setAttribute("x_paymentScheduleDetailList", pm.getPaymentScheduleDetailList(po));
        request.setAttribute("X_APPROVELIST", pm.getPurchaseOrderApproveRequestList(po));
    }
    
    /**
     * action method for selecting inspector
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward selectInspector(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PurchaseOrder po = this.getPurchaseOrderFromRequest(request);
        request.setAttribute("x_po", po);
        PurchaseOrderManager pm=ServiceLocator.getPurchaseOrderManager(request);
        Set requestorSet=pm.getRequestorsOfPurchaseOrder(po);
        request.setAttribute("x_requestorSet", requestorSet);
        
        DepartmentManager dm=ServiceLocator.getDepartmentManager(request);
        List departmentList=dm.getEnabledDepartmentTreeOfSite(po.getSite());
        request.setAttribute("x_departmentList",departmentList);
        
        UserQueryForm uqForm=(UserQueryForm) form;
        if(StringUtils.isEmpty(uqForm.getOrder()))
        {
            uqForm.setOrder(UserQueryOrder.NAME.getName());
            uqForm.setDescend(false);
        }
        Map conds=this.constructInspectorQueryMap(uqForm);
        conds.put(UserQueryCondition.SITE_ID_EQ,po.getSite().getId());
        conds.put(UserQueryCondition.ENABLED_EQ,EnabledDisabled.ENABLED.getEnumCode());
        
        UserManager um=ServiceLocator.getUserManager(request);
        
        if (uqForm.isFirstInit()) {
            uqForm.init(um.getUserListCount(conds),5);
        } else {
            uqForm.init();
        }
        
        List resultList=um.getUserList(conds,uqForm.getPageNoAsInt(),uqForm.getPageSizeAsInt(),
                UserQueryOrder.getEnum(uqForm.getOrder()),uqForm.isDescend());
        request.setAttribute("X_RESULTLIST", resultList);
        
        return mapping.findForward("page");
    }

    private Map constructInspectorQueryMap(UserQueryForm uqForm) {
        Map conds=new HashMap();
        Integer departmnetId=ActionUtils.parseInt(uqForm.getDepartmentId());
        if(departmnetId!=null)
            conds.put(UserQueryCondition.DEPARTMENT_ID_EQ,departmnetId);
        
        if(StringUtils.isNotEmpty(uqForm.getName()))
            conds.put(UserQueryCondition.NAME_LIKE,uqForm.getName());
        return conds;
    }

    /**
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward confirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PurchaseOrder po = this.getPurchaseOrderFromRequest(request);
        this.checkPurchaseOrderPurchasePower(po, request);
        request.setAttribute("x_po", po);
        
        
        this.putSupplierInfoToRequest(po.getSupplier(),request);

        if (!this.isBack(request)) {
            if (po.getSubCategory() != null) {
                po.setInspector(po.getSubCategory().getInspector());
            }
            
            BeanForm beanForm=(BeanForm) this.getForm("/confirmPurchaseOrder_result",request);
            beanForm.populateToForm(po);
            
            request.setAttribute("x_inspector",po.getInspector());
        }
        else
        {
            request.setAttribute("x_inspector",this.getInspectorFromRequest(request));
        }
        this.putPurchaseOrderDetailsToRequest(po, request);
        this.setEditing(true, request);
        
        request.setAttribute("x_fromTa",new Boolean(po.getSubCategory()==null));        
        
        return mapping.findForward("page");
    }
    
    private User getInspectorFromRequest(HttpServletRequest request) throws Exception {
        String parameterName="inspector_id";
        String str_user_id=request.getParameter(parameterName);
        if(StringUtils.isNotEmpty(str_user_id))
        {
           return (User) this.getObjectFromRequestAndCheckExists(parameterName,User.class,"id",request);
        }
        else
        {
            return null;
        }
    }

    private void putSupplierInfoToRequest(Supplier supplier,HttpServletRequest request)
    {
        request.setAttribute("x_supplier",supplier);
        request.setAttribute("x_supplierEnabled",new Boolean(supplier.getEnabled().equals(EnabledDisabled.ENABLED)));
        request.setAttribute("x_supplierConfirmed",new Boolean(supplier.getConfirmed().equals(YesNo.YES)));
        request.setAttribute("x_supplierSiteLevel",new Boolean(supplier.getPromoteStatus().equals(SupplierPromoteStatus.SITE)));
        
    }
    
    private void checkPurchaseOrderPurchasePower(PurchaseOrder po, HttpServletRequest request) {
        this.checkSite(po.getSite(),request);
        this.checkApproved(po);
    }

    private void checkApproved(PurchaseOrder po) {
        if(!po.getStatus().equals(PurchaseOrderStatus.APPROVED))
            throw new ActionException("purchaseOrder.confirm.mustBeApproved");
    }

    public ActionForward reject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PurchaseOrder po = this.getPurchaseOrderFromRequest(request);
        this.checkPurchaseOrderPurchasePower(po, request);
        PurchaseOrderManager pm=ServiceLocator.getPurchaseOrderManager(request);
        String comment = request.getParameter("rejectComment");
        pm.rejectPurhcaseOrderWhenFinalConfirm(po.getId(),this.getCurrentUser(request),comment);
        this.postGlobalMessage("purchaseOrder.conform.reject.success",po.getId(),request.getSession());
        return this.getListForwardFor(po);
    }
    
    private void checkSupplierConfirmed(Supplier supplier)
    {
        if(supplier.getConfirmed().equals(YesNo.NO))
            throw new ActionException("purchaseOrder.confirm.supplier.mustBeConfirmed");
    }


    public ActionForward confirmResult(
            ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        PurchaseOrder po = this.getPurchaseOrderFromRequest(request);
        this.checkPurchaseOrderPurchasePower(po, request);
        this.checkSupplierConfirmed(po.getSupplier());

        BeanForm beanForm=(BeanForm)form;
        beanForm.setBeanLoader(ServiceLocator.getBeanLoader(request));
        beanForm.populateToBean(po,request);
        
        if(po.getInspector()!=null && po.getInspector().equals(po.getRequestor()))
        {
            throw new ActionException("purchaseOrder.finalConfirm.inspectorEqualsRequestor");
        }
        
        List itemList = this.getPurchaseOrderItemListFromSession(request);
        
        PurchaseOrderManager pm = ServiceLocator.getPurchaseOrderManager(request);
        pm.confirmPurchaseOrder(po, itemList,this.getCurrentUser(request));

        this.clearPurchaseOrderItemListFromSession(request);
        
        this.postGlobalMessage("purchaseOrder.confirm.success", request.getSession());
        
        if (po.getSupplier().getAirTicket() == YesNo.NO) { //Add by Jackey, if the po is airticket po, don't need to invoke email inform
            EmailManager em=ServiceLocator.getEmailManager(request);
            Map context =new HashMap();
            context.put("x_po",po);
            Set emailTOUserSet=new HashSet();
            emailTOUserSet.add(po.getInspector());
            for (Iterator iter = itemList.iterator(); iter.hasNext();) {
                PurchaseOrderItem item = (PurchaseOrderItem) iter.next();
                emailTOUserSet.add(item.getPurchaseRequestItem().getPurchaseRequest().getRequestor());
            }
            for (Iterator iter = emailTOUserSet.iterator(); iter.hasNext();) {
                User emailToUser = (User) iter.next();
                context.put("x_user",emailToUser);
                if (emailToUser.equals(po.getInspector())) {
                    context.put("role", EmailManager.EMAIL_ROLE_INSPECTOR);
                } else {
                    context.put("role", EmailManager.EMAIL_ROLE_REQUESTOR);
                }
                em.insertEmail(po.getSite(),emailToUser.getEmail(),"POFinalConfirm.vm",context);
            }
        }
        
        return getViewForwardFor(po);

    }

    private ActionForward getViewForwardFor(PurchaseOrder po) {
        if(po.getStatus().equals(PurchaseOrderStatus.RECEIVED) ||po.getSubCategory()==null)
            return new ActionForward("viewPurchaseOrder_confirm.do?id="+po.getId(),true);
        else if(po.getStatus().equals(PurchaseOrderStatus.CONFIRMED))
            return new ActionForward("editConfirmedPurchaseOrder.do?id="+po.getId(),true);
        return null;
    }
    
    
    /**
     * action method for viewing purchaseOrder
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
        this.checkSite(po.getSite(),request);
        request.setAttribute("x_po", po);
        request.setAttribute("x_confirm",Boolean.TRUE);

        this.putPurchaseOrderDetailsForView(po, request);

        this.setEditing(false, request);

        return mapping.findForward("page");
    }
    
    public ActionForward editConfirmed(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PurchaseOrder po = this.getPurchaseOrderFromRequest(request);
        if(po.getSubCategory()==null) throw new ActionException("purchaseOrder.updateInspector.fromTa");
        this.checkSite(po.getSite(),request);
        this.checkConfirmed(po);
        request.setAttribute("x_po", po);
        
        if (!this.isBack(request)) {
            BeanForm beanForm=(BeanForm) this.getForm("/confirmPurchaseOrder_result",request);
            beanForm.populateToForm(po);
            
            request.setAttribute("x_inspector",po.getInspector());
        }
        else
        {
            request.setAttribute("x_inspector",this.getInspectorFromRequest(request));
        }
        
        this.putPurchaseOrderDetailsForView(po, request);
        this.setEditing(false, request);
        return mapping.findForward("page");
    }
    
    public ActionForward updateConfirmed(
            ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        PurchaseOrder po = this.getPurchaseOrderFromRequest(request);
        if(po.getSubCategory()==null) throw new ActionException("purchaseOrder.updateInspector.fromTa");
        this.checkSite(po.getSite(),request);
        this.checkConfirmed(po);
        User oldInspector=po.getInspector();
        
        BeanForm beanForm=(BeanForm)form;
        beanForm.populateToBean(po,request);
        
        User newInspector=po.getInspector();
        
        
        if(!oldInspector.equals(newInspector))
        {
            PurchaseOrderManager pm = ServiceLocator.getPurchaseOrderManager(request);
            po.setInspector(oldInspector);
            pm.updatePurchaseOrderInspector(po,newInspector,this.getCurrentUser(request));
        }

        this.postGlobalMessage("purchaseOrder.editConfirmed.success", request.getSession());
        return new ActionForward("editConfirmedPurchaseOrder.do?id="+po.getId(),true);

    }
    

    private void checkConfirmed(PurchaseOrder po) {
        if(!po.getStatus().equals(PurchaseOrderStatus.CONFIRMED))
            throw new ActionException("purchaseOrder.editConfirmed.notConfirmed");
    }

    private ActionForward getListForwardFor(PurchaseOrder po)
    {
        return new ActionForward("listPurchaseOrder_confirm.do",true);
    }


    private Supplier getSupplierFromRequest(HttpServletRequest request) throws Exception {
        return (Supplier)(this.getObjectFromRequestAndCheckExists("id",Supplier.class,"id",request));
    }
    
    private void checkSupplier(Supplier supplier, HttpServletRequest request) throws Exception {
        if (supplier.getPromoteStatus().equals(SupplierPromoteStatus.GLOBAL)) {
            throw new ActionException("supplier.error.siteEditGlobal");
        } else {
            this.checkSite(supplier.getSite(), request);
        }
    }
    
    private void putContractListToRequest(Supplier supplier, HttpServletRequest request) throws Exception {
        SupplierContractManager scm = ServiceLocator.getSupplierContractManager(request);
        Map conditions = new HashMap();
        conditions.put(SupplierContractQueryCondition.SUPPLIER_ID_EQ, supplier.getId());
        List result = scm.getSupplierContractList(conditions, 0, -1, SupplierContractQueryOrder.ID, false);
        request.setAttribute("x_contractList", result);
    }
    
    /**
     * 确认Supplier
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward confirmSupplier(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Supplier supplier = getSupplierFromRequest(request);

        checkSupplier(supplier, request);

        request.setAttribute("x_supplier", supplier);
        if (!isBack(request)) {
            BeanForm supplierForm = (BeanForm) getForm("/confirmSupplierResult_purchaseOrder", request);
            supplierForm.populateToForm(supplier);
        }
        putContractListToRequest(supplier, request);
        putConfirmSupplierEnumListToRequest(request);
        request.setAttribute("x_action", "/confirmSupplierResult_purchaseOrder");
        request.setAttribute("x_confirm", "true");
        request.setAttribute("x_fromPO", "true");

        if (supplier.getConfirmStatus() == SupplierConfirmStatus.NEW)
            return mapping.findForward("pageConfirmNew");
        else
            return mapping.findForward("pageConfirmModify");
    }
    
    /**
     * 确认Supplier的结果
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward confirmSupplierResult(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        Supplier supplier = getSupplierFromRequest(request);
        boolean cancel = (!request.getParameter("cancel").equals("false"));

        checkSupplier(supplier, request);

        BeanForm supplierForm = (BeanForm) form;
        if (!cancel) {
            supplierForm.populateToBean(supplier, request);
        }

        SupplierManager supplierManager = ServiceLocator.getSupplierManager(request);
        if (!cancel)
            supplierManager.confirmSupplier(supplier);
        else
            supplierManager.cancelSupplier(supplier);
        
        this.putSupplierInfoToRequest(supplier,request);
        request.setAttribute("X_CONTENTPAGE", "purchaseOrderConfirm/supplierContent.jsp");
        return mapping.findForward("success");
    }
    private void putConfirmSupplierEnumListToRequest(HttpServletRequest request) throws Exception {
        request.setAttribute("X_YESNOLIST", PersistentEnum.getEnumList(YesNo.class));
        request.setAttribute("X_ENABLEDDISABLEDLIST", PersistentEnum.getEnumList(EnabledDisabled.class));
        request.setAttribute("X_SUPPLIERSTATUSLIST", PersistentEnum.getEnumList(ContractStatus.class));
        List list = PersistentEnum.getEnumList(SupplierPromoteStatus.class);
        request.setAttribute("X_PROMOTESTATUSLIST", list);
        putEnabledCountryCityListToRequest(request);
        CurrencyManager cm = ServiceLocator.getCurrencyManager(request);
        request.setAttribute("X_CURRENCYLIST", cm.getAllEnabledCurrencyList());
    }
    
    private void putEnabledCountryCityListToRequest(HttpServletRequest request) {
        List countryList = getEnabledCountryCityList(request);
        request.setAttribute("x_countryList", countryList);
    }
    
    private List getEnabledCountryCityList(HttpServletRequest request) {
        CountryManager cm = ServiceLocator.getCountryManager(request);
        return cm.listEnabledCountryCity();
    }
    
    public ActionForward exportPDF(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PurchaseOrder po = this.getPurchaseOrderFromRequest(request);
        this.checkSite(po.getSite(),request);
        this.putPurchaseOrderDetailsForView(po, request);
        this.exportPDF(po,request,response);
        return null;
    }

    
}