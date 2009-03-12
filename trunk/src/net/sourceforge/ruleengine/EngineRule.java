/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.ruleengine;

import java.util.ArrayList;
import java.util.List;

/**
 * Rule Engine中的Rule
 * 
 * @author nicebean
 * @version 1.0 (2005-11-15)
 */
public class EngineRule {

    private Object externalId;

    private int nextSeqPass;

    private int nextSeqFail;

    private List conditions;

    private Object consequencesPass;

    private Object consequencesFail;

    int inDegree;

    EngineRule nextRulePass;

    EngineRule nextRuleFail;

    EngineFlow flow;

    /**
     * 构造函数
     * 
     * @param externalId
     *            和该EngineRule对应的外部Id，记录外部Id以便在外部Rule发生变化时快速确定哪些EngineRule需要更新
     */
    public EngineRule(Object externalId) {
        this.externalId = externalId;
        conditions = new ArrayList();
        nextSeqPass = -1;
        nextSeqFail = -1;
        flow = null;
    }

    /**
     * 取得该EngineRule对应的外部Id
     * 
     * @return 外部Id
     */
    public Object getExternalId() {
        return externalId;
    }

    /**
     * 删除所有的Condition
     * 
     */
    public void clearConditions() {
        if (flow != null && flow.locking.get() == null)
            throw new RuntimeException("Lock flow before clear rule condition.");
        conditions.clear();
    }

    /**
     * 添加Condition到Rule
     * 
     * @param compareSource
     *            描述从target得到用来比较的结果的property字符串
     * @param comparePassCondition
     *            比较通过的条件，应该取值为EngineCondition预定义的常量
     * @param compareTarget
     *            比较的对象
     */
    public void addCondition(String compareSource, int comparePassCondition, Object compareTarget) {
        if (flow != null && flow.locking.get() == null)
            throw new RuntimeException("Lock flow before add rule condition.");
        EngineCondition condition = new EngineCondition(compareSource, comparePassCondition, compareTarget);
        conditions.add(condition);
    }

    /**
     * @return Returns the conditions.
     */
    List getConditions() {
        return conditions;
    }

    /**
     * @return Returns the consequencesPass.
     */
    Object getConsequencesPass() {
        return consequencesPass;
    }

    /**
     * @param consequencesPass
     *            The consequencesPass to set.
     */
    public void setConsequencesPass(Object consequencesPass) {
        if (flow != null && flow.locking.get() == null)
            throw new RuntimeException("Lock flow before change rule consequence.");
        this.consequencesPass = consequencesPass;
    }

    /**
     * @return Returns the consequencesFail.
     */
    public Object getConsequencesFail() {
        return consequencesFail;
    }

    /**
     * @param consequencesFail
     *            The consequencesFail to set.
     */
    public void setConsequencesFail(Object consequencesFail) {
        if (flow != null && flow.locking.get() == null)
            throw new RuntimeException("Lock flow before change rule consequence.");
        this.consequencesFail = consequencesFail;
    }

    /**
     * @return Returns the nextSeqFail.
     */
    public int getNextSeqFail() {
        return nextSeqFail;
    }

    /**
     * @param nextSeqFail
     *            The nextSeqFail to set.
     */
    public void setNextSeqFail(int nextSeqFail) {
        if (flow != null && flow.locking.get() == null)
            throw new RuntimeException("Lock flow before change next seq when fail.");
        if (this.nextSeqFail != nextSeqFail) {
            this.nextSeqFail = nextSeqFail;
            if (flow != null)
                flow.canUse = false;
        }
    }

    /**
     * @return Returns the nextSeqPass.
     */
    public int getNextSeqPass() {
        return nextSeqPass;
    }

    /**
     * @param nextSeqPass
     *            The nextSeqPass to set.
     */
    public void setNextSeqPass(int nextSeqPass) {
        if (flow != null && flow.locking.get() == null)
            throw new RuntimeException("Lock flow before change next seq when pass.");
        if (this.nextSeqPass != nextSeqPass) {
            this.nextSeqPass = nextSeqPass;
            if (flow != null)
                flow.canUse = false;
        }
    }

}
