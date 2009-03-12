/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

/*
 * Created Fri Sep 23 18:06:30 CST 2005 by MyEclipse Hibernate Tool.
 */
package net.sourceforge.model.admin;

import java.io.Serializable;

/**
 * A class that represents a row in the 't_user_dep' table. This class may be
 * customized as it is never re-generated after being created.
 */
public class UserDepartment extends AbstractUserDepartment implements Serializable {
    /**
     * Simple constructor of TUserDep instances.
     */
    public UserDepartment() {
    }

    /**
     * Constructor of UserDepartment instances given a composite primary key.
     * 
     * @param user
     * @param department
     */
    public UserDepartment(User user, Department department) {
        super(user, department);
    }

    /* Add customized code below */

}
