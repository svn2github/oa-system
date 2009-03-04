/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.service.admin;

import java.util.List;
import java.util.Map;

import com.aof.model.admin.Email;
import com.aof.model.admin.EmailBatch;
import com.aof.model.admin.Site;
import com.aof.model.admin.User;
import com.aof.model.admin.query.EmailQueryOrder;

/**
 * 定义操作Email的接口
 * 
 * @author ych
 * @version 1.0 (Nov 13, 2005)
 */
public interface EmailManager {
    
    public static final String EMAIL_ROLE_APPROVER = "Approver";
    public static final String EMAIL_ROLE_FINANCE = "Finance";
    public static final String EMAIL_ROLE_HOTEL_MAINTAINER = "Hotel Maintainer";
    public static final String EMAIL_ROLE_SUPPLIER_MAINTAINER = "Supplier Maintainer";
    public static final String EMAIL_ROLE_PURCHASER = "Purchaser";
    public static final String EMAIL_ROLE_REQUESTOR = "Requestor";
    public static final String EMAIL_ROLE_CREATOR = "Creator";
    public static final String EMAIL_ROLE_NOTIFIER = "Notifier";
    public static final String EMAIL_ROLE_CITY_MAINTAINER = "City Maintainer";
    public static final String EMAIL_ROLE_USER_MAINTAINER = "User Maintainer";
    public static final String EMAIL_ROLE_INSPECTOR = "Inspector";
    public static final String EMAIL_ROLE_RECEIVER = "Receiver";
    public static final String EMAIL_ROLE_DELEGATE_APPROVER = "Delegate Approver";

    /**
     * 从数据库取得指定id的Email
     * 
     * @param id
     *            Email的id
     * @return 返回指定的Email
     * @
     */
    public Email getEmail(Integer id) ;

    /**
     * 插入指定的Email对象到数据库
     * 
     * @param email
     *            要保存的Email对象
     * @return 保存后的Email对象
     * @
     */
    public Email insertEmail(Email email, String body) ;
    
    /**
     * 插入指定的Email对象到数据库
     * 
     * @param email
     *            要保存的Email对象
     * @return 保存后的Email对象
     * @
     */
    public EmailBatch insertEmailBatch(EmailBatch emailBatch, String body) ;

    /**
     * 更新指定的Email对象到数据库
     * 
     * @param email
     *            要更新的Email对象
     * @param body
     *            要更新的Email对象的body
     * 
     * @return 更新后的Email对象
     * @
     */

    public Email updateEmail(Email email) ;

    /**
     * 返回符合查询条件的Email对象个数
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自EmailQueryCondition类的预定义常量
     * @return 符合查询条件的Email对象个数
     * @
     */
    public int getEmailListCount(Map condtions) ;

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
     * @
     */
    public List getEmailList(Map condtions, int pageNo, int pageSize, EmailQueryOrder order, boolean descend) ;

    /**
     * 将状态为等待发送的邮件发送
     * 
     */
    public void sendEmail();

    /**
     * 插入Email对象到数据库
     * 
     * @param from
     *            Email的from
     * @param to
     *            Email的to
     * @param subject
     *            Email的subject
     * @param body
     *            Email的body
     * @
     */
    public void insertEmail(Site site,String from, String to, String subject, String body) ;       
    
    public void insertEmailBatch(Site site,String from, String to, String body,String batchEmailTemplateName,String refNo, User user);
    
    public void insertEmail(Site site,String to,String templateLocation,Map context) ;
    
    public void insertEmailBatch(Site site,String to,String templateLocation,String refNo,Map context,String batchEmailTemplateName) ;
    
    public void deleteEmailBatch(String refNo);
    
    public void updateEmailBatch(EmailBatch emailBatch);
    
    public EmailBatch findNotSendEmailBatchByRefNo(String refNo);
    
    /**
     * 将批量处理的Email插入数据库
     * 
     */
    public void mailReminder();
    
    public void sendBatchEmail();
}
