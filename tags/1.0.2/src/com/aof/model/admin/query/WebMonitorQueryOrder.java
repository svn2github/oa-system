/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.admin.query;

import org.apache.commons.lang.enums.Enum;

/**
 * WebMonitor查询顺序的枚举类
 * @author ych
 * @version 1.0 (Nov 13, 2005)
 */
public class WebMonitorQueryOrder extends Enum{

	public static final WebMonitorQueryOrder SITE = new WebMonitorQueryOrder("site");
    public static final WebMonitorQueryOrder USER_ID = new WebMonitorQueryOrder("user_id");
    public static final WebMonitorQueryOrder USER_NAME = new WebMonitorQueryOrder("user_name");
    public static final WebMonitorQueryOrder IP = new WebMonitorQueryOrder("ip");
    public static final WebMonitorQueryOrder LOGIN_TIME = new WebMonitorQueryOrder("login_time");
    public static final WebMonitorQueryOrder ACCESS_TIME = new WebMonitorQueryOrder("access_time");
    public static final WebMonitorQueryOrder LIVE_TIME = new WebMonitorQueryOrder("live_time");

    
	
	protected WebMonitorQueryOrder(String value) {
		super(value);
	}
	
	public static WebMonitorQueryOrder getEnum(String value) {
        return (WebMonitorQueryOrder) getEnum(WebMonitorQueryOrder.class, value);
    }

}
