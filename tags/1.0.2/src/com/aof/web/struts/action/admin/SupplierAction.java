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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.aof.model.admin.Country;
import com.aof.model.admin.Function;
import com.aof.model.admin.PurchaseSubCategory;
import com.aof.model.admin.Site;
import com.aof.model.admin.Supplier;
import com.aof.model.admin.User;
import com.aof.model.admin.query.SupplierContractQueryCondition;
import com.aof.model.admin.query.SupplierContractQueryOrder;
import com.aof.model.admin.query.SupplierQueryCondition;
import com.aof.model.admin.query.SupplierQueryOrder;
import com.aof.model.metadata.ContractStatus;
import com.aof.model.metadata.EnabledDisabled;
import com.aof.model.metadata.ExportStatus;
import com.aof.model.metadata.SupplierConfirmStatus;
import com.aof.model.metadata.SupplierPromoteStatus;
import com.aof.model.metadata.YesNo;
import com.aof.service.admin.CountryManager;
import com.aof.service.admin.CurrencyManager;
import com.aof.service.admin.EmailManager;
import com.aof.service.admin.FunctionManager;
import com.aof.service.admin.PurchaseCategoryManager;
import com.aof.service.admin.PurchaseSubCategoryManager;
import com.aof.service.admin.SupplierContractManager;
import com.aof.service.admin.SupplierItemManager;
import com.aof.service.admin.SupplierManager;
import com.aof.service.admin.UserManager;
import com.aof.utils.SessionTempFile;
import com.aof.web.struts.action.BaseAction;
import com.aof.web.struts.action.ServiceLocator;
import com.aof.web.struts.form.admin.SupplierQueryForm;
import com.shcnc.hibernate.PersistentEnum;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.action.ActionUtils;
import com.shcnc.struts.action.BackToInputActionException;
import com.shcnc.struts.form.BeanForm;
import com.shcnc.utils.BeanUtils;
import com.shcnc.utils.ExportUtil;
import com.shcnc.utils.Exportable;

/**
 * Supplier的Action类
 * 
 * @author ych
 * @version 1.0 (Nov 14, 2005)
 */
public class SupplierAction extends BaseAction {

    // private static final String SITE_PROMOTE_STATUS_ALL="siteall";

    /**
     * 查询Supplier
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

        /*
         * if(this.isGlobal(request)) { siteList.add(0,new Site()); }
         */

        SupplierManager sm = ServiceLocator.getSupplierManager(request);

        SupplierQueryForm queryForm = (SupplierQueryForm) form;

        
        if (this.isSite(request) && queryForm.getSite_id()==null) {
            queryForm.setSite_id(((Site)siteList.get(0)).getId().toString()); 
        }
        

        /*
         * if (this.isSite(request)) { if (queryForm.getPromoteStatus()==null ||
         * (queryForm.getPromoteStatus()!=null &&
         * queryForm.getPromoteStatus().trim().length()==0)) {
         * queryForm.setPromoteStatus(SITE_PROMOTE_STATUS_ALL); } }
         */

        Map conditions = constructQueryMap(queryForm);

        String exportType = queryForm.getExportType();
        if (exportType != null && exportType.length() > 0) {
            List data = sm.getSupplierList(conditions, 0, -1, SupplierQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend());

            int index = SessionTempFile.createNewTempFile(request);
            String fileName = "supplier";
            String suffix = ExportUtil.export(exportType, data, request, new FileOutputStream(SessionTempFile.getTempFile(index, request)), new Exportable() {

                public void exportHead(List row, HttpServletRequest request) throws Exception {
                    MessageResources messages = getResources(request);
                    row.add(messages.getMessage(getLocale(request), "supplier.code"));
                    row.add(messages.getMessage(getLocale(request), "supplier.name"));
                    row.add(messages.getMessage(getLocale(request), "supplier.telephone"));
                    row.add(messages.getMessage(getLocale(request), "supplier.fax"));
                    row.add(messages.getMessage(getLocale(request), "supplier.post"));
                    row.add(messages.getMessage(getLocale(request), "supplier.contactor"));
                    row.add(messages.getMessage(getLocale(request), "supplier.contractStatus"));
                    row.add(messages.getMessage(getLocale(request), "supplier.promoteStatus"));
                    row.add(messages.getMessage(getLocale(request), "supplier.status"));
                }

                public void exportRow(List row, Object data, HttpServletRequest request) throws Exception {
                    row.add(BeanUtils.getProperty(data, "code"));
                    row.add(BeanUtils.getProperty(data, "name"));
                    row.add(BeanUtils.getProperty(data, "telephone1"));
                    row.add(BeanUtils.getProperty(data, "fax1"));
                    row.add(BeanUtils.getProperty(data, "post"));
                    row.add(BeanUtils.getProperty(data, "contact"));
                    String locale = getCurrentUser(request).getLocale();
                    if ("en".equals(locale)) {
                        row.add(BeanUtils.getProperty(data, "contractStatus.engShortDescription"));
                        row.add(BeanUtils.getProperty(data, "promoteStatus.engShortDescription"));
                        row.add(BeanUtils.getProperty(data, "enabled.engShortDescription"));
                    } else {
                        row.add(BeanUtils.getProperty(data, "contractStatus.chnShortDescription"));
                        row.add(BeanUtils.getProperty(data, "promoteStatus.chnShortDescription"));
                        row.add(BeanUtils.getProperty(data, "enabled.chnShortDescription"));
                    }
                }
            });
            return new ActionForward("download/" + index + "/" + URLEncoder.encode(fileName, "UTF-8") + '.' + suffix, true);
        }

        if (queryForm.isFirstInit()) {
            queryForm.init(sm.getSupplierListCount(conditions));
        } else {
            queryForm.init();
        }
        int pageNo = queryForm.getPageNoAsInt();
        int pageSize = queryForm.getPageSizeAsInt();

        List result = sm.getSupplierList(conditions, pageNo, pageSize, SupplierQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend());
        request.setAttribute("X_RESULTLIST", result);
        putEnumListToRequest(request);

        request.setAttribute("X_SITELIST", siteList);

        return mapping.findForward("page");
    }

    /**
     * 选择Supplier
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward select(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        SupplierManager sm = ServiceLocator.getSupplierManager(request);

        SupplierQueryForm queryForm = (SupplierQueryForm) form;
        queryForm.setEnabled(EnabledDisabled.ENABLED.getEnumCode().toString());
        Map conditions = constructQueryMap(queryForm);
        queryForm.setPageSize("10");
        if (queryForm.isFirstInit()) {
            queryForm.init(sm.getSupplierListCount(conditions), queryForm.getPageSizeAsInt());
        } else {
            queryForm.init();
        }
        int pageNo = queryForm.getPageNoAsInt();
        int pageSize = queryForm.getPageSizeAsInt();

        List result = sm.getSupplierList(conditions, pageNo, pageSize, SupplierQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend());
        request.setAttribute("X_RESULTLIST", result);

        putEnabledCountryCityListToRequest(request);
        return mapping.findForward("page");
    }

    /**
     * 查询等待确认的Supplier
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward listConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List siteList = this.getAndCheckGrantedSiteList(request);

        SupplierManager sm = ServiceLocator.getSupplierManager(request);
        SupplierQueryForm queryForm = (SupplierQueryForm) form;
        if (this.isSite(request) && queryForm.getSite_id() == null) {
            queryForm.setSite_id(((Site) siteList.get(0)).getId().toString());
        }
        if (queryForm.getConfirmType() == null) {
            queryForm.setConfirmType("ALL");
        }
        if (this.isGlobal(request))
            queryForm.setConfirmGlobal(true);
        else
            queryForm.setConfirmGlobal(false);
        Map conditions = constructQueryMap(queryForm);

        String exportType = queryForm.getExportType();
        if (exportType != null && exportType.length() > 0) {
            List data = sm.getSupplierList(conditions, 0, -1, SupplierQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend());

            int index = SessionTempFile.createNewTempFile(request);
            String fileName = "supplierConfirm";
            String suffix = ExportUtil.export(exportType, data, request, new FileOutputStream(SessionTempFile.getTempFile(index, request)), new Exportable() {

                public void exportHead(List row, HttpServletRequest request) throws Exception {
                    MessageResources messages = getResources(request);
                    row.add(messages.getMessage(getLocale(request), "supplier.code"));
                    row.add(messages.getMessage(getLocale(request), "supplier.name"));
                    row.add(messages.getMessage(getLocale(request), "supplier.confirm.type"));
                    row.add(messages.getMessage(getLocale(request), "supplier.lastModifyDate"));
                }

                public void exportRow(List row, Object data, HttpServletRequest request) throws Exception {
                    row.add(BeanUtils.getProperty(data, "code"));
                    row.add(BeanUtils.getProperty(data, "name"));
                    String locale = getCurrentUser(request).getLocale();
                    if ("en".equals(locale)) {
                        row.add(BeanUtils.getProperty(data, "confirmStatus.engShortDescription"));
                    } else {
                        row.add(BeanUtils.getProperty(data, "confirmStatus.chnShortDescription"));
                    }
                    row.add(BeanUtils.getProperty(data, "lastModifyDate"));
                }
            });
            return new ActionForward("download/" + index + "/" + URLEncoder.encode(fileName, "UTF-8") + '.' + suffix, true);
        }

        if (queryForm.isFirstInit()) {
            queryForm.init(sm.getSupplierListCount(conditions));
        } else {
            queryForm.init();
        }
        int pageNo = queryForm.getPageNoAsInt();
        int pageSize = queryForm.getPageSizeAsInt();

        List result = sm.getSupplierList(conditions, pageNo, pageSize, SupplierQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend());

        request.setAttribute("X_RESULTLIST", result);
        request.setAttribute("X_SITELIST", siteList);
        request.setAttribute("X_CONFIRMSTATUSLIST", PersistentEnum.getEnumList(SupplierConfirmStatus.class));
        return mapping.findForward("page");
    }

    /**
     * 取得enabled的国家城市列表
     * 
     * @param request
     * @return
     */
    private List getEnabledCountryCityList(HttpServletRequest request) {
        CountryManager cm = ServiceLocator.getCountryManager(request);
        return cm.listEnabledCountryCity();
    }

    /**
     * 将enabled的国家城市列表放入request
     * 
     * @param request
     */
    private void putEnabledCountryCityListToRequest(HttpServletRequest request) {
        List countryList = getEnabledCountryCityList(request);
        request.setAttribute("x_countryList", countryList);
    }

    /**
     * 构建查询map
     * 
     * @param queryForm
     * @return
     */
    private Map constructQueryMap(SupplierQueryForm queryForm) {
        Map conditions = new HashMap();
        /* id */
        String id = queryForm.getId();
        if (id != null && id.trim().length() != 0) {
            conditions.put(SupplierQueryCondition.ID_EQ, id);
        }

        String code = queryForm.getCode();
        if (code != null && code.trim().length() != 0) {
            conditions.put(SupplierQueryCondition.CODE_LIKE, code);
        }

        Integer site_id = ActionUtils.parseInt(queryForm.getSite_id());
        if (site_id != null) {
            boolean includeGlobal = queryForm.isIncludeGlobal();
            if (includeGlobal)
                conditions.put(SupplierQueryCondition.GLOBAL_OR_SITE_ID_EQ, site_id);
            else {
                conditions.put(SupplierQueryCondition.SITE_ID_EQ, site_id);
                conditions.put(SupplierQueryCondition.PROMOTE_STATUS_NEQ, SupplierPromoteStatus.GLOBAL);
            }
        } else {
            conditions.put(SupplierQueryCondition.PROMOTE_STATUS_EQ, SupplierPromoteStatus.GLOBAL);
        }

        Integer country_id = ActionUtils.parseInt(queryForm.getCountry_id());
        if (country_id != null && country_id.intValue() != -1) {
            conditions.put(SupplierQueryCondition.COUNTRY_ID_EQ, country_id);
        }

        Integer city_id = ActionUtils.parseInt(queryForm.getCity_id());
        if (city_id != null && city_id.intValue() != -1) {
            conditions.put(SupplierQueryCondition.CITY_ID_EQ, city_id);
        }

        String name = queryForm.getName();
        if (name != null && name.trim().length() != 0) {
            conditions.put(SupplierQueryCondition.NAME_LIKE, name);
        }

        Integer enabled = ActionUtils.parseInt(queryForm.getEnabled());
        if (enabled != null) {
            conditions.put(SupplierQueryCondition.ENABLED_EQ, enabled);
        }

        /*
         * boolean confirmed=queryForm.isConfirmed(); if(confirmed) {
         * conditions.put(SupplierQueryCondition.CONFIRM_EQ,YesNo.YES); }
         */

        /*
         * boolean includeDisabled=queryForm.isIncludeDisabled();
         * if(!includeDisabled) {
         * conditions.put(SupplierQueryCondition.ENABLED_EQ,EnabledDisabled.ENABLED); }
         */

        String contractStatus = queryForm.getContractStatus();
        if (contractStatus != null && contractStatus.trim().length() != 0) {
            Date today = new Date();
            if (contractStatus.equals(ContractStatus.NOT_ACTIVED.getEnumCode().toString())) {
                conditions.put(SupplierQueryCondition.CONTRACT_NOT_ACTIVE, today);
            }
            if (contractStatus.equals(ContractStatus.ACTIVED.getEnumCode().toString())) {
                conditions.put(SupplierQueryCondition.CONTRACT_ACTIVE, new Object[] { today, today, today, today });
            }
            if (contractStatus.equals(ContractStatus.EXPIRED.getEnumCode().toString())) {
                conditions.put(SupplierQueryCondition.CONTRACT_EXPIRED, today);
            }
        }
        String confirmType = queryForm.getConfirmType();
        if (confirmType != null) {
            if (confirmType.equals(SupplierConfirmStatus.NEW.getEnumCode().toString())) {
                conditions.put(SupplierQueryCondition.CONFIRM_TYPE_EQ, SupplierConfirmStatus.NEW.getEnumCode());
            }
            if (confirmType.equals(SupplierConfirmStatus.MODIFY.getEnumCode().toString())) {
                conditions.put(SupplierQueryCondition.CONFIRM_TYPE_EQ, SupplierConfirmStatus.MODIFY.getEnumCode());
            }
            conditions.put(SupplierQueryCondition.CONFIRM_EQ, YesNo.NO);
            boolean confirmGlobal = queryForm.isConfirmGlobal();
            if (confirmGlobal) {
                conditions.put(SupplierQueryCondition.PROMOTE_STATUS_EQ, SupplierPromoteStatus.GLOBAL);
            } else {
                conditions.put(SupplierQueryCondition.PROMOTE_STATUS_NEQ, SupplierPromoteStatus.GLOBAL);
            }
        }
        String promoteStatus = queryForm.getPromoteStatus();
        if (promoteStatus != null) {
            if (promoteStatus.trim().length() != 0) {
                /*
                 * if (promoteStatus.equals(SITE_PROMOTE_STATUS_ALL)) {
                 * conditions.put(SupplierQueryCondition.PROMOTE_STATUS_NEQ,SupplierPromoteStatus.GLOBAL); }
                 * else {
                 * conditions.put(SupplierQueryCondition.PROMOTE_STATUS_EQ,promoteStatus); }
                 */
                conditions.put(SupplierQueryCondition.PROMOTE_STATUS_EQ, promoteStatus);
            }
        }

        return conditions;
    }

    /**
     * 把所要用到的枚举列表放入request
     * 
     * @param request
     * @throws Exception
     */
    private void putEnumListToRequest(HttpServletRequest request) throws Exception {
        request.setAttribute("X_YESNOLIST", PersistentEnum.getEnumList(YesNo.class));
        request.setAttribute("X_ENABLEDDISABLEDLIST", PersistentEnum.getEnumList(EnabledDisabled.class));
        request.setAttribute("X_SUPPLIERSTATUSLIST", PersistentEnum.getEnumList(ContractStatus.class));
        List list = PersistentEnum.getEnumList(SupplierPromoteStatus.class);
        /*
         * if (((String)request.getAttribute("x_version")).length()!=0) {
         * list.remove(SupplierPromoteStatus.GLOBAL); }
         */
        request.setAttribute("X_PROMOTESTATUSLIST", list);
        putEnabledCountryCityListToRequest(request);
        CurrencyManager cm = ServiceLocator.getCurrencyManager(request);
        request.setAttribute("X_CURRENCYLIST", cm.getAllEnabledCurrencyList());
    }

    /**
     * 从request取得Supplier
     * 
     * @param request
     * @return
     * @throws Exception
     */
    private Supplier getSupplierFromRequest(HttpServletRequest request) throws Exception {
        Integer id = new Integer(request.getParameter("id"));
        SupplierManager supplierManager = ServiceLocator.getSupplierManager(request);
        Supplier supplier = supplierManager.getSupplier(id);
        if (supplier == null)
            throw new ActionException("supplier.notFound", id);
        return supplier;
    }

    /**
     * 确认Supplier
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward confirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Supplier supplier = getSupplierFromRequest(request);

        checkSupplier(supplier, request);

        request.setAttribute("x_supplier", supplier);
        if (!isBack(request)) {
            BeanForm supplierForm = (BeanForm) getForm("/confirmSupplierResult", request);
            supplierForm.populateToForm(supplier);
        }
        putContractListToRequest(supplier, request);
        putEnumListToRequest(request);
        request.setAttribute("x_action", "/confirmSupplierResult" + request.getAttribute("x_version"));
        request.setAttribute("x_confirm", "true");

        // putItemListToRequest(supplier,request);

        if (supplier.getConfirmStatus() == SupplierConfirmStatus.NEW)
            return mapping.findForward("pageConfirmNew");
        else
            return mapping.findForward("pageConfirmModify");
    }

    /**
     * 编辑Supplier
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Supplier supplier;

        if (request.getAttribute("x_supplier") == null) {
            supplier = getSupplierFromRequest(request);
            request.setAttribute("x_supplier", supplier);
        } else {
            supplier = (Supplier) request.getAttribute("x_supplier");
        }

        putContractListToRequest(supplier, request);

        /*
         * if( (this.isSite(request) &&
         * supplier.getPromoteStatus().equals(SupplierPromoteStatus.GLOBAL)) ||
         * (this.isGlobal(request) &&
         * (!supplier.getPromoteStatus().equals(SupplierPromoteStatus.GLOBAL)))) {
         * SupplierItemManager sim =
         * ServiceLocator.getSupplierItemManager(request); List
         * itemList=sim.getSupplierItemListBySupplier(supplier);
         * request.setAttribute("X_ITEMLIST",itemList); return
         * mapping.findForward("view"); }
         */

        // checkSupplier(supplier,request);
        if (!isBack(request)) {
            BeanForm supplierForm = (BeanForm) getForm("/updateSupplier", request);
            supplierForm.populateToForm(supplier);
        }

        putEnumListToRequest(request);
        request.setAttribute("x_action", "/updateSupplier" + request.getAttribute("x_version"));

        return mapping.findForward("page");

    }

    /**
     * 新增Supplier
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
            Supplier supplier = new Supplier();
            supplier.setSite(site);

            if (site!=null) supplier.setCurrency(site.getBaseCurrency());
            
            BeanForm supplierForm = (BeanForm) getForm("/insertSupplier", request);
            if (this.isGlobal(request))
                supplier.setPromoteStatus(SupplierPromoteStatus.GLOBAL);
            else
                supplier.setPromoteStatus(SupplierPromoteStatus.SITE);
            supplier.setConfirmed(YesNo.NO);
            supplier.setAirTicket(YesNo.NO);
            supplier.setLastModifyDate(new Date());
            supplierForm.populateToForm(supplier);
        }
        putEnumListToRequest(request);

        return mapping.findForward("page");
    }

    /**
     * 确认Supplier的结果
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward confirmResult(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        Supplier supplier = getSupplierFromRequest(request);
        boolean cancel = (!request.getParameter("cancel").equals("false"));

        checkSupplier(supplier, request);

        BeanForm supplierForm = (BeanForm) form;
        if (!cancel) {
            supplierForm.populateToBean(supplier, request);
        }

        SupplierManager supplierManager = ServiceLocator.getSupplierManager(request);
        if (!cancel)
            supplierManager.confirmSupplier(supplier);
        else
            supplierManager.cancelSupplier(supplier);

        return getForwardFor("listConfirm", null, request);
    }

    /**
     * 更新Supplier
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        Supplier supplier = getSupplierFromRequest(request);

        checkSupplier(supplier, request);

        BeanForm supplierForm = (BeanForm) form;
        supplierForm.populateToBean(supplier, request);
        supplier.setLastModifyDate(new Date());

        SupplierManager supplierManager = ServiceLocator.getSupplierManager(request);
        supplierManager.updateSupplier(supplier);
        this.postGlobalMessage("supplier.update.success", request.getSession());
        
        EmailManager em =ServiceLocator.getEmailManager(request);
        UserManager um = ServiceLocator.getUserManager(request);
        FunctionManager fm = ServiceLocator.getFunctionManager(request);
        Function function;
        List userList;
        if (supplier.getSite()!=null) {
            function = fm.getFunction("siteSupplierConfirm");
            userList = um.getEnabledUserList(function,supplier.getSite());
        } else {
            function= fm.getFunction("globalSupplierConfirm");
            userList = um.getEnabledUserList(function);
        }
        for (Iterator itor = userList.iterator(); itor.hasNext();) {
            User user = (User) itor.next();
            if (user.getEmail()!=null) {
                CountryManager cm1 = ServiceLocator.getCountryManager(request);
                Country country = cm1.getCountry(supplier.getCountry().getId());
                Map context=new HashMap();
                context.put("user",user);
                context.put("supplier",supplier);
                context.put("country",country);
                context.put("role", EmailManager.EMAIL_ROLE_SUPPLIER_MAINTAINER);
                em.insertEmail(user.getPrimarySite(),user.getEmail(),"ModifySupplier.vm",context);
            }
        }
        
        return getForwardFor("edit", supplier.getId(), request);
    }

    private void checkSupplier(Supplier supplier, HttpServletRequest request) throws Exception {
        // global version can only edit global supplier
        // site version can only edit site version
        if (this.isSite(request)) {
            if (supplier.getPromoteStatus().equals(SupplierPromoteStatus.GLOBAL)) {
                throw new ActionException("supplier.error.siteEditGlobal");
            } else {
                this.checkSite(supplier.getSite(), request);
            }
        }
        if (this.isGlobal(request)) {
            if (!supplier.getPromoteStatus().equals(SupplierPromoteStatus.GLOBAL))
                throw new ActionException("supplier.error.globalEditSite");
        }
    }

    /**
     * 插入新增的Supplier
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
        BeanForm supplierForm = (BeanForm) form;
        Supplier supplier = new Supplier();
        supplierForm.populateToBean(supplier, request);

        supplier.setSite(site);

        SupplierManager supplierManager = ServiceLocator.getSupplierManager(request);
        if (supplier.getCode() != null) {
            supplier.setCode(supplier.getCode().trim());
            if (supplierManager.isCodeUsed(supplier.getCode().trim(), supplier.getSite())) {
                throw new BackToInputActionException("supplier.error.codeExist");
            }
        }
        supplier.setLastModifyDate(new Date());
        supplier.setExportStatus(ExportStatus.UNEXPORTED);
        supplierManager.insertSupplier(supplier);
        this.postGlobalMessage("supplier.insert.success", request.getSession());
        
        EmailManager em =ServiceLocator.getEmailManager(request);
        UserManager um = ServiceLocator.getUserManager(request);
        FunctionManager fm = ServiceLocator.getFunctionManager(request);
        Function function;
        List userList;
        if (supplier.getSite()!=null) {
            function = fm.getFunction("siteSupplierConfirm");
            userList = um.getEnabledUserList(function,supplier.getSite());
        } else {
            function= fm.getFunction("globalSupplierConfirm");
            userList = um.getEnabledUserList(function);
        }
        for (Iterator itor = userList.iterator(); itor.hasNext();) {
            User user = (User) itor.next();
            if (user.getEmail()!=null) {
                CountryManager cm1 = ServiceLocator.getCountryManager(request);
                Country country = cm1.getCountry(supplier.getCountry().getId());
                Map context=new HashMap();
                context.put("user",user);
                context.put("supplier",supplier);
                context.put("country",country);
                context.put("role", EmailManager.EMAIL_ROLE_FINANCE);
                em.insertEmail(user.getPrimarySite(),user.getEmail(),"NewSupplier.vm",context);
            }
        }
        
        return getForwardFor("edit", supplier.getId(), request);
    }

    /**
     * 返回跳转的ActionForward
     * 
     * @param actionName
     * @param request
     * @return
     */
    private ActionForward getForwardFor(String actionName, Integer id, HttpServletRequest request) {
        String url = actionName + "Supplier" + request.getAttribute(ATTR_VERSION) + ".do" + (id == null ? "" : "?id=" + id);
        ActionForward forward = new ActionForward(url);
        forward.setRedirect(true);
        return forward;
    }

    /**
     * 返回并检查site列表
     * 
     * @param request
     * @return
     * @throws Exception
     */
    private Site getAndCheckSite(HttpServletRequest request) throws Exception {

        // global version can only create supplier without site
        // site version can only create supplier with site
        if (hasSite(request)) {
            if (this.isGlobal(request)) {
                throw new ActionException("supplier.error.globalNewSite");
            } else {
                return this.getAndCheckSite("site_id", request);
            }
        } else {
            if (this.isGlobal(request))
                return null;
            else
                throw new ActionException("supplier.error.siteNewGlobal");
        }

    }

    /**
     * 是否request含有site_id参数
     * 
     * @param request
     * @return
     */
    private boolean hasSite(HttpServletRequest request) {
        return !StringUtils.isEmpty(request.getParameter("site_id"));
    }

    /**
     * 将SupplierContract列表放入request
     * 
     * @param supplier
     * @param request
     * @throws Exception
     */
    private void putContractListToRequest(Supplier supplier, HttpServletRequest request) throws Exception {
        SupplierContractManager scm = ServiceLocator.getSupplierContractManager(request);
        Map conditions = new HashMap();
        conditions.put(SupplierContractQueryCondition.SUPPLIER_ID_EQ, supplier.getId());
        List result = scm.getSupplierContractList(conditions, 0, -1, SupplierContractQueryOrder.ID, false);
        request.setAttribute("x_contractList", result);
    }

    /**
     * 提出提升请求
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward requestPromote(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        Supplier supplier = getSupplierFromRequest(request);
        this.checkSupplierPromoteStatus(supplier, SupplierPromoteStatus.SITE, request);

        request.setAttribute("x_supplier", supplier);

        if (!isBack(request)) {
            BeanForm supplierForm = (BeanForm) getForm("/requestPromoteSupplierResult", request);
            supplierForm.populateToForm(supplier);
        }
        /*
         * putContractListToRequest(supplier,request);
         * 
         * SupplierItemManager sim =
         * ServiceLocator.getSupplierItemManager(request); List
         * itemList=sim.getSupplierItemListBySupplier(supplier);
         * request.setAttribute("X_ITEMLIST",itemList);
         */

        return mapping.findForward("page");
    }

    /**
     * 提升请求结果
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward requestPromoteResult(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        Supplier supplier = getSupplierFromRequest(request);
        this.checkSupplierPromoteStatus(supplier, SupplierPromoteStatus.SITE, request);

        String promteMsg = request.getParameter("promoteMessage");
        supplier.setPromoteMessage(promteMsg);

        SupplierManager sm = ServiceLocator.getSupplierManager(request);
        sm.requestPromote(supplier.getId(), supplier.getPromoteMessage());

        EmailManager em =ServiceLocator.getEmailManager(request);
        UserManager um = ServiceLocator.getUserManager(request);
        FunctionManager fm = ServiceLocator.getFunctionManager(request);
        Function function = fm.getFunction("siteSupplierMaintenance");
        List userList=um.getEnabledUserList(function,supplier.getSite());
        for (Iterator itor = userList.iterator(); itor.hasNext();) {
            User user = (User) itor.next();
            if (user.getEmail()!=null) {
                Map context=new HashMap();
                context.put("user",user);
                context.put("supplier",supplier);
                context.put("role", EmailManager.EMAIL_ROLE_SUPPLIER_MAINTAINER);
                em.insertEmail(user.getPrimarySite(),user.getEmail(),"SupplierPromote.vm",context);
            }
        }
        
        
        
        
        request.setAttribute("PROMOTE_MESSAGE", promteMsg);

        return mapping.findForward(FORWARD_SUCCESS);
    }

    /**
     * 响应提升请求
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward responsePromote(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        Supplier supplier = getSupplierFromRequest(request);
        this.changeSubCategory(supplier,request);
        this.checkSupplierPromoteStatus(supplier, SupplierPromoteStatus.REQUEST, request);

        SupplierItemManager sim = ServiceLocator.getSupplierItemManager(request);
        List conflictItemList = sim.getSupplierItemListConflictWithPromoteBySupplierGroupByPurchaseSubCategory(supplier);
        if (conflictItemList.size()>0) {
            request.setAttribute("x_conflictItemList",conflictItemList);
            request.setAttribute("x_supplier", supplier);
            this.putEnabledGlobalPurchaseCategoryToRequest(request);
            this.postGlobalMessage("supplier.response.fail", request.getSession());
            return mapping.findForward(FORWARD_FAIL);
        } else {
            SupplierManager sm = ServiceLocator.getSupplierManager(request);
            supplier = sm.responsePromote(supplier.getId());
            request.setAttribute("x_supplier", supplier);
            this.postGlobalMessage("supplier.response.success", request.getSession());
            return mapping.findForward(FORWARD_SUCCESS);
        }
    }
    
    private void changeSubCategory(Supplier supplier,HttpServletRequest request) {
        SupplierItemManager sim = ServiceLocator.getSupplierItemManager(request);
        String changeId[]=request.getParameterValues("changeSubCategoryId");
        if (changeId!=null) { 
            for (int index=0;index<changeId.length;index++) {
                Integer originalId=ActionUtils.parseInt(changeId[index]);
                Integer destId=ActionUtils.parseInt(request.getParameter("purchaseSubCategory_"+changeId[index]));
                PurchaseSubCategoryManager pm=ServiceLocator.getPurchaseSubCategoryManager(request);
                PurchaseSubCategory destPurchaseSubCategory=pm.getPurchaseSubCategory(destId);
                sim.changePurchaseSubCategoryBySupplierAndPurchaseSubCategory(supplier.getId(),originalId,destPurchaseSubCategory);
                /*List itemList=sim.getSupplierItemListBySupplierPurchaseSubCategoryId(supplier.getId(),originalId);
                for (Iterator itor = itemList.iterator(); itor.hasNext();) {
                    SupplierItem item = (SupplierItem) itor.next();
                    item.setPurchaseSubCategory(destPurchaseSubCategory);
                    sim.updateSupplierItem(item);
                }*/
            }
        }
    }

    /**
     * 响应提升结果
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward responsePromoteResult(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        Supplier supplier = getSupplierFromRequest(request);
        this.checkSupplierPromoteStatus(supplier, SupplierPromoteStatus.REQUEST, request);

        SupplierManager sm = ServiceLocator.getSupplierManager(request);
        sm.responsePromote(supplier.getId());
        return getForwardFor("list", null, request);
    }

    /**
     * 察看Supplier基本信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward viewBaseInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        Supplier supplier = getSupplierFromRequest(request);
        request.setAttribute("x_supplier", supplier);
        return mapping.findForward("page");
    }

    public ActionForward viewAviableSupplier(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer siteId = new Integer(request.getParameter("siteId"));
        Integer purchaseSubCategoryId = new Integer(request.getParameter("subCategoryValue"));
        SupplierManager sm = ServiceLocator.getSupplierManager(request);
        List result = sm.getSuitableSupplierListForPurchase(new Site(siteId), new PurchaseSubCategory(purchaseSubCategoryId), null);       
        request.setAttribute("X_RESULTLIST", result);
        return mapping.findForward("page");
    }
    
    /**
     * 提升Supplier
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    /*
     * public ActionForward promote(ActionMapping mapping, ActionForm form,
     * HttpServletRequest request, HttpServletResponse response) throws
     * Exception {
     * 
     * Supplier supplier = getSupplierFromRequest(request);
     * this.checkSupplierPromoteStatus(supplier,
     * SupplierPromoteStatus.RESPONSED, request);
     * 
     * 
     * request.setAttribute("x_supplier",supplier); if (!isBack(request)) {
     * BeanForm supplierForm = (BeanForm) getForm("/promoteSupplierCreate",
     * request); supplierForm.populateToForm(supplier); }
     * putContractListToRequest(supplier,request);
     * putEnumListToRequest(request);
     * 
     * putItemListToRequest(supplier,request);
     * 
     * return mapping.findForward("page"); }
     * 
     *//**
         * 提升Supplier的save处理
         * 
         * @param mapping
         * @param form
         * @param request
         * @param response
         * @return
         * @throws Exception
         */
    /*
     * public ActionForward promoteSave(ActionMapping mapping, ActionForm form,
     * HttpServletRequest request, HttpServletResponse response) throws
     * Exception {
     * 
     * Supplier supplier = getSupplierFromRequest(request);
     * this.checkSupplierPromoteStatus(supplier,
     * SupplierPromoteStatus.RESPONSED, request);
     * 
     * BeanForm supplierForm = (BeanForm) form;
     * supplierForm.populateToBean(supplier, request);
     * 
     * SupplierManager supplierManager
     * =ServiceLocator.getSupplierManager(request);
     * supplierManager.updateSupplier(supplier);
     * 
     * return getForwardFor("list",request); }
     * 
     *//**
         * 提升Supplier的delete处理
         * 
         * @param mapping
         * @param form
         * @param request
         * @param response
         * @return
         * @throws Exception
         */
    /*
     * public ActionForward promoteDelete(ActionMapping mapping, ActionForm
     * form, HttpServletRequest request, HttpServletResponse response) throws
     * Exception {
     * 
     * Supplier supplier = getSupplierFromRequest(request);
     * this.checkSupplierPromoteStatus(supplier,
     * SupplierPromoteStatus.RESPONSED, request);
     * 
     * SupplierManager supplierManager
     * =ServiceLocator.getSupplierManager(request);
     * supplierManager.promoteDelete(supplier);
     * 
     * return getForwardFor("list",request); }
     * 
     *//**
         * 提升Supplier的create处理
         * 
         * @param mapping
         * @param form
         * @param request
         * @param response
         * @return
         * @throws Exception
         */
    /*
     * public ActionForward promoteCreate(ActionMapping mapping, ActionForm
     * form, HttpServletRequest request, HttpServletResponse response) throws
     * Exception {
     * 
     * Supplier supplier = getSupplierFromRequest(request);
     * this.checkSupplierPromoteStatus(supplier,
     * SupplierPromoteStatus.RESPONSED, request);
     * 
     * BeanForm supplierForm = (BeanForm) form;
     * supplierForm.populateToBean(supplier, request);
     * 
     * SupplierManager supplierManager
     * =ServiceLocator.getSupplierManager(request);
     * supplierManager.promoteCreate(supplier);
     * 
     * return getForwardFor("list",request); }
     */

    /**
     * 检查Supplier的PromoteStatus
     * 
     * @param supplier
     * @param status
     * @param request
     * @throws Exception
     */
    private void checkSupplierPromoteStatus(Supplier supplier, SupplierPromoteStatus status, HttpServletRequest request) throws Exception {
        if (this.isSite(request)) {
            if (!status.equals(SupplierPromoteStatus.REQUEST)) {
                throw new RuntimeException("status error");
            }
            this.checkSite(supplier.getSite(), request);
        }
        if (!supplier.getPromoteStatus().equals(status)) {
            throw new ActionException("supplier.promote.statusError", supplier.getName(), status.getLabel());
        }
        if (supplier.getEnabled().equals(EnabledDisabled.DISABLED)) {
            throw new ActionException("supplier.promote.disabled", supplier.getName());
        }
    }
    
    private void putEnabledGlobalPurchaseCategoryToRequest(HttpServletRequest request) throws Exception {
        PurchaseCategoryManager pcm=ServiceLocator.getPurchaseCategoryManager(request);
        request.setAttribute("x_purchaseCategoryList",pcm.getEnabledPurchaseCategorySubCategoryOfGlobal());
    }

}