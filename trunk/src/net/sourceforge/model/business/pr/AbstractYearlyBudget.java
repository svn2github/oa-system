/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.business.pr;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import net.sourceforge.model.admin.ExpenseCategory;
import net.sourceforge.model.admin.ExpenseSubCategory;
import net.sourceforge.model.admin.PurchaseCategory;
import net.sourceforge.model.admin.PurchaseSubCategory;
import net.sourceforge.model.admin.Site;
import net.sourceforge.model.admin.User;
import net.sourceforge.model.metadata.BudgetStatus;
import net.sourceforge.model.metadata.BudgetType;
import net.sourceforge.model.metadata.YesNo;

/**
 * 该类代表yearly_budget表中的一行
 * 
 * @author nicebean
 * @version 1.0 (2005-11-19)
 */
public abstract class AbstractYearlyBudget implements Serializable {
    /**
     * The cached hash code value for this instance. Settting to 0 triggers
     * re-calculation.
     */
    private int hashValue = 0;

    /** identifier field */
    private Integer id;

    /** persistent field */
    private String code;

    /** persistent field */
    private String name;

    /** persistent field */
    private BudgetType type;

    /** persistent field */
    private int year;

    /** persistent field */
    private int version;

    /** persistent field */
    private BigDecimal amount;

    /** persistent field */
    private BigDecimal actualAmount;

    /** persistent field */
    private BudgetStatus status;

    /** persistent field */
    private YesNo isFreeze;

    /** persistent field */
    private Date createDate;

    /** nullable persistent field */
    private Date modifyDate;

    /** persistent field */
    private Site site;

    /** nullable persistent field */
    private PurchaseCategory purchaseCategory;

    /** nullable persistent field */
    private PurchaseSubCategory purchaseSubCategory;

    /** persistent field */
    private User creator;

    /** nullable persistent field */
    private User modifier;

    /** persistent field */
    private int rowVersion;
    
    /** nullable persistent field */
    private ExpenseCategory expenseCategory;

    /** nullable persistent field */
    private ExpenseSubCategory expenseSubCategory;
    
    /** nullable persistent field */
    private Date durationFrom;
    
    /** nullable persistent field */
    private Date durationTo;

    /** default constructor */
    public AbstractYearlyBudget() {
    }

    /** minimal constructor */
    public AbstractYearlyBudget(Integer id) {
        setId(id);
    }

    /**
     * @return Returns the actualAmount.
     */
    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    /**
     * @param actualAmount
     *            The actualAmount to set.
     */
    public void setActualAmount(BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
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
     * @return Returns the code.
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     *            The code to set.
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return Returns the createDate.
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate
     *            The createDate to set.
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
     * @return Returns the isFreeze.
     */
    public YesNo getIsFreeze() {
        return isFreeze;
    }

    /**
     * @param isFreeze
     *            The isFreeze to set.
     */
    public void setIsFreeze(YesNo isFreeze) {
        this.isFreeze = isFreeze;
    }

    /**
     * @return Returns the modifier.
     */
    public User getModifier() {
        return modifier;
    }

    /**
     * @param modifier
     *            The modifier to set.
     */
    public void setModifier(User modifier) {
        this.modifier = modifier;
    }

    /**
     * @return Returns the modifyDate.
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * @param modifyDate
     *            The modifyDate to set.
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            The name to set.
     */
    public void setName(String name) {
        this.name = name;
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
     * @return Returns the status.
     */
    public BudgetStatus getStatus() {
        return status;
    }

    /**
     * @param status
     *            The status to set.
     */
    public void setStatus(BudgetStatus status) {
        this.status = status;
    }

    /**
     * @return Returns the type.
     */
    public BudgetType getType() {
        return type;
    }

    /**
     * @param type
     *            The type to set.
     */
    public void setType(BudgetType type) {
        this.type = type;
    }

    /**
     * @return Returns the year.
     */
    public int getYear() {
        return year;
    }

    /**
     * @param year
     *            The year to set.
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * @return Returns the version.
     */
    public int getVersion() {
        return version;
    }

    /**
     * @param version The version to set.
     */
    public void setVersion(int version) {
        this.version = version;
    }

    public int getRowVersion() {
        return rowVersion;
    }

    public void setRowVersion(int rowVersion) {
        this.rowVersion = rowVersion;
    }

    /**
     * @return Returns the expenseCategory.
     */
    public ExpenseCategory getExpenseCategory() {
        return expenseCategory;
    }

    /**
     * @param expenseCategory The expenseCategory to set.
     */
    public void setExpenseCategory(ExpenseCategory expenseCategory) {
        this.expenseCategory = expenseCategory;
    }

    /**
     * @return Returns the expenseSubCategory.
     */
    public ExpenseSubCategory getExpenseSubCategory() {
        return expenseSubCategory;
    }

    /**
     * @param expenseSubCategory The expenseSubCategory to set.
     */
    public void setExpenseSubCategory(ExpenseSubCategory expenseSubCategory) {
        this.expenseSubCategory = expenseSubCategory;
    }

    /**
     * @return Returns the durationFrom.
     */
    public Date getDurationFrom() {
        return durationFrom;
    }

    /**
     * @param durationFrom The durationFrom to set.
     */
    public void setDurationFrom(Date durationFrom) {
        this.durationFrom = durationFrom;
    }

    /**
     * @return Returns the durationTo.
     */
    public Date getDurationTo() {
        return durationTo;
    }

    /**
     * @param durationTo The durationTo to set.
     */
    public void setDurationTo(Date durationTo) {
        this.durationTo = durationTo;
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
        if (!(rhs instanceof YearlyBudget))
            return false;
        YearlyBudget that = (YearlyBudget) rhs;
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
