/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.dao.admin.hibernate;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.aof.dao.BaseDAOHibernate;
import com.aof.dao.admin.FunctionDAO;
import com.aof.dao.convert.LikeConvert;
import com.aof.model.admin.Function;
import com.aof.model.admin.query.FunctionQueryCondition;
import com.aof.model.admin.query.FunctionQueryOrder;

/**
 * FunctionDAOµÄHibernateÊµÏÖ
 * @author nicebean
 * @version 1.0 (2005-11-11)
 */
public class FunctionDAOHibernate extends BaseDAOHibernate implements FunctionDAO {
    private Log log = LogFactory.getLog(FunctionDAOHibernate.class);

    /* (non-Javadoc)
     * @see com.aof.dao.admin.FunctionDAO#getFunction(java.lang.Integer)
     */
    public Function getFunction(Integer id){
        if (id == null) {
            if (log.isDebugEnabled()) log.debug("try to get Function with null id");
            return null;
        }
        return (Function) getHibernateTemplate().get(Function.class, id);
    }

    /* (non-Javadoc)
     * @see com.aof.dao.admin.FunctionDAO#saveFunction(com.aof.model.admin.Function)
     */
    public Function saveFunction(Function function) {
        Integer id = function.getId();
        if (id == null) {
            throw new RuntimeException("cannot save a function with null id");
        }
        Function f = getFunction(id);
        if (f == null) {
            getHibernateTemplate().save(function);
            return function;
        }
        f.setName(function.getName());
        f.setDescription((function.getDescription()));
        getHibernateTemplate().update(f);
        return f;
    }

    private final static String SQL_COUNT = "select count(*) from Function f";

    private final static String SQL = "from Function f";

    private final static Object[][] QUERY_CONDITIONS = {
        { FunctionQueryCondition.ID_EQ, "f.id = ?", null },
        { FunctionQueryCondition.NAME_LIKE, "f.name like ?", new LikeConvert() },
    };
    
    private final static Object[][] QUERY_ORDERS = {
        { FunctionQueryOrder.ID, "f.id" },
        { FunctionQueryOrder.NAME, "f.name" },
        { FunctionQueryOrder.DESCRIPTION, "f.description" },
    };
    
    /* (non-Javadoc)
     * @see com.aof.dao.admin.FunctionDAO#getFunctionListCount(java.util.Map)
     */
    public int getFunctionListCount(final Map conditions) {
        return getListCount(conditions, SQL_COUNT, QUERY_CONDITIONS);
    }

    /* (non-Javadoc)
     * @see com.aof.dao.admin.FunctionDAO#getFunctionList(java.util.Map, int, int, com.aof.model.admin.query.FunctionQueryOrder, boolean)
     */
    public List getFunctionList(final Map conditions, final int pageNo, final int pageSize, final FunctionQueryOrder order, final boolean descend) {
        return getList(conditions, pageNo, pageSize, order, descend, SQL, QUERY_CONDITIONS, QUERY_ORDERS);
    }

}
