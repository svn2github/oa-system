/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.service.business.expense;

import java.util.List;
import java.util.Map;

import com.aof.model.admin.User;
import com.aof.model.business.expense.Expense;
import com.aof.model.business.expense.query.ExpenseQueryOrder;

/**
 * 定义操作Expense的接口
 * @author ych
 * @version 1.0 (Nov 19, 2005)
 */
public interface ExpenseManager {

    /**
     * 从数据库取得指定id的Expense
     * @param id
     * @return
     */
    public Expense getExpense(String id);

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
     * @param expense
     * @param expenseRowList
     * @param expenseRechargeList
     * @return
     */
    public Expense updateExpense(Expense expense,List expenseRowList,List expenseRechargeList);

    /**
     * 更新指定的Expense对象到数据库
     * @param expense 要更新的Expense对象
     * @param expenseRowList 
     * @param expenseRechargeList
     * @param approveRequestList
     * @param isDraft 是否草稿
     * @param currentUser 当前操作人，用于记录log
     * @return
     */
    public Expense updateExpense(Expense expense,List expenseRowList,List expenseRechargeList,//List approveRequestList,
            boolean isDraft, User currentUser);

    /**
     * 返回符合查询条件的Expense对象个数
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自ExpenseQueryCondition类的预定义常量
     * @return 符合查询条件的Expense对象个数
     */
    public int getExpenseListCount(Map condtions);
    
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
    public List getExpenseList(Map condtions, int pageNo, int pageSize, ExpenseQueryOrder order, boolean descend);
    
    /**
     * 返回指定approveRequestId的Expense
     * @param approveRequestId Expense的approveRequestId
     * @return 指定approveRequestId的Expense
     */
    public Expense getExpenseByApproveRequestId(String approveRequestId);

    /**
     * Expense的最终审核
     * @param expenseId
     *      报销单编号
     * @param ecList
     *      Expense Claim list
     * @param rechargeList     
     * @param currentUser  当前操作人，用于记录log
     */
    //public void finalConfirm(String expenseId, YesNo isRecharge, List ecList,List rechargeList, User currentUser);
    
    /**
     * Expense的最终审核
     * @param expense
     *      报销单
     * @param expenseRowList
     *      报销单明细
     * @param rechargeList
     *      分摊明细
     * @param ecList
     *      Expense Claim list
     * @param currentUser  当前操作人，用于记录log
     */
    public void finalConfirm(Expense expense, List expenseRowList, List rechargeList, List ecList, User currentUser);

    /**
     * Expense的最终审核
     * @param expenseId
     *      报销单编号
     */
    public void finalClaim(String expenseId);

    /**
     * 获得指定expense的ExpenseClaim对象列表
     * @param expense 要获得的ExpenseClaim对象的expense 
     * @return 指定Expense的ExpenseClaim对象列表
     */
    public List getExpenseClaimList(Expense expense);
    
    /**
     * 否决Expense
     * @param expense
     * @param currentUser 当前操作人，用于记录log
     * @param comment 备注，用于发送email
     */
    public void rejectExpense(Expense expense, User currentUser, String comment);
    
    /**
     * 根据Expense获得ExpenseRow和ExpenseCell列表
     * @param expense
     * @param enabledExpenseSubCategoryList
     * @return
     */
    public List getExpenseRowCellList(Expense expense,List enabledExpenseSubCategoryList);
    
    /**
     * 根据Expense获得ExpenseRecharge列表
     * @param expense
     * @return
     */
    public List getExpenseRechargeListByExpense(Expense expense);
    
    /**
     * 从数据库删除指定的Expense对象
     * @param expense
     * @param currentUser 当前操作人，用于记录log
     */
    public void removeExpense(Expense expense, User currentUser);

    /**
     * 撤回报销申请
     * @param expense
     * @param currentUser 当前操作人，用于记录log  
     */
    public void withDrawExpense(Expense expense, User currentUser);

    /**
     * 审批通过报销申请
     * @param expense
     */
    public void approveExpense(Expense expense);
    
    /**
     * 复制Expense
     * @param copyExpense 目标expense
     * @param srcExpense 源expense
     * @return 返回复制的Expense
     * @throws Exception 
     */
    public Expense copyExpense(Expense copyExpense,Expense srcExpense) throws Exception;

    /**
     * Final Confirm 时否决Expense
     * @param ep
     * @param currentUser 当前操作人，用于记录log
     * @param comment 备注，用于发送email
     */
    public void rejectExpenseByFinalConfirm(Expense ep, User currentUser, String comment);
    
    /**
     * Final Claim 时否决Expense
     * @param ep
     * @param currentUser 当前操作人，用于记录log
     * @param comment 备注，用于发送email
     */
    public void rejectExpenseByFinalClaim(Expense ep, User currentUser, String comment);

    public List getExpenseCategoriesAndUserageAmountBySiteId(Integer siteId);

}
