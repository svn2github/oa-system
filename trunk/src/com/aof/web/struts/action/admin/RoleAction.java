/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.web.struts.action.admin;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.aof.model.admin.Function;
import com.aof.model.admin.Role;
import com.aof.model.admin.query.RoleQueryCondition;
import com.aof.model.admin.query.RoleQueryOrder;
import com.aof.model.metadata.MetadataDetailEnum;
import com.aof.model.metadata.RoleType;
import com.aof.service.admin.FunctionManager;
import com.aof.service.admin.RoleManager;
import com.aof.utils.SessionTempFile;
import com.aof.web.struts.action.BaseAction;
import com.aof.web.struts.action.ServiceLocator;
import com.aof.web.struts.form.admin.RoleQueryForm;
import com.shcnc.hibernate.PersistentEnum;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.action.ActionUtils;
import com.shcnc.struts.form.BeanForm;
import com.shcnc.utils.BeanUtils;
import com.shcnc.utils.ExportUtil;
import com.shcnc.utils.Exportable;

/**
 * 操作Role的Action
 * @author nicebean
 * @version 1.0 (2005-11-15)
 */
public class RoleAction extends BaseAction {
    /**
     * 查询 Role
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	    RoleManager rm =ServiceLocator.getRoleManager(request);
        
        RoleQueryForm queryForm = (RoleQueryForm) form;
        List l = PersistentEnum.getEnumList(RoleType.class);
        l.add(0, MetadataDetailEnum.DUMMY);
        queryForm.getType().setList(l);

        Map conditions = constructQueryMap(queryForm);
        
        String exportType = queryForm.getExportType();
        if (exportType != null && exportType.length() > 0) {
            List data = rm.getRoleList(conditions, 0, -1, RoleQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend());
            int index = SessionTempFile.createNewTempFile(request);
            String fileName = "role";
            String suffix = ExportUtil.export(exportType, data, request, new FileOutputStream(SessionTempFile.getTempFile(index, request)), new Exportable() {

                        public void exportHead(List row, HttpServletRequest request) throws Exception {
                            MessageResources messages = getResources(request);
                            row.add(messages.getMessage(getLocale(request), "role.name"));
                            row.add(messages.getMessage(getLocale(request), "role.type"));
                        }

                        public void exportRow(List row, Object data, HttpServletRequest request) throws Exception {
                            row.add(BeanUtils.getProperty(data, "name"));
                            String locale = getCurrentUser(request).getLocale();
                            if ("en".equals(locale)) {
                                row.add(BeanUtils.getProperty(data, "type.engShortDescription"));
                            } else {
                                row.add(BeanUtils.getProperty(data, "type.chnShortDescription"));
                            }
                        }
                    });
            return new ActionForward("download/" + index + "/" + URLEncoder.encode(fileName, "UTF-8") + '.' + suffix, true);
        }

        if (queryForm.isFirstInit()) {
            queryForm.init(rm.getRoleListCount(conditions));
        } else {
            queryForm.init();
        }

        request.setAttribute("X_RESULTLIST", rm.getRoleList(conditions, queryForm.getPageNoAsInt(), queryForm.getPageSizeAsInt(), RoleQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend()));
        return mapping.findForward("page");
    }

    private Map constructQueryMap(RoleQueryForm queryForm) {
        Map conditions = new HashMap();

        Integer id = ActionUtils.parseInt(queryForm.getId());
        if (id != null) conditions.put(RoleQueryCondition.ID_EQ, id);

        String name = queryForm.getName();
        if (name != null) {
            name = name.trim();
            if (name.length() == 0) name = null;
        }
        if (name != null) conditions.put(RoleQueryCondition.NAME_LIKE, name);
        
        if (!queryForm.getType().isEmpty()) {
            Integer type = ActionUtils.parseInt(queryForm.getType().getValue());
            if (type != null && type.intValue() != MetadataDetailEnum.DUMMY_KEY) conditions.put(RoleQueryCondition.TYPE_EQ, type);
        }

        return conditions;
    }

    /**
     * 新增 Role
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward newObject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!isBack(request)) {
            Role r = new Role();
            BeanForm roleForm = (BeanForm) getForm("/insertRole", request);
            roleForm.populateToForm(r);
        }
        request.setAttribute("X_ROLETYPELIST", PersistentEnum.getEnumList(RoleType.class));
        return mapping.findForward("page");
    }

    /**
     * 插入新增的 Role
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward insert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        RoleManager rm = ServiceLocator.getRoleManager(request);
        BeanForm roleForm = (BeanForm) form;
        Role r = new Role();
        roleForm.populateToBean(r);
        request.setAttribute("X_OBJECT", rm.saveRole(r));
        request.setAttribute("X_ROWPAGE", "role/row.jsp");
        
        return mapping.findForward("success");
    }

    /**
     * 修改Role
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
            RoleManager rm = ServiceLocator.getRoleManager(request);
            Role r = rm.getRole(id);
            if (r == null) throw new ActionException("role.notFound", id);
            BeanForm roleForm = (BeanForm) getForm("/updateRole", request);
            roleForm.populateToForm(r);
        }
        request.setAttribute("X_ROLETYPELIST", PersistentEnum.getEnumList(RoleType.class));
        return mapping.findForward("page");
    }

    /**
     * 保存修改的Role
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        RoleManager rm =ServiceLocator.getRoleManager(request);
        BeanForm roleForm = (BeanForm) form;
        Integer id = ActionUtils.parseInt((String)roleForm.get("id"));
        Role r = rm.getRole(id);
        if (r == null) throw new ActionException("role.notFound", id);
        roleForm.populateToBean(r);
        request.setAttribute("X_OBJECT", rm.saveRole(r));
        request.setAttribute("X_ROWPAGE", "role/row.jsp");
        
        return mapping.findForward("success");
    }

    /**
     * 维护赋予Role的Function
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward grantFunction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        RoleManager rm = ServiceLocator.getRoleManager(request);
        FunctionManager fm = ServiceLocator.getFunctionManager(request);

        Integer id = ActionUtils.parseInt(request.getParameter("id"));
        Role r = rm.getRole(id);
        if (r == null) throw new ActionException("role.notFound", id);

        if ("POST".equals(request.getMethod())) {
            String[] strFunctionIds = request.getParameterValues("granted");
            List functionList = new ArrayList();
            if (strFunctionIds != null) {
                for (int i = 0; i < strFunctionIds.length; i++) {
                    Integer functionId = ActionUtils.parseInt(strFunctionIds[i]);
                    Function f = fm.getFunction(functionId);
                    if (f != null) functionList.add(f);
                }
            }
            rm.saveFunctionListForRole(r, functionList);
        }
        request.setAttribute("X_OBJECT", r);

        List grantedFunction = rm.getFunctionListByRole(r);

        List availableFunction = fm.getAllFunction();
        availableFunction.removeAll(grantedFunction);
        request.setAttribute("X_GRANTEDFUNCTION", grantedFunction);
        request.setAttribute("X_AVAILABLEFUNCTION", availableFunction);
        return mapping.findForward("page");
    }
}