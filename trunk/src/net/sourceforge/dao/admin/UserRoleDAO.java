/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.dao.admin;

import java.util.List;
import java.util.Map;

import net.sourceforge.dao.DAO;
import net.sourceforge.model.admin.query.UserQueryOrder;

/**
 * 定义操作UserRole的接口
 * 
 * @author nicebean
 * @version 1.0 (2005-11-12)
 */
public interface UserRoleDAO extends DAO {

    public void fillUserRole(List userList);

    public List getUserRoleList(Map conditions, UserQueryOrder order, boolean isDesc);
}
