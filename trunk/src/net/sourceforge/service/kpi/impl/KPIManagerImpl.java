package net.sourceforge.service.kpi.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sourceforge.dao.kpi.KPISummaryDAO;
import net.sourceforge.model.admin.ExpenseCategory;
import net.sourceforge.model.admin.PurchaseCategory;
import net.sourceforge.model.admin.Site;
import net.sourceforge.model.business.expense.Expense;
import net.sourceforge.model.business.expense.query.ExpenseQueryCondition;
import net.sourceforge.model.business.expense.query.ExpenseQueryOrder;
import net.sourceforge.model.business.pr.CapexRequest;
import net.sourceforge.model.business.pr.PurchaseRequest;
import net.sourceforge.model.business.pr.query.CapexRequestQueryCondition;
import net.sourceforge.model.business.pr.query.PurchaseRequestQueryCondition;
import net.sourceforge.model.business.pr.query.PurchaseRequestQueryOrder;
import net.sourceforge.model.business.ta.TravelApplication;
import net.sourceforge.model.business.ta.query.TravelApplicationQueryCondition;
import net.sourceforge.model.kpi.KPIExpenseCategorySummary;
import net.sourceforge.model.kpi.KPIPurchaseCategorySummary;
import net.sourceforge.model.kpi.KPISummary;
import net.sourceforge.model.kpi.query.KPISummaryQueryOrder;
import net.sourceforge.service.BaseManager;
import net.sourceforge.service.admin.SiteManager;
import net.sourceforge.service.business.expense.ExpenseManager;
import net.sourceforge.service.business.pr.CapexManager;
import net.sourceforge.service.business.pr.PurchaseRequestManager;
import net.sourceforge.service.business.ta.TravelApplicationManager;
import net.sourceforge.service.kpi.KPIManager;
import net.sourceforge.utils.DateUtils;
import net.sourceforge.web.domain.SessionList;
import net.sourceforge.web.struts.action.ActionUtils;

public class KPIManagerImpl extends BaseManager implements KPIManager {
    
    //private Log log = LogFactory.getLog(KPIManagerImpl.class);
    
    private KPISummaryDAO kpiSummaryDAO;
    
    private SiteManager siteManager;
    
    private CapexManager capexManager;
    
    private ExpenseManager expenseManager;
    
    private PurchaseRequestManager purchaseRequestManager;
    
    private TravelApplicationManager travelApplicationManager;
    
    public void setKpiSummaryDAO(KPISummaryDAO dao) {
        this.kpiSummaryDAO = dao;
    }
    
    public void setSiteManager(SiteManager siteManager) {
        this.siteManager = siteManager;
    }
    
    /**
     * @param capexManager The capexManager to set.
     */
    public void setCapexManager(CapexManager capexManager) {
        this.capexManager = capexManager;
    }

    /**
     * @param expenseManager The expenseManager to set.
     */
    public void setExpenseManager(ExpenseManager expenseManager) {
        this.expenseManager = expenseManager;
    }

    /**
     * @param purchaseRequestManager The purchaseRequestManager to set.
     */
    public void setPurchaseRequestManager(PurchaseRequestManager purchaseRequestManager) {
        this.purchaseRequestManager = purchaseRequestManager;
    }

    /**
     * @param travelApplicationManager The travelApplicationManager to set.
     */
    public void setTravelApplicationManager(TravelApplicationManager travelApplicationManager) {
        this.travelApplicationManager = travelApplicationManager;
    }  

    public void doSummary() {
        
        KPISummary kpiSummary = new KPISummary();
        //get totalUserVisitCount
        int totalUserVisitCount = SessionList.getTotalUserVisitCount();
        if (totalUserVisitCount > 0) { //totalUserVisitCount great than zero we do the summary  
            
            Date currDate = new Date();
            Date currDateStart = ActionUtils.getStartDate(currDate);
            Date currDateEnd = ActionUtils.getExpireDate(currDate);
            
            kpiSummary.setSummaryDate(currDateStart);
            kpiSummary.setLoginUserCount(totalUserVisitCount);
            
            //ta
            Object[] requestCloseNumAndAvgCloeseTime = calculateTACloseNumAndAvgCloseTime(currDateStart, currDateEnd);            
            kpiSummary.setClosedTANumToday(((Integer)requestCloseNumAndAvgCloeseTime[0]).intValue());
            kpiSummary.setAvgTAClosedDays(((BigDecimal)requestCloseNumAndAvgCloeseTime[1]).setScale(2, BigDecimal.ROUND_HALF_UP));            
            
            //capex
            requestCloseNumAndAvgCloeseTime = calculateCapexCloseNumAndAvgCloseTime(currDateStart, currDateEnd);
            kpiSummary.setClosedCapexNumToday(((Integer)requestCloseNumAndAvgCloeseTime[0]).intValue());
            kpiSummary.setAvgCapexClosedDays(((BigDecimal)requestCloseNumAndAvgCloeseTime[1]).setScale(2, BigDecimal.ROUND_HALF_UP));
            
            //pr
            requestCloseNumAndAvgCloeseTime = calculatePRCloseNumAndAvgCloseTime(currDateStart, currDateEnd);
            kpiSummary.setClosedPRNumToday(((Integer)requestCloseNumAndAvgCloeseTime[0]).intValue());
            kpiSummary.setAvgPRClosedDays(((BigDecimal)requestCloseNumAndAvgCloeseTime[1]).setScale(2, BigDecimal.ROUND_HALF_UP));
            
            //expense
            requestCloseNumAndAvgCloeseTime = calculateExpenseCloseNumAndAvgCloseTime(currDateStart, currDateEnd);
            kpiSummary.setClosedExpenseNumToday(((Integer)requestCloseNumAndAvgCloeseTime[0]).intValue());
            kpiSummary.setAvgExpenseClosedDays(((BigDecimal)requestCloseNumAndAvgCloeseTime[1]).setScale(2, BigDecimal.ROUND_HALF_UP));
            
            //
            kpiSummary.setCreatedTANumToday(calculateTACreateNum(currDateStart, currDateEnd));
            kpiSummary.setCreatedCapexNumToday(calculateCapexCreateNum(currDateStart, currDateEnd));
            kpiSummary.setCreatedPRNumToday(calculatePRCreateNum(currDateStart, currDateEnd));
            kpiSummary.setCreatedExpenseNumToday(calculateExpenseCreateNum(currDateStart, currDateEnd));            
            
            kpiSummaryDAO.insertKPISummary(kpiSummary);  
            
            summaryRequestForAllSite(currDateStart, currDateEnd, kpiSummary);
            
            SessionList.resetTotalUserVisitCount(); //reset the UserVisitCount to zero
        }        
    }
    
    private void summaryRequestForAllSite(Date currDateStart, Date currDateEnd, KPISummary kpiSummary) {        
        List siteList = siteManager.getAllEnabledSiteList();
        //fetch each site
        if (siteList != null && siteList.size() > 0) {
            for (int i0 = 0; i0 < siteList.size(); i0++) {
                Site site = (Site)siteList.get(i0);
                
                //
                Object[][] obj1= calculatePurchaseRequestCreateNum(currDateStart, currDateEnd, site);
                if (obj1 != null) {
                    for (int j0 = 0; j0 < obj1.length; j0++) {
                        PurchaseCategory purchaseCategory = (PurchaseCategory)obj1[j0][0];
                        Integer requestCreatedToday = (Integer)obj1[j0][1];
                        
                        KPIPurchaseCategorySummary kpiPurchaseCategorySummary = new KPIPurchaseCategorySummary();
                        kpiPurchaseCategorySummary.setKpiSummary(kpiSummary);
                        kpiPurchaseCategorySummary.setSite(site);
                        kpiPurchaseCategorySummary.setSummaryDate(currDateStart);
                        kpiPurchaseCategorySummary.setPurchaseCategory(purchaseCategory);
                        kpiPurchaseCategorySummary.setPurchaseRequestCreatedToday(requestCreatedToday != null ? requestCreatedToday.intValue() : 0);
                    }
                }
                
                //
                Object[][] obj2= calculateExpenseRequestCreateNum(currDateStart, currDateEnd, site);                
                if (obj2 != null) {
                    for (int k0 = 0; k0 < obj2.length; k0++) {
                        ExpenseCategory expenseCategory = (ExpenseCategory)obj2[k0][0];
                        Integer requestCreatedToday = (Integer)obj2[k0][1];
                        
                        KPIExpenseCategorySummary kpiExpenseCategorySummary = new KPIExpenseCategorySummary();
                        kpiExpenseCategorySummary.setKpiSummary(kpiSummary);
                        kpiExpenseCategorySummary.setSite(site);
                        kpiExpenseCategorySummary.setSummaryDate(currDateStart);
                        kpiExpenseCategorySummary.setExpenseCategory(expenseCategory);
                        kpiExpenseCategorySummary.setExpenseRequestCreatedToday(requestCreatedToday != null ? requestCreatedToday.intValue() : 0);
                    }
                }
            }
        }
    }
    
    private Object[] calculateTACloseNumAndAvgCloseTime(Date specifyDateStart, Date specifyDateEnd) {
        Object[] numAndTime = new Object[2];        
        
        //counting request num & avrage cloes time during startdate and enddate                           
        Map condition = new HashMap();
                       
        //ta
        condition.put(TravelApplicationQueryCondition.APPROVEDATE_LE, specifyDateStart);
        condition.put(TravelApplicationQueryCondition.APPROVEDATE_GE, specifyDateEnd);
        List list = travelApplicationManager.getTravelApplicationList(condition, -1, -1, null, true);
        if (list != null && list.size() > 0) {
            int dateDiff = 0;
            for (int i0 =0; i0 < list.size(); i0++) {
                TravelApplication ta = (TravelApplication)list.get(i0);
                dateDiff += DateUtils.getDateDiff(ta.getCreateDate(), ta.getApproveDate());
            }
            numAndTime[0] = new Integer(list.size());        
            numAndTime[1] = new BigDecimal(dateDiff / list.size());
        } else {
            numAndTime[0] = new Integer(0); 
            numAndTime[1] = new BigDecimal(0);
        }
        return numAndTime;
    }
    
    private Object[] calculateCapexCloseNumAndAvgCloseTime(Date specifyDateStart, Date specifyDateEnd) {
        Object[] numAndTime = new Object[2];        
        
        //counting request num & avrage cloes time during startdate and enddate                            
        Map condition = new HashMap();
        
        //capex
        condition.put(CapexRequestQueryCondition.APPROVEDATE_LE, specifyDateStart);
        condition.put(CapexRequestQueryCondition.APPROVEDATE_GE, specifyDateEnd);
        List list = capexManager.getCapexRequestList(condition, -1, -1, null, true);
        if (list != null && list.size() > 0) {
            int dateDiff = 0;
            for (int i0 =0; i0 < list.size(); i0++) {
                CapexRequest cr = (CapexRequest)list.get(i0);
                dateDiff += DateUtils.getDateDiff(cr.getCreateDate(), cr.getApproveDate());
            }
            numAndTime[0] = new Integer(list.size());        
            numAndTime[1] = new BigDecimal(dateDiff / list.size());
        } else {
            numAndTime[0] = new Integer(0); 
            numAndTime[1] = new BigDecimal(0);
        }
        
        return numAndTime;
    }
    
    private Object[] calculatePRCloseNumAndAvgCloseTime(Date specifyDateStart, Date specifyDateEnd) {
        Object[] numAndTime = new Object[2];        
        
        //counting request num & avrage cloes time during startdate and enddate                            
        Map condition = new HashMap();
       
        //pr
        condition.put(PurchaseRequestQueryCondition.APPROVEDATE_LE, specifyDateStart);
        condition.put(PurchaseRequestQueryCondition.APPROVEDATE_GE, specifyDateEnd);
        List list = purchaseRequestManager.getPurchaseRequestList(condition, -1, -1, null, true);
        if (list != null && list.size() > 0) {
            int dateDiff = 0;
            for (int i0 =0; i0 < list.size(); i0++) {
                PurchaseRequest pr = (PurchaseRequest)list.get(i0);
                dateDiff += DateUtils.getDateDiff(pr.getCreateDate(), pr.getApproveDate());
            }
            numAndTime[0] = new Integer(list.size());        
            numAndTime[1] = new BigDecimal(dateDiff / list.size());
        } else {
            numAndTime[0] = new Integer(0); 
            numAndTime[1] = new BigDecimal(0);
        }        
                
        return numAndTime;
    }
    
    private Object[] calculateExpenseCloseNumAndAvgCloseTime(Date specifyDateStart, Date specifyDateEnd) {
        Object[] numAndTime = new Object[2];        
        
        //counting request num & avrage cloes time during startdate and enddate                             
        Map condition = new HashMap();
               
        //expense      
        condition.clear();
        condition.put(ExpenseQueryCondition.APPROVEDATE_LE, specifyDateStart);
        condition.put(ExpenseQueryCondition.APPROVEDATE_GE, specifyDateEnd);
        List list = expenseManager.getExpenseList(condition, -1, -1, null, true);
        if (list != null && list.size() > 0) {
            int dateDiff = 0;
            for (int i0 =0; i0 < list.size(); i0++) {
                Expense ex = (Expense)list.get(i0);
                dateDiff += DateUtils.getDateDiff(ex.getCreateDate(), ex.getApproveDate());
            }
            numAndTime[0] = new Integer(list.size());        
            numAndTime[1] = new BigDecimal(dateDiff / list.size());
        } else {
            numAndTime[0] = new Integer(0); 
            numAndTime[1] = new BigDecimal(0);
        }
        return numAndTime;
    }
    
    private int calculateTACreateNum(Date specifyDateStart, Date specifyDateEnd) {
        Map condition = new HashMap();                
        condition.put(TravelApplicationQueryCondition.CREATEDATE_LE, specifyDateStart);
        condition.put(TravelApplicationQueryCondition.CREATEDATE_GE, specifyDateEnd);
        return travelApplicationManager.getTravelApplicationListCount(condition);
    }
    
    private int calculateCapexCreateNum(Date specifyDateStart, Date specifyDateEnd) {               
        Map condition = new HashMap();
        condition.put(CapexRequestQueryCondition.CREATEDATE_LE, specifyDateStart);
        condition.put(CapexRequestQueryCondition.CREATEDATE_GE, specifyDateEnd);
        return capexManager.getCapexRequestListCount(condition);      
    }

    private int calculatePRCreateNum(Date specifyDateStart, Date specifyDateEnd) {
                
        Map condition = new HashMap();        
        condition.put(PurchaseRequestQueryCondition.CREATEDATE_LE, specifyDateStart);
        condition.put(PurchaseRequestQueryCondition.CREATEDATE_GE, specifyDateEnd);
        return purchaseRequestManager.getPurchaseRequestListCount(condition);                
    }

    private int calculateExpenseCreateNum(Date specifyDateStart, Date specifyDateEnd) {

        Map condition = new HashMap();
        condition.put(ExpenseQueryCondition.CREATEDATE_LE, specifyDateStart);
        condition.put(ExpenseQueryCondition.CREATEDATE_GE, specifyDateEnd);
        return expenseManager.getExpenseListCount(condition);        
    }
    
    private Object[][] calculatePurchaseRequestCreateNum(Date specifyDateStart, Date specifyDateEnd, Site site) {
        Map condition = new HashMap();
        condition.put(PurchaseRequestQueryCondition.CREATEDATE_LE, specifyDateStart);
        condition.put(PurchaseRequestQueryCondition.CREATEDATE_GE, specifyDateEnd);
        condition.put(PurchaseRequestQueryCondition.SITE_ID_EQ, site.getId());
        List list = purchaseRequestManager.getPurchaseRequestList(condition, -1, -1, PurchaseRequestQueryOrder.PURCHASER_CATEGORY, true);
        if (list != null && list.size() > 0) {
            List purchaseCtgyList = new ArrayList();
            List createNumList = new ArrayList();
            PurchaseCategory oldPurchaseCtgyID = null;
            PurchaseCategory newPurchaseCtgyID = null;
            int createNum = 0;
            for (int i0 =0; i0 < list.size(); i0++) {
                PurchaseRequest pr = (PurchaseRequest)list.get(i0);
                newPurchaseCtgyID = pr.getPurchaseSubCategory().getPurchaseCategory();
                
                if (!newPurchaseCtgyID.equals(oldPurchaseCtgyID)) {
                    if (createNum != 0) {
                        purchaseCtgyList.add(oldPurchaseCtgyID);
                        createNumList.add(new Integer(createNum));
                    }
                    oldPurchaseCtgyID = newPurchaseCtgyID;                    
                    createNum = 0;
                }                 
                createNum++;
            }
            if (createNum != 0) {
                purchaseCtgyList.add(oldPurchaseCtgyID);
                createNumList.add(new Integer(createNum));
            }
            
            if (purchaseCtgyList != null && purchaseCtgyList.size() > 0) {
                Object[][] obj = new Object[purchaseCtgyList.size()][2];
                
                for (int j0 = 0; j0 < purchaseCtgyList.size(); j0++) {
                    obj[j0][0] = purchaseCtgyList.get(j0);
                    obj[j0][1] = createNumList.get(j0);
                }
                
                return obj;
            }
        }   
                       
        return null;
    }
    
    private Object[][] calculateExpenseRequestCreateNum(Date specifyDateStart, Date specifyDateEnd, Site site) {
        Map condition = new HashMap();
        condition.put(ExpenseQueryCondition.CREATEDATE_LE, specifyDateStart);
        condition.put(ExpenseQueryCondition.CREATEDATE_GE, specifyDateEnd);
        condition.put(ExpenseQueryCondition.SITE_ID_EQ, site.getId());
        List list = expenseManager.getExpenseList(condition, -1, -1, ExpenseQueryOrder.CATEGORY, true);
        if (list != null && list.size() > 0) {
            List expenseCtgyList = new ArrayList();
            List createNumList = new ArrayList();
            ExpenseCategory oldExpenseCtgyID = null;
            ExpenseCategory newExpenseCtgyID = null;
            int createNum = 0;
            for (int i0 =0; i0 < list.size(); i0++) {
                Expense ex = (Expense)list.get(i0);
                newExpenseCtgyID = ex.getExpenseCategory();
                
                if (!newExpenseCtgyID.equals(oldExpenseCtgyID)) {
                    if (createNum != 0) {
                        expenseCtgyList.add(oldExpenseCtgyID);
                        createNumList.add(new Integer(createNum));
                    }
                    oldExpenseCtgyID = newExpenseCtgyID;                    
                    createNum = 0;
                }                 
                createNum++;
            }
            if (createNum != 0) {
                expenseCtgyList.add(oldExpenseCtgyID);
                createNumList.add(new Integer(createNum));
            }
            
            if (expenseCtgyList != null) {
                Object[][] obj = new Object[expenseCtgyList.size()][2];
                
                for (int j0 = 0; j0 < expenseCtgyList.size(); j0++) {
                    obj[j0][0] = expenseCtgyList.get(j0);
                    obj[j0][1] = createNumList.get(j0);
                }
                
                return obj;
            }
        }   
                       
        return null;
    }
    
    public List getKPISummaryList(Map conditions, int pageNo, int pageSize, KPISummaryQueryOrder order, boolean descend) {
        return kpiSummaryDAO.getKPISummaryList(conditions, pageNo, pageSize, order, descend);
    }
    
    public List getTop5KPIExpenseCategorySummary(Site site) {
        return kpiSummaryDAO.getTop5KPIExpenseCategorySummary(site);
    }
    
    public List getTop5KPIPurchaseCategorySummary(Site site) {
        return kpiSummaryDAO.getTop5KPIPurchaseCategorySummary(site);
    }
}
