/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.dao.business.pr.hibernate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate.HibernateCallback;

import com.aof.dao.BaseDAOHibernate;
import com.aof.dao.business.pr.YearlyBudgetDAO;
import com.aof.dao.convert.LikeConvert;
import com.aof.model.admin.Department;
import com.aof.model.admin.PurchaseCategory;
import com.aof.model.admin.PurchaseSubCategory;
import com.aof.model.admin.Site;
import com.aof.model.business.pr.YearlyBudget;
import com.aof.model.business.pr.YearlyBudgetHistory;
import com.aof.model.business.pr.YearlyBudgetHistoryDepartment;
import com.aof.model.business.pr.query.YearlyBudgetQueryCondition;
import com.aof.model.business.pr.query.YearlyBudgetQueryOrder;
import com.aof.model.metadata.BudgetStatus;
import com.aof.model.metadata.BudgetType;
import com.shcnc.hibernate.CompositeQuery;
import com.shcnc.hibernate.QueryCondition;

/**
 * hibernate implement of YearlyBudgetDAO
 * @author shilei
 * @version 1.0 (Nov 29, 2005)
 */
public class YearlyBudgetDAOHibernate extends BaseDAOHibernate implements YearlyBudgetDAO {
    private Log log = LogFactory.getLog(YearlyBudgetDAOHibernate.class);


    public YearlyBudget getYearlyBudget(Integer id){
        if (id == null) {
            if (log.isDebugEnabled()) log.debug("try to get YearlyBudget with null id");
            return null;
        }
        return (YearlyBudget) getHibernateTemplate().get(YearlyBudget.class, id);
    }

    public YearlyBudget updateYearlyBudget(YearlyBudget yearlyBudget) {
        	getHibernateTemplate().update(yearlyBudget);	
        	return yearlyBudget;
    }

	public YearlyBudget insertYearlyBudget(YearlyBudget yearlyBudget) {
       	getHibernateTemplate().save(yearlyBudget);
       	return yearlyBudget;
    }

    private final static String SQL_COUNT = "select count(*) from YearlyBudget yearlyBudget left join yearlyBudget.purchaseCategory left join yearlyBudget.purchaseSubCategory";

    private final static String SQL = "select yearlyBudget from YearlyBudget yearlyBudget left join yearlyBudget.purchaseCategory left join yearlyBudget.purchaseSubCategory";

    private final static Object[][] QUERY_CONDITIONS = {
        
        { YearlyBudgetQueryCondition.HAS_PURCHASESUBCATEGORY,
            "(yearlyBudget.purchaseCategory is null) or (yearlyBudget.purchaseCategory.id=? and yearlyBudget.purchaseSubCategory is null) or (yearlyBudget.purchaseSubCategory.id=?)"
            , null },
    
		{ YearlyBudgetQueryCondition.ID_EQ, "yearlyBudget.id = ?", null },
        
        { YearlyBudgetQueryCondition.HAS_DEPARTMENT, "yearlyBudget.id in (select ybd.yearlyBudget.id from YearlyBudgetDepartment ybd where ybd.department.id=?)", null },

		{ YearlyBudgetQueryCondition.SITE_ID_EQ, "yearlyBudget.site.id = ?", null },
		{ YearlyBudgetQueryCondition.PURCHASECATEGORY_ID_EQ, "yearlyBudget.purchaseCategory.id = ?", null },
		{ YearlyBudgetQueryCondition.PURCHASESUBCATEGORY_ID_EQ, "yearlyBudget.purchaseSubCategory.id = ?", null },
		{ YearlyBudgetQueryCondition.CREATOR_ID_EQ, "yearlyBudget.creator.id = ?", null },
		{ YearlyBudgetQueryCondition.MODIFIER_ID_EQ, "yearlyBudget.modifier.id = ?", null },
        
        

		{ YearlyBudgetQueryCondition.CODE_EQ, "yearlyBudget.code = ?", null },
		{ YearlyBudgetQueryCondition.NAME_LIKE, "yearlyBudget.name like ?", new LikeConvert() },
		{ YearlyBudgetQueryCondition.TYPE_EQ, "yearlyBudget.type = ?", null },
		{ YearlyBudgetQueryCondition.YEAR_EQ, "yearlyBudget.year = ?", null },
		{ YearlyBudgetQueryCondition.AMOUNT_EQ, "yearlyBudget.amount = ?", null },
		{ YearlyBudgetQueryCondition.ACTUALAMOUNT_EQ, "yearlyBudget.actualAmount = ?", null },
		{ YearlyBudgetQueryCondition.STATUS_EQ, "yearlyBudget.status = ?", null },
		{ YearlyBudgetQueryCondition.ISFREEZE_EQ, "yearlyBudget.isFreeze = ?", null },
		{ YearlyBudgetQueryCondition.CREATEDATE_EQ, "yearlyBudget.createDate = ?", null },
		{ YearlyBudgetQueryCondition.MODIFYDATE_EQ, "yearlyBudget.modifyDate = ?", null },
        { YearlyBudgetQueryCondition.AMOUNT_LE, "yearlyBudget.amount <= ?", null },
        { YearlyBudgetQueryCondition.AMOUNT_GE, "yearlyBudget.amount >= ?", null },
    };
    
    private final static Object[][] QUERY_ORDERS = {
		/*id*/
        { YearlyBudgetQueryOrder.ID, "yearlyBudget.id" },

		/*property*/
        { YearlyBudgetQueryOrder.CODE, "yearlyBudget.code" },
        { YearlyBudgetQueryOrder.NAME, "yearlyBudget.name" },
        { YearlyBudgetQueryOrder.TYPE, "yearlyBudget.type" },
        { YearlyBudgetQueryOrder.YEAR, "yearlyBudget.year" },
        { YearlyBudgetQueryOrder.AMOUNT, "yearlyBudget.amount" },
        { YearlyBudgetQueryOrder.ACTUALAMOUNT, "yearlyBudget.actualAmount" },
        { YearlyBudgetQueryOrder.STATUS, "yearlyBudget.status" },
        { YearlyBudgetQueryOrder.ISFREEZE, "yearlyBudget.isFreeze" },
        { YearlyBudgetQueryOrder.CREATEDATE, "yearlyBudget.createDate" },
        { YearlyBudgetQueryOrder.MODIFYDATE, "yearlyBudget.modifyDate" },
    };
    
    public int getYearlyBudgetListCount(final Map conditions) {
        return getListCount(conditions, SQL_COUNT, QUERY_CONDITIONS);
    }

    public List getYearlyBudgetList(final Map conditions, final int pageNo, final int pageSize, final YearlyBudgetQueryOrder order, final boolean descend) {
        return getList(conditions, pageNo, pageSize, order, descend, SQL, QUERY_CONDITIONS, QUERY_ORDERS);
    }



    public YearlyBudgetHistory insertYearlyBudgetHistory(YearlyBudgetHistory ybh) {
        this.getHibernateTemplate().save(ybh);
        return ybh;
        
    }

    public void insertYearlyBudgetHistoryDepartment(YearlyBudgetHistoryDepartment ybhd) {
        this.getHibernateTemplate().save(ybhd);
        
    }

    public List getSuitableYearlyBudget(final Site site, final PurchaseCategory pc,
            final PurchaseSubCategory psc, final List departmentList,final BudgetType budgetType) {
        return getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                CompositeQuery query = new CompositeQuery("from YearlyBudget yb", "yb.code", session);
                if (psc == null && pc == null) {
                    if (site != null) { 
                        QueryCondition qc = query.createQueryCondition("yb.site.id = ?");
                        qc.appendParameter(site.getId());
                    }
                    query.createQueryCondition("yb.purchaseCategory.id is null and yb.purchaseSubCategory.id is null");
                } else {
                    if (psc != null) {
                        QueryCondition qc = query.createQueryCondition("yb.purchaseSubCategory.id = ? or ((yb.purchaseCategory.id = ? or yb.purchaseCategory.id is null) and yb.purchaseSubCategory.id is null)");
                        qc.appendParameter(psc.getId());
                        qc.appendParameter(pc.getId());
                    } else if (pc != null) {
                        QueryCondition qc = query.createQueryCondition("(yb.purchaseCategory.id = ? or yb.purchaseCategory.id is null) and yb.purchaseSubCategory.id is null");
                        qc.appendParameter(pc.getId());
                    }
                }
                QueryCondition qc = query.createQueryCondition("yb.status = ? and yb.type = ?");
                qc.appendParameter(BudgetStatus.OPEN.getEnumCode());
                qc.appendParameter(budgetType.getEnumCode());
                List yearlyBudegtList = query.list();

                List needIdList = new ArrayList();
                for (Iterator itor = departmentList.iterator(); itor.hasNext();) {
                    Department d = (Department) itor.next();
                    needIdList.add(d.getId());
                }
                List idList1 = new ArrayList();
                for (Iterator itor = yearlyBudegtList.iterator(); itor.hasNext();) {
                    YearlyBudget yb = (YearlyBudget) itor.next();
                    List idList2 = session.find("select ybd.department.id from YearlyBudgetDepartment ybd where ybd.yearlyBudget.id = ? order by ybd.department.id", yb.getId(), Hibernate.INTEGER);
                    idList1.addAll(needIdList);
                    idList1.removeAll(idList2);
                    if (idList1.isEmpty()) {
                        yb.setDepartmentIdString(convertIdCollectinToIdString(idList2));
                    } else {
                        itor.remove();
                    }
                }
                return yearlyBudegtList;
            }
            
        });
        
    }

    private String convertIdCollectinToIdString(Collection idList) {
        StringBuffer result = new StringBuffer(',');
        for (Iterator itor = idList.iterator(); itor.hasNext();) {
            result.append(((Integer)itor.next()).intValue()).append(',');
        }
        return result.toString();
    }

}
