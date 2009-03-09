package com.aof.model.kpi.query;

import org.apache.commons.lang.enums.Enum;

public class KPISummaryQueryOrder extends Enum{
    public static final KPISummaryQueryOrder ID = new KPISummaryQueryOrder("id");
    public static final KPISummaryQueryOrder SUMMARYDATE = new KPISummaryQueryOrder("summaryDate");
    public static final KPISummaryQueryOrder LOGINUSERCOUNT = new KPISummaryQueryOrder("loginUserCount");
    public static final KPISummaryQueryOrder CLOSEDREQUESTNUMTODAY = new KPISummaryQueryOrder("closedRequestNumToday");
    public static final KPISummaryQueryOrder AVGREQUESTCLOSEDDAYS = new KPISummaryQueryOrder("avgRequestClosedDays");
    public static final KPISummaryQueryOrder CREATEDREQUESTNUMTODAY = new KPISummaryQueryOrder("createdRequestNumToday");
    
    protected KPISummaryQueryOrder(String value) {
        super(value);
    }
    
    public static KPISummaryQueryOrder getEnum(String value) {
        return (KPISummaryQueryOrder) getEnum(KPISummaryQueryOrder.class, value);
    }
}
