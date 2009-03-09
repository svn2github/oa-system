/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.dao.business.rule.hibernate;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.hibernate.Hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate.HibernateTemplate;

import com.aof.dao.BaseDAOHibernate;
import com.aof.dao.business.rule.FlowDAO;
import com.aof.dao.convert.LikeConvert;
import com.aof.model.business.rule.Flow;
import com.aof.model.business.rule.FlowRule;
import com.aof.model.business.rule.Rule;
import com.aof.model.business.rule.query.FlowQueryCondition;
import com.aof.model.business.rule.query.FlowQueryOrder;

/**
 * FlowDAOµÄHibernateÊµÏÖ
 * @author nicebean
 * @version 1.0 (2005-11-14)
 */
public class FlowDAOHibernate extends BaseDAOHibernate implements FlowDAO {
	private Log log = LogFactory.getLog(FlowDAOHibernate.class);

    /* (non-Javadoc)
     * @see com.aof.dao.business.rule.FlowDAO#getFlow(java.lang.Integer, boolean)
     */
    public Flow getFlow(Integer id, boolean loadLazyProperties) {
        if (id == null) {
            if (log.isDebugEnabled())
                log.debug("try to get Flow with null id");
            return null;
        }
        HibernateTemplate template = getHibernateTemplate();
        Flow f = (Flow) template.get(Flow.class, id);
        if (f != null && loadLazyProperties) {
            template.initialize(f.getRules());
            Iterator iter = f.getRules().iterator();
            while (iter.hasNext()) {
                FlowRule flowRule = (FlowRule)iter.next();
                if (flowRule.getRule().getConditions() != null && flowRule.getRule().getConditions().size() > 0) {
                    template.initialize(flowRule.getRule().getConditions());
                }
                if (flowRule.getRule().getConsequences() != null && flowRule.getRule().getConsequences().size() > 0) {
                    template.initialize(flowRule.getRule().getConsequences());
                }
            }
        }
        return f;
    }

    /* (non-Javadoc)
     * @see com.aof.dao.business.rule.FlowDAO#saveFlow(com.aof.model.business.rule.Flow)
     */
    public Flow saveFlow(final Flow f) {
        HibernateTemplate template = getHibernateTemplate();
		template.saveOrUpdate(f);
        template.flush();
		return f;
	}

	/* (non-Javadoc)
	 * @see com.aof.dao.business.rule.FlowDAO#removeFlow(java.lang.Integer)
	 */
	public void removeFlow(Integer id) {
        Flow f = getFlow(id, false);
        HibernateTemplate template = getHibernateTemplate();
        template.delete(f);
        template.flush();
	}

	private final static String SQL_COUNT = "select count(*) from Flow f";

	private final static String SQL = "from Flow f";

	private final static Object[][] QUERY_CONDITIONS = {
			{ FlowQueryCondition.ID_EQ, "f.id = ?", null },
			{ FlowQueryCondition.DESCRIPTION_LIKE, "f.description like ?", new LikeConvert() },
			{ FlowQueryCondition.TYPE_EQ, "f.type = ?", null },
            { FlowQueryCondition.ENABLED_EQ, "f.enabled = ?", null },
            { FlowQueryCondition.SITE_ID_EQ, "f.site.id = ?", null },
	};

	private final static Object[][] QUERY_ORDERS = {
			{ FlowQueryOrder.ID, "f.id" },
			{ FlowQueryOrder.DESCRIPTION, "f.description" },
            { FlowQueryOrder.ENABLED, "f.enabled" },
	};

	/* (non-Javadoc)
	 * @see com.aof.dao.business.rule.FlowDAO#getFlowListCount(java.util.Map)
	 */
	public int getFlowListCount(final Map conditions) {
		return getListCount(conditions, SQL_COUNT, QUERY_CONDITIONS);
	}

	/* (non-Javadoc)
	 * @see com.aof.dao.business.rule.FlowDAO#getFlowList(java.util.Map, int, int, com.aof.model.business.rule.query.FlowQueryOrder, boolean)
	 */
	public List getFlowList(final Map conditions, final int pageNo,	final int pageSize, final FlowQueryOrder order,	final boolean descend) {
		return getList(conditions, pageNo, pageSize, order, descend, SQL, QUERY_CONDITIONS, QUERY_ORDERS);
	}

    /* (non-Javadoc)
     * @see com.aof.dao.business.rule.FlowDAO#getRulesForFlow(java.lang.Integer)
     */
    public List getRulesForFlow(Integer flowId) {
        return getHibernateTemplate().find("from FlowRule fr where fr.flow.id = ? order by fr.seq", flowId, Hibernate.INTEGER);
    }
    
    /* (non-Javadoc)
     * @see com.aof.dao.business.rule.FlowDAO#saveFlowRule(com.aof.model.business.rule.FlowRule)
     */
    public void saveFlowRule(FlowRule fr) {
        getHibernateTemplate().save(fr);
    }

    /* (non-Javadoc)
     * @see com.aof.dao.business.rule.FlowDAO#removeFlowRule(com.aof.model.business.rule.FlowRule)
     */
    public void removeFlowRule(FlowRule fr) {
        getHibernateTemplate().delete(fr);
    }


}
