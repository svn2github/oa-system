/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

/*
 * Created Tue Jun 14 10:11:27 CST 2005 by MyEclipse Hibernate Tool.
 */
package net.sourceforge.model.admin;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * A class that represents a row in the 'user' table. This class may
 * be customized as it is never re-generated after being created.
 */
public class User extends AbstractUser implements Serializable {
    /**
     * Simple constructor of User instances.
     */
    public User() {
    }

    /**
     * Constructor of User instances given a simple primary key.
     * 
     * @param id
     */
    public User(Integer id) {
        super(id);
    }
    
    private List userRoleList;

    public List getUserRoleList() {
        return userRoleList;
    }

    public void setUserRoleList(List userRoleList) {
        this.userRoleList = userRoleList;
    }
    
    public int getUserRoleListSize()
    {
        return userRoleList.size();
    }
    
    public UserRole getFirstUserRole()
    {
        if(userRoleList.isEmpty()) return null;
        else return (UserRole) userRoleList.get(0);
    }
    
    public List getRemainUserRoles()
    {
        if(userRoleList.isEmpty()) return Collections.EMPTY_LIST;
        else return userRoleList.subList(1,userRoleList.size());
            
    }
    
    
}