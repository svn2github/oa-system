/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.business;

import java.util.Map;

import com.aof.model.admin.Site;


/**
 * 支持通知的对象应该实现该接口
 * 
 * @author nicebean
 * @version 1.0 (2005-12-31)
 */
public interface Notifiable {

    /**
     * 返回用于通知该对象的Flow的名称
     * @return Flow的名称
     */
    public String getNotifyFlowName();

    public String getNotifyEmailTemplateName();

    public Map getNotifyEmailContext();
    
    public Site getLogSite();
    
}
