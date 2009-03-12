package net.sourceforge.service.business.pr.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;

import net.sourceforge.dao.business.pr.CapexDAO;
import net.sourceforge.model.admin.Department;
import net.sourceforge.model.admin.Function;
import net.sourceforge.model.admin.PurchaseCategory;
import net.sourceforge.model.admin.PurchaseSubCategory;
import net.sourceforge.model.admin.User;
import net.sourceforge.model.business.BaseApproveRequest;
import net.sourceforge.model.business.pr.Capex;
import net.sourceforge.model.business.pr.CapexDepartment;
import net.sourceforge.model.business.pr.CapexRequest;
import net.sourceforge.model.business.pr.CapexRequestApproveRequest;
import net.sourceforge.model.business.pr.CapexRequestAttachment;
import net.sourceforge.model.business.pr.CapexRequestHistory;
import net.sourceforge.model.business.pr.CapexRequestHistoryItem;
import net.sourceforge.model.business.pr.CapexRequestItem;
import net.sourceforge.model.business.pr.YearlyBudget;
import net.sourceforge.model.business.pr.query.CapexRequestQueryCondition;
import net.sourceforge.model.business.pr.query.CapexRequestQueryOrder;
import net.sourceforge.model.metadata.ApproverDelegateType;
import net.sourceforge.model.metadata.CapexRequestStatus;
import net.sourceforge.model.metadata.CapexRequestType;
import net.sourceforge.service.BaseManager;
import net.sourceforge.service.admin.FunctionManager;
import net.sourceforge.service.admin.PurchaseCategoryManager;
import net.sourceforge.service.admin.PurchaseSubCategoryManager;
import net.sourceforge.service.admin.SystemLogManager;
import net.sourceforge.service.admin.UserManager;
import net.sourceforge.service.business.ApproveRelativeEmailManager;
import net.sourceforge.service.business.pr.CapexManager;
import net.sourceforge.service.business.pr.YearlyBudgetManager;
import net.sourceforge.service.business.rule.FlowManager;
import com.shcnc.utils.UUID;

public class CapexManagerImpl extends BaseManager implements CapexManager {

    private CapexDAO dao;
    private YearlyBudgetManager yearlyBudgetManager;
    private PurchaseCategoryManager purchaseCategoryManager;
    private PurchaseSubCategoryManager purchaseSubCategoryManager;
    private FlowManager flowManager;
    private FunctionManager functionManager;
    private UserManager userManager;
    private SystemLogManager systemLogManager;
    private ApproveRelativeEmailManager approveRelativeEmailManager;

    public void setCapexDAO(CapexDAO dao) {
        this.dao = dao;
    }

    public void setYearlyBudgetManager(YearlyBudgetManager yearlyBudgetManager) {
        this.yearlyBudgetManager = yearlyBudgetManager;
    }

    public void setPurchaseCategoryManager(PurchaseCategoryManager purchaseCategoryManager) {
        this.purchaseCategoryManager = purchaseCategoryManager;
    }

    public void setPurchaseSubCategoryManager(PurchaseSubCategoryManager purchaseSubCategoryManager) {
        this.purchaseSubCategoryManager = purchaseSubCategoryManager;
    }

    public void setFlowManager(FlowManager flowManager) {
        this.flowManager = flowManager;
    }

    public void setSystemLogManager(SystemLogManager systemLogManager) {
        this.systemLogManager = systemLogManager;
    }

    public void setApproveRelativeEmailManager(ApproveRelativeEmailManager approveRelativeEmailManager) {
        this.approveRelativeEmailManager = approveRelativeEmailManager;
    }

    private String getIncCode(String lastCode) {
        String tpStr = "0000" + (new Integer(lastCode.substring(7)).intValue() + 1);
        return lastCode.substring(0, 7) + tpStr.substring(tpStr.length() - 5);
    }

    private synchronized String getLastCode(Integer siteId, Integer yearlyBudgetId) {
        YearlyBudget yb = null;
        if (yearlyBudgetId != null) {
            yb = yearlyBudgetManager.getYearlyBudget(yearlyBudgetId);
        }
        String capexYear = null;
        /* down add by jackey 2006-3-22 */
        if (yb != null) {
            capexYear = String.valueOf(yb.getYear()).substring(2);
        } else {
            //capexYear = String.valueOf(new Date().getYear() + 1900).substring(2);
            capexYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR)).substring(2);
        }
        /* up add by jackey 2006-3-22 */
        String strSiteId = siteId.toString(); 
        String prefix = "CE" + ("000" + siteId.toString()).substring(strSiteId.length()) + capexYear;
        String lastCode = getIncCode(dao.getLastCapexNo(prefix));
        for (int repeatTimes = 0; repeatTimes < 10; repeatTimes++, lastCode = getIncCode(dao.getLastCapexNo(prefix))) {
            if (dao.getCapex(lastCode) == null) {
                return lastCode;
            }
        }
        return null;
    }

    public CapexRequest getCapexRequest(Integer id) {
        return dao.getCapexRequest(id);
    }

    public CapexRequest saveCapexRequest(CapexRequest cr) {
        return saveCapexRequestAndDepartments(cr, null);
    }
    
    public CapexRequest saveCapexRequestAndDepartments(CapexRequest cr, List departmentList) {
        Capex c = cr.getCapex();
        if (c.getId() == null) {
            if (CapexRequestType.INCREASE.equals(cr.getType())) {
                throw new RuntimeException("cannot create new capex for increase capex request");
            }
            Integer yearlyBudgetID = null;
            YearlyBudget yb = c.getYearlyBudget();
            if (yb != null) {
                yearlyBudgetID = yb.getId();
                yb = yearlyBudgetManager.getYearlyBudget(yearlyBudgetID);
                yb.setActualAmount(yb.getActualAmount().add(cr.getAmount()));
                yearlyBudgetManager.updateAndNotifyYearlyBudget(yb, true);
            }
            Capex nc = new Capex(getLastCode(c.getSite().getId(), yearlyBudgetID));
            if (yb != null) {
                nc.setYear(yb.getYear());
            } else {
                nc.setYear(Calendar.getInstance().get(Calendar.YEAR));
            }
            try {
                PropertyUtils.copyProperties(nc, c);
                nc.setActualAmount(new BigDecimal(0d));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            cr.setCapex(dao.insertCapex(nc));
            for (Iterator itor = departmentList.iterator(); itor.hasNext();) {
                CapexDepartment cd = new CapexDepartment();
                cd.setCapex(nc);
                cd.setDepartment((Department) itor.next());
                dao.saveCapexDepartment(cd);
            }
        } else if (departmentList != null){
            throw new IllegalArgumentException("cannot change capex's department");
        }
        return dao.saveCapexRequest(cr);
    }

    public void saveCapexRequest(CapexRequest capexRequest, List capexRequestItemList) {
        CapexRequest oldRequest = dao.getCapexRequest(capexRequest.getId());
        if (!oldRequest.getAmount().equals(capexRequest.getAmount())
                && oldRequest.getCapex().getYearlyBudget() != null) {// add by jackey 2006-3-26
            YearlyBudget yb = oldRequest.getCapex().getYearlyBudget();
            yb.setActualAmount(yb.getActualAmount().subtract(oldRequest.getAmount()).add(capexRequest.getAmount()));
            yearlyBudgetManager.updateAndNotifyYearlyBudget(yb, true);
        }
        try {
            PropertyUtils.copyProperties(oldRequest, capexRequest);
        } catch (Exception e) {
            throw new RuntimeException("error while coping capex request object");
        }
        capexRequest = dao.saveCapexRequest(oldRequest);
        List oldCapexRequestItemList = dao.getCapexRequestItemListForCapex(capexRequest);
        for (Iterator itor = oldCapexRequestItemList.iterator(); itor.hasNext();) {
            dao.deleteCapexRequestItem((CapexRequestItem) itor.next());
        }
        for (Iterator itor = capexRequestItemList.iterator(); itor.hasNext();) {
            dao.saveCapexRequestItem((CapexRequestItem) itor.next());
        }
    }

    public void updateCapexRequest(CapexRequest capexRequest, List capexRequestItemList, List approveRequestList, boolean draft) {
        if (draft) {
            capexRequest.setStatus(CapexRequestStatus.DRAFT);
            capexRequest.setApproveRequestId(null);
        } else {
            capexRequest.setStatus(CapexRequestStatus.PENDING);
            capexRequest.setRequestDate(new Date());
            String approveRequestId = UUID.getUUID();
            capexRequest.setApproveRequestId(approveRequestId);
            for (Iterator itor = approveRequestList.iterator(); itor.hasNext();) {
                CapexRequestApproveRequest approveRequest = (CapexRequestApproveRequest) itor.next();
                approveRequest.setApproveRequestId(approveRequestId);
                dao.saveCapexRequestApproveRequest(approveRequest);
            }
        }
        saveCapexRequest(capexRequest, capexRequestItemList);
        if (!draft) {
            if (CapexRequestType.INITIAL.equals(capexRequest.getType())) {
                Capex c = capexRequest.getCapex();
                Department[] departments = this.getDepartments(c);
                Set ds = new HashSet();
                for (int i = 0; i < departments.length; i++) {
                    ds.add(departments[i]);
                }
                c.setCapexDepartments(ds);
                systemLogManager.generateLog(null, capexRequest, CapexRequest.LOG_ACTION_INITIAL_SUBMIT, capexRequest.getRequestor());
            } else {
                systemLogManager.generateLog(null, capexRequest, CapexRequest.LOG_ACTION_INCREASE_SUBMIT, capexRequest.getRequestor());
            }
            approveRelativeEmailManager.sendApprovalEmail(capexRequest, ApproverDelegateType.NONBUDGET_CAPEX_APPROVER, (BaseApproveRequest)approveRequestList.get(0));
        }
    }

    public int getCapexRequestListCount(Map conditions) {
        return dao.getCapexRequestListCount(conditions);
    }

    public List getCapexRequestList(Map conditions, int pageNo, int pageSize, CapexRequestQueryOrder order, boolean descend) {
        return dao.getCapexRequestList(conditions, pageNo, pageSize, order, descend);
    }

    public List getCapexDepartmentListForCapex(Capex c) {
        return dao.getCapexDepartmentListForCapex(c);
    }

    public List getOldApprovedCapexRequestListForCapexRequest(CapexRequest cr) {
        Map conditions = new HashMap();
        conditions.put(CapexRequestQueryCondition.CAPEX_ID_EQ, cr.getCapex().getId());
        conditions.put(CapexRequestQueryCondition.STATUS_EQ, CapexRequestStatus.APPROVED.getEnumCode());
        Date requestDate = cr.getRequestDate();
        if (requestDate == null) {
            requestDate = new Date();
        }
        conditions.put(CapexRequestQueryCondition.REQUESTDATE_LT, requestDate);
        return dao.getCapexRequestList(conditions, 0, -1, CapexRequestQueryOrder.REQUESTDATE, false);
    }

    public List getCapexRequestItemListForCapexRequest(CapexRequest cr) {
        return dao.getCapexRequestItemListForCapex(cr);
    }

    public List getCapexRequestAttachmentListForCapexRequest(CapexRequest cr) {
        return dao.getCapexRequestAttachmentListForCapexRequest(cr);
    }

    public List getCapexRequestApproveRequestListForCapexRequest(CapexRequest cr) {
        return dao.getCapexRequestApproveRequestListForCapexRequest(cr);
    }

    public CapexRequestAttachment saveCapexRequestAttachment(CapexRequestAttachment capexRequestAttachment, InputStream inputStream) {
        return dao.saveCapexRequestAttachment(capexRequestAttachment, inputStream);
    }

    public InputStream getCapexRequestAttachmentContent(Integer id) {
        return dao.getCapexRequestAttachmentContent(id);
    }

    public CapexRequestAttachment getCapexRequestAttachment(Integer id) {
        return dao.getCapexRequestAttachment(id);
    }

    public void deleteCapexRequestAttachment(CapexRequestAttachment capexRequestAttachment) {
        dao.deleteCapexRequestAttachment(capexRequestAttachment);
    }

    public CapexRequest getCapexRequestByApproveRequestId(String approveRequestId) {
        return dao.getCapexRequestByApproveRequestId(approveRequestId);
    }

    public void rejectCapexRequest(CapexRequest capexRequest) {
        capexRequest.setStatus(CapexRequestStatus.REJECTED);
        dao.saveCapexRequest(capexRequest);
        CapexRequestHistory history = new CapexRequestHistory();
        history.setAmount(capexRequest.getAmount());
        history.setApproveRequestId(capexRequest.getApproveRequestId());
        history.setStatus(capexRequest.getStatus());
        history.setCapexRequest(capexRequest);
        dao.saveCapexRequestHistory(history);
        List itemList = dao.getCapexRequestItemListForCapex(capexRequest);
        for (Iterator itor = itemList.iterator(); itor.hasNext();) {
            CapexRequestItem item = (CapexRequestItem) itor.next();
            CapexRequestHistoryItem historyItem = new CapexRequestHistoryItem();
            historyItem.setCapexRequestHistory(history);
            historyItem.setExchangeRate(item.getExchangeRate());
            historyItem.setExchangeRateValue(item.getExchangeRateValue());
            historyItem.setPrice(item.getPrice());
            historyItem.setPurchaseSubCategory(item.getPurchaseSubCategory());
            historyItem.setQuantity(item.getQuantity());
            historyItem.setSupplier(item.getSupplier());
            historyItem.setSupplierItem(item.getSupplierItem());
            historyItem.setSupplierItemSepc(item.getSupplierItemSepc());
            historyItem.setSupplierName(item.getSupplierName());
            dao.saveCapexRequestHistoryItem(historyItem);
        }
    }

    public void deleteCapexRequest(CapexRequest capexRequest) {
        if (capexRequest.getAmount().compareTo(new BigDecimal(0d)) != 0 
                && capexRequest.getCapex().getYearlyBudget() != null) { //add by jackey 2006-3-26
            YearlyBudget yb = capexRequest.getCapex().getYearlyBudget();
            yb.setActualAmount(yb.getActualAmount().subtract(capexRequest.getAmount()));
            yearlyBudgetManager.updateAndNotifyYearlyBudget(yb, true);
        }
        dao.deleteCapexRequest(capexRequest);
    }


    public CapexRequest createAddtionalCapexRequest(CapexRequest capexRequest) {
        CapexRequest addtionalRequest = new CapexRequest();
        Date nowDate = new Date();
        addtionalRequest.setAmount(capexRequest.getAmount());
        addtionalRequest.setCapex(capexRequest.getCapex());
        addtionalRequest.setCreateDate(nowDate);
        addtionalRequest.setCreator(capexRequest.getCreator());
        addtionalRequest.setRequestor(capexRequest.getRequestor());
        addtionalRequest.setRequestDate(nowDate);
        addtionalRequest.setStatus(CapexRequestStatus.DRAFT);
        addtionalRequest.setTitle(capexRequest.getTitle());
        addtionalRequest.setType(CapexRequestType.INCREASE);
        /*
         * Below Add by Jackey 2006-3-31
         */
        addtionalRequest.setDescription(addtionalRequest.getDescription());
        addtionalRequest.setActivity(capexRequest.getActivity());
        addtionalRequest.setPostAuditDate(capexRequest.getPostAuditDate());
        addtionalRequest.setFirstExpenseDate(capexRequest.getFirstExpenseDate());
        addtionalRequest.setLastExpenseDate(capexRequest.getLastExpenseDate());
        addtionalRequest.setCompletionDate(capexRequest.getCompletionDate());
        addtionalRequest.setProjectLeader(capexRequest.getProjectLeader());
        addtionalRequest.setProjectLeaderTitle(capexRequest.getProjectLeaderTitle());
        addtionalRequest.setAccountingCode(capexRequest.getAccountingCode());        
        addtionalRequest.setIsCapexType(capexRequest.getIsCapexType());
        addtionalRequest.setIsAssetDisposalType(capexRequest.getIsAssetDisposalType());
        addtionalRequest.setOtherType(capexRequest.getOtherType());
        addtionalRequest.setIsNewImplantationReason(capexRequest.getIsNewImplantationReason());
        addtionalRequest.setIsCapacityIncreaseReason(capexRequest.getIsCapacityIncreaseReason());
        addtionalRequest.setIsCostImprovementReason(capexRequest.getIsCostImprovementReason());
        addtionalRequest.setIsNewProductLineReason(capexRequest.getIsNewProductLineReason());
        addtionalRequest.setIsDownsizingReason(capexRequest.getIsDownsizingReason());
        addtionalRequest.setIsHSEReason(capexRequest.getIsHSEReason());
        addtionalRequest.setOtherReason(capexRequest.getOtherReason());
        addtionalRequest.setCapexCapitalizedAmount(capexRequest.getCapexCapitalizedAmount());
        addtionalRequest.setOtherExpenseAmount(capexRequest.getOtherExpenseAmount());
        addtionalRequest.setAssetDisposalAmount(capexRequest.getAssetDisposalAmount());
        addtionalRequest.setGrossBookAmount(capexRequest.getGrossBookAmount());
        addtionalRequest.setReferenceCurrency(capexRequest.getReferenceCurrency());
        addtionalRequest.setReferenceExchangeRate(capexRequest.getReferenceExchangeRate());
        addtionalRequest.setProjectImpactNonOperating1(capexRequest.getProjectImpactNonOperating1());
        addtionalRequest.setProjectImpactNonOperating2(capexRequest.getProjectImpactNonOperating2());
        addtionalRequest.setProjectImpactOther1(capexRequest.getProjectImpactOther1());
        addtionalRequest.setProjectImpactOther2(capexRequest.getProjectImpactOther2());
        addtionalRequest.setProjectImpactOther3(capexRequest.getProjectImpactOther3());
        addtionalRequest.setLastForecastAmount(capexRequest.getLastForecastAmount());
        addtionalRequest.setDiscountedCashFlowPayback(capexRequest.getDiscountedCashFlowPayback());
        addtionalRequest.setInternalRateOfReturn(capexRequest.getInternalRateOfReturn());
        addtionalRequest.setNpvAmount(capexRequest.getNpvAmount());
        addtionalRequest.setDiscountRate(capexRequest.getDiscountRate());
        dao.saveCapexRequest(addtionalRequest);
        
        List itemList = getCapexRequestItemListForCapexRequest(capexRequest);
        for (Iterator itor = itemList.iterator(); itor.hasNext();) {
            CapexRequestItem item = (CapexRequestItem) itor.next();
            CapexRequestItem newItem = new CapexRequestItem();
            newItem.setCapexRequest(addtionalRequest);
            newItem.setExchangeRate(item.getExchangeRate());
            newItem.setExchangeRateValue(item.getExchangeRateValue());
            newItem.setPrice(item.getPrice());
            newItem.setPurchaseSubCategory(item.getPurchaseSubCategory());
            newItem.setQuantity(item.getQuantity());
            newItem.setSupplier(item.getSupplier());
            newItem.setSupplierItem(item.getSupplierItem());
            newItem.setSupplierItemSepc(item.getSupplierItemSepc());
            newItem.setSupplierName(item.getSupplierName());
            dao.saveCapexRequestItem(newItem);
        }
        return addtionalRequest;
    }

    public Capex getCapex(String capex_id) {
        return dao.getCapex(capex_id);
        
    }

    public List getRequestApprovedCapexList(final PurchaseSubCategory psc, final Department department) {
        return dao.getRequestApprovedCapexList(psc,department);
        
    }

    public Department[] getDepartments(Capex cp) {
        return dao.getDepartments(cp);
    }

    public void withdrawCapexRequest(CapexRequest capexRequest) {
        dao.withdrawCapexRequest(capexRequest);
        approveRelativeEmailManager.deleteWithdrawEmail(capexRequest);
        systemLogManager.generateLog(null, capexRequest, CapexRequest.LOG_ACTION_INITIAL_SUBMIT, capexRequest.getRequestor());
    }

    public void setExtraInformationToCapexForExecuteFlow(Capex c) {
        c.setCapexDepartments(getCapexDepartmentListForCapex(c));
        PurchaseCategory pc = c.getPurchaseCategory();
        PurchaseSubCategory psc = c.getPurchaseSubCategory();

        Collection purchaseCategorys;
        Collection purchaseSubCategorys;

        if (pc != null) {
            purchaseCategorys = new ArrayList();
            purchaseCategorys.add(pc);
            if (psc != null) {
                purchaseSubCategorys = new ArrayList();
                purchaseSubCategorys.add(psc);
            } else {
                purchaseSubCategorys = purchaseSubCategoryManager.getEnabledPurchaseSubCategoryOfPurchaseCategory(pc);
            }
        } else {
            purchaseCategorys = purchaseCategoryManager.getEnabledPurchaseCategorySubCategoryOfSiteIncludeGlobal(c.getSite());
            purchaseSubCategorys = new ArrayList();
            for (Iterator itor = purchaseCategorys.iterator(); itor.hasNext();) {
                PurchaseCategory p = (PurchaseCategory) itor.next();
                purchaseSubCategorys.addAll(p.getEnabledPurchaseSubCategoryList());
            }
        }
        c.setPurchaseCategorys(purchaseCategorys);
        c.setPurchaseSubCategorys(purchaseSubCategorys);
    }

    public void approveCapexRequest(CapexRequest capexRequest) {
        capexRequest.setStatus(CapexRequestStatus.APPROVED);
        capexRequest.setApproveDate(new Date());
        capexRequest = dao.saveCapexRequest(capexRequest);
        Capex capex = capexRequest.getCapex();
        CapexRequest lastRequest = capex.getLastApprovedRequest();
        if (lastRequest == null || lastRequest.getRequestDate().compareTo(capexRequest.getRequestDate()) < 0) {
            capex.setLastApprovedRequest(capexRequest);
            this.updateAndNotifyCapex(capex, false);
        }
        approveRelativeEmailManager.sendApprovedEmail(capexRequest);
    }

    public void updateAndNotifyCapex(Capex capex, boolean ignoreAmountCondition) {
        dao.updateCapex(capex);
        this.setExtraInformationToCapexForExecuteFlow(capex);
        capex.setIgnoreAmountCondition(ignoreAmountCondition);
        flowManager.executeNotifyFlow(capex);
    }

    public boolean canViewCapexAmount(Capex capex, User user) {
        Function function=functionManager.getFunction("ViewCapexAmount");
        List capexDepartmentList=this.getCapexDepartmentListForCapex(capex);
        for (Iterator iter = capexDepartmentList.iterator(); iter.hasNext();) {
            CapexDepartment cd = (CapexDepartment) iter.next();
            if(userManager.hasDepartmentPower(cd.getDepartment(),user,function)) return true;
        }
        return false;
    }

    public void setFunctionManager(FunctionManager functionManager) {
        this.functionManager = functionManager;
    }

    public void setUserManager(UserManager um) {
        this.userManager=um;
    }

}
