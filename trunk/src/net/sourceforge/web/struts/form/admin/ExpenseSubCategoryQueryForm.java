/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.web.struts.form.admin;

import net.sourceforge.web.struts.form.BaseSessionQueryForm;

/**
 * ²éÑ¯ExpenseSubCategoruµÄForm
 * 
 * @author nicebean
 * @version 1.0 (2005-11-15)
 */
public class ExpenseSubCategoryQueryForm extends BaseSessionQueryForm {

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
    private String expenseCategory_id;

    /**
     * @return Returns the expenseCategory_id.
     */
    public String getExpenseCategory_id() {
        return expenseCategory_id;
    }

    /**
     * @param expenseCategory_id
     *            The expenseCategory_id to set.
     */
    public void setExpenseCategory_id(String expenseCategory_id) {
        this.expenseCategory_id = expenseCategory_id;
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

}
