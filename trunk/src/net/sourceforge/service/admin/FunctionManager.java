/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.service.admin;

import java.util.List;
import java.util.Map;

import org.apache.struts.config.ModuleConfig;

import net.sourceforge.model.admin.Function;
import net.sourceforge.model.admin.query.FunctionQueryOrder;

/**
 * 定义操作Function的接口
 * 
 * @author nicebean
 * @version 1.0 (2005-11-14)
 */
public interface FunctionManager {

    /**
     * 检查FunctionManager是否已经完成初始化
     * 
     * @return true表示已经完成，false表示尚未初始化
     */
    public boolean isInitiated();

    /**
     * 初始化FunctionManager。读取struts的配置信息，并从数据库读入Function的描述信息
     * 
     * @param config
     *            struts的ModuleConfig对象
     * 
     */
    public void initiate(ModuleConfig config) ;

    /**
     * 返回指定类型的Function对象
     * 
     * @param functionType
     *            Function类型，在struts-config中定义，每种类型只能赋予一个ActionMapping
     * @return Function对象
     * 
     */
    public Function getFunction(String functionType) ;

    /**
     * 返回指定id的Function对象
     * 
     * @param id
     *            Function对象的id
     * @return Function对象
     * 
     */
    public Function getFunction(Integer id) ;

    /**
     * 保存Function对象到数据库，仅description会保存
     * 
     * @param function
     *            Function对象
     * @return 保存后的Function对象
     * 
     */
    public Function saveFunction(Function function) ;

    /**
     * 返回符合查询条件的Function对象个数
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自FunctionQueryCondition类的预定义常量
     * @return 符合查询条件的Function对象个数
     * 
     */
    public int getFunctionListCount(Map condtions) ;

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
     * 
     */
    public List getFunctionList(Map condtions, int pageNo, int pageSize, FunctionQueryOrder order, boolean descend) ;

    /**
     * 返回所有的Function对象
     * 
     * @return Function对象列表
     * 
     */
    public List getAllFunction() ;

}
