/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.business.rule;

import com.aof.model.metadata.ConditionCompareType;
import com.aof.model.metadata.ConditionType;
import java.io.Serializable;


/**
 * 该类代表rule_condition表的一行记录
 */
public abstract class AbstractRuleCondition implements Serializable {
    /**
     * The cached hash code value for this instance. Settting to 0 triggers re-calculation.
     */
    private int hashValue = 0;

    /** identifier field */
    private Rule rule;

    /** identifier field */
    private ConditionType type;

    /** persistent field */
    private ConditionCompareType compareType;

    /** persistent field */
    private String value;

    /** 
     * 指定主键的构造函数
     * @param id
     */
    public AbstractRuleCondition(Rule rule, ConditionType type) {
        this.setRule(rule);
        this.setType(type);
    }

    /** 默认构造函数 */
    public AbstractRuleCondition() {
    }

    /**
     * @return Returns the compareType.
     */
    public ConditionCompareType getCompareType() {
        return compareType;
    }

    /**
     * @param compareType The compareType to set.
     */
    public void setCompareType(ConditionCompareType compareType) {
        this.compareType = compareType;
    }

    /**
     * @return Returns the rule.
     */
    public Rule getRule() {
        return rule;
    }

    /**
     * @param rule The rule to set.
     */
    public void setRule(Rule rule) {
        this.hashValue = 0;
        this.rule = rule;
    }

    /**
     * @return Returns the type.
     */
    public ConditionType getType() {
        return type;
    }

    /**
     * @param type The type to set.
     */
    public void setType(ConditionType type) {
        this.hashValue = 0;
        this.type = type;
    }

    /**
     * @return Returns the value.
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value The value to set.
     */
    public void setValue(String value) {
        this.value = value;
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
        if (!(rhs instanceof RuleCondition)) return false;
        RuleCondition that = (RuleCondition) rhs;
        if (this.getRule() != null) {
            if (!this.getRule().equals(that.getRule())) return false;
        }
        if (this.getType() != null) {
            if (!this.getType().equals(that.getType())) return false;
        }
        return true;
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
            int itemRuleValue = this.getRule() == null ? 0 : this.getRule().hashCode();
            result = result * 37 + itemRuleValue;
            int itemTypeValue = this.getType() == null ? 0 : this.getType().hashCode();
            result = result * 37 + itemTypeValue;
            this.hashValue = result;
        }
        return this.hashValue;
    }

}
