/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.web.struts.action.business.rule;

import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
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

import net.sourceforge.model.admin.Department;
import net.sourceforge.model.admin.Function;
import net.sourceforge.model.admin.Site;
import net.sourceforge.model.admin.User;
import net.sourceforge.model.admin.query.UserQueryCondition;
import net.sourceforge.model.admin.query.UserQueryOrder;
import net.sourceforge.model.business.rule.ApproverDelegate;
import net.sourceforge.model.business.rule.query.ApproverDelegateQueryCondition;
import net.sourceforge.model.business.rule.query.ApproverDelegateQueryOrder;
import net.sourceforge.model.business.rule.query.ApproverQueryCondition;
import net.sourceforge.model.metadata.ApproverDelegateType;
import net.sourceforge.model.metadata.EnabledDisabled;
import net.sourceforge.model.metadata.RuleType;
import net.sourceforge.service.admin.EmailManager;
import net.sourceforge.service.admin.FunctionManager;
import net.sourceforge.service.admin.UserManager;
import net.sourceforge.service.business.rule.ApproverDelegateManager;
import net.sourceforge.utils.SessionTempFile;
import net.sourceforge.web.struts.action.ActionUtils;
import net.sourceforge.web.struts.action.BaseAction;
import net.sourceforge.web.struts.action.ServiceLocator;
import net.sourceforge.web.struts.form.business.rule.ApproverDelegateQueryForm;
import com.shcnc.struts.action.ActionException;

import com.shcnc.struts.form.BeanForm;
import com.shcnc.struts.form.BeanQueryForm;
import com.shcnc.utils.BeanHelper;
import com.shcnc.utils.ExportUtil;
import com.shcnc.utils.Exportable;

/**
 * struts action class for domain model ApproverDelegate
 * 
 * @author shilei
 * @version 1.0 (Nov 15, 2005)
 */
public class ApproverDelegateAction extends BaseAction {

    /**
     * list original approver
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward listApprover(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        BeanQueryForm queryForm = (BeanQueryForm) form;
        ApproverQueryCondition cond = (ApproverQueryCondition) queryForm.newBean();

        List siteList = this.getAndCheckGrantedSiteDeparmentList(request);

        if (cond.getRuleType() == null)
        {
            cond.setRuleType(RuleType.CAPEX_APPROVAL_RULES);
            if(!this.isGlobal(request))
            {
                Site firstSite=(Site) siteList.get(0);
                cond.setSiteId(firstSite.getId());
                Department firstDepartment=(Department) firstSite.getDepartments().get(0);
                cond.setDepartmentId(firstDepartment.getId());
            }
        }
        queryForm.populateToForm(cond);

        Map conds = this.constructApproverQueryMap(cond, request);

        // order
        if (StringUtils.isEmpty(queryForm.getOrder())) {
            queryForm.setOrder(UserQueryOrder.NAME.getName());
            queryForm.setDescend(false);
            
        } else {
            if (UserQueryOrder.getEnum(queryForm.getOrder()) == null)
                throw new RuntimeException("order not found");
        }

        UserManager um = ServiceLocator.getUserManager(request);

        if (queryForm.isFirstInit()) {
            Integer pageSize = ActionUtils.parseInt(queryForm.getPageSize());
            if (pageSize != null)
                queryForm.init(um.getUserListCount(conds), pageSize.intValue());
            else
                queryForm.init(um.getUserListCount(conds));
        } else {
            queryForm.init();
        }

        List result = um.getUserList(conds, queryForm.getPageNoAsInt(), queryForm.getPageSizeAsInt(), UserQueryOrder.getEnum(queryForm.getOrder()), queryForm
                .isDescend());

        request.setAttribute("X_RESULTLIST", result);
        request.setAttribute("x_siteList", siteList);

        this.putRuleTypeListToRequest(request);

        return mapping.findForward("page");

    }

    private Map constructApproverQueryMap(ApproverQueryCondition cond, HttpServletRequest request) {
        Map conds = new HashMap();
        conds.put(UserQueryCondition.ENABLED_EQ, EnabledDisabled.ENABLED.getEnumCode());
        if (cond.getSiteId() != null)
            conds.put(UserQueryCondition.SITE_ID_EQ, cond.getSiteId());
        if (cond.getDepartmentId() != null)
            conds.put(UserQueryCondition.DEPARTMENT_ID_EQ, cond.getDepartmentId());
        
        if(cond.getName()!=null)
            conds.put(UserQueryCondition.NAME_LIKE, cond.getName());

        FunctionManager fm = ServiceLocator.getFunctionManager(request);
        Function function = fm.getFunction(cond.getRuleType().getPrefixUrl());
        conds.put(UserQueryCondition.FUNCTION_ID_EQ, function);
        return conds;
    }

    /**
     * put RuleType List To Request
     * 
     * @param request
     */
    private void putRuleTypeListToRequest(HttpServletRequest request) {
        List l = new ArrayList();
        l.add(RuleType.CAPEX_APPROVAL_RULES);
        l.add(RuleType.PR_APPROVAL_RULES);
        l.add(RuleType.PO_APPROVAL_RULES);
        l.add(RuleType.EXPENSE_APPROVAL_RULES);
        l.add(RuleType.TRAVEL_APPROVAL_RULES);
        request.setAttribute("x_ruleTypeList", l);
    }

    private boolean isSelf(HttpServletRequest request) {
        return this.isGlobal(request);
    }

    private RuleType getRuleTypeFromRequest(HttpServletRequest request) {
        Integer ruleTypeId = ActionUtils.parseInt(request.getParameter("ruleType"));
        if (ruleTypeId == null)
            throw new ActionException("approverDelegate.ruleType.notSet");

        RuleType ruleType = (RuleType) RuleType.fromEnumCode(RuleType.class, ruleTypeId);
        if (ruleType == null)
            throw new ActionException("approverDelegate.ruleType.error");
        return ruleType;
    }

    /**
     * search Approver Delegate
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        this.processSelfPostfix(request);

        ApproverDelegateManager fm = ServiceLocator.getApproverDelegateManager(request);

        ApproverDelegateQueryForm queryForm = (ApproverDelegateQueryForm) form;
        User originalApprover = null;

        if (this.isSelf(request))// self
        {
            queryForm.setOriginalApprover_id(this.getCurrentUser(request).getId().toString());
            originalApprover = this.getCurrentUser(request);
            List typeList=ApproverDelegateType.getEnumList(ApproverDelegateType.class);
            request.setAttribute("x_typeList",typeList );
            if(StringUtils.isEmpty(queryForm.getType()))
            {
                ApproverDelegateType firstType=(ApproverDelegateType) typeList.get(0);
                queryForm.setType(firstType.getEnumCode().toString());
            }
        } else {// other

            // init type with ruleType
            ApproverDelegateType type = null;
            if (queryForm.getType() == null) {
                RuleType ruleType = this.getRuleTypeFromRequest(request);
                type = ApproverDelegateType.getApproverDelegateTypeByRuleType(ruleType);
                if (type == null)
                    throw new ActionException("approverDelegate.ruleType.notApprove");
                queryForm.setType(type.getEnumCode().toString());
            } else {
                Integer typeId = ActionUtils.parseInt(queryForm.getType());
                type = (ApproverDelegateType) ApproverDelegateType.fromEnumCode(ApproverDelegateType.class, typeId);
                if (type == null)
                    throw new RuntimeException("type error");
            }

            // get originalApprover from request
            originalApprover = this.getAndCheckOriginalApproverFromRequest(request);
            request.setAttribute("x_type", type);
        }

        request.setAttribute("x_originalApprover", originalApprover);
        
        if (StringUtils.isEmpty(queryForm.getOrder())) {
            queryForm.setOrder(ApproverDelegateQueryOrder.DELEGATEAPPROVER_NAME.getName());
            queryForm.setDescend(false);
            queryForm.setFromDate2(ActionUtils.getTodayAsDisplayDate());
            queryForm.setToDate1(ActionUtils.getTodayAsDisplayDate());
        } else if (ApproverDelegateQueryOrder.getEnum(queryForm.getOrder()) == null) {
            throw new RuntimeException("order error");
        }
        
        Map conditions = constructQueryMap(queryForm);

        String exportType = queryForm.getExportType();
        if (StringUtils.isNotEmpty(exportType)) {
            List data = fm.getApproverDelegateList(conditions, 0, -1, ApproverDelegateQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend());

            int index = SessionTempFile.createNewTempFile(request);
            String fileName = "approverDelegate";
            String suffix = ExportUtil.export(exportType, data, request, new FileOutputStream(SessionTempFile.getTempFile(index, request)), new Exportable() {

                public void exportHead(List row, HttpServletRequest request) throws Exception {
                    MessageResources messages = getResources(request);
                    row.add(messages.getMessage(getLocale(request), "approverDelegate.delegateApprover.id"));
                    row.add(messages.getMessage(getLocale(request), "approverDelegate.fromDate"));
                    row.add(messages.getMessage(getLocale(request), "approverDelegate.toDate"));
                }

                public void exportRow(List row, Object data, HttpServletRequest request) throws Exception {
                    row.add(BeanHelper.getBeanPropertyValue(data, "delegateApprover.name"));
                    ApproverDelegate ad = (ApproverDelegate) data;
                    row.add(this.getDateString(ad.getFromDate()));
                    row.add(this.getDateString(ad.getToDate()));
                }

                private String getDateString(Date date) {
                    if (date == null)
                        return "";
                    else
                        return ActionUtils.getDisplayDateFromDate(date);

                }
            });
            return new ActionForward("download/" + index + "/" + URLEncoder.encode(fileName, "UTF-8") + '.' + suffix, true);
        }

        if (queryForm.isFirstInit()) {
            queryForm.init(fm.getApproverDelegateListCount(conditions));
        } else {
            queryForm.init();
        }

        List result = fm.getApproverDelegateList(conditions, queryForm.getPageNoAsInt(), queryForm.getPageSizeAsInt(), ApproverDelegateQueryOrder
                .getEnum(queryForm.getOrder()), queryForm.isDescend());

        request.setAttribute("X_RESULTLIST", result);
        return mapping.findForward("page");
    }

    /**
     * construct Query Condition
     * 
     * @param queryForm
     * @return Query Condition
     */
    private Map constructQueryMap(ApproverDelegateQueryForm queryForm) {
        Map conditions = new HashMap();
        /* id */
        Integer id = ActionUtils.parseInt(queryForm.getId());
        if (id != null) {
            conditions.put(ApproverDelegateQueryCondition.ID_EQ, id);
        }

        /* keyPropertyList */

        /* kmtoIdList */

        /* mtoIdList */
        Integer originalApprover_id = ActionUtils.parseInt(queryForm.getOriginalApprover_id());
        if (originalApprover_id != null) {
            conditions.put(ApproverDelegateQueryCondition.ORIGINALAPPROVER_ID_EQ, originalApprover_id);
        }
        Integer delegateApprover_id = ActionUtils.parseInt(queryForm.getDelegateApprover_id());
        if (delegateApprover_id != null) {
            conditions.put(ApproverDelegateQueryCondition.DELEGATEAPPROVER_ID_EQ, delegateApprover_id);
        }

        /* property */
        String type = queryForm.getType();
        if (type != null && type.trim().length() != 0) {
            conditions.put(ApproverDelegateQueryCondition.TYPE_EQ, type);
        }
        String fromDate = queryForm.getFromDate();
        if (fromDate != null && fromDate.trim().length() != 0) {
            conditions.put(ApproverDelegateQueryCondition.FROMDATE_EQ, fromDate);
        }
        String toDate = queryForm.getToDate();
        if (toDate != null && toDate.trim().length() != 0) {
            conditions.put(ApproverDelegateQueryCondition.TODATE_EQ, toDate);
        }
        
        if(StringUtils.isNotEmpty(queryForm.getFromDate1()))
        {
            Date d = ActionUtils.getDateFromDisplayDate(queryForm.getFromDate1());
            conditions.put(ApproverDelegateQueryCondition.FROMDATE_GE, d);
        }
        
        if(StringUtils.isNotEmpty(queryForm.getFromDate2()))
        {
            Date d = ActionUtils.getDateFromDisplayDate(queryForm.getFromDate2());
            conditions.put(ApproverDelegateQueryCondition.FROMDATE_LT, getNextDate(d));
        }
        
        if(StringUtils.isNotEmpty(queryForm.getToDate1()))
        {
            Date d = ActionUtils.getDateFromDisplayDate(queryForm.getToDate1());
            conditions.put(ApproverDelegateQueryCondition.TODATE_GE, d);
        }
        
        if(StringUtils.isNotEmpty(queryForm.getToDate2()))
        {
            Date d = ActionUtils.getDateFromDisplayDate(queryForm.getToDate2());
            conditions.put(ApproverDelegateQueryCondition.TODATE_LT, getNextDate(d));
        }
        
        return conditions;
    }
    
    private Date getNextDate(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.DATE, 1);
        return c.getTime();
    }

    /**
     * get Approver Delegate From Request
     * 
     * @param request
     * @return
     * @throws Exception
     */
    private ApproverDelegate getApproverDelegateFromRequest(HttpServletRequest request) throws Exception {
        Integer id = ActionUtils.parseInt(request.getParameter("id"));
        ApproverDelegateManager approverDelegateManager = ServiceLocator.getApproverDelegateManager(request);
        ApproverDelegate approverDelegate = approverDelegateManager.getApproverDelegate(id);
        if (approverDelegate == null)
            throw new ActionException("approverDelegate.notFound", id);
        return approverDelegate;
    }

    /**
     * edit approver delegate
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        this.processSelfPostfix(request);
        ApproverDelegate approverDelegate = getApproverDelegateFromRequest(request);
        request.setAttribute("x_ad", approverDelegate);
        String today = ActionUtils.getTodayAsDisplayDate();
        boolean fromDateBeforeToday = (ActionUtils.getDisplayDateFromDate(approverDelegate.getFromDate()).compareTo(today) <= 0);

        boolean toDateBeforeToday = (ActionUtils.getDisplayDateFromDate(approverDelegate.getToDate()).compareTo(today) <= 0);

        if (fromDateBeforeToday)
            request.setAttribute("x_fromBefore", Boolean.TRUE);
        if (toDateBeforeToday)
            request.setAttribute("x_toBefore", Boolean.TRUE);

        if (!isBack(request)) {

            if (this.isGlobal(request)) {

                if (!approverDelegate.getOriginalApprover().equals(this.getCurrentUser(request)))
                    throw new ActionException("approverDelegate.originalApprover.notSelf");
            } else {
                this.checkUser(approverDelegate.getOriginalApprover(), request);
                this.checkUser(approverDelegate.getDelegateApprover(), request);
            }
            BeanForm approverDelegateForm = (BeanForm) getForm("/updateApproverDelegate", request);
            approverDelegateForm.populate(approverDelegate, BeanForm.TO_FORM);
        }

        request.setAttribute("x_today", today);
        return mapping.findForward("page");
    }

    /**
     * get Original Approver From Request
     * 
     * @param request
     * @return Original Approver
     * @throws Exception
     */
    private User getAndCheckOriginalApproverFromRequest(HttpServletRequest request) throws Exception {
        User user = this.getOriginalApproverFromRequest(request);
        if(!this.isGlobal(request))
            this.checkUser(user, request);
        return user;
    }

    private void processSelfPostfix(HttpServletRequest request) {
        if (this.isGlobal(request))
            request.setAttribute("x_postfix", "_self");
    }

    /**
     * new Approver Delegate
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward newObject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        this.processSelfPostfix(request);

        if (!isBack(request)) {
            User originalApprover = null;
            if (this.isGlobal(request)) {
                originalApprover = this.getCurrentUser(request);

            } else {
                originalApprover = this.getAndCheckOriginalApproverFromRequest(request);
                this.checkUser(originalApprover, request);
            }

            ApproverDelegateType type = this.getApproverDelegateTypeFromRequest(request);

            ApproverDelegate approverDelegate = new ApproverDelegate();
            approverDelegate.setType(type);
            approverDelegate.setOriginalApprover(originalApprover);

            BeanForm approverDelegateForm = (BeanForm) getForm("/insertApproverDelegate", request);
            approverDelegateForm.populate(approverDelegate, BeanForm.TO_FORM);
        }
        request.setAttribute("x_today", ActionUtils.getTodayAsDisplayDate());
        return mapping.findForward("page");
    }

    /**
     * get Approver Delegate Type From Request
     * 
     * @param request
     * @return Approver Delegate
     */
    private ApproverDelegateType getApproverDelegateTypeFromRequest(HttpServletRequest request) {
        Integer id = ActionUtils.parseInt(request.getParameter("type"));
        if (id == null)
            throw new ActionException("approverDelegate.type.notSet");
        ApproverDelegateType type = (ApproverDelegateType) ApproverDelegateType.fromEnumCode(ApproverDelegateType.class, id);
        if (type == null)
            throw new ActionException("approverDelegate.type.notFound", id);
        return type;
    }

    /**
     * update Approver Delegate
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        User oUser = this.getOriginalApproverFromRequest(request);
        User dUser = this.getDelegateApproverFromRequest(request);
        if (this.isGlobal(request)) {
            if (!oUser.equals(this.getCurrentUser(request)))
                throw new ActionException("approverDelegate.originalApprover.notSelf");
        } else {
            this.checkUser(oUser, request);
            this.checkUser(dUser, request);
        }

        if (oUser.equals(dUser)) {
            throw new ActionException("approverDelegate.select.notSelf");
        }

        ApproverDelegate approverDelegate = getApproverDelegateFromRequest(request);

        String today = ActionUtils.getTodayAsDisplayDate();
        boolean oldFromDateBeforeToday = (ActionUtils.getDisplayDateFromDate(approverDelegate.getFromDate()).compareTo(today) <= 0);

        boolean oldToDateBeforeToday = (ActionUtils.getDisplayDateFromDate(approverDelegate.getToDate()).compareTo(today) <= 0);

        if (oldFromDateBeforeToday && oldToDateBeforeToday) {
            throw new RuntimeException("can't edit old");
        }

        Date oldFromDate = approverDelegate.getFromDate();
        Date oldToDate = approverDelegate.getToDate();
        User oldDelegateApprover = approverDelegate.getDelegateApprover();

        BeanForm approverDelegateForm = (BeanForm) form;

        approverDelegateForm.populate(approverDelegate, BeanForm.TO_BEAN);

        approverDelegate.setOriginalApprover(oUser);
        approverDelegate.setDelegateApprover(dUser);

        String sFromDate = ActionUtils.getDisplayDateFromDate(approverDelegate.getFromDate());
        String sToDate = ActionUtils.getDisplayDateFromDate(approverDelegate.getToDate());

        if (sFromDate.compareTo(sToDate) > 0)
            throw new ActionException("approverDelegate.fromDateAfterToDate");

        if (!oldFromDateBeforeToday)
            if (sFromDate.compareTo(today) <= 0)
                throw new ActionException("approverDelegate.fromDate.notAfterToday");

        if (!oldToDateBeforeToday)
            if (sToDate.compareTo(today) <= 0)
                throw new ActionException("approverDelegate.toDate.notAfterToday");

        if (oldFromDateBeforeToday) {
            approverDelegate.setFromDate(oldFromDate);
            approverDelegate.setDelegateApprover(oldDelegateApprover);

        }
        if (oldToDateBeforeToday)
            approverDelegate.setToDate(oldToDate);

        ApproverDelegateManager approverDelegateManager = ServiceLocator.getApproverDelegateManager(request);

        Map conds = new HashMap();
        conds.put(ApproverDelegateQueryCondition.DELEGATEAPPROVER_ID_EQ, dUser.getId());
        conds.put(ApproverDelegateQueryCondition.ORIGINALAPPROVER_ID_EQ, oUser.getId());
        conds.put(ApproverDelegateQueryCondition.TYPE_EQ, approverDelegate.getType());

        List dList = approverDelegateManager.getApproverDelegateList(conds, 0, -1, ApproverDelegateQueryOrder.FROMDATE, false);
        for (Iterator iter = dList.iterator(); iter.hasNext();) {
            ApproverDelegate ad = (ApproverDelegate) iter.next();
            if (ad.equals(approverDelegate))
                continue;
            String oldSFromDate = ActionUtils.getDisplayDateFromDate(ad.getFromDate());
            String oldSToDate = ActionUtils.getDisplayDateFromDate(ad.getToDate());
            if (sToDate.compareTo(oldSFromDate) < 0)
                continue;
            if (sFromDate.compareTo(oldSToDate) > 0)
                continue;
            throw new ActionException("approverDelegate.date.overlap");

        }

        approverDelegateManager.updateApproverDelegate(approverDelegate);
        request.setAttribute("X_OBJECT", approverDelegateManager.getApproverDelegate(approverDelegate.getId()));
        request.setAttribute("X_ROWPAGE", "approverDelegate/row.jsp");
        
        
        EmailManager em=ServiceLocator.getEmailManager(request);
        Map context=new HashMap();
        context.put("x_ad",approverDelegate);
        context.put("role", EmailManager.EMAIL_ROLE_DELEGATE_APPROVER);
        em.insertEmail(approverDelegate.getDelegateApprover().getPrimarySite(),approverDelegate.getDelegateApprover().getEmail(),
                "Delegate.vm",context);

        return mapping.findForward("success");
    }

    private User getOriginalApproverFromRequest(HttpServletRequest request) throws Exception {
        Integer id = ActionUtils.parseInt(request.getParameter("originalApprover_id"));
        if (id == null)
            throw new ActionException("approverDelegate.delegateApprover.notSet");
        UserManager um = ServiceLocator.getUserManager(request);
        User user = um.getUser(id);
        if (user == null)
            throw new ActionException("approverDelegate.delegateApprover.notFound");
        return user;
    }

    /**
     * insert Approver Delegate
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward insert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        User originalUser = null;
        User delegateApprover = null;
        if (this.isGlobal(request))// self
        {
            originalUser = this.getCurrentUser(request);
            delegateApprover = this.getDelegateApproverFromRequest(request);
        } else {
            originalUser = this.getAndCheckOriginalApproverFromRequest(request);
            delegateApprover = this.getAndCheckDelegateApproverFromRequest(request);
        }

        if (originalUser.equals(delegateApprover)) {
            throw new ActionException("approverDelegate.select.notSelf");
        }

        BeanForm approverDelegateForm = (BeanForm) form;
        ApproverDelegate approverDelegate = new ApproverDelegate();

        approverDelegateForm.populate(approverDelegate, BeanForm.TO_BEAN);
        approverDelegate.setOriginalApprover(originalUser);
        approverDelegate.setDelegateApprover(delegateApprover);

        String today = ActionUtils.getTodayAsDisplayDate();
        String sFromDate = ActionUtils.getDisplayDateFromDate(approverDelegate.getFromDate());
        String sToDate = ActionUtils.getDisplayDateFromDate(approverDelegate.getToDate());
        if (sFromDate.compareTo(sToDate) > 0)
            throw new ActionException("approverDelegate.fromDateAfterToDate");

        if (sFromDate.compareTo(today) <= 0)
            throw new ActionException("approverDelegate.fromDate.notAfterToday");

        if (sToDate.compareTo(today) <= 0)
            throw new ActionException("approverDelegate.toDate.notAfterToday");

        ApproverDelegateManager approverDelegateManager = ServiceLocator.getApproverDelegateManager(request);
        Map conds = new HashMap();
        conds.put(ApproverDelegateQueryCondition.DELEGATEAPPROVER_ID_EQ, delegateApprover.getId());
        conds.put(ApproverDelegateQueryCondition.ORIGINALAPPROVER_ID_EQ, originalUser.getId());
        conds.put(ApproverDelegateQueryCondition.TYPE_EQ, approverDelegate.getType());

        List dList = approverDelegateManager.getApproverDelegateList(conds, 0, -1, ApproverDelegateQueryOrder.FROMDATE, false);
        for (Iterator iter = dList.iterator(); iter.hasNext();) {
            ApproverDelegate ad = (ApproverDelegate) iter.next();
            String oldSFromDate = ActionUtils.getDisplayDateFromDate(ad.getFromDate());
            String oldSToDate = ActionUtils.getDisplayDateFromDate(ad.getToDate());
            if (sToDate.compareTo(oldSFromDate) < 0)
                continue;
            if (sFromDate.compareTo(oldSToDate) > 0)
                continue;
            throw new ActionException("approverDelegate.date.overlap");

        }

        request.setAttribute("X_OBJECT", approverDelegateManager.insertApproverDelegate(approverDelegate));
        request.setAttribute("X_ROWPAGE", "approverDelegate/row.jsp");
        
        EmailManager em=ServiceLocator.getEmailManager(request);
        Map context=new HashMap();
        context.put("x_ad",approverDelegate);
        context.put("role", EmailManager.EMAIL_ROLE_DELEGATE_APPROVER);
        em.insertEmail(approverDelegate.getDelegateApprover().getPrimarySite(),approverDelegate.getDelegateApprover().getEmail(),
                "Delegate.vm",context);

        return mapping.findForward("success");
    }

    private User getDelegateApproverFromRequest(HttpServletRequest request) throws Exception {
        Integer id = ActionUtils.parseInt(request.getParameter("delegateApprover_id"));
        if (id == null)
            throw new ActionException("approverDelegate.delegateApprover.notSet");
        UserManager um = ServiceLocator.getUserManager(request);
        User user = um.getUser(id);
        if (user == null)
            throw new ActionException("approverDelegate.delegateApprover.notFound");
        return user;
    }

    private User getAndCheckDelegateApproverFromRequest(HttpServletRequest request) throws Exception {
        User user = this.getDelegateApproverFromRequest(request);
        if(!isGlobal(request))
            this.checkUser(user, request);
        return user;
    }

    /**
     * select delegate for a user
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward select(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        this.processSelfPostfix(request);
        User originalApprover = getAndCheckOriginalApproverFromRequest(request);
        request.setAttribute("x_originalApprover", originalApprover);

        BeanQueryForm queryForm = (BeanQueryForm) form;
        ApproverQueryCondition cond = (ApproverQueryCondition) queryForm.newBean();
        if (cond.getRuleType() == null) {
            ApproverDelegateType type = this.getApproverDelegateTypeFromRequest(request);
            RuleType ruleType = ApproverDelegateType.getRuleTypeByApproverDelegateType(type);
            cond.setRuleType(ruleType);
        }

        ApproverDelegateType adt = ApproverDelegateType.getApproverDelegateTypeByRuleType(cond.getRuleType());
        if (adt == null)
            throw new ActionException("approverDelegate.rultType.notApprove");
        queryForm.populateToForm(cond);
        queryForm.setPageSize("5");

        return this.listApprover(mapping, form, request, response);
    }

}