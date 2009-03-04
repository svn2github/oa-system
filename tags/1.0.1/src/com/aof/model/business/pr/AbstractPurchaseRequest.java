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

import com.aof.model.admin.Currency;
import com.aof.model.admin.Department;
import com.aof.model.admin.PurchaseSubCategory;
import com.aof.model.admin.User;
import com.aof.model.metadata.PurchaseRequestStatus;
import com.aof.model.metadata.YesNo;


/**
 * 该类代表purchase_request表中的一行
 * @author nicebean
 * @version 1.0 (2005-12-01)
 */
public abstract class AbstractPurchaseRequest implements Serializable {
    /**
     * The cached hash code value for this instance. Settting to 0 triggers
     * re-calculation.
     */
    private int hashValue = 0;

    /** identifier field */
    private String id;

    /** persistent field */
    private String title;

    /** nullable persistent field */
    private String description;

    /** persistent field */
    private PurchaseRequestStatus status;

    /** nullable persistent field */
    private Date requestDate;

    /** persistent field */
    private Date createDate;
    
    /** persistent field */
    private BigDecimal amount;

    /** nullable persistent field */
    private String approveRequestId;

    /** persistent field */
    private PurchaseSubCategory purchaseSubCategory;

    /** persistent field */
    private Department department;

    /** nullable persistent field */
    private Capex capex;

    /** nullable persistent field */
    private YearlyBudget yearlyBudget;

    /** persistent field */
    private Currency currency;
    
    /** persistent field */
    private int rowVersion;

    /** nullable persistent field */
    private User requestor;

    /** persistent field */
    private User creator;

    /** nullable persistent field */
    private User purchaser;

    private Date approveDate;
    
    private YesNo isDelegate;
    
    private Date emailDate;
    
    private int emailTimes=0;
    
    
    /** default constructor */
    public AbstractPurchaseRequest() {
    }

    /** minimal constructor */
    public AbstractPurchaseRequest(String id, String title, PurchaseRequestStatus status, Date createDate, PurchaseSubCategory purchaseSubCategory, Department department, Currency currency, User creator) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.createDate = createDate;
        this.purchaseSubCategory = purchaseSubCategory;
        this.department = department;
        this.currency = currency;
        this.creator = creator;
    }

    /** minimal constructor */
    public AbstractPurchaseRequest(String id) {
        setId(id);
    }

    /**
     * @return Returns the approveRequestId.
     */
    public String getApproveRequestId() {
        return approveRequestId;
    }

    /**
     * @param approveRequestId The approveRequestId to set.
     */
    public void setApproveRequestId(String approveRequestId) {
        this.approveRequestId = approveRequestId;
    }

    /**
     * @return Returns the capex.
     */
    public Capex getCapex() {
        return capex;
    }

    /**
     * @param capex The capex to set.
     */
    public void setCapex(Capex capex) {
        this.capex = capex;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * @return Returns the createDate.
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate The createDate to set.
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return Returns the creator.
     */
    public User getCreator() {
        return creator;
    }

    /**
     * @param creator The creator to set.
     */
    public void setCreator(User creator) {
        this.creator = creator;
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
     * @return Returns the department.
     */
    public Department getDepartment() {
        return department;
    }

    /**
     * @param department The department to set.
     */
    public void setDepartment(Department department) {
        this.department = department;
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
    public String getId() {
        return id;
    }

    /**
     * @param id The id to set.
     */
    public void setId(String id) {
        this.hashValue = 0;
        this.id = id;
    }

    /**
     * @return Returns the purchaser.
     */
    public User getPurchaser() {
        return purchaser;
    }

    /**
     * @param purchaser The purchaser to set.
     */
    public void setPurchaser(User purchaser) {
        this.purchaser = purchaser;
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
     * @return Returns the requestDate.
     */
    public Date getRequestDate() {
        return requestDate;
    }

    /**
     * @param requestDate The requestDate to set.
     */
    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    /**
     * @return Returns the requestor.
     */
    public User getRequestor() {
        return requestor;
    }

    /**
     * @param requestor The requestor to set.
     */
    public void setRequestor(User requestor) {
        this.requestor = requestor;
    }

    /**
     * @return Returns the status.
     */
    public PurchaseRequestStatus getStatus() {
        return status;
    }

    /**
     * @param status The status to set.
     */
    public void setStatus(PurchaseRequestStatus status) {
        this.status = status;
    }

    /**
     * @return Returns the title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return Returns the yearlyBudget.
     */
    public YearlyBudget getYearlyBudget() {
        return yearlyBudget;
    }

    /**
     * @param yearlyBudget The yearlyBudget to set.
     */
    public void setYearlyBudget(YearlyBudget yearlyBudget) {
        this.yearlyBudget = yearlyBudget;
    }

    /**
     * @return Returns the approveDate.
     */
    public Date getApproveDate() {
        return approveDate;
    }

    /**
     * @param approveDate
     *            The approveDate to set.
     */
    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
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
        if (!(rhs instanceof PurchaseRequest))
            return false;
        PurchaseRequest that = (PurchaseRequest) rhs;
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

    public int getRowVersion() {
        return rowVersion;
    }

    public void setRowVersion(int rowVersion) {
        this.rowVersion = rowVersion;
    }

    public YesNo getIsDelegate() {
        return isDelegate;
    }

    public void setIsDelegate(YesNo isDelegate) {
        this.isDelegate = isDelegate;
    }

    public Date getEmailDate() {
        return emailDate;
    }

    public void setEmailDate(Date emailDate) {
        this.emailDate = emailDate;
    }

    public int getEmailTimes() {
        return emailTimes;
    }

    public void setEmailTimes(int emailTimes) {
        this.emailTimes = emailTimes;
    }



}
