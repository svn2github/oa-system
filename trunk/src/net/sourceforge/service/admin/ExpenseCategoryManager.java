/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.service.admin;

import java.util.List;
import java.util.Map;

import net.sourceforge.model.admin.ExpenseCategory;
import net.sourceforge.model.admin.Site;
import net.sourceforge.model.admin.query.ExpenseCategoryQueryOrder;

/**
 * sevice manager interface for domain model Expense Category
 * 
 * @author shilei
 * @version 1.0 (Nov 15, 2005)
 */
public interface ExpenseCategoryManager {

    /**
     * 从数据库取得指定id的Expense Category
     * 
     * @param id
     *            ExpenseCategory的id
     * @return 返回指定的Expense Category
     */
    public ExpenseCategory getExpenseCategory(Integer id);

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

    /**
     * get getExpense Category Count according to conditions
     * 
     * @param conditions
     *            search condition
     * @return list count
     */
    public int getExpenseCategoryListCount(Map condtions);

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
    public List getExpenseCategoryList(Map condtions, int pageNo, int pageSize, ExpenseCategoryQueryOrder order, boolean descend);

    /**
     * get Enabled TravelExpenseCategory List Of Site
     * 
     * @param siteId
     *            the id of site
     * @return Enabled TravelExpenseCategory List
     */
    public ExpenseCategory getEnabledTravelExpenseCategoryOfSite(int siteId);
    
    /**
     * get Enabled ExpenseCategory List Of Site without type Travel
     * 
     * @param siteId
     *            the id of site
     * @return Enabled ExpenseCategory List without type Travel
     */
    public List getEnabledNotTravelExpenseCategoryListOfSite(int site_id);

    /**
     * get Enabled Expense Category and Expense SubCategory Of Site
     * 在ExpenseCategory的enabledExpenseSubCategoryList属性中填充有效的Expense SubCategory
     * 
     * @param site
     * @return Expense Category List
     */
    public List getEnabledExpenseCategorySubCategoryOfSite(Site site);

    /**
     * get Enabled Expense Category Of Site
     * 
     * @param site
     * @return Expense Category List
     */
    public List getEnabledExpenseCategoryOfSite(Site s);
}
