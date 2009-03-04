/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

/*
 * Created Fri Sep 23 17:51:54 CST 2005 by MyEclipse Hibernate Tool.
 */ 
package com.aof.model.admin;

import java.io.Serializable;

/**
 * A class that represents a row in the 't_tra_grp_det' table. 
 * This class may be customized as it is never re-generated 
 * after being created.
 */
public class TravelGroupDetail
    extends AbstractTravelGroupDetail
    implements Serializable
{
    /**
     * Simple constructor of TTraGrpDet instances.
     */
    public TravelGroupDetail()
    {
    }

    /**
     * Constructor of TTraGrpDet instances given a composite primary key.
     * @param id
     */
    public TravelGroupDetail(ExpenseSubCategory expenseSubCategory,TravelGroup travelGroup)
    {
        super(expenseSubCategory,travelGroup);
    }

    /* Add customized code below */

}
