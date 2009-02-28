/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.web.struts.form.admin;


import com.aof.model.admin.query.ExpenseCategoryQueryOrder;
import com.aof.model.metadata.EnabledDisabled;
import com.aof.web.struts.form.BaseSessionQueryForm;

/**
 * ²éÑ¯ExpenseCategoryµÄForm
 * 
 * @author nicebean
 * @version 1.0 (2005-11-15)
 */
public class ExpenseCategoryQueryForm extends BaseSessionQueryForm {

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
    private String description;

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

    private String type;

    /**
     * @return Returns the type.
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     *            The type to set.
     */
    public void setType(String type) {
        this.type = type;
    }

    private String referenceErpId;

    /**
     * @return Returns the referenceErpId.
     */
    public String getReferenceErpId() {
        return referenceErpId;
    }

    /**
     * @param referenceErpId
     *            The referenceErpId to set.
     */
    public void setReferenceErpId(String referenceErpId) {
        this.referenceErpId = referenceErpId;
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


    public ExpenseCategoryQueryForm()
    {
        this.setEnabled(EnabledDisabled.ENABLED.getEnumCode().toString());
        this.setOrder(ExpenseCategoryQueryOrder.DESCRIPTION.getName());
        this.setDescend(false);
    }
}
