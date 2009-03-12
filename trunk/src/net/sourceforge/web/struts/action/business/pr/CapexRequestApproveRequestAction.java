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
import net.sourceforge.model.business.pr.CapexRequest;
import net.sourceforge.model.business.pr.CapexRequestApproveRequest;
import net.sourceforge.model.business.pr.CapexRequestApproveRequestListView;
import net.sourceforge.model.business.pr.query.CapexRequestApproveRequestQueryCondition;
import net.sourceforge.model.business.pr.query.CapexRequestApproveRequestQueryOrder;
import net.sourceforge.model.metadata.ApproveStatus;
import net.sourceforge.model.metadata.ApproverDelegateType;
import net.sourceforge.model.metadata.YesNo;
import net.sourceforge.service.admin.UserManager;
import net.sourceforge.service.business.pr.CapexManager;
import net.sourceforge.service.business.pr.CapexRequestApproveRequestManager;
import net.sourceforge.service.business.pr.YearlyBudgetManager;
import net.sourceforge.service.business.rule.ApproverDelegateManager;
import net.sourceforge.web.struts.action.ActionUtils;
import net.sourceforge.web.struts.action.ServiceLocator;
import net.sourceforge.web.struts.form.business.BaseApproveQueryForm;
import com.shcnc.hibernate.PersistentEnum;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.form.BeanForm;

/**
 * struts action class for domain model CapexRequestApproveRequest
 * 
 * @author nicebean
 * @version 1.0 (2005-12-8)
 */
public class CapexRequestApproveRequestAction extends BaseCapexRequestAction {

    private static final String FAKE_DATE_BEGIN = "1900/01/01";

    private static final String FAKE_DATE_TO = "2099/01/01";

    /**
     * 查询CapexRequestApproveRequest
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CapexRequestApproveRequestManager crarm = ServiceLocator.getCapexRequestApproveRequestManager(request);

        BaseApproveQueryForm queryForm = (BaseApproveQueryForm) form;

        User loginUser = getCurrentUser(request);
        Map conditions = constructQueryMap(queryForm, loginUser);

        if (StringUtils.isEmpty(queryForm.getOrder())) {
            queryForm.setOrder(CapexRequestApproveRequestQueryOrder.CAPEXREQUEST_CAPEX_ID.getName());
            queryForm.setDescend(true);
        }
        if (queryForm.isFirstInit()) {
            queryForm.init(crarm.getCapexRequestApproveRequestListCount(conditions));
        } else {
            queryForm.init();
        }

        List result = crarm.getCapexRequestApproveRequestList(conditions, queryForm.getPageNoAsInt(), queryForm.getPageSizeAsInt(),
                CapexRequestApproveRequestQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend());
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
            CapexRequestApproveRequestListView view = new CapexRequestApproveRequestListView();
            view.setCapexRequestApproveRequest((CapexRequestApproveRequest) obj[0]);
            view.setCapexRequest((CapexRequest) obj[1]);
            viewList.add(view);
        }
        return viewList;
    }

    private Map constructQueryMap(BaseApproveQueryForm queryForm, User loginUser) {
        Map conditions = new HashMap();

        long today = this.getTodayTimeMillis();
        conditions.put(CapexRequestApproveRequestQueryCondition.APPROVER_ID_EQ,
                new Object[] { loginUser.getId(), loginUser.getId(), new Date(today + 86400000), new Date(today) });

        String code = queryForm.getCode();
        if (code != null && code.trim().length() != 0) {
            conditions.put(CapexRequestApproveRequestQueryCondition.CODE_LIKE, code);
        }

        String title = queryForm.getTitle();
        if (title != null && title.trim().length() != 0) {
            conditions.put(CapexRequestApproveRequestQueryCondition.TITLE_LIKE, title);
        }

        Integer approveStatus = ActionUtils.parseInt(queryForm.getApproveStatus());
        if (approveStatus != null) {
            conditions.put(CapexRequestApproveRequestQueryCondition.STATUS_EQ, approveStatus);
        } else {
            conditions.put(CapexRequestApproveRequestQueryCondition.STATUS_NEQ, ApproveStatus.NOT_YOUR_TURN);
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
            conditions.put(CapexRequestApproveRequestQueryCondition.SUBMIT_DATE_BT, new Object[] { queryCreateTimeBegin, queryCreateTimeTo, });
        }

        return conditions;
    }

    private void putApproveStatusListToRequest(HttpServletRequest request) {
        List approveStatusList = PersistentEnum.getEnumList(ApproveStatus.class);
        approveStatusList.remove(ApproveStatus.NOT_YOUR_TURN);
        request.setAttribute("X_APPROVESTATUSLIST", approveStatusList);
    }

    /**
     * 查看CapexRequestApproveRequest
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        CapexRequestApproveRequest capexRequestApproveRequest = getCapexRequestApproveRequestFromRequest(request);
        CapexRequest capexRequest = getCapexRequestByApproveRequest(capexRequestApproveRequest, request);

        checkApprovePower(capexRequestApproveRequest, request);
        request.setAttribute("x_capexRequest", capexRequest);
        if (capexRequest.getCapex() != null && capexRequest.getCapex().getYearlyBudget() != null) {
            YearlyBudgetManager ym = ServiceLocator.getYearlyBudgetManager(request);
            request.setAttribute("x_canViewYearlyBudgetAmount",new Boolean(ym.canViewYearlyBudgetAmount(capexRequest.getCapex().getYearlyBudget(), this.getCurrentUser(request))));
        }

        request.setAttribute("X_APPROVEREQUEST", capexRequestApproveRequest);
        request.setAttribute("x_currentUserId", this.getCurrentUser(request).getId());
        request.setAttribute("X_APPROVEACTION", "CapexRequest");

        putCapexRequestInfoToRequest(capexRequest, request);

        if (ApproveStatus.WAITING_FOR_APPROVE.equals(capexRequestApproveRequest.getStatus())) {
            if (YesNo.YES.equals(capexRequestApproveRequest.getCanModify())) {
                BeanForm capexRequestForm = (BeanForm) getForm("/updateAndApproveCapexRequestApproveRequest", request);
                if (!isBack(request)) {
                    capexRequestForm.populateToForm(capexRequest);
                }

                request.setAttribute("x_edit", Boolean.TRUE);
                return mapping.findForward("editPage");
            }
            request.setAttribute("X_SHOWAPPROVEBUTTON", "1");
        }

        request.setAttribute("x_edit", Boolean.FALSE);
        return mapping.findForward("viewPage");
    }

    /**
     * 通过CapexRequestApproveRequest
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward approve(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CapexRequestApproveRequest capexRequestApproveRequest = getCapexRequestApproveRequestFromRequest(request);

        checkApprovePower(capexRequestApproveRequest, request);
        capexRequestApproveRequest.setComment(request.getParameter("comment"));
        capexRequestApproveRequest.setActualApprover(getCurrentUser(request));
        CapexRequestApproveRequestManager earm = ServiceLocator.getCapexRequestApproveRequestManager(request);
        earm.approveCapexRequestApproveRequest(capexRequestApproveRequest);
        return getViewForward(capexRequestApproveRequest);
    }

    /**
     * 拒绝CapexRequestApproveRequest
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward reject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CapexRequestApproveRequest capexRequestApproveRequest = getCapexRequestApproveRequestFromRequest(request);

        checkApprovePower(capexRequestApproveRequest, request);
        capexRequestApproveRequest.setComment(request.getParameter("comment"));
        capexRequestApproveRequest.setActualApprover(getCurrentUser(request));
        CapexRequestApproveRequestManager earm = ServiceLocator.getCapexRequestApproveRequestManager(request);
        earm.rejectCapexRequestApproveRequest(capexRequestApproveRequest);
        return getViewForward(capexRequestApproveRequest);
    }

    /**
     * 保存CapexRequest并通过CapexRequestApproveRequest
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward updateAndApprove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CapexRequestApproveRequest capexRequestApproveRequest = getCapexRequestApproveRequestFromRequest(request);
        checkApprovePower(capexRequestApproveRequest, request);

        CapexRequest capexRequest = getCapexRequestFromRequest(request);
        /*
         * 重新计算capexRequest的amount
         * Add by Jackey 2006-3-26
         */
        capexRequest.setAmount(capexRequest.getTotalAmount());        
        List capexRequestItemList = getCapexRequestItemListFromRequest(capexRequest, request, false);
        
        BeanForm capexRequestForm = (BeanForm) form;
        capexRequestForm.populateToBean(capexRequest, new String[] { "title", "description" });

        capexRequestApproveRequest.setComment(request.getParameter("comment"));
        capexRequestApproveRequest.setActualApprover(getCurrentUser(request));

        CapexRequestApproveRequestManager crarm = ServiceLocator.getCapexRequestApproveRequestManager(request);
        try {
            crarm.approveCapexRequestApproveRequestAndUpdateCapexRequest(capexRequestApproveRequest, capexRequest, capexRequestItemList);
        } catch(HibernateOptimisticLockingFailureException e) {
            throw new ActionException("capexRequest.yearlyBudget.alreadyModified");
        }

        return getViewForward(capexRequestApproveRequest);
    }

    private CapexRequest getCapexRequestByApproveRequest(CapexRequestApproveRequest capexRequestApproveRequest, HttpServletRequest request) {
        CapexManager capexManager = ServiceLocator.getCapexManager(request);
        CapexRequest capexRequest = capexManager.getCapexRequestByApproveRequestId(capexRequestApproveRequest.getApproveRequestId());
        if (capexRequest == null)
            throw new ActionException("capexRequestApproveRequest.error.capexRequestNotFound");
        return capexRequest;
    }

    private CapexRequestApproveRequest getCapexRequestApproveRequestFromRequest(HttpServletRequest request) throws Exception {
        String approveRequestId = request.getParameter("request_id");
        Integer userId = ActionUtils.parseInt(request.getParameter("approver_id"));
        UserManager um = ServiceLocator.getUserManager(request);
        User user = um.getUser(userId);
        CapexRequestApproveRequestManager earm = ServiceLocator.getCapexRequestApproveRequestManager(request);
        CapexRequestApproveRequest taaRequest = earm.getCapexRequestApproveRequest(approveRequestId, user);
        if (taaRequest == null)
            throw new ActionException("capexRequestApproveRequest.notFound", new Object[] { approveRequestId, userId });
        return taaRequest;
    }

    private void checkApprovePower(CapexRequestApproveRequest capexRequestApproveRequest, HttpServletRequest request) {
        User loginUser = getCurrentUser(request);
        if (!capexRequestApproveRequest.getApprover().getId().equals(loginUser.getId())) {
            ApproverDelegateManager adm = ServiceLocator.getApproverDelegateManager(request);
            if (!adm.isDelegateApprover(ApproverDelegateType.NONBUDGET_CAPEX_APPROVER, capexRequestApproveRequest.getApprover().getId(), loginUser.getId())) {
                throw new ActionException("capexRequestApproveRequest.error.noApprovePower");
            }
        }
    }

    private ActionForward getViewForward(CapexRequestApproveRequest capexRequestApproveRequest) {
        String url = "/viewCapexRequestApproveRequest.do?request_id=" + capexRequestApproveRequest.getApproveRequestId() + "&approver_id="
                + capexRequestApproveRequest.getApprover().getId();
        return new ActionForward(url, true);
    }

}