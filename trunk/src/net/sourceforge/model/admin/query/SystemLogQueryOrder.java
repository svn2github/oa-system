/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.model.admin.query;

import org.apache.commons.lang.enums.Enum;

/**
 * SystemLog查询顺序的枚举类
 * @author nicebean
 * @version 1.0 (2006-02-15)
 */
public class SystemLogQueryOrder extends Enum{

	public static final SystemLogQueryOrder ID = new SystemLogQueryOrder("id");
    public static final SystemLogQueryOrder SITE_NAME = new SystemLogQueryOrder("site_name");
	public static final SystemLogQueryOrder TARGET = new SystemLogQueryOrder("target");
	public static final SystemLogQueryOrder ACTION = new SystemLogQueryOrder("action");
	public static final SystemLogQueryOrder ACTIONTIME = new SystemLogQueryOrder("actionTime");
    public static final SystemLogQueryOrder USER_ID = new SystemLogQueryOrder("user_id");
    public static final SystemLogQueryOrder USER_NAME = new SystemLogQueryOrder("user_name");
    
	protected SystemLogQueryOrder(String value) {
		super(value);
	}
	
	public static SystemLogQueryOrder getEnum(String value) {
        return (SystemLogQueryOrder) getEnum(SystemLogQueryOrder.class, value);
    }

}
