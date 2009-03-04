package com.aof.web.struts.form.business.rule;

import com.aof.model.business.rule.query.FlowQueryOrder;
import com.aof.model.metadata.EnabledDisabled;
import com.aof.web.struts.form.BaseSessionQueryForm;

import com.shcnc.struts.form.ComboBox;

/**
 * ²éÑ¯FlowµÄForm
 * @author nicebean
 * @version 1.0 (2005-11-15)
 */
public class FlowQueryForm extends BaseSessionQueryForm {
    private ComboBox site;
    private String id;
    private String description;
    private String enabled;

    /**
     * @return Returns the description.
     */
    public String getDescription() {
        return description;
    }
    /**
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
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
     * @return Returns the id.
     */
    public String getId() {
        return id;
    }
    /**
     * @param id The id to set.
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * @return Returns the site.
     */
    public ComboBox getSite() {
        return site;
    }
    /**
     * @param site The site to set.
     */
    public void setSite(ComboBox site) {
        this.site = site;
    }

    public FlowQueryForm()
    {
        setSite(new ComboBox("id", "name"));
        setOrder(FlowQueryOrder.DESCRIPTION.getName());
        setDescend(false);
        this.setEnabled(EnabledDisabled.ENABLED.getEnumCode().toString());
    }
    
}
