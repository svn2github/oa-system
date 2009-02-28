/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.web.struts.action.business.rule;

import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.aof.model.admin.Site;
import com.aof.model.business.rule.Rule;
import com.aof.model.business.rule.query.RuleQueryCondition;
import com.aof.model.business.rule.query.RuleQueryOrder;
import com.aof.model.metadata.EnabledDisabled;
import com.aof.model.metadata.RuleType;
import com.aof.service.business.rule.RuleManager;
import com.aof.utils.SessionTempFile;
import com.aof.web.struts.action.BaseAction;
import com.aof.web.struts.action.ServiceLocator;
import com.aof.web.struts.form.business.rule.RuleQueryForm;
import com.shcnc.hibernate.PersistentEnum;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.action.ActionUtils;
import com.shcnc.struts.form.BeanForm;
import com.shcnc.utils.BeanUtils;
import com.shcnc.utils.ExportUtil;
import com.shcnc.utils.Exportable;

/**
 * 操作Rule和Filter的共用Action
 * @author nicebean
 * @version 1.0 (2005-12-27)
 */
public class BaseRuleAction extends BaseAction {
    protected ActionForward list(ActionMapping mapping, RuleQueryForm queryForm, HttpServletRequest request, HttpServletResponse response, RuleType type) throws Exception {
        RuleManager rm = ServiceLocator.getRuleManager(request);

        request.setAttribute("X_ENABLEDDISABLEDLIST", PersistentEnum.getEnumList(EnabledDisabled.class));

        queryForm.getSite().setList(getAndCheckGrantedSiteList(request));

        Map conditions = constructQueryMap(queryForm);

        conditions.put(RuleQueryCondition.TYPE_EQ, type.getEnumCode());

        String exportType = queryForm.getExportType();
        if (exportType != null && exportType.length() > 0) {
            List data = rm.getRuleList(conditions, 0, -1, RuleQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend());
            int index = SessionTempFile.createNewTempFile(request);
            String fileName = type.getPrefixUrl() + "Rules";
            String suffix = ExportUtil.export(exportType, data, request, new FileOutputStream(SessionTempFile.getTempFile(index, request)), new Exportable() {

                public void exportHead(List row, HttpServletRequest request) throws Exception {
                    MessageResources messages = getResources(request);
                    row.add(messages.getMessage(getLocale(request), "rule.id"));
                    row.add(messages.getMessage(getLocale(request), "rule.description"));
                    row.add(messages.getMessage(getLocale(request), "rule.enabled"));
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
            queryForm.init(rm.getRuleListCount(conditions));
        } else {
            queryForm.init();
        }

        request.setAttribute("X_RESULTLIST", rm.getRuleList(conditions, queryForm.getPageNoAsInt(), queryForm.getPageSizeAsInt(), RuleQueryOrder
                .getEnum(queryForm.getOrder()), queryForm.isDescend()));
        request.setAttribute("X_RULETYPE", type);
        return mapping.findForward("page");
    }

    private Map constructQueryMap(RuleQueryForm queryForm) {
        Map conditions = new HashMap();

        conditions.put(RuleQueryCondition.SITE_ID_EQ, ((Site) queryForm.getSite().getSelectedItem()).getId());

        Integer id = ActionUtils.parseInt(queryForm.getId());
        if (id != null)
            conditions.put(RuleQueryCondition.ID_EQ, id);

        String description = queryForm.getDescription();
        if (description != null) {
            description = description.trim();
            if (description.length() == 0)
                description = null;
        }
        if (description != null)
            conditions.put(RuleQueryCondition.DESCRIPTION_LIKE, description);

        Integer enabled = ActionUtils.parseInt(queryForm.getEnabled());
        if (enabled != null) {
            conditions.put(RuleQueryCondition.ENABLED_EQ, enabled);
        }
        
        String username=queryForm.getUsername();
        if (username !=null && !username.equals(""))
            conditions.put(RuleQueryCondition.USERNAME_LIKE, new Object[]{"%"+username.toUpperCase()+"%","%"+username.toUpperCase()+"%"});
        return conditions;
    }

    protected ActionForward newObject(ActionMapping mapping, HttpServletRequest request, RuleType type) throws Exception {
        if (!isBack(request)) {
            Rule r = prepareAndCheckRule(type, request);
            BeanForm ruleForm = (BeanForm) getForm("/insert" + type.getPrefixUrl() + "Rule", request);
            ruleForm.populateToForm(r);
        }
        prepareData(type, request);
        return mapping.findForward("pageRuleNew");
    }
    
    protected ActionForward newObjectWebDragAndDraw(ActionMapping mapping, HttpServletRequest request, RuleType type) throws Exception {
        if (!isBack(request)) {
            Rule r = prepareAndCheckRule(type, request);
            BeanForm ruleForm = (BeanForm) getForm("/insert" + type.getPrefixUrl() + "Rule", request);
            ruleForm.populateToForm(r);
        }
        prepareData(type, request);
        return mapping.findForward("pageRuleWebDragAndDraw");
    }

    protected ActionForward insert(ActionMapping mapping, BeanForm ruleForm, HttpServletRequest request, RuleType type) throws Exception {
        Rule r = prepareAndCheckRule(type, request);
        ruleForm.populateToBean(r);
        RuleManager rm = ServiceLocator.getRuleManager(request);
        request.setAttribute("X_OBJECT", rm.saveRule(r));
        request.setAttribute("X_ROWPAGE", "rule/row.jsp");
        return mapping.findForward("successRuleNew");
    }

    protected ActionForward edit(ActionMapping mapping, HttpServletRequest request, RuleType type) throws Exception {
        Rule r = getAndCheckRule(type, request);
        if (!isBack(request)) {
            BeanForm ruleForm = (BeanForm) getForm("/update" + type.getPrefixUrl() + "Rule", request);
            ruleForm.populateToForm(r);
        }
        prepareData(type, request);
        request.setAttribute("X_RULEINUSE", Boolean.valueOf(ServiceLocator.getRuleManager(request).isRuleInUse(r.getId())));
        request.setAttribute("X_ENABLED", r.getEnabled());
        return mapping.findForward("pageRuleEdit");
    }

    protected ActionForward update(ActionMapping mapping, BeanForm ruleForm, HttpServletRequest request, RuleType type) throws Exception {
        Rule r = getAndCheckRule(type, request);
        Site s = r.getSite();
        ruleForm.populateToBean(r);
        // 更新rule时不允许修改所属site
        r.setSite(s);

        RuleManager rm = ServiceLocator.getRuleManager(request);
        // 如果rule被flow引用，则状态一定为ENABLED
        if (rm.isRuleInUse(r.getId()))
            r.setEnabled(EnabledDisabled.ENABLED);
        request.setAttribute("X_OBJECT", rm.saveRule(r));
        request.setAttribute("X_ROWPAGE", "rule/row.jsp");

        return mapping.findForward("successRuleEdit");
    }

    protected ActionForward delete(ActionMapping mapping, HttpServletRequest request, RuleType type) throws Exception {
        Rule r = getAndCheckRule(type, request);
        RuleManager rm = ServiceLocator.getRuleManager(request);
        try {
            rm.removeRule(r.getId());
        } catch (Exception e) {
            throw new ActionException("rule.delete" + type.getPrefixUrl() + ".error");
        }

        return mapping.findForward("successRuleDelete");
    }

    private Rule prepareAndCheckRule(RuleType type, HttpServletRequest request) throws Exception {
        Site s = getAndCheckSite("site_id", request);
        Rule r = new Rule();
        r.setSite(s);
        r.setType(type);
        return r;
    }

    private Rule getAndCheckRule(RuleType type, HttpServletRequest request) throws Exception {
        RuleManager rm = ServiceLocator.getRuleManager(request);
        Integer id = ActionUtils.parseInt(request.getParameter("id"));
        Rule r = rm.getRule(id);
        if (r == null || !type.equals(r.getType()))
            throw new ActionException("rule." + type.getPrefixUrl() + ".notFound", id);
        checkSite(r.getSite(), request);
        return r;
    }

    private void prepareData(RuleType type, HttpServletRequest request) {
        request.setAttribute("X_ENABLEDDISABLEDLIST", PersistentEnum.getEnumList(EnabledDisabled.class));
        request.setAttribute("X_RULETYPE", type);
    }

}
