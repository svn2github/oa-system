/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.dao.admin;

import com.aof.dao.DAO;
import com.aof.model.admin.GlobalParameter;

public interface GlobalDAO extends DAO {

    public GlobalParameter getParameter();
	
    public GlobalParameter saveUser(GlobalParameter user);

}
