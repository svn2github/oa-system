/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

/*
 * Created Fri Sep 23 10:32:12 CST 2005 by MyEclipse Hibernate Tool.
 */
package com.aof.model.admin;

import java.io.Serializable;

/**
 * A class that represents a row in the 't_cust_code' table. This class may be
 * customized as it is never re-generated after being created.
 */
public class Customer extends AbstractCustomer implements Serializable {
    /**
     * Simple constructor of Customer instances.
     */
    public Customer() {
    }

    /**
     * Constructor of Customer instances given a simple primary key.
     * 
     * @param code
     */
    public Customer(String code) {
        super(code);
    }

    /* Add customized code below */

}
