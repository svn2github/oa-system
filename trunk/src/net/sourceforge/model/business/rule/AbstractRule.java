package net.sourceforge.model.business.rule;

import java.io.Serializable;
import java.util.Set;

import net.sourceforge.model.admin.Site;
import net.sourceforge.model.metadata.EnabledDisabled;
import net.sourceforge.model.metadata.RuleType;


/**
 * 该类代表rule表的一行记录
 */

public abstract class AbstractRule implements Serializable {
    /**
     * The cached hash code value for this instance. Settting to 0 triggers re-calculation.
     */
    private int hashValue = 0;

    /** identifier field */
    private Integer id;

    /** persistent field */
    private RuleType type;

    /** persistent field */
    private String description;

    /** persistent field */
    private EnabledDisabled enabled;
    
    private Integer top;
    
    private Integer left;

    /** persistent field */
    private Site site;

    /** persistent field */
    private Set conditions;

    /** persistent field */
    private Set consequences;

    /** 
     * 指定主键的构造函数
     * @param id
     */
    public AbstractRule(Integer id) {
        this.setId(id);
    }

    /** 默认构造函数 */
    public AbstractRule() {
    }

    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

    public Integer getLeft() {
        return left;
    }

    public void setLeft(Integer left) {
        this.left = left;
    }

    /**
     * @return Returns the conditions.
     */
    public Set getConditions() {
        return conditions;
    }

    /**
     * @param conditions The conditions to set.
     */
    public void setConditions(Set conditions) {
        this.conditions = conditions;
    }

    /**
     * @return Returns the consequences.
     */
    public Set getConsequences() {
        return consequences;
    }

    /**
     * @param consequences The consequences to set.
     */
    public void setConsequences(Set consequences) {
        this.consequences = consequences;
    }

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
    public EnabledDisabled getEnabled() {
        return enabled;
    }

    /**
     * @param enabled The enabled to set.
     */
    public void setEnabled(EnabledDisabled enabled) {
        this.enabled = enabled;
    }

    /**
     * @return Returns the id.
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id to set.
     */
    public void setId(Integer id) {
        this.hashValue = 0;
        this.id = id;
    }

    /**
     * @return Returns the site.
     */
    public Site getSite() {
        return site;
    }

    /**
     * @param site The site to set.
     */
    public void setSite(Site site) {
        this.site = site;
    }

    /**
     * @return Returns the type.
     */
    public RuleType getType() {
        return type;
    }

    /**
     * @param type The type to set.
     */
    public void setType(RuleType type) {
        this.type = type;
    }

    /**
     * Implementation of the equals comparison on the basis of equality of the
     * primary key values.
     * 
     * @param rhs
     * @return boolean
     */
    public boolean equals(Object rhs) {
        if (rhs == null) return false;
        if (this == rhs) return true;
        if (!(rhs instanceof Rule)) return false;
        Rule that = (Rule) rhs;
        if (this.getId() != null) return this.getId().equals(that.getId());
        return that.getId() == null;
    }

    /**
     * Implementation of the hashCode method conforming to the Bloch pattern
     * with the exception of array properties (these are very unlikely primary
     * key types).
     * 
     * @return int
     */
    public int hashCode() {
        if (this.hashValue == 0) {
            int result = 17;
            int itemIdValue = this.getId() == null ? 0 : this.getId().hashCode();
            result = result * 37 + itemIdValue;
            this.hashValue = result;
        }
        return this.hashValue;
    }


}
