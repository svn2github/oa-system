/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.dao.business.expense;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.aof.model.admin.ExpenseSubCategory;
import com.aof.model.admin.SiteMailReminder;
import com.aof.model.business.expense.Expense;
import com.aof.model.business.expense.ExpenseApproveRequest;
import com.aof.model.business.expense.ExpenseCell;
import com.aof.model.business.expense.ExpenseClaim;
import com.aof.model.business.expense.ExpenseHistory;
import com.aof.model.business.expense.ExpenseHistoryCell;
import com.aof.model.business.expense.ExpenseHistoryRow;
import com.aof.model.business.expense.ExpenseRecharge;
import com.aof.model.business.expense.ExpenseRow;
import com.aof.model.business.expense.query.ExpenseQueryOrder;

/**
 * 定义操作Expense的接口
 * 
 * @author ych
 * @version 1.0 (Nov 19, 2005)
 */
public interface ExpenseDAO {

    /**
     * 从数据库取得指定id的Expense
     * @param id
     * @return
     */
    public Expense getExpense(String id);

    /**
     * 返回符合查询条件的Expense对象个数
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自ExpenseQueryCondition类的预定义常量
     * @return 符合查询条件的Expense对象个数
     */
    public int getExpenseListCount(Map conditions);

    /**
     * 返回符合查询条件的Expense对象列表
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自ExpenseQueryCondition类的预定义常量
     * @param pageNo
     *            第几页，以pageSize为页的大小，pageSize为-1时忽略该参数
     * @param pageSize
     *            页的大小，-1表示不分页
     * @param order
     *            排序条件，null表示不排序
     * @param descend
     *            false表示升序，true表示降序
     * @return 符合查询条件的Expense对象列表
     */
    public List getExpenseList(Map conditions, int pageNo, int pageSize, ExpenseQueryOrder order, boolean descend);

    /**
     * 插入指定的Expense对象到数据库
     * 
     * @param expense
     *            要保存的Expense对象
     * @return 保存后的Expense对象
     */
    public Expense insertExpense(Expense expense);

    /**
     * 更新指定的Expense对象到数据库
     * 
     * @param expense
     *            要更新的Expense对象
     * @return 更新后的Expense对象
     */
    public Expense updateExpense(Expense expense);

    /**
     * 返回新增Expense时所要的primary key code
     * 
     * @return 新增Expense时所要的primary key code
     */
    public String getLastExpenseCode();


    /**
     * 获得指定expense的ExpenseClaim对象列表
     * @param expense 要获得的ExpenseClaim对象的expense 
     * @return 指定Expense的ExpenseClaim对象列表
     */
    public List getExpenseClaimList(Expense expense);
    
    /**
     * 插入指定的ExpenseClaim对象到数据库
     * 
     * @param ec
     *            要保存的ExpenseClaim对象
     */
    public void insertExpenseClaim(ExpenseClaim ec);


    /**
     * 返回指定approveRequestId的Expense
     * 
     * @param approveRequestId
     *            Expense的approveRequestId
     * @return 指定approveRequestId的Expense
     */
    public Expense getExpenseByApproveRequestId(String approveRequestId);

    /**
     * 保存指定的ExpenseApproveRequest对象到数据库
     * 
     * @param ear
     *            要保存的ExpenseApproveRequest对象
     */
    public void saveExpenseApproveRequst(ExpenseApproveRequest ear);
    
    /**
     * 保存指定的ExpenseRecharge对象到数据库
     * 
     * @param expenseRecharge
     *            要保存的ExpenseRecharge对象
     */
    public void saveExpenseRecharge(ExpenseRecharge expenseRecharge);
    
    /**
     * 根据Expense删除数据库中ExpenseRecharge对象
     * @param expense ExpenseRecharge的Expense
     */
    public void deleteExpenseRechargeByExpense(Expense expense);
    
    /**
     * 根据指定的Expense获得所有ExpenseRecharge列表
     * @param expense ExpenseRecharge的Expense对象
     * @return
     */
    public List getExpenseRechargeListByExpense(Expense expense);
    
    /**
     * 保存指定的ExpenseRow对象到数据库
     * 
     * @param expenseRow
     *            要保存的ExpenseRow对象
     */
    public void saveExpenseRow(ExpenseRow expenseRow);
    
    /**
     * 根据Expense删除数据库中ExpenseRecharge对象
     * @param expense ExpenseRecharge的Expense
     */
    public void deleteExpenseRowByExpense(Expense expense);
    
    /**
     * 根据指定的Expense获得所有ExpenseRow列表
     * @param expense ExpenseRow的Expense对象
     * @return
     */
    public List getExpenseRowListByExpense(Expense expense);
    
    /**
     * 保存指定的ExpenseCell对象到数据库
     * 
     * @param expenseCell
     *            要保存的ExpenseCell对象
     */
    public void saveExpenseCell(ExpenseCell expenseCell);
    
    /**
     * 根据Expense删除数据库中ExpenseRecharge对象
     * @param expense ExpenseRecharge的Expense
     */
    public void deleteExpenseCellByExpense(Expense expense);

    /**
     * 根据指定的Expense获得所有ExpenseCell列表
     * @param expense ExpenseCell的Expense对象
     * @return
     */
    public List getExpenseCellListByExpense(Expense expense);
    
    /**
     * 从数据库中删除Expense对象
     * @param expenseId Expense的Id
     */
    public void deleteExpense(String expenseId);

    /**
     * 保存指定的ExpenseHistory对象到数据库
     * 
     * @param expenseHistory
     *            要保存的ExpenseHistory对象
     */
    public void saveExpenseHistory(ExpenseHistory expenseHistory);
    
    /**
     * 从数据库中删除指定的ExpenseHistory对象
     * @param expenseHistory 指定的ExpenseHistory对象
     */
    public void deleteExpenseHistory(ExpenseHistory expenseHistory);
    
    /**
     * 保存指定的ExpenseHistoryRow对象到数据库
     * 
     * @param expenseHistoryRow
     *            要保存的ExpenseHistoryRow对象
     */
    public void saveExpenseHistoryRow(ExpenseHistoryRow expenseHistoryRow);
    
    /**
     * 从数据库中删除指定的ExpenseHistoryRow对象
     * @param expenseHistoryRow 指定的ExpenseHistoryRow对象
     */
    public void deleteExpenseHistoryRow(ExpenseHistoryRow expenseHistoryRow);

    /**
     * 保存指定的ExpenseHistoryCell对象到数据库
     * 
     * @param expenseHistoryCell
     *            要保存的ExpenseHistoryCell对象
     */
    public void saveExpenseHistoryCell(ExpenseHistoryCell expenseHistoryCell);
    
    /**
     * 从数据库中删除指定的ExpenseHistoryCell对象
     * @param expenseHistoryCell 指定的ExpenseHistoryCell对象
     */
    public void deleteExpenseHistoryCell(ExpenseHistoryCell expenseHistoryCell);
    
    /**
     * 获得以prefix为开始的最大Expense的id
     * @param prefix
     * @return
     */
    public String getMaxExpenseIdBeginWith(String prefix);
    
    /**
     * 根据Expense删除数据库中ExpenseHistory对象
     * @param expense ExpenseHistory的Expense
     */
    public void deleteExpenseHistoryListByExpense(Expense expense);
    
    /**
     * 根据Expense删除数据库中ExpenseHistoryCell对象
     * @param expense ExpenseHistoryCell的Expense
     */
    public void deleteExpenseHistoryCellListByExpense(Expense expense);
    
    /**
     * 根据Expense删除数据库中ExpenseHistoryRow对象
     * @param expense ExpenseHistoryRow的Expense
     */
    public void deleteExpenseHistoryRowListByExpense(Expense expense);

    /**
     * 根据Expense删除数据库中ExpenseClaim对象
     * @param expense ExpenseClaim的Expense
     */
    public void deleteExpenseClaimListByExpense(Expense expense);

    /**
     * getColumnDescription of expense
     * @param ep
     * @param esc
     * @return
     */
    public String getColumnDescription(Expense ep, ExpenseSubCategory esc);

    /**
     * get finance not responded Expense list
     * @param now
     * @param smr
     * @return Expense List
     */
    public List getFinanceNotRespondedExpenseList(Date now, SiteMailReminder smr);
    
    public List getExpenseCategoriesAndUserageAmountBySiteId(Integer siteId);
}
