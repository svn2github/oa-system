/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.web.struts.action.business.po;

import java.io.InputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.aof.model.business.po.PurchaseOrderAttachment;
import com.aof.service.business.po.PurchaseOrderManager;

import com.aof.web.struts.action.ServiceLocator;
import com.shcnc.servlet.DownloadUploadHelper;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.action.ActionUtils;
import com.shcnc.struts.form.BeanForm;

/**
 * PurchaseOrderAttachment的Action类
 * 
 * @author shilei
 * @version 1.0 (2005-12-3)
 */
public class PurchaseOrderAttachmentAction extends BasePurchaseOrderAction {


    /**
     * 新增PurchaseOrderAttachment
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newObject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        this.setEditing(true,request);
        
        return mapping.findForward("page");
    }

    /**
     * 插入新增的PurchaseOrderAttachment
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        BeanForm beanForm = (BeanForm) form;
        PurchaseOrderAttachment poa = new PurchaseOrderAttachment();
        beanForm.populateToBean(poa, request);
        
        poa.setPurchaseOrder(null);

        FormFile file = (FormFile) beanForm.get("fileContent");
        poa.setFileName(file.getFileName());
        poa.setUploadDate(new Date());

        if (file.getFileSize() > 0) {
            poa.setFileSize(file.getFileSize());
            PurchaseOrderManager pm = ServiceLocator.getPurchaseOrderManager(request);
            poa = pm.insertPurchaseOrderAttachment(poa, file.getInputStream());
        } else {
            throw new ActionException("purchaseOrderAttachment.error.fileSize.zero");
        }
        request.setAttribute("X_OBJECT", poa);
        request.setAttribute("X_ROWPAGE", "purchaseOrder/attachmentRow.jsp");
        
        this.setEditing(true,request);
        
        return mapping.findForward("success");
    }


    /**
     * 下载PurchaseOrderAttachment
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward download(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        PurchaseOrderAttachment poa = this.getPurchaseOrderAttachmentAttachmentFromRequest(request);

        if (poa.getFileSize() == 0) {
            throw new ActionException("purchaseOrderAttachment.error.fileSize.zero");
        } else {
            PurchaseOrderManager pm = ServiceLocator.getPurchaseOrderManager(request);
            InputStream in = pm.getPurchaseOrderAttachmentContent(poa.getId());
            if (in != null) {
                try {
                    DownloadUploadHelper.download(in, poa.getFileName(),
                            DownloadUploadHelper.getMime(poa.getFileName()),
                            poa.getFileSize(), request, response, true);
                } finally {
                    in.close();
                }
            }
        }
        return null;
    }

    private PurchaseOrderAttachment getPurchaseOrderAttachmentAttachmentFromRequest(HttpServletRequest request) throws Exception {
        Integer id = ActionUtils.parseInt(request.getParameter("id"));
        if(id==null) throw new ActionException("purchaseOrderAttachment.idNotSet");
        PurchaseOrderManager pm = ServiceLocator.getPurchaseOrderManager(request);
        PurchaseOrderAttachment poa = pm.getPurchaseOrderAttachment(id);
        if (poa == null)
            throw new ActionException("purchaseOrderAttachment.notFound", id);
        return poa;
    }
}