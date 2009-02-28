/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.dao.admin;

import java.util.List;
import java.util.Map;

import com.aof.dao.DAO;
import com.aof.model.admin.query.UserQueryOrder;

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
