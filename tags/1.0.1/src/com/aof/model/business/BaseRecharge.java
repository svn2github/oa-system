/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.business;

import java.io.Serializable;
import java.math.BigDecimal;

import com.aof.model.admin.Customer;
import com.aof.model.admin.Department;
import com.aof.model.admin.User;

/**
 * A class that represents base information of a row in the recharge like table.
 * 
 * @author nicebean
 * @version 1.0 (2005-11-24)
 */
public abstract class BaseRecharge implements Serializable {

    /**
     * The cached hash code value for this instance. Settting to 0 triggers
     * re-calculation.
     */
    private int hashValue = 0;

    private Integer id;

    private Customer customer;

    private Department personDepartment;

    private User person;

    private BigDecimal amount;

    private BigDecimal totalAmount;
    
    /**
     * Constructor of BaseRecharge.
     */
    protected BaseRecharge() {
    }

    /**
     * Constructor of BaseRecharge.
     * 
     * @param id
     */
    protected BaseRecharge(Integer id) {
        this.setId(id);
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
    public void setId(Integer id) {
        this.hashValue = 0;
        this.id = id;
    }

    /**
     * @return Returns the customer.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @param customer
     *            The customer to set.
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * @return Returns the amount.
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount
     *            The amount to set.
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * @return Returns the person.
     */
    public User getPerson() {
        return person;
    }

    /**
     * @param person
     *            The person to set.
     */
    public void setPerson(User person) {
        this.person = person;
    }

    /**
     * @return Returns the personDepartment.
     */
    public Department getPersonDepartment() {
        return personDepartment;
    }

    /**
     * @param personDepartment
     *            The personDepartment to set.
     */
    public void setPersonDepartment(Department personDepartment) {
        this.personDepartment = personDepartment;
    }

    /**
     * 不同子类会对应不同的table，所以应该由子类实现自己的equals方法。
     * 
     * @param rhs
     * @return boolean
     */
    public abstract boolean equals(Object rhs);

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

    /**
     * copy recharge info from specified recharge object
     * 
     * @param rechargeObj
     */
    public void copyFrom(BaseRecharge rechargeObj) {
        this.setCustomer(rechargeObj.getCustomer());
        this.setAmount(rechargeObj.getAmount());
        this.setPersonDepartment(rechargeObj.getPersonDepartment());
        this.setPerson(rechargeObj.getPerson());
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public BigDecimal getPercentage() {
        if (totalAmount == null) {
            return new BigDecimal(0d);
        }
        return amount.multiply(new BigDecimal(100d)).divide(totalAmount,12, BigDecimal.ROUND_HALF_EVEN);
    }

}
