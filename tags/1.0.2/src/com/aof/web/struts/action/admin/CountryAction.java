/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.web.struts.action.admin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aof.model.admin.Country;
import com.aof.model.admin.Function;
import com.aof.model.admin.Site;
import com.aof.model.admin.User;
import com.aof.model.admin.query.CountryQueryOrder;
import com.aof.model.metadata.EnabledDisabled;
import com.aof.service.admin.CountryManager;
import com.aof.service.admin.EmailManager;
import com.aof.service.admin.FunctionManager;
import com.aof.service.admin.UserManager;
import com.aof.web.struts.action.ActionUtils;
import com.aof.web.struts.action.BaseAction;
import com.aof.web.struts.action.ServiceLocator;
import com.shcnc.hibernate.PersistentEnum;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.action.BackToInputActionException;

import com.shcnc.struts.form.BeanForm;

/**
 * @author shilei
 * @version 1.0 (Nov 14, 2005)
 */
public class CountryAction extends BaseAction {

    /**
     * the action method promoting method to global level
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward promote(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        Country country = this.getCountryFromRequest(request);
        if (country.getSite() == null) {
            throw new ActionException("country.error.alreadyPromoted", country.getEngName());
        }

        if (request.getParameter(CONFIRM) != null) {
            CountryManager cm = ServiceLocator.getCountryManager(request);
            cm.promoteCountry(country);
            request.setAttribute(ATTR_OBJECT, country);
            request.setAttribute(ATTR_ROWPAGE, "country/row.jsp");
            return mapping.findForward(FORWARD_SUCCESS);
        }

        String name = null;
        if (this.isEnglish(request))
            name = country.getEngName();
        else
            name = country.getChnName();
        this.postGlobalMessage("country.promote.confirm", name, request);

        return mapping.findForward(CONFIRM);
    }

    /**
     * the action method listing country, province, city
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward listCountryProvinceCity(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        if (this.isSite(request))
            request.setAttribute(ATTR_SITELIST, this.getAndCheckGrantedSiteList(request));
        
        //------Add the code below by Jackey Ding 2005-11-17
        if (this.hasGlobalPower(request)) {
            request.setAttribute("x_promote", Boolean.TRUE);
        }

        return mapping.findForward(FORWARD_PAGE);
    }

    /**
     * the action method listing country
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CountryManager fm = ServiceLocator.getCountryManager(request);
        List result = fm.getCountryList(new HashMap(), 0, -1, this.isEnglish(request) ? CountryQueryOrder.ENGNAME : CountryQueryOrder.CHNNAME, false);
        request.setAttribute(ATTR_RESULTLIST, result);
        if (!result.isEmpty()) {
            request.setAttribute("X_FIRST", result.get(0));
        }
        //------Remove the code below by Jackey Ding 2005-11-17
        /*
        if (this.hasGlobalPower(request)) {
            request.setAttribute("x_promote", Boolean.TRUE);
        }
        */
        return mapping.findForward(FORWARD_PAGE);
    }

    /*
     * private Map constructQueryMap(CountryQueryForm queryForm) { Map
     * conditions = new HashMap();
     * 
     * Integer id = ActionUtils.parseInt(queryForm.getId()); if (id != null) {
     * conditions.put(CountryQueryCondition.ID_EQ, id); }
     * 
     * String shortName = queryForm.getShortName(); if (shortName != null &&
     * shortName.trim().length() != 0) {
     * conditions.put(CountryQueryCondition.SHORTNAME_LIKE, shortName); } String
     * engName = queryForm.getEngName(); if (engName != null &&
     * engName.trim().length() != 0) {
     * conditions.put(CountryQueryCondition.ENGNAME_LIKE, engName); } String
     * chnName = queryForm.getChnName(); if (chnName != null &&
     * chnName.trim().length() != 0) {
     * conditions.put(CountryQueryCondition.CHNNAME_LIKE, chnName); } String
     * enabled = queryForm.getEnabled(); if (enabled != null &&
     * enabled.trim().length() != 0) {
     * conditions.put(CountryQueryCondition.ENABLED_EQ, enabled); } return
     * conditions; }
     */

    private Country getCountryFromRequest(HttpServletRequest request) throws Exception {
        Integer id = ActionUtils.parseInt(request.getParameter("id"));
        CountryManager countryManager = ServiceLocator.getCountryManager(request);
        Country country = countryManager.getCountry(id);
        if (country == null)
            throw new ActionException("country.notFound", id);
        return country;
    }

    private void putEnumListToRequest(HttpServletRequest request) {
        request.setAttribute("X_ENABLEDDISABLEDLIST", PersistentEnum.getEnumList(EnabledDisabled.class));
    }

    /**
     * the action method editing country
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Country country = getCountryFromRequest(request);

        if (country.getSite() == null) {
            this.checkGlobalPower(request);
        } else
            this.checkSite(country.getSite(), request);

        request.setAttribute("x_country", country);
        if (!isBack(request)) {
            BeanForm countryForm = (BeanForm) getForm("/updateCountry", request);
            countryForm.populate(country, BeanForm.TO_FORM);
        }
        putEnumListToRequest(request);
        return mapping.findForward(FORWARD_PAGE);
    }
    
    /**
     * action method for deleting country
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Country country = getCountryFromRequest(request);
        if (country.getSite() == null) {
            this.checkGlobalPower(request);
        } else
            this.checkSite(country.getSite(), request);

        CountryManager cm=ServiceLocator.getCountryManager(request);
        try{
            cm.deleteCountry(country);
        }
        catch(Throwable t)
        {
            throw new ActionException("country.delete.fail");
        }
        return mapping.findForward("success");
    }
    

    /**
     * the action method updaing country
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        BeanForm countryForm = (BeanForm) form;
        Country country = new Country();
        countryForm.populate(country, BeanForm.TO_BEAN);

        if (country.getSite() == null) {
            this.checkGlobalPower(request);
        } else
            country.setSite(this.getAndCheckSite(country.getSite().getId(), request));

        CountryManager countryManager = ServiceLocator.getCountryManager(request);
        
        Country oldCountry=countryManager.getCountryByChnName(country.getChnName());
        if(oldCountry!=null && !oldCountry.equals(country))
        {
            throw new BackToInputActionException("country.chnName.repeat");
        }
        
        oldCountry=countryManager.getCountryByEngName(country.getEngName());
        if(oldCountry!=null && !oldCountry.equals(country))
        {
            throw new BackToInputActionException("country.engName.repeat");
        }
        
        oldCountry=countryManager.getCountryByShortName(country.getShortName());
        if(oldCountry!=null && !oldCountry.equals(country))
        {
            throw new BackToInputActionException("country.shortName.repeat");
        }
        
        request.setAttribute(ATTR_OBJECT, countryManager.updateCountry(country));
        request.setAttribute(ATTR_ROWPAGE, "country/row.jsp");

        return mapping.findForward(FORWARD_SUCCESS);
    }

    private Site getSiteFromRequestAndCheckPower(HttpServletRequest request) throws Exception {
        return this.getAndCheckSite("site_id", request);
    }

    private boolean hasSite(HttpServletRequest request) {
        String s = request.getParameter("site_id");
        return s != null && !s.equals("");
    }

    /**
     * the action method creating new country
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward newObject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        Site site = null;
        if (hasSite(request)) {
            site = getSiteFromRequestAndCheckPower(request);
        } else {
            this.checkGlobalPower(request);

        }

        if (!isBack(request)) {
            Country country = new Country();
            country.setSite(site);
            BeanForm countryForm = (BeanForm) getForm("/insertCountry", request);
            countryForm.populate(country, BeanForm.TO_FORM);
        }
        putEnumListToRequest(request);
        return mapping.findForward(FORWARD_PAGE);
    }

    /**
     * the action method inserting new country to db
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward insert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        Site site = null;
        if (hasSite(request)) {
            site = getSiteFromRequestAndCheckPower(request);
        } else {
            this.checkGlobalPower(request);

        }

        BeanForm countryForm = (BeanForm) form;
        Country country = new Country();
        countryForm.populate(country, BeanForm.TO_BEAN);
        country.setSite(site);

        CountryManager cm = ServiceLocator.getCountryManager(request);
        
        Country oldCountry=cm.getCountryByChnName(country.getChnName());
        if(oldCountry!=null)
        {
            throw new BackToInputActionException("country.chnName.repeat");
        }
        
        oldCountry=cm.getCountryByEngName(country.getEngName());
        if(oldCountry!=null)
        {
            throw new BackToInputActionException("country.engName.repeat");
        }
        
        oldCountry=cm.getCountryByShortName(country.getShortName());
        if(oldCountry!=null)
        {
            throw new BackToInputActionException("country.shortName.repeat");
        }
        
        request.setAttribute(ATTR_OBJECT, cm.insertCountry(country));
        request.setAttribute(ATTR_ROWPAGE, "country/row.jsp");
        
        if (country.getSite()!=null) {
            EmailManager em =ServiceLocator.getEmailManager(request);
            UserManager um = ServiceLocator.getUserManager(request);
            FunctionManager fm = ServiceLocator.getFunctionManager(request);
            Function function = fm.getFunction("countryProvinceCityMaintenance");
            List userList=um.getEnabledUserList(function);
            for (Iterator itor = userList.iterator(); itor.hasNext();) {
                User user = (User) itor.next();
                if (user.getEmail()!=null) {
                    Map context=new HashMap();
                    context.put("user",user);
                    context.put("country",country);
                    context.put("role", EmailManager.EMAIL_ROLE_CITY_MAINTAINER);
                    em.insertEmail(user.getPrimarySite(),user.getEmail(),"NewCountry.vm",context);
                }
            }
        }
        return mapping.findForward(FORWARD_SUCCESS);
    }
}