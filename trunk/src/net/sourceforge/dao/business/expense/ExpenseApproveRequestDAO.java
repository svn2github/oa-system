/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.dao.business.expense;

import java.util.List;
import java.util.Map;

import net.sourceforge.dao.business.BaseApproveRequestDAO;
import net.sourceforge.model.admin.User;
import net.sourceforge.model.business.expense.Expense;
import net.sourceforge.model.business.expense.ExpenseApproveRequest;
import net.sourceforge.model.business.expense.query.ExpenseApproveRequestQueryOrder;

/**
 * 定义操作ExpenseApproveRequest的接口
 * @author ych
 * @version 1.0 (Nov 18, 2005)
 */
public interface ExpenseApproveRequestDAO extends BaseApproveRequestDAO {

    /**
     * 从数据库取得指定approveRequestId和approver的ExpenseApproveRequest
     * @param approveRequestId ExpenseApproveRequest的approveRequestId
     * @param approver ExpenseApproveRequest的approver
     * @return 返回指定的ExpenseApproveRequest
     */
    public ExpenseApproveRequest getExpenseApproveRequest(String approveRequestId, User approver);    
	
    /**
     * 从数据库取得指定approveRequestId的ExpenseApproveRequest列表
     * @param approveRequestId ExpenseApproveRequest的approveRequestId
     * @return 返回指定的ExpenseApproveRequest列表
     */
    public List getExpenseApproveRequestListByApproveRequestId(String approveRequestId); 
    
    /**
     * 返回符合查询条件的ExpenseApproveRequest对象个数
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自ExpenseApproveRequestQueryCondition类的预定义常量
     * @return 符合查询条件的ExpenseApproveRequest对象个数
     */
	public int getExpenseApproveRequestListCount(Map conditions);
	
    /**
     * 返回符合查询条件的ExpenseApproveRequest对象列表
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自ExpenseApproveRequestQueryCondition类的预定义常量
     * @param pageNo
     *            第几页，以pageSize为页的大小，pageSize为-1时忽略该参数
     * @param pageSize
     *            页的大小，-1表示不分页
     * @param order
     *            排序条件，null表示不排序
     * @param descend
     *            false表示升序，true表示降序
     * @return 符合查询条件的ExpenseApproveRequest对象列表
     */
    public List getExpenseApproveRequestList(Map conditions, int pageNo, int pageSize, ExpenseApproveRequestQueryOrder order, boolean descend);

    /**
     * 插入指定的ExpenseApproveRequest对象到数据库
     * 
     * @param expenseApproveRequest
     *            要保存的ExpenseApproveRequest对象
     * @return 保存后的ExpenseApproveRequest对象
     */
    public ExpenseApproveRequest insertExpenseApproveRequest(ExpenseApproveRequest expenseApproveRequest);
    
    /**
     * 更新指定的ExpenseApproveRequest对象到数据库
     * 
     * @param expenseApproveRequest
     *            要更新的ExpenseApproveRequest对象
     * @return 更新后的ExpenseApproveRequest对象
     */
    public ExpenseApproveRequest updateExpenseApproveRequest(ExpenseApproveRequest expenseApproveRequest);

    /**
     * 删除指定Expense的ExpenseApproveRequest对象
     * @param expense
     */
    public void deleteExpenseApproveRequestByExpense(Expense expense);
    
}
