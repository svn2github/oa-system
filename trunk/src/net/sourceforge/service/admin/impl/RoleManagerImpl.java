/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.service.admin.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sourceforge.dao.admin.RoleDAO;
import net.sourceforge.model.admin.Function;
import net.sourceforge.model.admin.Role;
import net.sourceforge.model.admin.RoleFunction;
import net.sourceforge.model.admin.query.RoleQueryOrder;
import net.sourceforge.service.BaseManager;
import net.sourceforge.service.admin.RoleManager;

/**
 * RoleManager的实现
 * @author nicebean
 * @version 1.0 (2005-11-14)
 */
public class RoleManagerImpl extends BaseManager implements RoleManager {

    private RoleDAO dao;

    /**
     * 设置RoleDAO给属性dao
     * @param dao RoleDAO对象
     */
    public void setRoleDAO(RoleDAO dao) {
        this.dao = dao;
    }

    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.RoleManager#getRole(java.lang.Integer)
     */
    public Role getRole(Integer id) throws Exception {
        return dao.getRole(id);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.RoleManager#saveRole(net.sourceforge.model.admin.Role)
     */
    public Role saveRole(Role role) throws Exception {
        return dao.saveRole(role);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.RoleManager#removeRole(java.lang.Integer)
     */
    public void removeRole(Integer id) throws Exception {
        dao.removeRole(id);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.RoleManager#getRoleListCount(java.util.Map)
     */
    public int getRoleListCount(Map conditions) throws Exception {
        return dao.getRoleListCount(conditions);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.RoleManager#getRoleList(java.util.Map, int, int, net.sourceforge.model.admin.query.RoleQueryOrder, boolean)
     */
    public List getRoleList(Map conditions, int pageNo, int pageSize, RoleQueryOrder order, boolean descend) throws Exception {
        return dao.getRoleList(conditions, pageNo, pageSize, order, descend);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.RoleManager#getAllRoleList()
     */
    public List getAllRoleList() throws Exception {
        return dao.getRoleList(null, 0, -1, RoleQueryOrder.NAME, false);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.RoleManager#getFunctionListByRole(net.sourceforge.model.admin.Role)
     */
    public List getFunctionListByRole(Role role) throws Exception {
        return dao.getFunctionListByRole(role);
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.RoleManager#saveFunctionListForRole(net.sourceforge.model.admin.Role, java.util.List)
     */
    public void saveFunctionListForRole(Role role, List functionList) throws Exception {
        Iterator itor = dao.getFunctionListByRole(role).iterator();
        while (itor.hasNext()) {
            Function f = (Function) itor.next();
            int i = functionList.indexOf(f);
            if (i == -1) {
                RoleFunction rf = new RoleFunction();
                rf.setRole(role);
                rf.setFunction(f);
                dao.removeRoleFunction(rf);
            } else {
                functionList.remove(i);
            }
        }
        itor = functionList.iterator();
        while (itor.hasNext()) {
            RoleFunction rf = new RoleFunction();
            rf = new RoleFunction();
            rf.setRole(role);
            rf.setFunction((Function) itor.next());
            dao.saveRoleFunction(rf);
        }
    }

}
