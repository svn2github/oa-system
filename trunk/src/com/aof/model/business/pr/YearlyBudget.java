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
import java.util.Set;

import com.aof.model.Loggable;
import com.aof.model.admin.Department;
import com.aof.model.admin.PurchaseCategory;
import com.aof.model.admin.PurchaseSubCategory;
import com.aof.model.admin.Site;
import com.aof.model.business.Notifiable;
import com.aof.model.metadata.BudgetType;
import com.aof.model.metadata.RuleType;
import com.aof.ruleengine.FailRuleWhenAllConditionThrowMeException;

/**
 * A class that represents a row in the 'yearly_budget' table.
 * 
 * @author nicebean
 * @version 1.0 (2005-11-19)
 */
public class YearlyBudget extends AbstractYearlyBudget implements Serializable, Notifiable,Loggable {
    /**
     * Simple constructor of YearlyBudget instances.
     */
    public YearlyBudget() {
    }

    /**
     * Constructor of YearlyBudget instances given a simple primary key.
     * 
     * @param id
     */
    public YearlyBudget(Integer id) {
        super(id);        
    }

    public BigDecimal getRemainAmount() {
        BigDecimal amount = this.getAmount();
        if (amount == null)
            amount = new BigDecimal(0d);

        BigDecimal actualAmount = this.getActualAmount();
        if (actualAmount == null)
            actualAmount = new BigDecimal(0d);

        return amount.subtract(actualAmount);
    }

    /* Add customized code below */

    private Set departments;

    private String departmentIdString;

    private Collection purchaseCategorys;

    private Collection purchaseSubCategorys;

    private boolean ignoreAmountCondition;

    /**
     * @return Returns the ignoreAmountCondition.
     */
    public boolean isIgnoreAmountCondition() {
        return ignoreAmountCondition;
    }

    /**
     * @param ignoreAmountCondition
     *            The ignoreAmountCondition to set.
     */
    public void setIgnoreAmountCondition(boolean ignoreAmountCondition) {
        this.ignoreAmountCondition = ignoreAmountCondition;
    }

    public Set getDepartments() {
        return departments;
    }

    public void setDepartments(Set departments) {
        this.departments = departments;
    }

    /**
     * @return Returns the departmentIdString.
     */
    public String getDepartmentIdString() {
        return departmentIdString;
    }

    /**
     * @param departmentIdString
     *            The departmentIdString to set.
     */
    public void setDepartmentIdString(String departmentIdString) {
        this.departmentIdString = departmentIdString;
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

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.model.business.Notifiable#getNotifyFlowName()
     */
    public String getNotifyFlowName() {
        return this.getSite().getId() + RuleType.YEARLY_BUDGET_FILTERS.getPrefixUrl();
    }

    public String getNotifyEmailTemplateName() {
        return "YearlyBudgetFilter.vm";
    }

    public Map getNotifyEmailContext() {
        Map context = new HashMap();
        context.put("yb", this);
        return context;
    }

    /**
     * 返回Department Id列表 (审批用)
     * 
     * @return 包含Department对象Id的集合
     */
    public Collection getApproveDepartment() {
        Collection result = new ArrayList();
        Collection departments = getDepartments();
        if (departments != null) {
            for (Iterator itor = departments.iterator(); itor.hasNext();) {
                Department d = (Department) itor.next();
                result.add(d.getId());
            }
        }
        return result;
    }

    /**
     * 返回PurchaseCategory Id列表 (审批用)
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
     * 返回PurchaseSubCategory Id列表 (审批用)
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

    public BudgetType getNotifyBudgetType() {
        return this.getType();
    }

    public BigDecimal getNotifyRemainAmount() {
        return this.getRemainAmount();
    }

    public BigDecimal getApproveAmount() {
        if (ignoreAmountCondition) {
            throw new FailRuleWhenAllConditionThrowMeException();
        }
        return this.getAmount();
    }

    public void updateActualAmount(BigDecimal oldAmount, BigDecimal amount) {
        BigDecimal actualAmount = this.getActualAmount();
        actualAmount = actualAmount.subtract(oldAmount);
        actualAmount = actualAmount.add(amount);
        this.setActualAmount(actualAmount);
    }

    public Site getLogSite() {
        return this.getSite();
    }

    public String getLogTargetName() {
        return LOG_TARGET_NAME;
    }

    public String getLogTargetId() {
        return this.getId().toString();
    }

    public String[][] getLogFieldInfo(String action) {
        return (String[][]) actionFieldInfo.get(action);
    }
    
    
    public static final String LOG_ACTION_NEW = "New";
    public static final String LOG_ACTION_MODIFY = "Modify";
    public static final String LOG_ACTION_DELETE = "Delete";
    public static final String LOG_ACTION_FREEZE = "Freeze";
    public static final String LOG_ACTION_UNFREEZE = "Unfreeze";
    
    private static final String LOG_TARGET_NAME="Yearly Budget";
    
    private static final Map actionFieldInfo = new HashMap();
    static {
        actionFieldInfo.put(LOG_ACTION_NEW, 
                new String[][] { 
                    { "Department", "departments", "id" },
                    { "Budget Type", "type", "label" },
                    { "Year", "year", null },
                    { "Budget Amount", "amount", null },
                    { "Base Currency", "site.baseCurrency", "code" },
                    { "Purchase Category", "purchaseCategory", "description" },
                    { "Purchase Sub Category", "purchaseSubCategory", "description" },
        });
        actionFieldInfo.put(LOG_ACTION_MODIFY, 
                new String[][] { 
                    { "Department", "departments", "id" },
                    { "Budget Amount", "amount", null },
                    { "Status", "status", "label" },
        });
        actionFieldInfo.put(LOG_ACTION_DELETE, new String[][] { });
        actionFieldInfo.put(LOG_ACTION_FREEZE, new String[][] { });
        actionFieldInfo.put(LOG_ACTION_UNFREEZE, 
                new String[][] {}
        );
    }
}
