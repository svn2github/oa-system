/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.business.pr;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.aof.model.Loggable;
import com.aof.model.admin.PurchaseSubCategory;
import com.aof.model.admin.Site;
import com.aof.model.business.Approvable;
import com.aof.model.business.BaseApproveRequest;
import com.aof.model.business.BasePurchaser;
import com.aof.model.business.Controllable;
import com.aof.model.business.Notifiable;
import com.aof.model.business.Purchasable;
import com.aof.model.metadata.PurchaseRequestStatus;
import com.aof.model.metadata.RuleType;
import com.aof.model.metadata.YesNo;

/**
 * A class that represents a row in the 'purchase_request' table.
 * 
 * @author nicebean
 * @version 1.0 (2005-12-01)
 */
public class PurchaseRequest extends AbstractPurchaseRequest implements Serializable, Approvable, Purchasable, Controllable, Notifiable, Loggable {
    private BigDecimal maxItemPrice;
    
    public BigDecimal getMaxItemPrice() {
        return maxItemPrice;
    }

    public void setMaxItemPrice(BigDecimal maxItemPrice) {
        this.maxItemPrice = maxItemPrice;
    }

    /**
     * Simple constructor of PurchaseRequest instances.
     */
    public PurchaseRequest() {
        this.setIsDelegate(YesNo.NO);
    }

    /**
     * Constructor of PurchaseRequest instances given a simple primary key.
     * 
     * @param id
     */
    public PurchaseRequest(String id) {
        super(id);
    }

    /* Add customized code below */
    private BigDecimal purchaseAmount;

    /**
     * @return Returns the purchaseAmount.
     */
    public BigDecimal getPurchaseAmount() {
        return purchaseAmount;
    }

    /**
     * @param purchaseAmount
     *            The purchaseAmount to set.
     */
    public void setPurchaseAmount(BigDecimal purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    /**
     * 返回CapexRequest是否可以修改，DRAFT和REJECTED的状态是可以修改的 => REJECTED状态不可修改 2007-12-21
     * 
     * @return true: 可以修改; false: 不可以修改
     */
    public boolean isEditable() {
        return PurchaseRequestStatus.DRAFT.equals(this.getStatus());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.model.business.Approvable#createNewApproveRequestObj()
     */
    public BaseApproveRequest createNewApproveRequestObj() {
        return new PurchaseRequestApproveRequest();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.model.business.Approvable#getApproveFlowName()
     */
    public String getApproveFlowName() {
        return this.getDepartment().getSite().getId() + RuleType.PR_APPROVAL_RULES.getPrefixUrl();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.model.business.Purchasable#createNewPurchaserObj()
     */
    public BasePurchaser createNewPurchaserObj() {
        PurchaseRequestPurchaser prp = new PurchaseRequestPurchaser();
        prp.setPurchaseRequest(this);
        return prp;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.model.business.Purchasable#getPurchaseFlowName()
     */
    public String getPurchaseFlowName() {
        return this.getDepartment().getSite().getId() + RuleType.PURCHASING_RULES.getPrefixUrl();
    }

    /* (non-Javadoc)
     * @see com.aof.model.business.Controllable#getControlFlowName()
     */
    public String getControlFlowName() {
        return this.getDepartment().getSite().getId() + RuleType.PURCHASING_PRICE_CONTROL_RULES.getPrefixUrl();
    }

    /**
     * 返回PurchaseRequest所属的部门id (审批、采购用)
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
     * 返回PurchaseRequest的本币金额 (审批用)
     * 
     * @return 本币金额
     */
    public BigDecimal getApproveAmount() {
        return getAmount();
    }

    /**
     * 返回PurchaseRequest所属的PurchaseCategory id (审批、采购用)
     * 
     * @return PurchaseCategory id
     */
    public Integer getApprovePurchaseCategory() {
        PurchaseSubCategory psc = getPurchaseSubCategory();
        if (psc == null)
            return null;
        return psc.getPurchaseCategory().getId();
    }

    /**
     * 返回PurchaseRequest所属的PurchaseSubCategory id (审批、采购用)
     * 
     * @return PurchaseSubCategory id
     */
    public Integer getApprovePurchaseSubCategory() {
        PurchaseSubCategory psc = getPurchaseSubCategory();
        if (psc == null)
            return null;
        return psc.getId();
    }

    /**
     * 返回purchseRequest的amount超过所用budget或capex的量 (审批用)
     * 
     * @return 
     * 对使用capex的purchseRequest, 返回amount - capex.remainAmount
     * 对使用other budget的purchaseRequest, 返回 amount - yearlyBudget.remainAmount
     * 否则，返回null
     *
     */
    public BigDecimal getApproveOverBudget() {
        if (getCapex() != null) {
            /*
             * 正常情况(数据库中的值)下，capex的actualAmount已经包含pr的amount，
             * over budget是计算该pr导致超出capex的remainAmount的值，
             * 此时的remainAmount应该是不包括该pr的amount的。
             * 考虑到这种情况，实际的超出值就是capex当前remainAmount的负值
             */
            return getCapex().getRemainAmount();
        }
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

    /**
     * 返回purchseRequest的amount超过所用budget或capex的量的百分比 (审批用)
     * 
     * @return 
     * 对使用capex的purchseRequest, 返回(amount - capex.remainAmount) / capex.amount * 100
     * 对使用other budget的purchaseRequest, 返回 (amount - yearlyBudget.remainAmount) / yearlyBudget.amount * 100
     * 否则，返回null
     *
     */
    public BigDecimal getApproveOverBudgetPercentage() {
        BigDecimal amount = null;
        if (getCapex() != null) {
            amount = getCapex().getLastApprovedRequest().getAmount();
        } else if (getYearlyBudget() != null) {
            amount = getYearlyBudget().getAmount();
        } else {
            return null;
        }
        return getApproveOverBudget().multiply(new BigDecimal(100d)).divide(amount, BigDecimal.ROUND_HALF_EVEN);
        
    }

    /**
     * 返回PR Purchase Amount - PR Approved Amount (控制用)
     * 
     * @return PR Purchase Amount - PR Approved Amount
     */
    public BigDecimal getDifferenceBetweenPurchaseAmountAndApprovedAmount() {
        return getPurchaseAmount().subtract(getAmount());
    }

    /**
     * 返回(PR Purchase Amount - PR Approved Amount) / PR Approved Amount (控制用)
     * 
     * @return (PR Purchase Amount - PR Approved Amount) / PR Approved Amount
     */
    public BigDecimal getDifferencePercentageBetweenPurchaseAmountAndApprovedAmount() {
        BigDecimal amount = getAmount();
        return getPurchaseAmount().subtract(amount).multiply(new BigDecimal(100d)).divide(amount, BigDecimal.ROUND_HALF_EVEN);
    }

    public Integer getApproveDurationDay() {
        Date approveDate = getApproveDate();
        if (approveDate == null) return null;
        Date requestDate = getRequestDate();
        if (requestDate == null) return null;
        return new Integer((int)Math.ceil(((double)(approveDate.getTime() - requestDate.getTime())) / 86400000));
    }

    public YesNo getApproveWithBudget() {
         return (this.getCapex() != null || this.getYearlyBudget() != null) ? YesNo.YES : YesNo.NO;
    }
    
    /* (non-Javadoc)
     * @see com.aof.model.business.Notifiable#getNotifyFlowName()
     */
    public String getNotifyFlowName() {
        return this.getDepartment().getSite().getId() + RuleType.PURCHASE_REQUEST_FILTERS.getPrefixUrl();
    }

    public String getNotifyEmailTemplateName() {
        return "PRFilter.vm";
    }

    public Map getNotifyEmailContext() {
        Map context = new HashMap();
        context.put("pr", this);
        return context;
    }

    public Site getLogSite() {
        if(this.getDepartment()==null) return null;
        return this.getDepartment().getSite();
    }

    public String getLogTargetName() {
        return LOG_TARGET_NAME;

    }

    public String getLogTargetId() {
        return this.getId();
    }

    public String[][] getLogFieldInfo(String action) {
        return (String[][]) actionFieldInfo.get(action);
    }
    private static final String LOG_TARGET_NAME="PR";
    
    public static final String LOG_ACTION_SUBMIT = "Submit";
    public static final String LOG_ACTION_DELETE = "Delete";
    public static final String LOG_ACTION_WITHDRAW = "Withdraw";
    public static final String LOG_ACTION_APPROVE = "Approve";
    public static final String LOG_ACTION_REJECT = "Reject";
    
    
    private static final Map actionFieldInfo = new HashMap();
    static {
        actionFieldInfo.put(LOG_ACTION_SUBMIT, 
                new String[][] { 
                    { "Department", "department", "id" },
                    { "Requestor", "requestor", "name" },
                    { "PR Amount", "amount", null },
                    { "Base Currency", "currency", "code" },
                    { "Reference Yearly Budget Code", "yearlyBudget", "code" },
                    { "Reference Capex Code", "capex", "id" },
                    { "Purchase Category", "purchaseSubCategory.purchaseCategory", "description" },
                    { "Purchase Sub Category", "purchaseSubCategory", "description" },
        });
        actionFieldInfo.put(LOG_ACTION_APPROVE, 
                new String[][] { 
                    { "PR Amount", "amount", null },
        });
        actionFieldInfo.put(LOG_ACTION_DELETE, new String[][] { });
        actionFieldInfo.put(LOG_ACTION_WITHDRAW, new String[][] { });
        actionFieldInfo.put(LOG_ACTION_REJECT, 
                new String[][] {}
        );
    }

    public String getApprovalBatchEmailTemplateName() {
        return "PRApprovalBatch.vm";
    }
    
    public String getApprovalNotifyEmailTemplateName() {
        return "PRApproval.vm";
    }

    public String getApprovedNotifyEmailTemplateName() {
        return "PRApproved.vm";
    }

    public String getRejectedNotifyEmailTemplateName() {
        return "PRRejected.vm";
    }

    public Map getApprovalNotifyEmailContext() {
        Map context = new HashMap();
        context.put("pr", this);
        return context;
    }

    public Map getApprovedNotifyEmailContext() {
        Map context = new HashMap();
        context.put("pr", this);
        return context;
    }

    public Map getRejectedNotifyEmailContext() {
        Map context = new HashMap();
        context.put("pr", this);
        return context;
    }
    
    public void emailed(Date d)
    {
        this.setEmailDate(d);
        this.setEmailTimes(this.getEmailTimes()+1);
    }

    public String getRefNo() {
        return this.getId();
    }
}
