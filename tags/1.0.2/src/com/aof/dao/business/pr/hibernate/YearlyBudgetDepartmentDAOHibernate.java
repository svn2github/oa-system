/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.dao.business.pr.hibernate;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.aof.dao.BaseDAOHibernate;
import com.aof.dao.business.pr.YearlyBudgetDepartmentDAO;
import com.aof.dao.convert.LikeConvert;
import com.aof.model.business.pr.YearlyBudgetDepartment;
import com.aof.model.business.pr.query.YearlyBudgetDepartmentQueryCondition;
import com.aof.model.business.pr.query.YearlyBudgetDepartmentQueryOrder;

/**
 * hibernate implement for YearlyBudgetDepartmentDAO
 * @author shi1
 * @version 1.0 (Nov 30, 2005)
 */
public class YearlyBudgetDepartmentDAOHibernate extends BaseDAOHibernate implements YearlyBudgetDepartmentDAO {
    private Log log = LogFactory.getLog(YearlyBudgetDepartmentDAOHibernate.class);


    public YearlyBudgetDepartment getYearlyBudgetDepartment(Integer id){
        if (id == null) {
            if (log.isDebugEnabled()) log.debug("try to get YearlyBudgetDepartment with null id");
            return null;
        }
        return (YearlyBudgetDepartment) getHibernateTemplate().get(YearlyBudgetDepartment.class, id);
    }

    public YearlyBudgetDepartment updateYearlyBudgetDepartment(YearlyBudgetDepartment yearlyBudgetDepartment) {
        Integer id = yearlyBudgetDepartment.getId();
        if (id == null) {
            throw new RuntimeException("cannot save a yearlyBudgetDepartment with null id");
        }
        YearlyBudgetDepartment oldYearlyBudgetDepartment = getYearlyBudgetDepartment(id);
        if (oldYearlyBudgetDepartment != null) {
        	try{
                PropertyUtils.copyProperties(oldYearlyBudgetDepartment,yearlyBudgetDepartment);
        	}
        	catch(Exception e)
        	{
                throw new RuntimeException(e);
        	}
        	getHibernateTemplate().update(oldYearlyBudgetDepartment);	
        	return oldYearlyBudgetDepartment;
        }
        else
        {
        	throw new RuntimeException("yearlyBudgetDepartment not found");
        }
    }

	public YearlyBudgetDepartment insertYearlyBudgetDepartment(YearlyBudgetDepartment yearlyBudgetDepartment) {
       	getHibernateTemplate().save(yearlyBudgetDepartment);
       	return yearlyBudgetDepartment;
    }

    private final static String SQL_COUNT = "select count(*) from YearlyBudgetDepartment ybd";

    private final static String SQL = "from YearlyBudgetDepartment ybd";

    private final static Object[][] QUERY_CONDITIONS = {
        //yearlyBudget
        { YearlyBudgetDepartmentQueryCondition.YEARLYBUDGET_ISFREEZE_EQ, "ybd.yearlyBudget.isFreeze = ?", null },
        { YearlyBudgetDepartmentQueryCondition.YEARLYBUDGET_SITE_ID_EQ, "ybd.yearlyBudget.site.id = ?", null },
        { YearlyBudgetDepartmentQueryCondition.YEARLYBUDGET_YEAR_EQ, "ybd.yearlyBudget.year = ?", null },
        { YearlyBudgetDepartmentQueryCondition.YEARLYBUDGET_STATUS_EQ, "ybd.yearlyBudget.status = ?", null },
        { YearlyBudgetDepartmentQueryCondition.YEARLYBUDGET_CODE_EQ, "ybd.yearlyBudget.code = ?", null },
        { YearlyBudgetDepartmentQueryCondition.YEARLYBUDGET_NAME_LIKE, "ybd.yearlyBudget.name like ?", new LikeConvert() },
        { YearlyBudgetDepartmentQueryCondition.YEARLYBUDGET_AMOUNT_LE, "ybd.yearlyBudget.amount <= ?", null },
        { YearlyBudgetDepartmentQueryCondition.YEARLYBUDGET_AMOUNT_GE, "ybd.yearlyBudget.amount >= ?", null },
        { YearlyBudgetDepartmentQueryCondition.YEARLYBUDGET_TYPE_EQ, "ybd.yearlyBudget.type = ?", null },
        { YearlyBudgetDepartmentQueryCondition.YEARLYBUDGET_HAS_DEPARTMENT, "ybd.yearlyBudget.id  in (select ybd.yearlyBudget.id from YearlyBudgetDepartment ybd where ybd.department.id=?)", null },
        
        //ybd
		{ YearlyBudgetDepartmentQueryCondition.ID_EQ, "ybd.id = ?", null },
		{ YearlyBudgetDepartmentQueryCondition.YEARLYBUDGET_ID_EQ, "ybd.yearlyBudget.id = ?", null },
		{ YearlyBudgetDepartmentQueryCondition.DEPARTMENT_ID_EQ, "ybd.department.id = ?", null },


    };
    
    private final static Object[][] QUERY_ORDERS = {
        //ybd
        { YearlyBudgetDepartmentQueryOrder.ID, "ybd.id" },

    };
    
    public int getYearlyBudgetDepartmentListCount(final Map conditions) {
        return getListCount(conditions, SQL_COUNT, QUERY_CONDITIONS);
    }

    public List getYearlyBudgetDepartmentList(final Map conditions, final int pageNo, final int pageSize, final YearlyBudgetDepartmentQueryOrder order, final boolean descend) {
        return getList(conditions, pageNo, pageSize, order, descend, SQL, QUERY_CONDITIONS, QUERY_ORDERS);
    }

    public void delete(YearlyBudgetDepartment ybd) {
        this.getHibernateTemplate().delete(ybd);
    }


}
