/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.web.struts.form.admin;

import com.aof.web.struts.form.BaseSessionQueryForm;


/**
 * ²éÑ¯TravleGroupDetailµÄForm
 * 
 * @author nicebean
 * @version 1.0 (2005-11-15)
 */
public class TravelGroupDetailQueryForm extends BaseSessionQueryForm {

    /* id */

    /* keyPropertyList */

    /* kmtoIdList */
    private String expenseSubCategory_id;

    private String travelGroup_id;

    /**
     * @return Returns the expenseSubCategory_id.
     */
    public String getExpenseSubCategory_id() {
        return expenseSubCategory_id;
    }

    /**
     * @param expenseSubCategory_id
     *            The expenseSubCategory_id to set.
     */
    public void setExpenseSubCategory_id(String expenseSubCategory_id) {
        this.expenseSubCategory_id = expenseSubCategory_id;
    }

    /**
     * @return Returns the travelGroup_id.
     */
    public String getTravelGroup_id() {
        return travelGroup_id;
    }

    /**
     * @param travelGroup_id
     *            The travelGroup_id to set.
     */
    public void setTravelGroup_id(String travelGroup_id) {
        this.travelGroup_id = travelGroup_id;
    }

    /* mtoIdList */

    /* property */
    private String amountLimit;

    /**
     * @return Returns the amountLimit.
     */
    public String getAmountLimit() {
        return amountLimit;
    }

    /**
     * @param amountLimit
     *            The amountLimit to set.
     */
    public void setAmountLimit(String amountLimit) {
        this.amountLimit = amountLimit;
    }

}
