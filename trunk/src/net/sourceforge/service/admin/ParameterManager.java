/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.service.admin;


import java.util.List;

import net.sourceforge.model.admin.DataTransferParameter;
import net.sourceforge.model.admin.GlobalParameter;
import net.sourceforge.model.admin.Site;

/**
 * 定义操作GlobalParameter的接口
 * @author ych
 * @version 1.0 (Dec 14, 2005)
 */
public interface ParameterManager {

    /**
     * 从数据库取得GlobalParameter
     * @return 
     */
    public GlobalParameter getGlobalParameter();
        
    /**
     * 更新指定的GlobalParameter对象和GlobalMailReminder列表到数据库
     * @param globalParameter 指定的GlobalParameter对象
     * @param reminderList 指定的GlobalMailReminder对象列表
     * @throws Exception
     */
    public void updateGlobalParameter(GlobalParameter globalParameter,List reminderList);

    /**
     * 从数据库取得GlobalMailReminder列表
     *
     */
    public List getGlobalMailReminderList();

    /**
     * 根据Site从数据库取得DataTransferParameter
     * @param site 指定的Site对象
     * @return
     */
    public DataTransferParameter getDataTransferParameter(Site site);
    
    /**
     * 更新指定的DataTransferParameter对象和SiteMailReminder列表到数据库
     * @param dataTransferParameter 指定的DataTransferParameter对象
     * @param reminderList 指定的SiteMailReminder对象列表
     * @throws Exception
     */
    public void updateSiteParameter(DataTransferParameter dataTransferParameter,List reminderList);
    
    /**
     * 从数据库取得指定Site的SiteMailReminder对象列表
     * @param site 指定的Site对象
     */
    public List getSiteMailReminderList(Site site);

}
