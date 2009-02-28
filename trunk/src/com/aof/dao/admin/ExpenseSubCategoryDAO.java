/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.dao.admin;

import java.util.List;
import java.util.Map;

import com.aof.model.admin.ExpenseSubCategory;
import com.aof.model.admin.query.ExpenseSubCategoryQueryOrder;

/**
 * dao interface for domain model Expense Sub Category
 * 
 * @author shilei
 * @version 1.0 (Nov 15, 2005)
 */
public interface ExpenseSubCategoryDAO {

    /**
     * 从数据库取得指定id的Expense Sub Category
     * 
     * @param id
     *            ExpenseCategory的id
     * @return 返回指定的Expense Category
     */
    public ExpenseSubCategory getExpenseSubCategory(Integer id);

    /**
     * get getExpense Sub Category Count according to conditions
     * 
     * @param conditions
     *            search condition
     * @return list count
     */
    public int getExpenseSubCategoryListCount(Map conditions);

    /**
     * get Expense Sub Category List according to conditions
     * 
     * @param conditions
     *            search condition
     * @param pageNo
     *            start page no(0 based), ignored if -1
     * @param pageSize
     *            page size, ignored if -1
     * @param order
     *            search order
     * @param descend
     *            asc or desc
     * @return Expense Sub Category list
     */
    public List getExpenseSubCategoryList(Map conditions, int pageNo, int pageSize, ExpenseSubCategoryQueryOrder order, boolean descend);

    /**
     * insert Expense Sub Category to database
     * 
     * @param expenseSubCategory
     *            the Expense Category inserted
     * @return the Expense Sub Category inserted
     */
    public ExpenseSubCategory insertExpenseSubCategory(ExpenseSubCategory expenseSubCategory);

    /**
     * update Expense Sub Category to datebase
     * 
     * @param expenseSubCategory
     *            the Expense Sub Category updated
     * @return the Expense Sub Category updated
     */
    public ExpenseSubCategory updateExpenseSubCategory(ExpenseSubCategory expenseSubCategory);

}
