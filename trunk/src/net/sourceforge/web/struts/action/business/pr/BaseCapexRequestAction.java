package net.sourceforge.web.struts.action.business.pr;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sourceforge.model.business.BaseApproveRequest;
import net.sourceforge.model.business.pr.CapexRequest;
import net.sourceforge.model.business.pr.CapexRequestItem;
import net.sourceforge.model.metadata.ApproveStatus;
import net.sourceforge.service.business.pr.CapexManager;
import net.sourceforge.web.struts.action.BaseAction;
import net.sourceforge.web.struts.action.ServiceLocator;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.action.ActionUtils;
import com.shcnc.struts.form.BeanForm;

public class BaseCapexRequestAction extends BaseAction {
    
    protected CapexRequest getCapexRequestFromRequest(HttpServletRequest request) throws Exception {
        Integer id = ActionUtils.parseInt(request.getParameter("id"));
        CapexManager cm = ServiceLocator.getCapexManager(request);
        CapexRequest capexRequest = cm.getCapexRequest(id);
        if (capexRequest == null)
            throw new ActionException("capexRequest.notFound", id);
        return capexRequest;
    }

    protected void putCapexRequestInfoToRequest(CapexRequest cr, HttpServletRequest request) {
        CapexManager cm = ServiceLocator.getCapexManager(request);
        request.setAttribute("x_capexDepartmentList", cm.getCapexDepartmentListForCapex(cr.getCapex()));
        request.setAttribute("x_capexRequestHistoryList", cm.getOldApprovedCapexRequestListForCapexRequest(cr));
        if (!isBack(request)) {
            request.setAttribute("x_capexRequestItemList", cm.getCapexRequestItemListForCapexRequest(cr));
        } else {
            request.setAttribute("x_capexRequestItemList", getCapexRequestItemListFromRequest(cr, request, true));
        }
        request.setAttribute("x_capexRequestAttachmentList", cm.getCapexRequestAttachmentListForCapexRequest(cr));
        List approveList = cm.getCapexRequestApproveRequestListForCapexRequest(cr);
        request.setAttribute("X_APPROVELIST", approveList);
        if (approveList.size() > 0) {
            BaseApproveRequest approveRequest = (BaseApproveRequest)approveList.get(0);
            request.setAttribute("x_canWithDraw", Boolean.valueOf(approveRequest.getStatus().equals(ApproveStatus.WAITING_FOR_APPROVE)));
        }

    }

    protected List getCapexRequestItemListFromRequest(CapexRequest cr, HttpServletRequest request, boolean backToForm) {
        String[] purchaseSubCategory_id = request.getParameterValues("purchaseSubCategory_id");
        String[] supplier_id = request.getParameterValues("supplier_id");
        String[] supplierName = request.getParameterValues("supplierName");
        String[] supplierItem_id = request.getParameterValues("supplierItem_id");
        String[] supplierItemSepc = request.getParameterValues("supplierItemSepc");
        String[] price = request.getParameterValues("price");
        String[] exchangeRate_id = request.getParameterValues("exchangeRate_id");
        String[] quantity = request.getParameterValues("quantity");
        String[] exchangeRateValue = request.getParameterValues("exchangeRateValue");
        
        BeanForm capexRequestItemForm = (BeanForm) getForm("/updateCapexRequestItem", request);
        if (backToForm) {
            capexRequestItemForm.setBeanLoader(ServiceLocator.getBeanLoader(request));
        }
        BigDecimal totalBaseCurrencyAmount = new BigDecimal(0d);
        List result = new ArrayList();
        if (purchaseSubCategory_id != null) {
            for (int i = 0; i < purchaseSubCategory_id.length; i++) {
                capexRequestItemForm.set("purchaseSubCategory_id", purchaseSubCategory_id[i]);
                capexRequestItemForm.set("supplier_id", supplier_id[i]);
                capexRequestItemForm.set("supplierName", supplierName[i]);
                capexRequestItemForm.set("supplierItem_id", supplierItem_id[i]);
                capexRequestItemForm.set("supplierItemSepc", supplierItemSepc[i]);
                capexRequestItemForm.set("price", price[i]);
                capexRequestItemForm.set("exchangeRate_id", exchangeRate_id[i]);
                capexRequestItemForm.set("quantity", quantity[i]);
                capexRequestItemForm.set("exchangeRateValue", exchangeRateValue[i]);
                CapexRequestItem cri;
                if (backToForm) {
                    cri = new CapexRequestItem(new Integer(i));
                } else {
                    cri = new CapexRequestItem();
                }
                capexRequestItemForm.populateToBean(cri);
                cri.setCapexRequest(cr);
                if (cri.getSupplier() == null) {
                    cri.setSupplierItem(null);
                } else {
                    cri.setSupplierName(null);
                }
                totalBaseCurrencyAmount = totalBaseCurrencyAmount.add(cri.getBaseCurrencyAmount());
                result.add(cri);
            }
        }
        //cr.setAmount(totalBaseCurrencyAmount); //comment by jackey
        return result;
    }


}
