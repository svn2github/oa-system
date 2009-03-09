/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

/*
 * Created Fri Sep 23 14:47:57 CST 2005 by MyEclipse Hibernate Tool.
 */
package com.aof.model.business.rule;

import java.io.Serializable;

/**
 * 该类代表rule表的一行记录
 */
public class Rule extends AbstractRule implements Serializable {
    /**
     * Simple constructor of Rule instances.
     */
    public Rule() {
    }

    /**
     * Constructor of Rule instances given a simple primary key.
     * 
     * @param id
     */
    public Rule(Integer id) {
        super(id);
    }

    /* Add customized code below */

}
