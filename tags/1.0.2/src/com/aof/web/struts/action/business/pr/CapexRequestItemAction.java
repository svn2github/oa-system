/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.web.struts.action.business.pr;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aof.model.business.pr.CapexRequest;
import com.aof.model.business.pr.CapexRequestItem;
import com.aof.service.business.pr.CapexManager;
import com.aof.web.struts.action.BaseAction;
import com.aof.web.struts.action.ServiceLocator;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.action.ActionUtils;
import com.shcnc.struts.form.BeanForm;

/**
 * CapexRequestItem的Action类
 * 
 * @author nicebean
 * @version 1.0 (2005-12-4)
 */
public class CapexRequestItemAction extends BaseAction {

    /**
     * 修改CapexRequestItem
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CapexRequest capexRequest = getCapexRequestFromRequest(request);
        checkRequestor(capexRequest, request);
        //checkEditable(capexRequest);
        
        CapexRequestItemPageManager pageManager = new CapexRequestItemPageManager((BeanForm)form, capexRequest, request);
        pageManager.process();
        request.setAttribute("x_add", Boolean.valueOf(request.getParameter("x_add") != null));
        return mapping.findForward("page");
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
    public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CapexRequest capexRequest = getCapexRequestFromRequest(request);
        checkRequestor(capexRequest, request);
        //checkEditable(capexRequest);
        BeanForm capexRequestItemForm = (BeanForm) form;
        Integer id = ActionUtils.parseInt(capexRequestItemForm.getString("id"));
        if (id == null) {
            throw new ActionException("capexRequestItem.notFound", id);
        }
        capexRequestItemForm.setBeanLoader(ServiceLocator.getBeanLoader(request));
        CapexRequestItem cri = new CapexRequestItem(id);
        capexRequestItemForm.populateToBean(cri);
        if (cri.getSupplier() == null) {
            cri.setSupplierItem(null);
        } else {
            cri.setSupplierName(null);
        }
        request.setAttribute("X_OBJECT", cri);
        request.setAttribute("X_ROWPAGE", "capexRequest/itemRow.jsp");
        request.setAttribute("x_edit", Boolean.TRUE);
        
        return mapping.findForward("success");
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

    /*
    private void checkEditable(CapexRequest cr) {
        if (!cr.isEditable()) {
            throw new ActionException("capexRequest.cannotEdit");
        }
    }
    */
}