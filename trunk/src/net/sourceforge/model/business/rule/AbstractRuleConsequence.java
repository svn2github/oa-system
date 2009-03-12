/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.business.rule;

import java.io.Serializable;

import net.sourceforge.model.admin.User;
import net.sourceforge.model.metadata.YesNo;


/**
 * 该类代表rule_consequence表的一行记录
 */

public abstract class AbstractRuleConsequence implements Serializable {
    /**
     * The cached hash code value for this instance. Settting to 0 triggers re-calculation.
     */
    private int hashValue = 0;

    /** persistent field */
    private int seq;

    /** persistent field */
    private YesNo canModify;

    /** identifier field */
    private Rule rule;

    /** identifier field */
    private User user;

    /** nullable persistent field */
    private User superior;

    /** 
     * 指定主键的构造函数
     * @param id
     */
    public AbstractRuleConsequence(Rule rule, User user) {
        this.setRule(rule);
        this.setUser(user);        
    }

    /** 默认构造函数 */
    public AbstractRuleConsequence() {
    }

    /**
     * @return Returns the user.
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user The user to set.
     */
    public void setUser(User user) {
        this.hashValue = 0;
        this.user = user;
    }

    /**
     * @return Returns the canModify.
     */
    public YesNo getCanModify() {
        return canModify;
    }

    /**
     * @param canModify The canModify to set.
     */
    public void setCanModify(YesNo canModify) {
        this.canModify = canModify;
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
     * @return Returns the seq.
     */
    public int getSeq() {
        return seq;
    }

    /**
     * @param seq The seq to set.
     */
    public void setSeq(int seq) {
        this.seq = seq;
    }

    /**
     * @return Returns the superior.
     */
    public User getSuperior() {
        return superior;
    }

    /**
     * @param superior The superior to set.
     */
    public void setSuperior(User superior) {
        this.superior = superior;
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
        if (!(rhs instanceof RuleConsequence)) return false;
        RuleConsequence that = (RuleConsequence) rhs;
        if (this.getRule() != null) {
            if (!this.getRule().equals(that.getRule())) return false;
        }
        if (this.getUser() != null) {
            if (!this.getUser().equals(that.getUser())) return false;
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
            int itemUserValue = this.getUser() == null ? 0 : this.getUser().hashCode();
            result = result * 37 + itemUserValue;
            this.hashValue = result;
        }
        return this.hashValue;
    }

}
