/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.web.struts.action.admin;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.MessageResources;

import com.aof.model.admin.Site;
import com.aof.model.admin.query.SiteQueryCondition;
import com.aof.model.admin.query.SiteQueryOrder;
import com.aof.model.metadata.EnabledDisabled;
import com.aof.model.metadata.YesNo;
import com.aof.service.admin.CurrencyManager;
import com.aof.service.admin.SiteManager;
import com.aof.utils.SessionTempFile;
import com.aof.web.struts.action.BaseAction;
import com.aof.web.struts.action.ServiceLocator;
import com.aof.web.struts.form.admin.SiteQueryForm;
import com.shcnc.hibernate.PersistentEnum;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.action.ActionUtils;
import com.shcnc.struts.form.BeanForm;
import com.shcnc.utils.BeanUtils;
import com.shcnc.utils.ExportUtil;
import com.shcnc.utils.Exportable;

/**
 * 操作Site的Action
 * @author nicebean
 * @version 1.0 (2005-11-15)
 */
public class SiteAction extends BaseAction {
    
    /**
     * 查询Site
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	    SiteManager sm = ServiceLocator.getSiteManager(request);
        
        SiteQueryForm queryForm = (SiteQueryForm) form;
        request.setAttribute("X_ENABLEDDISABLEDLIST", PersistentEnum.getEnumList(EnabledDisabled.class));
        
        Map conditions = constructQueryMap(queryForm);
        
        String exportType = queryForm.getExportType();
        if (exportType != null && exportType.length() > 0) {
            List data = sm.getSiteList(conditions, 0, -1, SiteQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend());
            int index = SessionTempFile.createNewTempFile(request);
            String fileName = "site";
            String suffix = ExportUtil.export(exportType, data, request, new FileOutputStream(SessionTempFile.getTempFile(index, request)), new Exportable() {

                        public void exportHead(List row, HttpServletRequest request) throws Exception {
                            MessageResources messages = getResources(request);
                            row.add(messages.getMessage(getLocale(request), "site.name"));
                            row.add(messages.getMessage(getLocale(request), "site.enabled"));
                        }

                        public void exportRow(List row, Object data, HttpServletRequest request) throws Exception {
                            row.add(BeanUtils.getProperty(data, "name"));
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
            queryForm.init(sm.getSiteListCount(conditions));
        } else {
            queryForm.init();
        }

        request.setAttribute("X_RESULTLIST", sm.getSiteList(conditions, queryForm.getPageNoAsInt(), queryForm.getPageSizeAsInt(), SiteQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend()));
        return mapping.findForward("page");
    }

    private Map constructQueryMap(SiteQueryForm queryForm) {
        Map conditions = new HashMap();

        String name = queryForm.getName();
        if (name != null) {
            name = name.trim();
            if (name.length() == 0) name = null;
        }
        if (name != null) conditions.put(SiteQueryCondition.NAME_LIKE, name);

        Integer enabled = ActionUtils.parseInt(queryForm.getEnabled());
        if (enabled != null) {
            conditions.put(SiteQueryCondition.ENABLED_EQ, enabled);
        }
        
        return conditions;
    }

    /**
     * 新增Site
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newObject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!isBack(request)) {
            Site s = new Site();
            BeanForm siteForm = (BeanForm) getForm("/insertSite", request);
            siteForm.populateToForm(s);
        }
        CurrencyManager cm = ServiceLocator.getCurrencyManager(request);
        request.setAttribute("X_CURRENCYLIST", cm.getAllEnabledCurrencyList());
        request.setAttribute("X_YESNOLIST", PersistentEnum.getEnumList(YesNo.class));
        request.setAttribute("X_ENABLEDDISABLEDLIST", PersistentEnum.getEnumList(EnabledDisabled.class));
        request.setAttribute("X_COUNTRYLIST", ServiceLocator.getCountryManager(request).listEnabledCountryProvinceCity());
        return mapping.findForward("page");
    }

    /**
     * 插入新增的Site
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SiteManager sm = ServiceLocator.getSiteManager(request);
        BeanForm siteForm = (BeanForm) form;
        Site s = new Site();
        siteForm.populateToBean(s);
        FormFile logo = (FormFile)siteForm.get("logo");
        int logoSize = 0;
        if (logo != null) logoSize = logo.getFileSize();
        if (logoSize > 0) {
            request.setAttribute("X_OBJECT", sm.saveSite(s, logo.getInputStream()));
        } else {
            request.setAttribute("X_OBJECT", sm.saveSite(s));
        }
        request.setAttribute("X_ROWPAGE", "site/row.jsp");
        
        return mapping.findForward("success");
    }

    /**
     * 修改Site
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CurrencyManager cm = ServiceLocator.getCurrencyManager(request);
        Integer id = ActionUtils.parseInt(request.getParameter("id"));
        SiteManager sm = ServiceLocator.getSiteManager(request);
        if (!isBack(request)) {
            Site s = sm.getSite(id);
            if (s == null) throw new ActionException("site.notFound", id);
            BeanForm siteForm = (BeanForm) getForm("/updateSite", request);
            siteForm.populateToForm(s);
            request.setAttribute("X_CURRENCYLIST", cm.getAllEnabledCurrencyListAndIncludeThis(siteForm.getString("baseCurrency_code")));
        } else {
            request.setAttribute("X_CURRENCYLIST", cm.getAllEnabledCurrencyListAndIncludeThis(request.getParameter("baseCurrency_code")));
        }
        request.setAttribute("X_SITEHASLOGO", Boolean.valueOf(sm.isSiteHasLogo(id)));
        request.setAttribute("X_YESNOLIST", PersistentEnum.getEnumList(YesNo.class));
        request.setAttribute("X_ENABLEDDISABLEDLIST", PersistentEnum.getEnumList(EnabledDisabled.class));
        request.setAttribute("X_COUNTRYLIST", ServiceLocator.getCountryManager(request).listEnabledCountryProvinceCity());
        return mapping.findForward("page");
    }

    /**
     * 保存修改的Site
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SiteManager sm =  ServiceLocator.getSiteManager(request);
        BeanForm siteForm = (BeanForm) form;
        Integer id = ActionUtils.parseInt((String)siteForm.get("id"));
        Site s = sm.getSite(id);
        if (s == null) throw new ActionException("site.notFound", id);
        siteForm.populateToBean(s);
        FormFile logo = (FormFile)siteForm.get("logo");
        int logoSize = 0;
        if (logo != null) logoSize = logo.getFileSize();
        if (logoSize > 0) {
            request.setAttribute("X_OBJECT", sm.saveSite(s, logo.getInputStream()));
        } else {
            request.setAttribute("X_OBJECT", sm.saveSite(s));
        }
        request.setAttribute("X_ROWPAGE", "site/row.jsp");
        
        return mapping.findForward("success");
    }
    
    /**
     * 显示Site logo
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward showLogo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SiteManager sm = ServiceLocator.getSiteManager(request);
        Integer id = ActionUtils.parseInt(request.getParameter("id"));
        InputStream in = sm.getSiteLogo(id);
        if (in != null) {
            OutputStream out = response.getOutputStream();
            int buf;
            while ((buf = in.read()) != -1) {
                out.write(buf);
            }
            out.close();
            in.close();
        }
        
        return null;
    }

}