package com.aof.service.kpi;

import java.util.List;
import java.util.Map;

import com.aof.model.admin.Site;
import com.aof.model.kpi.query.KPISummaryQueryOrder;

public interface KPIManager {
    public void doSummary();    
    
    public List getKPISummaryList(Map conditions, int pageNo, int pageSize, KPISummaryQueryOrder order, boolean descend);
    
    public List getTop5KPIExpenseCategorySummary(Site site);
    
    public List getTop5KPIPurchaseCategorySummary(Site site);
}
