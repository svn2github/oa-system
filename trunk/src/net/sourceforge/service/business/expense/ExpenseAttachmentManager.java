/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.service.business.expense;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import net.sourceforge.model.business.expense.ExpenseAttachment;
import net.sourceforge.model.business.expense.query.ExpenseAttachmentQueryOrder;

/**
 * 定义操作ExpenseAttachment的接口
 * @author ych
 * @version 1.0 (Nov 23, 2005)
 */
public interface ExpenseAttachmentManager {
    /**
     * 从数据库取得指定id的ExpenseAttachment
     * 
     * @param id
     *            ExpenseAttachment的id
     * @return 返回指定的ExpenseAttachment
     * 
     */
    public ExpenseAttachment getExpenseAttachment(Integer id);
    
    /**
     * 从数据库删除指定id的ExpenseAttachment
     * 
     * @param id
     *            ExpenseAttachment的id
     * 
     */
    public void removeExpenseAttachment(Integer id);

    /**
     * 插入指定的ExpenseAttachment对象到数据库
     * 
     * @param supplierContract
     *            要保存的ExpenseAttachment对象
     * @return 保存后的ExpenseAttachment对象
     * 
     */
    public ExpenseAttachment insertExpenseAttachment(ExpenseAttachment expenseAttachment) ;
    
    /**
     * 更新指定的ExpenseAttachment对象到数据库
     * 
     * @param supplierContract
     *            要更新的ExpenseAttachment对象
     * @return 更新后的ExpenseAttachment对象
     * 
     */
    public ExpenseAttachment updateExpenseAttachment(ExpenseAttachment expenseAttachment);

    /**
     * 返回符合查询条件的ExpenseAttachment对象个数
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自ExpenseAttachmentQueryCondition类的预定义常量
     * @return 符合查询条件的ExpenseAttachment对象个数
     * 
     */
    public int getExpenseAttachmentListCount(Map condtions);

    /**
     * 返回符合查询条件的ExpenseAttachment对象列表
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自ExpenseAttachmentQueryCondition类的预定义常量
     * @param pageNo
     *            第几页，以pageSize为页的大小，pageSize为-1时忽略该参数
     * @param pageSize
     *            页的大小，-1表示不分页
     * @param order
     *            排序条件，null表示不排序
     * @param descend
     *            false表示升序，true表示降序
     * @return 符合查询条件的ExpenseAttachment对象列表
     * 
     */
    public List getExpenseAttachmentList(Map condtions, int pageNo, int pageSize, ExpenseAttachmentQueryOrder order, boolean descend);
    
    /**
     * 保存指定的ExpenseAttachment文件内容
     * 
     * @param supplierContract
     *            ExpenseAttachment的id
     * @param inputStream
     *            ExpenseAttachment文件输入流
     * @return 保存后的ExpenseAttachment对象
     */
    public ExpenseAttachment insertExpenseAttachment(ExpenseAttachment expenseAttachment, InputStream inputStream);

    /**
     * 返回指定id的ExpenseAttachment文件输入流
     * 
     * @param id
     *            ExpenseAttachment的id
     * @return 指定id的ExpenseAttachment文件输入流
     */
    public InputStream getExpenseAttachmentContent(Integer id);
    
}
