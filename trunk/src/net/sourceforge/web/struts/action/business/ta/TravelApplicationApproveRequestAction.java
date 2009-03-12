/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.web.struts.action.business.ta;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import net.sourceforge.model.admin.User;
import net.sourceforge.model.business.ta.TravelApplication;
import net.sourceforge.model.business.ta.TravelApplicationApproveRequest;
import net.sourceforge.model.business.ta.TravelApplicationApproveRequestListView;
import net.sourceforge.model.business.ta.query.TravelApplicationApproveRequestQueryCondition;
import net.sourceforge.model.business.ta.query.TravelApplicationApproveRequestQueryOrder;
import net.sourceforge.model.metadata.ApproveStatus;
import net.sourceforge.model.metadata.ApproverDelegateType;
import net.sourceforge.model.metadata.TravelApplicationUrgent;
import net.sourceforge.model.metadata.YesNo;
import net.sourceforge.service.admin.UserManager;
import net.sourceforge.service.business.rule.ApproverDelegateManager;
import net.sourceforge.service.business.ta.TravelApplicationApproveRequestManager;
import net.sourceforge.service.business.ta.TravelApplicationManager;
import net.sourceforge.web.struts.action.ActionUtils;
import net.sourceforge.web.struts.action.ServiceLocator;
import net.sourceforge.web.struts.form.business.ta.TravelApplicationApproveRequestQueryForm;
import com.shcnc.hibernate.PersistentEnum;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.form.BeanForm;

/**
 * struts action class for domain model TravelApplicationApproveRequest
 * 
 * @author ych
 * @version 1.0 (Nov 18, 2005)
 */
public class TravelApplicationApproveRequestAction extends BaseTravelApplicationAction {

    private static final String FAKE_DATE_BEGIN = "1900/01/01";

    private static final String FAKE_DATE_TO = "2099/01/01";

    /**
     * 查询TravelApplicationApproveRequest
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TravelApplicationApproveRequestManager fm = ServiceLocator.getTravelApplicationApproveRequestManager(request);

        TravelApplicationApproveRequestQueryForm queryForm = (TravelApplicationApproveRequestQueryForm) form;

        User loginUser = getCurrentUser(request);
        Map conditions = constructQueryMap(queryForm, loginUser);

        
        if (queryForm.isFirstInit()) {
            queryForm.init(fm.getTravelApplicationApproveRequestListCount(conditions));
        } else {
            queryForm.init();
        }

        List result = fm.getTravelApplicationApproveRequestList(conditions, queryForm.getPageNoAsInt(), queryForm.getPageSizeAsInt(),
                TravelApplicationApproveRequestQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend());
        List view = getApproveListView(result);
        request.setAttribute("X_RESULTLIST", view);
        putApproveStatusListToRequest(request);
        putUrgentListToRequest(request);
        return mapping.findForward("page");
    }

    private List getApproveListView(List queryResult) {
        List viewList = new ArrayList();
        Iterator itor = queryResult.iterator();
        while (itor.hasNext()) {
            Object[] obj = (Object[]) itor.next();
            TravelApplicationApproveRequestListView view = new TravelApplicationApproveRequestListView();
            view.setTravelApplicationApproveRequest((TravelApplicationApproveRequest) obj[0]);
            view.setTravelApplication((TravelApplication) obj[1]);
            viewList.add(view);
        }
        return viewList;
    }

    private Map constructQueryMap(TravelApplicationApproveRequestQueryForm queryForm, User loginUser) {
        Map conditions = new HashMap();

        long today = this.getTodayTimeMillis();
        conditions.put(TravelApplicationApproveRequestQueryCondition.APPROVER_ID_EQ, 
                new Object[] { loginUser.getId(), loginUser.getId(), new Date(today + 86400000), new Date(today) });

        String code = queryForm.getCode();
        if (code != null && code.trim().length() != 0) {
            conditions.put(TravelApplicationApproveRequestQueryCondition.CODE_LIKE, code);
        }

        String title = queryForm.getTitle();
        if (title != null && title.trim().length() != 0) {
            conditions.put(TravelApplicationApproveRequestQueryCondition.TITLE_LIKE, title);
        }

        Integer approveStatus = ActionUtils.parseInt(queryForm.getApproveStatus());
        if (approveStatus != null) {
            conditions.put(TravelApplicationApproveRequestQueryCondition.STATUS_EQ, approveStatus);
        } else {
            conditions.put(TravelApplicationApproveRequestQueryCondition.STATUS_NEQ, ApproveStatus.NOT_YOUR_TURN);
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
            conditions.put(TravelApplicationApproveRequestQueryCondition.SUBMIT_DATE_BT, new Object[] { queryCreateTimeBegin, queryCreateTimeTo, });
        }
        
        Integer urgent = ActionUtils.parseInt(queryForm.getUrgent());
        if (urgent != null) {
            conditions.put(TravelApplicationApproveRequestQueryCondition.URGENT_EQ, urgent);
        }

        String requestor=queryForm.getRequestor();
        if (requestor != null && requestor.trim().length() != 0) {
            conditions.put(TravelApplicationApproveRequestQueryCondition.REQUESTOR_LK, requestor);
        }
        
        return conditions;
    }

    private void putApproveStatusListToRequest(HttpServletRequest request) {
        List approveStatusList = PersistentEnum.getEnumList(ApproveStatus.class);
        approveStatusList.remove(ApproveStatus.NOT_YOUR_TURN);
        request.setAttribute("X_APPROVESTATUSLIST", approveStatusList);
    }

    /**
     * 编辑TravelApplicationApproveRequest
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        TravelApplicationApproveRequest travelApplicationApproveRequest = getTravelApplicationApproveRequestFromRequest(request);
        TravelApplication travelApplication = getTravelApplicationByApproveRequest(travelApplicationApproveRequest, request);

        checkApprovePower(travelApplicationApproveRequest, request);
        request.setAttribute("x_travelApplication", travelApplication);

        putApproveListToRequest(travelApplication, request);

        request.setAttribute("X_APPROVEREQUEST", travelApplicationApproveRequest);
        request.setAttribute("x_currentUserId", this.getCurrentUser(request).getId());
        request.setAttribute("X_APPROVEACTION", "TravelApplication");

        if (travelApplicationApproveRequest.getStatus().equals(ApproveStatus.WAITING_FOR_APPROVE)) {
            if (YesNo.YES.equals(travelApplicationApproveRequest.getCanModify())) {
                request.setAttribute("x_travelApplication", travelApplication);

                BeanForm travelApplicationForm = (BeanForm) form;
                if (!isBack(request) && !isComboChange(request)) {
                    travelApplicationForm.setConverterLocator(converterLocator);
                    travelApplicationForm.populateToForm(travelApplication);

                    this.processHotelPrice(travelApplication, travelApplicationForm);
                }

                TravelApplicationOtherPageManager pm = new TravelApplicationOtherPageManager(travelApplicationForm,travelApplication.getCreator() ,this.getFunction(request), request);
                pm.process();

                putEnumListToRequest(request);
                return mapping.findForward("editPage");
            }
            request.setAttribute("X_SHOWAPPROVEBUTTON", "1");
        }
        
        

        return mapping.findForward("viewPage"); 
    }

    /**
     * 通过TravelApplicationApproveRequest
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward approve(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TravelApplicationApproveRequest travelApplicationApproveRequest = getTravelApplicationApproveRequestFromRequest(request);

        checkApprovePower(travelApplicationApproveRequest, request);
        travelApplicationApproveRequest.setComment(request.getParameter("comment"));
        travelApplicationApproveRequest.setActualApprover(this.getCurrentUser(request));
        TravelApplicationApproveRequestManager tm = ServiceLocator.getTravelApplicationApproveRequestManager(request);
        tm.approveTravelApplicationApproveRequest(travelApplicationApproveRequest,this.getCurrentUser(request));
        return getViewForward(travelApplicationApproveRequest);
    }

    /**
     * 拒绝TravelApplicationApproveRequest
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward reject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TravelApplicationApproveRequest travelApplicationApproveRequest = getTravelApplicationApproveRequestFromRequest(request);

        checkApprovePower(travelApplicationApproveRequest, request);
        travelApplicationApproveRequest.setComment(request.getParameter("comment"));
        TravelApplicationApproveRequestManager tm = ServiceLocator.getTravelApplicationApproveRequestManager(request);
        tm.rejectTravelApplicationApproveRequest(travelApplicationApproveRequest,this.getCurrentUser(request));
        return getViewForward(travelApplicationApproveRequest);
    }

    /**
     * 保存TravelApplication并通过TravelApplicationApproveRequest
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward updateAndApprove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TravelApplicationApproveRequest travelApplicationApproveRequest = getTravelApplicationApproveRequestFromRequest(request);
        checkApprovePower(travelApplicationApproveRequest, request);

        TravelApplication travelApplication = getTravelApplicationFromRequest(request);

        BeanForm travelApplicationForm = (BeanForm) form;
        travelApplicationForm.setConverterLocator(converterLocator);
        travelApplicationForm.setBeanLoader(ServiceLocator.getBeanLoader(request));

        travelApplicationForm.populateToBean(travelApplication, request);

        travelApplicationApproveRequest.setComment(request.getParameter("comment"));
        travelApplicationApproveRequest.setActualApprover(getCurrentUser(request));
        TravelApplicationApproveRequestManager tm = ServiceLocator.getTravelApplicationApproveRequestManager(request);
        tm.approveTravelApplicationApproveRequestAndUpdateTravelApplication(travelApplicationApproveRequest, travelApplication,this.getCurrentUser(request));
        
        return getViewForward(travelApplicationApproveRequest);
    }

    private TravelApplication getTravelApplicationByApproveRequest(TravelApplicationApproveRequest travelApplicationApproveRequest, HttpServletRequest request) {
        TravelApplicationManager travelApplicationManager = ServiceLocator.getTravelApplicationManager(request);
        TravelApplication travelApplication = travelApplicationManager.getTravelApplicationByApproveRequestId(travelApplicationApproveRequest
                .getApproveRequestId());
        if (travelApplication == null)
            throw new ActionException("travelApplicationApproveRequest.error.travelApplicationNotFound");
        return travelApplication;
    }

    private TravelApplicationApproveRequest getTravelApplicationApproveRequestFromRequest(HttpServletRequest request) throws Exception {
        String approveRequestId = request.getParameter("request_id");
        Integer userId = ActionUtils.parseInt(request.getParameter("approver_id"));
        UserManager um = ServiceLocator.getUserManager(request);
        User user = um.getUser(userId);
        TravelApplicationApproveRequestManager tm = ServiceLocator.getTravelApplicationApproveRequestManager(request);
        TravelApplicationApproveRequest taaRequest = tm.getTravelApplicationApproveRequest(approveRequestId, user);
        if (taaRequest == null)
            throw new ActionException("travelApplicationApproveRequest.notFound", new Object[] { approveRequestId, userId });
        return taaRequest;
    }

    private void checkApprovePower(TravelApplicationApproveRequest travelApplicationApproveRequest, HttpServletRequest request) {
        User loginUser = getCurrentUser(request);
        if (!travelApplicationApproveRequest.getApprover().getId().equals(loginUser.getId())) {
            ApproverDelegateManager adm = ServiceLocator.getApproverDelegateManager(request);
            if (!adm.isDelegateApprover(ApproverDelegateType.TRAVEL_APPLICATION_APPROVER, travelApplicationApproveRequest.getApprover().getId(), loginUser
                    .getId())) {
                throw new ActionException("travelApplicationApproveRequest.error.noApprovePower");
            }
        }
    }
    
    private void putUrgentListToRequest(HttpServletRequest request) {
        request.setAttribute("x_urgentList", TravelApplicationUrgent.getEnumList(TravelApplicationUrgent.class));
    }
    
    private ActionForward getViewForward(TravelApplicationApproveRequest travelApplicationApproveRequest) {
        String url = "/viewTravelApplicationApproveRequest.do?request_id=" + travelApplicationApproveRequest.getApproveRequestId() + "&approver_id="
        + travelApplicationApproveRequest.getApprover().getId();
        return new ActionForward(url, true);
    }

}