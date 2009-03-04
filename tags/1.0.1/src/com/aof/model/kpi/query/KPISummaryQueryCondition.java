package com.aof.model.kpi.query;

import org.apache.commons.lang.enums.Enum;

public class KPISummaryQueryCondition extends Enum {
    
    public static final KPISummaryQueryCondition ID_EQ = new KPISummaryQueryCondition("id_eq");
    
    public static final KPISummaryQueryCondition SUMMARYDATE_EQ = new KPISummaryQueryCondition("summaryDate_eq");
    
    public static final KPISummaryQueryCondition SUMMARYDATE_GE = new KPISummaryQueryCondition("summaryDate_ge");
    
    public static final KPISummaryQueryCondition SUMMARYDATE_LE = new KPISummaryQueryCondition("summaryDate_le");       

    protected KPISummaryQueryCondition(String value) {
        super(value);
    }

    public static KPISummaryQueryCondition getEnum(String value) {
        return (KPISummaryQueryCondition) getEnum(KPISummaryQueryCondition.class, value);
    }
}
