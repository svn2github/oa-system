/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.service.business.rule;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sourceforge.model.business.rule.Rule;
import net.sourceforge.model.business.rule.RuleCondition;
import net.sourceforge.model.business.rule.RuleConsequence;
import net.sourceforge.model.business.rule.query.RuleQueryOrder;
import net.sourceforge.model.metadata.ConditionType;
import net.sourceforge.model.metadata.RuleType;
import net.sourceforge.ruleengine.EngineRule;

/**
 * 定义操作Rule的接口
 * 
 * @author nicebean
 * @version 1.0 (2005-11-14)
 */
public interface RuleManager {

    /**
     * 从数据库取得指定id的Rule对象
     * 
     * @param id
     *            Rule对象的id
     * @return Rule对象
     */
    public Rule getRule(Integer id);

    /**
     * 从数据库取得指定id的Rule对象，并初始化conditions和consequences集合
     * 
     * @param id
     *            Rule对象的id
     * @param loadLazyProperty
     *            true表示初始化conditions和consequences集合，false则不初始化
     * @return Rule对象
     */
    public Rule getRule(Integer id, boolean loadLazyProperty);

    /**
     * 保存Rule对象到数据库
     * 
     * @param r
     *            要保存的Rule对象
     * @return 保存后的Rule对象
     */
    public Rule saveRule(Rule r);

    /**
     * 从数据库删除指定id的Rule对象
     * 
     * @param id
     *            Rule对象的id
     */
    public void removeRule(Integer id);

    /**
     * 返回符合查询条件的Rule对象个数
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自RuleQueryCondition类的预定义常量
     * @return 符合查询条件的Rule对象个数
     */
    public int getRuleListCount(Map conditions);

    /**
     * 返回符合查询条件的Rule对象列表
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自RuleQueryCondition类的预定义常量
     * @param pageNo
     *            第几页，以pageSize为页的大小，pageSize为-1时忽略该参数
     * @param pageSize
     *            页的大小，-1表示不分页
     * @param order
     *            排序条件，null表示不排序
     * @param descend
     *            false表示升序，true表示降序
     * @return 符合查询条件的Rule对象列表
     */
    public List getRuleList(Map conditions, int pageNo, int pageSize, RuleQueryOrder order, boolean descend);

    /**
     * 查询指定id的Rule是否被Flow引用
     * 
     * @param id
     *            Rule对象的id
     * @return true表示指定的Rule被某个Flow引用，false表示没有任何Flow用到了该Rule
     */
    public boolean isRuleInUse(Integer id);

    /**
     * 获取指定的Site和Rule Type的所有状态为可用的Rule对象列表
     * 
     * @param siteId
     *            指定Site的id
     * @param type
     *            指定的Rule Type
     * @return Rule对象的列表
     */
    public List getSiteEnabledRuleList(Integer siteId, RuleType type);

    /**
     * 从数据库获取指定的RuleCondition对象
     * 
     * @param ruleId
     *            要获取的RuleCondition对象的rule属性的id
     * @param ct
     *            要获取的RuleCondition对象的conditionType属性
     * @return 返回指定的RuleCondition对象
     */
    public RuleCondition getRuleCondition(Integer ruleId, ConditionType ct);

    /**
     * 新增RuleCondition对象到数据库
     * 
     * @param rc
     *            要新增的RuleCondition对象
     * @return 保存后的RuleCondition对象
     */
    public RuleCondition saveRuleCondition(RuleCondition rc);

    /**
     * 更新RuleCondition对象到数据库
     * 
     * @param rc
     *            要更新的RuleCondition对象
     * @return 保存后的RuleCondition对象
     */
    public RuleCondition updateRuleCondition(RuleCondition rc);

    /**
     * 从数据库删除RuleCondition对象
     * 
     * @param rc
     *            要删除的RuleCondition对象
     */
    public void removeRuleCondition(RuleCondition rc);

    /**
     * 从数据库获取指定的RuleConsequence对象
     * 
     * @param ruleId
     *            要获取的RuleConsequence对象的rule属性的id
     * @param userId
     *            要获取的RuleConsequence对象的user属性的id
     * @return 返回指定的RuleConsequence对象
     */
    public RuleConsequence getRuleConsequence(Integer ruleId, Integer userId);

    /**
     * 新增RuleConsequence对象到数据库
     * 
     * @param rc
     *            要新增的RuleConsequence对象
     * @return 保存后的RuleConsequence对象
     */
    public RuleConsequence saveRuleConsequence(RuleConsequence rc);

    /**
     * 更新RuleConsequence对象到数据库
     * 
     * @param rc
     *            要更新的RuleConsequence对象
     * @return 保存后的RuleConsequence对象
     */
    public RuleConsequence updateRuleConsequence(RuleConsequence rc);

    /**
     * 从数据库删除RuleConsequence对象
     * 
     * @param rc
     *            要删除的RuleConsequence对象
     */
    public void removeRuleConsequence(RuleConsequence rc);

    /**
     * 查询指定id的Rule对象的consequences集合中最大的seq值
     * 
     * @param ruleId
     *            Rule对象的id
     * @return 如果consequences集合为空则返回null，否则返回最大的seq值
     */
    public Integer getMaxConsequenceSeqNoForRuleId(Integer ruleId);

    /**
     * 根据指定id的Rule的conditions和consequences更新用于Rule Engine的EngineRule
     * 
     * @param ruleId
     *            Rule对象的id
     * @param er
     *            要更新的EngineRule
     */
    public void updateEngineRuleForRule(Integer ruleId, EngineRule er);

    /**
     * 根据指定的Site Id和RuleType，返回所有Rule对象
     * 
     * @param siteId
     *            Site对象的Id
     * @param rt
     *            RuleType
     * @return 符合条件的Rule对象列表
     */
    /**
     * @param siteId
     * @param rt
     * @return
     */
    public List getEnabledRuleListForRuleType(Integer siteId, RuleType rt);

    public void saveRules(List list);
    
    public Rule saveRuleAll(Rule r);
    
    public void removeRules(Set ruleSet);
}
