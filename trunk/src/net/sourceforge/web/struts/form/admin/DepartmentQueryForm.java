/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.web.struts.form.admin;


import net.sourceforge.web.struts.form.BaseSessionQueryForm;
import com.shcnc.struts.form.ComboBox;

/**
 * ²éÑ¯DepartmentµÄForm
 * 
 * @author nicebean
 * @version 1.0 (2005-11-15)
 */
public class DepartmentQueryForm extends BaseSessionQueryForm {
    private ComboBox site=new ComboBox("id", "name");

    /**
     * @return Returns the site.
     */
    public ComboBox getSite() {
        return site;
    }

    /**
     * @param site
     *            The site to set.
     */
    public void setSite(ComboBox site) {
        this.site = site;
    }


}
