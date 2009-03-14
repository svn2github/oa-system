/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.web.struts.action.business.expense;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
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

import net.sourceforge.model.admin.Department;
import net.sourceforge.model.admin.ExpenseCategory;
import net.sourceforge.model.admin.ExpenseSubCategory;
import net.sourceforge.model.admin.PurchaseSubCategory;
import net.sourceforge.model.admin.Site;
import net.sourceforge.model.admin.User;
import net.sourceforge.model.business.expense.Expense;
import net.sourceforge.model.business.expense.ExpenseApproveRequest;
import net.sourceforge.model.business.expense.ExpenseCell;
import net.sourceforge.model.business.expense.ExpenseClaim;
import net.sourceforge.model.business.expense.ExpenseRow;
import net.sourceforge.model.business.expense.query.ExpenseQueryCondition;
import net.sourceforge.model.business.expense.query.ExpenseQueryOrder;
import net.sourceforge.model.business.pr.YearlyBudget;
import net.sourceforge.model.business.ta.TravelApplication;
import net.sourceforge.model.business.ta.query.TravelApplicationQueryCondition;
import net.sourceforge.model.business.ta.query.TravelApplicationQueryOrder;
import net.sourceforge.model.metadata.ApproveStatus;
import net.sourceforge.model.metadata.BudgetType;
import net.sourceforge.model.metadata.ExpenseStatus;
import net.sourceforge.model.metadata.ExpenseType;
import net.sourceforge.model.metadata.ExportStatus;
import net.sourceforge.model.metadata.TravelApplicationStatus;
import net.sourceforge.model.metadata.YesNo;
import net.sourceforge.service.admin.DepartmentManager;
import net.sourceforge.service.admin.EmailManager;
import net.sourceforge.service.admin.ExpenseCategoryManager;
import net.sourceforge.service.admin.ExpenseSubCategoryManager;
import net.sourceforge.service.admin.PurchaseSubCategoryManager;
import net.sourceforge.service.admin.UserManager;
import net.sourceforge.service.business.expense.ExpenseApproveRequestManager;
import net.sourceforge.service.business.expense.ExpenseManager;
import net.sourceforge.service.business.pr.YearlyBudgetManager;
import net.sourceforge.service.business.rule.ExecuteFlowEmptyResultException;
import net.sourceforge.service.business.rule.FlowManager;
import net.sourceforge.service.business.rule.NoAvailableFlowToExecuteException;
import net.sourceforge.service.business.ta.TravelApplicationManager;
import net.sourceforge.utils.PDFReport;
import net.sourceforge.utils.SessionTempFile;
import net.sourceforge.web.struts.action.ActionUtils;
import net.sourceforge.web.struts.action.ServiceLocator;
import net.sourceforge.web.struts.action.admin.BasicAction;
import net.sourceforge.web.struts.form.business.expense.ExpenseQueryForm;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.action.BackToInputActionException;
import com.shcnc.struts.form.BaseQueryForm;
import com.shcnc.struts.form.BeanForm;
import com.shcnc.struts.form.DynaValidatorForm;
import com.shcnc.utils.BeanHelper;
import com.shcnc.utils.ExportUtil;
import com.shcnc.utils.Exportable;

/**
 * Expense的Action类
 * 
 * @author ych
 * @version 1.0 (Nov 19, 2005)
 */
public class ExpenseAction extends BaseExpenseAction {
    /**
     * view expense for final confirm
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward view_finalConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Expense ep = this.getExpenseFromRequest(request);
        this.checkConfirmedOrRejected(ep);
        this.checkSite(ep.getDepartment().getSite(), request);
        request.setAttribute("x_postfix","_finalConfirm");
        request.setAttribute("x_pdf", "true");
        return this.view(mapping, form, request, response);
    }
    
    /**
     * view expense for final claim
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward view_finalClaim(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Expense ep = this.getExpenseFromRequest(request);
        this.checkClaimedOrRejected(ep);
        this.checkSite(ep.getDepartment().getSite(), request);
        request.setAttribute("x_postfix","_finalClaim");
        request.setAttribute("x_pdf", "true");
        return this.view(mapping, form, request, response);
    }

    private void checkRequestorSelf(Expense ep, HttpServletRequest request)
    {
        if(!ep.getRequestor().equals(this.getCurrentUser(request)))
            throw new ActionException("expense.requestor.notSelf");
    }
    
    private void checkCreatorSelf(Expense ep, HttpServletRequest request)
    {
        if(!ep.getCreator().equals(this.getCurrentUser(request)))
            throw new ActionException("expense.creator.notSelf");
    }

    /**
     * view current user's expense
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward view_self(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Expense ep = this.getExpenseFromRequest(request);
        this.checkRequestorSelf(ep,request);
        request.setAttribute("x_postfix", "_self");
        request.setAttribute("x_pdf", "true");
        return this.view(mapping, form, request, response);
    }

    /**
     * view other's expense
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward view_other(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Expense ep = this.getExpenseFromRequest(request);
        this.checkCreatorSelf(ep,request);
        this.checkDepartment(ep.getDepartment(), request);
        request.setAttribute("x_postfix", "_other");
        request.setAttribute("x_pdf", "true");
        return this.view(mapping, form, request, response);
    }

    private ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Expense expense = this.getExpenseFromRequest(request);
        request.setAttribute("x_expense", expense);
        getViewContent(expense,request);
        this.putCanViewExpenseBudgetAmount(expense.getYearlyBudget(),request);
        return mapping.findForward("page");
    }
    
    private void getViewContent(Expense expense,HttpServletRequest request) throws Exception {
        putExpenseSubCategoryListToRequest(expense,request);
        putEnumListToRequest(request);
        putExpenseAttachmentToRequest(expense, request);
        putExpenseRowCellListToRequest(expense,request,true,true);
        putApproveListToRequest(expense,request);
        putExpenseRechargeListToRequest(expense,request,null,true);
        /*if (expense.getStatus().equals(ExpenseStatus.CLAIMED)) {
            request.setAttribute("x_confirmedAmount",expense.getConfirmedAmount());
        }*/
    }
    
    public ActionForward reportView(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Expense ep = this.getExpenseFromRequest(request);
        this.checkDepartment(ep.getDepartment(), request);
        //request.setAttribute("x_report", "true");
        request.setAttribute("x_postfix", "_other");
        request.setAttribute("x_pdf", "true");
        return this.view(mapping, form, request, response);
    }

    private void checkConfirmedOrRejected(Expense ep) {
        if (!ep.getStatus().equals(ExpenseStatus.CLAIMED) 
                && !ep.getStatus().equals(ExpenseStatus.CONFIRMED) 
                && !ep.getStatus().equals(ExpenseStatus.REJECTED))
            throw new ActionException("expense.finalConfirm.view.notConfirmedOrRejected");
    }

    private void checkClaimedOrRejected(Expense ep) {
        if (!ep.getStatus().equals(ExpenseStatus.CLAIMED) && !ep.getStatus().equals(ExpenseStatus.REJECTED))
            throw new ActionException("expense.finalClaim.view.notClaimedOrRejected");
    }

    /**
     * 查询Expense
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        ExpenseManager em = ServiceLocator.getExpenseManager(request);
        ExpenseQueryForm queryForm = (ExpenseQueryForm) form;

        Map conditions = constructQueryMap(queryForm, request);

        final boolean isSelf = this.isGlobal(request);

        if (isSelf) {
            conditions.put(ExpenseQueryCondition.REQUESTOR_ID_EQ, this.getCurrentUser(request).getId());
            request.setAttribute("x_postfix", "_self");
        } else {
            conditions.put(ExpenseQueryCondition.CREATOR_ID_EQ, this.getCurrentUser(request).getId());
            conditions.put(ExpenseQueryCondition.REQUESTOR_ID_NEQ, this.getCurrentUser(request).getId());
            request.setAttribute("x_postfix", "_other");
        }

        String exportType = queryForm.getExportType();
        if (StringUtils.isNotEmpty(exportType)) {
            List data = em.getExpenseList(conditions, 0, -1, ExpenseQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend());

            int index = SessionTempFile.createNewTempFile(request);
            String fileName = "expense";
            String suffix = ExportUtil.export(exportType, data, request, new FileOutputStream(SessionTempFile.getTempFile(index, request)), new Exportable() {

                public void exportHead(List row, HttpServletRequest request) throws Exception {
                    MessageResources messages = getResources(request);
                    row.add(messages.getMessage(getLocale(request), "expense.listExpense.code"));
                    row.add(messages.getMessage(getLocale(request), "expense.listExpense.category"));
                    row.add(messages.getMessage(getLocale(request), "expense.listExpense.title"));
                    row.add(messages.getMessage(getLocale(request), "expense.listExpense.currency"));
                    row.add(messages.getMessage(getLocale(request), "expense.listExpense.totalAmount"));
                    row.add(messages.getMessage(getLocale(request), "expense.listExpense.requestDate"));
                    if (!isSelf) {
                        row.add(messages.getMessage(getLocale(request), "expense.listExpense.requestor"));
                    }
                    row.add(messages.getMessage(getLocale(request), "expense.listExpense.status"));
                }

                public void exportRow(List row, Object data, HttpServletRequest request) throws Exception {
                    row.add(BeanHelper.getBeanPropertyValue(data, "id"));
                    row.add(BeanHelper.getBeanPropertyValue(data, "expenseCategory.description"));
                    row.add(BeanHelper.getBeanPropertyValue(data, "title"));
                    row.add(BeanHelper.getBeanPropertyValue(data, "expenseCurrency.name"));
                    row.add(BeanHelper.getBeanPropertyValue(data, "amount"));
                    Expense expense = (Expense) data;
                    if (expense.getRequestDate() != null)
                        row.add(ActionUtils.getDisplayDateFromDate(expense.getRequestDate()));
                    else
                        row.add("");
                    if (!isSelf) {
                        row.add(BeanHelper.getBeanPropertyValue(data, "requestor.name"));
                    }
                    if (isEnglish(request))
                        row.add(BeanHelper.getBeanPropertyValue(data, "status.engShortDescription"));
                    else
                        row.add(BeanHelper.getBeanPropertyValue(data, "status.chnShortDescription"));

                }
            });
            return new ActionForward("download/" + index + "/" + URLEncoder.encode(fileName, "UTF-8") + '.' + suffix, true);
        }

        if (queryForm.isFirstInit()) {
            queryForm.init(em.getExpenseListCount(conditions));
        } else {
            queryForm.init();
        }

        List result = em.getExpenseList(conditions, queryForm.getPageNoAsInt(), queryForm.getPageSizeAsInt(), ExpenseQueryOrder.getEnum(queryForm.getOrder()),
                queryForm.isDescend());

        this.putEnumListToRequest(request);
        putEnabledUserSiteListToRequest(request);
        request.setAttribute("X_RESULTLIST", result);
        return mapping.findForward("page");
    }

    /**
     * 列出我的出差申请列表
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward listMyTA(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        BaseQueryForm queryForm = (BaseQueryForm) form;
        Map conditions = new HashMap();
        conditions.put(TravelApplicationQueryCondition.REQUESTOR_ID_EQ, this.getCurrentUser(request).getId());
        conditions.put(TravelApplicationQueryCondition.STATUS_EQ, TravelApplicationStatus.APPROVED);
        if (StringUtils.isEmpty(queryForm.getOrder())) {
            queryForm.setOrder(TravelApplicationQueryOrder.ID.getName());
            queryForm.setDescend(true);
        }
        TravelApplicationManager tam = ServiceLocator.getTravelApplicationManager(request);

        String exportType = queryForm.getExportType();
        if (StringUtils.isNotEmpty(exportType)) {
            List data = tam.getTravelApplicationList(conditions, 0, -1, TravelApplicationQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend());

            int index = SessionTempFile.createNewTempFile(request);
            String fileName = "My travel application";
            String suffix = ExportUtil.export(exportType, data, request, new FileOutputStream(SessionTempFile.getTempFile(index, request)), new Exportable() {

                public void exportHead(List row, HttpServletRequest request) throws Exception {
                    MessageResources messages = getResources(request);
                    row.add(messages.getMessage(getLocale(request), "expense.listTravelApplication.code"));
                    row.add(messages.getMessage(getLocale(request), "expense.listTravelApplication.destination"));
                    row.add(messages.getMessage(getLocale(request), "expense.listTravelApplication.travelMode"));
                    row.add(messages.getMessage(getLocale(request), "expense.listTravelApplication.createDate"));
                    row.add(messages.getMessage(getLocale(request), "expense.listTravelApplication.status"));
                }

                public void exportRow(List row, Object data, HttpServletRequest request) throws Exception {
                    row.add(BeanHelper.getBeanPropertyValue(data, "id"));
                    if (isEnglish(request))
                        row.add(BeanHelper.getBeanPropertyValue(data, "toCity.province.country.engName") + "/"
                                + BeanHelper.getBeanPropertyValue(data, "toCity.province.engName") + "/"
                                + BeanHelper.getBeanPropertyValue(data, "toCity.engName"));
                    else
                        row.add(BeanHelper.getBeanPropertyValue(data, "toCity.province.country.chnName") + "/"
                                + BeanHelper.getBeanPropertyValue(data, "toCity.province.chnName") + "/"
                                + BeanHelper.getBeanPropertyValue(data, "toCity.chnName"));
                    if (isEnglish(request))
                        row.add(BeanHelper.getBeanPropertyValue(data, "travellingMode.engShortDescription"));
                    else
                        row.add(BeanHelper.getBeanPropertyValue(data, "travellingMode.chnShortDescription"));
                    TravelApplication ta = (TravelApplication) data;
                    if (ta.getRequestDate() != null)
                        row.add(ActionUtils.getDisplayDateFromDate(ta.getRequestDate()));
                    else
                        row.add("");
                    if (isEnglish(request))
                        row.add(BeanHelper.getBeanPropertyValue(data, "status.engShortDescription"));
                    else
                        row.add(BeanHelper.getBeanPropertyValue(data, "status.chnShortDescription"));
                }
            });
            return new ActionForward("download/" + index + "/" + URLEncoder.encode(fileName, "UTF-8") + '.' + suffix, true);
        }

        if (queryForm.isFirstInit()) {
            queryForm.init(tam.getTravelApplicationListCount(conditions));
        } else {
            queryForm.init();
        }

        List result = tam.getTravelApplicationList(conditions, queryForm.getPageNoAsInt(), queryForm.getPageSizeAsInt(), TravelApplicationQueryOrder
                .getEnum(queryForm.getOrder()), queryForm.isDescend());

        this.putEnumListToRequest(request);
        request.setAttribute("x_postfix", "_self");
        request.setAttribute("X_RESULTLIST", result);

        return mapping.findForward("page");

    }

    public ActionForward report(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        ExpenseManager em = ServiceLocator.getExpenseManager(request);
        ExpenseQueryForm queryForm = (ExpenseQueryForm) form;

        List siteList=this.getGrantedSiteDeparmentList(request);
        request.setAttribute("x_siteList",siteList);
        
        if (StringUtils.isEmpty(queryForm.getSite_id())) {
            
            Site firstSite=(Site) siteList.get(0);
            queryForm.setSite_id(firstSite.getId().toString());
            
            //Department firstDepartment=(Department) firstSite.getDepartments().get(0);
            //queryForm.setDepartment_id(firstDepartment.getId().toString());

        }
        
        Map conditions = constructQueryMap(queryForm, request);
        
        String exportType = queryForm.getExportType();
        if (StringUtils.isNotEmpty(exportType)) {
            List data = em.getExpenseList(conditions, 0, -1, ExpenseQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend());
            if ("pdf".equalsIgnoreCase(exportType)) {
                Integer siteId=ActionUtils.parseInt(queryForm.getSite_id());
                exportPDF(data, siteId, "expense", request, response);
                return null;
            }
            int index = SessionTempFile.createNewTempFile(request);
            String fileName = "expense";
            String suffix = ExportUtil.export(exportType, data, request, new FileOutputStream(SessionTempFile.getTempFile(index, request)), new Exportable() {

                public void exportHead(List row, HttpServletRequest request) throws Exception {
                    MessageResources messages = getResources(request);
                    row.add(messages.getMessage(getLocale(request), "expense.reportExpense.code"));
                    row.add(messages.getMessage(getLocale(request), "expense.reportExpense.category"));
                    row.add(messages.getMessage(getLocale(request), "expense.reportExpense.requestor"));
                    row.add(messages.getMessage(getLocale(request), "expense.reportExpense.department"));
                    row.add(messages.getMessage(getLocale(request), "expense.reportExpense.totalAmount"));
                    row.add(messages.getMessage(getLocale(request), "expense.reportExpense.createDate"));
                    row.add(messages.getMessage(getLocale(request), "expense.reportExpense.status"));
                    row.add(messages.getMessage(getLocale(request), "expense.reportExpense.approvalDuration"));
                }

                public void exportRow(List row, Object data, HttpServletRequest request) throws Exception {
                    row.add(BeanHelper.getBeanPropertyValue(data, "id"));
                    row.add(BeanHelper.getBeanPropertyValue(data, "expenseCategory.description"));
                    row.add(BeanHelper.getBeanPropertyValue(data, "requestor.name"));
                    row.add(BeanHelper.getBeanPropertyValue(data, "department.name"));
                    row.add(BeanHelper.getBeanPropertyValue(data, "amount"));
                    Expense expense = (Expense) data;
                    if (expense.getCreateDate() != null)
                        row.add(ActionUtils.getDisplayDateFromDate(expense.getCreateDate()));
                    else
                        row.add("");
                    if (isEnglish(request))
                        row.add(BeanHelper.getBeanPropertyValue(data, "status.engShortDescription"));
                    else
                        row.add(BeanHelper.getBeanPropertyValue(data, "status.chnShortDescription"));
                    row.add(BeanHelper.getBeanPropertyValue(data, "approveDurationDay"));
                }
            });
            return new ActionForward("download/" + index + "/" + URLEncoder.encode(fileName, "UTF-8") + '.' + suffix, true);
        }

        
        if (queryForm.isFirstInit()) {
            queryForm.init(em.getExpenseListCount(conditions));
        } else {
            queryForm.init();
        }

        List result = em.getExpenseList(conditions, queryForm.getPageNoAsInt(), queryForm.getPageSizeAsInt(), ExpenseQueryOrder.getEnum(queryForm.getOrder()),
                queryForm.isDescend());

        this.putEnumListToRequest(request);
        
        request.setAttribute("X_RESULTLIST", result);
        return mapping.findForward("page");
    }
    
    private void checkCanExportPDF(Expense expense) {
        if (expense.getStatus().equals(ExpenseStatus.DRAFT)) 
            throw new ActionException("expense.pdf.notAllowed");
    }
    
    public ActionForward exportDetailPDF_self(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Expense expense = this.getExpenseFromRequest(request);
        this.checkCanExportPDF(expense);
        this.checkRequestorSelf(expense,request);
        request.setAttribute("x_postfix", "_self");
        exportDetailPDF(expense,request,true,response);
        return null;
    }
    
    public ActionForward exportDetailPDF_finalConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Expense expense = this.getExpenseFromRequest(request);
        this.checkConfirmedOrRejected(expense);
        this.checkSite(expense.getDepartment().getSite(), request);
        request.setAttribute("x_postfix","_finalConfirm");
        exportDetailPDF(expense,request,true,response);
        return null;
    }

    public ActionForward exportDetailPDF_finalClaim(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Expense expense = this.getExpenseFromRequest(request);
        this.checkClaimedOrRejected(expense);
        this.checkSite(expense.getDepartment().getSite(), request);
        request.setAttribute("x_postfix","_finalClaim");
        exportDetailPDF(expense,request,true,response);
        return null;
    }

    public ActionForward exportDetailPDF_other(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Expense expense = this.getExpenseFromRequest(request);
        this.checkCanExportPDF(expense);
        this.checkCreatorSelf(expense,request);
        this.checkDepartment(expense.getDepartment(), request);
        request.setAttribute("x_postfix", "_other");
        exportDetailPDF(expense,request,false,response);
        return null;
    }
    
    private void exportDetailPDF(Expense ex,HttpServletRequest request,boolean self,HttpServletResponse response) throws Exception {
        getViewContent(ex,request);
        
        MessageResources messages = getResources(request);
        Locale locale = getLocale(request);
        
        PDFReport report = PDFReport.createReport(
                ex.getDepartment().getSite().getId(), 
                self ? "expense.view.title" : "expense.view.title.other",
                request,
                messages,
                locale);
        Document document = report.getDocument();

        Font subTitleFont=PDFReport.getSubTitleFont(); 

        Paragraph p = null;
        
        Color tColor = new Color(0, 0x33, 0x66);
        
        PdfPTable table = PDFReport.createTable(new float[] { 1, 2 , 1 , 2 }, 100, 0);
        report.addCell(table, "expense.site", tColor, true);
        report.addCell(table, ex.getDepartment().getSite().getName(), 3);
        report.addCell(table, "expense.department", tColor, true);
        report.addCell(table, ex.getDepartment().getName(), 3);
        report.addCell(table, "expense.requestor.id", tColor, true);
        report.addCell(table, ex.getRequestor().getName());
        report.addCell(table, "expense.requestdate", tColor, true);
        report.addCell(table, ex.getRequestDate());
        report.addCell(table, "expense.creator.id", tColor, true);
        report.addCell(table, ex.getCreator().getName());
        report.addCell(table, "expense.creatDate", tColor, true);
        report.addCell(table, ex.getCreateDate());
        document.add(table);
        
        report.addSplitLine();
        
        table = PDFReport.createTable(new float[] { 1, 2 , 1 , 2 }, 100, 0);
        report.addCell(table, "expense.id", tColor, true);
        Font idFont = PDFReport.getFont(Font.BOLD, "#6600ff");
        report.addCell(table, ex.getId(), 3 ,idFont);
        report.addCell(table, "expense.title", tColor, true);
        report.addCell(table, ex.getTitle(),3);
        report.addCell(table, "expense.description", tColor, true);
        report.addCell(table, ex.getDescription(),3);

        report.addCell(table, "expense.expenseCategory", tColor, true);
        if (ex.getExpenseCategory().getType().equals(ExpenseType.TRAVEL) && ex.getTravelApplication()!=null) {
            report.addCell(table,ex.getExpenseCategory().getDescription());
            report.addCell(table, "expense.travelApplication.id", tColor, true);
            report.addCell(table, ex.getTravelApplication().getId());
        } else {
            report.addCell(table,ex.getExpenseCategory().getDescription(),3);
        }
        report.addCell(table, "expense.amount", tColor, true);
        NumberFormat numFormat = NumberFormat.getInstance();
        numFormat.setMaximumFractionDigits(2);
        numFormat.setMinimumFractionDigits(2);
        report.addCell(table, numFormat.format(ex.getBaseAmount()));
        report.addCell(table, "expense.baseCurrency", tColor, true);
        report.addCell(table, ex.getDepartment().getSite().getBaseCurrency().getName());
        report.addCell(table, "expense.status", tColor, true);
        report.addCell(table, ex.getStatus());
        report.addCell(table, "expense.isRecharge", tColor, true);
        report.addCell(table, ex.getIsRecharge());
        document.add(table);

        report.addSplitLine();

        /*
        List attachList=(List)request.getAttribute("x_attachmentList");
        
        report.addAttachmentListTable(
                attachList,
                "expenseAttachment.list.title",
                "expenseAttachment.title",
                "expenseAttachment.fileName",
                "expenseAttachment.uploadDate");
        */
        
        p = new Paragraph(
                new StringBuffer().append(messages.getMessage(locale, "expense.row.title")).append("\n").toString(), 
                subTitleFont);
        p.setAlignment(Element.ALIGN_LEFT);
        document.add(p);
        report.addSpaceLine(3);
        table = PDFReport.createTable(new float[] { 1.5f , 1 , 1.5f , 1 , 4 }, 100, 0);
        Color defaultBackgroundColor = table.getDefaultCell().backgroundColor();
        report.addCell(table, "expense.expenseCurrency", tColor, true);
        report.addCell(table, ex.getExpenseCurrency().getName());
        report.addCell(table, "expense.exchangeRate", tColor, true);
        report.addCell(table, ex.getExchangeRate(),2);
        document.add(table);
        report.addSpaceLine(3);
        
        List subCategoryList=(List)request.getAttribute("x_subCategoryList");
        float[] tableWidth=new float[2+subCategoryList.size()];
        for (int i = 0; i < tableWidth.length; i++) tableWidth[i]=1;
        table = PDFReport.createTable(tableWidth, 100, 0.5f);
        PdfPCell defaultCell = table.getDefaultCell();
        defaultCell.setBackgroundColor(new Color(0x99,0x99,0xff));
        defaultCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        Font headFont = PDFReport.getFont(Font.BOLD, Color.BLACK);
        report.addCell(table, "expense.row.date", headFont, true);
        for (Iterator itor = subCategoryList.iterator(); itor.hasNext();) {
            ExpenseSubCategory subCategory = (ExpenseSubCategory) itor.next();
            report.addCell(table, subCategory.getDescription(), headFont);
        }
        report.addCell(table, "expense.row.subTotal", headFont, true);
        if (request.getAttribute("x_expenseLimitRow")!=null) {
            if (!ex.getRequestor().equals(request.getSession().getAttribute(BasicAction.LOGIN_USER_KEY))) {
                Font limitFont=PDFReport.getFont(10,Font.BOLD,Color.WHITE);
                defaultCell.setBackgroundColor(new Color(0x00,0x54,0x96));
                defaultCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                report.addCell(table, "expense.finalConfirm.limit", limitFont, true);
                defaultCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                ExpenseRow limitRow = (ExpenseRow) request.getAttribute("x_expenseLimitRow");
                List cellList=limitRow.getExpenseCellList();
                for (Iterator itor = cellList.iterator(); itor.hasNext();) {
                    ExpenseCell cell = (ExpenseCell) itor.next();
                    report.addCell(table, cell.getAmount(), limitFont);
                }
                report.addCell(table, "", limitFont);
            }
        }
        defaultCell.setBackgroundColor(defaultBackgroundColor);
        List rowList=(List)request.getAttribute("x_expenseRowList");
        for (Iterator itor = rowList.iterator(); itor.hasNext();) {
            ExpenseRow row = (ExpenseRow) itor.next();
            PdfPTable rowTable = PDFReport.createTable(tableWidth, 100, 0.5f);
            PdfPCell rowCell=rowTable.getDefaultCell();
            rowCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            report.addCell(rowTable, row.getDate());
            rowCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            List cellList=row.getExpenseCellList();
            for (Iterator iter = cellList.iterator(); iter.hasNext();) {
                ExpenseCell cell = (ExpenseCell) iter.next();
                if (cell.getAmount().equals(new BigDecimal("0.00"))) {
                    report.addCell(rowTable, "");
                }else{
                    report.addCell(rowTable, cell.getAmount());
                }
            }
            report.addCell(rowTable, row.getRowSubTotal());
            rowCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            report.addCell(rowTable, "expense.row.description",true);
            rowCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            for (Iterator iter = cellList.iterator(); iter.hasNext();) {
                ExpenseCell cell = (ExpenseCell) iter.next();
                report.addCell(rowTable, cell.getDescription());
            }
            report.addCell(rowTable, "");
            PDFReport.AddTableToTable(table,rowTable,tableWidth.length);
        }
        
        defaultCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        report.addCell(table, "expense.row.total",  headFont ,true);
        ExpenseRow sumRow = (ExpenseRow) request.getAttribute("x_expenseSumRow");
        List cellList=sumRow.getExpenseCellList();
        defaultCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        for (Iterator itor = cellList.iterator(); itor.hasNext();) {
            ExpenseCell cell = (ExpenseCell) itor.next();
            if (cell.getAmount().equals(new BigDecimal("0.00"))) {
                report.addCell(table, "");
            }else{
                report.addCell(table, cell.getAmount());
            }
        }
        report.addCell(table,sumRow.getRowSubTotal());
        report.addCell(table,"");

        document.add(table);
        
        if (request.getAttribute("x_confirmedAmount")!=null) {
            table = PDFReport.createTable(new float[] { 1, 5 }, 100, 0);
            defaultCell = table.getDefaultCell();
            report.addCell(table, "expense.finalConfirm.confirmedAmount",tColor,  true);
            report.addCell(table, ex.getConfirmedAmount());
            document.add(table);
        }
        
        if (ExpenseStatus.APPROVED.equals(ex.getStatus()) 
                || ExpenseStatus.CONFIRMED.equals(ex.getStatus()) 
                || ExpenseStatus.CLAIMED.equals(ex.getStatus())) {
            report.addSpaceLine(3);
            report.addSplitLine();
            Paragraph p2 = new Paragraph(new StringBuffer().append(messages.getMessage(locale, "approveRequest.approveList.title")).append("\n\n").toString(), PDFReport.getSubTitleFont());
            p2.setAlignment(Element.ALIGN_LEFT);            
            report.getDocument().add(p2);
            
            table = PDFReport.createTable(new float[] { 15, 150, 100, 100, 534 }, 100, 0.5f);   
            ExpenseApproveRequestManager earm = ServiceLocator.getExpenseApproveRequestManager(request);
            List list = earm.getExpenseApproveRequestListByApproveRequestId(ex.getApproveRequestId());
            defaultCell = table.getDefaultCell();
            defaultCell.setBackgroundColor(new Color(0x99,0x99,0xff));
            defaultCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            report.addCell(table, "#", headFont, false);
            report.addCell(table, "approveRequest.approveList.approver", headFont, true);
            report.addCell(table, "approveRequest.approveList.status", headFont, true);
            report.addCell(table, "approveRequest.approveList.date", headFont, true);
            report.addCell(table, "approveRequest.approveList.comment", headFont, true);
            defaultCell.setBackgroundColor(defaultBackgroundColor);
            for (int i0 = 0; i0 < list.size(); i0++) {
                ExpenseApproveRequest ear  = (ExpenseApproveRequest)list.get(i0);
                defaultCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                report.addCell(table, (i0 + 1) + "");
                defaultCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                report.addCell(table, ear.getActualApprover().getName());
                defaultCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                report.addCell(table, ear.getStatus());
                defaultCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                report.addCell(table, ear.getApproveDate());
                defaultCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                report.addCell(table, ear.getComment());
            }
            document.add(table);
        }
       
        report.addRechargeListTable(ex, ex.getAmount(), (Collection) request.getAttribute("X_RECHARGELIST"));
        report.output("expense", response);
    }
    

    
    private void exportPDF(List data, Integer siteId, String filename, HttpServletRequest request, HttpServletResponse response) throws DocumentException, IOException {

        MessageResources messages = getResources(request);
        Locale locale = getLocale(request);
        PDFReport report = PDFReport.createReport(siteId, "expense.report.title", request, messages, locale);
        Document document = report.getDocument();

        PdfPTable table = PDFReport.createTable(new float[] { 6, 4 , 4 , 10 , 5 , 4, 4 , 3 }, 100, 0.5f);
        table.setHeaderRows(1);
        PdfPCell defaultCell = table.getDefaultCell();
        Color defaultBackgroundColor = defaultCell.backgroundColor();

        table.setWidthPercentage(100);
        defaultCell.setNoWrap(true);
        defaultCell.setMinimumHeight(16);

        defaultCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        defaultCell.setBackgroundColor(new Color(0x99, 0x99, 0xff));
        
        Font headFont = PDFReport.getFont(Font.BOLD, Color.BLACK);
        report.addCell(table, "expense.reportExpense.code", headFont, true);
        report.addCell(table, "expense.reportExpense.category", headFont, true);
        report.addCell(table, "expense.reportExpense.requestor", headFont, true);
        report.addCell(table, "expense.reportExpense.department", headFont, true);
        report.addCell(table, "expense.reportExpense.totalAmount", headFont, true);
        report.addCell(table, "expense.reportExpense.createDate", headFont, true);
        report.addCell(table, "expense.reportExpense.status", headFont, true);
        report.addCell(table, "expense.reportExpense.pdf.approvalDuration", headFont, true);
        
        defaultCell.setBackgroundColor(defaultBackgroundColor);
        
        for (Iterator itor = data.iterator(); itor.hasNext();) {
            Expense ex = (Expense) itor.next();
            report.addCell(table, ex.getId());
            report.addCell(table, ex.getExpenseCategory().getDescription());
            report.addCell(table, ex.getRequestor().getName());
            report.addCell(table, ex.getDepartment().getName());
            defaultCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            report.addCell(table, ex.getAmount());
            defaultCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            report.addCell(table, ex.getCreateDate());
            report.addCell(table, ex.getStatus());
            report.addCell(table, ex.getApproveDurationDay());
        }
        
        document.add(table);
        
        report.output(filename, response);
        
    }

    private Map constructQueryMap(ExpenseQueryForm queryForm, HttpServletRequest request) {
        Map conditions = new HashMap();

        String id = queryForm.getId();
        if (id != null && id.trim().length() != 0) {
            conditions.put(ExpenseQueryCondition.ID_LIKE, id);
        }

        String requestor = queryForm.getRequestor();
        if (requestor != null && requestor.trim().length() != 0) {
            conditions.put(ExpenseQueryCondition.REQUESTOR_LK, requestor);
        }
        
        BigDecimal amountFrom=ActionUtils.parseBigDecimal(queryForm.getAmountFrom());
        if (amountFrom!=null) {
            conditions.put(ExpenseQueryCondition.AMOUNT_GT,amountFrom);
        }
        
        BigDecimal amountTo=ActionUtils.parseBigDecimal(queryForm.getAmountTo());
        if (amountFrom!=null) {
            conditions.put(ExpenseQueryCondition.AMOUNT_LT,amountTo);
        }
        
        if (queryForm.getRequestDateFrom()!=null && queryForm.getRequestDateFrom().trim().length()!=0) {
            Date requestDateFrom=ActionUtils.getDateFromDisplayDate(queryForm.getRequestDateFrom());
            if (requestDateFrom!=null) {
                conditions.put(ExpenseQueryCondition.REQUESTDATE_GT,requestDateFrom);
            }
        }
        
        if (queryForm.getRequestDateTo()!=null && queryForm.getRequestDateTo().trim().length()!=0) {
            Date requestDateTo=ActionUtils.getDateFromDisplayDate(queryForm.getRequestDateTo());
            if (requestDateTo!=null) {
                conditions.put(ExpenseQueryCondition.REQUESTDATE_LT,requestDateTo);
            }
        }
        
        if (queryForm.getCreateDateFrom()!=null && queryForm.getCreateDateFrom().trim().length()!=0) {
            Date createDateFrom=ActionUtils.getDateFromDisplayDate(queryForm.getCreateDateFrom());
            if (createDateFrom!=null) {
                conditions.put(ExpenseQueryCondition.CREATEDATE_GE,createDateFrom);
            }
        }
        
        if (queryForm.getCreateDateTo()!=null && queryForm.getCreateDateTo().trim().length()!=0) {
            Date createDateTo=ActionUtils.getDateFromDisplayDate(queryForm.getCreateDateTo());
            if (createDateTo!=null) {
                conditions.put(ExpenseQueryCondition.CREATEDATE_LE,createDateTo);
            }
        }
        String travelApplication_id = queryForm.getTravelApplication_id();
        if (travelApplication_id != null && travelApplication_id.trim().length() != 0) {
            conditions.put(ExpenseQueryCondition.TRAVELAPPLICATION_ID_EQ, travelApplication_id);
        }
        Integer site_id=ActionUtils.parseInt(queryForm.getSite_id());
        if(site_id!=null)
        {
            conditions.put(ExpenseQueryCondition.SITE_ID_EQ, site_id);
        }
        
        Integer department_id = ActionUtils.parseInt(queryForm.getDepartment_id());
        if (department_id != null && department_id.intValue() != 0) {
            conditions.put(ExpenseQueryCondition.DEPARTMENT_ID_EQ, department_id);
        } else {
            List siteList=this.getGrantedSiteDeparmentList(request);
            for (int i0 = 0; i0 < siteList.size(); i0++) {
                Site site = (Site)siteList.get(i0);
                if (site.getId().equals(site_id)) {
                    Object[] params = new Object[site.getDepartments().size()];                    
                    for (int j0 = 0; j0 < site.getDepartments().size(); j0++) {
                        params[j0] = ((Department)site.getDepartments().get(j0)).getId();
                    }
                    conditions.put(ExpenseQueryCondition.DEPARTMENT_ID_IN, params);
                }
            }
        }
        
        Integer expenseCategory_id = ActionUtils.parseInt(queryForm.getExpenseCategory_id());
        if (expenseCategory_id != null) {
            conditions.put(ExpenseQueryCondition.EXPENSECATEGORY_ID_EQ, expenseCategory_id);
        }
        String expenseCurrency_code = queryForm.getExpenseCurrency_code();
        if (expenseCurrency_code != null && expenseCurrency_code.trim().length() != 0) {
            conditions.put(ExpenseQueryCondition.EXPENSECURRENCY_CODE_EQ, expenseCurrency_code);
        }
        String baseCurrency_code = queryForm.getBaseCurrency_code();
        if (baseCurrency_code != null && baseCurrency_code.trim().length() != 0) {
            conditions.put(ExpenseQueryCondition.BASECURRENCY_CODE_EQ, baseCurrency_code);
        }
        Integer requestor_id = ActionUtils.parseInt(queryForm.getRequestor_id());
        if (requestor_id != null) {
            conditions.put(ExpenseQueryCondition.REQUESTOR_ID_EQ, requestor_id);
        }
        Integer creator_id = ActionUtils.parseInt(queryForm.getCreator_id());
        if (creator_id != null) {
            conditions.put(ExpenseQueryCondition.CREATOR_ID_EQ, creator_id);
        }

        /* property */
        String title = queryForm.getTitle();
        if (title != null && title.trim().length() != 0) {
            conditions.put(ExpenseQueryCondition.TITLE_LIKE, title);
        }
        String description = queryForm.getDescription();
        if (description != null && description.trim().length() != 0) {
            conditions.put(ExpenseQueryCondition.DESCRIPTION_LIKE, description);
        }
        String status = queryForm.getStatus();
        if (status != null && status.trim().length() != 0) {
            conditions.put(ExpenseQueryCondition.STATUS_EQ, status);
        }
        String amount = queryForm.getAmount();
        if (amount != null && amount.trim().length() != 0) {
            conditions.put(ExpenseQueryCondition.AMOUNT_EQ, amount);
        }
        String exchangeRate = queryForm.getExchangeRate();
        if (exchangeRate != null && exchangeRate.trim().length() != 0) {
            conditions.put(ExpenseQueryCondition.EXCHANGERATE_EQ, exchangeRate);
        }
        String requestDate = queryForm.getRequestDate();
        if (requestDate != null && requestDate.trim().length() != 0) {
            conditions.put(ExpenseQueryCondition.REQUESTDATE_EQ, requestDate);
        }
        String crateDate = queryForm.getCrateDate();
        if (crateDate != null && crateDate.trim().length() != 0) {
            conditions.put(ExpenseQueryCondition.CRATEDATE_EQ, crateDate);
        }
        String isRecharge = queryForm.getIsRecharge();
        if (isRecharge != null && isRecharge.trim().length() != 0) {
            conditions.put(ExpenseQueryCondition.ISRECHARGE_EQ, isRecharge);
        }
        Integer approveRequestId = ActionUtils.parseInt(queryForm.getApproveRequestId());
        if (approveRequestId != null) {
            conditions.put(ExpenseQueryCondition.APPROVEREQUESTID_EQ, approveRequestId);
        }
        String exportStatus = queryForm.getExportStatus();
        if (exportStatus != null && exportStatus.trim().length() != 0) {
            conditions.put(ExpenseQueryCondition.EXPORTSTATUS_EQ, exportStatus);
        }
        return conditions;
    }

    /**
     * 编辑Expense
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Expense expense = getExpenseFromRequest(request);
        request.setAttribute("x_expense", expense);

        final boolean isSelf = this.isGlobal(request);

        if (isSelf) {
            this.checkRequestorIsSelf(expense,request);
            request.setAttribute("x_postfix", "_self");
        } else {
            this.checkCreatorIsSelf(expense,request);
            this.checkDepartment(expense.getDepartment(), request);
            request.setAttribute("x_postfix", "_other");
        }
        this.checkDraft(expense,"edit");
        
        BeanForm expenseForm = (BeanForm) form;
        if (!isBack(request)) {
            expenseForm.populateToForm(expense);
        }
        
        putExpenseCurrencyListToRequest(expense,request);
        putProjectCodeToRequest(expense.getDepartment().getSite(), request);
        putExpenseSubCategoryListToRequest(expense,request);
        putEnumListToRequest(request);
        putExpenseAttachmentToRequest(expense, request);
        putExpenseRowCellListToRequest(expense,request,false,false);
        putApproveListToRequest(expense,request);
        putExpenseRechargeListToRequest(expense,request,expenseForm,false);
        this.putCanViewExpenseBudgetAmount(expense.getYearlyBudget(),request);

        return mapping.findForward("page");
    }
    
    public ActionForward copy(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        final boolean isSelf = this.isGlobal(request);
        Expense expense = getExpenseFromRequest(request);
        ExpenseManager em = ServiceLocator.getExpenseManager(request);
        
        Expense copyExpense = new Expense();
        copyExpense.setCreator(this.getCurrentUser(request));
        if (isSelf) {
            copyExpense.setRequestor(this.getCurrentUser(request));
        } else {
            copyExpense.setRequestor(expense.getRequestor());
            checkDepartment(expense.getDepartment(),request);
        }
        
        copyExpense = em.copyExpense(copyExpense,expense);
        return getEditForwardFor(copyExpense,isSelf);
    }
    
    private void checkRequestorIsSelf(Expense expense,HttpServletRequest request) {
        if (!expense.getRequestor().equals(this.getCurrentUser(request))) {
            throw new ActionException("expense.error.requestor.notSelf");
        }
    }
    
    private void checkCreatorIsSelf(Expense expense,HttpServletRequest request) {
        if (!expense.getCreator().equals(this.getCurrentUser(request))) {
            throw new ActionException("expense.error.creator.notSelf");
        }
    }
    
    /*
     * Changed by nicebean, rejected expense cannot edit & delete, 2007-12-21
     */
    private void checkDraft(Expense expense,String checkMethod) {
        if (!expense.getStatus().equals(ExpenseStatus.DRAFT)) {
            throw new ActionException("expense.error."+checkMethod+"NotDraft");
        }
    }
    
    /**
     * 新增My Expense
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward newObject_self(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        BeanForm expenseForm = (BeanForm) form;
        Expense expense = new Expense();
        expense.setCreateDate(new Date());
        expense.setCreator(this.getCurrentUser(request));
        expense.setRequestor(this.getCurrentUser(request));
        expense.setStatus(ExpenseStatus.DRAFT);
        expense.setTravelApplication(getTravelApplicationFromRequest(request));
        if (!isBack(request)) {
            expenseForm.populateToForm(expense);
        }
        request.setAttribute("x_newExpense", expense);
        putEnabledUserSiteListToRequest(request);
        putCurrentDateToRequest(request);
        
        return mapping.findForward("page");
    }

    /**
     * 新增Delegate Expense
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward newObject_other(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        BeanForm expenseForm = (BeanForm) form;
        Expense expense = new Expense();
        expense.setCreateDate(new Date());
        expense.setCreator(this.getCurrentUser(request));
        expense.setStatus(ExpenseStatus.DRAFT);

        if (!isBack(request)) {
            // expenseForm.populateToForm(expense);
        }

        ExpenseOtherPageManager em = new ExpenseOtherPageManager(expenseForm, this.getCurrentUser(request), this.getFunction(request), request);
        em.process();

        request.setAttribute("x_newExpense", expense);
        putCurrentDateToRequest(request);
        return mapping.findForward("page");
    }


    /**
     * 删除Expense
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Expense expense = this.getExpenseFromRequest(request);
        final boolean isSelf = this.isGlobal(request);
        if (isSelf) {
            this.checkRequestorIsSelf(expense,request);
        } else {
            this.checkCreatorIsSelf(expense,request);
            this.checkDepartment(expense.getDepartment(),request);
        }
        this.checkDraft(expense,"delete");
        
        ExpenseManager em=ServiceLocator.getExpenseManager(request);
        
        em.removeExpense(expense,this.getCurrentUser(request));
        
        this.postGlobalMessage("expense.delete.success",request.getSession());
        return getListForward(isSelf);
    }
        
    private TravelApplication getTravelApplicationFromRequest(HttpServletRequest request) {
        String id = request.getParameter("ta_id");
        if (id != null) {
            TravelApplicationManager tam = ServiceLocator.getTravelApplicationManager(request);
            return tam.getTravelApplication(id);
        } else {
            return null;
        }
    }

    /**
     * 查看审批人
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward viewapprover(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)  throws Exception {
        return update(mapping, form, request, response, true);
    }
    
    private void putEnabledUserSiteListToRequest(HttpServletRequest request) {
        UserManager um = ServiceLocator.getUserManager(request);
        List userSiteList = um.getEnabledUserSiteListWithDepartmentsAndExpenseCategory(this.getCurrentUser(request));
        request.setAttribute("x_userSiteList", userSiteList);
    }
    
    private void putCurrentDateToRequest(HttpServletRequest request) {
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        request.setAttribute("x_currentDate", format.format(new Date()));
    }

    private boolean isDraft(HttpServletRequest request) {
        String s = request.getParameter("draft");
        if (s != null && s.equals("true"))
            return true;
        return false;
    }

    private List executeFlow(Expense expense, List expenseRowList, HttpServletRequest request) {
        List eList = ServiceLocator.getExpenseManager(request).viewApprover(expense, expenseRowList);
        return eList;
    }
    
    private ActionForward getEditForwardFor(Expense expense, boolean isSelf) {

        ActionForward forward;
        if (isSelf)
            forward = new ActionForward("editExpense_self.do?id="+expense.getId());
        else
            forward = new ActionForward("editExpense_other.do?id="+expense.getId());
        forward.setRedirect(true);
        return forward;
    }
    
    private ActionForward getViewForwardFor(Expense expense, boolean isSelf) {

        ActionForward forward;
        if (isSelf)
            forward = new ActionForward("viewExpense_self.do?id="+expense.getId());
        else
            forward = new ActionForward("viewExpense_other.do?id="+expense.getId());
        forward.setRedirect(true);
        return forward;
    }
    
    private ActionForward getListForward(boolean isSelf) {
        if (isSelf)
            return new ActionForward("listExpense_self.do",true);
        else
            return new ActionForward("listExpense_other.do",true);
    }
    
    /**
     * 更新Expense
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

    private ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean viewApprover) throws Exception {
        Expense expense = getExpenseFromRequest(request);
        
        final boolean isSelf = this.isGlobal(request);
        if (isSelf) {
            this.checkRequestorIsSelf(expense,request);
        } else {
            this.checkCreatorIsSelf(expense,request);
            this.checkDepartment(expense.getDepartment(),request);
        }
        this.checkDraft(expense,"edit");
        
        BeanForm expenseForm = (BeanForm) form;
        expenseForm.setBeanLoader(ServiceLocator.getBeanLoader(request));
        expenseForm.populateToBean(expense,request);
        
        List expenseRowList = getExpenseRowCellFromRequest(expense,request);
        
        List rechargeList = getRechargeInfoFromRequest(expense, request);
        
        if (viewApprover) {
            try {
                request.setAttribute("X_APPROVELIST", executeFlow(expense, expenseRowList, request));
            } catch (ActionException e) {
                ActionMessage message = new ActionMessage(e.getKey(), e.getValues());
                ActionMessages messages = new ActionMessages();
                messages.add(null, message);
                saveErrors(request, messages);
            }
            return mapping.findForward("page");
        } else {             
            ExpenseManager expenseManager = ServiceLocator.getExpenseManager(request);
            expenseManager.updateExpense(expense,expenseRowList,rechargeList, isDraft(request),this.getCurrentUser(request));
            if (!isDraft(request)) {
                this.postGlobalMessage("expense.submit.success",request.getSession());
            } else {
                this.postGlobalMessage("expense.update.success",request.getSession());
            }
            if(isDraft(request))
                return getEditForwardFor(expense, isSelf);
            else
                return getViewForwardFor(expense, isSelf);
        }
    }
    
    /**
     * 插入My Expense
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward insert_self(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        DepartmentManager dm = ServiceLocator.getDepartmentManager(request);

        BeanForm expenseForm = (BeanForm) form;
        Expense expense = new Expense();
        expenseForm.setBeanLoader(ServiceLocator.getBeanLoader(request));
        expenseForm.populateToBean(expense, request);
        
        this.checkExpenseCategory(expense.getExpenseCategory(),request);

        expense.setCreateDate(new Date());
        expense.setCreator(this.getCurrentUser(request));
        expense.setRequestor(this.getCurrentUser(request));
        expense.setStatus(ExpenseStatus.DRAFT);
        if (!ExpenseType.TRAVEL.equals(expense.getExpenseCategory().getType())) {
            expense.setTravelApplication(null);
        }
        Department department = dm.getDepartment(expense.getDepartment().getId());
        expense.setBaseCurrency(department.getSite().getBaseCurrency());
        expense.setExpenseCurrency(expense.getBaseCurrency());
        expense.setAmount(new BigDecimal(0d));
        expense.setExchangeRate(new BigDecimal(1d));
        expense.setIsRecharge(YesNo.NO);
        expense.setExportStatus(ExportStatus.UNEXPORTED);
        expense.setConfirmedAmount(new BigDecimal(0d));
        ExpenseManager expenseManager = ServiceLocator.getExpenseManager(request);
        expenseManager.insertExpense(expense);

        ActionForward forward = new ActionForward("editExpense_self.do?id=" + expense.getId());
        forward.setRedirect(true);
        return forward;
    }

    private void checkExpenseCategory(ExpenseCategory expenseCategory,HttpServletRequest request) {
        ExpenseSubCategoryManager em = ServiceLocator.getExpenseSubCategoryManager(request);
        List result= em.getEnabledChildrenOfExpenseCategory(expenseCategory.getId());
        if (result.size()==0)
            throw new BackToInputActionException("expense.error.expenseCategory.noSubCategory");
    }

    /**
     * 插入Delegate Expense
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward insert_other(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        DepartmentManager dm = ServiceLocator.getDepartmentManager(request);

        BeanForm expenseForm = (BeanForm) form;
        Expense expense = new Expense();
        expenseForm.setBeanLoader(ServiceLocator.getBeanLoader(request));
        expenseForm.populateToBean(expense, request);
        
        this.checkDepartment(expense.getDepartment(), request);
        this.checkExpenseCategory(expense.getExpenseCategory(),request);
        
        expense.setCreateDate(new Date());
        expense.setCreator(this.getCurrentUser(request));
        expense.setStatus(ExpenseStatus.DRAFT);
        if (!expense.getExpenseCategory().getType().equals(ExpenseType.TRAVEL)) {
            expense.setTravelApplication(null);
        }
        Department department = dm.getDepartment(expense.getDepartment().getId());
        expense.setBaseCurrency(department.getSite().getBaseCurrency());
        expense.setExpenseCurrency(expense.getBaseCurrency());
        expense.setAmount(new BigDecimal(0d));
        expense.setExchangeRate(new BigDecimal(1d));
        expense.setIsRecharge(YesNo.NO);
        expense.setExportStatus(ExportStatus.UNEXPORTED);
        expense.setConfirmedAmount(new BigDecimal(0d));
        
        ExpenseManager expenseManager = ServiceLocator.getExpenseManager(request);
        expenseManager.insertExpense(expense);

        ActionForward forward = new ActionForward("editExpense_other.do?id=" + expense.getId());
        forward.setRedirect(true);
        return forward;
    }

    
    public ActionForward selectRechargeCustomer(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return super.selectRechargeCustomer(mapping, form, request, response);
    }

    public ActionForward selectRechargeEntity(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return super.selectRechargeEntity(mapping, form, request, response);
    }

    public ActionForward selectRechargePerson(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return super.selectRechargePerson(mapping, form, request, response);
    }

    private void checkApproved(Expense ep) {
        if (!ep.getStatus().equals(ExpenseStatus.APPROVED))
            throw new ActionException("expense.finalConfirm.notApproved");
    }

    private void checkConfirmed(Expense ep) {
        if (!ep.getStatus().equals(ExpenseStatus.CONFIRMED))
            throw new ActionException("expense.finalClaim.notConfirmed");
    }

    /**
     * expense final confirm
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward finalConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Expense ep = this.getExpenseFromRequest(request);
        BeanForm beanForm=(BeanForm)this.getForm("/finalConfirmExpense_result",request);
        beanForm.populateToForm(ep);

        this.checkApproved(ep);
        this.checkSite(ep.getDepartment().getSite(), request);

        request.setAttribute("x_expense", ep);
        request.setAttribute("finalConfirm",Boolean.TRUE);

        putExpenseCurrencyListToRequest(ep,request);
        putProjectCodeToRequest(ep.getDepartment().getSite(), request);
        putExpenseSubCategoryListToRequest(ep,request);
        putEnumListToRequest(request);
        putExpenseAttachmentToRequest(ep, request);
        putExpenseRowCellListToRequest(ep, request, true, true);
        putApproveListToRequest(ep,request);
        putExpenseRechargeListToRequest(ep,request,beanForm,false);

        ExpenseSubCategoryManager escm = ServiceLocator.getExpenseSubCategoryManager(request);
        List escList = escm.getEnabledChildrenOfExpenseCategory(ep.getExpenseCategory().getId());
        request.setAttribute("x_escList", escList);

        if (!this.isBack(request)) {
            Map amounts = new HashMap();
            Map descriptions = new HashMap();
            beanForm.set("amounts",amounts);
            beanForm.set("descriptions", descriptions);

            ExpenseRow sumRow=(ExpenseRow) request.getAttribute("x_expenseSumRow");
            List sumCellList=sumRow.getExpenseCellList();
            for (Iterator iter = escList.iterator(),itor=sumCellList.iterator(); iter.hasNext()&&itor.hasNext();) {
                ExpenseSubCategory esc = (ExpenseSubCategory) iter.next();
                ExpenseCell ec=(ExpenseCell) itor.next();
                amounts.put(esc.getId().toString(),ec.getAmount().toString());
                //not fill sum description when final comfirm -- nicebean 07-01-12
                //descriptions.put(esc.getId().toString(),ec.getDescription());
            }
        }

        return mapping.findForward("page");
    }

    private boolean isReject(HttpServletRequest request) {
        return "true".equals(request.getParameter("isReject"));
    }

    /**
     * final confirm result
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward finalConfirmResult(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Expense ep = this.getExpenseFromRequest(request);
        this.checkApproved(ep);
        this.checkSite(ep.getDepartment().getSite(), request);

        if (isReject(request)) {
            ExpenseManager em = ServiceLocator.getExpenseManager(request);
            em.rejectExpenseByFinalConfirm(ep, this.getCurrentUser(request), "Rejected by expense final comfirm: " + request.getParameter("rejectComment"));
            //em.rejectExpense(ep, this.getCurrentUser(request), "Rejected by expense final comfirm");

            this.postGlobalMessage("expense.finalConfirm.reject.success", request.getSession());
            return getFinalConfirmViewForward(ep);
        }

        BeanForm beanForm=(BeanForm) form;
        beanForm.populateToBean(ep,request);

        List expenseRowList = getExpenseRowCellFromRequest(ep, request);
        List rechargeList = getRechargeInfoFromRequest(ep, request);

        ExpenseSubCategoryManager escm = ServiceLocator.getExpenseSubCategoryManager(request);
        List escList = escm.getEnabledChildrenOfExpenseCategory(ep.getExpenseCategory().getId());

        DynaValidatorForm theForm = (DynaValidatorForm) form;
        Map amounts = (Map) theForm.get("amounts");
        Map descriptions = (Map) theForm.get("descriptions");
        
        List ecList = new ArrayList();

        BigDecimal sumAmount=new BigDecimal(0d);
        for (Iterator iter = escList.iterator(); iter.hasNext();) {
            ExpenseSubCategory esc = (ExpenseSubCategory) iter.next();

            String id = esc.getId().toString();
            BigDecimal amount = getAsBigDecimal((String) amounts.get(id));
            String description = (String) descriptions.get(id);
            
            sumAmount=sumAmount.add(amount);

            ExpenseClaim ec = new ExpenseClaim();
            ec.setExpense(ep);
            ec.setExpenseSubCategory(esc);
            ec.setAmount(amount);
            ec.setDescription(description);
            //ec.setProjectCode(projectCode);

            ecList.add(ec);
        }
        
        FlowManager fm=ServiceLocator.getFlowManager(request);

        ep.setConfirmedAmount(sumAmount.setScale(2, BigDecimal.ROUND_HALF_UP));
        
        try{
            if(!fm.executeControlFlow(ep))
                throw new ActionException("expense.finalConfirm.notPass");
        } catch (NoAvailableFlowToExecuteException e) {
            throw new ActionException("flow.execute.notFlowFound");
        }

        ExpenseManager expenseManager = ServiceLocator.getExpenseManager(request);

        expenseManager.finalConfirm(ep, expenseRowList, rechargeList, ecList, this.getCurrentUser(request));

        EmailManager emailManager=ServiceLocator.getEmailManager(request);
        Map context=new HashMap();
        context.put("x_ep",ep);
        context.put("x_userName",ep.getCreator().getName());
        context.put("role", EmailManager.EMAIL_ROLE_CREATOR);
        emailManager.insertEmail(ep.getCreator().getPrimarySite(),ep.getCreator().getEmail(),"ExpenseFinalConfirm.vm",context);
        if(!ep.getCreator().equals(ep.getRequestor()))
        {
            context.put("x_userName",ep.getRequestor().getName());
            context.put("role", EmailManager.EMAIL_ROLE_REQUESTOR);
            emailManager.insertEmail(ep.getRequestor().getPrimarySite(),ep.getRequestor().getEmail(),"ExpenseFinalConfirm.vm",context);
        }
        
        this.postGlobalMessage("expense.finalConfirm.success", request.getSession());
        return getFinalConfirmViewForward(ep);
    }

    private ActionForward getFinalConfirmViewForward(Expense ep) {
        return new ActionForward("viewExpense_finalConfirm.do?id=" + ep.getId(), true);
    }

    /**
     * list expense for final confirm
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward listFinalConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List siteList=this.getAndCheckGrantedSiteList(request);
        request.setAttribute("x_siteList",siteList);
        
        ExpenseQueryForm queryForm = (ExpenseQueryForm) form;
        
        if (StringUtils.isEmpty(queryForm.getSite_id())) {
            Site firstSite=(Site) siteList.get(0);
            queryForm.setSite_id(firstSite.getId().toString());
        }

//        if (StringUtils.isEmpty(queryForm.getStatus())) {            //为什么要这样呢？想默认只查询approved状态的expense
//            queryForm.setStatus(ExpenseStatus.APPROVED.getEnumCode().toString());
//        }
        
        Map conditions = constructQueryMap(queryForm, request);
        
        conditions.put(ExpenseQueryCondition.STATUS_EQ3,
                new Object[]{ExpenseStatus.APPROVED,
                //ExpenseStatus.CONFIRMED,
                ExpenseStatus.CLAIMED});

        ExpenseQueryOrder queryOrder=ExpenseQueryOrder.getEnum(queryForm.getOrder());
        if(queryOrder==null) throw new RuntimeException("order not found!");

        ExpenseManager em = ServiceLocator.getExpenseManager(request);

        String exportType = queryForm.getExportType();
        if (StringUtils.isNotEmpty(exportType)) {
            List data = em.getExpenseList(conditions, 0, -1, queryOrder, queryForm.isDescend());

            int index = SessionTempFile.createNewTempFile(request);
            String fileName = "expenseFinalConfirm";
            String suffix = ExportUtil.export(exportType, data, request, new FileOutputStream(SessionTempFile.getTempFile(index, request)), new Exportable() {

                public void exportHead(List row, HttpServletRequest request) throws Exception {
                    MessageResources messages = getResources(request);
                    row.add(messages.getMessage(getLocale(request), "expense.listExpense.code"));
                    row.add(messages.getMessage(getLocale(request), "expense.listExpense.category"));
                    row.add(messages.getMessage(getLocale(request), "expense.listExpense.requestor"));
                    row.add(messages.getMessage(getLocale(request), "expense.listExpense.department"));
                    row.add(messages.getMessage(getLocale(request), "expense.listExpense.currency"));
                    row.add(messages.getMessage(getLocale(request), "expense.listExpense.totalAmount"));
                    row.add(messages.getMessage(getLocale(request), "expense.listExpense.createDate"));
                    row.add(messages.getMessage(getLocale(request), "expense.listExpense.confirmDate"));
                    row.add(messages.getMessage(getLocale(request), "expense.listExpense.status"));
                }

                public void exportRow(List row, Object data, HttpServletRequest request) throws Exception {
                    row.add(BeanHelper.getBeanPropertyValue(data, "id"));
                    row.add(BeanHelper.getBeanPropertyValue(data, "expenseCategory.description"));
                    row.add(BeanHelper.getBeanPropertyValue(data, "requestor.name"));
                    row.add(BeanHelper.getBeanPropertyValue(data, "department.name"));
                    row.add(BeanHelper.getBeanPropertyValue(data, "expenseCurrency.name"));
                    row.add(BeanHelper.getBeanPropertyValue(data, "amount"));
                    Expense expense = (Expense) data;
                    if (expense.getCreateDate() != null) {
                        row.add(ActionUtils.getDisplayDateFromDate(expense.getCreateDate()));
                    } else {
                        row.add("");
                    }
                    if (expense.getConfirmDate() != null) {
                        row.add(ActionUtils.getDisplayDateFromDate(expense.getConfirmDate()));
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
            queryForm.init(em.getExpenseListCount(conditions));
        } else {
            queryForm.init();
        }
        
        List result = em.getExpenseList(conditions, queryForm.getPageNoAsInt(), queryForm.getPageSizeAsInt(), queryOrder,
                queryForm.isDescend());
        request.setAttribute("X_RESULTLIST", result);        

        final List statusList=new ArrayList();
        statusList.add(ExpenseStatus.APPROVED);
        //statusList.add(ExpenseStatus.CONFIRMED);
        statusList.add(ExpenseStatus.CLAIMED);
        request.setAttribute("x_expenseStatusList", statusList);
        request.setAttribute("x_approved", ExpenseStatus.APPROVED);
        return mapping.findForward("page");
    }


    /**
     * list expense for final claim
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward listFinalClaim(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List siteList=this.getAndCheckGrantedSiteList(request);
        request.setAttribute("x_siteList",siteList);
        
        ExpenseQueryForm queryForm = (ExpenseQueryForm) form;
        
        if (StringUtils.isEmpty(queryForm.getSite_id())) {
            Site firstSite=(Site) siteList.get(0);
            queryForm.setSite_id(firstSite.getId().toString());
        }

        if(queryForm.getStatus()==null) {
            queryForm.setStatus(ExpenseStatus.APPROVED.getEnumCode().toString());
        }

        Map conditions = constructQueryMap(queryForm, request);
        
        conditions.put(ExpenseQueryCondition.STATUS_EQ2,
                new Object[]{ExpenseStatus.APPROVED, ExpenseStatus.CLAIMED});

        ExpenseQueryOrder queryOrder=ExpenseQueryOrder.getEnum(queryForm.getOrder());
        if(queryOrder==null) throw new RuntimeException("order not found!");

        ExpenseManager em = ServiceLocator.getExpenseManager(request);
        
        String exportType = queryForm.getExportType();
        if (StringUtils.isNotEmpty(exportType)) {
            List data = em.getExpenseList(conditions, 0, -1, queryOrder, queryForm.isDescend());

            int index = SessionTempFile.createNewTempFile(request);
            String fileName = "expenseFinalClaim";
            String suffix = ExportUtil.export(exportType, data, request, new FileOutputStream(SessionTempFile.getTempFile(index, request)), new Exportable() {

                public void exportHead(List row, HttpServletRequest request) throws Exception {
                    MessageResources messages = getResources(request);
                    row.add(messages.getMessage(getLocale(request), "expense.listExpense.code"));
                    row.add(messages.getMessage(getLocale(request), "expense.listExpense.category"));
                    row.add(messages.getMessage(getLocale(request), "expense.listExpense.requestor"));
                    row.add(messages.getMessage(getLocale(request), "expense.listExpense.department"));
                    row.add(messages.getMessage(getLocale(request), "expense.listExpense.currency"));
                    row.add(messages.getMessage(getLocale(request), "expense.listExpense.totalAmount"));
                    row.add(messages.getMessage(getLocale(request), "expense.listExpense.createDate"));
                    row.add(messages.getMessage(getLocale(request), "expense.listExpense.status"));
                }

                public void exportRow(List row, Object data, HttpServletRequest request) throws Exception {
                    row.add(BeanHelper.getBeanPropertyValue(data, "id"));
                    row.add(BeanHelper.getBeanPropertyValue(data, "expenseCategory.description"));
                    row.add(BeanHelper.getBeanPropertyValue(data, "requestor.name"));
                    row.add(BeanHelper.getBeanPropertyValue(data, "department.name"));
                    row.add(BeanHelper.getBeanPropertyValue(data, "expenseCurrency.name"));
                    row.add(BeanHelper.getBeanPropertyValue(data, "amount"));
                    Expense expense = (Expense) data;
                    if (expense.getCreateDate() != null) {
                        row.add(ActionUtils.getDisplayDateFromDate(expense.getCreateDate()));
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
            queryForm.init(em.getExpenseListCount(conditions));
        } else {
            queryForm.init();
        }
        
        List result = em.getExpenseList(conditions, queryForm.getPageNoAsInt(), queryForm.getPageSizeAsInt(), queryOrder,
                queryForm.isDescend());
        request.setAttribute("X_RESULTLIST", result);        

        final List statusList=new ArrayList();
        statusList.add(ExpenseStatus.APPROVED);
        statusList.add(ExpenseStatus.CLAIMED);
        request.setAttribute("x_expenseStatusList", statusList);
        
        return mapping.findForward("page");
    }

    /**
     * expense final claim
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward finalClaim(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Expense ep = this.getExpenseFromRequest(request);
        BeanForm beanForm=(BeanForm)this.getForm("/finalClaimExpense_result",request);
        beanForm.populateToForm(ep);

        this.checkApproved(ep);
        this.checkSite(ep.getDepartment().getSite(), request);

        getViewContent(ep,request);
        request.setAttribute("x_expense", ep);
        this.putCanViewExpenseBudgetAmount(ep.getYearlyBudget(), request);
        
        return mapping.findForward("page");
    }

    /**
     * final claim result
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward finalClaimResult(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Expense ep = this.getExpenseFromRequest(request);
        BeanForm beanForm=(BeanForm) form;
        beanForm.populateToBean(ep,request);
        this.checkApproved(ep);
        this.checkSite(ep.getDepartment().getSite(), request);

        ExpenseManager em = ServiceLocator.getExpenseManager(request);
        if (isReject(request)) {
            em.rejectExpenseByFinalClaim(ep, this.getCurrentUser(request), "Rejected by expense final claim: " + request.getParameter("rejectComment"));

            this.postGlobalMessage("expense.finalClaim.reject.success", request.getSession());
            return getFinalClaimViewForward(ep);
        }

        em.finalClaim(ep.getId());

        EmailManager emailManager=ServiceLocator.getEmailManager(request);
        Map context=new HashMap();
        context.put("x_ep",ep);
        context.put("x_userName",ep.getCreator().getName());
        context.put("role", EmailManager.EMAIL_ROLE_CREATOR);
        emailManager.insertEmail(ep.getCreator().getPrimarySite(),ep.getCreator().getEmail(),"ExpenseFinalClaim.vm",context);
        if(!ep.getCreator().equals(ep.getRequestor())) {
            context.put("x_userName",ep.getRequestor().getName());
            context.put("role", EmailManager.EMAIL_ROLE_REQUESTOR);
            emailManager.insertEmail(ep.getRequestor().getPrimarySite(),ep.getRequestor().getEmail(),"ExpenseFinalClaim.vm",context);
        }
        
        this.postGlobalMessage("expense.finalClaim.success", request.getSession());
      
        return getFinalClaimViewForward(ep);
    }

    private ActionForward getFinalClaimViewForward(Expense ep) {
        return new ActionForward("viewExpense_finalClaim.do?id=" + ep.getId(), true);
    }

    public ActionForward withdraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Expense expense = this.getExpenseFromRequest(request);
        final boolean isSelf = this.isGlobal(request);
        if (isSelf) {
            this.checkRequestorIsSelf(expense,request);
        } else {
            this.checkCreatorIsSelf(expense,request);
            this.checkDepartment(expense.getDepartment(),request);
        }
        this.checkCanWithDraw(expense,request);
        ExpenseManager em = ServiceLocator.getExpenseManager(request);
        em.withDrawExpense(expense,this.getCurrentUser(request));
        this.postGlobalMessage("expense.withdraw.success",request.getSession());
        return getEditForwardFor(expense,isSelf);
        
    }
    
    private void checkCanWithDraw(Expense expense, HttpServletRequest request) {
        ExpenseApproveRequestManager em = ServiceLocator.getExpenseApproveRequestManager(request);
        List approveList = em.getExpenseApproveRequestListByApproveRequestId(expense.getApproveRequestId());
        if (approveList.size()>0) {
            ExpenseApproveRequest ear=(ExpenseApproveRequest)approveList.get(0);
            if (!ear.getStatus().equals(ApproveStatus.WAITING_FOR_APPROVE)) {
                throw new ActionException("expense.error.cannotWithdraw");
            }
        }
    }
    
    public ActionForward selectBudget(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ExpenseCategory ec = this.getExpenseCategoryFromRequest(request);
        Department department = this.getDepartmentFromRequest(request);
        Date effectiveDate = this.getEffectiveDateFromRequest(request);
        
        YearlyBudgetManager ym = ServiceLocator.getYearlyBudgetManager(request);

        List departmentList = new ArrayList();
        departmentList.add(department);
        User currentUser = getCurrentUser(request);
        List result = ym.getSuitableYearlyBudget(department.getSite(), ec, departmentList,BudgetType.Expense, effectiveDate, currentUser);
        request.setAttribute("X_RESULTLIST", result);
        
        return mapping.findForward("page");
    }
    
    private ExpenseCategory getExpenseCategoryFromRequest(HttpServletRequest request) {
        Integer id = ActionUtils.parseInt(request.getParameter("expenseCategory_id"));
        if (id == null)
            throw new ActionException("expenseCategory.idNotSet");
        ExpenseCategoryManager ecm = ServiceLocator.getExpenseCategoryManager(request);
        ExpenseCategory psc = ecm.getExpenseCategory(id);
        if (psc == null)
            throw new ActionException("expenseCategory..notFound", id);
        return psc;
    }

    private Department getDepartmentFromRequest(HttpServletRequest request) {
        Integer id = ActionUtils.parseInt(request.getParameter("department_id"));
        if (id == null)
            throw new ActionException("department.idNotSet");
        DepartmentManager dm = ServiceLocator.getDepartmentManager(request);
        Department d = dm.getDepartment(id);
        if (d == null)
            throw new ActionException("department.notFound", id);
        return d;
    }
    
    private Date getEffectiveDateFromRequest(HttpServletRequest request) {
        Date date = ActionUtils.getDateFromDisplayDate(request.getParameter("requestDate"));
        return date;
    }
    
    protected void putCanViewExpenseBudgetAmount(YearlyBudget yb, HttpServletRequest request) {
        if (yb != null) {
            YearlyBudgetManager ym = ServiceLocator.getYearlyBudgetManager(request);
            request.setAttribute("x_canViewExpenseBudgetAmount",new Boolean(ym.canViewExpenseBudgetAmount(yb,this.getCurrentUser(request))));
        } else {
            request.setAttribute("x_canViewExpenseBudgetAmount", false);
        }
         
    }
}