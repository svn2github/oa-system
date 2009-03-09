/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.business.pr;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.aof.model.admin.PurchaseCategory;
import com.aof.model.admin.PurchaseSubCategory;
import com.aof.model.admin.Site;
import com.aof.model.business.Notifiable;
import com.aof.model.metadata.RuleType;
import com.aof.model.metadata.YesNo;
import com.aof.ruleengine.FailRuleWhenAllConditionThrowMeException;

/**
 * A class that represents a row in the 'capex' table.
 * 
 * @author nicebean
 * @version 1.0 (2005-11-19)
 */
public class Capex extends AbstractCapex implements Serializable, Notifiable {
    /**
     * Simple constructor of Capex instances.
     */
    public Capex() {
    }

    /**
     * Constructor of Capex instances given a simple primary key.
     * 
     * @param id
     */
    public Capex(String id) {
        super(id);
    }

    /* Add customized code below */
    private Collection capexDepartments;

    private Collection purchaseCategorys;

    private Collection purchaseSubCategorys;

    private Collection purchaseRequestItems;
    
    private boolean ignoreAmountCondition;
    
    private BigDecimal[] monthlyAmount;
    
    /**
     * @return Returns the ignoreAmountCondition.
     */
    public boolean isIgnoreAmountCondition() {
        return ignoreAmountCondition;
    }

    /**
     * @param ignoreAmountCondition The ignoreAmountCondition to set.
     */
    public void setIgnoreAmountCondition(boolean ignoreAmountCondition) {
        this.ignoreAmountCondition = ignoreAmountCondition;
    }

    /**
     * @return Returns the capexDepartments.
     */
    public Collection getCapexDepartments() {
        return capexDepartments;
    }

    /**
     * @param capexDepartments
     *            The capexDepartments to set.
     */
    public void setCapexDepartments(Collection capexDepartments) {
        this.capexDepartments = capexDepartments;
    }

    /**
     * @return Returns the purchaseCategorys.
     */
    public Collection getPurchaseCategorys() {
        return purchaseCategorys;
    }

    /**
     * @param purchaseCategorys
     *            The purchaseCategorys to set.
     */
    public void setPurchaseCategorys(Collection purchaseCategorys) {
        this.purchaseCategorys = purchaseCategorys;
    }

    /**
     * @return Returns the purchaseSubCategorys.
     */
    public Collection getPurchaseSubCategorys() {
        return purchaseSubCategorys;
    }

    /**
     * @param purchaseSubCategorys
     *            The purchaseSubCategorys to set.
     */
    public void setPurchaseSubCategorys(Collection purchaseSubCategorys) {
        this.purchaseSubCategorys = purchaseSubCategorys;
    }

    /**
     * @return Returns the purchaseRequestItems.
     */
    public Collection getPurchaseRequestItems() {
        return purchaseRequestItems;
    }

    /**
     * @param purchaseRequestItems
     *            The purchaseRequestItems to set.
     */
    public void setPurchaseRequestItems(Collection purchaseRequestItems) {
        this.purchaseRequestItems = purchaseRequestItems;
    }

    public void updateActualAmount(BigDecimal oldAmount, BigDecimal amount) {
        BigDecimal actualAmount = this.getActualAmount();
        actualAmount = actualAmount.subtract(oldAmount);
        actualAmount = actualAmount.add(amount);
        this.setActualAmount(actualAmount);
    }

    public BigDecimal getRemainAmount() {
        CapexRequest capexRequest = this.getLastApprovedRequest();
        BigDecimal amount = null;
        if (capexRequest != null) {
            amount = capexRequest.getAmount();
        }
        if (amount == null) {
            amount = new BigDecimal(0d);
        }

        BigDecimal actualAmount = this.getActualAmount();
        if (actualAmount == null) {
            actualAmount = new BigDecimal(0d);
        }

        return amount.subtract(actualAmount);
    }

    /* (non-Javadoc)
     * @see com.aof.model.business.Notifiable#getNotifyFlowName()
     */
    public String getNotifyFlowName() {
        return getSite().getId() + RuleType.CAPEX_FILTERS.getPrefixUrl();
    }
    
    public String getNotifyEmailTemplateName() {
        return "CapexFilter.vm";
    }

    public Map getNotifyEmailContext() {
        Map context = new HashMap();
        context.put("capex", this);
        return context;
    }

    public BigDecimal getNotifyRemainAmount() {
        return getRemainAmount();
    }

    /**
     * 返回所属Capex对象的Department Id列表 (通知用)
     * 
     * @return 包含Department对象Id的集合
     */
    public Collection getApproveDepartment() {
        Collection result = new ArrayList();
        Collection departments = getCapexDepartments();
        if (departments != null) {
            for (Iterator itor = departments.iterator(); itor.hasNext();) {
                CapexDepartment cd = (CapexDepartment) itor.next();
                result.add(cd.getDepartment().getId());
            }
        }
        return result;
    }

    /**
     * 返回所属Capex对象的PurchaseCategory Id列表 (通知用)
     * 
     * @return 包含PurchaseCategory对象Id的集合
     */
    public Collection getApprovePurchaseCategory() {
        Collection result = new ArrayList();
        Collection categorys = getPurchaseCategorys();
        if (categorys != null) {
            for (Iterator itor = categorys.iterator(); itor.hasNext();) {
                PurchaseCategory pc = (PurchaseCategory) itor.next();
                result.add(pc.getId());
            }
        }
        return result;
    }

    /**
     * 返回所属Capex对象的PurchaseSubCategory Id列表 (通知用)
     * 
     * @return 包含PurchaseSubCategory对象Id的集合
     */
    public Collection getApprovePurchaseSubCategory() {
        Collection result = new ArrayList();
        Collection subCategorys = getPurchaseSubCategorys();
        if (subCategorys != null) {
            for (Iterator itor = subCategorys.iterator(); itor.hasNext();) {
                PurchaseSubCategory psc = (PurchaseSubCategory) itor.next();
                result.add(psc.getId());
            }
        }
        return result;
    }

    public BigDecimal getApproveAmount() {
        if (ignoreAmountCondition) {
            throw new FailRuleWhenAllConditionThrowMeException();
        }
        CapexRequest capexRequest = this.getLastApprovedRequest();
        if (capexRequest == null) {
            return null;
        }
        return capexRequest.getAmount();
    }

    public YesNo getApproveWithBudget() {
        return (this.getYearlyBudget() != null) ? YesNo.YES : YesNo.NO;
   }

    public Site getLogSite() {
        return this.getSite();
    }

    public BigDecimal[] getMonthlyAmount() {
        return monthlyAmount;
    }

    public void setMonthlyAmount(BigDecimal[] monthlyAmount) {
        this.monthlyAmount = monthlyAmount;
    }

}
