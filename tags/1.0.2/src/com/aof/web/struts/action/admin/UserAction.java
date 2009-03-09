/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.web.struts.action.admin;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.aof.model.admin.Function;
import com.aof.model.admin.GlobalParameter;
import com.aof.model.admin.Site;
import com.aof.model.admin.User;
import com.aof.model.admin.UserRole;
import com.aof.model.admin.query.UserQueryCondition;
import com.aof.model.admin.query.UserQueryOrder;
import com.aof.model.metadata.EnabledDisabled;
import com.aof.model.metadata.Gender;
import com.aof.service.admin.EmailManager;
import com.aof.service.admin.FunctionManager;
import com.aof.service.admin.GlobalManager;
import com.aof.service.admin.RoleManager;
import com.aof.service.admin.UserManager;
import com.aof.utils.PDFReport;
import com.aof.utils.SessionTempFile;
import com.aof.web.struts.action.BaseAction;
import com.aof.web.struts.action.ServiceLocator;
import com.aof.web.struts.form.admin.UserQueryForm;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.shcnc.hibernate.PersistentEnum;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.action.ActionUtils;
import com.shcnc.struts.action.BackToInputActionException;
import com.shcnc.struts.form.BeanForm;
import com.shcnc.utils.BeanUtils;
import com.shcnc.utils.ExportUtil;
import com.shcnc.utils.Exportable;
import com.shcnc.utils.MD5;

/**
 * 操作User的Action
 * @author nicebean
 * @version 1.0 (2005-11-14)
 */
public class UserAction extends BaseAction {
    
    private static final String DEFAULT_LOCALE="en";
    
    
    /**
     * User role报表
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward userRoleReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UserManager um =  ServiceLocator.getUserManager(request);
        UserQueryForm queryForm = (UserQueryForm) form;
        request.setAttribute("X_ENABLEDDISABLEDLIST", PersistentEnum.getEnumList(EnabledDisabled.class));
        

        /**
         * 检查要查询的Site是否被授权，并取得授权Site列表
         */
        List grantedSiteList = getAndCheckGrantedSiteDeparmentList(request);
        request.setAttribute("X_SITELIST", grantedSiteList); 
        
        Integer siteId = ActionUtils.parseInt(queryForm.getSiteId());
        if (siteId == null) {
            siteId = ((Site) grantedSiteList.get(0)).getId();
            queryForm.setSiteId(siteId.toString());
            queryForm.setOrder(UserQueryOrder.NAME.getName());
        } else {
            checkSite(siteId, request);
        }


        Map conditions = constructQueryMap(queryForm);
        
        String exportType = queryForm.getExportType();
        if (exportType != null && exportType.length() > 0) {
            List data = um.getUserRoleList(conditions, UserQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend());
            
            exportPDF(ActionUtils.parseInt(queryForm.getSiteId()),data, "userRole", request, response);
            return null;
        }

        if (queryForm.isFirstInit()) {
            queryForm.init(um.getUserListCount(conditions));
        } else {
            queryForm.init();
        }

        List userList=um.getUserList
            (conditions, queryForm.getPageNoAsInt(), queryForm.getPageSizeAsInt(), UserQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend());
        um.fillUserRole(userList);
        request.setAttribute("X_RESULTLIST",userList); 
        
        RoleManager rm=ServiceLocator.getRoleManager(request);
        request.setAttribute("x_roleList",rm.getAllRoleList());
        return mapping.findForward("page");
    }
    
    private void exportPDF(Integer siteId, List data, String filename, HttpServletRequest request, HttpServletResponse response) throws MalformedURLException, DocumentException, IOException {
        MessageResources messages = getResources(request);
        Locale locale = getLocale(request);
        
        PDFReport report = PDFReport.createReport(siteId, "user.userRole.report", request, messages, locale, PageSize.A4.rotate());
        Document document = report.getDocument();

        PdfPTable table = PDFReport.createTable(new float[] {1,2,1,1,1}, 100, 0.5f);
        table.setHeaderRows(1);
        table.setSplitLate(false);
        PdfPCell defaultCell = table.getDefaultCell();
        Color defaultBackgroundColor = defaultCell.backgroundColor();

        defaultCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        defaultCell.setBackgroundColor(new Color(0x99, 0x99, 0xff));
        
        Font headFont = PDFReport.getFont(Font.BOLD, Color.BLACK);
        report.addCell(table, "user.name", headFont, true);
        report.addCell(table, "userRole.role", headFont, true);
        report.addCell(table, "role.type", headFont, true);
        report.addCell(table, "userDepartment.site", headFont, true);
        report.addCell(table, "userDepartment.department", headFont, true);
        

        defaultCell.setBackgroundColor(defaultBackgroundColor);
        

        for (Iterator itor = data.iterator(); itor.hasNext();) {
            User user = (User) itor.next();
            defaultCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            report.addCell(table, user.getName());
            
            PdfPTable table2 = PDFReport.createTable(new float[] {2,1,1,1}, 100, 0.5f);
            
            boolean hasItem = false;
            for (Iterator itor2 = user.getUserRoleList().iterator(); itor2.hasNext();) {
                hasItem = true;
                UserRole ur = (UserRole) itor2.next();
                report.addCell(table2, ur.getRole().getName());
                report.addCell(table2, ur.getRole().getType().getEngDescription());
                if(ur.getGrantedSite()!=null)
                    report.addCell(table2, ur.getGrantedSite().getName());
                else
                    report.addCell(table2, null);
                
                if(ur.getGrantedDepartment()!=null)
                    report.addCell(table2, ur.getGrantedDepartment().getName());
                else
                    report.addCell(table2, null);
                
                
            }
            if (!hasItem) {
                report.addCell(table2, null);
                report.addCell(table2, null);
                report.addCell(table2, null);
            }
            PDFReport.AddTableToTable(table, table2, 4);
        }
        
        document.add(table);
        
        report.output(filename, response);

    }


    /**
     * 查询 User
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        UserQueryForm queryForm = (UserQueryForm) form;
        request.setAttribute("X_ENABLEDDISABLEDLIST", PersistentEnum.getEnumList(EnabledDisabled.class));
        

        /**
         * 检查要查询的Site是否被授权，并取得授权Site列表
         */
        List grantedSiteList = getAndCheckGrantedSiteDeparmentList(request);
        request.setAttribute("X_SITELIST", grantedSiteList);
        
        if (hasGlobalPower(request)) {
            request.setAttribute("X_GLOBAL", Boolean.TRUE);
        } else {
            /**
             * Site级查询初始应取授权Site列表第一项
             */
            Integer siteId = ActionUtils.parseInt(queryForm.getSiteId());
            if (siteId == null) {
                siteId = ((Site) grantedSiteList.get(0)).getId();
                queryForm.setSiteId(siteId.toString());
            } else {
                checkSite(siteId, request);
            }
            request.setAttribute("X_GLOBAL", Boolean.FALSE);
        }
        
        UserManager um =  ServiceLocator.getUserManager(request);
        
        Map conditions = constructQueryMap(queryForm);
        
        String exportType = queryForm.getExportType();
        if (exportType != null && exportType.length() > 0) {
            List data = um.getUserList(conditions, 0, -1, UserQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend());
            int index = SessionTempFile.createNewTempFile(request);
            String fileName = "user";
            String suffix = ExportUtil.export(exportType, data, request, new FileOutputStream(SessionTempFile.getTempFile(index, request)), new Exportable() {

                        public void exportHead(List row, HttpServletRequest request) throws Exception{
                            MessageResources messages = getResources(request);
                            row.add(messages.getMessage(getLocale(request), "user.loginName"));
                            row.add(messages.getMessage(getLocale(request), "user.name"));
                            row.add(messages.getMessage(getLocale(request), "user.email"));
                            row.add(messages.getMessage(getLocale(request), "user.telephone"));
                            row.add(messages.getMessage(getLocale(request), "user.enabled"));
                        }

                        public void exportRow(List row, Object data, HttpServletRequest request) throws Exception {
                            row.add(BeanUtils.getProperty(data, "loginName"));
                            row.add(BeanUtils.getProperty(data, "name"));
                            row.add(BeanUtils.getProperty(data, "email"));
                            row.add(BeanUtils.getProperty(data, "telephone"));
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
            queryForm.init(um.getUserListCount(conditions));
        } else {
            queryForm.init();
        }

        request.setAttribute("X_RESULTLIST", um.getUserList(conditions, queryForm.getPageNoAsInt(), queryForm.getPageSizeAsInt(), UserQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend()));
        return mapping.findForward("page");
    }
    
    /**
     * 选取 User (维护Site时选取manager)
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward select(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UserQueryForm queryForm = (UserQueryForm) form;
        queryForm.setPageSize("10");
        return list(mapping, form, request, response);
    }
    
    private Map constructQueryMap(UserQueryForm queryForm) {
        Map conditions = new HashMap();

        String loginName = queryForm.getLoginName();
        if (loginName != null) {
            loginName = loginName.trim();
            if (loginName.length() == 0) loginName = null;
        }
        if (loginName != null) conditions.put(UserQueryCondition.LOGINNAME_LIKE, loginName);

        String name = queryForm.getName();
        if (name != null) {
            name = name.trim();
            if (name.length() == 0) name = null;
        }
        if (name != null) conditions.put(UserQueryCondition.NAME_LIKE, name);
        
        Integer siteId = ActionUtils.parseInt(queryForm.getSiteId());
        Integer departmentId = ActionUtils.parseInt(queryForm.getDepartmentId());
        if (departmentId != null) {
            conditions.put(UserQueryCondition.DEPARTMENT_ID_EQ, departmentId);
        } else if (siteId != null) {
            conditions.put(UserQueryCondition.PRIMARY_OR_SITE_ID_EQ, siteId);
        }
        
        Integer enabled = ActionUtils.parseInt(queryForm.getEnabled());
        if (enabled != null) {
            conditions.put(UserQueryCondition.ENABLED_EQ, enabled);
        }
        
        {
            Integer roleId = ActionUtils.parseInt(queryForm.getRoleId());
            if (roleId != null) {
                conditions.put(UserQueryCondition.ROLE_ID_EQ, roleId);
            }
        }
        return conditions;
    }

    /**
     * 新增 User
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newObject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!isBack(request)) {
            User u = new User();
            BeanForm userForm = (BeanForm) getForm("/insertUser", request);
            userForm.populateToForm(u, new String[] { "password" });
        }
        request.setAttribute("X_GENDERLIST", PersistentEnum.getEnumList(Gender.class));
        request.setAttribute("X_ENABLEDDISABLEDLIST", PersistentEnum.getEnumList(EnabledDisabled.class));
        request.setAttribute("X_SITELIST", getAndCheckGrantedSiteList(request));
        return mapping.findForward("page");
    }

    /**
     * 插入新增的User
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UserManager um =  ServiceLocator.getUserManager(request);
        BeanForm userForm = (BeanForm) form;
        User u = new User();
        userForm.populateToBean(u);
        if (um.getUserByLoginName(u.getLoginName())!= null) {
            throw new BackToInputActionException("user.loginName.existed", (Object)u.getLoginName());
        }
        checkSite(u.getPrimarySite().getId(), request);
        u.setPassword(MD5.getDigestString(u.getPassword()));
        u.setLocale(DEFAULT_LOCALE);
        request.setAttribute("X_OBJECT", um.saveUser(u));
        request.setAttribute("X_ROWPAGE", "user/row.jsp");
        
        EmailManager em=ServiceLocator.getEmailManager(request);
        Map context=new HashMap();
        context.put("user", u);
        context.put("password", userForm.get("password"));
        em.insertEmail(u.getPrimarySite(),u.getEmail(), "NewUser.vm", context);
        return mapping.findForward("success");
    }

    /**
     * 更新 User
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!isBack(request)) {
            Integer id = ActionUtils.parseInt(request.getParameter("id"));
            UserManager um =  ServiceLocator.getUserManager(request);
            User u = um.getUser(id);
            if (u == null) throw new ActionException("user.notFound", id);
            checkSite(u.getPrimarySite(), request);
            BeanForm userForm = (BeanForm) getForm("/updateUser", request);
            userForm.populateToForm(u, new String[] { "password" });
        }
        request.setAttribute("X_GENDERLIST", PersistentEnum.getEnumList(Gender.class));
        request.setAttribute("X_ENABLEDDISABLEDLIST", PersistentEnum.getEnumList(EnabledDisabled.class));
        request.setAttribute("X_SITELIST", getAndCheckGrantedSiteList(request));
        return mapping.findForward("page");
    }

    /**
     * 保存更新的User
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UserManager um =  ServiceLocator.getUserManager(request);
        BeanForm userForm = (BeanForm) form;
        Integer id = ActionUtils.parseInt((String)userForm.get("id"));
        User u = um.getUser(id);
        if (u == null) throw new ActionException("user.notFound", id);
        // 检查被修改的User的PrimarySite是否在当前用户的授权Site中
        List siteList = getAndCheckGrantedSiteList(request);
        Site s = u.getPrimarySite();
        if (!siteList.contains(s)) {
            throw new ActionException("errors.noSitePermission", s.getName());
        }
        EnabledDisabled oldEnabled = u.getEnabled();
        // 如果password字段为空白的话表示不修改password，因此先备份旧password 
        String oldPassword = u.getPassword();
        // loginName字段不能修改，因此先备份旧loginName
        String loginName = u.getLoginName();
        userForm.setBeanLoader(ServiceLocator.getBeanLoader(request));
        userForm.populateToBean(u);
        // 检查修改后的User的PrimarySite是否在当前用户的授权Site中
        s = u.getPrimarySite();
        if (s == null) {
            throw new BackToInputActionException("site.notFound", ActionUtils.parseInt(userForm.getString("primarySite_id")));
        }
        if (!siteList.contains(s)) {
            throw new BackToInputActionException("errors.noSitePermission", s.getName());
        }
        String newPassword = u.getPassword();
        if (newPassword == null || newPassword.length() == 0) {
            u.setPassword(oldPassword);
        } else {
            u.setPassword(MD5.getDigestString(newPassword));
        }
        u.setLoginName(loginName);
        if (Boolean.TRUE.equals(userForm.get("resetLoginFailedCount"))) {
            u.setLoginFailedCount(0);
        }
        if (EnabledDisabled.ENABLED.equals(oldEnabled) && EnabledDisabled.DISABLED.equals(u.getEnabled())) {
            FunctionManager functionManager = ServiceLocator.getFunctionManager(request);
            Function f = functionManager.getFunction("siteUserMaintainance");
            if (f != null) {
                EmailManager emailManager = ServiceLocator.getEmailManager(request);
                Map context = new HashMap();
                for (Iterator itor = um.getEnabledUserList(f, s).iterator(); itor.hasNext(); ) {
                    User admin = (User) itor.next();
                    context.put("user", admin);
                    context.put("target", u);
                    context.put("role", EmailManager.EMAIL_ROLE_USER_MAINTAINER);
                    emailManager.insertEmail(s, admin.getEmail(), "UserDisabled.vm", context);
                }
            }
        }
        request.setAttribute("X_OBJECT", um.saveUser(u));
        request.setAttribute("X_ROWPAGE", "user/row.jsp");
        
        return mapping.findForward("success");
    }

    /**
     * 修改密码
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward changePassword(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        GlobalManager gm=ServiceLocator.getGlobalManager(request);
        GlobalParameter gp=gm.getParameter();
        request.setAttribute("x_minPwdLen",gp.getMinPasswordLength());
        if (!request.getMethod().equals("POST") || isBack(request)) return mapping.findForward("page");
        
        Integer userId = getCurrentUser(request).getId();
        UserManager um =ServiceLocator.getUserManager(request);
        User u = um.getUser(userId);
        if (u == null) throw new ActionException("user.notFound", userId);
        if (!u.getPassword().equalsIgnoreCase(MD5.getDigestString(request.getParameter("oldPwd")))) throw new BackToInputActionException("user.changePassword.passwordIncorrect");
        u.setPassword(MD5.getDigestString(request.getParameter("newPwd")));
        um.saveUser(u);
        return mapping.findForward("success");
    }

    /**
     * 切换语言
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward switchLocale(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        User currentUser = getCurrentUser(request);
        String locale = currentUser.getLocale();
        if ("en".equals(locale)) {
            locale = "zh";
        } else {
            locale = "en";
        }
        UserManager um = ServiceLocator.getUserManager(request);
        User u = um.getUser(currentUser.getId());
        u.setLocale(locale);
        um.saveUser(u);
        currentUser.setLocale(locale);
        setLocale(request, new Locale(locale));
        return mapping.findForward("page");
    }
    
    /**
     * 检查登录名是否可用
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward checkLoginName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if ("POST".equals(request.getMethod())) {
            String loginName = request.getParameter("loginName");
            UserManager um =ServiceLocator.getUserManager(request);
            request.setAttribute("X_LOGINNAME", loginName);
            request.setAttribute("X_USEREXISTED", Boolean.valueOf(um.getUserByLoginName(loginName) != null));
        }
        return mapping.findForward("page");
    }
}