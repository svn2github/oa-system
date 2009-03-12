/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.dao.admin;

import java.util.List;
import java.util.Map;

import net.sourceforge.model.admin.ExpenseCategory;
import net.sourceforge.model.admin.query.ExpenseCategoryQueryOrder;

/**
 * dao interface for domain model Expense Category
 * 
 * @author shilei
 * @version 1.0 (Nov 15, 2005)
 */
public interface ExpenseCategoryDAO {

    /**
     * 从数据库取得指定id的Expense Category
     * 
     * @param id
     *            ExpenseCategory的id
     * @return 返回指定的Expense Category
     */
    public ExpenseCategory getExpenseCategory(Integer id);

    /**
     * get getExpense Category Count according to conditions
     * 
     * @param conditions
     *            search condition
     * @return list count
     */
    public int getExpenseCategoryListCount(Map conditions);

    /**
     * get Expense Category List according to conditions
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
     * @return Expense Category list
     */
    public List getExpenseCategoryList(Map conditions, int pageNo, int pageSize, ExpenseCategoryQueryOrder order, boolean descend);

    /**
     * insert Expense Category to database
     * 
     * @param expenseCategory
     *            Expense Category inserted
     * @return Expense Category inserted
     */
    public ExpenseCategory insertExpenseCategory(ExpenseCategory expenseCategory);

    /**
     * update Expense Category to datebase
     * 
     * @param expenseCategory
     *            Expense Category updated
     * @return Expense Category updated
     */
    public ExpenseCategory updateExpenseCategory(ExpenseCategory expenseCategory);

}
