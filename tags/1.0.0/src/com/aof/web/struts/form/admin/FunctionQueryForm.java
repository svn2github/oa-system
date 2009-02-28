/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.web.struts.form.admin;

import com.aof.model.admin.query.FunctionQueryOrder;
import com.aof.web.struts.form.BaseSessionQueryForm;

/**
 * ²éÑ¯FunctionµÄForm
 * 
 * @author nicebean
 * @version 1.0 (2005-11-15)
 */
public class FunctionQueryForm extends BaseSessionQueryForm {
    private String name;

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

    public FunctionQueryForm()
    {
        this.setOrder(FunctionQueryOrder.NAME.getName());
        this.setDescend(false);
    }
}
