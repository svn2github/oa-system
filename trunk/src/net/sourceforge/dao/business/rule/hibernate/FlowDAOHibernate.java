/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.dao.business.rule.hibernate;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.hibernate.Hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate.HibernateTemplate;

import net.sourceforge.dao.BaseDAOHibernate;
import net.sourceforge.dao.business.rule.FlowDAO;
import net.sourceforge.dao.convert.LikeConvert;
import net.sourceforge.model.business.rule.Flow;
import net.sourceforge.model.business.rule.FlowRule;
import net.sourceforge.model.business.rule.Rule;
import net.sourceforge.model.business.rule.query.FlowQueryCondition;
import net.sourceforge.model.business.rule.query.FlowQueryOrder;

/**
 * FlowDAOµÄHibernateÊµÏÖ
 * @author nicebean
 * @version 1.0 (2005-11-14)
 */
public class FlowDAOHibernate extends BaseDAOHibernate implements FlowDAO {
	private Log log = LogFactory.getLog(FlowDAOHibernate.class);

    /* (non-Javadoc)
     * @see net.sourceforge.dao.business.rule.FlowDAO#getFlow(java.lang.Integer, boolean)
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
     * @see net.sourceforge.dao.business.rule.FlowDAO#saveFlow(net.sourceforge.model.business.rule.Flow)
     */
    public Flow saveFlow(final Flow f) {
        HibernateTemplate template = getHibernateTemplate();
		template.saveOrUpdate(f);
        template.flush();
		return f;
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.dao.business.rule.FlowDAO#removeFlow(java.lang.Integer)
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
	 * @see net.sourceforge.dao.business.rule.FlowDAO#getFlowListCount(java.util.Map)
	 */
	public int getFlowListCount(final Map conditions) {
		return getListCount(conditions, SQL_COUNT, QUERY_CONDITIONS);
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.dao.business.rule.FlowDAO#getFlowList(java.util.Map, int, int, net.sourceforge.model.business.rule.query.FlowQueryOrder, boolean)
	 */
	public List getFlowList(final Map conditions, final int pageNo,	final int pageSize, final FlowQueryOrder order,	final boolean descend) {
		return getList(conditions, pageNo, pageSize, order, descend, SQL, QUERY_CONDITIONS, QUERY_ORDERS);
	}

    /* (non-Javadoc)
     * @see net.sourceforge.dao.business.rule.FlowDAO#getRulesForFlow(java.lang.Integer)
     */
    public List getRulesForFlow(Integer flowId) {
        return getHibernateTemplate().find("from FlowRule fr where fr.flow.id = ? order by fr.seq", flowId, Hibernate.INTEGER);
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.dao.business.rule.FlowDAO#saveFlowRule(net.sourceforge.model.business.rule.FlowRule)
     */
    public void saveFlowRule(FlowRule fr) {
        getHibernateTemplate().save(fr);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.dao.business.rule.FlowDAO#removeFlowRule(net.sourceforge.model.business.rule.FlowRule)
     */
    public void removeFlowRule(FlowRule fr) {
        getHibernateTemplate().delete(fr);
    }


}
