/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.web.struts.action.business.po;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import net.sourceforge.model.admin.User;
import net.sourceforge.model.business.expense.Expense;
import net.sourceforge.model.business.expense.query.ExpenseQueryOrder;
import net.sourceforge.model.business.po.PurchaseOrder;
import net.sourceforge.model.business.po.PurchaseOrderApproveRequest;
import net.sourceforge.model.business.po.PurchaseOrderApproveRequestListView;
import net.sourceforge.model.business.po.query.PurchaseOrderApproveRequestQueryCondition;
import net.sourceforge.model.business.po.query.PurchaseOrderApproveRequestQueryOrder;
import net.sourceforge.model.metadata.ApproveStatus;
import net.sourceforge.model.metadata.ApproverDelegateType;
import net.sourceforge.model.metadata.YesNo;
import net.sourceforge.service.admin.UserManager;
import net.sourceforge.service.business.po.PurchaseOrderApproveRequestManager;
import net.sourceforge.service.business.po.PurchaseOrderManager;
import net.sourceforge.service.business.rule.ApproverDelegateManager;
import net.sourceforge.utils.SessionTempFile;
import net.sourceforge.web.struts.action.ActionUtils;
import net.sourceforge.web.struts.action.ServiceLocator;
import net.sourceforge.web.struts.form.business.po.PurchaseOrderApproveRequestQueryForm;
import com.shcnc.hibernate.PersistentEnum;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.form.BeanForm;
import com.shcnc.utils.BeanHelper;
import com.shcnc.utils.ExportUtil;
import com.shcnc.utils.Exportable;

/**
 * struts action class for domain model PurchaseOrderApproveRequest
 * 
 * @author nicebean
 * @version 1.0 (2005-12-22)
 */
public class PurchaseOrderApproveRequestAction extends BasePurchaseOrderAction {

    private static final String FAKE_DATE_BEGIN = "1900/01/01";

    private static final String FAKE_DATE_TO = "2099/01/01";

    /**
     * 查询PurchaseOrderApproveRequest
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PurchaseOrderApproveRequestManager crarm = ServiceLocator.getPurchaseOrderApproveRequestManager(request);

        PurchaseOrderApproveRequestQueryForm queryForm = (PurchaseOrderApproveRequestQueryForm) form;

        User loginUser = getCurrentUser(request);
        Map conditions = constructQueryMap(queryForm, loginUser);

        if (StringUtils.isEmpty(queryForm.getOrder())) {
            queryForm.setOrder(PurchaseOrderApproveRequestQueryOrder.PURCHASEORDER_ID.getName());
            queryForm.setDescend(true);
        }

        String exportType = queryForm.getExportType();
        if (StringUtils.isNotEmpty(exportType)) {
            List data = getApproveListView(crarm.getPurchaseOrderApproveRequestList(conditions, 0, -1, PurchaseOrderApproveRequestQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend()));

            int index = SessionTempFile.createNewTempFile(request);
            String fileName = "purchaseOrderApprove";
            String suffix = ExportUtil.export(exportType, data, request, new FileOutputStream(SessionTempFile.getTempFile(index, request)), new Exportable() {

                public void exportHead(List row, HttpServletRequest request) throws Exception {
                    MessageResources messages = getResources(request);
                    row.add(messages.getMessage(getLocale(request), "purchaseOrder.id"));
                    row.add(messages.getMessage(getLocale(request), "purchaseOrder.title"));
                    row.add(messages.getMessage(getLocale(request), "purchaseOrder.supplier"));
                    row.add(messages.getMessage(getLocale(request), "purchaseOrder.purchaser.id"));
                    row.add(messages.getMessage(getLocale(request), "purchaseOrder.amount"));
                    row.add(messages.getMessage(getLocale(request), "purchaseOrderApproveRequest.table.submitDate"));
                    row.add(messages.getMessage(getLocale(request), "purchaseOrderApproveRequest.table.status"));
                }

                public void exportRow(List row, Object data, HttpServletRequest request) throws Exception {
                    row.add(BeanHelper.getBeanPropertyValue(data, "purchaseOrder.id"));
                    row.add(BeanHelper.getBeanPropertyValue(data, "purchaseOrder.title"));
                    row.add(BeanHelper.getBeanPropertyValue(data, "purchaseOrder.supplier.name"));
                    row.add(BeanHelper.getBeanPropertyValue(data, "purchaseOrder.purchaser.name"));
                    row.add(BeanHelper.getBeanPropertyValue(data, "purchaseOrder.amount"));
                    row.add(BeanHelper.getBeanPropertyValue(data, "purchaseOrder.amount"));
                    PurchaseOrderApproveRequestListView view = (PurchaseOrderApproveRequestListView) data;
                    if (view.getPurchaseOrder().getRequestDate() != null) {
                        row.add(ActionUtils.getDisplayDateFromDate(view.getPurchaseOrder().getRequestDate()));
                    } else {
                        row.add("");
                    }
                    if (isEnglish(request)) {
                        row.add(BeanHelper.getBeanPropertyValue(data, "purchaseOrderApproveRequest.status.engShortDescription"));
                    } else {
                        row.add(BeanHelper.getBeanPropertyValue(data, "purchaseOrderApproveRequest.status.chnShortDescription"));
                    }
                }
            });
            return new ActionForward("download/" + index + "/" + URLEncoder.encode(fileName, "UTF-8") + '.' + suffix, true);
        }

        
        if (queryForm.isFirstInit()) {
            queryForm.init(crarm.getPurchaseOrderApproveRequestListCount(conditions));
        } else {
            queryForm.init();
        }

        List result = crarm.getPurchaseOrderApproveRequestList(conditions, queryForm.getPageNoAsInt(), queryForm.getPageSizeAsInt(),
                PurchaseOrderApproveRequestQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend());
        List view = getApproveListView(result);
        request.setAttribute("X_RESULTLIST", view);
        putApproveStatusListToRequest(request);
        return mapping.findForward("page");
    }

    private List getApproveListView(List queryResult) {
        List viewList = new ArrayList();
        Iterator itor = queryResult.iterator();
        while (itor.hasNext()) {
            Object[] obj = (Object[]) itor.next();
            PurchaseOrderApproveRequestListView view = new PurchaseOrderApproveRequestListView();
            view.setPurchaseOrderApproveRequest((PurchaseOrderApproveRequest) obj[0]);
            view.setPurchaseOrder((PurchaseOrder) obj[1]);
            viewList.add(view);
        }
        return viewList;
    }

    private Map constructQueryMap(PurchaseOrderApproveRequestQueryForm queryForm, User loginUser) {
        Map conditions = new HashMap();

        long today = this.getTodayTimeMillis();
        conditions.put(PurchaseOrderApproveRequestQueryCondition.APPROVER_ID_EQ,
                new Object[] { loginUser.getId(), loginUser.getId(), new Date(today + 86400000), new Date(today) });

        String code = queryForm.getCode();
        if (code != null && code.trim().length() != 0) {
            conditions.put(PurchaseOrderApproveRequestQueryCondition.CODE_LIKE, code);
        }

        String title = queryForm.getTitle();
        if (title != null && title.trim().length() != 0) {
            conditions.put(PurchaseOrderApproveRequestQueryCondition.TITLE_LIKE, title);
        }

        Integer approveStatus = ActionUtils.parseInt(queryForm.getApproveStatus());
        if (approveStatus != null) {
            conditions.put(PurchaseOrderApproveRequestQueryCondition.STATUS_EQ, approveStatus);
        } else {
            conditions.put(PurchaseOrderApproveRequestQueryCondition.STATUS_NEQ, ApproveStatus.NOT_YOUR_TURN);
        }

        String submitTimeFrom = queryForm.getSubmitDateFrom();
        String submitTimeTo = queryForm.getSubmitDateTo();
        if ((submitTimeFrom != null && submitTimeFrom.trim().length() != 0) || (submitTimeTo != null && submitTimeTo.trim().length() != 0)) {
            if (submitTimeFrom == null || submitTimeFrom.trim().length() == 0)
                submitTimeFrom = FAKE_DATE_BEGIN;
            if (submitTimeTo == null || submitTimeTo.trim().length() == 0)
                submitTimeTo = FAKE_DATE_TO;
            Date queryCreateTimeBegin = ActionUtils.getQueryBeginDateFromDisplayDate(submitTimeFrom);
            Date queryCreateTimeTo = ActionUtils.getQueryToDateFromDisplayDate(submitTimeTo);
            conditions.put(PurchaseOrderApproveRequestQueryCondition.SUBMIT_DATE_BT, new Object[] { queryCreateTimeBegin, queryCreateTimeTo, });
        }

        if(!StringUtils.isEmpty(queryForm.getSupplier_name()))
        {
            conditions.put(PurchaseOrderApproveRequestQueryCondition.PUCHASE_ORDER_SUPPLIER_NAME_LIKE,queryForm.getSupplier_name());
        }

        BigDecimal amountFrom = ActionUtils.parseBigDecimal(queryForm.getAmountFrom());
        if (amountFrom != null) {
            conditions.put(PurchaseOrderApproveRequestQueryCondition.PUCHASE_ORDER_AMOUNT_GE, amountFrom);
        }

        BigDecimal amountTo = ActionUtils.parseBigDecimal(queryForm.getAmountTo());
        if (amountTo != null) {
            conditions.put(PurchaseOrderApproveRequestQueryCondition.PUCHASE_ORDER_AMOUNT_LE, amountTo);
        }


        return conditions;
    }

    private void putApproveStatusListToRequest(HttpServletRequest request) {
        List approveStatusList = PersistentEnum.getEnumList(ApproveStatus.class);
        approveStatusList.remove(ApproveStatus.NOT_YOUR_TURN);
        request.setAttribute("X_APPROVESTATUSLIST", approveStatusList);
    }

    /**
     * 查看PurchaseOrderApproveRequest
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        PurchaseOrderApproveRequest purchaseOrderApproveRequest = getPurchaseOrderApproveRequestFromRequest(request);
        PurchaseOrder purchaseOrder = getPurchaseOrderByApproveRequest(purchaseOrderApproveRequest, request);
        

        checkApprovePower(purchaseOrderApproveRequest, request);
        request.setAttribute("x_po", purchaseOrder);


        request.setAttribute("X_APPROVEREQUEST", purchaseOrderApproveRequest);
        request.setAttribute("x_currentUserId", this.getCurrentUser(request).getId());
        request.setAttribute("X_APPROVEACTION", "PurchaseOrder");

        request.setAttribute("x_postfix", "_self");

        if (ApproveStatus.WAITING_FOR_APPROVE.equals(purchaseOrderApproveRequest.getStatus())) {
            if (YesNo.YES.equals(purchaseOrderApproveRequest.getCanModify())) {
                BeanForm purchaseOrderForm = (BeanForm) getForm("/updateAndApprovePurchaseOrderApproveRequest", request);
                if (!isBack(request)) {
                    purchaseOrderForm.populateToForm(purchaseOrder);
                }

                request.setAttribute("x_po_id", purchaseOrder.getId());
                putPurchaseOrderDetailsToRequest(purchaseOrder, request);
                putEnumListToRequest(request);
                
                request.setAttribute("x_edit", Boolean.TRUE);
                return mapping.findForward("editPage");
            }
            request.setAttribute("X_SHOWAPPROVEBUTTON", "1");
        }

        putPurchaseOrderDetailsForView(purchaseOrder, request);
        request.setAttribute("x_edit", Boolean.FALSE);
        return mapping.findForward("viewPage");
    }

    /**
     * 通过PurchaseOrderApproveRequest
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward approve(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PurchaseOrderApproveRequest purchaseOrderApproveRequest = getPurchaseOrderApproveRequestFromRequest(request);

        checkApprovePower(purchaseOrderApproveRequest, request);
        purchaseOrderApproveRequest.setComment(request.getParameter("comment"));
        purchaseOrderApproveRequest.setActualApprover(getCurrentUser(request));
        PurchaseOrderApproveRequestManager prarm = ServiceLocator.getPurchaseOrderApproveRequestManager(request);
        prarm.approvePurchaseOrderApproveRequest(purchaseOrderApproveRequest,this.getCurrentUser(request));
        return getViewForward(purchaseOrderApproveRequest);
    }

    /**
     * 拒绝PurchaseOrderApproveRequest
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward reject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PurchaseOrderApproveRequest purchaseOrderApproveRequest = getPurchaseOrderApproveRequestFromRequest(request);

        checkApprovePower(purchaseOrderApproveRequest, request);
        purchaseOrderApproveRequest.setComment(request.getParameter("comment"));
        purchaseOrderApproveRequest.setActualApprover(getCurrentUser(request));
        PurchaseOrderApproveRequestManager prarm = ServiceLocator.getPurchaseOrderApproveRequestManager(request);
        prarm.rejectPurchaseOrderApproveRequest(purchaseOrderApproveRequest,this.getCurrentUser(request));
        return getViewForward(purchaseOrderApproveRequest);
    }

    /**
     * 保存PurchaseOrder并通过PurchaseOrderApproveRequest
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward updateAndApprove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PurchaseOrderApproveRequest purchaseOrderApproveRequest = getPurchaseOrderApproveRequestFromRequest(request);
        checkApprovePower(purchaseOrderApproveRequest, request);

        request.setAttribute("x_po_id", super.getPurchaseOrderId(request));
        PurchaseOrder purchaseOrder = getPurchaseOrderFromRequest(request);
        

        BeanForm purchaseOrderForm = (BeanForm) form;
        purchaseOrderForm.populateToBean(purchaseOrder, request);

        List itemList = getPurchaseOrderItemListFromSession(request);
        List attachmentList = getPurchaseOrderAttachmentListFromRequest(request);
        List paymentScheduleDetailList = getPaymentScheduleDetailListFromSession(request);

        purchaseOrderApproveRequest.setComment(request.getParameter("comment"));
        purchaseOrderApproveRequest.setActualApprover(getCurrentUser(request));

        PurchaseOrderApproveRequestManager crarm = ServiceLocator.getPurchaseOrderApproveRequestManager(request);
        PurchaseOrderManager pom=ServiceLocator.getPurchaseOrderManager(request); 
        List oldItemList=pom.getPurchaseOrderItemList(purchaseOrder);
        crarm.approvePurchaseOrderApproveRequestAndUpdatePurchaseOrder(purchaseOrderApproveRequest, purchaseOrder,oldItemList, itemList, attachmentList, paymentScheduleDetailList,this.getCurrentUser(request));

        clearPurchaseOrderItemListFromSession(request);
        clearPaymentScheduleDetailListFromSession(request);

        return getViewForward(purchaseOrderApproveRequest);
    }

    private PurchaseOrder getPurchaseOrderByApproveRequest(PurchaseOrderApproveRequest purchaseOrderApproveRequest, HttpServletRequest request) {
        PurchaseOrderManager purchaseOrderManager = ServiceLocator.getPurchaseOrderManager(request);
        PurchaseOrder purchaseOrder = purchaseOrderManager.getPurchaseOrderByApproveRequestId(purchaseOrderApproveRequest.getApproveRequestId());
        if (purchaseOrder == null)
            throw new ActionException("purchaseOrderApproveRequest.error.purchaseOrderNotFound");
        return purchaseOrder;
    }

    private PurchaseOrderApproveRequest getPurchaseOrderApproveRequestFromRequest(HttpServletRequest request) throws Exception {
        String approveRequestId = request.getParameter("request_id");
        Integer userId = ActionUtils.parseInt(request.getParameter("approver_id"));
        UserManager um = ServiceLocator.getUserManager(request);
        User user = um.getUser(userId);
        PurchaseOrderApproveRequestManager earm = ServiceLocator.getPurchaseOrderApproveRequestManager(request);
        PurchaseOrderApproveRequest taaRequest = earm.getPurchaseOrderApproveRequest(approveRequestId, user);
        if (taaRequest == null)
            throw new ActionException("purchaseOrderApproveRequest.notFound", new Object[] { approveRequestId, userId });
        return taaRequest;
    }

    private void checkApprovePower(PurchaseOrderApproveRequest purchaseOrderApproveRequest, HttpServletRequest request) {
        User loginUser = getCurrentUser(request);
        if (!purchaseOrderApproveRequest.getApprover().getId().equals(loginUser.getId())) {
            ApproverDelegateManager adm = ServiceLocator.getApproverDelegateManager(request);
            if (!adm.isDelegateApprover(ApproverDelegateType.PURCHASE_ORDER_APPROVER, purchaseOrderApproveRequest.getApprover().getId(), loginUser.getId())) {
                throw new ActionException("purchaseOrderApproveRequest.error.noApprovePower");
            }
        }
    }

    private ActionForward getViewForward(PurchaseOrderApproveRequest purchaseOrderApproveRequest) {
        String url = "/viewPurchaseOrderApproveRequest.do?request_id=" + purchaseOrderApproveRequest.getApproveRequestId() + "&approver_id="
                + purchaseOrderApproveRequest.getApprover().getId();
        return new ActionForward(url, true);
    }

    protected String getPurchaseOrderId(HttpServletRequest request) {
        return (String) request.getAttribute("x_po_id");
    }

}