package com.aof.model.kpi;

import java.io.Serializable;
import java.util.Date;

import com.aof.model.admin.PurchaseCategory;
import com.aof.model.admin.Site;

public abstract class AbstractKPIPurchaseCategorySummary implements Serializable {

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
    private PurchaseCategory purchaseCategory;
    
    /** nullable persistent field */
    private int purchaseRequestCreatedToday;
    
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
     * @return Returns the purchaseCategory.
     */
    public PurchaseCategory getPurchaseCategory() {
        return purchaseCategory;
    }

    /**
     * @param purchaseCategory The purchaseCategory to set.
     */
    public void setPurchaseCategory(PurchaseCategory purchaseCategory) {
        this.purchaseCategory = purchaseCategory;
    }

    /**
     * @return Returns the purchaseRequestCreatedToday.
     */
    public int getPurchaseRequestCreatedToday() {
        return purchaseRequestCreatedToday;
    }

    /**
     * @param purchaseRequestCreatedToday The purchaseRequestCreatedToday to set.
     */
    public void setPurchaseRequestCreatedToday(int purchaseRequestCreatedToday) {
        this.purchaseRequestCreatedToday = purchaseRequestCreatedToday;
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
        if (!(ks instanceof KPIPurchaseCategorySummary))
            return false;
        KPIPurchaseCategorySummary that = (KPIPurchaseCategorySummary) ks;
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
    
    public AbstractKPIPurchaseCategorySummary()
    {
    }

    public AbstractKPIPurchaseCategorySummary(java.lang.Integer id)
    {
        this.setId(id);
    }
}
