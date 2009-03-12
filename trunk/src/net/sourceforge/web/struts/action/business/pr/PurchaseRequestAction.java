/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.web.struts.action.business.pr;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
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
import org.springframework.orm.hibernate.HibernateOptimisticLockingFailureException;

import net.sourceforge.model.admin.Department;
import net.sourceforge.model.admin.PurchaseSubCategory;
import net.sourceforge.model.admin.Site;
import net.sourceforge.model.admin.User;
import net.sourceforge.model.business.BaseApproveRequest;
import net.sourceforge.model.business.pr.Capex;
import net.sourceforge.model.business.pr.PurchaseRequest;
import net.sourceforge.model.business.pr.PurchaseRequestItem;
import net.sourceforge.model.business.pr.YearlyBudget;
import net.sourceforge.model.business.pr.query.PurchaseRequestQueryCondition;
import net.sourceforge.model.business.pr.query.PurchaseRequestQueryOrder;
import net.sourceforge.model.metadata.ApproveStatus;
import net.sourceforge.model.metadata.BudgetType;
import net.sourceforge.model.metadata.PurchaseRequestStatus;
import net.sourceforge.model.metadata.YesNo;
import net.sourceforge.service.admin.DepartmentManager;
import net.sourceforge.service.admin.PurchaseCategoryManager;
import net.sourceforge.service.admin.PurchaseSubCategoryManager;
import net.sourceforge.service.admin.UserManager;
import net.sourceforge.service.business.pr.CapexManager;
import net.sourceforge.service.business.pr.PurchaseRequestApproveRequestManager;
import net.sourceforge.service.business.pr.PurchaseRequestManager;
import net.sourceforge.service.business.pr.YearlyBudgetManager;
import net.sourceforge.service.business.rule.ExecuteFlowEmptyResultException;
import net.sourceforge.service.business.rule.NoAvailableFlowToExecuteException;
import net.sourceforge.utils.PDFReport;
import net.sourceforge.utils.SessionTempFile;
import net.sourceforge.web.struts.action.ActionUtils;
import net.sourceforge.web.struts.action.ServiceLocator;
import net.sourceforge.web.struts.form.business.pr.PurchaseRequestQueryForm;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.action.BackToInputActionException;
import com.shcnc.struts.form.BeanForm;
import com.shcnc.struts.form.ComboBox;
import com.shcnc.utils.BeanUtils;
import com.shcnc.utils.ExportUtil;
import com.shcnc.utils.Exportable;

/**
 * struts actions class for domain model PurchaseRequest
 * 
 * @author shilei
 * @version 1.0 (Dec 7, 2005)
 */
public class PurchaseRequestAction extends BasePurchaseRequestAction {
    
    /**
     * action method for deleting my request purchaseRequest
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward deleteSelf(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return this.delete(mapping,form,request,response,true);
    }
    
    /**
     * action method for deleting my request purchaseRequest
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward deleteOther(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return this.delete(mapping,form,request,response,false);
    }
    
    /**
     * action method for deleting purchaseRequest
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response,boolean isSelf) throws Exception {
        PurchaseRequest pr = this.getPurchaseRequestFromRequest(request);
        this.checkPurchaseRequestEditPower(pr, request);

        PurchaseRequestManager pm = ServiceLocator.getPurchaseRequestManager(request);
        if(pm.purchaseRequestHasPOItem(pr)) throw new ActionException("purchaseRequest.delete.inUse",pr.getId());
        pm.deletePurchaseRequest(pr,this.getCurrentUser(request));

        this.postGlobalMessage("purchaseRequest.delete.success", pr.getId(), request.getSession());
        return this.getListForwardFor(pr, request,isSelf);
    }
    
    public ActionForward withdrawSelf(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return this.withdraw(mapping,form,request,response,true);
    }
    
    public ActionForward withdrawOther(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return this.withdraw(mapping,form,request,response,false);
    }
    
    
    
    /**
     * action method for withdrawing purhcaseRequest
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward withdraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response,boolean isSelf) throws Exception {
        PurchaseRequest pr = this.getPurchaseRequestFromRequest(request);
        this.checkPurchaseRequestViewPower(pr, request);
        this.checkCanWithDraw(pr, request);

        PurchaseRequestManager pm = ServiceLocator.getPurchaseRequestManager(request);
        pm.withdrawPurchaseRequest(pr,this.getCurrentUser(request));

        this.postGlobalMessage("purchaseRequest.withdraw.success", request.getSession());
        return this.getEditForwardFor(pr, request,isSelf);
    }

    private void checkCanWithDraw(PurchaseRequest pr, HttpServletRequest request) {
        PurchaseRequestApproveRequestManager manager = ServiceLocator.getPurchaseRequestApproveRequestManager(request);
        List approveList = manager.getPurchaseRequestApproveRequestListByApproveRequestId(pr.getApproveRequestId());
        if (approveList.size()>0) {
            BaseApproveRequest approveRequest = (BaseApproveRequest)approveList.get(0);
            if (!approveRequest.getStatus().equals(ApproveStatus.WAITING_FOR_APPROVE)) {
                throw new ActionException("purchaseRequest.withdraw.cannotWithdraw");
            }
        }
    }

    private PurchaseSubCategory getPurchaseSubCategoryFromRequest(HttpServletRequest request) {
        Integer id = ActionUtils.parseInt(request.getParameter("purchaseSubCategory_id"));
        if (id == null)
            throw new ActionException("purchaseRequest.purchaseSubCategory.idNotSet");
        PurchaseSubCategoryManager pscm = ServiceLocator.getPurchaseSubCategoryManager(request);
        PurchaseSubCategory psc = pscm.getPurchaseSubCategory(id);
        if (psc == null)
            throw new ActionException("purchaseRequest.purchaseSubCategory.notFound", id);
        return psc;
    }

    private Department getDepartmentFromRequest(HttpServletRequest request) {
        Integer id = ActionUtils.parseInt(request.getParameter("department_id"));
        if (id == null)
            throw new ActionException("purchaseRequest.department.idNotSet");
        DepartmentManager dm = ServiceLocator.getDepartmentManager(request);
        Department d = dm.getDepartment(id);
        if (d == null)
            throw new ActionException("purchaseRequest.department.notFound", id);
        return d;
    }
    
    /**
     * action method for selecting capex on new page
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward selectCapex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String capex_id = request.getParameter(PARAMETER_CAPEXID_ID);
        
        if (capex_id == null) {
            PurchaseSubCategory psc = this.getPurchaseSubCategoryFromRequest(request);
            Department department = this.getDepartmentFromRequest(request);

            CapexManager cm = ServiceLocator.getCapexManager(request);
            List result = cm.getRequestApprovedCapexList(psc, department);
            request.setAttribute("X_RESULTLIST", result);

            return mapping.findForward("page");
        } else {
            Capex cp = this.getCapexFromRequest(request);
            request.setAttribute("x_capex", cp);
            this.putCapexDepartmentIdsToRequest(cp, request);
            request.setAttribute("X_CONTENTPAGE", "purchaseRequest/capexContent.jsp");
            this.putCanViewCapexAmountToRequest(cp,request);
            return mapping.findForward("selectOK");
        }
    }
    
    /**
     * action method for selecting yearlyBudget on new page
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward selectYearlyBudget(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String str_yearlyBudget_id = request.getParameter(PARAMETER_YEAYLYBUDGET_ID);
        if (str_yearlyBudget_id == null) {
            PurchaseSubCategory psc = this.getPurchaseSubCategoryFromRequest(request);
            Department department = this.getDepartmentFromRequest(request);

            YearlyBudgetManager ym = ServiceLocator.getYearlyBudgetManager(request);

            List departmentList = new ArrayList();
            departmentList.add(department);
            User currentUser = getCurrentUser(request);
            List result = ym.getSuitableYearlyBudget
            (department.getSite(), psc.getPurchaseCategory(), psc, departmentList,BudgetType.PR, currentUser);
            request.setAttribute("X_RESULTLIST", result);
            
            return mapping.findForward("page");
        } else {
            YearlyBudget yb = this.getYearlyBudgetFromRequest(request);
            this.putYearlyBudgetDepartmentIdsToRequest(yb, request);
            request.setAttribute("x_yearlyBudget", yb);
            this.putCanViewYearlyBudgetAmount(yb,request);
            request.setAttribute("X_CONTENTPAGE", "purchaseRequest/yearlyBudgetContent.jsp");
            return mapping.findForward("selectOK");
        }
    }
    private void putYearlyBudgetDepartmentIdsToRequest(YearlyBudget yb, HttpServletRequest request) {
        StringBuffer sb = new StringBuffer();
        YearlyBudgetManager ybm = ServiceLocator.getYearlyBudgetManager(request);
        Department[] departments = ybm.getBudgetDepartments(yb);
        for (int i = 0; i < departments.length; i++) {
            sb.append(',');
            sb.append(departments[i].getId());
        }
        sb.append(',');
        request.setAttribute("x_yearlyBudgetDepartmentIds", sb.toString());
    }

    private void putCapexDepartmentIdsToRequest(Capex cp, HttpServletRequest request) {
        StringBuffer sb = new StringBuffer();
        CapexManager cm = ServiceLocator.getCapexManager(request);
        Department[] departments = cm.getDepartments(cp);
        for (int i = 0; i < departments.length; i++) {
            sb.append(',');
            sb.append(departments[i].getId());
        }
        sb.append(',');
        request.setAttribute("x_capexDepartmentIds", sb.toString());
    }

    private void listAssignPageProcess(PurchaseRequestQueryForm queryForm, HttpServletRequest request) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        UserManager um = ServiceLocator.getUserManager(request);
        List siteList = um.getSiteOfGrantedSiteDeparmentList(this.getCurrentUser(request), this.getFunction(request));
        if (siteList.isEmpty()) {
            throw new ActionException("errors.noDepartmentPermission");
        }
        ComboBox comboSite = new ComboBox("id", "name");
        comboSite.setValue(queryForm.getSite_id());
        comboSite.setList(siteList);
        Site site = (Site) comboSite.getSelectedItem();
        queryForm.setSite_id(site.getId().toString());

        List departmentList = um.getGrantedDepartmentListOfSite(this.getCurrentUser(request), site, this.getFunction(request));
        
        if (queryForm.getDepartment_id() == null) {
            for (Iterator itor = departmentList.iterator(); itor.hasNext();) {
                Department d = (Department) itor.next();
                if (d.isGranted()) {
                    queryForm.setDepartment_id(d.getId().toString());
                    break;
                }
            }
        }

        ComboBox comboDepartment = new ComboBox("id", "name");
        comboDepartment.setValue(queryForm.getDepartment_id());
        comboDepartment.setList(departmentList);
        Department d = (Department)comboDepartment.getSelectedItem();
        if (!d.isGranted()) {
            throw new ActionException("errors.noDepartmentPermission");
        }
        queryForm.setDepartment_id(d.getId().toString());
        
        request.setAttribute("x_siteList", siteList);
        request.setAttribute("x_departmentList", departmentList);

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
        PurchaseRequestQueryForm queryForm = (PurchaseRequestQueryForm) form;
        listAssignPageProcess(queryForm, request);

        if (StringUtils.isEmpty(queryForm.getOrder())) {
            queryForm.setOrder(PurchaseRequestQueryOrder.ID.getName());
            queryForm.setStatus(PurchaseRequestStatus.APPROVED.getEnumCode().toString());
            queryForm.setDescend(false);
        }

        Map conditions = constructQueryMap(queryForm, request);
        
        if (conditions.get(PurchaseRequestQueryCondition.STATUS_EQ) == null) {
            conditions.put(PurchaseRequestQueryCondition.STATUS_IN_2, new Object[] { PurchaseRequestStatus.APPROVED.getEnumCode(),
                    PurchaseRequestStatus.BOOKED.getEnumCode() });
        }

        PurchaseRequestManager fm = ServiceLocator.getPurchaseRequestManager(request);

        if (queryForm.isFirstInit()) {
            queryForm.init(fm.getPurchaseRequestListCount(conditions));
        } else {
            queryForm.init();
        }

        List result = fm.getPurchaseRequestList(conditions, queryForm.getPageNoAsInt(), queryForm.getPageSizeAsInt(), PurchaseRequestQueryOrder
                .getEnum(queryForm.getOrder()), queryForm.isDescend());

        request.setAttribute("X_RESULTLIST", result);
        
        List statusList=new ArrayList();
        statusList.add(PurchaseRequestStatus.APPROVED);
        statusList.add(PurchaseRequestStatus.BOOKED);
        request.setAttribute("x_statusList", statusList);
        
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
    public ActionForward listPurchase(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List siteList=this.getAndCheckGrantedSiteDeparmentList(request);
        request.setAttribute("x_siteList",siteList);
        
        PurchaseRequestQueryForm queryForm = (PurchaseRequestQueryForm) form;
        
        if (StringUtils.isEmpty(queryForm.getOrder())) {
            queryForm.setSite_id(((Site)siteList.get(0)).getId().toString());
            queryForm.setOrder(PurchaseRequestQueryOrder.ID.getName());
            queryForm.setStatus(PurchaseRequestStatus.APPROVED.getEnumCode().toString());
            queryForm.setDescend(false);
        } else if (PurchaseRequestQueryOrder.getEnum(queryForm.getOrder()) == null)
            throw new RuntimeException("order not found!");

        Map conditions = constructQueryMap(queryForm, request);

        User currentUser = this.getCurrentUser(request);
        conditions.put(PurchaseRequestQueryCondition.CAN_PURCHASED_BY, new Object[] { currentUser, currentUser });

        conditions.put(PurchaseRequestQueryCondition.STATUS_IN_2, new Object[] { PurchaseRequestStatus.APPROVED.getEnumCode(),
                PurchaseRequestStatus.BOOKED.getEnumCode() });

        PurchaseRequestManager fm = ServiceLocator.getPurchaseRequestManager(request);

        if (queryForm.isFirstInit()) {
            queryForm.init(fm.getPurchaseRequestListCount(conditions));
        } else {
            queryForm.init();
        }

        List result = fm.getPurchaseRequestList(conditions, queryForm.getPageNoAsInt(), queryForm.getPageSizeAsInt(), PurchaseRequestQueryOrder
                .getEnum(queryForm.getOrder()), queryForm.isDescend());

        request.setAttribute("X_RESULTLIST", result);
        
        List statusList=new ArrayList();
        statusList.add(PurchaseRequestStatus.APPROVED);
        statusList.add(PurchaseRequestStatus.BOOKED);
        request.setAttribute("x_statusList", statusList);
        
        return mapping.findForward("page");
    }
    
    private void exportPDF(Integer siteId, List data, String filename, HttpServletRequest request, HttpServletResponse response) throws DocumentException, IOException {
        MessageResources messages = getResources(request);
        Locale locale = getLocale(request);
        
        PDFReport report = PDFReport.createReport(siteId, "purchaseRequest.report.title", request, messages, locale);
        Document document = report.getDocument();
        
        PdfPTable table = PDFReport.createTable(new float[] { 6, 4, 5, 5, 4, 5, 4 ,4}, 100, 0.5f);
        table.setHeaderRows(1);
        PdfPCell defaultCell = table.getDefaultCell();
        Color defaultBackgroundColor = defaultCell.backgroundColor();

        defaultCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        defaultCell.setBackgroundColor(new Color(0x99, 0x99, 0xff));
        Font headFont = PDFReport.getFont(Font.BOLD, Color.BLACK);
        report.addCell(table, "purchaseRequest.id", headFont, true);
        report.addCell(table, "purchaseRequest.requestor.id", headFont, true);
        report.addCell(table, "purchaseRequest.purchaseCategory", headFont, true);
        report.addCell(table, "purchaseRequest.purchaseSubCategory", headFont, true);
        report.addCell(table, "purchaseRequest.amount", headFont, true);
        report.addCell(table, "purchaseRequest.requestDate", headFont, true);
        report.addCell(table, "purchaseRequest.status", headFont, true);
        report.addCell(table, "purchaseRequest.pdf.approvalDuration", headFont, true);
        
        defaultCell.setBackgroundColor(defaultBackgroundColor);
        for (Iterator itor = data.iterator(); itor.hasNext();) {
            PurchaseRequest pr = (PurchaseRequest) itor.next();
            report.addCell(table, pr.getId());
            report.addCell(table, pr.getRequestor().getName());
            report.addCell(table, pr.getPurchaseSubCategory().getPurchaseCategory().getDescription());
            report.addCell(table, pr.getPurchaseSubCategory().getDescription());
            report.addCell(table, pr.getAmount());
            report.addCell(table, pr.getRequestDate());
            report.addCell(table, pr.getStatus());
            report.addCell(table, pr.getApproveDurationDay());
        }
        
        document.add(table);
        
        report.output(filename, response);
    }

    
    /**
     * action method for PurchaseRequest report
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward report(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List siteList=this.getGrantedSiteDeparmentList(request);
        request.setAttribute("x_siteList",siteList);
        
        PurchaseCategoryManager pcm = ServiceLocator.getPurchaseCategoryManager(request);
        pcm.fillEnabledPurchaseCategorySubCategoryForSiteList(siteList);
        
        
        PurchaseRequestQueryForm queryForm = (PurchaseRequestQueryForm) form;

        if (StringUtils.isEmpty(queryForm.getOrder())) {
            queryForm.setOrder(PurchaseRequestQueryOrder.ID.getName());
            queryForm.setDescend(false);
            
            Site firstSite=(Site) siteList.get(0);
            queryForm.setSite_id(firstSite.getId().toString());
            
            //Department firstDepartment=(Department) firstSite.getDepartments().get(0);
            //queryForm.setDepartment_id(firstDepartment.getId().toString());

        } else if (PurchaseRequestQueryOrder.getEnum(queryForm.getOrder()) == null)
            throw new RuntimeException("order not found!");
        
        Map conditions = constructQueryMap(queryForm, request);

        PurchaseRequestManager fm = ServiceLocator.getPurchaseRequestManager(request);

        String exportType = queryForm.getExportType();
        if (exportType != null && exportType.length() > 0) {
            List data = fm.getPurchaseRequestList(conditions, 0, -1, PurchaseRequestQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend());
            if ("pdf".equalsIgnoreCase(exportType)) {
                exportPDF(ActionUtils.parseInt(queryForm.getSite_id()), data, "purchaseRequest", request, response);
                return null;
            }
            int index = SessionTempFile.createNewTempFile(request);
            String fileName = "purchaseRequest";
            String suffix = ExportUtil.export(exportType, data, request, new FileOutputStream(SessionTempFile.getTempFile(index, request)), new Exportable() {

                private String getMessage(String key, HttpServletRequest request) {
                    MessageResources messages = getResources(request);
                    Locale locale = getLocale(request);
                    return messages.getMessage(locale, key);
                }

                public void exportHead(List row, HttpServletRequest request) throws Exception {
                    row.add(this.getMessage("purchaseRequest.id", request));
                    row.add(this.getMessage("purchaseRequest.requestor.id", request));
                    row.add(this.getMessage("purchaseRequest.purchaseCategory", request));
                    row.add(this.getMessage("purchaseRequest.purchaseSubCategory", request));
                    row.add(this.getMessage("purchaseRequest.amount", request));
                    row.add(this.getMessage("purchaseRequest.requestDate", request));
                    row.add(this.getMessage("purchaseRequest.status", request));
                    row.add(this.getMessage("purchaseRequest.approvalDuration", request));
                }

                public void exportRow(List row, Object data, HttpServletRequest request) throws Exception {
                    row.add(BeanUtils.getProperty(data, "id"));
                    row.add(BeanUtils.getProperty(data, "requestor.name"));
                    row.add(BeanUtils.getProperty(data, "purchaseSubCategory.purchaseCategory.description"));
                    row.add(BeanUtils.getProperty(data, "purchaseSubCategory.description"));
                    row.add(BeanUtils.getProperty(data, "amount"));    
                    PurchaseRequest pr = (PurchaseRequest) data;
                    if(pr.getRequestDate()!=null)
                        row.add(ActionUtils.getDisplayDateFromDate(pr.getRequestDate()));
                    else 
                        row.add("");

                    if (isEnglish(request)) {
                        row.add(BeanUtils.getProperty(data, "status.engShortDescription"));
                    } else {
                        row.add(BeanUtils.getProperty(data, "status.chnShortDescription"));
                    }
                    row.add(BeanUtils.getProperty(data, "approveDurationDay"));
                }
            });
            return new ActionForward("download/" + index + "/" + URLEncoder.encode(fileName, "UTF-8") + '.' + suffix, true);
        }

        if (queryForm.isFirstInit()) {
            queryForm.init(fm.getPurchaseRequestListCount(conditions));
        } else {
            queryForm.init();
        }

        List result = fm.getPurchaseRequestList(conditions, queryForm.getPageNoAsInt(), queryForm.getPageSizeAsInt(), PurchaseRequestQueryOrder
                .getEnum(queryForm.getOrder()), queryForm.isDescend());

        request.setAttribute("X_RESULTLIST", result);

        this.putEnumListToRequest(request);
        
        request.setAttribute("x_pdf",Boolean.TRUE);
       
        return mapping.findForward("page");
    }
    
    public ActionForward listSelf(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return list(mapping,form,request,response,true);
    }
    
    public ActionForward listOther(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return list(mapping,form,request,response,false);
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
    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response,boolean isSelf) throws Exception {
        PurchaseRequestQueryForm queryForm = (PurchaseRequestQueryForm) form;

        if (StringUtils.isEmpty(queryForm.getOrder())) {
            queryForm.setOrder(PurchaseRequestQueryOrder.ID.getName());
            queryForm.setDescend(false);
        } else if (PurchaseRequestQueryOrder.getEnum(queryForm.getOrder()) == null)
            throw new RuntimeException("order not found!");
        
        Map conditions = constructQueryMap(queryForm, request);
        
        if (isSelf) {
            conditions.put(PurchaseRequestQueryCondition.REQUESTOR_ID_EQ,this.getCurrentUser(request).getId());
            conditions.put(PurchaseRequestQueryCondition.ISDELEGATE_EQ, YesNo.NO.getEnumCode());
        } else {
            conditions.put(PurchaseRequestQueryCondition.CREATOR_ID_EQ,this.getCurrentUser(request).getId());            
            conditions.put(PurchaseRequestQueryCondition.ISDELEGATE_EQ, YesNo.YES.getEnumCode());
        }

        PurchaseRequestManager fm = ServiceLocator.getPurchaseRequestManager(request);

        String exportType = queryForm.getExportType();
        if (exportType != null && exportType.length() > 0) {
            List data = fm.getPurchaseRequestList(conditions, 0, -1, PurchaseRequestQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend());
            int index = SessionTempFile.createNewTempFile(request);
            String fileName = "purchaseRequest";
            String suffix = ExportUtil.export(exportType, data, request, new FileOutputStream(SessionTempFile.getTempFile(index, request)), new Exportable() {

                private String getMessage(String key, HttpServletRequest request) {
                    MessageResources messages = getResources(request);
                    Locale locale = getLocale(request);
                    return messages.getMessage(locale, key);
                }

                public void exportHead(List row, HttpServletRequest request) throws Exception {
                    row.add(this.getMessage("purchaseRequest.id", request));
                    row.add(this.getMessage("purchaseRequest.requestor.id", request));
                    row.add(this.getMessage("purchaseRequest.title", request));
                    row.add(this.getMessage("purchaseRequest.amount", request));
                    row.add(this.getMessage("purchaseRequest.createDate", request));
                    row.add(this.getMessage("purchaseRequest.status", request));
                }

                public void exportRow(List row, Object data, HttpServletRequest request) throws Exception {
                    row.add(BeanUtils.getProperty(data, "id"));
                    row.add(BeanUtils.getProperty(data, "requestor.name"));
                    row.add(BeanUtils.getProperty(data, "title"));
                    row.add(BeanUtils.getProperty(data, "amount"));

                    PurchaseRequest pr = (PurchaseRequest) data;
                    row.add(ActionUtils.getDisplayDateFromDate(pr.getCreateDate()));

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
            queryForm.init(fm.getPurchaseRequestListCount(conditions));
        } else {
            queryForm.init();
        }

        List result = fm.getPurchaseRequestList(conditions, queryForm.getPageNoAsInt(), queryForm.getPageSizeAsInt(), PurchaseRequestQueryOrder
                .getEnum(queryForm.getOrder()), queryForm.isDescend());

        request.setAttribute("X_RESULTLIST", result);
        this.putSelfOrOtherPostfixToRequest(isSelf,request);
        this.putEnumListToRequest(request);
        
        List siteList=null;
        UserManager um=ServiceLocator.getUserManager(request);
        siteList=um.getEnabledSiteListOfUser(this.getCurrentUser(request));
        request.setAttribute("x_siteList",siteList);
        return mapping.findForward("page");
    }

    private Map constructQueryMap(PurchaseRequestQueryForm queryForm, HttpServletRequest request) {
        Map conditions = new HashMap();

        String id = queryForm.getId();
        if (id != null && id.trim().length() != 0) {
            conditions.put(PurchaseRequestQueryCondition.ID_LIKE, id);
        }

        Integer purchaseSubCategory_id = ActionUtils.parseInt(queryForm.getPurchaseSubCategory_id());
        if (purchaseSubCategory_id != null) {
            conditions.put(PurchaseRequestQueryCondition.PURCHASESUBCATEGORY_ID_EQ, purchaseSubCategory_id);
        }
        Integer department_id = ActionUtils.parseInt(queryForm.getDepartment_id());
        Integer site_id = ActionUtils.parseInt(queryForm.getSite_id());
        if (department_id != null && department_id.intValue() != 0) {
            conditions.put(PurchaseRequestQueryCondition.DEPARTMENT_ID_EQ, department_id);
        } else {
            List siteList=this.getGrantedSiteDeparmentList(request);
            for (int i0 = 0; i0 < siteList.size(); i0++) {
                Site site = (Site)siteList.get(i0);
                if (site.getId().equals(site_id)) {
                    Object[] params = new Object[site.getDepartments().size()];                    
                    for (int j0 = 0; j0 < site.getDepartments().size(); j0++) {
                        params[j0] = ((Department)site.getDepartments().get(j0)).getId();
                    }
                    conditions.put(PurchaseRequestQueryCondition.DEPARTMENT_ID_IN, params);
                }
            }
        }
        String capex_id = queryForm.getCapex_id();
        if (capex_id != null && capex_id.trim().length() != 0) {
            conditions.put(PurchaseRequestQueryCondition.CAPEX_ID_EQ, capex_id);
        }
        Integer yearlyBudget_id = ActionUtils.parseInt(queryForm.getYearlyBudget_id());
        if (yearlyBudget_id != null) {
            conditions.put(PurchaseRequestQueryCondition.YEARLYBUDGET_ID_EQ, yearlyBudget_id);
        }
        String currency_code = queryForm.getCurrency_code();
        if (currency_code != null && currency_code.trim().length() != 0) {
            conditions.put(PurchaseRequestQueryCondition.CURRENCY_CODE_EQ, currency_code);
        }
        Integer requestor_id = ActionUtils.parseInt(queryForm.getRequestor_id());
        if (requestor_id != null) {
            conditions.put(PurchaseRequestQueryCondition.REQUESTOR_ID_EQ, requestor_id);
        }
        Integer creator_id = ActionUtils.parseInt(queryForm.getCreator_id());
        if (creator_id != null) {
            conditions.put(PurchaseRequestQueryCondition.CREATOR_ID_EQ, creator_id);
        }
        Integer purchaser_id = ActionUtils.parseInt(queryForm.getPurchaser_id());
        if (purchaser_id != null) {
            conditions.put(PurchaseRequestQueryCondition.PURCHASER_ID_EQ, purchaser_id);
        }

        String title = queryForm.getTitle();
        if (title != null && title.trim().length() != 0) {
            conditions.put(PurchaseRequestQueryCondition.TITLE_LIKE, title);
        }
        String description = queryForm.getDescription();
        if (description != null && description.trim().length() != 0) {
            conditions.put(PurchaseRequestQueryCondition.DESCRIPTION_LIKE, description);
        }
        String status = queryForm.getStatus();
        if (status != null && status.trim().length() != 0) {
            conditions.put(PurchaseRequestQueryCondition.STATUS_EQ, status);
        }
        String requestDate = queryForm.getRequestDate();
        if (requestDate != null && requestDate.trim().length() != 0) {
            conditions.put(PurchaseRequestQueryCondition.REQUESTDATE_EQ, requestDate);
        }
        String createDate = queryForm.getCreateDate();
        if (createDate != null && createDate.trim().length() != 0) {
            conditions.put(PurchaseRequestQueryCondition.CREATEDATE_EQ, createDate);
        }
        if (StringUtils.isNotEmpty(queryForm.getRequestor_name())) {
            conditions.put(PurchaseRequestQueryCondition.REQUESTOR_NAME_LIKE, queryForm.getRequestor_name());
        }
        //Integer site_id=ActionUtils.parseInt(queryForm.getSite_id());
        if (site_id!=null) {
            conditions.put(PurchaseRequestQueryCondition.SITE_ID_EQ,site_id);
        }
        
        if(StringUtils.isNotEmpty(queryForm.getAmount1()))
        {
            BigDecimal amount1=new BigDecimal(queryForm.getAmount1());
            conditions.put(PurchaseRequestQueryCondition.AMOUNT_GE,amount1);
        }
        
        if(StringUtils.isNotEmpty(queryForm.getAmount2()))
        {
            BigDecimal amount2=new BigDecimal(queryForm.getAmount2());
            conditions.put(PurchaseRequestQueryCondition.AMOUNT_LE,amount2);
        }
        
        Integer purchaseCategory_id = ActionUtils.parseInt(queryForm.getPurchaseCategory_id());
        if (purchaseCategory_id != null) {
            conditions.put(PurchaseRequestQueryCondition.PURCHASECATEGORY_ID_EQ, purchaseCategory_id);
        }
        
        if(StringUtils.isNotEmpty(queryForm.getCreateDate1()))
        {
            Date d = ActionUtils.getDateFromDisplayDate(queryForm.getCreateDate1());
            conditions.put(PurchaseRequestQueryCondition.CREATEDATE_GE, d);
        }
        
        if(StringUtils.isNotEmpty(queryForm.getCreateDate2()))
        {
            Date d = ActionUtils.getDateFromDisplayDate(queryForm.getCreateDate2());
            conditions.put(PurchaseRequestQueryCondition.CREATEDATE_LT, getNextDate(d));
        }
        
        return conditions;
    }
    
    private Date getNextDate(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.DATE, 1);
        return c.getTime();
    }
    
    public ActionForward editSelf(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return this.edit(mapping,form,request,response,true);
    }
    public ActionForward editOther(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return this.edit(mapping,form,request,response,false);
    }
    

    /**
     * action method for edit purchaseRequest
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response,boolean isSelf) throws Exception {
        PurchaseRequest purchaseRequest = getPurchaseRequestFromRequest(request);
        this.checkPurchaseRequestEditPower(purchaseRequest, request);
        request.setAttribute("x_pr", purchaseRequest);
        request.setAttribute("x_capex", purchaseRequest.getCapex());
        request.setAttribute("x_yearlyBudget", purchaseRequest.getYearlyBudget());
        if(purchaseRequest.getCapex()!=null)
            this.putCanViewCapexAmountToRequest(purchaseRequest.getCapex(),request);
        
        if(purchaseRequest.getYearlyBudget()!=null)
            this.putCanViewYearlyBudgetAmount(purchaseRequest.getYearlyBudget(),request);
        PurchaseRequestManager pm=ServiceLocator.getPurchaseRequestManager(request);
        request.setAttribute("x_deletable",new Boolean(!pm.purchaseRequestHasPOItem(purchaseRequest)));

        if (!this.isBack(request)) {
            BeanForm purchaseRequestForm = (BeanForm) getForm("/updatePurchaseRequest_self", request);
            purchaseRequestForm.populate(purchaseRequest, BeanForm.TO_FORM);
        }

        this.putPurchaseRequestDetailsToRequest(purchaseRequest, request);

        this.putEnumListToRequest(request);
        this.putSelfOrOtherPostfixToRequest(isSelf,request);
        this.setEditing(true, request);

        return mapping.findForward("page");
    }
    
    public ActionForward copy(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        final boolean isSelf = this.isGlobal(request);
        PurchaseRequest pr = getPurchaseRequestFromRequest(request);
        PurchaseRequestManager prm = ServiceLocator.getPurchaseRequestManager(request);
        
        PurchaseRequest copyPR = new PurchaseRequest();
        copyPR.setCreator(this.getCurrentUser(request));
        if (isSelf) {
            copyPR.setRequestor(this.getCurrentUser(request));
        } else {
            copyPR.setRequestor(pr.getRequestor());
            checkDepartment(pr.getDepartment(),request);
        }
        
        copyPR = prm.copyPurchaseRequest(copyPR,pr);
        return getEditForwardFor(copyPR, request, isSelf);
    }

    public ActionForward newObjectSelf(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return newObject(mapping,form,request,response,true);
    }
    
    public ActionForward newObjectOther(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return newObject(mapping,form,request,response,false);
    }
    
    /**
     * action method for new PurchaseRequest
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward newObject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response,boolean isSelf) throws Exception {
        List siteList = null;
        
        UserManager um = ServiceLocator.getUserManager(request);
        siteList = um.getEnabledSiteDepartmentTreeOfUser(this.getCurrentUser(request));
        
        request.setAttribute("x_siteList", siteList);

        PurchaseCategoryManager pcm = ServiceLocator.getPurchaseCategoryManager(request);
        pcm.fillEnabledPurchaseCategorySubCategoryForSiteList(siteList);

        if (!isBack(request)) {
            PurchaseRequest purchaseRequest = new PurchaseRequest();
            purchaseRequest.setRequestor(this.getCurrentUser(request));
            BeanForm purchaseRequestForm = (BeanForm) getForm("/insertPurchaseRequest_self", request);
            purchaseRequestForm.populate(purchaseRequest, BeanForm.TO_FORM);
            purchaseRequestForm.set("budgetType", "1");// capex
        }

        if (StringUtils.isNotEmpty(request.getParameter(PARAMETER_YEAYLYBUDGET_ID))) {
            YearlyBudget yb = this.getYearlyBudgetFromRequest(request);
            this.putYearlyBudgetDepartmentIdsToRequest(yb, request);
            request.setAttribute("x_yearlyBudget", yb);
        }

        if (StringUtils.isNotEmpty(request.getParameter(PARAMETER_CAPEXID_ID))) {
            Capex cp = this.getCapexFromRequest(request);
            this.putCapexDepartmentIdsToRequest(cp, request);
            request.setAttribute("x_capex", cp);
        }

        this.putEnumListToRequest(request);
        this.putSelfOrOtherPostfixToRequest(isSelf,request);

        return mapping.findForward("page");
    }

    private static final String PARAMETER_YEAYLYBUDGET_ID = "yearlyBudget_id";

    private static final String PARAMETER_CAPEXID_ID = "capex_id";

    private Capex getCapexFromRequest(HttpServletRequest request) {
        String capex_id = request.getParameter(PARAMETER_CAPEXID_ID);
        if (StringUtils.isEmpty(capex_id))
            throw new ActionException("purchaseRequest.capex.idNotSet");
        CapexManager cm = ServiceLocator.getCapexManager(request);
        Capex cp = cm.getCapex(capex_id);
        if (cp == null)
            throw new ActionException("purchaseRequest.capex.notFound");
        return cp;
    }

    private YearlyBudget getYearlyBudgetFromRequest(HttpServletRequest request) {
        Integer yearlyBudget_id = ActionUtils.parseInt(request.getParameter(PARAMETER_YEAYLYBUDGET_ID));
        if (yearlyBudget_id == null)
            throw new ActionException("purchaseRequest.yearlyBudget.idNotSet");
        YearlyBudgetManager ybm = ServiceLocator.getYearlyBudgetManager(request);
        YearlyBudget yb = ybm.getYearlyBudget(yearlyBudget_id);
        if (yb == null)
            throw new ActionException("purchaseRequest.yearlyBudget.notFound");
        return yb;
    }
    

    /**
     * action method for viewing purchaseRequest
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward viewPurchaseRequest(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PurchaseRequest pr = this.getPurchaseRequestFromRequest(request);
        request.setAttribute("x_pr", pr);

        this.putPurchaseRequestDetailsForView(pr, request);

        this.setEditing(false, request);
        request.setAttribute("x_capex", pr.getCapex());
        request.setAttribute("x_yearlyBudget", pr.getYearlyBudget());

        return mapping.findForward("page");
    }
    
    public ActionForward viewReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PurchaseRequest pr = this.getPurchaseRequestFromRequest(request);
        this.checkDepartment(pr.getDepartment(),request);
        request.setAttribute("x_postfix","_other");
        request.setAttribute("x_canExportPdf",Boolean.TRUE);
        return viewPurchaseRequest(mapping,form,request,response);
        
    }
    
    public ActionForward viewSelf(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return this.view(mapping,form,request,response,true);
    }
    
    public ActionForward viewOther(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return this.view(mapping,form,request,response,false);
    }
    
    
    public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response,boolean isSelf) throws Exception {
        PurchaseRequest pr = this.getPurchaseRequestFromRequest(request);
        this.checkPurchaseRequestViewPower(pr, request);
        this.putSelfOrOtherPostfixToRequest(isSelf,request);
        
        if(pr.getCapex()!=null)
            this.putCanViewCapexAmountToRequest(pr.getCapex(),request);
        
        if(pr.getYearlyBudget()!=null)
            this.putCanViewYearlyBudgetAmount(pr.getYearlyBudget(),request);

        this.putCanExportPdf(pr,request);
        return viewPurchaseRequest(mapping,form,request,response);
    }
    
    public ActionForward exportPDF(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PurchaseRequest pr = this.getPurchaseRequestFromRequest(request);
        this.checkCanExportPDF(pr);
        this.checkPurchaseRequestViewPower(pr, request);
        this.putPurchaseRequestDetailsForView(pr, request);
        if(pr.getCapex()!=null)
            this.putCanViewCapexAmountToRequest(pr.getCapex(),request);
        
        if(pr.getYearlyBudget()!=null)
            this.putCanViewYearlyBudgetAmount(pr.getYearlyBudget(),request);
        
        exportPDF(pr,request,response,this.isGlobal(request));
        return null;
    }
    
    private void putCanExportPdf(PurchaseRequest pr,HttpServletRequest request)
    {
        request.setAttribute("x_canExportPdf",
                new Boolean(
                        pr.getStatus().equals(PurchaseRequestStatus.APPROVED) || 
                        pr.getStatus().equals(PurchaseRequestStatus.BOOKED) || 
                        pr.getStatus().equals(PurchaseRequestStatus.CLOSED) ));
    }
    
    private void exportPDF(PurchaseRequest pr, HttpServletRequest request, HttpServletResponse response, boolean self) throws Exception {
        MessageResources messages = getResources(request);
        Locale locale = getLocale(request);
        
        PDFReport report = PDFReport.createReport(
                pr.getDepartment().getSite().getId(),
                self ? "purchaseRequest.view.self.title" : "purchaseRequest.view.other.title",
                request,
                messages,
                locale);
        
        Document document = report.getDocument();

        Font subTitleFont=PDFReport.getSubTitleFont(); 

        Paragraph p = null;
        
        Color tColor = new Color(0, 0x33, 0x66);
        
        PdfPTable table = PDFReport.createTable(new float[] { 1, 2 , 1 , 2 }, 100, 0);
        PdfPCell defaultCell = table.getDefaultCell();
        defaultCell.setNoWrap(false);
        report.addCell(table, "purchaseRequest.site.id", tColor, true);
        report.addCell(table, pr.getDepartment().getSite().getName());
        report.addCell(table, "purchaseRequest.department", tColor, true);
        report.addCell(table, pr.getDepartment().getName());
        if (!self) {
            report.addCell(table, "purchaseRequest.requestor.id", tColor, true);
            report.addCell(table, pr.getRequestor().getName(),3);
        }    
        report.addCell(table, "purchaseRequest.title", tColor, true);
        report.addCell(table, pr.getTitle());
        report.addCell(table, "purchaseRequest.id", tColor, true);
        report.addCell(table, pr.getId());
        report.addCell(table, "purchaseRequest.description", tColor, true);
        report.addCell(table, pr.getDescription(),3);
        report.addCell(table, "purchaseRequest.purchaseCategory.id", tColor, true);
        report.addCell(table, pr.getPurchaseSubCategory().getPurchaseCategory().getDescription());
        report.addCell(table, "purchaseRequest.purchaseSubCategory.id", tColor, true);
        report.addCell(table, pr.getPurchaseSubCategory().getDescription());
        report.addCell(table, "purchaseRequest.status", tColor, true);
        report.addCell(table, pr.getStatus(),3);
        document.add(table);
        
        report.addSplitLine();
        
        table = PDFReport.createTable(new float[] { 1, 2 , 1 , 2 }, 100, 0);
        defaultCell = table.getDefaultCell();
        defaultCell.setNoWrap(false);
        report.addCell(table, "purchaseRequest.withinBudget", tColor, true);
        if (pr.getCapex()!=null) {
            report.addCell(table, "purchaseRequest.capexBudget", 3 ,  true);
            report.addCell(table, "purchaseRequest.capex.id", tColor, true);
            report.addCell(table, pr.getCapex().getId());
            report.addCell(table, "purchaseRequest.capex.description", tColor, true);
            report.addCell(table, "");
            report.addCell(table, "purchaseRequest.capex.category", tColor, true);
            if (pr.getCapex().getPurchaseCategory()!=null)
                report.addCell(table, pr.getCapex().getPurchaseCategory().getDescription());
            else
                report.addCell(table,"");
            report.addCell(table, "purchaseRequest.capex.subCategory", tColor, true);
            if (pr.getCapex().getPurchaseSubCategory()!=null)
                report.addCell(table, pr.getCapex().getPurchaseSubCategory().getDescription());
            else
                report.addCell(table,"");
            report.addCell(table, "purchaseRequest.capex.requestor", tColor, true);
            report.addCell(table, pr.getCapex().getLastApprovedRequest()!=null?pr.getCapex().getLastApprovedRequest().getRequestor().getName():"");
            report.addCell(table, "purchaseRequest.capex.requestDate", tColor, true);
            if (pr.getCapex().getLastApprovedRequest()!=null) 
                report.addCell(table, pr.getCapex().getLastApprovedRequest().getRequestDate());
            else
                report.addCell(table, "");
            
            boolean canViewCapexAmount=((Boolean)request.getAttribute("x_canViewCapexAmount")).booleanValue();
            if (canViewCapexAmount) {
                report.addCell(table, "purchaseRequest.capex.amount", tColor, true);
                if (pr.getCapex().getLastApprovedRequest()!=null) 
                    report.addCell(table, pr.getCapex().getLastApprovedRequest().getAmount());
                else
                    report.addCell(table, "");
                report.addCell(table, "purchaseRequest.capex.actualAmount", tColor, true);
                report.addCell(table, pr.getCapex().getActualAmount());
            }
        }    
        if (pr.getYearlyBudget()!=null) { 
            report.addCell(table, "purchaseRequest.budget.code", 3 , true);
            report.addCell(table, "purchaseRequest.yealyBudget.id", tColor, true);
            report.addCell(table, pr.getYearlyBudget().getCode());
            report.addCell(table, "purchaseRequest.yealyBudget.description", tColor, true);
            report.addCell(table, pr.getYearlyBudget().getName());
            boolean canViewYearlyBudgetAmount=((Boolean)request.getAttribute("x_canViewYearlyBudgetAmount")).booleanValue();
            if (canViewYearlyBudgetAmount) {
                report.addCell(table, "purchaseRequest.yearlyBudget.amount", tColor, true);
                report.addCell(table, pr.getYearlyBudget().getAmount());
                report.addCell(table, "purchaseRequest.yearlyBudget.actualAmount", tColor, true);
                report.addCell(table, pr.getYearlyBudget().getActualAmount());
            }
        }
        if (pr.getCapex()==null && pr.getYearlyBudget()==null)
            report.addCell(table, "purchaseRequest.nonBudget", 3 , true);
        
        document.add(table);
        
        report.addSplitLine();
        
        table = PDFReport.createTable(new float[] { 1, 2 , 1 , 2 }, 100, 0);
        report.addCell(table, "purchaseRequest.requestAmount", tColor, true);
        report.addCell(table, pr.getAmount());
        report.addCell(table, "purchaseRequest.baseCurrency", tColor, true);
        report.addCell(table, pr.getCurrency().getCode());
        report.addCell(table, "purchaseRequest.requestor.id", tColor, true);
        report.addCell(table, pr.getRequestor().getName());
        report.addCell(table, "purchaseRequest.requestDate", tColor, true);
        if (pr.getRequestDate()!=null)
            report.addCell(table, pr.getRequestDate());
        else
            report.addCell(table, "");
        
        document.add(table);
        
        PurchaseRequestManager prm =ServiceLocator.getPurchaseRequestManager(request);
        List itemList=(List)request.getAttribute("x_purchaseRequestItemList");
        if (itemList.size()>0) {
            p = new Paragraph(
                    new StringBuffer().append(messages.getMessage(locale, "purchaseRequest.item")).append("\n").toString(), 
                    subTitleFont);
            p.setAlignment(Element.ALIGN_LEFT);
            document.add(p);
            
            for (Iterator itor = itemList.iterator(); itor.hasNext();) {
                report.addSpaceLine(3);
                table = PDFReport.createTable(new float[] { 4 , 4 , 4 , 4 , 3 , 3 , 4 , 5.5f , 4 }, 100, 0.5f);
                Color defaultBackgroundColor = table.getDefaultCell().backgroundColor();
                defaultCell = table.getDefaultCell();
                defaultCell.setBackgroundColor(new Color(0x99,0x99,0xff));
                defaultCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                Font headFont = PDFReport.getFont(Font.BOLD, Color.BLACK);
                report.addCell(table, "purchaseRequestItem.supplier.id", headFont, true);
                report.addCell(table, "purchaseRequestItem.supplierItem.id", headFont, true);
                report.addCell(table, "purchaseRequestItem.supplierItemSepc", headFont, true);
                report.addCell(table, "purchaseRequestItem.price", headFont, true);
                report.addCell(table, "purchaseRequestItem.exchangeRate.id", headFont, true);
                report.addCell(table, "purchaseRequestItem.quantity", headFont, true);
                report.addCell(table, "purchaseRequestItem.amount", headFont, true);
                defaultCell.setNoWrap(false);
                report.addCell(table, "purchaseRequestItem.baseCurrencyAmount", headFont, true);
                defaultCell.setNoWrap(true);
                report.addCell(table, "purchaseRequestItem.dueDate", headFont, true);
                defaultCell.setBackgroundColor(defaultBackgroundColor);
                defaultCell.setNoWrap(false);
                
                PurchaseRequestItem item = (PurchaseRequestItem) itor.next();
                defaultCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                if (item.getSupplier()!=null)
                    report.addCell(table, item.getSupplier().getName());
                else
                    report.addCell(table, item.getSupplierName());
                if (item.getSupplierItem()!=null)
                    report.addCell(table, item.getSupplierItem().getSepc());
                else
                    report.addCell(table, "");
                if (item.getSupplierItemSepc()!=null)
                    report.addCell(table, item.getSupplierItemSepc());
                else
                    report.addCell(table, "");
                defaultCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                report.addCell(table, item.getUnitPrice());
                defaultCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                report.addCell(table, item.getExchangeRate().getCurrency().getName());
                defaultCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                report.addCell(table, item.getQuantity()+"");
                report.addCell(table, item.getAmount());
                report.addCell(table, item.getBaseCurrencyAmount());
                defaultCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                report.addCell(table, item.getDueDate());
                
                PurchaseRequestItem pri=prm.getPurchaseRequestItemWithDetails(item.getId());
                report.addRechargeListTableToTable(table,9, pri, pri.getAmount(),pri.getRecharges());
                if (pri.getAttachments().size()>0) {
                    report.addAttachmentListTableToTable(table,9,pri.getAttachments(),
                            "purchaseRequestItem.attachment",
                            "purchaseRequestItemAttachment.title",
                            "purchaseRequestItemAttachment.fileName",
                            "purchaseRequestItemAttachment.uploadDate"
                            );
                }
                document.add(table);
            }
            
        }
        
        /*
        report.addAttachmentListTable((List)request.getAttribute("x_purchaseRequestAttachmentList"),
                "purchaseRequest.attachment",
                "purchaseRequestAttachment.title",
                "purchaseRequestAttachment.fileName",
                "purchaseRequestAttachment.uploadDate");
        */

        report.addApproveListTable((List)request.getAttribute("X_APPROVELIST"));
        
        report.output("purchaseRequest", response);
        
    }

    private void checkCanExportPDF(PurchaseRequest pr) {
        if (!(pr.getStatus().equals(PurchaseRequestStatus.APPROVED) || pr.getStatus().equals(PurchaseRequestStatus.BOOKED) || pr.getStatus().equals(PurchaseRequestStatus.CLOSED))) 
            throw new ActionException("purchaseRequest.pdf.notAllowed");
        
    }

    public ActionForward viewDialog(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PurchaseRequest pr = this.getPurchaseRequestFromRequest(request);
        this.checkSite(pr.getDepartment().getSite(),request);
        return viewPurchaseRequest(mapping,form,request,response);
    }

    public ActionForward update_viewApprover_self(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return update(mapping, form, request, response,true, true);
    }
    public ActionForward update_viewApprover_other(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return update(mapping, form, request, response,false, true);
        
    }
    
    
    /**
     * action method for updating purchaseRequest
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward updateSelf(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return update(mapping, form, request, response,true, false);
    }
    
    public ActionForward updateOther(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return update(mapping, form, request, response,false, false);
    }
    
    
    private BigDecimal getSumAmountOfPrItems(List list)
    {
        BigDecimal amount = new BigDecimal(0d);
        for (Iterator iter = list.iterator(); iter != null && iter.hasNext();) {
            PurchaseRequestItem pri = (PurchaseRequestItem) iter.next();
            amount = amount.add(pri.getBaseCurrencyAmount());
        }
        return amount;
    }

    private BigDecimal getMaxPriceOfPrItems(List itemList) {
        BigDecimal maxPrice = null;
        for (Iterator iter = itemList.iterator(); iter.hasNext();) {
            PurchaseRequestItem pri = (PurchaseRequestItem) iter.next();
            BigDecimal price = pri.getBaseCurrencyPrice();
            if (maxPrice == null || maxPrice.compareTo(price) < 0) {
                maxPrice = price;
            }
        }
        return maxPrice;
    }

    private ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response,boolean isSelf, boolean viewApprover)
            throws Exception {
        PurchaseRequest pr = this.getPurchaseRequestFromRequest(request);
        BigDecimal oldAmount=pr.getAmount();
        this.checkPurchaseRequestEditPower(pr, request);

        BeanForm purchaseRequestForm = (BeanForm) form;
        purchaseRequestForm.populateToBean(pr, request);

        List itemList = this.getPurchaseRequestItemListFromSession(request);
        BigDecimal newAmount=getSumAmountOfPrItems(itemList);
        pr.setMaxItemPrice(getMaxPriceOfPrItems(itemList));
        if(pr.getYearlyBudget()!=null)
        {
            pr.getYearlyBudget().updateActualAmount(oldAmount,newAmount);
        }
        else if(pr.getCapex()!=null)
        {
            pr.getCapex().updateActualAmount(oldAmount,newAmount);
        }
        if (viewApprover) {
            try {
                request.setAttribute("X_APPROVELIST", executeFlow(pr, request));
            } catch (ActionException e) {
                ActionMessage message = new ActionMessage(e.getKey(), e.getValues());
                ActionMessages messages = new ActionMessages();
                messages.add(null, message);
                saveErrors(request, messages);
            }
            return mapping.findForward("page");
        } else {              
            this.clearPurchaseRequestItemListFromSession(request);

            List attachmentList = this.getPurchaseRequestAttachmentListFromRequest(request);

            PurchaseRequestManager purchaseRequestManager = ServiceLocator.getPurchaseRequestManager(request);
            try{
                purchaseRequestManager.updatePurchaseRequest(pr, itemList, attachmentList, isDraft(request),this.getCurrentUser(request));
            }
            catch(HibernateOptimisticLockingFailureException e)
            {
                throw new ActionException("all.stale");
            }

            if (this.isDraft(request)) {
                this.postGlobalMessage("purchaseRequest.saveDraft.success", request.getSession());
                return getEditForwardFor(pr, request,isSelf);
            } else {
                this.postGlobalMessage("purchaseRequest.submit.success", request.getSession());
                return getViewForwardFor(pr, request,isSelf);
            }
        }
    }

    private List executeFlow(PurchaseRequest pr, HttpServletRequest request) {
        try {
            List eList = ServiceLocator.getFlowManager(request).executeApproveFlow(pr);
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
    
    public ActionForward insertSelf(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return this.insert(mapping,form,request,response,true);
    }
    
    public ActionForward insertOther(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return this.insert(mapping,form,request,response,false);
    }
    

    /**
     * action method for inserting purchaseMethod
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward insert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response,boolean isSelf) throws Exception {
        BeanForm purchaseRequestForm = (BeanForm) form;
        purchaseRequestForm.setBeanLoader(ServiceLocator.getBeanLoader(request));

        PurchaseRequest purchaseRequest = new PurchaseRequest();
        purchaseRequestForm.populate(purchaseRequest, BeanForm.TO_BEAN);


        final String budgetType = purchaseRequestForm.getString("budgetType");
        if (budgetType.equals("1")) {// capex
            if (purchaseRequest.getCapex() == null)
                throw new BackToInputActionException("purchaseRequest.capex.required");
            purchaseRequest.setYearlyBudget(null);
        } else if (budgetType.equals("2")) {// yearlyBudget
            if (purchaseRequest.getYearlyBudget() == null)
                throw new BackToInputActionException("purchaseRequest.yearlyBudget.required");
            purchaseRequest.setCapex(null);
        } else if (budgetType.equals("3")) {// none
            purchaseRequest.setYearlyBudget(null);
            purchaseRequest.setCapex(null);
        } else
            throw new RuntimeException("budgetType error");

        User currentUser = this.getCurrentUser(request);

        PurchaseRequestManager purchaseRequestManager = ServiceLocator.getPurchaseRequestManager(request);
        if(isSelf)
            purchaseRequest.setIsDelegate(YesNo.NO);
        else
            purchaseRequest.setIsDelegate(YesNo.YES);

        purchaseRequest = purchaseRequestManager.insertPurchaseRequest(purchaseRequest, currentUser, currentUser);

        this.postGlobalMessage("purchaseRequest.insert.success",request.getSession());
        return getEditForwardFor(purchaseRequest, request,isSelf);
    }

    private ActionForward getEditForwardFor(PurchaseRequest purchaseRequest, HttpServletRequest request,boolean isSelf) {
        if (isSelf)
            return new ActionForward("editPurchaseRequest_self.do?id=" + purchaseRequest.getId(), true);
        else
            return new ActionForward("editPurchaseRequest_other.do?id=" + purchaseRequest.getId(), true);
    }

    private ActionForward getViewForwardFor(PurchaseRequest purchaseRequest, HttpServletRequest request,boolean isSelf) {
        if (isSelf)
            return new ActionForward("viewPurchaseRequest_self.do?id=" + purchaseRequest.getId(), true);
        else
            return new ActionForward("viewPurchaseRequest_other.do?id=" + purchaseRequest.getId(), true);
    }

    private ActionForward getListForwardFor(PurchaseRequest purchaseRequest, HttpServletRequest request,boolean isSelf) {
        if (isSelf)
            return new ActionForward("listPurchaseRequest_self.do", true);
        else
            return new ActionForward("listPurchaseRequest_other.do", true);
    }

}