/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

/*
 * Created Fri Sep 23 18:13:55 CST 2005 by MyEclipse Hibernate Tool.
 */
package com.aof.model.admin;

import java.io.Serializable;
import java.util.List;

/**
 * A class that represents a row in the 't_user_site' table. This class may be
 * customized as it is never re-generated after being created.
 */
public class UserSite extends AbstractUserSite implements Serializable {
    /**
     * Simple constructor of UserSite instances.
     */
    public UserSite() {
    }

    /**
     * Constructor of UserSite instances given a composite primary key.
     * 
     * @param user
     * @param site
     */
    public UserSite(User user, Site site) {
        super(user, site);
    }

    /* Add customized code below */

    private List enabledUserDepartmentList;

    /**
     * @return Returns the enabledUserDepartmentList.
     */
    public List getEnabledUserDepartmentList() {
        return enabledUserDepartmentList;
    }

    /**
     * @param enabledUserDepartmentList The enabledUserDepartmentList to set.
     */
    public void setEnabledUserDepartmentList(List enabledUserDepartmentList) {
        this.enabledUserDepartmentList = enabledUserDepartmentList;
    }

        
    
}
