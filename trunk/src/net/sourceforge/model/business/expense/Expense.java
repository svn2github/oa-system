/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.business.expense;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sourceforge.model.Loggable;
import net.sourceforge.model.admin.Site;
import net.sourceforge.model.business.Approvable;
import net.sourceforge.model.business.BaseApproveRequest;
import net.sourceforge.model.business.BaseRecharge;
import net.sourceforge.model.business.Controllable;
import net.sourceforge.model.business.Notifiable;
import net.sourceforge.model.business.Rechargeable;
import net.sourceforge.model.metadata.RuleType;
import net.sourceforge.model.metadata.YesNo;

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
     * @see net.sourceforge.model.business.Rechargeable#createNewRechargeObj()
     */
    public BaseRecharge createNewRechargeObj() {
        ExpenseRecharge er = new ExpenseRecharge();
        er.setExpense(this);
        return er;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sourceforge.model.business.Approvable#createNewApproveRequestObj()
     */
    public BaseApproveRequest createNewApproveRequestObj() {
        return new ExpenseApproveRequest();
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sourceforge.model.business.Approvable#getApproveFlowName()
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
     * @see net.sourceforge.model.business.Controllable#getControlFlowName()
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
    
    public YesNo getApproveWithBudget() {
        return (this.getYearlyBudget() != null) ? YesNo.YES : YesNo.NO;
    }
    
    public BigDecimal getApproveOverBudget() {        
        if (getYearlyBudget() != null) {
            /*
             * 正常情况(数据库中的值)下，budget的actualAmount已经包含pr的amount，
             * over budget是计算该pr导致超出budget的remainAmount的值，
             * 此时的remainAmount应该是不包括该pr的amount的。
             * 考虑到这种情况，实际的超出值就是budget当前remainAmount的负值
             */
            return getYearlyBudget().getRemainAmount();
        }
        return null;
    }
    
    public BigDecimal getApproveOverBudgetPercentage() {
        BigDecimal amount = null;
        if (getYearlyBudget() != null) {
            amount = getYearlyBudget().getAmount();
        } else {
            return null;
        }
        return getApproveOverBudget().multiply(new BigDecimal(100d)).divide(amount, BigDecimal.ROUND_HALF_EVEN);        
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
     * @see net.sourceforge.model.business.Notifiable#getNotifyFlowName()
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
