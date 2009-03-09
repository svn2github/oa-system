/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.dao.admin.hibernate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate.HibernateCallback;

import com.aof.dao.BaseDAOHibernate;
import com.aof.dao.admin.DepartmentDAO;
import com.aof.model.admin.Department;
import com.aof.model.admin.Site;
import com.aof.model.admin.query.DepartmentQueryCondition;
import com.aof.model.admin.query.DepartmentQueryOrder;
import com.aof.model.metadata.EnabledDisabled;
import com.shcnc.hibernate.CompositeQuery;

/**
 * DepartmentDAOµÄHibernateÊµÏÖ
 * @author nicebean
 * @version 1.0 (2005-11-11)
 */
public class DepartmentDAOHibernate extends BaseDAOHibernate implements DepartmentDAO {
    private Log log = LogFactory.getLog(DepartmentDAOHibernate.class);

    /* (non-Javadoc)
     * @see com.aof.dao.admin.DepartmentDAO#getDepartment(java.lang.Integer)
     */
    public Department getDepartment(Integer id) {
        if (id == null) {
            if (log.isDebugEnabled()) log.debug("try to get Department with null id");
            return null;
        }
        return (Department) getHibernateTemplate().get(Department.class, id);
    }

    /* (non-Javadoc)
     * @see com.aof.dao.admin.DepartmentDAO#saveDepartment(com.aof.model.admin.Department)
     */
    public Department saveDepartment(final Department department) {
        getHibernateTemplate().saveOrUpdate(department);
        return department;
    }

    /* (non-Javadoc)
     * @see com.aof.dao.admin.DepartmentDAO#removeDepartment(java.lang.Integer)
     */
    public void removeDepartment(Integer id) {
        Department department = getDepartment(id);
        getHibernateTemplate().delete(department);
    }

    private final static String SQL_COUNT = "select count(*) from Department d";
    
    private final static String SQL = "from Department d";
    
    private final static Object[][] QUERY_CONDITIONS = {
        { DepartmentQueryCondition.SITE_EQ, "d.site.id = ?", null },
        { DepartmentQueryCondition.ENABLED_EQ, "d.enabled = ?", null },
    };
    
    private final static Object[][] QUERY_ORDERS = {
        { DepartmentQueryOrder.NAME, "d.name" },
    };
    
    /* (non-Javadoc)
     * @see com.aof.dao.admin.DepartmentDAO#getDepartmentListCount(java.util.Map)
     */
    public int getDepartmentListCount(final Map conditions) {
        return getListCount(conditions, SQL_COUNT, QUERY_CONDITIONS);
    }

    /* (non-Javadoc)
     * @see com.aof.dao.admin.DepartmentDAO#getDepartmentList(java.util.Map, int, int, com.aof.model.admin.query.DepartmentQueryOrder, boolean)
     */
    public List getDepartmentList(final Map conditions, final int pageNo, final int pageSize, final DepartmentQueryOrder order, final boolean descend) {
        return getList(conditions, pageNo, pageSize, order, descend, SQL, QUERY_CONDITIONS, QUERY_ORDERS);
    }
    
	/* (non-Javadoc)
	 * @see com.aof.dao.admin.DepartmentDAO#fillSiteDepartment(java.util.List)
	 */
	public void fillSiteDepartment(final List siteList, final boolean onlyEnabled) {
		if(siteList.isEmpty()) return ;
		
		List departmentList = (List) getHibernateTemplate().execute( new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                CompositeQuery query = new CompositeQuery("from Department d", "", session);
                if (onlyEnabled) {
                    makeSimpleCondition(query, "d.enabled = ?", EnabledDisabled.ENABLED.getEnumCode());
                    if (siteList.size() < 20) {
                        query.createQueryCondition("d.site.id in " + getIdRange(siteList));
                    }
                }
                return query.list();
            }
            
        });
		
		for (Iterator iter = siteList.iterator(); iter.hasNext();) {
			Site site = (Site) iter.next();
			site.setDepartments(new ArrayList());
		}
		for (Iterator iter = departmentList.iterator(); iter.hasNext();) {
			Department d = (Department) iter.next();
            int i = siteList.indexOf(d.getSite());
            if (i != -1) {
                ((Site) siteList.get(i)).getDepartments().add(d);
            }
		}
		
	}

	private String getIdRange(List siteList) {
		StringBuffer sb=new StringBuffer();
		sb.append('(');
		for (Iterator iter = siteList.iterator(); iter.hasNext();) {
			Site site = (Site) iter.next();
			sb.append(site.getId());
			sb.append(',');
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append(')');
		return sb.toString();
	}


}
