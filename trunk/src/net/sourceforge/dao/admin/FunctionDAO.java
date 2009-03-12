/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.dao.admin;

import java.util.List;
import java.util.Map;

import net.sourceforge.dao.DAO;
import net.sourceforge.model.admin.Function;
import net.sourceforge.model.admin.query.FunctionQueryOrder;

/**
 * 定义操作Function的接口
 * 
 * @author nicebean
 * @version 1.0 (2005-11-11)
 */
public interface FunctionDAO extends DAO {

    /**
     * 从系统配置和数据库取得指定id的Function对象
     * 
     * @param id
     *            Function对象的id
     * @return 返回指定id的Function对象
     */
    public Function getFunction(Integer id);

    /**
     * 保存Function对象到数据库(仅更新name和description)
     * 
     * @param function
     *            要保存的Function对象
     * @return 保存的Function对象
     */
    public Function saveFunction(Function function);

    /**
     * 返回符合查询条件的Function对象个数
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自FunctionQueryCondition类的预定义常量
     * @return 符合查询条件的Function对象个数
     */
    public int getFunctionListCount(Map conditions);

    /**
     * 返回符合查询条件的Function对象列表
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自FunctionQueryCondition类的预定义常量
     * @param pageNo
     *            第几页，以pageSize为页的大小，pageSize为-1时忽略该参数
     * @param pageSize
     *            页的大小，-1表示不分页
     * @param order
     *            排序条件，null表示不排序
     * @param descend
     *            false表示升序，true表示降序
     * @return 符合查询条件的Function对象列表
     */
    public List getFunctionList(Map conditions, int pageNo, int pageSize, FunctionQueryOrder order, boolean descend);

}
