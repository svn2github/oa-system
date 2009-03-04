/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.web.struts.action.business.po;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.util.MessageResources;

import com.aof.model.admin.PurchaseSubCategory;
import com.aof.model.admin.Site;
import com.aof.model.business.BaseApproveRequest;
import com.aof.model.business.po.PaymentScheduleDetail;
import com.aof.model.business.po.PurchaseOrder;
import com.aof.model.business.po.PurchaseOrderAttachment;
import com.aof.model.business.po.PurchaseOrderItem;
import com.aof.model.business.po.PurchaseOrderItemAttachment;
import com.aof.model.metadata.ApproveStatus;
import com.aof.model.metadata.PurchaseOrderStatus;
import com.aof.service.admin.ProjectCodeManager;
import com.aof.service.business.po.PurchaseOrderManager;
import com.aof.utils.PDFReport;
import com.aof.web.struts.action.ActionUtils;
import com.aof.web.struts.action.ServiceLocator;
import com.aof.web.struts.action.business.RechargeAction;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.shcnc.hibernate.PersistentEnum;
import com.shcnc.struts.action.ActionException;

/**
 * Base Class of Action classes for domian model PurchaseOrder
 * @author shilei
 * @version 1.0 (Dec 15, 2005)
 */
public class BasePurchaseOrderAction extends RechargeAction {
    
    protected PurchaseOrderItem getPurchaseOrderItemFromSession(HttpServletRequest request) {
        Integer id = ActionUtils.parseInt(request.getParameter("id"));
        return this.getPurchaseOrderItemFromSession(id,request);
    }
    
    protected PaymentScheduleDetail getPaymentScheduleDetailFromSession(HttpServletRequest request) {
        return getPaymentScheduleDetailFromSession(ActionUtils.parseInt(request.getParameter("id")),request);
    }
    
    protected PaymentScheduleDetail getPaymentScheduleDetailFromSession(Integer id,HttpServletRequest request) {
        if (id == null) throw new ActionException("paymentScheduleDetail.idNotSet");
        List psdList=this.getPaymentScheduleDetailListFromSession(request);
        for (Iterator iter = psdList.iterator(); iter.hasNext();) {
            PaymentScheduleDetail psd = (PaymentScheduleDetail) iter.next();
            if(psd.getId().equals(id)) return psd;
        }
        throw new ActionException("paymentScheduleDetail.notFound",id);
    }
    
    protected PurchaseOrderItem getPurchaseOrderItemFromSession(Integer id,HttpServletRequest request) {
        if (id == null) throw new ActionException("purchaseRequestItem.idNotSet");
        List itemList=this.getPurchaseOrderItemListFromSession(request);
        for (Iterator iter = itemList.iterator(); iter.hasNext();) {
            PurchaseOrderItem item = (PurchaseOrderItem) iter.next();
            if(item.getId().equals(id)) return item;
        }
        throw new ActionException("purchaseRequestItem.notFound",id);
    }
    
    protected PurchaseOrderItem getPurchaseOrderItemFromRequest(HttpServletRequest request) {
        Integer id = ActionUtils.parseInt(request.getParameter("id"));
        if (id == null) throw new ActionException("purchaseOrderItem.idNotSet");
        PurchaseOrderManager pm =ServiceLocator.getPurchaseOrderManager(request);
        PurchaseOrderItem poi=pm.getPurchaseOrderItemWithDetails(id);
        if(poi==null)
            throw new ActionException("purchaseOrderItem.notFound",id);
        return poi;
    }
    
    protected void setEditing(boolean isEdit,HttpServletRequest request)
    {
        request.setAttribute("x_edit", Boolean.valueOf(isEdit));
    }
    
    protected void checkEditable(PurchaseOrder po) {
        if (!po.isEditable())
            throw new ActionException("purchaseOrder.notEditable");
    }
    
    protected void checkPurchaseOrderEditPower(PurchaseOrder po, HttpServletRequest request) {
        this.checkPurchaseOrderViewPower(po,request);
        this.checkEditable(po);
    }

    protected void checkPurchaseOrderViewPower(PurchaseOrder po, HttpServletRequest request) {
        this.checkSite(po.getSite(),request);
        this.checkPurchaserSelf(po,request);
    }

    protected void checkPurchaserSelf(PurchaseOrder po, HttpServletRequest request) {
        if(!po.getPurchaser().equals(this.getCurrentUser(request)))
            throw new ActionException("purchaseOrder.purchaser.notSelf");
    }

    protected PurchaseOrder getPurchaseOrderFromRequest(HttpServletRequest request) 
    {
        String id = this.getPurchaseOrderId(request);
        if(id==null) throw new ActionException("purchaseOrder.idNotSet");
        PurchaseOrderManager purchaseOrderManager = ServiceLocator.getPurchaseOrderManager(request);
        PurchaseOrder purchaseOrder = purchaseOrderManager.getPurchaseOrder(id);
        if (purchaseOrder == null)
            throw new ActionException("purchaseOrder.notFound", id);
        return purchaseOrder;
    }

    protected void putPurchaseOrderDetailsToRequest(PurchaseOrder po, HttpServletRequest request) {
        PurchaseOrderManager pm = ServiceLocator.getPurchaseOrderManager(request);
        if (!isBack(request) ) {
            this.putPurchaseOrderItemListToSession(pm.getPurchaseOrderItemListWithDetails(po),request);
            this.putPaymentScheduleDetailListToSession(pm.getPaymentScheduleDetailList(po),request);
            request.setAttribute("x_purchaseOrderAttachmentList", pm.getPurchaseOrderAttachmentList(po));
        } else {
            request.setAttribute("x_purchaseOrderAttachmentList", this.getPurchaseOrderAttachmentListFromRequest(request));
        }
        request.setAttribute("x_paymentScheduleDetailList", this.getPaymentScheduleDetailListFromSession(request));
        request.setAttribute("x_purchaseOrderItemList", this.getPurchaseOrderItemListFromSession(request));
        List approveList = pm.getPurchaseOrderApproveRequestList(po);
        request.setAttribute("X_APPROVELIST", approveList);
        if (approveList.size() > 0) {
            BaseApproveRequest approveRequest = (BaseApproveRequest)approveList.get(0);
            request.setAttribute("x_canWithDraw", Boolean.valueOf(approveRequest.getStatus().equals(ApproveStatus.WAITING_FOR_APPROVE)));
        }
        
    }
    
    protected List getPaymentScheduleDetailListFromSession(HttpServletRequest request) {
        return (List) request.getSession().getAttribute(
                this.getPaymentScheduleDetailListAttributeName(request));
    }

    protected void putPaymentScheduleDetailListToSession(List scheduleDetailList, HttpServletRequest request) {
        request.getSession().setAttribute(
                this.getPaymentScheduleDetailListAttributeName(request),scheduleDetailList);
    }

    private String getPaymentScheduleDetailListAttributeName(HttpServletRequest request) {
        String po_id=this.getPurchaseOrderId(request);
        if(po_id==null) throw new ActionException("purchaseOrder.idNotSet");
        return "paymentScheduleDetailList_"+this.getPurchaseOrderId(request);
    }

    protected void putPurchaseOrderDetailsForView(PurchaseOrder po, HttpServletRequest request) {
        PurchaseOrderManager pm = ServiceLocator.getPurchaseOrderManager(request);
        request.setAttribute("x_purchaseOrderAttachmentList", pm.getPurchaseOrderAttachmentList(po));
        request.setAttribute("x_purchaseOrderItemList", pm.getPurchaseOrderItemList(po));
        request.setAttribute("x_paymentScheduleDetailList", pm.getPaymentScheduleDetailList(po));
        request.setAttribute("X_APPROVELIST", pm.getPurchaseOrderApproveRequestList(po));
    }


    protected String getPurchaseOrderId(HttpServletRequest request)
    {
        return request.getParameter("id");
    }
    
    protected String getPurchaseOrderItemListAttributeName(HttpServletRequest request)
    {
        String po_id=this.getPurchaseOrderId(request);
        if(po_id==null) throw new ActionException("purchaseOrder.idNotSet");
        return "purchaseOrderItemList_"+this.getPurchaseOrderId(request);
    }
    
    protected void clearPaymentScheduleDetailListFromSession(HttpServletRequest request)
    {
        request.getSession().removeAttribute(this.getPaymentScheduleDetailListAttributeName(request));
    }

    
    protected void clearPurchaseOrderItemListFromSession(HttpServletRequest request)
    {
        request.getSession().removeAttribute(this.getPurchaseOrderItemListAttributeName(request));
    }
    
    protected void putPurchaseOrderItemListToSession(List itemList,HttpServletRequest request) {
        request.getSession().setAttribute(
                this.getPurchaseOrderItemListAttributeName(request),itemList);
    }

    protected List getPurchaseOrderItemListFromSession(HttpServletRequest request) {
        return (List) request.getSession().getAttribute(
                this.getPurchaseOrderItemListAttributeName(request));
    }

    protected List getPurchaseOrderAttachmentListFromRequest(HttpServletRequest request) {
        String[] ids = this.getParameterValues("attachment_id", request);
        List attachmentList = new ArrayList();
        PurchaseOrderManager prm = ServiceLocator.getPurchaseOrderManager(request);
        for (int i = 0; i < ids.length; i++) {
            Integer id = ActionUtils.parseInt(ids[i]);
            PurchaseOrderAttachment pra = prm.getPurchaseOrderAttachment(id);
            attachmentList.add(pra);
        }
        return attachmentList;
    }

    protected List getPurchaseOrderItemAttachmentListFromRequest(HttpServletRequest request) {
        String[] ids = this.getParameterValues("item_attachment_id", request);
        List attachmentList = new ArrayList();
        PurchaseOrderManager prm = ServiceLocator.getPurchaseOrderManager(request);
        for (int i = 0; i < ids.length; i++) {
            Integer id = ActionUtils.parseInt(ids[i]);
            PurchaseOrderItemAttachment poia = prm.getPurchaseOrderItemAttachment(id);
            attachmentList.add(poia);
        }
        return attachmentList;
    }

    protected void putEnumListToRequest(HttpServletRequest request) {
        request.setAttribute("x_statusList", PersistentEnum.getEnumList(PurchaseOrderStatus.class));
    }
    
    protected void putProjectCodeToRequest(Site site, HttpServletRequest request) 
    {
        ProjectCodeManager pcm = ServiceLocator.getProjectCodeManager(request);
        List projectCodeList = pcm.getEnabledProjectCodeListBySite(site);
        request.setAttribute("x_projectCodeList", projectCodeList);
    }


    protected void exportPDF(PurchaseOrder po, HttpServletRequest request, HttpServletResponse response) throws Exception {
        MessageResources messages = getResources(request);
        Locale locale = getLocale(request);
        
        PDFReport report = PDFReport.createReport(
                po.getSite().getId(),
                "purchaseOrder.pdf.title",
                request,
                messages,
                locale);
        
        Document document = report.getDocument();
        Font subTitleFont=PDFReport.getSubTitleFont(); 
        Paragraph p = null;
        Color tColor = new Color(0, 0x33, 0x66);

        PdfPTable table = PDFReport.createTable(new float[] { 1, 2 , 1 , 2 }, 100, 0);
        report.addCell(table, "purchaseOrder.id", tColor, true);
        report.addCell(table, po.getId());
        report.addCell(table, "purchaseOrder.erpNo", tColor, true);
        report.addCell(table, po.getErpNo());
        report.addCell(table, "purchaseOrder.supplier.id", tColor, true);
        report.addCell(table, po.getSupplier().getName());
        report.addCell(table, "purchaseOrder.title", tColor, true);
        report.addCell(table, po.getTitle());
        report.addCell(table, "purchaseOrder.description", tColor, true);
        report.addCell(table, po.getDescription(),3);
        report.addCell(table, "purchaseOrder.category", tColor, true);
        PurchaseSubCategory psc = po.getSubCategory();
        if (psc == null) {
            report.addCell(table, "");
        } else {
            report.addCell(table, psc.getPurchaseCategory().getDescription());
        }
        report.addCell(table, "purchaseOrder.status", tColor, true);
        report.addCell(table, po.getStatus());
        report.addCell(table, "purchaseOrder.subCategory.id", tColor, true);
        if (psc == null) {
            report.addCell(table, null);
        } else {
            report.addCell(table, psc.getDescription(),3);
        }
        document.add(table);
        
        report.addSplitLine();
        
        table = PDFReport.createTable(new float[] { 1, 1 , 1 , 1, 1, 1 }, 100, 0);
        report.addCell(table, "purchaseOrder.amount", tColor, true);
        report.addCell(table, po.getAmount());
        report.addCell(table, "purchaseOrder.currency", tColor, true);
        report.addCell(table, po.getExchangeRate().getCurrency().getCode());
        report.addCell(table, "purchaseOrder.exchangeRate", tColor, true);
        report.addCell(table, po.getExchangeRateValue());
        report.addCell(table, "purchaseOrder.baseAmount", tColor, true);
        report.addCell(table, po.getBaseCurrencyAmount());
        report.addCell(table, "purchaseOrder.baseCurrency.code", tColor, true);
        report.addCell(table, po.getBaseCurrency().getCode(),3);
        report.addCell(table, "purchaseOrder.createUser.id", tColor, true);
        report.addCell(table, po.getCreateUser().getName());
        report.addCell(table, "purchaseOrder.createDate", tColor, true);
        report.addCell(table, po.getCreateDate(),3);
        document.add(table);
        
        PurchaseOrderManager pom =ServiceLocator.getPurchaseOrderManager(request);
        List itemList=(List)request.getAttribute("x_purchaseOrderItemList");
        if (itemList.size()>0) {
            p = new Paragraph(
                    new StringBuffer().append(messages.getMessage(locale, "purchaseOrder.item")).append("\n").toString(), 
                    subTitleFont);
            p.setAlignment(Element.ALIGN_LEFT);
            document.add(p);
            
            for (Iterator itor = itemList.iterator(); itor.hasNext();) {
                report.addSpaceLine(3);
                table = PDFReport.createTable(new float[] { 4 , 4 , 4 , 3 , 4 , 5.5f , 3.5f , 4 , 6 }, 100, 0.5f);
                Color defaultBackgroundColor = table.getDefaultCell().backgroundColor();
                PdfPCell defaultCell = table.getDefaultCell();
                defaultCell.setBackgroundColor(new Color(0x99,0x99,0xff));
                defaultCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                Font headFont = PDFReport.getFont(Font.BOLD, Color.BLACK);
                report.addCell(table, "purchaseOrderItem.item.id", headFont, true);
                report.addCell(table, "purchaseOrderItem.itemSpec", headFont, true);
                report.addCell(table, "purchaseOrderItem.price", headFont, true);
                report.addCell(table, "purchaseOrderItem.quantity", headFont, true);
                report.addCell(table, "purchaseOrderItem.amount", headFont, true);
                report.addCell(table, "purchaseOrderItem.baseCurrencyAmount", headFont, true);
                report.addCell(table, "purchaseOrderItem.purchaseType.code", headFont, true);
                report.addCell(table, "purchaseOrderItem.dueDate", headFont, true);
                report.addCell(table, "purchaseOrderItem.referencedNo", headFont, true);
                defaultCell.setBackgroundColor(defaultBackgroundColor);
                defaultCell.setNoWrap(false);
                
                PurchaseOrderItem item = (PurchaseOrderItem) itor.next();
                defaultCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                if (item.getItem()!=null)
                    report.addCell(table, item.getItem().getSepc());
                else
                    report.addCell(table, "");
                if (item.getItemSpec()!=null)
                    report.addCell(table, item.getItemSpec());
                else
                    report.addCell(table, "");
                defaultCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                report.addCell(table, item.getUnitPrice());
                report.addCell(table, item.getQuantity()+"");
                report.addCell(table, item.getAmount());
                report.addCell(table, item.getBaseCurrencyAmount());
                defaultCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                if (item.getPurchaseType()!=null)
                    report.addCell(table, item.getPurchaseType().getDescription());
                else 
                    report.addCell(table, "");
                defaultCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                report.addCell(table, item.getDueDate());
                if (item.getPurchaseRequestItem()!=null) {
                    report.addCell(table, item.getPurchaseRequestItem().getPurchaseRequest().getId());
                } else {
                    report.addCell(table, "");
                }
                
                
                PurchaseOrderItem poi=pom.getPurchaseOrderItemWithDetails(item.getId());
                report.addRechargeListTableToTable(table,9, poi, poi.getAmount(),poi.getRecharges());
                if (poi.getAttachments().size()>0) {
                    report.addAttachmentListTableToTable(table,9,poi.getAttachments(),
                            "purchaseOrderItem.attachment",
                            "purchaseOrderItemAttachment.title",
                            "purchaseOrderItemAttachment.fileName",
                            "purchaseOrderItemAttachment.uploadDate"
                            );
                }
                document.add(table);
            }
            
        }
        
        /*
        report.addAttachmentListTable((List)request.getAttribute("x_purchaseOrderAttachmentList"),
                "purchaseOrder.attachment",
                "purchaseOrderAttachment.title",
                "purchaseOrderAttachment.fileName",
                "purchaseOrderAttachment.uploadDate");
        */
        
        p = new Paragraph(
                new StringBuffer().append(messages.getMessage(locale, "purchaseOrder.paymentScheduleDetail")).append("\n").toString(), 
                subTitleFont);
        p.setAlignment(Element.ALIGN_LEFT);
        document.add(p);
        report.addSpaceLine(3);
        table = PDFReport.createTable(new float[] { 1, 1 , 1 , 3 }, 100, 0);
        table.getDefaultCell().setNoWrap(false);
        report.addCell(table, "purchaseOrder.receiver", tColor, true);
        if (po.getReceiver()!=null)
            report.addCell(table, po.getReceiver());
        else
            report.addCell(table,"");
        report.addCell(table, "purchaseOrder.receiveAddress", tColor, true);
        if (po.getReceiveAddress()!=null)
            report.addCell(table, po.getReceiveAddress());
        else
            report.addCell(table,"");
        document.add(table);
        List psList=(List)request.getAttribute("x_paymentScheduleDetailList");
        
        table = PDFReport.createTable(new float[] { 4 , 1 , 1.5f }, 100, 0.5f);
        Color defaultBackgroundColor = table.getDefaultCell().backgroundColor();
        PdfPCell defaultCell = table.getDefaultCell();
        defaultCell.setBackgroundColor(new Color(0x99,0x99,0xff));
        defaultCell.setNoWrap(false);
        defaultCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        Font headFont = PDFReport.getFont(Font.BOLD, Color.BLACK);
        report.addCell(table, "paymentScheduleDetail.description", headFont, true);
        report.addCell(table, "paymentScheduleDetail.date", headFont, true);
        report.addCell(table, "paymentScheduleDetail.amount", headFont, true);
        defaultCell.setBackgroundColor(defaultBackgroundColor);
        for (Iterator itor = psList.iterator(); itor.hasNext();) {
            PaymentScheduleDetail ps = (PaymentScheduleDetail) itor.next();
            defaultCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            report.addCell(table, ps.getDescription());
            defaultCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            report.addCell(table, ps.getDate());
            defaultCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            report.addCell(table, ps.getAmount());
        }
        document.add(table);
        
        report.output("purchaseOrder", response);
    }
    
}
