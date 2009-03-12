/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.web.struts.form.admin;

import net.sourceforge.web.struts.form.BaseSessionQueryForm;

/**
 * ²éÑ¯ProvinceµÄForm
 * 
 * @author nicebean
 * @version 1.0 (2005-11-15)
 */
public class ProvinceQueryForm extends BaseSessionQueryForm {

    /* id */
    private String id;

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

    /* keyPropertyList */

    /* kmtoIdList */

    /* mtoIdList */
    private String country_id;

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

    /* property */
    private String engName;

    private String chnName;

    private String enabled;

    /**
     * @return Returns the chnName.
     */
    public String getChnName() {
        return chnName;
    }

    /**
     * @param chnName
     *            The chnName to set.
     */
    public void setChnName(String chnName) {
        this.chnName = chnName;
    }

    /**
     * @return Returns the enabled.
     */
    public String getEnabled() {
        return enabled;
    }

    /**
     * @param enabled
     *            The enabled to set.
     */
    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    /**
     * @return Returns the engName.
     */
    public String getEngName() {
        return engName;
    }

    /**
     * @param engName
     *            The engName to set.
     */
    public void setEngName(String engName) {
        this.engName = engName;
    }
}
