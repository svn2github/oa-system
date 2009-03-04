/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.web.struts.action.admin;

import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;


import com.aof.model.admin.query.SystemLogQueryCondition;
import com.aof.model.admin.query.SystemLogQueryOrder;
import com.aof.service.admin.SystemLogManager;
import com.aof.utils.SessionTempFile;
import com.aof.web.struts.action.ActionUtils;
import com.aof.web.struts.action.BaseAction;
import com.aof.web.struts.action.ServiceLocator;

import com.aof.web.struts.form.admin.SystemLogQueryForm;
import com.shcnc.utils.BeanUtils;
import com.shcnc.utils.ExportUtil;
import com.shcnc.utils.Exportable;

/**
 * @author ych
 * @version 1.0 (Feb 20, 2006)
 */
public class SystemLogAction extends BaseAction {
    
    private Map constructQueryMap(SystemLogQueryForm queryForm) {
        Map conditions = new HashMap();

        String siteId = queryForm.getSiteId();
        if (siteId!=null) {
            siteId=siteId.trim();
            if (siteId.length()==0) siteId=null;
        }
        if (siteId != null) conditions.put(SystemLogQueryCondition.SITE_ID_EQ, siteId);
        
        String userId = queryForm.getUserId();
        if (userId!=null) {
            userId=userId.trim();
            if (userId.length()==0) userId=null;
        }
        if (userId != null) conditions.put(SystemLogQueryCondition.USER_ID_EQ, userId);
        

        String userName = queryForm.getUserName();
        if (userName!=null) {
            userName=userName.trim();
            if (userName.length()==0) userName=null;
        }
        if (userName != null) conditions.put(SystemLogQueryCondition.USER_NAME_LIKE, userName);
        
        String target = queryForm.getTargetObject();
        if (target != null && target.trim().length() != 0) {
            conditions.put(SystemLogQueryCondition.TARGET_EQ, target);
        }
        
        if (queryForm.getActionDateFrom()!=null && queryForm.getActionDateFrom().trim().length()!=0) {
            Date actionTimeFrom=ActionUtils.getDateFromDisplayDate(queryForm.getActionDateFrom());
            if (actionTimeFrom!=null) {
                conditions.put(SystemLogQueryCondition.ACTION_TIME_GT,actionTimeFrom);
            }
        }
        
        if (queryForm.getActionDateTo()!=null && queryForm.getActionDateTo().trim().length()!=0) {
            Date actionTimeTo=ActionUtils.getDateFromDisplayDate(queryForm.getActionDateTo());
            if (actionTimeTo!=null) {
                conditions.put(SystemLogQueryCondition.ACTION_TIME_LT,actionTimeTo);
            }
        }
        
        return conditions;
    }
    
    /**
     * ²éÑ¯SystemLog
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        List siteList = this.getAndCheckGrantedSiteList(request);
        
        SystemLogManager cm = ServiceLocator.getSystemLogManager(request);
        
        SystemLogQueryForm queryForm = (SystemLogQueryForm) form;
        Map conditions = constructQueryMap(queryForm);
        
        if (request.getMethod().equals("POST")) {
            conditions = constructQueryMap(queryForm);
            
            if(queryForm.isFirstInit())
            {
                queryForm.init(cm.getSystemLogListCount(conditions));
            }
            else
            {
                queryForm.init();
            }
            int pageNo=queryForm.getPageNoAsInt();
            int pageSize=queryForm.getPageSizeAsInt();
            request.setAttribute("X_RESULTLIST", cm.getSystemLogList(conditions, pageNo, pageSize, SystemLogQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend()));
        } else {
            if(queryForm.isFirstInit())
            {
                queryForm.init(0);
            }
            else
            {
                queryForm.init();
            }
        }
        
        String exportType = queryForm.getExportType();
        if (exportType != null && exportType.length() > 0) {
            List data;
            
            data =cm.getSystemLogList(conditions, 0, -1, SystemLogQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend());
            
            int index = SessionTempFile.createNewTempFile(request);
            String fileName = "systemLog";
            String suffix = ExportUtil.export(exportType, data, request, new FileOutputStream(SessionTempFile.getTempFile(index, request)), new Exportable() {

                        public void exportHead(List row, HttpServletRequest request) throws Exception {
                            MessageResources messages = getResources(request);
                            row.add(messages.getMessage(getLocale(request), "systemLog.tablehead.site"));
                            row.add(messages.getMessage(getLocale(request), "systemLog.tablehead.userId"));
                            row.add(messages.getMessage(getLocale(request), "systemLog.tablehead.userName"));
                            row.add(messages.getMessage(getLocale(request), "systemLog.tablehead.updateTime"));
                            row.add(messages.getMessage(getLocale(request), "systemLog.tablehead.object"));
                            row.add(messages.getMessage(getLocale(request), "systemLog.tablehead.objectId"));
                            row.add(messages.getMessage(getLocale(request), "systemLog.tablehead.action"));
                            row.add(messages.getMessage(getLocale(request), "systemLog.tablehead.keyField"));
                        }

                        public void exportRow(List row, Object data, HttpServletRequest request) throws Exception {
                            row.add(BeanUtils.getProperty(data, "site.name"));
                            row.add(BeanUtils.getProperty(data, "user.loginName"));
                            row.add(BeanUtils.getProperty(data, "user.name"));
                            row.add(BeanUtils.getProperty(data, "actionTime"));
                            row.add(BeanUtils.getProperty(data, "target"));
                            row.add(BeanUtils.getProperty(data, "targetId"));
                            row.add(BeanUtils.getProperty(data, "action"));
                            row.add(BeanUtils.getProperty(data, "content"));
                        }
                    });
            return new ActionForward("download/" + index + "/" + URLEncoder.encode(fileName, "UTF-8") + '.' + suffix, true);
        }
        
        request.setAttribute("X_SITELIST", siteList);
        
        return mapping.findForward("page");
    }
    
}
