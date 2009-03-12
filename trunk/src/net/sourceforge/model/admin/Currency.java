/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

/*
 * Created Thu Sep 22 15:57:27 CST 2005 by MyEclipse Hibernate Tool.
 */ 
package net.sourceforge.model.admin;

import java.io.Serializable;

/**
 * A class that represents a row in the 't_currency' table. 
 * This class may be customized as it is never re-generated 
 * after being created.
 */
public class Currency
    extends AbstractCurrency
    implements Serializable
{
    /**
     * Simple constructor of TCurrency instances.
     */
    public Currency()
    {
    }

    /**
     * Constructor of TCurrency instances given a simple primary key.
     * @param code
     */
    public Currency(java.lang.String code)
    {
        super(code);
    }


}
