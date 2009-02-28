/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.web.struts.action.business.pr;

import java.io.InputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.aof.model.business.pr.PurchaseRequestAttachment;
import com.aof.service.business.pr.PurchaseRequestManager;

import com.aof.web.struts.action.ServiceLocator;

import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.action.ActionUtils;
import com.shcnc.struts.form.BeanForm;

/**
 * PurchaseRequestAttachment的Action类
 * 
 * @author shilei
 * @version 1.0 (2005-12-3)
 */
public class PurchaseRequestAttachmentAction extends BasePurchaseRequestAction {

    public ActionForward newObjectSelf(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return newObject(mapping,form,request,response,true);
    }
    
    public ActionForward newObjectOther(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return newObject(mapping,form,request,response,false);
    }
    

    /**
     * 新增PurchaseRequestAttachment
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newObject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response,boolean isSelf) throws Exception {
        //selfOrOther
        this.putSelfOrOtherPostfixToRequest(isSelf,request);
        this.setEditing(true,request);
        
        return mapping.findForward("page");
    }
    
    public ActionForward insertSelf(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return this.insert(mapping,form,request,response,true);
    }
    
    public ActionForward insertOther(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return this.insert(mapping,form,request,response,false);
    }
    

    /**
     * 插入新增的PurchaseRequestAttachment
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response,boolean isSelf) throws Exception {
        BeanForm beanForm = (BeanForm) form;
        PurchaseRequestAttachment pra = new PurchaseRequestAttachment();
        beanForm.populateToBean(pra, request);
        
        pra.setPurchaseRequest(null);

        FormFile file = (FormFile) beanForm.get("fileContent");
        pra.setFileName(file.getFileName());
        pra.setUploadDate(new Date());

        if (file.getFileSize() > 0) {
            pra.setFileSize(file.getFileSize());
            PurchaseRequestManager pm = ServiceLocator.getPurchaseRequestManager(request);
            pra = pm.insertPurchaseRequestAttachment(pra, file.getInputStream());
        } else {
            throw new ActionException("purchaseRequestAttachment.error.fileSize.zero");
        }
        request.setAttribute("X_OBJECT", pra);
        request.setAttribute("X_ROWPAGE", "purchaseRequest/attachmentRow.jsp");
        
        this.putSelfOrOtherPostfixToRequest(isSelf,request);
        this.setEditing(true,request);
        
        return mapping.findForward("success");
    }


    /**
     * 下载PurchaseRequestAttachment
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward download(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        PurchaseRequestAttachment pra = this.getPurchaseRequestAttachmentAttachmentFromRequest(request);
        //if(pra.getPurchaseRequest()!=null)
        //{
            //this.checkPurchaseRequestViewPower(pra.getPurchaseRequest(),request);
        //}

        if (pra.getFileSize() == 0) {
            throw new ActionException("purchaseRequestAttachment.error.fileSize.zero");
        } else {
            PurchaseRequestManager pm = ServiceLocator.getPurchaseRequestManager(request);
            InputStream in = pm.getPurchaseRequestAttachmentContent(pra.getId());
            if (in != null) {
                try {
                    return this.downloadAttachment(in, pra.getFileName(), request);
                    /*
                    DownloadUploadHelper.download(in, pra.getFileName(),
                            DownloadUploadHelper.getMime(pra.getFileName()),
                            pra.getFileSize(), request, response, true);
                    */
                } finally {
                    in.close();
                }
            }
        }
        return null;
    }

    private PurchaseRequestAttachment getPurchaseRequestAttachmentAttachmentFromRequest(HttpServletRequest request) throws Exception {
        Integer id = ActionUtils.parseInt(request.getParameter("id"));
        if(id==null) throw new ActionException("purchaseRequestAttachment.idNotSet");
        PurchaseRequestManager pm = ServiceLocator.getPurchaseRequestManager(request);
        PurchaseRequestAttachment pra = pm.getPurchaseRequestAttachment(id);
        if (pra == null)
            throw new ActionException("purchaseRequestAttachment.notFound", id);
        return pra;
    }
}