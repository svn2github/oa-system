/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.web.struts.action.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import net.sourceforge.model.admin.DataTransferParameter;
import net.sourceforge.model.admin.GlobalMailReminder;
import net.sourceforge.model.admin.GlobalParameter;
import net.sourceforge.model.admin.Site;
import net.sourceforge.model.admin.SiteMailReminder;
import net.sourceforge.model.metadata.ExportFileType;
import net.sourceforge.model.metadata.GlobalMailReminderType;
import net.sourceforge.model.metadata.MetadataType;
import net.sourceforge.model.metadata.SiteMailReminderType;
import net.sourceforge.model.metadata.YesNo;
import net.sourceforge.service.admin.DataTransferManager;
import net.sourceforge.service.admin.ParameterManager;
import net.sourceforge.service.admin.SiteManager;
import net.sourceforge.web.struts.action.ActionUtils;
import net.sourceforge.web.struts.action.BaseAction;
import net.sourceforge.web.struts.action.ServiceLocator;
import com.shcnc.hibernate.PersistentEnum;
import com.shcnc.struts.form.BeanForm;
import com.shcnc.struts.form.converter.Converter;
import com.shcnc.struts.form.converter.ConverterLocator;
import com.shcnc.struts.form.converterLocators.DefaultConvertLocator;
import com.shcnc.struts.form.converters.DateConverter;

/**
 * GlobalParameter的Action类
 * @author ych
 * @version 1.0 (Dec 14, 2005)
 */
public class ParameterAction extends BaseAction {
	
    protected static DateConverter timeConverter;

    static {
        timeConverter = new DateConverter();
        timeConverter.setFormat("HH:mm");
    }
    
    private static ConverterLocator dataTransferConverterLocator = new ConverterLocator() {

        public Converter getConverter(Class clazz) {
            return DefaultConvertLocator.getInstance().getConverter(clazz);
        }

        public Converter getConverter(String convertId) {
            return null;
        }

        public Converter getConverter(Class clazz, String propName) {
            if (propName.endsWith("Time"))
                return timeConverter;
            else
                return null;
        }

    };
    
 	
	/**
     * 编辑 GlobalParameter
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
	 */
    public ActionForward editGlobal(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ParameterManager gm =ServiceLocator.getParameterManager(request);
        GlobalParameter globalParameter = gm.getGlobalParameter();
		request.setAttribute("x_globalParameter",globalParameter);
        if (!isBack(request)) {
            BeanForm globalParameterForm = (BeanForm) getForm("/updateGlobalParameter", request);
            globalParameterForm.populateToForm(globalParameter);
        }
        this.putGlobalMailReminderToRequest(gm,request);
        return mapping.findForward("page");
    }

    private void putGlobalMailReminderToRequest(ParameterManager gm,HttpServletRequest request) {
        List reminderList=PersistentEnum.getEnumList(MetadataType.GLOBAL_MAIL_REMINDER_TYPE.getDetailClass());
        List dbReminderList=gm.getGlobalMailReminderList();
        List result=new ArrayList();
        for (Iterator itor = reminderList.iterator(); itor.hasNext();) {
            GlobalMailReminderType type = (GlobalMailReminderType) itor.next();
            result.add(findReminderFromList(dbReminderList,type));
        }
        request.setAttribute("X_YESNOLIST", PersistentEnum.getEnumList(YesNo.class));
        request.setAttribute("x_mailReminderList",result);
    }
    
    private GlobalMailReminder findReminderFromList(List dbReminderList,GlobalMailReminderType type) {
        for (int index = 0; index < dbReminderList.size(); index++) {
            GlobalMailReminder reminder = (GlobalMailReminder) dbReminderList.get(index);
            if (reminder.getType().equals(type)) {
                dbReminderList.remove(index);
                return reminder;
            }
        }
        GlobalMailReminder reminder=new GlobalMailReminder();
        reminder.setType(type);
        return reminder;
    }
    
    private SiteMailReminder findReminderFromList(List dbReminderList,SiteMailReminderType type) {
        for (int index = 0; index < dbReminderList.size(); index++) {
            SiteMailReminder reminder = (SiteMailReminder) dbReminderList.get(index);
            if (reminder.getType().equals(type)) {
                dbReminderList.remove(index);
                return reminder;
            }
        }
        SiteMailReminder reminder=new SiteMailReminder();
        reminder.setType(type);
        return reminder;
    }
    
    /**
     * 更新GlobalParameter
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward updateGlobal(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        BeanForm globalParameterForm = (BeanForm) form;
        GlobalParameter globalParameter = new GlobalParameter();
        globalParameterForm.populateToBean(globalParameter,request);
        
        List reminderList=getReminderListFromRequest(request,null);
        
        ParameterManager pm =ServiceLocator.getParameterManager(request);
        pm.updateGlobalParameter(globalParameter,reminderList);
        
        this.postGlobalMessage("globalParameter.updateSuccess",request.getSession());
        
        final ActionForward forward=new ActionForward();
        forward.setRedirect(true);
        forward.setPath("editGlobalParameter.do");
        return forward;
    }
    
    private List getReminderListFromRequest(HttpServletRequest request,Site site) {
        String typeId[]=request.getParameterValues("type");
        String dueDay[]=request.getParameterValues("dueDay");
        String intervalDay[]=request.getParameterValues("intervalDay");
        String maxTime[]=request.getParameterValues("maxTime");
        
        List reminderList=new ArrayList();
        for (int index=0;index<typeId.length;index++) {
            if (this.isGlobal(request)) {
                GlobalMailReminder reminder=new GlobalMailReminder();
                reminder.setType((GlobalMailReminderType)PersistentEnum.fromEnumCode(GlobalMailReminderType.class,new Integer(typeId[index])));
                reminder.setDueDay(ActionUtils.parseInt(dueDay[index]).intValue());
                reminder.setIntervalDay(ActionUtils.parseInt(intervalDay[index]).intValue());
                reminder.setMaxTime(ActionUtils.parseInt(maxTime[index]).intValue());
                reminderList.add(reminder);
            } else {
                SiteMailReminder reminder=new SiteMailReminder();
                reminder.setSite(site);
                reminder.setType((SiteMailReminderType)PersistentEnum.fromEnumCode(SiteMailReminderType.class,new Integer(typeId[index])));
                reminder.setDueDay(ActionUtils.parseInt(dueDay[index]).intValue());
                reminder.setIntervalDay(ActionUtils.parseInt(intervalDay[index]).intValue());
                reminder.setMaxTime(ActionUtils.parseInt(maxTime[index]).intValue());
                reminderList.add(reminder);
            }
        }
        return reminderList;
    }
    
    /**
     * 编辑SiteParameter
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward editSite(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List siteList = this.getAndCheckGrantedSiteList(request);
        Site site= getSiteFromRequest(request);
        if (site!=null)
            this.checkSite(site,request);
        else {
            site=(Site)siteList.get(0);
        }
        request.setAttribute("x_siteList",siteList);
        
        ParameterManager gm =ServiceLocator.getParameterManager(request);
        DataTransferParameter dataTransferParameter=gm.getDataTransferParameter(site);
        
        request.setAttribute("x_dataTransferParameter",dataTransferParameter);
        if (!isBack(request)) {
            BeanForm siteParameterForm = (BeanForm) getForm("/updateSiteParameter", request);
            siteParameterForm.setConverterLocator(dataTransferConverterLocator);
            siteParameterForm.populateToForm(dataTransferParameter);
        }
        this.putSiteMailReminderToRequest(site,gm,request);
        request.setAttribute("x_exportFileTypeList",PersistentEnum.getEnumList(ExportFileType.class));
        return mapping.findForward("page");
    }

    private Site getSiteFromRequest(HttpServletRequest request) {
        Integer id = ActionUtils.parseInt(request.getParameter("site_id"));
        if (id != null) {
            SiteManager sm=ServiceLocator.getSiteManager(request);
            return sm.getSite(id);
        } else {
            return null;
        }
    }
    
    private void putSiteMailReminderToRequest(Site site,ParameterManager gm,HttpServletRequest request) {
        List reminderList=PersistentEnum.getEnumList(MetadataType.SITE_MAIL_REMINDER_TYPE.getDetailClass());
        List dbReminderList=gm.getSiteMailReminderList(site);
        List result=new ArrayList();
        for (Iterator itor = reminderList.iterator(); itor.hasNext();) {
            SiteMailReminderType type = (SiteMailReminderType) itor.next();
            result.add(findReminderFromList(dbReminderList,type));
        }
        request.setAttribute("x_mailReminderList",result);
    }
    
    public ActionForward updateSite(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        BeanForm siteParameterForm = (BeanForm) form;
        Site site= getSiteFromRequest(request);
        checkSite(site,request);
        
        ParameterManager gm =ServiceLocator.getParameterManager(request);
        DataTransferParameter dataTransferParameter=gm.getDataTransferParameter(site);
        
        siteParameterForm.setConverterLocator(dataTransferConverterLocator);
        siteParameterForm.populateToBean(dataTransferParameter,request);
        
        List reminderList=getReminderListFromRequest(request,site);
        
        ParameterManager pm =ServiceLocator.getParameterManager(request);
        pm.updateSiteParameter(dataTransferParameter,reminderList);
        
        this.postGlobalMessage("siteParameter.updateSuccess",request.getSession());
        
        final ActionForward forward=new ActionForward();
        forward.setRedirect(true);
        forward.setPath("editSiteParameter.do?site_id="+site.getId());
        return forward;
    }

    public ActionForward startJob(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Site site= getSiteFromRequest(request);
        checkSite(site,request);
        
        ParameterManager gm =ServiceLocator.getParameterManager(request);
        DataTransferParameter dataTransferParameter=gm.getDataTransferParameter(site);
        
        DataTransferManager dm=ServiceLocator.getDataTransferManager(request);
        dm.startJob(dataTransferParameter);
        
        this.postGlobalMessage("dataTransferParameter.startJobSuccess",request.getSession());
        
        final ActionForward forward=new ActionForward();
        forward.setRedirect(true);
        forward.setPath("editSiteParameter.do?site_id="+site.getId());
        return forward;
    }
}