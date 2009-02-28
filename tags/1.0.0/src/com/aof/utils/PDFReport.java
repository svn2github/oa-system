/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.utils;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.util.MessageResources;

import com.aof.model.BaseAttachment;
import com.aof.model.admin.Customer;
import com.aof.model.admin.Department;
import com.aof.model.admin.User;
import com.aof.model.business.BaseApproveRequest;
import com.aof.model.business.BaseRecharge;
import com.aof.model.business.BuyForTarget;
import com.aof.model.business.Rechargeable;
import com.aof.model.metadata.ApproveStatus;
import com.aof.model.metadata.MetadataDetailEnum;
import com.aof.model.metadata.RechargeType;
import com.aof.model.metadata.YesNo;
import com.aof.service.admin.SiteManager;
import com.aof.web.struts.action.ActionUtils;
import com.aof.web.struts.action.ServiceLocator;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

/**
 * 为导出pdf提供众多helper方法
 * 
 * @author nicebean
 * @version 1.0 (2006-01-17)
 */
public class PDFReport {
    
    private static BaseFont baseFont;
    private static Font defaultTitleFont, defaultFont, boldFont , subTitleFont , itemSubTitleFont;
    
    private PDFReport() {
        
    }
    
    static {
        try {
            //baseFont = BaseFont.createFont("MSungStd-Light", "UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED);
            baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        } catch (Exception e) {
            throw new RuntimeException("error create font for pdf", e);
        }
        defaultTitleFont = new Font(baseFont, 16, Font.BOLD);
        defaultFont = new Font(baseFont, 10, Font.NORMAL);
        subTitleFont = getFont(14, Font.BOLD, new Color(0,0,0xFF));
        itemSubTitleFont = getFont(10, Font.BOLD, new Color(0,0,0xFF));
        boldFont = new Font(baseFont, 10, Font.BOLD);
    }

    public static Font getSubTitleFont() {
        return subTitleFont;
    }

    public static Font getDefaultFont() {
        return defaultFont;
    }

    public static Font getDefaultTitleFont() {
        return defaultTitleFont;
    }
    
    public static Font getItemSubTitleFont() {
        return itemSubTitleFont;
    }
    
    public static Font getBoldFont() {
        return boldFont;
    }
    
    public static Font getFont(String strColor) {
        return getFont(Font.NORMAL, strColor);
    }

    public static Font getFont(Color color) {
        return getFont(Font.NORMAL, color);
    }

    public static Font getFont(int style, String strColor) {
        return getFont(10, style, strColor);
    }

    public static Font getFont(int style, Color color) {
        return getFont(10, style, color);
    }

    public static Font getFont(float size, int style, String strColor) {
        if (strColor == null) {
            return new Font(baseFont, size, style);
        }
        Color color = ColorUtils.getColor(strColor);
        return new Font(baseFont, size, style, color);
    }
    
    public static Font getFont(float size, int style, Color color) {
        return new Font(baseFont, size, style, color);
    }
    
    public static PdfPTable createTable(float[] widths) {
        PdfPTable table = new PdfPTable(widths);
        PdfPCell defaultCell= table.getDefaultCell();
        defaultCell.setNoWrap(false);
        defaultCell.setMinimumHeight(16);
        defaultCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        //defaultCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        return table;
        
    }
    
    public static PdfPTable createTable(float[] widths, int totalWidth) {
        PdfPTable table = createTable(widths);
        table.setWidthPercentage(totalWidth);
        return table;
    }
    
    public static PdfPTable createTable(float[] widths, int totalWidth, float borderWidth) {
        PdfPTable table = createTable(widths, totalWidth);
        PdfPCell defaultCell= table.getDefaultCell();
        defaultCell.setBorderWidth(borderWidth);
        return table;
    }

    private Document document;
    private OutputStream out;
    private MessageResources messages;
    private Locale locale;
    private int tempFileIndex;
    
    public static PDFReport createReport(Integer siteId, String titleKey, HttpServletRequest request, MessageResources messages, Locale locale) throws DocumentException, MalformedURLException, IOException {
        return createReport(siteId, titleKey, request, messages, locale, PageSize.A4);
    }

    public static PDFReport createReport(Integer siteId, String titleKey, HttpServletRequest request, MessageResources messages, Locale locale, Rectangle pageSize) throws DocumentException, MalformedURLException, IOException {
        PDFReport report = new PDFReport();
        report.tempFileIndex = SessionTempFile.createNewTempFile(request);
        report.document = new Document(pageSize);
        report.out = new FileOutputStream(SessionTempFile.getTempFile(report.tempFileIndex, request));
        report.messages = messages;
        report.locale = locale;
        
        PdfWriter.getInstance(report.document, report.out);
        report.document.open();

        if (siteId != null) {
            SiteManager sm = ServiceLocator.getSiteManager(request);
            InputStream is = sm.getSiteLogo(siteId);
            if (is != null) {
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                byte[] buf = new byte[512];
                while (true) {
                    int i = is.read(buf);
                    if (i > 0) {
                        os.write(buf, 0, i);
                    } else {
                        break;
                    }
                }
                Image logo = Image.getInstance(os.toByteArray());
                logo.scalePercent(80);
                report.document.add(logo);
                
            }
        }

        Paragraph p = new Paragraph(new StringBuffer().append(messages.getMessage(locale, titleKey)).append("\n\n").toString(), getDefaultTitleFont());
        p.setAlignment(Element.ALIGN_CENTER);
        
        report.document.add(p);
        
        return report;
    }
    
    public Document getDocument() {
        return document;
    }

    public void addSpaceLine(int lineHeight) throws DocumentException {
        Paragraph p = new Paragraph(
                new StringBuffer().append("").append("\n").toString(), 
                getFont(lineHeight,Font.NORMAL,Color.BLACK));
        p.setAlignment(Element.ALIGN_LEFT);
        document.add(p);
        
    }
    
    public void addCell(PdfPTable table, Object content) {
        addCell(table, content, 1, null, null, false);
    }

    public void addCell(PdfPTable table, Object content, boolean resource) {
        addCell(table, content, 1, null, null, resource);
    }

    public void addCell(PdfPTable table, Object content, int colSpan) {
        addCell(table, content, colSpan, null, null, false);
    }
    
    public void addCell(PdfPTable table, Object content, int colSpan, boolean resource) {
        addCell(table, content, colSpan, null, null, resource);
    }

    public void addCell(PdfPTable table, Object content, Color color) {
        addCell(table, content, 1, color, null, false);
    }

    public void addCell(PdfPTable table, Object content, Color color, boolean resource) {
        addCell(table, content, 1, color, null, resource);
    }

    public void addCell(PdfPTable table, Object content, Font font) {
        addCell(table, content, 1, null, font, false);
    }

    public void addCell(PdfPTable table, Object content, Font font, boolean resource) {
        addCell(table, content, 1, null, font, resource);
    }

    public void addCell(PdfPTable table, Object content, int colSpan, Color color) {
        addCell(table, content, colSpan, color, null, false);
    }

    public void addCell(PdfPTable table, Object content, int colSpan, Color color, boolean resource) {
        addCell(table, content, colSpan, color, null, resource);
    }

    public void addCell(PdfPTable table, Object content, int colSpan, Font font) {
        addCell(table, content, colSpan, null, font, false);
    }
    
    public void addCell(PdfPTable table, Object content, int colSpan, Font font, boolean resource) {
        addCell(table, content, colSpan, null, font, resource);
    }

    private void addCell(PdfPTable table, Object content, int colSpan, Color color, Font font, boolean resource) {
        String cellContent = null;
        Font cellFont = null;
        if (content == null) {
            cellContent = "";
        } else if (content instanceof MetadataDetailEnum) {
            MetadataDetailEnum metadata = (MetadataDetailEnum) content;
            cellContent = locale.equals(Locale.ENGLISH) ? metadata.getEngShortDescription() : metadata.getChnShortDescription();
            if (font != null) {
                cellFont = getFont(font.size(), font.style(), metadata.getColor());
            } else {
                cellFont = getFont(metadata.getColor());
            }
        } else if (content instanceof Date) {
            cellContent = ActionUtils.getDisplayDateFromDate((Date)content);
        } else if (content instanceof String) {
            if (resource) {
                cellContent = messages.getMessage(locale, (String) content);
            } else {
                cellContent = (String) content;
            }
        } else {
            if (content instanceof PdfPCell) {
                table.addCell((PdfPCell) content);
                return;
            }
            if (content instanceof Phrase) {
                table.addCell((Phrase) content);
                return;
            }
            if (content instanceof PdfPTable) {
                table.addCell((PdfPTable) content);
                return;
            }
            if (content instanceof Image) {
                table.addCell((Image) content);
                return;
            }
            cellContent = content.toString();
        }
        if (cellFont == null) {
            if (font == null) {
                if (color == null) {
                    cellFont = getDefaultFont();
                } else {
                    cellFont = getFont(color);
                }
            } else {
                cellFont = font;
            }
        }
        table.getDefaultCell().setColspan(colSpan);
        table.addCell(new Phrase(cellContent, cellFont));
        table.getDefaultCell().setColspan(1);
    }
    
    public void output(String fileName, HttpServletResponse response) throws IOException {
        document.close();
        out.close();
        response.sendRedirect("download/" + tempFileIndex + "/" + URLEncoder.encode(fileName, "UTF8") + ".pdf");
    }

    public void addSplitLine() throws DocumentException {
        PdfPTable table = createTable(new float[] { 1 }, 100);
        PdfPCell defaultCell= table.getDefaultCell();
        defaultCell.setBorderWidth(0);
        defaultCell.setBorderWidthBottom(0.1f);
        defaultCell.setFixedHeight(5);
        defaultCell.setBorderColorBottom(new Color(0x3c, 0x78, 0xb5));
        table.addCell("");
        defaultCell.setBorderWidthBottom(0);
        table.addCell("");
        
        document.add(table);
    }

    /*
    public void addAttachmentListTable(Collection attachList, String titleKey, String fileTitleKey, String fileNameKey, String uploadDateKey) throws DocumentException {
        if (!attachList.isEmpty()) {
            Paragraph p = new Paragraph(new StringBuffer().append(messages.getMessage(locale, titleKey)).append("\n").toString(), subTitleFont);
            p.setAlignment(Element.ALIGN_LEFT);
            document.add(p);
            addSpaceLine(3);
            PdfPTable table = PDFReport.createTable(new float[] { 4, 4, 1 }, 100, 0.5f);
            PdfPCell defaultCell = table.getDefaultCell();
            Color defaultBackgroundColor = defaultCell.backgroundColor();
            defaultCell.setBackgroundColor(new Color(0x99, 0x99, 0xff));
            defaultCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            Font headFont = PDFReport.getFont(Font.BOLD, Color.BLACK);
            addCell(table, fileTitleKey, headFont, true);
            addCell(table, fileNameKey, headFont, true);
            addCell(table, uploadDateKey, headFont, true);
            defaultCell.setBackgroundColor(defaultBackgroundColor);
            for (Iterator itor = attachList.iterator(); itor.hasNext();) {
                BaseAttachment attach = (BaseAttachment) itor.next();
                addCell(table, attach.getDescription());
                addCell(table, attach.getFileName());
                addCell(table, attach.getUploadDate());
            }
            document.add(table);
        }
    }
    */

    public void addAttachmentListTableToTable(PdfPTable parentTable,int tableColumnCount,Collection attachList, String titleKey, String fileTitleKey, String fileNameKey, String uploadDateKey) {
        if (!attachList.isEmpty()) {
            parentTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
            addCell(parentTable,titleKey , tableColumnCount , itemSubTitleFont ,true);
            PdfPTable table = PDFReport.createTable(new float[] { 4, 4, 1 }, 100, 0.5f);
            PdfPCell defaultCell = table.getDefaultCell();
            Color defaultBackgroundColor = defaultCell.backgroundColor();
            defaultCell.setBackgroundColor(new Color(0xDD, 0xDD, 0xFF));
            defaultCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            Font headFont = PDFReport.getFont(Font.BOLD, Color.BLACK);
            addCell(table, fileTitleKey, headFont, true);
            addCell(table, fileNameKey, headFont, true);
            addCell(table, uploadDateKey, headFont, true);
            defaultCell.setBackgroundColor(defaultBackgroundColor);
            for (Iterator itor = attachList.iterator(); itor.hasNext();) {
                BaseAttachment attach = (BaseAttachment) itor.next();
                addCell(table, attach.getDescription());
                addCell(table, attach.getFileName());
                addCell(table, attach.getUploadDate());
            }
            AddTableToTable(parentTable,table,tableColumnCount);
        }
    }
    
    public void addApproveListTable(Collection approveList) throws DocumentException {
        Paragraph p = new Paragraph(new StringBuffer().append(messages.getMessage(locale, "approveRequest.approveList.title")).append("\n").toString(), subTitleFont);
        p.setAlignment(Element.ALIGN_LEFT);
        document.add(p);
        addSpaceLine(3);
        PdfPTable table = PDFReport.createTable(new float[] { 1, 2, 2, 2, 6 }, 100, 0.5f);
        PdfPCell defaultCell = table.getDefaultCell();
        Color defaultBackgroundColor = defaultCell.backgroundColor();
        defaultCell.setBackgroundColor(new Color(0x99, 0x99, 0xff));
        defaultCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        Font headFont = PDFReport.getFont(Font.BOLD, Color.BLACK);
        addCell(table, "#", headFont);
        addCell(table, "approveRequest.approveList.approver", headFont, true);
        addCell(table, "approveRequest.approveList.status", headFont, true);
        addCell(table, "approveRequest.approveList.date", headFont, true);
        addCell(table, "approveRequest.approveList.comment", headFont, true);
        int i = 1;
        for (Iterator itor = approveList.iterator(); itor.hasNext(); i++) {
            BaseApproveRequest approveRequest = (BaseApproveRequest) itor.next();
            if (ApproveStatus.WAITING_FOR_APPROVE.equals(approveRequest.getStatus())) {
                defaultCell.setBackgroundColor(new Color(0xff, 0xff, 0x99));
            } else {
                defaultCell.setBackgroundColor(defaultBackgroundColor);
            }
            defaultCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            addCell(table, String.valueOf(i));
            User approver = approveRequest.getApprover();
            User actualApprover = approveRequest.getActualApprover();
            defaultCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            if (actualApprover == null || actualApprover.equals(approver)) {
                addCell(table, approver.getName());
            } else {
                addCell(table, new StringBuffer().append(approver.getName()).append('/').append(actualApprover.getName()));
            }
            defaultCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            addCell(table, approveRequest.getStatus());
            addCell(table, approveRequest.getApproveDate());
            defaultCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            addCell(table, approveRequest.getComment());
        }
        document.add(table);
        
    }

    public void addRechargeListTable(Rechargeable target, BigDecimal amount, Collection rechargeList) throws DocumentException {
        if (YesNo.YES.equals(target.getIsRecharge())) {
            Paragraph p = new Paragraph(new StringBuffer().append(messages.getMessage(locale, "recharge.buyFor.recharge")).append("\n").toString(), subTitleFont);
            p.setAlignment(Element.ALIGN_LEFT);
            document.add(p);
            addSpaceLine(3);
            PdfPTable table = PDFReport.createTable(new float[] { 1, 4, 2, 2 }, 100, 0.5f);
            PdfPCell defaultCell = table.getDefaultCell();
            Color defaultBackgroundColor = defaultCell.backgroundColor();
            defaultCell.setBackgroundColor(new Color(0x99, 0x99, 0xff));
            defaultCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            Font headFont = PDFReport.getFont(Font.BOLD, Color.BLACK);
            addCell(table, "#", headFont);
            addCell(table, "recharge.user", headFont, true);
            addCell(table, "recharge.amount", headFont, true);
            addCell(table, "recharge.percentage", headFont, true);
            defaultCell.setBackgroundColor(defaultBackgroundColor);
            //BigDecimal totalPercentage = new BigDecimal(0d);
            int i = 1;
            for (Iterator itor = rechargeList.iterator(); itor.hasNext(); i++) {
                BaseRecharge recharge = (BaseRecharge) itor.next();
                defaultCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                addCell(table, String.valueOf(i));
                defaultCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                Customer c = recharge.getCustomer();
                if (c != null) {
                    Phrase content = new Phrase();
                    content.add(new Phrase(c.getDescription(), defaultFont));
                    content.add(new Phrase(" (", defaultFont));
                    content.add(getPhrase(c.getType().getRechargeType()));
                    content.add(new Phrase(")", defaultFont));
                    addCell(table, content);
                } else {
                    Department d = recharge.getPersonDepartment();
                    User u = recharge.getPerson();
                    Phrase content = new Phrase();
                    content.add(new Phrase(d.getName(),defaultFont));
                    content.add(new Phrase(" \\ ",defaultFont));
                    if (u != null) {
                        content.add(new Phrase(u.getName(),defaultFont));
                    }
                    content.add(new Phrase(" (", defaultFont));
                    content.add(getPhrase(RechargeType.PERSON));
                    content.add(new Phrase(")", defaultFont));
                    addCell(table, content);
                }
                //BigDecimal percentage = recharge.getAmount();
                BigDecimal ramount = recharge.getAmount();
                defaultCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                addCell(table, ramount);
                addCell(table, ramount.multiply(new BigDecimal(100)).divide(amount, 2, BigDecimal.ROUND_HALF_EVEN));
                //totalPercentage = totalPercentage.add(percentage);
            }
            defaultCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            addCell(table, null);
            addCell(table, "recharge.total", boldFont, true);
            addCell(table, amount);
            addCell(table, "100.00");
            document.add(table);
        } else {
            if (target instanceof BuyForTarget) {
                BuyForTarget bufForTarget = (BuyForTarget) target;
                Paragraph p = new Paragraph(new StringBuffer().append(messages.getMessage(locale, "recharge.buyFor")).append("\n").toString(), subTitleFont);
                p.setAlignment(Element.ALIGN_LEFT);
                document.add(p);
    
                Color tColor = new Color(0, 0x33, 0x66);
    
                PdfPTable table = PDFReport.createTable(new float[] { 1, 2 , 1 , 2 }, 100, 0);
                addCell(table, "recharge.buyFor.department", tColor, true);
                Department d = bufForTarget.getBuyForDepartment();
                if (d != null) {
                    addCell(table, d.getName());
                } else {
                    addCell(table, null);
                }
                addCell(table, "recharge.buyFor.user", tColor, true);
                User u = bufForTarget.getBuyForUser();
                if (u != null) {
                    addCell(table, u.getName());
                } else {
                    addCell(table, null);
                }
                document.add(table);
            }
        }
    }

    public void addRechargeListTableToTable(PdfPTable parentTable,int tableColumnCount,Rechargeable target, BigDecimal amount, Collection rechargeList) {
        if (YesNo.YES.equals(target.getIsRecharge())) {
            parentTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
            addCell(parentTable,"recharge.buyFor.recharge", tableColumnCount , itemSubTitleFont ,true);
            PdfPTable table = PDFReport.createTable(new float[] { 1, 4, 2, 2 }, 100, 0.5f);
            PdfPCell defaultCell = table.getDefaultCell();
            Color defaultBackgroundColor = defaultCell.backgroundColor();
            defaultCell.setBackgroundColor(new Color(0xDD, 0xDD, 0xFF));
            defaultCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            Font headFont = PDFReport.getFont(Font.BOLD, Color.BLACK);
            addCell(table, "#", headFont);
            addCell(table, "recharge.user", headFont, true);
            addCell(table, "recharge.amount", headFont, true);
            addCell(table, "recharge.percentage", headFont, true);
            defaultCell.setBackgroundColor(defaultBackgroundColor);
            //BigDecimal totalPercentage = new BigDecimal(0d);
            int i = 1;
            for (Iterator itor = rechargeList.iterator(); itor.hasNext(); i++) {
                BaseRecharge recharge = (BaseRecharge) itor.next();
                defaultCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                addCell(table, String.valueOf(i));
                defaultCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                Customer c = recharge.getCustomer();
                if (c != null) {
                    Phrase content = new Phrase();
                    content.add(new Phrase(c.getDescription(), defaultFont));
                    content.add(new Phrase(" (", defaultFont));
                    content.add(getPhrase(c.getType().getRechargeType()));
                    content.add(new Phrase(")", defaultFont));
                    addCell(table, content);
                } else {
                    Department d = recharge.getPersonDepartment();
                    User u = recharge.getPerson();
                    Phrase content = new Phrase();
                    content.add(new Phrase(d.getName(),defaultFont));
                    content.add(new Phrase(" \\ ",defaultFont));
                    if (u != null) {
                        content.add(new Phrase(u.getName(),defaultFont));
                    }
                    content.add(new Phrase(" (", defaultFont));
                    content.add(getPhrase(RechargeType.PERSON));
                    content.add(new Phrase(")", defaultFont));
                    addCell(table, content);
                }
                BigDecimal ramount = recharge.getAmount();
                defaultCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                addCell(table, ramount);
                addCell(table, ramount.multiply(new BigDecimal(100)).divide(amount, 2, BigDecimal.ROUND_HALF_EVEN));
                //totalPercentage = totalPercentage.add(amount.multiply(new BigDecimal(100)).divide(ramount, 2, BigDecimal.ROUND_HALF_EVEN));
            }
            defaultCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            addCell(table, null);
            addCell(table, "recharge.total", boldFont, true);
            addCell(table, amount);
            addCell(table, "100");
            AddTableToTable(parentTable,table,tableColumnCount);
        } else {
            if (target instanceof BuyForTarget) {
                BuyForTarget bufForTarget = (BuyForTarget) target;
                Color tColor = new Color(0, 0x33, 0x66);
                PdfPTable table = PDFReport.createTable(new float[] { 1, 2 , 1 , 2 }, 100, 0);
                addCell(table, "recharge.buyFor", 4 , itemSubTitleFont, true);
                addCell(table, "recharge.buyFor.department", tColor, true);
                Department d = bufForTarget.getBuyForDepartment();
                if (d != null) {
                    addCell(table, d.getName());
                } else {
                    addCell(table, null);
                }
                addCell(table, "recharge.buyFor.user", tColor, true);
                User u = bufForTarget.getBuyForUser();
                if (u != null) {
                    addCell(table, u.getName());
                } else {
                    addCell(table, null);
                }
                AddTableToTable(parentTable,table,tableColumnCount);
            }
        }
    }

    public static void AddTableToTable(PdfPTable parentTable,PdfPTable table,int tableColumnCount) {
        PdfPCell pCell = parentTable.getDefaultCell();
        int horizontalAlignment = pCell.getHorizontalAlignment();
        int verticalAlignment = pCell.getVerticalAlignment();
        float paddingTop = pCell.getPaddingTop();
        float paddingBottom = pCell.getPaddingBottom();
        float paddingLeft = pCell.getPaddingLeft();
        float paddingRight = pCell.getPaddingRight();
        pCell.setVerticalAlignment(Element.ALIGN_TOP);
        pCell.setColspan(tableColumnCount);
        pCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pCell.setPaddingLeft(0);
        pCell.setPaddingRight(0);
        pCell.setPaddingTop(0);
        pCell.setPaddingBottom(0);
        table.setExtendLastRow(true);
        parentTable.addCell(table);
        pCell.setHorizontalAlignment(horizontalAlignment);
        pCell.setVerticalAlignment(verticalAlignment);
        pCell.setColspan(1);
        pCell.setPaddingLeft(paddingLeft);
        pCell.setPaddingRight(paddingRight);
        pCell.setPaddingTop(paddingTop);
        pCell.setPaddingBottom(paddingBottom);
    }
    
    private Phrase getPhrase(MetadataDetailEnum metadata) {
        String content = locale.equals(Locale.ENGLISH) ? metadata.getEngShortDescription() : metadata.getChnShortDescription();
        Font font = getFont(metadata.getColor());
        return new Phrase(content, font);
        
    }
}
