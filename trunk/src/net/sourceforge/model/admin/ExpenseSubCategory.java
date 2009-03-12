/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

/*
 * Created Fri Sep 23 12:31:49 CST 2005 by MyEclipse Hibernate Tool.
 */
package net.sourceforge.model.admin;

import java.io.Serializable;

import net.sourceforge.model.metadata.YesNo;

/**
 * A class that represents a row in the 't_exp_subcate' table. This class may be
 * customized as it is never re-generated after being created.
 */
public class ExpenseSubCategory extends AbstractExpenseSubCategory implements Serializable {
    /**
     * Simple constructor of ExpenseSubCategory instances.
     */
    public ExpenseSubCategory() {
        setIsHotel(YesNo.NO);
    }

    /**
     * Constructor of ExpenseSubCategory instances given a simple primary key.
     * 
     * @param id
     */
    public ExpenseSubCategory(java.lang.Integer id) {
        super(id);
        setIsHotel(YesNo.NO);
    }

    /* Add customized code below */

}
