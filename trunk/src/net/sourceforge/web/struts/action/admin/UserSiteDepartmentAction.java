/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.web.struts.action.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import net.sourceforge.model.admin.Department;
import net.sourceforge.model.admin.Function;
import net.sourceforge.model.admin.Site;
import net.sourceforge.model.admin.User;
import net.sourceforge.model.admin.UserDepartment;
import net.sourceforge.model.admin.UserSite;
import net.sourceforge.service.admin.DepartmentManager;
import net.sourceforge.service.admin.EmailManager;
import net.sourceforge.service.admin.FunctionManager;
import net.sourceforge.service.admin.SiteManager;
import net.sourceforge.service.admin.TravelGroupManager;
import net.sourceforge.service.admin.UserManager;
import net.sourceforge.web.struts.action.BaseAction;
import net.sourceforge.web.struts.action.ServiceLocator;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.action.ActionUtils;
import com.shcnc.struts.action.BackToInputActionException;
import com.shcnc.struts.form.BeanForm;

/**
 * 操作UserSite和UserDepartment的Action
 * @author nicebean
 * @version 1.0 (2005-11-15)
 */
public class UserSiteDepartmentAction extends BaseAction {
    
    /**
     * 显示赋予User的Site和Department
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UserManager um = ServiceLocator.getUserManager(request);

        Integer id = ActionUtils.parseInt(request.getParameter("userId"));
        User u = um.getUser(id);
        if (u == null) throw new ActionException("user.notFound", id);

        request.setAttribute("X_USER", u);
        
        List userSiteList = um.getUserSiteListByUser(u);
        List userDepartmentList = null;
        if (!hasGlobalPower(request)) {
            userDepartmentList = new ArrayList();
            request.setAttribute("X_GLOBAL", Boolean.FALSE);
            // 过滤当前登录用户未授权管理的Site
            List grantedSiteList = getGrantedSiteList(request);
            for (Iterator itor = userSiteList.iterator(); itor.hasNext(); ) {
                UserSite us = (UserSite) itor.next();
                Site s = us.getSite();
                if (!grantedSiteList.contains(s)) {
                    itor.remove();
                } else {
                    userDepartmentList.addAll(um.getUserDepartmentListByUserAndSite(u, s));
                }
            }
        } else {
            request.setAttribute("X_GLOBAL", Boolean.TRUE);
            userDepartmentList = um.getUserDepartmentListByUser(u);
        }
        
        request.setAttribute("X_SITELIST", userSiteList);
        request.setAttribute("X_DEPARTMENTLIST", userDepartmentList);
        return mapping.findForward("page");
    }

    /**
     * 新增用户站点
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newSite(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!isBack(request)) {
            UserManager um = ServiceLocator.getUserManager(request);

            Integer id = ActionUtils.parseInt(request.getParameter("user_id"));
            User u = um.getUser(id);
            if (u == null) throw new ActionException("user.notFound", id);

            UserSite us = new UserSite();
            us.setUser(u);
            BeanForm userSiteForm = (BeanForm) getForm("/insertUserSite", request);
            userSiteForm.populateToForm(us);
        }

        List siteList = getAndCheckGrantedSiteList(request);
        TravelGroupManager tgm = ServiceLocator.getTravelGroupManager(request);
        for (Iterator itor = siteList.iterator(); itor.hasNext(); ) {
            Site s = (Site) itor.next();
            s.setTravelGroups(tgm.getAllEnabledTravelGroupListBySiteId(s.getId()));
        }

        request.setAttribute("X_SITELIST", siteList);
        return mapping.findForward("page");
    }

    /**
     * 插入新增的用户站点
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insertSite(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UserManager um = ServiceLocator.getUserManager(request);

        Integer id = ActionUtils.parseInt(request.getParameter("user_id"));
        User u = um.getUser(id);
        if (u == null) throw new ActionException("user.notFound", id);

        BeanForm userSiteForm = (BeanForm) form;
        UserSite us = new UserSite();
        userSiteForm.populateToBean(us);

        Site s = us.getSite();
        if (s == null) {
            throw new BackToInputActionException("site.notFound", (Object)null);
        }
        SiteManager sm = ServiceLocator.getSiteManager(request);
        s = sm.getSite(s.getId());
        if (s == null) {
            throw new BackToInputActionException("site.notFound", s.getId());
        }
        checkSite(s, request);

        if (um.getUserSite(us.getUser().getId(), s.getId()) != null) {
            throw new BackToInputActionException("userSite.duplicate");
        }
        request.setAttribute("X_OBJECT", um.saveUserSite(us));
        request.setAttribute("X_ROWPAGE", "userSiteDepartment/siteRow.jsp");
        
        EmailManager em =ServiceLocator.getEmailManager(request);
        FunctionManager fm = ServiceLocator.getFunctionManager(request);
        Function function = fm.getFunction("finalConfirmPurchaseOrder");
        List poFinanceUserList=um.getEnabledUserList(function,s);
        function = fm.getFunction("finalConfirmExpense");
        List expenseFinanceUserList=um.getEnabledUserList(function,s);;
        List userList=new ArrayList();
        userList.addAll(poFinanceUserList);
        for (Iterator itor = expenseFinanceUserList.iterator(); itor.hasNext();) {
           User userExpense = (User) itor.next();
           if (!userList.contains(userExpense))
               userList.add(userExpense);
        }
        for (Iterator itor = userList.iterator(); itor.hasNext();) {
            User user = (User) itor.next();
            if (user.getEmail()!=null) {
                Map context=new HashMap();
                context.put("user",u);
                context.put("finance",user);
                context.put("role", EmailManager.EMAIL_ROLE_FINANCE);
                em.insertEmail(s,user.getEmail(),"NewUserSite.vm",context);
            }
        }
        return mapping.findForward("success");
    }

    /**
     * 修改用户站点
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editSite(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UserManager um = ServiceLocator.getUserManager(request);
        Integer userId = ActionUtils.parseInt(request.getParameter("user_id"));
        Integer siteId = ActionUtils.parseInt(request.getParameter("site_id"));
        UserSite us = um.getUserSite(userId, siteId);
        if (us == null) throw new ActionException("userSite.notFound", userId, siteId);

        // 检查当前用户是否可以维护该UserSite
        checkSite(us.getSite(), request);

        BeanForm userSiteForm = (BeanForm) getForm("/updateUserSite", request);
        if (!isBack(request)) {
            userSiteForm.populateToForm(us);
        }

        TravelGroupManager tgm = ServiceLocator.getTravelGroupManager(request);
        request.setAttribute("X_TRAVELGROUPLIST", tgm.getAllEnabledTravelGroupListBySiteIdAndIncludeThis(us.getSite().getId(), ActionUtils.parseInt((String) userSiteForm.get("travelGroup_id"))));
        return mapping.findForward("page");
    }

    /**
     * 保存修改的用户站点
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward updateSite(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UserManager um = ServiceLocator.getUserManager(request);

        Integer userId = ActionUtils.parseInt(request.getParameter("user_id"));
        Integer siteId = ActionUtils.parseInt(request.getParameter("site_id"));
        UserSite us = um.getUserSite(userId, siteId);
        if (us == null) throw new ActionException("userSite.notFound", userId, siteId);
        // 检查当前用户是否可以维护该UserSite
        checkSite(us.getSite(), request);

        BeanForm userSiteForm = (BeanForm) form;
        userSiteForm.populateToBean(us);
        request.setAttribute("X_OBJECT", um.updateUserSite(us));
        request.setAttribute("X_ROWPAGE", "userSiteDepartment/siteRow.jsp");
        
        return mapping.findForward("success");
    }
    
    /**
     * 删除用户站点
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward deleteSite(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UserManager um = ServiceLocator.getUserManager(request);
        Integer userId = ActionUtils.parseInt(request.getParameter("user_id"));
        Integer siteId = ActionUtils.parseInt(request.getParameter("site_id"));
        UserSite us = um.getUserSite(userId, siteId);
        if (us == null) throw new ActionException("userSite.notFound", userId, siteId);

        // 检查当前用户是否可以维护该UserSite
        checkSite(us.getSite(), request);

        um.removeUserSite(us);
        
        return mapping.findForward("success");
    }

    /**
     * 新增用户部门
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newDepartment(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UserManager um = ServiceLocator.getUserManager(request);
        Integer id = ActionUtils.parseInt(request.getParameter("user_id"));
        User u = um.getUser(id);
        if (u == null) throw new ActionException("user.notFound", id);

        if (!isBack(request)) {

            UserDepartment ud = new UserDepartment();
            ud.setUser(u);
            BeanForm userDepartmentForm = (BeanForm) getForm("/insertUserDepartment", request);
            userDepartmentForm.populateToForm(ud);
        }
        
        List siteList = new ArrayList();
        List userSiteList = um.getUserSiteListByUser(u);
        // 过滤当前登录用户未授权管理的Site
        List grantedSiteList = getGrantedSiteList(request);
        for (Iterator itor = userSiteList.iterator(); itor.hasNext(); ) {
            UserSite us = (UserSite) itor.next();
            Site s = us.getSite();
            if (grantedSiteList.contains(s)) {
                siteList.add(s);
            }
        }
        ServiceLocator.getDepartmentManager(request).fillDepartment(siteList, true);
        request.setAttribute("X_SITELIST",  siteList);
        return mapping.findForward("page");
    }

    /**
     * 插入新增的用户部门
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insertDepartment(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UserManager um = ServiceLocator.getUserManager(request);

        BeanForm userDepartmentForm = (BeanForm) form;
        UserDepartment ud = new UserDepartment();
        userDepartmentForm.populateToBean(ud);

        DepartmentManager dm = ServiceLocator.getDepartmentManager(request);
        Department d = ud.getDepartment();
        if (d == null) {
            throw new BackToInputActionException("department.notFound", (Object)null);
        }
        d = dm.getDepartment(d.getId());
        if (d == null) {
            throw new BackToInputActionException("department.notFound", d.getId());
        }
        //检查当前用户是否可以维护该UserDepartment
        checkSite(d.getSite(), request);

        if (um.getUserDepartment(ud.getUser().getId(), ud.getDepartment().getId()) != null) {
            throw new BackToInputActionException("userDepartment.duplicate");
        }
        request.setAttribute("X_OBJECT", um.saveUserDepartment(ud));
        request.setAttribute("X_ROWPAGE", "userSiteDepartment/departmentRow.jsp");
        
        return mapping.findForward("success");
    }

    /**
     * 修改用户部门
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward editDepartment(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!isBack(request)) {
            UserManager um = ServiceLocator.getUserManager(request);
            Integer userId = ActionUtils.parseInt(request.getParameter("user_id"));
            Integer departmentId = ActionUtils.parseInt(request.getParameter("department_id"));
            UserDepartment ud = um.getUserDepartment(userId, departmentId);
            if (ud == null) throw new ActionException("userDepartment.notFound", userId, departmentId);

            // 检查当前用户是否可以维护该UserDepartment
            checkSite(ud.getDepartment().getSite(), request);

            BeanForm userDepartmentForm = (BeanForm) getForm("/updateUserDepartment", request);
            userDepartmentForm.populateToForm(ud);
        }
        return mapping.findForward("page");
    }

    /**
     * 保存修改的用户部门
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward updateDepartment(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UserManager um = ServiceLocator.getUserManager(request);

        Integer userId = ActionUtils.parseInt(request.getParameter("user_id"));
        Integer departmentId = ActionUtils.parseInt(request.getParameter("department_id"));
        UserDepartment ud = um.getUserDepartment(userId, departmentId);
        if (ud == null) throw new ActionException("userDeparmtment.notFound", userId, departmentId);
        // 检查当前用户是否可以维护该UserDepartment
        checkSite(ud.getDepartment().getSite(), request);

        BeanForm userDepartmentForm = (BeanForm) form;
        userDepartmentForm.populateToBean(ud);
        request.setAttribute("X_OBJECT", um.updateUserDepartment(ud));
        request.setAttribute("X_ROWPAGE", "userSiteDepartment/departmentRow.jsp");
        
        return mapping.findForward("success");
    }
    
    /**
     * 删除用户部门
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward deleteDepartment(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UserManager um = ServiceLocator.getUserManager(request);
        Integer userId = ActionUtils.parseInt(request.getParameter("user_id"));
        Integer departmentId = ActionUtils.parseInt(request.getParameter("department_id"));
        UserDepartment ud = um.getUserDepartment(userId, departmentId);
        if (ud == null) throw new ActionException("userDepartment.notFound", userId, departmentId);

        // 检查当前用户是否可以维护该UserDepartment
        checkSite(ud.getDepartment().getSite(), request);

        um.removeUserDepartment(ud);
        
        return mapping.findForward("success");
    }
    
}