/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.model.admin.query;

import org.apache.commons.lang.enums.Enum;

/**
 * Email查询顺序的枚举类
 * @author ych
 * @version 1.0 (Nov 13, 2005)
 */
public class EmailQueryOrder extends Enum{

	public static final EmailQueryOrder ID = new EmailQueryOrder("id");
	public static final EmailQueryOrder MAILFROM = new EmailQueryOrder("mailFrom");
	public static final EmailQueryOrder MAILTO = new EmailQueryOrder("mailTo");
	public static final EmailQueryOrder SUBJECT = new EmailQueryOrder("subject");
	public static final EmailQueryOrder BODY = new EmailQueryOrder("body");
	public static final EmailQueryOrder CREATETIME = new EmailQueryOrder("createTime");
	public static final EmailQueryOrder SENTTIME = new EmailQueryOrder("sentTime");
	public static final EmailQueryOrder FAILCOUNT = new EmailQueryOrder("failCount");
	public static final EmailQueryOrder WAITTOSEND = new EmailQueryOrder("status");
    
	protected EmailQueryOrder(String value) {
		super(value);
	}
	
	public static EmailQueryOrder getEnum(String value) {
        return (EmailQueryOrder) getEnum(EmailQueryOrder.class, value);
    }

}
