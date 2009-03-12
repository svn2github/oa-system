/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.dao.business.rule;

import java.util.List;
import java.util.Map;

import net.sourceforge.dao.DAO;
import net.sourceforge.model.business.rule.Flow;
import net.sourceforge.model.business.rule.FlowRule;
import net.sourceforge.model.business.rule.query.FlowQueryOrder;

/**
 * 定义操作Flow的接口
 * 
 * @author nicebean
 * @version 1.0 (2005-11-14)
 */
public interface FlowDAO extends DAO {
    /**
     * 从数据库取得指定id的Flow对象，并且初始化它的rules集合
     * 
     * @param id
     *            Flow对象的id
     * @param loadLazyProperty
     *            true表示初始化rules集合，false则不初始化
     * @return Flow对象
     */
    public Flow getFlow(Integer id, boolean loadLazyProperties);

    /**
     * 保存Flow对象到数据库
     * 
     * @param f
     *            要保存的Flow对象
     * @return 保存后的Flow对象
     */
    public Flow saveFlow(Flow f);

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
     * 取得属于指定id的Flow对象的所有FlowRule
     * 
     * @param flowId
     *            Flow对象的id
     * @return FlowRule对象的列表，按照其seq属性排序
     */
    public List getRulesForFlow(Integer flowId);

    /**
     * 新增FlowRule对象到数据库
     * 
     * @param fr
     *            要新增的FlowRule对象
     * @return 保存后的FlowRule对象
     */
    public void saveFlowRule(FlowRule fr);

    /**
     * 从数据库删除指定的FlowRule对象
     * 
     * @param fr
     *            要删除的FlowRule对象
     */
    public void removeFlowRule(FlowRule fr);
}
