package com.aof.web.struts.action.business.pr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aof.model.admin.Function;
import com.aof.model.admin.Site;
import com.aof.model.business.pr.PurchaseRequest;
import com.aof.model.metadata.PurchaseRequestStatus;
import com.aof.service.admin.EmailManager;
import com.aof.service.admin.FunctionManager;
import com.aof.service.admin.UserManager;
import com.aof.service.business.pr.PurchaseRequestManager;
import com.aof.web.struts.action.ActionUtils;
import com.aof.web.struts.action.ServiceLocator;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.form.BeanForm;

public class PurchaseRequestPurchaseAction extends BasePurchaseRequestPurchaseAction {

    /**
     * action method for view purchaseRequest's purchasing
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PurchaseRequest pr = this.getPurchaseRequestFromRequest(request);
        this.checkViewable(pr,request);
        this.putPurchaseRequestToRequest(pr,request);

        PurchaseRequestManager pm = ServiceLocator.getPurchaseRequestManager(request);
        request.setAttribute("x_purchaseRequestAttachmentList", pm.getPurchaseRequestAttachmentList(pr));
        request.setAttribute("x_purchaseOrderItemList", pm.getPurchaseOrderItemList(pr));

        this.setEditing(false, request);
        

        return mapping.findForward("page");
    }
    
    /**
     * action method for rejecting purchaseRequest where purchasing
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward reject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String rejectComment = request.getParameter("rejectComment");
        PurchaseRequest pr = this.getPurchaseRequestFromRequest(request);
        this.checkPurchasable(pr,request);
        PurchaseRequestManager pm=ServiceLocator.getPurchaseRequestManager(request);
        String pr_id=pr.getId();
        pm.rejectPurchaseRequestByPurchaser(pr, this.getCurrentUser(request), rejectComment);
        this.postGlobalMessage("purchaseRequest.purchase.reject.success",pr_id,request.getSession());
        return this.getListPurchaseForward();
    }
    
    /**
     * action method for assign purchaseRequest
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward assign(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PurchaseRequest pr = this.getPurchaseRequestFromRequest(request);
        this.checkAssignable(pr, request);
        this.putPurchaseRequestToRequest(pr, request);
        this.putPurchaserListToRequest(pr.getDepartment().getSite(), request);
        
        if (!isBack(request)) {
            BeanForm purchaseRequestAssignForm = (BeanForm) this.getForm("/assignPurchaseRequest_result", request);
            purchaseRequestAssignForm.populateToForm(pr);
        }
        PurchaseRequestManager pm = ServiceLocator.getPurchaseRequestManager(request);
        request.setAttribute("x_purchaseRequestAttachmentList", pm.getPurchaseRequestAttachmentList(pr));
        request.setAttribute("x_purchaseOrderItemList", pm.getPurchaseOrderItemList(pr));

        this.setEditing(false, request);
        

        return mapping.findForward("page");
    }

    /**
     * action method for assign purchaseRequest
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward assignResult(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PurchaseRequest pr = this.getPurchaseRequestFromRequest(request);
        this.checkAssignable(pr, request);

        BeanForm beanForm = (BeanForm) form;
        beanForm.setBeanLoader(ServiceLocator.getBeanLoader(request));
        beanForm.populateToBean(pr, request);

        /**
         * 不必检查设置的Purchaser是否真有Purchaser权限，只需要检查当前用户是否有权assign
         */
        /*
        UserManager um = ServiceLocator.getUserManager(request);
        if (!um.hasSitePower(pr.getDepartment().getSite(), pr.getPurchaser(), this.getPurchaseFunction(request)))
            throw new ActionException("purchaseRequest.assign.purchaserNoPower");
        */
        
        PurchaseRequestManager prm = ServiceLocator.getPurchaseRequestManager(request);
        prm.updatePurchaseRequest(pr);
        
        EmailManager em=ServiceLocator.getEmailManager(request);
        Map context=new HashMap();
        context.put("x_pr",pr);
        context.put("role", EmailManager.EMAIL_ROLE_PURCHASER);
        em.insertEmail(pr.getLogSite(),pr.getPurchaser().getEmail(),"PRAssignee.vm",context);
        
        this.postGlobalMessage("purchaseRequest.assign.success", request.getSession());
        return new ActionForward("assignPurchaseRequest.do?id=" + pr.getId(), true);

    }

    private Function getPurchaseFunction(HttpServletRequest request) {
        FunctionManager fm = ServiceLocator.getFunctionManager(request);
        Function f = fm.getFunction("Purchasing"); 
        if (f == null) throw new ActionException("function.typeNotFound", "PurchaseRequestPurchase");
        return f;
    }

    private void putPurchaserListToRequest(Site site, HttpServletRequest request) {
        UserManager um = ServiceLocator.getUserManager(request);
        request.setAttribute("x_purchaserList", um.getEnabledUserList(getPurchaseFunction(request), site));
    }

    private void putPurchaseRequestToRequest(PurchaseRequest pr,HttpServletRequest request)
    {
        request.setAttribute("x_pr", pr);
        request.setAttribute("x_capex", pr.getCapex());
        request.setAttribute("x_yearlyBudget", pr.getYearlyBudget());
    }
    /**
     * action method for purchasing purchaseRequest
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward purchase(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PurchaseRequest pr = this.getPurchaseRequestFromRequest(request);
        this.checkPurchasable(pr,request);
        this.putPurchaseRequestToRequest(pr,request);
        
        PurchaseRequestManager pm = ServiceLocator.getPurchaseRequestManager(request);
        if (!isBack(request)) {
            this.putPurchaseOrderItemListToSession(pm.getPurchaseOrderItemListWithDetails(pr), request);
        }
        request.setAttribute("x_purchaseRequestAttachmentList", pm.getPurchaseRequestAttachmentList(pr));
        request.setAttribute("x_purchaseOrderItemList", this.getPurchaseOrderItemListFromSession(request));
        this.setEditing(true,request);

        return mapping.findForward("page");
    }
    
    private void checkPurchasable(PurchaseRequest pr,HttpServletRequest request)
    {
        if(!pr.getStatus().equals(PurchaseRequestStatus.APPROVED))
            throw new ActionException("purchaseReqest.purchase.statusMustBeApproved");
        this.checkPower(pr,request);
    }
    
    private void checkPower(PurchaseRequest pr,HttpServletRequest request)
    {
        if(pr.getPurchaser()!=null && !pr.getPurchaser().equals(this.getCurrentUser(request)))
            throw new ActionException("purchaseRequest.purchase.purchaserMustBeSelf");
        this.checkSite(pr.getDepartment().getSite(),request);
    }
    
    private void checkViewable(PurchaseRequest pr,HttpServletRequest request)
    {
        if(!pr.getStatus().equals(PurchaseRequestStatus.BOOKED))
            throw new ActionException("purchaseReqest.purchase.view.statusMustBeBooked");
        this.checkPower(pr,request);
    }
    
    private void checkAssignable(PurchaseRequest pr, HttpServletRequest request) {
        PurchaseRequestStatus status = pr.getStatus();
        if (!PurchaseRequestStatus.APPROVED.equals(status) && !PurchaseRequestStatus.BOOKED.equals(status)) {
            throw new ActionException("purchaseRequest.assign.statusMustBeApprovedOrBooked");
        }
        this.checkAssignPower(pr, request);
    }

    private void checkAssignPower(PurchaseRequest pr,HttpServletRequest request) {
        this.checkDepartment(pr.getDepartment(), request);
    }
    
    
    /**
     * action method for result of purchasing purchaseRequest
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */

    public ActionForward purchaseResult(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PurchaseRequest pr = this.getPurchaseRequestFromRequest(request);
        this.checkPurchasable(pr,request);
        List purchaseOrderItemList=this.getPurchaseOrderItemListFromSession(request);
        PurchaseRequestManager pm=ServiceLocator.getPurchaseRequestManager(request);
        List oldPoItemList=pm.getPurchaseOrderItemList(pr);
        if(this.isToCreatePO(request))
        {
            String[] po_ids=(String[]) ((DynaBean)(form)).get("po_ids");
            List selectedPOItemList=new ArrayList();
            for (int i = 0; i < po_ids.length; i++) {
                Integer poItem_id=ActionUtils.parseInt(po_ids[i]);
                selectedPOItemList.add(this.getPurchaseOrderItemFromSession(poItem_id,request));
            }
            pm.savePurchaseOrderItemsOfPurchaseRequestAndCreatePO(pr,oldPoItemList,purchaseOrderItemList,
                    selectedPOItemList,this.getCurrentUser(request));
            this.postGlobalMessage("purchaseRequest.purchase.saveAndCreatePO.success",request.getSession());
        }
        else
        {
            pm.savePurchaseOrderItemsOfPurchaseRequest(pr,oldPoItemList,purchaseOrderItemList,this.getCurrentUser(request));
            this.postGlobalMessage("purchaseRequest.purchase.save.success",request.getSession());
        }
        if(pr.getStatus().equals(PurchaseRequestStatus.BOOKED))
            return this.getViewPurchaseForward(pr);
        else
            return this.getPurchaseForward(pr);
    }
    
    private boolean isToCreatePO(HttpServletRequest request)
    {
        return "true".equals(request.getParameter("createPO"));
    }

    private ActionForward getPurchaseForward(PurchaseRequest pr) {
        return new ActionForward("purchasePurchaseRequest.do?id=" + pr.getId(),true);
    }
    
    private ActionForward getViewPurchaseForward(PurchaseRequest pr) {
        return new ActionForward("viewPurchaseRequest_purchase.do?id=" + pr.getId(),true);
    }
    
    private ActionForward getListPurchaseForward() {
        return new ActionForward("listPurchaseRequest_purchase.do?",true);
    }


    
    protected String getPurchaseRequsetId(HttpServletRequest request)
    {
        return request.getParameter("id");
    }
}
