/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.service.admin;

import net.sourceforge.model.admin.GlobalParameter;

public interface GlobalManager {
    public GlobalParameter getParameter();
	
    public GlobalParameter saveUser(GlobalParameter para);
    
}
