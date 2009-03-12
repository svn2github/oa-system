/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

/*
 * Created Fri Sep 23 17:32:45 CST 2005 by MyEclipse Hibernate Tool.
 */ 
package net.sourceforge.model.admin;

import java.io.Serializable;

/**
 * A class that represents a row in the 't_role_function' table. 
 * This class may be customized as it is never re-generated 
 * after being created.
 */
public class RoleFunction
    extends AbstractRoleFunction
    implements Serializable
{
    /**
     * Simple constructor of TRoleFunction instances.
     */
    public RoleFunction()
    {
    }

    /**
     * Constructor of TRoleFunction instances given a composite primary key.
     * @param id
     */
    public RoleFunction(Function function,Role role)
    {
        super(function,role);
    }

    /* Add customized code below */

}
