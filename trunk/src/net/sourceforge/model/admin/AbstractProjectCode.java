package net.sourceforge.model.admin;

import java.io.Serializable;

import net.sourceforge.model.metadata.EnabledDisabled;

public abstract class AbstractProjectCode implements Serializable {

    /** The cached hash code value for this instance.  Settting to 0 triggers re-calculation. */
    private int hashValue = 0;

    /** The composite primary key value. */
    private java.lang.Integer id;
    
    /** The value of the simple project code property. */
    private java.lang.String code;


    /** The value of the simple description property. */
    private java.lang.String description;
    
    /** The value of the simple enabled property. */
    private EnabledDisabled enabled;
    
    /** The value of the simple enabled property. */
    private Site site;


    public AbstractProjectCode() {
        
    }
    
    public AbstractProjectCode(Integer id) {
        this.id = id;
    }


    public java.lang.Integer getId() {
        return id;
    }


    public void setId(java.lang.Integer id) {
        this.id = id;
    }


    public java.lang.String getCode() {
        return code;
    }


    public void setCode(java.lang.String code) {
        this.code = code;
    }


    public java.lang.String getDescription() {
        return description;
    }


    public void setDescription(java.lang.String description) {
        this.description = description;
    }
    
    /**
     * Implementation of the equals comparison on the basis of equality of the primary key values.
     * @param rhs
     * @return boolean
     */
    public boolean equals(Object rhs)
    {
        if (rhs == null) return false;
        if (this == rhs) return true;
        if (!(rhs instanceof ProjectCode)) return false;
        ProjectCode that = (ProjectCode) rhs;
        if (this.getId() != null) return this.getId().equals(that.getId());
        return that.getId() == null;
    }

    /**
     * Implementation of the hashCode method conforming to the Bloch pattern with
     * the exception of array properties (these are very unlikely primary key types).
     * @return int
     */
    public int hashCode()
    {
        if (this.hashValue == 0)
        {
            int result = 17;
            int projectCodeIdValue = this.getId() == null ? 0 : this.getId().hashCode();
            result = result * 37 + projectCodeIdValue;
            this.hashValue = result;
        }
        return this.hashValue;
    }

    public EnabledDisabled getEnabled() {
        return enabled;
    }

    public void setEnabled(EnabledDisabled enabled) {
        this.enabled = enabled;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }
}
