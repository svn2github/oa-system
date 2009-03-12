/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.web.struts.action.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import net.sourceforge.model.admin.Menu;
import net.sourceforge.service.admin.FunctionManager;
import net.sourceforge.service.admin.MenuManager;
import net.sourceforge.web.struts.action.BaseAction;
import net.sourceforge.web.struts.action.ServiceLocator;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.action.ActionUtils;
import com.shcnc.struts.form.BeanForm;

/**
 * 操作Menu的Action
 * @author nicebean
 * @version 1.0 (2005-11-14)
 */
public class MenuAction extends BaseAction {

    /**
     * 显示Menu
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionMapping
     * @throws Exception
     */
    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        MenuManager mm =ServiceLocator.getMenuManager(request);
        request.setAttribute("X_MENUXML", mm.getMenuXml(getLocale(request)));
        return mapping.findForward("page");
    }

    /**
     * 新增 Menu
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionMapping
     * @throws Exception
     */
    public ActionForward newObject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        MenuManager mm = ServiceLocator.getMenuManager(request);
        FunctionManager fm = ServiceLocator.getFunctionManager(request);
        Integer parentId = ActionUtils.parseInt(request.getParameter("parentId"));
        Menu parent = null;
        if (parentId != null) {
            parent = mm.getMenu(parentId);
            if (parent == null) throw new ActionException("menu.notFound", parentId);
            request.setAttribute("X_PARENT", parent);
        }
        if (!isBack(request)) {
            Menu m = new Menu();
            m.setParentMenu(parent);
            BeanForm menuForm = (BeanForm) getForm("/insertMenu", request);
            menuForm.populateToForm(m);
        }
        List functionList = fm.getAllFunction();
        request.setAttribute("X_FUNCTIONLIST", functionList);
        return mapping.findForward("page");
    }

    /**
     * 插入新增的Menu
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionMapping
     * @throws Exception
     */
    public ActionForward insert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        MenuManager mm = ServiceLocator.getMenuManager(request);
        BeanForm menuForm = (BeanForm) form;
        Menu m = new Menu();
        menuForm.populateToBean(m);
        request.setAttribute("X_OBJECT", mm.saveMenu(m));
        
        return mapping.findForward("success");
    }

    /**
     * 更新Menu
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionMapping
     * @throws Exception
     */
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        FunctionManager fm = ServiceLocator.getFunctionManager(request);
        if (!isBack(request)) {
            Integer id = ActionUtils.parseInt(request.getParameter("id"));
            MenuManager mm = ServiceLocator.getMenuManager(request);
            Menu m = mm.getMenu(id);
            if (m == null) throw new ActionException("menu.notFound", id);
            BeanForm menuForm = (BeanForm) getForm("/updateMenu", request);
            menuForm.populateToForm(m);
        }
        List functionList = fm.getAllFunction();
        request.setAttribute("X_FUNCTIONLIST", functionList);
        return mapping.findForward("page");
    }

    /**
     * 保存更新的Menu
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionMapping
     * @throws Exception
     */
    public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        MenuManager mm = ServiceLocator.getMenuManager(request);
        BeanForm menuForm = (BeanForm) form;
        Integer id = ActionUtils.parseInt((String)menuForm.get("id"));
        Menu m = mm.getMenu(id);
        if (m == null) throw new ActionException("menu.notFound", id);
        menuForm.populateToBean(m);
        request.setAttribute("X_OBJECT", mm.saveMenu(m));
        
        return mapping.findForward("success");
    }
      
    /**
     * 删除Menu
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionMapping
     * @throws Exception
     */
    public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer id = ActionUtils.parseInt(request.getParameter("id"));
        MenuManager mm = ServiceLocator.getMenuManager(request);
        mm.removeMenu(id);
        return mapping.findForward("success");
    }

}