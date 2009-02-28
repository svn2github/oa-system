/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.business.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 该类代表payment_schdl_det表的一行记录
 */
public abstract class AbstractPaymentScheduleDetail implements Serializable {
    /**
     * The cached hash code value for this instance. Settting to 0 triggers
     * re-calculation.
     */
    private int hashValue = 0;

    private Integer id;
    
    private PurchaseOrder purchaseOrder;

    private String description;

    private Date date;

    private BigDecimal amount;


    /**
     * 缺省构造函数
     */
    public AbstractPaymentScheduleDetail() {
    }

    /**
     * 给定主键的构造函数
     * 
     * @param id
     */
    public AbstractPaymentScheduleDetail(Integer id) {
        this.setId(id);
    }


    /**
     * @return Returns the amount.
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount The amount to set.
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * @return Returns the date.
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date The date to set.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return Returns the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
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
    protected void setId(Integer id) {
        this.hashValue = 0;
        this.id = id;
    }

    /**
     * @return Returns the purchaseOrder.
     */
    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    /**
     * @param purchaseOrder The purchaseOrder to set.
     */
    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
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
        if (!(rhs instanceof PaymentScheduleDetail))
            return false;
        PaymentScheduleDetail that = (PaymentScheduleDetail) rhs;
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
            int itemIdValue = this.getId() == null ? 0 : this.getId().hashCode();
            result = result * 37 + itemIdValue;
            this.hashValue = result;
        }
        return this.hashValue;
    }

}
