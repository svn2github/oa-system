/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.web.struts.action.business.ta;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import net.sourceforge.model.admin.Site;
import net.sourceforge.model.business.ta.AirTicket;
import net.sourceforge.model.business.ta.TravelApplication;
import net.sourceforge.model.metadata.AirTicketStatus;
import net.sourceforge.model.metadata.FlightClass;
import net.sourceforge.model.metadata.YesNo;
import net.sourceforge.service.admin.CountryManager;
import net.sourceforge.service.admin.ExchangeRateManager;
import net.sourceforge.service.admin.PurchaseTypeManager;
import net.sourceforge.service.admin.SupplierManager;
import net.sourceforge.service.business.ta.AirTicketManager;
import net.sourceforge.service.business.ta.TravelApplicationManager;
import net.sourceforge.web.struts.action.ServiceLocator;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.action.ActionUtils;
import com.shcnc.struts.form.BeanForm;
import com.shcnc.struts.form.converter.Converter;
import com.shcnc.struts.form.converter.ConverterLocator;
import com.shcnc.struts.form.converterLocators.DefaultConvertLocator;

/**
 * AirTicket的Action类
 * 
 * @author shi1
 * @version 1.0 (2005-12-4)
 */
public class TravelApplicationAirTicketAction extends BaseTravelApplicationAction  {
    private static ConverterLocator airTicketConverterLocator = new ConverterLocator() {

        public Converter getConverter(Class clazz) {
            return DefaultConvertLocator.getInstance().getConverter(clazz);
        }

        public Converter getConverter(String convertId) {
            return null;
        }

        public Converter getConverter(Class clazz, String propName) {
            if (propName.endsWith("Time"))
                return dateTimeConverter;
            else
                return null;
        }

    };
    
    /**
     * view AirTicket detail
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        AirTicket airTicket;
        Integer id = ActionUtils.parseInt(request.getParameter("id"));
        if (id == null) throw new ActionException("airticket.idNotSet");
        if(id.intValue()>=0)
            airTicket=this.getAirTicketFromRequest(request);
        else
            airTicket=this.getAirTicketFromSession(request);
        
        request.setAttribute("x_airTicket",airTicket);
        
        //recharge
        this.setRechargeInfoToRequestForView(airTicket,airTicket.getRecharges(),request);
        
         return mapping.findForward("page");
    }
    
    
    
    public AirTicket getAirTicketFromRequest(HttpServletRequest request) {
        Integer id = ActionUtils.parseInt(request.getParameter("id"));
        if (id == null) throw new ActionException("airticket.idNotSet");
        TravelApplicationManager tm =ServiceLocator.getTravelApplicationManager(request);
        AirTicket airTicket=tm.getAirTicketWithDetails(id);
        if(airTicket==null)
            throw new ActionException("airTicket.notFound",id);
        return airTicket;
    }

    
    
    /**
     * action method for booking AirTicket
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward book(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TravelApplication ta = this.getTravelApplicationFromRequest(request);
        
        BeanForm airTicketForm=(BeanForm)this.getForm("/bookAirTicket_result",request);
        AirTicket airTicket = new AirTicket();
        
        request.setAttribute("x_ta",ta);
        
        if(!this.isBack(request))
        {
            airTicket.setIsRecharge(YesNo.NO);
            airTicket.setBoardingPassRecevied(YesNo.NO);
            airTicket.setTravelApplication(ta);
            airTicket.setFromCity(ta.getFromCity());
            airTicket.setToCity(ta.getToCity());
            if(ta.getFromTime()!=null)
            {
                Calendar departTime = Calendar.getInstance();
                departTime.setTime(ta.getFromTime());
                int hour = departTime.get(Calendar.HOUR_OF_DAY);
                int minute = departTime.get(Calendar.MINUTE);
                departTime.setTime(ta.getFromDate());
                departTime.set(Calendar.HOUR_OF_DAY, hour);
                departTime.set(Calendar.MINUTE, minute);
                airTicket.setDepartTime(departTime.getTime());
                airTicket.setArriveTime(departTime.getTime());
            }
            airTicketForm.setConverterLocator(airTicketConverterLocator);
            airTicketForm.populateToForm(airTicket);
        }
        
        //recharge 
        this.setRechargeInfoToRequest(airTicket,new ArrayList(),ta.getDepartment().getSite(),airTicketForm,request);
        request.setAttribute("X_FORMNAME", "airTicketBookForm");
        
        //combo list
        this.putSupplierToRequest(ta, request);
        this.putExchangeRateListToRequest(ta.getDepartment().getSite(), request);
        this.putFlightClassListToRequest(request);
        this.putPurchaseTypeListToRequest(ta.getDepartment().getSite(), request);
        this.putEnabledCountryProvinceCityListToRequest(request);
        
        this.setEditing(true,request);
        
        request.setAttribute("x_add",Boolean.TRUE);
        
        return mapping.findForward("page");
    }    
    
    private void putEnabledCountryProvinceCityListToRequest(HttpServletRequest request) {
        List countryList = getEnabledCountryProvinceCityList(request);
        request.setAttribute("x_countryList", countryList);
    }
    
    private List getEnabledCountryProvinceCityList(HttpServletRequest request) {
        CountryManager cm = ServiceLocator.getCountryManager(request);
        return cm.listEnabledCountryProvinceCity();
    }
    
    private void putSupplierToRequest(TravelApplication ta, HttpServletRequest request) {
        SupplierManager sm = ServiceLocator.getSupplierManager(request);
        List supplierList = sm.getEnabledAirTicketSuppliersForSiteAndIncludeGlobal(ta.getDepartment().getSite());
        request.setAttribute("x_supplierList", supplierList);
    }

    private void putExchangeRateListToRequest(Site s, HttpServletRequest request) {
        ExchangeRateManager erm = ServiceLocator.getExchangeRateManager(request);
        request.setAttribute("x_exchangeRateList", erm.getEnabledExchangeRateListBySite(s));
    }
    
    private void putPurchaseTypeListToRequest(Site site, HttpServletRequest request) {
        PurchaseTypeManager ptm = ServiceLocator.getPurchaseTypeManager(request);
        request.setAttribute("x_purchaseTypeList", ptm.getEnabledPurchaseTypeList(site));
    }
    private void putFlightClassListToRequest(HttpServletRequest request) {
        request.setAttribute("x_flightClassList", FlightClass.getEnumList(FlightClass.class));
    }
    
    protected String getPurchaseRequsetId(HttpServletRequest request)
    {
        return request.getParameter("purchaseRequest_id");
    }
    
    /**
     * action method for deleting AirTicket
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        AirTicket airTicket=this.getAirTicketFromSession(request);
        if(airTicket.getStatus()!=null && !airTicket.getStatus().equals(AirTicketStatus.BOOKED))
        {
            throw new ActionException("airTicket.delete.notBooked");
        }
        this.getAirTicketListFromSession(request).remove(airTicket);
        return mapping.findForward("success");
    }
    
    public ActionForward requestorDelete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer id = ActionUtils.parseInt(request.getParameter("id"));
        String taId = request.getParameter("travelApplication_id");
        if (id == null) throw new ActionException("airTicket.idNotSet");
        
        AirTicketManager atm = ServiceLocator.getAirTicketManager(request);
        atm.deleteAirTicket(id, taId);        
        return mapping.findForward("success");
    }
    

    /**
     * 修改AirTicket
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        AirTicket airTicket=this.getAirTicketFromSession(request);
        TravelApplication ta=airTicket.getTravelApplication();
        
        request.setAttribute("x_airTicket",airTicket);
        request.setAttribute("x_ta",ta);
        
        BeanForm airTicketForm=(BeanForm)this.getForm("/updateBookAirTicket",request);
        
        if(!this.isBack(request))
        {
            airTicketForm.setConverterLocator(airTicketConverterLocator);
            airTicketForm.populateToForm(airTicket);
        }
        
        //recharge
        this.setRechargeInfoToRequest(airTicket,airTicket.getRecharges(),airTicket.getTravelApplication().getDepartment().getSite(),airTicketForm,request);
        request.setAttribute("X_FORMNAME", "airTicketBookForm");

        //combo list
        this.putSupplierToRequest(ta, request);
        this.putExchangeRateListToRequest(ta.getDepartment().getSite(), request);
        this.putFlightClassListToRequest(request);
        this.putPurchaseTypeListToRequest(ta.getDepartment().getSite(), request);
        this.putEnabledCountryProvinceCityListToRequest(request);
        
        this.setEditing(true,request);
        request.setAttribute("x_add",Boolean.FALSE);
        
        return mapping.findForward("page");
    }
    
    public AirTicket getAirTicketFromSession(HttpServletRequest request) {
        Integer id = ActionUtils.parseInt(request.getParameter("id"));
        if (id == null) throw new ActionException("airTicket.idNotSet");
        List itemList=this.getAirTicketListFromSession(request);
        if (itemList != null) {
            for (Iterator iter = itemList.iterator(); iter.hasNext();) {
                AirTicket item = (AirTicket) iter.next();
                if(item.getId().equals(id)) return item;
            }
        }        
        throw new ActionException("airTicket.notFound",id);
    }
    
    
    public ActionForward bookResult(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TravelApplication ta=this.getTravelApplicationFromRequest(request);
        
        BeanForm airTicketForm = (BeanForm) form;
        
        AirTicket airTicket = new AirTicket(this.getNewAirTicketId(request));
        airTicket.setTravelApplication(ta);
        airTicket.setBoardingPassRecevied(YesNo.NO);
        airTicketForm.setConverterLocator(airTicketConverterLocator);
        airTicketForm.setBeanLoader(ServiceLocator.getBeanLoader(request));
        airTicketForm.populateToBean(airTicket,request);
        
        airTicket.setRecharges(this.getRechargeInfoFromRequest(airTicket,  request));
        
        this.insertAirTicket(airTicket,request);
        
        
        request.setAttribute("X_OBJECT", airTicket);
        request.setAttribute("x_ta",ta);
        request.setAttribute("X_ROWPAGE", "travelApplication/airTicketRow.jsp");
        this.putPurchaseTypeListToRequest(ta.getDepartment().getSite(), request);

        this.setEditing(true,request);

        return mapping.findForward("success");
    }
    

    public void insertAirTicket(AirTicket airTicket, HttpServletRequest request) {
        this.getAirTicketListFromSession(request).add(airTicket);
    }

    public Integer getNewAirTicketId(HttpServletRequest request) {
        List itemList=this.getAirTicketListFromSession(request);
        int min=0;
        for (Iterator iter = itemList.iterator(); iter.hasNext();) {
            AirTicket airTicket = (AirTicket) iter.next();
            min=Math.min(min,airTicket.getId().intValue());
        }
        return new Integer(min-1);
    }
    

    /**
     * 保存修改的CapexRequestItem
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        BeanForm airTicketForm = (BeanForm) form;
        
        AirTicket airTicket = this.getAirTicketFromSession(request);
        airTicketForm.setConverterLocator(airTicketConverterLocator);
        airTicketForm.populateToBean(airTicket);
        
        airTicket.setRecharges(this.getRechargeInfoFromRequest(airTicket,  request));
        
        request.setAttribute("X_OBJECT", airTicket);
        request.setAttribute("X_ROWPAGE", "travelApplication/airTicketRow.jsp");

        this.setEditing(true,request);
        this.putPurchaseTypeListToRequest(airTicket.getTravelApplication().getDepartment().getSite(), request);
        return mapping.findForward("success");
    }
    
    protected String getTravelApplicationId(HttpServletRequest request)
    {
        return request.getParameter("travelApplication_id");
    }

    /* (non-Javadoc)
     * @see net.sourceforge.web.struts.action.business.RechargeAction#selectRechargeCustomer(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public ActionForward selectRechargeCustomer(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return super.selectRechargeCustomer(mapping, form, request, response);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.web.struts.action.business.RechargeAction#selectRechargeEntity(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public ActionForward selectRechargeEntity(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return super.selectRechargeEntity(mapping, form, request, response);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.web.struts.action.business.RechargeAction#selectRechargePerson(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public ActionForward selectRechargePerson(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return super.selectRechargePerson(mapping, form, request, response);
    }


}