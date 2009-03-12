/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.service.admin.impl;

import net.sourceforge.dao.admin.GlobalDAO;
import net.sourceforge.model.admin.GlobalParameter;
import net.sourceforge.service.BaseManager;
import net.sourceforge.service.admin.GlobalManager;

public class GlobalManagerImpl extends BaseManager implements GlobalManager {
	private GlobalDAO dao;
	
	public GlobalParameter getParameter()   {
		return dao.getParameter();
	}

	public GlobalParameter saveUser(GlobalParameter para)   {
		return dao.saveUser(para);
	}

	public void setGlobalDAO(GlobalDAO dao) {
		this.dao = dao;
	}

}