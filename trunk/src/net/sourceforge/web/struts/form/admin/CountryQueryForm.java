/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.web.struts.form.admin;

import net.sourceforge.web.struts.form.BaseSessionQueryForm;

/**
 * ²éÑ¯CountryµÄForm
 * 
 * @author nicebean
 * @version 1.0 (2005-11-15)
 */
public class CountryQueryForm extends BaseSessionQueryForm {

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

    /* property */
    private String shortName;

    /**
     * @return Returns the shortName.
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * @param shortName
     *            The shortName to set.
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

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
