/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.web.struts.form.admin;

import com.aof.web.struts.form.BaseSessionQueryForm;

/**
 * ²éÑ¯HotelµÄForm
 * 
 * @author nicebean
 * @version 1.0 (2005-11-15)
 */
public class HotelQueryForm extends BaseSessionQueryForm {

    /* id */
    private String id;

    private String site_id;

    private String currency_code;

    private String country_id;

    private String province_id;

    private String city_id;

    private String name;

    private String address;

    private String description;

    private String telephone;

    private String fax;

    private String level;

    private String contractStartDate;

    private String contractExpireDate;
    
    private String promoteStatus;
    
    private String enabled;

    

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    /**
     * @return Returns the address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address
     *            The address to set.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return Returns the city_id.
     */
    public String getCity_id() {
        return city_id;
    }

    /**
     * @param city_id
     *            The city_id to set.
     */
    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    /**
     * @return Returns the contractExpireDate.
     */
    public String getContractExpireDate() {
        return contractExpireDate;
    }

    /**
     * @param contractExpireDate
     *            The contractExpireDate to set.
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
     * @param contractStartDate
     *            The contractStartDate to set.
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
     * @param country_id
     *            The country_id to set.
     */
    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    /**
     * @return Returns the currency_code.
     */
    public String getCurrency_code() {
        return currency_code;
    }

    /**
     * @param currency_code
     *            The currency_code to set.
     */
    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
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
     * @return Returns the fax.
     */
    public String getFax() {
        return fax;
    }

    /**
     * @param fax
     *            The fax to set.
     */
    public void setFax(String fax) {
        this.fax = fax;
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
        this.id = id;
    }


    /**
     * @return Returns the level.
     */
    public String getLevel() {
        return level;
    }

    /**
     * @param level
     *            The level to set.
     */
    public void setLevel(String level) {
        this.level = level;
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
     * @return Returns the province_id.
     */
    public String getProvince_id() {
        return province_id;
    }

    /**
     * @param province_id
     *            The province_id to set.
     */
    public void setProvince_id(String province_id) {
        this.province_id = province_id;
    }

    /**
     * @return Returns the site_id.
     */
    public String getSite_id() {
        return site_id;
    }

    /**
     * @param site_id
     *            The site_id to set.
     */
    public void setSite_id(String site_id) {
        this.site_id = site_id;
    }

    /**
     * @return Returns the telephone.
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * @param telephone
     *            The telephone to set.
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPromoteStatus() {
        return promoteStatus;
    }

    public void setPromoteStatus(String promoteStatus) {
        this.promoteStatus = promoteStatus;
    }

}
