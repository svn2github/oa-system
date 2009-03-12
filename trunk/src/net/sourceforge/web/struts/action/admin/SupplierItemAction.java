/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.web.struts.action.admin;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import net.sourceforge.model.admin.PurchaseCategory;
import net.sourceforge.model.admin.PurchaseSubCategory;
import net.sourceforge.model.admin.Site;
import net.sourceforge.model.admin.Supplier;
import net.sourceforge.model.admin.SupplierItem;
import net.sourceforge.model.admin.query.SupplierItemQueryCondition;
import net.sourceforge.model.admin.query.SupplierItemQueryOrder;
import net.sourceforge.model.metadata.EnabledDisabled;
import net.sourceforge.model.metadata.SupplierPromoteStatus;
import net.sourceforge.service.admin.CurrencyManager;
import net.sourceforge.service.admin.PurchaseCategoryManager;
import net.sourceforge.service.admin.SupplierItemManager;
import net.sourceforge.service.admin.SupplierManager;
import net.sourceforge.utils.SessionTempFile;
import net.sourceforge.web.struts.action.BaseAction;
import net.sourceforge.web.struts.action.ServiceLocator;
import net.sourceforge.web.struts.form.admin.SupplierItemQueryForm;
import com.shcnc.hibernate.PersistentEnum;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.action.ActionUtils;
import com.shcnc.struts.form.BeanForm;
import com.shcnc.utils.BeanUtils;
import com.shcnc.utils.ExportUtil;
import com.shcnc.utils.Exportable;

/**
 * SupplierItem的Action类
 * @author ych
 * @version 1.0 (Nov 14, 2005)
 */
public class SupplierItemAction extends BaseAction {
    
    /**
     * 查询SupplierItem
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Supplier supplier=getSupplierFromRequest(request);
        
        request.setAttribute("x_supplier",supplier);
        SupplierItemQueryForm queryForm = (SupplierItemQueryForm) form;
        request.setAttribute("x_back",queryForm.getBackPage());
        request.setAttribute("x_fromPO",queryForm.getFromPO());
        queryForm.setSupplier_id(supplier.getId().toString());
        SupplierItemManager sm =  ServiceLocator.getSupplierItemManager(request);
        Map conditions=new HashMap();
        if (request.getMethod().equals("POST")) {
            conditions = constructQueryMap(queryForm);
            
            if(queryForm.isFirstInit())
            {
                queryForm.init(sm.getSupplierItemListCount(conditions));
            }
            else
            {
                queryForm.init();
            }
            int pageNo=queryForm.getPageNoAsInt();
            int pageSize=queryForm.getPageSizeAsInt();
            request.setAttribute("X_RESULTLIST", sm.getSupplierItemList(conditions, pageNo, pageSize, SupplierItemQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend()));
        } else {
            if(queryForm.isFirstInit())
            {
                queryForm.init(0);
            }
            else
            {
                queryForm.init();
            }
        }
        
        String exportType = queryForm.getExportType();
        if (exportType != null && exportType.length() > 0) {
            List data;
            
            data =sm.getSupplierItemList(conditions, 0, -1, SupplierItemQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend());
            
            int index = SessionTempFile.createNewTempFile(request);
            String fileName = "supplierItem";
            String suffix = ExportUtil.export(exportType, data, request, new FileOutputStream(SessionTempFile.getTempFile(index, request)), new Exportable() {

                        public void exportHead(List row, HttpServletRequest request) throws Exception {
                            MessageResources messages = getResources(request);
                            row.add(messages.getMessage(getLocale(request), "supplierItem.purchaseCategory"));
                            row.add(messages.getMessage(getLocale(request), "supplierItem.purchaseSubCategory"));
                            row.add(messages.getMessage(getLocale(request), "supplierItem.sepc"));
                            row.add(messages.getMessage(getLocale(request), "supplierItem.unitPrice"));
                            row.add(messages.getMessage(getLocale(request), "supplierItem.currency.code"));
                            row.add(messages.getMessage(getLocale(request), "supplierItem.status"));
                        }

                        public void exportRow(List row, Object data, HttpServletRequest request) throws Exception {
                            row.add(BeanUtils.getProperty(data, "purchaseSubCategory.purchaseCategory.description"));
                            row.add(BeanUtils.getProperty(data, "purchaseSubCategory.description"));
                            row.add(BeanUtils.getProperty(data, "sepc"));
                            row.add(BeanUtils.getProperty(data, "unitPrice"));
                            row.add(BeanUtils.getProperty(data, "currency.name"));
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
        putEnabledPurchaseCategoryToRequest(request,supplier.getSite());
        if (queryForm.getFromPO().equals("false"))
            return mapping.findForward("page");
        else
            return mapping.findForward("dialogPage");
    }
    
    private Map constructQueryMap(SupplierItemQueryForm queryForm) {
        Map conditions = new HashMap();

        Integer supplierId = ActionUtils.parseInt(queryForm.getSupplier_id());
        if (supplierId!= null) 
            conditions.put(SupplierItemQueryCondition.SUPPLIER_ID_EQ, supplierId);

        Integer purchaseCategoryId =ActionUtils.parseInt(queryForm.getPurchaseCategory_id());
        if (purchaseCategoryId!=null)
            conditions.put(SupplierItemQueryCondition.PURCHASECATEGORY_ID_EQ,purchaseCategoryId);
        
        Integer purchaseSubCategoryId =ActionUtils.parseInt(queryForm.getPurchaseSubCategory_id());
        if (purchaseSubCategoryId!=null)
            conditions.put(SupplierItemQueryCondition.PURCHASESUBCATEGORY_ID_EQ,purchaseSubCategoryId);
        
        return conditions;
    }
    
    /**
     * 新增SupplierItem
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward newObject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Supplier supplier=getSupplierFromRequest(request);
        request.setAttribute("x_supplier",supplier);
        checkSupplier(supplier,request);
        if (!isBack(request)) {
            SupplierItem supplierItem=new SupplierItem();
            supplierItem.setSupplier(supplier);
            supplierItem.setCurrency(supplier.getCurrency());
            supplierItem.setUnitPrice(new BigDecimal(0d));
            
            PurchaseSubCategory p=new PurchaseSubCategory();
            Integer id=ActionUtils.parseInt(request.getParameter("category"));
            if (id!=null)
                p.setPurchaseCategory(new PurchaseCategory(id));
            id=ActionUtils.parseInt(request.getParameter("subcategory"));
            if (id!=null)
                p.setId(id);
            supplierItem.setPurchaseSubCategory(p);
            
            BeanForm supplierItemForm = (BeanForm) getForm("/insertSupplierItem", request);
            supplierItemForm.populateToForm(supplierItem);
        }
        putEnumListToRequest(request);
        putCurrencyListToRequest(request);
        putEnabledPurchaseCategoryToRequest(request,supplier.getSite());
        return mapping.findForward("page");
    }
    
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SupplierItem supplierItem= getSupplierItemFromRequest(request);
        request.setAttribute("x_supplierItem", supplierItem);
        request.setAttribute("x_supplier", supplierItem.getSupplier());
        if (!isBack(request)) {
            BeanForm supplierItemForm = (BeanForm) getForm("/updateSupplierItem", request);
            supplierItemForm.populateToForm(supplierItem);
        }
        putEnumListToRequest(request);
        putCurrencyListToRequest(request);
        putEnabledPurchaseCategoryToRequest(request,supplierItem.getSupplier().getSite());
        return mapping.findForward("page");
    }
    
	private void putEnumListToRequest(HttpServletRequest request) throws Exception {
		request.setAttribute("x_enabledDisabledList", PersistentEnum.getEnumList(EnabledDisabled.class));
	}
    
    private void putCurrencyListToRequest(HttpServletRequest request) throws Exception {
        CurrencyManager cm = ServiceLocator.getCurrencyManager(request);
        request.setAttribute("x_currencyList", cm.getAllEnabledCurrencyList());
    }

	private void putEnabledPurchaseCategoryToRequest(HttpServletRequest request,Site site) throws Exception {
		PurchaseCategoryManager pcm=ServiceLocator.getPurchaseCategoryManager(request);
		if (this.isSite(request) && site!=null) {
			request.setAttribute("x_purchaseCategoryList",pcm.getEnabledPurchaseCategorySubCategoryOfSiteIncludeGlobal(site));
		} else {
			request.setAttribute("x_purchaseCategoryList",pcm.getEnabledPurchaseCategorySubCategoryOfGlobal());
		}
	}
	
	private void checkSupplier(Supplier supplier,HttpServletRequest request ) throws Exception
	{
        if(this.isSite(request))
        {
            if(supplier.getPromoteStatus().equals(SupplierPromoteStatus.GLOBAL))
            {
                throw new ActionException("supplier.error.siteEditGlobal");
            }
            else
            {
                this.checkSite(supplier.getSite(),request);
            }
        }
        if(this.isGlobal(request)) {
            if (!supplier.getPromoteStatus().equals(SupplierPromoteStatus.GLOBAL))
                throw new ActionException("supplier.error.globalEditSite");
        }
	}
	
    
 	private SupplierItem getSupplierItemFromRequest(HttpServletRequest request)
			throws Exception {
        Integer id = ActionUtils.parseInt(request.getParameter("id"));
		SupplierItemManager supplierItemManager =ServiceLocator.getSupplierItemManager(request);
		SupplierItem supplierItem = supplierItemManager.getSupplierItem(id);
		if (supplierItem == null)
			throw new ActionException("supplierItem.notFound", id);
		return supplierItem;
	}
	
 	private Supplier getSupplierFromRequest(HttpServletRequest request) throws Exception {
    	Integer id = new Integer(request.getParameter("supplier_id"));
		SupplierManager supplierManager = ServiceLocator.getSupplierManager(request);
		Supplier supplier = supplierManager.getSupplier(id);
		if (supplier == null)
			throw new ActionException("supplier.notFound", id);
		return supplier;
	}

 	/**
     * 更新SupplierItem 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
 	 */
    public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        BeanForm supplierItemForm = (BeanForm) form;
        SupplierItem supplierItem = getSupplierItemFromRequest(request);
        
        this.checkSupplier(supplierItem.getSupplier(),request);
        
        supplierItemForm.setBeanLoader(ServiceLocator.getBeanLoader(request));
        supplierItemForm.populateToBean(supplierItem, request);
        
        SupplierItemManager sm =ServiceLocator.getSupplierItemManager(request);
        request.setAttribute("X_OBJECT", sm.updateSupplierItem(supplierItem));
        request.setAttribute("X_ROWPAGE", "supplierItem/row.jsp");
        request.setAttribute("modifyable", "true");
        return mapping.findForward("success");
    }
    
    /**
     * 插入新增的SupplierItem
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward insert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        BeanForm supplierItemForm = (BeanForm) form;
        SupplierItem supplierItem = new SupplierItem();
        supplierItemForm.setBeanLoader(ServiceLocator.getBeanLoader(request));
        supplierItemForm.populateToBean(supplierItem, request);
        
        this.checkSupplier(supplierItem.getSupplier(),request);
        
        SupplierItemManager sm = ServiceLocator.getSupplierItemManager(request);
        request.setAttribute("X_OBJECT", sm.insertSupplierItem(supplierItem));
        request.setAttribute("X_ROWPAGE", "supplierItem/row.jsp");
        request.setAttribute("modifyable", "true");
        return mapping.findForward("success");
        
    }
    

    /**
     * 删除SupplierItem
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	SupplierItem supplierItem=getSupplierItemFromRequest(request);
        this.checkSupplier(supplierItem.getSupplier(),request);
        
        SupplierItemManager sm =ServiceLocator.getSupplierItemManager(request);
		sm.removeSupplierItem(supplierItem.getId());
		
        return mapping.findForward("success");
    }
    
    

}