/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.web.struts.action.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aof.model.admin.ExpenseCategory;
import com.aof.model.admin.ExpenseSubCategory;
import com.aof.model.admin.query.ExpenseSubCategoryQueryCondition;
import com.aof.model.admin.query.ExpenseSubCategoryQueryOrder;
import com.aof.model.metadata.EnabledDisabled;
import com.aof.model.metadata.ExpenseType;
import com.aof.model.metadata.YesNo;

import com.aof.service.admin.ExpenseCategoryManager;
import com.aof.service.admin.ExpenseSubCategoryManager;
import com.aof.web.struts.action.BaseAction;
import com.aof.web.struts.action.ServiceLocator;
import com.aof.web.struts.form.admin.ExpenseSubCategoryQueryForm;
import com.shcnc.hibernate.PersistentEnum;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.action.ActionUtils;
import com.shcnc.struts.form.BeanForm;

/**
 * the struts action class for domain model expense sub category
 * 
 * @author shilei
 * @version 1.0 (Nov 15, 2005)
 */
public class ExpenseSubCategoryAction extends BaseAction {

    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ExpenseSubCategoryManager fm = ServiceLocator.getExpenseSubCategoryManager(request);

        ExpenseSubCategoryQueryForm queryForm = (ExpenseSubCategoryQueryForm) form;

        Map conditions = constructQueryMap(queryForm);
        
        if(StringUtils.isEmpty(queryForm.getOrder()))
        {
            queryForm.setOrder(ExpenseSubCategoryQueryOrder.DESCRIPTION.getName());
            queryForm.setDescend(false);
        }
        
        if(queryForm.isFirstInit())
            queryForm.init(fm.getExpenseSubCategoryListCount(conditions),5);
        else
            queryForm.init();

        int pageNo = queryForm.getPageNoAsInt();
        int pageSize = queryForm.getPageSizeAsInt();

        request.setAttribute("X_RESULTLIST", fm.getExpenseSubCategoryList(conditions, pageNo, pageSize, ExpenseSubCategoryQueryOrder.getEnum(queryForm
                .getOrder()), queryForm.isDescend()));
        return mapping.findForward("page");
    }

    private Map constructQueryMap(ExpenseSubCategoryQueryForm queryForm) {
        Map conditions = new HashMap();

        Integer id = ActionUtils.parseInt(queryForm.getId());
        if (id != null) {
            conditions.put(ExpenseSubCategoryQueryCondition.ID_EQ, id);
        }

        Integer expenseCategory_id = ActionUtils.parseInt(queryForm.getExpenseCategory_id());
        if (expenseCategory_id != null) {
            conditions.put(ExpenseSubCategoryQueryCondition.EXPENSECATEGORY_ID_EQ, expenseCategory_id);
        }

        String description = queryForm.getDescription();
        if (description != null && description.trim().length() != 0) {
            conditions.put(ExpenseSubCategoryQueryCondition.DESCRIPTION_LIKE, description);
        }
        String referenceErpId = queryForm.getReferenceErpId();
        if (referenceErpId != null && referenceErpId.trim().length() != 0) {
            conditions.put(ExpenseSubCategoryQueryCondition.REFERENCEERPID_LIKE, referenceErpId);
        }
        String enabled = queryForm.getEnabled();
        if (enabled != null && enabled.trim().length() != 0) {
            conditions.put(ExpenseSubCategoryQueryCondition.ENABLED_EQ, enabled);
        }
        return conditions;
    }

    private ExpenseSubCategory getExpenseSubCategoryFromRequest(HttpServletRequest request) throws Exception {
        Integer id = ActionUtils.parseInt(request.getParameter("id"));
        ExpenseSubCategoryManager expenseSubCategoryManager = ServiceLocator.getExpenseSubCategoryManager(request);
        ExpenseSubCategory expenseSubCategory = expenseSubCategoryManager.getExpenseSubCategory(id);
        if (expenseSubCategory == null)
            throw new ActionException("expenseSubCategory.notFound", id);
        return expenseSubCategory;
    }

    /**
     * the action method for editing ExpenseSubCategory
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ExpenseSubCategory expenseSubCategory = getExpenseSubCategoryFromRequest(request);
        this.checkSite(expenseSubCategory.getExpenseCategory().getSite(), request);
        request.setAttribute("x_esc", expenseSubCategory);

        if (!isBack(request)) {
            BeanForm expenseSubCategoryForm = (BeanForm) getForm("/updateExpenseSubCategory", request);
            expenseSubCategoryForm.populate(expenseSubCategory, BeanForm.TO_FORM);
        }

        if (expenseSubCategory.getExpenseCategory().getType().equals(ExpenseType.TRAVEL)) {
            request.setAttribute("x_isTravel", Boolean.TRUE);
        }

        putEnumListToRequest(request);
        return mapping.findForward("page");
    }

    private void putEnumListToRequest(HttpServletRequest request) {
        request.setAttribute("X_ENABLEDDISABLEDLIST", PersistentEnum.getEnumList(EnabledDisabled.class));
        request.setAttribute("x_yesNoList", PersistentEnum.getEnumList(YesNo.class));
    }

    /**
     * the action method for updating ExpenseSubCategory
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ExpenseSubCategory oldEsc = getExpenseSubCategoryFromRequest(request);

        ExpenseCategory ec = oldEsc.getExpenseCategory();
        this.checkSite(ec.getSite(), request);

        ExpenseSubCategory expenseSubCategory = new ExpenseSubCategory();
        BeanForm expenseSubCategoryForm = (BeanForm) form;
        expenseSubCategoryForm.populate(expenseSubCategory, BeanForm.TO_BEAN);
        expenseSubCategory.setReferenceErpId(oldEsc.getReferenceErpId());

        expenseSubCategory.setExpenseCategory(ec);

        if (ec.getType().equals(ExpenseType.TRAVEL)) {

            boolean newIsHotel = expenseSubCategory.getEnabled().equals(EnabledDisabled.ENABLED) && expenseSubCategory.getIsHotel().equals(YesNo.YES);

            boolean oldIsNotHotel = (oldEsc.getEnabled().equals(EnabledDisabled.DISABLED) || oldEsc.getIsHotel().equals(YesNo.NO));

            if (oldIsNotHotel && newIsHotel) {
                if (getHotelExpenseSubCategoryCount(ec, request) > 0) {
                    throw new ActionException("expenseSubCategory.error.alreadyHasHotel");
                }
            }
        } else {
            expenseSubCategory.setIsHotel(YesNo.NO);
        }

        ExpenseSubCategoryManager expenseSubCategoryManager = ServiceLocator.getExpenseSubCategoryManager(request);

        request.setAttribute("X_OBJECT", expenseSubCategoryManager.updateExpenseSubCategory(expenseSubCategory));
        request.setAttribute("X_ROWPAGE", "expenseSubCategory/row.jsp");

        return mapping.findForward("success");
    }

    private ExpenseCategory getExpenseCategoryFromRequest(HttpServletRequest request) throws NumberFormatException, Exception {
        ExpenseCategoryManager cm = ServiceLocator.getExpenseCategoryManager(request);

        String expenseCategory_id = request.getParameter("expenseCategory_id");
        ExpenseCategory expenseCategory = cm.getExpenseCategory(Integer.valueOf(expenseCategory_id));

        return expenseCategory;
    }

    /**
     * the action method for creating new ExpenseSubCategory
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward newObject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ExpenseCategory expenseCategory = getExpenseCategoryFromRequest(request);
        this.checkSite(expenseCategory.getSite(), request);

        if (!isBack(request)) {
            ExpenseSubCategory expenseSubCategory = new ExpenseSubCategory();
            expenseSubCategory.setExpenseCategory(expenseCategory);

            BeanForm expenseSubCategoryForm = (BeanForm) getForm("/insertExpenseSubCategory", request);
            expenseSubCategoryForm.populate(expenseSubCategory, BeanForm.TO_FORM);
        }

        if (expenseCategory.getType().equals(ExpenseType.TRAVEL)) {
            request.setAttribute("x_isTravel", Boolean.TRUE);
        }

        putEnumListToRequest(request);
        return mapping.findForward("page");
    }

    /**
     * the action method for inserting new ExpenseSubCategory to db
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward insert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        ExpenseCategory expenseCategory = getExpenseCategoryFromRequest(request);
        this.checkSite(expenseCategory.getSite(), request);

        BeanForm expenseSubCategoryForm = (BeanForm) form;
        ExpenseSubCategory expenseSubCategory = new ExpenseSubCategory();
        expenseSubCategoryForm.populate(expenseSubCategory, BeanForm.TO_BEAN);
        expenseSubCategory.setExpenseCategory(expenseCategory);

        if (expenseCategory.getType().equals(ExpenseType.TRAVEL)) {
            if (expenseSubCategory.getEnabled().equals(EnabledDisabled.ENABLED) && expenseSubCategory.getIsHotel().equals(YesNo.YES)) {
                if (getHotelExpenseSubCategoryCount(expenseCategory, request) > 0) {
                    throw new ActionException("expenseSubCategory.error.alreadyHasHotel");
                }
            }
        } else {
            expenseSubCategory.setIsHotel(YesNo.NO);
        }

        ExpenseSubCategoryManager cm = ServiceLocator.getExpenseSubCategoryManager(request);

        request.setAttribute("X_OBJECT", cm.insertExpenseSubCategory(expenseSubCategory));
        request.setAttribute("X_ROWPAGE", "expenseSubCategory/row.jsp");

        return mapping.findForward("success");
    }

    private int getHotelExpenseSubCategoryCount(ExpenseCategory expenseCategory, HttpServletRequest request) throws Exception {

        ExpenseSubCategoryManager cm = ServiceLocator.getExpenseSubCategoryManager(request);
        Map conds = new HashMap();
        conds.put(ExpenseSubCategoryQueryCondition.ENABLED_EQ, EnabledDisabled.ENABLED.getEnumCode());
        conds.put(ExpenseSubCategoryQueryCondition.ISHOTEL_EQ, YesNo.YES.getEnumCode());
        conds.put(ExpenseSubCategoryQueryCondition.EXPENSECATEGORY_ID_EQ, expenseCategory.getId());
        return cm.getExpenseSubCategoryListCount(conds);
    }
}