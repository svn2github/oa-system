package net.sourceforge.service.kpi;

import java.util.List;
import java.util.Map;

import net.sourceforge.model.admin.Site;
import net.sourceforge.model.kpi.query.KPISummaryQueryOrder;

public interface KPIManager {
    public void doSummary();    
    
    public List getKPISummaryList(Map conditions, int pageNo, int pageSize, KPISummaryQueryOrder order, boolean descend);
    
    public List getTop5KPIExpenseCategorySummary(Site site);
    
    public List getTop5KPIPurchaseCategorySummary(Site site);
}
