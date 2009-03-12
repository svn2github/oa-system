package net.sourceforge.web.struts.form.business.po;

import net.sourceforge.web.struts.form.BaseSessionQueryForm;


public class PurchaseOrderQueryForm extends BaseSessionQueryForm {

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
	private String site_id;
	public String getSite_id() {
        return site_id;
    }
    public void setSite_id(String site_id) {
        this.site_id = site_id;
    }
	private String supplier_id;
	public String getSupplier_id() {
        return supplier_id;
    }
    public void setSupplier_id(String supplier_id) {
        this.supplier_id = supplier_id;
    }

    private String category_id;
    public String getCategory_id() {
        return category_id;
    }
    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }
    
	private String subCategory_id;
	public String getSubCategory_id() {
        return subCategory_id;
    }
    public void setSubCategory_id(String subCategory_id) {
        this.subCategory_id = subCategory_id;
    }
	private String currency_code;
	public String getCurrency_code() {
        return currency_code;
    }
    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }
	private String baseCurrency_code;
	public String getBaseCurrency_code() {
        return baseCurrency_code;
    }
    public void setBaseCurrency_code(String baseCurrency_code) {
        this.baseCurrency_code = baseCurrency_code;
    }
	private String createUser_id;
	public String getCreateUser_id() {
        return createUser_id;
    }
    public void setCreateUser_id(String createUser_id) {
        this.createUser_id = createUser_id;
    }
	private String purchaser_id;
	public String getPurchaser_id() {
        return purchaser_id;
    }
    public void setPurchaser_id(String purchaser_id) {
        this.purchaser_id = purchaser_id;
    }
    private String purchaser_name;
    public String getPurchaser_name() {
        return purchaser_name;
    }
    public void setPurchaser_name(String purchaser_name) {
        this.purchaser_name = purchaser_name;
    }    

	/*property*/
	private String erpNo;
	public String getErpNo() {
        return erpNo;
    }
    public void setErpNo(String erpNo) {
        this.erpNo = erpNo;
    }
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
	private String amount;
	public String getAmount() {
        return amount;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }
	private String exchangeRate;
	public String getExchangeRate() {
        return exchangeRate;
    }
    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
	private String status;
	public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
	private String createDate;
	public String getCreateDate() {
        return createDate;
    }
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
	private String exportStatus;
	public String getExportStatus() {
        return exportStatus;
    }
    public void setExportStatus(String exportStatus) {
        this.exportStatus = exportStatus;
    }
	private String approveRequestId;
	public String getApproveRequestId() {
        return approveRequestId;
    }
    public void setApproveRequestId(String approveRequestId) {
        this.approveRequestId = approveRequestId;
    }
    
    private String supplier_name;
    public String getSupplier_name() {
        return supplier_name;
    }
    public void setSupplier_name(String supplier_name) {
        this.supplier_name = supplier_name;
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

    private String confirmDate1;
    private String confirmDate2;
    public String getConfirmDate1() {
        return confirmDate1;
    }
    public void setConfirmDate1(String confirmDate1) {
        this.confirmDate1 = confirmDate1;
    }
    public String getConfirmDate2() {
        return confirmDate2;
    }
    public void setConfirmDate2(String confirmDate2) {
        this.confirmDate2 = confirmDate2;
    }
}
