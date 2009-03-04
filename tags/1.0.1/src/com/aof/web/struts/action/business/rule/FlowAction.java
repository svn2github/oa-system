/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.web.struts.action.business.rule;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.QName;
import org.dom4j.io.SAXReader;

import com.aof.model.admin.Department;
import com.aof.model.admin.ExpenseCategory;
import com.aof.model.admin.PurchaseCategory;
import com.aof.model.admin.PurchaseSubCategory;
import com.aof.model.admin.Site;
import com.aof.model.admin.User;
import com.aof.model.business.rule.Flow;
import com.aof.model.business.rule.FlowRule;
import com.aof.model.business.rule.Rule;
import com.aof.model.business.rule.RuleCondition;
import com.aof.model.business.rule.RuleConsequence;
import com.aof.model.business.rule.query.FlowQueryCondition;
import com.aof.model.business.rule.query.FlowQueryOrder;
import com.aof.model.metadata.BudgetType;
import com.aof.model.metadata.ConditionCompareType;
import com.aof.model.metadata.ConditionType;
import com.aof.model.metadata.EnabledDisabled;
import com.aof.model.metadata.RuleType;
import com.aof.model.metadata.TravelType;
import com.aof.model.metadata.TravellingMode;
import com.aof.model.metadata.YesNo;
import com.aof.service.admin.SiteManager;
import com.aof.service.business.rule.FlowManager;
import com.aof.service.business.rule.RuleManager;
import com.aof.utils.SessionTempFile;
import com.aof.web.struts.action.BaseAction;
import com.aof.web.struts.action.ServiceLocator;
import com.aof.web.struts.form.business.rule.FlowQueryForm;
import com.shcnc.hibernate.PersistentEnum;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.action.ActionUtils;
import com.shcnc.struts.action.BackToInputActionException;
import com.shcnc.struts.form.BeanForm;
import com.shcnc.utils.BeanUtils;
import com.shcnc.utils.ExportUtil;
import com.shcnc.utils.Exportable;

/**
 * 操作Flow的Action
 * @author nicebean
 * @version 1.0 (2005-11-14)
 */
public class FlowAction extends BaseAction {
    
    public static final String WEB_DRAG_AND_DRAW = "WebDragAndDraw"; 
    
    private ActionForward list(ActionMapping mapping, FlowQueryForm queryForm, HttpServletRequest request, HttpServletResponse response, RuleType type) throws Exception {
        FlowManager fm = ServiceLocator.getFlowManager(request);

        request.setAttribute("X_ENABLEDDISABLEDLIST", PersistentEnum.getEnumList(EnabledDisabled.class));
        
        queryForm.getSite().setList(getAndCheckGrantedSiteList(request));

        Map conditions = constructQueryMap(queryForm);
        
        conditions.put(FlowQueryCondition.TYPE_EQ, type.getEnumCode());
        
        String exportType = queryForm.getExportType();
        if (exportType != null && exportType.length() > 0) {
            List data = fm.getFlowList(conditions, 0, -1, FlowQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend());
            int index = SessionTempFile.createNewTempFile(request);
            String fileName = type.getPrefixUrl() + "Flows";
            String suffix = ExportUtil.export(exportType, data, request, new FileOutputStream(SessionTempFile.getTempFile(index, request)), new Exportable() {

                        public void exportHead(List row, HttpServletRequest request) throws Exception {
                            MessageResources messages = getResources(request);
                            row.add(messages.getMessage(getLocale(request), "flow.id"));
                            row.add(messages.getMessage(getLocale(request), "flow.description"));
                            row.add(messages.getMessage(getLocale(request), "flow.enabled"));
                        }

                        public void exportRow(List row, Object data, HttpServletRequest request) throws Exception {
                            row.add(BeanUtils.getProperty(data, "id"));
                            row.add(BeanUtils.getProperty(data, "description"));
                            String locale = getCurrentUser(request).getLocale();
                            if ("en".equals(locale)) {
                                row.add(BeanUtils.getProperty(data, "enabled.engShortDescription"));
                            } else {
                                row.add(BeanUtils.getProperty(data, "enabled.chnShortDescription"));
                            }
                        }
                    });
            return new ActionForward("download/" + index + "/" + URLEncoder.encode(fileName, "UTF-8") + '.' + suffix, true);
        }

        if (queryForm.isFirstInit()) {
            queryForm.init(fm.getFlowListCount(conditions));
        } else {
            queryForm.init();
        }

        request.setAttribute("X_RESULTLIST", fm.getFlowList(conditions, queryForm.getPageNoAsInt(), queryForm.getPageSizeAsInt(), FlowQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend()));
        request.setAttribute("X_RULETYPE", type);
        return mapping.findForward("page");
    }

    private Map constructQueryMap(FlowQueryForm queryForm) {
        Map conditions = new HashMap();

        conditions.put(FlowQueryCondition.SITE_ID_EQ, ((Site)queryForm.getSite().getSelectedItem()).getId());
        
        Integer id = ActionUtils.parseInt(queryForm.getId());
        if (id != null) conditions.put(FlowQueryCondition.ID_EQ, id);

        String description = queryForm.getDescription();
        if (description != null) {
            description = description.trim();
            if (description.length() == 0) description = null;
        }
        if (description != null) conditions.put(FlowQueryCondition.DESCRIPTION_LIKE, description);
        
        Integer enabled = ActionUtils.parseInt(queryForm.getEnabled());
        if (enabled != null) {
            conditions.put(FlowQueryCondition.ENABLED_EQ, enabled);
        }
        

        return conditions;
    }

    private ActionForward newObject(ActionMapping mapping, HttpServletRequest request, RuleType type, boolean isGetRuleList) throws Exception {
        Flow f = prepareAndCheckFlow(type, request);
        if (!isBack(request)) {
            BeanForm flowForm = (BeanForm) getForm("/insert" + type.getPrefixUrl() + "Flow", request);
            flowForm.populateToForm(f);
            request.setAttribute("X_FLOWRULES", new ArrayList());
            request.setAttribute("X_FLOWRULESIZE", new Integer(0));
        }
        prepareData(f, request, isGetRuleList);
        return mapping.findForward("page");
    }

    private ActionForward insert(ActionMapping mapping, BeanForm flowForm, HttpServletRequest request, RuleType type) throws Exception {
        Flow f = prepareAndCheckFlow(type, request);
        flowForm.populateToBean(f);
        FlowManager fm = ServiceLocator.getFlowManager(request);
        Set flowRules = getRulesFromRequest(f, request);
        request.setAttribute("X_FLOWRULES", flowRules);
        request.setAttribute("X_FLOWRULESIZE", new Integer(flowRules.size()));
        
        switch (fm.validateRules(flowRules)) {
        case -1:
            throw new BackToInputActionException("flow.detect.loopFound");
        case -2:
            throw new BackToInputActionException("flow.detect.badSeq");
        case -3:
            throw new BackToInputActionException("flow.detect.noRule");
        }
        
        Flow[] flows = fm.saveFlow(f, flowRules);

        request.setAttribute("X_OBJECT", flows[0]);
        if (flows.length == 2) request.setAttribute("X_OBJECT2", flows[1]);
        return mapping.findForward("success");
    }
    
    private ActionForward insertWebDragAndDraw(ActionMapping mapping, BeanForm flowForm, HttpServletRequest request, RuleType type) throws Exception {
        Flow f = prepareAndCheckFlow(type, request);
        flowForm.populateToBean(f);
        
        Site site = prepareWebDragAndDraw(request);        
        String flowXml = request.getParameter("flowXml");
        Element flowXmlRoot = getFlowXmlRoot(flowXml, "utf-8");
        Set flowRules = getFlowRules(flowXmlRoot, site, type, f);
        List ruleList = getRulesFromFlowRules(flowRules);
        RuleManager rm = ServiceLocator.getRuleManager(request);
        rm.saveRules(ruleList);
                
        FlowManager fm = ServiceLocator.getFlowManager(request);

        request.setAttribute("X_FLOWRULES", flowRules);
        request.setAttribute("X_FLOWRULESIZE", new Integer(flowRules.size()));
        
        switch (fm.validateRules(flowRules)) {
        case -1:
            throw new BackToInputActionException("flow.detect.loopFound");
        case -2:
            throw new BackToInputActionException("flow.detect.badSeq");
        case -3:
            throw new BackToInputActionException("flow.detect.noRule");
        }
        
        Flow[] flows = fm.saveFlow(f, flowRules);

        request.setAttribute("X_OBJECT", flows[0]);
        if (flows.length == 2) request.setAttribute("X_OBJECT2", flows[1]);
        return mapping.findForward("success");
    }
    
    private ActionForward edit(ActionMapping mapping, HttpServletRequest request, RuleType type, boolean isGetRuleList) throws Exception {
        Flow f = getAndCheckFlow(type, request);
        if (!isBack(request)) {
            BeanForm flowForm = (BeanForm) getForm("/update" + type.getPrefixUrl() + "Flow", request);
            flowForm.populateToForm(f);
            Set flowRules = f.getRules();
            request.setAttribute("X_FLOWRULES", flowRules);
            request.setAttribute("X_FLOWRULESIZE", new Integer(flowRules.size()));
        }

        prepareData(f, request, isGetRuleList);
        return mapping.findForward("page");
    }

    private ActionForward update(ActionMapping mapping, BeanForm flowForm, HttpServletRequest request, RuleType type) throws Exception {
        Flow f = getAndCheckFlow(type, request);
        // 更新rule时不允许修改所属site
        flowForm.populateToBean(f, (String[])null, new String[] { "site.id" });

        Set flowRules = getRulesFromRequest(f, request);
        request.setAttribute("X_FLOWRULES", flowRules);
        request.setAttribute("X_FLOWRULESIZE", new Integer(flowRules.size()));
        
        FlowManager fm = ServiceLocator.getFlowManager(request);
        switch (fm.validateRules(flowRules)) {
        case -1:
            throw new BackToInputActionException("flow.detect.loopFound");
        case -2:
            throw new BackToInputActionException("flow.detect.badSeq");
        case -3:
            throw new BackToInputActionException("flow.detect.noRule");
        }
        
        Flow[] flows = fm.saveFlow(f, flowRules);
        request.setAttribute("X_OBJECT", flows[0]);
        if (flows.length == 2) request.setAttribute("X_OBJECT2", flows[1]);
        return mapping.findForward("success");
    }
    
    private ActionForward updateWebDragAndDraw(ActionMapping mapping, BeanForm flowForm, HttpServletRequest request, RuleType type) throws Exception {
        Flow f = getAndCheckFlow(type, request);
        Set oldRules = f.getRules();
        RuleManager rm = ServiceLocator.getRuleManager(request);
        String flowXml = request.getParameter("flowXml");
        Element flowXmlRoot = getFlowXmlRoot(flowXml, "utf-8");
        Set flowRules = getFlowRules(flowXmlRoot, f.getSite(), type, f);
        List ruleList = getRulesFromFlowRules(flowRules);
        
        rm.saveRules(ruleList);
        
        // 更新rule时不允许修改所属site
        flowForm.populateToBean(f, (String[])null, new String[] { "site.id" });

        request.setAttribute("X_FLOWRULES", flowRules);
        request.setAttribute("X_FLOWRULESIZE", new Integer(flowRules.size()));
        
        FlowManager fm = ServiceLocator.getFlowManager(request);
        switch (fm.validateRules(flowRules)) {
        case -1:
            throw new BackToInputActionException("flow.detect.loopFound");
        case -2:
            throw new BackToInputActionException("flow.detect.badSeq");
        case -3:
            throw new BackToInputActionException("flow.detect.noRule");
        }
        
        Flow[] flows = fm.saveFlow(f, flowRules);
        request.setAttribute("X_OBJECT", flows[0]);
        if (flows.length == 2) request.setAttribute("X_OBJECT2", flows[1]);
        
        if (oldRules != null && oldRules.size() > 0) {
            Iterator iter = oldRules.iterator();
            while (iter.hasNext()) {
                rm.removeRule(((FlowRule)iter.next()).getRule().getId());
            }
        }
        
        return mapping.findForward("success");
    }
    
    private ActionForward delete(ActionMapping mapping, HttpServletRequest request, RuleType type) throws Exception {
        Flow f = getAndCheckFlow(type, request);
        FlowManager fm = ServiceLocator.getFlowManager(request);
        fm.removeFlow(f.getId());
        
        return mapping.findForward("success");
    }
    
    private Flow prepareAndCheckFlow(RuleType type, HttpServletRequest request) throws Exception {
        Site s = getAndCheckSite("site_id", request);
        Flow f = new Flow();
        f.setSite(s);
        f.setType(type);
        return f;
    }

    private Flow getAndCheckFlow(RuleType type, HttpServletRequest request) throws Exception {
        FlowManager fm = ServiceLocator.getFlowManager(request);
        Integer id = ActionUtils.parseInt(request.getParameter("id"));
        Flow f = fm.getFlow(id, true);
        
        if (f != null && f.getRules() != null && f.getRules().size() > 0) {
            for (Iterator ritor = f.getRules().iterator(); ritor.hasNext(); ) {
                FlowRule fr = (FlowRule)ritor.next();
                Rule r = fr.getRule();
                if (r != null && r.getConditions() != null && r.getConditions().size() > 0) {
                for (Iterator citor = r.getConditions().iterator(); citor.hasNext(); ) {
                    RuleCondition rc = (RuleCondition) citor.next();
                    rc.setDisplayValue(getDisplayValue(rc.getType(), rc.getValue(), request));
                }
                }
            }
        }
                       
        if (f == null || !type.equals(f.getType())) throw new ActionException("flow." + type.getPrefixUrl() + ".notFound", id);
        checkSite(f.getSite(), request);
        return f;
    }
    
    private void prepareData(Flow f, HttpServletRequest request, boolean isGetRuleList) throws Exception {
        FlowManager fm = ServiceLocator.getFlowManager(request);
        request.setAttribute("X_ENABLEDDISABLEDLIST", PersistentEnum.getEnumList(EnabledDisabled.class));
        request.setAttribute("X_RULETYPE", f.getType());
        Flow ef = fm.getSiteEnabledFlow(f.getSite().getId(), f.getType());
        if (ef != null) {
            if (f.equals(ef)) {
                request.setAttribute("X_MEENABLED", Boolean.TRUE);
            } else {
                request.setAttribute("X_OTHERENABLEDFLOW", ef);
            }
        }
        request.setAttribute("X_ENABLED", EnabledDisabled.ENABLED);
        if (isGetRuleList) {
            RuleManager rm = ServiceLocator.getRuleManager(request);
            List ruleList = rm.getSiteEnabledRuleList(f.getSite().getId(), f.getType());
            if (ruleList.isEmpty()) throw new ActionException("flow.error.noRuleCanUse");
            request.setAttribute("X_RULELIST", ruleList);
        }
    }

    private Set getRulesFromRequest(Flow f, HttpServletRequest request) {
        String[] seq = request.getParameterValues("fr_seq");
        String[] ruleId = request.getParameterValues("fr_rule");
        String[] nextSeqPass = request.getParameterValues("fr_seq_pass");
        String[] nextSeqFail = request.getParameterValues("fr_seq_fail");
        
        TreeSet result = new TreeSet(new Comparator() {

            public int compare(Object o1, Object o2) {
                return ((FlowRule)o1).getSeq() - ((FlowRule)o2).getSeq(); 
            }
            
        });
        
        if (seq != null) {
            for (int i = 0; i < seq.length; i++) {
                FlowRule fr = new FlowRule();
                fr.setFlow(f);
                fr.setSeq(ActionUtils.parseInt(seq[i]).intValue());
                fr.setRule(new Rule(ActionUtils.parseInt(ruleId[i])));
                fr.setNextSeqWhenPass(ActionUtils.parseInt(nextSeqPass[i]).intValue());
                fr.setNextSeqWhenFail(ActionUtils.parseInt(nextSeqFail[i]).intValue());
                result.add(fr);
            }
        }
        return result;
    }
    
    private String getDisplayValue(ConditionType ct, String value, HttpServletRequest request) throws Exception {
        if (value == null) return null;
        if (ConditionType.DEPARTMENT.equals(ct)) {
            Integer id = ActionUtils.parseInt(value);
            if (id == null) return null;
            Department d = ServiceLocator.getDepartmentManager(request).getDepartment(id);
            if (d == null) return null;
            return d.getName();
        }
        if (ConditionType.EXPENSE_CATEGORY.equals(ct)) {
            Integer id = ActionUtils.parseInt(value);
            if (id == null) return null;
            ExpenseCategory ec = ServiceLocator.getExpenseCategoryManager(request).getExpenseCategory(id);
            if (ec == null) return null;
            return ec.getDescription();
        }
        if (ConditionType.PURCHASE_CATEGORY.equals(ct)) {
            Integer id = ActionUtils.parseInt(value.substring(1));
            if (id == null) return null;
            if (value.charAt(0) == 'p') {
                PurchaseCategory pc = ServiceLocator.getPurchaseCategoryManager(request).getPurchaseCategory(id);
                if (pc == null) return null;
                return pc.getDescription();
            } else {
                PurchaseSubCategory psc = ServiceLocator.getPurchaseSubCategoryManager(request).getPurchaseSubCategory(id);
                if (psc == null) return null;
                return psc.getPurchaseCategory().getDescription() + " \\ " + psc.getDescription();
            }
        }
        if (ConditionType.TRAVEL_FROM.equals(ct) || ConditionType.TRAVEL_TO.equals(ct)) {
            Integer id = ActionUtils.parseInt(value);
            if (id == null) return null;
            TravelType tt = (TravelType)PersistentEnum.fromEnumCode(TravelType.class, id);
            if (tt == null) return null;
            return this.getLocaleShortDescription(tt, request);
        }
        if (ConditionType.TRAVEL_MODE.equals(ct)) {
            Integer id = ActionUtils.parseInt(value);
            if (id == null) return null;
            TravellingMode tm = (TravellingMode)PersistentEnum.fromEnumCode(TravellingMode.class, id);
            if (tm == null) return null;
            return this.getLocaleShortDescription(tm, request);
        }
        if (ConditionType.BUDGET_TYPE.equals(ct)) {
            Integer id = ActionUtils.parseInt(value);
            if (id == null) return null;
            BudgetType bt = (BudgetType)PersistentEnum.fromEnumCode(BudgetType.class, id);
            if (bt == null) return null;
            return this.getLocaleShortDescription(bt, request);
        }
        if (ConditionType.WITH_BUDGET.equals(ct)) {
            Integer id = ActionUtils.parseInt(value);
            if (id == null) return null;
            YesNo yn = (YesNo)PersistentEnum.fromEnumCode(YesNo.class, id);
            if (yn == null) return null;
            return this.getLocaleShortDescription(yn, request);
        }
        return value;
    }
    
    /**
     * 查询Capex Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward listCapexApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute(FlowAction.WEB_DRAG_AND_DRAW, FlowAction.WEB_DRAG_AND_DRAW);
        return list(mapping, (FlowQueryForm) form, request, response, RuleType.CAPEX_APPROVAL_RULES);
    }
    
    /**
     * 查询Capex Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward listCapexApprovalWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute(FlowAction.WEB_DRAG_AND_DRAW, FlowAction.WEB_DRAG_AND_DRAW);
        return list(mapping, (FlowQueryForm) form, request, response, RuleType.CAPEX_APPROVAL_RULES);
    }
    
    /**
     * 新增Capex Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newCapexApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return newObject(mapping, request, RuleType.CAPEX_APPROVAL_RULES, true);
    }
    
    /**
     * 新增Capex Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newCapexApprovalWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return newObject(mapping, request, RuleType.CAPEX_APPROVAL_RULES, false);
    }

    /**
     * 插入新增的Capex Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insertCapexApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return insert(mapping, (BeanForm)form, request, RuleType.CAPEX_APPROVAL_RULES);
    }
    
    /**
     * 插入新增的Capex Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insertCapexApprovalWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return insertWebDragAndDraw(mapping, (BeanForm)form, request, RuleType.CAPEX_APPROVAL_RULES);
    }

    /**
     * 修改Capex Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editCapexApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return edit(mapping, request, RuleType.CAPEX_APPROVAL_RULES, true);
    }
    
    /**
     * 修改Capex Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editCapexApprovalWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return edit(mapping, request, RuleType.CAPEX_APPROVAL_RULES, true);
    }

    /**
     * 保存修改的Capex Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward updateCapexApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return update(mapping, (BeanForm)form, request, RuleType.CAPEX_APPROVAL_RULES);
    }
    
    /**
     * 保存修改的Capex Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward updateCapexApprovalWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return updateWebDragAndDraw(mapping, (BeanForm)form, request, RuleType.CAPEX_APPROVAL_RULES);
    }

    /**
     * 删除Capex Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward deleteCapexApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return delete(mapping, request, RuleType.CAPEX_APPROVAL_RULES);
    }

    /**
     * 查询PR Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward listPRApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute(FlowAction.WEB_DRAG_AND_DRAW, FlowAction.WEB_DRAG_AND_DRAW);
        return list(mapping, (FlowQueryForm) form, request, response, RuleType.PR_APPROVAL_RULES);
    }
    
    /**
     * 查询PR Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward listPRApprovalWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute(FlowAction.WEB_DRAG_AND_DRAW, FlowAction.WEB_DRAG_AND_DRAW);
        return list(mapping, (FlowQueryForm) form, request, response, RuleType.PR_APPROVAL_RULES);
    }
    
    /**
     * 新增PR Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newPRApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return newObject(mapping, request, RuleType.PR_APPROVAL_RULES, true);
    }
    
    /**
     * 新增PR Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newPRApprovalWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return newObject(mapping, request, RuleType.PR_APPROVAL_RULES, false);
    }

    /**
     * 插入新增的PR Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insertPRApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return insert(mapping, (BeanForm)form, request, RuleType.PR_APPROVAL_RULES);
    }
    
    /**
     * 插入新增的PR Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insertPRApprovalWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return insertWebDragAndDraw(mapping, (BeanForm)form, request, RuleType.PR_APPROVAL_RULES);
    }

    /**
     * 修改PR Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editPRApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return edit(mapping, request, RuleType.PR_APPROVAL_RULES, true);
    }
    
    /**
     * 修改PR Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editPRApprovalWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return edit(mapping, request, RuleType.PR_APPROVAL_RULES, true);
    }

    /**
     * 保存修改的PR Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward updatePRApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return update(mapping, (BeanForm)form, request, RuleType.PR_APPROVAL_RULES);
    }
    
    /**
     * 保存修改的PR Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward updatePRApprovalWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return updateWebDragAndDraw(mapping, (BeanForm)form, request, RuleType.PR_APPROVAL_RULES);
    }

    /**
     * 删除PR Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward deletePRApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return delete(mapping, request, RuleType.PR_APPROVAL_RULES);
    }

    /**
     * 查询Purchasing Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward listPurchasing(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute(FlowAction.WEB_DRAG_AND_DRAW, FlowAction.WEB_DRAG_AND_DRAW);
        return list(mapping, (FlowQueryForm) form, request, response, RuleType.PURCHASING_RULES);
    }
    
    /**
     * 查询Purchasing Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward listPurchasingWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute(FlowAction.WEB_DRAG_AND_DRAW, FlowAction.WEB_DRAG_AND_DRAW);
        return list(mapping, (FlowQueryForm) form, request, response, RuleType.PURCHASING_RULES);
    }
    
    /**
     * 新增Purchasing Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newPurchasing(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return newObject(mapping, request, RuleType.PURCHASING_RULES, true);
    }
    
    /**
     * 新增Purchasing Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newPurchasingWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return newObject(mapping, request, RuleType.PURCHASING_RULES, false);
    }

    /**
     * 插入新增的Purchasing Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insertPurchasing(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return insert(mapping, (BeanForm)form, request, RuleType.PURCHASING_RULES);
    }
    
    /**
     * 插入新增的Purchasing Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insertPurchasingWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return insertWebDragAndDraw(mapping, (BeanForm)form, request, RuleType.PURCHASING_RULES);
    }

    /**
     * 修改Purchasing Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editPurchasing(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return edit(mapping, request, RuleType.PURCHASING_RULES, true);
    }
    
    /**
     * 修改Purchasing Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editPurchasingWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return edit(mapping, request, RuleType.PURCHASING_RULES, true);
    }

    /**
     * 保存修改的Purchasing Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward updatePurchasing(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return update(mapping, (BeanForm)form, request, RuleType.PURCHASING_RULES);
    }
    
    /**
     * 保存修改的Purchasing Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward updatePurchasingWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return updateWebDragAndDraw(mapping, (BeanForm)form, request, RuleType.PURCHASING_RULES);
    }

    /**
     * 删除Purchasing Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward deletePurchasing(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return delete(mapping, request, RuleType.PURCHASING_RULES);
    }

    /**
     * 查询Purchasing Price Control Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward listPurchasingPriceControl(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute(FlowAction.WEB_DRAG_AND_DRAW, FlowAction.WEB_DRAG_AND_DRAW);
        return list(mapping, (FlowQueryForm) form, request, response, RuleType.PURCHASING_PRICE_CONTROL_RULES);
    }
    
    /**
     * 查询Purchasing Price Control Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward listPurchasingPriceControlWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute(FlowAction.WEB_DRAG_AND_DRAW, FlowAction.WEB_DRAG_AND_DRAW);
        return list(mapping, (FlowQueryForm) form, request, response, RuleType.PURCHASING_PRICE_CONTROL_RULES);
    }
    
    /**
     * 新增Purchasing Price Control Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newPurchasingPriceControl(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return newObject(mapping, request, RuleType.PURCHASING_PRICE_CONTROL_RULES, true);
    }
    
    /**
     * 新增Purchasing Price Control Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newPurchasingPriceControlWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return newObject(mapping, request, RuleType.PURCHASING_PRICE_CONTROL_RULES, false);
    }

    /**
     * 插入新增的Purchasing Price Control Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insertPurchasingPriceControl(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return insert(mapping, (BeanForm)form, request, RuleType.PURCHASING_PRICE_CONTROL_RULES);
    }
    
    /**
     * 插入新增的Purchasing Price Control Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insertPurchasingPriceControlWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return insertWebDragAndDraw(mapping, (BeanForm)form, request, RuleType.PURCHASING_PRICE_CONTROL_RULES);
    }

    /**
     * 修改Purchasing Price Control Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editPurchasingPriceControl(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return edit(mapping, request, RuleType.PURCHASING_PRICE_CONTROL_RULES, true);
    }
    
    /**
     * 修改Purchasing Price Control Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editPurchasingPriceControlWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return edit(mapping, request, RuleType.PURCHASING_PRICE_CONTROL_RULES, true);
    }

    /**
     * 保存修改的Purchasing Price Control Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward updatePurchasingPriceControl(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return update(mapping, (BeanForm)form, request, RuleType.PURCHASING_PRICE_CONTROL_RULES);
    }
    
    /**
     * 保存修改的Purchasing Price Control Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward updatePurchasingPriceControlWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return updateWebDragAndDraw(mapping, (BeanForm)form, request, RuleType.PURCHASING_PRICE_CONTROL_RULES);
    }

    /**
     * 删除Purchasing Price Control Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward deletePurchasingPriceControl(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return delete(mapping, request, RuleType.PURCHASING_PRICE_CONTROL_RULES);
    }

    /**
     * 查询PO Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward listPOApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute(FlowAction.WEB_DRAG_AND_DRAW, FlowAction.WEB_DRAG_AND_DRAW);
        return list(mapping, (FlowQueryForm) form, request, response, RuleType.PO_APPROVAL_RULES);
    }
    
    /**
     * 查询PO Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward listPOApprovalWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute(FlowAction.WEB_DRAG_AND_DRAW, FlowAction.WEB_DRAG_AND_DRAW);
        return list(mapping, (FlowQueryForm) form, request, response, RuleType.PO_APPROVAL_RULES);
    }
    
    /**
     * 新增PO Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newPOApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return newObject(mapping, request, RuleType.PO_APPROVAL_RULES, true);
    }
    
    /**
     * 新增PO Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newPOApprovalWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return newObject(mapping, request, RuleType.PO_APPROVAL_RULES, false);
    }

    /**
     * 插入新增的PO Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insertPOApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return insert(mapping, (BeanForm)form, request, RuleType.PO_APPROVAL_RULES);
    }
    
    /**
     * 插入新增的PO Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insertPOApprovalWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return insertWebDragAndDraw(mapping, (BeanForm)form, request, RuleType.PO_APPROVAL_RULES);
    }

    /**
     * 修改PO Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editPOApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return edit(mapping, request, RuleType.PO_APPROVAL_RULES, true);
    }
    
    /**
     * 修改PO Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editPOApprovalWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return edit(mapping, request, RuleType.PO_APPROVAL_RULES, true);
    }

    /**
     * 保存修改的PO Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward updatePOApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return update(mapping, (BeanForm)form, request, RuleType.PO_APPROVAL_RULES);
    }
    
    /**
     * 保存修改的PO Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward updatePOApprovalWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return updateWebDragAndDraw(mapping, (BeanForm)form, request, RuleType.PO_APPROVAL_RULES);
    }

    /**
     * 删除PO Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward deletePOApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return delete(mapping, request, RuleType.PO_APPROVAL_RULES);
    }

    /**
     * 查询Expense Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward listExpenseApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute(FlowAction.WEB_DRAG_AND_DRAW, FlowAction.WEB_DRAG_AND_DRAW);
        return list(mapping, (FlowQueryForm) form, request, response, RuleType.EXPENSE_APPROVAL_RULES);
    }
    
    /**
     * 查询Expense Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward listExpenseApprovalWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute(FlowAction.WEB_DRAG_AND_DRAW, FlowAction.WEB_DRAG_AND_DRAW);
        return list(mapping, (FlowQueryForm) form, request, response, RuleType.EXPENSE_APPROVAL_RULES);
    }
    
    /**
     * 新增Expense Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newExpenseApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return newObject(mapping, request, RuleType.EXPENSE_APPROVAL_RULES, true);
    }
    
    /**
     * 新增Expense Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newExpenseApprovalWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return newObject(mapping, request, RuleType.EXPENSE_APPROVAL_RULES, false);
    }

    /**
     * 插入新增的Expense Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insertExpenseApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return insert(mapping, (BeanForm)form, request, RuleType.EXPENSE_APPROVAL_RULES);
    }
    
    
    /**
     * 插入新增的Expense Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insertExpenseApprovalWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {       
        return insertWebDragAndDraw(mapping, (BeanForm)form, request, RuleType.EXPENSE_APPROVAL_RULES);
    }

    /**
     * 修改Expense Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editExpenseApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return edit(mapping, request, RuleType.EXPENSE_APPROVAL_RULES, true);
    }
    
    /**
     * 修改Expense Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editExpenseApprovalWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {        
        return edit(mapping, request, RuleType.EXPENSE_APPROVAL_RULES, true);
    }

    /**
     * 保存修改的Expense Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward updateExpenseApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return update(mapping, (BeanForm)form, request, RuleType.EXPENSE_APPROVAL_RULES);
    }
    
    /**
     * 保存修改的Expense Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward updateExpenseApprovalWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {  
        return updateWebDragAndDraw(mapping, (BeanForm)form, request, RuleType.EXPENSE_APPROVAL_RULES);
    }    

    /**
     * 删除Expense Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward deleteExpenseApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return delete(mapping, request, RuleType.EXPENSE_APPROVAL_RULES);
    }

    /**
     * 查询Expense Claim Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward listExpenseClaim(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute(FlowAction.WEB_DRAG_AND_DRAW, FlowAction.WEB_DRAG_AND_DRAW);
        return list(mapping, (FlowQueryForm) form, request, response, RuleType.EXPENSE_CLAIM_RULES);
    }
    
    /**
     * 查询Expense Claim Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward listExpenseClaimWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {        
        request.setAttribute(FlowAction.WEB_DRAG_AND_DRAW, FlowAction.WEB_DRAG_AND_DRAW);
        return list(mapping, (FlowQueryForm) form, request, response, RuleType.EXPENSE_CLAIM_RULES);
    }
    
    /**
     * 新增Expense Claim Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newExpenseClaim(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return newObject(mapping, request, RuleType.EXPENSE_CLAIM_RULES, true);
    }
    
    /**
     * 新增Expense Claim Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newExpenseClaimWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return newObject(mapping, request, RuleType.EXPENSE_CLAIM_RULES, false);
    }

    /**
     * 插入新增的Expense Claim Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insertExpenseClaim(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return insert(mapping, (BeanForm)form, request, RuleType.EXPENSE_CLAIM_RULES);
    }

    /**
     * 插入新增的Expense Claim Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insertExpenseClaimWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return insertWebDragAndDraw(mapping, (BeanForm)form, request, RuleType.EXPENSE_CLAIM_RULES);
    }
    
    /**
     * 修改Expense Claim Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editExpenseClaim(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return edit(mapping, request, RuleType.EXPENSE_CLAIM_RULES, true);
    }
    
    /**
     * 修改Expense Claim Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editExpenseClaimWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return edit(mapping, request, RuleType.EXPENSE_CLAIM_RULES, true);
    }

    /**
     * 保存修改的Expense Claim Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward updateExpenseClaim(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return update(mapping, (BeanForm)form, request, RuleType.EXPENSE_CLAIM_RULES);
    }
    
    /**
     * 保存修改的Expense Claim Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward updateExpenseClaimWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return updateWebDragAndDraw(mapping, (BeanForm)form, request, RuleType.EXPENSE_CLAIM_RULES);
    }

    /**
     * 删除Expense Claim Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward deleteExpenseClaim(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return delete(mapping, request, RuleType.EXPENSE_CLAIM_RULES);
    }

    /**
     * 查询Travel Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward listTravelApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute(FlowAction.WEB_DRAG_AND_DRAW, FlowAction.WEB_DRAG_AND_DRAW);
        return list(mapping, (FlowQueryForm) form, request, response, RuleType.TRAVEL_APPROVAL_RULES);
    }
    
    /**
     * 查询Travel Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward listTravelApprovalWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute(FlowAction.WEB_DRAG_AND_DRAW, FlowAction.WEB_DRAG_AND_DRAW);
        return list(mapping, (FlowQueryForm) form, request, response, RuleType.TRAVEL_APPROVAL_RULES);
    }
    
    /**
     * 新增Travel Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newTravelApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return newObject(mapping, request, RuleType.TRAVEL_APPROVAL_RULES, true);
    }
    
    /**
     * 新增Travel Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newTravelApprovalWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return newObject(mapping, request, RuleType.TRAVEL_APPROVAL_RULES, false);
    }

    /**
     * 插入新增的Travel Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insertTravelApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return insert(mapping, (BeanForm)form, request, RuleType.TRAVEL_APPROVAL_RULES);
    }
    
    /**
     * 插入新增的Travel Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insertTravelApprovalWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return insertWebDragAndDraw(mapping, (BeanForm)form, request, RuleType.TRAVEL_APPROVAL_RULES);
    }

    /**
     * 修改Travel Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editTravelApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return edit(mapping, request, RuleType.TRAVEL_APPROVAL_RULES, true);
    }
    
    /**
     * 修改Travel Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editTravelApprovalWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return edit(mapping, request, RuleType.TRAVEL_APPROVAL_RULES, true);
    }

    /**
     * 保存修改的Travel Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward updateTravelApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return update(mapping, (BeanForm)form, request, RuleType.TRAVEL_APPROVAL_RULES);
    }
    
    /**
     * 保存修改的Travel Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward updateTravelApprovalWebDragAndDraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return updateWebDragAndDraw(mapping, (BeanForm)form, request, RuleType.TRAVEL_APPROVAL_RULES);
    }

    /**
     * 删除Travel Approval Flow
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward deleteTravelApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return delete(mapping, request, RuleType.TRAVEL_APPROVAL_RULES);
    }
    
    private Site prepareWebDragAndDraw(HttpServletRequest request) {
        SiteManager sm = ServiceLocator.getSiteManager(request);
        Integer siteId = ActionUtils.parseInt(request.getParameter("site_id"));
        Site site = sm.getSite(siteId);
        if (site == null) {
            throw new ActionException("site.notFound", siteId);
        }
        request.setAttribute(FlowAction.WEB_DRAG_AND_DRAW, FlowAction.WEB_DRAG_AND_DRAW);
        
        return site;
    }       
    
    private Set getFlowRules(Element flowXmlRoot, Site site, RuleType type,  Flow flow) {
        TreeSet result = new TreeSet(new Comparator() {

            public int compare(Object o1, Object o2) {
                return ((FlowRule)o1).getSeq() - ((FlowRule)o2).getSeq(); 
            }
            
        });      
        Set conditionSet = null;
        Set consequenceSet = null;
        Element flowRuleElement = null;
        Element conditionElement = null;
        Element consequenceElement = null;
        for (Iterator i = flowXmlRoot.elementIterator("rules"); i.hasNext();) { 
            for (Iterator j = ((Element)i.next()).elementIterator("rule"); j.hasNext();) {
                Rule rule = new Rule();
                flowRuleElement = (Element)j.next();
                rule.setSite(site);
                rule.setType(type);
                rule.setDescription(flowRuleElement.attributeValue("description"));
                rule.setEnabled((EnabledDisabled)PersistentEnum.fromEnumCode(EnabledDisabled.class, ActionUtils.parseInt(flowRuleElement.attributeValue("enabled"))));
                if (stringNotEmpty(flowRuleElement.attributeValue("top"))) {
                    rule.setTop(Integer.valueOf(flowRuleElement.attributeValue("top")));
                }
                if (stringNotEmpty(flowRuleElement.attributeValue("left"))) {
                    rule.setLeft(Integer.valueOf(flowRuleElement.attributeValue("left")));
                }
                
                FlowRule flowRule = new FlowRule();
                flowRule.setFlow(flow);
                flowRule.setSeq(Integer.valueOf(flowRuleElement.attributeValue("seq")));
                flowRule.setRule(rule);
                if (stringNotEmpty(flowRuleElement.attributeValue("nextSeqWhenPass"))) {
                    flowRule.setNextSeqWhenPass(Integer.valueOf(flowRuleElement.attributeValue("nextSeqWhenPass")));
                }                
                if (stringNotEmpty(flowRuleElement.attributeValue("nextSeqWhenFail"))) {
                    flowRule.setNextSeqWhenFail(Integer.valueOf(flowRuleElement.attributeValue("nextSeqWhenFail")));
                }
                
                conditionSet = new HashSet();
                for (Iterator k = flowRuleElement.elementIterator("conditions"); k.hasNext();) {                    
                    for (Iterator l = ((Element)k.next()).elementIterator("condition"); l.hasNext();) {
                        conditionElement = (Element)l.next();
                        RuleCondition condition = new RuleCondition();
                        if (stringNotEmpty(conditionElement.attributeValue("conditionType"))) {
                            condition.setType((ConditionType)PersistentEnum.fromEnumCode(ConditionType.class, ActionUtils.parseInt(conditionElement.attributeValue("conditionType"))));
                        }
                        if (stringNotEmpty(conditionElement.attributeValue("compareType"))) {
                            condition.setCompareType((ConditionCompareType)PersistentEnum.fromEnumCode(ConditionCompareType.class, ActionUtils.parseInt(conditionElement.attributeValue("compareType"))));
                        }                        
                        condition.setValue(conditionElement.attributeValue("value"));      
                        conditionSet.add(condition);
                    }
                }
                rule.setConditions(conditionSet);
                
                consequenceSet = new HashSet();
                for (Iterator k = flowRuleElement.elementIterator("consequences"); k.hasNext();) {                    
                    for (Iterator l = ((Element)k.next()).elementIterator("consequence"); l.hasNext();) {
                        consequenceElement = (Element)l.next();
                        RuleConsequence consequence = new RuleConsequence();
                        if (stringNotEmpty(consequenceElement.attributeValue("seq"))) {
                            consequence.setSeq(Integer.valueOf(consequenceElement.attributeValue("seq")));
                        }                        
                        if (stringNotEmpty(consequenceElement.attributeValue("canModify"))) {
                            consequence.setCanModify((YesNo)PersistentEnum.fromEnumCode(YesNo.class, ActionUtils.parseInt(consequenceElement.attributeValue("canModify"))));
                        }
                        String userId = consequenceElement.attributeValue("user");
                        if (stringNotEmpty(userId)) {
                            User user = new User(Integer.valueOf(userId));
                            consequence.setUser(user);
                        }
                        
                        String superiorId = consequenceElement.attributeValue("superior");                        
                        if (stringNotEmpty(superiorId)) {
                            User superior = new User(Integer.valueOf(superiorId));
                            consequence.setSuperior(superior);
                        }
                        
                        consequenceSet.add(consequence);
                    }
                }  
                rule.setConsequences(consequenceSet);
                
                result.add(flowRule);
            }
        }
        
        return result;
    }
      

    private Element getFlowXmlRoot(String flowXml, String encoding) {
        SAXReader reader = new SAXReader();
        InputStream bais;
        try {
            bais = new ByteArrayInputStream(flowXml.getBytes(encoding));
            Document doc = reader.read(bais);
            Element root = doc.getRootElement(); 
            
            return root;
        } catch (DocumentException e) {
            throw new ActionException("get flow xml root error");
        } catch (UnsupportedEncodingException e1) {
            throw new ActionException("get flow xml root error, unsupported encoding " + encoding);
        }
    }
    
    private boolean stringNotEmpty(String value) {
        return (value != null && value.trim().length() > 0);
    }
    
    private List getRulesFromFlowRules(Set flowRuleSet) {
        List list = new ArrayList();
        if (flowRuleSet != null && flowRuleSet.size() > 0) {
            Iterator iterator = flowRuleSet.iterator();
            while (iterator.hasNext()) {
            list.add(((FlowRule)iterator.next()).getRule());
            }
        }
        return list;
    }
}
