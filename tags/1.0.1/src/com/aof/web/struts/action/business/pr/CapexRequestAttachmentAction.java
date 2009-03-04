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

import com.aof.model.business.pr.CapexRequest;
import com.aof.model.business.pr.CapexRequestAttachment;
import com.aof.service.business.pr.CapexManager;
import com.aof.web.struts.action.BaseAction;
import com.aof.web.struts.action.ServiceLocator;
import com.shcnc.servlet.DownloadUploadHelper;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.action.ActionUtils;
import com.shcnc.struts.form.BeanForm;

/**
 * CapexRequestAttachment的Action类
 * 
 * @author nicebean
 * @version 1.0 (2005-12-3)
 */
public class CapexRequestAttachmentAction extends BaseAction {

    /**
     * 新增CapexRequestAttachment
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newObject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CapexRequest capexRequest = getCapexRequestFromRequest(request);
        checkRequestor(capexRequest, request);
        checkEditable(capexRequest);
        if (!isBack(request)) {
            CapexRequestAttachment capexRequestAttachment = new CapexRequestAttachment();
            capexRequestAttachment.setCapexRequest(capexRequest);
            BeanForm capexRequestAttachmentForm = (BeanForm) getForm("/insertCapexRequestAttachment", request);
            capexRequestAttachmentForm.populateToForm(capexRequestAttachment);
        }
        return mapping.findForward("page");
    }

    /**
     * 插入新增的CapexRequestAttachment
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CapexRequest capexRequest = getCapexRequestFromRequest(request);
        checkRequestor(capexRequest, request);
        checkEditable(capexRequest);

        BeanForm capexRequestAttachmentForm = (BeanForm) form;
        CapexRequestAttachment capexRequestAttachment = new CapexRequestAttachment();
        capexRequestAttachmentForm.populateToBean(capexRequestAttachment, request);

        FormFile file = (FormFile) capexRequestAttachmentForm.get("fileContent");
        capexRequestAttachment.setFileName(file.getFileName());
        capexRequestAttachment.setUploadDate(new Date());

        if (file.getFileSize() > 0) {
            capexRequestAttachment.setFileSize(file.getFileSize());
            CapexManager cm = ServiceLocator.getCapexManager(request);
            capexRequestAttachment = cm.saveCapexRequestAttachment(capexRequestAttachment, file.getInputStream());
        } else {
            throw new ActionException("capexRequestAttachment.error.fileSize.zero");
        }
        request.setAttribute("X_OBJECT", capexRequestAttachment);
        request.setAttribute("X_ROWPAGE", "capexRequest/attachmentRow.jsp");
        request.setAttribute("x_edit", Boolean.TRUE);
        return mapping.findForward("success");
    }

    /**
     * 删除CapexRequestAttachment
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CapexRequestAttachment capexRequestAttachment = getCapexRequestAttachmentAttachmentFromRequest(request);
        CapexRequest capexRequest = capexRequestAttachment.getCapexRequest();
        checkRequestor(capexRequest, request);
        checkEditable(capexRequest);
        CapexManager cm = ServiceLocator.getCapexManager(request);
        cm.deleteCapexRequestAttachment(capexRequestAttachment);
        return mapping.findForward("success");
    }

    /**
     * 下载CapexRequestAttachment
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward download(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        CapexRequestAttachment capexRequestAttachment = this.getCapexRequestAttachmentAttachmentFromRequest(request);

        if (capexRequestAttachment.getFileSize() == 0) {
            throw new ActionException("capexRequestAttachment.error.fileSize.zero");
        } else {
            InputStream in = ServiceLocator.getCapexManager(request).getCapexRequestAttachmentContent(capexRequestAttachment.getId());
            if (in != null) {
                try {
                    DownloadUploadHelper.download(in, capexRequestAttachment.getFileName(), DownloadUploadHelper.getMime(capexRequestAttachment.getFileName()),
                            capexRequestAttachment.getFileSize(), request, response, true);
                } finally {
                    in.close();
                }
            } 
        } 
        return null;
    }

    private CapexRequestAttachment getCapexRequestAttachmentAttachmentFromRequest(HttpServletRequest request) throws Exception {
        Integer id = ActionUtils.parseInt(request.getParameter("id"));
        CapexManager cm = ServiceLocator.getCapexManager(request);
        CapexRequestAttachment capexRequestAttachment = cm.getCapexRequestAttachment(id);
        if (capexRequestAttachment == null)
            throw new ActionException("capexRequestAttachment.notFound", id);
        return capexRequestAttachment;
    }

    private CapexRequest getCapexRequestFromRequest(HttpServletRequest request) throws Exception {
        Integer capexRequestId = ActionUtils.parseInt(request.getParameter("capexRequest_id"));
        CapexManager capexManager = ServiceLocator.getCapexManager(request);
        CapexRequest capexRequest = capexManager.getCapexRequest(capexRequestId);
        if (capexRequest == null) {
            throw new ActionException("capexRequest.notFound", capexRequestId);
        }
        return capexRequest;
    }

    private void checkRequestor(CapexRequest cr, HttpServletRequest request) {
        if (!getCurrentUser(request).equals(cr.getRequestor())) {
            throw new ActionException("capexRequest.requestor.notSelf");
        }
    }

    private void checkEditable(CapexRequest cr) {
        if (!cr.isEditable()) {
            throw new ActionException("capexRequest.cannotEdit");
        }
    }
}