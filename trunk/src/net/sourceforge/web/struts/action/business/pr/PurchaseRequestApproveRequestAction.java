/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.web.struts.action.business.pr;

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
import org.springframework.orm.hibernate.HibernateOptimisticLockingFailureException;

import net.sourceforge.model.admin.User;
import net.sourceforge.model.business.pr.PurchaseRequest;
import net.sourceforge.model.business.pr.PurchaseRequestApproveRequest;
import net.sourceforge.model.business.pr.PurchaseRequestApproveRequestListView;
import net.sourceforge.model.business.pr.query.PurchaseRequestApproveRequestQueryCondition;
import net.sourceforge.model.business.pr.query.PurchaseRequestApproveRequestQueryOrder;
import net.sourceforge.model.metadata.ApproveStatus;
import net.sourceforge.model.metadata.ApproverDelegateType;
import net.sourceforge.model.metadata.YesNo;
import net.sourceforge.service.admin.UserManager;
import net.sourceforge.service.business.pr.CapexManager;
import net.sourceforge.service.business.pr.PurchaseRequestManager;
import net.sourceforge.service.business.pr.PurchaseRequestApproveRequestManager;
import net.sourceforge.service.business.pr.YearlyBudgetManager;
import net.sourceforge.service.business.rule.ApproverDelegateManager;
import net.sourceforge.web.struts.action.ActionUtils;
import net.sourceforge.web.struts.action.ServiceLocator;
import net.sourceforge.web.struts.form.business.BaseApproveQueryForm;
import com.shcnc.hibernate.PersistentEnum;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.form.BeanForm;

/**
 * struts action class for domain model PurchaseRequestApproveRequest
 * 
 * @author nicebean
 * @version 1.0 (2005-12-8)
 */
public class PurchaseRequestApproveRequestAction extends BasePurchaseRequestAction {

    private static final String FAKE_DATE_BEGIN = "1900/01/01";

    private static final String FAKE_DATE_TO = "2099/01/01";

    /**
     * 查询PurchaseRequestApproveRequest
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PurchaseRequestApproveRequestManager crarm = ServiceLocator.getPurchaseRequestApproveRequestManager(request);

        BaseApproveQueryForm queryForm = (BaseApproveQueryForm) form;

        User loginUser = getCurrentUser(request);
        Map conditions = constructQueryMap(queryForm, loginUser);

        if (StringUtils.isEmpty(queryForm.getOrder())) {
            queryForm.setOrder(PurchaseRequestApproveRequestQueryOrder.PURCHASEREQUEST_ID.getName());
            queryForm.setDescend(true);
        }
        if (queryForm.isFirstInit()) {
            queryForm.init(crarm.getPurchaseRequestApproveRequestListCount(conditions));
        } else {
            queryForm.init();
        }

        List result = crarm.getPurchaseRequestApproveRequestList(conditions, queryForm.getPageNoAsInt(), queryForm.getPageSizeAsInt(),
                PurchaseRequestApproveRequestQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend());
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
            PurchaseRequestApproveRequestListView view = new PurchaseRequestApproveRequestListView();
            view.setPurchaseRequestApproveRequest((PurchaseRequestApproveRequest) obj[0]);
            view.setPurchaseRequest((PurchaseRequest) obj[1]);
            viewList.add(view);
        }
        return viewList;
    }

    private Map constructQueryMap(BaseApproveQueryForm queryForm, User loginUser) {
        Map conditions = new HashMap();

        long today = this.getTodayTimeMillis();
        conditions.put(PurchaseRequestApproveRequestQueryCondition.APPROVER_ID_EQ,
                new Object[] { loginUser.getId(), loginUser.getId(), new Date(today + 86400000), new Date(today) });

        String code = queryForm.getCode();
        if (code != null && code.trim().length() != 0) {
            conditions.put(PurchaseRequestApproveRequestQueryCondition.CODE_LIKE, code);
        }

        String title = queryForm.getTitle();
        if (title != null && title.trim().length() != 0) {
            conditions.put(PurchaseRequestApproveRequestQueryCondition.TITLE_LIKE, title);
        }

        Integer approveStatus = ActionUtils.parseInt(queryForm.getApproveStatus());
        if (approveStatus != null) {
            conditions.put(PurchaseRequestApproveRequestQueryCondition.STATUS_EQ, approveStatus);
        } else {
            conditions.put(PurchaseRequestApproveRequestQueryCondition.STATUS_NEQ, ApproveStatus.NOT_YOUR_TURN);
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
            conditions.put(PurchaseRequestApproveRequestQueryCondition.SUBMIT_DATE_BT, new Object[] { queryCreateTimeBegin, queryCreateTimeTo, });
        }

        return conditions;
    }

    private void putApproveStatusListToRequest(HttpServletRequest request) {
        List approveStatusList = PersistentEnum.getEnumList(ApproveStatus.class);
        approveStatusList.remove(ApproveStatus.NOT_YOUR_TURN);
        request.setAttribute("X_APPROVESTATUSLIST", approveStatusList);
    }

    /**
     * 查看PurchaseRequestApproveRequest
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        PurchaseRequestApproveRequest purchaseRequestApproveRequest = getPurchaseRequestApproveRequestFromRequest(request);
        PurchaseRequest purchaseRequest = getPurchaseRequestByApproveRequest(purchaseRequestApproveRequest, request);
        
        if(purchaseRequest.getCapex()!=null)
            this.putCanViewCapexAmountToRequest(purchaseRequest.getCapex(),request);
        
        if(purchaseRequest.getYearlyBudget()!=null)
            this.putCanViewYearlyBudgetAmount(purchaseRequest.getYearlyBudget(),request);


        checkApprovePower(purchaseRequestApproveRequest, request);
        request.setAttribute("x_pr", purchaseRequest);
        request.setAttribute("x_capex", purchaseRequest.getCapex());
        if (purchaseRequest.getCapex() != null) {
            CapexManager cm = ServiceLocator.getCapexManager(request);
            request.setAttribute("x_canViewCapexAmount",new Boolean(cm.canViewCapexAmount(purchaseRequest.getCapex(),this.getCurrentUser(request))));
        }
        request.setAttribute("x_yearlyBudget", purchaseRequest.getYearlyBudget());
        if (purchaseRequest.getYearlyBudget() != null) {
            YearlyBudgetManager ym = ServiceLocator.getYearlyBudgetManager(request);
            request.setAttribute("x_canViewYearlyBudgetAmount",new Boolean(ym.canViewYearlyBudgetAmount(purchaseRequest.getYearlyBudget(),this.getCurrentUser(request))));
        }

        request.setAttribute("X_APPROVEREQUEST", purchaseRequestApproveRequest);
        request.setAttribute("x_currentUserId", this.getCurrentUser(request).getId());
        request.setAttribute("X_APPROVEACTION", "PurchaseRequest");

        request.setAttribute("x_postfix", "_self");
        request.setAttribute("x_showBudgetWarning",Boolean.TRUE);

        if (ApproveStatus.WAITING_FOR_APPROVE.equals(purchaseRequestApproveRequest.getStatus())) {
            if (YesNo.YES.equals(purchaseRequestApproveRequest.getCanModify())) {
                BeanForm purchaseRequestForm = (BeanForm) getForm("/updateAndApprovePurchaseRequestApproveRequest", request);
                if (!isBack(request)) {
                    purchaseRequestForm.populateToForm(purchaseRequest);
                }

                request.setAttribute("x_pr_id", purchaseRequest.getId());
                putPurchaseRequestDetailsToRequest(purchaseRequest, request);
                putEnumListToRequest(request);
                
                request.setAttribute("x_edit", Boolean.TRUE);
                return mapping.findForward("editPage");
            }
            
            request.setAttribute("X_SHOWAPPROVEBUTTON", "1");
        }

        putPurchaseRequestDetailsForView(purchaseRequest, request);
        request.setAttribute("x_edit", Boolean.FALSE);
        
        return mapping.findForward("viewPage");
    }

    /**
     * 通过PurchaseRequestApproveRequest
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward approve(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PurchaseRequestApproveRequest purchaseRequestApproveRequest = getPurchaseRequestApproveRequestFromRequest(request);

        checkApprovePower(purchaseRequestApproveRequest, request);
        purchaseRequestApproveRequest.setComment(request.getParameter("comment"));
        purchaseRequestApproveRequest.setActualApprover(getCurrentUser(request));
        PurchaseRequestApproveRequestManager prarm = ServiceLocator.getPurchaseRequestApproveRequestManager(request);
        prarm.approvePurchaseRequestApproveRequest(purchaseRequestApproveRequest,this.getCurrentUser(request));
        return getViewForward(purchaseRequestApproveRequest);
    }

    /**
     * 拒绝PurchaseRequestApproveRequest
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward reject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PurchaseRequestApproveRequest purchaseRequestApproveRequest = getPurchaseRequestApproveRequestFromRequest(request);

        checkApprovePower(purchaseRequestApproveRequest, request);
        purchaseRequestApproveRequest.setComment(request.getParameter("comment"));
        purchaseRequestApproveRequest.setActualApprover(getCurrentUser(request));
        PurchaseRequestApproveRequestManager prarm = ServiceLocator.getPurchaseRequestApproveRequestManager(request);
        prarm.rejectPurchaseRequestApproveRequest(purchaseRequestApproveRequest,this.getCurrentUser(request));
        return getViewForward(purchaseRequestApproveRequest);
    }

    /**
     * 保存PurchaseRequest并通过PurchaseRequestApproveRequest
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward updateAndApprove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PurchaseRequestApproveRequest purchaseRequestApproveRequest = getPurchaseRequestApproveRequestFromRequest(request);
        checkApprovePower(purchaseRequestApproveRequest, request);

        request.setAttribute("x_pr_id", super.getPurchaseRequsetId(request));
        PurchaseRequest purchaseRequest = getPurchaseRequestFromRequest(request);
        request.setAttribute("x_capex", purchaseRequest.getCapex());
        request.setAttribute("x_yearlyBudget", purchaseRequest.getYearlyBudget());
        

        BeanForm purchaseRequestForm = (BeanForm) form;
        purchaseRequestForm.populateToBean(purchaseRequest, request);

        List itemList = getPurchaseRequestItemListFromSession(request);
        clearPurchaseRequestItemListFromSession(request);
        List attachmentList = getPurchaseRequestAttachmentListFromRequest(request);

        purchaseRequestApproveRequest.setComment(request.getParameter("comment"));
        purchaseRequestApproveRequest.setActualApprover(getCurrentUser(request));

        PurchaseRequestApproveRequestManager crarm = ServiceLocator.getPurchaseRequestApproveRequestManager(request);
        try{
            crarm.approvePurchaseRequestApproveRequestAndUpdatePurchaseRequest(purchaseRequestApproveRequest, purchaseRequest, itemList, attachmentList,this.getCurrentUser(request));
        }
        catch(HibernateOptimisticLockingFailureException e)
        {
            throw new ActionException("all.stale");
        }

        return getViewForward(purchaseRequestApproveRequest);
    }

    private PurchaseRequest getPurchaseRequestByApproveRequest(PurchaseRequestApproveRequest purchaseRequestApproveRequest, HttpServletRequest request) {
        PurchaseRequestManager purchaseRequestManager = ServiceLocator.getPurchaseRequestManager(request);
        PurchaseRequest purchaseRequest = purchaseRequestManager.getPurchaseRequestByApproveRequestId(purchaseRequestApproveRequest.getApproveRequestId());
        if (purchaseRequest == null)
            throw new ActionException("purchaseRequestApproveRequest.error.purchaseRequestNotFound");
        return purchaseRequest;
    }

    private PurchaseRequestApproveRequest getPurchaseRequestApproveRequestFromRequest(HttpServletRequest request) throws Exception {
        String approveRequestId = request.getParameter("request_id");
        Integer userId = ActionUtils.parseInt(request.getParameter("approver_id"));
        UserManager um = ServiceLocator.getUserManager(request);
        User user = um.getUser(userId);
        PurchaseRequestApproveRequestManager earm = ServiceLocator.getPurchaseRequestApproveRequestManager(request);
        PurchaseRequestApproveRequest taaRequest = earm.getPurchaseRequestApproveRequest(approveRequestId, user);
        if (taaRequest == null)
            throw new ActionException("purchaseRequestApproveRequest.notFound", new Object[] { approveRequestId, userId });
        return taaRequest;
    }

    private void checkApprovePower(PurchaseRequestApproveRequest purchaseRequestApproveRequest, HttpServletRequest request) {
        User loginUser = getCurrentUser(request);
        if (!purchaseRequestApproveRequest.getApprover().getId().equals(loginUser.getId())) {
            ApproverDelegateManager adm = ServiceLocator.getApproverDelegateManager(request);
            if (!adm.isDelegateApprover(ApproverDelegateType.PURCHASE_REQUEST_APPROVER, purchaseRequestApproveRequest.getApprover().getId(), loginUser.getId())) {
                throw new ActionException("purchaseRequestApproveRequest.error.noApprovePower");
            }
        }
    }

    private ActionForward getViewForward(PurchaseRequestApproveRequest purchaseRequestApproveRequest) {
        String url = "/viewPurchaseRequestApproveRequest.do?request_id=" + purchaseRequestApproveRequest.getApproveRequestId() + "&approver_id="
                + purchaseRequestApproveRequest.getApprover().getId();
        return new ActionForward(url, true);
    }

    protected String getPurchaseRequsetId(HttpServletRequest request) {
        return (String) request.getAttribute("x_pr_id");
    }

}