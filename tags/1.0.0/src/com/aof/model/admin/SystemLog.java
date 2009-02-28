/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

/*
 * Created Fri Sep 23 13:45:10 CST 2005 by MyEclipse Hibernate Tool.
 */
package com.aof.model.admin;

import java.io.Serializable;

/**
 * A class that represents a row in the 'system_log' table. This class may be
 * customized as it is never re-generated after being created.
 */
public class SystemLog extends AbstractSystemLog implements Serializable {
    /**
     * Simple constructor of SystemLog instances.
     */
    public SystemLog() {
    }

    /**
     * Constructor of SystemLog instances given a simple primary key.
     * 
     * @param id
     */
    public SystemLog(Integer id) {
        super(id);
    }

    /* Add customized code below */

}
