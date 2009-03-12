/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

/*
 * Created Fri Sep 23 14:49:24 CST 2005 by MyEclipse Hibernate Tool.
 */ 
package net.sourceforge.model.admin;

import java.io.Serializable;

/**
 * A class that represents a row in the 't_pur_subcate' table. 
 * This class may be customized as it is never re-generated 
 * after being created.
 */
public class PurchaseSubCategory
    extends AbstractPurchaseSubCategory
    implements Serializable
{
    /**
     * Simple constructor of TPurSubcate instances.
     */
    public PurchaseSubCategory()
    {
    }

    /**
     * Constructor of TPurSubcate instances given a simple primary key.
     * @param id
     */
    public PurchaseSubCategory(java.lang.Integer id)
    {
        super(id);
    }


}
