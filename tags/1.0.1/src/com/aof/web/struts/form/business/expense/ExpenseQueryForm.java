/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.web.struts.form.business.expense;

import com.aof.model.business.expense.query.ExpenseQueryOrder;
import com.aof.web.struts.form.BaseSessionQueryForm;


/**
 * ²éÑ¯ExpenseµÄForm
 * @author ych
 * @version 1.0 (Nov 19, 2005)
 */
public class ExpenseQueryForm extends BaseSessionQueryForm {

    private String id;

    private String travelApplication_id;

    private String department_id;
    
    private String site_id;

    private String expenseCategory_id;

    private String expenseCurrency_code;

    private String baseCurrency_code;

    private String requestor_id;

    private String creator_id;

    private String title;

    private String description;

    private String status;

    private String amount;

    private String exchangeRate;

    private String requestDate;

    private String crateDate;

    private String isRecharge;

    private String approveRequestId;

    private String exportStatus;
    
    private String requestDateFrom;
    
    private String requestDateTo;
    
    private String amountFrom;
    
    private String amountTo;
    
    private String requestor;
    
    private String createDateFrom;
    
    private String createDateTo;

    /**
     * @return Returns the amount.
     */
    public String getAmount() {
        return amount;
    }

    /**
     * @param amount The amount to set.
     */
    public void setAmount(String amount) {
        this.amount = amount;
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
     * @return Returns the baseCurrency_code.
     */
    public String getBaseCurrency_code() {
        return baseCurrency_code;
    }

    /**
     * @param baseCurrency_code The baseCurrency_code to set.
     */
    public void setBaseCurrency_code(String baseCurrency_code) {
        this.baseCurrency_code = baseCurrency_code;
    }

    /**
     * @return Returns the crateDate.
     */
    public String getCrateDate() {
        return crateDate;
    }

    /**
     * @param crateDate The crateDate to set.
     */
    public void setCrateDate(String crateDate) {
        this.crateDate = crateDate;
    }

    /**
     * @return Returns the creator_id.
     */
    public String getCreator_id() {
        return creator_id;
    }

    /**
     * @param creator_id The creator_id to set.
     */
    public void setCreator_id(String creator_id) {
        this.creator_id = creator_id;
    }

    /**
     * @return Returns the department_id.
     */
    public String getDepartment_id() {
        return department_id;
    }

    /**
     * @param department_id The department_id to set.
     */
    public void setDepartment_id(String department_id) {
        this.department_id = department_id;
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
     * @return Returns the exchangeRate.
     */
    public String getExchangeRate() {
        return exchangeRate;
    }

    /**
     * @param exchangeRate The exchangeRate to set.
     */
    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    /**
     * @return Returns the expenseCategory_id.
     */
    public String getExpenseCategory_id() {
        return expenseCategory_id;
    }

    /**
     * @param expenseCategory_id The expenseCategory_id to set.
     */
    public void setExpenseCategory_id(String expenseCategory_id) {
        this.expenseCategory_id = expenseCategory_id;
    }

    /**
     * @return Returns the expenseCurrency_code.
     */
    public String getExpenseCurrency_code() {
        return expenseCurrency_code;
    }

    /**
     * @param expenseCurrency_code The expenseCurrency_code to set.
     */
    public void setExpenseCurrency_code(String expenseCurrency_code) {
        this.expenseCurrency_code = expenseCurrency_code;
    }

    /**
     * @return Returns the exportStatus.
     */
    public String getExportStatus() {
        return exportStatus;
    }

    /**
     * @param exportStatus The exportStatus to set.
     */
    public void setExportStatus(String exportStatus) {
        this.exportStatus = exportStatus;
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
        this.id = id;
    }

    /**
     * @return Returns the isRecharge.
     */
    public String getIsRecharge() {
        return isRecharge;
    }

    /**
     * @param isRecharge The isRecharge to set.
     */
    public void setIsRecharge(String isRecharge) {
        this.isRecharge = isRecharge;
    }

    /**
     * @return Returns the requestDate.
     */
    public String getRequestDate() {
        return requestDate;
    }

    /**
     * @param requestDate The requestDate to set.
     */
    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    /**
     * @return Returns the requestor_id.
     */
    public String getRequestor_id() {
        return requestor_id;
    }

    /**
     * @param requestor_id The requestor_id to set.
     */
    public void setRequestor_id(String requestor_id) {
        this.requestor_id = requestor_id;
    }

    /**
     * @return Returns the status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status The status to set.
     */
    public void setStatus(String status) {
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
     * @return Returns the travelApplication_id.
     */
    public String getTravelApplication_id() {
        return travelApplication_id;
    }

    /**
     * @param travelApplication_id The travelApplication_id to set.
     */
    public void setTravelApplication_id(String travelApplication_id) {
        this.travelApplication_id = travelApplication_id;
    }

    
    

    public String getSite_id() {
        return site_id;
    }

    public void setSite_id(String site_id) {
        this.site_id = site_id;
    }

    /**
     * @return Returns the amountFrom.
     */
    public String getAmountFrom() {
        return amountFrom;
    }

    /**
     * @param amountFrom The amountFrom to set.
     */
    public void setAmountFrom(String amountFrom) {
        this.amountFrom = amountFrom;
    }

    /**
     * @return Returns the amountTo.
     */
    public String getAmountTo() {
        return amountTo;
    }

    /**
     * @param amountTo The amountTo to set.
     */
    public void setAmountTo(String amountTo) {
        this.amountTo = amountTo;
    }

    /**
     * @return Returns the requestDateFrom.
     */
    public String getRequestDateFrom() {
        return requestDateFrom;
    }

    /**
     * @param requestDateFrom The requestDateFrom to set.
     */
    public void setRequestDateFrom(String requestDateFrom) {
        this.requestDateFrom = requestDateFrom;
    }

    /**
     * @return Returns the requestDateTo.
     */
    public String getRequestDateTo() {
        return requestDateTo;
    }

    /**
     * @param requestDateTo The requestDateTo to set.
     */
    public void setRequestDateTo(String requestDateTo) {
        this.requestDateTo = requestDateTo;
    }

    /**
     * @return Returns the requestor.
     */
    public String getRequestor() {
        return requestor;
    }

    /**
     * @param requestor The requestor to set.
     */
    public void setRequestor(String requestor) {
        this.requestor = requestor;
    }

    
    
    /**
     * @return Returns the createDateFrom.
     */
    public String getCreateDateFrom() {
        return createDateFrom;
    }

    /**
     * @param createDateFrom The createDateFrom to set.
     */
    public void setCreateDateFrom(String createDateFrom) {
        this.createDateFrom = createDateFrom;
    }

    
    /**
     * @return Returns the createDateTo.
     */
    public String getCreateDateTo() {
        return createDateTo;
    }

    /**
     * @param createDateTo The createDateTo to set.
     */
    public void setCreateDateTo(String createDateTo) {
        this.createDateTo = createDateTo;
    }

    public ExpenseQueryForm()
    {
        this.setOrder(ExpenseQueryOrder.ID.getName());
        this.setDescend(true);
    }
    
}
