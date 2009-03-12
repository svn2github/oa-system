/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.dao.admin;

import java.util.List;
import java.util.Map;

import net.sourceforge.model.admin.SystemLog;
import net.sourceforge.model.admin.query.SystemLogQueryOrder;

/**
 * 定义操作SystemLog的接口
 * @author nicebean
 * @version 1.0 (2006-02-15)
 */
public interface SystemLogDAO{

    /**
     * 从数据库取得指定id的SystemLog
     * 
     * @param id
     *            SystemLog的id
     * @return 返回指定的SystemLog
     */
    public SystemLog getSystemLog(Integer id);
	
    /**
     * 返回符合查询条件的SystemLog对象个数
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自SystemLogQueryCondition类的预定义常量
     * @return 符合查询条件的SystemLog对象个数
     */
	public int getSystemLogListCount(Map conditions);
	
    /**
     * 返回符合查询条件的SystemLog对象列表
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自SystemLogQueryCondition类的预定义常量
     * @param pageNo
     *            第几页，以pageSize为页的大小，pageSize为-1时忽略该参数
     * @param pageSize
     *            页的大小，-1表示不分页
     * @param order
     *            排序条件，null表示不排序
     * @param descend
     *            false表示升序，true表示降序
     * @return 符合查询条件的SystemLog对象列表
     */
    public List getSystemLogList(Map conditions, int pageNo, int pageSize, SystemLogQueryOrder order, boolean descend);

    /**
     * 插入指定的SystemLog对象到数据库
     * 
     * @param systemLog
     *            要保存的SystemLog对象
     * @return 保存后的SystemLog对象
     */
    public SystemLog insertSystemLog(SystemLog systemLog);
    
}
