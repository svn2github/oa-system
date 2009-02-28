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
import java.util.Map;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.aof.model.admin.PurchaseCategory;
import com.aof.model.admin.PurchaseSubCategory;
import com.aof.model.admin.query.PurchaseSubCategoryQueryCondition;
import com.aof.model.admin.query.PurchaseSubCategoryQueryOrder;
import com.aof.model.admin.query.UserQueryCondition;
import com.aof.model.admin.query.UserQueryOrder;
import com.aof.model.metadata.EnabledDisabled;
import com.aof.service.admin.PurchaseCategoryManager;
import com.aof.service.admin.PurchaseSubCategoryManager;
import com.aof.service.admin.SupplierManager;
import com.aof.service.admin.UserManager;
import com.aof.utils.SessionTempFile;
import com.aof.web.struts.action.BaseAction;
import com.aof.web.struts.form.admin.PurchaseSubCategoryQueryForm;
import com.aof.web.struts.form.admin.UserQueryForm;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.action.ActionUtils;
import com.shcnc.struts.form.BeanForm;
import com.shcnc.utils.BeanUtils;
import com.shcnc.utils.ExportUtil;
import com.shcnc.utils.Exportable;
import com.shcnc.hibernate.PersistentEnum;
import com.aof.web.struts.action.ServiceLocator;

/**
 * PurchaseSubCategory的Action类
 * @author ych
 * @version 1.0 (Nov 14, 2005)
 */
public class PurchaseSubCategoryAction extends BaseAction {
    
    /**
     * 查询PurchaseSubCategory
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	    
        PurchaseSubCategoryQueryForm queryForm = (PurchaseSubCategoryQueryForm) form;
        PurchaseCategoryManager pcm=ServiceLocator.getPurchaseCategoryManager(request);
        PurchaseCategory purchaseCategory=pcm.getPurchaseCategory(new Integer(queryForm.getPurchaseCategory_id()));
       	if (purchaseCategory == null) throw new ActionException("purchaseCategory.notFound", queryForm.getPurchaseCategory_id());
       	request.setAttribute("X_PURCHASECATEGORY", purchaseCategory);
       	
        PurchaseSubCategoryManager fm =ServiceLocator.getPurchaseSubCategoryManager(request);
        Map conditions = constructQueryMap(queryForm);
        queryForm.setPageSize("5");
        if(queryForm.isFirstInit())
		{
			queryForm.init(fm.getPurchaseSubCategoryListCount(conditions),queryForm.getPageSizeAsInt());
		}
		else
		{
			queryForm.init();
		}
		int pageNo=queryForm.getPageNoAsInt();
		int pageSize=queryForm.getPageSizeAsInt();
		
        List result=fm.getPurchaseSubCategoryList(
        	conditions, pageNo, pageSize, PurchaseSubCategoryQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend());
        request.setAttribute("X_RESULTLIST", result);
        return mapping.findForward("page");
    }
    
    public ActionForward selectInspector(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        UserQueryForm queryForm = (UserQueryForm) form;
                
        UserManager um =  ServiceLocator.getUserManager(request);
        
        Map conditions = constructUserQueryMap(queryForm);
        
        String exportType = queryForm.getExportType();
        if (exportType != null && exportType.length() > 0) {
            List data = um.getUserList(conditions, 0, -1, UserQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend());
            int index = SessionTempFile.createNewTempFile(request);
            String fileName = "user";
            String suffix = ExportUtil.export(exportType, data, request, new FileOutputStream(SessionTempFile.getTempFile(index, request)), new Exportable() {

                        public void exportHead(List row, HttpServletRequest request) throws Exception{
                            MessageResources messages = getResources(request);
                            row.add(messages.getMessage(getLocale(request), "user.loginName"));
                            row.add(messages.getMessage(getLocale(request), "user.name"));
                            row.add(messages.getMessage(getLocale(request), "user.email"));
                            row.add(messages.getMessage(getLocale(request), "user.telephone"));
                            row.add(messages.getMessage(getLocale(request), "user.enabled"));
                        }

                        public void exportRow(List row, Object data, HttpServletRequest request) throws Exception {
                            row.add(BeanUtils.getProperty(data, "loginName"));
                            row.add(BeanUtils.getProperty(data, "name"));
                            row.add(BeanUtils.getProperty(data, "email"));
                            row.add(BeanUtils.getProperty(data, "telephone"));
                            String locale = getCurrentUser(request).getLocale();
                            if ("en".equals(locale)) {
                                row.add(BeanUtils.getProperty(data, "enabled.engShortDescription"));
                            } else {
                                row.add(BeanUtils.getProperty(data, "enabled.chnShortDescription"));
                            }
                        }
                    });
            return new ActionForward("download/" + index + "/" + URLEncoder.encode(fileName, "UTF-8") + '.' + suffix, true);
        }

        if (queryForm.isFirstInit()) {
            queryForm.init(um.getUserListCount(conditions));
        } else {
            queryForm.init();
        }

        request.setAttribute("X_RESULTLIST", um.getUserList(conditions, queryForm.getPageNoAsInt(), queryForm.getPageSizeAsInt(), UserQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend()));
        return mapping.findForward("page");
        
    }
    
    
    /**
     * 构建查询map
     * @param queryForm
     * @return
     */
    private Map constructQueryMap(PurchaseSubCategoryQueryForm queryForm) {
        Map conditions = new HashMap();
		Integer purchaseCategory_id =
			ActionUtils.parseInt(queryForm.getPurchaseCategory_id());
		if (purchaseCategory_id != null) 
		{
			conditions.put(PurchaseSubCategoryQueryCondition.PURCHASECATEGORY_ID_EQ,
				 purchaseCategory_id);
		}
		String defaultSupplier_id = 
			queryForm.getDefaultSupplier_id();
		if(defaultSupplier_id != null && defaultSupplier_id.trim().length()!=0)
		{
			conditions.put(PurchaseSubCategoryQueryCondition.DEFAULTSUPPLIER_ID_EQ,
				 defaultSupplier_id);
		}
		String description = 
			queryForm.getDescription();
		if(description != null && description.trim().length()!=0)
		{
			conditions.put(PurchaseSubCategoryQueryCondition.DESCRIPTION_LIKE,
				 description);
		}
        return conditions;
    }
    
    
    
    private Map constructUserQueryMap(UserQueryForm queryForm) {
        Map conditions = new HashMap();

        String loginName = queryForm.getLoginName();
        if (loginName != null) {
            loginName = loginName.trim();
            if (loginName.length() == 0) loginName = null;
        }
        if (loginName != null) conditions.put(UserQueryCondition.LOGINNAME_LIKE, loginName);

        String name = queryForm.getName();
        if (name != null) {
            name = name.trim();
            if (name.length() == 0) name = null;
        }
        if (name != null) conditions.put(UserQueryCondition.NAME_LIKE, name);
        
        Integer siteId = ActionUtils.parseInt(queryForm.getSiteId());
        conditions.put(UserQueryCondition.SITE_ID_EQ, siteId);
        conditions.put(UserQueryCondition.ENABLED_EQ, EnabledDisabled.ENABLED);
        
        return conditions;
    }

    /**
     * 把所要用到的枚举列表放入request
     * @param request
     */
	private void putEnumListToRequest(HttpServletRequest request) {
		request.setAttribute("X_ENABLEDDISABLEDLIST", PersistentEnum
				.getEnumList(EnabledDisabled.class));
	}

	
	

    /**
     * 编辑PurchaseSubCategory
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	PurchaseSubCategory purchaseSubCategory = getPurchaseSubCategoryFromRequest(request);    
        checkPurchaseCategory(purchaseSubCategory.getPurchaseCategory(),request,false);
        
		request.setAttribute("x_purchaseSubCategory",purchaseSubCategory);
        if (this.isSite(request))
            request.setAttribute("x_site_id",purchaseSubCategory.getPurchaseCategory().getSite().getId());
        if (!isBack(request)) {
            BeanForm purchaseSubCategoryForm = (BeanForm) getForm("/updatePurchaseSubCategory", request);
            purchaseSubCategoryForm.populateToForm(purchaseSubCategory);
        }
        putEnumListToRequest(request);
        
        return mapping.findForward("page");
    }

    /**
     * 新增PurchaseSubCategory
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
	public ActionForward newObject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PurchaseCategory purchaseCategory=getPurchaseCategoryFromRequest(request);
        checkPurchaseCategory(purchaseCategory,request,true);
        if (!isBack(request)) {
            if (this.isSite(request))
                request.setAttribute("x_site_id",purchaseCategory.getSite().getId());
            PurchaseSubCategory purchaseSubCategory = new PurchaseSubCategory();
            purchaseSubCategory.setPurchaseCategory(purchaseCategory);
            BeanForm purchaseSubCategoryForm = (BeanForm) getForm("/insertPurchaseSubCategory", request);
            purchaseSubCategoryForm.populateToForm(purchaseSubCategory);
        }
        putEnumListToRequest(request);
        return mapping.findForward("page");
    }

    /**
     * 更新PurchaseSubCategory
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PurchaseSubCategory purchaseSubCategory = getPurchaseSubCategoryFromRequest(request);
        checkPurchaseCategory(purchaseSubCategory.getPurchaseCategory(),request,false);
        BeanForm purchaseSubCategoryForm = (BeanForm) form;
        purchaseSubCategoryForm.populateToBean(purchaseSubCategory, request);
        SupplierManager sm=ServiceLocator.getSupplierManager(request);
        purchaseSubCategory.setDefaultSupplier(sm.getSupplier(ActionUtils.parseInt((String) purchaseSubCategoryForm.get("defaultSupplier_id"))));
        PurchaseSubCategoryManager purchaseSubCategoryManager = ServiceLocator.getPurchaseSubCategoryManager(request);
        request.setAttribute("X_OBJECT", purchaseSubCategoryManager.updatePurchaseSubCategory(purchaseSubCategory));
        request.setAttribute("X_ROWPAGE", "purchaseSubCategory/row.jsp");
        return mapping.findForward("success");
    }
    
    /**
     * 插入新增的PurchaseSubCategory
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward insert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PurchaseCategory purchaseCategory=getPurchaseCategoryFromRequest(request);
        checkPurchaseCategory(purchaseCategory,request,true);
        
        BeanForm purchaseSubCategoryForm = (BeanForm) form;
        PurchaseSubCategory purchaseSubCategory = new PurchaseSubCategory();
        purchaseSubCategoryForm.populateToBean(purchaseSubCategory, request);
        purchaseSubCategory.setPurchaseCategory(purchaseCategory);
        
        SupplierManager sm=ServiceLocator.getSupplierManager(request);
        purchaseSubCategory.setDefaultSupplier(sm.getSupplier(ActionUtils.parseInt((String)purchaseSubCategoryForm.get("defaultSupplier_id"))));
        
        PurchaseSubCategoryManager purchaseSubCategoryManager =ServiceLocator.getPurchaseSubCategoryManager(request);
		request.setAttribute("X_OBJECT", purchaseSubCategoryManager.insertPurchaseSubCategory(purchaseSubCategory));
        request.setAttribute("X_ROWPAGE", "purchaseSubCategory/row.jsp");
        
        return mapping.findForward("success");
    }	

    /**
     * 从request得到PurchaseSubCategory 
     * @param request
     * @return
     * @throws Exception
     */
    private PurchaseSubCategory getPurchaseSubCategoryFromRequest(HttpServletRequest request)
            throws Exception {
        Integer id = ActionUtils.parseInt(request.getParameter("id"));
        PurchaseSubCategoryManager purchaseSubCategoryManager =ServiceLocator.getPurchaseSubCategoryManager(request);
        PurchaseSubCategory purchaseSubCategory = purchaseSubCategoryManager.getPurchaseSubCategory(id);
        if (purchaseSubCategory == null)
            throw new ActionException("purchaseSubCategory.notFound", id);
        return purchaseSubCategory;
    }
    
    /**
     * 从request得到PurchaseCategory 
     * @param request
     * @return
     * @throws Exception
     */
    private PurchaseCategory getPurchaseCategoryFromRequest(HttpServletRequest request) {
        Integer id = new Integer(request.getParameter("purchaseCategory_id"));
        PurchaseCategoryManager purchaseCategoryManager =ServiceLocator.getPurchaseCategoryManager(request);
        PurchaseCategory purchaseCategory=purchaseCategoryManager.getPurchaseCategory(id);
        if (purchaseCategory == null)
            throw new ActionException("purchaseCategory.notFound", id);
        return purchaseCategory;
    }
    
    private void checkPurchaseCategory(PurchaseCategory purchaseCategory, HttpServletRequest request,boolean isNew) throws Exception {
        if (this.isSite(request)) {
            if (isGlobalPurchaseCategory(purchaseCategory)) {
                if (isNew)
                    throw new ActionException("purchaseSubCategory.error.siteNewGlobal");
                else
                    throw new ActionException("purchaseSubCategory.error.siteEditGlobal");
            } else {
                this.checkSite(purchaseCategory.getSite(), request);
            }
        }
        if (this.isGlobal(request)) {
            if (!isGlobalPurchaseCategory(purchaseCategory))
                if (isNew)
                    throw new ActionException("purchaseSubCategory.error.globalNewSite");
                else
                    throw new ActionException("purchaseSubCategory.error.globalEditSite");
        }
    }
    
    private boolean isGlobalPurchaseCategory(PurchaseCategory purchaseCategory) {
        return purchaseCategory.getSite()==null;
    }

}