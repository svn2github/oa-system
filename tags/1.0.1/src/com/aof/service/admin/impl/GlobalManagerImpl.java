/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.service.admin.impl;

import com.aof.dao.admin.GlobalDAO;
import com.aof.model.admin.GlobalParameter;
import com.aof.service.BaseManager;
import com.aof.service.admin.GlobalManager;

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