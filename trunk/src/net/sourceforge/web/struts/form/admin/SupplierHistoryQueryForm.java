/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.web.struts.form.admin;

import net.sourceforge.web.struts.form.BaseSessionQueryForm;


/**
 * ²éÑ¯SupplierHistoryµÄForm
 * 
 * @author ych
 * @version 1.0 (Nov 14, 2005)
 */
public class SupplierHistoryQueryForm extends BaseSessionQueryForm {

    private String id;

    private String site_id;

    private String currency_code;

    private String country_id;

    private String city_id;

    private String name;

    private String address1;

    private String address3;

    private String address2;

    private String post;

    private String attention1;

    private String attention2;

    private String telephone1;

    private String telephone2;

    private String ext1;

    private String ext2;

    private String fax1;

    private String fax2;

    private String purchaseAccount;

    private String purchaseSubAccount;

    private String purchaseCostCenter;

    private String apAccount;

    private String apSubaccount;

    private String apCostCenter;

    private String bank;

    private String creditTerms;

    private String contact;

    private String contractStartDate;

    private String contractExpireDate;

    private String enabled;

    private String airTicket;

    private String promoteStatus;

    /**
     * @return Returns the address1.
     */
    public String getAddress1() {
        return address1;
    }

    /**
     * @param address1 The address1 to set.
     */
    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    /**
     * @return Returns the address2.
     */
    public String getAddress2() {
        return address2;
    }

    /**
     * @param address2 The address2 to set.
     */
    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    /**
     * @return Returns the address3.
     */
    public String getAddress3() {
        return address3;
    }

    /**
     * @param address3 The address3 to set.
     */
    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    /**
     * @return Returns the airTicket.
     */
    public String getAirTicket() {
        return airTicket;
    }

    /**
     * @param airTicket The airTicket to set.
     */
    public void setAirTicket(String airTicket) {
        this.airTicket = airTicket;
    }

    /**
     * @return Returns the apAccount.
     */
    public String getApAccount() {
        return apAccount;
    }

    /**
     * @param apAccount The apAccount to set.
     */
    public void setApAccount(String apAccount) {
        this.apAccount = apAccount;
    }

    /**
     * @return Returns the apCostCenter.
     */
    public String getApCostCenter() {
        return apCostCenter;
    }

    /**
     * @param apCostCenter The apCostCenter to set.
     */
    public void setApCostCenter(String apCostCenter) {
        this.apCostCenter = apCostCenter;
    }

    /**
     * @return Returns the apSubaccount.
     */
    public String getApSubaccount() {
        return apSubaccount;
    }

    /**
     * @param apSubaccount The apSubaccount to set.
     */
    public void setApSubaccount(String apSubaccount) {
        this.apSubaccount = apSubaccount;
    }

    /**
     * @return Returns the attention1.
     */
    public String getAttention1() {
        return attention1;
    }

    /**
     * @param attention1 The attention1 to set.
     */
    public void setAttention1(String attention1) {
        this.attention1 = attention1;
    }

    /**
     * @return Returns the attention2.
     */
    public String getAttention2() {
        return attention2;
    }

    /**
     * @param attention2 The attention2 to set.
     */
    public void setAttention2(String attention2) {
        this.attention2 = attention2;
    }

    /**
     * @return Returns the bank.
     */
    public String getBank() {
        return bank;
    }

    /**
     * @param bank The bank to set.
     */
    public void setBank(String bank) {
        this.bank = bank;
    }

    /**
     * @return Returns the city_id.
     */
    public String getCity_id() {
        return city_id;
    }

    /**
     * @param city_id The city_id to set.
     */
    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    /**
     * @return Returns the contact.
     */
    public String getContact() {
        return contact;
    }

    /**
     * @param contact The contact to set.
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * @return Returns the contractExpireDate.
     */
    public String getContractExpireDate() {
        return contractExpireDate;
    }

    /**
     * @param contractExpireDate The contractExpireDate to set.
     */
    public void setContractExpireDate(String contractExpireDate) {
        this.contractExpireDate = contractExpireDate;
    }

    /**
     * @return Returns the contractStartDate.
     */
    public String getContractStartDate() {
        return contractStartDate;
    }

    /**
     * @param contractStartDate The contractStartDate to set.
     */
    public void setContractStartDate(String contractStartDate) {
        this.contractStartDate = contractStartDate;
    }

    /**
     * @return Returns the country_id.
     */
    public String getCountry_id() {
        return country_id;
    }

    /**
     * @param country_id The country_id to set.
     */
    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    /**
     * @return Returns the creditTerms.
     */
    public String getCreditTerms() {
        return creditTerms;
    }

    /**
     * @param creditTerms The creditTerms to set.
     */
    public void setCreditTerms(String creditTerms) {
        this.creditTerms = creditTerms;
    }

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
     * @return Returns the ext1.
     */
    public String getExt1() {
        return ext1;
    }

    /**
     * @param ext1 The ext1 to set.
     */
    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    /**
     * @return Returns the ext2.
     */
    public String getExt2() {
        return ext2;
    }

    /**
     * @param ext2 The ext2 to set.
     */
    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    /**
     * @return Returns the fax1.
     */
    public String getFax1() {
        return fax1;
    }

    /**
     * @param fax1 The fax1 to set.
     */
    public void setFax1(String fax1) {
        this.fax1 = fax1;
    }

    /**
     * @return Returns the fax2.
     */
    public String getFax2() {
        return fax2;
    }

    /**
     * @param fax2 The fax2 to set.
     */
    public void setFax2(String fax2) {
        this.fax2 = fax2;
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
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Returns the post.
     */
    public String getPost() {
        return post;
    }

    /**
     * @param post The post to set.
     */
    public void setPost(String post) {
        this.post = post;
    }

    /**
     * @return Returns the promoteStatus.
     */
    public String getPromoteStatus() {
        return promoteStatus;
    }

    /**
     * @param promoteStatus The promoteStatus to set.
     */
    public void setPromoteStatus(String promoteStatus) {
        this.promoteStatus = promoteStatus;
    }

    /**
     * @return Returns the purchaseAccount.
     */
    public String getPurchaseAccount() {
        return purchaseAccount;
    }

    /**
     * @param purchaseAccount The purchaseAccount to set.
     */
    public void setPurchaseAccount(String purchaseAccount) {
        this.purchaseAccount = purchaseAccount;
    }

    /**
     * @return Returns the purchaseCostCenter.
     */
    public String getPurchaseCostCenter() {
        return purchaseCostCenter;
    }

    /**
     * @param purchaseCostCenter The purchaseCostCenter to set.
     */
    public void setPurchaseCostCenter(String purchaseCostCenter) {
        this.purchaseCostCenter = purchaseCostCenter;
    }

    /**
     * @return Returns the purchaseSubAccount.
     */
    public String getPurchaseSubAccount() {
        return purchaseSubAccount;
    }

    /**
     * @param purchaseSubAccount The purchaseSubAccount to set.
     */
    public void setPurchaseSubAccount(String purchaseSubAccount) {
        this.purchaseSubAccount = purchaseSubAccount;
    }

    /**
     * @return Returns the site_id.
     */
    public String getSite_id() {
        return site_id;
    }

    /**
     * @param site_id The site_id to set.
     */
    public void setSite_id(String site_id) {
        this.site_id = site_id;
    }

    /**
     * @return Returns the telephone1.
     */
    public String getTelephone1() {
        return telephone1;
    }

    /**
     * @param telephone1 The telephone1 to set.
     */
    public void setTelephone1(String telephone1) {
        this.telephone1 = telephone1;
    }

    /**
     * @return Returns the telephone2.
     */
    public String getTelephone2() {
        return telephone2;
    }

    /**
     * @param telephone2 The telephone2 to set.
     */
    public void setTelephone2(String telephone2) {
        this.telephone2 = telephone2;
    }

    
}
