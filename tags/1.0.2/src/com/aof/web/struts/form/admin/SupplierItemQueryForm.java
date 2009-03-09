/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.web.struts.form.admin;


import com.aof.model.admin.query.SupplierItemQueryOrder;
import com.aof.web.struts.form.BaseSessionQueryForm;


/**
 * ²éÑ¯SupplierItemµÄForm
 * 
 * @author ych
 * @version 1.0 (Nov 14, 2005)
 */
public class SupplierItemQueryForm extends BaseSessionQueryForm {

    private String id;

    private String currency_code;

    private String purchaseCategory_id;
    
    private String purchaseSubCategory_id;

    private String supplier_id;

    private String sepc;

    private String unitPrice;

    private String enabled;

    private String erpNo;
    
    private String backPage="edit";
    
    private String fromPO="false";

    /**
     * @return Returns the currency_code.
     */
    public String getCurrency_code() {
        return currency_code;
    }

    /**
     * @param currency_code The currency_code to set.
     */
    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }

    /**
     * @return Returns the enabled.
     */
    public String getEnabled() {
        return enabled;
    }

    /**
     * @param enabled The enabled to set.
     */
    public void setEnabled(String enabled) {
        this.enabled = enabled;
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
     * @return Returns the purchaseSubCategory_id.
     */
    public String getPurchaseSubCategory_id() {
        return purchaseSubCategory_id;
    }

    /**
     * @param purchaseSubCategory_id The purchaseSubCategory_id to set.
     */
    public void setPurchaseSubCategory_id(String purchaseSubCategory_id) {
        this.purchaseSubCategory_id = purchaseSubCategory_id;
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
     * @return Returns the supplier_id.
     */
    public String getSupplier_id() {
        return supplier_id;
    }

    /**
     * @param supplier_id The supplier_id to set.
     */
    public void setSupplier_id(String supplier_id) {
        this.supplier_id = supplier_id;
    }

    /**
     * @return Returns the unitPrice.
     */
    public String getUnitPrice() {
        return unitPrice;
    }

    /**
     * @param unitPrice The unitPrice to set.
     */
    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * @return Returns the purchaseCategory_id.
     */
    public String getPurchaseCategory_id() {
        return purchaseCategory_id;
    }

    /**
     * @param purchaseCategory_id The purchaseCategory_id to set.
     */
    public void setPurchaseCategory_id(String purchaseCategory_id) {
        this.purchaseCategory_id = purchaseCategory_id;
    }

    
    /**
     * @return Returns the backPage.
     */
    public String getBackPage() {
        return backPage;
    }

    /**
     * @param back The backPage to set.
     */
    public void setBackPage(String backPage) {
        this.backPage = backPage;
    }
    
    

    /**
     * @return Returns the fromPO.
     */
    public String getFromPO() {
        return fromPO;
    }

    /**
     * @param fromPO The fromPO to set.
     */
    public void setFromPO(String fromPO) {
        this.fromPO = fromPO;
    }

    
    public SupplierItemQueryForm()
    {
        this.setOrder(SupplierItemQueryOrder.CATEGORY.getName());
        this.setDescend(false);
    }
    
}
