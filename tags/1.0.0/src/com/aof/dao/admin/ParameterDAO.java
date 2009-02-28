/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.dao.admin;

import java.util.List;
import java.util.Set;

import com.aof.model.admin.DataTransferParameter;
import com.aof.model.admin.GlobalMailReminder;
import com.aof.model.admin.GlobalParameter;
import com.aof.model.admin.Site;
import com.aof.model.metadata.GlobalMailReminderType;
/**
 * 定义操作Global和Site的Parameter的接口
 * @author ych
 * @version 1.0 (Dec 14, 2005)
 */
public interface ParameterDAO{

    /**
     * 从数据库取得GlobalParameter
     * @return 
     */
    public GlobalParameter getGlobalParameter();
	
    /**
     * 更新指定的GlobalParameter到数据库
     * @param globalParameter
     * @return
     */
    public GlobalParameter updateGlobalParameter(GlobalParameter globalParameter);

    /**
     * 从数据库取得GlobalMailReminder列表
     *
     */
    public List getGlobalMailReminderList();
    
    /**
     * 更新GlobalMailReminder对象列表到数据库
     * @param reminderList GlobalMailReminder对象列表
     */
    public void updateGlobalMailReminder(List reminderList);
    
    /**
     * 从数据库取得指定Site的DataTransferParameter
     * @param site 指定的Site
     * @return
     */
    public DataTransferParameter getDataTransferParameter(Site site);
    
    /**
     * 更新指定的DataTransferParameter到数据库
     * @param dataTransferParameter
     * @return
     */
    public DataTransferParameter updateDataTransferParameter(DataTransferParameter dataTransferParameter);
    
    /**
     * 更新SiteMailReminder对象列表到数据库
     * @param site 指定的site
     * @param reminderList SiteMailReminder对象列表
     */
    public void updateSiteMailReminder(Site site,List reminderList);

    /**
     * 从数据库取得指定Site的SiteMailReminder列表
     * @param site 指定的Site
     * @return
     */
    public List getSiteMailReminderList(Site site);
    
    public Set getEnabledSiteSetWithMailReminders();
    
    /**
     * 获取指定类型的GlobalMailReminder
     * @param type
     * @return
     */
    public GlobalMailReminder getGlobalMailReminder(GlobalMailReminderType type);
}
