/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.web.struts.form.admin;

import com.aof.web.struts.form.BaseSessionQueryForm;

/**
 * ²éÑ¯CityµÄForm
 * 
 * @author nicebean
 * @version 1.0 (2005-11-15)
 */
public class CityQueryForm extends BaseSessionQueryForm {

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
    private String province_id;

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

    /* property */
    private String engName;

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

    private String chnName;

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

    private String enabled;

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
}
