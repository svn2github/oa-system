/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

/*
 * Created Fri Sep 23 14:47:57 CST 2005 by MyEclipse Hibernate Tool.
 */
package net.sourceforge.model.business.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sourceforge.model.Loggable;
import net.sourceforge.model.admin.Site;
import net.sourceforge.model.admin.User;
import net.sourceforge.model.business.Approvable;
import net.sourceforge.model.business.BaseApproveRequest;
import net.sourceforge.model.business.Notifiable;
import net.sourceforge.model.metadata.PurchaseOrderStatus;
import net.sourceforge.model.metadata.RuleType;

/**
 * A class that represents a row in the 'purchase_order' table. This class may
 * be customized as it is never re-generated after being created.
 */
public class PurchaseOrder extends AbstractPurchaseOrder implements Serializable, Approvable, Notifiable,Loggable {
    /**
     * Simple constructor of PurchaseOrder instances.
     */
    public PurchaseOrder() {
    }

    /**
     * Constructor of PurchaseOrder instances given a simple primary key.
     * 
     * @param id
     */
    public PurchaseOrder(String id) {
        super(id);
    }

    /* Add customized code below */
    public BigDecimal getBaseCurrencyAmount() {
        BigDecimal exchangeRateValue = getExchangeRateValue();
        if (exchangeRateValue == null)
            return null;
        BigDecimal amount = getAmount();
        if (amount == null)
            return null;
        return amount.multiply(exchangeRateValue).setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    /**
     * 返回CapexRequest是否可以修改，DRAFT和REJECTED的状态是可以修改的 => REJECTED状态不可修改 2007-12-21
     * 
     * @return true: 可以修改; false: 不可以修改
     */
    public boolean isEditable() {
        return PurchaseOrderStatus.DRAFT.equals(this.getStatus());
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sourceforge.model.business.Approvable#createNewApproveRequestObj()
     */
    public BaseApproveRequest createNewApproveRequestObj() {
        return new PurchaseOrderApproveRequest();
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sourceforge.model.business.Approvable#getApproveFlowName()
     */
    public String getApproveFlowName() {
        return this.getSite().getId() + RuleType.PO_APPROVAL_RULES.getPrefixUrl();
    }

    public User getRequestor() {
        return this.getPurchaser();
    }

    /* (non-Javadoc)
     * @see net.sourceforge.model.business.Notifiable#getNotifyFlowName()
     */
    public String getNotifyFlowName() {
        return this.getSite().getId() + RuleType.PURCHASE_ORDER_FILTERS.getPrefixUrl();
    }
    
    public String getNotifyEmailTemplateName() {
        return "POFilter.vm";
    }

    public Map getNotifyEmailContext() {
        Map context = new HashMap();
        context.put("po", this);
        return context;
    }
    
    public boolean isFromAirTicket()
    {
        return this.getSubCategory()==null;
    }
    
    public BigDecimal getApproveAmount() {
        return this.getBaseCurrencyAmount();
    }

    public Site getLogSite() {
        return this.getSite();
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
    
    private static final String LOG_TARGET_NAME="PO";
    
    public static final String LOG_ACTION_CREATE = "Create";
    public static final String LOG_ACTION_MODIFY = "Modify";
    public static final String LOG_ACTION_DELETE = "Delete";
    public static final String LOG_ACTION_CONSOLIDATE = "Consolidate";
    public static final String LOG_ACTION_SUBMIT = "Submit";
    public static final String LOG_ACTION_WITHDRAW = "Widthdraw";
    public static final String LOG_ACTION_APPROVE = "Approve";
    public static final String LOG_ACTION_REJECT = "Reject";
    public static final String LOG_ACTION_FINALCONFIRM = "Final Confirm";
    public static final String LOG_CHANGE_INSPECTOR = "change inspector";
    //public static final String LOG_ACTION_RECEIPT = "PO Receipt";
    
    
    
    private static final Map actionFieldInfo = new HashMap();
    static {
        actionFieldInfo.put(LOG_ACTION_CREATE, 
                new String[][] { 
                    { "PO No.", "id", null },
                    { "Supplier Name", "supplier", "name" },
                    { "Purchase Amount", "amount", null },
                    { "Purchase Currency", "baseCurrency", "code" },
                    { "Exchange Rate", "exchangeRate", "exchangeRate" },
                    { "Purchase Category", "subCategory.purchaseCategory", "description" },
                    { "Purchase Sub Category", "subCategory", "description" },
        });
        actionFieldInfo.put(LOG_ACTION_MODIFY, 
                new String[][] { 
                    { "Purchase Amount", "amount", null },
        });
        actionFieldInfo.put(LOG_ACTION_CONSOLIDATE, 
                new String[][] {
                { "PO No.(Multi-PO no.)", "consolidateIds", null },
                { "Purchase Amount", "amount", null },
        });
        actionFieldInfo.put(LOG_ACTION_APPROVE, 
                new String[][] { 
                    { "Purchase Amount", "amount", null },
        });
        actionFieldInfo.put(LOG_ACTION_FINALCONFIRM, 
                new String[][] { 
                    { "Purchase Amount", "amount", null },
                    { "Inspector", "inspector", "name" },
        });
        
        actionFieldInfo.put(LOG_CHANGE_INSPECTOR, 
                new String[][] { 
                    { "Inspector", "inspector", "name" },
        });
        
        actionFieldInfo.put(LOG_ACTION_DELETE,
                new String[][] { });
        
        actionFieldInfo.put(LOG_ACTION_WITHDRAW,
                new String[][] { });
        
        actionFieldInfo.put(LOG_ACTION_REJECT, 
                new String[][] {}
        );
        
        actionFieldInfo.put(LOG_ACTION_SUBMIT, 
                new String[][] {}
        );
    }
    
    private String consolidateIds;

    public String getConsolidateIds() {
        return consolidateIds;
    }

    public void setConsolidateIds(String consolidateIds) {
        this.consolidateIds = consolidateIds;
    }

    public String getApprovalBatchEmailTemplateName() {
        return "POApprovalBatch.vm";
    }
    
    public String getApprovalNotifyEmailTemplateName() {
        return "POApproval.vm";
    }

    public String getApprovedNotifyEmailTemplateName() {
        return "POApproved.vm";
    }

    public String getRejectedNotifyEmailTemplateName() {
        return "PORejected.vm";
    }

    public Map getApprovalNotifyEmailContext() {
        Map context = new HashMap();
        context.put("po", this);
        return context;
    }

    public Map getApprovedNotifyEmailContext() {
        Map context = new HashMap();
        context.put("po", this);
        return context;
    }

    public Map getRejectedNotifyEmailContext() {
        Map context = new HashMap();
        context.put("po", this);
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
    
    public User getCreator() {
        return this.getCreateUser();
    }
}
