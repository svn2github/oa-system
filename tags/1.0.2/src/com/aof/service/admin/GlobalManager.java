/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.service.admin;

import com.aof.model.admin.GlobalParameter;

public interface GlobalManager {
    public GlobalParameter getParameter();
	
    public GlobalParameter saveUser(GlobalParameter para);
    
}
