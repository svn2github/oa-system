/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model;

import net.sourceforge.model.admin.Site;

/**
 * 支持日志记录的对象应该实现该接口
 * 
 * @author nicebean
 * @version 1.0 (2006-02-15)
 */
public interface Loggable {
    
    /**
     * 返回对象的Site
     * @return Site对象
     */
    public Site getLogSite();
    
    /**
     * 返回日志中表示该对象的名称
     * @return
     */
    public String getLogTargetName();

    /**
     * 返回对象在日志的表示的id
     * @return
     */
    public String getLogTargetId();
    
    /**
     * 返回指定action对应的Key Field信息，如果action不被支持，返回null
     * @param action
     * @return
     */
    public String[][] getLogFieldInfo(final String action);
    
}
