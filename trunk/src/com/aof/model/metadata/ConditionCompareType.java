/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.metadata;

import com.aof.ruleengine.EngineCondition;

public class ConditionCompareType extends MetadataDetailEnum {
    public final static ConditionCompareType LESS_THAN =  new ConditionCompareType(1, "<", MetadataType.CONDITION_COMPARE_TYPE);
    public final static ConditionCompareType LESS_OR_EQUAL =  new ConditionCompareType(2, "<=", MetadataType.CONDITION_COMPARE_TYPE);
    public final static ConditionCompareType EQUAL =  new ConditionCompareType(3, "=", MetadataType.CONDITION_COMPARE_TYPE);
    public final static ConditionCompareType GREATER_OR_EQUAL =  new ConditionCompareType(4, ">=", MetadataType.CONDITION_COMPARE_TYPE);
    public final static ConditionCompareType GREATER_THAN =  new ConditionCompareType(5, ">", MetadataType.CONDITION_COMPARE_TYPE);
    
    static {
        LESS_THAN.engineComparePassCondition = EngineCondition.COMPARE_PASS_CONDITION_LESS_THAN;
        LESS_OR_EQUAL.engineComparePassCondition = EngineCondition.COMPARE_PASS_CONDITION_LESS_EQUAL;
        EQUAL.engineComparePassCondition = EngineCondition.COMPARE_PASS_CONDITION_EQUAL;
        GREATER_OR_EQUAL.engineComparePassCondition = EngineCondition.COMPARE_PASS_CONDITION_GREATER_EQUAL;
        GREATER_THAN.engineComparePassCondition = EngineCondition.COMPARE_PASS_CONDITION_GREATER_THAN;
    }

    public ConditionCompareType() {
    }

    private ConditionCompareType(int metadataId, String defaultLabel, MetadataType type) {
        super(metadataId, defaultLabel, type);
    }

    private int engineComparePassCondition;
    
    /**
     * @return Returns the engineComparePassCondition.
     */
    public int getEngineComparePassCondition() {
        return engineComparePassCondition;
    }
    
}
