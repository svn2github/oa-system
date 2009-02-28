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

import com.aof.model.admin.City;
import com.aof.model.admin.Function;
import com.aof.model.admin.Province;
import com.aof.model.admin.Site;
import com.aof.model.admin.User;
import com.aof.model.admin.query.CityQueryCondition;
import com.aof.model.admin.query.CityQueryOrder;
import com.aof.model.metadata.EnabledDisabled;
import com.aof.service.admin.CityManager;
import com.aof.service.admin.EmailManager;
import com.aof.service.admin.FunctionManager;
import com.aof.service.admin.ProvinceManager;
import com.aof.service.admin.UserManager;
import com.aof.web.struts.action.BaseAction;
import com.aof.web.struts.action.ServiceLocator;
import com.shcnc.hibernate.PersistentEnum;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.action.ActionUtils;
import com.shcnc.struts.action.BackToInputActionException;
import com.shcnc.struts.form.BeanForm;

/**
 * @author shilei
 * @version 1.0 (Nov 14, 2005)
 */
public class CityAction extends BaseAction {

    /**
     * the action method promoting city to global level
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward promote(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        City city = this.getCity(request);
        if (city.getSite() == null) {
            throw new ActionException("city.error.alreadyPromoted", city.getEngName());
        }

        if (request.getParameter(CONFIRM) != null) {
            CityManager cm = ServiceLocator.getCityManager(request);
            cm.promoteCity(city);
            request.setAttribute(ATTR_OBJECT, city);
            request.setAttribute(ATTR_ROWPAGE, "city/row.jsp");

            return mapping.findForward(FORWARD_SUCCESS);
        }

        String name = null;
        if (this.isEnglish(request))
            name = city.getEngName();
        else
            name = city.getChnName();

        this.postGlobalMessage("city.promote.confirm", name, request);
        return mapping.findForward(CONFIRM);
    }

    /**
     * the action method list city
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Province province = this.getProvinceFromRequest(request);
        Map conds = new HashMap();
        conds.put(CityQueryCondition.PROVINCE_ID_EQ, province.getId());

        CityManager fm = ServiceLocator.getCityManager(request);
        List result = fm.getCityList(conds, 0, -1, this.isEnglish(request) ? CityQueryOrder.ENGNAME : CityQueryOrder.CHNNAME, false);
        request.setAttribute("X_RESULTLIST", result);
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

    private Province getProvinceFromRequest(HttpServletRequest request) throws Exception {
        Integer province_id = ActionUtils.parseInt(request.getParameter("province_id"));
        ProvinceManager provinceManager = ServiceLocator.getProvinceManager(request);
        Province province = provinceManager.getProvince(province_id);
        if (province == null)
            throw new ActionException("province.notFound", province_id);
        return province;
    }

    /*
     * private Map constructQueryMap(CityQueryForm queryForm) { Map conditions =
     * new HashMap();
     * 
     * Integer id = ActionUtils.parseInt(queryForm.getId()); if (id != null) {
     * conditions.put(CityQueryCondition.ID_EQ, id); }
     * 
     * Integer province_id = ActionUtils.parseInt(queryForm.getProvince_id());
     * if (province_id != null) {
     * conditions.put(CityQueryCondition.PROVINCE_ID_EQ, province_id); }
     * 
     * 
     * 
     * 
     * String engName = queryForm.getEngName(); if(engName != null &&
     * engName.trim().length()!=0) {
     * conditions.put(CityQueryCondition.ENGNAME_LIKE, engName); } String
     * chnName = queryForm.getChnName(); if(chnName != null &&
     * chnName.trim().length()!=0) {
     * conditions.put(CityQueryCondition.CHNNAME_LIKE, chnName); } String
     * enabled = queryForm.getEnabled(); if(enabled != null &&
     * enabled.trim().length()!=0) {
     * conditions.put(CityQueryCondition.ENABLED_EQ, enabled); } return
     * conditions; }
     */

    private City getCity(HttpServletRequest request) throws Exception {
        Integer id = ActionUtils.parseInt(request.getParameter("id"));
        CityManager cityManager = ServiceLocator.getCityManager(request);
        City city = cityManager.getCity(id);
        if (city == null)
            throw new ActionException("city.notFound", id);
        return city;
    }

    private void putEnumListToRequest(HttpServletRequest request) {
        request.setAttribute("X_ENABLEDDISABLEDLIST", PersistentEnum.getEnumList(EnabledDisabled.class));
    }

    /**
     * the action method editing city
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        City city = this.getCity(request);

        if (city.getSite() == null)
            this.checkGlobalPower(request);
        else
            this.checkSite(city.getSite(), request);

        request.setAttribute("x_city", city);
        if (!isBack(request)) {

            BeanForm cityForm = (BeanForm) getForm("/updateCity", request);
            cityForm.populate(city, BeanForm.TO_FORM);
        }
        putEnumListToRequest(request);
        return mapping.findForward(FORWARD_PAGE);
    }
    
    /**
     * action method for deleting city
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        City city = getCity(request);
        if (city.getSite() == null) {
            this.checkGlobalPower(request);
        } else
            this.checkSite(city.getSite(), request);

        CityManager cm=ServiceLocator.getCityManager(request);
        try{
            cm.deleteCity(city);
        }
        catch(Throwable t)
        {
            throw new ActionException("city.delete.fail");
        }
        return mapping.findForward("success");
    }

    /**
     * the action method updating city
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        BeanForm cityForm = (BeanForm) form;
        City city = new City();
        cityForm.populate(city, BeanForm.TO_BEAN);

        if (city.getSite() == null) {
            this.checkGlobalPower(request);
        } else
            city.setSite(this.getAndCheckSite(city.getSite().getId(), request));

        CityManager cityManager = ServiceLocator.getCityManager(request);
        City oldCity=cityManager.getCityByChnName(city.getProvince(),city.getChnName());
        if(oldCity!=null && !oldCity.equals(city))
            throw new BackToInputActionException("city.chnName.repeat");
        
        oldCity=cityManager.getCityByEngName(city.getProvince(),city.getEngName());
        if(oldCity!=null && !oldCity.equals(city))
            throw new BackToInputActionException("city.engName.repeat");
        
        request.setAttribute(ATTR_OBJECT, cityManager.updateCity(city));
        request.setAttribute(ATTR_ROWPAGE, "city/row.jsp");

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
     * the action method creating new city
     * 
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

        Province province = this.getProvinceFromRequest(request);
        request.setAttribute("x_province", province);
        if (!isBack(request)) {
            City city = new City();
            city.setSite(site);
            city.setProvince(province);
            BeanForm cityForm = (BeanForm) getForm("/insertCity", request);
            cityForm.populate(city, BeanForm.TO_FORM);
        }
        putEnumListToRequest(request);
        return mapping.findForward(FORWARD_PAGE);
    }

    /**
     * the action method inserting new city
     * 
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

        CityManager cm = ServiceLocator.getCityManager(request);
        BeanForm cityForm = (BeanForm) form;
        City city = new City();
        cityForm.populate(city, BeanForm.TO_BEAN);
        city.setSite(site);
        
        if(cm.getCityByChnName(city.getProvince(),city.getChnName())!=null)
            throw new BackToInputActionException("city.chnName.repeat");
        
        if(cm.getCityByEngName(city.getProvince(),city.getEngName())!=null)
            throw new BackToInputActionException("city.engName.repeat");

        request.setAttribute(ATTR_OBJECT, cm.insertCity(city));
        request.setAttribute(ATTR_ROWPAGE, "city/row.jsp");
        
        if (city.getSite()!=null) {
            EmailManager em =ServiceLocator.getEmailManager(request);
            UserManager um = ServiceLocator.getUserManager(request);
            FunctionManager fm = ServiceLocator.getFunctionManager(request);
            Function function = fm.getFunction("countryProvinceCityMaintenance");
            List userList=um.getEnabledUserList(function);
            for (Iterator itor = userList.iterator(); itor.hasNext();) {
                User user = (User) itor.next();
                if (user.getEmail()!=null) {
                    ProvinceManager pm = ServiceLocator.getProvinceManager(request);
                    Map context=new HashMap();
                    context.put("user",user);
                    context.put("city",city);
                    context.put("province",pm.getProvince(city.getProvince().getId()));
                    context.put("role", EmailManager.EMAIL_ROLE_CITY_MAINTAINER);
                    em.insertEmail(user.getPrimarySite(),user.getEmail(),"NewCity.vm",context);
                }
            }
        }
        
        return mapping.findForward(FORWARD_SUCCESS);
    }
}