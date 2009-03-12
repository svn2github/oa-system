/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

/*
 * Created Fri Sep 23 15:41:29 CST 2005 by MyEclipse Hibernate Tool.
 */
package net.sourceforge.model.admin;

import java.io.Serializable;

/**
 * A class that represents a row in the 'supplier_contract' table. This class
 * may be customized as it is never re-generated after being created.
 */
public class SupplierContract extends AbstractSupplierContract implements Serializable {
    /**
     * Simple constructor of SupplierContract instances.
     */
    public SupplierContract() {
    }

    /**
     * Constructor of SupplierContract instances given a simple primary key.
     * 
     * @param id
     */
    public SupplierContract(Integer id) {
        super(id);
    }


}
