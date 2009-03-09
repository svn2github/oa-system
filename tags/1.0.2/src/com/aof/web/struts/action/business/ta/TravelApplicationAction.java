/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.web.struts.action.business.ta;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

import com.aof.model.admin.Department;
import com.aof.model.admin.Function;
import com.aof.model.admin.Site;
import com.aof.model.admin.TravelGroup;
import com.aof.model.admin.TravelGroupDetail;
import com.aof.model.admin.UserSite;

import com.aof.model.business.ta.AirTicket;
import com.aof.model.business.ta.TravelApplication;
import com.aof.model.business.ta.query.TravelApplicationQueryCondition;
import com.aof.model.business.ta.query.TravelApplicationQueryOrder;
import com.aof.model.metadata.AirTicketStatus;
import com.aof.model.metadata.TravelApplicationBookStatus;
import com.aof.model.metadata.TravelApplicationStatus;
import com.aof.model.metadata.TravelApplicationUrgent;
import com.aof.model.metadata.TravellingMode;
import com.aof.model.metadata.YesNo;
import com.aof.service.admin.CountryManager;
import com.aof.service.admin.EmailManager;
import com.aof.service.admin.FunctionManager;
import com.aof.service.admin.HotelManager;
import com.aof.service.admin.PurchaseTypeManager;
import com.aof.service.admin.TravelGroupManager;
import com.aof.service.admin.UserManager;
import com.aof.service.business.rule.ExecuteFlowEmptyResultException;
import com.aof.service.business.rule.NoAvailableFlowToExecuteException;
import com.aof.service.business.ta.AirTicketManager;
import com.aof.service.business.ta.TravelApplicationApproveRequestManager;
import com.aof.service.business.ta.TravelApplicationManager;
import com.aof.utils.PDFReport;
import com.aof.utils.SessionTempFile;
import com.aof.web.struts.action.ActionUtils;
import com.aof.web.struts.action.ServiceLocator;
import com.aof.web.struts.form.business.ta.TravelApplicationQueryForm;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.form.BeanForm;
import com.shcnc.struts.form.ComboBox;
import com.shcnc.struts.form.DateFormatter;
import com.shcnc.utils.BeanHelper;
import com.shcnc.utils.ExportUtil;
import com.shcnc.utils.Exportable;

/**
 * struts action class for domain model TravelApplication
 * 
 * @author shilei
 * @version 1.0 (Nov 15, 2005)
 */
public class TravelApplicationAction extends BaseTravelApplicationAction {

    private List getEnabledCountryProvinceCityList(HttpServletRequest request) {
        CountryManager cm = ServiceLocator.getCountryManager(request);
        return cm.listEnabledCountryProvinceCity();
    }

    private void putEnabledCountryProvinceCityListToRequest(HttpServletRequest request) {
        List countryList = getEnabledCountryProvinceCityList(request);
        request.setAttribute("x_countryList", countryList);
    }

    /**
     * action method for listing travel application
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TravelApplicationManager fm = ServiceLocator.getTravelApplicationManager(request);

        TravelApplicationQueryForm queryForm = (TravelApplicationQueryForm) form;
        if (StringUtils.isEmpty(queryForm.getOrder())) {
            queryForm.setOrder(TravelApplicationQueryOrder.ID.getName());
            queryForm.setDescend(false);
        } else {
            if (TravelApplicationQueryOrder.getEnum(queryForm.getOrder()) == null)
                throw new RuntimeException("order not found");
        }

        Map conditions = constructQueryMap(queryForm, request);

        final boolean isSelf = this.isGlobal(request);

        if (isSelf) {
            conditions.put(TravelApplicationQueryCondition.REQUESTOR_ID_EQ, this.getCurrentUser(request).getId());
            request.setAttribute("x_postfix", "_self");
        } else {
            conditions.put(TravelApplicationQueryCondition.CREATOR_ID_EQ, this.getCurrentUser(request).getId());
            conditions.put(TravelApplicationQueryCondition.REQUESTOR_ID_NOT_EQ, this.getCurrentUser(request).getId());
            request.setAttribute("x_postfix", "_other");
        }

        String exportType = queryForm.getExportType();
        if (StringUtils.isNotEmpty(exportType)) {
            List data = fm.getTravelApplicationList(conditions, 0, -1, TravelApplicationQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend());

            int index = SessionTempFile.createNewTempFile(request);
            String fileName = "travelApplication";
            String suffix = ExportUtil.export(exportType, data, request, new FileOutputStream(SessionTempFile.getTempFile(index, request)), new Exportable() {

                public void exportHead(List row, HttpServletRequest request) throws Exception {
                    MessageResources messages = getResources(request);
                    row.add(messages.getMessage(getLocale(request), "travelApplication.id"));
                    if (!isSelf)
                        row.add(messages.getMessage(getLocale(request), "travelApplication.requestor"));

                    row.add(messages.getMessage(getLocale(request), "travelApplication.title"));

                    row.add(messages.getMessage(getLocale(request), "travelApplication.toCity.id"));
                    row.add(messages.getMessage(getLocale(request), "travelApplication.fromDate"));
                    row.add(messages.getMessage(getLocale(request), "travelApplication.toDate"));
                    row.add(messages.getMessage(getLocale(request), "travelApplication.requestDate"));
                    row.add(messages.getMessage(getLocale(request), "travelApplication.urgent"));
                    row.add(messages.getMessage(getLocale(request), "travelApplication.status"));
                    row.add(messages.getMessage(getLocale(request), "travelApplication.bookStatus"));
                }

                public void exportRow(List row, Object data, HttpServletRequest request) throws Exception {
                    TravelApplication ta = (TravelApplication) data;
                    row.add(BeanHelper.getBeanPropertyValue(data, "id"));

                    if (!isSelf)
                        row.add(BeanHelper.getBeanPropertyValue(data, "requestor.name"));

                    row.add(BeanHelper.getBeanPropertyValue(data, "title"));

                    if (isEnglish(request))
                        row.add(BeanHelper.getBeanPropertyValue(data, "toCity.engName"));
                    else
                        row.add(BeanHelper.getBeanPropertyValue(data, "toCity.chnName"));

                    if (ta.getFromDate() != null)
                        row.add(ActionUtils.getDisplayDateFromDate(ta.getFromDate()));
                    else
                        row.add("");

                    if (ta.getToDate() != null)
                        row.add(ActionUtils.getDisplayDateFromDate(ta.getToDate()));
                    else
                        row.add("");

                    if (ta.getRequestDate() != null)
                        row.add(ActionUtils.getDisplayDateFromDate(ta.getRequestDate()));
                    else
                        row.add("");

                    if (isEnglish(request))
                        row.add(BeanHelper.getBeanPropertyValue(data, "urgent.engShortDescription"));
                    else
                        row.add(BeanHelper.getBeanPropertyValue(data, "urgent.chnShortDescription"));

                    if (isEnglish(request))
                        row.add(BeanHelper.getBeanPropertyValue(data, "status.engShortDescription"));
                    else
                        row.add(BeanHelper.getBeanPropertyValue(data, "status.chnShortDescription"));

                    if (isEnglish(request))
                        row.add(BeanHelper.getBeanPropertyValue(data, "bookStatus.engShortDescription"));
                    else
                        row.add(BeanHelper.getBeanPropertyValue(data, "bookStatus.chnShortDescription"));

                }
            });
            return new ActionForward("download/" + index + "/" + URLEncoder.encode(fileName, "UTF-8") + '.' + suffix, true);
        }

        if (queryForm.isFirstInit()) {
            queryForm.init(fm.getTravelApplicationListCount(conditions));
        } else {
            queryForm.init();
        }

        List result = fm.getTravelApplicationList(conditions, queryForm.getPageNoAsInt(), queryForm.getPageSizeAsInt(), TravelApplicationQueryOrder
                .getEnum(queryForm.getOrder()), queryForm.isDescend());

        request.setAttribute("X_RESULTLIST", result);
        request.setAttribute("x_travelApplicationBookStatusList", TravelApplicationBookStatus.getEnumList(TravelApplicationBookStatus.class));
        putEnumListToRequest(request);
        putEnabledCountryProvinceCityListToRequest(request);

        return mapping.findForward("page");
    }

    /**
     * struts action method for selecting travel application
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward select(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TravelApplicationManager fm = ServiceLocator.getTravelApplicationManager(request);
        TravelApplicationQueryForm queryForm = (TravelApplicationQueryForm) form;

        Map conditions = constructQueryMap(queryForm, request);
        String requestorId = request.getParameter("requestor_id");
        if (requestorId == null)
            conditions.put(TravelApplicationQueryCondition.REQUESTOR_ID_EQ, this.getCurrentUser(request).getId());
        else
            conditions.put(TravelApplicationQueryCondition.REQUESTOR_ID_EQ, new Integer(requestorId));
        conditions.put(TravelApplicationQueryCondition.STATUS_EQ, TravelApplicationStatus.APPROVED);

        if (StringUtils.isEmpty(queryForm.getOrder())) {
            queryForm.setOrder(TravelApplicationQueryOrder.ID.getName());
            queryForm.setDescend(true);
        }
        queryForm.setPageSize("10");
        if (queryForm.isFirstInit()) {
            queryForm.init(fm.getTravelApplicationListCount(conditions), queryForm.getPageSizeAsInt());
        } else {
            queryForm.init();
        }

        List result = fm.getTravelApplicationList(conditions, queryForm.getPageNoAsInt(), queryForm.getPageSizeAsInt(), TravelApplicationQueryOrder
                .getEnum(queryForm.getOrder()), queryForm.isDescend());

        request.setAttribute("X_RESULTLIST", result);
        putEnumListToRequest(request);
        putEnabledCountryProvinceCityListToRequest(request);
        return mapping.findForward("page");
    }

    private Date getNextDate(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.DATE, 1);
        return c.getTime();
    }

    private Map constructQueryMap(TravelApplicationQueryForm queryForm, HttpServletRequest request) {
        Map conditions = new HashMap();
        /* id */
        {
            String id = queryForm.getId();
            if (id != null && id.trim().length() != 0) {
                conditions.put(TravelApplicationQueryCondition.ID_LIKE, id);
            }
        }

        if (StringUtils.isNotEmpty(queryForm.getRequestDate1())) {
            Date d = ActionUtils.getDateFromDisplayDate(queryForm.getRequestDate1());
            conditions.put(TravelApplicationQueryCondition.REQUESTDATE_GE, d);
        }

        if (StringUtils.isNotEmpty(queryForm.getRequestDate2())) {
            Date d = ActionUtils.getDateFromDisplayDate(queryForm.getRequestDate2());
            conditions.put(TravelApplicationQueryCondition.REQUESTDATE_LT, getNextDate(d));
        }

        if (StringUtils.isNotEmpty(queryForm.getFromDate1())) {
            Date d = ActionUtils.getDateFromDisplayDate(queryForm.getFromDate1());
            conditions.put(TravelApplicationQueryCondition.FROMDATE_GE, d);
        }

        if (StringUtils.isNotEmpty(queryForm.getFromDate2())) {
            Date d = ActionUtils.getDateFromDisplayDate(queryForm.getFromDate2());
            conditions.put(TravelApplicationQueryCondition.FROMDATE_LT, getNextDate(d));
        }

        if (StringUtils.isNotEmpty(queryForm.getToDate1())) {
            Date d = ActionUtils.getDateFromDisplayDate(queryForm.getToDate1());
            conditions.put(TravelApplicationQueryCondition.TODATE_GE, d);
        }

        if (StringUtils.isNotEmpty(queryForm.getToDate2())) {
            Date d = ActionUtils.getDateFromDisplayDate(queryForm.getToDate2());
            conditions.put(TravelApplicationQueryCondition.TODATE_LT, getNextDate(d));
        }

        if (StringUtils.isNotEmpty(queryForm.getRequestor_name())) {
            conditions.put(TravelApplicationQueryCondition.REQUESTOR_NAME_LIKE, queryForm.getRequestor_name());
        }

        Integer department_id = ActionUtils.parseInt(queryForm.getDepartment_id());
        Integer site_id = ActionUtils.parseInt(queryForm.getSite_id());
        if (department_id != null && department_id.intValue() != 0) {
            conditions.put(TravelApplicationQueryCondition.DEPARTMENT_ID_EQ, department_id);
        } else {
            List siteList=this.getGrantedSiteDeparmentList(request);
            for (int i0 = 0; i0 < siteList.size(); i0++) {
                Site site = (Site)siteList.get(i0);
                if (site.getId().equals(site_id)) {
                    Object[] params = new Object[site.getDepartments().size()];                    
                    for (int j0 = 0; j0 < site.getDepartments().size(); j0++) {
                        params[j0] = ((Department)site.getDepartments().get(j0)).getId();
                    }
                    conditions.put(TravelApplicationQueryCondition.DEPARTMENT_ID_IN, params);
                }
            }
        }
        if (site_id != null) {
            conditions.put(TravelApplicationQueryCondition.SITE_ID_EQ, site_id);
        }

        Integer fromCity_id = ActionUtils.parseInt(queryForm.getFromCity_id());
        if (fromCity_id != null) {
            conditions.put(TravelApplicationQueryCondition.FROMCITY_ID_EQ, fromCity_id);
        }

        Integer toCity_id = ActionUtils.parseInt(queryForm.getToCity_id());
        Integer toProvince_id = ActionUtils.parseInt(queryForm.getToProvince_id());
        Integer toCountry_id = ActionUtils.parseInt(queryForm.getToCountry_id());
        if (toCity_id != null) {
            conditions.put(TravelApplicationQueryCondition.TOCITY_ID_EQ, toCity_id);
        } else if (toProvince_id != null) {
            conditions.put(TravelApplicationQueryCondition.TOPROVINCE_ID_EQ, toProvince_id);
        } else if (toCountry_id != null) {
            conditions.put(TravelApplicationQueryCondition.TOCOUNTRY_ID_EQ, toCountry_id);
        }

        Integer hotel_id = ActionUtils.parseInt(queryForm.getHotel_id());
        if (hotel_id != null) {
            conditions.put(TravelApplicationQueryCondition.HOTEL_ID_EQ, hotel_id);
        }

        Integer price_id = ActionUtils.parseInt(queryForm.getPrice_id());
        if (price_id != null) {
            conditions.put(TravelApplicationQueryCondition.PRICE_ID_EQ, price_id);
        }

        Integer requestor_id = ActionUtils.parseInt(queryForm.getRequestor_id());
        if (requestor_id != null) {
            conditions.put(TravelApplicationQueryCondition.REQUESTOR_ID_EQ, requestor_id);
        }

        Integer booker_id = ActionUtils.parseInt(queryForm.getBooker_id());
        if (booker_id != null) {
            conditions.put(TravelApplicationQueryCondition.BOOKER_ID_EQ, booker_id);
        }

        /* property */
        String title = queryForm.getTitle();
        if (title != null && title.trim().length() != 0) {
            conditions.put(TravelApplicationQueryCondition.TITLE_LIKE, title);
        }
        String description = queryForm.getDescription();
        if (description != null && description.trim().length() != 0) {
            conditions.put(TravelApplicationQueryCondition.DESCRIPTION_LIKE, description);
        }
        String status = queryForm.getStatus();
        if (status != null && status.trim().length() != 0) {
            conditions.put(TravelApplicationQueryCondition.STATUS_EQ, Integer.valueOf(status));
        }

        Integer urgent = ActionUtils.parseInt(queryForm.getUrgent());
        if (urgent != null) {
            conditions.put(TravelApplicationQueryCondition.URGENT_EQ, urgent);
        }

        String bookStatus = queryForm.getBookStatus();
        if (bookStatus != null && bookStatus.trim().length() != 0) {
            conditions.put(TravelApplicationQueryCondition.BOOKSTATUS_EQ, Integer.valueOf(bookStatus));
        }
        String hotelName = queryForm.getHotelName();
        if (hotelName != null && hotelName.trim().length() != 0) {
            conditions.put(TravelApplicationQueryCondition.HOTELNAME_LIKE, hotelName);
        }
        String roomDescription = queryForm.getRoomDescription();
        if (roomDescription != null && roomDescription.trim().length() != 0) {
            conditions.put(TravelApplicationQueryCondition.ROOMDESCRIPTION_LIKE, roomDescription);
        }
        String checkInDate = queryForm.getCheckInDate();
        if (checkInDate != null && checkInDate.trim().length() != 0) {
            conditions.put(TravelApplicationQueryCondition.CHECKINDATE_EQ, checkInDate);
        }
        String checkOutDate = queryForm.getCheckOutDate();
        if (checkOutDate != null && checkOutDate.trim().length() != 0) {
            conditions.put(TravelApplicationQueryCondition.CHECKOUTDATE_EQ, checkOutDate);
        }
        String travellingMode = queryForm.getTravellingMode();
        if (travellingMode != null && travellingMode.trim().length() != 0) {
            conditions.put(TravelApplicationQueryCondition.TRAVELLINGMODE_EQ, travellingMode);
        }
        String singleOrReturn = queryForm.getSingleOrReturn();
        if (singleOrReturn != null && singleOrReturn.trim().length() != 0) {
            conditions.put(TravelApplicationQueryCondition.SINGLEORRETURN_EQ, singleOrReturn);
        }

        String fromDate = queryForm.getFromDate();
        if (fromDate != null && fromDate.trim().length() != 0) {
            conditions.put(TravelApplicationQueryCondition.FROMDATE_TODATE_GT, new Object[] { fromDate, fromDate });
        }
        String fromTime = queryForm.getFromTime();
        if (fromTime != null && fromTime.trim().length() != 0) {
            conditions.put(TravelApplicationQueryCondition.FROMTIME_EQ, fromTime);
        }
        String toDate = queryForm.getToDate();
        if (toDate != null && toDate.trim().length() != 0) {
            conditions.put(TravelApplicationQueryCondition.FROMDATE_TODATE_LT, new Object[] { toDate, toDate });
        }
        String toTime = queryForm.getToTime();
        if (toTime != null && toTime.trim().length() != 0) {
            conditions.put(TravelApplicationQueryCondition.TOTIME_EQ, toTime);
        }
        String requestDate = queryForm.getRequestDate();
        if (requestDate != null && requestDate.trim().length() != 0) {
            conditions.put(TravelApplicationQueryCondition.REQUESTDATE_EQ, requestDate);
        }
        Integer approveRequestId = ActionUtils.parseInt(queryForm.getApproveRequestId());
        if (approveRequestId != null) {
            conditions.put(TravelApplicationQueryCondition.APPROVEREQUESTID_EQ, approveRequestId);
        }

        if (StringUtils.isNotEmpty(queryForm.getCreateDate1())) {
            Date d = ActionUtils.getDateFromDisplayDate(queryForm.getCreateDate1());
            conditions.put(TravelApplicationQueryCondition.CREATEDATE_GE, d);
        }

        if (StringUtils.isNotEmpty(queryForm.getCreateDate2())) {
            Date d = ActionUtils.getDateFromDisplayDate(queryForm.getCreateDate2());
            conditions.put(TravelApplicationQueryCondition.CREATEDATE_LT, getNextDate(d));
        }
        return conditions;
    }

    /**
     * action method for editing the current user's travel application
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward edit_self(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        TravelApplication travelApplication = getTravelApplicationFromRequest(request);
        this.checkRequestorIsSelf(travelApplication, request);
        this.checkDraft(travelApplication);

        request.setAttribute("x_travelApplication", travelApplication);
        putApproveListToRequest(travelApplication, request);

        BeanForm travelApplicationForm = (BeanForm) form;
        if (!isBack(request) && !isComboChange(request)) {
            travelApplicationForm.setConverterLocator(converterLocator);
            travelApplicationForm.populateToForm(travelApplication);

            this.processHotelPrice(travelApplication, travelApplicationForm);
        }

        TravelApplicationSelfPageManager pm = new TravelApplicationSelfPageManager(travelApplicationForm, this.getCurrentUser(request), request);
        pm.process();

        putEnumListToRequest(request);
        return mapping.findForward("page");
    }

    /**
     * action method for editing the other user's travel application
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward edit_other(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TravelApplication travelApplication = getTravelApplicationFromRequest(request);

        this.checkCreatorIsSelf(travelApplication, request);
        this.checkDepartment(travelApplication.getDepartment(), request);
        this.checkDraft(travelApplication);

        request.setAttribute("x_travelApplication", travelApplication);
        putApproveListToRequest(travelApplication, request);

        BeanForm travelApplicationForm = (BeanForm) form;
        if (!isBack(request) && !isComboChange(request)) {
            travelApplicationForm.setConverterLocator(converterLocator);
            travelApplicationForm.populateToForm(travelApplication);

            this.processHotelPrice(travelApplication, travelApplicationForm);
        }

        TravelApplicationOtherPageManager pm = new TravelApplicationOtherPageManager(travelApplicationForm, this.getCurrentUser(request), this
                .getFunction(request), request);
        pm.process();

        putEnumListToRequest(request);
        return mapping.findForward("page");
    }

    /**
     * action method for creating the current user's travel application
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward newObject_self(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        BeanForm travelApplicationForm = (BeanForm) form;

        if (!isBack(request) && !isComboChange(request)) {
            TravelApplication travelApplication = new TravelApplication();
            travelApplication.setRequestor(this.getCurrentUser(request));
            travelApplicationForm.populateToForm(travelApplication);
        }

        TravelApplicationSelfPageManager pm = new TravelApplicationSelfPageManager(travelApplicationForm, this.getCurrentUser(request), request);
        pm.process();

        putEnumListToRequest(request);
        
        return mapping.findForward("page");
    }

    /**
     * action method for creating other user's travel application
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward newObject_other(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        BeanForm travelApplicationForm = (BeanForm) form;

        if (!isBack(request) && !isComboChange(request)) {
            TravelApplication travelApplication = new TravelApplication();
            travelApplicationForm.populateToForm(travelApplication);
        }

        TravelApplicationOtherPageManager pm = new TravelApplicationOtherPageManager(travelApplicationForm, this.getCurrentUser(request), this
                .getFunction(request), request);
        pm.process();

        putEnumListToRequest(request);
        
        return mapping.findForward("page");
    }

    /**
     * action method for updating other user's travel application
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward update_other(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        return update_other(mapping, form, request, response, false);
    }

    /**
     * action method for previewing approver list when editing travel
     * application
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward update_other_viewapprover(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        return update_other(mapping, form, request, response, true);
    }

    /*
     * Changed by nicebean, rejected ta cannot modify & delete
     */
    private void checkDraft(TravelApplication travelApplication) {
        if (!travelApplication.getStatus().equals(TravelApplicationStatus.DRAFT)) {
            throw new ActionException("travelApplication.error.editNotDraft");
        }
    }

    /**
     * struts action method for deleting the current user's travel application
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @param viewApprover
     * @return
     * @throws Exception
     */
    public ActionForward delete_self(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TravelApplication ta = this.getTravelApplicationFromRequest(request);
        this.checkRequestorIsSelf(ta, request);
        this.checkDraft(ta);

        TravelApplicationManager tm = ServiceLocator.getTravelApplicationManager(request);
        tm.deleteTravelApplication(ta, this.getCurrentUser(request));
        this.postGlobalMessage("travelApplication.delete.success", request.getSession());
        return getListForward(true);
    }

    /**
     * struts action method for deleting the other user's travel application
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @param viewApprover
     * @return
     * @throws Exception
     */
    public ActionForward delete_other(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TravelApplication ta = this.getTravelApplicationFromRequest(request);
        this.checkCreatorIsSelf(ta, request);
        this.checkDraft(ta);
        TravelApplicationManager tm = ServiceLocator.getTravelApplicationManager(request);
        tm.deleteTravelApplication(ta, this.getCurrentUser(request));
        this.postGlobalMessage("travelApplication.delete.success", request.getSession());
        return getListForward(false);
    }

    private void checkRequestorIsSelf(TravelApplication ta, HttpServletRequest request) {
        if (!ta.getRequestor().equals(this.getCurrentUser(request)))
            throw new ActionException("travelApplication.error.requestor.notSelf");
    }

    private void checkCreatorIsSelf(TravelApplication ta, HttpServletRequest request) {
        if (!ta.getCreator().equals(this.getCurrentUser(request)))
            throw new ActionException("travelApplication.error.creatorNotSelf");
    }

    private void checkRequestorOrCreatorIsSelf(TravelApplication ta, HttpServletRequest request) {
        if (!ta.getRequestor().equals(this.getCurrentUser(request)) && !ta.getCreator().equals(this.getCurrentUser(request)))
            throw new ActionException("travelApplication.error.requestorOrCreator.notSelf");
    }

    private ActionForward update_other(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean viewApprover)
            throws Exception {
        TravelApplication travelApplication = this.getTravelApplicationFromRequest(request);

        this.checkCreatorIsSelf(travelApplication, request);
        this.checkDepartment(travelApplication.getDepartment(), request);
        this.checkDraft(travelApplication);

        BeanForm travelApplicationForm = (BeanForm) form;
        travelApplicationForm.setConverterLocator(converterLocator);
        travelApplicationForm.setBeanLoader(ServiceLocator.getBeanLoader(request));

        travelApplicationForm.populateToBean(travelApplication, request);

        this.checkDepartment(travelApplication.getDepartment(), request);

        travelApplication.setCreator(this.getCurrentUser(request));

        if (viewApprover) {
            try {
                request.setAttribute("X_APPROVELIST", executeFlow(travelApplication, request));
            } catch (ActionException e) {
                ActionMessage message = new ActionMessage(e.getKey(), e.getValues());
                ActionMessages messages = new ActionMessages();
                messages.add(null, message);
                saveErrors(request, messages);
            }
            return mapping.findForward("page");

        } else {
            List taarList = null;
            if (!isDraft(request)) {
                taarList = executeFlow(travelApplication, request);
            }

            TravelApplicationManager travelApplicationManager = ServiceLocator.getTravelApplicationManager(request);
            travelApplicationManager.updateTravelApplication(travelApplication, taarList, isDraft(request), this.getCurrentUser(request));

            if (isDraft(request)) {
                this.postGlobalMessage("travelApplication.updateDraft.success", request.getSession());
                return getEditForwardFor(travelApplication, false);
            } else {
                this.postGlobalMessage("travelApplication.submit.success", request.getSession());
                return getViewForwardFor(travelApplication, false);
            }

        }

    }

    /**
     * action method for updating current user's travel application
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward update_self(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return update_self(mapping, form, request, response, false);
    }

    private ActionForward update_self(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean viewApprover)
            throws Exception {
        TravelApplication travelApplication = this.getTravelApplicationFromRequest(request);
        this.checkRequestorIsSelf(travelApplication, request);
        this.checkDraft(travelApplication);

        BeanForm travelApplicationForm = (BeanForm) form;
        travelApplicationForm.setConverterLocator(converterLocator);
        travelApplicationForm.setBeanLoader(ServiceLocator.getBeanLoader(request));

        travelApplicationForm.populateToBean(travelApplication, request);

        travelApplication.setCreator(this.getCurrentUser(request));
        travelApplication.setRequestor(this.getCurrentUser(request));

        List taarList = null;
        if (!isDraft(request) || viewApprover) {
            taarList = executeFlow(travelApplication, request);
        }

        if (viewApprover) {
            request.setAttribute("X_APPROVELIST", taarList);
            return mapping.findForward("page");

        } else {
            TravelApplicationManager travelApplicationManager = ServiceLocator.getTravelApplicationManager(request);
            travelApplicationManager.updateTravelApplication(travelApplication, taarList, isDraft(request), this.getCurrentUser(request));

            if (isDraft(request)) {
                this.postGlobalMessage("travelApplication.updateDraft.success", request.getSession());
                return getEditForwardFor(travelApplication, true);
            } else {
                this.postGlobalMessage("travelApplication.submit.success", request.getSession());
                return getViewForwardFor(travelApplication, true);
            }
        }

    }

    /**
     * action method for view approver before updating current user's travel
     * application
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward update_self_viewapprover(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return update_self(mapping, form, request, response, true);
    }

    private boolean isDraft(HttpServletRequest request) {
        String s = request.getParameter("draft");
        if (s != null && s.equals("true"))
            return true;
        return false;
    }

    /**
     * action method for inserting current user's travel application
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward insert_self(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return insert_self(mapping, form, request, response, false);
    }

    /**
     * action method for previewing approver list when new travel application
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward insert_self_viewapprover(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return insert_self(mapping, form, request, response, true);
    }

    private ActionForward insert_self(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean viewApprover)
            throws Exception {
        BeanForm travelApplicationForm = (BeanForm) form;
        travelApplicationForm.setConverterLocator(converterLocator);
        travelApplicationForm.setBeanLoader(ServiceLocator.getBeanLoader(request));

        TravelApplication travelApplication = (TravelApplication) travelApplicationForm.newBean(request);

        travelApplication.setRequestor(this.getCurrentUser(request));
        travelApplication.setCreator(this.getCurrentUser(request));

        List taarList = null;
        if (!isDraft(request) || viewApprover) {
            taarList = executeFlow(travelApplication, request);
        }

        if (viewApprover) {
            request.setAttribute("X_APPROVELIST", taarList);
            return mapping.findForward("page");
        } else {
            TravelApplicationManager travelApplicationManager = ServiceLocator.getTravelApplicationManager(request);
            travelApplicationManager.insertTravelApplication(travelApplication, taarList, isDraft(request), this.getCurrentUser(request));

            if (isDraft(request)) {
                this.postGlobalMessage("travelApplication.newDraft.success", request.getSession());
                return getEditForwardFor(travelApplication, true);
            } else {
                this.postGlobalMessage("travelApplication.submit.success", request.getSession());
                return getViewForwardFor(travelApplication, true);
            }
        }

    }

    /**
     * action method for inserting other user's travel application
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward insert_other(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return insert_other(mapping, form, request, response, false);
    }

    /**
     * action method for previewing approver list when new travel application
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward insert_other_viewapprover(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return insert_other(mapping, form, request, response, true);
    }

    private ActionForward insert_other(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean viewApprover)
            throws Exception {
        TravelApplication travelApplication = new TravelApplication();

        BeanForm travelApplicationForm = (BeanForm) form;
        travelApplicationForm.setConverterLocator(converterLocator);
        travelApplicationForm.setBeanLoader(ServiceLocator.getBeanLoader(request));

        travelApplicationForm.populateToBean(travelApplication, request);

        this.checkDepartment(travelApplication.getDepartment(), request);

        travelApplication.setCreator(this.getCurrentUser(request));

        List taarList = null;
        if (!isDraft(request) || viewApprover) {
            taarList = executeFlow(travelApplication, request);
        }

        if (viewApprover) {
            request.setAttribute("X_APPROVELIST", taarList);
            return mapping.findForward("page");
        } else {
            TravelApplicationManager travelApplicationManager = ServiceLocator.getTravelApplicationManager(request);
            travelApplicationManager.insertTravelApplication(travelApplication, taarList, isDraft(request), this.getCurrentUser(request));

            if (isDraft(request)) {
                this.postGlobalMessage("travelApplication.newDraft.success", request.getSession());
                return getEditForwardFor(travelApplication, false);
            } else {
                this.postGlobalMessage("travelApplication.submit.success", request.getSession());
                return getViewForwardFor(travelApplication, false);
            }
        }
    }

    private List executeFlow(TravelApplication ta, HttpServletRequest request) {
        try {
            List taarList = ServiceLocator.getFlowManager(request).executeApproveFlow(ta);
            return taarList;
        } catch (ExecuteFlowEmptyResultException e) {
            throw new ActionException("flow.execute.notApproverFound");
        } catch (NoAvailableFlowToExecuteException e) {
            throw new ActionException("flow.execute.notFlowFound");
        }
    }

    private ActionForward getEditForwardFor(TravelApplication ta, boolean isSelf) {

        ActionForward forward;
        if (isSelf)
            forward = new ActionForward("editTravelApplication_self.do?id=" + ta.getId());
        else
            forward = new ActionForward("editTravelApplication_other.do?id=" + ta.getId());
        forward.setRedirect(true);
        return forward;
    }

    private ActionForward getViewForwardFor(TravelApplication ta, boolean isSelf) {

        ActionForward forward;
        if (isSelf)
            forward = new ActionForward("viewTravelApplication_self.do?id=" + ta.getId());
        else
            forward = new ActionForward("viewTravelApplication_other.do?id=" + ta.getId());
        forward.setRedirect(true);
        return forward;
    }

    private ActionForward getListForward(boolean isSelf) {
        if (isSelf)
            return new ActionForward("listTravelApplication_self.do", true);
        else
            return new ActionForward("listTravelApplication_other.do", true);
    }

    /**
     * struts action method for viewing travel application
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TravelApplication ta = this.getTravelApplicationFromRequest(request);
        request.setAttribute("x_travelApplication", ta);

        this.putApproveListToRequest(ta, request);

        final boolean isSelf = this.isGlobal(request);
        if (isSelf) {
            this.checkRequestorIsSelf(ta, request);
            request.setAttribute("x_postfix", "_self");
        } else {
            this.checkCreatorIsSelf(ta, request);
            this.checkDepartment(ta.getDepartment(), request);
            request.setAttribute("x_postfix", "_other");
        }

        return mapping.findForward("page");
    }

    private void putHotelRoomToRequest(TravelApplication ta, HttpServletRequest request) {
        HotelManager hm = ServiceLocator.getHotelManager(request);
        List hotelList = hm.getEnabledHotelRoomList(ta.getToCity());
        request.setAttribute("x_hotelList", hotelList);

        UserManager um = ServiceLocator.getUserManager(request);
        UserSite userSite = um.getUserSite(ta.getRequestor().getId(), ta.getDepartment().getSite().getId());
        if (userSite == null)
            throw new ActionException("travelApplication.userSiteNotFound", ta.getRequestor().getId(), ta.getDepartment().getSite().getId());

        TravelGroup tg = userSite.getTravelGroup();
        TravelGroupManager tgm = ServiceLocator.getTravelGroupManager(request);
        TravelGroupDetail tgd = tgm.getHotelTravelGroupDetail(tg);

        boolean isLocal = false;
        if (userSite.getSite().getCity() != null) {
            isLocal = userSite.getSite().getCity().getProvince().getCountry().equals(ta.getToCity().getProvince().getCountry());
        }

        request.setAttribute("x_currency", ta.getDepartment().getSite().getBaseCurrency());
        if (isLocal) {
            if (tgd != null)
                request.setAttribute("x_limit", tgd.getAmountLimit());
        } else {
            if (tgd != null)
                request.setAttribute("x_limit", tgd.getAbroadAmountLimit());
        }

    }

    private void checkApprovedOrUrgent(TravelApplication ta) {
        if (!ta.getStatus().equals(TravelApplicationStatus.APPROVED) && !ta.getUrgent().equals(TravelApplicationUrgent.URGENT))
            throw new ActionException("travelApplication.purchase.notApprovedOrUrgent");
    }

    private void checkNA(TravelApplication ta) {
        if (!ta.getBookStatus().equals(TravelApplicationBookStatus.NA))
            throw new ActionException("travelApplication.purchase.notNA");
    }

    private void checkNotNA(TravelApplication ta) {
        if (ta.getBookStatus().equals(TravelApplicationBookStatus.NA))
            throw new ActionException("travelApplication.purchase.NA");
    }

    private void putPurchaseTypeListToRequest(Site site, HttpServletRequest request) {
        PurchaseTypeManager ptm = ServiceLocator.getPurchaseTypeManager(request);
        request.setAttribute("x_purchaseTypeList", ptm.getEnabledPurchaseTypeList(site));
    }

    /**
     * 出差申请相关的采购的struts方法
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward purchase(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TravelApplication ta = this.getTravelApplicationFromRequest(request);
        Site s = ta.getDepartment().getSite();
        this.checkDepartment(ta.getDepartment(), request);
        this.checkApprovedOrUrgent(ta);
        this.checkNA(ta);

        request.setAttribute("x_ta", ta);
        request.setAttribute("x_edit", Boolean.TRUE);

        this.putHotelRoomToRequest(ta, request);
        this.putEnumListToRequest(request);
        this.putPurchaseTypeListToRequest(s, request);
        this.putTravelApplicationDetailsToRequest(ta, request);

        BeanForm beanForm = (BeanForm) getForm("/purchaseTravelApplication_result", request);

        if (!isBack(request)) {

            beanForm.populateToForm(ta);

            this.processPurchaseHotelPrice(ta, beanForm);
        }

        return mapping.findForward("page");

    }

    private void processPurchaseHotelPrice(TravelApplication ta, BeanForm purchaseBeanForm) {
        if (ta.getHotel() == null)
            purchaseBeanForm.set("hotel_id", "-1");
        if (ta.getPrice() == null)
            purchaseBeanForm.set("price_id", "-1");
    }

    public ActionForward requestorViewPurchase(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        TravelApplication ta = this.getTravelApplicationFromRequest(request);
        this.checkRequestorOrCreatorIsSelf(ta, request);
        this.checkApprovedOrUrgent(ta);
        this.checkNotNA(ta);

        request.setAttribute("x_ta", ta);

        this.putApproveListToRequest(ta, request);

        if (ta.getTravellingMode().equals(TravellingMode.AIR)) {
            TravelApplicationManager tm = ServiceLocator.getTravelApplicationManager(request);
            List airTicketList = tm.getAirTicketList(ta);
            request.setAttribute("x_airTicketList", airTicketList);
        }

        request.setAttribute("x_requestor", Boolean.TRUE);
        this.putPurchaseTypeListToRequest(ta.getDepartment().getSite(), request);
        return mapping.findForward("page");

    }

    public ActionForward editPurchase(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TravelApplication ta = this.getTravelApplicationFromRequest(request);
        this.checkDepartment(ta.getDepartment(), request);
        this.checkApprovedOrUrgent(ta);
        this.checkNotNA(ta);

        request.setAttribute("x_ta", ta);

        this.putApproveListToRequest(ta, request);
        this.putPurchaseTypeListToRequest(ta.getDepartment().getSite(), request);

        if (ta.getTravellingMode().equals(TravellingMode.AIR)) {
            this.putTravelApplicationDetailsToRequest(ta, request);
            if (ta.getToDate().compareTo(new Date()) >= 0)
                this.setEditing(true, request);
        }
        return mapping.findForward("page");

    }

    public ActionForward updatePurchase(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TravelApplication ta = this.getTravelApplicationFromRequest(request);

        this.checkDepartment(ta.getDepartment(), request);
        this.checkApprovedOrUrgent(ta);
        this.checkNotNA(ta);

        boolean saveIsOnTravel = false;
        boolean setIsOnTravel = false;
        if (TravelApplicationBookStatus.RECEIVED.equals(ta.getBookStatus()) && YesNo.NO.equals(ta.getIsOnTravel())) {
            saveIsOnTravel = true;
            String isOnTravel = request.getParameter("isOnTravel");
            if (YesNo.YES.getEnumCode().toString().equals(isOnTravel)) {
                setIsOnTravel = true;
            }
        }

        boolean isAir = ta.getTravellingMode().equals(TravellingMode.AIR);
        boolean isBeforeToDate = ta.getToDate().compareTo(new Date()) >= 0;
        if (isAir && isBeforeToDate) {
            List airTicketList = this.getAirTicketListFromSession(request);
    
            airTicketEmail(ta, airTicketList, request);
    
            TravelApplicationManager tm = ServiceLocator.getTravelApplicationManager(request);
            if (setIsOnTravel) {
                ta = tm.updateAirTickets(ta.getId(), airTicketList, true, this.getCurrentUser(request));
            } else {
                ta = tm.updateAirTickets(ta.getId(), airTicketList, this.getCurrentUser(request));
            }
        } else  if (!saveIsOnTravel) {
            if (!isAir) {
                throw new ActionException("travelApplication.updatePurchase.notAir");
            }
            if (!isBeforeToDate) {
                throw new ActionException("travelApplication.updatePurchase.overToDate");
            }
        } else if (setIsOnTravel) {
            TravelApplicationManager tm = ServiceLocator.getTravelApplicationManager(request);
            ta.setIsOnTravel(YesNo.YES);
            tm.updateTravelApplication(ta);
        }
        if (ta.getBookStatus().equals(TravelApplicationBookStatus.NA))
            return new ActionForward("purchaseTravelApplication.do?id=" + ta.getId(), true);
        else
            return new ActionForward("editTravelApplicationPurchase.do?id=" + ta.getId(), true);

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.web.struts.action.business.RechargeAction#selectRechargeCustomer(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm,
     *      javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public ActionForward selectRechargeCustomer(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return super.selectRechargeCustomer(mapping, form, request, response);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.web.struts.action.business.RechargeAction#selectRechargeEntity(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm,
     *      javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public ActionForward selectRechargeEntity(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return super.selectRechargeEntity(mapping, form, request, response);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.web.struts.action.business.RechargeAction#selectRechargePerson(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm,
     *      javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public ActionForward selectRechargePerson(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return super.selectRechargePerson(mapping, form, request, response);
    }

    private AirTicket getAirTicketFromRequest(HttpServletRequest request) throws Exception {
        Integer id = ActionUtils.parseInt(request.getParameter("id"));
        AirTicketManager airTicketManager = ServiceLocator.getAirTicketManager(request);
        AirTicket airTicket = airTicketManager.getAirTicket(id);
        if (airTicket == null)
            throw new ActionException("airTicket.notFound", id);
        return airTicket;
    }

    private void checkBooked(AirTicket at) {
        if (!at.getStatus().equals(AirTicketStatus.BOOKED))
            throw new ActionException("travelApplication.purchase.airTicket.notBooked");

        TravelApplication ta = at.getTravelApplication();
        checkApprovedOrUrgent(ta);

        if (!ta.getBookStatus().equals(TravelApplicationBookStatus.BOOKED))
            throw new ActionException("travelApplication.purchase.airTicket.notBooked");
    }

    private void checkReceived(AirTicket at) {
        if (!at.getStatus().equals(AirTicketStatus.RECEIVED))
            throw new ActionException("travelApplication.purchase.airTicket.notReceived");

        TravelApplication ta = at.getTravelApplication();
        checkApprovedOrUrgent(ta);

    }

    /**
     * 确认机票的struts方法
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward confirmAirTicket(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        AirTicket at = this.getAirTicketFromRequest(request);
        this.checkRequestorOrCreatorIsSelf(at.getTravelApplication(), request);
        this.checkBooked(at);

        TravelApplicationManager tm = ServiceLocator.getTravelApplicationManager(request);
        at = tm.confirmAirTicket(at.getId());

        request.setAttribute("X_OBJECT", at);
        request.setAttribute("x_ta", at.getTravelApplication());
        request.setAttribute("X_ROWPAGE", "travelApplication/airTicketRow.jsp");
        this.putPurchaseTypeListToRequest(at.getTravelApplication().getDepartment().getSite(), request);
        request.setAttribute("x_requestor", Boolean.TRUE);
        return mapping.findForward("success");

    }

    /**
     * struts action method for canceling air ticket
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward cancelAirTicket(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        AirTicket at = this.getAirTicketFromRequest(request);
        this.checkRequestorOrCreatorIsSelf(at.getTravelApplication(), request);
        this.checkReceived(at);
        String sPrice = request.getParameter("price");
        if (sPrice == null) {
            request.setAttribute("x_at", at);

            return mapping.findForward("page");
        } else {
            BigDecimal price = new BigDecimal(sPrice);
            TravelApplicationManager tm = ServiceLocator.getTravelApplicationManager(request);
            at = tm.cancelAirTicket(at.getId(), price, this.getCurrentUser(request));

            request.setAttribute("X_OBJECT", at);
            request.setAttribute("x_ta", at.getTravelApplication());
            request.setAttribute("X_ROWPAGE", "travelApplication/airTicketRow.jsp");
            this.putPurchaseTypeListToRequest(at.getTravelApplication().getDepartment().getSite(), request);
            request.setAttribute("x_requestor", Boolean.TRUE);
            return mapping.findForward("success");
        }
    }

    /**
     * struts action method for deleting air ticket
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward deleteAirTicket(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        AirTicket at = this.getAirTicketFromRequest(request);
        this.checkDepartment(at.getTravelApplication().getDepartment(), request);

        this.checkBooked(at);

        TravelApplicationManager tm = ServiceLocator.getTravelApplicationManager(request);
        tm.deleteAirTicket(at.getId());

        this.postGlobalMessage("travelApplication.purchase.airTicket.delete.success", request.getSession());
        return getPurchaseForward(at.getTravelApplication());
    }

    /**
     * 采购结果
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward purchaseResult(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TravelApplication ta = this.getTravelApplicationFromRequest(request);
        this.checkDepartment(ta.getDepartment(), request);
        this.checkApprovedOrUrgent(ta);
        this.checkNA(ta);

        BeanForm beanForm = (BeanForm) form;
        beanForm.populateToBean(ta, request);

        List airTicketList = this.getAirTicketListFromSession(request);
        TravelApplicationManager tm = ServiceLocator.getTravelApplicationManager(request);
        tm.book(ta, airTicketList, this.getCurrentUser(request));

        if (ta.getTravellingMode().equals(TravellingMode.AIR)) {
            airTicketEmail(ta, airTicketList, request);
            this.postGlobalMessage("travelApplication.purchase.book.success", request.getSession());
        } else
            this.postGlobalMessage("travelApplication.purchase.confirm.success", request.getSession());
        return getViewPurchaseForward(ta);
    }

    private void airTicketEmail(TravelApplication ta, List airTicketList, HttpServletRequest request) {
        EmailManager em = ServiceLocator.getEmailManager(request);
        Map context = new HashMap();
        context.put("x_ta", ta);
        context.put("x_userName", ta.getCreator().getName());
        if (ta.getHotel() != null)
            context.put("x_hotelName", ta.getHotel().getName());
        else
            context.put("x_hotelName", ta.getHotelName());
        if (ta.getPrice() != null)
            context.put("x_roomDesc", ta.getPrice().getRoom());
        else
            context.put("x_roomDesc", ta.getRoomDescription());
        if (ta.getTravellingMode().equals(TravellingMode.AIR)) {
            context.put("x_airTicketList", airTicketList);
        }

        context.put("role", EmailManager.EMAIL_ROLE_CREATOR);
        em.insertEmail(ta.getLogSite(), ta.getCreator().getEmail(), "TABooked.vm", context);
        if (!ta.getCreator().equals(ta.getRequestor())) {
            context.put("x_userName", ta.getRequestor().getName());
            context.put("role", EmailManager.EMAIL_ROLE_REQUESTOR);
            em.insertEmail(ta.getLogSite(), ta.getRequestor().getEmail(), "TABooked.vm", context);
        }
    }

    private ActionForward getViewPurchaseForward(TravelApplication ta) {
        String url = "editTravelApplicationPurchase.do?id=" + ta.getId();
        return new ActionForward(url, true);
    }

    private ActionForward getPurchaseForward(TravelApplication ta) {
        String url = "purchaseTravelApplication.do?id=" + ta.getId();
        return new ActionForward(url, true);
    }

    /**
     * 采购列表
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward listPurchase(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List grantedSiteList = getAndCheckGrantedSiteDeparmentList(request);

        TravelApplicationManager fm = ServiceLocator.getTravelApplicationManager(request);

        TravelApplicationQueryForm queryForm = (TravelApplicationQueryForm) form;

        if (StringUtils.isEmpty(queryForm.getOrder())) {
            Site site = (Site)grantedSiteList.get(0);
            queryForm.setSite_id(site.getId().toString());
            Department dep = (Department)site.getDepartments().get(0);
            queryForm.setDepartment_id(dep.getId().toString());
            queryForm.setBookStatus(TravelApplicationBookStatus.NA.getEnumCode().toString());
            queryForm.setUrgent(TravelApplicationUrgent.URGENT.getEnumCode().toString());

            queryForm.setOrder(TravelApplicationQueryOrder.ID.getName());
            queryForm.setDescend(false);
        } else {
            if (TravelApplicationQueryOrder.getEnum(queryForm.getOrder()) == null)
                throw new RuntimeException("order not found");
        }

        Map conditions = constructQueryMap(queryForm, request);

        conditions.put(TravelApplicationQueryCondition.STATUS_EQ_OR_URGENT_EQ, new Object[] { TravelApplicationStatus.APPROVED.getEnumCode(),
                TravelApplicationUrgent.URGENT });

        conditions.put(TravelApplicationQueryCondition.BOOKER_NULL_OR_ID_EQ, this.getCurrentUser(request));

        String exportType = queryForm.getExportType();
        if (StringUtils.isNotEmpty(exportType)) {
            List data = fm.getTravelApplicationList(conditions, 0, -1, TravelApplicationQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend());

            int index = SessionTempFile.createNewTempFile(request);
            String fileName = "travelApplication";
            String suffix = ExportUtil.export(exportType, data, request, new FileOutputStream(SessionTempFile.getTempFile(index, request)), new Exportable() {

                public void exportHead(List row, HttpServletRequest request) throws Exception {
                    MessageResources messages = getResources(request);
                    row.add(messages.getMessage(getLocale(request), "travelApplication.id"));
                    row.add(messages.getMessage(getLocale(request), "travelApplication.creator"));
                    row.add(messages.getMessage(getLocale(request), "travelApplication.requestor"));
                    row.add(messages.getMessage(getLocale(request), "travelApplication.department.id"));
                    row.add(messages.getMessage(getLocale(request), "travelApplication.toCity.id"));
                    row.add(messages.getMessage(getLocale(request), "travelApplication.travellingMode"));
                    row.add(messages.getMessage(getLocale(request), "travelApplication.requestDate"));
                    row.add(messages.getMessage(getLocale(request), "travelApplication.bookStatus"));
                }

                public void exportRow(List row, Object data, HttpServletRequest request) throws Exception {
                    row.add(BeanHelper.getBeanPropertyValue(data, "id"));

                    row.add(BeanHelper.getBeanPropertyValue(data, "creator.name"));
                    row.add(BeanHelper.getBeanPropertyValue(data, "requestor.name"));

                    row.add(BeanHelper.getBeanPropertyValue(data, "department.name"));

                    if (isEnglish(request))
                        row.add(BeanHelper.getBeanPropertyValue(data, "toCity.engName"));
                    else
                        row.add(BeanHelper.getBeanPropertyValue(data, "toCity.chnName"));

                    if (isEnglish(request))
                        row.add(BeanHelper.getBeanPropertyValue(data, "travellingMode.engShortDescription"));
                    else
                        row.add(BeanHelper.getBeanPropertyValue(data, "travellingMode.chnShortDescription"));

                    TravelApplication ta = (TravelApplication) data;

                    if (ta.getRequestDate() != null)
                        row.add(ActionUtils.getDisplayDateFromDate(ta.getRequestDate()));
                    else
                        row.add("");

                    if (isEnglish(request))
                        row.add(BeanHelper.getBeanPropertyValue(data, "bookStatus.engShortDescription"));
                    else
                        row.add(BeanHelper.getBeanPropertyValue(data, "bookStatus.chnShortDescription"));

                }
            });
            return new ActionForward("download/" + index + "/" + URLEncoder.encode(fileName, "UTF-8") + '.' + suffix, true);
        }

        if (queryForm.isFirstInit()) {
            queryForm.init(fm.getTravelApplicationListCount(conditions));
        } else {
            queryForm.init();
        }

        List result = fm.getTravelApplicationList(conditions, queryForm.getPageNoAsInt(), queryForm.getPageSizeAsInt(), TravelApplicationQueryOrder
                .getEnum(queryForm.getOrder()), queryForm.isDescend());

        request.setAttribute("X_RESULTLIST", result);

        putEnumListToRequest(request);

        request.setAttribute("x_travelApplicationBookStatusList", TravelApplicationBookStatus.getEnumList(TravelApplicationBookStatus.class));
        request.setAttribute("x_siteList", grantedSiteList);

        this.putUrgentListToRequest(request);

        return mapping.findForward("page");
    }

    private void listAssignPageProcess(TravelApplicationQueryForm queryForm, HttpServletRequest request) throws IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {
        UserManager um = ServiceLocator.getUserManager(request);
        List siteList = um.getSiteOfGrantedSiteDeparmentList(this.getCurrentUser(request), this.getFunction(request));
        if (siteList.isEmpty()) {
            throw new ActionException("errors.noDepartmentPermission");
        }
        ComboBox comboSite = new ComboBox("id", "name");
        comboSite.setValue(queryForm.getSite_id());
        comboSite.setList(siteList);
        Site site = (Site) comboSite.getSelectedItem();
        queryForm.setSite_id(site.getId().toString());

        List departmentList = um.getGrantedDepartmentListOfSite(this.getCurrentUser(request), site, this.getFunction(request));

        if (queryForm.getDepartment_id() == null) {
            for (Iterator itor = departmentList.iterator(); itor.hasNext();) {
                Department d = (Department) itor.next();
                if (d.isGranted()) {
                    queryForm.setDepartment_id(d.getId().toString());
                    break;
                }
            }
        }

        ComboBox comboDepartment = new ComboBox("id", "name");
        comboDepartment.setValue(queryForm.getDepartment_id());
        comboDepartment.setList(departmentList);
        Department d = (Department) comboDepartment.getSelectedItem();
        if (!d.isGranted()) {
            throw new ActionException("errors.noDepartmentPermission");
        }
        queryForm.setDepartment_id(d.getId().toString());

        request.setAttribute("x_siteList", siteList);
        request.setAttribute("x_departmentList", departmentList);

    }

    public ActionForward assignResult(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TravelApplication ta = this.getTravelApplicationFromRequest(request);
        this.checkApprovedOrUrgent(ta);
        this.checkDepartment(ta.getDepartment(), request);
        this.checkNotReceived(ta);

        BeanForm beanForm = (BeanForm) form;
        beanForm.setBeanLoader(ServiceLocator.getBeanLoader(request));
        beanForm.populateToBean(ta, request);

        /**
         * 不必检查Booker是否为空，form验证已保证
         */
        /*
         * if (ta.getBooker() == null) throw new
         * ActionException("travelApplication.assign.bookerNotFound",
         * beanForm.getString("booker_id"));
         */

        /**
         * 不必检查设置的Booker是否真有Booker权限，只需要检查当前用户是否有权assign
         */
        /*
         * UserManager um = ServiceLocator.getUserManager(request); if
         * (!um.hasSitePower(ta.getDepartment().getSite(), ta.getBooker(),
         * this.getPurchaseFunction(request))) throw new
         * ActionException("travelApplication.assign.bookerNoPower");
         */

        TravelApplicationManager tm = ServiceLocator.getTravelApplicationManager(request);
        tm.assignBooker(ta.getId(), ta.getBooker());

        EmailManager em = ServiceLocator.getEmailManager(request);
        Map context = new HashMap();
        context.put("x_ta", ta);
        context.put("role", EmailManager.EMAIL_ROLE_PURCHASER);
        em.insertEmail(ta.getDepartment().getSite(), ta.getBooker().getEmail(), "TAAssignee.vm", context);

        this.postGlobalMessage("travelApplication.assign.success", request.getSession());
        return new ActionForward("assignTravelApplication.do?id=" + ta.getId(), true);

    }

    private Function getPurchaseFunction(HttpServletRequest request) {
        FunctionManager fm = ServiceLocator.getFunctionManager(request);
        Function f = fm.getFunction("TravelApplicationPurchase");
        if (f == null)
            throw new ActionException("function.typeNotFound", "TravelApplicationPurchase");
        return f;
    }

    private void putBookerListToRequest(Site site, HttpServletRequest request) {
        UserManager um = ServiceLocator.getUserManager(request);
        request.setAttribute("x_bookerList", um.getEnabledUserList(this.getPurchaseFunction(request), site));
    }

    private void checkNotReceived(TravelApplication ta) {
        if (ta.getBookStatus().equals(TravelApplicationBookStatus.RECEIVED))
            throw new ActionException("travelApplication.assign.isReceived");
    }

    public ActionForward assign(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TravelApplication ta = this.getTravelApplicationFromRequest(request);
        this.checkApprovedOrUrgent(ta);
        this.checkDepartment(ta.getDepartment(), request);
        this.checkNotReceived(ta);

        putBookerListToRequest(ta.getDepartment().getSite(), request);

        request.setAttribute("x_travelApplication", ta);

        if (!this.isBack(request)) {
            BeanForm beanForm = (BeanForm) this.getForm("/assignTravelApplication_result", request);
            beanForm.populateToForm(ta);
        }

        this.putApproveListToRequest(ta, request);
        request.setAttribute("x_postfix", "_assign");
        return mapping.findForward("page");
    }

    public ActionForward listAssign(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TravelApplicationManager fm = ServiceLocator.getTravelApplicationManager(request);

        TravelApplicationQueryForm queryForm = (TravelApplicationQueryForm) form;

        if (StringUtils.isEmpty(queryForm.getOrder())) {
            queryForm.setOrder(TravelApplicationQueryOrder.ID.getName());
            queryForm.setDescend(false);
        } else {
            if (TravelApplicationQueryOrder.getEnum(queryForm.getOrder()) == null)
                throw new RuntimeException("order not found");
        }

        listAssignPageProcess(queryForm, request);

        Map conditions = constructQueryMap(queryForm, request);

        conditions.put(TravelApplicationQueryCondition.STATUS_EQ_OR_URGENT_EQ, new Object[] { TravelApplicationStatus.APPROVED.getEnumCode(),
                TravelApplicationUrgent.URGENT });

        conditions.put(TravelApplicationQueryCondition.BOOKSTATUS_NOT_EQ, TravelApplicationBookStatus.RECEIVED);

        if (queryForm.isFirstInit()) {
            queryForm.init(fm.getTravelApplicationListCount(conditions));
        } else {
            queryForm.init();
        }

        List result = fm.getTravelApplicationList(conditions, queryForm.getPageNoAsInt(), queryForm.getPageSizeAsInt(), TravelApplicationQueryOrder
                .getEnum(queryForm.getOrder()), queryForm.isDescend());

        request.setAttribute("X_RESULTLIST", result);

        putEnumListToRequest(request);

        List taBookStatusList = TravelApplicationBookStatus.getEnumList(TravelApplicationBookStatus.class);
        taBookStatusList.remove(TravelApplicationBookStatus.RECEIVED);
        request.setAttribute("x_travelApplicationBookStatusList", taBookStatusList);

        this.putUrgentListToRequest(request);

        return mapping.findForward("page");
    }

    private void putUrgentListToRequest(HttpServletRequest request) {
        request.setAttribute("x_taUrgentList", TravelApplicationUrgent.getEnumList(TravelApplicationUrgent.class));
    }

    /**
     * action method for TravelApplication report
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward report(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List siteList = this.getGrantedSiteDeparmentList(request);
        request.setAttribute("x_siteList", siteList);

        TravelApplicationQueryForm queryForm = (TravelApplicationQueryForm) form;

        if (StringUtils.isEmpty(queryForm.getOrder())) {
            queryForm.setOrder(TravelApplicationQueryOrder.ID.getName());
            queryForm.setDescend(false);

            Site firstSite = (Site) siteList.get(0);
            queryForm.setSite_id(firstSite.getId().toString());

            //Department firstDepartment = (Department) firstSite.getDepartments().get(0);
            //queryForm.setDepartment_id(firstDepartment.getId().toString());

        } else if (TravelApplicationQueryOrder.getEnum(queryForm.getOrder()) == null)
            throw new RuntimeException("order not found!");

        Map conditions = constructQueryMap(queryForm, request);

        TravelApplicationManager tam = ServiceLocator.getTravelApplicationManager(request);

        String exportType = queryForm.getExportType();
        if (exportType != null && exportType.length() > 0) {
            List data = tam.getTravelApplicationList(conditions, 0, -1, TravelApplicationQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend());
            if ("pdf".equalsIgnoreCase(exportType)) {
                exportPDF(ActionUtils.parseInt(queryForm.getSite_id()), data, "travelApplication", request, response);
                return null;
            }
            int index = SessionTempFile.createNewTempFile(request);
            String fileName = "travelApplication";
            String suffix = ExportUtil.export(exportType, data, request, new FileOutputStream(SessionTempFile.getTempFile(index, request)), new Exportable() {

                private String getMessage(String key, HttpServletRequest request) {
                    MessageResources messages = getResources(request);
                    Locale locale = getLocale(request);
                    return messages.getMessage(locale, key);
                }

                public void exportHead(List row, HttpServletRequest request) throws Exception {
                    row.add(this.getMessage("travelApplication.id", request));
                    row.add(this.getMessage("travelApplication.requestor.id", request));
                    row.add(this.getMessage("travelApplication.department.id", request));
                    row.add(this.getMessage("travelApplication.toCity.id", request));
                    row.add(this.getMessage("travelApplication.travellingMode", request));
                    row.add(this.getMessage("travelApplication.createDate", request));
                    row.add(this.getMessage("travelApplication.urgent", request));
                    row.add(this.getMessage("travelApplication.status", request));
                    row.add(this.getMessage("travelApplication.bookStatus", request));
                    row.add(this.getMessage("travelApplication.approvalDuration", request));
                }

                public void exportRow(List row, Object data, HttpServletRequest request) throws Exception {
                    TravelApplication ta = (TravelApplication) data;
                    row.add(ta.getId());
                    row.add(ta.getRequestor().getName());
                    row.add(ta.getDepartment().getName());
                    if (isEnglish(request)) {
                        row.add(ta.getToCity().getEngName());
                    } else {
                        row.add(ta.getToCity().getChnName());
                    }
                    row.add(getLocaleShortDescription(ta.getTravellingMode(), request));
                    row.add(ActionUtils.getDisplayDateFromDate(ta.getCreateDate()));
                    row.add(getLocaleShortDescription(ta.getUrgent(), request));
                    row.add(getLocaleShortDescription(ta.getStatus(), request));
                    row.add(getLocaleShortDescription(ta.getBookStatus(), request));
                    row.add(ta.getApproveDurationDay());
                }
            });
            return new ActionForward("download/" + index + "/" + URLEncoder.encode(fileName, "UTF-8") + '.' + suffix, true);
        }

        if (queryForm.isFirstInit()) {
            queryForm.init(tam.getTravelApplicationListCount(conditions));
        } else {
            queryForm.init();
        }

        List result = tam.getTravelApplicationList(conditions, queryForm.getPageNoAsInt(), queryForm.getPageSizeAsInt(), TravelApplicationQueryOrder
                .getEnum(queryForm.getOrder()), queryForm.isDescend());

        request.setAttribute("X_RESULTLIST", result);
        request.setAttribute("x_postfix", "_report");
        this.putEnumListToRequest(request);

        request.setAttribute("x_travelApplicationBookStatusList", TravelApplicationBookStatus.getEnumList(TravelApplicationBookStatus.class));
        return mapping.findForward("page");
    }

    public ActionForward exportDetailPDF_self(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        TravelApplication ta = this.getTravelApplicationFromRequest(request);
        this.checkRequestorIsSelf(ta, request);
        exportDetailPDF(ta, request, true, response);
        return null;
    }

    public ActionForward exportDetailPDF_other(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        TravelApplication ta = this.getTravelApplicationFromRequest(request);
        this.checkCreatorIsSelf(ta, request);
        this.checkDepartment(ta.getDepartment(), request);
        exportDetailPDF(ta, request, false, response);
        return null;
    }

    private void exportDetailPDF(TravelApplication ta, HttpServletRequest request, boolean self, HttpServletResponse response) throws Exception {
        MessageResources messages = getResources(request);
        Locale locale = getLocale(request);

        PDFReport report = PDFReport.createReport(ta.getDepartment().getSite().getId(), self ? "travelApplication.pdf.title.self"
                : "travelApplication.pdf.pdf.title.other", request, messages, locale);
        Document document = report.getDocument();

        PdfPTable table = PDFReport.createTable(new float[] { 1, 2, 1, 2 }, 100, 0);

        Color tColor = new Color(0, 0x33, 0x66);
        report.addCell(table, "travelGroup.site", tColor, true);
        report.addCell(table, ta.getDepartment().getSite().getName());
        report.addCell(table, null);
        report.addCell(table, null);
        report.addCell(table, "travelGroup.department", tColor, true);
        report.addCell(table, ta.getDepartment().getName());
        report.addCell(table, null);
        report.addCell(table, null);
        report.addCell(table, "travelApplication.requestor", tColor, true);
        report.addCell(table, ta.getRequestor().getName());
        report.addCell(table, "travelApplication.requestDate", tColor, true);
        report.addCell(table, ta.getRequestDate());
        report.addCell(table, "travelApplication.creator", tColor, true);
        report.addCell(table, ta.getCreator().getName());
        report.addCell(table, "travelApplication.createDate", tColor, true);
        report.addCell(table, ta.getCreateDate());
        document.add(table);

        table = PDFReport.createTable(new float[] { 1, 2, 1, 2 }, 100, 0);

        report.addCell(table, "travelApplication.id", tColor, true);
        Font idFont = PDFReport.getFont(Font.BOLD, Color.BLUE);
        report.addCell(table, ta.getId(), idFont);
        report.addCell(table, null);
        report.addCell(table, null);
        report.addCell(table, "travelApplication.title", tColor, true);
        report.addCell(table, ta.getTitle());
        report.addCell(table, null);
        report.addCell(table, null);
        report.addCell(table, "travelApplication.description", tColor, true);
        report.addCell(table, ta.getDescription());
        report.addCell(table, null);
        report.addCell(table, null);
        report.addCell(table, "travelApplication.urgent", tColor, true);
        report.addCell(table, ta.getUrgent());
        report.addCell(table, "travelApplication.status", tColor, true);
        report.addCell(table, ta.getStatus());
        document.add(table);

        table = PDFReport.createTable(new float[] { 1, 2, 1, 2 }, 100, 0);

        report.addCell(table, null);
        report.addCell(table, "travelApplication.from", tColor, true);
        report.addCell(table, "travelApplication.to", 2, tColor, true);

        report.addCell(table, "travelApplication.country", tColor, true);
        if (isEnglish(request)) {
            report.addCell(table, ta.getFromCity().getProvince().getCountry().getEngName());
            report.addCell(table, ta.getToCity().getProvince().getCountry().getEngName(), 2);
        } else {
            report.addCell(table, ta.getFromCity().getProvince().getCountry().getChnName());
            report.addCell(table, ta.getToCity().getProvince().getCountry().getChnName(), 2);
        }

        report.addCell(table, "travelApplication.province", tColor, true);
        if (isEnglish(request)) {
            report.addCell(table, ta.getFromCity().getProvince().getEngName());
            report.addCell(table, ta.getToCity().getProvince().getEngName(), 2);
        } else {
            report.addCell(table, ta.getFromCity().getProvince().getChnName());
            report.addCell(table, ta.getToCity().getProvince().getChnName(), 2);
        }

        report.addCell(table, "travelApplication.city", tColor, true);
        if (isEnglish(request)) {
            report.addCell(table, ta.getFromCity().getEngName());
            report.addCell(table, ta.getToCity().getEngName());
        } else {
            report.addCell(table, ta.getFromCity().getChnName());
            report.addCell(table, ta.getToCity().getChnName());
        }
        document.add(table);
        table = PDFReport.createTable(new float[] { 1, 2, 1, 2 }, 100, 0);

        report.addCell(table, "travelApplication.hotel.id", tColor, true);
        if (ta.getHotel() != null)
            report.addCell(table, ta.getHotel().getName());
        else
            report.addCell(table, ta.getHotelName());
        report.addCell(table, null);
        report.addCell(table, null);

        report.addCell(table, "travelApplication.roomDescription", tColor, true);
        if (ta.getPrice() != null)
            report.addCell(table, ta.getPrice().getRoom());
        else
            report.addCell(table, ta.getRoomDescription());
        report.addCell(table, null);
        report.addCell(table, null);

        report.addCell(table, "travelApplication.checkInDate", tColor, true);
        report.addCell(table, ta.getCheckInDate());
        report.addCell(table, "travelApplication.checkOutDate", tColor, true);
        report.addCell(table, ta.getCheckOutDate());
        document.add(table);
        table = PDFReport.createTable(new float[] { 1, 2, 1, 2 }, 100, 0);
        report.addCell(table, "travelApplication.travellingMode", tColor, true);
        report.addCell(table, ta.getTravellingMode());
        report.addCell(table, "travelApplication.singleOrReturn", tColor, true);
        report.addCell(table, ta.getSingleOrReturn());

        report.addCell(table, "travelApplication.duration", tColor, true);
        StringBuffer sb = new StringBuffer();
        if (ta.getFromDate() != null)
            sb.append(ActionUtils.getDisplayDateFromDate(ta.getFromDate()));
        sb.append("~");
        if (ta.getToDate() != null)
            sb.append(ActionUtils.getDisplayDateFromDate(ta.getToDate()));
        report.addCell(table, sb.toString());
        report.addCell(table, null);
        report.addCell(table, null);

        report.addCell(table, "travelApplication.fromTime", tColor, true);
        if (ta.getFromTime() != null)
            report.addCell(table, dfDisplayTime.format(ta.getFromTime()));
        else
            report.addCell(table, null);

        report.addCell(table, "travelApplication.toTime", tColor, true);
        if (ta.getToTime() != null)
            report.addCell(table, dfDisplayTime.format(ta.getToTime()));
        else
            report.addCell(table, null);
        document.add(table);

        if (ta.getApproveRequestId() != null) {
            TravelApplicationApproveRequestManager tam = ServiceLocator.getTravelApplicationApproveRequestManager(request);
            report.addApproveListTable(tam.getTravelApplicationApproveRequestListByApproveRequestId(ta.getApproveRequestId()));
        }

        report.output("expense", response);
    }

    private static DateFormatter dfDisplayTime = new DateFormatter(java.util.Date.class, "HH:mm");

    private void exportPDF(Integer siteId, List data, String filename, HttpServletRequest request, HttpServletResponse response) throws DocumentException,
            IOException {
        MessageResources messages = getResources(request);
        Locale locale = getLocale(request);

        PDFReport report = PDFReport.createReport(siteId, "travelApplication.report.title", request, messages, locale);

        Document document = report.getDocument();

        PdfPTable table = PDFReport.createTable(new float[] { 7, 4, 10, 4, 4, 4, 4, 4, 4, 4 }, 100, 0.5f);
        table.setHeaderRows(1);
        PdfPCell defaultCell = table.getDefaultCell();
        Color defaultBackgroundColor = defaultCell.backgroundColor();

        defaultCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        defaultCell.setBackgroundColor(new Color(0x99, 0x99, 0xff));

        Font headFont = PDFReport.getFont(Font.BOLD, Color.BLACK);
        report.addCell(table, "travelApplication.id", headFont, true);
        report.addCell(table, "travelApplication.requestor.id", headFont, true);
        report.addCell(table, "travelApplication.department.id", headFont, true);
        report.addCell(table, "travelApplication.toCity.id", headFont, true);
        report.addCell(table, "travelApplication.travellingMode", headFont, true);
        report.addCell(table, "travelApplication.createDate", headFont, true);
        report.addCell(table, "travelApplication.urgent", headFont, true);
        report.addCell(table, "travelApplication.status", headFont, true);
        report.addCell(table, "travelApplication.bookStatus", headFont, true);
        report.addCell(table, "travelApplication.approvalDuration", headFont, true);

        defaultCell.setBackgroundColor(defaultBackgroundColor);

        for (Iterator itor = data.iterator(); itor.hasNext();) {
            TravelApplication ta = (TravelApplication) itor.next();
            report.addCell(table, ta.getId());
            report.addCell(table, ta.getRequestor().getName());
            report.addCell(table, ta.getDepartment().getName());
            if (isEnglish(request)) {
                report.addCell(table, ta.getToCity().getEngName());
            } else {
                report.addCell(table, ta.getToCity().getChnName());
            }
            report.addCell(table, ta.getTravellingMode());
            report.addCell(table, ta.getCreateDate());
            report.addCell(table, ta.getUrgent());
            report.addCell(table, ta.getStatus());
            report.addCell(table, ta.getBookStatus());
            report.addCell(table, ta.getApproveDurationDay());
        }

        document.add(table);

        report.output(filename, response);
    }

    /**
     * struts action method for viewing travel application
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward viewReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TravelApplication ta = this.getTravelApplicationFromRequest(request);
        this.checkDepartment(ta.getDepartment(), request);
        request.setAttribute("x_travelApplication", ta);
        this.putApproveListToRequest(ta, request);
        request.setAttribute("x_postfix", "_other");
        return mapping.findForward("page");
    }

    public ActionForward withdraw_self(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TravelApplication ta = this.getTravelApplicationFromRequest(request);
        if (!ta.getStatus().equals(TravelApplicationStatus.PENDING))
            throw new ActionException("travelApplication.withdraw.notPending");

        this.checkRequestorIsSelf(ta, request);

        TravelApplicationManager tam = ServiceLocator.getTravelApplicationManager(request);
        tam.withdrawTravelApplication(ta, this.getCurrentUser(request));
        return this.getEditForwardFor(ta, true);
    }

    public ActionForward withdraw_other(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TravelApplication ta = this.getTravelApplicationFromRequest(request);
        if (!ta.getStatus().equals(TravelApplicationStatus.PENDING))
            throw new ActionException("travelApplication.withdraw.notPending");

        this.checkCreatorIsSelf(ta, request);
        this.checkDepartment(ta.getDepartment(), request);

        TravelApplicationManager tam = ServiceLocator.getTravelApplicationManager(request);
        tam.withdrawTravelApplication(ta, this.getCurrentUser(request));
        return this.getEditForwardFor(ta, false);
    }

    /**
     * @deprecated
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward savePurchase(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TravelApplication ta = getTravelApplicationFromRequest(request);
        String isOnTravel = request.getParameter("isOnTravel");
        if (YesNo.YES.getEnumCode().toString().equals(isOnTravel)) {
            ta.setIsOnTravel(YesNo.YES);
        } else {
            ta.setIsOnTravel(YesNo.NO);
        }
        TravelApplicationManager travelApplicationManager = ServiceLocator.getTravelApplicationManager(request);
        travelApplicationManager.updateTravelApplication(ta);
        return mapping.findForward("viewPurchase");
    }
}