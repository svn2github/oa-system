/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

/*
 * Created Fri Sep 23 14:47:57 CST 2005 by MyEclipse Hibernate Tool.
 */
package com.aof.model.business.rule;

import java.io.Serializable;

import com.aof.model.admin.User;
import com.aof.model.metadata.YesNo;

/**
 * 该类代表rule_consequence表的一行记录
 */
public class RuleConsequence extends AbstractRuleConsequence implements Serializable {
    /**
     * Simple constructor of RuleConsequence instances.
     */
    public RuleConsequence() {
        this.setCanModify(YesNo.NO);
    }

    /**
     * Constructor of RuleConsequence instances given a simple primary key.
     * 
     * @param id
     */
    public RuleConsequence(Rule rule, User approver) {
        super(rule, approver);
        this.setCanModify(YesNo.NO);
    }

    /* Add customized code below */

}
