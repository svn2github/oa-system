/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.admin;

import com.shcnc.hibernate.PersistentStringEnum;

public class ExpiresTime extends PersistentStringEnum {
	public static final ExpiresTime NEVER = new ExpiresTime("永不", "00");
	public static final ExpiresTime TEND = new ExpiresTime("十天", "01");
	public static final ExpiresTime ONEM = new ExpiresTime("一个月", "02");
	public static final ExpiresTime TWOM = new ExpiresTime("两个月", "03");
	public static final ExpiresTime THREEM = new ExpiresTime("三个月", "04");
	public static final ExpiresTime HALFY = new ExpiresTime("半年", "05");
	public static final ExpiresTime ONEY = new ExpiresTime("一年", "06");
	public ExpiresTime() {
	}

	private ExpiresTime(String name, String persistentValue) {
		super(name, persistentValue);
	}
	
	
}