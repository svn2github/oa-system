/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.web.struts.form.business.pr;

import com.aof.web.struts.form.BaseSessionQueryForm;

/**
 * query form for searching purchaseRequest
 * @author shilei
 * @version 1.0 (Dec 7, 2005)
 */
public class PurchaseRequestQueryForm extends BaseSessionQueryForm {

	/*id*/
	private String id;
	public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

	/*keyPropertyList*/

	/*kmtoIdList*/


	/*mtoIdList*/
	private String purchaseSubCategory_id;
	public String getPurchaseSubCategory_id() {
        return purchaseSubCategory_id;
    }
    public void setPurchaseSubCategory_id(String purchaseSubCategory_id) {
        this.purchaseSubCategory_id = purchaseSubCategory_id;
    }
	private String department_id;
	public String getDepartment_id() {
        return department_id;
    }
    public void setDepartment_id(String department_id) {
        this.department_id = department_id;
    }
	private String capex_id;
	public String getCapex_id() {
        return capex_id;
    }
    public void setCapex_id(String capex_id) {
        this.capex_id = capex_id;
    }
	private String yearlyBudget_id;
	public String getYearlyBudget_id() {
        return yearlyBudget_id;
    }
    public void setYearlyBudget_id(String yearlyBudget_id) {
        this.yearlyBudget_id = yearlyBudget_id;
    }
	private String currency_code;
	public String getCurrency_code() {
        return currency_code;
    }
    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }
	private String requestor_id;
	public String getRequestor_id() {
        return requestor_id;
    }
    public void setRequestor_id(String requestor_id) {
        this.requestor_id = requestor_id;
    }
	private String creator_id;
	public String getCreator_id() {
        return creator_id;
    }
    public void setCreator_id(String creator_id) {
        this.creator_id = creator_id;
    }
	private String purchaser_id;
	public String getPurchaser_id() {
        return purchaser_id;
    }
    public void setPurchaser_id(String purchaser_id) {
        this.purchaser_id = purchaser_id;
    }
      

	/*property*/
	private String title;
	public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
	private String description;
	public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
	private String status;
	public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
	private String requestDate;
	public String getRequestDate() {
        return requestDate;
    }
    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }
	private String createDate;
	public String getCreateDate() {
        return createDate;
    }
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
	private String approveRequestId;
	public String getApproveRequestId() {
        return approveRequestId;
    }
    public void setApproveRequestId(String approveRequestId) {
        this.approveRequestId = approveRequestId;
    }
    
    private String requestor_name;
    public String getRequestor_name() {
        return requestor_name;
    }
    public void setRequestor_name(String requestor_name) {
        this.requestor_name = requestor_name;
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
    
    private String createDate1;
    private String createDate2;
    public String getCreateDate1() {
        return createDate1;
    }
    public void setCreateDate1(String createDate1) {
        this.createDate1 = createDate1;
    }
    public String getCreateDate2() {
        return createDate2;
    }
    public void setCreateDate2(String createDate2) {
        this.createDate2 = createDate2;
    }
    
    private String amount1;
    private String amount2;
    public String getAmount1() {
        return amount1;
    }
    public void setAmount1(String amount1) {
        this.amount1 = amount1;
    }
    public String getAmount2() {
        return amount2;
    }
    public void setAmount2(String amount2) {
        this.amount2 = amount2;
    }
    
    
}
