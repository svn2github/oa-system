/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.web.struts.action.business.po;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import net.sourceforge.model.business.po.PaymentScheduleDetail;
import net.sourceforge.model.business.po.PurchaseOrder;
import net.sourceforge.web.struts.action.ServiceLocator;
import com.shcnc.struts.form.BeanForm;

/**
 * Action类，处理PaymentScheduleDetail
 * 
 * @author shi1
 * @version 1.0 (2005-12-4)
 */
public class PaymentScheduleDetailAction extends BasePurchaseOrderAction  {
    protected String getPurchaseOrderId(HttpServletRequest request) {
        return request.getParameter("purchaseOrder_id");
    }

    /**
     * 编辑PaymentScheduleDetail
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PaymentScheduleDetail psd=this.getPaymentScheduleDetailFromSession(request);
        //this.checkPurchaseOrderEditPower(psd.getPurchaseOrder(),request);
        request.setAttribute("x_psd",psd);
        
        if(!this.isBack(request))
        {
            BeanForm itemForm=(BeanForm)this.getForm("/updatePaymentScheduleDetail",request);
            itemForm.populateToForm(psd);
        }
        
        this.setAdding(false,request);
        
        return mapping.findForward("page");
    }
    
    private void setAdding(boolean b,HttpServletRequest request)
    {
        request.setAttribute("x_add",Boolean.valueOf(b));
    }
    
    /**
     * action method for creating PaymentScheduleDetailItem
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward newObject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PurchaseOrder po=this.getPurchaseOrderFromRequest(request);
        //this.checkPurchaseOrderEditPower(po,request);
        if(!this.isBack(request))
        {
            BeanForm beanForm=(BeanForm)this.getForm("/insertPaymentScheduleDetail",request);
            PaymentScheduleDetail psd = new PaymentScheduleDetail();
            psd.setPurchaseOrder(po);
            beanForm.populateToForm(psd);
        }
        
        this.setAdding(true,request);
        
        return mapping.findForward("page");
    }    
    
    /**
     * 新增PaymentScheduleDetail
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward insert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PurchaseOrder po=this.getPurchaseOrderFromRequest(request);
        //this.checkPurchaseOrderEditPower(po,request);
        
        BeanForm beanForm = (BeanForm) form;
        beanForm.setBeanLoader(ServiceLocator.getBeanLoader(request));
        
        PaymentScheduleDetail psd = new PaymentScheduleDetail(this.getNewPaymentScheduleDetailId(request));
        psd.setPurchaseOrder(po);
        beanForm.populateToBean(psd,request);
        
        this.insertPaymentScheduleDetail(psd,request);
        
        request.setAttribute("X_OBJECT", psd);
        request.setAttribute("X_ROWPAGE", ROW_PAGE);

        //make psdItemRow.jsp edit version
        this.setEditing(true,request);

        return mapping.findForward("success");
    }
    
    private final static String ROW_PAGE="purchaseOrder/paymentScheduleDetailRow.jsp";
    
    private void insertPaymentScheduleDetail(PaymentScheduleDetail psd, HttpServletRequest request) {
        this.getPaymentScheduleDetailListFromSession(request).add(psd);
    }
    
    private Integer getNewPaymentScheduleDetailId(HttpServletRequest request) {
        List psdList=this.getPaymentScheduleDetailListFromSession(request);
        int min=0;
        for (Iterator iter = psdList.iterator(); iter.hasNext();) {
            PaymentScheduleDetail psd = (PaymentScheduleDetail) iter.next();
            min=Math.min(min,psd.getId().intValue());
        }
        return new Integer(min-1);
    }
    

    /**
     * 保存修改的PaymentScheduleDetail
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        BeanForm beanForm = (BeanForm) form;
        beanForm.setBeanLoader(ServiceLocator.getBeanLoader(request));
        
        PaymentScheduleDetail psd = this.getPaymentScheduleDetailFromSession(request);
        beanForm.populateToBean(psd,request);
        
        request.setAttribute("X_OBJECT", psd);
        request.setAttribute("X_ROWPAGE", ROW_PAGE);

        //make psdItemRow.jsp edit version
        this.setEditing(true,request);

        return mapping.findForward("success");
    }
    
    /**
     * action method for deleting PaymentScheduleDetail
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PaymentScheduleDetail psd=this.getPaymentScheduleDetailFromSession(request);
        this.getPaymentScheduleDetailListFromSession(request).remove(psd);
        return mapping.findForward("success");
    }

}