/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.web.struts.action.business.ta;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.taglib.html.Constants;

import com.aof.model.admin.ExchangeRate;
import com.aof.model.admin.Site;
import com.aof.model.admin.Supplier;
import com.aof.model.business.po.PurchaseOrderItem;
import com.aof.model.business.ta.AirTicket;
import com.aof.model.business.ta.query.AirTicketQueryCondition;
import com.aof.model.business.ta.query.AirTicketQueryOrder;
import com.aof.model.metadata.AirTicketStatus;
import com.aof.model.metadata.SingleReturn;
import com.aof.model.metadata.YesNo;
import com.aof.service.business.po.PurchaseOrderManager;
import com.aof.service.business.ta.AirTicketManager;
import com.aof.web.struts.action.ServiceLocator;
import com.aof.web.struts.action.business.RechargeAction;
import com.aof.web.struts.form.business.ta.AirTicketQueryForm;
import com.shcnc.hibernate.PersistentEnum;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.action.ActionUtils;
import com.shcnc.struts.form.BeanForm;
import com.shcnc.struts.form.ComboBox;

public class AirTicketAction extends RechargeAction {
    
    
    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List siteList = this.getAndCheckGrantedSiteDeparmentList(request);
        AirTicketManager atm = ServiceLocator.getAirTicketManager(request);
        
        AirTicketQueryForm queryForm = (AirTicketQueryForm) form;

        ComboBox comboSite = new ComboBox("id", "name");
        comboSite.setValue(queryForm.getSite_id());
        comboSite.setList(siteList);
        queryForm.setSite_id(comboSite.getValue());

        Map conditions = constructQueryMap(queryForm);
        
        List result = new ArrayList();

        Supplier oldSupplier = null;
        ExchangeRate oldExchangeRate = null;
        BigDecimal sum = new BigDecimal(0d);
        
        List airTicketList = null;
        String include_received_air_ticket = queryForm.getInclude_received_air_ticket();
        if (include_received_air_ticket != null && include_received_air_ticket.equals("1")) {
            conditions.put(AirTicketQueryCondition.STATUS_EQ, AirTicketStatus.RECEIVED);
            airTicketList = atm.getAirTicketList(conditions, -1, -1, AirTicketQueryOrder.SUPPLIER, true);
        } else {
            airTicketList = atm.getReceivedAirTicketList(conditions);
        }
        for (Iterator itor = airTicketList.iterator(); itor.hasNext();) {
            AirTicket at = (AirTicket) itor.next();
            PurchaseOrderItem poi = at.getPoItem();
            if (poi.getPurchaseOrder() == null 
                    || (include_received_air_ticket != null && include_received_air_ticket.equals("1"))) {

                Supplier newSupplier = poi.getSupplier();
                ExchangeRate newExchangeRate = poi.getExchangeRate();
                if (!newSupplier.equals(oldSupplier) || !newExchangeRate.equals(oldExchangeRate)) {
                    if (oldSupplier != null) {
                        PurchaseOrderItem t_poi = new PurchaseOrderItem();
                        t_poi.setUnitPrice(sum);
                        result.add(t_poi);
                        sum = new BigDecimal(0d);
                    }
                }
                oldSupplier = newSupplier;
                oldExchangeRate = newExchangeRate;
                sum = sum.add(poi.getUnitPrice());

                poi.setAirTicket(at);
                result.add(poi);
            }
            poi = at.getCancelPoItem();
            if (poi != null 
                    && (poi.getPurchaseOrder() == null 
                            || (include_received_air_ticket != null && include_received_air_ticket.equals("1")))) {

                Supplier newSupplier = poi.getSupplier();
                ExchangeRate newExchangeRate = poi.getExchangeRate();
                if (!newSupplier.equals(oldSupplier) || !newExchangeRate.equals(oldExchangeRate)) {
                    if (oldSupplier != null) {
                        PurchaseOrderItem t_poi = new PurchaseOrderItem();
                        t_poi.setUnitPrice(sum);
                        result.add(t_poi);
                        sum = new BigDecimal(0d);
                    }
                }
                oldSupplier = newSupplier;
                oldExchangeRate = newExchangeRate;
                sum = sum.add(poi.getUnitPrice());

                poi.setAirTicket(at);
                result.add(poi);
            }
        }

        if (oldSupplier != null) {
            PurchaseOrderItem t_poi = new PurchaseOrderItem();
            t_poi.setUnitPrice(sum);
            result.add(t_poi);
        }

        request.setAttribute("x_return", SingleReturn.RETURN);
        request.setAttribute("x_yes", YesNo.YES);
        request.setAttribute("x_purchaseOrderItemList", result);
        request.setAttribute("x_siteList", siteList);
        request.setAttribute("x_tokenName", Constants.TOKEN_KEY);
        request.setAttribute("x_tokenValue", Globals.TRANSACTION_TOKEN_KEY);
        request.setAttribute("x_yesno", PersistentEnum.getEnumList(YesNo.class));
        return mapping.findForward("page");
    }

    private Map constructQueryMap(AirTicketQueryForm queryForm) {
        Map conditions = new HashMap();
        
        //set site condition
        conditions.put(AirTicketQueryCondition.TA_SITE_ID_EQ, new Short(queryForm.getSite_id()));
        
        //set department condition
        String depertmentId = queryForm.getDepartment_id();        
        if (depertmentId != null && depertmentId.trim().length() > 0) {
            conditions.put(AirTicketQueryCondition.TA_DEPERTMENT_ID_EQ, new Short(depertmentId));
        }
        
        //set buy for condition
        String buyFor = queryForm.getBuyFor();
        if (buyFor != null && buyFor.trim().length() > 0) {
            conditions.put(AirTicketQueryCondition.TA_REQUESTOR_NAME_LIKE, buyFor.trim());
        }
        
        //set supplier condition
        String supplierName = queryForm.getSupplier_name();
        if (supplierName != null && supplierName.trim().length() > 0) {
            conditions.put(AirTicketQueryCondition.SUPPLIER_NAME_LIKE, supplierName.trim());            
        }
        
        //set datetime condition from
        String leave_datetime_from = queryForm.getLeave_datetime_from();
        if (leave_datetime_from != null && leave_datetime_from.trim().length() > 0) {
            conditions.put(AirTicketQueryCondition.DEPART_TIME_LT, com.aof.web.struts.action.ActionUtils.getQueryToDateFromDisplayDate(leave_datetime_from));
        }
        
        //set datetime condition to
        String leave_datetime_to = queryForm.getLeave_datetime_to();
        if (leave_datetime_to != null && leave_datetime_to.trim().length() > 0) {
            conditions.put(AirTicketQueryCondition.DEPART_TIME_GT, com.aof.web.struts.action.ActionUtils.getQueryToDateFromDisplayDate(leave_datetime_to));
        }
        
        //set is on travel condition
        String is_on_travel = queryForm.getIs_on_travel();
        if (is_on_travel != null && is_on_travel.trim().length() > 0) {
            conditions.put(AirTicketQueryCondition.TA_IS_ON_TRAVEL_EQ, com.aof.web.struts.action.ActionUtils.parseInt(is_on_travel));
        }                
        
        return conditions;
    }

    public ActionForward createPO(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Site s = this.getAndCheckSite("site_id", request);
        String strIds[] =  request.getParameterValues("id");
        if (strIds != null) {
            AirTicketManager atm = ServiceLocator.getAirTicketManager(request);
            List allIdList = atm.getSiteReceivedAirTicketPoItemIdList(s); 
            Collection ids = new HashSet();
            for (int i = 0; i < strIds.length; i++) {
                ids.add(ActionUtils.parseInt(strIds[i]));
            }
            ids.retainAll(allIdList);
            atm.createMergePO(s, this.getCurrentUser(request), ids);
        }
        return new ActionForward("listAirTicket.do", true);
    }
    
    public ActionForward saveBoardingPass(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        this.getAndCheckSite("site_id", request);
        String strAirtictIds[] =  request.getParameterValues("airticketId");
        String strboardingPassReceiveds[] =  request.getParameterValues("boardingPassReceived");
        if (strAirtictIds != null) {
            AirTicketManager atm = ServiceLocator.getAirTicketManager(request);
            for (int i = 0; i < strAirtictIds.length; i++) {
                AirTicket at = atm.getAirTicket(ActionUtils.parseInt(strAirtictIds[i]));
                if (strboardingPassReceiveds[i].equals(YesNo.YES.toString())) {    
                    if (YesNo.NO.equals(at.getBoardingPassRecevied())) {
                        at.setBoardingPassRecevied(YesNo.YES);
                        atm.updateAirTicket(at);
                    }
                } else {
                    if (YesNo.YES.equals(at.getBoardingPassRecevied())) {
                        at.setBoardingPassRecevied(YesNo.NO);
                        atm.updateAirTicket(at);
                    }
                }
            }
        }
        return new ActionForward("listAirTicket.do", true);
    }
    
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        AirTicket at = this.getAirTicketFromRequest(request);
        Site s = at.getTravelApplication().getDepartment().getSite();
        this.checkSite(s, request);
        PurchaseOrderItem poi = this.getPurchaseOrderItemFromRequest(request);
        this.checkPoItem(poi, request);
        poi.setAirTicket(at);
        request.setAttribute("x_poi", poi);
        BeanForm airTicketForm = (BeanForm) getForm("/updateAirTicket", request);
        if (!isBack(request)) {
            airTicketForm.populateToForm(poi);
        }
        Collection recharges = ServiceLocator.getPurchaseOrderManager(request).getPurchaseOrderItemRecharges(poi);
        this.setRechargeInfoToRequest(poi, recharges, s, airTicketForm, request, false);
        request.setAttribute("X_FORMNAME", "airTicketForm");
        return mapping.findForward("page");
    }

    public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        AirTicket at = this.getAirTicketFromRequest(request);
        Site s = at.getTravelApplication().getDepartment().getSite();
        this.checkSite(s, request);
        PurchaseOrderItem poi = this.getPurchaseOrderItemFromRequest(request);
        this.checkPoItem(poi, request);

        poi.setRecharges( this.getRechargeInfoFromRequest(poi, request));
        ServiceLocator.getPurchaseOrderManager(request).updatePurchaseOrderItemRecharges(poi);

        return mapping.findForward("success");
    }

    private PurchaseOrderItem getPurchaseOrderItemFromRequest(HttpServletRequest request) {
        Integer id = ActionUtils.parseInt(request.getParameter("id"));
        PurchaseOrderManager pom = ServiceLocator.getPurchaseOrderManager(request);
        PurchaseOrderItem poi = pom.getPurchaseOrderItem(id);
        if (poi == null)
            throw new ActionException("purchaseOrderItem.notFound", id);
        return poi;
    }

    private AirTicket getAirTicketFromRequest(HttpServletRequest request) {
        Integer id = ActionUtils.parseInt(request.getParameter("airTicket_id"));
        AirTicketManager airTicketManager = ServiceLocator.getAirTicketManager(request);
        AirTicket airTicket = airTicketManager.getAirTicket(id);
        if (airTicket == null)
            throw new ActionException("airTicket.notFound", id);
        return airTicket;
    }

    private void checkPoItem(PurchaseOrderItem poi, HttpServletRequest request) {
        if (poi.getPurchaseOrder() != null) {
            throw new ActionException("airTicket.poItem.associatedWithPO");
        }
        if (!YesNo.YES.equals(poi.getIsRecharge())) {
            throw new ActionException("airTicket.poItem.isNotRecharge");
        }
        
    }


}