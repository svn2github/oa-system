/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.dao.admin;

import net.sourceforge.dao.DAO;
import net.sourceforge.model.admin.GlobalParameter;

public interface GlobalDAO extends DAO {

    public GlobalParameter getParameter();
	
    public GlobalParameter saveUser(GlobalParameter user);

}
