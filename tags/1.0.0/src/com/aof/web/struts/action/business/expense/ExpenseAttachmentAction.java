/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.web.struts.action.business.expense;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.aof.model.business.expense.Expense;
import com.aof.model.business.expense.ExpenseAttachment;
import com.aof.model.business.expense.query.ExpenseAttachmentQueryOrder;
import com.aof.model.business.expense.query.ExpenseAttachmentQueryCondition;

import com.aof.service.business.expense.ExpenseAttachmentManager;
import com.aof.service.business.expense.ExpenseManager;
import com.aof.web.struts.action.BaseAction;
import com.aof.web.struts.form.business.expense.ExpenseAttachmentQueryForm;
import com.shcnc.servlet.DownloadUploadHelper;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.action.ActionUtils;
import com.shcnc.struts.form.BeanActionForm;
import com.shcnc.struts.form.BeanForm;

import com.aof.web.struts.action.ServiceLocator;

/**
 * ExpenseAttachment的Action类
 * @author ych
 * @version 1.0 (Nov 23, 2005)
 */
public class ExpenseAttachmentAction extends BaseAction {
    
    /**
     * 查询ExpenseAttachment
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	    ExpenseAttachmentManager fm =  ServiceLocator.getExpenseAttachmentManager(request);

        ExpenseAttachmentQueryForm queryForm = (ExpenseAttachmentQueryForm) form;

        Map conditions = constructQueryMap(queryForm);

		if (queryForm.isFirstInit()) {
			queryForm.init(fm.getExpenseAttachmentListCount(conditions));
		} else {
			queryForm.init();
		}
		
		List result = fm.getExpenseAttachmentList(conditions, queryForm
				.getPageNoAsInt(), queryForm.getPageSizeAsInt(),
				ExpenseAttachmentQueryOrder.getEnum(queryForm.getOrder()),
				queryForm.isDescend());

        request.setAttribute("X_RESULTLIST", result);
        return mapping.findForward("page");
    }
    
    private Map constructQueryMap(ExpenseAttachmentQueryForm queryForm) {
        Map conditions = new HashMap();
		Integer id =
			ActionUtils.parseInt(queryForm.getId());
		if (id != null) 
		{
			conditions.put(ExpenseAttachmentQueryCondition.ID_EQ,
				 id);
		}

		String expense_id = 
			queryForm.getExpense_id();
		if(expense_id != null && expense_id.trim().length()!=0)
		{
			conditions.put(ExpenseAttachmentQueryCondition.EXPENSE_ID_EQ,
				 expense_id);
		}

		Integer fileSize =
			ActionUtils.parseInt(queryForm.getFileSize());
		if (fileSize != null) 
		{
			conditions.put(ExpenseAttachmentQueryCondition.FILESIZE_EQ,
				 fileSize);
		}
		String fileName = 
			queryForm.getFileName();
		if(fileName != null && fileName.trim().length()!=0)
		{
			conditions.put(ExpenseAttachmentQueryCondition.FILENAME_LIKE,
				 fileName);
		}
		String description = 
			queryForm.getDescription();
		if(description != null && description.trim().length()!=0)
		{
			conditions.put(ExpenseAttachmentQueryCondition.DESCRIPTION_LIKE,
				 description);
		}
		String uploadDate = 
			queryForm.getUploadDate();
		if(uploadDate != null && uploadDate.trim().length()!=0)
		{
			conditions.put(ExpenseAttachmentQueryCondition.UPLOADDATE_EQ,
				 uploadDate);
		}		
        return conditions;
    }

    /**
     * 编辑ExpenseAttachment
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ExpenseAttachment expenseAttachment = getExpenseAttachmentFromRequest(request);    
		request.setAttribute("x_expenseAttachment",expenseAttachment);
        if (!isBack(request)) {
            BeanActionForm expenseAttachmentForm = (BeanActionForm) getForm("/updateExpenseAttachment", request);
            expenseAttachmentForm.populate(expenseAttachment, BeanActionForm.TO_FORM);
        }
        return mapping.findForward("page");
    }

    /**
     * 新增ExpenseAttachment
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
	public ActionForward newObject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        final boolean isSelf = this.isGlobal(request);

        if (isSelf) {
            request.setAttribute("x_postfix", "_self");
        } else {
            request.setAttribute("x_postfix", "_other");
        }
        Expense expense=this.getExpenseFromRequest(request);
        if (!isBack(request)) {
            ExpenseAttachment expenseAttachment = new ExpenseAttachment();
            expenseAttachment.setExpense(expense);
            BeanForm expenseAttachmentForm = (BeanForm) getForm("/insertExpenseAttachment_self", request);
            expenseAttachmentForm.populateToForm(expenseAttachment);
        }
        return mapping.findForward("page");
    }

    /**
     * 更新ExpenseAttachment
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        BeanActionForm expenseAttachmentForm = (BeanActionForm) form;
        ExpenseAttachment expenseAttachment = new ExpenseAttachment();
        expenseAttachmentForm.populate(expenseAttachment, BeanActionForm.TO_BEAN);
        
        ExpenseAttachmentManager expenseAttachmentManager =ServiceLocator.getExpenseAttachmentManager(request);
        request.setAttribute("X_OBJECT", expenseAttachmentManager.updateExpenseAttachment(expenseAttachment));
        request.setAttribute("X_ROWPAGE", "expenseAttachment/row.jsp");
        
        return mapping.findForward("success");
    }
    
    private ExpenseAttachment getExpenseAttachmentFromRequest(HttpServletRequest request) throws Exception {
        Integer id = ActionUtils.parseInt(request.getParameter("id"));
        ExpenseAttachmentManager expenseAttachmentManager =ServiceLocator.getExpenseAttachmentManager(request);
        ExpenseAttachment expenseAttachment = expenseAttachmentManager.getExpenseAttachment(id);
        if (expenseAttachment == null)
            throw new ActionException("expenseAttachment.notFound", id);
        return expenseAttachment;
    }

    private Expense getExpenseFromRequest(HttpServletRequest request) throws Exception {
        String id = request.getParameter("expense_id");
        ExpenseManager expenseManager = ServiceLocator.getExpenseManager(request);
        Expense expense = expenseManager.getExpense(id);
        if (expense == null)
            throw new ActionException("expense.notFound", id);
        return expense;
    }
    
    /**
     * 下载ExpenseAttachment
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward download(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        ExpenseAttachment expenseAttachment = this.getExpenseAttachmentFromRequest(request);

        InputStream in = ServiceLocator.getExpenseAttachmentManager(request).getExpenseAttachmentContent(expenseAttachment.getId());
        if (in != null) {
            try {
                if (expenseAttachment.getFileSize() == 0) {
                    throw new ActionException("expenseAttachment.error.fileSize.zero");
                } else {
                    DownloadUploadHelper.download(
                            in, 
                            expenseAttachment.getFileName(), 
                            DownloadUploadHelper.getMime(expenseAttachment.getFileName()), expenseAttachment.getFileSize(),
                            request,
                            response,
                            true);
                }
            } finally {
                in.close();
            }
        }
        return null;
    }

    /**
     * 插入新增的ExpenseAttachment
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward insert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        final boolean isSelf = this.isGlobal(request);
        if (isSelf) {
            request.setAttribute("x_postfix", "_self");
        } else {
            request.setAttribute("x_postfix", "_other");
        }
        
        BeanForm expenseAttachmentForm = (BeanForm) form;
        ExpenseAttachment expenseAttachment = new ExpenseAttachment();
        expenseAttachmentForm.populateToBean(expenseAttachment,request);
        
        FormFile file = (FormFile) expenseAttachmentForm.get("fileContent");
        expenseAttachment.setFileName(file.getFileName());

        ExpenseAttachmentManager expenseAttachmentManager = ServiceLocator.getExpenseAttachmentManager(request);
        ExpenseAttachment newHc = null;
        if (file.getFileSize() > 0) {
            expenseAttachment.setFileSize(file.getFileSize());
            newHc = expenseAttachmentManager.insertExpenseAttachment(expenseAttachment, file.getInputStream());
        } else {
            throw new ActionException("expenseAttachment.error.fileSize.zero");
        }
        request.setAttribute("X_OBJECT", newHc);
        request.setAttribute("X_ROWPAGE", "expenseAttachment/row.jsp");

        return mapping.findForward("success");
    }

    /**
     * 删除ExpenseAttachment
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ExpenseAttachment expenseAttachment = getExpenseAttachmentFromRequest(request);
        ExpenseAttachmentManager eam = ServiceLocator.getExpenseAttachmentManager(request);
        eam.removeExpenseAttachment(expenseAttachment.getId());
        return mapping.findForward("success");
    }
}