/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.web.struts.form.admin;

import net.sourceforge.web.struts.form.BaseSessionQueryForm;

/**
 * ²éÑ¯TravelGroupµÄForm
 * 
 * @author nicebean
 * @version 1.0 (2005-11-15)
 */
public class TravelGroupQueryForm extends BaseSessionQueryForm {

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
    private String site_id;

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

    /* property */
    private String name;

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
}
