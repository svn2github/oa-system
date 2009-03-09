/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.web.struts.action.admin;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aof.model.admin.Department;
import com.aof.model.admin.Role;
import com.aof.model.admin.Site;
import com.aof.model.admin.User;
import com.aof.model.admin.UserDepartment;
import com.aof.model.admin.UserRole;
import com.aof.model.metadata.RoleType;
import com.aof.service.admin.RoleManager;
import com.aof.service.admin.UserManager;
import com.aof.web.struts.action.BaseAction;
import com.aof.web.struts.action.ServiceLocator;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.action.ActionUtils;
import com.shcnc.struts.action.BackToInputActionException;
import com.shcnc.struts.form.BeanForm;

/**
 * 操作UserRole的Action
 * @author nicebean
 * @version 1.0 (2005-11-15)
 */
public class UserRoleAction extends BaseAction {
    
    /**
     * 显示属于User的Role
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
        // 检查当前用户是否可以维护该User(该User的PrimarySite在的当前用户的授权Site中)
        //checkSite(u.getPrimarySite(), request);

        request.setAttribute("X_USER", u);

        List userDepartmentList = um.getUserDepartmentListByUser(u);
        List userRoleList = um.getUserRoleListByUser(u);
        if (hasGlobalPower(request)) {
            request.setAttribute("X_GLOBAL", Boolean.TRUE);
        } else {
            request.setAttribute("X_GLOBAL", Boolean.FALSE);
            // 过滤当前登录用户未授权管理的Site
            List grantedSiteList = getGrantedSiteList(request);
            for (Iterator itor = userDepartmentList.iterator(); itor.hasNext(); ) {
                UserDepartment ud = (UserDepartment) itor.next();
                Site s = ud.getDepartment().getSite();
                if (!grantedSiteList.contains(s)) {
                    itor.remove();
                }
            }
            for (Iterator itor = userRoleList.iterator(); itor.hasNext(); ) {
                UserRole ur = (UserRole) itor.next();
                RoleType rt = ur.getRole().getType();
                if (RoleType.GLOBAL_ADMIN.equals(rt)) {
                    itor.remove();
                } else if (RoleType.SITE_ADMIN.equals(rt)) {
                    Site grantedSite = ur.getGrantedSite();
                    if (grantedSite == null || !grantedSiteList.contains(grantedSite)) {
                        itor.remove();
                    }
                } else if (RoleType.ENDUSER.equals(rt)){
                    Department grantedDepartment = ur.getGrantedDepartment();
                    Site grantedSite = ur.getGrantedSite();
                    if (grantedDepartment != null) {
                        grantedSite = grantedDepartment.getSite();
                    }
                    if (grantedSite == null || !grantedSiteList.contains(grantedSite)) {
                        itor.remove();
                    }
                }
            }
        }

        request.setAttribute("X_DEPARTMENTLIST", userDepartmentList);
        request.setAttribute("X_RESULTLIST", userRoleList);
        return mapping.findForward("page");
    }

    /**
     * 新增UserRole
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newObject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        if (!isBack(request)) {
            UserManager um = ServiceLocator.getUserManager(request);

            Integer id = ActionUtils.parseInt(request.getParameter("user_id"));
            User u = um.getUser(id);
            if (u == null) throw new ActionException("user.notFound", id);
            // 检查当前用户是否可以维护该User(该User的PrimarySite在的当前用户的授权Site中)
            checkSite(u.getPrimarySite(), request);

            UserRole ur = new UserRole();
            ur.setUser(u);
            BeanForm userRoleForm = (BeanForm) getForm("/insertUserRole", request);
            userRoleForm.populateToForm(ur);
        }
        RoleManager rm = ServiceLocator.getRoleManager(request);
        List roleList = rm.getAllRoleList();
        // 只有Global Admin才可以赋予用户Global的Role
        if (!hasGlobalPower(request)) {
            for (Iterator itor = roleList.iterator(); itor.hasNext(); ) {
                Role r = (Role) itor.next();
                if (RoleType.GLOBAL_ADMIN.equals(r.getType())) itor.remove();
            }
        }
        request.setAttribute("X_ROLELIST", roleList);

        List siteList = getGrantedSiteDeparmentList(request);
        request.setAttribute("X_SITELIST", siteList);
        return mapping.findForward("page");
    }

    /**
     * 插入新增的UserRole
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UserManager um = ServiceLocator.getUserManager(request);

        Integer id = ActionUtils.parseInt(request.getParameter("user_id"));
        User u = um.getUser(id);
        if (u == null) throw new ActionException("user.notFound", id);
        // 检查当前用户是否可以维护该User(该User的PrimarySite在的当前用户的授权Site中)
        checkSite(u.getPrimarySite(), request);

        BeanForm userRoleForm = (BeanForm) form;
        UserRole ur = new UserRole();
        userRoleForm.populateToBean(ur);
        Iterator itor = um.getUserRoleListByUser(ur.getUser()).iterator();
        while (itor.hasNext()) {
            UserRole oldUr = (UserRole) itor.next();
            Role r = oldUr.getRole();
            if (!r.equals(ur.getRole())) continue;
            if (RoleType.GLOBAL_ADMIN.equals(r.getType()) || RoleType.COMMON_GLOBAL_LEVEL.equals(r.getType())) {
                throw new BackToInputActionException("userRole.duplicate");
            } else if (RoleType.SITE_ADMIN.equals(r.getType())) {
                Site s = oldUr.getGrantedSite();
                if (s.equals(ur.getGrantedSite())) {
                    throw new BackToInputActionException("userRole.duplicate");
                }
            } else if (RoleType.ENDUSER.equals(r.getType())) {
                Department d = oldUr.getGrantedDepartment();
                if (d.equals(ur.getGrantedDepartment())) {
                    throw new BackToInputActionException("userRole.duplicate");
                }
            }
        }

        RoleManager rm = ServiceLocator.getRoleManager(request);
        Role r = rm.getRole(ur.getRole().getId());
        RoleType rt = r.getType();
        if (RoleType.GLOBAL_ADMIN.equals(rt)) {
            if (!hasGlobalPower(request)) {
                throw new ActionException("userRole.cannotAssignGlobalRole");
            }
            ur.setGrantedSite(null);
            ur.setGrantedDepartment(null);
        } else if (RoleType.SITE_ADMIN.equals(rt)) {
            ur.setGrantedDepartment(null);
        } else if (RoleType.COMMON_GLOBAL_LEVEL.equals(rt)) {
            ur.setGrantedSite(null);
            ur.setGrantedDepartment(null);
        }
        request.setAttribute("X_OBJECT", um.saveUserRole(ur));
        request.setAttribute("X_ROWPAGE", "userRole/row.jsp");
        
        return mapping.findForward("success");
    }

    /**
     * 修改UserRole
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!isBack(request)) {
            UserManager um = ServiceLocator.getUserManager(request);
            Integer id = ActionUtils.parseInt(request.getParameter("id"));
            UserRole ur = um.getUserRole(id);
            if (ur == null) throw new ActionException("userRole.notFound", id);

            // 检查当前用户是否可以维护该User(该User的PrimarySite在的当前用户的授权Site中)
            checkSite(ur.getUser().getPrimarySite(), request);

            if (RoleType.GLOBAL_ADMIN.equals(ur.getRole().getType())) {
                if (!hasGlobalPower(request)) {
                    throw new ActionException("userRole.cannotMaintainGlobalRole");
                }
            }

            BeanForm userRoleForm = (BeanForm) getForm("/updateUserRole", request);
            userRoleForm.populateToForm(ur);
        }
        RoleManager rm = ServiceLocator.getRoleManager(request);
        List roleList = rm.getAllRoleList();
        // 只有Global Admin才可以赋予用户Global的Role
        if (!hasGlobalPower(request)) {
            for (Iterator itor = roleList.iterator(); itor.hasNext(); ) {
                Role r = (Role) itor.next();
                if (RoleType.GLOBAL_ADMIN.equals(r.getType())) itor.remove();
            }
        }
        request.setAttribute("X_ROLELIST", roleList);
        List siteList = getGrantedSiteDeparmentList(request);
        request.setAttribute("X_SITELIST", siteList);
        return mapping.findForward("page");
    }

    /**
     * 保存修改的UserRole
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UserManager um = ServiceLocator.getUserManager(request);

        Integer userId = ActionUtils.parseInt(request.getParameter("user_id"));
        User u = um.getUser(userId);
        if (u == null) throw new ActionException("user.notFound", userId);
        // 检查当前用户是否可以维护该User(该User的PrimarySite在的当前用户的授权Site中)
        checkSite(u.getPrimarySite(), request);

        BeanForm userRoleForm = (BeanForm) form;
        UserRole ur = new UserRole();
        userRoleForm.populateToBean(ur);
        Integer id = ur.getId();
        Iterator itor = um.getUserRoleListByUser(ur.getUser()).iterator();
        while (itor.hasNext()) {
            UserRole oldUr = (UserRole) itor.next();
            if (oldUr.getId().equals(id)) continue;
            Role r = oldUr.getRole();
            if (!r.equals(ur.getRole())) continue;
            if (RoleType.GLOBAL_ADMIN.equals(r.getType()) || RoleType.COMMON_GLOBAL_LEVEL.equals(r.getType())) {
                throw new BackToInputActionException("userRole.duplicate");
            } else if (RoleType.SITE_ADMIN.equals(r.getType())) {
                Site s = oldUr.getGrantedSite();
                if (s.equals(ur.getGrantedSite())) {
                    throw new BackToInputActionException("userRole.duplicate");
                }
            } else if (RoleType.ENDUSER.equals(r.getType())) {
                Department d = oldUr.getGrantedDepartment();
                if (d.equals(ur.getGrantedDepartment())) {
                    throw new BackToInputActionException("userRole.duplicate");
                }
            }
        }

        RoleManager rm = ServiceLocator.getRoleManager(request);
        Role r = rm.getRole(ur.getRole().getId());
        RoleType rt = r.getType();
        if (RoleType.GLOBAL_ADMIN.equals(rt)) {
            if (!hasGlobalPower(request)) {
                throw new ActionException("userRole.cannotAssignGlobalRole");
            }
            ur.setGrantedSite(null);
            ur.setGrantedDepartment(null);
        } else if (RoleType.SITE_ADMIN.equals(rt)) {
            ur.setGrantedDepartment(null);
        } else if (RoleType.COMMON_GLOBAL_LEVEL.equals(rt)) {
            ur.setGrantedSite(null);
            ur.setGrantedDepartment(null);
        }
        request.setAttribute("X_OBJECT", um.saveUserRole(ur));
        request.setAttribute("X_ROWPAGE", "userRole/row.jsp");
        
        return mapping.findForward("success");
    }
    
    /**
     * 删除UserRole
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UserManager um = ServiceLocator.getUserManager(request);
        Integer id = ActionUtils.parseInt(request.getParameter("id"));
        UserRole ur = um.getUserRole(id);
        if (ur == null) throw new ActionException("userRole.notFound", id);
        
        // 检查当前用户是否可以维护该User(该User的PrimarySite在的当前用户的授权Site中)
        checkSite(ur.getUser().getPrimarySite(), request);

        if (RoleType.GLOBAL_ADMIN.equals(ur.getRole().getType())) {
            if (!hasGlobalPower(request)) {
                throw new ActionException("userRole.cannotDeleteGlobalRole");
            }
        }
        
        um.removeUserRole(ur);
        
        return mapping.findForward("success");
    }
    

}