/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.web.struts.action.kpi;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import net.sourceforge.model.admin.Site;
import net.sourceforge.model.kpi.KPISummary;
import net.sourceforge.model.kpi.query.KPISummaryQueryCondition;
import net.sourceforge.model.kpi.query.KPISummaryQueryOrder;
import net.sourceforge.service.admin.SiteManager;
import net.sourceforge.service.kpi.KPIManager;
import net.sourceforge.utils.DateUtils;

import net.sourceforge.web.struts.action.BaseAction;
import net.sourceforge.web.struts.action.ServiceLocator;

/**
 * @author nicebean
 * @version 1.0 (2005-11-23)
 */
public class KPIAction extends BaseAction {

    public ActionForward report(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        SiteManager siteManager = ServiceLocator.getSiteManager(request);
        List siteList = siteManager.getAllEnabledSiteList();
        
        if (siteList != null && siteList.size() > 0) {    
            //get site id that user selected
            String selectedSiteIDStr = request.getParameter("selectedSiteID");
            Site selectedSite = null;
            Integer selectedSiteID = null;
            if (selectedSiteIDStr == null) {
                selectedSite = (Site)siteList.get(0);
                selectedSiteID = selectedSite.getId();
            } else {
                selectedSiteID = new Integer(selectedSiteIDStr);
                for (int i0 = 0; i0 < siteList.size(); i0++) {
                    Site tempSite = (Site)siteList.get(i0);
                    if (tempSite.getId().equals(selectedSiteID)) {
                        selectedSite = tempSite;
                        break;
                    }
                }
                if (selectedSite == null) {
                    selectedSite = (Site)siteList.get(0);
                }
            }
            
            Date currEndDate = DateUtils.getExpireDate(new Date());
            Date currStartDate = DateUtils.getStartDate(new Date());
            Date lastWeek = DateUtils.dateAdd(currStartDate, Calendar.DATE, -7);
            Date lastMonth = DateUtils.dateAdd(currStartDate, Calendar.MONTH, -1);
            
            Map condition = new HashMap();
            condition.put(KPISummaryQueryCondition.SUMMARYDATE_GE, currEndDate);
            condition.put(KPISummaryQueryCondition.SUMMARYDATE_LE, lastMonth);  
            
            KPIManager kpiManager = ServiceLocator.getKPIManager(request);
            List kpiList = kpiManager.getKPISummaryList(condition, -1, -1, KPISummaryQueryOrder.SUMMARYDATE, true);
            
            int numberOfUsers = 0;
            BigDecimal totalResponseTime = new  BigDecimal(0);
            int totalRequestClosed = 0;
            int totalRequestCreatedLastWeek = 0;
            int totalRequestCreatedLastMonth = 0; 
//            int totalCapexCreatedLastWeekForSpecifiedSite = 0;
//            int totalExpenseCreatedLastWeekForSpecifiedSite = 0;
//            int totalPRCreatedLastWeekForSpecifiedSite = 0;
//            int totalTACreatedLastWeekForSpecifiedSite = 0;
//            int totalCapexCreatedLastMonthForSpecifiedSite = 0;
//            int totalExpenseCreatedLastMonthForSpecifiedSite = 0;
//            int totalPRCreatedLastMonthForSpecifiedSite = 0;
//            int totalTACreatedLastMonthForSpecifiedSite = 0;
            
            
            if (kpiList != null && kpiList.size() > 0) {
                for (int i0 = 0; i0 < kpiList.size(); i0++) {
                    KPISummary kpiSummary = (KPISummary)kpiList.get(i0);
                    
                    numberOfUsers += kpiSummary.getLoginUserCount();
                    
                    if (kpiSummary.getClosedCapexNumToday() > 0) {
                        totalResponseTime.add(kpiSummary.getAvgCapexClosedDays());
                        totalRequestClosed += kpiSummary.getClosedCapexNumToday();
                    }
                    
                    if (kpiSummary.getClosedExpenseNumToday() > 0) {
                        totalResponseTime.add(kpiSummary.getAvgExpenseClosedDays());
                        totalRequestClosed += kpiSummary.getClosedExpenseNumToday();
                    }
                    
                    if (kpiSummary.getClosedPRNumToday() > 0) {
                        totalResponseTime.add(kpiSummary.getAvgPRClosedDays());
                        totalRequestClosed += kpiSummary.getClosedPRNumToday();
                    }
                    
                    if (kpiSummary.getClosedTANumToday() > 0) {
                        totalResponseTime.add(kpiSummary.getAvgTAClosedDays());
                        totalRequestClosed += kpiSummary.getClosedTANumToday();
                    }
                    
                    if (kpiSummary.getSummaryDate().before(lastWeek)) {
                        totalRequestCreatedLastWeek += kpiSummary.getCreatedCapexNumToday();
                        totalRequestCreatedLastWeek += kpiSummary.getCreatedExpenseNumToday();
                        totalRequestCreatedLastWeek += kpiSummary.getCreatedPRNumToday();
                        totalRequestCreatedLastWeek += kpiSummary.getCreatedTANumToday();
                        
//                        if (kpiSummary.getSite().equals(selectedSite)) {
//                            totalCapexCreatedLastWeekForSpecifiedSite += kpiSummary.getCreatedCapexNumToday();
//                            totalExpenseCreatedLastWeekForSpecifiedSite += kpiSummary.getCreatedExpenseNumToday();
//                            totalPRCreatedLastWeekForSpecifiedSite += kpiSummary.getCreatedPRNumToday();
//                            totalTACreatedLastWeekForSpecifiedSite += kpiSummary.getCreatedTANumToday();
//                        }
                    }
                    
                    totalRequestCreatedLastMonth += kpiSummary.getCreatedCapexNumToday();
                    totalRequestCreatedLastMonth += kpiSummary.getCreatedExpenseNumToday();
                    totalRequestCreatedLastMonth += kpiSummary.getCreatedPRNumToday();
                    totalRequestCreatedLastMonth += kpiSummary.getCreatedTANumToday();
                    
//                    if (kpiSummary.getSite().equals(selectedSite)) {
//                        totalCapexCreatedLastMonthForSpecifiedSite += kpiSummary.getCreatedCapexNumToday();
//                        totalExpenseCreatedLastMonthForSpecifiedSite += kpiSummary.getCreatedExpenseNumToday();
//                        totalPRCreatedLastMonthForSpecifiedSite += kpiSummary.getCreatedPRNumToday();
//                        totalTACreatedLastMonthForSpecifiedSite += kpiSummary.getCreatedTANumToday();
//                    }
                }
            }
            
            request.setAttribute("x_RunningSiteNums", siteList.size() + "");
            request.setAttribute("x_NumberOfUsers", numberOfUsers + "");            
            request.setAttribute("x_ResponseTimes", totalRequestClosed != 0 ? totalResponseTime.divide(new BigDecimal(totalRequestClosed), 2, BigDecimal.ROUND_HALF_UP) + "" : "");
            request.setAttribute("x_TotalRequestCreatedLastWeek", totalRequestCreatedLastWeek + "");
            request.setAttribute("x_TotalRequestCreatedLastMonth", totalRequestCreatedLastMonth + "");
//            request.setAttribute("x_TotalCapexCreatedLastWeekForSpecifiedSite", totalCapexCreatedLastWeekForSpecifiedSite + "");
//            request.setAttribute("x_TotalExpenseCreatedLastWeekForSpecifiedSite", totalExpenseCreatedLastWeekForSpecifiedSite + "");
//            request.setAttribute("x_TotalPRCreatedLastWeekForSpecifiedSite", totalPRCreatedLastWeekForSpecifiedSite + "");
//            request.setAttribute("x_TotalTACreatedLastWeekForSpecifiedSite", totalTACreatedLastWeekForSpecifiedSite + "");
//            request.setAttribute("x_TotalCreatedLastWeekForSpecifiedSite", 
//                    totalCapexCreatedLastWeekForSpecifiedSite + totalExpenseCreatedLastWeekForSpecifiedSite + 
//                    totalPRCreatedLastWeekForSpecifiedSite + totalTACreatedLastWeekForSpecifiedSite + "");            
//            request.setAttribute("x_TotalCapexCreatedLastMonthForSpecifiedSite", totalCapexCreatedLastMonthForSpecifiedSite + "");
//            request.setAttribute("x_TotalExpenseCreatedLastMonthForSpecifiedSite", totalExpenseCreatedLastMonthForSpecifiedSite + "");
//            request.setAttribute("x_TotalPRCreatedLastMonthForSpecifiedSite", totalPRCreatedLastMonthForSpecifiedSite + "");
//            request.setAttribute("x_TotalTACreatedLastMonthForSpecifiedSite", totalTACreatedLastMonthForSpecifiedSite + "");
//            request.setAttribute("x_TotalCreatedLastMonthForSpecifiedSite", 
//                    totalCapexCreatedLastMonthForSpecifiedSite + totalExpenseCreatedLastMonthForSpecifiedSite + 
//                    totalPRCreatedLastMonthForSpecifiedSite + totalTACreatedLastMonthForSpecifiedSite + ""); 
            
            request.setAttribute("x_SiteList", siteList);
            request.setAttribute("x_SelectedSite", selectedSite);  
            request.setAttribute("x_Top5KPIExpenseCategory", kpiManager.getTop5KPIExpenseCategorySummary(selectedSite));
            request.setAttribute("x_Top5KPIPurchaseCategory", kpiManager.getTop5KPIPurchaseCategorySummary(selectedSite));
        }        
        
        return mapping.findForward("page");
    }

}
