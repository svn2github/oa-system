/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.dao.business.rule.hibernate;

import java.util.List;
import java.util.Map;

import net.sf.hibernate.Hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate.HibernateTemplate;

import net.sourceforge.dao.BaseDAOHibernate;
import net.sourceforge.dao.business.rule.RuleDAO;
import net.sourceforge.dao.convert.LikeConvert;
import net.sourceforge.dao.convert.QueryParameterConvert;
import net.sourceforge.model.admin.User;
import net.sourceforge.model.business.rule.Rule;
import net.sourceforge.model.business.rule.RuleCondition;
import net.sourceforge.model.business.rule.RuleConsequence;
import net.sourceforge.model.business.rule.query.RuleQueryCondition;
import net.sourceforge.model.business.rule.query.RuleQueryOrder;
import net.sourceforge.model.metadata.ConditionType;
import net.sourceforge.model.metadata.SupplierPromoteStatus;

/**
 * RuleDAOµÄHibernateÊµÏÖ
 * @author nicebean
 * @version 1.0 (2005-11-14)
 */
public class RuleDAOHibernate extends BaseDAOHibernate implements RuleDAO {
	private Log log = LogFactory.getLog(RuleDAOHibernate.class);

    /* (non-Javadoc)
     * @see net.sourceforge.dao.business.rule.RuleDAO#getRule(java.lang.Integer, boolean)
     */
    public Rule getRule(Integer id, boolean loadLazyProperties) {
        if (id == null) {
            if (log.isDebugEnabled())
                log.debug("try to get Rule with null id");
            return null;
        }
        HibernateTemplate template = getHibernateTemplate();
        Rule r = (Rule) template.get(Rule.class, id);
        if (r != null && loadLazyProperties) {
            template.initialize(r.getConditions());
            template.initialize(r.getConsequences());
        }
        return r;
    }

    /* (non-Javadoc)
     * @see net.sourceforge.dao.business.rule.RuleDAO#saveRule(net.sourceforge.model.business.rule.Rule)
     */
    public Rule saveRule(final Rule r) {
        HibernateTemplate template = getHibernateTemplate();
		template.saveOrUpdate(r);
        template.flush();
		return r;
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.dao.business.rule.RuleDAO#removeRule(java.lang.Integer)
	 */
	public void removeRule(Integer id) {
		Rule r = getRule(id, false);
        HibernateTemplate template = getHibernateTemplate();
        template.delete(r);
        template.flush();
	}

	private final static String SQL_COUNT = "select count(*) from Rule r";

	private final static String SQL = "from Rule r";

	private final static Object[][] QUERY_CONDITIONS = {
			{ RuleQueryCondition.ID_EQ, "r.id = ?", null },
			{ RuleQueryCondition.DESCRIPTION_LIKE, "r.description like ?", new LikeConvert() },
			{ RuleQueryCondition.TYPE_EQ, "r.type = ?", null },
            { RuleQueryCondition.ENABLED_EQ, "r.enabled = ?", null },
            { RuleQueryCondition.SITE_ID_EQ, "r.site.id = ?", null },
            { RuleQueryCondition.USERNAME_LIKE, " exists (select rc.rule.id from RuleConsequence rc where rc.rule.id=r.id and (upper(rc.user.name) like ? or upper(rc.superior.name) like ?))",null},
	};

	private final static Object[][] QUERY_ORDERS = {
			{ RuleQueryOrder.ID, "r.id" },
			{ RuleQueryOrder.DESCRIPTION, "r.description" },
            { RuleQueryOrder.ENABLED, "r.enabled" },
	};

	/* (non-Javadoc)
	 * @see net.sourceforge.dao.business.rule.RuleDAO#getRuleListCount(java.util.Map)
	 */
	public int getRuleListCount(final Map conditions) {
		return getListCount(conditions, SQL_COUNT, QUERY_CONDITIONS);
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.dao.business.rule.RuleDAO#getRuleList(java.util.Map, int, int, net.sourceforge.model.business.rule.query.RuleQueryOrder, boolean)
	 */
	public List getRuleList(final Map conditions, final int pageNo,	final int pageSize, final RuleQueryOrder order,	final boolean descend) {
		return getList(conditions, pageNo, pageSize, order, descend, SQL, QUERY_CONDITIONS, QUERY_ORDERS);
	}

    /* (non-Javadoc)
     * @see net.sourceforge.dao.business.rule.RuleDAO#isRuleInUse(java.lang.Integer)
     */
    public boolean isRuleInUse(Integer id) {
        return getHibernateTemplate().iterate("from FlowRule fr where fr.rule.id = ?", id, Hibernate.INTEGER).hasNext();
    }

    /* (non-Javadoc)
     * @see net.sourceforge.dao.business.rule.RuleDAO#getRuleCondition(java.lang.Integer, net.sourceforge.model.metadata.ConditionType)
     */
    public RuleCondition getRuleCondition(Integer ruleId, ConditionType ct) {
        if (ruleId == null) {
            if (log.isDebugEnabled())
                log.debug("try to get RuleCondition with null rule id");
            return null;
        }
        HibernateTemplate template = getHibernateTemplate();
        Rule r = (Rule) template.get(Rule.class, ruleId);
        if (r == null) return null;
        return (RuleCondition) template.get(RuleCondition.class, new RuleCondition(r, ct)); 
    }

    /* (non-Javadoc)
     * @see net.sourceforge.dao.business.rule.RuleDAO#saveRuleCondition(net.sourceforge.model.business.rule.RuleCondition)
     */
    public RuleCondition saveRuleCondition(RuleCondition rc) {
        HibernateTemplate template = getHibernateTemplate();
        template.save(rc);
        template.flush();
        template.evict(rc);
        return getRuleCondition(rc.getRule().getId(), rc.getType());
    }

    /* (non-Javadoc)
     * @see net.sourceforge.dao.business.rule.RuleDAO#updateRuleCondition(net.sourceforge.model.business.rule.RuleCondition)
     */
    public RuleCondition updateRuleCondition(RuleCondition rc) {
        HibernateTemplate template = getHibernateTemplate();
        template.update(rc);
        template.flush();
        template.evict(rc);
        return getRuleCondition(rc.getRule().getId(), rc.getType());
    }

    /* (non-Javadoc)
     * @see net.sourceforge.dao.business.rule.RuleDAO#removeRuleCondition(net.sourceforge.model.business.rule.RuleCondition)
     */
    public void removeRuleCondition(RuleCondition rc) {
        HibernateTemplate template = getHibernateTemplate();
        template.delete(rc);
        template.flush();
    }

    /* (non-Javadoc)
     * @see net.sourceforge.dao.business.rule.RuleDAO#getRuleConsequence(java.lang.Integer, java.lang.Integer)
     */
    public RuleConsequence getRuleConsequence(Integer ruleId, Integer userId) {
        if (ruleId == null) {
            if (log.isDebugEnabled())
                log.debug("try to get RuleConsequence with null rule id");
            return null;
        }
        if (userId == null) {
            if (log.isDebugEnabled())
                log.debug("try to get RuleConsequence with null user id");
            return null;
        }
        HibernateTemplate template = getHibernateTemplate();
        Rule r = (Rule) template.get(Rule.class, ruleId);
        if (r == null) return null;
        User u = (User) template.get(User.class, userId);
        if (u == null) return null;
        return (RuleConsequence) template.get(RuleConsequence.class, new RuleConsequence(r, u)); 
    }

    /* (non-Javadoc)
     * @see net.sourceforge.dao.business.rule.RuleDAO#saveRuleConsequence(net.sourceforge.model.business.rule.RuleConsequence)
     */
    public RuleConsequence saveRuleConsequence(RuleConsequence rc) {
        HibernateTemplate template = getHibernateTemplate();
        template.save(rc);
        template.flush();
        template.evict(rc);
        return getRuleConsequence(rc.getRule().getId(), rc.getUser().getId());
    }

    /* (non-Javadoc)
     * @see net.sourceforge.dao.business.rule.RuleDAO#updateRuleConsequence(net.sourceforge.model.business.rule.RuleConsequence)
     */
    public RuleConsequence updateRuleConsequence(RuleConsequence rc) {
        HibernateTemplate template = getHibernateTemplate();
        template.saveOrUpdate(rc);
        template.flush();
        template.evict(rc);
        return getRuleConsequence(rc.getRule().getId(), rc.getUser().getId());
    }

    /* (non-Javadoc)
     * @see net.sourceforge.dao.business.rule.RuleDAO#removeRuleConsequence(net.sourceforge.model.business.rule.RuleConsequence)
     */
    public void removeRuleConsequence(RuleConsequence rc) {
        HibernateTemplate template = getHibernateTemplate();
        template.delete(rc);
        template.flush();
    }

    /* (non-Javadoc)
     * @see net.sourceforge.dao.business.rule.RuleDAO#getMaxConsequenceSeqNoForRuleId(java.lang.Integer)
     */
    public Integer getMaxConsequenceSeqNoForRuleId(Integer ruleId) {
        return (Integer) getHibernateTemplate().find("select max(rc.seq) from RuleConsequence rc where rc.rule.id = ?", ruleId, Hibernate.INTEGER).get(0);
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.dao.business.rule.RuleDAO#getConditionsForRuleId(java.lang.Integer)
     */
    public List getConditionsForRuleId(Integer ruleId) {
        return getHibernateTemplate().find("from RuleCondition rc where rc.rule.id = ?", ruleId, Hibernate.INTEGER);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.dao.business.rule.RuleDAO#getConsequencesForRuleId(java.lang.Integer)
     */
    public List getConsequencesForRuleId(Integer ruleId) {
        return getHibernateTemplate().find("from RuleConsequence rc where rc.rule.id = ? order by rc.seq", ruleId, Hibernate.INTEGER);
    }

}
