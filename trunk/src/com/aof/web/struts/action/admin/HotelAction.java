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
import java.util.Iterator;
import java.util.List;
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

import com.aof.model.admin.Function;
import com.aof.model.admin.Hotel;
import com.aof.model.admin.Site;
import com.aof.model.admin.User;
import com.aof.model.admin.query.HotelContractQueryCondition;
import com.aof.model.admin.query.HotelContractQueryOrder;
import com.aof.model.admin.query.HotelQueryCondition;
import com.aof.model.admin.query.HotelQueryOrder;
import com.aof.model.admin.query.PriceQueryCondition;
import com.aof.model.admin.query.PriceQueryOrder;
import com.aof.model.metadata.EnabledDisabled;
import com.aof.model.metadata.HotelLevel;
import com.aof.model.metadata.HotelPromoteStatus;

import com.aof.service.admin.CountryManager;
import com.aof.service.admin.CurrencyManager;
import com.aof.service.admin.EmailManager;
import com.aof.service.admin.FunctionManager;
import com.aof.service.admin.HotelContractManager;
import com.aof.service.admin.HotelManager;
import com.aof.service.admin.PriceManager;
import com.aof.service.admin.UserManager;
import com.aof.utils.SessionTempFile;
import com.aof.web.struts.action.BaseAction;
import com.aof.web.struts.action.ServiceLocator;
import com.aof.web.struts.form.admin.HotelQueryForm;
import com.shcnc.hibernate.PersistentEnum;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.action.ActionUtils;
import com.shcnc.struts.form.BeanForm;
import com.shcnc.utils.BeanHelper;
import com.shcnc.utils.ExportUtil;
import com.shcnc.utils.Exportable;

/**
 * the action class for domain model hotel
 * 
 * @author shilei
 * @version 1.0 (Nov 15, 2005)
 */
public class HotelAction extends BaseAction {

    private List getEnabledCountryProvinceCityList(HttpServletRequest request) {
        CountryManager cm = ServiceLocator.getCountryManager(request);
        return cm.listEnabledCountryProvinceCity();
    }

    private void putEnabledCountryProvinceCityListToRequest(HttpServletRequest request) {
        List countryList = getEnabledCountryProvinceCityList(request);
        request.setAttribute("x_countryList", countryList);
    }

    /**
     * the action method for searching hotel
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        List siteList = this.getAndCheckGrantedSiteList(request);
        
        //if (this.isGlobal(request)) {
        //siteList.add(0, new Site());
        //}
        HotelQueryForm queryForm = (HotelQueryForm) form;
        
        if (this.isSite(request))
        {
            if(queryForm.getSite_id() == null)
            {
                Site firstSite=(Site) siteList.get(0);
                
                queryForm.setSite_id(firstSite.getId().toString());
            }
        }
        
        HotelManager fm = ServiceLocator.getHotelManager(request);

        //if (this.isSite(request)) {
           //conditions.put(HotelQueryCondition.PROMOTESTATUS_NOTEQ, HotelPromoteStatus.GLOBAL.getEnumCode());
        //}

        if (StringUtils.isEmpty(queryForm.getOrder())) {
            queryForm.setEnabled(EnabledDisabled.ENABLED.toString());
            queryForm.setOrder(HotelQueryOrder.NAME.getName());
            queryForm.setDescend(false);
        }
        
        Map conditions = constructQueryMap(queryForm);

        String exportType = queryForm.getExportType();
        if (StringUtils.isNotEmpty(exportType)) {
            List data = fm.getHotelList(conditions, 0, -1, HotelQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend());

            int index = SessionTempFile.createNewTempFile(request);
            String fileName = "hotel";
            String suffix = ExportUtil.export(exportType, data, request, new FileOutputStream(SessionTempFile.getTempFile(index, request)), new Exportable() {

                public void exportHead(List row, HttpServletRequest request) throws Exception {
                    MessageResources messages = getResources(request);
                    row.add(messages.getMessage(getLocale(request), "hotel.name"));
                    if (isGlobal(request)) 
                        row.add(messages.getMessage(getLocale(request), "hotel.site"));
                    row.add(messages.getMessage(getLocale(request), "hotel.country"));
                    row.add(messages.getMessage(getLocale(request), "hotel.province"));
                    row.add(messages.getMessage(getLocale(request), "hotel.city"));
                    row.add(messages.getMessage(getLocale(request), "hotel.telephone"));
                    row.add(messages.getMessage(getLocale(request), "hotel.level"));
                    row.add(messages.getMessage(getLocale(request), "hotel.promoteStatus"));
                    row.add(messages.getMessage(getLocale(request), "hotel.status"));
                }

                public void exportRow(List row, Object data, HttpServletRequest request) throws Exception {
                    row.add(BeanHelper.getBeanPropertyValue(data, "name"));
                    if (isGlobal(request)) 
                        row.add(BeanHelper.getBeanPropertyValue(data, "site.name"));
                    
                    if(isEnglish(request))
                        row.add(BeanHelper.getBeanPropertyValue(data, "city.province.country.engName"));
                    else
                        row.add(BeanHelper.getBeanPropertyValue(data, "city.province.country.chnName"));
                    
                    if(isEnglish(request))
                        row.add(BeanHelper.getBeanPropertyValue(data, "city.province.engName"));
                    else
                        row.add(BeanHelper.getBeanPropertyValue(data, "city.province.chnName"));
                    
                    if(isEnglish(request))
                        row.add(BeanHelper.getBeanPropertyValue(data, "city.engName"));
                    else
                        row.add(BeanHelper.getBeanPropertyValue(data, "city.chnName"));
                    
                    row.add(BeanHelper.getBeanPropertyValue(data, "telephone"));
                    
                    if(isEnglish(request))
                        row.add(BeanHelper.getBeanPropertyValue(data, "promoteStatus.engShortDescription"));
                    else
                        row.add(BeanHelper.getBeanPropertyValue(data, "promoteStatus.chnShortDescription"));
                    
                    if (isEnglish(request))
                        row.add(BeanHelper.getBeanPropertyValue(data, "enabled.engShortDescription"));
                    else
                        row.add(BeanHelper.getBeanPropertyValue(data, "enabled.chnShortDescription"));
                    
                }
            });
            return new ActionForward("download/" + index + "/" + URLEncoder.encode(fileName, "UTF-8") + '.' + suffix, true);
        }

        if (queryForm.isFirstInit()) {
            queryForm.init(fm.getHotelListCount(conditions));
        } else {
            queryForm.init();
        }
        int pageNo = queryForm.getPageNoAsInt();
        int pageSize = queryForm.getPageSizeAsInt();

        List result = fm.getHotelList(conditions, pageNo, pageSize, HotelQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend());
        request.setAttribute("X_RESULTLIST", result);
        putEnumListToRequest(request);

        request.setAttribute("x_siteList", siteList);

        return mapping.findForward("page");
    }

    private Map constructQueryMap(HotelQueryForm queryForm) {
        Map conditions = new HashMap();

        
        Integer promoteStatus=ActionUtils.parseInt(queryForm.getPromoteStatus());
        if(promoteStatus!=null)
        {
            conditions.put(HotelQueryCondition.PROMOTESTATUS_EQ, promoteStatus);
        }
        
        /* id */
        Integer id = ActionUtils.parseInt(queryForm.getId());
        if (id != null) {
            conditions.put(HotelQueryCondition.ID_EQ, id);
        }

        /* keyPropertyList */

        /* kmtoIdList */

        /* mtoIdList */
        Integer site_id = ActionUtils.parseInt(queryForm.getSite_id());
        if (site_id != null) {
            conditions.put(HotelQueryCondition.SITE_ID_EQ, site_id);
            conditions.put(HotelQueryCondition.PROMOTESTATUS_NOTEQ, HotelPromoteStatus.GLOBAL.toString());
        } else {
            conditions.put(HotelQueryCondition.PROMOTESTATUS_EQ, HotelPromoteStatus.GLOBAL.toString());
        }
   
        String currency_code = queryForm.getCurrency_code();
        if (currency_code != null && currency_code.trim().length() != 0) {
            conditions.put(HotelQueryCondition.CURRENCY_CODE_EQ, currency_code);
        }
        
        
        Integer city_id = ActionUtils.parseInt(queryForm.getCity_id());
        Integer province_id = ActionUtils.parseInt(queryForm.getProvince_id());
        Integer country_id = ActionUtils.parseInt(queryForm.getCountry_id());
        if (city_id != null) {
            conditions.put(HotelQueryCondition.CITY_ID_EQ, city_id);
        }
        else if(province_id!=null)
        {
            conditions.put(HotelQueryCondition.PROVINCE_ID_EQ, province_id);
        }
        else if (country_id != null) {
            conditions.put(HotelQueryCondition.COUNTRY_ID_EQ, country_id);
        }
        

        Integer enabled=ActionUtils.parseInt(queryForm.getEnabled());
        if (enabled!=null) {
            conditions.put(HotelQueryCondition.ENABLED_EQ, enabled);
        }
        

        /* property */
        String name = queryForm.getName();
        if (name != null && name.trim().length() != 0) {
            conditions.put(HotelQueryCondition.NAME_LIKE, name);
        }
        String address = queryForm.getAddress();
        if (address != null && address.trim().length() != 0) {
            conditions.put(HotelQueryCondition.ADDRESS_LIKE, address);
        }
        String description = queryForm.getDescription();
        if (description != null && description.trim().length() != 0) {
            conditions.put(HotelQueryCondition.DESCRIPTION_LIKE, description);
        }
        String telephone = queryForm.getTelephone();
        if (telephone != null && telephone.trim().length() != 0) {
            conditions.put(HotelQueryCondition.TELEPHONE_LIKE, telephone);
        }
        String fax = queryForm.getFax();
        if (fax != null && fax.trim().length() != 0) {
            conditions.put(HotelQueryCondition.FAX_LIKE, fax);
        }
        String level = queryForm.getLevel();
        if (level != null && level.trim().length() != 0) {
            conditions.put(HotelQueryCondition.LEVEL_EQ, level);
        }
        String contractStartDate = queryForm.getContractStartDate();
        if (contractStartDate != null && contractStartDate.trim().length() != 0) {
            conditions.put(HotelQueryCondition.CONTRACTSTARTDATE_EQ, contractStartDate);
        }
        String contractExpireDate = queryForm.getContractExpireDate();
        if (contractExpireDate != null && contractExpireDate.trim().length() != 0) {
            conditions.put(HotelQueryCondition.CONTRACTEXPIREDATE_EQ, contractExpireDate);
        }
        return conditions;
    }

    private void putEnumListToRequest(HttpServletRequest request) throws Exception {
        request.setAttribute("X_ENABLEDDISABLEDLIST", PersistentEnum.getEnumList(EnabledDisabled.class));
        request.setAttribute("x_hotelLevelList", PersistentEnum.getEnumList(HotelLevel.class));

        request.setAttribute("x_hotelPromoteStatusList", PersistentEnum.getEnumList(HotelPromoteStatus.class));

        this.putEnabledCountryProvinceCityListToRequest(request);
        CurrencyManager cm = ServiceLocator.getCurrencyManager(request);
        request.setAttribute("x_currencyList", cm.getAllEnabledCurrencyList());
    }

    private Hotel getHotelFromRequest(HttpServletRequest request) throws Exception {
        Integer id = ActionUtils.parseInt(request.getParameter("id"));
        HotelManager hotelManager = ServiceLocator.getHotelManager(request);
        Hotel hotel = hotelManager.getHotel(id);
        if (hotel == null)
            throw new ActionException("hotel.notFound", id);
        return hotel;
    }

    private void checkHotelPromoteStatus(Hotel hotel, HotelPromoteStatus status, HttpServletRequest request) throws Exception {
        if (this.isSite(request)) {
            if (!status.equals(HotelPromoteStatus.REQUEST)) {
                throw new RuntimeException("status error");
            }
            this.checkSite(hotel.getSite(), request);
        }
        if (!hotel.getPromoteStatus().equals(status)) {
            throw new ActionException("hotel.promote.statusError", hotel.getName(), status.getLabel());
        }
        if (hotel.getEnabled().equals(EnabledDisabled.DISABLED)) {
            throw new ActionException("hotel.promote.disabled", hotel.getName());
        }
    }

   
    /**
     * the action method for requesting hotel to promote
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward requestPromote(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        Hotel hotel = getHotelFromRequest(request);
        this.checkHotelPromoteStatus(hotel, HotelPromoteStatus.SITE, request);

        request.setAttribute("x_hotel", hotel);
        if (!isBack(request)) {
            BeanForm hotelForm = (BeanForm) getForm("/requestPromoteHotel_result", request);
            hotelForm.populate(hotel, BeanForm.TO_FORM);
        }
        return mapping.findForward("page");
    }

    /**
     * the action method for responsing hotel promote request
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward responsePromote(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Hotel hotel = getHotelFromRequest(request);

        //if (request.getParameter(CONFIRM) != null) {
            HotelManager hotelManager = ServiceLocator.getHotelManager(request);
            hotel = hotelManager.reponsePromote(hotel.getId());
            
            request.setAttribute("x_hotel", hotel);
            //request.setAttribute(ATTR_ROWPAGE, "hotel/row.jsp");
            
            ActionMessages messages=new ActionMessages();
            ActionMessage message=new ActionMessage("hotel.response.success");
            messages.add("success",message);
            this.saveMessages(request.getSession(),messages);

            return mapping.findForward(FORWARD_SUCCESS);
        //}
        //ActionMessage message;
        //if (hotel.getPromoteMessage() != null)
            //message = new ActionMessage("hotel.promote.response.confirm", hotel.getName(), hotel.getPromoteMessage());
        //else
            //message = new ActionMessage("hotel.promote.response.confirm.noMessage", hotel.getName());

        //ActionMessages messages = new ActionMessages();
        //messages.add("message", message);
        //this.saveMessages(request, messages);

        //return mapping.findForward(CONFIRM);
    }

    /**
     * the action method for request of hotel promote request
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward requestPromote_result(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Hotel hotel = getHotelFromRequest(request);
        this.checkHotelPromoteStatus(hotel, HotelPromoteStatus.SITE, request);
        
        String promteMsg = request.getParameter("promoteMessage");

        hotel.setPromoteMessage(promteMsg);

        HotelManager hotelManager = ServiceLocator.getHotelManager(request);

        hotel = hotelManager.requestPromote(hotel.getId(), hotel.getPromoteMessage());

        EmailManager em =ServiceLocator.getEmailManager(request);
        UserManager um = ServiceLocator.getUserManager(request);
        FunctionManager fm = ServiceLocator.getFunctionManager(request);
        Function function = fm.getFunction("siteHotelMaintenance");
        List userList=um.getEnabledUserList(function,hotel.getSite());
        for (Iterator itor = userList.iterator(); itor.hasNext();) {
            User user = (User) itor.next();
            if (user.getEmail()!=null) {
                Map context=new HashMap();
                context.put("user",user);
                context.put("hotel",hotel);
                context.put("promoteMessage",promteMsg);
                context.put("role", EmailManager.EMAIL_ROLE_HOTEL_MAINTAINER);
                em.insertEmail(user.getPrimarySite(),user.getEmail(),"HotelPromote.vm",context);
            }
        }
        
        request.setAttribute("PROMOTE_MESSAGE", promteMsg);
        //request.setAttribute(ATTR_ROWPAGE, "hotel/row.jsp");

        return mapping.findForward(FORWARD_SUCCESS);

    }

    /**
     * the action method for editing hotel
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        Hotel hotel = null;
        
        if (request.getAttribute("x_hotel") == null) {
            hotel = getHotelFromRequest(request);
            request.setAttribute("x_hotel", hotel);
        } else {
            hotel = (Hotel)request.getAttribute("x_hotel");
        }

        // global version can edit global hotel
        // site version can only edit site version
        /*if (this.isSite(request)) {
            if (hotel.getPromoteStatus().equals(HotelPromoteStatus.GLOBAL)) {
                throw new ActionException("hotel.error.siteEditGlobal");
            } else {
                this.checkSite(hotel.getSite(), request);
            }
        }
        else if(this.isGlobal(request))
        {
            if (!hotel.getPromoteStatus().equals(HotelPromoteStatus.GLOBAL)) {
                throw new ActionException("hotel.error.globalEditSite");
            }
        }*/
        if (!isBack(request)) {
            BeanForm hotelForm = (BeanForm) getForm("/updateHotel", request);
            hotelForm.populate(hotel, BeanForm.TO_FORM);
        }
        putContractListToRequest(hotel, request);
        putPriceListToRequest(hotel, request);

        putEnumListToRequest(request);

        request.setAttribute("x_action", "/updateHotel" + request.getAttribute("x_version"));
        return mapping.findForward("page");
    }

    private void putPriceListToRequest(Hotel hotel, HttpServletRequest request) throws Exception {
        PriceManager fm = ServiceLocator.getPriceManager(request);
        Map conditions = new HashMap();
        conditions.put(PriceQueryCondition.HOTEL_ID_EQ, hotel.getId());
        List result = fm.getPriceList(conditions, 0, -1, PriceQueryOrder.ID, false);
        request.setAttribute("x_priceList", result);

    }

    private void putContractListToRequest(Hotel hotel, HttpServletRequest request) throws Exception {
        HotelContractManager fm = ServiceLocator.getHotelContractManager(request);
        Map conditions = new HashMap();
        conditions.put(HotelContractQueryCondition.HOTEL_ID_EQ, hotel.getId());
        List result = fm.getHotelContractList(conditions, 0, -1, HotelContractQueryOrder.ID, false);
        request.setAttribute("x_contractList", result);
    }

    private Site getAndCheckSite(HttpServletRequest request) throws Exception {

        // global version can only create hotel without site
        // site version can only create hotel with site
        if (hasSite(request)) {
            if (this.isGlobal(request)) {
                throw new ActionException("hotel.error.globalNewSite");
            } else {
                return this.getAndCheckSite("site_id", request);
            }
        } else {
            if (this.isGlobal(request))
                return null;
            else
                throw new ActionException("hotel.error.siteNewGlobal");
        }

    }

    private boolean hasSite(HttpServletRequest request) {
        return !StringUtils.isEmpty(request.getParameter("site_id"));
    }

    /**
     * the action method for creating new hotel
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward newObject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Site site = this.getAndCheckSite(request);

        if (!isBack(request)) {
            Hotel hotel = new Hotel();
            hotel.setSite(site);
            if(site!=null)
                hotel.setCurrency(site.getBaseCurrency());

            BeanForm hotelForm = (BeanForm) getForm("/insertHotel", request);
            hotel.setPromoteStatus(HotelPromoteStatus.SITE);
            hotelForm.populate(hotel, BeanForm.TO_FORM);

        }
        putEnumListToRequest(request);
        return mapping.findForward("page");
    }

    /**
     * the action method for updating hotel
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Hotel oldHotel = getHotelFromRequest(request);

        // global version can edit global and site hotel
        // site version can only edit site version
        if (this.isSite(request)) {
            if (oldHotel.getPromoteStatus().equals(HotelPromoteStatus.GLOBAL)) {
                throw new ActionException("hotel.error.siteEditGlobal");
            } else {
                this.checkSite(oldHotel.getSite(), request);
            }
        }else if(this.isGlobal(request))
        {
            if (!oldHotel.getPromoteStatus().equals(HotelPromoteStatus.GLOBAL)) {
                throw new ActionException("hotel.error.globalEditSite");
            }
        }

        BeanForm hotelForm = (BeanForm) form;
        Hotel hotel = new Hotel();
        hotelForm.populate(hotel, BeanForm.TO_BEAN);
        hotel.setSite(oldHotel.getSite());

        HotelManager hotelManager = ServiceLocator.getHotelManager(request);
        hotelManager.updateHotel(hotel);
        
        ActionMessages messages=new ActionMessages();
        ActionMessage message=new ActionMessage("hotel.update.success");
        messages.add("success",message);
        this.saveMessages(request.getSession(),messages);

        return getForwardFor(hotel, request);
    }

    /**
     * the action method for inserting new hotel to db
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward insert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Site site = this.getAndCheckSite(request);

        BeanForm hotelForm = (BeanForm) form;
        Hotel hotel = new Hotel();
        hotelForm.populateToBean(hotel,request);
        hotel.setSite(site);

        HotelManager hotelManager = ServiceLocator.getHotelManager(request);
        hotelManager.insertHotel(hotel);
        
        ActionMessages messages=new ActionMessages();
        ActionMessage message=new ActionMessage("hotel.insert.success");
        messages.add("success",message);
        this.saveMessages(request.getSession(),messages);

        return getForwardFor(hotel, request);
    }

    private ActionForward getForwardFor(Hotel hotel, HttpServletRequest request) {
        String url = "editHotel" + request.getAttribute(ATTR_VERSION) + ".do?id="+hotel.getId();
        ActionForward forward = new ActionForward(url);
        forward.setRedirect(true);
        return forward;
    }

}