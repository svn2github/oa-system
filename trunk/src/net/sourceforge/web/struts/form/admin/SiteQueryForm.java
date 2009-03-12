/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.web.struts.form.admin;


import net.sourceforge.model.admin.query.SiteQueryOrder;
import net.sourceforge.model.metadata.EnabledDisabled;
import net.sourceforge.web.struts.form.BaseSessionQueryForm;



/**
 * ²éÑ¯SiteµÄForm
 * 
 * @author nicebean
 * @version 1.0 (2005-11-15)
 */
public class SiteQueryForm extends BaseSessionQueryForm {
    private String name;

    private String enabled;

    

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


    
    public SiteQueryForm()
    {
        setOrder(SiteQueryOrder.NAME.getName());
        setDescend(false);
        this.setEnabled(EnabledDisabled.ENABLED.getEnumCode().toString());
    }

}
