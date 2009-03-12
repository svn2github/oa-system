/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.business.expense;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import net.sourceforge.model.admin.Currency;
import net.sourceforge.model.admin.Department;
import net.sourceforge.model.admin.ExpenseCategory;
import net.sourceforge.model.admin.User;
import net.sourceforge.model.business.pr.YearlyBudget;
import net.sourceforge.model.business.ta.TravelApplication;
import net.sourceforge.model.metadata.ExpenseStatus;
import net.sourceforge.model.metadata.ExportStatus;
import net.sourceforge.model.metadata.YesNo;

/**
 * 该类代表expense表中的一行
 * 
 * @author nicebean
 * @version 1.0 (2005-11-18)
 */
public abstract class AbstractExpense implements Serializable {
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
    private BigDecimal amount;

    /** persistent field */
    private Date requestDate;

    /** persistent field */
    private Date createDate;

    /** persistent field */
    private YesNo isRecharge;

    /** persistent field */
    private ExportStatus exportStatus;

    /** nullable persistent field */
    private TravelApplication travelApplication;

    /** persistent field */
    private Department department;

    /** persistent field */
    private ExpenseCategory expenseCategory;

    /** persistent field */
    private Currency expenseCurrency;

    /** persistent field */
    private Currency baseCurrency;

    /** persistent field */
    private User requestor;

    /** persistent field */
    private User creator;

    /** persistent field */
    private ExpenseStatus status;

    /** persistent field */
    private BigDecimal exchangeRate;

    /** persistent field */
    private String approveRequestId;
    
    //private BigDecimal baseAmount;
    private Date approveDate;

    private BigDecimal confirmedAmount;
    
    private Date emailDate;
    
    private int emailTimes = 0;
    
    private Date confirmDate;
    
    private YearlyBudget yearlyBudget;
    
    /**
     * @return Returns the emailDate.
     */
    public Date getEmailDate() {
        return emailDate;
    }

    /**
     * @param emailDate The emailDate to set.
     */
    public void setEmailDate(Date emailDate) {
        this.emailDate = emailDate;
    }

    /**
     * @return Returns the emailTimes.
     */
    public int getEmailTimes() {
        return emailTimes;
    }

    /**
     * @param emailTimes The emailTimes to set.
     */
    public void setEmailTimes(int emailTimes) {
        this.emailTimes = emailTimes;
    }

    /** default constructor */
    public AbstractExpense() {
    }

    /** minimal constructor */
    public AbstractExpense(String id) {
        setId(id);
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
     * @return Returns the baseCurrency.
     */
    public Currency getBaseCurrency() {
        return baseCurrency;
    }

    /**
     * @param baseCurrency
     *            The baseCurrency to set.
     */
    public void setBaseCurrency(Currency baseCurrency) {
        this.baseCurrency = baseCurrency;
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
     * @param creator
     *            The creator to set.
     */
    public void setCreator(User creator) {
        this.creator = creator;
    }

    /**
     * @return Returns the department.
     */
    public Department getDepartment() {
        return department;
    }

    /**
     * @param department
     *            The department to set.
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
     * @param description
     *            The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return Returns the expenseCategory.
     */
    public ExpenseCategory getExpenseCategory() {
        return expenseCategory;
    }

    /**
     * @param expenseCategory
     *            The expenseCategory to set.
     */
    public void setExpenseCategory(ExpenseCategory expenseCategory) {
        this.expenseCategory = expenseCategory;
    }

    /**
     * @return Returns the expenseCurrency.
     */
    public Currency getExpenseCurrency() {
        return expenseCurrency;
    }

    /**
     * @param expenseCurrency
     *            The expenseCurrency to set.
     */
    public void setExpenseCurrency(Currency expenseCurrency) {
        this.expenseCurrency = expenseCurrency;
    }

    /**
     * @return Returns the exportStatus.
     */
    public ExportStatus getExportStatus() {
        return exportStatus;
    }

    /**
     * @param exportStatus
     *            The exportStatus to set.
     */
    public void setExportStatus(ExportStatus exportStatus) {
        this.exportStatus = exportStatus;
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
    public void setId(String id) {
        hashValue = 0;
        this.id = id;
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
     * @return Returns the requestDate.
     */
    public Date getRequestDate() {
        return requestDate;
    }

    /**
     * @param requestDate
     *            The requestDate to set.
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
     * @param requestor
     *            The requestor to set.
     */
    public void setRequestor(User requestor) {
        this.requestor = requestor;
    }

    /**
     * @return Returns the title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     *            The title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return Returns the travelApplication.
     */
    public TravelApplication getTravelApplication() {
        return travelApplication;
    }

    /**
     * @param travelApplication
     *            The travelApplication to set.
     */
    public void setTravelApplication(TravelApplication travelApplication) {
        this.travelApplication = travelApplication;
    }

    /**
     * @return Returns the approveRequestId.
     */
    public String getApproveRequestId() {
        return approveRequestId;
    }

    /**
     * @param approveRequestId
     *            The approveRequestId to set.
     */
    public void setApproveRequestId(String approveRequestId) {
        this.approveRequestId = approveRequestId;
    }

    /**
     * @return Returns the status.
     */
    public ExpenseStatus getStatus() {
        return status;
    }

    /**
     * @param status
     *            The status to set.
     */
    public void setStatus(ExpenseStatus status) {
        this.status = status;
    }

    /**
     * @return Returns the exchangeRate.
     */
    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    /**
     * @param exchangeRate
     *            The exchangeRate to set.
     */
    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    /**
     * @return Returns the baseAmount.
     */
    public BigDecimal getBaseAmount() {
        if (this.amount != null && this.exchangeRate != null) {
            return this.amount.multiply(this.exchangeRate);
        } else {
            return null;
        }
    }

    /**
     * @param baseAmount The baseAmount to set.
     */
//    public void setBaseAmount(BigDecimal baseAmount) {
//        this.baseAmount = baseAmount;
//    }

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

    public BigDecimal getConfirmedAmount() {
        return confirmedAmount;
    }

    public void setConfirmedAmount(BigDecimal confirmedAmount) {
        this.confirmedAmount = confirmedAmount;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
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
        if (!(rhs instanceof Expense))
            return false;
        Expense that = (Expense) rhs;
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
