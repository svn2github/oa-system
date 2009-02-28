/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.business.expense;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.aof.model.Loggable;
import com.aof.model.admin.Site;
import com.aof.model.business.Approvable;
import com.aof.model.business.BaseApproveRequest;
import com.aof.model.business.BaseRecharge;
import com.aof.model.business.Controllable;
import com.aof.model.business.Notifiable;
import com.aof.model.business.Rechargeable;
import com.aof.model.metadata.RuleType;

/**
 * A class that represents a row in the 'expense' table.
 * 
 * @author nicebean
 * @version 1.0 (2005-11-18)
 */
public class Expense extends AbstractExpense implements Serializable, Rechargeable, Approvable, Controllable, Notifiable , Loggable {
    /**
     * Simple constructor of Expense instances.
     */
    public Expense() {
    }

    /**
     * Constructor of Expense instances given a simple primary key.
     * 
     * @param id
     */
    public Expense(String id) {
        super(id);
    }

    /* Add customized code below */

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.model.business.Rechargeable#createNewRechargeObj()
     */
    public BaseRecharge createNewRechargeObj() {
        ExpenseRecharge er = new ExpenseRecharge();
        er.setExpense(this);
        return er;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.model.business.Approvable#createNewApproveRequestObj()
     */
    public BaseApproveRequest createNewApproveRequestObj() {
        return new ExpenseApproveRequest();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.model.business.Approvable#getApproveFlowName()
     */
    public String getApproveFlowName() {
        if (this.getDepartment() != null && this.getDepartment().getSite() != null)
            return this.getDepartment().getSite().getId() + RuleType.EXPENSE_APPROVAL_RULES.getPrefixUrl();
        else
            return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.model.business.Controllable#getControlFlowName()
     */
    public String getControlFlowName() {
        if (this.getDepartment() != null && this.getDepartment().getSite() != null)
            return this.getDepartment().getSite().getId() + RuleType.EXPENSE_CLAIM_RULES.getPrefixUrl();
        else
            return null;
    }

    /**
     * 返回Expense所属的部门id (审批用)
     * 
     * @return 部门id
     */
    public Integer getApproveDepartment() {
        if (this.getDepartment() != null) {
            return this.getDepartment().getId();
        }
        return null;
    }

    /**
     * 返回Expense的本币金额 (审批用)
     * 
     * @return 本币金额
     */
    public BigDecimal getApproveAmount() {
        if (this.getAmount() != null && this.getExchangeRate() != null)
            return getAmount().multiply(getExchangeRate());
        else if (this.getExchangeRate() == null)
            return getAmount();
        else
            return new BigDecimal(0d);
    }

    /**
     * 返回Expense的expense category id (审批用)
     * 
     * @return expense category id
     */
    public Integer getApproveExpenseCategory() {
        if (this.getExpenseCategory() != null)
            return getExpenseCategory().getId();
        else
            return null;
    }

    /**
     * 返回Expense的claim amount - amount (审批用)
     * 
     * @return claim amount - amount
     */
    public BigDecimal getDifferenceBetweenClaimAmountAndAmount() {
        return getConfirmedAmount().subtract(getAmount());
    }

    /**
     * 返回Expense的(claim amount - amount) / amount (审批用)
     * 
     * @return (claim amount - amount) / amount
     */
    public BigDecimal getDifferencePercentageBetweenClaimAmountAndAmount() {
        BigDecimal amount = getAmount().setScale(2, BigDecimal.ROUND_HALF_UP);        
        return getConfirmedAmount().subtract(amount).multiply((new BigDecimal(100d)).setScale(0, BigDecimal.ROUND_HALF_UP)).divide(amount, BigDecimal.ROUND_HALF_UP);
    }
    
    public Integer getApproveDurationDay() {
        Date approveDate = getApproveDate();
        if (approveDate == null) return null;
        Date requestDate = getRequestDate();
        if (requestDate == null) return null;
        return new Integer((int)Math.ceil(((double)(approveDate.getTime() - requestDate.getTime())) / 86400000));
    }

    /* (non-Javadoc)
     * @see com.aof.model.business.Notifiable#getNotifyFlowName()
     */
    public String getNotifyFlowName() {
        return this.getDepartment().getSite().getId() + RuleType.EXPENSE_FILTERS.getPrefixUrl();
    }

    public String getNotifyEmailTemplateName() {
        return "ExpenseFilter.vm";
    }
    
    public Map getNotifyEmailContext() {
        Map context = new HashMap();
        context.put("expense", this);
        return context;
    }
    
    /*
     * 以下代码处理log 
     *
     */
    public static final String LOG_ACTION_SUBMIT = "Submit";
    public static final String LOG_ACTION_DELETE = "Delete";
    public static final String LOG_ACTION_WITHDRAW = "Withdraw";
    public static final String LOG_ACTION_APPROVE = "Approve";
    public static final String LOG_ACTION_REJECT = "Reject";
    public static final String LOG_ACTION_FINAL_CONFIRM = "Final Confirm";
    
    private static final Map actionFieldInfo = new HashMap();
    
    static {
        actionFieldInfo.put(LOG_ACTION_SUBMIT, 
                new String[][] { 
                    new String[] { "Department", "department", "id" },
                    new String[] { "Requestor", "requestor", "name" },
                    new String[] { "Expense Amount", "amount", null },
                    new String[] { "Expense Currency", "expenseCurrency", "name" },
                    new String[] { "Expense Category", "expenseCategory", "description" },
                    new String[] { "Reference TA No.", "travelApplication", "id" },
        });

        actionFieldInfo.put(LOG_ACTION_DELETE, new String[][] { });
        actionFieldInfo.put(LOG_ACTION_WITHDRAW, new String[][] { });
        actionFieldInfo.put(LOG_ACTION_APPROVE, 
                new String[][] { 
                    new String[] { "Expense Amount", "amount", null },
        });
        actionFieldInfo.put(LOG_ACTION_REJECT, new String[][] { });
        actionFieldInfo.put(LOG_ACTION_FINAL_CONFIRM, 
                new String[][] { 
                    new String[] { "Final Confirm Amount", "confirmedAmount", null },
        });
    }
    
    public Site getLogSite() {
        return this.getDepartment().getSite();
    }

    public String getLogTargetName() {
        return "Expense";
    }

    public String getLogTargetId() {
        return this.getId();
    }

    public String[][] getLogFieldInfo(String action) {
        return (String[][]) actionFieldInfo.get(action);
    }
    
    public String getApprovalBatchEmailTemplateName() {
        return "ExpenseApprovalBatch.vm";
    }
    
    public String getApprovalNotifyEmailTemplateName() {
        return "ExpenseApproval.vm";
    }

    public String getApprovedNotifyEmailTemplateName() {
        return "ExpenseApproved.vm";
    }

    public String getRejectedNotifyEmailTemplateName() {
        return "ExpenseRejected.vm";
    }

    public Map getApprovalNotifyEmailContext() {
        Map context = new HashMap();
        context.put("expense", this);
        return context;
    }

    public Map getApprovedNotifyEmailContext() {
        Map context = new HashMap();
        context.put("expense", this);
        return context;
    }

    public Map getRejectedNotifyEmailContext() {
        Map context = new HashMap();
        context.put("expense", this);
        return context;
    }

    public void emailed(Date d) {
        this.setEmailDate(d);
        this.setEmailTimes(this.getEmailTimes()+1);
    }
    
    public String getRefNo() {
        return this.getId();
    }
}
