/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

/*
 * WARNING: DO NOT EDIT THIS FILE. This is a generated file that is synchronized
 * by MyEclipse Hibernate tool integration.
 *
 * Created Fri Sep 23 13:45:10 CST 2005 by MyEclipse Hibernate Tool.
 */
package net.sourceforge.model.business.po;

import java.math.BigDecimal;
import java.util.Date;

import net.sourceforge.model.admin.Department;
import net.sourceforge.model.admin.ExchangeRate;
import net.sourceforge.model.admin.ProjectCode;
import net.sourceforge.model.admin.PurchaseType;
import net.sourceforge.model.admin.Supplier;
import net.sourceforge.model.admin.SupplierItem;
import net.sourceforge.model.admin.User;
import net.sourceforge.model.metadata.YesNo;

/**
 * 该class代表po_item表和po_item_history表中一行共有的信息.
 */
public abstract class BasePurchaseOrderItem {
    /**
     * The cached hash code value for this instance. Settting to 0 triggers
     * re-calculation.
     */
    private int hashValue = 0;

    private Integer id;

    private Supplier supplier;

    private SupplierItem item;

    private String itemSpec;

    private ExchangeRate exchangeRate;

    private BigDecimal unitPrice;

    private int quantity;

    private Date dueDate;

    private User buyForUser;

    private Department buyForDepartment;

    private YesNo isRecharge;

    private PurchaseType purchaseType;
    
    private ProjectCode projectCode;

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
    public void setId(Integer id) {
        this.hashValue = 0;
        this.id = id;
    }

    /**
     * @return Returns the buyForUser.
     */
    public User getBuyForUser() {
        return buyForUser;
    }

    /**
     * @param buyForUser
     *            The buyForUser to set.
     */
    public void setBuyForUser(User buyForUser) {
        this.buyForUser = buyForUser;
    }

    /**
     * @return Returns the buyForDepartment.
     */
    public Department getBuyForDepartment() {
        return buyForDepartment;
    }

    /**
     * @param buyForDepartment
     *            The buyForDepartment to set.
     */
    public void setBuyForDepartment(Department buyForDepartment) {
        this.buyForDepartment = buyForDepartment;
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
     * @param dueDate
     *            The dueDate to set.
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * @return Returns the isRecharge.
     */
    public YesNo getIsRecharge() {
        return isRecharge;
    }

    /**
     * @param isRecharge
     *            The isRecharge to set.
     */
    public void setIsRecharge(YesNo isRecharge) {
        this.isRecharge = isRecharge;
    }

    /**
     * @return Returns the item.
     */
    public SupplierItem getItem() {
        return item;
    }

    /**
     * @param item
     *            The item to set.
     */
    public void setItem(SupplierItem item) {
        this.item = item;
    }

    /**
     * @return Returns the itemSpec.
     */
    public String getItemSpec() {
        return itemSpec;
    }

    /**
     * @param itemSpec
     *            The itemSpec to set.
     */
    public void setItemSpec(String itemSpec) {
        this.itemSpec = itemSpec;
    }

    /**
     * @return Returns the purchaseType.
     */
    public PurchaseType getPurchaseType() {
        return purchaseType;
    }

    /**
     * @param purchaseType
     *            The purchaseType to set.
     */
    public void setPurchaseType(PurchaseType purchaseType) {
        this.purchaseType = purchaseType;
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
     * @return Returns the unitPrice.
     */
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    /**
     * @param unitPrice
     *            The unitPrice to set.
     */
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
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
            int itemIdValue = this.getId() == null ? 0 : this.getId().hashCode();
            result = result * 37 + itemIdValue;
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
