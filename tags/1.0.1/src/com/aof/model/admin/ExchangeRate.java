/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

/*
 * Created Fri Sep 23 11:04:42 CST 2005 by MyEclipse Hibernate Tool.
 */
package com.aof.model.admin;

import java.io.Serializable;

/**
 * A class that represents a row in the 'exchange_rate' table. This class may be
 * customized as it is never re-generated after being created.
 */
public class ExchangeRate extends AbstractExchangeRate implements Serializable {
    /**
     * Simple constructor of ExchangeRate instances.
     */
    public ExchangeRate() {
    }

    /**
     * Constructor of ExchangeRate instances given a simple primary key.
     * 
     * @param id
     */
    public ExchangeRate(Integer id) {
        super(id);
    }

}
