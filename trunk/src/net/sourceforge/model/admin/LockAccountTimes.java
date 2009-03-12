/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.admin;

import com.shcnc.hibernate.PersistentStringEnum;

public class LockAccountTimes extends PersistentStringEnum {
	public static final LockAccountTimes NEVER = new LockAccountTimes("永不", "00");
	public static final LockAccountTimes THREETIMES = new LockAccountTimes("三次", "01");
	public static final LockAccountTimes FIVETIMES = new LockAccountTimes("五次", "02");
	public static final LockAccountTimes TENTIMES = new LockAccountTimes("十次", "03");

	public LockAccountTimes() {
	}

	private LockAccountTimes(String name, String persistentValue) {
		super(name, persistentValue);
	}
	
	
}