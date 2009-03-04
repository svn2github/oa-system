/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.service.business.expense.impl;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.aof.dao.business.expense.ExpenseAttachmentDAO;

import com.aof.model.business.expense.ExpenseAttachment;
import com.aof.model.business.expense.query.ExpenseAttachmentQueryOrder;
import com.aof.service.BaseManager;
import com.aof.service.business.expense.ExpenseAttachmentManager;

/**
 * ExpenseAttachmentManagerµÄÊµÏÖ
 * @author ych
 * @version 1.0 (Nov 23, 2005)
 */
public class ExpenseAttachmentManagerImpl extends BaseManager implements ExpenseAttachmentManager {
   
    private ExpenseAttachmentDAO dao;

    public void setExpenseAttachmentDAO(ExpenseAttachmentDAO dao) {
        this.dao = dao;
    }
    
    
    
    public void removeExpenseAttachment(Integer id) {
        dao.deleteExpenseAttachment(id);
    }


    public ExpenseAttachment getExpenseAttachment(Integer id){
        return dao.getExpenseAttachment(id);
    }

    public ExpenseAttachment updateExpenseAttachment(ExpenseAttachment function) {
        return dao.updateExpenseAttachment(function);
    }
    
    public ExpenseAttachment insertExpenseAttachment(ExpenseAttachment function) {
        return dao.insertExpenseAttachment(function);
    }
    

    public int getExpenseAttachmentListCount(Map conditions)  {
        return dao.getExpenseAttachmentListCount(conditions);
    }

    public List getExpenseAttachmentList(Map conditions, int pageNo, int pageSize, ExpenseAttachmentQueryOrder order, boolean descend) {
        return dao.getExpenseAttachmentList(conditions, pageNo, pageSize, order, descend);
    }

    public ExpenseAttachment insertExpenseAttachment(ExpenseAttachment expenseAttachment, InputStream inputStream) {
        expenseAttachment.setUploadDate(new Date());
        ExpenseAttachment ea = dao.insertExpenseAttachment(expenseAttachment);
        dao.saveExpenseAttachmentContent(ea.getId(), inputStream);
        return ea;
    }
    
    public InputStream getExpenseAttachmentContent(Integer id) {
        return dao.getExpenseAttachmentContent(id);
    }
}
