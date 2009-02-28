/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.web.struts.action.admin;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aof.model.admin.Department;
import com.aof.model.admin.Site;
import com.aof.model.metadata.EnabledDisabled;
import com.aof.service.admin.DepartmentManager;
import com.aof.web.struts.action.BaseAction;
import com.aof.web.struts.action.ServiceLocator;
import com.aof.web.struts.form.admin.DepartmentQueryForm;
import com.shcnc.hibernate.PersistentEnum;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.action.ActionUtils;
import com.shcnc.struts.form.BeanForm;
import com.shcnc.struts.form.ComboBox;

/**
 * 操作Department的Action
 * @author nicebean
 * @version 1.0 (2005-11-14)
 */
public class DepartmentAction extends BaseAction {

    /**
     * 显示Department
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionMapping
     * @throws Exception
     */
    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        DepartmentQueryForm queryForm = (DepartmentQueryForm) form;
        ComboBox comboSite = queryForm.getSite();
        comboSite.setList(getAndCheckGrantedSiteList(request));
        DepartmentManager dm = ServiceLocator.getDepartmentManager(request);
        request.setAttribute("X_DEPARTMENTXML", dm.getDepartmentXmlBySite(ActionUtils.parseInt(comboSite.getValue())));
        return mapping.findForward("page");
    }

    /**
     * 新增Department
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionMapping
     * @throws Exception
     */
    public ActionForward newObject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Site s = getAndCheckSite("siteId", request);
        if (!isBack(request)) {
            DepartmentManager dm = ServiceLocator.getDepartmentManager(request);
            Integer parentId = ActionUtils.parseInt(request.getParameter("parentId"));
            Department parent = null;
            if (parentId != null) {
                parent = dm.getDepartment(parentId);
                if (parent == null) throw new ActionException("department.notFound", parentId);
            }
            Department d = new Department();
            d.setSite(s);
            d.setParentDepartment(parent);
            BeanForm departmentForm = (BeanForm) getForm("/insertDepartment", request);
            departmentForm.populateToForm(d);
        }
        prepareData(s, request, null);
        return mapping.findForward("page");
    }

    /**
     * 插入新增的Department
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        DepartmentManager dm = ServiceLocator.getDepartmentManager(request);
        BeanForm departmentForm = (BeanForm) form;
        Department d = new Department();
        departmentForm.populateToBean(d);
        checkSite(d.getSite().getId(), request);
        request.setAttribute("X_OBJECT", dm.saveDepartment(d));
        
        return mapping.findForward("success");
    }

    /**
     * 更新Department
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer id = ActionUtils.parseInt(request.getParameter("id"));
        DepartmentManager dm = ServiceLocator.getDepartmentManager(request);
        Department d = dm.getDepartment(id);
        if (d == null) throw new ActionException("department.notFound", id);
        Site s = d.getSite();
        checkSite(s, request);
        if (!isBack(request)) {
            BeanForm departmentForm = (BeanForm) getForm("/updateDepartment", request);
            departmentForm.populate(d, BeanForm.TO_FORM);
        }
        prepareData(s, request, d);
        request.setAttribute("X_ENABLEDUSERLIST", ServiceLocator.getUserManager(request).getEnabledUserListOfDepartment(d));
        return mapping.findForward("page");
    }

    /**
     * 保存更新的Department
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        DepartmentManager dm = ServiceLocator.getDepartmentManager(request);
        BeanForm departmentForm = (BeanForm) form;
        Integer id = ActionUtils.parseInt((String)departmentForm.get("id"));
        Department d = dm.getDepartment(id);
        if (d == null) throw new ActionException("department.notFound", id);
        checkSite(d.getSite(), request);

        // 更新department时不允许修改所属site
        departmentForm.populateToBean(d, (String[]) null, new String[] { "site.id" } );

        request.setAttribute("X_OBJECT", dm.saveDepartment(d));
        
        return mapping.findForward("success");
    }
    
    private void prepareData(Site s, HttpServletRequest request, Department me) {
        DepartmentManager dm = ServiceLocator.getDepartmentManager(request);
        dm.fillDepartment(s, false);
        Set children = null;
        if (me != null) {
            children = new HashSet();
            children.add(me);
        }
        for (Iterator itor = s.getDepartments().iterator(); itor.hasNext(); ) {
            Department d = (Department) itor.next();
            if (me != null) {
                if (d.equals(me)) {
                    itor.remove();
                    continue;
                }
                Department pd = d.getParentDepartment();
                if (children.contains(pd)) {
                    itor.remove();
                    children.add(d);
                    continue;
                }
            }
            d.setIndentName("　" + d.getIndentName());
        }
        request.setAttribute("X_SITE", s);
        request.setAttribute("X_ENABLEDDISABLEDLIST", PersistentEnum.getEnumList(EnabledDisabled.class));
    }
      
}