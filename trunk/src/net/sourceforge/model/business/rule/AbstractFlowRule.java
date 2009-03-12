/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.business.rule;

import java.io.Serializable;


/**
 * 该类代表rule_flow_rule表的一行记录
 */

public abstract class AbstractFlowRule implements Serializable {
    /**
     * The cached hash code value for this instance. Settting to 0 triggers re-calculation.
     */
    private int hashValue = 0;

    /** identifier field */
    private Flow flow;

    /** identifier field */
    private int seq;

    /** persistent field */
    private Rule rule;

    /** persistent field */
    private int nextSeqWhenPass;

    /** persistent field */
    private int nextSeqWhenFail;

    /** 
     * 指定主键的构造函数
     * @param id
     */
    public AbstractFlowRule(Flow flow, int seq) {
        this.setFlow(flow);
        this.setSeq(seq);
    }

    /** 默认构造函数 */
    public AbstractFlowRule() {
    }

    /**
     * @return Returns the flow.
     */
    public Flow getFlow() {
        return flow;
    }

    /**
     * @param flow The flow to set.
     */
    public void setFlow(Flow flow) {
        this.hashValue = 0;
        this.flow = flow;
    }

    /**
     * @return Returns the nextSeqWhenFail.
     */
    public int getNextSeqWhenFail() {
        return nextSeqWhenFail;
    }

    /**
     * @param nextSeqWhenFail The nextSeqWhenFail to set.
     */
    public void setNextSeqWhenFail(int nextSeqWhenFail) {
        this.nextSeqWhenFail = nextSeqWhenFail;
    }

    /**
     * @return Returns the nextSeqWhenPass.
     */
    public int getNextSeqWhenPass() {
        return nextSeqWhenPass;
    }

    /**
     * @param nextSeqWhenPass The nextSeqWhenPass to set.
     */
    public void setNextSeqWhenPass(int nextSeqWhenPass) {
        this.nextSeqWhenPass = nextSeqWhenPass;
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
        this.rule = rule;
    }

    /**
     * @return Returns the seq.
     */
    public int getSeq() {
        this.hashValue = 0;
        return seq;
    }

    /**
     * @param seq The seq to set.
     */
    public void setSeq(int seq) {
        this.seq = seq;
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
        if (!(rhs instanceof FlowRule)) return false;
        FlowRule that = (FlowRule) rhs;
        if (this.getFlow() != null) {
            if (!this.getFlow().equals(that.getFlow())) return false;
        }
        return this.getSeq() == that.getSeq();
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
            int itemFlowValue = this.getFlow() == null ? 0 : this.getFlow().hashCode();
            result = result * 37 + itemFlowValue;
            result = result * 37 + this.getSeq();
            this.hashValue = result;
        }
        return this.hashValue;
    }

}
