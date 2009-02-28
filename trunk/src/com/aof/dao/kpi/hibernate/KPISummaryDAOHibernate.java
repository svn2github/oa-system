package com.aof.dao.kpi.hibernate;

import java.util.List;
import java.util.Map;

import net.sf.hibernate.Hibernate;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.aof.dao.BaseDAOHibernate;
import com.aof.dao.kpi.KPISummaryDAO;
import com.aof.model.admin.Site;
import com.aof.model.kpi.KPIExpenseCategorySummary;
import com.aof.model.kpi.KPIPurchaseCategorySummary;
import com.aof.model.kpi.KPISummary;
import com.aof.model.kpi.query.KPISummaryQueryCondition;
import com.aof.model.kpi.query.KPISummaryQueryOrder;

public class KPISummaryDAOHibernate extends BaseDAOHibernate implements KPISummaryDAO {
    private Log log = LogFactory.getLog(KPISummaryDAOHibernate.class);
    
    //private final static String SQL_COUNT = "select count(*) from KPISummary kpiSummary";
    
    private final static  String SQL_TOP5_EXPENSE_CATEGORY = "select kecs.expenseCategory.description, "
                                                            + "sum(kecs.expenseRequestCreatedToday) "
                                                            + "from KPIExpenseCategorySummary as kecs "
                                                            + "where kecs.site.id = ? "
                                                            + "group by kecs.expenseCategory.description "
                                                            + "order by sum(kecs.expenseRequestCreatedToday) DESC ";
    
    private final static  String SQL_TOP5_PURCHASE_CATEGORY  = "select kpcs.purchaseCategory.description, "
                                                            + "sum(kpcs.purchaseRequestCreatedToday) "
                                                            + "from KPIPurchaseCategorySummary as kpcs "
                                                            + "where kpcs.site.id = ? "
                                                            + "group by kpcs.purchaseCategory.description "
                                                            + "order by sum(kpcs.purchaseRequestCreatedToday) DESC ";

    private final static String SQL = "from KPISummary kpiSummary";
    
    private final static Object[][] QUERY_CONDITIONS = {
        { KPISummaryQueryCondition.ID_EQ, "kpiSummary.id = ?", null },
        { KPISummaryQueryCondition.SUMMARYDATE_EQ, "kpiSummary.summaryDate = ?", null },
        { KPISummaryQueryCondition.SUMMARYDATE_GE, "kpiSummary.summaryDate >= ?", null },
        { KPISummaryQueryCondition.SUMMARYDATE_LE, "kpiSummary.summaryDate <= ?", null },
    };
    
    private final static Object[][] QUERY_ORDERS = {
        { KPISummaryQueryOrder.ID, "kpiSummary.id" }, 
        { KPISummaryQueryOrder.SUMMARYDATE, "kpiSummary.summaryDate" }, 
        { KPISummaryQueryOrder.LOGINUSERCOUNT, "kpiSummary.loginUserCount" }, 
        { KPISummaryQueryOrder.CLOSEDREQUESTNUMTODAY, "kpiSummary.closedRequestNumToday" }, 
        { KPISummaryQueryOrder.AVGREQUESTCLOSEDDAYS, "kpiSummary.avgRequestClosedDays" }, 
        { KPISummaryQueryOrder.CREATEDREQUESTNUMTODAY, "kpiSummary.createdRequestNumToday" },
    };
    
    public KPISummary getKPISummary(Integer id) {
        if (id == null) {
            if (log.isDebugEnabled())
                log.debug("try to get KPISummary with null id");
            return null;
        }
        return (KPISummary) getHibernateTemplate().get(KPISummary.class, id);
    }
    
    public KPISummary insertKPISummary(KPISummary kpiSummary) {
        getHibernateTemplate().save(kpiSummary);
        return kpiSummary;
    }

    public KPISummary updateKPISummary(KPISummary kpiSummary) {
        Integer id = kpiSummary.getId();
        if (id == null) {
            throw new RuntimeException("cannot save a KPISummary with null id");
        }
        KPISummary oldKPISummary = getKPISummary(id);
        if (oldKPISummary != null) {
            try {
                PropertyUtils.copyProperties(oldKPISummary, kpiSummary);
            } catch (Exception e) {
                throw new RuntimeException("copy error£º"+e);
            }
            getHibernateTemplate().update(oldKPISummary);
            return oldKPISummary;
        } else {
            throw new RuntimeException("KPISummary not found");
        }
    }
    
    public void deleteKPISummary(KPISummary kpiSummary) {
        this.getHibernateTemplate().delete(kpiSummary);
    }

    public KPIPurchaseCategorySummary getKPIPurchaseCategorySummary(Integer id) {
        if (id == null) {
            if (log.isDebugEnabled())
                log.debug("try to get KPIPurchaseCategorySummary with null id");
            return null;
        }
        return (KPIPurchaseCategorySummary) getHibernateTemplate().get(KPIPurchaseCategorySummary.class, id);
    }

    public KPIPurchaseCategorySummary insertKPIPurchaseCategorySummary(KPIPurchaseCategorySummary kpiPurchaseCategorySummary) {
        getHibernateTemplate().save(kpiPurchaseCategorySummary);
        return kpiPurchaseCategorySummary;
    }

    public KPIPurchaseCategorySummary updateKPIPurchaseCategorySummary(KPIPurchaseCategorySummary kpiPurchaseCategorySummary) {
        Integer id = kpiPurchaseCategorySummary.getId();
        if (id == null) {
            throw new RuntimeException("cannot save a KPIPurchaseCategorySummary with null id");
        }
        KPIPurchaseCategorySummary oldKPIPurchaseCategorySummary = getKPIPurchaseCategorySummary(id);
        if (oldKPIPurchaseCategorySummary != null) {
            try {
                PropertyUtils.copyProperties(oldKPIPurchaseCategorySummary, kpiPurchaseCategorySummary);
            } catch (Exception e) {
                throw new RuntimeException("copy error£º"+e);
            }
            getHibernateTemplate().update(oldKPIPurchaseCategorySummary);
            return oldKPIPurchaseCategorySummary;
        } else {
            throw new RuntimeException("KPIPurchaseCategorySummary not found");
        }
    }

    public void deleteKPIPurchaseCategorySummary(KPIPurchaseCategorySummary kpiPurchaseCategorySummary) {
        this.getHibernateTemplate().delete(kpiPurchaseCategorySummary);
    }

    public KPIExpenseCategorySummary getKPIExpenseCategorySummary(Integer id) {
        if (id == null) {
            if (log.isDebugEnabled())
                log.debug("try to get KPIExpenseCategorySummary with null id");
            return null;
        }
        return (KPIExpenseCategorySummary) getHibernateTemplate().get(KPIExpenseCategorySummary.class, id);
    }

    public KPIExpenseCategorySummary insertKPIExpenseCategorySummary(KPIExpenseCategorySummary kpiExpenseCategorySummary) {
        getHibernateTemplate().save(kpiExpenseCategorySummary);
        return kpiExpenseCategorySummary;
    }

    public KPIExpenseCategorySummary updateKPIExpenseCategorySummary(KPIExpenseCategorySummary kpiExpenseCategorySummary) {
        Integer id = kpiExpenseCategorySummary.getId();
        if (id == null) {
            throw new RuntimeException("cannot save a KPIExpenseCategorySummary with null id");
        }
        KPIExpenseCategorySummary oldKPIExpenseCategorySummary = getKPIExpenseCategorySummary(id);
        if (oldKPIExpenseCategorySummary != null) {
            try {
                PropertyUtils.copyProperties(oldKPIExpenseCategorySummary, kpiExpenseCategorySummary);
            } catch (Exception e) {
                throw new RuntimeException("copy error£º"+e);
            }
            getHibernateTemplate().update(oldKPIExpenseCategorySummary);
            return oldKPIExpenseCategorySummary;
        } else {
            throw new RuntimeException("KPIExpenseCategorySummary not found");
        }
    }

    public void deleteKPIExpenseCategorySummary(KPIExpenseCategorySummary kpiExpenseCategorySummary) {
        this.getHibernateTemplate().delete(kpiExpenseCategorySummary);
    }
    
    public List getKPISummaryList(final Map conditions, final int pageNo, final int pageSize, final KPISummaryQueryOrder order, final boolean descend) {
        return getList(conditions, pageNo, pageSize, order, descend, SQL, QUERY_CONDITIONS, QUERY_ORDERS);
    }
    
    public List getTop5KPIExpenseCategorySummary(Site site) {               
       return this.getHibernateTemplate().find(SQL_TOP5_EXPENSE_CATEGORY, site.getId(), Hibernate.INTEGER);       
    }
    
    public List getTop5KPIPurchaseCategorySummary(Site site) {               
        return this.getHibernateTemplate().find(SQL_TOP5_PURCHASE_CATEGORY, site.getId(), Hibernate.INTEGER);       
     }
}
