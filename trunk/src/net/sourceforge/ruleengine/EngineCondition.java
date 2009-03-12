/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.ruleengine;

/**
 * Rule Engine中的Condition，代表一个Rule中的一个比较条件
 * 
 * @author nicebean
 * @version 1.0 (2005-11-15)
 */
public class EngineCondition {
    /**
     * 比较规则：小于
     */
    public static final int COMPARE_PASS_CONDITION_LESS_THAN = 1;

    /**
     * 比较规则：小于等于
     */
    public static final int COMPARE_PASS_CONDITION_LESS_EQUAL = 2;

    /**
     * 比较规则：大于等于
     */
    public static final int COMPARE_PASS_CONDITION_GREATER_EQUAL = 3;

    /**
     * 比较规则：大于
     */
    public static final int COMPARE_PASS_CONDITION_GREATER_THAN = 4;

    /**
     * 比较规则：等于
     */
    public static final int COMPARE_PASS_CONDITION_EQUAL = 5;

    /**
     * 比较规则：不等于
     */
    public static final int COMPARE_PASS_CONDITION_NOT_EQUAL = 6;

    private String compareSource;

    private Object compareTarget;

    private int comparePassCondition;

    /**
     * 构造函数
     */
    EngineCondition(String compareSource, int comparePassCondition, Object compareTarget) {
        this.compareSource = compareSource;
        
        switch (comparePassCondition) {
        case COMPARE_PASS_CONDITION_LESS_THAN:
        case COMPARE_PASS_CONDITION_LESS_EQUAL:
        case COMPARE_PASS_CONDITION_GREATER_EQUAL:
        case COMPARE_PASS_CONDITION_GREATER_THAN:
        case COMPARE_PASS_CONDITION_EQUAL:
        case COMPARE_PASS_CONDITION_NOT_EQUAL:
            break;
        default:
            throw new RuntimeException("Compare pass condition code " + comparePassCondition + " is not supported");
        }
        this.comparePassCondition = comparePassCondition;
        
        this.compareTarget = compareTarget;
    }

    /**
     * @return Returns the comparePassCondition.
     */
    public int getComparePassCondition() {
        return comparePassCondition;
    }

    /**
     * @return Returns the compareSource.
     */
    public String getCompareSource() {
        return compareSource;
    }

    /**
     * @return Returns the compareTarget.
     */
    public Object getCompareTarget() {
        return compareTarget;
    }

}
