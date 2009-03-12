/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.business.pr;

import java.io.Serializable;
import java.math.BigDecimal;

import net.sourceforge.model.admin.Currency;
import net.sourceforge.model.admin.PurchaseCategory;
import net.sourceforge.model.admin.PurchaseSubCategory;
import net.sourceforge.model.admin.Site;

/**
 * 该类代表capex表中的一行
 * 
 * @author nicebean
 * @version 1.0 (2005-11-19)
 */
public abstract class AbstractCapex implements Serializable {
    /**
     * The cached hash code value for this instance. Settting to 0 triggers
     * re-calculation.
     */
    private int hashValue = 0;

    /** identifier field */
    private String id;

    /** persistent field */
    private Site site;

    /** nullable persistent field */
    private PurchaseCategory purchaseCategory;

    /** nullable persistent field */
    private PurchaseSubCategory purchaseSubCategory;

    /** persistent field */
    private Currency currency;

    /** persistent field */
    private YearlyBudget yearlyBudget;

    /** nullable persistent field */
    private CapexRequest lastApprovedRequest;

    /** persistent field */
    private BigDecimal actualAmount;

    /** persistent field */
    private int year;

    /** persistent field */
    private int rowVersion;
    
    /** default constructor */
    public AbstractCapex() {
    }

    /** minimal constructor */
    public AbstractCapex(String id) {
        setId(id);
    }

    /**
     * @return Returns the currency.
     */
    public Currency getCurrency() {
        return currency;
    }

    /**
     * @param currency
     *            The currency to set.
     */
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    /**
     * @return Returns the id.
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            The id to set.
     */
    protected void setId(String id) {
        this.hashValue = 0;
        this.id = id;
    }

    /**
     * @return Returns the purchaseCategory.
     */
    public PurchaseCategory getPurchaseCategory() {
        return purchaseCategory;
    }

    /**
     * @param purchaseCategory
     *            The purchaseCategory to set.
     */
    public void setPurchaseCategory(PurchaseCategory purchaseCategory) {
        this.purchaseCategory = purchaseCategory;
    }

    /**
     * @return Returns the purchaseSubCategory.
     */
    public PurchaseSubCategory getPurchaseSubCategory() {
        return purchaseSubCategory;
    }

    /**
     * @param purchaseSubCategory
     *            The purchaseSubCategory to set.
     */
    public void setPurchaseSubCategory(PurchaseSubCategory purchaseSubCategory) {
        this.purchaseSubCategory = purchaseSubCategory;
    }

    /**
     * @return Returns the site.
     */
    public Site getSite() {
        return site;
    }

    /**
     * @param site
     *            The site to set.
     */
    public void setSite(Site site) {
        this.site = site;
    }

    /**
     * @return Returns the yearlyBudget.
     */
    public YearlyBudget getYearlyBudget() {
        return yearlyBudget;
    }

    /**
     * @param yearlyBudget
     *            The yearlyBudget to set.
     */
    public void setYearlyBudget(YearlyBudget yearlyBudget) {
        this.yearlyBudget = yearlyBudget;
    }

    /**
     * @return Returns the lastApprovedRequest.
     */
    public CapexRequest getLastApprovedRequest() {
        return lastApprovedRequest;
    }

    /**
     * @param lastApprovedRequest
     *            The lastApprovedRequest to set.
     */
    public void setLastApprovedRequest(CapexRequest lastApprovedRequest) {
        this.lastApprovedRequest = lastApprovedRequest;
    }

    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getRowVersion() {
        return rowVersion;
    }

    public void setRowVersion(int rowVersion) {
        this.rowVersion = rowVersion;
    }

    /**
     * Implementation of the equals comparison on the basis of equality of the
     * primary key values.
     * 
     * @param rhs
     * @return boolean
     */
    public boolean equals(Object rhs) {
        if (rhs == null)
            return false;
        if (this == rhs)
            return true;
        if (!(rhs instanceof Capex))
            return false;
        Capex that = (Capex) rhs;
        if (this.getId() != null)
            return this.getId().equals(that.getId());
        return that.getId() == null;
    }

    /**
     * Implementation of the hashCode method conforming to the Bloch pattern
     * with the exception of array properties (these are very unlikely primary
     * key types).
     * 
     * @return int
     */
    public int hashCode() {
        if (this.hashValue == 0) {
            int result = 17;
            int idValue = this.getId() == null ? 0 : this.getId().hashCode();
            result = result * 37 + idValue;
            this.hashValue = result;
        }
        return this.hashValue;
    }

}
