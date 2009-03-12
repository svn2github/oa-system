/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

/*
 * Created Fri Sep 23 13:21:54 CST 2005 by MyEclipse Hibernate Tool.
 */
package net.sourceforge.model.admin;

import java.io.Serializable;

/**
 * A class that represents a row in the 't_global_para' table. This class may be
 * customized as it is never re-generated after being created.
 */
public class GlobalParameter extends AbstractGlobalParameter implements Serializable {
    /**
     * Simple constructor of GlobalParameter instances.
     */
    public GlobalParameter() {
        this.setSmtpAddress("");
        this.setSmtpUsername("");
        this.setSmtpPassword("");
    }

    /**
     * Constructor of GlobalParameter instances given a simple primary key.
     * 
     * @param id
     */
    public GlobalParameter(Integer id) {
        super(id);
    }

    /* Add customized code below */

}
