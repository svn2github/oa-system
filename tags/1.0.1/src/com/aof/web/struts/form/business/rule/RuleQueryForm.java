package com.aof.web.struts.form.business.rule;

import com.aof.model.business.rule.query.RuleQueryOrder;
import com.aof.model.metadata.EnabledDisabled;
import com.aof.web.struts.form.BaseSessionQueryForm;

import com.shcnc.struts.form.ComboBox;

/**
 * ²éÑ¯RuleµÄForm
 * @author nicebean
 * @version 1.0 (2005-11-15)
 */
public class RuleQueryForm extends BaseSessionQueryForm {
    private ComboBox site;
    private String id;
    private String description;
    private String enabled;
    private String username;
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

    
    public RuleQueryForm()
    {
        setSite(new ComboBox("id", "name"));
        setEnabled(EnabledDisabled.ENABLED.getEnumCode().toString());
        setOrder(RuleQueryOrder.DESCRIPTION.getName());
        setDescend(false);
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    
}
