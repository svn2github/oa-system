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
 * Created Fri Sep 23 14:47:57 CST 2005 by MyEclipse Hibernate Tool.
 */
package net.sourceforge.model.admin;

import java.io.Serializable;
import java.math.BigDecimal;

import net.sourceforge.model.metadata.EnabledDisabled;

/**
 * A class that represents a row in the t_item table. 
 * You can customize the behavior of this class by editing the class, {@link TItem()}.
 * WARNING: DO NOT EDIT THIS FILE. This is a generated file that is synchronized
 * by MyEclipse Hibernate tool integration.
 */
public abstract class AbstractSupplierItem 
    implements Serializable
{
    /** The cached hash code value for this instance.  Settting to 0 triggers re-calculation. */
    private int hashValue = 0;

    /** The composite primary key value. */
    private Integer id;

    /** The value of the tCurrency association. */
    private Currency currency;

    /** The value of the tPurSubcate association. */
    private PurchaseSubCategory purchaseSubCategory;

    /** The value of the tSupplier association. */
    private Supplier supplier;

    /** The value of the simple itemSepc property. */
    private String sepc;

    /** The value of the simple unitPrice property. */
    private BigDecimal unitPrice;

    /** The value of the simple enabled property. */
    private EnabledDisabled enabled;

    /** The value of the simple refErpNo property. */
    private String erpNo;

    /**
     * Simple constructor of AbstractTItem instances.
     */
    public AbstractSupplierItem()
    {
    }

    /**
     * Constructor of AbstractTItem instances given a simple primary key.
     * @param id
     */
    public AbstractSupplierItem(Integer id)
    {
        this.setId(id);
    }

    /**
     * Return the simple primary key value that identifies this object.
     * @return Integer
     */
    public Integer getId()
    {
        return id;
    }

    /**
     * Set the simple primary key value that identifies this object.
     * @param itemId
     */
    public void setId(Integer id)
    {
        this.hashValue = 0;
        this.id = id;
    }

    
    
    /**
	 * @return Returns the currency.
	 */
	public Currency getCurrency() {
		return currency;
	}

	/**
	 * @param currency The currency to set.
	 */
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	/**
	 * @return Returns the enabled.
	 */
	public EnabledDisabled getEnabled() {
		return enabled;
	}

	/**
	 * @param enabled The enabled to set.
	 */
	public void setEnabled(EnabledDisabled enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return Returns the purchaseSubCategory.
	 */
	public PurchaseSubCategory getPurchaseSubCategory() {
		return purchaseSubCategory;
	}

	/**
	 * @param purchaseSubCategory The purchaseSubCategory to set.
	 */
	public void setPurchaseSubCategory(PurchaseSubCategory purchaseSubCategory) {
		this.purchaseSubCategory = purchaseSubCategory;
	}

	/**
	 * @return Returns the sepc.
	 */
	public String getSepc() {
		return sepc;
	}

	/**
	 * @param sepc The sepc to set.
	 */
	public void setSepc(String sepc) {
		this.sepc = sepc;
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
     * Return the value of the unit_price column.
     * @return BigDecimal
     */
    public BigDecimal getUnitPrice()
    {
        return this.unitPrice;
    }

    /**
     * Set the value of the unit_price column.
     * @param unitPrice
     */
    public void setUnitPrice(BigDecimal unitPrice)
    {
        this.unitPrice = unitPrice;
    }

   


    /**
	 * @return Returns the erpNo.
	 */
	public String getErpNo() {
		return erpNo;
	}

	/**
	 * @param erpNo The erpNo to set.
	 */
	public void setErpNo(String erpNo) {
		this.erpNo = erpNo;
	}

	/**
     * Implementation of the equals comparison on the basis of equality of the primary key values.
     * @param rhs
     * @return boolean
     */
    public boolean equals(Object rhs)
    {
    	if (rhs == null) return false;
        if (this == rhs) return true;
        if (!(rhs instanceof SupplierItem)) return false;
        SupplierItem that = (SupplierItem) rhs;
        if (this.getId() != null) return this.getId().equals(that.getId());
        return that.getId() == null;
    }

    /**
     * Implementation of the hashCode method conforming to the Bloch pattern with
     * the exception of array properties (these are very unlikely primary key types).
     * @return int
     */
    public int hashCode()
    {
        if (this.hashValue == 0)
        {
            int result = 17;
            int itemIdValue = this.getId() == null ? 0 : this.getId().hashCode();
            result = result * 37 + itemIdValue;
            this.hashValue = result;
        }
        return this.hashValue;
    }
}