/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.service.business.expense.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.aof.dao.business.expense.ExpenseApproveRequestDAO;
import com.aof.dao.business.expense.ExpenseAttachmentDAO;
import com.aof.dao.business.expense.ExpenseDAO;
import com.aof.model.admin.ExpenseSubCategory;
import com.aof.model.admin.Site;
import com.aof.model.admin.User;
import com.aof.model.business.BaseApproveRequest;
import com.aof.model.business.expense.Expense;
import com.aof.model.business.expense.ExpenseApproveRequest;
import com.aof.model.business.expense.ExpenseAttachment;
import com.aof.model.business.expense.ExpenseCell;
import com.aof.model.business.expense.ExpenseClaim;
import com.aof.model.business.expense.ExpenseHistory;
import com.aof.model.business.expense.ExpenseHistoryCell;
import com.aof.model.business.expense.ExpenseHistoryRow;
import com.aof.model.business.expense.ExpenseRecharge;
import com.aof.model.business.expense.ExpenseRow;
import com.aof.model.business.expense.query.ExpenseAttachmentQueryCondition;
import com.aof.model.business.expense.query.ExpenseAttachmentQueryOrder;
import com.aof.model.business.expense.query.ExpenseQueryOrder;
import com.aof.model.business.pr.Capex;
import com.aof.model.business.pr.PurchaseRequest;
import com.aof.model.business.pr.YearlyBudget;
import com.aof.model.metadata.ApproverDelegateType;
import com.aof.model.metadata.ExpenseStatus;
import com.aof.model.metadata.ExportStatus;
import com.aof.service.BaseManager;
import com.aof.service.admin.ExpenseSubCategoryManager;
import com.aof.service.admin.SystemLogManager;
import com.aof.service.business.ApproveRelativeEmailManager;
import com.aof.service.business.expense.ExpenseManager;
import com.aof.service.business.pr.YearlyBudgetManager;
import com.aof.service.business.rule.ExecuteFlowEmptyResultException;
import com.aof.service.business.rule.FlowManager;
import com.aof.service.business.rule.NoAvailableFlowToExecuteException;
import com.aof.web.struts.action.ActionUtils;
import com.aof.web.struts.action.ServiceLocator;
import com.shcnc.struts.action.ActionException;
import com.shcnc.utils.UUID;

/**
 * ExpenseManager的实现
 * 
 * @author ych
 * @version 1.0 (Nov 19, 2005)
 */
public class ExpenseManagerImpl extends BaseManager implements ExpenseManager {

    private ExpenseDAO dao;

    private ExpenseApproveRequestDAO approveDao;

    private ExpenseAttachmentDAO attachmentDao;
    
    private FlowManager flowManager;
    
    private ExpenseSubCategoryManager expenseSubCategoryManager;
    
    private SystemLogManager systemLogManager;

    private ApproveRelativeEmailManager approveRelativeEmailManager;     
    
    private YearlyBudgetManager yearlyBudgetManager;

    public void setExpenseSubCategoryManager(ExpenseSubCategoryManager expenseSubCategoryManager) {
        this.expenseSubCategoryManager = expenseSubCategoryManager;
    }
    
    public void setSystemLogManager(SystemLogManager systemLogManager) {
        this.systemLogManager = systemLogManager;
    }

    public void setApproveRelativeEmailManager(ApproveRelativeEmailManager approveRelativeEmailManager) {
        this.approveRelativeEmailManager = approveRelativeEmailManager;
    }
    
    public void setYearlyBudgetManager(YearlyBudgetManager yearlyBudgetManager) {
        this.yearlyBudgetManager = yearlyBudgetManager;
    }

    private String getLastCode(Site site,Date date) {
        StringBuffer sb=new StringBuffer("EX");
        String siteId=site.getId().toString();
        for(int i=0;i<3-siteId.length();i++)
            sb.append('0');
        sb.append(siteId);
        sb.append(StringUtils.right(ActionUtils.get8CharsFromDate(date),6));
        String prefix=sb.toString();
        String maxId=dao.getMaxExpenseIdBeginWith(prefix);
        
        int serialNo=1;
        if(maxId!=null)
        {
            Integer maxSN=ActionUtils.parseInt(StringUtils.right(maxId,5));
            if(maxSN==null)throw new RuntimeException("max serial no. is not digit");
            serialNo=maxSN.intValue()+1;
        }
        String sn=String.valueOf(serialNo);
        for(int i=0;i<5-sn.length();i++)
            sb.append('0');
        sb.append(sn);
        return sb.toString();
    }

    public void setExpenseDAO(ExpenseDAO dao) {
        this.dao = dao;
    }

    /**
     * @param approveDao
     *            The approveDao to set.
     */
    public void setExpenseApproveRequestDAO(ExpenseApproveRequestDAO approveDao) {
        this.approveDao = approveDao;
    }

    /**
     * @param attachmentDao
     *            The attachmentDao to set.
     */
    public void setExpenseAttachmentDAO(ExpenseAttachmentDAO attachmentDao) {
        this.attachmentDao = attachmentDao;
    }

    public Expense getExpense(String id) {
        return dao.getExpense(id);
    }

    
    
    /**
     * @param flowManager The flowManager to set.
     */
    public void setFlowManager(FlowManager flowManager) {
        this.flowManager = flowManager;
    }

    public Expense updateExpense(Expense expense, List expenseRowList, List expenseRechargeList) {
        insertExpenseRow(expense, expenseRowList);
        insertExpenseRecharge(expense, expenseRechargeList);
        return dao.updateExpense(expense);
    }

    public Expense updateExpense(Expense expense, List expenseRowList, List expenseRechargeList, //List approveRequestList, 
            boolean isDraft, User currentUser) {
        List approveRequestList = null;
        insertExpenseRow(expense, expenseRowList);
        insertExpenseRecharge(expense, expenseRechargeList);

        if (isDraft) {
            expense.setStatus(ExpenseStatus.DRAFT);
            expense.setApproveRequestId(null);
            expense = dao.updateExpense(expense);
        } else {
            expense.setStatus(ExpenseStatus.PENDING);
            expense.setRequestDate(new Date());
            expense = dao.updateExpense(expense);
            
            //占用预算金额
            updateBudgetAmount(expense, new BigDecimal(0D), expense.getAmount());
            try {
                approveRequestList = flowManager.executeApproveFlow(expense);                
            } catch (ExecuteFlowEmptyResultException e) {
                throw new ActionException("flow.execute.notApproverFound");
            } catch (NoAvailableFlowToExecuteException e) {
                throw new ActionException("flow.execute.notFlowFound");
            }
            insertApproveRequests(expense, approveRequestList);
            systemLogManager.generateLog(null, expense, Expense.LOG_ACTION_SUBMIT, currentUser);
            approveRelativeEmailManager.sendApprovalEmail(expense, ApproverDelegateType.EXPENSE_APPROVER, (BaseApproveRequest)approveRequestList.get(0));
        }

        return expense;
    }

    private void insertExpenseRow(Expense expense, List expenseRowList) {
        dao.deleteExpenseCellByExpense(expense);
        dao.deleteExpenseRowByExpense(expense);
        for (Iterator itorRow = expenseRowList.iterator(); itorRow.hasNext();) {
            ExpenseRow row = (ExpenseRow) itorRow.next();
            dao.saveExpenseRow(row);
            List cellList = row.getExpenseCellList();
            for (Iterator itorCell = cellList.iterator(); itorCell.hasNext();) {
                ExpenseCell cell = (ExpenseCell) itorCell.next();
                dao.saveExpenseCell(cell);
            }
        }
    }

    private void insertExpenseRecharge(Expense expense, List expenseRechargeList) {
        dao.deleteExpenseRechargeByExpense(expense);
        for (Iterator itor = expenseRechargeList.iterator(); itor.hasNext();) {
            ExpenseRecharge expenseRecharge = (ExpenseRecharge) itor.next();
            dao.saveExpenseRecharge(expenseRecharge);
        }
    }

    private void insertApproveRequests(Expense expense, List approveRequestList) {
        expense.setApproveRequestId(UUID.getUUID());
        dao.updateExpense(expense);

        for (Iterator iter = approveRequestList.iterator(); iter.hasNext();) {
            ExpenseApproveRequest ear = (ExpenseApproveRequest) iter.next();
            ear.setApproveRequestId(expense.getApproveRequestId());
            dao.saveExpenseApproveRequst(ear);
        }

    }

    public Expense insertExpense(Expense expense) {
        String expenseCode = getLastCode(expense.getDepartment().getSite(),new Date());
        if (expenseCode == null)
            throw new RuntimeException("error occurs when get last expense code");
        expense.setId(expenseCode);
        return dao.insertExpense(expense);
    }

    public int getExpenseListCount(Map conditions) {
        return dao.getExpenseListCount(conditions);
    }

    public List getExpenseList(Map conditions, int pageNo, int pageSize, ExpenseQueryOrder order, boolean descend) {
        return dao.getExpenseList(conditions, pageNo, pageSize, order, descend);
    }

    public Expense getExpenseByApproveRequestId(String approveRequestId) {
        return dao.getExpenseByApproveRequestId(approveRequestId);
    }

    public List getExpenseClaimList(Expense expense) {
        return dao.getExpenseClaimList(expense);
    }
    
    /* (non-Javadoc)
     * @see com.aof.service.business.expense.ExpenseManager#finalConfirm(com.aof.model.business.expense.Expense, java.util.List, java.util.List, java.util.List, com.aof.model.admin.User)
     */
    public void finalConfirm(Expense expense, List expenseRowList, List rechargeList, List ecList, User currentUser) {
        List escList=expenseSubCategoryManager.getEnabledChildrenOfExpenseCategory(expense.getExpenseCategory().getId());
        if(escList.size()!=ecList.size()) throw new RuntimeException("column count is not same");

        for (Iterator iter = ecList.iterator(); iter.hasNext();) {
            ExpenseClaim ec = (ExpenseClaim) iter.next();
            dao.insertExpenseClaim(ec);
        }
        expense.setStatus(ExpenseStatus.CONFIRMED);
        expense.setConfirmDate(new Date());
        this.updateExpense(expense, expenseRowList, rechargeList);
        flowManager.executeNotifyFlow(expense);
        systemLogManager.generateLog(null, expense, Expense.LOG_ACTION_FINAL_CONFIRM, currentUser);
    }

    /* (non-Javadoc)
     * @see com.aof.service.business.expense.ExpenseManager#finalConfirm(java.lang.String, com.aof.model.metadata.YesNo, java.util.List, java.util.List, com.aof.model.admin.User)
     */
    /*
    public void finalConfirm(String epId, YesNo isRecharge, List ecList,List rechargeList,User currentUser) {
        
        
        Expense ep = this.getExpense(epId);
        ep.setIsRecharge(isRecharge);
        
        List escList=expenseSubCategoryManager.getEnabledChildrenOfExpenseCategory(ep.getExpenseCategory().getId());
        if(escList.size()!=ecList.size()) throw new RuntimeException("column count is not same");
        
        BigDecimal amount=new BigDecimal(0d);
        for (Iterator iter = ecList.iterator() ;iter.hasNext();) {
            ExpenseClaim ec = (ExpenseClaim) iter.next();
            //ExpenseSubCategory esc=(ExpenseSubCategory) iterEsc.next();
            amount=amount.add(ec.getAmount());
            //ec.setDescription(dao.getColumnDescription(ep,esc));
            dao.insertExpenseClaim(ec);
        }
        ep.setConfirmedAmount(amount.setScale(2, BigDecimal.ROUND_HALF_UP));
        ep.setStatus(ExpenseStatus.CONFIRMED);
        dao.updateExpense(ep);
        insertExpenseRecharge(ep, rechargeList);
        flowManager.executeNotifyFlow(ep);
        systemLogManager.generateLog(null, ep, Expense.LOG_ACTION_FINAL_CONFIRM, currentUser);
    }
    */
    
    /* (non-Javadoc)
     * @see com.aof.service.business.expense.ExpenseManager#finalClaim(java.lang.String)
     */
    public void finalClaim(String epId) {
        Expense ep = this.getExpense(epId);
        ep.setStatus(ExpenseStatus.CLAIMED);
        dao.updateExpense(ep);
    }

    public void rejectExpense(Expense ep, User currentUser, String comment) {
        ep.setStatus(ExpenseStatus.REJECTED);
        dao.updateExpense(ep);
        ExpenseHistory expenseHistory = new ExpenseHistory();
        expenseHistory.setApproveRequestId(ep.getApproveRequestId());
        expenseHistory.setExchangeRate(ep.getExchangeRate());
        expenseHistory.setStatus(ep.getStatus());
        expenseHistory.setExpense(ep);
        dao.saveExpenseHistory(expenseHistory);
        List rows = dao.getExpenseRowListByExpense(ep);
        Map rowHistoryMap = new HashMap();
        for (Iterator itor = dao.getExpenseCellListByExpense(ep).iterator(); itor.hasNext();) {
            ExpenseCell cell = (ExpenseCell) itor.next();
            ExpenseRow row = cell.getRow();
            ExpenseHistoryRow historyRow = (ExpenseHistoryRow) rowHistoryMap.get(row);
            if (historyRow == null) {
                historyRow = createHistory(row, expenseHistory);
                dao.saveExpenseHistoryRow(historyRow);
                rowHistoryMap.put(row, historyRow);
                rows.remove(row);
            }
            ExpenseHistoryCell historyCell = createHistory(cell, historyRow);
            dao.saveExpenseHistoryCell(historyCell);
        }
        for (Iterator itor = rows.iterator(); itor.hasNext();) {
            ExpenseHistoryRow historyRow = createHistory((ExpenseRow) itor.next(), expenseHistory);
            dao.saveExpenseHistoryRow(historyRow);
        }
        //释放预算
        updateBudgetAmount(ep, ep.getAmount(), new BigDecimal(0D));
        systemLogManager.generateLog(null, ep, Expense.LOG_ACTION_REJECT, currentUser);
        approveRelativeEmailManager.sendRejectedEmail(ep, currentUser.getName(), comment);
    }

    private ExpenseHistoryRow createHistory(ExpenseRow row, ExpenseHistory expenseHistory) {
        ExpenseHistoryRow historyRow = new ExpenseHistoryRow();
        historyRow.setDate(row.getDate());
        //historyRow.setDescription(row.getDescription());
        historyRow.setExpenseHistory(expenseHistory);
        historyRow.setProjectCode(row.getProjectCode());
        return historyRow;
    }

    private ExpenseHistoryCell createHistory(ExpenseCell cell, ExpenseHistoryRow historyRow) {
        ExpenseHistoryCell historyCell = new ExpenseHistoryCell();
        historyCell.setAmount(cell.getAmount());
        historyCell.setExpenseSubCategory(cell.getExpenseSubCategory());
        historyCell.setDescription(cell.getDescription());
        historyCell.setRow(historyRow);
        return historyCell;
    }

    public List getExpenseRowCellList(Expense expense, List enabledExpenseSubCategoryList) {
        List rowList = dao.getExpenseRowListByExpense(expense);
        List cellList = dao.getExpenseCellListByExpense(expense);
        for (Iterator itorRow = rowList.iterator(); itorRow.hasNext();) {
            ExpenseRow row = (ExpenseRow) itorRow.next();
            List rowCellList = new ArrayList();
            for (Iterator itorExpenseSubCategory = enabledExpenseSubCategoryList.iterator(); itorExpenseSubCategory.hasNext();) {
                ExpenseSubCategory expenseSubCategory = (ExpenseSubCategory) itorExpenseSubCategory.next();
                ExpenseCell cell = getCellFromList(cellList, row, expenseSubCategory);
                if (cell == null) {
                    cell = new ExpenseCell();
                    cell.setRow(row);
                    cell.setExpenseSubCategory(expenseSubCategory);
                    cell.setAmount(new BigDecimal(0d));
                }
                rowCellList.add(cell);
            }
            row.setExpenseCellList(rowCellList);
        }
        return rowList;
    }

    private ExpenseCell getCellFromList(List cellList, ExpenseRow row, ExpenseSubCategory expenseSubCategory) {
        for (int index = 0; index < cellList.size(); index++) {
            ExpenseCell cell = (ExpenseCell) cellList.get(index);
            if (cell.getRow().equals(row) && cell.getExpenseSubCategory().equals(expenseSubCategory)) {
                cellList.remove(index);
                return cell;
            }
        }
        return null;
    }
    
    private List getCellListFromRow(List cellList, ExpenseRow row) {
        List retList=new ArrayList();
        for (int index = 0; index < cellList.size(); index++) {
            ExpenseCell cell = (ExpenseCell) cellList.get(index);
            if (cell.getRow().equals(row)) {
                retList.add(cell);
            }
        }
        return retList;
    }

    public List getExpenseRechargeListByExpense(Expense expense) {
        return dao.getExpenseRechargeListByExpense(expense);
    }

    public void removeExpense(Expense expense, User currentUser) {
        approveDao.deleteExpenseApproveRequestByExpense(expense);
        attachmentDao.deleteExpenseAttachmentByExpense(expense);
        dao.deleteExpenseRechargeByExpense(expense);
        dao.deleteExpenseCellByExpense(expense);
        dao.deleteExpenseRowByExpense(expense);
        dao.deleteExpenseHistoryCellListByExpense(expense);
        dao.deleteExpenseHistoryRowListByExpense(expense);
        dao.deleteExpenseHistoryListByExpense(expense);
        dao.deleteExpenseClaimListByExpense(expense);
        dao.deleteExpense(expense.getId());
        systemLogManager.generateLog(null, expense, Expense.LOG_ACTION_DELETE, currentUser);
    }

    public void withDrawExpense(Expense expense, User currentUser) {
        approveDao.deleteExpenseApproveRequestByExpense(expense);
        expense.setStatus(ExpenseStatus.DRAFT);
        expense.setApproveRequestId(null);
        
        //释放预算
        updateBudgetAmount(expense, expense.getAmount(), new BigDecimal(0D));
        dao.updateExpense(expense);
        approveRelativeEmailManager.deleteWithdrawEmail(expense);        
        systemLogManager.generateLog(null, expense, Expense.LOG_ACTION_WITHDRAW, currentUser);
    }

    public void approveExpense(Expense expense) {
        expense.setStatus(ExpenseStatus.APPROVED);
        expense.setApproveDate(new Date());
        expense = dao.updateExpense(expense);
        approveRelativeEmailManager.sendApprovedEmail(expense);
    }

    public Expense copyExpense(Expense copyExpense,Expense srcExpense) {
        
        copyExpense.setAmount(srcExpense.getAmount());
        copyExpense.setApproveDate(null);
        copyExpense.setApproveRequestId(null);
        copyExpense.setBaseCurrency(srcExpense.getBaseCurrency());
        copyExpense.setConfirmedAmount(new BigDecimal(0d));
        copyExpense.setCreateDate(new Date());
        copyExpense.setDepartment(srcExpense.getDepartment());
        copyExpense.setDescription(srcExpense.getDescription());
        copyExpense.setEmailDate(null);
        copyExpense.setEmailTimes(0);
        copyExpense.setExchangeRate(srcExpense.getExchangeRate());
        copyExpense.setExpenseCategory(srcExpense.getExpenseCategory());
        copyExpense.setExpenseCurrency(srcExpense.getExpenseCurrency());
        copyExpense.setExportStatus(ExportStatus.UNEXPORTED);
        copyExpense.setIsRecharge(srcExpense.getIsRecharge());
        copyExpense.setRequestDate(null);
        copyExpense.setStatus(ExpenseStatus.DRAFT);
        copyExpense.setTitle(srcExpense.getTitle());
        copyExpense.setTravelApplication(srcExpense.getTravelApplication());
        copyExpense.setYearlyBudget(srcExpense.getYearlyBudget());
        
        copyExpense=insertExpense(copyExpense);
        
        List rowList = dao.getExpenseRowListByExpense(srcExpense);
        List cellList = dao.getExpenseCellListByExpense(srcExpense);
        
        for (Iterator itorRow = rowList.iterator(); itorRow.hasNext();) {
            ExpenseRow srcRow = (ExpenseRow) itorRow.next();
            ExpenseRow copyRow = new ExpenseRow();
            copyRow.setDate(srcRow.getDate());
            copyRow.setExpense(copyExpense);
            copyRow.setProjectCode(srcRow.getProjectCode());
            dao.saveExpenseRow(copyRow);
            for (Iterator itorCell = getCellListFromRow(cellList,srcRow).iterator(); itorCell.hasNext();) {
                ExpenseCell srcCell = (ExpenseCell) itorCell.next();
                ExpenseCell copyCell = new ExpenseCell();
                copyCell.setAmount(srcCell.getAmount());
                copyCell.setDescription(srcCell.getDescription());
                copyCell.setExpenseSubCategory(srcCell.getExpenseSubCategory());
                copyCell.setRow(copyRow);
                dao.saveExpenseCell(copyCell);
            }
        }
        List rechargeList = getExpenseRechargeListByExpense(srcExpense);
        for (Iterator itor = rechargeList.iterator(); itor.hasNext();) {
            ExpenseRecharge srcRecharge = (ExpenseRecharge) itor.next();
            ExpenseRecharge copyRecharge = new ExpenseRecharge();
            copyRecharge.setAmount(srcRecharge.getAmount());
            copyRecharge.setCustomer(srcRecharge.getCustomer());
            copyRecharge.setPerson(srcRecharge.getPerson());
            copyRecharge.setPersonDepartment(srcRecharge.getPersonDepartment());
            copyRecharge.setExpense(copyExpense);
            copyRecharge.setId(null);
            dao.saveExpenseRecharge(copyRecharge);
        }
        
        Map conditions = new HashMap();
        conditions.put(ExpenseAttachmentQueryCondition.EXPENSE_ID_EQ, srcExpense.getId());
        List attachmentList = attachmentDao.getExpenseAttachmentList(conditions, 0, -1, ExpenseAttachmentQueryOrder.ID, false);
        for (Iterator itor = attachmentList.iterator(); itor.hasNext();) {
            ExpenseAttachment srcAttachment = (ExpenseAttachment) itor.next();
            ExpenseAttachment copyAttachment = new ExpenseAttachment();
            copyAttachment.setExpense(copyExpense);
            copyAttachment.setDescription(srcAttachment.getDescription());
            copyAttachment.setFileName(srcAttachment.getFileName());
            copyAttachment.setFileSize(srcAttachment.getFileSize());
            copyAttachment.setUploadDate(srcAttachment.getUploadDate());
            copyAttachment = attachmentDao.insertExpenseAttachment(copyAttachment);
            attachmentDao.saveExpenseAttachmentContent(copyAttachment.getId(),attachmentDao.getExpenseAttachmentContent(srcAttachment.getId()));
        }
        
        
        
        return copyExpense;
        
    }

    
    public void rejectExpenseByFinalConfirm(Expense ep, User currentUser, String comment) {
        insertRejectApproveRequest(approveDao, ep, currentUser, comment);
        rejectExpense(ep,currentUser,comment);
    }
    
    public void rejectExpenseByFinalClaim(Expense ep, User currentUser, String comment) {
        insertRejectApproveRequest(approveDao, ep, currentUser, comment);
        dao.deleteExpenseClaimListByExpense(ep);
        rejectExpense(ep,currentUser,comment);
    }

    public List getExpenseCategoriesAndUserageAmountBySiteId(Integer siteId) {
        return dao.getExpenseCategoriesAndUserageAmountBySiteId(siteId);
    }

    public List viewApprover(Expense expense) {
        try {
            //因为是在提交后在扣减预算，所以在view approver的时候，需要先扣预算，然后执行approve flow，然后在把预算加回来
            YearlyBudget yb = null;
            if (expense.getYearlyBudget() != null) {               
                yb = yearlyBudgetManager.getYearlyBudget(expense.getYearlyBudget().getId());
                yb.updateActualAmount(new BigDecimal(0d), expense.getAmount());
                yearlyBudgetManager.updateYearBudget(yb);
                expense.setYearlyBudget(yb);
            }
            List eList = flowManager.executeApproveFlow(expense);
            if (expense.getYearlyBudget() != null) {
                yb.updateActualAmount(expense.getAmount(), new BigDecimal(0d));
                yearlyBudgetManager.updateYearBudget(yb);
                expense.setYearlyBudget(yb);
            }
            return eList;
        } catch (ExecuteFlowEmptyResultException e) {
            throw new ActionException("flow.execute.notApproverFound");
        } catch (NoAvailableFlowToExecuteException e) {
            throw new ActionException("flow.execute.notFlowFound");
        }
    }
    
    private void updateBudgetAmount(Expense expense, BigDecimal oldAmount,BigDecimal newAmount)
    {
        if (expense != null && expense.getYearlyBudget() != null) {
            YearlyBudget yb = yearlyBudgetManager.getYearlyBudget(expense.getYearlyBudget().getId());
            yb.updateActualAmount(oldAmount, newAmount);
            yearlyBudgetManager.updateYearBudget(yb);
            expense.setYearlyBudget(yb);
        }
    }

}
