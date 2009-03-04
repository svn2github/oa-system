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
import com.aof.model.admin.Province;
import com.aof.model.admin.Site;
import com.aof.model.admin.User;
import com.aof.model.admin.query.ProvinceQueryCondition;
import com.aof.model.admin.query.ProvinceQueryOrder;
import com.aof.model.metadata.EnabledDisabled;
import com.aof.service.admin.CountryManager;
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
 * the struts action class for domain model province
 * 
 * @author shilei
 * @version 1.0 (Nov 15, 2005)
 */
public class ProvinceAction extends BaseAction {

    /**
     * the action method for promoting province to global level
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward promote(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        Province p = this.getProvinceFromRequest(request);
        if (p.getSite() == null) {
            throw new ActionException("province.error.alreadyPromoted", p.getEngName());
        }
        if (request.getParameter(CONFIRM) != null) {
            ProvinceManager pm = ServiceLocator.getProvinceManager(request);
            pm.promoteProvince(p);
            request.setAttribute(ATTR_OBJECT, p);
            request.setAttribute(ATTR_ROWPAGE, "province/row.jsp");
            return mapping.findForward(FORWARD_SUCCESS);
        }

        String name = null;
        if (this.isEnglish(request))
            name = p.getEngName();
        else
            name = p.getChnName();
        this.postGlobalMessage("province.promote.confirm", name, request);
        return mapping.findForward(CONFIRM);
    }

    /**
     * the action method for listing province
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Country country = this.getCountryFromRequest(request);

        Map conds = new HashMap();
        conds.put(ProvinceQueryCondition.COUNTRY_ID_EQ, country.getId());

        ProvinceManager fm = ServiceLocator.getProvinceManager(request);
        List result = fm.getProvinceList(conds, 0, -1, this.isEnglish(request) ? ProvinceQueryOrder.ENGNAME : ProvinceQueryOrder.CHNNAME, false);
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
        return mapping.findForward("page");
    }

    private Country getCountryFromRequest(HttpServletRequest request) throws Exception {
        Integer country_id = ActionUtils.parseInt(request.getParameter("country_id"));
        CountryManager countryManager = ServiceLocator.getCountryManager(request);
        Country country = countryManager.getCountry(country_id);
        if (country == null)
            throw new ActionException("country.notFound", country_id);

        return country;
    }

    /*
     * private Map constructQueryMap(ProvinceQueryForm queryForm) { Map
     * conditions = new HashMap();
     * 
     * Integer id = ActionUtils.parseInt(queryForm.getId()); if (id != null) {
     * conditions.put(ProvinceQueryCondition.ID_EQ, id); }
     * 
     * 
     * 
     * 
     * 
     * 
     * Integer country_id = ActionUtils.parseInt(queryForm.getCountry_id()); if
     * (country_id != null) {
     * conditions.put(ProvinceQueryCondition.COUNTRY_ID_EQ, country_id); }
     * 
     * 
     * String engName = queryForm.getEngName(); if (engName != null &&
     * engName.trim().length() != 0) {
     * conditions.put(ProvinceQueryCondition.ENGNAME_LIKE, engName); } String
     * chnName = queryForm.getChnName(); if (chnName != null &&
     * chnName.trim().length() != 0) {
     * conditions.put(ProvinceQueryCondition.CHNNAME_LIKE, chnName); } String
     * enabled = queryForm.getEnabled(); if (enabled != null &&
     * enabled.trim().length() != 0) {
     * conditions.put(ProvinceQueryCondition.ENABLED_EQ, enabled); } return
     * conditions; }
     */

    private Province getProvinceFromRequest(HttpServletRequest request) throws Exception {
        Integer id = ActionUtils.parseInt(request.getParameter("id"));
        ProvinceManager provinceManager = ServiceLocator.getProvinceManager(request);
        Province province = provinceManager.getProvince(id);
        if (province == null)
            throw new ActionException("province.notFound", id);
        return province;
    }

    /**
     * the action method for editing province
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Province province = this.getProvinceFromRequest(request);

        if (province.getSite() == null)
            this.checkGlobalPower(request);
        else
            this.checkSite(province.getSite(), request);

        request.setAttribute("x_province", province);

        if (!isBack(request)) {
            BeanForm provinceForm = (BeanForm) getForm("/updateProvince", request);
            provinceForm.populate(province, BeanForm.TO_FORM);
        }

        putEnumListToRequest(request);
        return mapping.findForward("page");
    }
    
    /**
     * action method for deleting province
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Province p = this.getProvinceFromRequest(request);
        if (p.getSite() == null) {
            this.checkGlobalPower(request);
        } else
            this.checkSite(p.getSite(), request);

        ProvinceManager pm=ServiceLocator.getProvinceManager(request);
        
        try{
            pm.deleteProvince(p);
        }
        catch(Throwable t)
        {
            throw new ActionException("province.delete.fail");
        }
        return mapping.findForward("success");
    }    

    private void putEnumListToRequest(HttpServletRequest request) {
        request.setAttribute("X_ENABLEDDISABLEDLIST", PersistentEnum.getEnumList(EnabledDisabled.class));
    }

    /**
     * the action method for updating province
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        BeanForm provinceForm = (BeanForm) form;
        Province province = new Province();
        provinceForm.populate(province, BeanForm.TO_BEAN);

        if (province.getSite() == null) {
            this.checkGlobalPower(request);
        } else
            province.setSite(this.getAndCheckSite(province.getSite().getId(), request));

        ProvinceManager provinceManager = ServiceLocator.getProvinceManager(request);
        
        Province oldProvince=provinceManager.getProvinceByChnName(province.getCountry(),province.getChnName());
        if(oldProvince!=null &&!oldProvince.equals(province)) throw new BackToInputActionException("province.chnName.repeat");
        
        oldProvince=provinceManager.getProvinceByEngName(province.getCountry(),province.getEngName());
        if(oldProvince!=null &&!oldProvince.equals(province)) throw new BackToInputActionException("province.engName.repeat");
        
        request.setAttribute("X_OBJECT", provinceManager.updateProvince(province));
        request.setAttribute("X_ROWPAGE", "province/row.jsp");

        return mapping.findForward("success");
    }

    private Site getSiteFromRequestAndCheckPower(HttpServletRequest request) throws Exception {
        return this.getAndCheckSite("site_id", request);
    }

    private boolean hasSite(HttpServletRequest request) {
        String s = request.getParameter("site_id");
        return s != null && !s.equals("");
    }

    /**
     * the action method for creating new province
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

        Country country = this.getCountryFromRequest(request);
        request.setAttribute("x_country", country);
        if (!isBack(request)) {
            Province province = new Province();
            province.setSite(site);
            province.setCountry(country);
            BeanForm provinceForm = (BeanForm) getForm("/insertProvince", request);
            provinceForm.populate(province, BeanForm.TO_FORM);
        }
        putEnumListToRequest(request);
        return mapping.findForward("page");
    }

    /**
     * the action method for insert new province to db
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

        BeanForm provinceForm = (BeanForm) form;
        Province province = new Province();
        provinceForm.populate(province, BeanForm.TO_BEAN);
        province.setSite(site);

        ProvinceManager pm = ServiceLocator.getProvinceManager(request);
        
        Province oldProvince=pm.getProvinceByChnName(province.getCountry(),province.getChnName());
        if(oldProvince!=null) throw new BackToInputActionException("province.chnName.repeat");
        
        oldProvince=pm.getProvinceByEngName(province.getCountry(),province.getEngName());
        if(oldProvince!=null) throw new BackToInputActionException("province.engName.repeat");
        
        request.setAttribute("X_OBJECT", pm.insertProvince(province));
        request.setAttribute("X_ROWPAGE", "province/row.jsp");
        
        if (province.getSite()!=null) {
            EmailManager em =ServiceLocator.getEmailManager(request);
            UserManager um = ServiceLocator.getUserManager(request);
            FunctionManager fm = ServiceLocator.getFunctionManager(request);
            Function function = fm.getFunction("countryProvinceCityMaintenance");
            List userList=um.getEnabledUserList(function);
            for (Iterator itor = userList.iterator(); itor.hasNext();) {
                User user = (User) itor.next();
                if (user.getEmail()!=null) {
                    CountryManager cm = ServiceLocator.getCountryManager(request);
                    Map context=new HashMap();
                    context.put("user",user);
                    context.put("province",province);
                    context.put("country",cm.getCountry(province.getCountry().getId()));
                    context.put("role", EmailManager.EMAIL_ROLE_CITY_MAINTAINER);
                    em.insertEmail(user.getPrimarySite(),user.getEmail(),"NewProvince.vm",context);
                }
            }
        }
        return mapping.findForward("success");
    }
}