/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.web.struts.action.admin;

import java.io.FileOutputStream;
import java.net.URLEncoder;
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
import com.aof.model.admin.query.FunctionQueryCondition;
import com.aof.model.admin.query.FunctionQueryOrder;
import com.aof.service.admin.FunctionManager;
import com.aof.utils.SessionTempFile;
import com.aof.web.struts.action.BaseAction;
import com.aof.web.struts.action.ServiceLocator;
import com.aof.web.struts.form.admin.FunctionQueryForm;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.action.ActionUtils;
import com.shcnc.struts.form.BeanForm;
import com.shcnc.utils.BeanUtils;
import com.shcnc.utils.ExportUtil;
import com.shcnc.utils.Exportable;

/**
 * 操作Function的Action
 * @author nicebean
 * @version 1.0 (2005-11-15)
 */
public class FunctionAction extends BaseAction {
    
    /**
     * 查询Function
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	    FunctionManager fm = ServiceLocator.getFunctionManager(request);

        FunctionQueryForm queryForm = (FunctionQueryForm) form;

        Map conditions = constructQueryMap(queryForm);

        String exportType = queryForm.getExportType();
        if (exportType != null && exportType.length() > 0) {
            List data = fm.getFunctionList(conditions, 0, -1, FunctionQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend());
            int index = SessionTempFile.createNewTempFile(request);
            String fileName = "function";
            String suffix = ExportUtil.export(exportType, data, request, new FileOutputStream(SessionTempFile.getTempFile(index, request)), new Exportable() {

                        public void exportHead(List row, HttpServletRequest request) throws Exception {
                            MessageResources messages = getResources(request);
                            row.add(messages.getMessage(getLocale(request), "function.id"));
                            row.add(messages.getMessage(getLocale(request), "function.name"));
                            row.add(messages.getMessage(getLocale(request), "function.description"));
                        }

                        public void exportRow(List row, Object data, HttpServletRequest request) throws Exception {
                            row.add(BeanUtils.getProperty(data, "id"));
                            row.add(BeanUtils.getProperty(data, "name"));
                            row.add(BeanUtils.getProperty(data, "description"));
                        }
                    });
            return new ActionForward("download/" + index + "/" + URLEncoder.encode(fileName, "UTF-8") + '.' + suffix, true);
        }

        if (queryForm.isFirstInit()) {
            queryForm.init(fm.getFunctionListCount(conditions));
        } else {
            queryForm.init();
        }

        request.setAttribute("X_RESULTLIST", fm.getFunctionList(conditions, queryForm.getPageNoAsInt(), queryForm.getPageSizeAsInt(), FunctionQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend()));
        return mapping.findForward("page");
    }
    
    private Map constructQueryMap(FunctionQueryForm queryForm) {
        Map conditions = new HashMap();

        String name = queryForm.getName();
        if (name != null) {
            name = name.trim();
            if (name.length() == 0) name = null;
        }
        if (name != null) conditions.put(FunctionQueryCondition.NAME_LIKE, name);

        return conditions;
    }
    
    /**
     * 修改 Function
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
            FunctionManager fm = ServiceLocator.getFunctionManager(request);
            Function f = fm.getFunction(id);
            if (f == null) throw new ActionException("function.notFound", id);
            BeanForm functionForm = (BeanForm) getForm("/updateFunction", request);
            functionForm.populateToForm(f);
        }
        return mapping.findForward("page");
    }

    /**
     * 保存修改的 Function
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        BeanForm functionForm = (BeanForm) form;
        Function f = new Function();
        functionForm.populateToBean(f);
        FunctionManager fm = ServiceLocator.getFunctionManager(request);
        request.setAttribute("X_OBJECT", fm.saveFunction(f));
        request.setAttribute("X_ROWPAGE", "function/row.jsp");
        
        return mapping.findForward("success");
    }
}