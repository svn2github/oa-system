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

import com.aof.model.admin.Department;
import com.aof.model.admin.ExchangeRate;
import com.aof.model.admin.ProjectCode;
import com.aof.model.admin.Supplier;
import com.aof.model.admin.SupplierItem;
import com.aof.model.admin.User;
import com.aof.model.metadata.YesNo;

/**
 * 该类代表pr_history_item表中的一行
 * 
 * @author nicebean
 * @version 1.0 (2005-12-01)
 */
public abstract class AbstractPurchaseRequestHistoryItem implements Serializable {
    /**
     * The cached hash code value for this instance. Settting to 0 triggers
     * re-calculation.
     */
    private int hashValue = 0;

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String supplierName;

    /** persistent field */
    private String supplierItemSepc;

    /** persistent field */
    private BigDecimal exchangeRateValue;

    /** persistent field */
    private BigDecimal unitPrice;

    /** persistent field */
    private int quantity;

    /** nullable persistent field */
    private Date dueDate;

    /** persistent field */
    private YesNo isRecharge;

    /** persistent field */
    private PurchaseRequestHistory purchaseRequestHistory;

    /** nullable persistent field */
    private Supplier supplier;

    /** nullable persistent field */
    private SupplierItem supplierItem;

    /** persistent field */
    private ExchangeRate exchangeRate;

    /** nullable persistent field */
    private User buyForUser;

    /** nullable persistent field */
    private Department buyForDepartment;
    
    private ProjectCode projectCode;

    /** default constructor */
    public AbstractPurchaseRequestHistoryItem() {
    }

    /** minimal constructor */
    public AbstractPurchaseRequestHistoryItem(Integer id) {
        setId(id);
    }

    /**
     * @return Returns the buyForDepartment.
     */
    public Department getBuyForDepartment() {
        return buyForDepartment;
    }

    /**
     * @param buyForDepartment The buyForDepartment to set.
     */
    public void setBuyForDepartment(Department buyForDepartment) {
        this.buyForDepartment = buyForDepartment;
    }

    /**
     * @return Returns the buyForUser.
     */
    public User getBuyForUser() {
        return buyForUser;
    }

    /**
     * @param buyForUser The buyForUser to set.
     */
    public void setBuyForUser(User buyForUser) {
        this.buyForUser = buyForUser;
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
     * @return Returns the dueDate.
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * @param dueDate The dueDate to set.
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
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
     * @param id The id to set.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return Returns the isRecharge.
     */
    public YesNo getIsRecharge() {
        return isRecharge;
    }

    /**
     * @param isRecharge The isRecharge to set.
     */
    public void setIsRecharge(YesNo isRecharge) {
        this.isRecharge = isRecharge;
    }

    /**
     * @return Returns the purchaseRequestHistory.
     */
    public PurchaseRequestHistory getPurchaseRequestHistory() {
        return purchaseRequestHistory;
    }

    /**
     * @param purchaseRequestHistory The purchaseRequestHistory to set.
     */
    public void setPurchaseRequestHistory(PurchaseRequestHistory purchaseRequestHistory) {
        this.purchaseRequestHistory = purchaseRequestHistory;
    }

    /**
     * @return Returns the quantity.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity The quantity to set.
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
     * @param supplier The supplier to set.
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
     * @param supplierItem The supplierItem to set.
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
     * @param supplierItemSepc The supplierItemSepc to set.
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
     * @param supplierName The supplierName to set.
     */
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    /**
     * @return Returns the unitPrice.
     */
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    /**
     * @param unitPrice The unitPrice to set.
     */
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
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
        if (!(rhs instanceof PurchaseRequestHistoryItem))
            return false;
        PurchaseRequestHistoryItem that = (PurchaseRequestHistoryItem) rhs;
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
