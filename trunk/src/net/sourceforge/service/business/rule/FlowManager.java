/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.service.business.rule;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sourceforge.model.business.Approvable;
import net.sourceforge.model.business.Controllable;
import net.sourceforge.model.business.Notifiable;
import net.sourceforge.model.business.Purchasable;
import net.sourceforge.model.business.rule.Flow;
import net.sourceforge.model.business.rule.FlowRule;
import net.sourceforge.model.business.rule.query.FlowQueryOrder;
import net.sourceforge.model.metadata.RuleType;
import net.sourceforge.ruleengine.EngineRule;

/**
 * 定义操作Flow的接口
 * 
 * @author nicebean
 * @version 1.0 (2005-11-10)
 */
public interface FlowManager {
    /**
     * 从数据库取得指定id的Flow对象
     * 
     * @param id
     *            Flow对象的id
     * @return Flow对象
     */
    public Flow getFlow(Integer id);

    /**
     * 从数据库取得指定id的Flow对象，并且初始化它的rules集合
     * 
     * @param id
     *            Flow对象的id
     * @param loadLazyProperty
     *            true表示初始化rules集合，false则不初始化
     * @return Flow对象
     */
    public Flow getFlow(Integer id, boolean loadLazyProperty);

    /**
     * 保存Flow对象到数据库
     * 
     * @param f
     *            要保存的Flow对象
     * @return 包含Flow对象的数组。
     *         可能含有1到2个元素，其中第一个元素是保存到数据库的Flow对象，第二个元素是另一个受影响的Flow对象。
     *         比如保存一个状态是ENABLED的Flow对象时，会将数据库中已有的另一个状态为ENABLED的Flow变为DISABLED。
     *         如果没有影响其它Flow对象，数组将只包含一个元素。
     */
    public Flow[] saveFlow(Flow f);

    /**
     * 保存Flow对象和相关的FlowRule对象集合到数据库
     * 
     * @param f
     *            要保存的Flow对象
     * @param flowRules
     *            要保存的FlowRule对象集合，该参数为null时等价于saveFlow(Flow f)
     * @return 包含Flow对象的数组。
     *         可能含有1到2个元素，其中第一个元素是保存到数据库的Flow对象，第二个元素是另一个受影响的Flow对象。
     *         比如保存一个状态是ENABLED的Flow对象时，会将数据库中已有的另一个状态为ENABLED的Flow变为DISABLED。
     *         如果没有影响其它Flow对象，数组将只包含一个元素。
     */
    public Flow[] saveFlow(Flow f, Set flowRules);

    /**
     * 从数据库删除指定id的Flow对象
     * 
     * @param id
     *            Flow对象的id
     */
    public void removeFlow(Integer id);

    /**
     * 返回符合查询条件的Flow对象个数
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自FlowQueryCondition类的预定义常量
     * @return 符合查询条件的Flow对象个数
     */
    public int getFlowListCount(Map conditions);

    /**
     * 返回符合查询条件的Flow对象列表
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自FlowQueryCondition类的预定义常量
     * @param pageNo
     *            第几页，以pageSize为页的大小，pageSize为-1时忽略该参数
     * @param pageSize
     *            页的大小，-1表示不分页
     * @param order
     *            排序条件，null表示不排序
     * @param descend
     *            false表示升序，true表示降序
     * @return 符合查询条件的Flow对象列表
     */
    public List getFlowList(Map conditions, int pageNo, int pageSize, FlowQueryOrder order, boolean descend);

    /**
     * 返回指定Site和类别且状态为可用的Flow对象
     * 
     * @param siteId
     *            指定Site的id
     * @param type
     *            Flow类别
     * @return Flow对象，如果没有符合条件的，返回null
     */
    public Flow getSiteEnabledFlow(Integer siteId, RuleType type);

    /**
     * 检查flowRules中包含的规则序列是否正确
     * 
     * @param flowRules
     *            包含FlowRule对象的集合, 该集合的Iterator必须是有序的。
     * @return -1: 不正确，发现环路 -2:
     *         不正确，集合中存在非法的Seq值，存在对象的nextSeqPass或nextSeqFail值大于等于集合的size -3:
     *         不正确，集合是空的 0: 正确，检查通过
     */
    public int validateRules(Collection flowRules);

    /**
     * 为FlowRule创建用于Rule Engine的EngineRule对象
     * 
     * @param fr
     *            FlowRule对象
     * @return 创建的EngineRule对象
     */
    public EngineRule createEngineRuleForFlowRule(FlowRule fr);

    /**
     * 为指定的site和Flow类别构造EngineFlow。
     * 该方法使指定的Flow更新到最新状态，根据数据库的最新数据即可能创建新的EngineFlow也可能删除已有的EngineFlow
     * 
     * @param siteId
     *            Site对象的Id
     * @param rt
     *            Flow类别
     */
    public void constructEngineFlow(Integer siteId, RuleType rt);

    /**
     * 为给定的对象执行审批Flow并返回相应的结果
     * 
     * @param target
     *            提交给Flow中Rule比较的目标，目标应该实现了Approvable接口
     * @return 根据target的不同，返回相应的ApproveRequest对象列表
     * @throws ExecuteFlowEmptyResultException
     *             执行结果为空时抛出此异常
     * @throws NoAvailableFlowToExecuteException
     *             没有可以执行的Flow时抛出此异常
     */
    public List executeApproveFlow(Approvable target) throws ExecuteFlowEmptyResultException, NoAvailableFlowToExecuteException;

    /**
     * 为给定的对象执行采购Flow并返回相应的结果
     * 
     * @param target
     *            提交给Flow中Rule比较的目标，目标应该实现了Controllable接口
     * @return 根据target的不同，返回相应的Purchaser对象列表
     * @throws ExecuteFlowEmptyResultException
     *             执行结果为空时抛出此异常
     * @throws NoAvailableFlowToExecuteException
     *             没有可以执行的Flow时抛出此异常
     */
    public List executePurchaseFlow(Purchasable target) throws ExecuteFlowEmptyResultException, NoAvailableFlowToExecuteException;

    /**
     * 为给定的对象执行审核Flow并返回相应的结果
     * 
     * @param target
     *            提交给Flow中Rule比较的目标，目标应该实现了Controllable接口
     * @return true表示审核通过，false表示不通过
     * @throws ExecuteFlowEmptyResultException
     *             执行结果为空时抛出此异常
     * @throws NoAvailableFlowToExecuteException
     *             没有可以执行的Flow时抛出此异常
     */
    public boolean executeControlFlow(Controllable target) throws NoAvailableFlowToExecuteException;

    /**
     * 为给定的对象执行通知Flow
     * 
     * @param target
     *            提交给Flow中Rule比较的目标，目标应该实现了Notifiable接口
     */
    public void executeNotifyFlow(Notifiable target);
    
    public void saveFlowRules(Set flowRuleSet);
}
