/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

/*
 * Created Fri Sep 23 14:47:57 CST 2005 by MyEclipse Hibernate Tool.
 */ 
package net.sourceforge.model.admin;

import java.io.Serializable;

/**
 * A class that represents a row in the 't_item' table. 
 * This class may be customized as it is never re-generated 
 * after being created.
 */
public class SupplierItem
    extends AbstractSupplierItem
    implements Serializable
{
    /**
     * Simple constructor of TItem instances.
     */
    public SupplierItem()
    {
    }

    /**
     * Constructor of TItem instances given a simple primary key.
     * @param id
     */
    public SupplierItem(java.lang.Integer id)
    {
        super(id);
    }

}
