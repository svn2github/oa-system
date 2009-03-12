/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.web.struts.action.admin;

import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import net.sourceforge.model.admin.ExpenseCategory;
import net.sourceforge.model.admin.Site;
import net.sourceforge.model.admin.query.ExpenseCategoryQueryCondition;
import net.sourceforge.model.admin.query.ExpenseCategoryQueryOrder;
import net.sourceforge.model.metadata.EnabledDisabled;
import net.sourceforge.model.metadata.ExpenseType;
import net.sourceforge.service.admin.ExpenseCategoryManager;
import net.sourceforge.service.admin.ExpenseSubCategoryManager;
import net.sourceforge.utils.SessionTempFile;
import net.sourceforge.web.struts.action.BaseAction;
import net.sourceforge.web.struts.action.ServiceLocator;
import net.sourceforge.web.struts.form.admin.ExpenseCategoryQueryForm;
import com.shcnc.hibernate.PersistentEnum;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.action.ActionUtils;
import com.shcnc.struts.form.BeanForm;
import com.shcnc.utils.BeanHelper;
import com.shcnc.utils.ExportUtil;
import com.shcnc.utils.Exportable;

/**
 * the struts action class for domain model expenseCategory
 * 
 * @author shilei
 * @version 1.0 (Nov 15, 2005)
 */
public class ExpenseCategoryAction extends BaseAction {

    /**
     * the action method for searching expenseCategory
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ExpenseCategoryManager fm = ServiceLocator.getExpenseCategoryManager(request);

        List siteList = this.getAndCheckGrantedSiteList(request);

        ExpenseCategoryQueryForm queryForm = (ExpenseCategoryQueryForm) form;
        if (queryForm.getSite_id() == null) {
            if (siteList.size() > 0) {
                queryForm.setSite_id(((Site) siteList.get(0)).getId().toString());
            }
        }

        Map conditions = constructQueryMap(queryForm);

        if (StringUtils.isEmpty(queryForm.getOrder())) {
            queryForm.setOrder(ExpenseCategoryQueryOrder.DESCRIPTION.getName());
            queryForm.setDescend(false);
        }
        else if(ExpenseCategoryQueryOrder.getEnum(queryForm.getOrder())==null)
        {
            throw new RuntimeException("order not found");
        }

        String exportType = queryForm.getExportType();
        if (StringUtils.isNotEmpty(exportType)) {
            List data = fm.getExpenseCategoryList(conditions, 0, -1, ExpenseCategoryQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend());

            int index = SessionTempFile.createNewTempFile(request);
            String fileName = "expenseCategory";
            String suffix = ExportUtil.export(exportType, data, request, new FileOutputStream(SessionTempFile.getTempFile(index, request)), new Exportable() {

                public void exportHead(List row, HttpServletRequest request) throws Exception {
                    MessageResources messages = getResources(request);
                    row.add(messages.getMessage(getLocale(request), "expenseCategory.description"));
                    row.add(messages.getMessage(getLocale(request), "expenseCategory.enabled"));
                }

                public void exportRow(List row, Object data, HttpServletRequest request) throws Exception {
                    row.add(BeanHelper.getBeanPropertyValue(data, "description"));
                    if (isEnglish(request))
                        row.add(BeanHelper.getBeanPropertyValue(data, "enabled.engShortDescription"));
                    else
                        row.add(BeanHelper.getBeanPropertyValue(data, "enabled.chnShortDescription"));
                }
            });
            return new ActionForward("download/" + index + "/" + URLEncoder.encode(fileName, "UTF-8") + '.' + suffix, true);
        }

        if (queryForm.isFirstInit()) {
            queryForm.init(fm.getExpenseCategoryListCount(conditions));
        } else {
            queryForm.init();
        }

        List result = fm.getExpenseCategoryList(conditions, queryForm.getPageNoAsInt(), queryForm.getPageSizeAsInt(), ExpenseCategoryQueryOrder
                .getEnum(queryForm.getOrder()), queryForm.isDescend());
        request.setAttribute("X_RESULTLIST", result);
        request.setAttribute("x_siteList", siteList);
        putEnumListToRequest(request);
        return mapping.findForward("page");
    }

    private Map constructQueryMap(ExpenseCategoryQueryForm queryForm) {
        Map conditions = new HashMap();
        /* id */
        Integer id = ActionUtils.parseInt(queryForm.getId());
        if (id != null) {
            conditions.put(ExpenseCategoryQueryCondition.ID_EQ, id);
        }

        /* keyPropertyList */

        /* kmtoIdList */

        /* mtoIdList */
        Integer site_id = ActionUtils.parseInt(queryForm.getSite_id());
        if (site_id != null) {
            conditions.put(ExpenseCategoryQueryCondition.SITE_ID_EQ, site_id);
        }

        /* property */
        String description = queryForm.getDescription();
        if (description != null && description.trim().length() != 0) {
            conditions.put(ExpenseCategoryQueryCondition.DESCRIPTION_LIKE, description);
        }
        String type = queryForm.getType();
        if (type != null && type.trim().length() != 0) {
            conditions.put(ExpenseCategoryQueryCondition.TYPE_EQ, type);
        }
        String referenceErpId = queryForm.getReferenceErpId();
        if (referenceErpId != null && referenceErpId.trim().length() != 0) {
            conditions.put(ExpenseCategoryQueryCondition.REFERENCEERPID_LIKE, referenceErpId);
        }
        Integer enabled = ActionUtils.parseInt(queryForm.getEnabled());
        if (enabled != null) {
            conditions.put(ExpenseCategoryQueryCondition.ENABLED_EQ, enabled);
        }
        return conditions;
    }

    private ExpenseCategory getExpenseCategoryFormRequest(HttpServletRequest request) throws Exception {
        Integer id = ActionUtils.parseInt(request.getParameter("id"));
        ExpenseCategoryManager expenseCategoryManager = ServiceLocator.getExpenseCategoryManager(request);
        ExpenseCategory expenseCategory = expenseCategoryManager.getExpenseCategory(id);
        if (expenseCategory == null)
            throw new ActionException("expenseCategory.notFound", id);
        return expenseCategory;
    }

    private List getChildrenOfExpenseCategory(ExpenseCategory expenseCategory, HttpServletRequest request) throws Exception {
        ExpenseSubCategoryManager expenseSubCategoryManager = ServiceLocator.getExpenseSubCategoryManager(request);
        List subList = expenseSubCategoryManager.getChildrenOfExpenseCategory(expenseCategory.getId());
        return subList;
    }

    /**
     * the action method for editing expenseCategory
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ExpenseCategory expenseCategory = this.getExpenseCategoryFormRequest(request);

        checkSite(expenseCategory.getSite(), request);
        request.setAttribute("x_expenseCategory", expenseCategory);

        List subList = getChildrenOfExpenseCategory(expenseCategory, request);
        request.setAttribute("x_subList", subList);

        if (!isBack(request)) {
            BeanForm expenseCategoryForm = (BeanForm) getForm("/updateExpenseCategory", request);
            expenseCategoryForm.populate(expenseCategory, BeanForm.TO_FORM);
        }
        putEnumListToRequest(request);
        return mapping.findForward("page");
    }

    private Site getAndCheckSite(HttpServletRequest request) throws Exception {
        return this.getAndCheckSite("site_id", request);
    }

    private void putEnumListToRequest(HttpServletRequest request) {
        request.setAttribute("X_ENABLEDDISABLEDLIST", PersistentEnum.getEnumList(EnabledDisabled.class));
        request.setAttribute("x_typeList", PersistentEnum.getEnumList(ExpenseType.class));
    }

    /**
     * the action method for updating expenseCategory
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        ExpenseCategory oldEc = this.getExpenseCategoryFormRequest(request);

        Site site = getAndCheckSite(request);

        BeanForm expenseCategoryForm = (BeanForm) form;

        ExpenseCategory expenseCategory = new ExpenseCategory();
        expenseCategoryForm.populate(expenseCategory, BeanForm.TO_BEAN);
        expenseCategory.setReferenceErpId(oldEc.getReferenceErpId());
        expenseCategory.setSite(site);

        ExpenseCategoryManager expenseCategoryManager = ServiceLocator.getExpenseCategoryManager(request);

        boolean newIsTravel = expenseCategory.getEnabled().equals(EnabledDisabled.ENABLED) && expenseCategory.getType().equals(ExpenseType.TRAVEL);

        boolean oldIsNotTravel = oldEc.getEnabled().equals(EnabledDisabled.DISABLED) || !oldEc.getType().equals(ExpenseType.TRAVEL);

        if (oldIsNotTravel && newIsTravel) {
            if (this.getTravelExpenseCategoryCountOfSite(site, request) > 0) {
                throw new ActionException("expenseCategory.error.alreadyHasTravel");
            }

        }

        expenseCategoryManager.updateExpenseCategory(expenseCategory);

        request.setAttribute("X_OBJECT", expenseCategory);
        request.setAttribute("X_ROWPAGE", "expenseCategory/row.jsp");

        return mapping.findForward("success");
    }

    /**
     * the action method for creating new expenseCategory
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward newObject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Site site = getAndCheckSite(request);
        request.setAttribute("x_site", site);

        if (!isBack(request)) {
            ExpenseCategory expenseCategory = new ExpenseCategory();
            expenseCategory.setSite(site);
            BeanForm expenseCategoryForm = (BeanForm) getForm("/insertExpenseCategory", request);
            expenseCategoryForm.populate(expenseCategory, BeanForm.TO_FORM);
        }

        putEnumListToRequest(request);

        return mapping.findForward("page");
    }

    /**
     * the action method for inserting new expenseCategory to db
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward insert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Site site = getAndCheckSite(request);

        ExpenseCategory expenseCategory = new ExpenseCategory();
        BeanForm expenseCategoryForm = (BeanForm) form;
        expenseCategoryForm.populate(expenseCategory, BeanForm.TO_BEAN);

        expenseCategory.setSite(site);

        ExpenseCategoryManager cm = ServiceLocator.getExpenseCategoryManager(request);

        if (expenseCategory.getEnabled().equals(EnabledDisabled.ENABLED) && expenseCategory.getType().equals(ExpenseType.TRAVEL)) {
            if (this.getTravelExpenseCategoryCountOfSite(site, request) > 0) {
                throw new ActionException("expenseCategory.error.alreadyHasTravel");
            }
        }

        cm.insertExpenseCategory(expenseCategory);

        request.setAttribute("X_OBJECT", expenseCategory);
        request.setAttribute("X_ROWPAGE", "expenseCategory/row.jsp");

        return mapping.findForward("success");
    }

    private int getTravelExpenseCategoryCountOfSite(Site site, HttpServletRequest request) throws Exception {
        ExpenseCategoryManager cm = ServiceLocator.getExpenseCategoryManager(request);
        Map conds = new HashMap();
        conds.put(ExpenseCategoryQueryCondition.ENABLED_EQ, EnabledDisabled.ENABLED.getEnumCode());
        conds.put(ExpenseCategoryQueryCondition.TYPE_EQ, ExpenseType.TRAVEL.getEnumCode());
        conds.put(ExpenseCategoryQueryCondition.SITE_ID_EQ, site.getId());

        return cm.getExpenseCategoryListCount(conds);
    }
}