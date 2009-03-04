package com.aof.web.struts.form.business.pr;

import java.util.Calendar;

import com.aof.model.business.pr.query.CapexRequestQueryOrder;
import com.aof.web.struts.form.BaseSessionQueryForm;


public class CapexRequestQueryForm extends BaseSessionQueryForm {

    private String capex_id;

    public String getCapex_id() {
        return capex_id;
    }

    public void setCapex_id(String capex_id) {
        this.capex_id = capex_id;
    }

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String requestDateFrom;

    private String requestDateTo;

    /**
     * @return Returns the requestDateFrom.
     */
    public String getRequestDateFrom() {
        return requestDateFrom;
    }

    /**
     * @param requestDateFrom
     *            The requestDateFrom to set.
     */
    public void setRequestDateFrom(String requestDateFrom) {
        this.requestDateFrom = requestDateFrom;
    }

    /**
     * @return Returns the requestDateTo.
     */
    public String getRequestDateTo() {
        return requestDateTo;
    }

    /**
     * @param requestDateTo
     *            The requestDateTo to set.
     */
    public void setRequestDateTo(String requestDateTo) {
        this.requestDateTo = requestDateTo;
    }

    private String capex_site_id;

    /**
     * @return Returns the capex_site_id.
     */
    public String getCapex_site_id() {
        return capex_site_id;
    }

    /**
     * @param capex_site_id
     *            The capex_site_id to set.
     */
    public void setCapex_site_id(String capex_site_id) {
        this.capex_site_id = capex_site_id;
    }

    private String capex_yearlyBudget_year;
    
    public String getCapex_yearlyBudget_year() {
        return capex_yearlyBudget_year;
    }

    public void setCapex_yearlyBudget_year(String capex_yearlyBudget_year) {
        this.capex_yearlyBudget_year = capex_yearlyBudget_year;
    }

    private String amountFrom;
    private String amountTo;
    private String capex_remainAmountFrom;
    private String capex_remainAmountTo;
    
    public String getAmountFrom() {
        return amountFrom;
    }

    public void setAmountFrom(String amountFrom) {
        this.amountFrom = amountFrom;
    }

    public String getAmountTo() {
        return amountTo;
    }

    public void setAmountTo(String amountTo) {
        this.amountTo = amountTo;
    }

    public String getCapex_remainAmountFrom() {
        return capex_remainAmountFrom;
    }

    public void setCapex_remainAmountFrom(String capex_remainAmountFrom) {
        this.capex_remainAmountFrom = capex_remainAmountFrom;
    }

    public String getCapex_remainAmountTo() {
        return capex_remainAmountTo;
    }

    public void setCapex_remainAmountTo(String capex_remainAmountTo) {
        this.capex_remainAmountTo = capex_remainAmountTo;
    }
    
    public CapexRequestQueryForm()
    {
        this.setOrder(CapexRequestQueryOrder.CAPEX_ID.getName());
        this.setDescend(false);
        Calendar c = Calendar.getInstance();
        this.setCapex_yearlyBudget_year(String.valueOf(c.get(Calendar.YEAR)));
    }


    private String capex_department_id;

    /**
     * @return Returns the capex_department_id.
     */
    public String getCapex_department_id() {
        return capex_department_id;
    }

    /**
     * @param capex_department_id The capex_department_id to set.
     */
    public void setCapex_department_id(String capex_department_id) {
        this.capex_department_id = capex_department_id;
    }
    
    
}
