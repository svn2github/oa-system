/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.web.struts.form.business.pr;

import com.aof.web.struts.form.BaseSessionQueryForm;


/**
 * @author shilei
 * @version 1.0 (Nov 29, 2005)
 */
public class YearlyBudgetQueryForm extends BaseSessionQueryForm {

    private String amountFrom;
    private String amountTo;
    private String department_id;
    
    
	private String id;
	public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

	private String site_id;
	public String getSite_id() {
        return site_id;
    }
    public void setSite_id(String site_id) {
        this.site_id = site_id;
    }
    
	private String purchaseCategory_id;
	public String getPurchaseCategory_id() {
        return purchaseCategory_id;
    }
    public void setPurchaseCategory_id(String purchaseCategory_id) {
        this.purchaseCategory_id = purchaseCategory_id;
    }
	private String purchaseSubCategory_id;
	public String getPurchaseSubCategory_id() {
        return purchaseSubCategory_id;
    }
    public void setPurchaseSubCategory_id(String purchaseSubCategory_id) {
        this.purchaseSubCategory_id = purchaseSubCategory_id;
    }
	private String creator_id;
	public String getCreator_id() {
        return creator_id;
    }
    public void setCreator_id(String creator_id) {
        this.creator_id = creator_id;
    }
	private String modifier_id;
	public String getModifier_id() {
        return modifier_id;
    }
    public void setModifier_id(String modifier_id) {
        this.modifier_id = modifier_id;
    }
      

	private String code;
	public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
	private String name;
	public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
	private String type;
	public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
	private String year;
	public String getYear() {
        return year;
    }
    public void setYear(String year) {
        this.year = year;
    }
	private String amount;
	public String getAmount() {
        return amount;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }
	private String actualAmount;
	public String getActualAmount() {
        return actualAmount;
    }
    public void setActualAmount(String actualAmount) {
        this.actualAmount = actualAmount;
    }
	private String status;
	public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
	private String isFreeze;
	public String getIsFreeze() {
        return isFreeze;
    }
    public void setIsFreeze(String isFreeze) {
        this.isFreeze = isFreeze;
    }
	private String createDate;
	public String getCreateDate() {
        return createDate;
    }
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
	private String modifyDate;
	public String getModifyDate() {
        return modifyDate;
    }
    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }
    public String getAmountFrom() {
        return amountFrom;
    }
    public void setAmountFrom(String amountForm) {
        this.amountFrom = amountForm;
    }
    public String getAmountTo() {
        return amountTo;
    }
    public void setAmountTo(String amountTo) {
        this.amountTo = amountTo;
    }
    public String getDepartment_id() {
        return department_id;
    }
    public void setDepartment_id(String department_id) {
        this.department_id = department_id;
    }
}
