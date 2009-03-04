package com.aof.web.struts.form.business.pr;

import com.aof.web.struts.form.BaseSessionQueryForm;


public class YearlyBudgetDepartmentQueryForm extends BaseSessionQueryForm {

	/*id*/
	private String id;
	public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

	/*keyPropertyList*/

	/*kmtoIdList*/


	/*mtoIdList*/
	private String yearlyBudget_id;
	public String getYearlyBudget_id() {
        return yearlyBudget_id;
    }
    public void setYearlyBudget_id(String yearlyBudget_id) {
        this.yearlyBudget_id = yearlyBudget_id;
    }
	private String department_id;
	public String getDepartment_id() {
        return department_id;
    }
    public void setDepartment_id(String department_id) {
        this.department_id = department_id;
    }
      

	/*property*/
}
