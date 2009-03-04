package com.aof.web.struts.action.business.pr;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;
import org.springframework.orm.hibernate.HibernateOptimisticLockingFailureException;

import com.aof.model.admin.Department;
import com.aof.model.admin.PurchaseCategory;
import com.aof.model.admin.PurchaseSubCategory;
import com.aof.model.admin.Site;
import com.aof.model.admin.Supplier;
import com.aof.model.admin.SupplierItem;
import com.aof.model.admin.User;
import com.aof.model.business.BaseApproveRequest;
import com.aof.model.business.po.PurchaseOrderItem;
import com.aof.model.business.pr.Capex;
import com.aof.model.business.pr.CapexDepartment;
import com.aof.model.business.pr.CapexRequest;
import com.aof.model.business.pr.CapexRequestItem;
import com.aof.model.business.pr.PurchaseRequestItem;
import com.aof.model.business.pr.YearlyBudget;
import com.aof.model.business.pr.query.CapexRequestQueryCondition;
import com.aof.model.business.pr.query.CapexRequestQueryOrder;
import com.aof.model.metadata.ApproveStatus;
import com.aof.model.metadata.BudgetType;
import com.aof.model.metadata.CapexRequestStatus;
import com.aof.model.metadata.CapexRequestType;
import com.aof.model.metadata.MetadataDetailEnum;
import com.aof.model.metadata.YesNo;
import com.aof.service.admin.CurrencyManager;
import com.aof.service.admin.DepartmentManager;
import com.aof.service.admin.ExchangeRateManager;
import com.aof.service.admin.PurchaseCategoryManager;
import com.aof.service.admin.SiteManager;
import com.aof.service.business.pr.CapexManager;
import com.aof.service.business.pr.PurchaseRequestManager;
import com.aof.service.business.rule.ExecuteFlowEmptyResultException;
import com.aof.service.business.rule.NoAvailableFlowToExecuteException;
import com.aof.utils.PDFReport;
import com.aof.utils.SessionTempFile;
import com.aof.web.struts.action.ActionUtils;
import com.aof.web.struts.action.ServiceLocator;
import com.aof.web.struts.form.business.pr.CapexRequestQueryForm;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.shcnc.hibernate.PersistentEnum;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.action.BackToInputActionException;
import com.shcnc.struts.form.BeanForm;
import com.shcnc.utils.BeanUtils;
import com.shcnc.utils.ExportUtil;
import com.shcnc.utils.Exportable;

public class CapexRequestAction extends BaseCapexRequestAction {
    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CapexManager cm = ServiceLocator.getCapexManager(request);

        CapexRequestQueryForm queryForm = (CapexRequestQueryForm) form;
        
        queryForm.setCapex_yearlyBudget_year(null);

        Map conditions = constructQueryMap(queryForm, request);

        conditions.put(CapexRequestQueryCondition.REQUESTOR_ID_EQ, getCurrentUser(request).getId());

        String exportType = queryForm.getExportType();
        if (exportType != null && exportType.length() > 0) {
            List data = cm.getCapexRequestList(conditions, 0, -1, CapexRequestQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend());
            int index = SessionTempFile.createNewTempFile(request);
            String fileName = "capexRequest";
            String suffix = ExportUtil.export(exportType, data, request, new FileOutputStream(SessionTempFile.getTempFile(index, request)), new Exportable() {

                public void exportHead(List row, HttpServletRequest request) throws Exception {
                    MessageResources messages = getResources(request);
                    Locale locale = getLocale(request);
                    row.add(messages.getMessage(locale, "capexRequest.capex.id"));
                    row.add(messages.getMessage(locale, "capexRequest.requestor.id"));
                    row.add(messages.getMessage(locale, "capexRequest.capex.yearlyBudget.id"));
                    row.add(messages.getMessage(locale, "capexRequest.requestDate"));
                    row.add(messages.getMessage(locale, "capexRequest.type"));
                    row.add(messages.getMessage(locale, "capexRequest.status"));
                }

                public void exportRow(List row, Object data, HttpServletRequest request) throws Exception {
                    row.add(BeanUtils.getProperty(data, "capex.id"));
                    row.add(BeanUtils.getProperty(data, "requestor.name"));
                    row.add(BeanUtils.getProperty(data, "capex.yearlyBudget.id"));
                    row.add(BeanUtils.getProperty(data, "requestDate"));
                    String locale = getCurrentUser(request).getLocale();
                    if ("en".equals(locale)) {
                        row.add(BeanUtils.getProperty(data, "type.engShortDescription"));
                    } else {
                        row.add(BeanUtils.getProperty(data, "type.chnShortDescription"));
                    }
                    if ("en".equals(locale)) {
                        row.add(BeanUtils.getProperty(data, "status.engShortDescription"));
                    } else {
                        row.add(BeanUtils.getProperty(data, "status.chnShortDescription"));
                    }
                }
            });
            return new ActionForward("download/" + index + "/" + URLEncoder.encode(fileName, "UTF-8") + '.' + suffix, true);
        }

        if (queryForm.isFirstInit()) {
            queryForm.init(cm.getCapexRequestListCount(conditions));
        } else {
            queryForm.init();
        }

        List result = cm.getCapexRequestList(conditions, queryForm.getPageNoAsInt(), queryForm.getPageSizeAsInt(), CapexRequestQueryOrder.getEnum(queryForm
                .getOrder()), queryForm.isDescend());

        putEnumListToRequest(request);
        request.setAttribute("X_RESULTLIST", result);
        return mapping.findForward("page");
    }

    public ActionForward report(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List grantedSiteList = getAndCheckGrantedSiteDeparmentList(request);
        request.setAttribute("x_siteList", grantedSiteList);

        CapexManager cm = ServiceLocator.getCapexManager(request);

        CapexRequestQueryForm queryForm = (CapexRequestQueryForm) form;

        //Integer siteId = ActionUtils.parseInt(queryForm.getCapex_site_id());
        Integer depId = ActionUtils.parseInt(queryForm.getCapex_department_id());
        if (depId == null) {
            Site site = ((Site) grantedSiteList.get(0));
            //depId = ((Department)site.getDepartments().get(0)).getId();
            //queryForm.setCapex_department_id(depId.toString());
            queryForm.setCapex_site_id(site.getId().toString());
        } else {
            //DepartmentManager dm = ServiceLocator.getDepartmentManager(request);            
            //checkDepartment(dm.getDepartment(depId), request);
        }

        Map conditions = constructQueryMap(queryForm, request);
        conditions.put(CapexRequestQueryCondition.IS_LAST_APPROVED, null);

        String exportType = queryForm.getExportType();
        if (exportType != null && exportType.length() > 0) {
            List data = cm.getCapexRequestList(conditions, 0, -1, CapexRequestQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend());
            fillInformationForCapexReport(data, request);
            if ("pdf".equalsIgnoreCase(exportType)) {
                exportPDF(ActionUtils.parseInt(queryForm.getCapex_site_id()), data, "capex vs purchaseRequest", request, response);
                return null;
            }
            if ("excel".equalsIgnoreCase(exportType)) {
                exportExcel(ActionUtils.parseInt(queryForm.getCapex_site_id()), data, "capex vs purchaseRequest", request, response);
                return null;
            }
            int index = SessionTempFile.createNewTempFile(request);
            String fileName = "capex vs pr";
            String suffix = ExportUtil.export(exportType, data, request, new FileOutputStream(SessionTempFile.getTempFile(index, request)), new Exportable() {

                public void exportHead(List row, HttpServletRequest request) throws Exception {
                    MessageResources messages = getResources(request);
                    Locale locale = getLocale(request);
                    row.add(messages.getMessage(locale, "capexRequest.capex.id"));
                    row.add(messages.getMessage(locale, "capexRequest.requestor.id"));
                    row.add(messages.getMessage(locale, "capexRequest.amount"));
                    row.add(messages.getMessage(locale, "capexRequest.requestDate"));
                }

                public void exportRow(List row, Object data, HttpServletRequest request) throws Exception {
                    row.add(BeanUtils.getProperty(data, "capex.id"));
                    row.add(BeanUtils.getProperty(data, "requestor.name"));
                    row.add(BeanUtils.getProperty(data, "amount"));
                    row.add(BeanUtils.getProperty(data, "requestDate"));
                }
            });
            return new ActionForward("download/" + index + "/" + URLEncoder.encode(fileName, "UTF-8") + '.' + suffix, true);
        }

        if (queryForm.isFirstInit()) {
            queryForm.init(cm.getCapexRequestListCount(conditions));
        } else {
            queryForm.init();
        }

        List result = cm.getCapexRequestList(conditions, queryForm.getPageNoAsInt(), queryForm.getPageSizeAsInt(), CapexRequestQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend());
        fillInformationForCapexReport(result, request);

        request.setAttribute("X_RESULTLIST", result);
        return mapping.findForward("page");
    }

    private void fillInformationForCapexReport(List data, HttpServletRequest request) {
        PurchaseRequestManager prm = ServiceLocator.getPurchaseRequestManager(request);
        Calendar cal = Calendar.getInstance();
        for (Iterator itor = data.iterator(); itor.hasNext();) {
            BigDecimal[] amounts = new BigDecimal[12];
            for (int i = 0; i < 12; i++) {
                amounts[i] = new BigDecimal(0d).setScale(2);
            }
            CapexRequest capexRequest = (CapexRequest) itor.next();
            Capex c = capexRequest.getCapex();
            int capexYear = c.getYear();
            List prItems = prm.getCalculatedPurchaseRequestItemByCapexId(c.getId());
            c.setPurchaseRequestItems(prItems);
            for (Iterator itor2 = prItems.iterator(); itor2.hasNext(); ) {
                PurchaseRequestItem prItem = (PurchaseRequestItem) itor2.next();
                cal.setTime(prItem.getPurchaseRequest().getRequestDate());
                int year = cal.get(Calendar.YEAR);
                int month = 0;
                if (year == capexYear) {
                    month = cal.get(Calendar.MONTH);
                } else if (year > capexYear) {
                    month = 11;
                }
                amounts[month] = amounts[month].add(prItem.getBaseCurrencyAmount());
            }
            c.setMonthlyAmount(amounts);
        }
    }
    
    private void exportPDF(Integer siteId, List data, String filename, HttpServletRequest request, HttpServletResponse response) throws MalformedURLException, DocumentException, IOException {
        MessageResources messages = getResources(request);
        Locale locale = getLocale(request);
        
        PDFReport report = PDFReport.createReport(siteId, "capexRequest.report.title", request, messages, locale, PageSize.A4.rotate());
        Document document = report.getDocument();

        PdfPTable table = PDFReport.createTable(new float[] {6, 3, 6, 8, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 5, 4, 6, 8}, 100, 0.5f);
        table.setHeaderRows(1);
        table.setSplitLate(false);
        PdfPCell defaultCell = table.getDefaultCell();
        Color defaultBackgroundColor = defaultCell.backgroundColor();

        defaultCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        defaultCell.setBackgroundColor(new Color(0x99, 0x99, 0xff));
        
        Font headFont = PDFReport.getFont(Font.BOLD, Color.BLACK);
        report.addCell(table, "yearlyBudget.code", headFont, true);
        report.addCell(table, "yearlyBudget.year", headFont, true);
        report.addCell(table, "yearlyBudget.amount", headFont, true);
        report.addCell(table, "capexRequest.capex.id", headFont, true);
        report.addCell(table, "capexRequest.amount", headFont, true);
        report.addCell(table, "capexRequest.capex.actualAmount", headFont, true);
        PdfPTable headTable2 = PDFReport.createTable(new float[] {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3}, 100, 0.5f);
        PdfPCell headDefaultCell2 = headTable2.getDefaultCell();
        headDefaultCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        headDefaultCell2.setBackgroundColor(new Color(0x99, 0x99, 0xff));
        report.addCell(headTable2, "capex.actualExpenditure", 12, headFont, true);
        for (int i = 1; i < 13; i++) {
            report.addCell(headTable2, "capex.actualExpenditure." + i, headFont, true);
        }
        PDFReport.AddTableToTable(table, headTable2, 12);
        report.addCell(table, "purchaseRequestItem.supplierItem.id", headFont, true);
        report.addCell(table, "purchaseRequestItem.quantity", headFont, true);
        report.addCell(table, "purchaseRequestItem.unitPrice", headFont, true);
        report.addCell(table, "purchaseRequestItem.status", headFont, true);

        defaultCell.setBackgroundColor(defaultBackgroundColor);

        for (Iterator itor = data.iterator(); itor.hasNext();) {
            CapexRequest capexRequest = (CapexRequest) itor.next();
            Capex capex = capexRequest.getCapex();
            YearlyBudget yb = capex.getYearlyBudget();
            defaultCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            report.addCell(table, yb == null ? null : yb.getCode());
            defaultCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            report.addCell(table, new Integer(capex.getYear()));
            defaultCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            report.addCell(table, yb == null ? null : yb.getAmount());
            defaultCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            report.addCell(table, capex.getId());
            defaultCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            report.addCell(table, capexRequest.getAmount());
            report.addCell(table, capex.getActualAmount());
            BigDecimal[] amounts = capex.getMonthlyAmount();
            for (int i = 0; i < 12; i++) {
                report.addCell(table, amounts[i]);
            }
            PdfPTable table2 = PDFReport.createTable(new float[] {5, 4, 6, 8}, 100, 0.5f);
            PdfPCell defaultCell2 = table2.getDefaultCell();
            
            boolean hasItem = false;
            for (Iterator itor2 = capex.getPurchaseRequestItems().iterator(); itor2.hasNext();) {
                hasItem = true;
                PurchaseRequestItem pr_item = (PurchaseRequestItem) itor2.next();
                PurchaseOrderItem po_item = pr_item.getPurchaseOrderItem();
                if (po_item == null) {
                    defaultCell2.setHorizontalAlignment(Element.ALIGN_LEFT);
                    report.addCell(table2, pr_item.getSupplierItemSepc());
                    defaultCell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    report.addCell(table2, new Integer(pr_item.getQuantity()));
                    report.addCell(table2, pr_item.getUnitPrice());
                    defaultCell2.setHorizontalAlignment(Element.ALIGN_LEFT);
                    report.addCell(table2, createItemStatusPhrase("PR:", (MetadataDetailEnum)pr_item.getStatus(), locale));
                } else {
                    defaultCell2.setHorizontalAlignment(Element.ALIGN_LEFT);
                    report.addCell(table2, po_item.getItemSpec());
                    defaultCell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    report.addCell(table2, new Integer(po_item.getQuantity()));
                    report.addCell(table2, po_item.getUnitPrice());
                    defaultCell2.setHorizontalAlignment(Element.ALIGN_LEFT);
                    if (po_item.getPurchaseOrder() == null) {
                        report.addCell(table2, createItemStatusPhrase("PR: ", (MetadataDetailEnum)pr_item.getStatus(), locale));
                    } else {
                        report.addCell(table2, createItemStatusPhrase("PO: ", (MetadataDetailEnum)pr_item.getStatus(), locale));
                    }
                }
            }
            if (!hasItem) {
                report.addCell(table2, null);
                report.addCell(table2, null);
                report.addCell(table2, null);
                report.addCell(table2, null);
            }
            PDFReport.AddTableToTable(table, table2, 4);
        }
        
        document.add(table);
        
        report.output(filename, response);

    }

    private void exportExcel(Integer siteId, List data, String filename, HttpServletRequest request, HttpServletResponse response) throws MalformedURLException, IOException {
        MessageResources messages = getResources(request);
        Locale locale = getLocale(request);
        
        String title = messages.getMessage(locale, "capexRequest.report.title");
        HSSFWorkbook book = new HSSFWorkbook(new FileInputStream(this.servlet.getServletContext().getRealPath("/WEB-INF/reportTemplate/reportCapexVsPR.xls")));
        HSSFDataFormat format = book.createDataFormat();
        HSSFCellStyle cellStyleAlignLeft = book.createCellStyle();
        cellStyleAlignLeft.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        HSSFCellStyle cellStyleAlignRight = book.createCellStyle();
        cellStyleAlignRight.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        HSSFCellStyle cellStyleAlignCenter = book.createCellStyle();
        cellStyleAlignCenter.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFCellStyle cellStyleAmount = book.createCellStyle();
        cellStyleAmount.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        cellStyleAmount.setDataFormat(format.getFormat("#,##0.00"));
        HSSFSheet sheet = book.getSheetAt(0);
        HSSFRow row = sheet.getRow(0);
        HSSFCell cell = row.getCell((short)0);
        cell.setCellValue(title);

        row = sheet.getRow(1);
        cell = row.getCell((short)0);
        cell.setCellValue(messages.getMessage(locale, "yearlyBudget.code"));
        cell = row.getCell((short)1);
        cell.setCellValue(messages.getMessage(locale, "yearlyBudget.year"));
        cell = row.getCell((short)2);
        cell.setCellValue(messages.getMessage(locale, "yearlyBudget.amount"));
        cell = row.getCell((short)3);
        cell.setCellValue(messages.getMessage(locale, "capexRequest.capex.id"));
        cell = row.getCell((short)4);
        cell.setCellValue(messages.getMessage(locale, "capexRequest.amount"));
        cell = row.getCell((short)5);
        cell.setCellValue(messages.getMessage(locale, "capexRequest.capex.actualAmount"));
        cell = row.getCell((short)6);
        cell.setCellValue(messages.getMessage(locale, "capex.actualExpenditure"));
        cell = row.getCell((short)18);
        cell.setCellValue(messages.getMessage(locale, "purchaseRequestItem.supplierItem.id"));
        cell = row.getCell((short)19);
        cell.setCellValue(messages.getMessage(locale, "purchaseRequestItem.quantity"));
        cell = row.getCell((short)20);
        cell.setCellValue(messages.getMessage(locale, "purchaseRequestItem.unitPrice"));
        cell = row.getCell((short)21);
        cell.setCellValue(messages.getMessage(locale, "purchaseRequestItem.status"));
        row = sheet.getRow(2);
        for (int i = 6; i < 18; i++) {
            cell = row.getCell((short)i);
            cell.setCellValue(messages.getMessage(locale, "capex.actualExpenditure." + (i - 5)));
        }

        int rowIndex = 3;
        for (Iterator itor = data.iterator(); itor.hasNext(); rowIndex++) {
            CapexRequest capexRequest = (CapexRequest) itor.next();
            Capex capex = capexRequest.getCapex();
            YearlyBudget yb = capex.getYearlyBudget();
            row = sheet.createRow(rowIndex);
            if (yb != null) {
                cell = row.createCell((short)0);
                cell.setCellValue(yb.getCode());
                cell.setCellStyle(cellStyleAlignLeft);
            }
            cell = row.createCell((short)1);
            cell.setCellValue(capex.getYear());
            cell.setCellStyle(cellStyleAlignCenter);
            if (yb != null) {
                cell = row.createCell((short)2);
                cell.setCellValue(yb.getAmount().doubleValue());
                cell.setCellStyle(cellStyleAmount);
            }
            cell = row.createCell((short)3);
            cell.setCellValue(capex.getId());
            cell.setCellStyle(cellStyleAlignCenter);
            cell = row.createCell((short)4);
            cell.setCellValue(capexRequest.getAmount().doubleValue());
            cell.setCellStyle(cellStyleAmount);
            cell = row.createCell((short)5);
            cell.setCellValue(capex.getActualAmount().doubleValue());
            cell.setCellStyle(cellStyleAmount);
            BigDecimal[] amounts = capex.getMonthlyAmount();
            for (int i = 0; i < 12; i++) {
                cell = row.createCell((short)(6 + i));
                cell.setCellValue(amounts[i].doubleValue());
                cell.setCellStyle(cellStyleAmount);
            }
            for (Iterator itor2 = capex.getPurchaseRequestItems().iterator(); itor2.hasNext();) {
                PurchaseRequestItem pr_item = (PurchaseRequestItem) itor2.next();
                PurchaseOrderItem po_item = pr_item.getPurchaseOrderItem();
                if (po_item == null) {
                    cell = row.createCell((short)18);
                    cell.setCellValue(pr_item.getSupplierItemSepc());
                    cell.setCellStyle(cellStyleAlignLeft);
                    cell = row.createCell((short)19);
                    cell.setCellValue(pr_item.getQuantity());
                    cell.setCellStyle(cellStyleAlignRight);
                    cell = row.createCell((short)20);
                    cell.setCellValue(pr_item.getUnitPrice().doubleValue());
                    cell.setCellStyle(cellStyleAmount);
                    cell = row.createCell((short)21);
                    cell.setCellValue("PR:" + this.getLocaleShortDescription((MetadataDetailEnum)pr_item.getStatus(), request));
                    cell.setCellStyle(cellStyleAlignLeft);
                } else {
                    cell = row.createCell((short)18);
                    cell.setCellValue(po_item.getItemSpec());
                    cell.setCellStyle(cellStyleAlignLeft);
                    cell = row.createCell((short)19);
                    cell.setCellValue(po_item.getQuantity());
                    cell.setCellStyle(cellStyleAlignRight);
                    cell = row.createCell((short)20);
                    cell.setCellValue(po_item.getUnitPrice().doubleValue());
                    cell.setCellStyle(cellStyleAmount);
                    cell = row.createCell((short)21);
                    cell.setCellValue((po_item.getPurchaseOrder() == null ? "PR:" : "PO:") + this.getLocaleShortDescription((MetadataDetailEnum)pr_item.getStatus(), request));
                    cell.setCellStyle(cellStyleAlignLeft);
                }
                if (itor2.hasNext()) {
                    rowIndex++;
                    row = sheet.createRow(rowIndex);
                }
            }
        }

        int index = SessionTempFile.createNewTempFile(request);
        OutputStream out = new FileOutputStream(SessionTempFile.getTempFile(index, request));
        book.write(out);
        out.close();
        response.sendRedirect("download/" + index + "/" + URLEncoder.encode(filename, "UTF-8") + ".xls");
    }
    
    private Phrase createItemStatusPhrase(String prefix, MetadataDetailEnum metadata, Locale locale) {
        Phrase p = new Phrase(prefix, PDFReport.getDefaultFont());
        String content = locale.equals(Locale.ENGLISH) ? metadata.getEngShortDescription() : metadata.getChnShortDescription();
        Phrase m = new Phrase(content, PDFReport.getFont(metadata.getColor()));
        Phrase result = new Phrase();
        result.add(p);
        result.add(m);
        return result;
    }
    
    private Map constructQueryMap(CapexRequestQueryForm queryForm, HttpServletRequest request) {
        Map conditions = new HashMap();

        String capex_id = queryForm.getCapex_id();
        if (capex_id != null && capex_id.trim().length() != 0) {
            conditions.put(CapexRequestQueryCondition.CAPEX_ID_LIKE, capex_id);
        }

        Integer type = ActionUtils.parseInt(queryForm.getType());
        if (type != null) {
            conditions.put(CapexRequestQueryCondition.TYPE_EQ, type);
        }

        String title = queryForm.getTitle();
        if (title != null && title.trim().length() != 0) {
            conditions.put(CapexRequestQueryCondition.TITLE_LIKE, title);
        }

        Integer status = ActionUtils.parseInt(queryForm.getStatus());
        if (status != null) {
            conditions.put(CapexRequestQueryCondition.STATUS_EQ, status);
        }

        String requestDateFrom = queryForm.getRequestDateFrom();
        if (requestDateFrom != null) {
            requestDateFrom = requestDateFrom.trim();
            if (requestDateFrom.length() > 0) {
                Date dateFrom = ActionUtils.getDateFromDisplayDate(requestDateFrom);
                if (dateFrom != null) {
                    conditions.put(CapexRequestQueryCondition.REQUESTDATE_GE, dateFrom);
                }
            }
        }

        String requestDateTo = queryForm.getRequestDateTo();
        if (requestDateTo != null) {
            requestDateTo = requestDateTo.trim();
            if (requestDateTo.length() > 0) {
                Date dateTo = ActionUtils.getDateFromDisplayDate(requestDateTo);
                if (dateTo != null) {
                    conditions.put(CapexRequestQueryCondition.REQUESTDATE_LE, dateTo);
                }
            }
        }

        Integer depId = ActionUtils.parseInt(queryForm.getCapex_department_id());
        Integer site_id = ActionUtils.parseInt(queryForm.getCapex_site_id());
        if (depId != null && depId.intValue() != 0) {
            conditions.put(CapexRequestQueryCondition.CAPEX_DEPARTMENT_ID_EQ, depId);
        } else {
            List siteList=this.getGrantedSiteDeparmentList(request);
            for (int i0 = 0; i0 < siteList.size(); i0++) {
                Site site = (Site)siteList.get(i0);
                if (site.getId().equals(site_id)) {
                    Object[] params = new Object[site.getDepartments().size()];                    
                    for (int j0 = 0; j0 < site.getDepartments().size(); j0++) {
                        params[j0] = ((Department)site.getDepartments().get(j0)).getId();
                    }
                    conditions.put(CapexRequestQueryCondition.CAPEX_DEPARTMENT_ID_IN, params);
                }
            }
        }
        
        Integer year = ActionUtils.parseInt(queryForm.getCapex_yearlyBudget_year());
        if (year != null) {
            conditions.put(CapexRequestQueryCondition.CAPEX_YEAR_EQ, year);
        }
        
        BigDecimal amountFrom = ActionUtils.parseBigDecimal(queryForm.getAmountFrom());
        if (amountFrom != null) {
            conditions.put(CapexRequestQueryCondition.AMOUNT_GE, amountFrom);
        }
        
        BigDecimal amountTo = ActionUtils.parseBigDecimal(queryForm.getAmountTo());
        if (amountTo != null) {
            conditions.put(CapexRequestQueryCondition.AMOUNT_LE, amountTo);
        }
        
        BigDecimal capex_remainAmountFrom = ActionUtils.parseBigDecimal(queryForm.getCapex_remainAmountFrom());
        if (capex_remainAmountFrom != null) {
            conditions.put(CapexRequestQueryCondition.CAPEX_REMAINAMOUNT_GE, capex_remainAmountFrom);
        }
        
        BigDecimal capex_remainAmountTo = ActionUtils.parseBigDecimal(queryForm.getCapex_remainAmountTo());
        if (capex_remainAmountTo != null) {
            conditions.put(CapexRequestQueryCondition.CAPEX_REMAINAMOUNT_LE, capex_remainAmountTo);
        }
        
        return conditions;
    }

    public ActionForward newObject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List siteList = getAndCheckGrantedSiteDeparmentList(request);
        PurchaseCategoryManager pcm = ServiceLocator.getPurchaseCategoryManager(request);
        pcm.fillEnabledPurchaseCategorySubCategoryForSiteList(siteList);
        //down add by jackey 2006-3-17 
        ExchangeRateManager erm = ServiceLocator.getExchangeRateManager(request);
        erm.fillEnabledExchangeRateListBySiteListIncludeBaseCurrency(siteList);
        //up add by jackey 2006-3-17
        request.setAttribute("x_siteList", siteList);

        CapexRequest capexRequest = prepareNewCapexRequest(request);        
        CurrencyManager cm = ServiceLocator.getCurrencyManager(request); // add by Jackey 2006-3-17
        capexRequest.setReferenceCurrency(cm.getCurrency(CapexRequest.DEFALUT_REFERENCE_CURRENCY)); // add by Jackey 2006-3-17
        
        if (!isBack(request)) {
            BeanForm capexRequestForm = (BeanForm) getForm("/insertCapexRequest", request);
            capexRequestForm.populateToForm(capexRequest);
        }

        request.setAttribute("x_capexRequest", capexRequest);
        return mapping.findForward("page");
    }

    public ActionForward insert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer siteId = ActionUtils.parseInt(request.getParameter("capex_site_id"));
        SiteManager sm = ServiceLocator.getSiteManager(request);
        Site s = sm.getSite(siteId);
        if (s == null) {
            throw new ActionException("site.notFound", siteId);
        }
        BeanForm capexRequestForm = (BeanForm) form;
        CapexRequest capexRequest = prepareNewCapexRequest(request);
        capexRequestForm.populateToBean(capexRequest);
        capexRequest.setAmount(capexRequest.getTotalAmount()); //modified by Jackey 2006-3-22
        Calendar c=Calendar.getInstance();
        c.setTime(capexRequest.getRequestDate());
        //capexRequest.getCapex().setYear(capexRequest.getRequestDate().getYear() + 1900);
        capexRequest.getCapex().setYear(c.get(Calendar.YEAR));
        List departmentList = getAndCheckDepartmentListFromRequest(s, capexRequestForm, request);

        CapexManager cm = ServiceLocator.getCapexManager(request);
        cm.saveCapexRequestAndDepartments(capexRequest, departmentList);

        return getForwardFor(capexRequest);
    }

    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CapexRequest capexRequest = getCapexRequestFromRequest(request);
        checkRequestor(capexRequest, request);
        checkEditable(capexRequest);
        request.setAttribute("x_capexRequest", capexRequest);
        putCapexRequestInfoToRequest(capexRequest, request);
        //down add by jackey 2006-3-17 
        ExchangeRateManager erm = ServiceLocator.getExchangeRateManager(request);
        request.setAttribute("x_refCurrency", erm.getEnabledExchangeRateListBySite(capexRequest.getCapex().getSite()));
        //up add by jackey 2006-3-17
        if (!isBack(request)) {
            BeanForm capexRequestForm = (BeanForm) getForm("/updateCapexRequest", request);
            capexRequestForm.populateToForm(capexRequest);
        }
        request.setAttribute("x_edit", Boolean.TRUE);
        return mapping.findForward("page");
    }

    public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return update(mapping, form, request, response, false);
    }

    public ActionForward viewapprover(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return update(mapping, form, request, response, true);
    }

    private ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean viewApprover)
            throws Exception {
        CapexRequest capexRequest = getCapexRequestFromRequest(request);
        checkRequestor(capexRequest, request);
        checkEditable(capexRequest);
        
        /*
         * 从capexRequest用到的budget的actualAmount中减去capexRequest的amount
         * 这样budget的actualAmount变为此capexRequest不存在时应该的值
         */
        YearlyBudget yb = capexRequest.getCapex().getYearlyBudget();
        if (yb != null) {
            yb.setActualAmount(yb.getActualAmount().subtract(capexRequest.getAmount()));
        }
        /*
         * 从request获取item信息
         */
        List capexRequestItemList = getCapexRequestItemListFromRequest(capexRequest, request, false);
        
        BeanForm capexRequestForm = (BeanForm) form;
        capexRequestForm.populateToBean(capexRequest, 
                new String[] { 
                "title", 
                "description", 
                "postAuditDate",
                "firstExpenseDate",
                "lastExpenseDate",
                "completionDate",
                "projectLeader.id",
                "projectLeaderTitle",
                "accountingCode",
                "isCapexType",
                "isNewImplantationReason",
                "isCapacityIncreaseReason",
                "isAssetDisposalType",
                "isCostImprovementReason",
                "isNewProductLineReason",
                "otherType",
                "isDownsizingReason",
                "isHSEReason",
                "otherReason",
                "capexCapitalizedAmount",
                "otherExpenseAmount",
                "assetDisposalAmount",
                "grossBookAmount",
                "referenceCurrency.code",                
                "referenceExchangeRate",
                "projectImpactNonOperating1",
                "projectImpactNonOperating2",
                "projectImpactOther1",
                "projectImpactOther2",
                "projectImpactOther3",
                "lastForecastAmount",
                "discountedCashFlowPayback",
                "internalRateOfReturn",
                "npvAmount",
                "discountRate"
                });

        /*
         * 重新计算capexRequest的amount
         * Add by Jackey 2006-3-26
         */
        capexRequest.setAmount(capexRequest.getTotalAmount());
        /*
         * 再将capexRequest新的amount加到它用到的budget的actualAmount上
         * 这样budget就反映了最新的情况。
         * 因为flow的执行位于调用CapexManager保存capexRequest之前，不这样做执行flow时就无法正确计算over bugdet值。
         * 
         * 需要注意的是，在Action中对budget的改变在调用CapexManager保存capexRequest时不会对数据库造成影响，
         * CapexManager也不依赖Action中维护的budget值，相反，它会重新从数据库读取该capexRequest的旧值以及budget最新值，
         * 以便正确计算budget的最新actualAmount
         */
        if (yb != null) {
            yb.setActualAmount(yb.getActualAmount().add(capexRequest.getAmount()));
        }

        CapexManager cm = ServiceLocator.getCapexManager(request);
        
        cm.setExtraInformationToCapexForExecuteFlow(capexRequest.getCapex());
        
        if (viewApprover) {
            try {
                request.setAttribute("X_APPROVELIST", executeFlow(capexRequest, request));
            } catch (ActionException e) {
                ActionMessage message = new ActionMessage(e.getKey(), e.getValues());
                ActionMessages messages = new ActionMessages();
                messages.add(null, message);
                saveErrors(request, messages);
            }
            return mapping.findForward("page");
        } else {
            List crarList = null;
            boolean draft = isDraft(request);
            if (!draft) {
                crarList = executeFlow(capexRequest, request);
            }

            try {
                cm.updateCapexRequest(capexRequest, capexRequestItemList, crarList, draft);
            } catch(HibernateOptimisticLockingFailureException e) {
                throw new ActionException("capexRequest.yearlyBudget.alreadyModified");
            }

            if (draft) {
                this.postGlobalMessage("capexRequest.update.success", request.getSession());
            } else {
                this.postGlobalMessage("capexRequest.submit.success", request.getSession());
            }
            return getForwardFor(capexRequest);
        }
    }

    public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CapexRequest capexRequest = getCapexRequestFromRequest(request);
        checkRequestor(capexRequest, request);
        request.setAttribute("x_capexRequest", capexRequest);
        request.setAttribute("x_approved", CapexRequestStatus.APPROVED);
        putCapexRequestInfoToRequest(capexRequest, request);
        request.setAttribute("x_edit", Boolean.FALSE);
        if (request.getParameter("onlyview") != null) {
            return mapping.findForward("pageDialog");
        }
        return mapping.findForward("page");
    }

    public ActionForward selectYearlyBudget(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer siteId = ActionUtils.parseInt(request.getParameter("capex_site_id"));
        SiteManager sm = ServiceLocator.getSiteManager(request);
        Site s = sm.getSite(siteId);
        if (s == null) {
            throw new ActionException("site.notFound", siteId);
        }
        CapexRequest capexRequest = prepareNewCapexRequest(request);
        BeanForm capexRequestForm = (BeanForm) form;
        capexRequestForm.setBeanLoader(ServiceLocator.getBeanLoader(request));
        capexRequestForm.populateToBean(capexRequest);
        List departmentList = getAndCheckDepartmentListFromRequest(s, capexRequestForm, request);
        User currentUser = getCurrentUser(request);
        List l = ServiceLocator.getYearlyBudgetManager(request).getSuitableYearlyBudget(s, capexRequest.getCapex().getPurchaseCategory(),
                capexRequest.getCapex().getPurchaseSubCategory(), departmentList,BudgetType.CAPEX, currentUser);
        request.setAttribute("X_RESULTLIST", l);
        return mapping.findForward("page");
    }

    public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CapexRequest capexRequest = getCapexRequestFromRequest(request);
        checkRequestor(capexRequest, request);
        checkEditable(capexRequest);
        CapexManager cm = ServiceLocator.getCapexManager(request);
        cm.deleteCapexRequest(capexRequest);
        return getForwardFor(null);
    }

    public ActionForward createIncrease(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CapexRequest capexRequest = getCapexRequestFromRequest(request);
        checkRequestor(capexRequest, request);
        checkIsLastApprovedRequest(capexRequest);
        CapexManager cm = ServiceLocator.getCapexManager(request);
        Map conditions = new HashMap();
        conditions.put(CapexRequestQueryCondition.CAPEX_ID_EQ, capexRequest.getCapex().getId());
        conditions.put(CapexRequestQueryCondition.STATUS_NE, CapexRequestStatus.APPROVED);
        if (cm.getCapexRequestListCount(conditions) > 0) {
            throw new ActionException("capexRequest.existsUnApprovedRequest");
        }
        CapexRequest addtionalCapexRequest = cm.createAddtionalCapexRequest(capexRequest);
        return getForwardFor(addtionalCapexRequest);
    }

    public ActionForward withdraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CapexRequest capexRequest = getCapexRequestFromRequest(request);
        checkRequestor(capexRequest, request);
        checkCanWithdraw(capexRequest, request);
        CapexManager cm = ServiceLocator.getCapexManager(request);
        cm.withdrawCapexRequest(capexRequest);
        this.postGlobalMessage("capexRequest.withdraw.success", request.getSession());
        return getForwardFor(capexRequest);
    }


    public ActionForward export(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CapexRequest capexRequest = getCapexRequestFromRequest(request);
        Capex capex = capexRequest.getCapex();
        checkRequestor(capexRequest, request);
        request.setAttribute("x_capexRequest", capexRequest);
        putCapexRequestInfoToRequest(capexRequest, request);
        
        MessageResources messages = getResources(request);
        Locale locale = getLocale(request);

        PDFReport report = PDFReport.createReport(
                capexRequest.getCapex().getSite().getId(), 
                "capexRequest.pdf.title", 
                request, 
                messages, 
                locale);
        Document document = report.getDocument();

        Color tColor = new Color(0, 0x33, 0x66);

        PdfPTable table = PDFReport.createTable(new float[] { 1, 2 , 1 , 1 }, 100, 0);
        
        report.addCell(table, "capexRequest.title", tColor, true);
        report.addCell(table, capexRequest.getTitle(), 3);
        
        report.addCell(table, "capexRequest.activity", tColor, true);
        report.addCell(table, capexRequest.getActivity());
        report.addCell(table, "capexRequest.capex.id", tColor, true);
        report.addCell(table, capex.getId(), PDFReport.getFont(Font.BOLD, "#6600ff"));
        
        report.addCell(table, "capexRequest.capex.department", tColor, true);
        StringBuffer departments = new StringBuffer();
        for (Iterator itor = ((Collection) request.getAttribute("x_capexDepartmentList")).iterator(); itor.hasNext(); ) {
            if (departments.length() > 0) {
                departments.append(", ");
            }
            departments.append((((CapexDepartment)itor.next())).getDepartment().getName());
        }
        report.addCell(table, departments.toString());
        PdfPTable table2 = PDFReport.createTable(new float[] { 1 , 1 }, 100, 0);
        report.addCell(table2, "capexRequest.requestDate", tColor, true);
        report.addCell(table2, capexRequest.getRequestDate());
        PurchaseCategory pc = capex.getPurchaseCategory();
        PurchaseSubCategory psc = capex.getPurchaseSubCategory();
        report.addCell(table2, "capexRequest.capex.purchaseCategory.id", tColor, true);
        if (pc == null) {
            report.addCell(table2, "capexRequest.capex.purchaseCategory.all", true);
        } else {
            report.addCell(table2, pc.getDescription());
        }
        report.addCell(table2, "capexRequest.capex.purchaseSubCategory.id", tColor, true);
        if (psc == null) {
            report.addCell(table2, "capexRequest.capex.purchaseSubCategory.all", true);
        } else {
            report.addCell(table2, psc.getDescription());
        }
        report.addCell(table2, "capexRequest.type", tColor, true);
        report.addCell(table2, capexRequest.getType());
        PDFReport.AddTableToTable(table, table2, 2);
        
        report.addCell(table, "capexRequest.capex.site.location", tColor, true);
        report.addCell(table, locale.equals(Locale.ENGLISH) ? capex.getSite().getCity().getEngName() : capex.getSite().getCity().getChnName());
        report.addCell(table, "capexRequest.capex.yearlyBudget.id", tColor, true);
        YearlyBudget yb = capex.getYearlyBudget();
        if (yb == null) {
            report.addCell(table, null);
        } else {
            report.addCell(table, capex.getYearlyBudget().getId());
        }

        report.addCell(table, "capexRequest.capex.site.id", tColor, true);
        report.addCell(table, capex.getSite().getName());
        report.addCell(table, "capexRequest.postAuditDate", tColor, true);
        report.addCell(table, capexRequest.getPostAuditDate());
        
        report.addCell(table, "capexRequest.status", tColor, true);
        report.addCell(table, capexRequest.getStatus(), 3);

        document.add(table);

        Paragraph p = new Paragraph(
                new StringBuffer().append(messages.getMessage(locale, "capexRequest.description")).append("\n").toString(),
                PDFReport.getSubTitleFont());
        p.setAlignment(Element.ALIGN_LEFT);
        document.add(p);

        table = PDFReport.createTable(new float[] { 3, 2 }, 100, 0);
        report.addCell(table, capexRequest.getDescription());
        table2 = PDFReport.createTable(new float[] { 1, 1 }, 100, 0);
        report.addCell(table2, "capexRequest.firstExpenseDate", tColor, true);
        report.addCell(table2, capexRequest.getFirstExpenseDate());
        report.addCell(table2, "capexRequest.lastExpenseDate", tColor, true);
        report.addCell(table2, capexRequest.getLastExpenseDate());
        report.addCell(table2, "capexRequest.completionDate", tColor, true);
        report.addCell(table2, capexRequest.getCompletionDate());
        report.addCell(table2, "capexRequest.projectLeader", tColor, true);
        User leader = capexRequest.getProjectLeader();
        report.addCell(table2, leader == null ? null : leader.getName());
        report.addCell(table2, "capexRequest.projectLeaderTitle", tColor, true);
        report.addCell(table2, capexRequest.getProjectLeaderTitle());
        report.addCell(table2, "capexRequest.accountingCode", tColor, true);
        report.addCell(table2, capexRequest.getAccountingCode());
        report.addCell(table, table2);
        document.add(table);
        
        report.addSplitLine();
        
        p = new Paragraph(
                new StringBuffer().append(messages.getMessage(locale, "capexRequest.projectTypeAndReasons")).append("\n").toString(),
                PDFReport.getSubTitleFont());
        p.setAlignment(Element.ALIGN_LEFT);
        document.add(p);
        
        String path = this.servlet.getServletContext().getRealPath("/WEB-INF/lib/wingdng2.ttf");
        BaseFont bfCheck = BaseFont.createFont(path, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);     
        Font checkFont = new Font(bfCheck, 12, Font.NORMAL);
        
        table = PDFReport.createTable(new float[] { 9, 1 , 9 , 1 }, 100, 0);
        PdfPCell defaultCell = table.getDefaultCell();
        defaultCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        
        report.addCell(table, "capexRequest.capitalExpenditure", tColor, true);
        report.addCell(table, YesNo.YES.equals(capexRequest.getIsCapexType()) ? "\u0052" : "\u00a3", checkFont);
        report.addCell(table, "capexRequest.newImplantation", tColor, true);
        report.addCell(table, YesNo.YES.equals(capexRequest.getIsNewImplantationReason()) ? "\u0052" : "\u00a3", checkFont);
        
        report.addCell(table, "capexRequest.capacityIncrease", 3, tColor, true);
        report.addCell(table, YesNo.YES.equals(capexRequest.getIsCapacityIncreaseReason()) ? "\u0052" : "\u00a3", checkFont);
        
        report.addCell(table, "capexRequest.assetDisposal", tColor, true);
        report.addCell(table, YesNo.YES.equals(capexRequest.getIsAssetDisposalType()) ? "\u0052" : "\u00a3", checkFont);
        report.addCell(table, "capexRequest.costImprovement", tColor, true);
        report.addCell(table, YesNo.YES.equals(capexRequest.getIsCostImprovementReason()) ? "\u0052" : "\u00a3", checkFont);
        
        report.addCell(table, "capexRequest.newProductLine", 3, tColor, true);
        report.addCell(table, YesNo.YES.equals(capexRequest.getIsNewProductLineReason()) ? "\u0052" : "\u00a3", checkFont);
        
        String otherType = capexRequest.getOtherType();
        if (otherType == null) {
            report.addCell(table, "capexRequest.otherType", tColor, true);
        } else {
            Phrase p2 = new Phrase(messages.getMessage(locale, "capexRequest.otherType"), PDFReport.getFont(tColor));
            Phrase p3 = new Phrase(' ' + otherType, PDFReport.getDefaultFont());
            Phrase p4 = new Phrase();
            p4.add(p2);
            p4.add(p3);
            report.addCell(table, p4);
        }
        report.addCell(table, capexRequest.getOtherType() != null ? "\u0052" : "\u00a3", checkFont);
        report.addCell(table, "capexRequest.downsizing", tColor, true);
        report.addCell(table, YesNo.YES.equals(capexRequest.getIsDownsizingReason()) ? "\u0052" : "\u00a3", checkFont);

        report.addCell(table, "capexRequest.otherTypeDesc", 2, tColor, true);
        table2 = PDFReport.createTable(new float[] { 9 , 1 }, 100, 0);
        defaultCell = table2.getDefaultCell();
        defaultCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        report.addCell(table2, "capexRequest.hse", tColor, true);
        report.addCell(table2, YesNo.YES.equals(capexRequest.getIsHSEReason()) ? "\u0052" : "\u00a3", checkFont);
        String otherReason = capexRequest.getOtherReason();
        if (otherReason == null) {
            report.addCell(table2, "capexRequest.otherReason", tColor, true);
        } else {
            Phrase p2 = new Phrase(messages.getMessage(locale, "capexRequest.otherReason"), PDFReport.getFont(tColor));
            Phrase p3 = new Phrase(' ' + otherReason, PDFReport.getDefaultFont());
            Phrase p4 = new Phrase();
            p4.add(p2);
            p4.add(p3);
            report.addCell(table2, p4);
        }
        report.addCell(table2, otherReason != null ? "\u0052" : "\u00a3", checkFont);
        PDFReport.AddTableToTable(table, table2, 2);
        document.add(table);

        report.addSplitLine();

        p = new Paragraph(
                new StringBuffer().append(messages.getMessage(locale, "capexRequest.amountRequested")).append("\n").toString(),
                PDFReport.getSubTitleFont());
        p.setAlignment(Element.ALIGN_LEFT);
        document.add(p);
        
        table = PDFReport.createTable(new float[] { 50, 15 , 20 , 15 }, 100, 0);
        defaultCell = table.getDefaultCell();
        defaultCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        report.addCell(table, "capexRequest.capex.currency.code", tColor, true);
        report.addCell(table, capex.getCurrency().getName());
        report.addCell(table, "capexRequest.refenceCurrency", tColor, true);
        report.addCell(table, capexRequest.getReferenceCurrency().getCode());
        
        report.addCell(table, "capexRequest.capexCapitalizedAmount", tColor, true);
        report.addCell(table, capexRequest.getCapexCapitalizedAmount());
        report.addCell(table, capexRequest.getCapexCapitalizedAmount().divide(capexRequest.getReferenceExchangeRate(), 2), 2);
        
        report.addCell(table, "capexRequest.otherExpenseAmount", tColor, true);
        report.addCell(table, capexRequest.getOtherExpenseAmount());
        report.addCell(table, capexRequest.getOtherExpenseAmount().divide(capexRequest.getReferenceExchangeRate(), 2), 2);
        
        report.addCell(table, "capexRequest.assetDisposalAmount", tColor, true);
        report.addCell(table, capexRequest.getAssetDisposalAmount());
        report.addCell(table, capexRequest.getAssetDisposalAmount().divide(capexRequest.getReferenceExchangeRate(), 2), 2);

        report.addCell(table, "capexRequest.totalRequestAmount.pdf", PDFReport.getFont(Font.BOLD, tColor), true);
        report.addCell(table, capexRequest.getTotalAmount());
        report.addCell(table, capexRequest.getTotalAmount().divide(capexRequest.getReferenceExchangeRate(), 2), 2);

        report.addCell(table, "capexRequest.grossBookAmount", tColor, true);
        report.addCell(table, capexRequest.getGrossBookAmount());
        report.addCell(table, capexRequest.getGrossBookAmount().divide(capexRequest.getReferenceExchangeRate(), 2), 2);

        report.addCell(table, null, 4);

        report.addCell(table, "capexRequest.otherProjectImpact", tColor, true);
        report.addCell(table, null, 3);
        
        report.addCell(table, "capexRequest.projectImpactNonOperating", tColor, true);
        report.addCell(table, capexRequest.getProjectImpactNonOperating1());
        report.addCell(table, capexRequest.getProjectImpactNonOperating2(), 2);

        {
            Phrase p2 = new Phrase(messages.getMessage(locale, "capexRequest.projectImpactOther"), PDFReport.getFont(tColor));
            Phrase p3 = new Phrase(' ' + capexRequest.getProjectImpactOther1(), PDFReport.getDefaultFont());
            Phrase p4 = new Phrase();
            p4.add(p2);
            p4.add(p3);
            report.addCell(table, p4);
        }
        report.addCell(table, capexRequest.getProjectImpactOther2());
        report.addCell(table, capexRequest.getProjectImpactOther3(), 2);

        report.addCell(table, null, 4);

        report.addCell(table, "capexRequest.referenceExchangeRate", 3, tColor, true);
        report.addCell(table, capexRequest.getReferenceExchangeRate(), 3);

        report.addCell(table, null, 4);

        report.addCell(table, "capexRequest.includeLastForecast", tColor, true);
        report.addCell(table, capexRequest.getLastForecastAmount() != null ? "\u0052" : "\u00a3", checkFont);
        report.addCell(table, null, 2);

        report.addCell(table, "capexRequest.lastForecastAmount", tColor, true);
        report.addCell(table, capexRequest.getLastForecastAmount());
        report.addCell(table, null, 2);

        report.addCell(table, "capexRequest.notIncludeLastForecast", tColor, true);
        report.addCell(table, capexRequest.getLastForecastAmount() == null ? "\u0052" : "\u00a3", checkFont);
        report.addCell(table, null, 2);

        document.add(table);

        report.addSplitLine();

        p = new Paragraph(
                new StringBuffer().append(messages.getMessage(locale, "capexRequest.profitability")).append("\n").toString(),
                PDFReport.getSubTitleFont());
        p.setAlignment(Element.ALIGN_LEFT);
        document.add(p);

        table = PDFReport.createTable(new float[] { 50, 15 , 20 , 15 }, 100, 0);
        defaultCell = table.getDefaultCell();
        defaultCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        report.addCell(table, "capexRequest.discountedCashFlowPayback", tColor, true);
        report.addCell(table, capexRequest.getDiscountedCashFlowPayback());
        report.addCell(table, null, 2);
        
        report.addCell(table, "capexRequest.internalRateOfReturn", tColor, true);
        report.addCell(table, capexRequest.getInternalRateOfReturn());
        report.addCell(table, null, 2);

        report.addCell(table, "capexRequest.npvAmount", tColor, true);
        report.addCell(table, capexRequest.getNpvAmount());
        report.addCell(table, "capexRequest.referenceNpvAmount", tColor, true);
        report.addCell(table, capexRequest.getNpvAmount().divide(capexRequest.getReferenceExchangeRate(), 2));

        report.addCell(table, "capexRequest.discountRate", tColor, true);
        report.addCell(table, capexRequest.getDiscountRate());
        report.addCell(table, null, 2);
        document.add(table);

        report.addSpaceLine(3);
        
        Collection histories = (Collection) request.getAttribute("x_capexRequestHistoryList");
        if (!histories.isEmpty()) {
            p = new Paragraph(
                    new StringBuffer().append(messages.getMessage(locale, "capexRequest.history")).append("\n").toString(),
                    PDFReport.getSubTitleFont());
            p.setAlignment(Element.ALIGN_LEFT);
            document.add(p);

            report.addSpaceLine(3);

            table = PDFReport.createTable(new float[] { 1 , 6 , 2 , 2 , 2 }, 100, 0.5f);
            
            defaultCell = table.getDefaultCell();
            Color defaultBackgroundColor = defaultCell.backgroundColor();
            defaultCell.setBackgroundColor(new Color(0x99,0x99,0xff));
            defaultCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            Font headFont = PDFReport.getFont(Font.BOLD, Color.BLACK);
            
            report.addCell(table, "capexRequest.history.version", headFont, true);
            report.addCell(table, "capexRequest.title", headFont, true);
            report.addCell(table, "capexRequest.amount", headFont, true);
            report.addCell(table, "capexRequest.requestor.id", headFont, true);
            report.addCell(table, "capexRequest.requestDate", headFont, true);
            
            int i = 1;
            defaultCell.setBackgroundColor(defaultBackgroundColor);
            for (Iterator itor = histories.iterator(); itor.hasNext(); i++) {
                CapexRequest capexRequestHistory = (CapexRequest) itor.next();
                defaultCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                report.addCell(table, String.valueOf(i));
                defaultCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                report.addCell(table, capexRequestHistory.getTitle());
                defaultCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                report.addCell(table, capexRequestHistory.getAmount());
                defaultCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                report.addCell(table, capexRequestHistory.getRequestor().getName());
                report.addCell(table, capexRequestHistory.getRequestDate());
            }
            document.add(table);

            report.addSpaceLine(3);
        }
        
        report.addSplitLine();

        Collection items = (Collection) request.getAttribute("x_capexRequestItemList");
        {
            p = new Paragraph(
                    new StringBuffer().append(messages.getMessage(locale, "capexRequest.item")).append("\n").toString(),
                    PDFReport.getSubTitleFont());
            p.setAlignment(Element.ALIGN_LEFT);
            document.add(p);
    
            report.addSpaceLine(3);
    
            table = PDFReport.createTable(new float[] { 3, 3, 3, 2, 2, 2, 3, 3 }, 100, 0.5f);
            
            defaultCell = table.getDefaultCell();
            Color defaultBackgroundColor = defaultCell.backgroundColor();
            defaultCell.setBackgroundColor(new Color(0x99, 0x99, 0xff));
            defaultCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            Font headFont = PDFReport.getFont(Font.BOLD, Color.BLACK);
            
            report.addCell(table, "capexRequestItem.supplier.id", headFont, true);
            report.addCell(table, "capexRequestItem.supplierItem.id", headFont, true);
            report.addCell(table, "capexRequestItem.supplierItemSepc", headFont, true);
            report.addCell(table, "capexRequestItem.price", headFont, true);
            report.addCell(table, "capexRequestItem.currency.code", headFont, true);
            report.addCell(table, "capexRequestItem.quantity", headFont, true);
            report.addCell(table, "capexRequestItem.amount", headFont, true);
            report.addCell(table, "capexRequestItem.baseCurrencyAmount", headFont, true);
            
            defaultCell.setBackgroundColor(defaultBackgroundColor);
            for (Iterator itor = items.iterator(); itor.hasNext(); ) {
                CapexRequestItem item = (CapexRequestItem) itor.next();
                defaultCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                Supplier supplier = item.getSupplier();
                if (supplier != null) {
                    report.addCell(table, supplier.getName());
                } else {
                    report.addCell(table, item.getSupplierName());
                }
                SupplierItem supplierItem = item.getSupplierItem();
                if (supplierItem != null) {
                    report.addCell(table, supplierItem.getSepc());
                } else {
                    report.addCell(table, null);
                }
                report.addCell(table, item.getSupplierItemSepc());
                defaultCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                report.addCell(table, item.getPrice());
                defaultCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                report.addCell(table, item.getExchangeRate().getCurrency().getName());
                defaultCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                report.addCell(table, String.valueOf(item.getQuantity()));
                report.addCell(table, item.getAmount());
                report.addCell(table, item.getBaseCurrencyAmount());
            }
            document.add(table);
        }

        /*
        report.addAttachmentListTable(
                (Collection) request.getAttribute("x_capexRequestAttachmentList"),
                "capexRequest.attachment",
                "capexRequestAttachment.title",
                "capexRequestAttachment.fileName",
                "capexRequestAttachment.uploadDate");
        */

        report.addApproveListTable((Collection) request.getAttribute("X_APPROVELIST"));
        
        report.output("capexRequest", response);
        
        return null;
    }

    private void checkCanWithdraw(CapexRequest capexRequest, HttpServletRequest request) {
        CapexRequestStatus status = capexRequest.getStatus();
        if (!CapexRequestStatus.PENDING.equals(status)) {
            throw new ActionException("capexRequest.error.cannotWithdrawNotPending", this.getLocaleShortDescription(status, request));
        }
        CapexManager cm = ServiceLocator.getCapexManager(request);
        List approveList = cm.getCapexRequestApproveRequestListForCapexRequest(capexRequest);
        if (approveList.size() > 0) {
            if (!ApproveStatus.WAITING_FOR_APPROVE.equals(((BaseApproveRequest)approveList.get(0)).getStatus())) {
                throw new ActionException("capexRequest.error.cannotWithdraw");
            }
        }
        
    }

    private void putEnumListToRequest(HttpServletRequest request) {
        request.setAttribute("X_CAPEXREQUESTTYPELIST", PersistentEnum.getEnumList(CapexRequestType.class));
        request.setAttribute("X_CAPEXREQUESTSTATUSLIST", PersistentEnum.getEnumList(CapexRequestStatus.class));
    }

    private CapexRequest prepareNewCapexRequest(HttpServletRequest request) {
        CapexRequest capexRequest = new CapexRequest();
        User currentUser = getCurrentUser(request);
        Date nowDate = new Date();
        capexRequest.setCreator(currentUser);
        capexRequest.setCreateDate(nowDate);
        capexRequest.setRequestor(currentUser);
        capexRequest.setRequestDate(nowDate); // add by Jackey 2006-3-17
        capexRequest.setStatus(CapexRequestStatus.DRAFT);
        capexRequest.setType(CapexRequestType.INITIAL);        
        return capexRequest;
    }

    private List getAndCheckDepartmentListFromRequest(Site site, BeanForm capexRequestForm, HttpServletRequest request) {
        List siteList = this.getAndCheckGrantedSiteDeparmentList(request);
        int i = siteList.indexOf(site);
        if (i == -1) {
            throw new ActionException("errors.noSitePermission", site.getName());
        }
        List departmentList = ((Site) siteList.get(i)).getDepartments();
        List result = new ArrayList();
        DepartmentManager dm = ServiceLocator.getDepartmentManager(request);
        String[] department_id = (String[]) capexRequestForm.get("capex_department_id");
        for (int j = 0; j < department_id.length; j++) {
            Integer departmentId = ActionUtils.parseInt(department_id[j]);
            Department d = dm.getDepartment(departmentId);
            if (d == null) {
                throw new ActionException("department.notFound", departmentId);
            }
            if (!departmentList.contains(d)) {
                throw new ActionException("errors.noDepartmentPermission", d.getName());
            }
            result.add(d);
        }
        return result;
    }

    private ActionForward getForwardFor(CapexRequest cr) {
        ActionForward forward = null;
        if (cr == null) {
            forward = new ActionForward("listCapexRequest.do");
        } else {
            if (cr.isEditable()) {
                forward = new ActionForward("editCapexRequest.do?id=" + cr.getId());
            } else {
                forward = new ActionForward("viewCapexRequest.do?id=" + cr.getId());
            }
        }
        forward.setRedirect(true);
        return forward;
    }

    private void checkRequestor(CapexRequest cr, HttpServletRequest request) {
        if (!getCurrentUser(request).equals(cr.getRequestor())) {
            throw new ActionException("capexRequest.requestor.notSelf");
        }
    }

    private void checkEditable(CapexRequest cr) {
        if (!cr.isEditable()) {
            throw new ActionException("capexRequest.cannotEdit");
        }
    }

    private boolean isDraft(HttpServletRequest request) {
        String s = request.getParameter("draft");
        if (s != null && s.equals("true"))
            return true;
        return false;
    }

    private List executeFlow(CapexRequest capexRequest, HttpServletRequest request) {
        try {
            List eList = ServiceLocator.getFlowManager(request).executeApproveFlow(capexRequest);
            return eList;
        } catch (ExecuteFlowEmptyResultException e) {
            throw new BackToInputActionException("flow.execute.notApproverFound");
        } catch (NoAvailableFlowToExecuteException e) {
            throw new BackToInputActionException("flow.execute.notFlowFound");
        }
    }

    private void checkIsLastApprovedRequest(CapexRequest capexRequest) {
        CapexRequest lastApprovedRequest = capexRequest.getCapex().getLastApprovedRequest();
        if (!capexRequest.equals(lastApprovedRequest)) {
            throw new ActionException("capexRequest.notLastRequest");
        }
    }

}