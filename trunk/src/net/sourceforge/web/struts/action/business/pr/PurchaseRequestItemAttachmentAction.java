/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.web.struts.action.business.pr;

import java.io.InputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import net.sourceforge.model.business.pr.PurchaseRequestItemAttachment;
import net.sourceforge.service.business.pr.PurchaseRequestManager;
import net.sourceforge.web.struts.action.ServiceLocator;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.action.ActionUtils;
import com.shcnc.struts.form.BeanForm;

/**
 * PurchaseRequestItemAttachment的Action类
 * 
 * @author shilei
 * @version 1.0 (2005-12-3)
 */
public class PurchaseRequestItemAttachmentAction extends BasePurchaseRequestAction {
    
    public ActionForward newObjectSelf(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return newObject(mapping,form,request,response,true);
    }
    
    public ActionForward newObjectOther(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return newObject(mapping,form,request,response,false);
    }
    

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
    public ActionForward newObject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response,boolean isSelf) throws Exception {
        this.putSelfOrOtherPostfixToRequest(isSelf,request);
        this.setEditing(true, request);

        return mapping.findForward("page");
    }
    
    public ActionForward insertSelf(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return this.insert(mapping,form,request,response,true);
    }
    
    public ActionForward insertOther(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return this.insert(mapping,form,request,response,false);
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
    public ActionForward insert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response,boolean isSelf) throws Exception {
        BeanForm beanForm = (BeanForm) form;
        PurchaseRequestItemAttachment pria = new PurchaseRequestItemAttachment();
        beanForm.populateToBean(pria, request);

        pria.setPurchaseRequestItem(null);

        FormFile file = (FormFile) beanForm.get("fileContent");
        pria.setFileName(file.getFileName());
        pria.setUploadDate(new Date());

        if (file.getFileSize() > 0) {
            pria.setFileSize(file.getFileSize());
            PurchaseRequestManager pm = ServiceLocator.getPurchaseRequestManager(request);
            pria = pm.insertPurchaseRequestItemAttachment(pria, file.getInputStream());
        } else {
            throw new ActionException("capexRequestAttachment.error.fileSize.zero");
        }
        request.setAttribute("X_OBJECT", pria);
        request.setAttribute("X_ROWPAGE", "purchaseRequest/itemAttachmentRow.jsp");

        this.putSelfOrOtherPostfixToRequest(isSelf,request);
        this.setEditing(true, request);

        return mapping.findForward("success");
    }

    /**
     * 下载PurchaseRequestItemAttachment
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward download(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        PurchaseRequestItemAttachment pria = this.getPurchaseRequestItemAttachmentFromRequest(request);
        //if (pria.getPurchaseRequestItem() != null)
            //this.checkPurchaseRequestViewPower(pria.getPurchaseRequestItem().getPurchaseRequest(), request);

        if (pria.getFileSize() == 0) {
            throw new ActionException("purchaseRequestAttachment.error.fileSize.zero");
        } else {
            PurchaseRequestManager pm = ServiceLocator.getPurchaseRequestManager(request);
            InputStream in = pm.getPurchaseRequestItemAttachmentContent(pria.getId());
            if (in != null) {
                try {
                    return this.downloadAttachment(in, pria.getFileName(), request);
                    /*
                    DownloadUploadHelper
                            .download(in, pria.getFileName(), DownloadUploadHelper.getMime(pria.getFileName()), pria.getFileSize(), request, response, true);
                    */
                } finally {
                    in.close();
                }
            }
        }
        return null;
    }

    private PurchaseRequestItemAttachment getPurchaseRequestItemAttachmentFromRequest(HttpServletRequest request) throws Exception {
        Integer id = ActionUtils.parseInt(request.getParameter("id"));
        if(id==null) throw new ActionException("purchaseRequestItemAttachment.idNotSet");
        PurchaseRequestManager pm = ServiceLocator.getPurchaseRequestManager(request);
        PurchaseRequestItemAttachment pria = pm.getPurchaseRequestItemAttachment(id);
        if (pria == null)
            throw new ActionException("purchaseRequestItemAttachment.notFound", id);
        return pria;
    }
}