/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

/*
 * Created Fri Sep 23 14:47:57 CST 2005 by MyEclipse Hibernate Tool.
 */
package net.sourceforge.model.business.rule;

import java.io.Serializable;

import net.sourceforge.model.metadata.ConditionType;

/**
 * 该类代表rule_condition表的一行记录
 */
public class RuleCondition extends AbstractRuleCondition implements Serializable {
    /**
     * Simple constructor of RuleCondition instances.
     */
    public RuleCondition() {
    }

    /**
     * Constructor of RuleCondition instances given a simple primary key.
     * 
     * @param id
     */
    public RuleCondition(Rule rule, ConditionType type) {
        super(rule, type);
    }

    /* Add customized code below */
    private String displayValue;

    /**
     * 获取Rule Condition的显示值
     * @return String
     */
    public String getDisplayValue() {
        return displayValue == null ? getValue() : displayValue;
    }

    /**
     * 设置Rule Condition的显示值
     * @param displayValue
     */
    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }

}
