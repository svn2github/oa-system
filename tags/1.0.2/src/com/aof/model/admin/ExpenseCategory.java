/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

/*
 * Created Fri Sep 23 12:31:33 CST 2005 by MyEclipse Hibernate Tool.
 */
package com.aof.model.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.aof.model.metadata.ExpenseType;

/**
 * A class that represents a row in the 't_exp_cate' table. This class may be
 * customized as it is never re-generated after being created.
 */
public class ExpenseCategory extends AbstractExpenseCategory implements Serializable {
    /**
     * Simple constructor of ExpenseCategory instances.
     */
    public ExpenseCategory() {
        setType(ExpenseType.OTHER);
    }

    /**
     * Constructor of ExpenseCategory instances given a simple primary key.
     * 
     * @param id
     */
    public ExpenseCategory(java.lang.Integer id) {
        super(id);
        setType(ExpenseType.OTHER);
    }

    /* Add customized code below */
    private List enabledExpenseSubCategoryList;

    public List getEnabledExpenseSubCategoryList() {
        return enabledExpenseSubCategoryList;
    }

    public void setEnabledExpenseSubCategoryList(List enabledExpenseSubCategoryList) {
        this.enabledExpenseSubCategoryList = enabledExpenseSubCategoryList;
    }

    public void addEnabledExpenseSubCategory(ExpenseSubCategory esc) {
        if (enabledExpenseSubCategoryList == null)
            enabledExpenseSubCategoryList = new ArrayList();
        enabledExpenseSubCategoryList.add(esc);
    }

}
