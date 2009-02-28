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

import com.aof.model.business.po.PurchaseOrderItemAttachment;
import com.aof.service.business.po.PurchaseOrderManager;
import com.aof.web.struts.action.ActionUtils;
import com.aof.web.struts.action.BaseAction;
import com.aof.web.struts.action.ServiceLocator;
import com.shcnc.servlet.DownloadUploadHelper;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.form.BeanForm;


/**
 * PurchaseRequestItemAttachment的Action类
 * 
 * @author shilei
 * @version 1.0 (2005-12-3)
 */
public class PurchaseOrderItemAttachmentAction extends BaseAction {
    
    
    /**
     * 新增PurchaseRequestItemAttachment
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newObject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return mapping.findForward("page");
    }

    /**
     * 插入新增的PurchaseRequestItemAttachment
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
        PurchaseOrderItemAttachment poia = new PurchaseOrderItemAttachment();
        beanForm.populateToBean(poia, request);

        poia.setPurchaseOrderItem(null);

        FormFile file = (FormFile) beanForm.get("fileContent");
        poia.setFileName(file.getFileName());
        poia.setUploadDate(new Date());

        if (file.getFileSize() > 0) {
            poia.setFileSize(file.getFileSize());
            PurchaseOrderManager pm = ServiceLocator.getPurchaseOrderManager(request);
            poia = pm.insertPurchaseOrderItemAttachment(poia, file.getInputStream());
        } else {
            throw new ActionException("purchaseOrderItemAttachment.error.fileSize.zero");
        }
        
        request.setAttribute("X_OBJECT", poia);
        request.setAttribute("X_ROWPAGE", "purchaseOrder/itemAttachmentRow.jsp");
        request.setAttribute("x_edit",Boolean.TRUE);

        return mapping.findForward("success");
    }

    /**
     * 下载PurchaseOrderItemAttachment
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward download(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        PurchaseOrderItemAttachment poia = this.getPurchaseOrderItemAttachmentAttachmentFromRequest(request);

        if (poia.getFileSize() == 0) {
            throw new ActionException("purchaseRequestAttachment.error.fileSize.zero");
        } else {
            PurchaseOrderManager pm = ServiceLocator.getPurchaseOrderManager(request);
            InputStream in = pm.getPurchaseRequestItemAttachmentContent(poia.getId());
            if (in != null) {
                try {
                    DownloadUploadHelper
                        .download(in, poia.getFileName(), DownloadUploadHelper.getMime(poia.getFileName()), poia.getFileSize(), request, response, true);
                } finally {
                    in.close();
                }
            }
        }
        return null;
    }

    private PurchaseOrderItemAttachment getPurchaseOrderItemAttachmentAttachmentFromRequest(HttpServletRequest request) throws Exception {
        Integer id = ActionUtils.parseInt(request.getParameter("id"));
        if(id==null) throw new ActionException("purchaseOrderItemAttachment.idNotSet");
        PurchaseOrderManager pm = ServiceLocator.getPurchaseOrderManager(request);
        PurchaseOrderItemAttachment poia = pm.getPurchaseOrderItemAttachment(id);
        if (poia == null)
            throw new ActionException("purchaseOrderItemAttachment.notFound", id);
        return poia;
    }
}