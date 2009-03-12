/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.web.struts.action.business.expense;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.DynaBean;


import net.sourceforge.model.admin.Department;
import net.sourceforge.model.admin.ExpenseCategory;
import net.sourceforge.model.admin.Function;

import net.sourceforge.model.admin.Site;
import net.sourceforge.model.admin.User;
import net.sourceforge.service.admin.ExpenseCategoryManager;
import net.sourceforge.service.admin.UserManager;
import net.sourceforge.web.struts.action.ServiceLocator;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.form.DynaBeanComboBox;

/**
 * 编辑他人的报销申请的页面管理器(combobox 联动)
 * 
 * @author ych
 * @version 1.0 (Nov 19, 2005)
 */
public class ExpenseOtherPageManager {

	public ExpenseOtherPageManager(DynaBean form, User creator,
			Function function, HttpServletRequest request)
			throws NumberFormatException, Exception {
		this.request = request;
		this.function = function;
		this.creator = creator;

		this.comboSite = new DynaBeanComboBox("id", "name", form,
				"department_site_id");

		this.comboDepartment = new DynaBeanComboBox("id", "name", form,
				"department_id");
        
        this.comboExpenseCategory = new DynaBeanComboBox("id", "name", form,
                "expenseCategory_id");

		this.comboRequestor = new DynaBeanComboBox("id", "name", form,
				"requestor_id");

		

	}

	public void process() throws Exception {
		this.processComboSite();
		this.processComboDepartment();
        this.processComboExpenseCategory();
		this.processComboRequestor();
        this.processBaseCurrency();

	}

	

	private void processBaseCurrency() {
        if (site==null) {
            request.setAttribute("x_baseCurrency","");
        } else {
            request.setAttribute("x_baseCurrency",site.getBaseCurrency().getName());
        }
        
    }

    private void processComboExpenseCategory() throws Exception  {
        List expenseCategoryList = new ArrayList();
        if (site==null) {
            this.comboExpenseCategory.setEmptyList();
            request.setAttribute("x_travelExpenseCategoryId","-1");
        }   else {
            ExpenseCategoryManager ecm=ServiceLocator.getExpenseCategoryManager(request);
            ExpenseCategory travelExpense=ecm.getEnabledTravelExpenseCategoryOfSite(site.getId().intValue());
            if (travelExpense!=null) {
                expenseCategoryList.add(travelExpense);
                request.setAttribute("x_travelExpenseCategoryId",travelExpense.getId());
            } else {
                request.setAttribute("x_travelExpenseCategoryId","-1");
            }
            expenseCategoryList.addAll(ecm.getEnabledNotTravelExpenseCategoryListOfSite(site.getId().intValue()));
            this.comboExpenseCategory.setList(expenseCategoryList);
        }
        
        request.setAttribute("x_expenseCategoryList", expenseCategoryList);
        
    }

    private void processComboRequestor() throws Exception {
		UserManager um = ServiceLocator.getUserManager(request);

		if (this.department == null) {
			comboRequestor.setEmptyList();
		} else {

			this.comboRequestor.setList(um
					.getEnabledUserListOfDepartment(this.department));
		}
		
		request.setAttribute("x_requestorList", comboRequestor.getList());
	}

	private void processComboDepartment() throws Exception {
		UserManager um = ServiceLocator.getUserManager(request);
		List departmentList = um.getGrantedDepartmentListOfSite(creator, site, function);

		List dList = new ArrayList();
		for (Iterator iter = departmentList.iterator(); iter.hasNext();) {
			Department d = (Department) iter.next();
			if (d.isGranted()) {
				dList.add(d);
			}
		}

		this.comboDepartment.setList(dList);
		this.department = (Department) this.comboDepartment.getSelectedItem();

		request.setAttribute("x_departmentList", departmentList);

	}

	private void processComboSite() throws Exception {
		UserManager um = ServiceLocator.getUserManager(request);
		
        List siteList = um.getSiteOfGrantedSiteDeparmentList(creator, function);
        if (siteList.isEmpty()) {
            throw new ActionException("errors.noDepartmentPermission");
        }

        this.comboSite.setList(siteList);
        this.site = (Site) this.comboSite.getSelectedItem();
        request.setAttribute("x_baseCurrency", this.site.getBaseCurrency());
		request.setAttribute("x_siteList", this.comboSite.getList());
	}

	private DynaBeanComboBox comboSite;

	private DynaBeanComboBox comboDepartment;
    
    private DynaBeanComboBox comboExpenseCategory;

	private DynaBeanComboBox comboRequestor;

	private HttpServletRequest request;

	private User creator = null;

	private Site site = null;

	private Department department = null;

	private Function function;

}
