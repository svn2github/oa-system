/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.web.struts.form.admin;

import net.sourceforge.model.admin.query.RoleQueryOrder;
import net.sourceforge.web.struts.form.BaseSessionQueryForm;
import com.shcnc.struts.form.ComboBox;

/**
 * ²éÑ¯RoleµÄForm
 * 
 * @author nicebean
 * @version 1.0 (2005-11-15)
 */
public class RoleQueryForm extends BaseSessionQueryForm {
    private String id;

    private String name;

    private ComboBox type;

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

    /**
     * @return Returns the type.
     */
    public ComboBox getType() {
        return type;
    }

    /**
     * @param type
     *            The type to set.
     */
    public void setType(ComboBox type) {
        this.type = type;
    }


    public RoleQueryForm()
    {
        setType(new ComboBox("enumCode", "enumCode"));
        setOrder(RoleQueryOrder.NAME.getName());
        setDescend(false);
    }

}
