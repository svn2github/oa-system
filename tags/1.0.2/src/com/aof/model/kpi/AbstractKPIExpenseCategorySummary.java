package com.aof.model.kpi;

import java.io.Serializable;
import java.util.Date;

import com.aof.model.admin.ExpenseCategory;
import com.aof.model.admin.Site;

public abstract class AbstractKPIExpenseCategorySummary implements Serializable {

    private int hashValue = 0;
    
    /** identifier field */
    private Integer id;
    
    /** nullable persistent field */
    private KPISummary kpiSummary;
    
    /** nullable persistent field */
    private Site site;
    
    /** nullable persistent field */
    private Date summaryDate;
    
    /** nullable persistent field */
    private ExpenseCategory expenseCategory;
    
    /** nullable persistent field */
    private int expenseRequestCreatedToday;
    

    /**
     * @return Returns the expenseCategory.
     */
    public ExpenseCategory getExpenseCategory() {
        return expenseCategory;
    }

    /**
     * @param expenseCategory The expenseCategory to set.
     */
    public void setExpenseCategory(ExpenseCategory expenseCategory) {
        this.expenseCategory = expenseCategory;
    }

    /**
     * @return Returns the expenseRequestCreatedToday.
     */
    public int getExpenseRequestCreatedToday() {
        return expenseRequestCreatedToday;
    }

    /**
     * @param expenseRequestCreatedToday The expenseRequestCreatedToday to set.
     */
    public void setExpenseRequestCreatedToday(int expenseRequestCreatedToday) {
        this.expenseRequestCreatedToday = expenseRequestCreatedToday;
    }

    /**
     * @return Returns the id.
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id to set.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return Returns the kpiSummary.
     */
    public KPISummary getKpiSummary() {
        return kpiSummary;
    }

    /**
     * @param kpiSummary The kpiSummary to set.
     */
    public void setKpiSummary(KPISummary kpiSummary) {
        this.kpiSummary = kpiSummary;
    }

    /**
     * @return Returns the site.
     */
    public Site getSite() {
        return site;
    }

    /**
     * @param site The site to set.
     */
    public void setSite(Site site) {
        this.site = site;
    }

    /**
     * @return Returns the summaryDate.
     */
    public Date getSummaryDate() {
        return summaryDate;
    }

    /**
     * @param summaryDate The summaryDate to set.
     */
    public void setSummaryDate(Date summaryDate) {
        this.summaryDate = summaryDate;
    }
    
    public boolean equals(Object ks) {
        if (ks == null)
            return false;
        if (this == ks)
            return true;
        if (!(ks instanceof KPIExpenseCategorySummary))
            return false;
        KPIExpenseCategorySummary that = (KPIExpenseCategorySummary) ks;
        if (this.getId() != null)
            return this.getId().equals(that.getId());
        return that.getId() == null;
    }
    
    public int hashCode() {
        if (this.hashValue == 0) {
            int result = 17;
            int itemIdValue = this.getId() == null ? 0 : this.getId().hashCode();
            result = result * 37 + itemIdValue;
            this.hashValue = result;
        }
        return this.hashValue;
    }
    
    public AbstractKPIExpenseCategorySummary()
    {
    }

    public AbstractKPIExpenseCategorySummary(java.lang.Integer id)
    {
        this.setId(id);
    }
    

}
