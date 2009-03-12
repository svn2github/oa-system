/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.business.pr;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.sourceforge.model.Loggable;
import net.sourceforge.model.admin.Site;
import net.sourceforge.model.business.Approvable;
import net.sourceforge.model.business.BaseApproveRequest;
import net.sourceforge.model.metadata.CapexRequestStatus;
import net.sourceforge.model.metadata.RuleType;
import net.sourceforge.model.metadata.YesNo;

/**
 * A class that represents a row in the 'capex_request' table.
 * 
 * @author nicebean
 * @version 1.0 (2005-11-19)
 */
public class CapexRequest extends AbstractCapexRequest implements Serializable, Approvable, Loggable {
    /**
     * Default value of reference currency
     */
    public static String DEFALUT_REFERENCE_CURRENCY = "EUR";
    
    /**
     * Default value of Activity
     */
    public static String DEFALUT_ACTIVITY = "Ceramics";
    
    /**
     * Simple constructor of CapexRequest instances.
     */
    public CapexRequest() {
    }

    /**
     * Constructor of CapexRequest instances given a simple primary key.
     * 
     * @param id
     */
    public CapexRequest(Integer id) {
        super(id);
    }

    /* Add customized code below */

    /**
     * 返回CapexRequest是否可以修改，DRAFT和REJECTED的状态是可以修改的 => REJECTED状态不可修改 2007-12-21
     * 
     * @return true: 可以修改; false: 不可以修改
     */
    public boolean isEditable() {
        return CapexRequestStatus.DRAFT.equals(this.getStatus());
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sourceforge.model.business.Approvable#createNewApproveRequestObj()
     */
    public BaseApproveRequest createNewApproveRequestObj() {
        return new CapexRequestApproveRequest();
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sourceforge.model.business.Approvable#getApproveFlowName()
     */
    public String getApproveFlowName() {
        return getCapex().getSite().getId() + RuleType.CAPEX_APPROVAL_RULES.getPrefixUrl();
    }

    /**
     * 返回所属Capex对象的Department Id列表 (审批用)
     * 
     * @return 包含Department对象Id的集合
     */
    public Collection getApproveDepartment() {
        return getCapex().getApproveDepartment();
    }

    /**
     * 返回amount属性(审批用)
     * 
     * @return amount
     */
    public BigDecimal getApproveAmount() {
        return getAmount();
    }

    /**
     * 返回CapexRequest的amount和它所属yearlyBudget的remainAmount的差值(审批用)
     * 
     * @return amount - capex.yearlyBudget.remainAmount, if capex is without yearly budget will return null.
     */
    public BigDecimal getApproveOverBudget() {
        /*
         * 正常情况(数据库中的值)下，budget的actualAmount已经包含capex request的amount，
         * over budget是计算该capex request导致超出budget的remainAmount的值，
         * 此时的remainAmount应该是不包括该capex request的amount的。
         * 考虑到这种情况，实际的超出值就是budget当前remainAmount的负值
         */
        /*
         * modify by Jackey 2006-3-16
         * for support nonbudget capex
         */
        if (getCapex().getYearlyBudget() != null) {
            return getCapex().getYearlyBudget().getRemainAmount().negate();
        }
        
        return null;
    }

    /**
     * 返回CapexRequest的amount和它所属yearlyBudget的remainAmount的差值占yearlyBudget的amount百分比(审批用)
     * 
     * @return (amount - capex.yearlyBudget.remainAmount) * 100 / capex.yearlyBudget.amount, if capex is without yearly budget will return null.
     */
    public BigDecimal getApproveOverBudgetPercentage() {
        /*
         * modify by Jackey 2006-3-16
         * for support nonbudget capex
         */
        if (getApproveOverBudget() != null) {
            return getApproveOverBudget().multiply(new BigDecimal(100d)).divide(getCapex().getYearlyBudget().getAmount(), BigDecimal.ROUND_HALF_EVEN);
        }
        
        return null;
    }
    
    /**
     * 返回所属Capex对象的PurchaseCategory Id列表 (审批用)
     * 
     * @return 包含PurchaseCategory对象Id的集合
     */
    public Collection getApprovePurchaseCategory() {
        return getCapex().getApprovePurchaseCategory();
    }

    /**
     * 返回所属Capex对象的PurchaseSubCategory Id列表 (审批用)
     * 
     * @return 包含PurchaseSubCategory对象Id的集合
     */
    public Collection getApprovePurchaseSubCategory() {
        return getCapex().getApprovePurchaseSubCategory();
    }
    
    public YesNo getApproveWithBudget() {
        return getCapex().getApproveWithBudget();
    }

    /*
     * 以下代码处理log 
     *
     */
    public static final String LOG_ACTION_INITIAL_SUBMIT = "Initial Submit";
    public static final String LOG_ACTION_INCREASE_SUBMIT = "Increase Submit";
    public static final String LOG_ACTION_DELETE = "Delete";
    public static final String LOG_ACTION_WITHDRAW = "Withdraw";
    public static final String LOG_ACTION_APPROVE = "Approve";
    public static final String LOG_ACTION_REJECT = "Reject";
    
    private static final Map actionFieldInfo = new HashMap();
    
    static {
        actionFieldInfo.put(LOG_ACTION_INITIAL_SUBMIT, 
                new String[][] { 
                    { "Department", "capex.capexDepartments", "id" },
                    { "Requestor", "requestor", "name" },
                    { "Capex Amount", "amount", null },
                    { "Base Currency", "capex.currency", "name" },
                    { "Reference Budget Code", "capex.yearlyBudget", "code" },
                    { "Purchase Category", "capex.purchaseCategory", "description" },
                    { "Purchase Sub Category", "capex.purchaseSubCategory", "description" },
        });
        actionFieldInfo.put(LOG_ACTION_INCREASE_SUBMIT, 
                new String[][] { 
                    { "Capex Amount", "amount", null },
        });
        actionFieldInfo.put(LOG_ACTION_DELETE, new String[][] { });
        actionFieldInfo.put(LOG_ACTION_WITHDRAW, new String[][] { });
        actionFieldInfo.put(LOG_ACTION_APPROVE, 
                new String[][] { 
                    { "Capex Amount", "amount", null },
        });
        actionFieldInfo.put(LOG_ACTION_REJECT, new String[][] { });
        
    }
    
    public Site getLogSite() {
        return this.getCapex().getSite();
    }

    public String getLogTargetName() {
        return "Capex";
    }

    public String getLogTargetId() {
        return this.getCapex().getId();
    }

    public String[][] getLogFieldInfo(String action) {
        return (String[][]) actionFieldInfo.get(action);
    }
    
    /* add by jackey for caculate total capex amount
     * 
     * capex total amount is not caculate from item list any more
     * but instead by sum of capital expenditure amount and other expenses related amount
     * and asset disposal amount
     * */
    public BigDecimal getTotalAmount() {
        return this.getCapexCapitalizedAmount()
        .add(this.getOtherExpenseAmount()
                .add(this.getAssetDisposalAmount()));
    }
    
    public String getApprovalBatchEmailTemplateName() {
        return "CapexApprovalBatch.vm";
    }
    
    public String getApprovalNotifyEmailTemplateName() {
        return "CapexApproval.vm";
    }

    public String getApprovedNotifyEmailTemplateName() {
        return "CapexApproved.vm";
    }

    public String getRejectedNotifyEmailTemplateName() {
        return "CapexRejected.vm";
    }

    public Map getApprovalNotifyEmailContext() {
        Map context = new HashMap();
        context.put("capexRequest", this);
        return context;
    }

    public Map getApprovedNotifyEmailContext() {
        Map context = new HashMap();
        context.put("capexRequest", this);
        return context;
    }

    public Map getRejectedNotifyEmailContext() {
        Map context = new HashMap();
        context.put("capexRequest", this);
        return context;
    }
    
    public String getRefNo() {
        return this.getCapex().getId();
    }
}
