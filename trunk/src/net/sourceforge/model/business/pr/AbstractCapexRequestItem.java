/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.business.pr;

import java.io.Serializable;
import java.math.BigDecimal;

import net.sourceforge.model.admin.ExchangeRate;
import net.sourceforge.model.admin.PurchaseSubCategory;
import net.sourceforge.model.admin.Supplier;
import net.sourceforge.model.admin.SupplierItem;

/**
 * 该类代表capex_request_item表中的一行
 * 
 * @author nicebean
 * @version 1.0 (2005-11-19)
 */
public abstract class AbstractCapexRequestItem implements Serializable {
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
    private CapexRequest capexRequest;

    /** nullable persistent field */
    private Supplier supplier;

    /** nullable persistent field */
    private SupplierItem supplierItem;

    /** persistent field */
    private ExchangeRate exchangeRate;

    /** default constructor */
    public AbstractCapexRequestItem() {
    }

    /** minimal constructor */
    public AbstractCapexRequestItem(Integer id) {
        setId(id);
    }

    /**
     * @return Returns the capexRequest.
     */
    public CapexRequest getCapexRequest() {
        return capexRequest;
    }

    /**
     * @param capexRequest
     *            The capexRequest to set.
     */
    public void setCapexRequest(CapexRequest capexRequest) {
        this.capexRequest = capexRequest;
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
        if (!(rhs instanceof CapexRequestItem))
            return false;
        CapexRequestItem that = (CapexRequestItem) rhs;
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
