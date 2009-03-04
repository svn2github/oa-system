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
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.aof.model.admin.Site;
import com.aof.model.admin.User;
import com.aof.model.admin.query.WebMonitorQueryOrder;
import com.aof.utils.IpAddress;
import com.aof.utils.SessionTempFile;
import com.aof.web.domain.SessionList;
import com.aof.web.domain.SessionListView;
import com.aof.web.struts.action.BaseAction;
import com.aof.web.struts.form.admin.WebMonitorQueryForm;
import com.shcnc.struts.action.ActionUtils;
import com.shcnc.utils.BeanHelper;
import com.shcnc.utils.ExportUtil;
import com.shcnc.utils.Exportable;

/**
 * WebMonitor的Action类
 * @author ych
 * @version 1.0 (2005-10-8)
 */
public class WebMonitorAction extends BaseAction {
    
    /**
     * 查询WebMonitor
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        WebMonitorQueryForm queryForm = (WebMonitorQueryForm) form;

        /**
         * 检查要查询的Site是否被授权，并取得授权Site列表
         */
        List grantedSiteList = getAndCheckGrantedSiteList(request);
        request.setAttribute("X_SITELIST", grantedSiteList);

        Site site = null;
        boolean global = hasGlobalPower(request);
        request.setAttribute("X_GLOBAL", Boolean.valueOf(global));
        request.setAttribute("x_action",global?"listGlobalOnlineUser":"listOnlineUser");
        Integer siteId = ActionUtils.parseInt(queryForm.getSiteId());
        if (siteId == null) {
            if (!global) {
                /**
                 * Site级查询初始应取授权Site列表第一项
                 */
                site = (Site) grantedSiteList.get(0);
            }
        } else {
            site = getAndCheckSite(siteId, request);
        }

        SessionList.lock.getReadLock();
        try {
            List orgList = site == null ? SessionList.getSessionList() : SessionList.getSessionListBySite(site,request);
            
            List list = this.getSessionListView(orgList, 0, Integer.MAX_VALUE, queryForm.getOrder(), queryForm.isDescend());
            //List list= this.getSessionListView(
            //        (site == null ? SessionList.getSessionList() : SessionList.getSessionListBySite(site,request)), 
            //        queryForm.getPageNoAsInt(), queryForm.getPageSizeAsInt(), 
            //        queryForm.getOrder(), queryForm.isDescend());
            
            String exportType = queryForm.getExportType();
            if (StringUtils.isNotEmpty(exportType)) {

                int index = SessionTempFile.createNewTempFile(request);
                String fileName = "onlineUser";
                String suffix = ExportUtil.export(exportType, list, request, new FileOutputStream(SessionTempFile.getTempFile(index, request)), new Exportable() {

                    public void exportHead(List row, HttpServletRequest request) throws Exception {
                        MessageResources messages = getResources(request);
                        if (isGlobal(request)) 
                            row.add(messages.getMessage(getLocale(request), "webMonitor.site"));
                        row.add(messages.getMessage(getLocale(request), "webMonitor.userId"));
                        row.add(messages.getMessage(getLocale(request), "webMonitor.userName"));
                        row.add(messages.getMessage(getLocale(request), "webMonitor.ip"));
                        row.add(messages.getMessage(getLocale(request), "webMonitor.loginTime"));
                        row.add(messages.getMessage(getLocale(request), "webMonitor.lastAccessTime"));
                        row.add(messages.getMessage(getLocale(request), "webMonitor.timeToLive"));
                    }

                    public void exportRow(List row, Object data, HttpServletRequest request) throws Exception {
                        if (isGlobal(request)) 
                            row.add(BeanHelper.getBeanPropertyValue(data, "user.primarySite.name"));
                        row.add(BeanHelper.getBeanPropertyValue(data, "user.loginName"));
                        row.add(BeanHelper.getBeanPropertyValue(data, "user.name"));
                        row.add(BeanHelper.getBeanPropertyValue(data, "ip"));
                        row.add(BeanHelper.getBeanPropertyValue(data, "loginTime"));
                        row.add(BeanHelper.getBeanPropertyValue(data, "accessTime"));
                        row.add(BeanHelper.getBeanPropertyValue(data, "liveMinute"));
                        row.add(BeanHelper.getBeanPropertyValue(data, "liveSecond"));
                    }
                });
                return new ActionForward("download/" + index + "/" + URLEncoder.encode(fileName, "UTF-8") + '.' + suffix, true);
            }
            
            if (queryForm.isFirstInit()) {
                queryForm.init(list.size());
            } else {
                queryForm.init();
            }
            request.setAttribute("X_RESULTLIST", this.getSessionListView(orgList, queryForm.getPageNoAsInt(), queryForm
                    .getPageSizeAsInt(), queryForm.getOrder(), queryForm.isDescend()));
        } finally {
            SessionList.lock.releaseLock();
        }

        return mapping.findForward("page");
    }


    private List getSessionListView(List sessionList, int pageNo, int pageSize, String order, boolean isDescend) {
        List sortedList = getSortedList(sessionList, order);
        List retList = new ArrayList();
        long nowTimeLong = Calendar.getInstance().getTimeInMillis();
        if (!isDescend) {
            for (int index = pageNo * pageSize; index < sortedList.size() && index < (pageNo + 1) * pageSize; index++) {
                try {
                    HttpSession session = (HttpSession) sortedList.get(index);
                    SessionListView view = new SessionListView();
                    view.setSession(session);
                    view.setUser((User) session.getAttribute(BasicAction.LOGIN_USER_KEY));
                    view.setIp((IpAddress) session.getAttribute(BasicAction.LOGIN_IP_KEY));
                    view.setLoginTime((Date) session.getAttribute(BasicAction.LOGIN_TIME_KEY));
                    view.setAccessTime(session.getLastAccessedTime());
                    long secondLeft = session.getMaxInactiveInterval() - (nowTimeLong - session.getLastAccessedTime())
                            / 1000;
                    if (secondLeft > 0) {
                        view.setLiveMinute(secondLeft / 60);
                        view.setLiveSecond(secondLeft % 60);
                    }
                    retList.add(view);
                } catch (Exception e) {
                }
            }
        } else {
            for (int index = sortedList.size() - 1 - pageNo * pageSize; index >= 0
                    && index >= (sortedList.size() - (pageNo + 1) * pageSize); index--) {
                try {
                    HttpSession session = (HttpSession) sortedList.get(index);
                    SessionListView view = new SessionListView();
                    view.setSession(session);
                    view.setUser((User) session.getAttribute(BasicAction.LOGIN_USER_KEY));
                    view.setIp((IpAddress) session.getAttribute(BasicAction.LOGIN_IP_KEY));
                    view.setLoginTime((Date) session.getAttribute(BasicAction.LOGIN_TIME_KEY));
                    view.setAccessTime(session.getLastAccessedTime());
                    long secondLeft = session.getMaxInactiveInterval() - (nowTimeLong - session.getLastAccessedTime())
                            / 1000;
                    if (secondLeft > 0) {
                        view.setLiveMinute(secondLeft / 60);
                        view.setLiveSecond(secondLeft % 60);
                    }
                    retList.add(view);
                } catch (Exception e) {
                }
            }
        }
        return retList;
    }

    private List getSortedList(List sessionList, String order) {
        if (order == null) {
            return sessionList;
        }
        if (order.equals(WebMonitorQueryOrder.SITE.getName())) {
            return getSortedListBySite(sessionList);
        }
        if (order.equals(WebMonitorQueryOrder.USER_ID.getName())) {
            return getSortedListByUserId(sessionList);
        }
        if (order.equals(WebMonitorQueryOrder.USER_NAME.getName())) {
            return getSortedListByUserName(sessionList);
        }
        if (order.equals(WebMonitorQueryOrder.IP.getName())) {
            return getSortedListByIp(sessionList);
        }
        if (order.equals(WebMonitorQueryOrder.LOGIN_TIME.getName())) {
            return getSortedListByLoginTime(sessionList);
        }
        if (order.equals(WebMonitorQueryOrder.ACCESS_TIME.getName())) {
            return getSortedListByAccessTime(sessionList);
        }
        if (order.equals(WebMonitorQueryOrder.LIVE_TIME.getName())) {
            return getSortedListByAccessTime(sessionList);
        }
        return sessionList;
    }

    private List getSortedListBySite(List sessionList) {
        List retList = new ArrayList();
        Iterator itor = sessionList.iterator();
        while (itor.hasNext()) {
            try {
                HttpSession session = (HttpSession) itor.next();
                User user = (User) session.getAttribute(BasicAction.LOGIN_USER_KEY);
                int insertIndex = getSortedIndexBySite(user.getPrimarySite().getName(), retList);
                retList.add(insertIndex, session);
            } catch (Exception e) {
            }
        }
        return retList;
    }

    private int getSortedIndexBySite(String siteName, List sessionList) {
        int index = 0;
        while (index < sessionList.size()) {
            String compareContent = ((User) ((HttpSession) sessionList.get(index))
                    .getAttribute(BasicAction.LOGIN_USER_KEY)).getPrimarySite().getName();
            if (compareContent.compareTo(siteName) > 0)
                break;
            index++;
        }
        return index;
    }

    private List getSortedListByUserId(List sessionList) {
        List retList = new ArrayList();
        Iterator itor = sessionList.iterator();
        while (itor.hasNext()) {
            try {
                HttpSession session = (HttpSession) itor.next();
                User user = (User) session.getAttribute(BasicAction.LOGIN_USER_KEY);
                int insertIndex = getSortedIndexByUserId(user.getLoginName(), retList);
                retList.add(insertIndex, session);
            } catch (Exception e) {
            }
        }
        return retList;
    }

    private int getSortedIndexByUserId(String userId, List sessionList) {
        int index = 0;
        while (index < sessionList.size()) {
            String compareContent = ((User) ((HttpSession) sessionList.get(index))
                    .getAttribute(BasicAction.LOGIN_USER_KEY)).getLoginName();
            if (compareContent.compareTo(userId) > 0)
                break;
            index++;
        }
        return index;
    }

    private List getSortedListByUserName(List sessionList) {
        List retList = new ArrayList();
        Iterator itor = sessionList.iterator();
        while (itor.hasNext()) {
            try {
                HttpSession session = (HttpSession) itor.next();
                User user = (User) session.getAttribute(BasicAction.LOGIN_USER_KEY);
                int insertIndex = getSortedIndexByUserName(user.getName(), retList);
                retList.add(insertIndex, session);
            } catch (Exception e) {
            }
        }
        return retList;
    }

    private int getSortedIndexByUserName(String userName, List sessionList) {
        int index = 0;
        while (index < sessionList.size()) {
            String compareContent = ((User) ((HttpSession) sessionList.get(index))
                    .getAttribute(BasicAction.LOGIN_USER_KEY)).getName();
            if (compareContent.compareTo(userName) > 0)
                break;
            index++;
        }
        return index;
    }
    
    private List getSortedListByIp(List sessionList) {
        List retList = new ArrayList();
        Iterator itor = sessionList.iterator();
        while (itor.hasNext()) {
            try {
                HttpSession session = (HttpSession) itor.next();
                IpAddress ip = (IpAddress) session.getAttribute(BasicAction.LOGIN_IP_KEY);
                int insertIndex = getSortedIndexByIp(ip, retList);
                retList.add(insertIndex, session);
            } catch (Exception e) {
            }
        }
        return retList;
    }

    private int getSortedIndexByIp(IpAddress ip, List sessionList) {
        int index = 0;
        while (index < sessionList.size()) {
            IpAddress compareIp= (IpAddress) ((HttpSession) sessionList.get(index))
                    .getAttribute(BasicAction.LOGIN_IP_KEY);
            if (compareIp.compareTo(ip) > 0)
                break;
            index++;
        }
        return index;
    }
    
    private List getSortedListByLoginTime(List sessionList) {
        List retList = new ArrayList();
        Iterator itor = sessionList.iterator();
        while (itor.hasNext()) {
            try {
                HttpSession session = (HttpSession) itor.next();
                Date loginTime = (Date) session.getAttribute(BasicAction.LOGIN_TIME_KEY);
                int insertIndex = getSortedIndexByLoginTime(loginTime, retList);
                retList.add(insertIndex, session);
            } catch (Exception e) {
            }
        }
        return retList;
    }

    private int getSortedIndexByLoginTime(Date loginTime, List sessionList) {
        int index = 0;
        while (index < sessionList.size()) {
            Date compareDate = (Date) ((HttpSession) sessionList.get(index))
                    .getAttribute(BasicAction.LOGIN_TIME_KEY);
            if (compareDate.compareTo(loginTime) > 0)
                break;
            index++;
        }
        return index;
    }
    
    private List getSortedListByAccessTime(List sessionList) {
        List retList = new ArrayList();
        Iterator itor = sessionList.iterator();
        while (itor.hasNext()) {
            try {
                HttpSession session = (HttpSession) itor.next();
                Date accessTime = new Date(session.getLastAccessedTime());
                int insertIndex = getSortedIndexByAccessTime(accessTime, retList);
                retList.add(insertIndex, session);
            } catch (Exception e) {
            }
        }
        return retList;
    }

    private int getSortedIndexByAccessTime(Date accessTime, List sessionList) {
        int index = 0;
        while (index < sessionList.size()) {
            Date compareDate = new Date(((HttpSession) sessionList.get(index)).getLastAccessedTime());
            if (compareDate.compareTo(accessTime) > 0)
                break;
            index++;
        }
        return index;
    }
}
