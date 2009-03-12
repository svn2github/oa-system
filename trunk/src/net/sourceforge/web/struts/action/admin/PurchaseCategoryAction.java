/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.web.struts.action.admin;

import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import net.sourceforge.model.admin.PurchaseCategory;
import net.sourceforge.model.admin.Site;
import net.sourceforge.model.admin.query.PurchaseCategoryQueryCondition;
import net.sourceforge.model.admin.query.PurchaseCategoryQueryOrder;
import net.sourceforge.model.metadata.EnabledDisabled;
import net.sourceforge.service.admin.PurchaseCategoryManager;
import net.sourceforge.utils.SessionTempFile;
import net.sourceforge.web.struts.action.BaseAction;
import net.sourceforge.web.struts.form.admin.PurchaseCategoryQueryForm;
import com.shcnc.hibernate.PersistentEnum;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.action.ActionUtils;
import com.shcnc.struts.form.BeanForm;
import com.shcnc.utils.BeanUtils;
import com.shcnc.utils.ExportUtil;
import com.shcnc.utils.Exportable;
import net.sourceforge.web.struts.action.ServiceLocator;
/**
 * PurchaseCategory的Action类
 * @author ych
 * @version 1.0 (Nov 14, 2005)
 */
public class PurchaseCategoryAction extends BaseAction {
    
    /**
     * 查询PurchaseCategory
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List siteList=new ArrayList();
        if (this.isSite(request))
            siteList=this.getAndCheckGrantedSiteList(request);

		PurchaseCategoryManager pcm = ServiceLocator.getPurchaseCategoryManager(request);
        PurchaseCategoryQueryForm queryForm = (PurchaseCategoryQueryForm) form;
        if (this.isSite(request)) {
            if (queryForm.getSiteId()==null) {
            	queryForm.setSiteId(((Site)siteList.get(0)).getId().toString());
            }
        } else {
            queryForm.setSiteId(null);
        }
        Map conditions = constructQueryMap(queryForm);

        String exportType = queryForm.getExportType();
        if (exportType != null && exportType.length() > 0) {
            List data =pcm.getPurchaseCategoryList(conditions, 0, -1, PurchaseCategoryQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend());
            
            int index = SessionTempFile.createNewTempFile(request);
            String fileName = "purchaseCategory";
            String suffix = ExportUtil.export(exportType, data, request, new FileOutputStream(SessionTempFile.getTempFile(index, request)), new Exportable() {

                        public void exportHead(List row, HttpServletRequest request) throws Exception {
                            MessageResources messages = getResources(request);
                            row.add(messages.getMessage(getLocale(request), "purchaseCategory.id"));
                            row.add(messages.getMessage(getLocale(request), "purchaseCategory.description"));
                            row.add(messages.getMessage(getLocale(request), "purchaseCategory.status"));
                        }

                        public void exportRow(List row, Object data, HttpServletRequest request) throws Exception {
                            row.add(BeanUtils.getProperty(data, "id"));
                            row.add(BeanUtils.getProperty(data, "description"));
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
        
        if(queryForm.isFirstInit())
		{
			queryForm.init(pcm.getPurchaseCategoryListCount(conditions));
		}
		else
		{
			queryForm.init();
		}
		int pageNo=queryForm.getPageNoAsInt();
		int pageSize=queryForm.getPageSizeAsInt();
		
        request.setAttribute("X_ENABLEDDISABLEDLIST", PersistentEnum.getEnumList(EnabledDisabled.class));
        request.setAttribute("X_SITELIST",siteList);
        request.setAttribute("X_RESULTLIST", pcm.getPurchaseCategoryList(conditions, pageNo, pageSize, PurchaseCategoryQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend()));
        return mapping.findForward("page");
    }
    
    /**
     * 构建查询map
     * @param queryForm
     * @return
     */
    private Map constructQueryMap(PurchaseCategoryQueryForm queryForm) {
        Map conditions = new HashMap();
         
        Integer id=ActionUtils.parseInt(queryForm.getId());
        if (id!=null)
            conditions.put(PurchaseCategoryQueryCondition.ID_EQ,id);
        Integer site_id = ActionUtils.parseInt(queryForm.getSiteId());
		if (site_id != null) 
		{
			conditions.put(PurchaseCategoryQueryCondition.SITE_ID_EQ,site_id);
		} else {
            conditions.put(PurchaseCategoryQueryCondition.GLOBAL,null);
        }
		String description = queryForm.getDescription();
		if(description != null && description.trim().length()!=0)
		{
			conditions.put(PurchaseCategoryQueryCondition.DESCRIPTION_LIKE,description.trim());
		}
        String status = queryForm.getStatus();
        if (status!=null && status.trim().length()!=0) {
            if (status.equals(EnabledDisabled.ENABLED.getEnumCode().toString()))
                conditions.put(PurchaseCategoryQueryCondition.ENABLED_EQ,EnabledDisabled.ENABLED.getEnumCode());
            if (status.equals(EnabledDisabled.DISABLED.getEnumCode().toString()))
                conditions.put(PurchaseCategoryQueryCondition.ENABLED_EQ,EnabledDisabled.DISABLED.getEnumCode());
        }
        return conditions;
    }
    
    /**
     * 编辑PurchaseCategory
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!isBack(request)) {
            Integer id = ActionUtils.parseInt(request.getParameter("id"));
            PurchaseCategoryManager purchaseCategoryManager = ServiceLocator.getPurchaseCategoryManager(request);
            PurchaseCategory purchaseCategory = purchaseCategoryManager.getPurchaseCategory(id);
            if (purchaseCategory == null) throw new ActionException("purchaseCategory.notFound", id);
            BeanForm purchaseCategoryForm = (BeanForm) getForm("/updatePurchaseCategory", request);
            purchaseCategoryForm.populateToForm(purchaseCategory);
        }
        request.setAttribute("X_ENABLEDDISABLEDLIST", PersistentEnum.getEnumList(EnabledDisabled.class));
        return mapping.findForward("page");
    }

    /**
     * 更新PurchaseCategory
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PurchaseCategory purchaseCategory = getPurchaseCategoryFromRequest(request);
        checkPurchaseCategory(purchaseCategory, request);
        
        BeanForm purchaseCategoryForm = (BeanForm) form;
        purchaseCategoryForm.populateToBean(purchaseCategory,request);
        PurchaseCategoryManager purchaseCategoryManager =ServiceLocator.getPurchaseCategoryManager(request);
        request.setAttribute("X_OBJECT", purchaseCategoryManager.updatePurchaseCategory(purchaseCategory));
        request.setAttribute("X_ROWPAGE", "purchaseCategory/row.jsp");
        return mapping.findForward("success");
    }
    
    

    /**
     * 新增PurchaseCategory
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
            PurchaseCategory purchaseCategory = new PurchaseCategory();
            purchaseCategory.setSite(site);
            BeanForm purchaseCategoryForm = (BeanForm) getForm("/insertPurchaseCategory", request);
            purchaseCategoryForm.populateToForm(purchaseCategory);
        }
        request.setAttribute("X_ENABLEDDISABLEDLIST", PersistentEnum.getEnumList(EnabledDisabled.class));
        return mapping.findForward("page");
    }

    /**
     * 插入新增的PurchaseCategory
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward insert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Site site = this.getAndCheckSite(request);
        PurchaseCategoryManager cm = ServiceLocator.getPurchaseCategoryManager(request);
        BeanForm purchaseCategoryForm = (BeanForm) form;
        PurchaseCategory purchaseCategory = new PurchaseCategory();
        purchaseCategoryForm.populateToBean(purchaseCategory, request);
        purchaseCategory.setSite(site);
        request.setAttribute("X_OBJECT", cm.insertPurchaseCategory(purchaseCategory));
        request.setAttribute("X_ROWPAGE", "purchaseCategory/row.jsp");
        return mapping.findForward("success");
    }
    
    private void checkPurchaseCategory(PurchaseCategory purchaseCategory, HttpServletRequest request) throws Exception {
        if (this.isSite(request)) {
            if (isGlobalPurchaseCategory(purchaseCategory)) {
                throw new ActionException("purchaseCategory.error.siteEditGlobal");
            } else {
                this.checkSite(purchaseCategory.getSite(), request);
            }
        }
        if (this.isGlobal(request)) {
            if (!isGlobalPurchaseCategory(purchaseCategory))
                throw new ActionException("purchaseCategory.error.globalEditSite");
        }
    }
    
    private boolean isGlobalPurchaseCategory(PurchaseCategory purchaseCategory) {
        return purchaseCategory.getSite()==null;
    }
    
    private Site getAndCheckSite(HttpServletRequest request) throws Exception {
        // global version can only create purchaseCategory without site
        // site version can only create purchaseCategory with site
        if (hasSite(request)) {
            if (this.isGlobal(request)) {
                throw new ActionException("purchaseCategory.error.globalNewSite");
            } else {
                return this.getAndCheckSite("site_id", request);
            }
        } else {
            if (this.isGlobal(request))
                return null;
            else
                throw new ActionException("purchaseCategory.error.siteNewGlobal");
        }

    }
    
    private boolean hasSite(HttpServletRequest request) {
        return !StringUtils.isEmpty(request.getParameter("site_id"));
    }
    
    private PurchaseCategory getPurchaseCategoryFromRequest(HttpServletRequest request) {
        Integer id = new Integer(request.getParameter("id"));
        PurchaseCategoryManager purchaseCategoryManager =ServiceLocator.getPurchaseCategoryManager(request);
        PurchaseCategory purchaseCategory=purchaseCategoryManager.getPurchaseCategory(id);
        if (purchaseCategory == null)
            throw new ActionException("purchaseCategory.notFound", id);
        return purchaseCategory;
    }
 }