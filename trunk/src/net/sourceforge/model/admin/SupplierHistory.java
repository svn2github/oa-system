/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

/*
 * Created Fri Sep 23 15:43:29 CST 2005 by MyEclipse Hibernate Tool.
 */ 
package net.sourceforge.model.admin;

import java.io.Serializable;

/**
 * A class that represents a row in the 't_supplier_history' table. 
 * This class may be customized as it is never re-generated 
 * after being created.
 */
public class SupplierHistory
    extends AbstractSupplierHistory
    implements Serializable
{
    /**
     * Simple constructor of TSupplierHistory instances.
     */
    public SupplierHistory()
    {
    }

    /**
     * Constructor of TSupplierHistory instances given a simple primary key.
     * @param supplier
     */
    public SupplierHistory(Supplier supplier)
    {
        super(supplier);
    }


}
