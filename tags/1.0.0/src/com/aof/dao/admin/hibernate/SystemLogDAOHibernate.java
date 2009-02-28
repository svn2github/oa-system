/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.dao.admin.hibernate;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.aof.dao.BaseDAOHibernate;
import com.aof.dao.admin.SystemLogDAO;
import com.aof.dao.convert.LikeConvert;
import com.aof.model.admin.SystemLog;
import com.aof.model.admin.query.SystemLogQueryCondition;
import com.aof.model.admin.query.SystemLogQueryOrder;

/**
 * SystemLogDAOµÄHibernateÊµÏÖ 
 * @author nicebean
 * @version 1.0 (2006-02-15)
 */
public class SystemLogDAOHibernate extends BaseDAOHibernate implements SystemLogDAO {
    private Log log = LogFactory.getLog(SystemLogDAOHibernate.class);


    public SystemLog getSystemLog(Integer id){
        if (id == null) {
            if (log.isDebugEnabled()) log.debug("try to get system log with null id");
            return null;
        }
        return (SystemLog) getHibernateTemplate().get(SystemLog.class, id);
    }

	public SystemLog insertSystemLog(SystemLog systemLog) {
        systemLog.setActionTime(new Date());
       	getHibernateTemplate().save(systemLog);
       	return systemLog;
    }

    private final static String SQL_COUNT = "select count(*) from SystemLog s";

    private final static String SQL = "from SystemLog s";

    private final static Object[][] QUERY_CONDITIONS = {
		{ SystemLogQueryCondition.ID_EQ, "s.id = ?", null },
		{ SystemLogQueryCondition.CONTENT_LIKE, "s.content like ?", new LikeConvert() },
		{ SystemLogQueryCondition.SITE_ID_EQ, "s.site.id = ?", null },
		{ SystemLogQueryCondition.TARGET_LIKE, "s.target like ?", new LikeConvert() },
        { SystemLogQueryCondition.TARGET_EQ, "s.target = ?", null },
		{ SystemLogQueryCondition.USER_NAME_LIKE, "s.user.name like ?", new LikeConvert() },
        { SystemLogQueryCondition.USER_ID_EQ, "s.user.loginName = ?", null },
        { SystemLogQueryCondition.ACTION_TIME_GT, "s.actionTime >= ?", null },
        { SystemLogQueryCondition.ACTION_TIME_LT, "s.actionTime <= ?", null },
    };
    
    private final static Object[][] QUERY_ORDERS = {
        { SystemLogQueryOrder.ID, "s.id" },
        { SystemLogQueryOrder.ACTION, "s.action" },
        { SystemLogQueryOrder.ACTIONTIME, "s.actionTime" },
        { SystemLogQueryOrder.SITE_NAME, "s.site.name" },
        { SystemLogQueryOrder.TARGET, "s.target" },
        { SystemLogQueryOrder.USER_NAME, "s.user.name" },
        { SystemLogQueryOrder.USER_ID, "s.user.loginName" },
    };
    
    public int getSystemLogListCount(final Map conditions) {
        return getListCount(conditions, SQL_COUNT, QUERY_CONDITIONS);
    }

    public List getSystemLogList(final Map conditions, final int pageNo, final int pageSize, final SystemLogQueryOrder order, final boolean descend) {
        return getList(conditions, pageNo, pageSize, order, descend, SQL, QUERY_CONDITIONS, QUERY_ORDERS);
    }
    

}
