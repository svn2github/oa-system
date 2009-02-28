/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.business.pr;

import java.io.Serializable;
import java.math.BigDecimal;

import com.aof.model.admin.ExchangeRate;
import com.aof.model.admin.ProjectCode;
import com.aof.model.admin.PurchaseSubCategory;
import com.aof.model.admin.Supplier;
import com.aof.model.admin.SupplierItem;

/**
 * 该类代表capex_req_item_history表中的一行
 * 
 * @author nicebean
 * @version 1.0 (2005-11-19)
 */
public abstract class AbstractCapexRequestHistoryItem implements Serializable {
    /**
     * The cached hash code value for this instance. Settting to 0 triggers
     * re-calculation.
     */
    private int hashValue = 0;

    /** identifier field */
    private Integer id;

    /** persistent field */
    private PurchaseSubCategory purchaseSubCategory;

    /** nullable persistent field */
    private String supplierName;

    /** persistent field */
    private String supplierItemSepc;

    /** persistent field */
    private BigDecimal exchangeRateValue;

    /** persistent field */
    private BigDecimal price;

    /** persistent field */
    private int quantity;

    /** persistent field */
    private CapexRequestHistory capexRequestHistory;

    /** nullable persistent field */
    private Supplier supplier;

    /** nullable persistent field */
    private SupplierItem supplierItem;

    /** persistent field */
    private ExchangeRate exchangeRate;
    
    private ProjectCode projectCode;

    /** default constructor */
    public AbstractCapexRequestHistoryItem() {
    }

    /** minimal constructor */
    public AbstractCapexRequestHistoryItem(Integer id) {
        setId(id);
    }

    /**
     * @return Returns the capexRequestHistory.
     */
    public CapexRequestHistory getCapexRequestHistory() {
        return capexRequestHistory;
    }

    /**
     * @param capexRequestHistory
     *            The capexRequestHistory to set.
     */
    public void setCapexRequestHistory(CapexRequestHistory capexRequestHistory) {
        this.capexRequestHistory = capexRequestHistory;
    }

    /**
     * @return Returns the exchangeRate.
     */
    public ExchangeRate getExchangeRate() {
        return exchangeRate;
    }

    /**
     * @param exchangeRate
     *            The exchangeRate to set.
     */
    public void setExchangeRate(ExchangeRate exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    /**
     * @return Returns the id.
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     *            The id to set.
     */
    protected void setId(Integer id) {
        this.hashValue = 0;
        this.id = id;
    }

    /**
     * @return Returns the price.
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price
     *            The price to set.
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * @return Returns the quantity.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity
     *            The quantity to set.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return Returns the supplier.
     */
    public Supplier getSupplier() {
        return supplier;
    }

    /**
     * @param supplier
     *            The supplier to set.
     */
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    /**
     * @return Returns the supplierItem.
     */
    public SupplierItem getSupplierItem() {
        return supplierItem;
    }

    /**
     * @param supplierItem
     *            The supplierItem to set.
     */
    public void setSupplierItem(SupplierItem supplierItem) {
        this.supplierItem = supplierItem;
    }

    /**
     * @return Returns the supplierItemSepc.
     */
    public String getSupplierItemSepc() {
        return supplierItemSepc;
    }

    /**
     * @param supplierItemSepc
     *            The supplierItemSepc to set.
     */
    public void setSupplierItemSepc(String supplierItemSepc) {
        this.supplierItemSepc = supplierItemSepc;
    }

    /**
     * @return Returns the supplierName.
     */
    public String getSupplierName() {
        return supplierName;
    }

    /**
     * @param supplierName
     *            The supplierName to set.
     */
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    /**
     * @return Returns the exchangeRateValue.
     */
    public BigDecimal getExchangeRateValue() {
        return exchangeRateValue;
    }

    /**
     * @param exchangeRateValue
     *            The exchangeRateValue to set.
     */
    public void setExchangeRateValue(BigDecimal exchangeRateValue) {
        this.exchangeRateValue = exchangeRateValue;
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
        if (!(rhs instanceof CapexRequestHistoryItem))
            return false;
        CapexRequestHistoryItem that = (CapexRequestHistoryItem) rhs;
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

    public ProjectCode getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(ProjectCode projectCode) {
        this.projectCode = projectCode;
    }

}
