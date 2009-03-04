/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.dao.admin.hibernate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.aof.dao.BaseDAOHibernate;
import com.aof.dao.admin.UserRoleDAO;
import com.aof.dao.convert.LikeConvert;
import com.aof.dao.convert.QueryParameterConvert;
import com.aof.model.admin.User;
import com.aof.model.admin.UserRole;
import com.aof.model.admin.query.UserQueryCondition;
import com.aof.model.admin.query.UserQueryOrder;
import com.shcnc.utils.BeanHelper;

/**
 * UserDAOµÄHibernateÊµÏÖ
 * 
 * @author nicebean
 * @version 1.0 (2005-11-12)
 */
public class UserRoleDAOHibernate extends BaseDAOHibernate implements UserRoleDAO {

    private final static String SQL = "select u,ur from UserRole ur right join ur.user u";

    private final static Object[][] QUERY_CONDITIONS = {
            { UserQueryCondition.LOGINNAME_LIKE, "u.loginName like ?", new LikeConvert() },
            { UserQueryCondition.NAME_LIKE, "u.name like ?", new LikeConvert() },
            { UserQueryCondition.ROLE_ID_EQ, "exists (select ur2.user.id from UserRole ur2 where ur2.user.id = u.id and ur2.role.id = ?)", null },
            { UserQueryCondition.PRIMARY_OR_SITE_ID_EQ,
                "(u.primarySite.id = ? or "+
                "exists (select us.user.id from UserSite us where us.user.id = u.id and us.site.id = ?))", 
                new QueryParameterConvert() {

                    public Object convert(Object src) {
                        return new Object[] { src, src };
                    }
            } 
            },
            { UserQueryCondition.DEPARTMENT_ID_EQ,
                "exists (select ud.user.id from UserDepartment ud where ud.user.id = u.id and ud.department.id = ?)", null },
            { UserQueryCondition.ENABLED_EQ, "u.enabled = ?", null },
    };

    private final static Object[][] QUERY_ORDERS = { 
            { UserQueryOrder.NAME, "u.name" },
    };


    public List getUserRoleList(final Map conditions, final UserQueryOrder order, final boolean descend) {
        List list= getList(conditions, 0,-1, order, descend, SQL, QUERY_CONDITIONS, QUERY_ORDERS);
        Set result=new HashSet();
        for (Iterator iter = list.iterator(); iter.hasNext();) {
            Object[] row = (Object[]) iter.next();
            User user=(User) row[0];
            UserRole userRole=(UserRole) row[1];
            if(user.getUserRoleList()==null) user.setUserRoleList(new ArrayList());
            if(userRole!=null)
                user.getUserRoleList().add(userRole);
            result.add(user);
        }
        if(descend)
            return BeanHelper.sortToList(result,"name desc");
        else 
            return BeanHelper.sortToList(result,"name asc");
    }


    public void fillUserRole(List userList) {
        if(userList.isEmpty()) return;
        String sql="select ur from UserRole ur where ur.user.id in("+userIdList(userList)+")";
        List userRoleList=this.getHibernateTemplate().find(sql);
        Map urMap=new HashMap();
        for (Iterator iter = userRoleList.iterator(); iter.hasNext();) {
            UserRole ur = (UserRole) iter.next();
            if(urMap.get(ur.getUser())==null)
            {
                urMap.put(ur.getUser(),new ArrayList());
            }
            List urList=(List) urMap.get(ur.getUser());
            urList.add(ur);
        }
        for (Iterator iter = userList.iterator(); iter.hasNext();) {
            User user = (User) iter.next();
            List urList=(List) urMap.get(user);
            if(urList==null) user.setUserRoleList(Collections.EMPTY_LIST);
            else user.setUserRoleList(urList);
        }
    }


    private String userIdList(List userList) {
        StringBuffer sb=new StringBuffer();
        for (Iterator iter = userList.iterator(); iter.hasNext();) {
            User user = (User) iter.next();
            sb.append(user.getId());
            sb.append(',');
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }



}
