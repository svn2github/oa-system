package net.sourceforge.web.struts.action.business.pr;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sourceforge.model.business.po.PurchaseOrderItem;
import net.sourceforge.model.business.pr.PurchaseRequest;
import net.sourceforge.service.business.pr.PurchaseRequestManager;
import net.sourceforge.web.struts.action.ServiceLocator;
import net.sourceforge.web.struts.action.business.RechargeAction;
import com.shcnc.struts.action.ActionException;

public class BasePurchaseRequestPurchaseAction extends RechargeAction {
    
    protected PurchaseOrderItem getPurchaseOrderItemFromSession(Integer id,HttpServletRequest request) {
        if (id == null) throw new ActionException("purchaseRequestItem.idNotSet");
        List itemList=this.getPurchaseOrderItemListFromSession(request);
        for (Iterator iter = itemList.iterator(); iter.hasNext();) {
            PurchaseOrderItem item = (PurchaseOrderItem) iter.next();
            if(item.getId().equals(id)) return item;
        }
        throw new ActionException("purchaseRequestItem.notFound",id);
    }

    protected void setEditing(boolean isEdit,HttpServletRequest request)
    {
        request.setAttribute("x_edit", Boolean.valueOf(isEdit));
    }
    
    protected void putPurchaseOrderItemListToSession(List poItemList, HttpServletRequest request) {
        request.getSession().setAttribute(
                this.getPurchaseOrderItemListAttributeName(request),poItemList);
        
    }

    protected String getPurchaseOrderItemListAttributeName(HttpServletRequest request) {
        String pr_id=this.getPurchaseRequsetId(request);
        if(pr_id==null) throw new ActionException("purchaseRequset.idNotSet");
        return "purchaseRequest_purchaseOrderItemList_"+this.getPurchaseRequsetId(request);
    }
    
    protected List getPurchaseOrderItemListFromSession(HttpServletRequest request) {
        return (List) request.getSession().getAttribute(
                this.getPurchaseOrderItemListAttributeName(request));
    }
    
    protected void clearPurchaseOrderItemListFromSession(HttpServletRequest request)
    {
        request.getSession().removeAttribute(this.getPurchaseOrderItemListAttributeName(request));
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
    
    protected String getPurchaseRequsetId(HttpServletRequest request)
    {
        return request.getParameter("id");
    }
}
