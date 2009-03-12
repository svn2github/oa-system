/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.web.struts.action.business.po;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

import net.sourceforge.model.admin.PurchaseSubCategory;
import net.sourceforge.model.admin.Site;
import net.sourceforge.model.business.BaseApproveRequest;
import net.sourceforge.model.business.po.PurchaseOrder;
import net.sourceforge.model.business.po.PurchaseOrderItem;
import net.sourceforge.model.business.po.query.PurchaseOrderQueryCondition;
import net.sourceforge.model.business.po.query.PurchaseOrderQueryOrder;
import net.sourceforge.model.business.pr.PurchaseRequest;
import net.sourceforge.model.business.pr.PurchaseRequestItem;
import net.sourceforge.model.metadata.ApproveStatus;
import net.sourceforge.model.metadata.PurchaseOrderStatus;
import net.sourceforge.service.admin.PurchaseCategoryManager;
import net.sourceforge.service.business.po.PurchaseOrderApproveRequestManager;
import net.sourceforge.service.business.po.PurchaseOrderManager;
import net.sourceforge.service.business.pr.PurchaseRequestManager;
import net.sourceforge.service.business.rule.ExecuteFlowEmptyResultException;
import net.sourceforge.service.business.rule.FlowManager;
import net.sourceforge.service.business.rule.NoAvailableFlowToExecuteException;
import net.sourceforge.utils.PDFReport;
import net.sourceforge.utils.SessionTempFile;
import net.sourceforge.web.struts.action.ActionUtils;
import net.sourceforge.web.struts.action.ServiceLocator;
import net.sourceforge.web.struts.form.business.po.PurchaseOrderQueryForm;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.action.BackToInputActionException;
import com.shcnc.struts.form.BeanForm;
import com.shcnc.utils.BeanHelper;
import com.shcnc.utils.BeanUtils;
import com.shcnc.utils.ExportUtil;
import com.shcnc.utils.Exportable;


/**
 * action method for domain model purchaseOrder
 * @author shilei
 * @version 1.0 (Dec 23, 2005)
 */
public class PurchaseOrderAction extends BasePurchaseOrderAction {
    
    
    /**
     * action method for withdrawing purhcaseOrder
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward withdraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PurchaseOrder po = this.getPurchaseOrderFromRequest(request);
        this.checkPurchaseOrderViewPower(po, request);
        this.checkCanWithDraw(po, request);

        PurchaseOrderManager pm = ServiceLocator.getPurchaseOrderManager(request);
        pm.withdrawPurchaseOrder(po,this.getCurrentUser(request));

        this.postGlobalMessage("purchaseOrder.withdraw.success", request.getSession());
        return this.getEditForwardFor(po);
    }

    private void checkCanWithDraw(PurchaseOrder po, HttpServletRequest request) {
        PurchaseOrderApproveRequestManager manager = ServiceLocator.getPurchaseOrderApproveRequestManager(request);
        List approveList = manager.getPurchaseOrderApproveRequestListByApproveRequestId(po.getApproveRequestId());
        if (approveList.size()>0) {
            BaseApproveRequest approveRequest = (BaseApproveRequest)approveList.get(0);
            if (!approveRequest.getStatus().equals(ApproveStatus.WAITING_FOR_APPROVE)) {
                throw new ActionException("purchaseOrder.withdraw.cannotWithdraw");
            }
        }
    }

    /**
     * action method condolidateing purchaseOrder
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward consolidate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] po_ids=request.getParameterValues("selected_po_id");
        if(po_ids==null)throw new ActionException("purchaseOrder.consolidate.selectedPoId.notSet");
        if(po_ids.length<2)throw new ActionException("purchaseOrder.consolidate.mustSelectMoreThanOne");
        
        PurchaseOrderManager pm=ServiceLocator.getPurchaseOrderManager(request);
        
        for (int i = 0; i < po_ids.length; i++) {
            PurchaseOrder po=pm.getPurchaseOrder(po_ids[i]);
            if(po==null) throw new ActionException("purchaseOrder.notFound",po_ids[i]);
            if(!po.isEditable())throw new ActionException("purchaseOrder.consolidate.notEditable",po_ids[i]);
        }
        
        pm.consolidatePurchaseOrder(po_ids,this.getCurrentUser(request));
        this.postGlobalMessage("purchaseOrder.consolidate.success",request.getSession());
        return new ActionForward("listPurchaseOrder.do",true);
    }
    
    /**
     * action method for updating eroNo of purchaseOrder
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward updateErpNo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] po_ids=request.getParameterValues("po_id");
        if(po_ids==null || po_ids.length==0)
            throw new ActionException("purchaseOrder.updateErpNo.poId.notSet");
        
        String newErpNo[]=request.getParameterValues("newErpNo");
        if(newErpNo==null || newErpNo.length==0)
            throw new ActionException("purchaseOrder.updateErpNo.newErpNo.notSet");
        
        if(po_ids.length!=newErpNo.length) throw new ActionException("purchaseOrder.updateErpNo.lenthNotEqual");
        
        PurchaseOrderManager pm=ServiceLocator.getPurchaseOrderManager(request);
        
        List poList=new ArrayList();
        for (int i = 0; i < po_ids.length; i++) {
            PurchaseOrder po=pm.getPurchaseOrder(po_ids[i]);
            if(po==null) throw new ActionException("purchaseOrder.notFound",po_ids[i]);
            poList.add(po);
        }
        
        pm.updateErpNo(poList,newErpNo);
        
        this.postGlobalMessage("purchaseOrder.updateErpNo.success",request.getSession());
        return new ActionForward("listPurchaseOrder.do",true);
    }
    /**
     * action method for viewing purchaseOrder
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PurchaseOrder po = this.getPurchaseOrderFromRequest(request);
        this.checkSite(po.getSite(),request);
        request.setAttribute("x_po", po);

        this.putPurchaseOrderDetailsForView(po, request);

        this.setEditing(false, request);

        return mapping.findForward("page");
    }

    public ActionForward viewReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PurchaseOrder po = this.getPurchaseOrderFromRequest(request);
        this.checkSite(po.getSite(),request);
        request.setAttribute("x_po", po);

        this.putPurchaseOrderDetailsForView(po, request);

        this.setEditing(false, request);
        request.setAttribute("x_report", Boolean.TRUE);

        return mapping.findForward("page");
        
    }

    public ActionForward exportPDF(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PurchaseOrder po = this.getPurchaseOrderFromRequest(request);
        this.checkSite(po.getSite(),request);
        this.checkCanExportPDF(po);
        this.putPurchaseOrderDetailsForView(po, request);
        this.exportPDF(po,request,response);
        return null;
    }
    
    private void checkCanExportPDF(PurchaseOrder po) {
        if (po.getStatus().equals(PurchaseOrderStatus.DRAFT)) 
            throw new ActionException("purchaseOrder.pdf.notAllowed");
    }
    
    private void exportPDF(Integer siteId, List data, String filename, HttpServletRequest request, HttpServletResponse response) throws DocumentException, IOException {
        MessageResources messages = getResources(request);
        Locale locale = getLocale(request);
        
        PDFReport report = PDFReport.createReport(siteId, "purchaseOrder.report.title", request, messages, locale);
        Document document = report.getDocument();
        
        PdfPTable table = PDFReport.createTable(new float[] { 6, 4, 5, 5, 4, 5, 4}, 100, 0.5f);
        table.setHeaderRows(1);
        PdfPCell defaultCell = table.getDefaultCell();
        Color defaultBackgroundColor = defaultCell.backgroundColor();

        defaultCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        defaultCell.setBackgroundColor(new Color(0x99, 0x99, 0xff));
        Font headFont = PDFReport.getFont(Font.BOLD, Color.BLACK);
        report.addCell(table, "purchaseOrder.id", headFont, true);
        report.addCell(table, "purchaseOrder.amount", headFont, true);
        report.addCell(table, "purchaseOrder.supplier.id", headFont, true);
        report.addCell(table, "purchaseOrder.category", headFont, true);
        report.addCell(table, "purchaseOrder.subCategory", headFont, true);
        report.addCell(table, "purchaseOrder.createDate", headFont, true);
        report.addCell(table, "purchaseOrder.status", headFont, true);
        
        defaultCell.setBackgroundColor(defaultBackgroundColor);
        for (Iterator itor = data.iterator(); itor.hasNext();) {
            PurchaseOrder po = (PurchaseOrder) itor.next();
            report.addCell(table, po.getId());
            report.addCell(table, po.getAmount());
            report.addCell(table, po.getSupplier().getName());
            PurchaseSubCategory subCategory = po.getSubCategory();
            if (subCategory != null) {
                report.addCell(table, subCategory.getPurchaseCategory().getDescription());
                report.addCell(table, subCategory.getDescription());
            } else {
                report.addCell(table, null);
                report.addCell(table, null);
            }
            report.addCell(table, po.getCreateDate());
            report.addCell(table, po.getStatus());
        }
        
        document.add(table);
        
        report.output(filename, response);
    }

    /**
     * action method for PurchaseOrder report
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward report(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List siteList=this.getGrantedSiteList(request);
        request.setAttribute("x_siteList",siteList);
        
        PurchaseCategoryManager pcm = ServiceLocator.getPurchaseCategoryManager(request);
        pcm.fillEnabledPurchaseCategorySubCategoryForSiteList(siteList);
        
        
        PurchaseOrderQueryForm queryForm = (PurchaseOrderQueryForm) form;

        if (StringUtils.isEmpty(queryForm.getOrder())) {
            queryForm.setOrder(PurchaseOrderQueryOrder.ID.getName());
            queryForm.setDescend(false);
            
            Site firstSite=(Site) siteList.get(0);
            queryForm.setSite_id(firstSite.getId().toString());
            
        } else if (PurchaseOrderQueryOrder.getEnum(queryForm.getOrder()) == null)
            throw new RuntimeException("order not found!");
        
        Map conditions = constructQueryMap(queryForm);

        PurchaseOrderManager fm = ServiceLocator.getPurchaseOrderManager(request);

        String exportType = queryForm.getExportType();
        if (exportType != null && exportType.length() > 0) {
            List data = fm.getPurchaseOrderList(conditions, 0, -1, PurchaseOrderQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend());
            if ("pdf".equalsIgnoreCase(exportType)) {
                exportPDF(ActionUtils.parseInt(queryForm.getSite_id()), data, "purchaseOrder", request, response);
                return null;
            }
            int index = SessionTempFile.createNewTempFile(request);
            String fileName = "purchaseOrder";
            String suffix = ExportUtil.export(exportType, data, request, new FileOutputStream(SessionTempFile.getTempFile(index, request)), new Exportable() {

                private String getMessage(String key, HttpServletRequest request) {
                    MessageResources messages = getResources(request);
                    Locale locale = getLocale(request);
                    return messages.getMessage(locale, key);
                }

                public void exportHead(List row, HttpServletRequest request) throws Exception {
                    row.add(this.getMessage("purchaseOrder.id", request));
                    row.add(this.getMessage("purchaseOrder.amount", request));
                    row.add(this.getMessage("purchaseOrder.supplier.id", request));
                    row.add(this.getMessage("purchaseOrder.category", request));
                    row.add(this.getMessage("purchaseOrder.subCategory", request));
                    row.add(this.getMessage("purchaseOrder.createDate", request));
                    row.add(this.getMessage("purchaseOrder.status", request));
                }

                public void exportRow(List row, Object data, HttpServletRequest request) throws Exception {
                    row.add(BeanUtils.getProperty(data, "id"));
                    row.add(BeanUtils.getProperty(data, "amount"));
                    row.add(BeanUtils.getProperty(data, "supplier.name"));
                    PurchaseOrder po = (PurchaseOrder) data;
                    PurchaseSubCategory subCategory = po.getSubCategory();
                    if (subCategory != null) {
                        row.add(BeanUtils.getProperty(subCategory, "purchaseCategory.description"));
                        row.add(BeanUtils.getProperty(subCategory, "description"));
                    } else {
                        row.add("");
                        row.add("");
                    }
                    row.add(BeanUtils.getProperty(data, "createDate"));    
                    if(po.getCreateDate()!=null)
                        row.add(ActionUtils.getDisplayDateFromDate(po.getCreateDate()));
                    else 
                        row.add("");

                    if (isEnglish(request)) {
                        row.add(BeanUtils.getProperty(data, "status.engShortDescription"));
                    } else {
                        row.add(BeanUtils.getProperty(data, "status.chnShortDescription"));
                    }
                }
            });
            return new ActionForward("download/" + index + "/" + URLEncoder.encode(fileName, "UTF-8") + '.' + suffix, true);
        }

        if (queryForm.isFirstInit()) {
            queryForm.init(fm.getPurchaseOrderListCount(conditions));
        } else {
            queryForm.init();
        }

        List result = fm.getPurchaseOrderList(conditions, queryForm.getPageNoAsInt(), queryForm.getPageSizeAsInt(), PurchaseOrderQueryOrder
                .getEnum(queryForm.getOrder()), queryForm.isDescend());

        request.setAttribute("X_RESULTLIST", result);

        this.putEnumListToRequest(request);
        
        request.setAttribute("x_pdf",Boolean.TRUE);
       
        return mapping.findForward("page");
    }    

    public ActionForward listConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List siteList=this.getAndCheckGrantedSiteList(request);
        request.setAttribute("x_siteList",siteList);
        PurchaseOrderManager fm =  ServiceLocator.getPurchaseOrderManager(request);
        PurchaseOrderQueryForm queryForm = (PurchaseOrderQueryForm) form;
        
        if(StringUtils.isEmpty(queryForm.getOrder()))
        {
            //init queryForm
            Site firstSite=(Site) siteList.get(0);
            queryForm.setSite_id(firstSite.getId().toString());
            queryForm.setStatus(PurchaseOrderStatus.APPROVED.getEnumCode().toString());
            
            queryForm.setOrder(PurchaseOrderQueryOrder.ID.getName());
            queryForm.setDescend(false);
        }
        else if(PurchaseOrderQueryOrder.getEnum(queryForm.getOrder())==null)
            throw new RuntimeException("order not found!");
        
        Map conditions = constructQueryMap(queryForm);
        conditions.put(PurchaseOrderQueryCondition.STATUS_IN3,
                new Object[]{PurchaseOrderStatus.APPROVED.getEnumCode(),
                PurchaseOrderStatus.RECEIVED.getEnumCode(),
                PurchaseOrderStatus.CONFIRMED.getEnumCode()});

        String exportType = queryForm.getExportType();
        if (StringUtils.isNotEmpty(exportType)) {
            List data = fm.getPurchaseOrderList(conditions, 0, -1, PurchaseOrderQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend());

            int index = SessionTempFile.createNewTempFile(request);
            String fileName = "purchaseOrderFinalConfirm";
            String suffix = ExportUtil.export(exportType, data, request, new FileOutputStream(SessionTempFile.getTempFile(index, request)), new Exportable() {

                public void exportHead(List row, HttpServletRequest request) throws Exception {
                    MessageResources messages = getResources(request);
                    row.add(messages.getMessage(getLocale(request), "purchaseOrder.id"));
                    row.add(messages.getMessage(getLocale(request), "purchaseOrder.supplier"));
                    row.add(messages.getMessage(getLocale(request), "purchaseOrder.category"));
                    row.add(messages.getMessage(getLocale(request), "purchaseOrder.subCategory"));
                    row.add(messages.getMessage(getLocale(request), "purchaseOrder.amount"));
                    row.add(messages.getMessage(getLocale(request), "purchaseOrder.createDate"));
                    row.add(messages.getMessage(getLocale(request), "purchaseOrder.search.createUser"));
                    row.add(messages.getMessage(getLocale(request), "purchaseOrder.search.confirmDate"));
                    row.add(messages.getMessage(getLocale(request), "purchaseOrder.status"));
                }

                public void exportRow(List row, Object data, HttpServletRequest request) throws Exception {
                    row.add(BeanHelper.getBeanPropertyValue(data, "id"));
                    row.add(BeanHelper.getBeanPropertyValue(data, "supplier.name"));
                    row.add(BeanHelper.getBeanPropertyValue(data, "subCategory.purchaseCategory.description"));
                    row.add(BeanHelper.getBeanPropertyValue(data, "subCategory.description"));
                    row.add(BeanHelper.getBeanPropertyValue(data, "baseCurrencyAmount"));
                    PurchaseOrder po = (PurchaseOrder) data;
                    if (po.getCreateDate() != null) {
                        row.add(ActionUtils.getDisplayDateFromDate(po.getCreateDate()));
                    } else {
                        row.add("");
                    }
                    row.add(BeanHelper.getBeanPropertyValue(data, "createUser.name"));
                    if (po.getConfirmDate() != null) {
                        row.add(ActionUtils.getDisplayDateFromDate(po.getConfirmDate()));
                    } else {
                        row.add("");
                    }
                    if (isEnglish(request)) {
                        row.add(BeanHelper.getBeanPropertyValue(data, "status.engShortDescription"));
                    } else {
                        row.add(BeanHelper.getBeanPropertyValue(data, "status.chnShortDescription"));
                    }

                }
            });
            return new ActionForward("download/" + index + "/" + URLEncoder.encode(fileName, "UTF-8") + '.' + suffix, true);
        }

        if (queryForm.isFirstInit()) {
            queryForm.init(fm.getPurchaseOrderListCount(conditions));
        } else {
            queryForm.init();
        }
        
        List result = fm.getPurchaseOrderList(conditions, queryForm
                .getPageNoAsInt(), queryForm.getPageSizeAsInt(),
                PurchaseOrderQueryOrder.getEnum(queryForm.getOrder()),
                queryForm.isDescend());

        request.setAttribute("X_RESULTLIST", result);
        
        List statusList=new ArrayList();
        statusList.add(PurchaseOrderStatus.APPROVED);
        statusList.add(PurchaseOrderStatus.CONFIRMED);
        statusList.add(PurchaseOrderStatus.RECEIVED);
        request.setAttribute("x_statusList",statusList);
        
        return mapping.findForward("page");
    }
    
    public ActionForward listCancel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List siteList=this.getAndCheckGrantedSiteList(request);
        request.setAttribute("x_siteList",siteList);
        PurchaseOrderManager fm =  ServiceLocator.getPurchaseOrderManager(request);
        PurchaseOrderQueryForm queryForm = (PurchaseOrderQueryForm) form;
        
        if (StringUtils.isEmpty(queryForm.getOrder())) {
            //init queryForm
            Site firstSite = (Site) siteList.get(0);
            queryForm.setSite_id(firstSite.getId().toString());
            queryForm.setOrder(PurchaseOrderQueryOrder.ID.getName());
            queryForm.setDescend(false);
        } else if(PurchaseOrderQueryOrder.getEnum(queryForm.getOrder())==null) {
            throw new RuntimeException("order not found!");
        }
        
        Map conditions = constructQueryMap(queryForm);
        conditions.put(PurchaseOrderQueryCondition.STATUS_EQ, PurchaseOrderStatus.CONFIRMED.getEnumCode());

        if (queryForm.isFirstInit()) {
            queryForm.init(fm.getPurchaseOrderListCount(conditions));
        } else {
            queryForm.init();
        }
        
        List result = fm.getPurchaseOrderList(conditions, queryForm
                .getPageNoAsInt(), queryForm.getPageSizeAsInt(),
                PurchaseOrderQueryOrder.getEnum(queryForm.getOrder()),
                queryForm.isDescend());

        request.setAttribute("X_RESULTLIST", result);
        
        return mapping.findForward("page");
    }
    
    /**
     * action method for search PurchaseRequest
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward listAssign(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List siteList=this.getAndCheckGrantedSiteList(request);
        request.setAttribute("x_siteList",siteList);
        PurchaseOrderManager fm =  ServiceLocator.getPurchaseOrderManager(request);
        PurchaseOrderQueryForm queryForm = (PurchaseOrderQueryForm) form;
        
        if (StringUtils.isEmpty(queryForm.getOrder())) {
            //init queryForm
            Site firstSite = (Site) siteList.get(0);
            queryForm.setSite_id(firstSite.getId().toString());
            queryForm.setOrder(PurchaseOrderQueryOrder.ID.getName());
            queryForm.setStatus(PurchaseOrderStatus.APPROVED.getEnumCode().toString());
            queryForm.setDescend(false);
        } else if(PurchaseOrderQueryOrder.getEnum(queryForm.getOrder())==null) {
            throw new RuntimeException("order not found!");
        }

        Map conditions = constructQueryMap(queryForm);
        
        if (conditions.get(PurchaseOrderQueryCondition.STATUS_EQ) == null) {
            conditions.put(PurchaseOrderQueryCondition.STATUS_IN2, 
                    new Object[] { 
                        PurchaseOrderStatus.APPROVED.getEnumCode(),
                        PurchaseOrderStatus.CONFIRMED.getEnumCode() 
                    });
        }

        if (queryForm.isFirstInit()) {
            queryForm.init(fm.getPurchaseOrderListCount(conditions));
        } else {
            queryForm.init();
        }

        List result = fm.getPurchaseOrderList(conditions, queryForm.getPageNoAsInt(), queryForm.getPageSizeAsInt(), PurchaseOrderQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend());

        request.setAttribute("X_RESULTLIST", result);
        
        List statusList=new ArrayList();
        statusList.add(PurchaseOrderStatus.APPROVED);
        statusList.add(PurchaseOrderStatus.CONFIRMED);
        request.setAttribute("x_statusList", statusList);
        
        return mapping.findForward("page");
    }

    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	    List siteList=this.getAndCheckGrantedSiteList(request);
        request.setAttribute("x_siteList",siteList);
        PurchaseOrderManager fm =  ServiceLocator.getPurchaseOrderManager(request);
        PurchaseOrderQueryForm queryForm = (PurchaseOrderQueryForm) form;
        
        Site site = null;
        Integer siteId = ActionUtils.parseInt(queryForm.getSite_id());
        if (siteId == null) {
            site = (Site) siteList.get(0);
            queryForm.setSite_id(site.getId().toString());
        } else {
            site = this.getAndCheckSite(siteId, request);
        }
        if(StringUtils.isEmpty(queryForm.getOrder())) {
            //init queryForm
            queryForm.setStatus(PurchaseOrderStatus.PENDING.getEnumCode().toString());
            
            queryForm.setOrder(PurchaseOrderQueryOrder.ID.getName());
            queryForm.setDescend(false);
        } else {
            if(PurchaseOrderQueryOrder.getEnum(queryForm.getOrder())==null) {
                throw new RuntimeException("order not found!");
            }
        }
        
        Map conditions = constructQueryMap(queryForm);
        conditions.put(PurchaseOrderQueryCondition.PURCHASER_ID_EQ,this.getCurrentUser(request).getId());

		if (queryForm.isFirstInit()) {
			queryForm.init(fm.getPurchaseOrderListCount(conditions));
		} else {
			queryForm.init();
		}
		
		List result = fm.getPurchaseOrderList(conditions, queryForm
				.getPageNoAsInt(), queryForm.getPageSizeAsInt(),
				PurchaseOrderQueryOrder.getEnum(queryForm.getOrder()),
				queryForm.isDescend());

        request.setAttribute("X_RESULTLIST", result);
        
        request.setAttribute("x_statusList",PurchaseOrderStatus.getEnumList(PurchaseOrderStatus.class));
        putEnabledPurchaseCategoryToRequest(request, site);
        return mapping.findForward("page");
    }
    
    private void putEnabledPurchaseCategoryToRequest(HttpServletRequest request,Site site) throws Exception {
        PurchaseCategoryManager pcm=ServiceLocator.getPurchaseCategoryManager(request);
        request.setAttribute("x_purchaseCategoryList",pcm.getEnabledPurchaseCategorySubCategoryOfSiteIncludeGlobal(site));
    }
    
    private Map constructQueryMap(PurchaseOrderQueryForm queryForm) {
        Map conditions = new HashMap();
         /*id*/
		String id = 
			queryForm.getId();
		if(id != null && id.trim().length()!=0)
		{
			conditions.put(PurchaseOrderQueryCondition.ID_LIKE,
				 id);
		}		

		/*keyPropertyList*/

		/*kmtoIdList*/

		/*mtoIdList*/
		Integer site_id =
			ActionUtils.parseInt(queryForm.getSite_id());
		if (site_id != null) 
		{
			conditions.put(PurchaseOrderQueryCondition.SITE_ID_EQ,
				 site_id);
		}
		Integer supplier_id =
			ActionUtils.parseInt(queryForm.getSupplier_id());
		if (supplier_id != null) 
		{
			conditions.put(PurchaseOrderQueryCondition.SUPPLIER_ID_EQ,
				 supplier_id);
		}
		Integer subCategory_id =
			ActionUtils.parseInt(queryForm.getSubCategory_id());
		if (subCategory_id != null) 
		{
			conditions.put(PurchaseOrderQueryCondition.SUBCATEGORY_ID_EQ,
				 subCategory_id);
		}
		String currency_code = 
			queryForm.getCurrency_code();
		if(currency_code != null && currency_code.trim().length()!=0)
		{
			conditions.put(PurchaseOrderQueryCondition.CURRENCY_CODE_EQ,
				 currency_code);
		}
		String baseCurrency_code = 
			queryForm.getBaseCurrency_code();
		if(baseCurrency_code != null && baseCurrency_code.trim().length()!=0)
		{
			conditions.put(PurchaseOrderQueryCondition.BASECURRENCY_CODE_EQ,
				 baseCurrency_code);
		}
		Integer createUser_id =
			ActionUtils.parseInt(queryForm.getCreateUser_id());
		if (createUser_id != null) 
		{
			conditions.put(PurchaseOrderQueryCondition.CREATEUSER_ID_EQ,
				 createUser_id);
		}
		Integer purchaser_id =
			ActionUtils.parseInt(queryForm.getPurchaser_id());
		if (purchaser_id != null) 
		{
			conditions.put(PurchaseOrderQueryCondition.PURCHASER_ID_EQ,
				 purchaser_id);
		}


  
		/*property*/
		String erpNo = 
			queryForm.getErpNo();
		if(erpNo != null && erpNo.trim().length()!=0)
		{
			conditions.put(PurchaseOrderQueryCondition.ERPNO_LIKE,
				 erpNo);
		}
		String title = 
			queryForm.getTitle();
		if(title != null && title.trim().length()!=0)
		{
			conditions.put(PurchaseOrderQueryCondition.TITLE_LIKE,
				 title);
		}
		String description = 
			queryForm.getDescription();
		if(description != null && description.trim().length()!=0)
		{
			conditions.put(PurchaseOrderQueryCondition.DESCRIPTION_LIKE,
				 description);
		}
		String amount = 
			queryForm.getAmount();
		if(amount != null && amount.trim().length()!=0)
		{
			conditions.put(PurchaseOrderQueryCondition.AMOUNT_EQ,
				 amount);
		}		
		String exchangeRate = 
			queryForm.getExchangeRate();
		if(exchangeRate != null && exchangeRate.trim().length()!=0)
		{
			conditions.put(PurchaseOrderQueryCondition.EXCHANGERATE_EQ,
				 exchangeRate);
		}		
		String status = 
			queryForm.getStatus();
		if(status != null && status.trim().length()!=0)
		{
			conditions.put(PurchaseOrderQueryCondition.STATUS_EQ,
				 status);
		}		
		String createDate = 
			queryForm.getCreateDate();
		if(createDate != null && createDate.trim().length()!=0)
		{
			conditions.put(PurchaseOrderQueryCondition.CREATEDATE_EQ,
				 createDate);
		}		
		String exportStatus = 
			queryForm.getExportStatus();
		if(exportStatus != null && exportStatus.trim().length()!=0)
		{
			conditions.put(PurchaseOrderQueryCondition.EXPORTSTATUS_EQ,
				 exportStatus);
		}		
        
        if(!StringUtils.isEmpty(queryForm.getSupplier_name()))
        {
            conditions.put(PurchaseOrderQueryCondition.SUPPLIER_NAME_LIKE,queryForm.getSupplier_name());
        }

        Integer category_id = ActionUtils.parseInt(queryForm.getCategory_id());
        if (category_id != null) {
            conditions.put(PurchaseOrderQueryCondition.CATEGORY_ID_EQ, category_id);
        }
        
        if(StringUtils.isNotEmpty(queryForm.getAmount1()))
        {
            BigDecimal amount1=new BigDecimal(queryForm.getAmount1());
            conditions.put(PurchaseOrderQueryCondition.AMOUNT_GE,amount1);
        }
        
        if(StringUtils.isNotEmpty(queryForm.getAmount2()))
        {
            BigDecimal amount2=new BigDecimal(queryForm.getAmount2());
            conditions.put(PurchaseOrderQueryCondition.AMOUNT_LE,amount2);
        }
        
        if(StringUtils.isNotEmpty(queryForm.getCreateDate1()))
        {
            Date d = ActionUtils.getDateFromDisplayDate(queryForm.getCreateDate1());
            conditions.put(PurchaseOrderQueryCondition.CREATEDATE_GE, d);
        }
        
        if(StringUtils.isNotEmpty(queryForm.getCreateDate2()))
        {
            Date d = ActionUtils.getDateFromDisplayDate(queryForm.getCreateDate2());
            conditions.put(PurchaseOrderQueryCondition.CREATEDATE_LT, getNextDate(d));
        }
        
        if(StringUtils.isNotEmpty(queryForm.getConfirmDate1()))
        {
            Date d = ActionUtils.getDateFromDisplayDate(queryForm.getConfirmDate1());
            conditions.put(PurchaseOrderQueryCondition.CONFIRMDATE_GE, d);
        }
        
        if(StringUtils.isNotEmpty(queryForm.getConfirmDate2()))
        {
            Date d = ActionUtils.getDateFromDisplayDate(queryForm.getConfirmDate2());
            conditions.put(PurchaseOrderQueryCondition.CONFIRMDATE_LT, getNextDate(d));
        }
        
        return conditions;
    }

    private Date getNextDate(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.DATE, 1);
        return c.getTime();
    }
    
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PurchaseOrder po = this.getPurchaseOrderFromRequest(request);
        this.checkPurchaseOrderEditPower(po, request);
        request.setAttribute("x_po", po);

        if (!this.isBack(request)) {
            BeanForm beanForm = (BeanForm) getForm("/updatePurchaseOrder", request);
            beanForm.populate(po, BeanForm.TO_FORM);
        }

        this.putPurchaseOrderDetailsToRequest(po, request);
        
        this.putEnumListToRequest(request);
        this.setEditing(true, request);

        return mapping.findForward("page");
    }
    
    public ActionForward cancel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PurchaseOrder po = this.getPurchaseOrderFromRequest(request);
        this.checkPurchaseOrderEditPower(po, request);

        PurchaseOrderManager pm=ServiceLocator.getPurchaseOrderManager(request);
        pm.cancelPurchaseOrder(po.getId());
        
        this.postGlobalMessage("purchaseOrder.cancel.success",po.getId(),request.getSession());

        return this.getListForwardFor(po);
    }



    /**
     * action method for viewing approver when updating purchaseOrder
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward update_viewApprover(
            ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return update(mapping, form, request, response, true);
    }
    
    /**
     * action method for updating purchaseOrder
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return update(mapping, form, request, response, false);
    }

    private ActionForward update(
            ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean viewApprover)
            throws Exception {
        PurchaseOrder po = this.getPurchaseOrderFromRequest(request);
        this.checkPurchaseOrderEditPower(po, request);

        BeanForm beanForm = (BeanForm) form;
        beanForm.populateToBean(po, request);

        if (viewApprover) {
            try {
                request.setAttribute("X_APPROVELIST", executeFlow(po, request));
            } catch (ActionException e) {
                ActionMessage message = new ActionMessage(e.getKey(), e.getValues());
                ActionMessages messages = new ActionMessages();
                messages.add(null, message);
                saveErrors(request, messages);
            }
            return mapping.findForward("page");
        } else {
            List itemList = this.getPurchaseOrderItemListFromSession(request);
            /*
             * 对每个po item所属的pr执行Purchasing Price Control Flow
             */
            Set purchaseRequests = new HashSet();
            for (Iterator itor = itemList.iterator(); itor.hasNext();) {
                PurchaseOrderItem poi = (PurchaseOrderItem) itor.next();
                purchaseRequests.add(poi.getPurchaseRequestItem().getPurchaseRequest());
            }
            PurchaseRequestManager prm = ServiceLocator.getPurchaseRequestManager(request);
            FlowManager fm = ServiceLocator.getFlowManager(request);
            StringBuffer failedPurchaseRequestIds = new StringBuffer();
            
            for (Iterator itor = purchaseRequests.iterator(); itor.hasNext();) {
                PurchaseRequest pr = (PurchaseRequest) itor.next();
                List poItems = prm.getPurchaseOrderItemList(pr);
                BigDecimal poAmount = new BigDecimal(0d);
                for (Iterator itor2 = poItems.iterator(); itor2.hasNext();) {
                    PurchaseOrderItem poi = (PurchaseOrderItem) itor2.next();
                    PurchaseRequestItem pri = poi.getPurchaseRequestItem();
                    if (poi.getExchangeRate().equals(pri.getExchangeRate())) {
                        //如果POItem使用和PRItem相同的货币，采用保存在PRItem中的汇率值计算采购本币金额，避免汇率影响使采购价格控制流程无法通过
                        poAmount.add(poi.getAmount().multiply(pri.getExchangeRateValue()).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                    } else {
                        //如果POItem使用和PRItem不同的货币，采用保存在PO中的汇率值或者最新的汇率(如果此POItem还未建立PO)计算采购本币金额
                        poAmount = poAmount.add(poi.getBaseCurrencyAmount());
                    }
                }
                pr.setPurchaseAmount(poAmount);
                try {
                    if (!fm.executeControlFlow(pr)) {
                        if (failedPurchaseRequestIds.length() > 0) {
                            failedPurchaseRequestIds.append(", ");
                        }
                        failedPurchaseRequestIds.append(pr.getId());
                    }
                } catch (NoAvailableFlowToExecuteException e) {
                    throw new BackToInputActionException("flow.execute.notFlowFound");
                }
            }
            if (failedPurchaseRequestIds.length() > 0) {
                this.postGlobalMessage("purchaseOrder.purchaseRequest.notPass", failedPurchaseRequestIds.toString(), request.getSession());
                throw new BackToInputActionException("purchaseOrder.submit.fail");
            }
            
            List poarList = null;
            boolean draft = isDraft(request);
            if (!draft) {
                poarList = executeFlow(po, request);
            }

            List attachmentList = this.getPurchaseOrderAttachmentListFromRequest(request);
            
            List paymentScheduleDetailList=this.getPaymentScheduleDetailListFromSession(request);

            PurchaseOrderManager pm = ServiceLocator.getPurchaseOrderManager(request);
            PurchaseOrder oldPO= this.getPurchaseOrderFromRequest(request);
            List oldItemList=pm.getPurchaseOrderItemList(po);
            pm.updatePurchaseOrder(oldPO,po,oldItemList, itemList, attachmentList,paymentScheduleDetailList, poarList, isDraft(request),this.getCurrentUser(request));

            this.clearPurchaseOrderItemListFromSession(request);
            this.clearPaymentScheduleDetailListFromSession(request);
            if (this.isDraft(request)) {
                this.postGlobalMessage("purchaseOrder.saveDraft.success", request.getSession());
                return getEditForwardFor(po);
            } else {
                this.postGlobalMessage("purchaseOrder.submit.success", request.getSession());
                return getViewForwardFor(po);
            }
        }
    }

    private ActionForward getViewForwardFor(PurchaseOrder po) {
        return new ActionForward("viewPurchaseOrder.do?id="+po.getId(),true);
    }

    private List executeFlow(PurchaseOrder po, HttpServletRequest request) {
        try {
            List eList = ServiceLocator.getFlowManager(request).executeApproveFlow(po);
            return eList;
        } catch (ExecuteFlowEmptyResultException e) {
            throw new BackToInputActionException("flow.execute.notApproverFound");
        } catch (NoAvailableFlowToExecuteException e) {
            throw new BackToInputActionException("flow.execute.notFlowFound");
        }
    }

    private boolean isDraft(HttpServletRequest request) {
        return "true".equals(request.getParameter("draft"));
    }

    
    private ActionForward getEditForwardFor(PurchaseOrder po)
	{
		return new ActionForward("editPurchaseOrder.do?id="+po.getId(),true);
	}
    
    private ActionForward getListForwardFor(PurchaseOrder po)
    {
        return new ActionForward("listPurchaseOrder.do",true);
    }


}