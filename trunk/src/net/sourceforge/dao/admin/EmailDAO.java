/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.dao.admin;

import java.util.List;
import java.util.Map;

import net.sourceforge.model.admin.Email;
import net.sourceforge.model.admin.EmailBatch;
import net.sourceforge.model.admin.query.EmailQueryOrder;

/**
 * 定义操作Email的接口
 * @author ych
 * @version 1.0 (Nov 13, 2005)
 */
public interface EmailDAO{

    /**
     * 从数据库取得指定id的Email
     * 
     * @param id
     *            Email的id
     * @return 返回指定的Email
     */
    public Email getEmail(Integer id);
    
    public EmailBatch getEmailBatch(Integer id);
    
    public String getEmailBody(Integer id);
    
    public List getAllUnSendEmailBatch();

    public String getEmailBatchBody(Integer id);
	
    /**
     * 返回符合查询条件的Email对象个数
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自EmailQueryCondition类的预定义常量
     * @return 符合查询条件的Email对象个数
     */
	public int getEmailListCount(Map conditions);
	
    /**
     * 返回符合查询条件的Email对象列表
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自EmailQueryCondition类的预定义常量
     * @param pageNo
     *            第几页，以pageSize为页的大小，pageSize为-1时忽略该参数
     * @param pageSize
     *            页的大小，-1表示不分页
     * @param order
     *            排序条件，null表示不排序
     * @param descend
     *            false表示升序，true表示降序
     * @return 符合查询条件的Email对象列表
     */
    public List getEmailList(Map conditions, int pageNo, int pageSize, EmailQueryOrder order, boolean descend);

    /**
     * 插入指定的Email对象到数据库
     * 
     * @param email
     *            要保存的Email对象
     * @return 保存后的Email对象
     */
    public Email insertEmail(Email email);
    
    /**
     * 插入指定的Email对象到数据库
     * 
     * @param email
     *            要保存的Email对象
     * @return 保存后的Email对象
     */
    public EmailBatch insertEmailBatch(EmailBatch emailBatch);
    
    /**
     * 保存Email的body
     * @param id
     *            要保存的Email的id
     * @param body
     */
    public void saveEmailBody(Integer id, String body);
    
    /**
     * 保存Email的body
     * @param id
     *            要保存的Email的id
     * @param body
     */
    public void saveEmailBatchBody(Integer id, String body);
    
    /**
     * 更新指定的Email对象到数据库
     * 
     * @param email
     *            要更新的Email对象
     * @return 更新后的Email对象
     */
    public Email updateEmail(Email email);
    
    public EmailBatch updateEmailBatch(EmailBatch emailBatch);
    
    /**
     * 返回等待发送的Email对象列表
     * @return 等待发送的Email对象列表
     */
    public List getWaitToSendEmailList();
    
    public void deleteEmailBatch(String refNo);
    
    public EmailBatch findNotSendEmailBatchByRefNo(String refNo);
}
