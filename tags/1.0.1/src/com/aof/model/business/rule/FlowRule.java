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

/**
 * 该类代表rule_flow_rule表的一行记录
 */
public class FlowRule extends AbstractFlowRule implements Serializable {
    /**
     * Simple constructor of FlowRule instances.
     */
    public FlowRule() {
    }

    /**
     * Constructor of FlowRule instances given a primary key.
     * 
     * @param flow
     * @param seq
     */
    public FlowRule(Flow flow, int seq) {
        super(flow, seq);
    }

    /* Add customized code below */

}
