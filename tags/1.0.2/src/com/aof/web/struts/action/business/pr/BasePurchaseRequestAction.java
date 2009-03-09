/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.web.struts.action.business.pr;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.aof.model.business.BaseApproveRequest;
import com.aof.model.business.pr.Capex;
import com.aof.model.business.pr.PurchaseRequest;
import com.aof.model.business.pr.PurchaseRequestAttachment;
import com.aof.model.business.pr.PurchaseRequestItemAttachment;
import com.aof.model.business.pr.YearlyBudget;
import com.aof.model.metadata.ApproveStatus;
import com.aof.model.metadata.PurchaseRequestStatus;
import com.aof.model.metadata.YesNo;
import com.aof.service.business.pr.CapexManager;
import com.aof.service.business.pr.PurchaseRequestManager;
import com.aof.service.business.pr.YearlyBudgetManager;
import com.aof.web.struts.action.ActionUtils;
import com.aof.web.struts.action.ServiceLocator;
import com.aof.web.struts.action.business.RechargeAction;
import com.shcnc.hibernate.PersistentEnum;
import com.shcnc.struts.action.ActionException;

/**
 * Base Class of Action classes for domian model PurchaseRequest
 * @author shilei
 * @version 1.0 (Dec 15, 2005)
 */
public class BasePurchaseRequestAction extends RechargeAction {
    
    
    
    protected void setEditing(boolean isEdit,HttpServletRequest request)
    {
        request.setAttribute("x_edit", Boolean.valueOf(isEdit));
    }

    protected void checkCreatorSelf(PurchaseRequest purchaseRequest, HttpServletRequest request) {
        if (!purchaseRequest.getCreator().equals(this.getCurrentUser(request)))
            throw new ActionException("purchaseRequest.createor.notSelf");
    }

    protected void checkRequestorSelf(PurchaseRequest purchaseRequest, HttpServletRequest request) {        
        if (!purchaseRequest.getRequestor().equals(this.getCurrentUser(request)))
            throw new ActionException("purchaseRequest.requestor.notSelf");
    }

    protected void checkEditable(PurchaseRequest purchaseRequest) {
        if (!purchaseRequest.isEditable())
            throw new ActionException("purchaseRequest.notEditable");
    }

    protected void putSelfOrOtherPostfixToRequest(boolean isSelf,HttpServletRequest request) {
        if (isSelf)
            request.setAttribute("x_postfix", "_self");
        else
            request.setAttribute("x_postfix", "_other");
    }


    protected void checkPurchaseRequestEditPower(PurchaseRequest purchaseRequest, HttpServletRequest request) {
        this.checkPurchaseRequestViewPower(purchaseRequest,request);
        this.checkEditable(purchaseRequest);
    }

    protected void checkPurchaseRequestViewPower(PurchaseRequest purchaseRequest, HttpServletRequest request) {
        if (purchaseRequest.getIsDelegate().equals(YesNo.YES)) {
            this.checkCreatorSelf(purchaseRequest, request);
        } else {
            this.checkRequestorSelf(purchaseRequest, request);
        }
        
    }
    
    protected PurchaseRequest getPurchaseRequestFromRequest(HttpServletRequest request)
    {
        String id = this.getPurchaseRequsetId(request);
        if(id==null) throw new ActionException("purchaseRequest.idNotSet");
        PurchaseRequestManager purchaseRequestManager = ServiceLocator.getPurchaseRequestManager(request);
        PurchaseRequest purchaseRequest = purchaseRequestManager.getPurchaseRequest(id);
        if (purchaseRequest == null)
            throw new ActionException("purchaseRequest.notFound", id);
        return purchaseRequest;
    }

    protected void putPurchaseRequestDetailsToRequest(PurchaseRequest pr, HttpServletRequest request) {
        PurchaseRequestManager pm = ServiceLocator.getPurchaseRequestManager(request);
        if (!isBack(request) ) {
            this.putPurchaseRequestItemListToSession(pm.getPurchaseRequestItemListWithDetails(pr),request);
            request.setAttribute("x_purchaseRequestAttachmentList", pm.getPurchaseRequestAttachmentList(pr));
        } else {
            request.setAttribute("x_purchaseRequestAttachmentList", this.getPurchaseRequestAttachmentListFromRequest(request));
        }
        request.setAttribute("x_purchaseRequestItemList", this.getPurchaseRequestItemListFromSession(request));
        request.setAttribute("X_APPROVELIST", pm.getPurchaseRequestApproveRequestList(pr));
    }
    
    protected void putPurchaseRequestDetailsForView(PurchaseRequest pr, HttpServletRequest request) {
        PurchaseRequestManager pm = ServiceLocator.getPurchaseRequestManager(request);
        request.setAttribute("x_purchaseRequestAttachmentList", pm.getPurchaseRequestAttachmentList(pr));
        request.setAttribute("x_purchaseRequestItemList", pm.getPurchaseRequestItemList(pr));
        List approveList = pm.getPurchaseRequestApproveRequestList(pr);
        request.setAttribute("X_APPROVELIST", approveList);
        if (approveList.size() > 0) {
            BaseApproveRequest approveRequest = (BaseApproveRequest)approveList.get(0);
            request.setAttribute("x_canWithDraw", Boolean.valueOf(approveRequest.getStatus().equals(ApproveStatus.WAITING_FOR_APPROVE)));
        }
    }


    protected String getPurchaseRequsetId(HttpServletRequest request)
    {
        return request.getParameter("id");
    }
    
    protected String getPurchaseRequestItemListAttributeName(HttpServletRequest request)
    {
        String pr_id=this.getPurchaseRequsetId(request);
        if(pr_id==null) throw new ActionException("purchaseRequset.idNotSet");
        return "purchaseRequestItemList_"+this.getPurchaseRequsetId(request);
    }
    
    protected void clearPurchaseRequestItemListFromSession(HttpServletRequest request)
    {
        request.getSession().removeAttribute(this.getPurchaseRequestItemListAttributeName(request));
    }
    
    protected void putPurchaseRequestItemListToSession(List itemList,HttpServletRequest request) {
        request.getSession().setAttribute(
                this.getPurchaseRequestItemListAttributeName(request),itemList);
    }

    protected List getPurchaseRequestItemListFromSession(HttpServletRequest request) {
        return (List) request.getSession().getAttribute(
                this.getPurchaseRequestItemListAttributeName(request));
    }

    protected List getPurchaseRequestAttachmentListFromRequest(HttpServletRequest request) {
        String[] ids = this.getParameterValues("attachment_id", request);
        List attachmentList = new ArrayList();
        PurchaseRequestManager prm = ServiceLocator.getPurchaseRequestManager(request);
        for (int i = 0; i < ids.length; i++) {
            Integer id = ActionUtils.parseInt(ids[i]);
            PurchaseRequestAttachment pra = prm.getPurchaseRequestAttachment(id);
            attachmentList.add(pra);
        }
        return attachmentList;
    }

    protected List getPurchaseRequestItemAttachmentListFromRequest(HttpServletRequest request) {
        String[] ids = this.getParameterValues("item_attachment_id", request);
        List attachmentList = new ArrayList();
        PurchaseRequestManager prm = ServiceLocator.getPurchaseRequestManager(request);
        for (int i = 0; i < ids.length; i++) {
            Integer id = ActionUtils.parseInt(ids[i]);
            PurchaseRequestItemAttachment pria = prm.getPurchaseRequestItemAttachment(id);
            attachmentList.add(pria);
        }
        return attachmentList;
    }

    protected void putEnumListToRequest(HttpServletRequest request) {
        request.setAttribute("x_statusList", PersistentEnum.getEnumList(PurchaseRequestStatus.class));
    }

    protected void putCanViewCapexAmountToRequest(Capex cp, HttpServletRequest request) {
        CapexManager cm = ServiceLocator.getCapexManager(request);
        request.setAttribute("x_canViewCapexAmount",new Boolean(cm.canViewCapexAmount(cp,this.getCurrentUser(request))));
    }

    protected void putCanViewYearlyBudgetAmount(YearlyBudget yb, HttpServletRequest request) {
        YearlyBudgetManager ym = ServiceLocator.getYearlyBudgetManager(request);
        request.setAttribute("x_canViewYearlyBudgetAmount",new Boolean(ym.canViewYearlyBudgetAmount(yb,this.getCurrentUser(request))));
    
    }

}
